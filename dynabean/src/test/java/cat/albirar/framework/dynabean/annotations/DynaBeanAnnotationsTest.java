/*
 * This file is part of "dynabean".
 * 
 * "dynabean" is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * "dynabean" is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with calendar. If not, see
 * <http://www.gnu.org/licenses/>.
 * 
 * Copyright (C) 2015 Octavi Fornés <ofornes@albirar.cat>
 */

package cat.albirar.framework.dynabean.annotations;

import java.beans.PropertyEditorSupport;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;
import java.util.Vector;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.util.StringUtils;

import cat.albirar.framework.dynabean.impl.DefaultDynaBeanFactory;
import cat.albirar.framework.dynabean.impl.DynaBeanDescriptorTest;
import cat.albirar.framework.dynabean.impl.IDynaBeanImplementationFactory;
import cat.albirar.framework.dynabean.impl.models.test.AnnotatedModelImpl;
import cat.albirar.framework.dynabean.impl.models.test.IAnnotatedModel;
import cat.albirar.framework.dynabean.impl.models.test.IAnnotatedParentModel;
import cat.albirar.framework.dynabean.impl.models.test.SimpleModelImpl;

/**
 * DynBean annotations tests.
 * 
 * @author <a href="mailto:ofornes@albirar.cat">Octavi Fornés ofornes@albirar.cat</a>
 * @since 2.0
 */
public class DynaBeanAnnotationsTest
{
    static final int DATE_TEST_CED_YEAR = 2012;

    static final int DATE_TEST_CED_MONTH = 10;

    static final int DATE_TEST_CED_DAY = 12;

    static final String DATE_TEST_CED = "" + DATE_TEST_CED_YEAR + "" + DATE_TEST_CED_MONTH + "" + DATE_TEST_CED_DAY;
    
    static final String PATTERN_DATE_TEST_CED = "yyyyMMdd";
    
    static final int INT_TEST_CED = 255;

    static final String ARRAY_VALUES_1 = "10";
    static final String ARRAY_VALUES_2 = "20";
    static final String ARRAY_VALUES_3 = "30";
    static final String [] ARRAY_VALUES_STRING = {
            ARRAY_VALUES_1
            ,ARRAY_VALUES_2
            ,ARRAY_VALUES_3
    };
    static final float [] ARRAY_VALUES_FLOAT = { Float.parseFloat(ARRAY_VALUES_1)
            , Float.parseFloat(ARRAY_VALUES_2)
            , Float.parseFloat(ARRAY_VALUES_3)
            };
    

    private IDynaBeanImplementationFactory factory;

    @Before
    public void initTest()
    {
        factory = new DefaultDynaBeanFactory();
    }

    /**
     * Test the {@link PropertyDefaultValue} annotation.
     */
    @Test
    public void testDefaultValues()
    {
        IAnnotatedModel model;

        model = factory.newDynaBean(IAnnotatedModel.class);
        // Assert default values
        Assert.assertTrue(model.isPending());
        Assert.assertEquals(Vector.class, model.getNamesList().getClass());
        Assert.assertEquals(IAnnotatedModel.DEFAULT_AMOUNT, model.getAmount(), 0D);
        Assert.assertArrayEquals(IAnnotatedModel.DEFAULT_OTHER_NAMES, model.getOtherNames());
        Assert.assertArrayEquals(IAnnotatedModel.DEFAULT_OTHER_AMOUNTS, model.getOtherAmounts(), 0.0D);
    }

