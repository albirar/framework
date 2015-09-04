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
 * Named set and register related items.
 * <p>A named set is a {@link cat.albirar.framework.sets.ISet} associated with a name.
 * Is used to create a set and hold them in a register for use later, in code, repeatedly.</p>
 * <p>The {@link cat.albirar.framework.sets.registry.NamedSetUtils} enables {@link cat.albirar.framework.sets.registry.INamedSet} instantiation:
 * <ul>
 * <li>For model class and name: {@link cat.albirar.framework.sets.registry.NamedSetUtils#instantiateNamedSetFor(Class, String)}</li>
 * <li>For create from {@link cat.albirar.framework.sets.ISet} and name: {@link cat.albirar.framework.sets.registry.NamedSetUtils#instantiateNamedSetFor(cat.albirar.framework.sets.ISet, String)}</li>
 * </ul>
 * </p>
 * <p>Once a {@link cat.albirar.framework.sets.registry.INamedSet} was created, can hold in any of two types of registers: JVM global register or thread local register.</p>
 * <p>In order to access to any of this register, the {@link cat.albirar.framework.sets.registry.SetRegistryFactory} offers two methods:
 * <ul>
 * <li>JVM registry: {@link cat.albirar.framework.sets.registry.SetRegistryFactory#getJVMRegistry()}</li>
 * <li>Thread local registry: {@link cat.albirar.framework.sets.registry.SetRegistryFactory#getThreadRegistry()}</li>
 * </ul>
 * </p>
 * <p>Named sets also can be defined by property file and loaded on setup application time with two methods of {@link cat.albirar.framework.sets.registry.ISetRegistry}:
 * <ul>
 * <li>From a {@link java.util.Properties} with {@link cat.albirar.framework.sets.registry.ISetRegistry#loadFromProperties(java.util.Properties)}</li>
 * <li>From a {@link org.springframework.core.io.Resource} with {@link cat.albirar.framework.sets.registry.ISetRegistry#loadFromResource(org.springframework.core.io.Resource)}</li>
 * </ul>
 * </p>
 * <p>The format of property files should to be:
 * <pre>
 * setName1=QualifiedModelName, property1, property2, property3,...
 * setName2=QualifiedModelName, property4, property5, property6,...
 * setName3=QualifiedModelName, property7, property8, property9,...
 * ...
 * </pre>
 * </p>
 * <p>The property name is the {@link cat.albirar.framework.sets.registry.INamedSet} name. 
 * The {@code QualifiedModelName} designates the applicable model root for this set.
 * Next, the collection of property names relative to the model separated by comma
 * </p>
 * @author Octavi Fornés ofornes@albirar.cat
 * @since 2.1.0
 */
package cat.albirar.framework.sets.registry;