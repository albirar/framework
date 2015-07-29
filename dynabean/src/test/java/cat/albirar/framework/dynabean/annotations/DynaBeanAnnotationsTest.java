/*
 * This file is part of "dynabean".
 * 
 * "dynabean" is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * "dynabean" is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with calendar. If not, see
 * <http://www.gnu.org/licenses/>.
 * 
 * Copyright (C) 2015 Octavi Fornés <ofornes@albirar.cat>
 */

package cat.albirar.framework.dynabean.annotations;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Vector;

import org.junit.Assert;
import org.junit.Test;

import cat.albirar.framework.dynabean.DynaBeanUtils;
import cat.albirar.framework.dynabean.impl.models.test.AnnotatedModelImpl;
import cat.albirar.framework.dynabean.impl.models.test.IAnnotatedModel;
import cat.albirar.framework.dynabean.impl.models.test.IAnnotatedParentModel;
import cat.albirar.framework.dynabean.impl.models.test.SimpleModelImpl;

/**
 * DynBean annotations tests.
 * 
 * @author <a href="mailto:ofornes@albirar.cat">Octavi Fornés ofornes@albirar.cat</a>
 * @since 2.0
 */
public class DynaBeanAnnotationsTest
{
    /**
     * Test the {@link PropertyDefaultValue} annotation.
     */
    @Test
    public void testDefaultValues()
    {
        IAnnotatedModel model;

        model = DynaBeanUtils.instanceDefaultFactory().newDynaBean(IAnnotatedModel.class);
        // Assert default values
        Assert.assertTrue(model.isPending());
        Assert.assertEquals(Vector.class, model.getNamesList().getClass());
        Assert.assertEquals(IAnnotatedModel.DEFAULT_AMOUNT, model.getAmount(), 0D);
        Assert.assertArrayEquals(IAnnotatedModel.DEFAULT_OTHER_NAMES, model.getOtherNames());
        Assert.assertArrayEquals(IAnnotatedModel.DEFAULT_OTHER_AMOUNTS, model.getOtherAmounts(), 0.0D);
    }

    /**
     * Test the {@link DynaBean} autodetection feature.
     */
    @Test
    public void testDynabeanAutodetection()
    {
        IAnnotatedParentModel model;

        model = DynaBeanUtils.instanceDefaultFactory().newDynaBean(IAnnotatedParentModel.class);
        Assert.assertNull(model.getId());
        Assert.assertNotNull(model.getSubmodel());
        Assert.assertNotNull(model.getSubmodel().getNamesList());
        Assert.assertNull(model.getDynabeanNotAnnotatedModel());
        Assert.assertNotNull(model.getSimpleModel());
        Assert.assertEquals(SimpleModelImpl.class, model.getSimpleModel().getClass());
    }
    /**
     * Test the clone feature with annotated model.
     */
    @Test public void testClone()
    {
        IAnnotatedModel m1, m2;
        
        m1 = DynaBeanUtils.instanceDefaultFactory().newDynaBean(IAnnotatedModel.class);
        m2 = m1.clone();
        Assert.assertEquals(m1, m2);
        // Test clone of date
        Assert.assertNotSame(m1.getNamesList(), m2.getNamesList());
        Assert.assertNotSame(m1.getOtherAmounts(), m2.getOtherAmounts());
        Assert.assertNotSame(m1.getOtherNames(), m2.getOtherNames());
    }
    /**
     * Test the clone feature with annotated model.
     */
    @Test public void testCloneComplex()
    {
        IAnnotatedModel m1, m2;
        IAnnotatedParentModel pm1, pm2;
        
        pm1 = DynaBeanUtils.instanceDefaultFactory().newDynaBean(IAnnotatedParentModel.class);
        m1 = DynaBeanUtils.instanceDefaultFactory().newDynaBean(IAnnotatedModel.class);
        m1.getNamesList().clear();
        m1.getNamesList().add("X");
        m1.getNamesList().add("Y");
        m1.getNamesList().add("Z");
        pm1.setOtherSubmodels(new Vector<IAnnotatedModel>());
        pm1.getOtherSubmodels().add(m1);
        pm1.setId("WWW");
        
        pm2 = pm1.clone();
        
        
        Assert.assertEquals(pm1, pm2);
        Assert.assertNotSame(pm1,  pm2);
        
        Assert.assertEquals(pm1.getOtherSubmodels(), pm2.getOtherSubmodels());
        Assert.assertNotSame(pm1.getOtherSubmodels(), pm2.getOtherSubmodels());
        m2 = pm2.getOtherSubmodels().get(0);
        Assert.assertEquals(m1, m2);
        Assert.assertNotSame(m1, m2);
    }
    /**
     * Test the hashCode feature with annotated model.
     */
    @Test public void testHashsCode()
    {
        IAnnotatedModel m1, m2;
        int n;
        
        m1 = DynaBeanUtils.instanceDefaultFactory().newDynaBean(IAnnotatedModel.class);
        m2 = m1.clone();
        
        for(n = 0; n < 10; n++)
        {
            Assert.assertEquals(m1.hashCode(), m2.hashCode());
        }
    }
    /**
     * Test the serialization feature.
     */
    @Test public void testSerialize() throws Exception
    {
        ByteArrayOutputStream baos;
        IAnnotatedModel model, model1;
        ObjectOutputStream out;
        ByteArrayInputStream bais;
        ObjectInputStream in;
        
        // Prepare bean
        model = DynaBeanUtils.instanceDefaultFactory().newDynaBean(IAnnotatedModel.class);

        // Prepare stream
        baos = new ByteArrayOutputStream();
        out = new ObjectOutputStream(baos);
        out.writeObject(model);
        out.flush();
        
        // Deserialize
        bais = new ByteArrayInputStream(baos.toByteArray());
        in = new ObjectInputStream(bais);
        model1 = (IAnnotatedModel)in.readObject();
        
        // Test if equals
        Assert.assertNotNull(model1);
        Assert.assertEquals(model, model1);
        Assert.assertNotSame(model, model1);
    }
    /**
     * Test the clone with explicit bean implementation.
     */
    @Test public void testMixedEquals()
    {
        IAnnotatedModel m1, m2;
        List<String> names;
        double [] oamount = {10.5D, 125.223D, 998.66271D};
        
        m2 = DynaBeanUtils.instanceDefaultFactory().newDynaBean(IAnnotatedModel.class);
        m1 = new AnnotatedModelImpl();
        m1.setId("XX");
        m1.setAmount(IAnnotatedModel.DEFAULT_AMOUNT + 2.0D);
        names = new Vector<String>();
        names.add("N1");
        names.add("N2");
        names.add("N3");
        m1.setNamesList(names);
        m1.setPending(true);
        names.clear();
        names.add("ON1");
        names.add("ON2");
        names.add("ON3");
        names.add("ON4");
        m1.setOtherNames(names.toArray(new String[]{}));
        m1.setOtherAmounts(oamount);

        m2.setId(m1.getId());
        m2.setAmount(m1.getAmount());
        m2.setNamesList(cloneList(m1.getNamesList()));
        m2.setOtherAmounts(oamount);
        m2.setOtherNames(names.toArray(new String []{}));
        
        Assert.assertTrue(m2.equals(m1));
        m2.setOtherNames(new String[]{"A","B","C"});
        Assert.assertFalse(m2.equals(m1));
    }
    /**
     * Clones a list.
     * @param list The list
     * @return The cloned list
     */
    private <T> List<T> cloneList(List<T> list)
    {
        List<T> dest;
        
        dest = new Vector<T>();
        for(T e : list)
        {
            dest.add(e);
        }
        return dest;
    }
}
