/*
 * This file is part of "imodel" project.
 * 
 * "imodel" is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * "imodel" is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with calendar.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright (C) 2013 Octavi Fornés octavi@fornes.cat
 */
package cat.albirar.framework.imodel;

import static cat.albirar.framework.imodel.ProxyBeanUtils.isCorrectProperty;
import static cat.albirar.framework.imodel.ProxyBeanUtils.isGetter;
import static cat.albirar.framework.imodel.ProxyBeanUtils.isProperty;
import static cat.albirar.framework.imodel.ProxyBeanUtils.propertyName;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import cat.albirar.framework.imodel.utils.ObjectUtils;

/**
 * A proxy for create dynamic beans from interfaces.
 * 
 * For use with interfaces that represents a Java Bean. <br/>
 * <b>Use</b>
 * <pre>
 * InterfaceJavaBean a;
 * 
 * a = {@link ProxyBeanFactory}.newProxy(InterfaceJavaBean.class);
 * ...
 * a.setXXX("xxx");
 * </pre>
 * 
 * The proxy is a JDK Proxy framework instance.
 * 
 * @author octavi@fornes.cat
 * @since 1.0.0
 */
public class ProxyBeanImpl<T> implements InvocationHandler, Serializable
{
    private static final long serialVersionUID = 0L;

    /** Implemented type */
    private Class<T> implementedType;

    /** Property list */
    private Map<String, PropertyBean> properties;

    /** Property names in appearance order */
    private List<String> names;

    /** Values for calculate hash code */
    private Object[] hashCodeValues;

    /**
     * Creates a proxy for the type.
     * @param typeToImplement The interface type to implement
     * @return The proxy, as typeToImplement type.
     * @throws IllegalArgumentException If the type is not an interface
     */
    @SuppressWarnings("unchecked")
    public static final <T> T newProxy(Class<T> typeToImplement)
    {
        return (T) Proxy.newProxyInstance(typeToImplement.getClassLoader(), new Class[]
        {
            typeToImplement
        }, new ProxyBeanImpl<T>(typeToImplement));
    }

    /**
     * Constructor with type to implement.
     * @param typeToImplement The interface type to implement
     * @throws IllegalArgumentException If the type is not an interface
     */
    protected ProxyBeanImpl(Class<T> typeToImplement)
    {
        PropertyBean pb;

        if(typeToImplement.isInterface() == false)
        {
            // Only interfaces
            throw new IllegalArgumentException("ProxyBeanImpl can only implement interfaces. '" + typeToImplement.getName() + "' is not an interface");
        }

        this.implementedType = typeToImplement;
        properties = Collections.synchronizedMap(new TreeMap<String, PropertyBean>());
        names = Collections.synchronizedList(new ArrayList<String>());
        // Prepare the properties list
        for(Method method : typeToImplement.getMethods())
        {
            if(Modifier.isPublic(method.getModifiers()) && isProperty(method.getName())
            		&& isCorrectProperty(method))
            {
                if((pb = properties.get(propertyName(method.getName()))) == null)
                {
                    // Put them!
                    pb = new PropertyBean();
                    pb.name = propertyName(method.getName());
                    if(isGetter(method.getName())) {
                    	pb.primitive = method.getReturnType().isPrimitive();
                    } else {
                    	pb.primitive = method.getParameterTypes()[0].isPrimitive();
                    }
                    properties.put(pb.name, pb);
                    names.add(pb.name);
                }
            }
        }
        resolveGettersSettersMethods();
        // Assign default values
        for(PropertyBean pbv : properties.values())
        {
            pbv.value = nullSafeValue(null, pbv);
        }
    }

    /**
     * On deserialize, reconstruct the implemented type information.
     */
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
    {
        in.defaultReadObject();
        resolveGettersSettersMethods();
    }

    /**
     * Resolve the 'getter method' for all properties.
     */
    private void resolveGettersSettersMethods()
    {
        PropertyBean pb;
        // Prepare the properties list
        for(Method method : implementedType.getMethods())
        {
            if(Modifier.isPublic(method.getModifiers()) && isProperty(method.getName()))
            {
                pb = properties.get(propertyName(method.getName()));
                if(isGetter(method.getName()))
                {
                    pb.getterMethod = method;
                }
                else
                {
                    pb.setterMethod = method;
                }
            }
        }
    }

    /**
     * The implemented interface for this proxy.
     * @return implementedType The implemented type
     */
    public Class<T> getImplementedType()
    {
        return implementedType;
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
    protected Object doInvoke(Method method, Object... args)
    {
        PropertyBean pb;
        String name;

        name = method.getName();
        // Check if is a get/set method
        if(isProperty(name))
        {
            if((pb = properties.get(propertyName(name))) != null)
            {
                if(isGetter(name))
                {
                    return doGetter(pb);
                }
                // is a setter
                doSetter(pb, args);
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
     * Do the getter call.
     * @param pb The propertybean descriptor
     * @return the property value
     */
    protected Object doGetter(PropertyBean pb)
    {
        return pb.value;
    }

    /**
     * Do the setter call.
     * @param pb The propertybean descriptor
     * @param arguments The arguments
     */
    protected void doSetter(PropertyBean pb, Object... arguments)
    {
        pb.value = nullSafeValue(arguments[0], pb);
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
        StringBuilder stb;

        stb = new StringBuilder(implementedType.getSimpleName());
        stb.append(" [");
        for(PropertyBean pb : properties.values())
        {
            stb.append(pb.name).append("=").append("" + pb.value).append(", ");
        }
        // Change the last element (",") by "]"
        stb.replace(stb.length() - 2, stb.length(), "]");
        return stb.toString();
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
        ProxyBeanImpl pbo = null;

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
        if(implementedType.isAssignableFrom(o.getClass()) == false)
        {
            // ...if not, can be a ProxyBeanImpl?...
            if(ProxyBeanImpl.class.isAssignableFrom(o.getClass()))
            {
                pbo = (ProxyBeanImpl) o;

                // Yes, check if the implementedType is the same or derived
                if(implementedType.isAssignableFrom(pbo.implementedType) == false)
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
        for(PropertyBean pb : properties.values())
        {
            try
            {
                Object value;
                if(pbo != null)
                {
                    PropertyBean pbv;

                    pbv = (PropertyBean) pbo.properties.get(pb.name);
                    value = pbv.value;
                }
                else
                {
                    value = pb.getterMethod.invoke(theOther);
                }
                if(ObjectUtils.nullSafeEquals(pb.value, value) == false)
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
    private Object nullSafeValue(Object value, PropertyBean pb)
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
        int n;
        PropertyBean pb;

        if(hashCodeValues == null)
        {
            hashCodeValues = new Object[properties.size()];
        }
        n = 0;
        for(String name : names)
        {
            pb = properties.get(name);
            hashCodeValues[n] = pb.value;
            n++;
        }
        return ObjectUtils.nullSafeHashCode(hashCodeValues);
    }

    /**
     * Clone the implemented bean with the same values
     */
    public T clone()
    {
        T other;

        other = newProxy(implementedType);

        for(PropertyBean pb : properties.values())
        {
            try
            {
                pb.setterMethod.invoke(other, pb.value);
            }
            catch(Exception e)
            {
                throw new RuntimeException("On clone (" + pb.setterMethod.getName() + "!", e);
            }
        }
        return other;
    }

    /**
     * A structure for hold the properties information of bean.
     */
    protected class PropertyBean implements Serializable
    {
        private static final long serialVersionUID = 0L;

        public String name;

        public transient Method getterMethod;

        public transient Method setterMethod;

        public Object value;

        public boolean primitive;
    }

}
