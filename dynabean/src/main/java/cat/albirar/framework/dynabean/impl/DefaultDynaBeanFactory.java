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

import cat.albirar.framework.dynabean.IDynaBeanFactory;

/**
 * A default and convenient factory.
 * @author Octavi Fornés <ofornes@albirar.cat>
 * @since 2.0
 */
public class DefaultDynaBeanFactory implements IDynaBeanFactory {
	private static final long serialVersionUID = 1L;
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
		return createDynaBean((DynaBeanImpl<T>)dynaBean);
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
}
