/*
 * This file is part of "imodel".
 * 
 * "imodel" is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * "imodel" is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with calendar. If not, see
 * <http://www.gnu.org/licenses/>.
 * 
 * Copyright (C) 2013 Octavi Fornés
 */
package cat.albirar.framework.imodel.models;

import java.util.Date;

import cat.albirar.framework.imodel.utils.ObjectUtils;

/**
 * A implementation of {@link IModel} for test mixed operations (proxy and class).
 * 
 * @author octavi@fornes.cat
 * @since 1.0.0
 */
public class ModelImpl implements IModel
{
    private static final long serialVersionUID = 2392710557526409071L;

    private long id;

    private int numberOfChildren;

    private double incomingYear;

    private String name;

    private String lastName;

    private Date birthDate;

    private EGender gender;

    /**
     * Default constructor, the properties are left with default values.
     */
    public ModelImpl()
    {
        // Nothing to do
    }

    /**
     * Copy constructor.
     * @param origin The origin of data
     */
    public ModelImpl(IModel origin)
    {
        if(origin != null)
        {
            name = origin.getName();
            lastName = origin.getLasName();
            birthDate = origin.getBirthDate() == null ? null : new Date(origin.getBirthDate().getTime());
            gender = origin.getGender();
            id = origin.getId();
            numberOfChildren = origin.getNumberOfChildren();
            incomingYear = origin.getIncomingYear();
        }
    }
    /* (non-Javadoc)
     * @see cat.albirar.framework.imodel.beans.models.IModel#getName()
     */
    @Override
    public String getName()
    {
        return name;
    }

    /* (non-Javadoc)
     * @see cat.albirar.framework.imodel.beans.models.IModel#setName(java.lang.String)
     */
    @Override
    public void setName(String name)
    {
        this.name = name;
    }

    /* (non-Javadoc)
     * @see cat.albirar.framework.imodel.beans.models.IModel#getLasName()
     */
    @Override
    public String getLasName()
    {
        return lastName;
    }

    /* (non-Javadoc)
     * @see cat.albirar.framework.imodel.beans.models.IModel#setLasName(java.lang.String)
     */
    @Override
    public void setLasName(String lastName)
    {
        this.lastName = lastName;
    }

    /* (non-Javadoc)
     * @see cat.albirar.framework.imodel.beans.models.IModel#getBirthDate()
     */
    @Override
    public Date getBirthDate()
    {
        return birthDate;
    }

    /* (non-Javadoc)
     * @see cat.albirar.framework.imodel.beans.models.IModel#setBirthDate(java.util.Date)
     */
    @Override
    public void setBirthDate(Date birthDate)
    {
        this.birthDate = birthDate == null ? null : new Date(birthDate.getTime());
    }

    /* (non-Javadoc)
     * @see cat.albirar.framework.imodel.beans.models.IModel#getGender()
     */
    @Override
    public EGender getGender()
    {
        return gender;
    }

    /* (non-Javadoc)
     * @see cat.albirar.framework.imodel.beans.models.IModel#setGender(cat.albirar.framework.imodel.beans.models.EGender)
     */
    @Override
    public void setGender(EGender gender)
    {
        this.gender = gender;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        long temp;

        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
        result = prime * result + ((gender == null) ? 0 : gender.hashCode());
        result = prime * result + ((birthDate == null) ? 0 : birthDate.hashCode());
        result = prime * result + numberOfChildren;
        temp = Double.doubleToLongBits(incomingYear);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
        {
            return true;
        }
        if(obj == null || IModel.class.isAssignableFrom(obj.getClass()) == false)
        {
            return false;
        }
        IModel other = (IModel) obj;

        return (id == other.getId()
                && ObjectUtils.nullSafeEquals(name, other.getName()) 
                && ObjectUtils.nullSafeEquals(lastName, other.getLasName())
                && ObjectUtils.nullSafeEquals(birthDate, other.getBirthDate())
                && ObjectUtils.nullSafeEquals(gender, other.getGender())
                && numberOfChildren == other.getNumberOfChildren()
                && incomingYear == other.getIncomingYear()
                );
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return String.format("ModelImpl [id=%d, name=%s, lastName=%s, gender=%s, birthDate=%s, numberOfChildren=%d, incomingYear=%d]"
                , id, name, lastName, gender, birthDate, numberOfChildren, incomingYear);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#clone()
     */
    @Override
    public IModel clone()
    {
        return new ModelImpl(this);
    }

    /* (non-Javadoc)
     * @see cat.albirar.framework.imodel.beans.models.IModel#getId()
     */
    @Override
    public long getId()
    {
        return id;
    }

    /* (non-Javadoc)
     * @see cat.albirar.framework.imodel.beans.models.IModel#setId(long)
     */
    @Override
    public void setId(long id)
    {
        this.id = id;
    }

    /* (non-Javadoc)
     * @see cat.albirar.framework.imodel.beans.models.IModel#getNumberOfChildren()
     */
    @Override
    public int getNumberOfChildren()
    {
        return numberOfChildren;
    }

    /* (non-Javadoc)
     * @see cat.albirar.framework.imodel.beans.models.IModel#setNumberOfChildren(int)
     */
    @Override
    public void setNumberOfChildren(int numberOfChildren)
    {
        this.numberOfChildren = numberOfChildren;
    }

    /* (non-Javadoc)
     * @see cat.albirar.framework.imodel.beans.models.IModel#getIncomingYear()
     */
    @Override
    public double getIncomingYear()
    {
        return incomingYear;
    }

    /* (non-Javadoc)
     * @see cat.albirar.framework.imodel.beans.models.IModel#setIncomingYear(double)
     */
    @Override
    public void setIncomingYear(double incomingYear)
    {
        this.incomingYear = incomingYear;
    }

}
