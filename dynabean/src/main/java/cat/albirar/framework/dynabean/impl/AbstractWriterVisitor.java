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
 * Copyright (C) 2015 Octavi Fornés ofornes@albirar.cat
 */

package cat.albirar.framework.dynabean.impl;

import cat.albirar.framework.patterns.ICollectorVisitor;
import cat.albirar.framework.patterns.ITransformerVisitor;

/**
 * An abstract visitor to write values to a property.
 * @param <R> The return value type
 * @param <T> The reader value type
 * @author Octavi Fornés ofornes@albirar.cat
 * @since 2.0
 */
public abstract class AbstractWriterVisitor<R, T> implements ICollectorVisitor<R, T>
{
    private ITransformerVisitor<T> reader;
    private R collectedObject;
    /**
     * Constructor with reader.
     * @param reader The reader to use before write
     */
    public AbstractWriterVisitor(ITransformerVisitor<T> reader)
    {
        this.reader = reader;
    }
    /**
     * The reader to use in each iteration.
     * @return The reader
     */
    public ITransformerVisitor<T> getReader()
    {
        return reader;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public R getCollectedObject()
    {
        return collectedObject;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void setCollectedObject(R collectedObject)
    {
        this.collectedObject = collectedObject;
    }
    /**
     * Generic method to get the collected object.
     * @return The collected object
     */
    public Object getReturnValue()
    {
        return (Object)getCollectedObject();
    }
}
