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

package cat.albirar.framework.sets.tsb.impl;

import java.lang.reflect.Array;
import java.lang.reflect.Method;

import org.springframework.beans.BeanUtils;

import cat.albirar.framework.proxy.IProxyHandler;
import cat.albirar.framework.proxy.ProxyFactory;
import cat.albirar.framework.sets.ISet;
import cat.albirar.framework.sets.ISetBuilder;
import cat.albirar.framework.sets.impl.SetBuilderDefaultImpl;
import cat.albirar.framework.sets.tsb.ITypeSafeBuilder;

/**
 * A default implementation of {@link ITypeSafeBuilder}.
 * @author Octavi Fornés ofornes@albirar.cat
 * @since 2.1.0
 */
public class TypeSafeBuilderDefaultImpl<M> implements ITypeSafeBuilder<M>
{
    private ISetBuilder<M> setBuilder;
    private BuilderAssistant builderAssistant;
    private M model;
    
    /**
     * The unique constructor.
     */
    public TypeSafeBuilderDefaultImpl(Class<M> rootModel)
    {
        setBuilder = new SetBuilderDefaultImpl<M>(rootModel);
        builderAssistant = newAssistant();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public ITypeSafeBuilder<M> addProperty(byte property)
    {
        return endPath();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ITypeSafeBuilder<M> addProperty(boolean property)
    {
        return endPath();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ITypeSafeBuilder<M> addProperty(char property)
    {
        return endPath();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ITypeSafeBuilder<M> addProperty(short property)
    {
        return endPath();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ITypeSafeBuilder<M> addProperty(int property)
    {
        return endPath();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ITypeSafeBuilder<M> addProperty(long property)
    {
        return endPath();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ITypeSafeBuilder<M> addProperty(double property)
    {
        return endPath();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ITypeSafeBuilder<M> addProperty(float property)
    {
        return endPath();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ITypeSafeBuilder<M> addProperty(String property)
    {
        return endPath();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ITypeSafeBuilder<M> addProperty(Object property)
    {
        return endPath();
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public ITypeSafeBuilder<M> addProperty(byte [] property)
    {
        return endPath();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ITypeSafeBuilder<M> addProperty(boolean [] property)
    {
        return endPath();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ITypeSafeBuilder<M> addProperty(char [] property)
    {
        return endPath();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ITypeSafeBuilder<M> addProperty(short [] property)
    {
        return endPath();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ITypeSafeBuilder<M> addProperty(int [] property)
    {
        return endPath();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ITypeSafeBuilder<M> addProperty(long [] property)
    {
        return endPath();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ITypeSafeBuilder<M> addProperty(double [] property)
    {
        return endPath();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ITypeSafeBuilder<M> addProperty(float [] property)
    {
        return endPath();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ITypeSafeBuilder<M> addProperty(String [] property)
    {
        return endPath();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ITypeSafeBuilder<M> addProperty(Object [] property)
    {
        return endPath();
    }
    /**
     * End of a 'path' composition.
     * @return The builder itself
     */
    private ITypeSafeBuilder<M> endPath()
    {
        builderAssistant.endPath();
        return this;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public M getModel()
    {
        return model;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISet<M> build()
    {
        return setBuilder.build();
    }
    /**
     * Create a new proxy builder assistant.
     * @return The new builder assistant
     */
    BuilderAssistant newAssistant()
    {
        BuilderAssistant ba;
        
        ba = new BuilderAssistant(setBuilder);
        model = (M) ProxyFactory.instanceProxy().newProxy(ba, setBuilder.getModelRoot());
        return ba;
    }
    /**
     * Create a new proxy builder assistant.
     * @return The new builder assistant
     */
    Object newChildAssistant(BuilderAssistant bParent, Class<?> type)
    {
        return ProxyFactory.instanceProxy().newProxy(new BuilderAssistant(bParent), type);
    }
    /**
     * The data to help assistant to constructs the property path.
     * @author Octavi Fornés ofornes@albirar.cat
     * @since 2.1.0
     */
    class BuilderAssistantData
    {
        ISetBuilder<M> builder;
        String lastProp;
    }
    /**
     * The assistant for type-safe builder operations.
     * @author Octavi Fornés ofornes@albirar.cat
     * @since 2.1.0
     */
    class BuilderAssistant implements IProxyHandler
    {
        BuilderAssistantData data;
        
        BuilderAssistant(BuilderAssistant parent)
        {
            this.data = parent.data;
        }
        
        BuilderAssistant(ISetBuilder<M> builder)
        {
            this.data = new BuilderAssistantData();
            this.data.builder = builder;
        }
        
        void endPath()
        {
            if(data.lastProp != null)
            {
                data.builder.addProperty(data.lastProp);
                data.builder.resetPropertyPathStack();
                data.lastProp = null;
            }
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public Object invokeMethod(Object target, Method method, Object[] arguments)
        {
            Class<?> clazz;
            boolean isArray;
            Object aValue;
            /*
             * Check if property get/is method
             * if simple type, add property
             * if object, create another assistant and push current path
             */
            if(isPropertyMethod(method))
            {
                // add current property path
                if(data.lastProp != null)
                {
                    data.builder.pushPropertyPath(data.lastProp);
                }
                data.lastProp = propertyNameFromMethod(method);
                isArray = method.getReturnType().isArray();
                if(isArray)
                {
                    clazz = method.getReturnType().getComponentType();
                }
                else
                {
                    clazz = method.getReturnType();
                }
                if(!BeanUtils.isSimpleProperty(clazz))
                {
                    if(isArray)
                    {
                        aValue = Array.newInstance(clazz, 1);
                        Array.set(aValue, 0, newChildAssistant(this, clazz));
                    }
                    else
                    {
                        aValue = newChildAssistant(this, clazz);
                    }
                    return aValue;
                }
                
                if(byte.class.isAssignableFrom(clazz))
                {
                    return (isArray ? new byte []{(byte)0} : (byte)0);
                }
                if(boolean.class.isAssignableFrom(clazz))
                {
                    return (isArray ? new boolean [] { false} : false);
                }
                if(char.class.isAssignableFrom(clazz))
                {
                    return (isArray ? new char [] { ' '} : ' ');
                }
                if(short.class.isAssignableFrom(clazz))
                {
                    return (isArray ? new short [] { (short)0} : (short)0);
                }
                if(int.class.isAssignableFrom(clazz))
                {
                    return (isArray ? new int [] { 0} : 0);
                }
                if(long.class.isAssignableFrom(clazz))
                {
                    return (isArray ? new long [] { 0L} : 0L);
                }
                if(float.class.isAssignableFrom(clazz))
                {
                    return (isArray ? new float [] { 0F} : 0F);
                }
                if(double.class.isAssignableFrom(clazz))
                {
                    return (isArray ? new double [] { 0D} : 0D);
                }
                if(String.class.isAssignableFrom(clazz))
                {
                    return (isArray ? new String [] { ""} : "");
                }
                if(clazz.isEnum())
                {
                    if(isArray)
                    {
                        aValue = Array.newInstance(clazz, 1);
                        Array.set(aValue, 0, clazz.getEnumConstants()[0]);
                    }
                    else
                    {
                        aValue = clazz.getEnumConstants()[0];
                    }
                    return aValue;
                }
                try
                {
                    if(isArray)
                    {
                        aValue = Array.newInstance(clazz, 1);
                        Array.set(aValue, 0, clazz.newInstance());
                    }
                    else
                    {
                        aValue = clazz.newInstance();
                    }
                    return aValue;
                }
                catch(Exception e)
                {
                    throw new IllegalArgumentException("The type '" + clazz.getName() + "' of property method '" + method.getName() + "' cannot be instantiated", e);
                }
            }
            // Not a 'get/is' method, error
            throw new IllegalArgumentException("The method '" + method.getName() + "' is not a get/is method. Not a java bean property method convention!");
        }
        /**
         * Check if a method is a getter property method.
         * A method is getter if:
         * <ul>
         * <li>parameter.length == 1</li>
         * <li>&amp;&amp; parameter[0] == void</li>
         * <li>&amp;&amp;
         *      <ul>
         *          <li>
         *              <ul>
         *                  <li>name starts with <i>get</i></li>
         *                  <li>&amp;&amp; name.length > 3</li>
         *                  <li>&amp;&amp; return type != void</li>
         *              </ul>
         *          </li>
         *          <li>||</li>
         *          <li>
         *              <ul>
         *                  <li>name starts with <i>is</i></li>
         *                  <li>&amp;&amp; name.length > 2</li>
         *                  <li>&amp;&amp; return type == boolean</li>
         *              </ul>
         *          </li>
         *     </ul>
         * </li>
         * @param method
         * @return
         */
        private boolean isPropertyMethod(Method method)
        {
            return ( method.getParameterTypes().length == 0
                    && ( (method.getName().startsWith("get")
                        && method.getName().length() > 3
                        && !void.class.equals(method.getReturnType()))
                    || (method.getName().startsWith("is")
                            && method.getName().length() > 2
                            && boolean.class.equals(method.getReturnType())) ) );
        }
        /**
         * Return the corresponding property name from a 'get/is' method name.
         * @param method The method
         * @return The propertyName.
         */
        private String propertyNameFromMethod(Method method)
        {
            if(method.getName().startsWith("get"))
            {
                return method.getName().substring(3, 4).toLowerCase().concat(method.getName().substring(4));
            }
            return method.getName().substring(2, 3).toLowerCase().concat(method.getName().substring(3));
        }
    }
}
