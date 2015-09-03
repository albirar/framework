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

package cat.albirar.framework.sets.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.TreeSet;

import org.springframework.util.Assert;

import cat.albirar.framework.sets.ISet;
import cat.albirar.framework.sets.SetUtils;

/**
 * A default implementation for {@link ISet}.
 * @author Octavi Fornés ofornes@albirar.cat
 * @since 2.1.0
 */
public class SetDefaultImpl extends TreeSet<String> implements ISet
{
    private static final long serialVersionUID = 4450706681782137190L;
    private Class<?> modelRoot;
    /**
     * Unique constructor.
     * @param modelRoot The model root for this set, <em>required</em>
     * @throws IllegalArgumentException if modelRoot is null
     */
    public SetDefaultImpl(Class<?> modelRoot)
    {
        super();
        Assert.notNull(modelRoot, "The model root is required");
        this.modelRoot = modelRoot;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public Class<?> getModelRoot()
    {
        return modelRoot;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean add(String e)
    {
        // Check for valid argument
        Assert.hasText(e, "The property name is required");
        // First check if property exists
        if(!SetUtils.checkPathForModel(modelRoot, e))
        {
            throw new IllegalArgumentException("The property path '".concat(e)
                    .concat("' doesn't exists at model '")
                    .concat(modelRoot.getName())
                    .concat("'"));
        }
        return super.add(e);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addAll(Collection<? extends String> c)
    {
        boolean ret;
        
        ret = false;
        if(c != null)
        {
            for(String e: c)
            {
                if(add(e))
                {
                    ret = true;
                }
            }
        }
        return ret;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return String.format("%s [%s]", getClass().getSimpleName(), toStringProperties());
    }
    /**
     * Create the {@link #toString()} text only for properties.
     * The derived classes can use this method to get the body of property values.
     * @return The body for properties, without the '[' and ']' at start and end respectivelly.
     */
    protected String toStringProperties()
    {
        StringBuilder stb;
        Iterator<String> itr;
        
        stb = new StringBuilder("modelRoot=");
        stb.append(modelRoot.getName()).append(", properties=[");

        if(!isEmpty())
        {
            itr = iterator();
            stb.append(itr.next());
            while(itr.hasNext())
            {
                stb.append(", ").append(itr.next());
            }
        }
        stb.append("]");
        return stb.toString();
    }
}
