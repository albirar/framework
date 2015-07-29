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

import cat.albirar.framework.utilities.StringUtilities;

/**
 * Test for {@link StringUtilities}.
 * @author Octavi Fornés ofornes@albirar.cat
 * @since 2.0
 */
public class StringUtilitiesTest
{
    /**
     * Test {@link StringUtilities#hashText(String...)} with different values and expected results.
     */
    @Test public void testhasText()
    {
        String [] texts;
        
        texts = null;
        Assert.assertFalse(StringUtilities.hashText());
        Assert.assertFalse(StringUtilities.hashText(texts));
        texts = new String[] {null, null, null};
        Assert.assertFalse(StringUtilities.hashText(texts));
        texts[0] = "A";
        Assert.assertFalse(StringUtilities.hashText(texts));
        texts[1] = "B";
        Assert.assertFalse(StringUtilities.hashText(texts));
        texts[2] = "         ";
        Assert.assertFalse(StringUtilities.hashText(texts));
        texts[2] = "";
        Assert.assertFalse(StringUtilities.hashText(texts));
        texts[2] = " ";
        Assert.assertFalse(StringUtilities.hashText(texts));
        texts[2] = "C";
        Assert.assertTrue(StringUtilities.hashText(texts));
        texts[0] = "            ";
        Assert.assertFalse(StringUtilities.hashText(texts));
    }
}
