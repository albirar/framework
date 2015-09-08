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

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import java.util.Vector;

import org.junit.Assert;
import org.junit.Test;

import cat.albirar.framework.sets.ISet;
import cat.albirar.framework.sets.impl.models.TestModelRoot;

/**
 * Test for {@link SetDefaultImpl}.
 * @author Octavi Fornés ofornes@albirar.cat
 * @since 2.1.0
 */
public class SetDefaultImplTest
{
    /** An unknown one level property name for model {@link TestModelRoot}. */
    protected static final String UNKNOWN_1L_PROPERTY = "unknown";
    /** An unknown one level property name for model {@link TestModelRoot}. */
    protected static final String UNKNOWN_2L_PROPERTY = "thirdLevelModelProperty.unknown";
    /** An unknown one level property name for model {@link TestModelRoot}. */
    protected static final String UNKNOWN_3L_PROPERTY = "secondLevelModelProperty.thirdLevel2ModelProperty.unknown";
    /** An incorrect one level property name for model {@link TestModelRoot}. */
    protected static final String INCORRECT_1L_PROPERTY = "3Df%";
    /** An incorrect 2 level property name for model {@link TestModelRoot}. */
    protected static final String INCORRECT_2L_PROPERTY = "thirdLevelModelProperty." + INCORRECT_1L_PROPERTY;
    /** An incorrect 3 level property name for model {@link TestModelRoot}. */
    protected static final String INCORRECT_3L_PROPERTY = "secondLevelModelProperty.thirdLevel2ModelProperty." + INCORRECT_1L_PROPERTY;
    /** An known one level property name for model {@link TestModelRoot}. */
    protected static final String KNOWN_1L_PROPERTY = "stringProperty";
    /** An known one level property name for model {@link TestModelRoot}. */
    protected static final String KNOWN2_1L_PROPERTY = "intProperty";
    /** An known two level property name for model {@link TestModelRoot}. */
    protected static final String KNOWN_2L_PROPERTY = "thirdLevelModelProperty.int3Property";
    /** An known three level property name for model {@link TestModelRoot}. */
    protected static final String KNOWN_3L_PROPERTY = "secondLevelModelProperty.thirdLevel2ModelProperty.string3Property";
    
    protected static final String [] EMPTY_COLLECTION = {};
    protected static final String [] INCORRECT_COLLECTION_N = {null};
    protected static final String [] INCORRECT_COLLECTION_E = {""};
    protected static final String [] INCORRECT_COLLECTION_W = {"  "};
    protected static final String [] UNKNOWN_L1_COLLECTION = { UNKNOWN_1L_PROPERTY }; 
    protected static final String [] UNKNOWN_L2_COLLECTION = { UNKNOWN_2L_PROPERTY }; 
    protected static final String [] UNKNOWN_L3_COLLECTION = { UNKNOWN_3L_PROPERTY }; 
    protected static final String [] CORRECT_L1_AND_INCORRECT_COLLECTION_N = {KNOWN_1L_PROPERTY, null };
    protected static final String [] CORRECT_L1_AND_INCORRECT_COLLECTION_E = {KNOWN_1L_PROPERTY, "" };
    protected static final String [] CORRECT_L1_AND_INCORRECT_COLLECTION_W = {KNOWN_1L_PROPERTY, "  " };
    protected static final String [] CORRECT_L2_AND_INCORRECT_COLLECTION_N = {KNOWN_2L_PROPERTY, null };
    protected static final String [] CORRECT_L2_AND_INCORRECT_COLLECTION_E = {KNOWN_2L_PROPERTY, "" };
    protected static final String [] CORRECT_L2_AND_INCORRECT_COLLECTION_W = {KNOWN_2L_PROPERTY, "  " };
    protected static final String [] CORRECT_L3_AND_INCORRECT_COLLECTION_N = {KNOWN_3L_PROPERTY, null };
    protected static final String [] CORRECT_L3_AND_INCORRECT_COLLECTION_E = {KNOWN_3L_PROPERTY, "" };
    protected static final String [] CORRECT_L3_AND_INCORRECT_COLLECTION_W = {KNOWN_3L_PROPERTY, "  " };
    protected static final String [] CORRECT_L1_COLLECTION = {KNOWN_1L_PROPERTY };
    protected static final String [] CORRECT_L2_COLLECTION = {KNOWN_2L_PROPERTY };
    protected static final String [] CORRECT_L3_COLLECTION = {KNOWN_3L_PROPERTY };

