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

import java.util.List;

import cat.albirar.framework.patterns.ITransformerVisitor;

/**
 * A visitor to write values into a collection.
 * 
 * @author Octavi Fornés ofornes@albirar.cat
 * @since 2.0
 */
class CollectionWriterVisitor extends AbstractWriterVisitor<List<Object>, Object> implements IPropertyWriter
{
    public CollectionWriterVisitor(ITransformerVisitor<Object> reader, List<Object> valueList)
    {
        super(reader);
        setCollectedObject(valueList);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(Object element)
    {
        getCollectedObject().add(getReader().visit(element));
    }
}
