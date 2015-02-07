/*
 * This file is part of "albirar framework".
 * 
 * "albirar framework" is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * "albirar framework" is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with calendar.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright (C) 2013 Octavi Fornés
 */
package cat.albirar.framework.dynabean.impl.models;

import java.io.Serializable;
import java.util.Date;

/**
 * A model for test purposes.
 * 
 * @author octavi@fornes.cat
 * @since 1.0.0
 */
public interface IModel extends Serializable, Cloneable
{
	/** Internal ID, unique */
	public long getId();
	/** Internal ID, unique */
	public void setId(long id);
	/** Name */
	public String getName();
	/** Name */
	public void setName(String name);
	/** Last name */
	public String getLasName();
	/** Last name */
	public void setLasName(String lastName);
	/** Birth date */
	public Date getBirthDate();
	/** Birth date */
	public void setBirthDate(Date birthDate);
	/** Number of children */
	public int getNumberOfChildren();
	/** Number of children */
	public void setNumberOfChildren(int numberOfChildren);
	/** The incoming for year */
	public double getIncomingYear();
	/** The incoming for year */
	public void setIncomingYear(double incomingYear);
	/** Gender */
	public EGender getGender();
	/** Gender */
	public void setGender(EGender gender);
	/**
	 * Clone this bean.
	 */
	public IModel clone();
}
