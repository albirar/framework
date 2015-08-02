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

import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import cat.albirar.framework.utilities.DatesUtilities;

/**
 * Test de {@link DatesUtilities}.
 * @author Octavi Fornés ofornes@albirar.cat
 * @since 2.0
 */
public class DatesUtilitiesTest
{
    /**
     * Test for {@link DatesUtilities#nullSafeCompareDate(Date, Date)}
     */
    @Test public void nullSafeCompareData()
    {
        Date d1, d2;
        Calendar cal;
        
        d1 = null;
        d2 = null;

        cal = Calendar.getInstance();
        Assert.assertEquals(0, DatesUtilities.nullSafeCompareDate(d1, d2));

        d1 = cal.getTime();
        Assert.assertTrue(DatesUtilities.nullSafeCompareDate(d1, d2) > 0);
        Assert.assertTrue(DatesUtilities.nullSafeCompareDate(d2, d1) < 0);

        d2 = cal.getTime();
        Assert.assertEquals(0, DatesUtilities.nullSafeCompareDate(d1, d2));
        
        cal.set(Calendar.HOUR, cal.get(Calendar.HOUR) + 2);
        d2 = cal.getTime();
        Assert.assertEquals(0, DatesUtilities.nullSafeCompareDate(d1, d2));
        
        cal.add(Calendar.DATE, 2);
        d1 = cal.getTime();
        Assert.assertTrue(DatesUtilities.nullSafeCompareDate(d1, d2) > 0);
        Assert.assertTrue(DatesUtilities.nullSafeCompareDate(d2, d1) < 0);
    }
    /**
     * Test for {@link DatesUtilities#nullSafeEqualsDate(Date, Date)}
     */
    @Test public void nullSafeEqualsData()
    {
        Date d1, d2;
        Calendar cal;
        
        d1 = null;
        d2 = null;

        cal = Calendar.getInstance();
        Assert.assertTrue(DatesUtilities.nullSafeEqualsDate(d1, d2));

        d1 = cal.getTime();
        Assert.assertFalse(DatesUtilities.nullSafeEqualsDate(d1, d2));
        Assert.assertFalse(DatesUtilities.nullSafeEqualsDate(d2, d1));

        d2 = cal.getTime();
        Assert.assertTrue(DatesUtilities.nullSafeEqualsDate(d1, d2));
        
        cal.set(Calendar.HOUR, cal.get(Calendar.HOUR) + 2);
        d2 = cal.getTime();
        Assert.assertTrue(DatesUtilities.nullSafeEqualsDate(d1, d2));
        
        cal.add(Calendar.DATE, 2);
        d1 = cal.getTime();
        Assert.assertFalse(DatesUtilities.nullSafeEqualsDate(d1, d2));
        Assert.assertFalse(DatesUtilities.nullSafeEqualsDate(d2, d1));
    }
    /**
     * Test for {@link DatesUtilities#nullSafeCompareDateTime(Date, Date)}
     */
    @Test public void nullSafeCompareDataHora()
    {
        Date d1, d2;
        Calendar cal;
        
        d1 = null;
        d2 = null;

        cal = Calendar.getInstance();
        Assert.assertEquals(0, DatesUtilities.nullSafeCompareDateTime(d1, d2));

        d1 = cal.getTime();
        Assert.assertTrue(DatesUtilities.nullSafeCompareDateTime(d1, d2) > 0);
        Assert.assertTrue(DatesUtilities.nullSafeCompareDateTime(d2, d1) < 0);

        d2 = cal.getTime();
        Assert.assertEquals(0, DatesUtilities.nullSafeCompareDateTime(d1, d2));
        
        cal.set(Calendar.MILLISECOND, cal.get(Calendar.MILLISECOND) + 2);
        d2 = cal.getTime();
        Assert.assertEquals(0, DatesUtilities.nullSafeCompareDateTime(d1, d2));
        
        cal.add(Calendar.SECOND, 2);
        d1 = cal.getTime();
        Assert.assertTrue(DatesUtilities.nullSafeCompareDateTime(d1, d2) > 0);
        Assert.assertTrue(DatesUtilities.nullSafeCompareDateTime(d2, d1) < 0);
    }
    /**
     * Test for {@link DatesUtilities#nullSafeEqualsDateTime(Date, Date)}
     */
    @Test public void nullSafeEqualsDataHora()
    {
        Date d1, d2;
        Calendar cal;
        
        d1 = null;
        d2 = null;

        cal = Calendar.getInstance();
        Assert.assertTrue(DatesUtilities.nullSafeEqualsDateTime(d1, d2));

        d1 = cal.getTime();
        Assert.assertFalse(DatesUtilities.nullSafeEqualsDateTime(d1, d2));
        Assert.assertFalse(DatesUtilities.nullSafeEqualsDateTime(d2, d1));

        d2 = cal.getTime();
        Assert.assertTrue(DatesUtilities.nullSafeEqualsDateTime(d1, d2));
        
        cal.set(Calendar.MILLISECOND, cal.get(Calendar.MILLISECOND) + 2);
        d2 = cal.getTime();
        Assert.assertTrue(DatesUtilities.nullSafeEqualsDateTime(d1, d2));
        
        cal.add(Calendar.SECOND, 2);
        d1 = cal.getTime();
        Assert.assertFalse(DatesUtilities.nullSafeEqualsDateTime(d1, d2));
        Assert.assertFalse(DatesUtilities.nullSafeEqualsDateTime(d2, d1));
    }
    
}
