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

import java.lang.reflect.Proxy;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.beans.SimpleTypeConverter;
import org.springframework.util.Assert;

/**
 * A convenient default factory.
 * @author <a href="mailto:ofornes@albirar.cat">Octavi Fornés ofornes@albirar.cat</a>
 * @since 2.0
 */
public class DefaultDynaBeanFactory implements IDynaBeanImplementationFactory {
	private PropertyEditorRegistry propRegistry;
	
	private Map<String, DynaBeanDescriptor<?>> descriptors;

	/**
	 * Default constructor.
	 */
	public DefaultDynaBeanFactory() {
		propRegistry = new SimpleTypeConverter();
		descriptors = Collections.synchronizedMap(new TreeMap<String, DynaBeanDescriptor<?>>());
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T> T newDynaBean(Class<T> typeToImplement) {
		if(typeToImplement == null)
		{
			throw new IllegalArgumentException("typeToImplement can not to be null");
		}
		return createDynaBean(new DynaBeanImpl<T>(this, typeToImplement));
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T cloneDynaBean(T dynaBean) {
		if(dynaBean == null) {
			throw new IllegalArgumentException("The dynaBean to clone is required");
		}
		if(DynaBeanImpl.class.isAssignableFrom(dynaBean.getClass())) {
			return createDynaBean(new DynaBeanImpl<T>((DynaBeanImpl<T>)dynaBean));
		}
		
		throw new IllegalArgumentException("To clone a dynaBean, another dynaBean is required!");
	}
	/**
	 * Proxyfy the dynaBean instace.
	 * @param typeToImplement The type to implement
	 * @param dynaBean The dynabean
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private <T> T createDynaBean(DynaBeanImpl<T> dynaBean) {
		return (T)Proxy.newProxyInstance(dynaBean.getClass().getClassLoader()
				, new Class[] {dynaBean.getImplementedType()}
				, dynaBean);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PropertyEditorRegistry getPropertyEditorRegistry() {
		return propRegistry;
	}
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> DynaBeanDescriptor<T> getDescriptorFor(Class<T> typeToImplement) {
		DynaBeanDescriptor<?> d;
		
		Assert.notNull(typeToImplement, "The typeToImplement is required");
		
		d = descriptors.get(typeToImplement.getName());
		if(d == null) {
			DynaBeanDescriptor<T> desc;
			
			desc = new DynaBeanDescriptor<T>(this, typeToImplement);
			descriptors.put(typeToImplement.getName(), desc);
			d = desc;
		}
		return (DynaBeanDescriptor<T>) d;
	}
	
}
