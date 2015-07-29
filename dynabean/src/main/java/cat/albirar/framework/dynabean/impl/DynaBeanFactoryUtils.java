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
 * Copyright (C) 2015 Octavi Fornés ofornes@albirar.cat
 */

package cat.albirar.framework.dynabean.impl;

import java.lang.reflect.Proxy;

import org.springframework.util.Assert;

import cat.albirar.framework.dynabean.IDynaBeanFactory;

/**
 * Some utils for {@link IDynaBeanFactory} implementations.
 * @author Octavi Fornés ofornes@albirar.cat
 * @since 2.0
 */
public abstract class DynaBeanFactoryUtils
{
    /**
     * Extract a dynaBean from proxy or return directly if isn't proxy.
     * @param dynaBean The dynabean (proxified or not) <b>REQUIRED</b>
     * @return The {@link DynaBeanImpl} deproxified if needed
     * @throws IllegalArgumentException If the dynaBean is not of the class {@link DynaBeanImpl}
     */
    public static DynaBeanImpl<?> deproxifyDynabean(Object dynaBean)
    {
        Object db;
        if(Proxy.isProxyClass(dynaBean.getClass()))
        {
            db = Proxy.getInvocationHandler(dynaBean);
        }
        else
        {
            db = dynaBean;
        }
        Assert.isTrue(DynaBeanImpl.class.isAssignableFrom(db.getClass()), "The dynaBean should to be a true dynaBean");
        return (DynaBeanImpl<?>)db;
    }
    /**
     * Check if the object is a {@link DynaBeanImpl} or a Proxy with a {@link DynaBeanImpl} as a {@link Proxy#getInvocationHandler(Object) invocation handler}.
     * @param object The object (or proxy)
     * @return true if the object is a {@link DynaBeanImpl} instance or a {@link Proxy} with a {@link DynaBeanImpl} as an invocation handler. Return false otherwise ever if object is null 
     */
    public static boolean checkDynaBean(Object object)
    {
        if(object != null)
        {
            return ((Proxy.isProxyClass(object.getClass()) && DynaBeanImpl.class.isAssignableFrom(Proxy.getInvocationHandler(object).getClass()))
                    || DynaBeanImpl.class.isAssignableFrom(object.getClass()));
        }
        return false;
    }
    /**
     * Asserts that {@code object} is a DynaBean.
     * @param object The object <b>REQUIRED</b>
     * @throws IllegalArgumentException if the {@code object} is not a {@link DynaBeanImpl} or its null
     * @see #checkDynaBean(Object)
     */
    public static void assertDynaBean(Object object)
    {
        assertDynaBean(object, "The object is not a DynaBean implementation");
    }
    /**
     * Asserts that {@code object} is a DynaBean.
     * @param object The object <b>REQUIRED</b>
     * @param message The message to show if assertion fails
     * @throws IllegalArgumentException if the {@code object} is not a {@link DynaBeanImpl} or its null
     * @see #checkDynaBean(Object)
     */
    public static void assertDynaBean(Object object, String message)
    {
        if(!checkDynaBean(object))
        {
            throw new IllegalArgumentException(message);
        }
    }
}
