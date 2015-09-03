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
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.util.Assert;

/**
 * A factory to create proxies for interfaces or classes.
 * Use a JDK Dynamic Proxy if the type is an interface.
 * Use a CGLib proxy if the type is a concrete class.
 * 
 * @author Octavi Fornés ofornes@albirar.cat
 * @since 2.1.0
 */
public class ProxyFactory
{
    private static ProxyFactory singleton;
    /**
     * Factory instantiation method.
     * @return A singleton proxy factory
     */
    public static ProxyFactory instanceProxy()
    {
        if(singleton == null)
        {
            singleton = new ProxyFactory();
        }
        return singleton;
    }
    /**
     * Create a proxy for the indicated type, tied to the indicated type.
     * @param handler The handler, required
     * @param type
     * @return the new type
     */
    public <T> T newProxy(IProxyHandler handler, Class<T> type)
    {
        Assert.notNull(handler, "Handler is required");
        Assert.notNull(type, "Type is required");
        if(type.isInterface())
        {
            return newProxyForInterface(new ProxyInvocationHandlerWrapper(handler), type);
        }
        return newProxyForConcreteClass(new ProxyInvocationHandlerWrapper(handler), type);
    }
    /**
     * Create a proxy for the indicated type.
     * @param handler The handler
     * @param type The type, should to be a concrete class type
     * @return The proxy
     */
    @SuppressWarnings("unchecked")
    private <T> T newProxyForConcreteClass(org.springframework.cglib.proxy.Callback handler, Class<T> type)
    {
        Enhancer enhancer;
        
        Assert.isTrue(!Modifier.isAbstract(type.getModifiers()), "The type should to be a concrete class");
        
        enhancer = new Enhancer();
        enhancer.setSuperclass(type);
        enhancer.setClassLoader(type.getClassLoader());
        enhancer.setCallback(handler);
        return (T)enhancer.create();
    }
    /**
     * Create a proxy for the indicated type.
     * @param handler The handler
     * @param type The type, should to be a interface type
     * @return The proxy
     */
    @SuppressWarnings("unchecked")
    private <T> T newProxyForInterface(java.lang.reflect.InvocationHandler handler, Class<T> type)
    {
        Assert.isTrue(type.isInterface(), "The type should to be an interface");
        return (T)Proxy.newProxyInstance(type.getClassLoader(), new Class[]
        {
                type
        }, handler);
    }
    /**
     * A class for wrap a {@link IProxyHandler} into a "dynamic proxy" or a "cglib proxy".
     * @author Octavi Fornés ofornes@albirar.cat
     * @since 2.1.0
     */
    class ProxyInvocationHandlerWrapper implements org.springframework.cglib.proxy.InvocationHandler, java.lang.reflect.InvocationHandler, org.springframework.cglib.proxy.Callback
    {
        private IProxyHandler proxyHandler;
        /**
         * Unique constructor.
         * @param proxyHandler The proxy handler to manage methods call
         */
        ProxyInvocationHandlerWrapper(IProxyHandler proxyHandler)
        {
            Assert.notNull(proxyHandler, "The proxy handler is required");
            this.proxyHandler = proxyHandler;
        }
        /**
         * {@inheritDoc}
         */
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
        {
            return proxyHandler.invokeMethod(proxy, method, args);
        }
    }
}
