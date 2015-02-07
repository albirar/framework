/*
 * This file is part of "albirar framework" project.
 * 
 * "albirar framework" is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * "albirar framework" is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with calendar.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright (C) 2013 Octavi Fornés octavi@fornes.cat
 */
package cat.albirar.framework.dynabean.impl;

import static cat.albirar.framework.dynabean.impl.DynaBeanUtils.isGetter;
import static cat.albirar.framework.dynabean.impl.DynaBeanUtils.isPropertyMethod;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.util.ObjectUtils;

import cat.albirar.framework.dynabean.DynaBean;
import cat.albirar.framework.dynabean.IDynaBeanFactory;

/**
 * A proxy for create dynamic beans from interfaces.
 * 
 * For use with interfaces that represents a Java Bean. <br/>
 * <b>Use</b>
 * <pre>
 * InterfaceJavaBean a;
 * 
 * a = {@link DynaBean#instanceFactory()}.{@link IDynaBeanFactory#newDynaBean(Class) newDynaBean(InterfaceJavaBean.class)};
 * ...
 * a.setXXX("xxx");
 * </pre>
 * 
 * The proxy is a JDK Proxy framework instance.
 * 
 * @author octavi@fornes.cat
 * @since 1.0.0
 */
class DynaBeanImpl<T> implements InvocationHandler, Serializable
{
    private static final long serialVersionUID = 0L;
    
    DynaBeanDescriptor<T> descriptor;
    Map<String, Object> values;
    IDynaBeanFactory factory;

    private DynaBeanImpl(IDynaBeanFactory factory)
    {
    	values = Collections.synchronizedMap(new TreeMap<String, Object>());
    	this.factory = factory;
    }
    /**
     * Constructor with a prepared descriptor.
     * Used to reduce memory consumption and CPU cycles.
     * @param descriptor The descriptor
     */
    DynaBeanImpl(IDynaBeanFactory factory, DynaBeanDescriptor<T> descriptor) {
    	this(factory);
    	this.descriptor = descriptor;
    }
    /**
     * Clone constructor.
     * @param origin The origin for data
     */
    DynaBeanImpl(DynaBeanImpl<T> origin) {
    	this(origin.factory, origin.descriptor);
    }
    /**
     * Constructor with type to implement.
     * @param typeToImplement The interface type to implement
     * @throws IllegalArgumentException If the type is not an interface
     */
    DynaBeanImpl(IDynaBeanFactory factory, Class<T> typeToImplement)
    {
    	this(factory);
    	
    	descriptor = new DynaBeanDescriptor<T>(typeToImplement);
        // Assign default values
        for(PropertyDescriptor prop : descriptor.getProperties())
        {
            values.put(prop.propertyName,nullSafeValue(prop.defaultValue, prop));
        }
    }

    /**
     * The implemented interface for this dynaBean.
     * @return implementedType The implemented type
     */
    public Class<T> getImplementedType()
    {
        return descriptor.getImplementedType();
    }

    /* (non-Javadoc)
     * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
    {
        return doInvoke(method, args);
    }

    /**
     * Invocation operation.
     * @param method The invoked method 
     * @param args The arguments, if any
     * @return The result. Can be the value for a property or the result of {@link #equals(Object)}, {@link #hashCode()} or {@link #toString()}
     */
    protected Object doInvoke(Method method, Object... args) throws Throwable
    {
        PropertyDescriptor propDesc;
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
	public T clone() throws CloneNotSupportedException {
		return (T) this.factory.cloneDynaBean(this);
	}
	/**
     * Do the getter call.
     * @param propDesc The {@link PropertyDescriptor} descriptor
     * @return the property value
     */
    protected Object doGetter(PropertyDescriptor propDesc)
    {
        return values.get(propDesc.propertyName);
    }

    /**
     * Do the setter call.
     * @param propDesc The property descriptor
     * @param arguments The arguments
     */
    protected void doSetter(PropertyDescriptor propDesc, Object... arguments)
    {
    	values.put(propDesc.propertyName, nullSafeValue(arguments[0], propDesc));
    }

    /**
     * Dynamically implemented {@link Object#toString()} method.
     * Returns a String with the pattern:
     * <pre>
     * SimpleNameOfImplementedType [ propertyName=value, ...]
     * </pre>
     * For the {@link #getImplementedType() implemented type}
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
    	return String.format(descriptor.getPatternForToString(), values.entrySet().toArray());
    }

    /**
     * Dynamically implemented {@link Object#equals(Object)} method.
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
        for(PropertyDescriptor property : descriptor.getProperties())
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
                /* Includes: SecurityException, IllegalArgumentException, IllegalAccessException, InvocationTargetException */
                throw new RuntimeException("On equals call!", e);
            }
        }
        return true;
    }

    /**
     * Test if the value is for a primitive type and return an object representation with default (0) value.
     * If value is null and the type is primitive, return a representation of default value for the primitive corresponding type.
     * @param value the value, can be null
     * @param pb The property bean descriptor
     * @return The value or the default value representation for the primitive type (0)
     */
    private Object nullSafeValue(Object value, PropertyDescriptor pb)
    {
        if(!pb.primitive)
        {
            return value;
        }
        if(pb.getterMethod.getReturnType().getName().equals("byte"))
        {
            return (value == null ? Byte.valueOf((byte) 0) : (Byte) value);
        }
        if(pb.getterMethod.getReturnType().getName().equals("short"))
        {
            return (value == null ? Short.valueOf((short) 0) : (Short) value);
        }
        if(pb.getterMethod.getReturnType().getName().equals("int"))
        {
            return (value == null ? Integer.valueOf(0) : (Integer) value);
        }
        if(pb.getterMethod.getReturnType().getName().equals("long"))
        {
            return (value == null ? Long.valueOf(0L) : (Long) value);
        }
        if(pb.getterMethod.getReturnType().getName().equals("float"))
        {
            return (value == null ? Float.valueOf(0F) : (Float) value);
        }
        if(pb.getterMethod.getReturnType().getName().equals("double"))
        {
            return (value == null ? Double.valueOf(0D) : (Double) value);
        }
        if(pb.getterMethod.getReturnType().getName().equals("char"))
        {
            return (value == null ? Character.valueOf('\u0000') : (Character) value);
        }
        if(pb.getterMethod.getReturnType().getName().equals("boolean"))
        {
            return (value == null ? Boolean.FALSE : (Boolean) value);
        }
        if(pb.getterMethod.getReturnType().getSimpleName().equals("String"))
        {
            return value;
        }
        // Unknown??
        throw new IllegalArgumentException("No handler found for type '" + pb.getterMethod.getReturnType().getSimpleName() + "'.");
    }

    /**
     * Dynamically implemented {@link Object#hashCode()} method.
     * Includes all properties. The properties are processed in the same order that
     * they appear on interface (see {@link #names}).
     * 
     * @return The calculated hashCode
     * 
     * @see ObjectUtils#nullSafeHashCode(Object[])
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
    	return ObjectUtils.nullSafeHashCode(values.entrySet().toArray());
    }
}
