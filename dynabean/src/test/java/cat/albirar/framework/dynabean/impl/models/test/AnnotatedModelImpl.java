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
 * Copyright (C) 2015 Octavi Fornés ofornes@albirar.cat
 */

package cat.albirar.framework.dynabean.impl.models.test;

import java.util.List;

/**
 * @author Octavi Fornés ofornes@albirar.cat
 *
 */
public class AnnotatedModelImpl implements IAnnotatedModel
{
    private static final long serialVersionUID = 1259465366370606255L;
    private String id;
    private List<String> namesList;
    private boolean pending;
    private double amount;
    private String [] otherNames;
    private double [] otherAmounts;
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String getId()
    {
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getNamesList()
    {
        return namesList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setNamesList(List<String> namesList)
    {
        this.namesList = namesList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPending()
    {
        return pending;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPending(boolean pending)
    {
        this.pending = pending;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getAmount()
    {
        return amount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAmount(double a)
    {
        this.amount = a;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String[] getOtherNames()
    {
        return otherNames;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setOtherNames(String[] otherNames)
    {
        this.otherNames = otherNames;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double[] getOtherAmounts()
    {
        return otherAmounts;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setOtherAmounts(double[] otherAmounts)
    {
        this.otherAmounts = otherAmounts;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IAnnotatedModel clone()
    {
        throw new RuntimeException("No implemented!", new CloneNotSupportedException());
    }

}
