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

package cat.albirar.framework.sets.registry;

import org.junit.Assert;
import org.junit.Test;

import cat.albirar.framework.sets.ISet;
import cat.albirar.framework.sets.impl.SetDefaultImplTest;
import cat.albirar.framework.sets.impl.TestModelRoot;
import cat.albirar.framework.sets.registry.impl.NamedSetDefaultImpl;

/**
 * Test for {@link NamedSetDefaultImpl}
 * @author Octavi Fornés ofornes@albirar.cat
 * @since 2.1.0
 */
public class NamedSetDefaultImplTest extends SetDefaultImplTest
{
    private static final String DEFAULT_NAME_1 = "name1";
    private static final String DEFAULT_NAME_2 = "name2";
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    protected <T extends ISet> T  instance(Class<?> rootModel, Object ... args)
    {
        if(args == null || args.length == 0)
        {
            return (T)new NamedSetDefaultImpl(rootModel, DEFAULT_NAME_1);
        }
        return (T)new NamedSetDefaultImpl(rootModel, "" + args[0]);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    protected <T extends ISet> String stringPropsStart(T set)
    {
        return "name=" + ((INamedSet)set).getName() + ", " + super.stringPropsStart(set);
    }
    /**
     * Test for {@link INamedSet#equals(Object)} with null.
     */
    @Test public void testEqualsWithNull()
    {
        INamedSet nset;
        
        nset = instance(TestModelRoot.class, DEFAULT_NAME_1);
        Assert.assertFalse(nset.equals(null));
    }
    /**
     * Test for {@link INamedSet#equals(Object)} with null.
     */
    @Test public void testEqualsWithAnotherType()
    {
        INamedSet nset;
        
        nset = instance(TestModelRoot.class, DEFAULT_NAME_1);
        Assert.assertFalse(nset.equals("xx"));
    }
    /**
     * Test for {@link INamedSet#equals(Object)} with self.
     */
    @Test public void testEqualsSelf()
    {
        INamedSet nset;
        
        nset = instance(TestModelRoot.class, DEFAULT_NAME_1);
        Assert.assertTrue(nset.equals(nset));
    }
    /**
     * Test for {@link INamedSet#equals(Object)} with another.
     */
    @Test public void testEqualsAntoher()
    {
        INamedSet nset, nset1;
        
        nset = instance(TestModelRoot.class, DEFAULT_NAME_1);
        nset1 = instance(TestModelRoot.class, DEFAULT_NAME_1);
        Assert.assertTrue(nset.equals(nset1));
        nset1.add(KNOWN_1L_PROPERTY);
        Assert.assertFalse(nset.equals(nset1));
        nset1 = instance(TestModelRoot.class, DEFAULT_NAME_2);
        Assert.assertFalse(nset.equals(nset1));
    }
}
