/*
 * This file is part of "utilitats".
 * 
 * "utilitats" is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * "utilitats" is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with calendar. If not, see
 * <http://www.gnu.org/licenses/>.
 * 
 * Copyright (C) 2015 Octavi Fornés <ofornes@albirar.cat>
 */

package cat.albirar.framework.utilities;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

/**
 * Extra utilities for dates.
 * 
 * @see DateUtils
 * @author <a href="mailto:ofornes@albirar.cat">Octavi Fornés ofornes@albirar.cat</a>
 * @since 1.0
 */
public abstract class DatesUtilities
{
    /**
     * Check if two date+time are equals or not, with care of null. The information bellow second part of time are
     * ignored.
     * 
     * @param d1 The first date+time
     * @param d2 The second date+time
     * @return true if are equals (even if both are null), false if not.
     */
    public static final boolean nullSafeEqualsDateTime(Date d1, Date d2)
    {
        return (nullSafeCompareDateTime(d1, d2) == 0);
    }

    /**
     * Compare two date+time, with care of null. The information bellow second part of time are ignored. A null value is
     * considered less than any non-null value.
     * 
     * @param d1 The first date+time
     * @param d2 The second date+time
     * @return Zero if both are equals (even if both are nulls), a number less than zero if d1 is minor than d2 and a
     *         number greater than zero if d1 is greater than d2.
     * @see DateUtils#truncatedCompareTo(Calendar, Calendar, int)
     */
    public static final int nullSafeCompareDateTime(Date d1, Date d2)
    {
        if(d1 == null || d2 == null)
        {
            if(d1 == d2)
            {
                return 0;
            }
            if(d1 == null)
            {
                return -1;
            }
            return 1;
        }
        return DateUtils.truncatedCompareTo(d1, d2, Calendar.SECOND);
    }

    /**
     * Check if two dates are equals or not, with care of null. The time part of both dates are ignored (from hour and
     * bellow)
     * 
     * @param d1 The first date
     * @param d2 The second date
     * @return true if are equals (even if both are null), false if not.
     */
    public static final boolean nullSafeEqualsDate(Date d1, Date d2)
    {
        return (nullSafeCompareDate(d1, d2) == 0);
    }

    /**
     * Compare two dates, with care of null. The time part of both dates are ignored (from hour and bellow)
     * 
     * @param d1 The first date
     * @param d2 The second date
     * @return Zero if both are equals (even if both are nulls), a number less than zero if d1 is minor than d2 and a
     *         number greater than zero if d1 is greater than d2.
     * @see DateUtils#truncatedCompareTo(Calendar, Calendar, int)
     */
    public static final int nullSafeCompareDate(Date d1, Date d2)
    {
        if(d1 == null || d2 == null)
        {
            if(d1 == d2)
            {
                return 0;
            }
            if(d1 == null)
            {
                return -1;
            }
            return 1;
        }
        return DateUtils.truncatedCompareTo(d1, d2, Calendar.DATE);
    }
}
