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

import cat.albirar.framework.sets.impl.SetBuilderDefaultImpl;

/**
 * A builder factory for build sets.
 * <p>Use:</p>
 * <p>Once a builder is instantiated (see {@link SetBuilderDefaultImpl}), operate as:
 * <pre>
 * ISet set;
 * 
 * set = builder.instantiateBuilderFor(IModel.class)
 *      .addProperty("propertyOne")
 *      .addProperty("propertyTwo")
 *      .pushProperty("propertyAnotherModel")
 *      .addProperty("propertyAnotherOne")
 *      .addProperty("propertyThirdModel.propertyAnother")
 *      .popProperty()
 *      .addProperty("propertyFour")
 *      .build();
 * </pre>
 * The {@code push} method doesn't to be "leveled" with {@code pop} method if not needed:
 * <pre>
 * ISet set;
 * 
 * set = SetBuilderUtils.instantiateSetBuilderFor(IModel.class)
 *      .addProperty("propertyOne")
 *      .addProperty("propertyTwo")
 *      .pushProperty("propertyAnotherModel")
 *      .addProperty("propertyAnotherOne")
 *      .addProperty("propertyThirdModel.propertyAnother")
 *      .build();
 * </pre>
 * </p>
 * @author Octavi Fornés ofornes@albirar.cat
 * @since 2.1.0
 */
public interface ISetBuilder
{
    /**
     * Add a property to the set.
     * @param propertyPath The property path
     * @return The builder itself
     * @throws IllegalArgumentException If the {@code propertyPath} is null or empty or whitespace or root model have not a property denoted with this path
     */
    public ISetBuilder addProperty(String propertyPath);
    /**
     * Push builder relative path to the path denoted with the indicated {@code propertyPath}.
     * Simplifies building operations with models as properties, so push to the relative model property and then indicate properties relative
     * to the pushed path.
     * @param propertyPath The property path to set relative path to build
     * @return The builder itself
     * @throws IllegalArgumentException If the {@code propertyPath} is null or empty or whitespace or root model have not a property denoted with this path or property denoted is not a model
     */
    public ISetBuilder pushPropertyPath(String propertyPath);
    /**
     * Pop builder relative path to the path denoted with the last {@code push} operation or root if no more {@code pushed} path are available.
     * Is the counterpart of {@link #pushPropertyPath(String)}
     * @return The builder itself with relative path to the last {@link #pushPropertyPath(String) push} operation or {@code root path} if no more pushed path are available
     */
    public ISetBuilder popPropertyPath();
    /**
     * Final operation of building, creates the {@link ISet set} with the information recorded from the instantiation.
     * @return The builded set. Can be empty if no operations was made since call to {@link #instantiateBuilderFor(Class) instantiate} to call to {@link #build()}
     */
    public ISet build();
}
