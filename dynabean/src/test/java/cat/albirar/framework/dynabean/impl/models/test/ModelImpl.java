/*
 * This file is part of "albirar framework".
 * 
 * "albirar framework" is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * "albirar framework" is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with calendar. If not, see
 * <http://www.gnu.org/licenses/>.
 * 
 * Copyright (C) 2013 Octavi Fornés
 */
package cat.albirar.framework.dynabean.impl.models.test;

import java.util.Arrays;
import java.util.Date;

import org.springframework.util.ObjectUtils;

/**
 * A implementation of {@link IModel} for test mixed operations (proxy and class).
 * 
 * @author <a href="mailto:ofornes@albirar.cat">Octavi Fornés ofornes@albirar.cat</a>
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

    private String [] names;
    
    private float taxesYear;
    private byte byteProp;
    private char charProp;
    private short shortProp;
    private boolean booleanProp;
    
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
            lastName = origin.getLastName();
            birthDate = origin.getBirthDate() == null ? null : new Date(origin.getBirthDate().getTime());
            gender = origin.getGender();
            id = origin.getId();
            numberOfChildren = origin.getNumberOfChildren();
            incomingYear = origin.getIncomingYear();
            if(origin.getNames() != null)
            {
                names = Arrays.copyOf(origin.getNames(), origin.getNames().length);
            }
        }
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public long getId()
    {
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setId(long id)
    {
        this.id = id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNumberOfChildren()
    {
        return numberOfChildren;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setNumberOfChildren(int numberOfChildren)
    {
        this.numberOfChildren = numberOfChildren;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getIncomingYear()
    {
        return incomingYear;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setIncomingYear(double incomingYear)
    {
        this.incomingYear = incomingYear;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName()
    {
        return name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getLastName()
    {
        return lastName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date getBirthDate()
    {
        return birthDate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBirthDate(Date birthDate)
    {
        this.birthDate = birthDate == null ? null : new Date(birthDate.getTime());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EGender getGender()
    {
        return gender;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setGender(EGender gender)
    {
        this.gender = gender;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String[] getNames()
    {
        return names;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setNames(String[] names)
    {
        this.names = names;
    }

    @Override
    public float getTaxesYear()
    {
        return taxesYear;
    }

    @Override
    public void setTaxesYear(float taxesYear)
    {
        this.taxesYear = taxesYear;
    }

    @Override
    public byte getByteProp()
    {
        return byteProp;
    }

    @Override
    public void setByteProp(byte byteProp)
    {
        this.byteProp = byteProp;
    }

    @Override
    public char getCharProp()
    {
        return charProp;
    }

    @Override
    public void setCharProp(char charProp)
    {
        this.charProp = charProp;
    }

    @Override
    public short getShortProp()
    {
        return shortProp;
    }

    @Override
    public void setShortProp(short shortProp)
    {
        this.shortProp = shortProp;
    }

    @Override
    public boolean isBooleanProp()
    {
        return booleanProp;
    }

    @Override
    public void setBooleanProp(boolean booleanProp)
    {
        this.booleanProp = booleanProp;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode()
    {
        return ObjectUtils.nullSafeHashCode(new Object[] {id, name, lastName, gender, birthDate, numberOfChildren
                , incomingYear, names, taxesYear, byteProp, charProp, shortProp, booleanProp});
    }

    /**
     * {@inheritDoc}
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
                && ObjectUtils.nullSafeEquals(lastName, other.getLastName())
                && ObjectUtils.nullSafeEquals(birthDate, other.getBirthDate())
                && ObjectUtils.nullSafeEquals(gender, other.getGender())
                && numberOfChildren == other.getNumberOfChildren()
                && incomingYear == other.getIncomingYear()
                && ObjectUtils.nullSafeEquals(names, other.getNames())
                );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return String.format("ModelImpl [id=%s, name=%s, lastName=%s, gender=%s, birthDate=%s, numberOfChildren=%d, incomingYear=%s, names=%s]"
                , id, name, lastName, gender, birthDate, numberOfChildren, incomingYear, names);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IModel clone()
    {
        return new ModelImpl(this);
    }

}
