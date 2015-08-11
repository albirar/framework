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

package cat.albirar.framework.dynabean.visitor;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import cat.albirar.framework.dynabean.impl.DefaultDynaBeanFactory;
import cat.albirar.framework.dynabean.impl.IDynaBeanImplementationFactory;

/**
 * Test for {@link DynaBeanVisitorListDefault}.
 * 
 * @author Octavi Fornés ofornes@albirar.cat
 * @since 2.0.0
 */
public class DynaBeanVisitorListDefaultTest
{
    private IDynaBeanImplementationFactory factory;

    private DynaBeanVisitorListDefault visitorList;
    
    @Before
    public void initTest()
    {
        factory = new DefaultDynaBeanFactory();
        visitorList = new DynaBeanVisitorListDefault();
    }
    /**
     * Test the {@link IDynaBeanVisitor#eventGet(String, Object, Class)} and {@link IDynaBeanVisitor#eventSet(String, Object, Class)} without visitors.
     */
    @Test public void testNoVisitors()
    {
        IDynaBeanModelTest d;
        
        d = factory.newDynaBean(IDynaBeanModelTest.class, visitorList);
        d.setFieldInt(10);
        Assert.assertEquals(10,d.getFieldInt());
    }
    /**
     * Test the {@link IDynaBeanVisitor#eventGet(String, Object, Class)} and {@link IDynaBeanVisitor#eventSet(String, Object, Class)} without visitors.
     */
    @Test public void testVisitors()
    {
        IDynaBeanModelTest d;
        IDynaBeanVisitor v1, v2;
        
        v1 = prepareVisitorMock();
        v2 = prepareVisitorMock();
        visitorList.add(v1);
        visitorList.add(v2);
        d = factory.newDynaBean(IDynaBeanModelTest.class, visitorList);
        d.setFieldInt(10);
        Assert.assertEquals(10,d.getFieldInt());
        Mockito.verify(v1).eventGet(IDynaBeanModelTest.FIELD_INT_NAME, Integer.valueOf(10), int.class);
        Mockito.verify(v1).eventSet(IDynaBeanModelTest.FIELD_INT_NAME, Integer.valueOf(10), int.class);
        Mockito.verify(v1, Mockito.never()).eventGet(Mockito.eq(IDynaBeanModelTest.FIELD_STRING_NAME), Mockito.anyObject(), Mockito.any(Class.class));
        Mockito.verify(v1, Mockito.never()).eventSet(Mockito.eq(IDynaBeanModelTest.FIELD_STRING_NAME), Mockito.anyObject(), Mockito.any(Class.class));

        Mockito.verify(v2).eventGet(IDynaBeanModelTest.FIELD_INT_NAME, Integer.valueOf(10), int.class);
        Mockito.verify(v2).eventSet(IDynaBeanModelTest.FIELD_INT_NAME, Integer.valueOf(10), int.class);
        Mockito.verify(v2, Mockito.never()).eventGet(Mockito.eq(IDynaBeanModelTest.FIELD_STRING_NAME), Mockito.anyObject(), Mockito.any(Class.class));
        Mockito.verify(v2, Mockito.never()).eventSet(Mockito.eq(IDynaBeanModelTest.FIELD_STRING_NAME), Mockito.anyObject(), Mockito.any(Class.class));
    }
    /**
     * Test the {@link IDynaBeanVisitor#eventGet(String, Object, Class)} and {@link IDynaBeanVisitor#eventSet(String, Object, Class)} with visitors and nulls visitors.
     */
    @Test public void testVisitorsAndNulls()
    {
        IDynaBeanModelTest d;
        IDynaBeanVisitor v1, v2;
        
        v1 = prepareVisitorMock();
        v2 = prepareVisitorMock();
        visitorList.add(null);
        visitorList.add(v1);
        visitorList.add(null);
        visitorList.add(v2);
        visitorList.add(null);
        d = factory.newDynaBean(IDynaBeanModelTest.class, visitorList);
        d.setFieldInt(10);
        Assert.assertEquals(10,d.getFieldInt());
        Mockito.verify(v1).eventGet(IDynaBeanModelTest.FIELD_INT_NAME, Integer.valueOf(10), int.class);
        Mockito.verify(v1).eventSet(IDynaBeanModelTest.FIELD_INT_NAME, Integer.valueOf(10), int.class);
        Mockito.verify(v1, Mockito.never()).eventGet(Mockito.eq(IDynaBeanModelTest.FIELD_STRING_NAME), Mockito.anyObject(), Mockito.any(Class.class));
        Mockito.verify(v1, Mockito.never()).eventSet(Mockito.eq(IDynaBeanModelTest.FIELD_STRING_NAME), Mockito.anyObject(), Mockito.any(Class.class));

        Mockito.verify(v2).eventGet(IDynaBeanModelTest.FIELD_INT_NAME, Integer.valueOf(10), int.class);
        Mockito.verify(v2).eventSet(IDynaBeanModelTest.FIELD_INT_NAME, Integer.valueOf(10), int.class);
        Mockito.verify(v2, Mockito.never()).eventGet(Mockito.eq(IDynaBeanModelTest.FIELD_STRING_NAME), Mockito.anyObject(), Mockito.any(Class.class));
        Mockito.verify(v2, Mockito.never()).eventSet(Mockito.eq(IDynaBeanModelTest.FIELD_STRING_NAME), Mockito.anyObject(), Mockito.any(Class.class));
    }
    /**
     * Create a new mocked visitor that answer ever the same as arguments.
     * @return The mocked visitor
     */
    private IDynaBeanVisitor prepareVisitorMock()
    {
        IDynaBeanVisitor v;

        v = Mockito.mock(IDynaBeanVisitor.class);
        Mockito.when(v.eventGet(Mockito.anyString(), Mockito.anyObject(), Mockito.any(Class.class))).then(new DynaBeanMockAnswer());
        Mockito.when(v.eventSet(Mockito.anyString(), Mockito.anyObject(), Mockito.any(Class.class))).then(new DynaBeanMockAnswer());
        return v;
    }
    /**
     * To test visitors.
     */
    class DynaBeanMockAnswer implements Answer<Object>
    {
        @Override
        public Object answer(InvocationOnMock invocation) throws Throwable
        {
            return invocation.getArguments()[1];
        }
        
    }
    /**
     * Model to test.
     */
    interface IDynaBeanModelTest
    {
        static final String FIELD_INT_NAME = "fieldInt";
        static final String FIELD_STRING_NAME = "fieldString";
        
        public int getFieldInt();
        public void setFieldInt(int fieldInt);
        public String getFieldString();
        public void setFieldString(String fieldString);
    }
}
