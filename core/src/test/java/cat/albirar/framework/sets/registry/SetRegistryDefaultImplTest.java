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
 * Copyright (C) 2015 Octavi Fornés
 */

package cat.albirar.framework.sets.registry;

import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import cat.albirar.framework.sets.impl.TestModelRoot;
import cat.albirar.framework.sets.impl.TestModelSecondLevel;
import cat.albirar.framework.sets.registry.impl.NamedSetDefaultImpl;
import cat.albirar.framework.sets.registry.impl.SetRegistryDefaultImpl;

/**
 * Test for {@link SetRegistryDefaultImpl}.
 * 
 * @author Octavi Fornés ofornes@albirar.cat
 * @since 2.1.0
 */
public class SetRegistryDefaultImplTest
{
    /** intProperty of {@link TestModelRoot}. */
    private static final String MODEL_ROOT_INT_PROPERTY = "intProperty";

    public static final String PATH_PACKAGE = SetRegistryDefaultImpl.class.getPackage().getName().replace(".", "/") + "/";

    public static final String NAME_FILE_ERROR_FORMAT_1 = "test-error-format-1.properties";

    public static final String NAME_FILE_ERROR_FORMAT_2 = "test-error-format-2.properties";

    public static final String NAME_FILE_ERROR_FORMAT_3 = "test-error-format-3.properties";

    public static final String NAME_FILE_ERROR_CNF = "test-error-classnotfound.properties";

    public static final String NAME_FILE_ERROR_PROP_ERROR = "test-error-property-error.properties";

    public static final String NAME_FILE_OK = "test-ok.properties";

    public static final String NAME_FILE_OK_DUPLICATES = "test-ok-duplicates.properties";

    public static final String Q_NAME_FILE_NOT_FOUND = "xxx.properties";

    public static final String Q_NAME_FILE_ERROR_FORMAT_1 = PATH_PACKAGE + NAME_FILE_ERROR_FORMAT_1;

    public static final String Q_NAME_FILE_ERROR_FORMAT_2 = PATH_PACKAGE + NAME_FILE_ERROR_FORMAT_2;

    public static final String Q_NAME_FILE_ERROR_FORMAT_3 = PATH_PACKAGE + NAME_FILE_ERROR_FORMAT_3;

    public static final String Q_NAME_FILE_ERROR_CNF = PATH_PACKAGE + NAME_FILE_ERROR_CNF;

    public static final String Q_NAME_FILE_ERROR_PROP_ERROR = PATH_PACKAGE + NAME_FILE_ERROR_PROP_ERROR;

    public static final String Q_NAME_FILE_OK = PATH_PACKAGE + NAME_FILE_OK;

    public static final String Q_NAME_FILE_OK_DUPLICATES = PATH_PACKAGE + NAME_FILE_OK_DUPLICATES;

    private SetRegistryDefaultImpl registry;

    /**
     * Prepare test.
     */
    @Before
    public void initTest()
    {
        registry = new SetRegistryDefaultImpl();
        registry.clear();
    }

    /**
     * Test {@link SetRegistryDefaultImpl#loadFromResource(org.springframework.core.io.Resource)} with error in format 1
     */
    @Test(expected = IllegalArgumentException.class)
    public void testLoadPropertiesErrorFormat1() throws Exception
    {
        Properties props;

        props = new Properties();
        props.setProperty("set1", "xxx");
        registry.loadFromProperties(props);
    }

    /**
     * Test {@link SetRegistryDefaultImpl#loadFromResource(org.springframework.core.io.Resource)} with error in format 2
     */
    @Test(expected = IllegalArgumentException.class)
    public void testLoadPropertiesErrorFormat2() throws Exception
    {
        Properties props;

        props = new Properties();
        props.setProperty("set2", "");
        registry.loadFromProperties(props);
    }

