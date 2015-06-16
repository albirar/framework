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
package cat.albirar.framework.utilitats;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

/**
 * Test for {@link PageUtilitats}.
 * @author Octavi Fornés ofornes@albirar.cat
 * @since 2.0
 */
public class PageUtilitatsTest
{
    /**
     * Test for {@link PageUtilitats#copiaPageable(org.springframework.data.domain.Pageable)} with null.
     */
    @Test public void testCopiaPageableNull()
    {
        Assert.assertNull(PageUtilitats.copiaPageable(null));
    }
    /**
     * Test for {@link PageUtilitats#copiaPageable(org.springframework.data.domain.Pageable)} with unasigned data.
     */
    @Test public void testCopiaPageableUnasigned()
    {
        Pageable pag, r;
        
        pag = new PageRequest(0, 10);
        r = PageUtilitats.copiaPageable(pag);
        Assert.assertEquals(pag, r);
    }
    /**
     * Test for {@link PageUtilitats#copiaPageable(org.springframework.data.domain.Pageable)} with asigned data.
     */
    @Test public void testCopiaPageableAsigned()
    {
        Pageable pag, r;
        Sort s;
        
        s = new Sort(Direction.ASC, "name");
        pag = new PageRequest(0, 10, s);
        r = PageUtilitats.copiaPageable(pag);
        Assert.assertEquals(pag, r);
        Assert.assertNotSame(r, pag);
    }
    /**
     * Test for {@link PageUtilitats#copiaOCreaPageable(org.springframework.data.domain.Pageable)} with null.
     */
    @Test public void testCopiaOCreaPageableNull()
    {
        Pageable pag, r;
        
        pag = null;
        r = PageUtilitats.copiaOCreaPageable(pag);
        Assert.assertNotNull(r);
        Assert.assertEquals(PageUtilitats.ELEMENTS_PER_PAGINA, r.getPageSize());
        Assert.assertEquals(0, r.getPageNumber());
    }
    /**
     * Test for {@link PageUtilitats#copiaOCreaPageable(org.springframework.data.domain.Pageable)} with null.
     */
    @Test public void testCopiaOCreaPageableUnasigned()
    {
        Pageable pag, r;
        
        pag = new PageRequest(0, 10);
        r = PageUtilitats.copiaOCreaPageable(pag);
        Assert.assertNotNull(r);
        Assert.assertEquals(pag, r);
    }
    /**
     * Test for {@link PageUtilitats#copiaOCreaPageable(org.springframework.data.domain.Pageable)} with assigned data.
     */
    @Test public void testCopiaOCreaPageableAsigned()
    {
        Pageable pag, r;
        Sort s;
        
        s = new Sort(Direction.ASC, "name");
        pag = new PageRequest(0, 10, s);
        r = PageUtilitats.copiaOCreaPageable(pag);
        Assert.assertNotNull(r);
        Assert.assertEquals(pag, r);
    }
}
