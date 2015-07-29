/*
 * This file is part of "database".
 * 
 * "database" is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * "database" is distributed in the hope that it will be useful,
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

import org.springframework.util.StringUtils;

/**
 * Throw on try to read an entity by id or any unique key and doesn't found.
 * @author Octavi Fornés ofornes@albirar.cat
 * @since 2.0
 */
public class EntityNotFoundException extends DataAbstractException
{
    private static final long serialVersionUID = -3924648748848748206L;

    private String entityName;
    private String keyName;
    private Serializable keyValue;
    /**
     * A more detailed constructor, with operation context information.
     * @param operation The operation context message, like "On create order, when search for customer"
     * @param entityName The entity name
     * @param keyName The key name, like 'id', or 'name', etc.
     * @param keyValue The value of the search key
     */
    public EntityNotFoundException(String operation, String entityName, String keyName, Serializable keyValue)
    {
        super();
        setOperation(operation);
        this.entityName = entityName;
        this.keyName = keyName;
        this.keyValue = keyValue;
    }
    /**
     * Constructor with entity search information for generic context.
     * @param entityName The entity name
     * @param keyName The key name, like 'id', or 'name', etc.
     * @param keyValue The value of the search key
     * @see #EntityNotFoundException(String, String, String, Serializable)
     */
    public EntityNotFoundException(String entityName, String keyName, Serializable keyValue)
    {
        this(null, entityName, keyName, keyValue);
    }
    /**
     * The searched entity name.
     * @return the name
     */
    public String getEntityName()
    {
        return entityName;
    }
    /**
     * The property key name used on search.
     * @return the name, like 'id' or 'name', etc.
     */
    public String getKeyName()
    {
        return keyName;
    }
    /**
     * The key value used on search.
     * @return the value
     */
    public Serializable getKeyValue()
    {
        return keyValue;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public String getMessage()
    {
        return compoundMessage();
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public String getLocalizedMessage()
    {
        return getMessage();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return String.format("EntityNotFoundException [operation=%s, entityName=%s, keyName=%s, keyValue=%s]", getOperation(), entityName, keyName, keyValue);
    }
    /**
     * Compound the message for the search information.
     * The message are:
     * <pre>
     * [{@link #getOperation() operation} + ": "] + "entity " + ["with " + {@link #getKeyName()} + "='" + {@link #getKeyValue()} + "' " ] + "not found!"
     * </pre> 
     * @return The compound message
     */
    private String compoundMessage()
    {
        StringBuilder stb;
        
        stb = new StringBuilder();
        if(!StringUtils.isEmpty(getOperation()))
        {
            stb.append(getOperation()).append(": ");
        }
        stb.append("entity ");
        if(!StringUtils.isEmpty(getEntityName()))
        {
            stb.append("'").append(getEntityName()).append("' ");
        }
        if(!StringUtils.isEmpty(getKeyValue()) && !StringUtils.isEmpty(getKeyName()))
        {
            stb.append("with ").append(getKeyName()).append("='").append(getKeyValue()).append("' ");
        }
        stb.append("not found!");
        return stb.toString();
    }
}