    /**
     * Test the {@link DynaBean} autodetection feature.
     */
    @Test
    public void testDynabeanAutodetection()
    {
        IAnnotatedParentModel model;

        model = factory.newDynaBean(IAnnotatedParentModel.class);
        Assert.assertNull(model.getId());
        Assert.assertNotNull(model.getSubmodel());
        Assert.assertNotNull(model.getSubmodel().getNamesList());
        Assert.assertNull(model.getDynabeanNotAnnotatedModel());
        Assert.assertNotNull(model.getSimpleModel());
        Assert.assertEquals(SimpleModelImpl.class, model.getSimpleModel().getClass());
    }
    /**
     * Test the clone feature with annotated model.
     */
    @Test public void testClone()
    {
        IAnnotatedModel m1, m2;
        
        m1 = factory.newDynaBean(IAnnotatedModel.class);
        m2 = m1.clone();
        Assert.assertEquals(m1, m2);
        // Test clone of date
        Assert.assertNotSame(m1.getNamesList(), m2.getNamesList());
        Assert.assertNotSame(m1.getOtherAmounts(), m2.getOtherAmounts());
        Assert.assertNotSame(m1.getOtherNames(), m2.getOtherNames());
    }
    /**
     * Test the clone feature with annotated model.
     */
    @Test public void testCloneComplex()
    {
        IAnnotatedModel m1, m2;
        IAnnotatedParentModel pm1, pm2;
        
        pm1 = factory.newDynaBean(IAnnotatedParentModel.class);
        m1 = factory.newDynaBean(IAnnotatedModel.class);
        m1.getNamesList().clear();
        m1.getNamesList().add("X");
        m1.getNamesList().add("Y");
        m1.getNamesList().add("Z");
        pm1.setOtherSubmodels(new Vector<IAnnotatedModel>());
        pm1.getOtherSubmodels().add(m1);
        pm1.setId("WWW");
        
        pm2 = pm1.clone();
        
        
        Assert.assertEquals(pm1, pm2);
        Assert.assertNotSame(pm1,  pm2);
        
        Assert.assertEquals(pm1.getOtherSubmodels(), pm2.getOtherSubmodels());
        Assert.assertNotSame(pm1.getOtherSubmodels(), pm2.getOtherSubmodels());
        m2 = pm2.getOtherSubmodels().get(0);
        Assert.assertEquals(m1, m2);
        Assert.assertNotSame(m1, m2);
    }
    /**
     * Test the hashCode feature with annotated model.
     */
    @Test public void testHashsCode()
    {
        IAnnotatedModel m1, m2;
        int n;
        
        m1 = factory.newDynaBean(IAnnotatedModel.class);
        m2 = m1.clone();
        
        for(n = 0; n < 10; n++)
        {
            Assert.assertEquals(m1.hashCode(), m2.hashCode());
        }
    }
    /**
     * Test the serialization feature.
     */
    @Test public void testSerialize() throws Exception
    {
        ByteArrayOutputStream baos;
        IAnnotatedModel model, model1;
        ObjectOutputStream out;
        ByteArrayInputStream bais;
        ObjectInputStream in;
        
        // Prepare bean
        model = factory.newDynaBean(IAnnotatedModel.class);

        // Prepare stream
        baos = new ByteArrayOutputStream();
        out = new ObjectOutputStream(baos);
        out.writeObject(model);
        out.flush();
        
        // Deserialize
        bais = new ByteArrayInputStream(baos.toByteArray());
        in = new ObjectInputStream(bais);
        model1 = (IAnnotatedModel)in.readObject();
        
        // Test if equals
        Assert.assertNotNull(model1);
        Assert.assertEquals(model, model1);
        Assert.assertNotSame(model, model1);
    }
    /**
     * Test the clone with explicit bean implementation.
     */
    @Test public void testMixedEquals()
    {
        IAnnotatedModel m1, m2;
        List<String> names;
        double [] oamount = {10.5D, 125.223D, 998.66271D};
        
        m2 = factory.newDynaBean(IAnnotatedModel.class);
        m1 = new AnnotatedModelImpl();
        m1.setId("XX");
        m1.setAmount(IAnnotatedModel.DEFAULT_AMOUNT + 2.0D);
        names = new Vector<String>();
        names.add("N1");
        names.add("N2");
        names.add("N3");
        m1.setNamesList(names);
        m1.setPending(true);
        names.clear();
        names.add("ON1");
        names.add("ON2");
        names.add("ON3");
        names.add("ON4");
        m1.setOtherNames(names.toArray(new String[]{}));
        m1.setOtherAmounts(oamount);

        m2.setId(m1.getId());
        m2.setAmount(m1.getAmount());
        m2.setNamesList(cloneList(m1.getNamesList()));
        m2.setOtherAmounts(oamount);
        m2.setOtherNames(names.toArray(new String []{}));
        
        Assert.assertTrue(m2.equals(m1));
        m2.setOtherNames(new String[]{"A","B","C"});
        Assert.assertFalse(m2.equals(m1));
    }
    /**
     * Test for {@link DynaBean} annotated property with {@link IWrongUseOfDynaBeanAnnotationModelA}.
     */
    @Test(expected=IllegalArgumentException.class) 
    public void testAnnotatedPropertyErrorModelA()
    {
        factory.newDynaBean(IWrongUseOfDynaBeanAnnotationModelA.class);
    }
    /**
     * Test for {@link DynaBean} annotated property with {@link IWrongUseOfDynaBeanAnnotationModelB}.
     */
    @Test(expected=IllegalArgumentException.class) 
    public void testAnnotatedPropertyErrorModelB()
    {
        factory.newDynaBean(IWrongUseOfDynaBeanAnnotationModelB.class);
    }
    /**
     * Test for {@link DynaBean} annotated property with {@link IWrongUseOfDynaBeanAnnotationModelC}.
     */
    @Test(expected=IllegalArgumentException.class) 
    public void testAnnotatedPropertyErrorModelC()
    {
        factory.newDynaBean(IWrongUseOfDynaBeanAnnotationModelC.class);
    }
    /**
     * Test for {@link DynaBean} annotated property with {@link IWrongUseOfDynaBeanAnnotationModelD}.
     */
    @Test(expected=IllegalArgumentException.class) 
    public void testAnnotatedPropertyErrorModelD()
    {
        factory.newDynaBean(IWrongUseOfDynaBeanAnnotationModelD.class);
    }
    /**
     * Test for {@link DynaBean} annotated property with {@link IWrongUseOfDynaBeanAnnotationModelE}.
     */
    @Test(expected=IllegalArgumentException.class) 
    public void testAnnotatedPropertyErrorModelE()
    {
        factory.newDynaBean(IWrongUseOfDynaBeanAnnotationModelE.class);
    }
    /**
     * Test if a {@link DynaBean} annotated implementation interface is detected.
     */
    @Test public void testAnnotatedPropertyDynBeanImplementation()
    {
        IDynaBeanExplicitDynaBeanProperty d;
        
        d = factory.newDynaBean(IDynaBeanExplicitDynaBeanProperty.class);
        Assert.assertNotNull(d.getModel());
    }
    /**
     * Test use of editors by item type. This test case sets a property editor by item type for {@link java.util.Date}.
     * Gets or set the property for {@link IDynaBeanDescriptorTestValidModel#setFieldDate(Date)} and check if value is
     * correctly assigned.
     */
    @Test
    public void testPropertyEditorByItemType()
    {
        IDynaBeanDescriptorDates model;
        Calendar date;
        CustomDateEditor cde;
        
        cde = new CustomDateEditor(new SimpleDateFormat(PATTERN_DATE_TEST_CED), false);
        factory.getPropertyEditorRegistry().registerCustomEditor(Date.class, null, cde);

        model = factory.newDynaBean(IDynaBeanDescriptorDates.class);
        Assert.assertNotNull(model.getAnotherFieldDate());
        // Data should to be DATE_TEST_CED
        date = Calendar.getInstance();
        date.setTimeInMillis(model.getFieldDate().getTime());
        Assert.assertEquals(DATE_TEST_CED_DAY, date.get(Calendar.DATE));
        Assert.assertEquals(DATE_TEST_CED_MONTH, date.get(Calendar.MONTH) + 1);
        Assert.assertEquals(DATE_TEST_CED_YEAR, date.get(Calendar.YEAR));
    }

