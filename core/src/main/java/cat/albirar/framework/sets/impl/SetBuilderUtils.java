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

import java.beans.PropertyDescriptor;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import org.springframework.util.Assert;

import cat.albirar.framework.sets.ISet;
import cat.albirar.framework.sets.ISetBuilder;

/**
 * Utilities for {@link ISet} builder.
 * @author Octavi Fornés ofornes@albirar.cat
 * @since 2.1.0
 */
public abstract class SetBuilderUtils
{
    /** The property RE pattern. */
    public static final String PROPERTY_PATTERN = "\\p{javaJavaIdentifierStart}\\p{javaJavaIdentifierPart}*";
    public static final String PROPERTY_PATH_PATTERN = "(" + PROPERTY_PATTERN + ")(\\." + PROPERTY_PATTERN + ")*";
    /** A path pattern matcher for check. */
    private static final Pattern pathPattern = Pattern.compile(PROPERTY_PATH_PATTERN);
    /**
     * Create a new instance of builder to operate to.
     * @param rootModel The root model of the set
     * @return The instantiated builder
     */
    public static ISetBuilder instantiateBuilderFor(Class<?> rootModel)
    {
        return new SetBuilderDefaultImpl(rootModel);
    }
    /**
     * Check if path is correct for the indicated model.
     * @param model The model
     * @param propertyPath The property path, required
     * @return true if correct and false if not
     * @throws IllegalArgumentException If the property path is null or empty or only whitespace or not correct format
     */
    public static boolean checkPathForModel(Class<?> model, String propertyPath)
    {
        StringTokenizer stk;
        ModelDescriptor pathFollower;
        PropertyDescriptor pdesc;
        String ppath;
        
        Assert.hasText(propertyPath, "The property path is required and cannot be empty or only whitespace");
        
        // Check pattern
        if(!pathPattern.matcher(propertyPath).matches())
        {
            throw new IllegalArgumentException(String.format("The property path '%s' is incorrect!"
                    , propertyPath));
        }
        stk = new StringTokenizer(propertyPath, ".");
        pathFollower = new ModelDescriptor(model);
        while(stk.hasMoreTokens())
        {
            ppath = stk.nextToken();
            if( (pdesc = pathFollower.getProperties().get(ppath)) != null)
            {
                if(stk.hasMoreTokens())
                {
                    pathFollower = new ModelDescriptor(pdesc.getPropertyType());
                }
            }
            else
            {
                // Error
                return false;
            }
        }
        return true;
    }

}
