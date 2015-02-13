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

package cat.albirar.framework.dynabean;

import java.beans.PropertyEditor;

import org.springframework.beans.PropertyEditorRegistry;

/**
 * The contract for DynaBean factories.
 * 
 * @author <a href="mailto:ofornes@albirar.cat">Octavi Fornés ofornes@albirar.cat</a>
 * @since 2.0
 */
public interface IDynaBeanFactory {
	/**
	 * DynaBean instantiator.
	 * @param typeToImplement The Class of the type to implement.
	 * @param <T> The type to implement
	 * @return The instance dynaBean
	 */
	public <T> T newDynaBean(Class<T> typeToImplement);
	/**
	 * Clone a dynaBean
	 * @param <T> The type to implement
	 * @param dynaBean The dynaBean to clone
	 * @return The new cloned dynaBean
	 */
	public <T> T cloneDynaBean(T dynaBean);
	
	/**
	 * Gets the {@link PropertyEditor} registry associated with this factory.
	 * @return The registry
	 */
	public PropertyEditorRegistry getPropertyEditorRegistry();
}
