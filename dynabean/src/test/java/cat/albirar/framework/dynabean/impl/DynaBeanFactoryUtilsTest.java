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

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cat.albirar.framework.dynabean.impl.models.test.IModel;

/**
 * Test for {@link DynaBeanFactoryUtils}.
 * @author Octavi Fornés ofornes@albirar.cat
 * @since 2.0
 */
public class DynaBeanFactoryUtilsTest
{
    private InvocationHandler nonDynabean;
    private Object proxifiedNonDynabean;
    private Object dynaBean;
    private Object proxifiedDynaBean;
    /**
     * Initialize test.
     */
    @Before
    public void initTest()
    {
        nonDynabean = new InvocationHandler()
        {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
            {
                return null;
            }
        };
        proxifiedNonDynabean = Proxy.newProxyInstance(getClass().getClassLoader()
                , new Class<?>[] {IModel.class}, nonDynabean);
        dynaBean = new DynaBeanImpl<IModel>(new DynaBeanDescriptor<IModel>(new DefaultDynaBeanFactory(), IModel.class));
        proxifiedDynaBean = Proxy.newProxyInstance(getClass().getClassLoader(), new Class<?>[] {IModel.class}, (InvocationHandler)dynaBean);
    }
    /**
     * Test {@link DynaBeanFactoryUtils#deproxifyDynabean(Object)} with a null object.
     */
    @Test(expected=NullPointerException.class)
    public void testDeproxifyWithNull()
    {
        DynaBeanFactoryUtils.deproxifyDynabean(null);
    }
    /**
     * Test {@link DynaBeanFactoryUtils#deproxifyDynabean(Object)} with a non dynaBean object.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testDeproxifyWithNonDynaBean()
    {
        DynaBeanFactoryUtils.deproxifyDynabean("X");
    }
    /**
     * Test {@link DynaBeanFactoryUtils#deproxifyDynabean(Object)} with a non dynaBean object.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testDeproxifyWithProxyNonDynaBean()
    {
        DynaBeanFactoryUtils.deproxifyDynabean(proxifiedNonDynabean);
    }
    /**
     * Test {@link DynaBeanFactoryUtils#deproxifyDynabean(Object)} with a proxy dynaBean object.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testDeproxifyWithProxy()
    {
        DynaBeanImpl<IModel> o;
        
        o = (DynaBeanImpl<IModel>) DynaBeanFactoryUtils.deproxifyDynabean(proxifiedDynaBean);
        Assert.assertSame(dynaBean, o);
    }
    /**
     * Test {@link DynaBeanFactoryUtils#deproxifyDynabean(Object)} with a dynaBean deproxified object.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testDeproxifyWithDynaBean()
    {
        DynaBeanImpl<IModel> o;
        
        o = (DynaBeanImpl<IModel>) DynaBeanFactoryUtils.deproxifyDynabean(dynaBean);
        Assert.assertSame(dynaBean, o);
    }
    /**
     * Test {@link DynaBeanFactoryUtils#checkDynaBean(Object)} with a null object.
     */
    @Test
    public void testCheckDynaBeanWithNull()
    {
        Assert.assertFalse(DynaBeanFactoryUtils.checkDynaBean(null));
    }
    /**
     * Test {@link DynaBeanFactoryUtils#checkDynaBean(Object)} with a non dynaBean object.
     */
    @Test
    public void testCheckDynaBeanWithNonDynaBean()
    {
        Assert.assertFalse(DynaBeanFactoryUtils.checkDynaBean(nonDynabean));
    }
    /**
     * Test {@link DynaBeanFactoryUtils#checkDynaBean(Object)} with a non dynaBean proxified object.
     */
    @Test
    public void testCheckDynaBeanWithProxifiedNonDynaBean()
    {
        Assert.assertFalse(DynaBeanFactoryUtils.checkDynaBean(proxifiedNonDynabean));
    }
    /**
     * Test {@link DynaBeanFactoryUtils#checkDynaBean(Object)} with a dynaBean object.
     */
    @Test
    public void testCheckDynaBeanWithDynaBean()
    {
        Assert.assertTrue(DynaBeanFactoryUtils.checkDynaBean(dynaBean));
    }
    /**
     * Test {@link DynaBeanFactoryUtils#checkDynaBean(Object)} with a dynaBean proxified object.
     */
    @Test
    public void testCheckDynaBeanWithProxifiedDynaBean()
    {
        Assert.assertTrue(DynaBeanFactoryUtils.checkDynaBean(proxifiedDynaBean));
    }
    /**
     * Test {@link DynaBeanFactoryUtils#assertDynaBean(Object)} with a null object.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testAssertDynaBeanWithNull()
    {
        DynaBeanFactoryUtils.assertDynaBean(null);
    }
    /**
     * Test {@link DynaBeanFactoryUtils#assertDynaBean(Object, String)} with a null object.
     */
    @Test public void testAssertDynaBeanMessageWithNull()
    {
        String msg;
        
        msg = "Test Message with null";
        try
        {
            DynaBeanFactoryUtils.assertDynaBean(null, msg);
            Assert.fail("Expected an IllegalArgumentException!");
        }
        catch(IllegalArgumentException e)
        {
            Assert.assertEquals(msg, e.getMessage());
        }
    }
    /**
     * Test {@link DynaBeanFactoryUtils#assertDynaBean(Object)} with a non dynaBean object.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testAssertDynaBeanWithNonDynaBean()
    {
        DynaBeanFactoryUtils.assertDynaBean(nonDynabean);
    }
    /**
     * Test {@link DynaBeanFactoryUtils#assertDynaBean(Object, String)} with a non dynaBean object.
     */
    @Test public void testAssertDynaBeanMessageWithNonDynaBean()
    {
        String msg;
        
        msg = "Test Message non dynaBean";
        try
        {
            DynaBeanFactoryUtils.assertDynaBean(nonDynabean, msg);
            Assert.fail("Expected an IllegalArgumentException!");
        }
        catch(IllegalArgumentException e)
        {
            Assert.assertEquals(msg, e.getMessage());
        }
    }
    /**
     * Test {@link DynaBeanFactoryUtils#assertDynaBean(Object)} with a proxified non dynaBean object.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testAssertDynaBeanWithProxifiedNonDynaBean()
    {
        DynaBeanFactoryUtils.assertDynaBean(proxifiedNonDynabean);
    }
    /**
     * Test {@link DynaBeanFactoryUtils#assertDynaBean(Object, String)} with a proxified non dynaBean object.
     */
    @Test public void testAssertDynaBeanMessageWithProxifiedNonDynaBean()
    {
        String msg;
        
        msg = "Test Message proxified non dynaBean";
        try
        {
            DynaBeanFactoryUtils.assertDynaBean(proxifiedNonDynabean, msg);
            Assert.fail("Expected an IllegalArgumentException!");
        }
        catch(IllegalArgumentException e)
        {
            Assert.assertEquals(msg, e.getMessage());
        }
    }
    /**
     * Test {@link DynaBeanFactoryUtils#assertDynaBean(Object)} with a dynaBean object.
     */
    @Test public void testAssertDynaBeanWithDynaBean()
    {
        DynaBeanFactoryUtils.assertDynaBean(dynaBean);
    }
    /**
     * Test {@link DynaBeanFactoryUtils#assertDynaBean(Object)} with a proxified dynaBean object.
     */
    @Test public void testAssertDynaBeanWithProxifiedDynaBean()
    {
        DynaBeanFactoryUtils.assertDynaBean(proxifiedDynaBean);
    }
}
