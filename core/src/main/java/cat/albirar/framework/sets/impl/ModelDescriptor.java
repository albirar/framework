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
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;

/**
 * A model descriptor.
 * @author Octavi Fornés ofornes@albirar.cat
 * @since 2.1.0
 */
public class ModelDescriptor
{
    private String relativePath;
    private String originalPath;
    private Class<?> model;
    private Map<String, PropertyDescriptor> properties;
    /**
     * Constructor for model.
     * @param model The model, required
     * @throws IllegalArgumentException If model is null
     */
    public ModelDescriptor(Class<?> model)
    {
        Assert.notNull(model, "The model is required");
        relativePath = "";
        originalPath = "";
        this.model = model;
        properties = Collections.synchronizedMap(new TreeMap<String, PropertyDescriptor>());
        resolveProperties();
    }
    /**
     * Constructor for model and relative path to calculate property path.
     * @param relativePath The relative path of this model
     * @param model The model
     */
    public ModelDescriptor(String relativePath, String originalPath, Class<?> model)
    {
        this(model);
        this.relativePath = relativePath;
        this.originalPath = originalPath;
    }
    /**
     * The relative path of this model; if root, the relative path is an empty string.
     * @return The relative path
     */
    public String getRelativePath()
    {
        return relativePath;
    }
    
    /**
     * The original path for this descriptor.
     * @return the originalPath
     */
    public String getOriginalPath()
    {
        return originalPath;
    }
    /**
     * Resolve the indicated path with relative.
     * @param path The path to resolve
     * @return The resolved path
     */
    public String resolvePath(String path)
    {
        if(relativePath.isEmpty())
        {
            return path;
        }
        return relativePath.concat(".").concat(path);
    }
    /**
     * The property descriptor map of the model
     * @return The map of property descriptors, with the property name as key
     */
    public Map<String, PropertyDescriptor> getProperties()
    {
        return properties;
    }
    /**
     * Resolve properties of the model.
     */
    private void resolveProperties()
    {
        PropertyDescriptor [] pd;
        
        pd = BeanUtils.getPropertyDescriptors(model);
        for(PropertyDescriptor prop : pd)
        {
            properties.put(prop.getName(), prop);
        }
    }

}
