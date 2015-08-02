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
 * A exception to indicate a constraint of uniqueness violation.
 * 
 * @author Octavi Fornés ofornes@albirar.cat
 * @since 2.0
 */
public class DuplicateException extends DataAbstractException
{
    private static final long serialVersionUID = -6471803520148278365L;

    /**
     * Default constructor.
     */
    public DuplicateException()
    {
        super();
        // Nothing to do!
    }

    /**
     * Constructor with message to show.
     * @param message The message to show.
     */
    public DuplicateException(String message)
    {
        super(message);
    }

    /**
     * Constructor with cause of this exception.
     * @param cause The cause of this exception
     */
    public DuplicateException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructor with message to show and cause of this exception.
     * @param message The message to show
     * @param cause The cause of this exception
     */
    public DuplicateException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
