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
package cat.albirar.framework.sets.registry;

import java.util.Properties;
import java.util.Set;

import org.springframework.core.io.Resource;

/**
 * Contract for registry of {@link INamedSet named sets}.
 * Use the {@link SetRegistryFactory factory} and access to global JVM registry or thread local registry.
 * 
 * @see SetRegistryFactory
 * @author Octavi Fornés ofornes@albirar.cat
 * @since 2.1.0
 */
public interface ISetRegistry extends Iterable<INamedSet>
{
    /**
     * Check if a set with the indicated name is registered.
     * @param setName The set name, required and cannot be empty nor only whitespace
     * @return true if set exists with that name and false if not
     * @throws IllegalArgumentException If {@code setName} are null or empty or only whitespace
     */
    public boolean containsSet(String setName);
    /**
     * Gets a set with name {@code setName}.
     * @param setName The set name, required and cannot be empty nor only whitespace
     * @return The set
     * @throws IllegalArgumentException If {@code setName} are null or empty or only whitespace
     * @throws SetNotFoundException If no sets with the name are found
     */
    public INamedSet getSet(String setName);

    /**
     * Put the {@code set} with name {@code setName}.
     * @param setName The set name, required and cannot be empty nor only whitespace
     * @param set The set to registry, required
     * @return true if the set doesn't replace any other and false if replace a previously registered set
     * @throws IllegalArgumentException If any argument are null or if {@code setName} is empty or whitespace
     */
    public boolean putSet(INamedSet set);

    /**
     * Remove the set with name {@code setName}.
     * @param setName The set name, required and cannot be empty nor only whitespace
     * @return true if the set where removed from registry and false if not
     * @throws IllegalArgumentException If {@code setName} are null or empty or only whitespace
     */
    public boolean removeSet(String setName);

    /**
     * Load sets from a {@link Properties} collection.
     * The format of property files should to be:
     * <pre>
     * setName1=qualifiedModel1: property1, property2, property3,...
     * setName2=qualifiedModel2: property4, property5, property6,...
     * setName3=qualifiedModel3: property7, property8, property9,...
     * ...
     * </pre>
     * 
     * @param properties The properties to load from, required
     * @return the number of sets loaded
     * @throws IllegalArgumentException If properties are null or properties format is incorrect or any property are incorrect for the root model.
     * @throws ClassNotFoundException if any of the root model classes cannot be found on {@link ClassLoader} of the registry
     */
    public int loadFromProperties(Properties properties) throws ClassNotFoundException;
    
    /**
     * Load sets from a {@link Resource} that should to have a property file format.
     * The format of property files should to be:
     * <pre>
     * setName1=qualifiedModel1: property1, property2, property3,...
     * setName2=qualifiedModel2: property4, property5, property6,...
     * setName3=qualifiedModel3: property7, property8, property9,...
     * ...
     * </pre>
     * 
     * @param resource The {@link Resource} to load from, required
     * @return the number of sets loaded
     * @throws IllegalArgumentException If resource are null or resource format is incorrect or any property are incorrect for the root model.
     * @throws ClassNotFoundException if any of the root model classes cannot be found on {@link ClassLoader} of the registry
     */
    public int loadFromResource(Resource resource) throws ClassNotFoundException;
    
    /**
     * If this registry are empty or not.
     * @return true if empty and false if not.
     */
    public boolean isEmpty();
    
    /**
     * Gets the number of named sets in this registry.
     * @return The size
     */
    public int size();
    /**
     * Add a collection of named sets to this registry.
     * @param sets The sets collection, required but empty collection are admited
     * @throws IllegalArgumentException If sets is null
     */
    public void addAll(Set<INamedSet> sets);
    
    /**
     * Remove all sets from the registry.
     */
    public void clear();
}
