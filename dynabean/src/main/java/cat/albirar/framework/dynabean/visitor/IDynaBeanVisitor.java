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

package cat.albirar.framework.dynabean.visitor;

/**
 * A contract for visitor implementations in get/set operations.
 * @pattern Visitor
 * @author <a href="mailto:ofornes@albirar.cat">Octavi Fornés ofornes@albirar.cat</a>
 * @since 2.0
 */
public interface IDynaBeanVisitor {
	/**
	 * Event fired on call a get method.
	 * The {@code value} object is the current property value.
	 * @param name The property name
	 * @param value The current value of the property.
	 * @return The value to return to. Generally, the same {@code value} as indicated
	 */
	public Object eventGet(String name, Object value);
	/**
	 * Event fired on call a set method.
	 * The {@code value} object is the new property value.
	 * @param name The property name
	 * @param value The new value for the property.
	 * @return The value to set to. Generally, the same {@code value} as indicated
	 */
	public Object eventSet(String name, Object value);
}
