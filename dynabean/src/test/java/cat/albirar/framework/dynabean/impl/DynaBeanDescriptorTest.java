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

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cat.albirar.framework.dynabean.DynaBeanUtils;

/**
 * Test for {@link DynaBeanDescriptor}.
 * 
 * @author Octavi Fornés ofornes@albirar.cat
 * @since 2.0
 */
public class DynaBeanDescriptorTest
{
    private IDynaBeanImplementationFactory factory;
    
    @Before public void initTest()
    {
        factory = (IDynaBeanImplementationFactory) DynaBeanUtils.instanceDefaultFactory();
    }
    
    /**
     * Test {@link DynaBeanDescriptor#DynaBeanDescriptor(IDynaBeanImplementationFactory, Class)} with null class model.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testInstantiateWithNull()
    {
        new DynaBeanDescriptor<DynaBeanDescriptorTest.IDynaBeanDescriptorTestValidModel>(factory, null);
    }
    /**
     * Test {@link DynaBeanDescriptor#DynaBeanDescriptor(IDynaBeanImplementationFactory, Class)} with a non-interface class model.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testInstantiateWithNonInterfaceModel()
    {
        new DynaBeanDescriptor<String>(factory, String.class);
    }
    /**
     * Test {@link DynaBeanDescriptor#DynaBeanDescriptor(IDynaBeanImplementationFactory, Class)} with a model without properties.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testInstantiateWithNonPropertiesModel()
    {
        new DynaBeanDescriptor<IDynaBeanDescriptorTestNonPropertiesModel>(factory, IDynaBeanDescriptorTestNonPropertiesModel.class);
    }
    /**
     * Test {@link DynaBeanDescriptor#DynaBeanDescriptor(IDynaBeanImplementationFactory, Class)} with a model with incoherent properties.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testInstantiateWithInvalidModel()
    {
        new DynaBeanDescriptor<IDynaBeanDescriptorTestInvalidModel>(factory, IDynaBeanDescriptorTestInvalidModel.class);
    }
    /**
     * Test {@link DynaBeanDescriptor#DynaBeanDescriptor(IDynaBeanImplementationFactory, Class)} with a model with some properties incorrects, but acceptable model.
     */
    @Test public void testInstantiateWithAcceptableInvalidModel()
    {
        DynaBeanDescriptor<IDynaBeanDescriptorTestModelWithInvalidProperties> d;
        
        d = new DynaBeanDescriptor<IDynaBeanDescriptorTestModelWithInvalidProperties>(factory, IDynaBeanDescriptorTestModelWithInvalidProperties.class);
        Assert.assertTrue(d.isValidDescriptor());
    }
    /**
     * Test {@link DynaBeanDescriptor#DynaBeanDescriptor(IDynaBeanImplementationFactory, Class)} with a totally valid model.
     */
    @Test public void testInstantiateWithTotallyValidModel()
    {
        DynaBeanDescriptor<IDynaBeanDescriptorTestValidModel> d;
        
        d = new DynaBeanDescriptor<IDynaBeanDescriptorTestValidModel>(factory, IDynaBeanDescriptorTestValidModel.class);
        Assert.assertTrue(d.isValidDescriptor());
    }
    /**
     * A valid model to test.
     */
    interface IDynaBeanDescriptorTestValidModel
    {
        public int getFieldInt();
        public void setFieldInt(int fieldInt);
        public boolean isBooleanValue();
        public void setBooleanValue(boolean booleanValue);
        public String getReadOnlyStringProperty();
        public String [] getArrayProperty();
        public void setArrayProperty(String [] arrayProperty);
        public List<String> getCollectionProperty();
        public void setCollectionProperty(List<String> collectionProperty);
        public String [] getReadOnlyArrayProperty();
        public void setWriteOnlyArrayProperty(String [] arrayProperty);
        public List<String> getReadOnlyCollectionProperty();
        public void setWriteOnlyCollectionProperty(List<String> collectionProperty);
    }
    interface IDynaBeanDescriptorTestValidModelWithDynaBean
    {
        
    }
    /** Model without properties. */
    interface IDynaBeanDescriptorTestNonPropertiesModel
    {
        public int nonPropertyMethod();
        public int getInvalidFieldInt(int fieldInt);
        public String isInvalidReturnTypeForBooleanValue();
    }
    /** Some properties are invalids, but the model is acceptable. */
    interface IDynaBeanDescriptorTestModelWithInvalidProperties extends IDynaBeanDescriptorTestValidModel, IDynaBeanDescriptorTestNonPropertiesModel
    {
    }
    /** Model with incoherent type properties. */
    interface IDynaBeanDescriptorTestInvalidModel extends IDynaBeanDescriptorTestValidModel
    {
        public int getIncoherentProperty();
        public void setIncoherentProperty(String incoherentProperty);
    }
}
