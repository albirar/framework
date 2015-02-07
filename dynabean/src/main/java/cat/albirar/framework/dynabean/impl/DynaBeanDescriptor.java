/*
 * This file is part of "dynabean".
 * 
 * "dynabean" is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * "dynabean" is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with calendar.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright (C) 2015 Octavi Fornés <ofornes@albirar.cat>
 */

package cat.albirar.framework.dynabean.impl;

import static cat.albirar.framework.dynabean.impl.DynaBeanUtils.fromMethodToPropertyName;
import static cat.albirar.framework.dynabean.impl.DynaBeanUtils.isCorrectProperty;
import static cat.albirar.framework.dynabean.impl.DynaBeanUtils.isGetter;
import static cat.albirar.framework.dynabean.impl.DynaBeanUtils.isPropertyMethod;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.util.StringUtils;

/**
 * A descriptor for a {@link DynaBeanImpl}.
 * @author Octavi Fornés <ofornes@albirar.cat>
 * @since 2.0
 */
class DynaBeanDescriptor<T> implements Serializable {
	private static final long serialVersionUID = -6361067303521108635L;
	
	/** The implemented qualified type name. */
	private Class<T> implementedType;
	/** The list of properties descriptors. */
	private Map<String, PropertyDescriptor> properties;
    /** Property names in appearance order */
    private List<String> names;
    /**
     * A pattern to create a 'toString' response.
     * Only need to call {@link String#format(String, Object...)} with the values.
     */
	private String patternForToString;
	/**
	 * Default constructor.
	 */
	public DynaBeanDescriptor() {
		properties = Collections.synchronizedMap(new TreeMap<String, PropertyDescriptor>());
		names = Collections.synchronizedList(new ArrayList<String>());
	}
	/**
	 * Constructor with the type to implement to get the information.
	 * @param typeToImplement the type to implement.
	 * @throws IllegalArgumentException If {@code typeToImplement} is null or isn't an interface
	 */
	public DynaBeanDescriptor(Class<T> typeToImplement) {
		this();
		
    	PropertyDescriptor propDesc;
    	StringBuilder stb;
    	String s;
    	
        if(typeToImplement == null || typeToImplement.isInterface() == false)
        {
            // Only interfaces
            throw new IllegalArgumentException("DynaBean can only implement interfaces. '" + typeToImplement.getName() + "' is not an interface");
        }
    	
    	implementedType = typeToImplement;
        // Prepare the properties list
        for(Method method : typeToImplement.getMethods())
        {
            if(Modifier.isPublic(method.getModifiers()) && isPropertyMethod(method.getName())
            		&& isCorrectProperty(method))
            {
                if((propDesc = getPropertyByMethodName(method.getName())) == null)
                {
                    // Put them!
                    propDesc = new PropertyDescriptor();
                    propDesc.propertyName = fromMethodToPropertyName(method.getName());
                    if(isGetter(method.getName())) {
                    	propDesc.primitive = method.getReturnType().isPrimitive();
                    	propDesc.getterMethod = method;
                    } else {
                    	propDesc.primitive = method.getParameterTypes()[0].isPrimitive();
                    	propDesc.setterMethod = method;
                    }
                    putPropertyDescriptor(propDesc);
                } else {
                	if(isGetter(method.getName())) {
                		propDesc.getterMethod = method;
                	} else {
                		propDesc.setterMethod = method;
                	}
                }
            }
        }
        // Prepare the string pattern
        stb = new StringBuilder();
        stb.append(implementedType.getSimpleName()).append(" [");
        s = "";
        for(String name: names) {
        	stb.append(s).append(name).append("=%s");
        	s = ", ";
        }
        // Change the last element (",") by "]"
        stb.append("]");
        patternForToString = stb.toString();
	}
	/**
	 * The implemented type.
	 * @return The class of implemented type
	 */
	public Class<T> getImplementedType() {
		return implementedType;
	}


	/**
	 * The implemented type.
	 * @param implementedType The class of implemented type
	 */
	public void setImplementedType(Class<T> implementedType) {
		this.implementedType = implementedType;
	}
	
	/**
	 * Get the property descriptor for the indicated method name.
	 * @param methodName The method name; required, should to be a 'get' or 'is' or 'set'.
	 * @return The property descriptor, if found, or null if not found or not a 'property method'.
	 * @see DynaBeanUtils#isPropertyMethod(String) 
	 */
	public PropertyDescriptor getPropertyByMethodName(String methodName) {
		if(StringUtils.hasText(methodName) && DynaBeanUtils.isPropertyMethod(methodName)) {
			return properties.get(fromMethodToPropertyName(methodName));
		}
		return null;
	}
	/**
	 * Get the property descriptor for the indicated property name.
	 * @param propName The property name, is required.
	 * @return The property descriptor, if found, or null if not found
	 */
	public PropertyDescriptor getPropertyByPropertyName(String propName) {
		if(StringUtils.hasText(propName)) {
			return properties.get(propName);
		}
		return null;
	}
	
	private void putPropertyDescriptor(PropertyDescriptor propDesc) {
		properties.put(propDesc.propertyName, propDesc);
		names.add(propDesc.propertyName);
	}
	
	/**
	 * The property descriptor collection.
	 * @return The collection, can be empty but never null
	 */
	public Iterable<PropertyDescriptor> getProperties() {
		return properties.values();
	}
	/**
	 * The list of property in same order as declared at {@link #getImplementedType()}.
	 * @return A unmodifiable list with the property names.
	 */
	public List<String> getPropertyNames() {
		return Collections.unmodifiableList(names);
	}
	/**
	 * Gets a pattern for the 'toString' method compose.
	 * @return A patter to use with {@link String#format(String, Object...)} with only the values
	 */
	public String getPatternForToString() {
		return patternForToString;
	}
    /**
     * On deserialize, reconstruct the implemented type information.
     */
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
    {
    	
        in.defaultReadObject();
        resolveAfterDeserialization();
    }
    
    /**
     * Resolve the non-serializable information after a deserialization process.
     * Basically gets the {@link Method} for getter and setters.
     */
    private void resolveAfterDeserialization() {
        PropertyDescriptor pb;
        
        for(Method method: implementedType.getMethods()) {
            if(Modifier.isPublic(method.getModifiers()) && isPropertyMethod(method.getName()))
            {
                pb = properties.get(fromMethodToPropertyName(method.getName()));
                if(isGetter(method.getName()))
                {
                    pb.getterMethod = method;
                }
                else
                {
                    pb.setterMethod = method;
                }
            }
        }
    }
}
