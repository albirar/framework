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

package cat.albirar.framework.dynabean.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.TreeMap;

/**
 * Indicates the default value for a property on instantiation.
 * The value is indicated as the textual representation of the type, by example:
 * <ul>
 * <li><b>true</b> o <b>false</b> for {@code boolean} value.</li>
 * <li><b>25.3F</b> for a {@code float} value.</li>
 * <li>etc...</li>
 * </ul>
 * If indicates an implementation class, {@link DynaBean} will create a new instance and
 * assign them to the property, by exemple:
 * <pre>
    &#64;DynaBean
    &#64;PropertyDefaultValue(implementation={&#64;link TreeMap java.util.TreeMap})
    public Map&lt;String, String&gt; getMap();
 </pre>
 * In this case, {@link DynaBean} will instantiate a {@link TreeMap} for the {@code map} property.<br>
 * <strong>If both are indicated, {@link #implementation()} have precedence over {@link #value()}.</strong>
 * 
 * @author <a href="mailto:ofornes@albirar.cat">Octavi Fornés ofornes@albirar.cat</a>
 * @since 2.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface PropertyDefaultValue {
	/**
	 * A specific value string representation of the value to use.
	 * @return the value
	 */
	String [] value() default {""};
	/**
	 * A specific implementation class to be instantiated.
	 * @return the implementation class
	 */
	Class<?> implementation() default Object.class;
}