    /**
     * Test use of automatic editor created with the values if {@link PropertyDefaultValue} annotation.
     */
    @Test
    public void testPropertyAutoEditorDateAndCalendar()
    {
        IDynaBeanDescriptorDates model;
        Calendar date;

        model = factory.newDynaBean(IDynaBeanDescriptorDates.class);
        Assert.assertNotNull(model.getFieldDate());
        // Data should to be DATE_TEST_CED
        date = Calendar.getInstance();
        date.setTimeInMillis(model.getFieldDate().getTime());
        Assert.assertEquals(DATE_TEST_CED_DAY, date.get(Calendar.DATE));
        Assert.assertEquals(DATE_TEST_CED_MONTH, date.get(Calendar.MONTH) + 1);
        Assert.assertEquals(DATE_TEST_CED_YEAR, date.get(Calendar.YEAR));

        Assert.assertNotNull(model.getFieldCalendar());
        // Data should to be DATE_TEST_CED
        date = Calendar.getInstance();
        date.setTimeInMillis(model.getFieldCalendar().getTimeInMillis());
        Assert.assertEquals(DATE_TEST_CED_DAY, date.get(Calendar.DATE));
        Assert.assertEquals(DATE_TEST_CED_MONTH, date.get(Calendar.MONTH) + 1);
        Assert.assertEquals(DATE_TEST_CED_YEAR, date.get(Calendar.YEAR));
    }

