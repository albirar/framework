/*
 * This file is part of "albirar framework" project.
 * 
 * "albirar framework" is free software: you can redistribute it and/or modify it under the terms of the GNU General
 * Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * "albirar framework" is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with calendar. If not, see
 * <http://www.gnu.org/licenses/>.
 * 
 * Copyright (C) 2013 Octavi Fornés octavi@fornes.cat
 */
package cat.albirar.framework.dynabean.impl;

import static cat.albirar.framework.dynabean.impl.DynaBeanImplementationUtils.isGetter;
import static cat.albirar.framework.dynabean.impl.DynaBeanImplementationUtils.isPropertyMethod;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import cat.albirar.framework.dynabean.DynaBeanUtils;
import cat.albirar.framework.dynabean.IDynaBeanFactory;
import cat.albirar.framework.dynabean.visitor.IDynaBeanVisitor;

/**
 * A proxy for create dynamic beans from interfaces.
 * 
 * For use with interfaces that represents a Java Bean. <br>
 * <b>Use</b>
 * 
 * <pre>
 * InterfaceJavaBean a;
 * 
 * a = {@link DynaBeanUtils#instanceFactory()}.{@link IDynaBeanFactory#newDynaBean(Class) newDynaBean(InterfaceJavaBean.class)};
 * ...
 * a.setXXX("xxx");
 * </pre>
 * 
 * The proxy is a JDK Proxy framework instance.
 * 
 * @param <T> The type to implement
 * 
 * @author <a href="mailto:ofornes@albirar.cat">Octavi Fornés ofornes@albirar.cat</a>
 * @since 1.0.0
 */
public class DynaBeanImpl<T> implements InvocationHandler, Serializable
{
    private static final long serialVersionUID = 0L;

    private static final Logger logger = LoggerFactory.getLogger(DynaBeanImpl.class);

    private DynaBeanDescriptor<T> descriptor;

    private Map<String, Object> values;

    private transient IDynaBeanVisitor visitor;

    private DynaBeanImpl()
    {
        values = Collections.synchronizedMap(new TreeMap<String, Object>());
        visitor = null;
    }

    /**
     * Constructor with a prepared descriptor. Used to reduce memory consumption and CPU cycles.
     * 
     * @param descriptor The descriptor
     */
    DynaBeanImpl(DynaBeanDescriptor<T> descriptor)
    {
        this();
        this.descriptor = descriptor;
        doInstantiate(null);
    }

    /**
     * Clone constructor.
     * 
     * @param origin The origin for data
     */
    DynaBeanImpl(DynaBeanImpl<T> origin)
    {
        this();
        this.descriptor = origin.descriptor;
        doInstantiate(origin);
    }

    /**
     * Constructor with type to implement.
     * 
     * @param typeToImplement The interface type to implement
     * @throws IllegalArgumentException If the type is not an interface
     */
    DynaBeanImpl(IDynaBeanImplementationFactory factory, Class<T> typeToImplement)
    {
        this();

        descriptor = factory.getDescriptorFor(typeToImplement);
        doInstantiate(null);
    }

    /**
     * Check and assign the values for the properties.
     * 
     * @param origin The origin to copy from. Can be null, so new instance is created and only default values are
     *            assigned
     */
    private void doInstantiate(DynaBeanImpl<T> origin)
    {
        // Assign values
        if(origin != null)
        {
            // Copy instantiation
            for(DynaBeanPropertyDescriptor propDesc : descriptor.getProperties())
            {
                if(Cloneable.class.isAssignableFrom(descriptor.getImplementedType()))
                {
                    // clone values...
                    values.put(propDesc.getPropertyName(), nullSafeValue(cloneValue(propDesc, origin.values.get(propDesc.getPropertyName())), propDesc));
                }
                else
                {
                    // copy values
                    values.put(propDesc.getPropertyName(), nullSafeValue(origin.values.get(propDesc.getPropertyName()), propDesc));
                }
            }
        }
        else
        {
            // Default instantiation
            for(DynaBeanPropertyDescriptor prop : descriptor.getProperties())
            {
                // Test: dynaBean; implementation; defaultValue; null (or 0 or false)
                if(prop.dynaBean)
                {
                    // Instantiate by factory
                    values.put(prop.propertyName, descriptor.getFactory().newDynaBean(prop.getPropertyType()));
                }
                else
                {
                    if(prop.defaultImplementation != null)
                    {
                        // Instantiate a concrete class
                        try
                        {
                            values.put(prop.propertyName, prop.defaultImplementation.newInstance());
                        }
                        catch(InstantiationException | IllegalAccessException e)
                        {
                            String s;

                            s = String.format("On instantiate '%s.%s': %s", descriptor.getImplementedType(), prop.propertyName, e.getMessage());
                            logger.error(s, e);
                            throw new RuntimeException(s, e);
                        }
                    }
                    else
                    {
                        values.put(prop.propertyName, nullSafeValue(cloneValue(prop, prop.defaultValue), prop));
                    }
                }
            }
        }
    }

