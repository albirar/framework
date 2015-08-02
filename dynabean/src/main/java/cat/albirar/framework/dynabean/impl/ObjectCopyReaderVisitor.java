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

import java.beans.PropertyEditor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import cat.albirar.framework.patterns.ITransformerVisitor;

/**
 * A visitor to read from clone or copy by editor or return directly a valye.
 * @author Octavi Fornés ofornes@albirar.cat
 * @since 2.0
 */
class ObjectCopyReaderVisitor implements ITransformerVisitor<Object>
{
    private PropertyEditor pEditor;
    private Method cloneMethod;
    private boolean dynaBean;
    private IDynaBeanImplementationFactory factory;
    /**
     * Constructor for copy value.
     */
    public ObjectCopyReaderVisitor()
    {
        pEditor = null;
        cloneMethod = null;
        dynaBean = false;
    }
    /**
     * Constructor for dynaBean copy.
     * @param factory The factory to copy values
     */
    public ObjectCopyReaderVisitor(IDynaBeanImplementationFactory factory)
    {
        this();
        dynaBean = true;
        this.factory = factory;
    }
    /**
     * Constructor for {@link PropertyEditor} read.
     * @param pEditor The property editor to use
     */
    public ObjectCopyReaderVisitor(PropertyEditor pEditor)
    {
        this();
        this.pEditor = pEditor;
    }
    /**
     * Constructor for {@link Method} clone read.
     * @param cloneMethod The method to use for clone.
     */
    public ObjectCopyReaderVisitor(Method cloneMethod)
    {
        this();
        this.cloneMethod = cloneMethod;
    }
    
    /**
     * Perform the read of the value, clone, copy by edit or return directly.
     * @param originalValue The original value to clone, copy by edit or return directly
     * @return The result value
     */
    @Override
    public Object visit(Object originalValue)
    {
        if(cloneMethod != null)
        {
            try
            {
                return cloneMethod.invoke(originalValue, (Object[]) null);
            }
            catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
            {
                throw new RuntimeException("On cloning value '" + originalValue + "'", e);
            }
        }
        if(pEditor != null)
        {
            if(String.class.isAssignableFrom(originalValue.getClass()))
            {
                pEditor.setAsText((String) originalValue);
            }
            else
            {
                pEditor.setValue(originalValue);
                pEditor.setAsText(pEditor.getAsText());
            }
            return pEditor.getValue();
        }
        if(dynaBean)
        {
            return factory.cloneDynaBean(originalValue);
        }
        // Copy method
        return originalValue;
    }
}
