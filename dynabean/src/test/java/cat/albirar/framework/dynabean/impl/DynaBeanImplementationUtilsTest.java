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

import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test for {@link DynaBeanImplementationUtils} class.
 * 
 * @author Octavi Fornés ofornes@albirar.cat
 * @since 2.0.0
 */
public class DynaBeanImplementationUtilsTest
{
    /**
     * Test for {@link DynaBeanImplementationUtils#isPropertyMethod(String)} with null value.
     */
    @Test(expected=NullPointerException.class)
    public void isPropertyMethodNull()
    {
        DynaBeanImplementationUtils.isPropertyMethod(null);
    }
    /**
     * Test for {@link DynaBeanImplementationUtils#isPropertyMethod(String)}.
     * <ul>
     *    <li><strong>Recognized property names</strong>
     *       <ul>
     *          <li><strong>getMethod</strong></li>
     *          <li><strong>setMethod</strong></li>
     *          <li><strong>isMethod</strong></li>
     *          <li><strong>getM</strong></li>
     *          <li><strong>setM</strong></li>
     *          <li><strong>isM</strong></li>
     *       </ul>
     *    </li>
     *    <li><strong>Unrecognized property names</strong>
     *       <ul>
     *          <li><strong>GetMethod</strong></li>
     *          <li><strong>SetMethod</strong></li>
     *          <li><strong>IsMethod</strong></li>
     *          <li><strong>GETMETHOD</strong></li>
     *          <li><strong>SETMETHOD</strong></li>
     *          <li><strong>ISMETHOD</strong></li>
     *          <li><strong>method</strong></li>
     *          <li><strong>m</strong></li>
     *          <li><strong>get</strong></li>
     *          <li><strong>set</strong></li>
     *          <li><strong>is</strong></li>
     *          <li><strong>Empty string</strong></li>
     *          <li><strong>Only withespaces string</strong></li>
     *       </ul>
     *    </li>
     * </ul>
     */
    @Test public void testIsPropertyMethod()
    {
        // Recognized method names
        Assert.assertTrue(DynaBeanImplementationUtils.isPropertyMethod("getMethod"));
        Assert.assertTrue(DynaBeanImplementationUtils.isPropertyMethod("setMethod"));
        Assert.assertTrue(DynaBeanImplementationUtils.isPropertyMethod("isMethod"));
        Assert.assertTrue(DynaBeanImplementationUtils.isPropertyMethod("getM"));
        Assert.assertTrue(DynaBeanImplementationUtils.isPropertyMethod("setM"));
        Assert.assertTrue(DynaBeanImplementationUtils.isPropertyMethod("isM"));
        // Unrecognized method names
        Assert.assertFalse(DynaBeanImplementationUtils.isPropertyMethod("GetMethod"));
        Assert.assertFalse(DynaBeanImplementationUtils.isPropertyMethod("SetMethod"));
        Assert.assertFalse(DynaBeanImplementationUtils.isPropertyMethod("IsMethod"));
        Assert.assertFalse(DynaBeanImplementationUtils.isPropertyMethod("GETMETHOD"));
        Assert.assertFalse(DynaBeanImplementationUtils.isPropertyMethod("SETMETHOD"));
        Assert.assertFalse(DynaBeanImplementationUtils.isPropertyMethod("ISMETHOD"));
        Assert.assertFalse(DynaBeanImplementationUtils.isPropertyMethod("method"));
        Assert.assertFalse(DynaBeanImplementationUtils.isPropertyMethod("m"));
        Assert.assertFalse(DynaBeanImplementationUtils.isPropertyMethod("get"));
        Assert.assertFalse(DynaBeanImplementationUtils.isPropertyMethod("set"));
        Assert.assertFalse(DynaBeanImplementationUtils.isPropertyMethod("is"));
        Assert.assertFalse(DynaBeanImplementationUtils.isPropertyMethod(""));
        Assert.assertFalse(DynaBeanImplementationUtils.isPropertyMethod("   "));
    }
    /**
     * Test for {@link DynaBeanImplementationUtils#isCorrectProperty(java.lang.reflect.Method)} with null value.
     */
    @Test(expected=NullPointerException.class)
    public void testCorrectMethodNull()
    {
        DynaBeanImplementationUtils.isCorrectProperty(null);
    }
    /**
     * Test for {@link DynaBeanImplementationUtils#isCorrectProperty(java.lang.reflect.Method)} for different corrects and incorrects methods.
     */
    @Test public void testCorrectMethod()
    {
        Method[] methods;
        
        // Incorrect methods
        methods = IIncorrectModelPropertyDefinition.class.getDeclaredMethods();
        for(Method m : methods)
        {
            Assert.assertFalse("For '".concat(m.getName()).concat("'"),DynaBeanImplementationUtils.isCorrectProperty(m));
        }
        
        // Correct methods
        methods = ICorrectModelPropertyDefinition.class.getDeclaredMethods();
        for(Method m : methods)
        {
            Assert.assertTrue("For '".concat(m.getName()).concat("'"),DynaBeanImplementationUtils.isCorrectProperty(m));
        }
    }
    /**
     * Test for {@link DynaBeanImplementationUtils#isGetter(String)} with null value.
     */
    @Test(expected=NullPointerException.class)
    public void testIsGetterMethodNull()
    {
        DynaBeanImplementationUtils.isGetter(null);
    }
    /**
     * Test for {@link DynaBeanImplementationUtils#isGetter(String)}.
     * <ul>
     *    <li>Getters...
     *       <ul>
     *          <li>getMethod</li>
     *          <li>getM</li>
     *          <li>isMethod</li>
     *          <li>isM</li>
     *       </ul>
     *    </li>
     *    <li>NOT getters...
     *       <ul>
     *          <li>GetMethod</li>
     *          <li>gEtMethod</li>
     *          <li>geTmethod</li>
     *          <li>GEtMethod</li>
     *          <li>GeTmethod</li>
     *          <li>GETmethod</li>
     *          <li>get</li>
     *          <li>IsMethod</li>
     *          <li>iSMethod</li>
     *          <li>ISmethod</li>
     *          <li>is</li>
     *          <li>method</li>
     *          <li>Empty string</li>
     *          <li>Only whitespace string</li>
     *       </ul>
     *    </li>
     * </ul>
     */
    @Test public void testIsGetterMethod()
    {
        Assert.assertTrue(DynaBeanImplementationUtils.isGetter("getMethod"));
        Assert.assertTrue(DynaBeanImplementationUtils.isGetter("getM"));
        Assert.assertTrue(DynaBeanImplementationUtils.isGetter("isMethod"));
        Assert.assertTrue(DynaBeanImplementationUtils.isGetter("isM"));

        Assert.assertFalse(DynaBeanImplementationUtils.isGetter("GetMethod"));
        Assert.assertFalse(DynaBeanImplementationUtils.isGetter("gEtMethod"));
        Assert.assertFalse(DynaBeanImplementationUtils.isGetter("geTmethod"));
        Assert.assertFalse(DynaBeanImplementationUtils.isGetter("GEtMethod"));
        Assert.assertFalse(DynaBeanImplementationUtils.isGetter("GeTmethod"));
        Assert.assertFalse(DynaBeanImplementationUtils.isGetter("GETmethod"));
        Assert.assertFalse(DynaBeanImplementationUtils.isGetter("get"));
        Assert.assertFalse(DynaBeanImplementationUtils.isGetter("IsMethod"));
        Assert.assertFalse(DynaBeanImplementationUtils.isGetter("iSMethod"));
        Assert.assertFalse(DynaBeanImplementationUtils.isGetter("ISmethod"));
        Assert.assertFalse(DynaBeanImplementationUtils.isGetter("is"));
        Assert.assertFalse(DynaBeanImplementationUtils.isGetter("method"));
        Assert.assertFalse(DynaBeanImplementationUtils.isGetter(""));
        Assert.assertFalse(DynaBeanImplementationUtils.isGetter("    "));
    }
    /**
     * Test for {@link DynaBeanImplementationUtils#isGetterBoolean(String)} with null value.
     */
    @Test(expected=NullPointerException.class)
    public void testIsGetterBooleanMethodNull()
    {
        DynaBeanImplementationUtils.isGetterBoolean(null);
    }
    /**
     * Test for {@link DynaBeanImplementationUtils#isGetterBoolean(String)}.
     * <ul>
     *    <li>Getters...
     *       <ul>
     *          <li>isMethod</li>
     *          <li>isM</li>
     *       </ul>
     *    </li>
     *    <li>NOT getters...
     *       <ul>
     *          <li>IsMethod</li>
     *          <li>iSMethod</li>
     *          <li>ISmethod</li>
     *          <li>is</li>
     *          <li>method</li>
     *          <li>Empty string</li>
     *          <li>Only whitespace string</li>
     *       </ul>
     *    </li>
     * </ul>
     */
    @Test public void testIsGetterBooleanMethod()
    {
        Assert.assertTrue(DynaBeanImplementationUtils.isGetter("isMethod"));
        Assert.assertTrue(DynaBeanImplementationUtils.isGetter("isM"));

        Assert.assertFalse(DynaBeanImplementationUtils.isGetter("IsMethod"));
        Assert.assertFalse(DynaBeanImplementationUtils.isGetter("iSMethod"));
        Assert.assertFalse(DynaBeanImplementationUtils.isGetter("ISmethod"));
        Assert.assertFalse(DynaBeanImplementationUtils.isGetter("is"));
        Assert.assertFalse(DynaBeanImplementationUtils.isGetter("method"));
        Assert.assertFalse(DynaBeanImplementationUtils.isGetter(""));
        Assert.assertFalse(DynaBeanImplementationUtils.isGetter("    "));
    }
    /**
     * Test for {@link DynaBeanImplementationUtils#isSetter(String)} with null value.
     */
    @Test(expected=NullPointerException.class)
    public void testIsSetterMethodNull()
    {
        DynaBeanImplementationUtils.isSetter(null);
    }
    /**
     * Test for {@link DynaBeanImplementationUtils#isSetter(String)}.
     * <ul>
     *    <li>Setters...
     *       <ul>
     *          <li>setMethod</li>
     *          <li>setM</li>
     *       </ul>
     *    </li>
     *    <li>NOT setters...
     *       <ul>
     *          <li>SetMethod</li>
     *          <li>sEtMethod</li>
     *          <li>seTmethod</li>
     *          <li>SEtMethod</li>
     *          <li>SeTmethod</li>
     *          <li>SETmethod</li>
     *          <li>set</li>
     *          <li>method</li>
     *          <li>Empty string</li>
     *          <li>Only whitespace string</li>
     *       </ul>
     *    </li>
     * </ul>
     */
    @Test public void testIsSetterMethod()
    {
        Assert.assertTrue(DynaBeanImplementationUtils.isSetter("setMethod"));
        Assert.assertTrue(DynaBeanImplementationUtils.isSetter("setM"));

        Assert.assertFalse(DynaBeanImplementationUtils.isSetter("SetMethod"));
        Assert.assertFalse(DynaBeanImplementationUtils.isSetter("sEtMethod"));
        Assert.assertFalse(DynaBeanImplementationUtils.isSetter("seTmethod"));
        Assert.assertFalse(DynaBeanImplementationUtils.isSetter("SEtMethod"));
        Assert.assertFalse(DynaBeanImplementationUtils.isSetter("SeTmethod"));
        Assert.assertFalse(DynaBeanImplementationUtils.isSetter("SETmethod"));
        Assert.assertFalse(DynaBeanImplementationUtils.isSetter("set"));
        Assert.assertFalse(DynaBeanImplementationUtils.isSetter("method"));
        Assert.assertFalse(DynaBeanImplementationUtils.isSetter(""));
        Assert.assertFalse(DynaBeanImplementationUtils.isSetter("    "));
    }
    /**
     * Test for {@link DynaBeanImplementationUtils#fromMethodToPropertyName(String)} with null value.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testFromMethodToPropertyNameNull()
    {
        DynaBeanImplementationUtils.fromMethodToPropertyName(null);
    }
    /**
     * Test for {@link DynaBeanImplementationUtils#fromMethodToPropertyName(String)} with illegal value.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testFromMethodToPropertyNameIllegal()
    {
        DynaBeanImplementationUtils.fromMethodToPropertyName("xxxx");
    }
    /**
     * Test for {@link DynaBeanImplementationUtils#fromMethodToPropertyName(String)}.
     */
    @Test public void testFromMethodToPropertyName()
    {
        String [][] testData;
        
        testData = new String[][] {
                {"getProp", "prop"}
                ,{"getP", "p"}
                ,{"setProp", "prop"}
                ,{"setP", "p"}
                ,{"isProp", "prop"}
                ,{"isP", "p"}
            };

        for(String [] testItem : testData)
        {
            Assert.assertEquals(testItem[1], DynaBeanImplementationUtils.fromMethodToPropertyName(testItem[0]));
        }
    }
    
