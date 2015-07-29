/*
 * This file is  part of "albirar-framework".
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

import java.lang.reflect.Array;
import java.util.List;
import java.util.Vector;

import cat.albirar.framework.patterns.ITransformerVisitor;

/**
 * A writter for array.
 * @author Octavi Fornés ofornes@albirar.cat
 * @since 2.0
 */
public class ArrayWriterVisitor extends AbstractWriterVisitor<Object, Object> implements IPropertyWriter
{
    private List<Object> preCollectedObject;
    Class<?> componentType;
    
    /**
     * Unique constructor with reader and component type.
     * @param reader The reader
     * @param componentType The component type
     */
    ArrayWriterVisitor(ITransformerVisitor<Object> reader, Class<?> componentType)
    {
        super(reader);
        this.componentType = componentType;
        preCollectedObject = new Vector<Object>();
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(Object element)
    {
        preCollectedObject.add(getReader().visit(element));
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public Object getCollectedObject()
    {
        Object value;
        int n;
        
        value = Array.newInstance(componentType, preCollectedObject.size());
        for(n = 0; n < preCollectedObject.size(); n++)
        {
            Array.set(value, n, preCollectedObject.get(n));
        }
        return value;
    }
}