    protected static final String [] CORRECT_MIXED_COLLECTION = {KNOWN_1L_PROPERTY, KNOWN_2L_PROPERTY, KNOWN_3L_PROPERTY };

    protected static final String [] CORRECT_MIXED_COLLECTION_TWICE = {KNOWN_1L_PROPERTY, KNOWN_3L_PROPERTY, KNOWN_2L_PROPERTY, KNOWN2_1L_PROPERTY };

   /**
     * Transform an array into a collection.
     * @param array The array
     * @return The resulting collection
     */
    private Collection<String> fromArray(String [] array)
    {
        List<String> col;
        
        col = new Vector<String>();
        for(String o : array)
        {
            col.add(o);
        }
        return col;
    }
    /**
     * New instance of target test object.
     * @param rootModel The root model for set
     * @return The instance
     */
    @SuppressWarnings("unchecked")
    protected <T, S extends ISet<T>> S instance(Class<T> rootModel, Object ... args)
    {
        return (S) new SetDefaultImpl<T>(rootModel);
    }
    /**
     * Returns the 'properties' fragment start
     * @param set The set to operate to
     * @return The corresponding fragment of 'properties' on {@link Object#toString()} method.
     */
    protected <T, S extends ISet<T>> String stringPropsStart(S set)
    {
        return "modelRoot=";
    }
    /**
     * Test for {@link ISet#SetDefaultImpl(Class)} with null argument.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testConstructionWithNull()
    {
        instance(null);
    }
    /**
     * Test for {@link ISet#SetDefaultImpl(Class)} with a valid argument.
     */
    @Test public void testConstructionWithModel()
    {
        ISet<TestModelRoot> set;
        
        set = instance(TestModelRoot.class);
        Assert.assertEquals(TestModelRoot.class, set.getModelRoot());
    }
    /**
     * Test {@link ISet#add(String)} with null argument.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testAddWithNull()
    {
        ISet<TestModelRoot> set;
        
        set = instance(TestModelRoot.class);
        set.add(null);
    }
    /**
     * Test {@link ISet#add(String)} with empty string argument.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testAddWithEmptyString()
    {
        ISet<TestModelRoot> set;
        
        set = instance(TestModelRoot.class);
        set.add("");
    }
    /**
     * Test {@link ISet#add(String)} with whitespace string argument.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testAdd2LWithEmptyString()
    {
        ISet<TestModelRoot> set;
        
        set = instance(TestModelRoot.class);
        set.add(KNOWN_2L_PROPERTY + ".");
    }
    /**
     * Test {@link ISet#add(String)} with whitespace string argument.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testAdd3LWithEmptyString()
    {
        ISet<TestModelRoot> set;
        
        set = instance(TestModelRoot.class);
        set.add(KNOWN_3L_PROPERTY + ".");
    }
    /**
     * Test {@link ISet#add(String)} with whitespace string argument.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testAddWithWhitespaceString()
    {
        ISet<TestModelRoot> set;
        
        set = instance(TestModelRoot.class);
        set.add("   ");
    }
    /**
     * Test {@link ISet#add(String)} with whitespace string argument.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testAdd2LWithWhitespaceString()
    {
        ISet<TestModelRoot> set;
        
        set = instance(TestModelRoot.class);
        set.add(KNOWN_2L_PROPERTY + ".    ");
    }
    /**
     * Test {@link ISet#add(String)} with whitespace string argument.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testAdd3LWithWhitespaceString()
    {
        ISet<TestModelRoot> set;
        
        set = instance(TestModelRoot.class);
        set.add(KNOWN_3L_PROPERTY + ".    ");
    }
    /**
     * Test {@link ISet#add(String)} with a simple dot string argument.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testAddWithDotStartString()
    {
        ISet<TestModelRoot> set;
        
        set = instance(TestModelRoot.class);
        set.add(".");
    }
    /**
     * Test {@link ISet#add(String)} with a property path of 1 level started with dot string argument.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testAdd1LWithDotStartString()
    {
        ISet<TestModelRoot> set;
        
        set = instance(TestModelRoot.class);
        set.add("." + KNOWN_1L_PROPERTY);
    }
    /**
     * Test {@link ISet#add(String)} with a property path of 2 level started with dot string argument.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testAdd2LWithDotStartString()
    {
        ISet<TestModelRoot> set;
        
        set = instance(TestModelRoot.class);
        set.add("." + KNOWN_2L_PROPERTY);
    }
    /**
     * Test {@link ISet#add(String)} with a property path of 3 level started with dot string argument.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testAdd3LWithDotStartString()
    {
        ISet<TestModelRoot> set;
        
        set = instance(TestModelRoot.class);
        set.add("." + KNOWN_3L_PROPERTY);
    }
    /**
     * Test {@link ISet#add(String)} with incorrect one level property name argument.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testAddWithIncorrectOneLevelProperty()
    {
        ISet<TestModelRoot> set;
        
        set = instance(TestModelRoot.class);
        set.add(INCORRECT_1L_PROPERTY);
    }
    /**
     * Test {@link ISet#add(String)} with incorrect two level property name argument.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testAddWithIncorrectTwoLevelProperty()
    {
        ISet<TestModelRoot> set;
        
        set = instance(TestModelRoot.class);
        set.add(INCORRECT_2L_PROPERTY);
    }
    /**
     * Test {@link ISet#add(String)} with incorrect three level property name argument.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testAddWithIncorrectThreeLevelProperty()
    {
        ISet<TestModelRoot> set;
        
        set = instance(TestModelRoot.class);
        set.add(INCORRECT_3L_PROPERTY);
    }
    /**
     * Test {@link ISet#add(String)} with unknown property name argument.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testAddWithUnknownOneLevelProperty()
    {
        ISet<TestModelRoot> set;
        
        set = instance(TestModelRoot.class);
        set.add(UNKNOWN_1L_PROPERTY);
    }
    /**
     * Test {@link ISet#add(String)} with unknown two level property name argument.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testAddWithUnknownTwoLevelProperty()
    {
        ISet<TestModelRoot> set;
        
        set = instance(TestModelRoot.class);
        set.add(UNKNOWN_2L_PROPERTY);
    }
    /**
     * Test {@link ISet#add(String)} with unknown three level property name argument.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testAddWithUnknownThreeLevelProperty()
    {
        ISet<TestModelRoot> set;
        
        set = instance(TestModelRoot.class);
        set.add(UNKNOWN_3L_PROPERTY);
    }
    /**
     * Test {@link ISet#add(String)} with known one level property name argument.
     */
    @Test public void testAddWithKnownOneLevelProperty()
    {
        ISet<TestModelRoot> set;
        
        set = instance(TestModelRoot.class);
        set.add(KNOWN_1L_PROPERTY);
        Assert.assertFalse(set.isEmpty());
        Assert.assertEquals(1, set.size());
        Assert.assertTrue(set.contains(KNOWN_1L_PROPERTY));
    }
    /**
     * Test {@link ISet#add(String)} with known two level property name argument.
     */
    @Test public void testAddWithKnownTwoLevelProperty()
    {
        ISet<TestModelRoot> set;
        
        set = instance(TestModelRoot.class);
        set.add(KNOWN_2L_PROPERTY);
        Assert.assertFalse(set.isEmpty());
        Assert.assertEquals(1, set.size());
        Assert.assertTrue(set.contains(KNOWN_2L_PROPERTY));
    }
    /**
     * Test {@link ISet#add(String)} with known three level property name argument.
     */
    @Test public void testAddWithKnownThreeLevelProperty()
    {
        ISet<TestModelRoot> set;
        
        set = instance(TestModelRoot.class);
        set.add(KNOWN_3L_PROPERTY);
        Assert.assertFalse(set.isEmpty());
        Assert.assertEquals(1, set.size());
        Assert.assertTrue(set.contains(KNOWN_3L_PROPERTY));
    }