    /**
     * The implemented interface for this dynaBean.
     * 
     * @return implementedType The implemented type
     */
    public Class<T> getImplementedType()
    {
        return descriptor.getImplementedType();
    }

    /**
     * Visitor to call in get/set events.
     * 
     * @param visitor The visitor.
     */
    public void setVisitor(IDynaBeanVisitor visitor)
    {
        this.visitor = visitor;
    }

    /**
     * Visitor to call in get/set events.
     * 
     * @return The visitor.
     */
    public IDynaBeanVisitor getVisitor()
    {
        return visitor;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
    {
        return doInvoke(method, args);
    }

    /**
     * Invocation operation.
     * 
     * @param method The invoked method
     * @param args The arguments, if any
     * @return The result. Can be the value for a property or the result of {@link #equals(Object)}, {@link #hashCode()}
     *         or {@link #toString()}
     * @throws Throwable If errors are produced on invoking
     */
    protected Object doInvoke(Method method, Object... args) throws Throwable
    {
        DynaBeanPropertyDescriptor propDesc;
        String name;

        name = method.getName();
        // Check if is a get/set method
        if(isPropertyMethod(name))
        {
            if((propDesc = descriptor.getPropertyByMethodName(name)) != null)
            {
                if(isGetter(name))
                {
                    return doGetter(propDesc);
                }
                // is a setter
                doSetter(propDesc, args);
                return null;
            }
        }
        // Can be hashCode, toString or equals
        if("hashCode".equals(name))
        {
            return hashCode();
        }
        if("toString".equals(name))
        {
            return toString();
        }
        if("equals".equals(name))
        {
            return equals(args[0]);
        }
        if("clone".equals(name))
        {
            return clone();
        }
        // Not support any other method call
        throw new UnsupportedOperationException("Call to '" + method.getName() + "'");
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public T clone() throws CloneNotSupportedException
    {
        return (T) descriptor.getFactory().cloneDynaBean(this);
    }

    /**
     * Do the getter call.
     * 
     * @param propDesc The {@link DynaBeanPropertyDescriptor} descriptor
     * @return the property value
     */
    protected Object doGetter(DynaBeanPropertyDescriptor propDesc)
    {
        Object v = values.get(propDesc.propertyName);
        if(visitor != null)
        {
            v = visitor.eventGet(propDesc.propertyName, v, propDesc.getPropertyType());
        }
        return v;
    }

    /**
     * Do the setter call.
     * 
     * @param propDesc The property descriptor
     * @param arguments The arguments
     */
    protected void doSetter(DynaBeanPropertyDescriptor propDesc, Object... arguments)
    {
        Object v;

        v = arguments[0];
        if(visitor != null)
        {
            v = visitor.eventSet(propDesc.propertyName, v, propDesc.getPropertyType());
        }
        values.put(propDesc.propertyName, nullSafeValue(v, propDesc));
    }

    /**
     * Dynamically implemented {@link Object#toString()} method. Returns a String with the pattern:
     * 
     * <pre>
     * SimpleNameOfImplementedType [ propertyName=value, ...]
     * </pre>
     * 
     * For the {@link #getImplementedType() implemented type}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return String.format(descriptor.getPatternForToString(), values.values().toArray());
    }

    /**
     * Dynamically implemented {@link Object#equals(Object)} method.
     * 
     * @param o The 'other' object
     * @return as equals specification
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    @SuppressWarnings(
    {
            "rawtypes", "unchecked"
    })
    public boolean equals(Object o)
    {
        T theOther;
        DynaBeanImpl theOtherDynaBean = null;

        // Check if 'other' is a null
        if(o == null)
        {
            return false;
        }
        // Check for self-equals...
        if(o == this)
        {
            return true;
        }
        // Check if 'the other' is a 'implementedType' type...
        if(descriptor.getImplementedType().isAssignableFrom(o.getClass()) == false)
        {
            // ...if not, can be a dynaBean?...
            if(DynaBeanImpl.class.isAssignableFrom(o.getClass()))
            {
                theOtherDynaBean = (DynaBeanImpl) o;

                // Yes, check if the implementedType is the same or derived
                if(getImplementedType().isAssignableFrom(theOtherDynaBean.getImplementedType()) == false)
                {
                    return false;
                }
            }
            else
            {
                // Is not implemented type nor ProxyBeanImpl...
                return false;
            }
        }
        theOther = (T) o;
        for(DynaBeanPropertyDescriptor property : descriptor.getProperties())
        {
            try
            {
                Object value;
                if(theOtherDynaBean != null)
                {
                    value = theOtherDynaBean.values.get(property.propertyName);
                }
                else
                {
                    value = property.getterMethod.invoke(theOther);
                }

                if(ObjectUtils.nullSafeEquals(values.get(property.propertyName), value) == false)
                {
                    return false;
                }
            }
            catch(Exception e)
            {
                /*
                 * Includes: SecurityException, IllegalArgumentException, IllegalAccessException,
                 * InvocationTargetException
                 */
                throw new RuntimeException("On equals call!", e);
            }
        }
        return true;
    }

    /**
     * Clone a property value.
     * 
     * @param propDesc The property descriptor
     * @param originalValue The value to clone
     * @return The cloned value
     */
    private Object cloneValue(DynaBeanPropertyDescriptor propDesc, Object originalValue)
    {
        PropertyEditor pEditor;
        Class<?> originalType;
        Object valueArray;
        Method m;
        int n, l;

        if(originalValue == null)
        {
            return null;
        }
        
        if(originalValue.getClass().isArray())
        {
            originalType = originalValue.getClass().getComponentType();
        }
        else
        {
            originalType = originalValue.getClass();
        }

        if(Cloneable.class.isAssignableFrom(originalType))
        {

            try
            {
                m = originalType.getMethod("clone", (Class<?>[]) null);
                if(originalValue.getClass().isArray() && propDesc.getPropertyType().isArray())
                {
                    // Clone an array
                    // Assign with conversion, one to one
                    valueArray = Array.newInstance(propDesc.getPropertyType().getComponentType(), ((Object[])originalValue).length);
                    n = 0;
                    for(Object val : (Object[])originalValue)
                    {
                        Array.set(valueArray, n++, m.invoke(val, (Object[]) null));
                    }
                    return valueArray;
                }
                else
                {
                    if(originalValue.getClass().isArray())
                    {
                        return m.invoke(Array.get(originalValue, 0), (Object[]) null);
                    }
                    else
                    {
                        return m.invoke(originalValue, (Object[]) null);
                    }
                }
            }
            catch(NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
            {
                logger.error("On cloning value for property '" + propDesc.getPropertyName() + "' (" + e.getMessage() + ")", e);
            }
        }

        // Try with editors
        if((pEditor = findPropertyEditorForProperty(propDesc, originalValue.getClass())) != null)
        {
            if(originalValue.getClass().isArray()
                    && propDesc.getPropertyType().isArray())
            {
                // Assign with conversion, one to one
                l = Array.getLength(originalValue);
                valueArray = Array.newInstance(propDesc.getPropertyType().getComponentType(), l);
                n = 0;
                for(n = 0; n < l; n++)
                {
                    Array.set(valueArray, n, resolveValue(pEditor, Array.get(originalValue, n)));
                }
                return valueArray;
            }
            else
            {
                if(originalValue.getClass().isArray())
                {
                    return resolveValue(pEditor, ((Object[])originalValue)[0]);
                }
                else
                {
                    return resolveValue(pEditor, originalValue);
                }
            }
        }
        // Nothing more we can do, return the same object
        return originalValue;
    }
    /**
     * Resolve a value with a corresponent {@link PropertyEditor}.
     * @param pEditor The editor, cannot be null
     * @param originalValue The original value
     * @return The resolved value
     */
    private Object resolveValue(PropertyEditor pEditor, Object originalValue)
    {
        if(String.class.isAssignableFrom(originalValue.getClass()))
        {
            pEditor.setAsText((String) originalValue);
        }
        else
        {
            pEditor.setValue(originalValue);
            pEditor.setAsText(pEditor.getAsText());
        }
        return pEditor.getValue();
    }
    /**
     * Search for a {@link PropertyEditor} for the given property.
     * 
     * @param propDesc The descriptor, required
     * @param originalType The original type (in case of clonning), can be null
     * @return The {@link PropertyEditor} or null if none was found
     */
    private PropertyEditor findPropertyEditorForProperty(DynaBeanPropertyDescriptor propDesc, Class<?> originalType)
    {
        PropertyEditor pEditor;
        Class<?> propType;

        pEditor = null;

        if(propDesc.getPropertyType().isArray())
        {
            propType = propDesc.getPropertyType().getComponentType();
        }
        else
        {
            propType = propDesc.getPropertyType();
        }
        if((pEditor = descriptor.getFactory().getPropertyEditorRegistry().findCustomEditor(propType, propDesc.getPropertyPath())) != null)
        {
            return pEditor;
        }
        if((pEditor = descriptor.getFactory().getPropertyEditorRegistry().findCustomEditor(propType, null)) != null)
        {
            return pEditor;
        }
        // Last, find in
        if((pEditor = PropertyEditorManager.findEditor(propType)) != null)
        {
            descriptor.getFactory().getPropertyEditorRegistry().registerCustomEditor(propType, pEditor);
            return pEditor;
        }
        if(originalType != null)
        {
            return descriptor.getFactory().getPropertyEditorRegistry().findCustomEditor(originalType, propDesc.getPropertyPath());
        }
        return null;
    }

    /**
     * Test if the value is for a primitive type and return an object representation with default (0) value. If value is
     * null and the type is primitive, return a representation of default value for the primitive corresponding type.
     * 
     * @param value the value, can be null
     * @param pb The property bean descriptor
     * @return The value or the default value representation for the primitive type (0)
     */
    private Object nullSafeValue(Object value, DynaBeanPropertyDescriptor pb)
    {
        if(!pb.getPropertyType().isPrimitive())
        {
            return value;
        }
        if(pb.getPropertyType().getName().equals("byte"))
        {
            return (value == null ? Byte.valueOf((byte) 0) : (Byte) value);
        }
        if(pb.getPropertyType().getName().equals("short"))
        {
            return (value == null ? Short.valueOf((short) 0) : (Short) value);
        }
        if(pb.getPropertyType().getName().equals("int"))
        {
            return (value == null ? Integer.valueOf(0) : (Integer) value);
        }
        if(pb.getPropertyType().getName().equals("long"))
        {
            return (value == null ? Long.valueOf(0L) : (Long) value);
        }
        if(pb.getPropertyType().getName().equals("float"))
        {
            return (value == null ? Float.valueOf(0F) : (Float) value);
        }
        if(pb.getPropertyType().getName().equals("double"))
        {
            return (value == null ? Double.valueOf(0D) : (Double) value);
        }
        if(pb.getPropertyType().getName().equals("char"))
        {
            return (value == null ? Character.valueOf('\u0000') : (Character) value);
        }
        if(pb.getPropertyType().getName().equals("boolean"))
        {
            return (value == null ? Boolean.FALSE : (Boolean) value);
        }
        if(pb.getPropertyType().getSimpleName().equals("String"))
        {
            return value;
        }
        // Unknown??
        throw new IllegalArgumentException("No handler found for type '" + pb.getPropertyType().getSimpleName() + "'.");
    }

    /**
     * Dynamically implemented {@link Object#hashCode()} method.
     * 
     * @return The calculated hashCode
     * @see ObjectUtils#nullSafeHashCode(Object[])
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        List<Object> vals;
        
        vals = new Vector<Object>();
        for(Entry<String,Object> e : values.entrySet())
        {
            vals.add(e.getValue());
        }
        return ObjectUtils.nullSafeHashCode(vals.toArray());
    }
}
