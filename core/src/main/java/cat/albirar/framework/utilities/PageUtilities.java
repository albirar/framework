/*
 * This file is part of "core".
 * 
 * "core" is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * "core" is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with calendar.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright (C) 2015 Octavi Fornés <ofornes@albirar.cat>
 */

package cat.albirar.framework.utilities;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * Utilities for {@link Pageable}.
 * @author <a href="mailto:ofornes@albirar.cat">Octavi Fornés ofornes@albirar.cat</a>
 * @since 1.0
 */
public abstract class PageUtilities {
	/** Default number of items for a page. */
	public static final int ITEMS_FOR_PAGE = 25;
	/**
	 * Clone a {@link Pageable} object.
	 * @param origin The origin
	 * @return The new instance with all the origin values. Null if origin is null
	 */
	public static final Pageable copyPageable(Pageable origin) {
		Pageable d;
		
		if(origin != null) {
			d = new PageRequest(origin.getPageNumber()
					, origin.getPageSize()
					, origin.getSort());
		} else {
			d = null;
		}
		return d;
	}
	/**
	 * Clone or instantiate a {@link Pageable}.
	 * @param origin  The origin. If null, a new instance with page one and {@value #ITEMS_FOR_PAGE} items for page is created.
	 * @return The cloned object or a new instance with default values if origin is null
	 */
	public static final Pageable copyOrCreatePageable(Pageable origin) {
		Pageable d;
		
		if(origin != null) {
			d = copyPageable(origin);
		} else {
			d = new PageRequest(0, ITEMS_FOR_PAGE);
		}
		return d;
	}

	/**
	 * Check (and adjust) the values of a {@link Pageable} object.
	 * Check that:
	 * <ul>
	 * <li>{@link Pageable#getPageSize() origin.getPageSize()} is equals or great than one</li>
	 * <li>{@link Pageable#getPageNumber() origin.getPageNumber()} is equals or great than zero</li>
	 * </ul>
	 * <strong>WARNING!!</strong>
	 * {@link Pageable} is immutable, so you should to use the same returned object
	 * @param origin The origin
	 * @return A new instance, amended if needed
	 */
	public static final Pageable checkAndAdjust(Pageable origin) {
		int elements, pagina;
		
		if(origin.getPageSize() < 1) {
			elements = ITEMS_FOR_PAGE;
		} else {
			elements = origin.getPageSize();
		}
		
		if(origin.getPageNumber() < 0) {
			pagina = 0;
		} else {
			pagina = origin.getPageNumber();
		}
		return new PageRequest(pagina, elements);
	}
}
