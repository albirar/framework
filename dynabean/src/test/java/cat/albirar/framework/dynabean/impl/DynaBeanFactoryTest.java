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
import java.util.List;
import java.util.Vector;

import org.junit.Assert;
import org.junit.Test;

import cat.albirar.framework.dynabean.DynaBeanUtils;
import cat.albirar.framework.dynabean.IDynaBeanFactory;
import cat.albirar.framework.dynabean.impl.models.test.IAnnotatedModel;
import cat.albirar.framework.dynabean.visitor.IDynaBeanVisitor;

/**
 * The class test for {@link IDynaBeanFactory} implementations.
 * <strong>USES the {@link DynaBeanUtils#instanceFactory()} to ensure test of static thread factories.
 * 
 * @author Octavi Fornés ofornes@albirar.cat
 * @sinze 2.0
 */
public class DynaBeanFactoryTest
{
    /** The value for amount property. */
    private static final double VALUE_AMOUNT = 145.0065D;
    /** The value for id property. */
    private static final String VALUE_ID = "identificator";
    /** The pattern for get notification. */
    private static final String PATTERN_GET = "get '%s'='%s'";
    /** The pattern for set notification. */
    private static final String PATTERN_SET = "set '%s'='%s'";
    /** The values for 'name' property. */
    private static final String [] VALUES_NAMES = { "a", "b", "c", "d"};

