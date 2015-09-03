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

import java.util.Date;

/**
 * A model for test purposes.
 * @author Octavi Fornés ofornes@albirar.cat
 * @since 2.1.0
 */
public class TestModelRootMixInterfaceClass
{
    private int intProperty;
    private String stringProperty;
    private Date dateProperty;
    private ITestModelSecondLevelMixInterfaceClass secondLevelModelProperty;
    private TestModelThirdLevelMixInterfaceClass thirdLevelModelProperty;
    /**
     * {@inheritDoc}
     */
    public int getIntProperty()
    {
        return intProperty;
    }
    /**
     * {@inheritDoc}
     */
    public void setIntProperty(int intProperty)
    {
        this.intProperty = intProperty;
    }
    /**
     * {@inheritDoc}
     */
    public String getStringProperty()
    {
        return stringProperty;
    }
    /**
     * {@inheritDoc}
     */
    public void setStringProperty(String stringProperty)
    {
        this.stringProperty = stringProperty;
    }
    
    /**
     * {@inheritDoc}
     */
    public Date getDateProperty()
    {
        return dateProperty;
    }
    /**
     * {@inheritDoc}
     */
    public void setDateProperty(Date dateProperty)
    {
        this.dateProperty = dateProperty;
    }
    /**
     * {@inheritDoc}
     */
    public ITestModelSecondLevelMixInterfaceClass getSecondLevelModelProperty()
    {
        return secondLevelModelProperty;
    }
    /**
     * {@inheritDoc}
     */
    public void setSecondLevelModelProperty(ITestModelSecondLevelMixInterfaceClass secondLevelModelProperty)
    {
        this.secondLevelModelProperty = secondLevelModelProperty;
    }
    /**
     * {@inheritDoc}
     */
    public TestModelThirdLevelMixInterfaceClass getThirdLevelModelProperty()
    {
        return thirdLevelModelProperty;
    }
    /**
     * {@inheritDoc}
     */
    public void setThirdLevelModelProperty(TestModelThirdLevelMixInterfaceClass thirdLevelModelProperty)
    {
        this.thirdLevelModelProperty = thirdLevelModelProperty;
    }

    
}
