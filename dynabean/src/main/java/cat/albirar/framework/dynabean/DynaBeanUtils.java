/*
 * This file is part of "albirar framework".
 * 
 * "albirar framework" is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * "albirar framework" is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with calendar.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright (C) 2013 Octavi Fornés
 */
package cat.albirar.framework.dynabean;

import cat.albirar.framework.dynabean.impl.DefaultDynaBeanFactory;

/**
 * The dynaBean creator.
 * 
 * For use with interfaces that represents a Java Bean. <br>
 * <b>Use</b>
 * <pre>
 * InterfaceJavaBean a;
 * 
 * a = DynaBeanUtils.instanceFactory.newDynaBean(InterfaceJavaBean.class);
 * ...
 * a.setXXX("xxx");
 * </pre>
 * 
 * @author <a href="mailto:ofornes@albirar.cat">Octavi Fornés ofornes@albirar.cat</a>
 * @since 1.0.0
 */
public abstract class DynaBeanUtils
{
	/**
	 * Thread safe singleton.
	 */
	private static final ThreadLocal<IDynaBeanFactory> singleton = new ThreadLocal<IDynaBeanFactory>() {
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected IDynaBeanFactory initialValue() {
			return new DefaultDynaBeanFactory();
		}
		
	};
	/**
	 * Gets a factory instance.
	 * This is a {@link ThreadLocal} factory
	 * @return The factory
	 */
	public static final IDynaBeanFactory instanceDefaultFactory() {
		return singleton.get();
	}
}
