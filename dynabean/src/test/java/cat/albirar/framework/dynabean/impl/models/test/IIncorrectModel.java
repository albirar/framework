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

/**
 * An incorrect model definition.
 * @author Octavi Fornés ofornes@albirar.cat
 *
 */
public interface IIncorrectModel
{
    /** Internal ID, unique */
    public long getId();
    /** Internal ID, unique */
    public void setId(long id);
    /** Name */
    public String getName();
    /** Name */
    public void setName(String name);
    /** Incorrect property definition. */
    public String get();
    /** Incorrect property definition. */
    public void set(String set);
    
    public String getDobleValue(String doubleValue);
    public String setDobleValue(String doubleValue);
    
    /** A non property method definition. */
    public String activate();
}
