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

package cat.albirar.framework.sets;

/**
 * A builder for create sets.
 * <p>Implemented as a <i>fluent</i> API, you can use in fluent mode (instantiate and chained calls):
 * <pre>
 * ISet set;
 * 
 * set = SetUtils.instantiateBuilderFor(IModel.class)
 *      .addProperty("propertyOne")
 *      .addProperty("propertyTwo")
 *      .pushProperty("propertyAnotherModel")
 *      .addProperty("propertyAnotherOne")
 *      .addProperty("propertyThirdModel.propertyAnother")
 *      .popProperty()
 *      .addProperty("propertyFour")
 *      .build();
 * </pre>
 * </p>
 * <p>Or in classic mode (assign and call):
 * <pre>
 * ISetBuilder<T> builder;
 * ISet set;
 * 
 * builder = SetUtils.instantiateSetBuilderFor(IModel.class);
 * builder.addProperty("propertyOne");
 * builder.addProperty("propertyTwo");
 * builder.pushProperty("propertyAnotherModel");
 * builder.addProperty("propertyAnotherOne");
 * builder.addProperty("propertyThirdModel.propertyAnother");
 * set = builder.build();
 * </pre>
 * </p>
 * @see SetUtils
 * @author Octavi Fornés ofornes@albirar.cat
 * @since 2.1.0
 */
public interface ISetBuilder<T>
{
    /**
     * Add a property to the set.
     * @param propertyPath The property path
     * @return The builder itself
     * @throws IllegalArgumentException If the {@code propertyPath} is null or empty or whitespace or root model have not a property denoted with this path
     */
    public ISetBuilder<T> addProperty(String propertyPath);
    /**
     * Push builder relative path to the path denoted with the indicated {@code propertyPath}.
     * Simplifies building operations with models as properties, so push to the relative model property and then indicate properties relative
     * to the pushed path.
     * @param propertyPath The property path to set relative path to build
     * @return The builder itself
     * @throws IllegalArgumentException If the {@code propertyPath} is null or empty or whitespace or root model have not a property denoted with this path or property denoted is not a model
     */
    public ISetBuilder<T> pushPropertyPath(String propertyPath);
    /**
     * Pop builder relative path to the path denoted with the last {@code push} operation or root if no more {@code pushed} path are available.
     * Is the counterpart of {@link #pushPropertyPath(String)}
     * @return The builder itself with relative path to the last {@link #pushPropertyPath(String) push} operation or {@code root path} if no more pushed path are available
     */
    public ISetBuilder<T> popPropertyPath();
    /**
     * Peek at property path stack and return the last property path pushed.
     * @return The last property name pushed, or null if no property are pushed
     */
    public String peekPropertyPathStack();
    /**
     * Reset the stack of property path.
     */
    public void resetPropertyPathStack();
    /**
     * Gets the current property path, relative to root model.
     * @return The current property path. If path is at root model, returns an empty string
     */
    public String getCurrentPropertyPath();
    /**
     * The root model for this builder.
     * @return The root model
     */
    public Class<? extends T> getModelRoot();
    /**
     * Final operation of building, creates the {@link ISet set} with the information recorded from the instantiation.
     * @return The builded set. Can be empty if no operations was made since call to {@link #instantiateBuilderFor(Class) instantiate} to call to {@link #build()}
     */
    public ISet<T> build();
}
