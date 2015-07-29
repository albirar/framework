/*
 * This file is part of "albirar framework".
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
 * Copyright (C) 2013 Octavi Fornés
 */
package cat.albirar.framework.dynabean.impl;

import java.lang.reflect.Method;

import org.springframework.util.Assert;

import cat.albirar.framework.dynabean.DynaBeanUtils;

/**
 * Some utilities for {@link DynaBeanUtils} and {@link DynaBeanImpl} operations. Also get a static (thread safe)
 * {@link IDynaBeanImplementationFactory dynabean implementation factory}.
 * 
 * @author <a href="mailto:ofornes@albirar.cat">Octavi Fornés ofornes@albirar.cat</a>
 * @since 1.0.0
 */
public abstract class DynaBeanImplementationUtils
{
    /** The prefix for getter method name (not boolean property). */
    private static final String GET_PREFIX_CONSTANT = "get";

    /** The prefix for boolean getter method name. */
    private static final String IS_PREFIX_CONSTANT = "is";

    /** The prefix for setter method name. */
    private static final String SET_PREFIX_CONSTANT = "set";

    /** The size for getter method name prefix . */
    private static final int GET_PREFIX_SIZE = GET_PREFIX_CONSTANT.length();

    /** The size for boolean setter method name prefix . */
    private static final int IS_PREFIX_SIZE = IS_PREFIX_CONSTANT.length();

    /** The size for setter method name prefix . */
    private static final int SET_PREFIX_SIZE = SET_PREFIX_CONSTANT.length();

    /**
     * Thread safe singleton.
     */
    private static final ThreadLocal<IDynaBeanImplementationFactory> singleton = new ThreadLocal<IDynaBeanImplementationFactory>()
    {
        /**
         * {@inheritDoc}
         */
        @Override
        protected IDynaBeanImplementationFactory initialValue()
        {
            return new DefaultDynaBeanFactory();
        }

    };

    /**
     * Gets a factory instance.
     * 
     * @return The factory
     */
    public static final IDynaBeanImplementationFactory instanceFactory()
    {
        return singleton.get();
    }

    /**
     * Check if the method name is a get/set/is property bean access method.
     * 
     * @param methodName The method name
     * @return <b>true</b> if is a property bean access method, <b>false</b> otherwise
     */
    public static final boolean isPropertyMethod(String methodName)
    {
        return (isGetter(methodName) || isSetter(methodName));
    }

    /**
     * Comprova que el mètode representa una propietat correcta. Un propietat correcta és:
     * <ul>
     * <li>Un {@link #isSetter(String) mètode set} amb arguments i sense retorn</li>
     * <li>Un {@link #isGetter(String) mètode get} sense arguments i amb retorn diferent de 'void'.</li>
     * <li>Un {@link #isGetterBoolean(String) mètode 'is'} sense arguments i amb retorn 'boolean'.</li>
     * </ul>
     * 
     * @param method El mètode
     * @return true si és correcta i false en cas contrari
     */
    public static final boolean isCorrectProperty(Method method)
    {
        if(isGetter(method.getName()))
        {
            if(isGetterBoolean(method.getName()))
            {
                return (method.getParameterTypes().length == 0 && method.getReturnType().equals(boolean.class));
            }
            return (!method.getReturnType().equals(void.class) 
                    && method.getParameterTypes().length == 0);
        }
        else
        {
            return (method.getParameterTypes().length == 1 && method.getReturnType().equals(void.class));
        }
    }

    /**
     * Check if the method name is a 'getter'
     * 
     * @param name The method name
     * @return <b>true</b> if is a 'getter' method, <b>false</b> otherwise
     * @see #isGetterBoolean(String)
     */
    public static final boolean isGetter(String name)
    {
        return ((name.startsWith(GET_PREFIX_CONSTANT) && name.length() > GET_PREFIX_SIZE) || isGetterBoolean(name));
    }

    /**
     * Check if the method name is a 'getter' for boolean property (is).
     * 
     * @param name The method name
     * @return <b>true</b> if is a 'getter' method name for boolean property, <b>false</b> otherwise.
     */
    public static final boolean isGetterBoolean(String name)
    {
        return (name.startsWith(IS_PREFIX_CONSTANT) && name.length() > IS_PREFIX_SIZE);
    }