    /**
     * Test the {@link IDynaBeanVisitor} feature with a non-modifying visitor.
     */
    @Test public void testVisitorFeatureNonModifying()
    {
        IAnnotatedModel dbean;
        IDynaBeanFactory factory;
        RecordingVisitorTest visitor;
        List<String> list, list1;
        
        visitor = new RecordingVisitorTest();
        factory = DynaBeanUtils.instanceFactory();
        dbean = factory.newDynaBean(IAnnotatedModel.class,visitor);
        
        Assert.assertSame(visitor, ((DynaBeanImpl<?>)Proxy.getInvocationHandler(dbean)).getVisitor());
        
        Assert.assertNull(visitor.getEvent());

        Assert.assertEquals(IAnnotatedModel.DEFAULT_AMOUNT, dbean.getAmount(),0.0D);
        Assert.assertNotNull(visitor.getEvent());
        Assert.assertEquals(String.format(PATTERN_GET,"amount", "" + IAnnotatedModel.DEFAULT_AMOUNT),visitor.getEvent());

        dbean.setAmount(VALUE_AMOUNT);
        Assert.assertNotNull(visitor.getEvent());
        Assert.assertEquals(String.format(PATTERN_SET,"amount", "" + VALUE_AMOUNT),visitor.getEvent());
        Assert.assertEquals(VALUE_AMOUNT, dbean.getAmount(),0.0D);
        Assert.assertNotNull(visitor.getEvent());
        Assert.assertEquals(String.format(PATTERN_GET,"amount", "" + VALUE_AMOUNT),visitor.getEvent());

        dbean.setId(VALUE_ID);
        Assert.assertNotNull(visitor.getEvent());
        Assert.assertEquals(String.format(PATTERN_SET,"id", VALUE_ID),visitor.getEvent());
        Assert.assertEquals(VALUE_ID, dbean.getId());
        Assert.assertNotNull(visitor.getEvent());
        Assert.assertEquals(String.format(PATTERN_GET,"id", VALUE_ID),visitor.getEvent());
        
        // Test with null
        dbean.setId(null);
        Assert.assertNotNull(visitor.getEvent());
        Assert.assertEquals(String.format(PATTERN_SET,"id", "" + null),visitor.getEvent());
        Assert.assertNull(dbean.getId());
        Assert.assertNotNull(visitor.getEvent());
        Assert.assertEquals(String.format(PATTERN_GET,"id", "" + null),visitor.getEvent());
        
        list = dbean.getNamesList();
        Assert.assertNotNull(visitor.getEvent());
        Assert.assertEquals(String.format(PATTERN_GET,"namesList", "" + list),visitor.getEvent());
        
        list1 = new Vector<String>();
        list1.add("A");
        list1.add("B");
        dbean.setNamesList(list1);
        Assert.assertNotNull(visitor.getEvent());
        Assert.assertEquals(String.format(PATTERN_SET,"namesList", "" + list1),visitor.getEvent());
    }
    /**
     * Test the {@link IDynaBeanVisitor} feature with a modifying visitor.
     */
    @Test public void testVisitorFeatureModifying()
    {
        IAnnotatedModel dbean;
        IDynaBeanFactory factory;
        ModifyingVisitorTest visitor;
        List<String> list, list1;
        
        visitor = new ModifyingVisitorTest();
        factory = DynaBeanUtils.instanceFactory();
        dbean = factory.newDynaBean(IAnnotatedModel.class,visitor);
        
        Assert.assertSame(visitor, ((DynaBeanImpl<?>)Proxy.getInvocationHandler(dbean)).getVisitor());

        // Check modify only on get
        visitor.setModifyGet(true);
        visitor.setModifySet(false);
        visitor.setNewValue(Double.valueOf(VALUE_AMOUNT * 2.0D));
        dbean.setAmount(VALUE_AMOUNT);
        
        Assert.assertEquals(VALUE_AMOUNT * 2.0D, dbean.getAmount(),0.0D);
        // Check the trully value
        visitor.setModifyGet(false);
        Assert.assertEquals(VALUE_AMOUNT, dbean.getAmount(),0.0D);
        
        // Check modifying on set
        visitor.setModifySet(true);
        visitor.setNewValue(VALUE_ID + "XXX");
        dbean.setId(VALUE_ID);
        Assert.assertEquals(VALUE_ID + "XXX", dbean.getId());
        
        list = new Vector<String>();
        list1 = new Vector<String>();
        for(String name: VALUES_NAMES) {
            list.add(name);
            list1.add(name.concat("XXX"));
        }
        visitor.setModifyGet(true);
        visitor.setModifySet(false);
        visitor.setNewValue(list1);
        dbean.setNamesList(list);
        Assert.assertEquals(dbean.getNamesList(), list1);

        visitor.setModifyGet(false);
        Assert.assertEquals(dbean.getNamesList(), list);
        visitor.setModifyGet(false);
        visitor.setModifySet(true);
        dbean.setNamesList(list);
        Assert.assertEquals(dbean.getNamesList(), list1);
        visitor.setModifySet(false);
        Assert.assertEquals(dbean.getNamesList(), list1);
    }

    
    // Private auxiliary test classes
    /** A visitor for recording events on get and set. */
    class RecordingVisitorTest implements IDynaBeanVisitor
    {
        private String event;
        /**
         * {@inheritDoc}
         */
        @Override
        public Object eventGet(String name, Object value, Class<?> propertyType)
        {
            event = String.format(PATTERN_GET,name, "" + value);
            return value;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Object eventSet(String name, Object value, Class<?> propertyType)
        {
            event = String.format(PATTERN_SET,name, "" + value);
            return value;
        }

        /**
         * @return the event
         */
        public String getEvent()
        {
            return event;
        }
    }
    /** A visitor to modifying get/set. */
    class ModifyingVisitorTest implements IDynaBeanVisitor
    {
        private boolean modifyGet;
        private boolean modifySet;
        private Object newValue;
        /**
         * {@inheritDoc}
         */
        @Override
        public Object eventGet(String name, Object value, Class<?> propertyType)
        {
            if(modifyGet)
            {
                value = newValue;
            }
            return value;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Object eventSet(String name, Object value, Class<?> propertyType)
        {
            if(modifySet)
            {
                value = newValue;
            }
            return value;
        }
        /**
         * @return the modifyGet
         */
        public boolean isModifyGet()
        {
            return modifyGet;
        }

        /**
         * @param modifyGet the modifyGet to set
         */
        public void setModifyGet(boolean modifyGet)
        {
            this.modifyGet = modifyGet;
        }

        /**
         * @return the modifySet
         */
        public boolean isModifySet()
        {
            return modifySet;
        }

        /**
         * @param modifySet the modifySet to set
         */
        public void setModifySet(boolean modifySet)
        {
            this.modifySet = modifySet;
        }

        /**
         * @return the newValue
         */
        public Object getNewValue()
        {
            return newValue;
        }

        /**
         * @param newValue the newValue to set
         */
        public void setNewValue(Object newValue)
        {
            this.newValue = newValue;
        }
        
    }
}
