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
 * Utilitats per a gestionar paginació.
 * @author <a href="mailto:ofornes@albirar.cat">Octavi Fornés ofornes@albirar.cat</a>
 * @since 1.0
 */
public abstract class PageUtilities {
	/** Nombre d'elements per pàgina per defecte. */
	public static final int ELEMENTS_PER_PAGINA = 25;
	/**
	 * Copia un objecte de paginació.
	 * @param origen L'origen
	 * @return Una nova còpia de 'origen' o null si no en tenia, d'origen
	 */
	public static final Pageable copyPageable(Pageable origen) {
		Pageable d;
		
		if(origen != null) {
			d = new PageRequest(origen.getPageNumber()
					, origen.getPageSize()
					, origen.getSort());
		} else {
			d = null;
		}
		return d;
	}
	/**
	 * Copia o crea un objecte de paginació
	 * @param origen L'origen de les dades. Si s'especifica un null, es crea un de nou, amb la primera pàgina i {@value #ELEMENTS_PER_PAGINA} elements per pàgina.
	 * @return L'objecte de paginació copiat o creat.
	 */
	public static final Pageable copyOrCreatePageable(Pageable origen) {
		Pageable d;
		
		if(origen != null) {
			d = copyPageable(origen);
		} else {
			d = new PageRequest(0, ELEMENTS_PER_PAGINA);
		}
		return d;
	}

	/**
	 * Comprova els paràmetres de paginació.
	 * Comprova que:
	 * <ul>
	 * <li>{@link Pageable#getPageSize() origen.getPageSize()} és igual o major de 1</li>
	 * <li> {@link Pageable#getPageNumber() origen.getPageNumber()} és igual o major de zero</li>
	 * </ul>
	 * <strong>ATENCIÓ!!</strong>
	 * {@link Pageable} és immutable, o sigui que heu d'emprar l'objecte retornat
	 * @param origen L'origen
	 * @return Una còpia corregida, si s'escau
	 */
	public static final Pageable checkAndAdjust(Pageable origen) {
		int elements, pagina;
		
		if(origen.getPageSize() < 1) {
			elements = ELEMENTS_PER_PAGINA;
		} else {
			elements = origen.getPageSize();
		}
		
		if(origen.getPageNumber() < 0) {
			pagina = 0;
		} else {
			pagina = origen.getPageNumber();
		}
		return new PageRequest(pagina, elements);
	}
}
