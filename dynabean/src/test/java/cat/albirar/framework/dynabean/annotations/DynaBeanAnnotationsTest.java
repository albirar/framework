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

package cat.albirar.framework.dynabean.annotations;

import java.util.Vector;

import org.junit.Assert;
import org.junit.Test;

import cat.albirar.framework.dynabean.DynaBeanUtils;
import cat.albirar.framework.dynabean.impl.models.IAnnotatedModel;
import cat.albirar.framework.dynabean.impl.models.IAnnotatedParentModel;

/**
 * DynBean annotations tests.
 * @author <a href="mailto:ofornes@albirar.cat">Octavi Fornés ofornes@albirar.cat</a>
 * @since 2.0
 */
public class DynaBeanAnnotationsTest {
	/**
	 * Test the {@link PropertyDefaultValue} annotation.
	 */
	@Test public void testDefaultValues() {
		IAnnotatedModel model;
		
		model = DynaBeanUtils.instanceFactory().newDynaBean(IAnnotatedModel.class);
		// Assert default values
		Assert.assertTrue(model.isPending());
		Assert.assertEquals(Vector.class, model.getNamesList().getClass());
		Assert.assertEquals(IAnnotatedModel.DEFAULT_AMOUNT, model.getAmount(), 0D);
	}
	
	@Test public void testDynabeanAutodetection() {
		IAnnotatedParentModel model;
		
		model = DynaBeanUtils.instanceFactory().newDynaBean(IAnnotatedParentModel.class);
		Assert.assertNull(model.getId());
		Assert.assertNotNull(model.getSubmodel());
		Assert.assertNotNull(model.getSubmodel().getNamesList());
		Assert.assertNull(model.getDynabeanNotAnnotatedModel());
	}
}
