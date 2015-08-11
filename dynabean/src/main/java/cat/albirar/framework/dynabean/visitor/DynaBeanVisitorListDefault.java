/*
 * This file is part of "albirar-framework".
 * 
 * "albirar-framework" is free software: you can redistribute it and/or modify it under the terms of the GNU General
 * Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * "albirar-framework" is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with calendar. If not, see
 * <http://www.gnu.org/licenses/>.
 *
 * Copyright (C) 2015 Octavi Fornés ofornes@albirar.cat
 */
package cat.albirar.framework.dynabean.visitor;

import java.util.List;
import java.util.Vector;

/**
 * A {@link IDynaBeanVisitor} implementation for chaining visitors as a {@link List}.
 * The list is executed entirely, no control of returned values are made.
 * If any visitor is null, this is ignored.
 * 
 * @author Octavi Fornés ofornes@albirar.cat
 * @since 2.0
 */
public class DynaBeanVisitorListDefault extends Vector<IDynaBeanVisitor> implements IDynaBeanVisitor, List<IDynaBeanVisitor>
{
    private static final long serialVersionUID = -7135301012824132934L;

    /**
     * {@inheritDoc}
     */
    @Override
    public Object eventGet(String name, Object value, Class<?> propertyType)
    {
        for(IDynaBeanVisitor visitor : this)
        {
            if(visitor != null)
            {
                value = visitor.eventGet(name, value, propertyType);
            }
        }
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object eventSet(String name, Object value, Class<?> propertyType)
    {
        for(IDynaBeanVisitor visitor : this)
        {
            if(visitor != null)
            {
                value = visitor.eventSet(name, value, propertyType);
            }
        }
        return value;
    }
}
