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

/**
 * A simple data structure to hold aspects of a property.
 * @author Octavi Fornés <ofornes@albirar.cat>
 * @since 2.0
 */
class PropertyDescriptor implements Serializable {
	private static final long serialVersionUID = 6311968165570922423L;
	/** The property name. */
	String propertyName;
	/** If property is primitive (true) or not (false). */
	boolean primitive;
	/** Default value for new instances. */
	Object defaultValue;
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
}
