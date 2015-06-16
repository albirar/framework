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
import org.junit.Test;

import cat.albirar.framework.dynabean.annotations.DynaBean;
import cat.albirar.framework.dynabean.impl.models.test.IAnnotatedModel;

/**
 * The class test for {@link DefaultDynaBeanFactory}.
 * 
 * @author Octavi Fornés ofornes@albirar.cat
 * @sinze 2.0
 */
public class DefaultDynaBeanFactoryTest
{
    /**
     * Test the feature of {@link DefaultDynaBeanFactory#getDescriptorFor(Class) descriptor registry} with null value.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testDescriptorRegistryForNull()
    {
        IDynaBeanImplementationFactory factory;
        
        factory = new DefaultDynaBeanFactory();
        factory.getDescriptorFor(null);
    }
    /**
     * Test the feature of {@link DefaultDynaBeanFactory#getDescriptorFor(Class) descriptor registry} with a non suitable {@link DynaBean} class.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testDescriptorRegistryForNonDynaBean()
    {
        IDynaBeanImplementationFactory factory;
        
        factory = new DefaultDynaBeanFactory();
        factory.getDescriptorFor(String.class);
    }
    /**
     * Test the feature of {@link DefaultDynaBeanFactory#getDescriptorFor(Class) descriptor registry} with a {@link DynaBean} class.
     */
    @Test public void testDescriptorRegistryForDynaBean()
    {
        IDynaBeanImplementationFactory factory;
        DynaBeanDescriptor<IAnnotatedModel> descriptor1, descriptor2;
        
        factory = new DefaultDynaBeanFactory();
        descriptor1 = factory.getDescriptorFor(IAnnotatedModel.class);
        Assert.assertNotNull(descriptor1);
        descriptor2 = factory.getDescriptorFor(IAnnotatedModel.class);
        Assert.assertNotNull(descriptor2);
        Assert.assertSame(descriptor1, descriptor2);
    }
}
