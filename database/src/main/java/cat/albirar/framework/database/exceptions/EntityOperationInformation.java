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
import java.util.List;
import java.util.Vector;

import cat.albirar.framework.utilities.CollectionsUtilities;

/**
 * Hold entity operation information about search, insert or modify.
 * @author Octavi Fornés ofornes@albirar.cat
 * @since 2.0
 */
class EntityOperationInformation implements Serializable
{
    private static final long serialVersionUID = -1788626137958059113L;

    private String operation;
    private String entityName;
    private List<PairKey> pairKeys;
    /**
     * Constructor for operation, entity name and one pair of key name and value
     * @param operation The involved operation, like 'Find product' or 'Adding customer', etc.
     * @param entityName The involved entity name, like 'Customer', 'Order', etc.
     * @param pairKeys A collection of one or more {@link PairKey property name and value} involved in operation
     */
    EntityOperationInformation(String operation, String entityName, PairKey ... pairKeys)
    {
        this.operation = operation;
        this.entityName = entityName;
        this.pairKeys = new Vector<PairKey>();
        for(PairKey pk : pairKeys)
        {
            this.pairKeys.add(pk);
        }
    }
    /**
     * Constructor for operation, entity name and one pair of key name and value.
     * @param operation The involved operation, like 'Find product' or 'Adding customer', etc.
     * @param entityName The involved entity name, like 'Customer', 'Order', etc.
     * @param keyName A property involved in operation, like 'id', 'name', etc.
     * @param keyValue The value of the previosly indicated property
     */
    EntityOperationInformation(String operation, String entityName, String keyName, String keyValue)
    {
        this(operation, entityName, new PairKey(keyName, keyValue));
    }
    /**
     * The involved operation, like 'Find product' or 'Adding customer', etc.
     * @return the operation
     */
    public String getOperation()
    {
        return operation;
    }
    /**
     * The involved operation, like 'Find product' or 'Adding customer', etc.
     * @param operation the operation to set
     */
    public void setOperation(String operation)
    {
        this.operation = operation;
    }
    /**
     * The involved entity name, like 'Customer', 'Order', etc.
     * @return the entityName
     */
    public String getEntityName()
    {
        return entityName;
    }
    /**
     * The involved entity name, like 'Customer', 'Order', etc.
     * @param entityName the entityName to set
     */
    public void setEntityName(String entityName)
    {
        this.entityName = entityName;
    }
    /**
     * A collection of parameter name and value involved in operation
     * @return the list of parameter value pair
     */
    public List<PairKey> getPairKeys()
    {
        return pairKeys;
    }
    /**
     * A collection of parameter name and value involved in operation
     * @param pairKeys the list of parameter value pair
     */
    public void setPairKeys(List<PairKey> keys)
    {
        this.pairKeys = keys;
    }
    
    /**
     * Generate an array with all of the arguments hold with this object.
     * The arguments and the order are:
     * <ul>
     * <li>{@link #getOperation()}</li>
     * <li>{@link #getEntityName()}</li>
     * <li>The list of {@link #getPairKeys()} in same order, in a sequence of name, value.</li>
     * </ul> 
     * @return An array with all arguments
     */
    public Object[] compoundArgumentsForFormat()
    {
        List<Object> args;
        
        args = new Vector<Object>();
        args.add(getOperation());
        args.add(getEntityName());
        if(!CollectionsUtilities.isEmpty(getPairKeys()))
        {
            for(PairKey pk : getPairKeys())
            {
                args.add(pk.getKeyName());
                args.add(pk.getKeyValue());
            }
        }
                
        return args.toArray();
    }
}
