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

package cat.albirar.framework.sets.impl;

/**
 * A model for test purposes.
 * @author Octavi Fornés ofornes@albirar.cat
 * @since 2.1.0
 */
public class TestModelRoot
{
    private int intProperty;
    private String stringProperty;
    private TestModelSecondLevel secondLevelModelProperty;
    private TestModelThirdLevel thirdLevelModelProperty;
    public int getIntProperty()
    {
        return intProperty;
    }
    public void setIntProperty(int intProperty)
    {
        this.intProperty = intProperty;
    }
    public String getStringProperty()
    {
        return stringProperty;
    }
    public void setStringProperty(String stringProperty)
    {
        this.stringProperty = stringProperty;
    }
    public TestModelSecondLevel getSecondLevelModelProperty()
    {
        return secondLevelModelProperty;
    }
    public void setSecondLevelModelProperty(TestModelSecondLevel secondLevelModelProperty)
    {
        this.secondLevelModelProperty = secondLevelModelProperty;
    }
    public TestModelThirdLevel getThirdLevelModelProperty()
    {
        return thirdLevelModelProperty;
    }
    public void setThirdLevelModelProperty(TestModelThirdLevel thirdLevelModelProperty)
    {
        this.thirdLevelModelProperty = thirdLevelModelProperty;
    }

    
}