    /**
     * Test for {@link DynaBeanImplementationUtils#fromPropertyToGetMethodName(String, Class)} with null values.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testFromPropertyToGetMethodNameClassNull()
    {
        DynaBeanImplementationUtils.fromPropertyToGetMethodName(null, null);
    }
    /**
     * Test for {@link DynaBeanImplementationUtils#fromPropertyToGetMethodName(String, Class)} with empty name.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testFromPropertyToGetMethodNameClassEmpty()
    {
        DynaBeanImplementationUtils.fromPropertyToGetMethodName("", null);
    }
    /**
     * Test for {@link DynaBeanImplementationUtils#fromPropertyToGetMethodName(String, Class)} with whitespaces as name.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testFromPropertyToGetMethodNameClassWhitespaces()
    {
        DynaBeanImplementationUtils.fromPropertyToGetMethodName("   ", null);
    }
    /**
     * Test for {@link DynaBeanImplementationUtils#fromPropertyToGetMethodName(String, Class)}.
     */
    @Test public void testFromPropertyToGetMethodNameClass()
    {
        GetDataInfo [] testData = {
            new GetDataInfo("prop", String.class, "getProp")
            , new GetDataInfo("p", String.class, "getP")
            , new GetDataInfo("p", Boolean.class, "isP")
            , new GetDataInfo("p", boolean.class, "isP")
            , new GetDataInfo("p", null, "getP")
        };
    
        for(GetDataInfo testItem : testData)
        {
            Assert.assertEquals(testItem.methodName, DynaBeanImplementationUtils
                    .fromPropertyToGetMethodName(testItem.propietat, testItem.type));
        }
    }

