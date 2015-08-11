/*
 * This file is part of "albirar-framework".
 * 
 * "albirar-framework" is free software: you can redistribute it and/or modify it under the terms of the GNU General
 * Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * "albirar-framework" is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with calendar. If not, see
 * <http://www.gnu.org/licenses/>.
 *
 * Copyright (C) 2015 Octavi Fornés ofornes@albirar.cat
 */

package cat.albirar.framework.dynabean.impl;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.propertyeditors.CustomNumberEditor;

import cat.albirar.framework.dynabean.annotations.PropertyDefaultValue;

/**
 * Test for {@link DynaBeanDescriptor}.
 * 
 * @author Octavi Fornés ofornes@albirar.cat
 * @since 2.0
 */
public class DynaBeanDescriptorTest
{
    private IDynaBeanImplementationFactory factory;

    @Before
    public void initTest()
    {
        factory = new DefaultDynaBeanFactory();
    }

    /**
     * Test {@link DynaBeanDescriptor#DynaBeanDescriptor(IDynaBeanImplementationFactory, Class)} with null class model.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testInstantiateWithNull()
    {
        new DynaBeanDescriptor<DynaBeanDescriptorTest.IDynaBeanDescriptorTestValidModel>(factory, null);
    }

    /**
     * Test {@link DynaBeanDescriptor#DynaBeanDescriptor(IDynaBeanImplementationFactory, Class)} with a non-interface
     * class model.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testInstantiateWithNonInterfaceModel()
    {
        new DynaBeanDescriptor<String>(factory, String.class);
    }

    /**
     * Test {@link DynaBeanDescriptor#DynaBeanDescriptor(IDynaBeanImplementationFactory, Class)} with a model without
     * properties.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testInstantiateWithNonPropertiesModel()
    {
        new DynaBeanDescriptor<IDynaBeanDescriptorTestNonPropertiesModel>(factory, IDynaBeanDescriptorTestNonPropertiesModel.class);
    }

    /**
     * Test {@link DynaBeanDescriptor#DynaBeanDescriptor(IDynaBeanImplementationFactory, Class)} with a model with
     * incoherent properties.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testInstantiateWithInvalidModel()
    {
        new DynaBeanDescriptor<IDynaBeanDescriptorTestInvalidModel>(factory, IDynaBeanDescriptorTestInvalidModel.class);
    }

    /**
     * Test {@link DynaBeanDescriptor#DynaBeanDescriptor(IDynaBeanImplementationFactory, Class)} with a model with some
     * properties incorrects, but acceptable model.
     */
    @Test
    public void testInstantiateWithAcceptableInvalidModel()
    {
        DynaBeanDescriptor<IDynaBeanDescriptorTestModelWithInvalidProperties> d;

        d = new DynaBeanDescriptor<IDynaBeanDescriptorTestModelWithInvalidProperties>(factory, IDynaBeanDescriptorTestModelWithInvalidProperties.class);
        Assert.assertTrue(d.isValidDescriptor());
    }

    /**
     * Test {@link DynaBeanDescriptor#DynaBeanDescriptor(IDynaBeanImplementationFactory, Class)} with a totally valid
     * model.
     */
    @Test
    public void testInstantiateWithTotallyValidModel()
    {
        DynaBeanDescriptor<IDynaBeanDescriptorTestValidModel> d;

        d = new DynaBeanDescriptor<IDynaBeanDescriptorTestValidModel>(factory, IDynaBeanDescriptorTestValidModel.class);
        Assert.assertTrue(d.isValidDescriptor());
    }

    /**
     * Test {@link DynaBeanDescriptor#getPropertyByMethodName(String)} with non valid method names.
     */
    @Test
    public void testGetPropertyByMethodNameWithNonValidMethodNames()
    {
        DynaBeanDescriptor<IDynaBeanDescriptorTestValidModel> d;
        DynaBeanPropertyDescriptor dp;

        d = factory.getDescriptorFor(IDynaBeanDescriptorTestValidModel.class);
        dp = d.getPropertyByMethodName(null);
        Assert.assertNull(dp);
        dp = d.getPropertyByMethodName("");
        Assert.assertNull(dp);
        dp = d.getPropertyByMethodName("           ");
        Assert.assertNull(dp);
        dp = d.getPropertyByMethodName("nonValidMethodName");
        Assert.assertNull(dp);
        dp = d.getPropertyByMethodName("getValidMethodNameButInexistent");
        Assert.assertNull(dp);
    }

