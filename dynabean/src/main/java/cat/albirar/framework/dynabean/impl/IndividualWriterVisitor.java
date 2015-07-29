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

import cat.albirar.framework.patterns.ITransformerVisitor;

/**
 * A writer for only one value.
 * Assigns the first value visited and ignore the others.
 * @author Octavi Fornés ofornes@albirar.cat
 * @since 2.0
 */
public class IndividualWriterVisitor extends AbstractWriterVisitor<Object, Object> implements IPropertyWriter
{
    private boolean assigned;
    /**
     * Unique constructor.
     * @param reader The reader to use to write
     */
    public IndividualWriterVisitor(ITransformerVisitor<Object> reader)
    {
        super(reader);
        assigned = false;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(Object element)
    {
        if(!assigned)
        {
            assigned = true;
            setCollectedObject(getReader().visit(element));
        }
    }
}