    /**
     * Test use of editors by path. This test case sets a property editor by path for
     * {@link IDynaBeanDescriptorTestValidModel#setFieldInt(int)}. The property editor multiply by two the original
     * value to be assigned Gets or set the property for {@link IDynaBeanDescriptorTestValidModel#setFieldInt(int)} and
     * check if value is correctly assigned as made by editor. Gets or set the property for
     * {@link IDynaBeanDescriptorTestValidModel#setFieldAnotherInt(int)} and check if value is correctly assigned
     * without the modifications made by editor.
     */
    @Test
    public void testPropertyEditorByPath()
    {
        IDynaBeanDescriptorDates model;
        
        factory.getPropertyEditorRegistry().registerCustomEditor(int.class, IDynaBeanDescriptorDates.class.getName() + ".fieldAnotherInt", new CustomEditorForInteger());
        model = factory.newDynaBean(IDynaBeanDescriptorDates.class);
        Assert.assertEquals(INT_TEST_CED * 2, model.getFieldAnotherInt());
    }

    @Test public void testNoPropertyEditorFound()
    {
        IDynaBeanDescriptorDates model;
        
        model = factory.newDynaBean(IDynaBeanDescriptorDates.class);
        Assert.assertEquals(INT_TEST_CED, model.getFieldAnotherInt());
        Assert.assertNotNull(model.getFieldDate());
    }
    /**
     * Test for annotated {@link PropertyDefaultValue} property array.
     */
    @Test public void testAnnotatedPropertyArray()
    {
        IAnnotatedPropertyArray d;
        
        d = factory.newDynaBean(IAnnotatedPropertyArray.class);
        Assert.assertNotNull(d.getFieldArrayFloat());
        Assert.assertArrayEquals(ARRAY_VALUES_FLOAT, d.getFieldArrayFloat(), 0.0F);
    }
    /**
     * Test default instantiation of collection property without default implementation. 
     */
    @Test public void testDynaBeanWithCollectionWithoutDefaultImplementation()
    {
        IDynaBeanWithCollectionWithoutDefaultImplementation d;
        int n;
        
        d = factory.newDynaBean(IDynaBeanWithCollectionWithoutDefaultImplementation.class);
        Assert.assertNotNull(d.getFieldList());
        Assert.assertEquals(ARRAY_VALUES_STRING.length, d.getFieldList().size());
        for(n = 0; n < ARRAY_VALUES_STRING.length; n++)
        {
            Assert.assertEquals(ARRAY_VALUES_STRING[n], d.getFieldList().get(n));
        }
    }
    
    interface IDynaBeanWithCollectionWithoutDefaultImplementation
    {
        @PropertyDefaultValue(value = {ARRAY_VALUES_1, ARRAY_VALUES_2, ARRAY_VALUES_3})
        public List<String> getFieldList();
        public void setFieldList(List<String> fieldList);
    }
    /**
     * Test for annotated {@link PropertyDefaultValue} property with a {@link PropertyDefaultValue#implementation()} without constructor.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testClassWithoutConstructor()
    {
        factory.newDynaBean(IDynaBeanDefaultImplementationError.class);
    }
    /**
     * For test default error on instantiate.
     */
    interface IClassWithoutConstructor
    {
        public int getFieldInt();
        public void setFieldInt(int fieldInt);
    }
    /**
     * For test default error on instantiate.
     */
    class ClassWithoutConstructor implements IClassWithoutConstructor
    {

        @Override
        public int getFieldInt()
        {
            return 0;
        }

        @Override
        public void setFieldInt(int fieldInt)
        {
        }
    }
    /**
     * For test default error on instantiate.
     */
    interface IDynaBeanDefaultImplementationError
    {
        @PropertyDefaultValue(implementation=ClassWithoutConstructor.class)
        public IClassWithoutConstructor getFieldClass();
        public void setFieldClass(IClassWithoutConstructor fieldClass);
    }
    /** To test detection of implementation dynabean annotated interface. */
    interface IDynaBeanExplicitDynaBeanProperty
    {
        @PropertyDefaultValue(implementation=IAnnotatedModel.class)
        public IAnnotatedModel getModel();
        public void setModel(IAnnotatedModel model);
    }
    /**
     * For test {@link DynaBeanDescriptorTest#testPropertyEditorByItemType()} and
     * {@link DynaBeanDescriptorTest#testPropertyEditorByPath()}.
     */
    interface IDynaBeanDescriptorDates
    {
        public int getFieldAnotherInt();
        @PropertyDefaultValue("" + INT_TEST_CED)
        public void setFieldAnotherInt(int fieldAnotherInt);