    /**
     * Test {@link DynaBeanDescriptor#getPropertyByMethodName(String)} with valid method name in order to get a
     * {@link DynaBeanPropertyDescriptor}.
     */
    @Test
    public void testGetPropertyByMethodNameWithValidMethodNames()
    {
        DynaBeanDescriptor<IDynaBeanDescriptorTestValidModel> d;
        DynaBeanPropertyDescriptor dp;

        d = factory.getDescriptorFor(IDynaBeanDescriptorTestValidModel.class);
        dp = d.getPropertyByMethodName("setWriteOnlyCollectionProperty");
        Assert.assertNotNull(dp);
        Assert.assertEquals("writeOnlyCollectionProperty", dp.getPropertyName());
    }
    /**
     * To test exceptions on resolve clone method.
     */
    @Test(expected=RuntimeException.class)
    public void testCloneablePropertyWithoutMethod()
    {
        factory.newDynaBean(IDynaBeanDescriptorTestCloneablePropertyWithoutMethod.class);
    }
    /**
     * To test exception on clone call.
     */
    @Test(expected=RuntimeException.class)
    public void testCloneCallException()
    {
        IDynaBeanCloneCallException d;
        
        d = factory.newDynaBean(IDynaBeanCloneCallException.class);
        d.setFieldCloneable(new CloneableClassExceptionOnCall());
        d.clone();
    }
    /**
     * To test non-string editor for clone.
     */
    @Test public void testEditorPropertyForClone()
    {
        IDynaBeanEditorPropertyForClone d1, d2;
        
        factory.getPropertyEditorRegistry().registerCustomEditor(Integer.class, new CustomNumberEditor(Integer.class, false));
        d1 = factory.newDynaBean(IDynaBeanEditorPropertyForClone.class);
        d1.setFieldInt(25);
        d2 = d1.clone();
        Assert.assertNotNull(d2.getFieldInt());
        Assert.assertEquals(d1.getFieldInt(), d2.getFieldInt());
    }
    /**
     * To test incorrect pattern string using {@link DynaBeanDateEditor}.
     */
    @Test public void testDateEditorWithErrorPattern()
    {
        IDynaBeanDateEditorWithErrorPattern d;
        
        d = factory.newDynaBean(IDynaBeanDateEditorWithErrorPattern.class);
        Assert.assertNull(d.getFieldDate());
    }
    /**
     * To test incorrect pattern string using {@link DynaBeanDateEditor}.
     */
    interface IDynaBeanDateEditorWithErrorPattern
    {
        @PropertyDefaultValue(value="abcdef", pattern="DDmmYYYY")
        public Date getFieldDate();
        public void setFieldDate(Date fieldDate);
    }
    /**
     * To test non-string editor for clone.
     */
    interface IDynaBeanEditorPropertyForClone extends Cloneable
    {
        public Integer getFieldInt();
        public void setFieldInt(Integer fieldInt);
        public IDynaBeanEditorPropertyForClone clone();
    }
    /**
     * To test exception on clone call.
     */
    interface IDynaBeanCloneCallException extends Cloneable
    {
        public CloneableClassExceptionOnCall getFieldCloneable();
        public void setFieldCloneable(CloneableClassExceptionOnCall fieldCloneable);
        public IDynaBeanCloneCallException clone();
    }
    /**
     * To test exception on clone call.
     */
    class CloneableClassExceptionOnCall implements Cloneable
    {

        @Override
        public Object clone() throws CloneNotSupportedException
        {
            throw new IllegalArgumentException();
        }
        
    }
    /**
     * To test exceptions on resolve clone method.
     */
    abstract class CloneableClassWithoutCloneMethod implements Cloneable
    {
        
    }
    /**
     * To test exceptions on resolve clone method.
     */
    interface IDynaBeanDescriptorTestCloneablePropertyWithoutMethod
    {
        public CloneableClassWithoutCloneMethod getFieldCloneable();
        public void setFieldCloneable(CloneableClassWithoutCloneMethod fieldCloneable);
    }
    /**
     * A valid model to test.
     */
    interface IDynaBeanDescriptorTestValidModel
    {
        public int getFieldInt();

        public void setFieldInt(int fieldInt);

        public int getFieldAnotherInt();

        public void setFieldAnotherInt(int fieldAnotherInt);

        public boolean isBooleanValue();

        public void setBooleanValue(boolean booleanValue);

        public String getReadOnlyStringProperty();

        public Date getFieldDate();

        public void setFieldDate(Date fieldDate);

        public String[] getArrayProperty();

        public void setArrayProperty(String[] arrayProperty);

        public List<String> getCollectionProperty();

        public void setCollectionProperty(List<String> collectionProperty);

        public String[] getReadOnlyArrayProperty();

        public void setWriteOnlyArrayProperty(String[] arrayProperty);

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
