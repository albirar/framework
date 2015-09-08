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

package cat.albirar.framework.sets.registry;

import cat.albirar.framework.sets.ISet;
import cat.albirar.framework.sets.registry.impl.NamedSetDefaultImpl;

/**
 * Utils to instantiate {@link INamedSet}.
 * @author Octavi Fornés ofornes@albirar.cat
 * @since 2.1.0
 */
public abstract class NamedSetUtils
{
    /**
     * Instantiate a {@link INamedSet} for the indicated root model and set name.
     * @param rootModel The root model, required
     * @param setName The set name, required and not an empty string or whitespace string
     * @return The named set
     * @throws IllegalArgumentException If root model is null or name is null or empty or only whitespace string
     */
    public static <T> INamedSet<T> instantiateNamedSetFor(Class<T> rootModel, String setName)
    {
        return new NamedSetDefaultImpl<T>(rootModel, setName);
    }
    /**
     * Instantiate a {@link INamedSet} as a copy of the indicated set and for the indicated set name.
     * @param origin The origin set, required
     * @param setName The set name, required and not an empty string or whitespace string
     * @return The named set
     * @throws IllegalArgumentException If root model is null or name is null or empty or only whitespace string
     */
    public static <T> INamedSet<T> instantiateNamedSetFor(ISet<T> origin, String setName)
    {
        return new NamedSetDefaultImpl<T>(origin, setName);
    }
}
