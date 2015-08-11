/*
 * This file is part of "core".
 * 
 * "core" is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * "core" is distributed in the hope that it will be useful,
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

import org.junit.Assert;
import org.junit.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import cat.albirar.framework.utilities.PageUtilities;

/**
 * Test for {@link PageUtilities}.
 * @author Octavi Fornés ofornes@albirar.cat
 * @since 2.0
 */
public class PageUtilitiesTest
{
    /**
     * Test for {@link PageUtilities#copyPageable(org.springframework.data.domain.Pageable)} with null.
     */
    @Test public void testCopyPageableNull()
    {
        Assert.assertNull(PageUtilities.copyPageable(null));
    }
    /**
     * Test for {@link PageUtilities#copyPageable(org.springframework.data.domain.Pageable)} with unasigned data.
     */
    @Test public void testCopyPageableUnasigned()
    {
        Pageable pag, r;
        
        pag = new PageRequest(0, 10);
        r = PageUtilities.copyPageable(pag);
        Assert.assertEquals(pag, r);
    }
    /**
     * Test for {@link PageUtilities#copyPageable(org.springframework.data.domain.Pageable)} with asigned data.
     */
    @Test public void testCopyPageableAsigned()
    {
        Pageable pag, r;
        Sort s;
        
        s = new Sort(Direction.ASC, "name");
        pag = new PageRequest(0, 10, s);
        r = PageUtilities.copyPageable(pag);
        Assert.assertEquals(pag, r);
        Assert.assertNotSame(r, pag);
    }
    /**
     * Test for {@link PageUtilities#copyOrCreatePageable(org.springframework.data.domain.Pageable)} with null.
     */
    @Test public void testCopyOrCreatePageableNull()
    {
        Pageable pag, r;
        
        pag = null;
        r = PageUtilities.copyOrCreatePageable(pag);
        Assert.assertNotNull(r);
        Assert.assertEquals(PageUtilities.PAGE_SIZE, r.getPageSize());
        Assert.assertEquals(0, r.getPageNumber());
    }
    /**
     * Test for {@link PageUtilities#copyOrCreatePageable(org.springframework.data.domain.Pageable)} with null.
     */
    @Test public void testCopyOrCreatePageableUnasigned()
    {
        Pageable pag, r;
        
        pag = new PageRequest(0, 10);
        r = PageUtilities.copyOrCreatePageable(pag);
        Assert.assertNotNull(r);
        Assert.assertEquals(pag, r);
    }
    /**
     * Test for {@link PageUtilities#copyOrCreatePageable(org.springframework.data.domain.Pageable)} with assigned data.
     */
    @Test public void testCopyOrCreatePageableAsigned()
    {
        Pageable pag, r;
        Sort s;
        
        s = new Sort(Direction.ASC, "name");
        pag = new PageRequest(0, 10, s);
        r = PageUtilities.copyOrCreatePageable(pag);
        Assert.assertNotNull(r);
        Assert.assertEquals(pag, r);
    }
    /**
     * Test for {@link PageUtilities#checkAndAdjust(Pageable)} with null argument.
     */
    @Test public void testCheckAndAdjustNull()
    {
        Assert.assertNull(PageUtilities.checkAndAdjust(null));
    }
    /**
     * Test for {@link PageUtilities#checkAndAdjust(Pageable)} with incorrect pagination (all cases).
     */
    @Test public void testCheckAndAdjustIncorrect()
    {
        Pageable page, adjusted;
        
        page = new PageableTestImpl(-1, 0);
        adjusted = PageUtilities.checkAndAdjust(page);
        Assert.assertEquals(0, adjusted.getPageNumber());
        Assert.assertEquals(PageUtilities.PAGE_SIZE, adjusted.getPageSize());
        
        page = new PageableTestImpl(5, -10);
        adjusted = PageUtilities.checkAndAdjust(page);
        Assert.assertEquals(5, adjusted.getPageNumber());
        Assert.assertEquals(PageUtilities.PAGE_SIZE, adjusted.getPageSize());
        
        page = new PageableTestImpl(-5, 10);
        adjusted = PageUtilities.checkAndAdjust(page);
        Assert.assertEquals(0, adjusted.getPageNumber());
        Assert.assertEquals(10, adjusted.getPageSize());
    }
    /**
     * Test for {@link PageUtilities#checkAndAdjust(Pageable)} with Correct pagination (all cases).
     */
    @Test public void testCheckAndAdjustCorrect()
    {
        Pageable page, adjusted;
        
        page = new PageableTestImpl(25, 530);
        adjusted = PageUtilities.checkAndAdjust(page);
        Assert.assertEquals(25, adjusted.getPageNumber());
        Assert.assertEquals(530, adjusted.getPageSize());
        
        page = new PageableTestImpl(0, PageUtilities.PAGE_SIZE);
        adjusted = PageUtilities.checkAndAdjust(page);
        Assert.assertEquals(0, adjusted.getPageNumber());
        Assert.assertEquals(PageUtilities.PAGE_SIZE, adjusted.getPageSize());
        
        page = new PageableTestImpl(0, 1);
        adjusted = PageUtilities.checkAndAdjust(page);
        Assert.assertEquals(0, adjusted.getPageNumber());
        Assert.assertEquals(1, adjusted.getPageSize());
    }
    
    class PageableTestImpl implements Pageable
    {
        private int page;
        private int size;
        PageableTestImpl(int page, int size)
        {
            this.page = page;
            this.size = size;
        }
        /**
         * {@inheritDoc}
         */
        @Override
        public int getPageNumber()
        {
            return page;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int getPageSize()
        {
            return size;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int getOffset()
        {
            return (page * size);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Sort getSort()
        {
            return null;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Pageable next()
        {
            return null;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Pageable previousOrFirst()
        {
            return null;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Pageable first()
        {
            return null;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean hasPrevious()
        {
            return false;
        }
        
    }
}
