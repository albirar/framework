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

/**
 * Register related items.
 * <p>A register holds named sets to use them.</p>
 * <p>Sets can be defined by property file and loaded on setup application time.</p>
 * <p>The format of property files should to be:
 * <pre>
 * setName1=QualifiedModelName, property1, property2, property3,...
 * setName2=QualifiedModelName, property4, property5, property6,...
 * setName3=QualifiedModelName, property7, property8, property9,...
 * ...
 * </pre>
 * </p>
 * <p>The {@code QualifiedModelName} designates the applicable model root for this set. Next, the collection of property names relative to the model
 * </p>
 * @author Octavi Fornés ofornes@albirar.cat
 * @since 2.1.0
 */
package cat.albirar.framework.sets.registry;