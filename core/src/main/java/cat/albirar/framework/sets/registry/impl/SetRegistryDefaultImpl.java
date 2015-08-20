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

package cat.albirar.framework.sets.registry.impl;

import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import cat.albirar.framework.sets.registry.INamedSet;
import cat.albirar.framework.sets.registry.ISetRegistry;
import cat.albirar.framework.sets.registry.SetNotFoundException;

/**
 * A default implementation for {@link ISetRegistry}.
 * @author Octavi Fornés ofornes@albirar.cat
 * @since 2.1.0
 */
public class SetRegistryDefaultImpl implements ISetRegistry
{
    private static final Logger logger = LoggerFactory.getLogger(SetRegistryDefaultImpl.class);
    
    private static final ThreadLocal<Map<String, INamedSet>> registry = new ThreadLocal<Map<String, INamedSet>>()
    {
        @Override
        public Map<String, INamedSet> initialValue()
        {
            return Collections.synchronizedMap(new TreeMap<String, INamedSet>());
        }

    };

    /**
     * REGEX format of properties.
     */
    public static final String REGEX_FORMAT = "([^:]+):([^,]+)([ ]*,[ ]*([^,]+))*";
    
    /** To process property and resources. */
    private Pattern pattern = Pattern.compile(REGEX_FORMAT);
    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<INamedSet> iterator()
    {
        return registry.get().values().iterator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsSet(String setName)
    {
        Assert.hasText(setName, "The setName are required and cannot be empty or only whitespace!");
        return registry.get().containsKey(setName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public INamedSet getSet(String setName)
    {
        INamedSet s;
        
        Assert.hasText(setName, "The setName are required and cannot be empty or only whitespace!");
        s = registry.get().get(setName);
        if(s == null)
        {
            throw new SetNotFoundException("The named set '" + setName + "' doesn't exists in this registry");
        }
        return s;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean putSet(INamedSet set)
    {
        Assert.notNull(set, "The 'set' argument are required'");
        Assert.hasText(set.getName(), "The setName are required and cannot be empty or only whitespace!");
        return (registry.get().put(set.getName(), set) == null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeSet(String setName)
    {
        Assert.hasText(setName, "The setName are required and cannot be empty or only whitespace!");
        return (registry.get().remove(setName) != null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int loadFromProperties(Properties properties) throws ClassNotFoundException
    {
        Iterator<String> it;
        String nom;
        String valor;
        Matcher matcher;
        INamedSet namedSet;
        String className;
        Class<?> modelClass;
        int n;
        
        Assert.notNull(properties, "The properties argument are required!");
        if(logger.isTraceEnabled())
        {
            logger.trace("Processing set registry from property file!");
        }
        it = properties.stringPropertyNames().iterator();
        while(it.hasNext())
        {
            nom = it.next().trim();
            valor = properties.getProperty(nom).trim();
            if(StringUtils.hasText(valor))
            {
                matcher = pattern.matcher(valor);
                if(matcher.matches())
                {
                    className = matcher.group(1);
                    // Check if available
                    modelClass = getClass().getClassLoader().loadClass(className);
                    namedSet = new NamedSetDefaultImpl(modelClass, nom);
                    // Process properties
                    for(n = 2; n < matcher.groupCount(); n++)
                    {
                        valor = matcher.group(n).trim();
                        if(valor.startsWith(","))
                        {
                            valor = valor.substring(1).trim();
                        }
                        namedSet.add(valor);
                    }
                    putSet(namedSet);
                }
                else
                {
                    logger.error("Error on load from property file. Property '" + nom + "' have incorrect format: '" + valor + "'");
                    throw new IllegalArgumentException("When load from property file. The property '"
                            + nom + "' is not in the expected format (" + valor + "). The format should to be compatible with "
                                    + REGEX_FORMAT);
                }
            }
            else
            {
                logger.error("Error on load from property file. Property '" + nom + "' have no value");
                throw new IllegalArgumentException("When load from property file. The property '"
                        + nom + "' have no value. The format should to be compatible with "
                                + REGEX_FORMAT);
            }
        }

        if(logger.isTraceEnabled())
        {
            logger.trace(String.format("Property file processed. %d sets added!", registry.get().size()));
        }
        return registry.get().size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int loadFromResource(Resource resource) throws ClassNotFoundException
    {
        Properties prop;
        int n;
        
        Assert.notNull(resource, "The resource argument are required!");
        if(logger.isTraceEnabled())
        {
            logger.trace("Processing set registry from resource '" + resource.getDescription() + "'...");
        }
        prop = new Properties();
        try
        {
            prop.load(resource.getInputStream());
            if(logger.isTraceEnabled())
            {
                logger.trace("Resource '" + resource.getDescription() + "' file loaded.");
            }
            n = loadFromProperties(prop);
            if(logger.isTraceEnabled())
            {
                logger.trace(String.format("End processing resource '%s', %d sets read", resource.getDescription(), n));
            }
            return n;
        }
        catch(IOException e)
        {
            logger.error("Cannot load from resource '" + resource.getDescription() + "'", e);
            throw new IllegalArgumentException("Cannot load from the resource '" + resource.getDescription() + "'"
                    , e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty()
    {
        return registry.get().isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size()
    {
        return registry.get().size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addAll(Set<INamedSet> sets)
    {
        
        Assert.notNull(sets, "The sets collection argument are required");
        for(INamedSet ns : sets)
        {
            putSet(ns);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear()
    {
        registry.get().clear();
    }
    
}
