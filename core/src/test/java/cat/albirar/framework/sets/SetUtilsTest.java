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

package cat.albirar.framework.sets;

import org.junit.Assert;
import org.junit.Test;

import cat.albirar.framework.sets.ISet;
import cat.albirar.framework.sets.ISetBuilder;
import cat.albirar.framework.sets.SetUtils;
import cat.albirar.framework.sets.impl.models.TestModelRoot;

/**
 * Test for {@link SetUtils}.
 * @author Octavi Fornés ofornes@albirar.cat
 * @since 2.1.0
 */
public class SetUtilsTest
{
    /**
     * Test for {@link SetUtils#instantiateSetFor(Class)} with null argument.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testInstantiateSetNull()
    {
        SetUtils.instantiateSetFor(null);
    }
    /**
     * Test for {@link SetUtils#instantiateSetFor(Class)} with correct argument.
     */
    @Test public void testInstantiateSet()
    {
        ISet<TestModelRoot> set;
        
        set = SetUtils.instantiateSetFor(TestModelRoot.class);
        Assert.assertNotNull(set);
        Assert.assertEquals(TestModelRoot.class, set.getModelRoot());
    }
    /**
     * Test for {@link SetUtils#instantiateBuilderFor(Class)} with null argument.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testInstantiateBuilderNull()
    {
        SetUtils.instantiateBuilderFor(null);
    }
    /**
     * Test for {@link SetUtils#instantiateBuilderFor(Class)} with correct argument.
     */
    @Test public void testInstantiateBuilder()
    {
        ISetBuilder<TestModelRoot> b;
        
        b = SetUtils.instantiateBuilderFor(TestModelRoot.class);
        Assert.assertNotNull(b);
        Assert.assertEquals(TestModelRoot.class, b.getModelRoot());
    }
    
    /**
     * Test for {@link SetUtils#checkPathForModel(Class, String)} with all arguments null.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testPathForModelNullNull()
    {
        SetUtils.checkPathForModel(null, null);
    }
    /**
     * Test for {@link SetUtils#checkPathForModel(Class, String)} with path argument null.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testPathForModelOkNull()
    {
        SetUtils.checkPathForModel(TestModelRoot.class, null);
    }
    /**
     * Test for {@link SetUtils#checkPathForModel(Class, String)} with class argument null.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testPathForModelNullOk()
    {
        SetUtils.checkPathForModel(null, "prop");
    }
    /**
     * Test for {@link SetUtils#checkPathForModel(Class, String)} with invalid path argument.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testPathForModelInvalidPath()
    {
        SetUtils.checkPathForModel(TestModelRoot.class, "x(F");
    }
    /**
     * Test for {@link SetUtils#checkPathForModel(Class, String)} with inexistent path.
     */
    @Test public void testPathForModelNonExistentProperty()
    {
        Assert.assertFalse(SetUtils.checkPathForModel(TestModelRoot.class, "prop"));
    }
    /**
     * Test for {@link SetUtils#checkPathForModel(Class, String)} with existent one level path.
     */
    @Test public void testPathForModelExistent1LProperty()
    {
        Assert.assertTrue(SetUtils.checkPathForModel(TestModelRoot.class, "intProperty"));
    }
    /**
     * Test for {@link SetUtils#checkPathForModel(Class, String)} with inexistent two level path.
     */
    @Test public void testPathForModelNonExistent2LProperty()
    {
        Assert.assertFalse(SetUtils.checkPathForModel(TestModelRoot.class, "secondLevelModelProperty.prop"));
    }
    /**
     * Test for {@link SetUtils#checkPathForModel(Class, String)} with existent two level path.
     */
    @Test public void testPathForModelExistent2LProperty()
    {
        Assert.assertTrue(SetUtils.checkPathForModel(TestModelRoot.class, "secondLevelModelProperty.string2Property"));
    }
}
