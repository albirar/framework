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

package cat.albirar.framework.sets.tsb.impl;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import cat.albirar.framework.sets.ISet;
import cat.albirar.framework.sets.impl.models.ITestModelAllPrimitives;
import cat.albirar.framework.sets.impl.models.ITestModelAllPrimitivesArray;
import cat.albirar.framework.sets.impl.models.ITestModelErrorMethods;
import cat.albirar.framework.sets.impl.models.ITestModelRoot;
import cat.albirar.framework.sets.impl.models.ITestModelRootMixInterfaceClass;
import cat.albirar.framework.sets.impl.models.TestModelRoot;
import cat.albirar.framework.sets.impl.models.TestModelRootMixInterfaceClass;
import cat.albirar.framework.sets.tsb.ITypeSafeBuilder;

/**
 * Test for {@link TypeSafeBuilderDefaultImpl}.
 * @author Octavi Fornés ofornes@albirar.cat
 * @since 2.1.0
 */
public class TypeSafeBuilderDefaultImplTest
{
    /**
     * Test {@link TypeSafeBuilderDefaultImpl#TypeSafeBuilderDefaultImpl(Class)} with null argument.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testRootModelNull()
    {
        new TypeSafeBuilderDefaultImpl<Date>(null);
    }
    /**
     * Test for composition with a wrong type (not a javaBean convention method).
     */
    @Test(expected=IllegalArgumentException.class)
    public void testCompositionNonJavaBeanConventionMethod()
    {
        ITypeSafeBuilder<Date> tsb;
        
        tsb = new TypeSafeBuilderDefaultImpl<Date>(Date.class);
        tsb.addProperty(tsb.getModel().toString());
    }
    /**
     * Test for composition with a wrong type (not a javaBean convention method).
     */
    @Test(expected=IllegalArgumentException.class)
    public void testAbstractModel()
    {
        new TypeSafeBuilderDefaultImpl<Number>(Number.class);
    }
    /**
     * Test for composition with {@link TestModelRoot} concrete class but no proxied as assistant.
     */
    @Test public void testCompositionNotProxiedModelEmptySet()
    {
        ITypeSafeBuilder<TestModelRoot> tsb;
        ISet set;
        TestModelRoot model;
        
        tsb = new TypeSafeBuilderDefaultImpl<TestModelRoot>(TestModelRoot.class);
        model = new TestModelRoot();
        tsb.addProperty(model.getIntProperty());
        tsb.addProperty(model.getStringProperty());
        tsb.addProperty(model.getDateProperty());
        set = tsb.build();
        Assert.assertNotNull(set);
        Assert.assertTrue(set.isEmpty());
    }
    /**
     * Test with a {@link ITestModelErrorMethods model} with errors. 
     */
    @Test(expected=IllegalArgumentException.class)
    public void testModelErrorMethods1()
    {
        ITypeSafeBuilder<ITestModelErrorMethods> tsb;
        
        tsb = new TypeSafeBuilderDefaultImpl<ITestModelErrorMethods>(ITestModelErrorMethods.class);
        tsb.addProperty(tsb.getModel().get());
    }
    /**
     * Test with a {@link ITestModelErrorMethods model} with errors. 
     */
    @Test(expected=IllegalArgumentException.class)
    public void testModelErrorMethods2()
    {
        ITypeSafeBuilder<ITestModelErrorMethods> tsb;
        
        tsb = new TypeSafeBuilderDefaultImpl<ITestModelErrorMethods>(ITestModelErrorMethods.class);
        tsb.addProperty(tsb.getModel().getInt("1"));
    }
    /**
     * Test with a {@link ITestModelErrorMethods model} with errors. 
     */
    @Test(expected=IllegalArgumentException.class)
    public void testModelErrorMethods3()
    {
        ITypeSafeBuilder<ITestModelErrorMethods> tsb;
        
        tsb = new TypeSafeBuilderDefaultImpl<ITestModelErrorMethods>(ITestModelErrorMethods.class);
        tsb.addProperty(tsb.getModel().isNotBoolean());
    }
    /**
     * Test with a {@link ITestModelErrorMethods model} with errors. 
     */
    @Test(expected=IllegalArgumentException.class)
    public void testModelErrorMethods4()
    {
        ITypeSafeBuilder<ITestModelErrorMethods> tsb;
        
        tsb = new TypeSafeBuilderDefaultImpl<ITestModelErrorMethods>(ITestModelErrorMethods.class);
        tsb.addProperty(tsb.getModel().isProperties("prop"));
    }
    /**
     * Test with a {@link ITestModelErrorMethods model} with errors. 
     */
    @Test(expected=IllegalArgumentException.class)
    public void testModelErrorMethods5()
    {
        ITypeSafeBuilder<ITestModelErrorMethods> tsb;
        
        tsb = new TypeSafeBuilderDefaultImpl<ITestModelErrorMethods>(ITestModelErrorMethods.class);
        tsb.addProperty(tsb.getModel().is());
    }
    /**
     * Test with a {@link ITestModelErrorMethods model} with errors. 
     */
    @Test(expected=IllegalArgumentException.class)
    public void testModelErrorMethods6()
    {
        ITypeSafeBuilder<ITestModelErrorMethods> tsb;
        
        tsb = new TypeSafeBuilderDefaultImpl<ITestModelErrorMethods>(ITestModelErrorMethods.class);
        tsb.getModel().getString();
    }
    /**
     * Test with a {@link ITestModelErrorMethods model} with errors. 
     */
    @Test(expected=IllegalArgumentException.class)
    public void testModelErrorMethods7()
    {
        ITypeSafeBuilder<ITestModelErrorMethods> tsb;
        
        tsb = new TypeSafeBuilderDefaultImpl<ITestModelErrorMethods>(ITestModelErrorMethods.class);
        tsb.addProperty(tsb.getModel().getNonInstantiable());
    }
    /**
     * Test with a {@link ITestModelErrorMethods model} with errors. 
     */
    @Test(expected=IllegalArgumentException.class)
    public void testModelErrorMethods8()
    {
        ITypeSafeBuilder<ITestModelErrorMethods> tsb;
        
        tsb = new TypeSafeBuilderDefaultImpl<ITestModelErrorMethods>(ITestModelErrorMethods.class);
        tsb.addProperty(tsb.getModel().getAbstract());
    }
    /**
     * Test with {@link ITestModelAllPrimitives} type.
     */
    @Test public void testCompositionAllTypes()
    {
        ITypeSafeBuilder<ITestModelAllPrimitives> tsb;
        ISet set;
        
        tsb = new TypeSafeBuilderDefaultImpl<ITestModelAllPrimitives>(ITestModelAllPrimitives.class);
        tsb.addProperty(tsb.getModel().getByte());
        tsb.addProperty(tsb.getModel().isBoolean());
        tsb.addProperty(tsb.getModel().getChar());
        tsb.addProperty(tsb.getModel().getShort());
        tsb.addProperty(tsb.getModel().getInt());
        tsb.addProperty(tsb.getModel().getLong());
        tsb.addProperty(tsb.getModel().getFloat());
        tsb.addProperty(tsb.getModel().getDouble());
        tsb.addProperty(tsb.getModel().getString());
        tsb.addProperty(tsb.getModel().getObject());
        tsb.addProperty(tsb.getModel().getModel());
        tsb.addProperty(tsb.getModel().getEnum());
        
        set = tsb.build();
        Assert.assertNotNull(set);
        Assert.assertFalse(set.isEmpty());
        Assert.assertEquals(12, set.size());
        Assert.assertTrue(set.contains("byte"));
        Assert.assertTrue(set.contains("boolean"));
        Assert.assertTrue(set.contains("char"));
        Assert.assertTrue(set.contains("short"));
        Assert.assertTrue(set.contains("int"));
        Assert.assertTrue(set.contains("long"));
        Assert.assertTrue(set.contains("float"));
        Assert.assertTrue(set.contains("double"));
        Assert.assertTrue(set.contains("string"));
        Assert.assertTrue(set.contains("object"));
        Assert.assertTrue(set.contains("model"));
        Assert.assertTrue(set.contains("enum"));
    }
    /**
     * Test with {@link ITestModelAllPrimitivesArray} type.
     */
    @Test public void testCompositionAllTypesArray()
    {
        ITypeSafeBuilder<ITestModelAllPrimitivesArray> tsb;
        ISet set;
        
        tsb = new TypeSafeBuilderDefaultImpl<ITestModelAllPrimitivesArray>(ITestModelAllPrimitivesArray.class);
        tsb.addProperty(tsb.getModel().getByte());
        tsb.addProperty(tsb.getModel().getBoolean());
        tsb.addProperty(tsb.getModel().getChar());
        tsb.addProperty(tsb.getModel().getShort());
        tsb.addProperty(tsb.getModel().getInt());
        tsb.addProperty(tsb.getModel().getLong());
        tsb.addProperty(tsb.getModel().getFloat());
        tsb.addProperty(tsb.getModel().getDouble());
        tsb.addProperty(tsb.getModel().getString());
        tsb.addProperty(tsb.getModel().getObject());
        tsb.addProperty(tsb.getModel().getModel());
        tsb.addProperty(tsb.getModel().getEnum());
        
        set = tsb.build();
        Assert.assertNotNull(set);
        Assert.assertFalse(set.isEmpty());
        Assert.assertEquals(12, set.size());
        Assert.assertTrue(set.contains("byte"));
        Assert.assertTrue(set.contains("boolean"));
        Assert.assertTrue(set.contains("char"));
        Assert.assertTrue(set.contains("short"));
        Assert.assertTrue(set.contains("int"));
        Assert.assertTrue(set.contains("long"));
        Assert.assertTrue(set.contains("float"));
        Assert.assertTrue(set.contains("double"));
        Assert.assertTrue(set.contains("string"));
        Assert.assertTrue(set.contains("object"));
        Assert.assertTrue(set.contains("model"));
        Assert.assertTrue(set.contains("enum"));
    }
    /**
     * Test for composition with {@link TestModelRoot} concrete class and only one level.
     */
    @Test public void testCompositionFirstLevelWithClass()
    {
        ITypeSafeBuilder<TestModelRoot> tsb;
        ISet set;
        
        tsb = new TypeSafeBuilderDefaultImpl<TestModelRoot>(TestModelRoot.class);
        tsb.addProperty(tsb.getModel().getIntProperty());
        tsb.addProperty(tsb.getModel().getStringProperty());
        tsb.addProperty(tsb.getModel().getDateProperty());
        
        set = tsb.build();
        Assert.assertNotNull(set);
        Assert.assertFalse(set.isEmpty());
        Assert.assertEquals(3, set.size());
        Assert.assertTrue(set.contains("intProperty"));
        Assert.assertTrue(set.contains("stringProperty"));
        Assert.assertTrue(set.contains("dateProperty"));
    }
    /**
     * Test for composition with {@link ITestModelRoot} interface and only one level.
     */
    @Test public void testCompositionFirstLevelWithInterface()
    {
        ITypeSafeBuilder<ITestModelRoot> tsb;
        ISet set;
        
        tsb = new TypeSafeBuilderDefaultImpl<ITestModelRoot>(ITestModelRoot.class);
        tsb.addProperty(tsb.getModel().getIntProperty());
        tsb.addProperty(tsb.getModel().getStringProperty());
        tsb.addProperty(tsb.getModel().getDateProperty());
        
        set = tsb.build();
        Assert.assertNotNull(set);
        Assert.assertFalse(set.isEmpty());
        Assert.assertEquals(3, set.size());
        Assert.assertTrue(set.contains("intProperty"));
        Assert.assertTrue(set.contains("stringProperty"));
        Assert.assertTrue(set.contains("dateProperty"));
    }
    /**
     * Test for composition with {@link TestModelRoot} concrete class and deep level.
     */
    @Test public void testCompositionDeepWithClass()
    {
        ITypeSafeBuilder<TestModelRoot> tsb;
        ISet set;
        
        tsb = new TypeSafeBuilderDefaultImpl<TestModelRoot>(TestModelRoot.class);
        tsb.addProperty(tsb.getModel().getSecondLevelModelProperty().getThirdLevel2ModelProperty().getInt3Property());
        tsb.addProperty(tsb.getModel().getSecondLevelModelProperty().getString2Property());
        tsb.addProperty(tsb.getModel().getThirdLevelModelProperty());
        
        set = tsb.build();
        Assert.assertNotNull(set);
        Assert.assertFalse(set.isEmpty());
        Assert.assertEquals(3, set.size());
        Assert.assertTrue(set.contains("secondLevelModelProperty.thirdLevel2ModelProperty.int3Property"));
        Assert.assertTrue(set.contains("secondLevelModelProperty.string2Property"));
        Assert.assertTrue(set.contains("thirdLevelModelProperty"));
    }
    /**
     * Test for composition with {@link TestModelRoot} concrete class and deep level.
     */
    @Test public void testCompositionDeepWithInterface()
    {
        ITypeSafeBuilder<ITestModelRoot> tsb;
        ISet set;
        
        tsb = new TypeSafeBuilderDefaultImpl<ITestModelRoot>(ITestModelRoot.class);
        tsb.addProperty(tsb.getModel().getSecondLevelModelProperty().getThirdLevel2ModelProperty().getInt3Property());
        tsb.addProperty(tsb.getModel().getSecondLevelModelProperty().getString2Property());
        tsb.addProperty(tsb.getModel().getThirdLevelModelProperty());
        
        set = tsb.build();
        Assert.assertNotNull(set);
        Assert.assertFalse(set.isEmpty());
        Assert.assertEquals(3, set.size());
        Assert.assertTrue(set.contains("secondLevelModelProperty.thirdLevel2ModelProperty.int3Property"));
        Assert.assertTrue(set.contains("secondLevelModelProperty.string2Property"));
        Assert.assertTrue(set.contains("thirdLevelModelProperty"));
    }
    /**
     * Test for composition with mixed case of interface and concrete class models, and deep level.
     */
    @Test public void testCompositionDeepWithMixedInterfaceClass()
    {
        ITypeSafeBuilder<TestModelRootMixInterfaceClass> tsbRootClass;
        ITypeSafeBuilder<ITestModelRootMixInterfaceClass> tsbRootInterface;
        ISet set;
        
        tsbRootClass = new TypeSafeBuilderDefaultImpl<TestModelRootMixInterfaceClass>(TestModelRootMixInterfaceClass.class);
        tsbRootClass.addProperty(tsbRootClass.getModel().getSecondLevelModelProperty().getThirdLevel2ModelProperty().getTestModelSecondLevel3Property().getInt2Property());
        tsbRootClass.addProperty(tsbRootClass.getModel().getSecondLevelModelProperty().getString2Property());
        tsbRootClass.addProperty(tsbRootClass.getModel().getThirdLevelModelProperty());
        
        set = tsbRootClass.build();
        Assert.assertNotNull(set);
        Assert.assertFalse(set.isEmpty());
        Assert.assertEquals(3, set.size());
        Assert.assertTrue(set.contains("secondLevelModelProperty.thirdLevel2ModelProperty.testModelSecondLevel3Property.int2Property"));
        Assert.assertTrue(set.contains("secondLevelModelProperty.string2Property"));
        Assert.assertTrue(set.contains("thirdLevelModelProperty"));
        
        tsbRootInterface = new TypeSafeBuilderDefaultImpl<ITestModelRootMixInterfaceClass>(ITestModelRootMixInterfaceClass.class);
        tsbRootInterface.addProperty(tsbRootInterface.getModel().getSecondLevelModelProperty().getThirdLevel2ModelProperty().getTestModelSecondLevel3Property().getInt2Property());
        tsbRootInterface.addProperty(tsbRootInterface.getModel().getSecondLevelModelProperty().getString2Property());
        tsbRootInterface.addProperty(tsbRootInterface.getModel().getThirdLevelModelProperty());
        
        set = tsbRootInterface.build();
        Assert.assertNotNull(set);
        Assert.assertFalse(set.isEmpty());
        Assert.assertEquals(3, set.size());
        Assert.assertTrue(set.contains("secondLevelModelProperty.thirdLevel2ModelProperty.testModelSecondLevel3Property.int2Property"));
        Assert.assertTrue(set.contains("secondLevelModelProperty.string2Property"));
        Assert.assertTrue(set.contains("thirdLevelModelProperty"));
    }
}
