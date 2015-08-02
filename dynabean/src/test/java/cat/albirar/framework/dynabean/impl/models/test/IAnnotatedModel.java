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
 * An annotated model to test functionality.
 * @author <a href="mailto:ofornes@albirar.cat">Octavi Fornés ofornes@albirar.cat</a>
 * @since 2.0
 */
@DynaBean
public interface IAnnotatedModel extends Serializable, Cloneable {
	public static final double DEFAULT_AMOUNT = 25.2D;
    public static final double DEFAULT_OTHER_AMOUNTS_1 = 10.5D;
    public static final double DEFAULT_OTHER_AMOUNTS_2 = 3.1416D;
    public static final double DEFAULT_OTHER_AMOUNTS_3 = 1D;
    public static final double DEFAULT_OTHER_AMOUNTS_4 = 25.599288181D;
    public static final String [] DEFAULT_NAMES = {"A","B","C"};
	public static final double [] DEFAULT_OTHER_AMOUNTS = { DEFAULT_OTHER_AMOUNTS_1, DEFAULT_OTHER_AMOUNTS_2, DEFAULT_OTHER_AMOUNTS_3, DEFAULT_OTHER_AMOUNTS_4};
	public static final String [] DEFAULT_OTHER_NAMES = {"aa","bb","cc"};
	
	public String getId();
	public void setId(String id);
	
	@PropertyDefaultValue(implementation=Vector.class, value={"A", "B", "C"})
	public List<String> getNamesList();
	public void setNamesList(List<String> names);
	
	public boolean isPending();
	@PropertyDefaultValue("true")
	public void setPending(boolean pending);
	
	@PropertyDefaultValue("" + DEFAULT_AMOUNT)
	public double getAmount();
	public void setAmount(double a);
	
    public String [] getOtherNames();
    @PropertyDefaultValue({"aa","bb","cc"})
    public void setOtherNames(String [] otherNames);
    
    @PropertyDefaultValue({"" + DEFAULT_OTHER_AMOUNTS_1,"" + DEFAULT_OTHER_AMOUNTS_2,"" + DEFAULT_OTHER_AMOUNTS_3, "" + DEFAULT_OTHER_AMOUNTS_4})
    public double [] getOtherAmounts();
    public void setOtherAmounts(double [] otherAmounts);

    public IAnnotatedModel clone();
}
