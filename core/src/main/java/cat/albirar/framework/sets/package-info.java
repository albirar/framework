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
 * <p>Sets are a complement of javaBean to enable customization of reading properties sets from any javaBean.</p>
 * <p>A set is a collection of property path that defines that the client wants from any model in any operation of services or repository.</p>
 * <p>A set have a model root. That is, a qualified model interface or class definition. Any property path is related to this root.</p>
 * <p>By example, if you have a model with the following properties:
 * <ul>
 *  <li>intProperty</li>
 *  <li>stringProperty</li>
 *  <li>anotherModelProperty</li>
 * </ul>
 * And the AnotherModel have the following properties:
 * <ul>
 *  <li>propertyOne</li>
 *  <li>propertyTwo</li>
 * </ul>
 * And your client does not need all the properties of all of the models, you can specify a set of properties to get:
 * <ul>
 * <li>stringProperty</li>
 * <li>anotherModelProperty.propertyTwo</li>
 * </ul>
 * The service or repository, instantiate the first model, read the {@code stringProperty}, instantiate
 * the {@anotherModel} and read the property {@code propertyTwo} and assign the instance of another model
 * to the property {@code anotherModelProperty} of the first model.
 * </p>
 * <p>Sets can also be hold in a registry associated with a name. Then you can use this name to use the defined set. 
 * </p>
 * <p>To instantiate a {@link cat.albirar.framework.sets.ISet} can use the
 * {@link cat.albirar.framework.sets.SetUtils#instantiateSetFor(Class)}:
 * <pre>
 * ISet set;
 * 
 * set = SetUtils.instantiateSetFor(Model.class);
 * ...
 * </pre>
 * </p>
 * <p>A default implementation of {@link cat.albirar.framework.sets.ISetBuilder} can
 * be instantiated with {@link cat.albirar.framework.sets.SetUtils#instantiateBuilderFor(Class)}:
 * <pre>
 * ISetBuilder builder;
 * 
 * builder = SetUtils.instantiateBuilderFor(Model.class);
 * ...
 * </pre>
 * <p>
 * 
 * @author Octavi Fornés ofornes@albirar.cat
 * @since 2.1.0
 */
package cat.albirar.framework.sets;