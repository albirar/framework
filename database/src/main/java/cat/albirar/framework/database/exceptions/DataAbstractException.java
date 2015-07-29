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

/**
 * Abstract database exception for all kind of exceptions related to database.
 * @author Octavi Fornés ofornes@albirar.cat
 * @since 2.0
 */
public abstract class DataAbstractException extends RuntimeException
{
    /** Default serialVersionUID. */
    private static final long serialVersionUID = 1L;
    private String operation;

    /**
     * Default constructor.
     * No {@link RuntimeException#getMessage()} and no {@link RuntimeException#getCause()}. 
     * @see RuntimeException#RuntimeException()
     */
    public DataAbstractException()
    {
        super();
        // Nothing to do
    }

    /**
     * Constructor with message.
     * @param message The {@link RuntimeException#getMessage() message}
     * @see RuntimeException#RuntimeException(String)
     */
    public DataAbstractException(String message)
    {
        super(message);
    }

    /**
     * Constructor with cause.
     * @param cause The {@link RuntimeException#getCause() cause}
     * @see RuntimeException#RuntimeException(Throwable)
     */
    public DataAbstractException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructor with message and cause.
     * @param message The {@link RuntimeException#getMessage() message}
     * @param cause The {@link RuntimeException#getCause() cause}
     * @see RuntimeException#RuntimeException(String, Throwable)
     */
    public DataAbstractException(String message, Throwable cause)
    {
        super(message, cause);
    }

    /**
     * The operation that produces the exception.
     * @return the operation. Can be null!
     */
    public String getOperation()
    {
        return operation;
    }

    /**
     * Sets the operation context.
     * Operation context can be 'On create order', 'On copying contact', etc.
     * @param operation the operation. If null or empty, no operation are considered
     */
    protected void setOperation(String operation)
    {
        this.operation = operation;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return String.format("DataAbstractException [operation=%s]", operation);
    }
}