    /**
     * Test for {@link DynaBeanImplementationUtils#fromPropertyToGetMethodName(String, boolean)} with null values.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testFromPropertyToGetMethodNameBoolleanNull()
    {
        DynaBeanImplementationUtils.fromPropertyToGetMethodName(null, false);
    }

    /**
     * Test for {@link DynaBeanImplementationUtils#fromPropertyToGetMethodName(String, boolean)} with empty name.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testFromPropertyToGetMethodNameBoolleanEmtpy()
    {
        DynaBeanImplementationUtils.fromPropertyToGetMethodName("", false);
    }

    /**
     * Test for {@link DynaBeanImplementationUtils#fromPropertyToGetMethodName(String, boolean)} with whitespace name.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testFromPropertyToGetMethodNameBoolleanWhitespaces()
    {
        DynaBeanImplementationUtils.fromPropertyToGetMethodName("    ", false);
    }

    /**
     * Test for {@link DynaBeanImplementationUtils#fromPropertyToGetMethodName(String, boolean)}.
     */
    @Test public void testFromPropertyToGetMethodNameBoolean()
    {
        GetDataInfoBool [] testData = {
            new GetDataInfoBool("prop", false, "getProp")
            , new GetDataInfoBool("p", false, "getP")
            , new GetDataInfoBool("p", true, "isP")
            , new GetDataInfoBool("p", true, "isP")
            , new GetDataInfoBool("p", false, "getP")
        };
    
        for(GetDataInfoBool testItem : testData)
        {
            Assert.assertEquals(testItem.methodName, DynaBeanImplementationUtils
                    .fromPropertyToGetMethodName(testItem.propietat, testItem.booleanInfo));
        }
    }
    
