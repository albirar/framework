/*
 * This file is part of "dynabean".
 * 
 * "dynabean" is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * "dynabean" is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with calendar.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright (C) 2015 Octavi Fornés <ofornes@albirar.cat>
 */

package cat.albirar.framework.dynabean.impl.models.test;

import java.util.Date;

/**
 * Implementació del model.
 * @author <a href="mailto:ofornes@albirar.cat">Octavi Fornés ofornes@albirar.cat</a>
 * @since 2.0
 */
public class SimpleModelImpl implements ISimpleModel {
	private static final long serialVersionUID = -3277182898944421449L;
	
	private String name;
	private Date date;
	private boolean tested;
	private Long number;
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Date getDate() {
		return date;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isTested() {
		return tested;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setTested(boolean tested) {
		this.tested = tested;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long getNumber() {
		return number;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setNumber(Long number) {
		this.number = number;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return String
				.format("SimpleModelImpl [name=%s, date=%s, tested=%s, number=%s]",
						name, date, tested, number);
	}
}
