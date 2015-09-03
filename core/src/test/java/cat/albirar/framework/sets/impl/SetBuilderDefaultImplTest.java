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
 * Copyright (C) 2015 Octavi Fornés
 */

package cat.albirar.framework.sets.impl;

import org.junit.Assert;
import org.junit.Test;

import cat.albirar.framework.sets.ISet;
import cat.albirar.framework.sets.ISetBuilder;
import cat.albirar.framework.sets.SetUtils;
import cat.albirar.framework.sets.impl.models.TestModelRoot;

/**
 * Test for {@link SetBuilderDefaultImpl}.
 * @author Octavi Fornés ofornes@albirar.cat
 * @since 2.1.0
 */
public class SetBuilderDefaultImplTest
{
    private static final String NAME_2L_MODEL = "secondLevelModelProperty";
    private static final String NAME_3L_MODEL = "thirdLevelModelProperty";
    private static final String NAME_2L3L_MODEL = "thirdLevel2ModelProperty";
    private static final String NAME_INT_2_PROPERTY = "int2Property";
    private static final String NAME_INT_3_PROPERTY = "int3Property";
    private static final String NAME_INT_PROPERTY = "intProperty";
    private static final String NAME_STRING_PROPERTY = "stringProperty";
    private static final String NAME_STRING_3_PROPERTY = "string3Property";
    private static final String NAME_3L_MODEL_INT3_PROPERTY = NAME_3L_MODEL + "." + NAME_INT_3_PROPERTY;
    private static final String NAME_2L_MODEL_3LEVEL_MODEL_INT3_PROPERTY = NAME_2L_MODEL + "." + NAME_2L3L_MODEL + "." + NAME_INT_3_PROPERTY;
    private static final String NAME_2L_MODEL_3LEVEL_MODEL_STRING3_PROPERTY = NAME_2L_MODEL + "." + NAME_2L3L_MODEL + "." + NAME_STRING_3_PROPERTY;
    private static final String NAME_2LEVEL_MODEL_INT2_PROPERTY = NAME_2L_MODEL + "." + NAME_INT_2_PROPERTY;
    private static final String NAME_3L2_MODEL_INT3_PROPERTY = NAME_2L3L_MODEL + "." + NAME_INT_3_PROPERTY;
    /**
     * Test builder with no javaBean model.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testBuildWithNoJavaBeanModel()
    {
        new SetBuilderDefaultImpl(int.class)
            .addProperty("propertyOne")
            .build();
    }
    /**
     * Test builder for empty property set.
     */
    @Test public void testBuildEmpty()
    {
        ISet set;
        
        set = new SetBuilderDefaultImpl(TestModelRoot.class)
                .build();
        
        Assert.assertNotNull(set);
        Assert.assertTrue(set.isEmpty());
    }
    /**
     * Test for build with simple path (only property names, not dotted paths).
     */
    @Test public void testBuildSimplePath()
    {
        ISet set;
        String [] props;
        
        set = new SetBuilderDefaultImpl(TestModelRoot.class)
                .addProperty(NAME_INT_PROPERTY)
                .addProperty(NAME_STRING_PROPERTY)
                .build();
        
        Assert.assertNotNull(set);
        Assert.assertFalse(set.isEmpty());
        Assert.assertEquals(2, set.size());
        props = set.toArray(new String []{});
        Assert.assertEquals(NAME_INT_PROPERTY, props[0]);
        Assert.assertEquals(NAME_STRING_PROPERTY, props[1]);
    }
    /**
     * Test methods related to path stack management.
     */
    @Test public void testPathStackManagement()
    {
        ISetBuilder setBuilder;
        
        setBuilder = new SetBuilderDefaultImpl(TestModelRoot.class);
        // should to be null
        Assert.assertNull(setBuilder.peekPropertyPathStack());
        // Current property path at root level should to be empty string
        Assert.assertEquals("", setBuilder.getCurrentPropertyPath());
        // push
        setBuilder.pushPropertyPath(NAME_2L_MODEL);
        // should to be the current property path
        Assert.assertEquals(NAME_2L_MODEL, setBuilder.peekPropertyPathStack());
        // Current property path...
        Assert.assertEquals(NAME_2L_MODEL, setBuilder.getCurrentPropertyPath());
        // push another level
        setBuilder.pushPropertyPath(NAME_2L3L_MODEL);
        // should to be the new property path
        Assert.assertEquals(NAME_2L3L_MODEL, setBuilder.peekPropertyPathStack());
        // Current property path...
        Assert.assertEquals(NAME_2L_MODEL + "." + NAME_2L3L_MODEL, setBuilder.getCurrentPropertyPath());
        // Pop
        setBuilder.popPropertyPath();
        // should to be the first push
        Assert.assertEquals(NAME_2L_MODEL, setBuilder.peekPropertyPathStack());
        // Current property path...
        Assert.assertEquals(NAME_2L_MODEL, setBuilder.getCurrentPropertyPath());
        // Pop
        setBuilder.popPropertyPath();
        // should to be null
        Assert.assertNull(setBuilder.peekPropertyPathStack());
        // Current property path at root level should to be empty string
        Assert.assertEquals("", setBuilder.getCurrentPropertyPath());
        // Check more pops
        setBuilder.popPropertyPath();
        setBuilder.popPropertyPath();
        setBuilder.popPropertyPath();
        setBuilder.popPropertyPath();
        setBuilder.popPropertyPath();
        // should to be null
        Assert.assertNull(setBuilder.peekPropertyPathStack());
        // Current property path at root level should to be empty string
        Assert.assertEquals("", setBuilder.getCurrentPropertyPath());
        // push
        setBuilder.pushPropertyPath(NAME_2L_MODEL);
        // should to be the current property path, check invariability
        Assert.assertEquals(NAME_2L_MODEL, setBuilder.peekPropertyPathStack());
        Assert.assertEquals(NAME_2L_MODEL, setBuilder.peekPropertyPathStack());
        Assert.assertEquals(NAME_2L_MODEL, setBuilder.peekPropertyPathStack());
        Assert.assertEquals(NAME_2L_MODEL, setBuilder.peekPropertyPathStack());
        Assert.assertEquals(NAME_2L_MODEL, setBuilder.peekPropertyPathStack());
        // Current property path invariability...
        Assert.assertEquals(NAME_2L_MODEL, setBuilder.getCurrentPropertyPath());
        Assert.assertEquals(NAME_2L_MODEL, setBuilder.getCurrentPropertyPath());
        Assert.assertEquals(NAME_2L_MODEL, setBuilder.getCurrentPropertyPath());
        Assert.assertEquals(NAME_2L_MODEL, setBuilder.getCurrentPropertyPath());
    }
    /**
     * Test builder for add an inexistent property.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testBuildInexistentProperty()
    {
        new SetBuilderDefaultImpl(TestModelRoot.class)
            .addProperty("propertyOne")
            .build();
    }
    /**
     * Test for build with complex path, that is, property names and dotted paths property names.
     */
    @Test public void testBuildComplexPath()
    {
        ISet set;
        String [] props;
        
        set = new SetBuilderDefaultImpl(TestModelRoot.class)
                .addProperty(NAME_2LEVEL_MODEL_INT2_PROPERTY)
                .addProperty(NAME_2L_MODEL_3LEVEL_MODEL_INT3_PROPERTY)
                .addProperty(NAME_3L_MODEL_INT3_PROPERTY)
                .build();
        
        Assert.assertNotNull(set);
        Assert.assertFalse(set.isEmpty());
        Assert.assertEquals(3, set.size());
        props = set.toArray(new String []{});
        Assert.assertEquals(NAME_2LEVEL_MODEL_INT2_PROPERTY, props[0]);
        Assert.assertEquals(NAME_2L_MODEL_3LEVEL_MODEL_INT3_PROPERTY, props[1]);
        Assert.assertEquals(NAME_3L_MODEL_INT3_PROPERTY, props[2]);
    }
    /**
     * Test for push with an inexistent property.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testPushInexistentProperty()
    {
        new SetBuilderDefaultImpl(TestModelRoot.class)
            .pushPropertyPath("xxx")
            .build();
    }
    /**
     * Test for push with an inexistent property at second level.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testPush2LInexistentProperty()
    {
        new SetBuilderDefaultImpl(TestModelRoot.class)
            .pushPropertyPath(NAME_2L_MODEL)
            .pushPropertyPath("xxx")
            .build();
    }
    /**
     * Test for empty build with a correct push.
     */
    @Test public void testBuildPushEmpty()
    {
        ISet set;
        
        set = new SetBuilderDefaultImpl(TestModelRoot.class)
            .pushPropertyPath(NAME_2L_MODEL)
            .build();

        Assert.assertNotNull(set);
        Assert.assertTrue(set.isEmpty());
    }
    /**
     * Test for build with a push and some correct properties at root and second level.
     */
    public void testBuildPushSimplePath()
    {
        ISet set;
        String [] props;
        
        set = new SetBuilderDefaultImpl(TestModelRoot.class)
                .addProperty(NAME_INT_PROPERTY)
                .addProperty(NAME_STRING_PROPERTY)
                .pushPropertyPath(NAME_2L_MODEL)
                .addProperty(NAME_INT_2_PROPERTY)
                .build();
        
        Assert.assertNotNull(set);
        Assert.assertFalse(set.isEmpty());
        Assert.assertEquals(3, set.size());
        props = set.toArray(new String []{});
        Assert.assertEquals(NAME_INT_PROPERTY, props[0]);
        Assert.assertEquals(NAME_STRING_PROPERTY, props[1]);
        Assert.assertEquals(NAME_2LEVEL_MODEL_INT2_PROPERTY, props[2]);
    }
    /**
     * Test for build with a push and some correct properties at root and second level.
     */
    @Test public void testBuildPushAndPopComplexPath()
    {
        ISet set;
        String [] props;
        
        set = new SetBuilderDefaultImpl(TestModelRoot.class)
                .addProperty(NAME_INT_PROPERTY)     // 1st level
                .pushPropertyPath(NAME_2L_MODEL)    // push to 2nd level
                .addProperty(NAME_3L2_MODEL_INT3_PROPERTY)  // complex from 2nd level to 3rd level
                .popPropertyPath()                  // pop to root
                .addProperty(NAME_STRING_PROPERTY)
                .build();
        
        Assert.assertNotNull(set);
        Assert.assertFalse(set.isEmpty());
        Assert.assertEquals(3, set.size());
        props = set.toArray(new String []{});
        Assert.assertEquals(NAME_INT_PROPERTY, props[0]);
        Assert.assertEquals(NAME_2L_MODEL_3LEVEL_MODEL_INT3_PROPERTY, props[1]);
        Assert.assertEquals(NAME_STRING_PROPERTY, props[2]);
    }
    /**
     * Test for build with push and complex path and pop beyond the root level.
     */
    @Test public void testBuildPushAndPopBeyondRoot()
    {
        ISet set;
        String [] props;
        
        set = new SetBuilderDefaultImpl(TestModelRoot.class)
                .addProperty(NAME_INT_PROPERTY)     // 1st level
                .pushPropertyPath(NAME_2L_MODEL)    // push to 2nd level
                .addProperty(NAME_3L2_MODEL_INT3_PROPERTY)  // complex from 2nd level to 3rd level
                .pushPropertyPath(NAME_2L3L_MODEL)  // push to 3rd level
                .addProperty(NAME_STRING_3_PROPERTY)
                .popPropertyPath()                  // pop to root
                .popPropertyPath()                  // pop to root
                .popPropertyPath()                  // pop to root
                .popPropertyPath()                  // pop to root
                .popPropertyPath()                  // pop to root
                .addProperty(NAME_STRING_PROPERTY)
                .build();
        
        Assert.assertNotNull(set);
        Assert.assertFalse(set.isEmpty());
        Assert.assertEquals(4, set.size());
        props = set.toArray(new String []{});
        Assert.assertEquals(NAME_INT_PROPERTY, props[0]);
        Assert.assertEquals(NAME_2L_MODEL_3LEVEL_MODEL_INT3_PROPERTY, props[1]);
        Assert.assertEquals(NAME_2L_MODEL_3LEVEL_MODEL_STRING3_PROPERTY, props[2]);
        Assert.assertEquals(NAME_STRING_PROPERTY, props[3]);
    }
    /**
     * Test for {@link SetUtils#instantiateBuilderFor(Class)}.
     */
    @Test public void testBuildUtilsNewInstance()
    {
        ISetBuilder builder;
        ISet set;
        String [] props;
        
        builder = SetUtils.instantiateBuilderFor(TestModelRoot.class);
        builder.addProperty(NAME_INT_PROPERTY);
        set = builder.build();
        
        Assert.assertNotNull(set);
        Assert.assertFalse(set.isEmpty());
        props = set.toArray(new String [] {} );
        Assert.assertEquals(1, set.size());
        props = set.toArray(new String []{});
        Assert.assertEquals(NAME_INT_PROPERTY, props[0]);
    }
}
