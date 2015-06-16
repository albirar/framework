/*
 * This file is part of "dynabean".
 * 
 * "dynabean" is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * "dynabean" is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with calendar.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright (C) 2015 Octavi Fornés <ofornes@albirar.cat>
 */

package cat.albirar.framework.dynabean.impl;

import java.io.Serializable;
import java.lang.reflect.Method;

import cat.albirar.framework.dynabean.annotations.DynaBean;
import cat.albirar.framework.dynabean.annotations.PropertyDefaultValue;

/**
 * A simple data structure to hold aspects of a property.
 * @author <a href="mailto:ofornes@albirar.cat">Octavi Fornés ofornes@albirar.cat</a>
 * @since 2.0
 */
class DynaBeanPropertyDescriptor implements Serializable {
	private static final long serialVersionUID = 6311968165570922423L;
	/** The property name. */
	String propertyName;
	String propertyPath;
	/** If this property, that should to be a interface, is marked as {@link DynaBean} managed property. */
	boolean dynaBean;
	/** Default value for new instances. */
	String [] defaultValue;
	/** The default concrete implementation for this property. */
	Class<?> defaultImplementation;
	/** The getter method. */
	transient Method getterMethod;
	/** The setter method. */
	transient Method setterMethod;
	/**
	 * Utility to know if is a read-only property (true) or not (false).
	 * @return true if is a read-only property and false if is a read-write property
	 */
	boolean isRO() {
		return (setterMethod == null);
	}
	/**
	 * Gets the property name -not method name-.
	 * @return The property name
	 */
	public String getPropertyName() {
		return propertyName;
	}
	
	/**
	 * The property path for this property.
	 * Generally is the qualified name of client class plus dot plus property name.
	 * @return The property path
	 */
	public String getPropertyPath() {
		return propertyPath;
	}
	/**
	 * The type of this property.
	 * @return The type class
	 */
	public Class<?> getPropertyType() {
		if(getterMethod != null) {
			return getterMethod.getReturnType();
		}
		if(setterMethod != null) {
			return setterMethod.getParameterTypes()[0];
		}
		return void.class;
	}
	/**
	 * Indicates if this property is primitive.
	 * @return true if primitive and false if not
	 */
	public boolean isPrimitive() {
		return getPropertyType().isPrimitive();
	}
	/**
	 * Default value for this property, as indicated in {@link PropertyDefaultValue} anotation.
	 * @return The default value
	 */
	public Object getDefaultValue() {
		return defaultValue;
	}
	/**
	 * The getter method.
	 * @return The getter method
	 */
	public Method getGetterMethod() {
		return getterMethod;
	}
	/**
	 * The setter method.
	 * @return The setter method
	 */
	public Method getSetterMethod() {
		return setterMethod;
	}
	/**
	 * The default concrete implementation {@link Class}, as indicated in {@link PropertyDefaultValue} anotation.
	 * @return The default implementation or null if none is defined
	 */
	public Class<?> getDefaultImplementation() {
		return defaultImplementation;
	}
	
}
