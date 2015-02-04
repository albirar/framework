/*
 * This file is part of "imodel".
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
 * Copyright (C) 2013 Octavi Fornés
 */
package cat.albirar.framework.imodel;

import java.lang.reflect.Method;


/**
 * Some utilities for {@link ProxyBeanFactory} and {@link ProxyBeanImpl} operations.
 * 
 * @author octavi@fornes.cat
 * @since 1.0.0
 */
public abstract class ProxyBeanUtils
{
	/**
	 * Check if the method name is a get/set/is property bean access method.
	 * @param name The method name
	 * @return <b>true</b> if is a property bean access method, <b>false</b> otherwise
	 */
	public static final boolean isProperty(String name)
	{
	    return ((name.startsWith("get") && name.length() > "get".length()) 
	            || (name.startsWith("set") && name.length() > "set".length())
	            || (name.startsWith("is") && name.length() > "is".length()));
	}
	/**
	 * Comprova que el mètode representa una propietat correcta.
	 * @param method El mètode
	 * @return true si és correcta i false en cas contrari
	 */
	public static final boolean isCorrectProperty(Method method) {
		if(isProperty(method.getName())) {
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
	public static final String propertyName(String methodName)
	{
	    String propName;
	
	    if(isProperty(methodName) == false)
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
}
