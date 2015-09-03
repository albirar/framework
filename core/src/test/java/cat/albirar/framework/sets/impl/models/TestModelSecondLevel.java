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

package cat.albirar.framework.sets.impl.models;

/**
 * A model for test purposes.
 * @author Octavi Fornés ofornes@albirar.cat
 * @since 2.1.0
 */
public class TestModelSecondLevel
{
    private int int2Property;
    private String string2Property;
    private TestModelThirdLevel thirdLevel2ModelProperty;
    /**
     * {@inheritDoc}
     */
    public int getInt2Property()
    {
        return int2Property;
    }
    /**
     * {@inheritDoc}
     */
    public void setInt2Property(int int2Property)
    {
        this.int2Property = int2Property;
    }
    /**
     * {@inheritDoc}
     */
    public String getString2Property()
    {
        return string2Property;
    }
    /**
     * {@inheritDoc}
     */
    public void setString2Property(String string2Property)
    {
        this.string2Property = string2Property;
    }
    /**
     * {@inheritDoc}
     */
    public TestModelThirdLevel getThirdLevel2ModelProperty()
    {
        return thirdLevel2ModelProperty;
    }
    /**
     * {@inheritDoc}
     */
    public void setThirdLevel2ModelProperty(TestModelThirdLevel thirdLevel2ModelProperty)
    {
        this.thirdLevel2ModelProperty = thirdLevel2ModelProperty;
    }
}
