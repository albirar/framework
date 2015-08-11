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
package cat.albirar.framework.dynabean.impl;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import cat.albirar.framework.dynabean.DynaBeanUtils;
import cat.albirar.framework.dynabean.impl.models.test.EGender;
import cat.albirar.framework.dynabean.impl.models.test.IIncorrectModel;
import cat.albirar.framework.dynabean.impl.models.test.IModel;
import cat.albirar.framework.dynabean.impl.models.test.INonCloneableModel;
import cat.albirar.framework.dynabean.impl.models.test.INonCloneablePropertiesModel;
import cat.albirar.framework.dynabean.impl.models.test.ISimpleModel;
import cat.albirar.framework.dynabean.impl.models.test.ModelImpl;

/**
 * Test of {@link DynaBeanImpl}.
 * 
 * @author <a href="mailto:ofornes@albirar.cat">Octavi Fornés ofornes@albirar.cat</a>
 * @since 1.0.0
 */
public class DynaBeanImplTest
{
    /** Test value for id prop. */
    private static final long ID_VALUE = 346112L;
	/** Test value for name prop. */
	private static final String NAME_VALUE = "NAME";
	/** Test value for last name prop. */
	private static final String LAST_NAME_VALUE = "LAST_NAME";
	private static final Date BIRTHDATE_VALUE;
    /** Test value for number of children prop. */
	private static final int NUMBER_OF_CHILDREN_VALUE = 3;
	/** Test value for incoming year prop. */
	private static final double INCOMING_YEAR_VALUE = 32056.12D;
	/** Test value for gender prop. */
	private static final EGender GENDER_VALUE = EGender.Female;
	
	private static final short SHORT_VALUE = (short)55;
	
	private static final char CHAR_VALUE = 'x';

	private static final boolean BOOLEAN_VALUE = true;
	
	private static final float FLOAT_VALUE = 45F;

