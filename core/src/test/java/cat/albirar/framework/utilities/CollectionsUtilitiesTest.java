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
package cat.albirar.framework.utilities;

import java.util.Collection;
import java.util.TreeSet;
import java.util.Vector;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link CollectionsUtilities}.
 * @author Octavi Fornés ofornes@albirar.cat
 * @since 2.0
 */
public class CollectionsUtilitiesTest
{
    /**
     * Test for {@link CollectionsUtilities#isEmpty(java.util.Collection)} with a null value.
     */
    @Test public void testIsEmptyWithNull()
    {
        Assert.assertTrue(CollectionsUtilities.isEmpty(null));
    }
    /**
     * Test for {@link CollectionsUtilities#isEmpty(java.util.Collection)} with an empty collection (check with list and set).
     */
    @Test public void testIsEmptyWithEmptyCollection()
    {
        Collection<String> collection;
        
        collection = new Vector<String>();
        Assert.assertTrue(CollectionsUtilities.isEmpty(collection));
        
        collection = new TreeSet<String>();
        Assert.assertTrue(CollectionsUtilities.isEmpty(collection));
    }
    /**
     * Test for {@link CollectionsUtilities#isEmpty(java.util.Collection)} with a not-empty collection (check with list and set).
     */
    @Test public void testIsEmptyWithNotEmptyCollection()
    {
        Collection<String> collection;
        
        collection = new Vector<String>();
        collection.add("Prova1");
        Assert.assertFalse(CollectionsUtilities.isEmpty(collection));
        
        collection = new TreeSet<String>();
        collection.add("Prova1");
        Assert.assertFalse(CollectionsUtilities.isEmpty(collection));
    }
}
