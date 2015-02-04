/*
 * This file is part of "imodel".
 * 
 * "imodel" is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * "imodel" is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with calendar.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright (C) 2013 Octavi Fornés
 */
package cat.albirar.framework.imodel;

import java.lang.reflect.Proxy;

/**
 * The proxy bean factory creator.
 * 
 * For use with interfaces that represents a Java Bean. <br/>
 * <b>Use</b>
 * <pre>
 * InterfaceJavaBean a;
 * 
 * a = ProxyBeanFactory.newProxy(InterfaceJavaBean.class);
 * ...
 * a.setXXX("xxx");
 * </pre>
 * 
 * 
 * @author octavi@fornes.cat
 * @since 1.0.0
 */
public abstract class ProxyBeanFactory
{
    /**
     * Creates a proxy for the type.
     * @param typeToImplement The interface type to implement
     * @return The proxy, as typeToImplement type.
     * @throws IllegalArgumentException If the type is not an interface
     * @throws IllegalArgumentException If null is passed
     */
    @SuppressWarnings("unchecked")
    public static final  <T> T newProxy(Class<T> typeToImplement)
    {
        if(typeToImplement == null)
        {
            throw new IllegalArgumentException("typeToImplement can not to be null");
        }
        return (T)Proxy.newProxyInstance(typeToImplement.getClassLoader(), new Class[] {typeToImplement}, new ProxyBeanImpl<T>(typeToImplement));
    }
    /**
     * Proxyfies the proxy implementation.
     * @param proxy The proxy
     * @return The proxy
     */
    @SuppressWarnings("unchecked")
    public static final <T> T newProxy(ProxyBeanImpl<T> proxy)
    {
        return (T)Proxy.newProxyInstance(proxy.getImplementedType().getClassLoader(), new Class[] {proxy.getImplementedType()},proxy);
    }
}
