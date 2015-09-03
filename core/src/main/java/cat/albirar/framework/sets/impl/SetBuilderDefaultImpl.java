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
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.Vector;

import org.springframework.util.Assert;

import cat.albirar.framework.sets.ISet;
import cat.albirar.framework.sets.ISetBuilder;

/**
 * Default implementation of {@link ISetBuilder}.
 * @author Octavi Fornés ofornes@albirar.cat
 * @since 2.1.0
 */
public class SetBuilderDefaultImpl implements ISetBuilder
{
    /** Text to use if current path is empty for messages. */
    private static final String TEXT_FOR_EMPTY_CURRENT_PATH = "root";
    private Stack<ModelDescriptor> pathStack;
    private ModelDescriptor currentModelDescriptor;
    private List<String> properties;
    private Class<?> rootModel;
    /**
     * Unique constructor.
     * @param rootModel The root model to build the {@link ISet}. <b>required</b>
     * @throws IllegalArgumentException If the {@code rootModel} is null
     */
    public SetBuilderDefaultImpl(Class<?> rootModel)
    {
        Assert.notNull(rootModel, "The rootModel argument is required");
        this.rootModel = rootModel;
        pathStack = new Stack<ModelDescriptor>();
        properties = new Vector<String>();
        currentModelDescriptor = new ModelDescriptor(rootModel);
        // The root always on bottom of stack
        pathStack.push(currentModelDescriptor);
    }
    /**
     * {@inheritDocs}
     */
    @Override
    public ISetBuilder addProperty(String propertyPath)
    {
        if(!checkPath(propertyPath))
        {
            throw new IllegalArgumentException("The path denoted by '" + propertyPath + "' at '" 
                    + getCurrentPathOrRoot() + "' for model '" + rootModel.getName() 
                    + "' doesn't exists. Cannot be added to set!");
        }
        
        properties.add(resolvePath(propertyPath));
        return this;
    }

    /**
     * {@inheritDocs}
     */
    @Override
    public ISetBuilder pushPropertyPath(String propertyPath)
    {
        StringTokenizer stk;
        String spath;
        PropertyDescriptor pdesc;
        
        // Verify propertyPath
        Assert.hasText(propertyPath);
        stk = new StringTokenizer(propertyPath, ".");
        while(stk.hasMoreTokens())
        {
            spath = stk.nextToken();
            if( (pdesc = currentModelDescriptor.getProperties().get(spath)) != null)
            {
                currentModelDescriptor = new ModelDescriptor(resolvePath(spath), propertyPath ,pdesc.getPropertyType());
            }
            else
            {
                throw new IllegalArgumentException("The path denoted by '" + propertyPath + "' at '" 
                        + getCurrentPathOrRoot() + "' for model '" + rootModel.getName() + "' doesn't exists. Cannot be pushed!");
            }
        }
        pathStack.push(currentModelDescriptor);
        return this;
    }

    /**
     * {@inheritDocs}
     */
    @Override
    public ISetBuilder popPropertyPath()
    {
        if(pathStack.size() > 1)
        {
            pathStack.pop();
            currentModelDescriptor = pathStack.peek();
        }
        return this;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public String peekPropertyPathStack()
    {
        if(pathStack.size() > 1)
        {
            return pathStack.peek().getOriginalPath();
        }
        return null;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void resetPropertyPathStack()
    {
        while(pathStack.size() > 1)
        {
            pathStack.pop();
        }
        currentModelDescriptor = pathStack.peek();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String getCurrentPropertyPath()
    {
        return currentModelDescriptor.getRelativePath();
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public Class<?> getModelRoot()
    {
        return rootModel;
    }
    /**
     * {@inheritDocs}
     */
    @Override
    public ISet build()
    {
        ISet s;
        
        s = new SetDefaultImpl(rootModel);
        s.addAll(properties);
        return s;
    }
    /**
     * Gets the current path or {@value #TEXT_FOR_EMPTY_CURRENT_PATH} if current path is root.
     * @return The current path or the text of constant {@link #TEXT_FOR_EMPTY_CURRENT_PATH}
     */
    private String getCurrentPathOrRoot()
    {
        if(currentModelDescriptor.getRelativePath().isEmpty())
        {
            return TEXT_FOR_EMPTY_CURRENT_PATH;
        }
        return currentModelDescriptor.getRelativePath();
    }
    /**
     * Check if path is correct for the root model.
     * @param propertyPath The property path
     * @return true if correct and false if not
     */
    private boolean checkPath(String propertyPath)
    {
        StringTokenizer stk;
        ModelDescriptor pathFollower;
        PropertyDescriptor pdesc;
        String ppath;
        
        stk = new StringTokenizer(propertyPath, ".");
        pathFollower = currentModelDescriptor;
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
    /**
     * Resolve path with {@link #currentPath}.
     * @param relativePropertyPath The relative to {@link #currentPath} property path to resolve
     * @return the property path resolved
     */
    private String resolvePath(String relativePropertyPath)
    {
        return currentModelDescriptor.resolvePath(relativePropertyPath);
    }
}