        @PropertyDefaultValue(value=DATE_TEST_CED, pattern=PATTERN_DATE_TEST_CED)
        public Date getFieldDate();
        public void setFieldDate(Date fieldDate);

        @PropertyDefaultValue(value=DATE_TEST_CED, pattern=PATTERN_DATE_TEST_CED)
        public Calendar getFieldCalendar();
        public void setFieldCalendar(Calendar fieldDate);

        @PropertyDefaultValue(value=DATE_TEST_CED)
        public Date getAnotherFieldDate();

        public void setAnotherFieldDate(Date fieldDate);
    }

    /**
     * For test {@link DynaBeanDescriptorTest#testPropertyEditorByPath()}.
     */
    class CustomEditorForInteger extends PropertyEditorSupport
    {
        @Override
        public String getAsText()
        {
            if(getValue() != null)
            {
                return Integer.toString((Integer) getValue());
            }
            return "0";
        }

        @Override
        public void setAsText(String text) throws IllegalArgumentException
        {
            int n;
            
            if(StringUtils.hasText(text))
            {
                try
                {
                    n = Integer.parseInt(text);
                }
                catch(NumberFormatException e)
                {
                    n = 0;
                }
            }
            else
            {
                n = 0;
            }
            setValue(Integer.valueOf(n * 2));
        }
    }
    /** To check detection of wrong use of annotations. */
    interface IWrongUseOfDynaBeanAnnotationModelA
    {
        public int getIntField();
        public void setIntField(int intField);
        
        @DynaBean(defaultInstantiate=true)
        public TreeMap<String, String> getMapProperty();
        public void setMapProperty(TreeMap<String, String> mapProperty);
        
        @PropertyDefaultValue()
        public String getStringField();
        public void setStringField(String stringField);
    }
    /** To check detection of wrong use of annotations. */
    interface IWrongUseOfDynaBeanAnnotationModelB
    {
        public int getIntField();
        public void setIntField(int intField);
        
        public TreeMap<String, String> getMapProperty();
        public void setMapProperty(TreeMap<String, String> mapProperty);
        
        @PropertyDefaultValue()
        public String getStringField();
        public void setStringField(String stringField);
    }
    /** To check detection of wrong use of annotations. */
    interface IWrongUseOfDynaBeanAnnotationModelC
    {
        public int getIntField();
        public void setIntField(int intField);
        
        @PropertyDefaultValue(implementation=List.class)
        public TreeMap<String, String> getMapProperty();
        public void setMapProperty(TreeMap<String, String> mapProperty);
        
    }
    /** To check detection of wrong use of annotations. */
    interface IWrongUseOfDynaBeanAnnotationModelD
    {
        public int getIntField();
        public void setIntField(int intField);
        
        @PropertyDefaultValue(implementation=AbstractMap.class)
        public TreeMap<String, String> getMapProperty();
        public void setMapProperty(TreeMap<String, String> mapProperty);
        
    }
    /** To check detection of wrong use of annotations. */
    interface IWrongUseOfDynaBeanAnnotationModelE
    {
        public int getIntField();
        public void setIntField(int intField);
        
        @PropertyDefaultValue(value="123", pattern="RRR")
        public TreeMap<String, String> getMapProperty();
        public void setMapProperty(TreeMap<String, String> mapProperty);
        
    }
    interface IAnnotatedPropertyArray
    {
        @PropertyDefaultValue(value={ ARRAY_VALUES_1, ARRAY_VALUES_2, ARRAY_VALUES_3})
        public float [] getFieldArrayFloat();
        public void setFieldArrayFloat(float [] fieldArrayFloat);

        @PropertyDefaultValue(value={ ARRAY_VALUES_1, ARRAY_VALUES_2, ARRAY_VALUES_3})
        public String [] getFieldArrayString();
        public void setFieldArrayString(String [] fieldArrayString);
    }
    /**
     * Clones a list.
     * @param list The list
     * @return The cloned list
     */
    private <T> List<T> cloneList(List<T> list)
    {
        List<T> dest;
        
        dest = new Vector<T>();
        for(T e : list)
        {
            dest.add(e);
        }
        return dest;
    }
}
