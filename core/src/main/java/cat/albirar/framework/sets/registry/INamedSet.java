/*
 * This file is part of "albirar-framework".
 * 
 * "albirar-framework" is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * "albirar-framework" is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with calendar.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright (C) 2015 Octavi Fornés
 */
package cat.albirar.framework.sets.registry;

import cat.albirar.framework.sets.ISet;

/**
 * A {@link ISet} with name for a registry.
 * 
 * @author Octavi Fornés ofornes@albirar.cat
 * @since 2.1.0
 */
public interface INamedSet<T> extends ISet<T>, Comparable<INamedSet<T>>
{
    /**
     * The name of this set.
     * @return The name
     */
    public String getName();
}