    /**
     * Test {@link SetRegistryDefaultImpl#loadFromResource(org.springframework.core.io.Resource)} with error in format 3
     */
    @Test(expected = IllegalArgumentException.class)
    public void testLoadPropertiesErrorFormat3() throws Exception
    {
        Properties props;

        props = new Properties();
        props.setProperty("set3", "xxxxxxxx:");
        registry.loadFromProperties(props);
    }

    /**
     * Test {@link SetRegistryDefaultImpl#loadFromResource(org.springframework.core.io.Resource)} with undefined class.
     */
    @Test(expected = ClassNotFoundException.class)
    public void testLoadPropertiesErrorCNFE() throws Exception
    {
        Properties props;

        props = new Properties();
        props.setProperty("setcnfe", "a.b.C:prop1");
        registry.loadFromProperties(props);
    }

    /**
     * Test {@link SetRegistryDefaultImpl#loadFromResource(org.springframework.core.io.Resource)} with undefined
     * properties.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testLoadPropertiesErrorPropertyNotFound() throws Exception
    {
        Properties props;

        props = new Properties();
        props.setProperty("set1", TestModelRoot.class.getName() + ":testpropxx,testpropyy");
        props.setProperty("set2", "cat.albirar.framework.sets.impl.TestModelSecondLevel:testpropxx, testpropyy");
        registry.loadFromProperties(props);
    }

    /**
     * Test {@link SetRegistryDefaultImpl#loadFromResource(org.springframework.core.io.Resource)} without errors.
     */
    @Test
    public void testLoadPropertiesOk() throws Exception
    {
        Properties props;

        props = new Properties();
        props.setProperty("set1", TestModelRoot.class.getName() + ":intProperty,secondLevelModelProperty.int2Property " + ",thirdLevelModelProperty.string3Property "
                + ",secondLevelModelProperty.thirdLevel2ModelProperty.string3Property " + ",stringProperty");
        props.setProperty("set2", TestModelSecondLevel.class.getName() + ":int2Property,string2Property");
        registry.loadFromProperties(props);
        Assert.assertFalse(registry.isEmpty());
        Assert.assertEquals(2, registry.size());
        Assert.assertTrue(registry.containsSet("set1"));
        Assert.assertTrue(registry.containsSet("set2"));
    }

    /**
     * Test {@link SetRegistryDefaultImpl#loadFromResource(org.springframework.core.io.Resource)} without errors but
     * duplicated sets.
     */
    @Test
    public void testLoadPropertiesOkDuplicates() throws Exception
    {
        Properties props;

        props = new Properties();
        props.setProperty("set1", TestModelRoot.class.getName() + ":intProperty,secondLevelModelProperty.int2Property " + ",thirdLevelModelProperty.string3Property "
                + ",secondLevelModelProperty.thirdLevel2ModelProperty.string3Property " + ",stringProperty");
        props.setProperty("set1", TestModelSecondLevel.class.getName() + ":int2Property,string2Property");
        registry.loadFromProperties(props);
        Assert.assertFalse(registry.isEmpty());
        Assert.assertEquals(1, registry.size());
        Assert.assertTrue(registry.containsSet("set1"));
        Assert.assertFalse(registry.containsSet("set2"));
    }

    /**
     * Test {@link SetRegistryDefaultImpl#loadFromResource(org.springframework.core.io.Resource)} with file not found.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testLoadResourceErrorFileNotFound() throws Exception
    {
        Resource resource;

        resource = new ClassPathResource(Q_NAME_FILE_NOT_FOUND);
        registry.loadFromResource(resource);
    }

    /**
     * Test {@link SetRegistryDefaultImpl#loadFromResource(org.springframework.core.io.Resource)} with error in format 1
     */
    @Test(expected = IllegalArgumentException.class)
    public void testLoadResourceErrorFormat1() throws Exception
    {
        Resource resource;

        resource = new ClassPathResource(Q_NAME_FILE_ERROR_FORMAT_1);
        registry.loadFromResource(resource);
    }

