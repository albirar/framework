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
package cat.albirar.framework.utilities;

import java.util.Collection;

/**
 * Utilities for collections.
 * @author Octavi Fornés ofornes@albirar.cat
 * @since 2.0
 */
public abstract class CollectionsUtilities
{
    /**
     * Check if collection is null or empty.
     * @param collection The collection to check
     * @return true if collection is null or empty and false if isn't null <b>and</b> have one or more elements
     */
    public static final boolean isEmpty(Collection<?> collection)
    {
        return (collection == null || collection.isEmpty());
    }
}
