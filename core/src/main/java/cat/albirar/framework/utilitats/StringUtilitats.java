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

import org.springframework.util.StringUtils;

/**
 * Utilitats extra per a strings.
 * @author Octavi Fornés ofornes@albirar.cat
 * @since 2.0
 */
public abstract class StringUtilitats
{
    /**
     * Check that strings is not null, have at least one item and all items have text.
     * @param strings The string or strings to check
     * @return true if not null, greather than 0 and all items {@link StringUtils#hasText(String)}
     * @see StringUtils#hasText(String)
     */
    public static final boolean hashText(String ...strings)
    {
        if(strings != null && strings.length > 0)
        {
            for(String s : strings)
            {
                if(!StringUtils.hasText(s))
                {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
