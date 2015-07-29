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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cat.albirar.framework.dynabean.annotations.DynaBean;
import cat.albirar.framework.dynabean.impl.models.test.IAnnotatedModel;
import cat.albirar.framework.dynabean.impl.models.test.IModel;
import cat.albirar.framework.dynabean.visitor.IDynaBeanVisitor;

/**
 * The class test for {@link DefaultDynaBeanFactory}.
 * 
 * @author Octavi Fornés ofornes@albirar.cat
 * @sinze 2.0
 */
public class DefaultDynaBeanFactoryTest
{
    private DefaultDynaBeanFactory factory;
    private IDynaBeanVisitor visitor;
    /**
     * Create the factory.
     */
    @Before public void initTest()
    {
        factory = new DefaultDynaBeanFactory();
        visitor = new IDynaBeanVisitor()
        {
            @Override
            public Object eventSet(String name, Object value, Class<?> propertyType)
            {
                // Nothing to do
                return null;
            }
            
            @Override
            public Object eventGet(String name, Object value, Class<?> propertyType)
            {
                // Nothing to do
                return null;
            }
        };
    }
    /**
     * Test the feature of {@link DefaultDynaBeanFactory#getDescriptorFor(Class) descriptor registry} with null value.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testDescriptorRegistryForNull()
    {
        factory.getDescriptorFor(null);
    }
    /**
     * Test the feature of {@link DefaultDynaBeanFactory#getDescriptorFor(Class) descriptor registry} with a non suitable {@link DynaBean} class.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testDescriptorRegistryForNonDynaBean()
    {
        factory.getDescriptorFor(String.class);
    }
    /**
     * Test the feature of {@link DefaultDynaBeanFactory#getDescriptorFor(Class) descriptor registry} with a {@link DynaBean} class.
     */
    @Test public void testDescriptorRegistryForDynaBean()
    {
        DynaBeanDescriptor<IAnnotatedModel> descriptor1, descriptor2;
        
        descriptor1 = factory.getDescriptorFor(IAnnotatedModel.class);
        Assert.assertNotNull(descriptor1);
        descriptor2 = factory.getDescriptorFor(IAnnotatedModel.class);
        Assert.assertNotNull(descriptor2);
        Assert.assertSame(descriptor1, descriptor2);
    }
    /**
     * Test {@link DefaultDynaBeanFactory#addVisitorToDynaBean(Object, IDynaBeanVisitor)} with a non-proxy instance.
     */
    @Test public void testSetVisitorNoProxy()
    {
        DynaBeanImpl<IModel> d;
        DynaBeanDescriptor<IModel> dbd;
        
        dbd = new DynaBeanDescriptor<IModel>(factory, IModel.class);
        d = new DynaBeanImpl<IModel>(dbd);
        factory.addVisitorToDynaBean(d, visitor);
        Assert.assertSame(visitor, d.getVisitor());
    }
    /**
     * Test {@link DefaultDynaBeanFactory#addVisitorToDynaBean(Object, IDynaBeanVisitor)} with a proxy instance.
     */
    @SuppressWarnings("unchecked")
    @Test public void testSetVisitorProxy()
    {
        IModel d;
        DynaBeanImpl<IModel> db;
        
        d = factory.newDynaBean(IModel.class);
        factory.addVisitorToDynaBean(d, visitor);
        db = (DynaBeanImpl<IModel>) DynaBeanFactoryUtils.deproxifyDynabean(d);
        Assert.assertSame(visitor, db.getVisitor());
    }
    /**
     * Test {@link DefaultDynaBeanFactory#addVisitorToDynaBean(Object, IDynaBeanVisitor)} with a non-dynaBean implementation.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testSetVisitorNoDynaBeanImplementation()
    {
        factory.addVisitorToDynaBean("X", visitor);
    }
    /**
     * Test {@link DefaultDynaBeanFactory#removeVisitorFromDynaBean(Object)} with a null dynaBean.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testSetVisitorToNullDynaBean()
    {
        factory.addVisitorToDynaBean(null, visitor);
    }
    /**
     * Test {@link DefaultDynaBeanFactory#addVisitorToDynaBean(Object, IDynaBeanVisitor)} with a null visitor.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testSetVisitorNullVisitor()
    {
        IModel d;
        
        d = factory.newDynaBean(IModel.class);
        factory.addVisitorToDynaBean(d, null);
    }
    /**
     * Test {@link DefaultDynaBeanFactory#removeVisitorFromDynaBean(Object)} with a non dynaBean object.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testRemoveVisitorNoDynaBean()
    {
        factory.removeVisitorFromDynaBean("X");
    }
    /**
     * Test {@link DefaultDynaBeanFactory#removeVisitorFromDynaBean(Object)} with no visitor.
     */
    @Test public void testRemoveVisitorNoVisitor()
    {
        IModel d;
        DynaBeanImpl<?> db;
        
        d = factory.newDynaBean(IModel.class);
        db = DynaBeanFactoryUtils.deproxifyDynabean(d);
        Assert.assertNull("No visitor at constructor!", db.getVisitor());
        factory.removeVisitorFromDynaBean(d);
        db = DynaBeanFactoryUtils.deproxifyDynabean(d);
        Assert.assertNull("No visitor after remove!", db.getVisitor());
    }
    /**
     * Test {@link DefaultDynaBeanFactory#removeVisitorFromDynaBean(Object)} with no visitor.
     */
    @Test public void testRemoveVisitor()
    {
        IModel d;
        DynaBeanImpl<?> db;
        
        d = factory.newDynaBean(IModel.class, visitor);
        db = DynaBeanFactoryUtils.deproxifyDynabean(d);
        Assert.assertNotNull("Visitor at constructor!", db.getVisitor());
        factory.removeVisitorFromDynaBean(d);
        db = DynaBeanFactoryUtils.deproxifyDynabean(d);
        Assert.assertNull("No visitor after remove!", db.getVisitor());
    }
    /**
     * Test {@link DefaultDynaBeanFactory#newDynaBean(Class)} with null.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testNewDynabeanNull()
    {
        factory.newDynaBean(null);
    }
    
}
