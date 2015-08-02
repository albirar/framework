/*
 * This file is part of "dynabean".
 * 
 * "dynabean" is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * "dynabean" is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with calendar. If not, see
 * <http://www.gnu.org/licenses/>.
 * 
 * Copyright (C) 2015 Octavi Fornés <ofornes@albirar.cat>
 */

package cat.albirar.framework.dynabean.impl;

import java.beans.PropertyEditor;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Collection;

import cat.albirar.framework.dynabean.annotations.DynaBean;
import cat.albirar.framework.dynabean.annotations.PropertyDefaultValue;

/**
 * A simple data structure to hold aspects of a property.
 * 
 * @author <a href="mailto:ofornes@albirar.cat">Octavi Fornés ofornes@albirar.cat</a>
 * @since 2.0
 */
class DynaBeanPropertyDescriptor implements Serializable
{
    private static final long serialVersionUID = 6311968165570922423L;

    /** The property name. */
    String propertyName;

    /** The property path. */
    String propertyPath;

    /** Item property type if property is collection or array. */
    Class<?> itemType;

    /** If this property, that should to be a interface, is marked as {@link DynaBean} managed property. */
    boolean dynaBean;
    
    /** If the component (array o collection element type) is marked as a {@link DynaBean} managed element. */
    boolean itemDynaBean;

    /** Default value for new instances. */
    String [] defaultValue;

    /** Property editor for assigning values or items in arrays or collections. */
    transient PropertyEditor propertyItemEditor;

    /** Clone method for this property, if applicable. */
    transient Method propertyItemCloneMethod;

    /** The default concrete implementation for this property. */
    Class<?> defaultImplementation;

    /** The getter method. */
    transient Method getterMethod;

    /** The setter method. */
    transient Method setterMethod;

    /**
     * Utility to know if is a read-write property (true) or not (false).
     * @return true if is a read-write property and false if not
     */
    public boolean isRW()
    {
        return (getterMethod != null && setterMethod != null);
    }

    /**
     * Indicates if this property is a {@link DynaBeanImpl}.
     * 
     * @return true if is a dynaBean or false if not
     */
    public boolean isDynaBean()
    {
        return dynaBean;
    }

    /**
     * Indicates if the item (array or collection item) property is a {@link DynaBeanImpl}.
     * @return true if is a dynaBean or false if not
     */
    public boolean isItemDynaBean()
    {
        return itemDynaBean;
    }

    /**
     * Gets the property name -not method name-.
     * 
     * @return The property name
     */
    public String getPropertyName()
    {
        return propertyName;
    }

    /**
     * The property path for this property. Generally is the qualified name of client class plus dot plus property name.
     * 
     * @return The property path
     */
    public String getPropertyPath()
    {
        return propertyPath;
    }

    /**
     * The type of this property.
     * 
     * @return The type class
     */
    public Class<?> getPropertyType()
    {
        if(getterMethod != null)
        {
            return getterMethod.getReturnType();
        }
        return setterMethod.getParameterTypes()[0];
    }

    /**
     * The item type when property type is array or collection.
     * 
     * @return The item type or {@link #getPropertyType()} if not applicable
     */
    public Class<?> getItemType()
    {
        return itemType;
    }

    /**
     * Indicates if this property is primitive.
     * 
     * @return true if primitive and false if not
     */
    public boolean isPrimitive()
    {
        return getPropertyType().isPrimitive();
    }

    /**
     * Indicates if this property is an array.
     * 
     * @return true if is an array and false if not
     */
    public boolean isArray()
    {
        return getPropertyType().isArray();
    }

    /**
     * Indicates if this property is a {@link Collection}.
     * 
     * @return true if is a collection and false if not
     */
    public boolean isCollection()
    {
        return Collection.class.isAssignableFrom(getPropertyType());
    }

    /**
     * Indicates if this property implements the {@link Cloneable} interface.
     * @return true if implements it and false if not
     */
    public boolean isCloneable()
    {
        if(isCollection() || isArray())
        {
            return Cloneable.class.isAssignableFrom(getItemType().getClass());
        }
        return Cloneable.class.isAssignableFrom(getPropertyType().getClass());
    }
    /**
     * Default value for this property, as indicated in {@link PropertyDefaultValue} anotation.
     * 
     * @return The default value
     */
    public String [] getDefaultValue()
    {
        return defaultValue;
    }

    /**
     * Property editor for this property or item if array or collection.
     * 
     * @return The property editor or null if none is found.
     */
    public PropertyEditor getPropertyItemEditor()
    {
        return propertyItemEditor;
    }

    /**
     * The clone method for this property or item if array or collection.
     * 
     * @return the clone method or null if non aplicable
     */
    public Method getPropertyItemCloneMethod()
    {
        return propertyItemCloneMethod;
    }

    /**
     * The getter method.
     * 
     * @return The getter method
     */
    public Method getGetterMethod()
    {
        return getterMethod;
    }

    /**
     * The setter method.
     * 
     * @return The setter method
     */
    public Method getSetterMethod()
    {
        return setterMethod;
    }

    /**
     * The default concrete implementation {@link Class}, as indicated in {@link PropertyDefaultValue} anotation.
     * 
     * @return The default implementation or null if none is defined
     */
    public Class<?> getDefaultImplementation()
    {
        return defaultImplementation;
    }

}
