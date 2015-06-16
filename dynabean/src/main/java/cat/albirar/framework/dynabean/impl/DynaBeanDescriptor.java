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

import static cat.albirar.framework.dynabean.impl.DynaBeanImplementationUtils.fromMethodToPropertyName;
import static cat.albirar.framework.dynabean.impl.DynaBeanImplementationUtils.isCorrectProperty;
import static cat.albirar.framework.dynabean.impl.DynaBeanImplementationUtils.isGetter;
import static cat.albirar.framework.dynabean.impl.DynaBeanImplementationUtils.isPropertyMethod;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import cat.albirar.framework.dynabean.annotations.DynaBean;
import cat.albirar.framework.dynabean.annotations.PropertyDefaultValue;

/**
 * A descriptor for a {@link DynaBeanImpl}.
 * @author <a href="mailto:ofornes@albirar.cat">Octavi Fornés ofornes@albirar.cat</a>
 * @since 2.0
 */
public class DynaBeanDescriptor<T> implements Serializable {
	
	private static final long serialVersionUID = 90102804695593324L;
	
	private static final Logger logger = LoggerFactory.getLogger(DynaBeanDescriptor.class);
	
	/** The implemented qualified type name. */
	private Class<T> implementedType;
	/** The list of properties descriptors. */
	private Map<String, DynaBeanPropertyDescriptor> properties;
	/** The associated dynaBean factory. */
	private transient IDynaBeanImplementationFactory factory;
    /**
     * A pattern to create a 'toString' response.
     * Only need to call {@link String#format(String, Object...)} with the values.
     */
	private String patternForToString;
	/**
	 * Default constructor.
	 */
	DynaBeanDescriptor(IDynaBeanImplementationFactory factory) {
		this.factory = factory;
		properties = Collections.synchronizedMap(new TreeMap<String, DynaBeanPropertyDescriptor>());
	}
	/**
	 * Constructor with the type to implement to get the information.
	 * @param typeToImplement the type to implement.
	 * @throws IllegalArgumentException If {@code typeToImplement} is null or isn't an interface
	 */
	public DynaBeanDescriptor(IDynaBeanImplementationFactory factory, Class<T> typeToImplement) {
		this(factory);
		
    	DynaBeanPropertyDescriptor propDesc;
    	StringBuilder stb;
    	String s;
    	
        if(typeToImplement == null || typeToImplement.isInterface() == false)
        {
            // Only interfaces
        	logger.error("DynaBean can only implement interfaces. '" + typeToImplement.getName() + "' is not an interface");
            throw new IllegalArgumentException("DynaBean can only implement interfaces. '" + typeToImplement.getName() + "' is not an interface");
        }
    	
    	implementedType = typeToImplement;
    	if(logger.isDebugEnabled()) {
    		logger.debug("Working for implementing the type '".concat(typeToImplement.getName()).concat("'"));
    	}
        // Prepare the properties list
        for(Method method : typeToImplement.getMethods())
        {
            if(Modifier.isPublic(method.getModifiers()) && isPropertyMethod(method.getName())
            		&& isCorrectProperty(method))
            {
                if((propDesc = getPropertyByMethodName(method.getName())) == null)
                {
                    // Put them!
                    propDesc = new DynaBeanPropertyDescriptor();
                    propDesc.propertyName = fromMethodToPropertyName(method.getName());
                    propDesc.propertyPath = implementedType.getName().concat(".").concat(propDesc.propertyName);
                    if(logger.isDebugEnabled()) {
                    	logger.debug("Working on property '".concat(implementedType.getName()).concat(".").concat(propDesc.getPropertyName()));
                    }
                    if(isGetter(method.getName())) {
                    	propDesc.getterMethod = method;
                    } else {
                    	propDesc.setterMethod = method;
                    }
                    properties.put(propDesc.getPropertyName(), propDesc);
                } else {
                	if(isGetter(method.getName())) {
                		propDesc.getterMethod = method;
                	} else {
                		propDesc.setterMethod = method;
                	}
                }
            }
        }
        // The descriptor have all the properties
        // Prepare the string pattern and test annotations
        stb = new StringBuilder();
        stb.append(implementedType.getSimpleName()).append(" [");
        s = "";
        for(String name: getPropertyNames()) {
        	// Test annotations
        	processAnnotations(properties.get(name));
        	// Add the property to the 'toString' pattern
        	stb.append(s).append(name).append("=%s");
        	s = ", ";
        }
        // Change the last element (",") by "]"
        stb.append("]");
        patternForToString = stb.toString();
        if(logger.isDebugEnabled()) {
        	logger.debug("Pattern string for '".concat(implementedType.getClass().getName()).concat("': ").concat(patternForToString));
        }
	}
	/**
	 * Process the annotations for the property.
	 * @param propDesc The property
	 */
	private void processAnnotations(DynaBeanPropertyDescriptor propDesc) {
		PropertyDefaultValue pdv;
		DynaBean db;

		// Is a dynaBean property?
		if( (db = getAnnotationForProperty(propDesc, DynaBean.class)) != null) {
			propDesc.dynaBean = true;
			propDesc.defaultValue = new String [] {Boolean.toString(db.defaultInstantiate())};
		} else {
			if( (pdv = getAnnotationForProperty(propDesc, PropertyDefaultValue.class)) != null) {
				if(pdv.implementation() != null 
						&& !Object.class.getName().equals(pdv.implementation().getName())) {
					if(pdv.implementation().isInterface()) {
						// Check for DynaBean
						if(pdv.implementation().isAnnotationPresent(DynaBean.class)) {
							// Instantiate by factory
							propDesc.dynaBean = true;
							propDesc.defaultValue = new String [] {Boolean.TRUE.toString()};
						} else {
							logger.error("The implementation type (" + pdv.implementation().getName() 
									+ ") for the property '" + propDesc.getPropertyName() + "' isn't a concrete class!");
						}
					} else {
						// Concrete class implementation!
						propDesc.defaultImplementation = pdv.implementation();
					}
				} else {
					if(!hasText(pdv.value())) {
						// No implementation && no value
						if(propDesc.getPropertyType().isInterface()) {
							// Check for DynaBean
							if(propDesc.getPropertyType().isAnnotationPresent(DynaBean.class)) {
								// Instantiate by factory
								propDesc.dynaBean = true;
								propDesc.defaultValue = new String[] {Boolean.TRUE.toString()};
							} else {
								logger.error("The property '".concat(propDesc.getPropertyName())
										.concat("' is not instantiable! Cannot be marked as 'default'"));
							}
						} else {
							// Concrete class implementation!
							propDesc.defaultImplementation = propDesc.getPropertyType();
						}
					} else {
						propDesc.defaultValue = pdv.value();
					}
				}
			} else {
				propDesc.defaultValue = null;
			}
		}
	}
	/**
	 * Check for {@code annotationClass} at get/set method for the indicated {@code propDesc}.
	 * The precedence is:
	 * <ol>
	 * <li>{@link DynaBeanPropertyDescriptor#getGetterMethod()}</li>
	 * <li>{@link DynaBeanPropertyDescriptor#getSetterMethod()}</li>
	 * </ol>
	 * 
	 * @param propDesc The property descriptor
	 * @param annotationClass The annotation class
	 * @return The annotation; null if annotation was not found.
	 */
	private <A extends Annotation> A getAnnotationForProperty(DynaBeanPropertyDescriptor propDesc, Class<A> annotationClass) {
		A annotation;
		
		annotation = null;
		if(propDesc.getGetterMethod() != null) {
			annotation = propDesc.getGetterMethod().getAnnotation(annotationClass);
		}
		if(annotation == null && propDesc.getSetterMethod() != null) {
			annotation = propDesc.getSetterMethod().getAnnotation(annotationClass);
		}
		return annotation;
	}
	/**
	 * The factory associated with this descriptor.
	 * @return The factory
	 */
	public IDynaBeanImplementationFactory getFactory() {
		return factory;
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
	 * @see DynaBeanImplementationUtils#isPropertyMethod(String) 
	 */
	public DynaBeanPropertyDescriptor getPropertyByMethodName(String methodName) {
		if(StringUtils.hasText(methodName) && DynaBeanImplementationUtils.isPropertyMethod(methodName)) {
			return properties.get(fromMethodToPropertyName(methodName));
		}
		return null;
	}
	/**
	 * Get the property descriptor for the indicated property name.
	 * @param propName The property name, is required.
	 * @return The property descriptor, if found, or null if not found
	 */
	public DynaBeanPropertyDescriptor getPropertyByPropertyName(String propName) {
		if(StringUtils.hasText(propName)) {
			return properties.get(propName);
		}
		return null;
	}
	
	/**
	 * The property descriptor collection.
	 * @return The collection, can be empty but never null
	 */
	public Iterable<DynaBeanPropertyDescriptor> getProperties() {
		return properties.values();
	}
	/**
	 * The list of property names.
	 * @return A unmodifiable list with the property names.
	 */
	public Set<String> getPropertyNames() {
		return Collections.unmodifiableSet(properties.keySet());
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
        DynaBeanPropertyDescriptor pb;
        
        factory = DynaBeanImplementationUtils.instanceFactory();
        
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
    /**
     * Check that strings isn't null, have one or more items and all items {@link StringUtils#hasText(String) has text}.
     * @param strings The array of strings
     * @return true if has text and false if not
     */
    private boolean hasText(String ...strings)
    {
        if(strings != null && strings.length > 0)
        {
            for(String s : strings)
            {
                if(!StringUtils.hasText(s))
                {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
