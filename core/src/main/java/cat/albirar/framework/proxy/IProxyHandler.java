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

package cat.albirar.framework.proxy;

import java.lang.reflect.Method;

/**
 * A generic interface to handle the callback of a proxy.
 * This enable to use JDK Dynamic Proxies or CGLib Proxies.
 * 
 * @author Octavi Fornés ofornes@albirar.cat
 * @since 2.1.0
 */
public interface IProxyHandler
{
    /**
     * Called on invoke any method of the proxied type.
     * @param target The target object, that is, the proxy itself (for proxied interfaces) or the instantiated object (for proxied concrete class)
     * @param method The invoked method
     * @param arguments The arguments
     * @return The return value
     * @see java.lang.reflect.InvocationHandler
     * @see org.springframework.cglib.proxy.InvocationHandler
     */
    public Object invokeMethod(Object target, Method method, Object [] arguments);
}
