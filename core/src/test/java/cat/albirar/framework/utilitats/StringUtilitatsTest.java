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

/**
 * Test for {@link StringUtilitats}.
 * @author Octavi Fornés ofornes@albirar.cat
 * @since 2.0
 */
public class StringUtilitatsTest
{
    /**
     * Test {@link StringUtilitats#hashText(String...)} with different values and expected results.
     */
    @Test public void testhasText()
    {
        String [] texts;
        
        texts = null;
        Assert.assertFalse(StringUtilitats.hashText());
        Assert.assertFalse(StringUtilitats.hashText(texts));
        texts = new String[] {null, null, null};
        Assert.assertFalse(StringUtilitats.hashText(texts));
        texts[0] = "A";
        Assert.assertFalse(StringUtilitats.hashText(texts));
        texts[1] = "B";
        Assert.assertFalse(StringUtilitats.hashText(texts));
        texts[2] = "         ";
        Assert.assertFalse(StringUtilitats.hashText(texts));
        texts[2] = "";
        Assert.assertFalse(StringUtilitats.hashText(texts));
        texts[2] = " ";
        Assert.assertFalse(StringUtilitats.hashText(texts));
        texts[2] = "C";
        Assert.assertTrue(StringUtilitats.hashText(texts));
        texts[0] = "            ";
        Assert.assertFalse(StringUtilitats.hashText(texts));
    }
}