    /**
     * Test {@link SetRegistryDefaultImpl#loadFromResource(org.springframework.core.io.Resource)} with error in format 2
     */
    @Test(expected = IllegalArgumentException.class)
    public void testLoadResourceErrorFormat2() throws Exception
    {
        Resource resource;

        resource = new ClassPathResource(Q_NAME_FILE_ERROR_FORMAT_2);
        registry.loadFromResource(resource);
    }

    /**
     * Test {@link SetRegistryDefaultImpl#loadFromResource(org.springframework.core.io.Resource)} with error in format 3
     */
    @Test(expected = IllegalArgumentException.class)
    public void testLoadResourceErrorFormat3() throws Exception
    {
        Resource resource;

        resource = new ClassPathResource(Q_NAME_FILE_ERROR_FORMAT_3);
        registry.loadFromResource(resource);
    }

    /**
     * Test {@link SetRegistryDefaultImpl#loadFromResource(org.springframework.core.io.Resource)} with undefined class.
     */
    @Test(expected = ClassNotFoundException.class)
    public void testLoadResourceErrorCNFE() throws Exception
    {
        Resource resource;

        resource = new ClassPathResource(Q_NAME_FILE_ERROR_CNF);
        registry.loadFromResource(resource);
    }

    /**
     * Test {@link SetRegistryDefaultImpl#loadFromResource(org.springframework.core.io.Resource)} with undefined
     * properties.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testLoadResourceErrorPropertyNotFound() throws Exception
    {
        Resource resource;

        resource = new ClassPathResource(Q_NAME_FILE_ERROR_PROP_ERROR);
        registry.loadFromResource(resource);
    }

    /**
     * Test {@link SetRegistryDefaultImpl#loadFromResource(org.springframework.core.io.Resource)} without errors.
     */
    @Test
    public void testLoadResourceOk() throws Exception
    {
        Resource resource;

        resource = new ClassPathResource(Q_NAME_FILE_OK);
        registry.loadFromResource(resource);
        Assert.assertFalse(registry.isEmpty());
        Assert.assertEquals(2, registry.size());
        Assert.assertTrue(registry.containsSet("set1"));
        Assert.assertTrue(registry.containsSet("set2"));
    }

    /**
     * Test {@link SetRegistryDefaultImpl#loadFromResource(org.springframework.core.io.Resource)} without errors.
     */
    @Test
    public void testLoadResourceOkDuplicateSet() throws Exception
    {
        Resource resource;

        resource = new ClassPathResource(Q_NAME_FILE_OK_DUPLICATES);
        registry.loadFromResource(resource);
    }

    /**
     * Test {@link SetRegistryDefaultImpl#iterator()}.
     */
    @Test
    public void testIteratorAndIterable() throws Exception
    {
        INamedSet[] set =
        {
                null, null, null
        };
        Iterator<INamedSet> iterator;
        int n;
        INamedSet nset;
        String[] actual, expected;

        set[0] = new NamedSetDefaultImpl(TestModelRoot.class, "set0");
        set[0].add(MODEL_ROOT_INT_PROPERTY);
        registry.putSet(set[0]);
        set[1] = new NamedSetDefaultImpl(TestModelRoot.class, "set1");
        set[1].add(MODEL_ROOT_INT_PROPERTY);
        registry.putSet(set[1]);
        set[2] = new NamedSetDefaultImpl(TestModelRoot.class, "set2");
        set[2].add(MODEL_ROOT_INT_PROPERTY);
        registry.putSet(set[2]);

        iterator = registry.iterator();
        n = 0;
        while(iterator.hasNext())
        {
            nset = iterator.next();
            Assert.assertEquals(set[n].getName(), nset.getName());
            Assert.assertEquals(set[n].size(), nset.size());
            expected = set[n].toArray(new String[] {});
            actual = nset.toArray(new String[] {});
            Assert.assertArrayEquals(expected, actual);
            n++;
        }
        // Iterable
        n = 0;
        for(INamedSet n1set : registry)
        {
            Assert.assertEquals(set[n].getName(), n1set.getName());
            Assert.assertEquals(set[n].size(), n1set.size());
            expected = set[n].toArray(new String[] {});
            actual = n1set.toArray(new String[] {});
            Assert.assertArrayEquals(expected, actual);
            n++;
        }
    }

