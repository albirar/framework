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

package cat.albirar.framework.sets.registry;

import cat.albirar.framework.sets.registry.impl.SetRegistryDefaultImpl;

/**
 * A factory to access to the JVM registry or the thread registry.
 * @author Octavi Fornés ofornes@albirar.cat
 * @since 2.1.0
 */
public abstract class SetRegistryFactory
{
    /** The JVM global registry. */
    private static ISetRegistry jvmRegistry;
    /** The thread local registry. */
    private static final ThreadLocal<ISetRegistry> registry = new ThreadLocal<ISetRegistry>()
    {
        @Override
        public ISetRegistry initialValue()
        {
            return new SetRegistryDefaultImpl();
        }

    };
    /**
     * Gets the global JVM registry.
     * @return The registry for all threads on JVM.
     */
    public static final ISetRegistry getJVMRegistry()
    {
        if(jvmRegistry == null)
        {
            jvmRegistry = new SetRegistryDefaultImpl();
        }
        return jvmRegistry;
    }
    /**
     * Gets the registry for current thread.
     * @return the registry
     */
    public static final ISetRegistry getThreadRegistry()
    {
        return registry.get();
    }
}
