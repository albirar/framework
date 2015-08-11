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

import java.beans.PropertyEditor;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import cat.albirar.framework.dynabean.DynaBeanUtils;
import cat.albirar.framework.dynabean.IDynaBeanFactory;
import cat.albirar.framework.dynabean.visitor.IDynaBeanVisitor;
import cat.albirar.framework.patterns.ITransformerVisitor;

/**
 * A proxy for create dynamic beans from interfaces.
 * 
 * For use with interfaces that represents a Java Bean. <br>
 * <b>Use</b>
 * 
 * <pre>
 * InterfaceJavaBean a;
 * 
 * a = {@link DynaBeanUtils#instanceDefaultFactory()}.{@link IDynaBeanFactory#newDynaBean(Class) newDynaBean(InterfaceJavaBean.class)};
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
        doInstantiate();
    }

    /**
     * Clone constructor.
     * 
     * @param origin The origin for data
     */
    DynaBeanImpl(DynaBeanImpl<T> origin)
    {
        this();
        Assert.notNull(origin, "The 'origin' object is required!");
        this.descriptor = origin.descriptor;
        doClone(origin);
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
        doInstantiate();
    }

    /**
     * Check and assign the values for the properties.
     */
    private void doInstantiate()
    {
        // Default instantiation
        for(DynaBeanPropertyDescriptor prop : descriptor.getProperties())
        {
            // Test: dynaBean; implementation; defaultValue; null (or 0 or false)
            if(prop.isDynaBean())
            {
                // Instantiate by factory
                values.put(prop.propertyName, descriptor.getFactory().newDynaBean(prop.getPropertyType()));
            }
            else
            {
                values.put(prop.propertyName, nullSafeValue(generateDefaultValue(prop), prop));
            }
        }
    }
    /**
     * Do a clone instantiation.
     * @param origin The origin to get values from. If null, a call to {@link #doInstantiate()} is made.
     */
    private void doClone(DynaBeanImpl<T> origin)
    {
        for(DynaBeanPropertyDescriptor propDesc : descriptor.getProperties())
        {
            values.put(propDesc.getPropertyName(), nullSafeValue(cloneValue(propDesc, origin.values.get(propDesc.getPropertyName())), propDesc));
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
     * @param oOrigin The 'other' object
     * @return as equals specification
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    @SuppressWarnings(
    {
            "rawtypes", "unchecked"
    })
    public boolean equals(Object oOrigin)
    {
        T theOther;
        DynaBeanImpl theOtherDynaBean = null;
        Object o;

        // Check if 'other' is a null
        if(oOrigin == null)
        {
            return false;
        }
        if(Proxy.isProxyClass(oOrigin.getClass()))
        {
            o = Proxy.getInvocationHandler(oOrigin);
        }
        else
        {
            o = oOrigin;
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
            catch(SecurityException | IllegalArgumentException | IllegalAccessException | InvocationTargetException e)
            {
                throw new RuntimeException("On equals call!", e);
            }
        }
        return true;
    }
    /**
     * Generate the value for the property taking care of {@link DynaBeanPropertyDescriptor#defaultImplementation} and {@link DynaBeanPropertyDescriptor#defaultValue}.
     * @param propDesc The property descriptor
     * @return The value for that property
     */
    private Object generateDefaultValue(DynaBeanPropertyDescriptor propDesc)
    {
        PropertyEditor pEditor;
        IPropertyWriter writer;
        ITransformerVisitor<Object> reader;
        
        if(propDesc.getDefaultValue() != null)
        {
            if(propDesc.getPropertyItemEditor() != null)
            {
                pEditor = propDesc.getPropertyItemEditor();
                reader = new ObjectCopyReaderVisitor(pEditor);
            }
            else
            {
                reader = new ObjectCopyReaderVisitor();
            }
            writer = prepareWriter(propDesc, reader);
            for(String iv : propDesc.getDefaultValue())
            {
                writer.visit(iv);
            }
            return writer.getReturnValue();
        }
        // No default value, return default implementation (or null if none are defined)
        return instantiateDefaultImplementation(propDesc);
    }

    /**
     * Clone or copy a property value.
     * Only applicable on clone call.
     * <ul>
     * <li>If {@code originalValue} is {@link Cloneable}, makes a clone.</li>
     * <li>If {@code originalValue} IS NOT {@link Cloneable}, search for an {@link PropertyEditor editor} and copy.</li>
     * <li>If cannot found a properly {@link PropertyEditor editor}, simply return the {@code originalValue}</li>
     * </ul>
     * @param propDesc The property descriptor
     * @param originalValue The value to clone
     * @return The cloned value
     */
    private Object cloneValue(DynaBeanPropertyDescriptor propDesc, Object originalValue)
    {
        Iterator<?> iterator;
        int n;
        IPropertyWriter writer;
        ITransformerVisitor<Object> reader;

        // only null?
        if(originalValue == null)
        {
            return null;
        }
        
        if(propDesc.isItemDynaBean())
        {
            reader = new ObjectCopyReaderVisitor(descriptor.getFactory());
        }
        else
        {
            if(propDesc.getPropertyItemCloneMethod() != null)
            {
                reader = new ObjectCopyReaderVisitor(propDesc.getPropertyItemCloneMethod());
            }
            else
            {
                if(propDesc.getPropertyItemEditor() != null)
                {
                    reader = new ObjectCopyReaderVisitor(propDesc.getPropertyItemEditor());
                }
                else
                {
                    reader = new ObjectCopyReaderVisitor();
                }
            }
        }
        writer = prepareWriter(propDesc, reader);
        if(propDesc.isArray())
        {
            for(n = 0; n < Array.getLength(originalValue); n++)
            {
                writer.visit(Array.get(originalValue, n));
            }
        }
        else
        {
            if(propDesc.isCollection())
            {
                iterator = ((Collection<?>)originalValue).iterator();
                while(iterator.hasNext())
                {
                    writer.visit(iterator.next());
                }
            }
            else
            {
                writer.visit(originalValue);
            }
        }
        return writer.getReturnValue();
    }
    /**
     * Prepare a writer for the indicated property.
     * @param propDesc The property descriptor
     * @param reader The reader
     * @return The writer
     */
    @SuppressWarnings("unchecked")
    private IPropertyWriter prepareWriter(DynaBeanPropertyDescriptor propDesc, ITransformerVisitor<Object> reader)
    {
        IPropertyWriter writer;
        Object di;
        
        // Specific writer
        if(propDesc.isArray() || propDesc.isCollection())
        {
            // 1t and 2d cases
            // We use a visitor pattern to assign values
            if(propDesc.isArray())
            {
                writer = new ArrayWriterVisitor(reader, propDesc.getPropertyType().getComponentType());
            }
            else
            {
                // Check if default collection is assigned
                if( (di = instantiateDefaultImplementation(propDesc)) != null)
                {
                    writer = new CollectionWriterVisitor(reader, (List<Object>)di);
                }
                else
                {
                    writer = new CollectionWriterVisitor(reader, new Vector<Object>());
                }
            }
        }
        else
        {
            writer = new IndividualWriterVisitor(reader);
        }
        return writer;
    }
    /**
     * Instantiate the default implementation for the property.
     * @param propDesc The property descriptor
     * @return The default instance or null if no default implementation are indicated
     * @throws IllegalArgumentException If the implementation class is cannot be instantiated
     */
    private Object instantiateDefaultImplementation(DynaBeanPropertyDescriptor propDesc)
    {
        String s;
        
        if(propDesc.getDefaultImplementation() != null)
        {
            try
            {
                return propDesc.getDefaultImplementation().newInstance();
            }
            catch(InstantiationException | IllegalAccessException e)
            {
                s = "On instantiating default implementation of type '"
                        .concat(propDesc.getDefaultImplementation().getName())
                        .concat("' for property '")
                        .concat(propDesc.getPropertyName())
                        .concat("' of type '")
                        .concat(propDesc.getPropertyType().getName())
                        .concat("' at dynaBean '")
                        .concat(getImplementedType().getName())
                        .concat("'");
                logger.error(s, e);
                throw new IllegalArgumentException(s, e);
            }
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
        if(!pb.isPrimitive())
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
        return (value == null ? Boolean.FALSE : (Boolean) value);
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
        for(Entry<String, Object> e : values.entrySet())
        {
            vals.add(e.getValue());
        }
        return ObjectUtils.nullSafeHashCode(vals.toArray());
    }
}