    /**
     * Test for {@link SetRegistryDefaultImpl#putSet(INamedSet)} with duplicates.
     */
    @Test
    public void testDuplicates()
    {
        INamedSet nset;

        nset = new NamedSetDefaultImpl(TestModelRoot.class, "set1");
        nset.add(MODEL_ROOT_INT_PROPERTY);
        registry.putSet(nset);
        Assert.assertFalse(registry.isEmpty());
        Assert.assertEquals(1, registry.size());
        nset = new NamedSetDefaultImpl(TestModelRoot.class, "set1");
        nset.add(MODEL_ROOT_INT_PROPERTY);
        registry.putSet(nset);
        Assert.assertFalse(registry.isEmpty());
        Assert.assertEquals(1, registry.size());
    }

    /**
     * Test for {@link SetRegistryDefaultImpl#putSet(INamedSet)} with null value.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testPutNull()
    {
        registry.putSet(null);
    }

    /**
     * Test for {@link SetRegistryDefaultImpl#putSet(INamedSet)}.
     */
    @Test
    public void testPut()
    {
        INamedSet nset, nsetr;

        nset = new NamedSetDefaultImpl(TestModelRoot.class, "set1");
        nset.add(MODEL_ROOT_INT_PROPERTY);
        registry.putSet(nset);
        Assert.assertFalse(registry.isEmpty());
        Assert.assertEquals(1, registry.size());
        nsetr = registry.getSet(nset.getName());
        Assert.assertNotNull(nsetr);
        Assert.assertSame(nset, nsetr);
    }

    /**
     * Test for {@link SetRegistryDefaultImpl#getSet(String)} with null argument.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetNull()
    {
        INamedSet nset;

        nset = new NamedSetDefaultImpl(TestModelRoot.class, "set1");
        nset.add(MODEL_ROOT_INT_PROPERTY);
        registry.putSet(nset);
        registry.getSet(null);
    }

    /**
     * Test for {@link SetRegistryDefaultImpl#getSet(String)} with empty argument.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetEmpty()
    {
        INamedSet nset;

        nset = new NamedSetDefaultImpl(TestModelRoot.class, "set1");
        nset.add(MODEL_ROOT_INT_PROPERTY);
        registry.putSet(nset);
        registry.getSet("");
    }

    /**
     * Test for {@link SetRegistryDefaultImpl#getSet(String)} with whitespace argument.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetWhitespace()
    {
        INamedSet nset;

        nset = new NamedSetDefaultImpl(TestModelRoot.class, "set1");
        nset.add(MODEL_ROOT_INT_PROPERTY);
        registry.putSet(nset);
        registry.getSet("   ");
    }

    /**
     * Test for {@link SetRegistryDefaultImpl#getSet(String)} with inexistent set name.
     */
    @Test(expected = SetNotFoundException.class)
    public void testGetInexistentSet()
    {
        INamedSet nset;

        nset = new NamedSetDefaultImpl(TestModelRoot.class, "set1");
        nset.add(MODEL_ROOT_INT_PROPERTY);
        registry.putSet(nset);
        registry.getSet("setXX");
    }

