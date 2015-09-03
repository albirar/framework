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
package cat.albirar.framework.sets;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

/**
 * A set is a collection of not-duplicated {@link String strings} that holds <i>paths</i> of property bean names.
 * They can be used to determine parts of models to operate to in CRUD operations or
 * to indicate the properties you should to return to a client from a service call.
 *  
 * @author Octavi Fornés ofornes@albirar.cat
 * @since 2.1.0
 */
public interface ISet extends Set<String>, Serializable
{
    /**
     * The model root of this set.
     * @return The model root.
     */
    public Class<?> getModelRoot();

    /**
     * Add the property name indicated to this set.
     * @param propertyName The property name
     * @throws IllegalArgumentException if the property name is null or empty or only whitespace or if property name is unknown in the {@link #getModelRoot() root model} of this set
     */
    @Override
    boolean add(String propertyName);

    /**
     * Add the property names of the indicated collection to this set.
     * @param c The collection, if null no elements are added and no errors are reported but returns false
     * @return true if at least one property was added and false if not
     * @throws IllegalArgumentException if any of the collection property name is null or empty or only whitespace or if any of the collection property name is unknown in the {@link #getModelRoot() root model} of this set
     */
    @Override
    boolean addAll(Collection<? extends String> c);
    
}
