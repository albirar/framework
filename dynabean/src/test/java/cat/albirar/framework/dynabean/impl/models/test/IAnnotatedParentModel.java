/*
 * This file is part of "dynabean".
 * 
 * "dynabean" is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * "dynabean" is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with calendar.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright (C) 2015 Octavi Fornés <ofornes@albirar.cat>
 */

package cat.albirar.framework.dynabean.impl.models.test;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

import cat.albirar.framework.dynabean.annotations.DynaBean;
import cat.albirar.framework.dynabean.annotations.PropertyDefaultValue;

/**
 * A parent model with annotated {@link DynaBean} properties for test auto-creation.
 * @author <a href="mailto:ofornes@albirar.cat">Octavi Fornés ofornes@albirar.cat</a>
 * @since 2.0
 */
public interface IAnnotatedParentModel extends Cloneable, Serializable {
	public String getId();
	public void setId(String id);
	@PropertyDefaultValue
	public IAnnotatedModel getSubmodel();
	public void setSubmodel(IAnnotatedModel submodel);
	public List<IAnnotatedModel> getOtherSubmodels();
	@PropertyDefaultValue(implementation=Vector.class)
	public void setOtherSubmodels(List<IAnnotatedModel> otherSubmodels);
    public ISimpleModel getDynabeanNotAnnotatedModel();
    public void setDynabeanNotAnnotatedModel(ISimpleModel model);
    @PropertyDefaultValue(implementation=SimpleModelImpl.class)
    public ISimpleModel getSimpleModel();
    public void setSimpleModel(ISimpleModel model);
	
	public IAnnotatedParentModel clone();
}