    /**
     * Test {@link ISet#addAll(java.util.Collection)} with null.
     */
    @Test public void testAddCollectionNull()
    {
        ISet<TestModelRoot> set;
        
        set = instance(TestModelRoot.class);
        Assert.assertFalse(set.addAll(null));
        Assert.assertTrue(set.isEmpty());
    }
    /**
     * Test {@link ISet#addAll(java.util.Collection)} with empty collection.
     */
    @Test public void testAddCollectionEmtpy()
    {
        ISet<TestModelRoot> set;
        
        set = instance(TestModelRoot.class);
        Assert.assertFalse(set.addAll(fromArray(EMPTY_COLLECTION)));
        Assert.assertTrue(set.isEmpty());
    }
    /**
     * Test {@link ISet#addAll(java.util.Collection)} with a null property on collection.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testAddCollectionIncorrectPropertyNull()
    {
        ISet<TestModelRoot> set;
        
        set = instance(TestModelRoot.class);
        set.addAll(fromArray(INCORRECT_COLLECTION_N));
    }
    /**
     * Test {@link ISet#addAll(java.util.Collection)} with an empty property on collection.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testAddCollectionIncorrectPropertyEmtpy()
    {
        ISet<TestModelRoot> set;
        
        set = instance(TestModelRoot.class);
        set.addAll(fromArray(INCORRECT_COLLECTION_E));
    }
    /**
     * Test {@link ISet#addAll(java.util.Collection)} with a whitespace property on collection.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testAddCollectionIncorrectPropertyWhitespace()
    {
        ISet<TestModelRoot> set;
        
        set = instance(TestModelRoot.class);
        set.addAll(fromArray(INCORRECT_COLLECTION_W));
    }
    /**
     * Test {@link ISet#addAll(java.util.Collection)} with an unknown L1 property on collection.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testAddCollectionUnknownL1Property()
    {
        ISet<TestModelRoot> set;
        
        set = instance(TestModelRoot.class);
        set.addAll(fromArray(UNKNOWN_L1_COLLECTION));
    }
    /**
     * Test {@link ISet#addAll(java.util.Collection)} with an unknown L2 property on collection.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testAddCollectionUnknownL2Property()
    {
        ISet<TestModelRoot> set;
        
        set = instance(TestModelRoot.class);
        set.addAll(fromArray(UNKNOWN_L2_COLLECTION));
    }
    /**
     * Test {@link ISet#addAll(java.util.Collection)} with an unknown L3 property on collection.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testAddCollectionUnknownL3Property()
    {
        ISet<TestModelRoot> set;
        
        set = instance(TestModelRoot.class);
        set.addAll(fromArray(UNKNOWN_L3_COLLECTION));
    }
    /**
     * Test {@link ISet#addAll(java.util.Collection)} with correct level one and incorrect null property on collection.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testAddCollectionCorrectL1AndIncorrectNullProperty()
    {
        ISet<TestModelRoot> set;
        
        set = instance(TestModelRoot.class);
        set.addAll(fromArray(CORRECT_L1_AND_INCORRECT_COLLECTION_N));
    }
    /**
     * Test {@link ISet#addAll(java.util.Collection)} with correct level one and incorrect empty property on collection.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testAddCollectionCorrectL1AndIncorrectEmptyProperty()
    {
        ISet<TestModelRoot> set;
        
        set = instance(TestModelRoot.class);
        set.addAll(fromArray(CORRECT_L1_AND_INCORRECT_COLLECTION_E));
    }
    /**
     * Test {@link ISet#addAll(java.util.Collection)} with correct level one and incorrect whitespace property on collection.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testAddCollectionCorrectL1AndIncorrectWhitespaceProperty()
    {
        ISet<TestModelRoot> set;
        
        set = instance(TestModelRoot.class);
        set.addAll(fromArray(CORRECT_L1_AND_INCORRECT_COLLECTION_W));
    }
    /**
     * Test {@link ISet#addAll(java.util.Collection)} with correct level two and incorrect null property on collection.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testAddCollectionCorrectL2AndIncorrectNullProperty()
    {
        ISet<TestModelRoot> set;
        
        set = instance(TestModelRoot.class);
        set.addAll(fromArray(CORRECT_L2_AND_INCORRECT_COLLECTION_N));
    }
    /**
     * Test {@link ISet#addAll(java.util.Collection)} with correct level two and incorrect empty property on collection.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testAddCollectionCorrectL2AndIncorrectEmptyProperty()
    {
        ISet<TestModelRoot> set;
        
        set = instance(TestModelRoot.class);
        set.addAll(fromArray(CORRECT_L2_AND_INCORRECT_COLLECTION_E));
    }
    /**
     * Test {@link ISet#addAll(java.util.Collection)} with correct level two and incorrect whitespace property on collection.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testAddCollectionCorrectL2AndIncorrectWhitespaceProperty()
    {
        ISet<TestModelRoot> set;
        
        set = instance(TestModelRoot.class);
        set.addAll(fromArray(CORRECT_L2_AND_INCORRECT_COLLECTION_W));
    }
    /**
     * Test {@link ISet#addAll(java.util.Collection)} with correct level three and incorrect null property on collection.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testAddCollectionCorrectL3AndIncorrectNullProperty()
    {
        ISet<TestModelRoot> set;
        
        set = instance(TestModelRoot.class);
        set.addAll(fromArray(CORRECT_L3_AND_INCORRECT_COLLECTION_N));
    }
    /**
     * Test {@link ISet#addAll(java.util.Collection)} with correct level three and incorrect empty property on collection.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testAddCollectionCorrectL3AndIncorrectEmptyProperty()
    {
        ISet<TestModelRoot> set;
        
        set = instance(TestModelRoot.class);
        set.addAll(fromArray(CORRECT_L3_AND_INCORRECT_COLLECTION_E));
    }
    /**
     * Test {@link ISet#addAll(java.util.Collection)} with correct level three and incorrect whitespace property on collection.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testAddCollectionCorrectL3AndIncorrectWhitespaceProperty()
    {
        ISet<TestModelRoot> set;
        
        set = instance(TestModelRoot.class);
        set.addAll(fromArray(CORRECT_L3_AND_INCORRECT_COLLECTION_W));
    }
    /**
     * Test {@link ISet#addAll(java.util.Collection)} with correct level one property on collection.
     */
    @Test public void testAddCollectionCorrectL1()
    {
        ISet<TestModelRoot> set;
        
        set = instance(TestModelRoot.class);
        Assert.assertTrue(set.addAll(fromArray(CORRECT_L1_COLLECTION)));
        Assert.assertFalse(set.isEmpty());
        Assert.assertEquals(1, set.size());
        Assert.assertEquals(CORRECT_L1_COLLECTION[0], set.iterator().next());
    }
    /**
     * Test {@link ISet#addAll(java.util.Collection)} with correct level two property on collection.
     */
    @Test public void testAddCollectionCorrectL2()
    {
        ISet<TestModelRoot> set;
        
        set = instance(TestModelRoot.class);
        Assert.assertTrue(set.addAll(fromArray(CORRECT_L2_COLLECTION)));
        Assert.assertFalse(set.isEmpty());
        Assert.assertEquals(1, set.size());
        Assert.assertEquals(CORRECT_L2_COLLECTION[0], set.iterator().next());
    }
    /**
     * Test {@link ISet#addAll(java.util.Collection)} with correct level three property on collection.
     */
    @Test public void testAddCollectionCorrectL3()
    {
        ISet<TestModelRoot> set;
        
        set = instance(TestModelRoot.class);
        Assert.assertTrue(set.addAll(fromArray(CORRECT_L3_COLLECTION)));
        Assert.assertFalse(set.isEmpty());
        Assert.assertEquals(1, set.size());
        Assert.assertEquals(CORRECT_L3_COLLECTION[0], set.iterator().next());
    }
    /**
     * Test {@link ISet#addAll(java.util.Collection)} with correct level mixed property on collection.
     */
    @Test public void testAddCollectionCorrectMixed()
    {
        ISet<TestModelRoot> set;
        Iterator<String> it, it1;
        TreeSet<String> sCol;
        
        set = instance(TestModelRoot.class);
        Assert.assertTrue(set.addAll(fromArray(CORRECT_MIXED_COLLECTION)));
        Assert.assertFalse(set.isEmpty());
        Assert.assertEquals(CORRECT_MIXED_COLLECTION.length, set.size());
        it = set.iterator();
        // Use a treeset to get the collection in order, as set
        sCol = new TreeSet<String>();
        sCol.addAll(fromArray(CORRECT_MIXED_COLLECTION));
        it1 = sCol.iterator();
        while(it.hasNext())
        {
            Assert.assertEquals(it1.next(), it.next());
        }
    }
    /**
     * Test {@link ISet#addAll(java.util.Collection)} with correct level mixed property on collection for twice.
     */
    @Test public void testAddCollectionCorrectMixedTwice()
    {
        ISet<TestModelRoot> set;
        Iterator<String> it, it1;
        TreeSet<String> sCol;
        
        set = instance(TestModelRoot.class);
        Assert.assertTrue(set.addAll(fromArray(CORRECT_MIXED_COLLECTION_TWICE)));
        Assert.assertFalse(set.addAll(fromArray(CORRECT_MIXED_COLLECTION_TWICE)));
        Assert.assertFalse(set.isEmpty());
        Assert.assertEquals(CORRECT_MIXED_COLLECTION_TWICE.length, set.size());
        it = set.iterator();
        // Use a treeset to get the collection in order, as set
        sCol = new TreeSet<String>();
        sCol.addAll(fromArray(CORRECT_MIXED_COLLECTION_TWICE));
        it1 = sCol.iterator();
        while(it.hasNext())
        {
            Assert.assertEquals(it1.next(), it.next());
        }
    }
    /**
     * Test {@link ISet#toString()} whit an empty set.
     */
    @Test public void testToStringEmtpySet()
    {
        String s;
        ISet<TestModelRoot> set;
        
        set = instance(TestModelRoot.class);
        s = set.toString();
        Assert.assertNotNull(s);
        Assert.assertTrue(s.startsWith(set.getClass().getSimpleName() + " [" + stringPropsStart(set)));
        Assert.assertTrue(s.endsWith(TestModelRoot.class.getName() + ", properties=[]]"));
    }
    /**
     * Test {@link ISet#toString()} whit one property on set.
     */
    @Test public void testToStringOneItemSet()
    {
        String s;
        ISet<TestModelRoot> set;
        
        set = instance(TestModelRoot.class);
        set.add(KNOWN2_1L_PROPERTY);
        s = set.toString();
        Assert.assertNotNull(s);
        Assert.assertTrue(s.startsWith(set.getClass().getSimpleName() + " [" + stringPropsStart(set)));
        Assert.assertTrue(s.endsWith(TestModelRoot.class.getName() + ", properties=["
                + KNOWN2_1L_PROPERTY + "]]"));
    }
    /**
     * Test {@link ISet#toString()} whit several property on set.
     */
    @Test public void testToStringSeveralItemSet()
    {
        String s;
        ISet<TestModelRoot> set;
        
        set = instance(TestModelRoot.class);
        set.add(KNOWN2_1L_PROPERTY);
        set.add(KNOWN_1L_PROPERTY);
        s = set.toString();
        Assert.assertNotNull(s);
        Assert.assertTrue(s.startsWith(set.getClass().getSimpleName() + " [" + stringPropsStart(set)));
        Assert.assertTrue(s.endsWith(TestModelRoot.class.getName() + ", properties=["
                + KNOWN2_1L_PROPERTY + ", " + KNOWN_1L_PROPERTY + "]]"));
    }
}
