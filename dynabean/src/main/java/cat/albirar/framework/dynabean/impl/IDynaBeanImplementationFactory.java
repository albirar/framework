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

import cat.albirar.framework.dynabean.IDynaBeanFactory;
import cat.albirar.framework.dynabean.annotations.DynaBean;

/**
 * Factory with method for implementation classes, not users.
 * @author <a href="mailto:ofornes@albirar.cat">Octavi Fornés ofornes@albirar.cat</a>
 * @since 2.0
 */
public interface IDynaBeanImplementationFactory extends IDynaBeanFactory {
	
	/**
	 * Search for a descriptor for the indicated dynaBean.
	 * @param <T> The type to implement
	 * @param typeToImplement The {@link Class} type to implement, required
	 * @return The descriptor.
	 * @throws IllegalArgumentException If {@code typeToImplement} is null or isn't suitable for {@link DynaBean}
	 */
	public <T> DynaBeanDescriptor<T> getDescriptorFor(Class<T> typeToImplement);
}
