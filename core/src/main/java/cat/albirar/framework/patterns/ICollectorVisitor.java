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
package cat.albirar.framework.patterns;

/**
 * A collector visitor.
 * Can be used to collect values into an object, like an array, collection, etc.
 * @param <R> The collected type
 * @param <T> The element type to visit
 * @author Octavi Fornés ofornes@albirar.cat
 * @since 2.0
 */
public interface ICollectorVisitor<R, T> extends IVisitor<T>
{
    /**
     * The collected values.
     * Can be an array, a collection, etc.
     * @return The collected object
     */
    public R getCollectedObject();
    /**
     * The collected values.
     * Can be an array, a collection, etc.
     * @param collectedObject The collected object
     */
    public void setCollectedObject(R collectedObject);
}
