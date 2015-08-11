/*
 * This file is part of "albirar framework" project.
 * 
 * "albirar framework" is free software: you can redistribute it and/or modify it under the terms of the GNU General
 * Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * "albirar framework" is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with calendar. If not, see
 * <http://www.gnu.org/licenses/>.
 * 
 * Copyright (C) 2013 Octavi Fornés octavi@fornes.cat
 */
package cat.albirar.framework.dynabean.impl;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * A formatter for {@link Date} or {@link Calendar} types.
 * 
 * @author <a href="mailto:ofornes@albirar.cat">Octavi Fornés ofornes@albirar.cat</a>
 * @since 2.0.0
 */
class DynaBeanDateEditor extends PropertyEditorSupport
{
    private static final Logger logger = LoggerFactory.getLogger(DynaBeanDateEditor.class);
    
    private SimpleDateFormat formatter;
    private boolean typeCalendar;
    
    /**
     * Instantiate a formatter for both, {@link Date} and {@link Calendar}.
     * @param pattern The pattern
     * @param typeCalendar If the desired type is {@link Calendar}
     */
    public DynaBeanDateEditor(String pattern, boolean typeCalendar)
    {
        Assert.hasText(pattern, "Should to indicate a date pattern string");
        formatter = new SimpleDateFormat(pattern);
        this.typeCalendar = typeCalendar;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException
    {
        Date d;
        Calendar cal;
        
        try
        {
            d = formatter.parse(text);
            if(typeCalendar)
            {
                cal = Calendar.getInstance();
                cal.setTimeInMillis(d.getTime());
                setValue(cal);
            }
            else
            {
                setValue(d);
            }
        }
        catch(ParseException e)
        {
            logger.error("When parsing text '" + text + "' to Date type", e);
            setValue(null);
        }
    }
}