	private static final String [] NAMES_VALUES = new String [] { "VALUE1", "VALUE2", "VALUE3", "VALUE4"};
	/*
	 * Initialize instance
	 */
	static 
	{
	    Calendar c;
	    
	    c = Calendar.getInstance();
	    c.set(Calendar.YEAR, 1964);
	    c.set(Calendar.MONTH, Calendar.NOVEMBER);
	    c.set(Calendar.DATE, 10);
	    c.set(Calendar.HOUR, 0);
	    c.set(Calendar.MINUTE, 0);
	    c.set(Calendar.SECOND, 0);
	    c.set(Calendar.MILLISECOND, 0);
	    BIRTHDATE_VALUE = c.getTime();
	}
	/**
	 * Test the 'getters' and 'setters' features
	 */
	@Test public void testGettersSettersModelCorrect()
	{
		IModel m;
		
		m = DynaBeanUtils.instanceDefaultFactory().newDynaBean(IModel.class);
		Assert.assertEquals(0L, m.getId());
		Assert.assertNull(m.getName());
		Assert.assertNull(m.getLastName());
		Assert.assertNull(m.getBirthDate());
        Assert.assertEquals(0,m.getNumberOfChildren());
        Assert.assertEquals(0.0D,m.getIncomingYear(),0.0D);
		Assert.assertNull(m.getGender());
		Assert.assertNull(m.getNames());
        Assert.assertEquals(0, m.getShortProp());
        Assert.assertEquals('\0', m.getCharProp());
		Assert.assertFalse(m.isBooleanProp());
        Assert.assertEquals(0.0F,m.getTaxesYear(),0.0F);
		
		m.setId(ID_VALUE);
        Assert.assertEquals(ID_VALUE, m.getId());
        Assert.assertNull(m.getName());
        Assert.assertNull(m.getLastName());
        Assert.assertNull(m.getBirthDate());
        Assert.assertEquals(0,m.getNumberOfChildren());
        Assert.assertEquals(0.0D,m.getIncomingYear(),0.0D);
        Assert.assertNull(m.getGender());
        Assert.assertNull(m.getNames());
        Assert.assertEquals(0, m.getShortProp());
        Assert.assertEquals('\0', m.getCharProp());
        Assert.assertFalse(m.isBooleanProp());
        Assert.assertEquals(0.0F,m.getTaxesYear(),0.0F);
		
		m.setName(NAME_VALUE);
        Assert.assertEquals(ID_VALUE, m.getId());
		Assert.assertEquals(NAME_VALUE, m.getName());
        Assert.assertNull(m.getLastName());
        Assert.assertNull(m.getBirthDate());
        Assert.assertEquals(0,m.getNumberOfChildren());
        Assert.assertEquals(0.0D,m.getIncomingYear(),0.0D);
        Assert.assertNull(m.getGender());
        Assert.assertNull(m.getNames());
        Assert.assertEquals(0, m.getShortProp());
        Assert.assertEquals('\0', m.getCharProp());
        Assert.assertFalse(m.isBooleanProp());
        Assert.assertEquals(0.0F,m.getTaxesYear(),0.0F);
		
		m.setLastName(LAST_NAME_VALUE);
        Assert.assertEquals(ID_VALUE, m.getId());
        Assert.assertEquals(NAME_VALUE, m.getName());
        Assert.assertEquals(LAST_NAME_VALUE, m.getLastName());
        Assert.assertNull(m.getBirthDate());
        Assert.assertEquals(0,m.getNumberOfChildren());
        Assert.assertEquals(0.0D,m.getIncomingYear(),0.0D);
        Assert.assertNull(m.getGender());
        Assert.assertNull(m.getNames());
        Assert.assertEquals(0, m.getShortProp());
        Assert.assertEquals('\0', m.getCharProp());
        Assert.assertFalse(m.isBooleanProp());
        Assert.assertEquals(0.0F,m.getTaxesYear(),0.0F);
		
		m.setBirthDate(BIRTHDATE_VALUE);
        Assert.assertEquals(ID_VALUE, m.getId());
        Assert.assertEquals(NAME_VALUE, m.getName());
        Assert.assertEquals(LAST_NAME_VALUE, m.getLastName());
		Assert.assertEquals(new Date(BIRTHDATE_VALUE.getTime()), m.getBirthDate());
        Assert.assertEquals(0,m.getNumberOfChildren());
        Assert.assertEquals(0.0D,m.getIncomingYear(),0.0D);
        Assert.assertNull(m.getGender());
        Assert.assertNull(m.getNames());
        Assert.assertEquals(0, m.getShortProp());
        Assert.assertEquals('\0', m.getCharProp());
        Assert.assertFalse(m.isBooleanProp());
        Assert.assertEquals(0.0F,m.getTaxesYear(),0.0F);

        m.setNumberOfChildren(NUMBER_OF_CHILDREN_VALUE);
        Assert.assertEquals(ID_VALUE, m.getId());
        Assert.assertEquals(NAME_VALUE, m.getName());
        Assert.assertEquals(LAST_NAME_VALUE, m.getLastName());
        Assert.assertEquals(new Date(BIRTHDATE_VALUE.getTime()), m.getBirthDate());
        Assert.assertEquals(NUMBER_OF_CHILDREN_VALUE,m.getNumberOfChildren());
        Assert.assertEquals(0.0D,m.getIncomingYear(),0.0D);
        Assert.assertNull(m.getGender());
        Assert.assertNull(m.getNames());
        Assert.assertEquals(0, m.getShortProp());
        Assert.assertEquals('\0', m.getCharProp());
        Assert.assertFalse(m.isBooleanProp());
        Assert.assertEquals(0.0F,m.getTaxesYear(),0.0F);

        m.setIncomingYear(INCOMING_YEAR_VALUE);
        Assert.assertEquals(ID_VALUE, m.getId());
        Assert.assertEquals(NAME_VALUE, m.getName());
        Assert.assertEquals(LAST_NAME_VALUE, m.getLastName());
        Assert.assertEquals(new Date(BIRTHDATE_VALUE.getTime()), m.getBirthDate());
        Assert.assertEquals(NUMBER_OF_CHILDREN_VALUE,m.getNumberOfChildren());
        Assert.assertEquals(INCOMING_YEAR_VALUE,m.getIncomingYear(),0.0D);
        Assert.assertNull(m.getGender());
        Assert.assertNull(m.getNames());
        Assert.assertEquals(0, m.getShortProp());
        Assert.assertEquals('\0', m.getCharProp());
        Assert.assertFalse(m.isBooleanProp());
        Assert.assertEquals(0.0F,m.getTaxesYear(),0.0F);
        
		m.setGender(GENDER_VALUE);
        Assert.assertEquals(ID_VALUE, m.getId());
        Assert.assertEquals(NAME_VALUE, m.getName());
        Assert.assertEquals(LAST_NAME_VALUE, m.getLastName());
        Assert.assertEquals(new Date(BIRTHDATE_VALUE.getTime()), m.getBirthDate());
        Assert.assertEquals(NUMBER_OF_CHILDREN_VALUE,m.getNumberOfChildren());
        Assert.assertEquals(INCOMING_YEAR_VALUE,m.getIncomingYear(),0.0D);
		Assert.assertEquals(GENDER_VALUE,m.getGender());
        Assert.assertNull(m.getNames());
        Assert.assertEquals(0, m.getShortProp());
        Assert.assertEquals('\0', m.getCharProp());
        Assert.assertFalse(m.isBooleanProp());
        Assert.assertEquals(0.0F,m.getTaxesYear(),0.0F);

        m.setNames(NAMES_VALUES);
        Assert.assertEquals(ID_VALUE, m.getId());
        Assert.assertEquals(NAME_VALUE, m.getName());
        Assert.assertEquals(LAST_NAME_VALUE, m.getLastName());
        Assert.assertEquals(new Date(BIRTHDATE_VALUE.getTime()), m.getBirthDate());
        Assert.assertEquals(NUMBER_OF_CHILDREN_VALUE,m.getNumberOfChildren());
        Assert.assertEquals(INCOMING_YEAR_VALUE,m.getIncomingYear(),0.0D);
        Assert.assertEquals(GENDER_VALUE,m.getGender());
        Assert.assertArrayEquals(NAMES_VALUES, m.getNames());
        Assert.assertEquals(0, m.getShortProp());
        Assert.assertEquals('\0', m.getCharProp());
        Assert.assertFalse(m.isBooleanProp());
        Assert.assertEquals(0.0F,m.getTaxesYear(),0.0F);

        m.setShortProp(SHORT_VALUE);
        Assert.assertEquals(ID_VALUE, m.getId());
        Assert.assertEquals(NAME_VALUE, m.getName());
        Assert.assertEquals(LAST_NAME_VALUE, m.getLastName());
        Assert.assertEquals(new Date(BIRTHDATE_VALUE.getTime()), m.getBirthDate());
        Assert.assertEquals(NUMBER_OF_CHILDREN_VALUE,m.getNumberOfChildren());
        Assert.assertEquals(INCOMING_YEAR_VALUE,m.getIncomingYear(),0.0D);
        Assert.assertEquals(GENDER_VALUE,m.getGender());
        Assert.assertArrayEquals(NAMES_VALUES, m.getNames());
        Assert.assertEquals(SHORT_VALUE, m.getShortProp());
        Assert.assertEquals('\0', m.getCharProp());
        Assert.assertFalse(m.isBooleanProp());
        Assert.assertEquals(0.0F,m.getTaxesYear(),0.0F);

        m.setCharProp(CHAR_VALUE);
        Assert.assertEquals(ID_VALUE, m.getId());
        Assert.assertEquals(NAME_VALUE, m.getName());
        Assert.assertEquals(LAST_NAME_VALUE, m.getLastName());
        Assert.assertEquals(new Date(BIRTHDATE_VALUE.getTime()), m.getBirthDate());
        Assert.assertEquals(NUMBER_OF_CHILDREN_VALUE,m.getNumberOfChildren());
        Assert.assertEquals(INCOMING_YEAR_VALUE,m.getIncomingYear(),0.0D);
        Assert.assertEquals(GENDER_VALUE,m.getGender());
        Assert.assertArrayEquals(NAMES_VALUES, m.getNames());
        Assert.assertEquals(SHORT_VALUE, m.getShortProp());
        Assert.assertEquals(CHAR_VALUE, m.getCharProp());
        Assert.assertFalse(m.isBooleanProp());
        Assert.assertEquals(0.0F,m.getTaxesYear(),0.0F);

        m.setBooleanProp(BOOLEAN_VALUE);
        Assert.assertEquals(ID_VALUE, m.getId());
        Assert.assertEquals(NAME_VALUE, m.getName());
        Assert.assertEquals(LAST_NAME_VALUE, m.getLastName());
        Assert.assertEquals(new Date(BIRTHDATE_VALUE.getTime()), m.getBirthDate());
        Assert.assertEquals(NUMBER_OF_CHILDREN_VALUE,m.getNumberOfChildren());
        Assert.assertEquals(INCOMING_YEAR_VALUE,m.getIncomingYear(),0.0D);
        Assert.assertEquals(GENDER_VALUE,m.getGender());
        Assert.assertArrayEquals(NAMES_VALUES, m.getNames());
        Assert.assertEquals(SHORT_VALUE, m.getShortProp());
        Assert.assertEquals(CHAR_VALUE, m.getCharProp());
        Assert.assertTrue(m.isBooleanProp() == BOOLEAN_VALUE);
        Assert.assertEquals(0.0F,m.getTaxesYear(),0.0F);

        m.setTaxesYear(FLOAT_VALUE);
        Assert.assertEquals(ID_VALUE, m.getId());
        Assert.assertEquals(NAME_VALUE, m.getName());
        Assert.assertEquals(LAST_NAME_VALUE, m.getLastName());
        Assert.assertEquals(new Date(BIRTHDATE_VALUE.getTime()), m.getBirthDate());
        Assert.assertEquals(NUMBER_OF_CHILDREN_VALUE,m.getNumberOfChildren());
        Assert.assertEquals(INCOMING_YEAR_VALUE,m.getIncomingYear(),0.0D);
        Assert.assertEquals(GENDER_VALUE,m.getGender());
        Assert.assertArrayEquals(NAMES_VALUES, m.getNames());
        Assert.assertEquals(SHORT_VALUE, m.getShortProp());
        Assert.assertEquals(CHAR_VALUE, m.getCharProp());
        Assert.assertTrue(m.isBooleanProp() == BOOLEAN_VALUE);
        Assert.assertEquals(FLOAT_VALUE,m.getTaxesYear(),0.0F);
	}
    /**
     * Test the clone feature with a model that have non-cloneable properties.
     */
    @Test public void testCloneNonCloneablePropertiesModel()
    {
        INonCloneablePropertiesModel m1, m2;
        INonCloneableModel ncm1;
        
        m1 = DynaBeanUtils.instanceDefaultFactory().newDynaBean(INonCloneablePropertiesModel.class);
        m1.setId(ID_VALUE);
        ncm1 = DynaBeanUtils.instanceDefaultFactory().newDynaBean(INonCloneableModel.class);
        ncm1.setId(ID_VALUE + 1000L);
        ncm1.setName(NAME_VALUE);
        m1.setNonCloneableModel(ncm1);
        
        m2 = m1.clone();
        Assert.assertEquals(m1, m2);
        Assert.assertNotSame(m1, m2);
        
        Assert.assertSame(ncm1, m2.getNonCloneableModel());
    }
	/**
	 * Test the 'clone' feature
	 */
	@Test public void testClone()
	{
		IModel m1, m2;
		
		m1 = DynaBeanUtils.instanceDefaultFactory().newDynaBean(IModel.class);
		assignValues(m1);
		
		m2 = m1.clone();
		
		Assert.assertEquals(m1, m2);
		// Test clone of date
		Assert.assertNotSame(m1.getBirthDate(), m2.getBirthDate());
	}
	/**
	 * Test the 'hashCode' feature
	 */
	@Test public void testHashCode()
	{
		IModel m1, m2;
		int n;
		
		m1 = DynaBeanUtils.instanceDefaultFactory().newDynaBean(IModel.class);
		assignValues(m1);
		
		m2 = m1.clone();

		for(n = 0; n < 10; n++)
		{
		    Assert.assertEquals(m1.hashCode(), m2.hashCode());
		}
	}
	/**
	 * Test toString
	 * @throws Exception
	 */
	@Test public void testToString() throws Exception {
		ISimpleModel smodel;
		String s;
		
		smodel = DynaBeanUtils.instanceDefaultFactory().newDynaBean(ISimpleModel.class);
		// 1.- name=null, date=null, tested=false, number=null
		s = smodel.toString();
		Assert.assertTrue(s.startsWith(ISimpleModel.class.getSimpleName()));
		Assert.assertTrue(s.contains("name=null"));
		Assert.assertTrue(s.contains("date=null"));
		Assert.assertTrue(s.contains("tested=false"));
		Assert.assertTrue(s.contains("number=null"));
		
		// 1.- name=NAME, date=null, tested=true, number=55
		smodel.setName("NAME");
		smodel.setTested(true);
		smodel.setNumber(55L);
		s = smodel.toString();
		Assert.assertTrue(s.contains("name=NAME"));
		Assert.assertTrue(s.contains("date=null"));
		Assert.assertTrue(s.contains("tested=true"));
		Assert.assertTrue(s.contains("number=55"));
		
	}
	/**
	 * Test the serialization feature.
	 */
	@Test public void testSerialize() throws Exception
	{
		ByteArrayOutputStream baos;
		IModel model, model1;
		ObjectOutputStream out;
		ByteArrayInputStream bais;
		ObjectInputStream in;
		
		// Prepare bean
		model = DynaBeanUtils.instanceDefaultFactory().newDynaBean(IModel.class);
		assignValues(model);

		// Prepare stream
		baos = new ByteArrayOutputStream();
		out = new ObjectOutputStream(baos);
		out.writeObject(model);
		out.flush();
		
		// Deserialize
		bais = new ByteArrayInputStream(baos.toByteArray());
		in = new ObjectInputStream(bais);
		model1 = (IModel)in.readObject();
		
		// Test if equals
		Assert.assertNotNull(model1);
		Assert.assertEquals(model, model1);
		Assert.assertNotSame(model, model1);
	}
	/**
	 * Test the {@link Object#equals(Object)} operation.
	 */
	@Test public void testEquals()
	{
	    IModel m1, m2, m3;
	    ISimpleModel sm1;
	    IIncorrectModel im1;
	    
	    m1 = DynaBeanUtils.instanceDefaultFactory().newDynaBean(IModel.class);
        sm1 = DynaBeanUtils.instanceDefaultFactory().newDynaBean(ISimpleModel.class);
        im1 = DynaBeanUtils.instanceDefaultFactory().newDynaBean(IIncorrectModel.class);
	    assignValues(m1);
	    m2 = null;
        Assert.assertFalse(m1.equals(m2));

        Assert.assertFalse(m1.equals(sm1));
        Assert.assertFalse(m1.equals(Proxy.getInvocationHandler(sm1)));
        
        Assert.assertFalse(m1.equals(im1));
        Assert.assertFalse(m1.equals(Proxy.getInvocationHandler(im1)));
        
        Assert.assertTrue(m1.equals(Proxy.getInvocationHandler(m1)));
        Assert.assertTrue(m1.equals(m1));
	    Assert.assertFalse(m1.equals(""));
	    m2 = new ModelImpl(m1);
	    Assert.assertTrue(m1.equals(m2));
	    
	    m3 = (IModel) Proxy.newProxyInstance(getClass().getClassLoader(),
	            new Class[] {IModel.class}, new InvocationHandler()
                {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
                    {
                        return null;
                    }
                });
	    Assert.assertFalse(m1.equals(m3));
	    Assert.assertFalse(m1.equals(Proxy.getInvocationHandler(m3)));
	}
	/**
	 * To test {@link Object#equals(Object)} with an exception on getter of other object.
	 */
	@Test(expected=RuntimeException.class)
	public void testEqualsException()
	{
	    IDynaBeanEqualsException d1, d2;
	    
	    d1 = DynaBeanUtils.instanceDefaultFactory().newDynaBean(IDynaBeanEqualsException.class);
	    d1.setFieldInt(10);
	    d2 = new DynaBeanEqualsExceptionImpl();
	    d1.equals(d2);
	}
	/**
	 * Test the clone with explicit bean implementation.
	 */
	@Test public void testMixedClone()
	{
		IModel m1, m2, m3;
		
		m1 = DynaBeanUtils.instanceDefaultFactory().newDynaBean(IModel.class);
		assignValues(m1);
		
		m2 = new ModelImpl(m1);
		
		Assert.assertEquals(m1, m2);
		m3 = m1.clone();
		Assert.assertNotSame(m3, m2);
		Assert.assertEquals(m3, m2);
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void testIncorrectModel()
	{
	    IIncorrectModel imodel;
	    
	    imodel = DynaBeanUtils.instanceDefaultFactory().newDynaBean(IIncorrectModel.class);
	    imodel.get();
	}
	/**
	 * Assign the test reference values for the model.
	 * @param model The model to assign to
	 */
	private void assignValues(IModel model)
	{
        model.setId(ID_VALUE);
        model.setName(NAME_VALUE);
        model.setLastName(LAST_NAME_VALUE);
        model.setBirthDate(BIRTHDATE_VALUE);
        model.setNumberOfChildren(NUMBER_OF_CHILDREN_VALUE);
        model.setIncomingYear(INCOMING_YEAR_VALUE);
        model.setGender(GENDER_VALUE);
        model.setNames(NAMES_VALUES);
	}
    /**
     * To test an equals method with exception.
     */
	interface IDynaBeanEqualsException
	{
	    public int getFieldInt();
	    public void setFieldInt(int fieldInt);
	}
	/**
	 * To test an equals method with exception.
	 */
	class DynaBeanEqualsExceptionImpl implements IDynaBeanEqualsException
	{
        @Override
        public int getFieldInt()
        {
            throw new SecurityException();
        }

        @Override
        public void setFieldInt(int fieldInt)
        {
            // Nothing to do!
        }
	    
	}
}