    /**
     * Test for {@link DynaBeanImplementationUtils#fromPropertyToSetMethodName(String)} with null values.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testFromPropertyToSetMethodNameNull()
    {
        DynaBeanImplementationUtils.fromPropertyToSetMethodName(null);
    }
    
    
    /**
     * Test for {@link DynaBeanImplementationUtils#fromPropertyToSetMethodName(String)} with emtpy name.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testFromPropertyToSetMethodNameEmpty()
    {
        DynaBeanImplementationUtils.fromPropertyToSetMethodName("");
    }
    
    
    /**
     * Test for {@link DynaBeanImplementationUtils#fromPropertyToSetMethodName(String)} with whitespace name.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testFromPropertyToSetMethodNameWhitespaces()
    {
        DynaBeanImplementationUtils.fromPropertyToSetMethodName("    ");
    }
    
    /**
     * Test for {@link DynaBeanImplementationUtils#fromPropertyToSetMethodName(String)}.
     */
    @Test public void testFromPropertyToSetMethodName()
    {
        String [][] testData;
        
        testData = new String [][]{
                {"p", "setP"}
                ,{"property", "setProperty"}
                ,{"propertyWithCamelCase", "setPropertyWithCamelCase"}
        };
        for(String [] item: testData)
        {
            Assert.assertEquals(item[1],DynaBeanImplementationUtils.fromPropertyToSetMethodName(item[0]));
        }
    }
    /**
     * Test for {@link DynaBeanImplementationUtils#changeFirstCharToLower(String)} whit null value.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testChangeFirstCharToLowerNull()
    {
        DynaBeanImplementationUtils.changeFirstCharToLower(null);
    }
    /**
     * Test for {@link DynaBeanImplementationUtils#changeFirstCharToLower(String)} whit empty value.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testChangeFirstCharToLowerEmtpy()
    {
        DynaBeanImplementationUtils.changeFirstCharToLower("");
    }
    /**
     * Test for {@link DynaBeanImplementationUtils#changeFirstCharToLower(String)} whit whitespaces value.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testChangeFirstCharToLowerWhitespaces()
    {
        DynaBeanImplementationUtils.changeFirstCharToLower("   ");
    }
    /**
     * Test for {@link DynaBeanImplementationUtils#changeFirstCharToLower(String)}.
     */
    @Test public void testChangeFirstCharToLower()
    {
        String [][] testData = 
        {
            {"ABC", "aBC"}
            ,{"aBC", "aBC"}
            ,{"a", "a"}
            ,{"A", "a"}
            ,{"ñ", "ñ"}
            ,{"Ñ", "ñ"}
            ,{"ç", "ç"}
            ,{"Ç", "ç"}
        };
        for(String [] testItem : testData)
        {
            Assert.assertEquals(testItem[1],DynaBeanImplementationUtils.changeFirstCharToLower(testItem[0]));
        }
    }
    /**
     * Test for {@link DynaBeanImplementationUtils#changeFirstCharToUpper(String)} whit null value.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testChangeFirstCharToUpperNull()
    {
        DynaBeanImplementationUtils.changeFirstCharToUpper(null);
    }
    /**
     * Test for {@link DynaBeanImplementationUtils#changeFirstCharToUpper(String)} whit empty value.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testChangeFirstCharToUpperEmtpy()
    {
        DynaBeanImplementationUtils.changeFirstCharToUpper("");
    }
    /**
     * Test for {@link DynaBeanImplementationUtils#changeFirstCharToUpper(String)} whit whitespaces value.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testChangeFirstCharToUpperWhitespaces()
    {
        DynaBeanImplementationUtils.changeFirstCharToUpper("   ");
    }
    /**
     * Test for {@link DynaBeanImplementationUtils#changeFirstCharToUpper(String)}.
     */
    @Test public void testChangeFirstCharToUpper()
    {
        String [][] testData = 
        {
            {"ABC", "ABC"}
            ,{"aBC", "ABC"}
            ,{"a", "A"}
            ,{"A", "A"}
            ,{"ñ", "Ñ"}
            ,{"Ñ", "Ñ"}
            ,{"ç", "Ç"}
            ,{"Ç", "Ç"}
        };
        for(String [] testItem : testData)
        {
            Assert.assertEquals(testItem[1],DynaBeanImplementationUtils.changeFirstCharToUpper(testItem[0]));
        }
    }
    /** Test data class. */
    static class GetDataInfo
    {
        String propietat;
        Class<?> type;
        String methodName;
        GetDataInfo(String p, Class<?> t, String m)
        {
            propietat = p;
            type = t;
            methodName = m;
        }
    }
    /** Test data class. */
    static class GetDataInfoBool
    {
        String propietat;
        boolean booleanInfo;
        String methodName;
        GetDataInfoBool(String p, boolean binfo, String m)
        {
            propietat = p;
            booleanInfo = binfo;
            methodName = m;
        }
    }
    /**
     * To {@link DynaBeanImplementationUtilsTest#testCorrectMethod() test incorrect} definition property methods.
     */
    private interface IIncorrectModelPropertyDefinition
    {
        public void isBooleanInverted(boolean value);
        public boolean setBooleanInverted();
        
        public boolean isBooleanBoth(boolean value);
        public boolean setBooleanBoth(boolean value);
        
        public void setNone();
        
        public String setStringInverted();
        public void getStringInverted(String value);
        
        public String getStringBoth(String value);
        
        public String setStringBoth(String value);
    }
    /**
     * To {@link DynaBeanImplementationUtilsTest#testCorrectMethod() test correct} definition property methods.
     */
    private interface ICorrectModelPropertyDefinition
    {
        public boolean isBooleanOk();
        public void setBooleanOk(boolean value);
        
        public void setStringOk(String value);
        public String getStringOk();
    }
}
