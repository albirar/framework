/*
 * This file is part of "utilitats".
 * 
 * "utilitats" is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * "utilitats" is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with calendar.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright (C) 2015 Octavi Fornés <ofornes@albirar.cat>
 */

package cat.albirar.framework.utilitats;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

/**
 * Utilitat de dates.
 * @author Octavi Fornés <ofornes@albirar.cat>
 * @since 1.0
 */
public abstract class DatesUtilitats {
	/**
	 * Comprova si dues data+hora són iguals o no tot tenint cura dels nulls.
	 * Ignora la part menor de segons.
	 * @param d1 La primera data+hora.
	 * @param d2 La segona data+hora.
	 * @return true si són iguals i false en cas contrari. Si ambdues són nul·les, també es consideren iguals
	 */
	public static final boolean nullSafeEqualsDataHora(Date d1, Date d2) {
		if(d1 == null || d2 == null) {
			if(d1 == d2) {
				return true;
			}
			return false;
		}
		return DateUtils.truncatedEquals(d1, d2, Calendar.SECOND);
	}
	/**
	 * Compara dues data+hora tot tenint cura dels nulls.
	 * Ignora la part menor de segons.
	 * Un valor null és considera un valor menor que qualsevol altre valor.
	 * @param d1 La primera data+hora.
	 * @param d2 La segona data+hora.
	 * @return igual a zero si són iguals, menor que zero si d1 és menor que d2 i major que zero si d1 és major que d2. Si ambdues són nul·les, també es consideren iguals
	 */
	public static final int nullSafeCompareDataHora(Date d1, Date d2) {
		if(d1 == null || d2 == null) {
			if(d1 == d2) {
				return 0;
			}
			if(d1 == null) {
				return -1;
			}
			return 1;
		}
		return DateUtils.truncatedCompareTo(d1, d2, Calendar.SECOND);
	}
}
