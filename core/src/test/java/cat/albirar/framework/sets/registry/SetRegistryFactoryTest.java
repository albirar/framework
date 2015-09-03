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

package cat.albirar.framework.sets.registry;

import org.junit.Assert;
import org.junit.Test;

import cat.albirar.framework.sets.impl.models.TestModelRoot;

/**
 * Test for {@link SetRegistryFactory}.
 * @author Octavi Fornés ofornes@albirar.cat
 * @since 2.1.0
 */
public class SetRegistryFactoryTest
{
    /** Name for set. */
    private static final String SET_NAME = "a";
    /**
     * Test the instance for JVM.
     */
    @Test public void testGetJvmRegistry()
    {
        ISetRegistry sr;
        
        sr = SetRegistryFactory.getJVMRegistry();
        Assert.assertNotNull(sr);
    }
    /**
     * Test the instance for thread.
     */
    @Test public void testGetThreadRegistry()
    {
        ISetRegistry sr1, sr2;
        
        sr1 = SetRegistryFactory.getThreadRegistry();
        Assert.assertNotNull(sr1);
        // invariability
        sr2 = SetRegistryFactory.getThreadRegistry();
        Assert.assertNotNull(sr2);
        Assert.assertSame(sr1, sr2);
        
    }
    /**
     * Test for different registry instance between jvm and thread.
     */
    @Test public void testCheckDifferentInstanceRegistry()
    {
        ISetRegistry sr1, sr2;
        
        sr1 = SetRegistryFactory.getJVMRegistry();
        Assert.assertNotNull(sr1);
        sr2 = SetRegistryFactory.getThreadRegistry();
        Assert.assertNotNull(sr2);
        Assert.assertNotSame(sr2, sr1);
    }
    /**
     * Test for different registry named sets between jvm and thread.
     */
    @Test public void testCheckDifferentRegistry()
    {
        ISetRegistry sr1, sr2;
        INamedSet ns1;
        
        sr1 = SetRegistryFactory.getJVMRegistry();
        Assert.assertNotNull(sr1);
        sr2 = SetRegistryFactory.getThreadRegistry();
        Assert.assertNotNull(sr2);
        // Add a named set
        ns1 = NamedSetUtils.instantiateNamedSetFor(TestModelRoot.class, SET_NAME);
        sr1.putSet(ns1);
        Assert.assertTrue(sr1.containsSet(SET_NAME));
        Assert.assertFalse(sr2.containsSet(SET_NAME));
        sr1.removeSet(SET_NAME);
        Assert.assertFalse(sr1.containsSet(SET_NAME));
        Assert.assertFalse(sr2.containsSet(SET_NAME));
        
        sr2.putSet(ns1);
        Assert.assertTrue(sr2.containsSet(SET_NAME));
        Assert.assertFalse(sr1.containsSet(SET_NAME));
        sr2.removeSet(SET_NAME);
        Assert.assertFalse(sr2.containsSet(SET_NAME));
        Assert.assertFalse(sr1.containsSet(SET_NAME));
    }
}