    /**
     * Test for {@link SetRegistryDefaultImpl#getSet(String)} with existent set name.
     */
    @Test public void testGetExistentSet()
    {
        INamedSet nset, nsetr;

        nset = new NamedSetDefaultImpl(TestModelRoot.class, "set1");
        nset.add(MODEL_ROOT_INT_PROPERTY);
        registry.putSet(nset);
        nset = new NamedSetDefaultImpl(TestModelRoot.class, "set2");
        nset.add(MODEL_ROOT_INT_PROPERTY);
        registry.putSet(nset);
        nset = new NamedSetDefaultImpl(TestModelRoot.class, "set3");
        nset.add(MODEL_ROOT_INT_PROPERTY);
        registry.putSet(nset);
        nsetr = registry.getSet("set2");
        Assert.assertNotNull(nsetr);
        nset = new NamedSetDefaultImpl(TestModelRoot.class, "set2");
        nset.add(MODEL_ROOT_INT_PROPERTY);
        Assert.assertEquals(nset, nsetr);
    }

    /**
     * Test for {@link SetRegistryDefaultImpl#removeSet(String)}.
     */
    @Test
    public void testRemove()
    {
        INamedSet nset1, nset2, nsetr;

        nset1 = new NamedSetDefaultImpl(TestModelRoot.class, "set1");
        nset1.add(MODEL_ROOT_INT_PROPERTY);
        registry.putSet(nset1);
        nset2 = new NamedSetDefaultImpl(TestModelRoot.class, "set2");
        nset2.add(MODEL_ROOT_INT_PROPERTY);
        registry.putSet(nset2);
        Assert.assertFalse(registry.isEmpty());
        Assert.assertEquals(2, registry.size());

        Assert.assertTrue(registry.removeSet(nset1.getName()));
        Assert.assertFalse(registry.isEmpty());
        Assert.assertEquals(1, registry.size());
        nsetr = registry.getSet(nset2.getName());
        Assert.assertNotNull(nsetr);
        Assert.assertSame(nset2, nsetr);
        // remove an inexistent set
        Assert.assertFalse(registry.removeSet("xxx"));
        Assert.assertFalse(registry.isEmpty());
        Assert.assertEquals(1, registry.size());
        nsetr = registry.getSet(nset2.getName());
        Assert.assertNotNull(nsetr);
        Assert.assertSame(nset2, nsetr);
    }

    /**
     * Test for {@link SetRegistryDefaultImpl#addAll(java.util.Set)} with null.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddCollectionWithNull()
    {
        registry.addAll(null);
    }

    /**
     * Test for {@link SetRegistryDefaultImpl#addAll(java.util.Set)} with an empty collection.
     */
    @Test
    public void testAddCollectionWithEmptyCollection()
    {
        Set<INamedSet> collection;

        collection = new TreeSet<INamedSet>();
        registry.addAll(collection);
        Assert.assertTrue(registry.isEmpty());
    }

    /**
     * Test for {@link SetRegistryDefaultImpl#addAll(java.util.Set)} with a collection with items.
     */
    @Test
    public void testAddCollectionWithItems()
    {
        Set<INamedSet> collection;
        INamedSet nset;
        int n;

        collection = new TreeSet<INamedSet>();
        nset = new NamedSetDefaultImpl(TestModelRoot.class, "set1");
        nset.add(MODEL_ROOT_INT_PROPERTY);
        collection.add(nset);
        nset = new NamedSetDefaultImpl(TestModelRoot.class, "set2");
        nset.add(MODEL_ROOT_INT_PROPERTY);
        collection.add(nset);
        nset = new NamedSetDefaultImpl(TestModelRoot.class, "set3");
        nset.add(MODEL_ROOT_INT_PROPERTY);
        collection.add(nset);

        registry.addAll(collection);
        Assert.assertFalse(registry.isEmpty());
        Assert.assertEquals(3, registry.size());
        n = 1;
        for(INamedSet ns1 : collection)
        {
            nset = registry.getSet(ns1.getName());
            Assert.assertEquals("set" + n, nset.getName());
            n++;
            Assert.assertSame(ns1, nset);
        }
    }
}
