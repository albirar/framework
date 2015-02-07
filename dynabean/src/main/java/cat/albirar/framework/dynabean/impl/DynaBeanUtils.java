/*
 * This file is part of "albirar framework".
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
 * Copyright (C) 2013 Octavi Fornés
 */
package cat.albirar.framework.dynabean.impl;

import java.lang.reflect.Method;

import org.springframework.util.Assert;

import cat.albirar.framework.dynabean.DynaBean;


/**
 * Some utilities for {@link DynaBean} and {@link DynaBeanImpl} operations.
 * 
 * @author octavi@fornes.cat
 * @since 1.0.0
 */
public abstract class DynaBeanUtils
{
	/**
	 * Check if the method name is a get/set/is property bean access method.
	 * @param methodName The method name
	 * @return <b>true</b> if is a property bean access method, <b>false</b> otherwise
	 */
	public static final boolean isPropertyMethod(String methodName)
	{
	    return ((methodName.startsWith("get") && methodName.length() > "get".length()) 
	            || (methodName.startsWith("set") && methodName.length() > "set".length())
	            || (methodName.startsWith("is") && methodName.length() > "is".length()));
	}
	/**
	 * Comprova que el mètode representa una propietat correcta.
	 * @param method El mètode
	 * @return true si és correcta i false en cas contrari
	 */
	public static final boolean isCorrectProperty(Method method) {
		if(isPropertyMethod(method.getName())) {
			if(isGetter(method.getName())) {
				return (method.getReturnType() != null);
			} else {
				return (method.getParameterTypes().length == 1);
			}
		}
		return false;
	}
	/**
	 * Check if the method name is a 'getter'
	 * @param name The method name
	 * @return <b>true</b> if is a 'getter' method, <b>false</b> otherwise
	 */
	public static final boolean isGetter(String name)
	{
	    return (name.startsWith("get") || name.startsWith("is"));
	}

	/**
	 * Gets the "property name" from a getter/setter method name.
	 * 
	 * @param methodName The method name
	 * @return The property name
	 * @throws IllegalArgumentException if method name is not a getter/setter method
	 */
	public static final String fromMethodToPropertyName(String methodName)
	{
	    String propName;
	
	    if(isPropertyMethod(methodName) == false)
	    {
	        throw new IllegalArgumentException("The method name (" + methodName + ") is not a getter/setter method");
	    }
	    if(isGetter(methodName) == false)
	    {
	        propName = methodName.substring("set".length());
	    }
	    else
	    {
	        if(methodName.startsWith("get"))
	        {
	            propName = methodName.substring("get".length());
	        }
	        else
	        {
	            propName = methodName.substring("is".length());
	        }
	    }
	    return changeFirstCharToLower(propName);
	}
	
	/**
	 * Create the method name for 'get' (or 'is') the indicated property.
	 * @param propertyName The property name, cannot be null and should to be at least 1 non-whitespace character.
	 * @return the 'get' method name for the indicated property
	 * @throws IllegalArgumentException If propertyName is null or have not enough chars (at least 1) 
	 */
	public static final String fromPropertyToGetMethodName(String propertyName, Class<?> type)
	{
		String prefix;
		
		Assert.hasText(propertyName, "propertyName argument is required and should to have at least 1 character");
		Assert.notNull(type, "type argument is required");
		
		if("boolean".equalsIgnoreCase(type.getSimpleName()) ||
				Boolean.class.getName().equals(type.getName()) )
		{
			prefix = "is";
		} else {
			prefix = "get";
		}
		if(propertyName.length() == 1) {
			return prefix + propertyName.toUpperCase();
		} else {
			return prefix + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
		}
	}
	/**
	 * Create the method name for 'set' the indicated property.
	 * @param propertyName The property name, cannot be null and should to be at least 1 non-whitespace character.
	 * @return the 'set' method name for the indicated property
	 * @throws IllegalArgumentException If propertyName is null or have not enough chars (at least 1) 
	 */
	public static final String fromPropertyToSetMethodName(String propertyName)
	{
		Assert.hasText(propertyName, "propertyName argument is required");
		
		if(propertyName.length() == 1) {
			return "set" + propertyName.toUpperCase();
		} else {
			return "set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
		}
	}

	/**
	 * Changes the first char to lower case.
	 * @param name The name
	 * @return The name with the first char changed to lower case
	 */
	public static final String changeFirstCharToLower(String name)
	{
	    StringBuilder stb;

	    stb = new StringBuilder(name.substring(0, 1).toLowerCase());
	    stb.append(name.substring(1));
	    return stb.toString();
	}
	
//	public static final String fromMethodToProperty(String methodName)
//	{
//		StringBuilder stb;
//		
//		if(isPropertyMethod(methodName)) {
//			
//		} else {
//			return methodName;
//		}
//	}
}
