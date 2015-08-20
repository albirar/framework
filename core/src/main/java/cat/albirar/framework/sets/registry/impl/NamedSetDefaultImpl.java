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

package cat.albirar.framework.sets.registry.impl;

import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import cat.albirar.framework.sets.impl.SetDefaultImpl;
import cat.albirar.framework.sets.registry.INamedSet;

/**
 * Default implementation of a {@link INamedSet}.
 * @author Octavi Fornés ofornes@albirar.cat
 * @since 2.1.0
 */
public class NamedSetDefaultImpl extends SetDefaultImpl implements INamedSet
{
    private static final long serialVersionUID = 3227604849293846449L;
    private String name;

    /**
     * Unique constructor
     * @param modelRoot
     */
    public NamedSetDefaultImpl(Class<?> modelRoot, String name)
    {
        super(modelRoot);
        Assert.hasText(name, "The name is a required argument, cannot be null, nor empty nor whitespace!");
        this.name = name;
    }

    /**
     * {@inheritDocs}
     */
    @Override
    public String getName()
    {
        return name;
    }

    /**
     * Add the {@link #getName()} on equals algorithm.
     * 
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o)
    {
        if(o == this)
        {
            return true;
        }
        if(o == null)
        {
            return false;
        }
        
        if(INamedSet.class.isAssignableFrom(o.getClass()))
        {
            return (ObjectUtils.nullSafeEquals(name, ((INamedSet)o).getName())
                    && super.equals(o));
        }
        return false;
    }

    @Override
    protected String toStringProperties()
    {
        return ("name=" + name + ", " + super.toStringProperties());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(INamedSet o)
    {
        return (getName().compareTo(o.getName()));
    }
    
}
