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
package cat.albirar.framework.database.exceptions;

import java.io.Serializable;

/**
 * A property name and value involved in {@link EntityOperationInformation}.
 * 
 * @author Octavi Fornés ofornes@albirar.cat
 * @since 2.0
 */
class PairKey implements Serializable
{
    private static final long serialVersionUID = -7234052337202580679L;

    private String keyName;
    private Serializable keyValue;
    /**
     * Unique constructor with key name and key value.
     * @param keyName A property involved in operation, like 'id', 'name', etc.
     * @param keyValue The value of the previosly indicated property
     */
    PairKey(String keyName, Serializable keyValue)
    {
        this.keyName = keyName;
        this.keyValue = keyValue;
    }
    /**
     * The property key name involved in operation.
     * @return the name, like 'id' or 'name', etc.
     */
    public String getKeyName()
    {
        return keyName;
    }
    /**
     * The property key name involved in operation.
     * @param keyName the name, like 'id' or 'name', etc.
     */
    protected void setKeyName(String keyName)
    {
        this.keyName = keyName;
    }
    /**
     * The property value involved in operation.
     * @return the value
     */
    public Serializable getKeyValue()
    {
        return keyValue;
    }
    /**
     * The property value involved in operation.
     * @param keyValue the keyValue to set
     */
    protected void setKeyValue(Serializable keyValue)
    {
        this.keyValue = keyValue;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return String.format("[keyName=%s, keyValue=%s]", keyName, keyValue);
    }
}
