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

package cat.albirar.framework.sets.tsb;

import cat.albirar.framework.sets.ISet;
import cat.albirar.framework.sets.registry.INamedSet;

/**
 * A contract for type safe builder of sets.
 * <p>A type safe builder can be used as a <i>fluent API</i>:
 * <pre>
 * ITypeSafeBuilder tsb;
 * ISet set;
 * 
 * tsb = TypeSafeBuilderFactory.instantiateBuilder(Model.class);
 * set = tsb.addProperty(tsb.getModel().getPropertyOne())
 *          .addProperty(tsb.getModel().getObject().getPropertyTwo())
 *          .build();
 * </pre>
 * </p>
 * <p>The build set contains the following properties:
 * <ul>
 * <li>propertyOne</li>
 * <li>object.propertyTwo</li>
 * </ul>
 * </p>
 * <p>If you want to build a {@link INamedSet named set}, use as following:
 * <pre>
 * ITypeSafeBuilder tsb;
 * INamedSet set;
 * 
 * tsb = TypeSafeBuilderFactory.instantiateBuilder(Model.class);
 * set = NamedSetUtils.instantiateNamedSetFor(tsb.addProperty(tsb.getModel().getPropertyOne())
 *          .addProperty(tsb.getModel().getObject().getPropertyTwo())
 *          .build(), "name");
 * </pre>
 * <p>
 * @param <M> The model type
 * @see TypeSafeBuilderFactory
 * @author Octavi Fornés ofornes@albirar.cat
 * @since 2.1.0
 */
public interface ITypeSafeBuilder<M>
{
    /**
     * To use with a get/is property method to add them to the set.
     * @param property The non-significant value from get/is property method.
     * @return The builder
     * @throws IllegalArgumentException If the called method is not a javaBean property convention method 
     */
    public ITypeSafeBuilder<M> addProperty(byte property);
    /**
     * To use with a get/is property method to add them to the set.
     * @param property The non-significant value from get/is property method.
     * @return The builder
     * @throws IllegalArgumentException If the called method is not a javaBean property convention method 
     */
    public ITypeSafeBuilder<M> addProperty(boolean property);
    /**
     * To use with a get/is property method to add them to the set.
     * @param property The non-significant value from get/is property method.
     * @return The builder
     * @throws IllegalArgumentException If the called method is not a javaBean property convention method 
     */
    public ITypeSafeBuilder<M> addProperty(char property);
    /**
     * To use with a get/is property method to add them to the set.
     * @param property The non-significant value from get/is property method.
     * @return The builder
     * @throws IllegalArgumentException If the called method is not a javaBean property convention method 
     */
    public ITypeSafeBuilder<M> addProperty(short property);
    /**
     * To use with a get/is property method to add them to the set.
     * @param property The non-significant value from get/is property method.
     * @return The builder
     * @throws IllegalArgumentException If the called method is not a javaBean property convention method 
     */
    public ITypeSafeBuilder<M> addProperty(int property);
    /**
     * To use with a get/is property method to add them to the set.
     * @param property The non-significant value from get/is property method.
     * @return The builder
     * @throws IllegalArgumentException If the called method is not a javaBean property convention method 
     */
    public ITypeSafeBuilder<M> addProperty(long property);
    /**
     * To use with a get/is property method to add them to the set.
     * @param property The non-significant value from get/is property method.
     * @return The builder
     * @throws IllegalArgumentException If the called method is not a javaBean property convention method 
     */
    public ITypeSafeBuilder<M> addProperty(double property);
    /**
     * To use with a get/is property method to add them to the set.
     * @param property The non-significant value from get/is property method.
     * @return The builder
     * @throws IllegalArgumentException If the called method is not a javaBean property convention method 
     */
    public ITypeSafeBuilder<M> addProperty(float property);
    /**
     * To use with a get/is property method to add them to the set.
     * @param property The non-significant value from get/is property method.
     * @return The builder
     * @throws IllegalArgumentException If the called method is not a javaBean property convention method 
     */
    public ITypeSafeBuilder<M> addProperty(String property);
    /**
     * To use with a get/is property method to add them to the set.
     * @param property The non-significant value from get/is property method.
     * @return The builder
     * @throws IllegalArgumentException If the called method is not a javaBean property convention method 
     */
    public ITypeSafeBuilder<M> addProperty(Object property);
    /**
     * To use with a get/is property method to add them to the set.
     * @param property The non-significant value from get/is property method.
     * @return The builder
     * @throws IllegalArgumentException If the called method is not a javaBean property convention method 
     */
    public ITypeSafeBuilder<M> addProperty(byte [] property);
    /**
     * To use with a get/is property method to add them to the set.
     * @param property The non-significant value from get/is property method.
     * @return The builder
     * @throws IllegalArgumentException If the called method is not a javaBean property convention method 
     */
    public ITypeSafeBuilder<M> addProperty(boolean [] property);
    /**
     * To use with a get/is property method to add them to the set.
     * @param property The non-significant value from get/is property method.
     * @return The builder
     * @throws IllegalArgumentException If the called method is not a javaBean property convention method 
     */
    public ITypeSafeBuilder<M> addProperty(char [] property);
    /**
     * To use with a get/is property method to add them to the set.
     * @param property The non-significant value from get/is property method.
     * @return The builder
     * @throws IllegalArgumentException If the called method is not a javaBean property convention method 
     */
    public ITypeSafeBuilder<M> addProperty(short [] property);
    /**
     * To use with a get/is property method to add them to the set.
     * @param property The non-significant value from get/is property method.
     * @return The builder
     * @throws IllegalArgumentException If the called method is not a javaBean property convention method 
     */
    public ITypeSafeBuilder<M> addProperty(int [] property);
    /**
     * To use with a get/is property method to add them to the set.
     * @param property The non-significant value from get/is property method.
     * @return The builder
     * @throws IllegalArgumentException If the called method is not a javaBean property convention method 
     */
    public ITypeSafeBuilder<M> addProperty(long [] property);
    /**
     * To use with a get/is property method to add them to the set.
     * @param property The non-significant value from get/is property method.
     * @return The builder
     * @throws IllegalArgumentException If the called method is not a javaBean property convention method 
     */
    public ITypeSafeBuilder<M> addProperty(double [] property);
    /**
     * To use with a get/is property method to add them to the set.
     * @param property The non-significant value from get/is property method.
     * @return The builder
     * @throws IllegalArgumentException If the called method is not a javaBean property convention method 
     */
    public ITypeSafeBuilder<M> addProperty(float [] property);
    /**
     * To use with a get/is property method to add them to the set.
     * @param property The non-significant value from get/is property method.
     * @return The builder
     * @throws IllegalArgumentException If the called method is not a javaBean property convention method 
     */
    public ITypeSafeBuilder<M> addProperty(String [] property);
    /**
     * To use with a get/is property method to add them to the set.
     * @param property The non-significant value from get/is property method.
     * @return The builder
     * @throws IllegalArgumentException If the called method is not a javaBean property convention method 
     */
    public ITypeSafeBuilder<M> addProperty(Object [] property);
    /**
     * The model for this builder.
     * @return The model
     */
    public M getModel();
    /**
     * The set from the current builder status.
     * @return The set
     */
    public ISet<M> build();
}