    /**
     * Check if the method name is a 'setter'
     * 
     * @param name The method name
     * @return <b>true</b> if is a 'setter' method, <b>false</b> otherwise
     */
    public static final boolean isSetter(String name)
    {
        return (name.startsWith(SET_PREFIX_CONSTANT) && name.length() > SET_PREFIX_SIZE);
    }

    /**
     * Gets the "property name" from a getter/setter method name.
     * 
     * @param methodName The method name
     * @return The property name
     * @throws IllegalArgumentException if method name is null or is not a getter/setter method
     */
    public static final String fromMethodToPropertyName(String methodName)
    {
        String propName;

        Assert.notNull(methodName, "The method name argument is required!");

        if(isSetter(methodName))
        {
            propName = methodName.substring(SET_PREFIX_SIZE);
        }
        else
        {
            if(isGetterBoolean(methodName))
            {
                propName = methodName.substring(IS_PREFIX_SIZE);
            }
            else
            {
                if(isGetter(methodName))
                {
                    propName = methodName.substring(GET_PREFIX_SIZE);
                }
                else
                {
                    throw new IllegalArgumentException("The method name (" + methodName + ") is not a getter/setter method");
                }
            }
        }
        return changeFirstCharToLower(propName);
    }

    /**
     * Create the method name for 'get' (or 'is') the indicated property.
     * 
     * @param propertyName The property name, cannot be null and should to be at least 1 non-whitespace character.
     * @param type The property type to infiere if method is for boolean property ('is') or not ('get'). If null no boolean property are assumed
     * @return the 'get' method name for the indicated property
     * @throws IllegalArgumentException If propertyName is null or have not enough chars (at least 1)
     * @see #fromPropertyToGetMethodName(String, boolean)
     */
    public static final String fromPropertyToGetMethodName(String propertyName, Class<?> type)
    {
        boolean isBoolean;
        
        Assert.hasText(propertyName, "propertyName argument is required and should to have at least 1 non-whitespace character");

        if(type == null)
        {
            isBoolean = false;
        }
        else
        {
            isBoolean = (boolean.class.equals(type) || Boolean.class.equals(type));
        }
        return fromPropertyToGetMethodName(propertyName, isBoolean);
    }

    /**
     * Create the method name for 'get' (or 'is') the indicated property.
     * 
     * @param propertyName The property name, cannot be null and should to be at least 1 non-whitespace character.
     * @param isBoolean Indicates if the get method is for boolean property ('is') or not ('get')
     * @return the 'get' method name for the indicated property
     * @throws IllegalArgumentException If propertyName is null or have not enough chars (at least 1)
     */
    public static final String fromPropertyToGetMethodName(String propertyName, boolean isBoolean)
    {
        String prefix;

        Assert.hasText(propertyName, "propertyName argument is required and should to have at least 1 non-whitespace character");

        if(isBoolean)
        {
            prefix = IS_PREFIX_CONSTANT;
        }
        else
        {
            prefix = GET_PREFIX_CONSTANT;
        }
        return prefix + changeFirstCharToUpper(propertyName);
    }

    /**
     * Create the method name for 'set' the indicated property.
     * 
     * @param propertyName The property name, cannot be null and should to be at least 1 non-whitespace character.
     * @return the 'set' method name for the indicated property
     * @throws IllegalArgumentException If propertyName is null or have not enough chars (at least 1)
     */
    public static final String fromPropertyToSetMethodName(String propertyName)
    {
        Assert.hasText(propertyName, "propertyName argument is required and should to have at least 1 non-whitespace character");

        return SET_PREFIX_CONSTANT + changeFirstCharToUpper(propertyName);
    }

    /**
     * Changes the first char to lower case.
     * 
     * @param name The name, required and with at least 1 character
     * @return The name with the first char changed to lower case
     * @throws IllegalArgumentException If {@code name} is null or empty or only whitespace
     */
    public static final String changeFirstCharToLower(String name)
    {
        Assert.hasText(name, "name is required and should to have at least 1 non-whitespace character");
        return name.substring(0, 1).toLowerCase().concat(name.substring(1));
    }

    /**
     * Changes the first char to upper case.
     * 
     * @param name The name, required and with at least 1 character
     * @return The name with the first char changed to upper case
     * @throws IllegalArgumentException If {@code name} is null or empty or only whitespace
     */
    public static final String changeFirstCharToUpper(String name)
    {
        Assert.hasText(name, "name is required and should to have at least 1 non-whitespace character");
        return name.substring(0, 1).toUpperCase().concat(name.substring(1));
    }
}
