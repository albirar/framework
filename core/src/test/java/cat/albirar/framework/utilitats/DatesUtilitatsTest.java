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

import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test de {@link DatesUtilitats}.
 * @author Octavi Fornés ofornes@albirar.cat
 * @since 2.0
 */
public class DatesUtilitatsTest
{
    /**
     * Test for {@link DatesUtilitats#nullSafeCompareData(Date, Date)}
     */
    @Test public void nullSafeCompareData()
    {
        Date d1, d2;
        Calendar cal;
        
        d1 = null;
        d2 = null;

        cal = Calendar.getInstance();
        Assert.assertEquals(0, DatesUtilitats.nullSafeCompareData(d1, d2));

        d1 = cal.getTime();
        Assert.assertTrue(DatesUtilitats.nullSafeCompareData(d1, d2) > 0);
        Assert.assertTrue(DatesUtilitats.nullSafeCompareData(d2, d1) < 0);

        d2 = cal.getTime();
        Assert.assertEquals(0, DatesUtilitats.nullSafeCompareData(d1, d2));
        
        cal.set(Calendar.HOUR, cal.get(Calendar.HOUR) + 2);
        d2 = cal.getTime();
        Assert.assertEquals(0, DatesUtilitats.nullSafeCompareData(d1, d2));
        
        cal.add(Calendar.DATE, 2);
        d1 = cal.getTime();
        Assert.assertTrue(DatesUtilitats.nullSafeCompareData(d1, d2) > 0);
        Assert.assertTrue(DatesUtilitats.nullSafeCompareData(d2, d1) < 0);
    }
    /**
     * Test for {@link DatesUtilitats#nullSafeEqualsData(Date, Date)}
     */
    @Test public void nullSafeEqualsData()
    {
        Date d1, d2;
        Calendar cal;
        
        d1 = null;
        d2 = null;

        cal = Calendar.getInstance();
        Assert.assertTrue(DatesUtilitats.nullSafeEqualsData(d1, d2));

        d1 = cal.getTime();
        Assert.assertFalse(DatesUtilitats.nullSafeEqualsData(d1, d2));
        Assert.assertFalse(DatesUtilitats.nullSafeEqualsData(d2, d1));

        d2 = cal.getTime();
        Assert.assertTrue(DatesUtilitats.nullSafeEqualsData(d1, d2));
        
        cal.set(Calendar.HOUR, cal.get(Calendar.HOUR) + 2);
        d2 = cal.getTime();
        Assert.assertTrue(DatesUtilitats.nullSafeEqualsData(d1, d2));
        
        cal.add(Calendar.DATE, 2);
        d1 = cal.getTime();
        Assert.assertFalse(DatesUtilitats.nullSafeEqualsData(d1, d2));
        Assert.assertFalse(DatesUtilitats.nullSafeEqualsData(d2, d1));
    }
    /**
     * Test for {@link DatesUtilitats#nullSafeCompareDataHora(Date, Date)}
     */
    @Test public void nullSafeCompareDataHora()
    {
        Date d1, d2;
        Calendar cal;
        
        d1 = null;
        d2 = null;

        cal = Calendar.getInstance();
        Assert.assertEquals(0, DatesUtilitats.nullSafeCompareDataHora(d1, d2));

        d1 = cal.getTime();
        Assert.assertTrue(DatesUtilitats.nullSafeCompareDataHora(d1, d2) > 0);
        Assert.assertTrue(DatesUtilitats.nullSafeCompareDataHora(d2, d1) < 0);

        d2 = cal.getTime();
        Assert.assertEquals(0, DatesUtilitats.nullSafeCompareDataHora(d1, d2));
        
        cal.set(Calendar.MILLISECOND, cal.get(Calendar.MILLISECOND) + 2);
        d2 = cal.getTime();
        Assert.assertEquals(0, DatesUtilitats.nullSafeCompareDataHora(d1, d2));
        
        cal.add(Calendar.SECOND, 2);
        d1 = cal.getTime();
        Assert.assertTrue(DatesUtilitats.nullSafeCompareDataHora(d1, d2) > 0);
        Assert.assertTrue(DatesUtilitats.nullSafeCompareDataHora(d2, d1) < 0);
    }
    /**
     * Test for {@link DatesUtilitats#nullSafeEqualsDataHora(Date, Date)}
     */
    @Test public void nullSafeEqualsDataHora()
    {
        Date d1, d2;
        Calendar cal;
        
        d1 = null;
        d2 = null;

        cal = Calendar.getInstance();
        Assert.assertTrue(DatesUtilitats.nullSafeEqualsDataHora(d1, d2));

        d1 = cal.getTime();
        Assert.assertFalse(DatesUtilitats.nullSafeEqualsDataHora(d1, d2));
        Assert.assertFalse(DatesUtilitats.nullSafeEqualsDataHora(d2, d1));

        d2 = cal.getTime();
        Assert.assertTrue(DatesUtilitats.nullSafeEqualsDataHora(d1, d2));
        
        cal.set(Calendar.MILLISECOND, cal.get(Calendar.MILLISECOND) + 2);
        d2 = cal.getTime();
        Assert.assertTrue(DatesUtilitats.nullSafeEqualsDataHora(d1, d2));
        
        cal.add(Calendar.SECOND, 2);
        d1 = cal.getTime();
        Assert.assertFalse(DatesUtilitats.nullSafeEqualsDataHora(d1, d2));
        Assert.assertFalse(DatesUtilitats.nullSafeEqualsDataHora(d2, d1));
    }
    
}
