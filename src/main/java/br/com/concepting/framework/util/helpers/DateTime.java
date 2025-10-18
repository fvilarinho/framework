package br.com.concepting.framework.util.helpers;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Date;

/**
 * Class that defines the date/time type.
 *
 * @author fvilarinho
 * @since 3.0.0
 *
 * <pre>Copyright (C) 2007 Innovative Thinking.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <a href="http://www.gnu.org/licenses"></a>.</pre>
 */
@JsonDeserialize(using = DateTimeDeserializer.class)
public class DateTime extends Date{
    private static final long serialVersionUID = -6296515216038853856L;
    
    /**
     * Constructor - Initialize the data type.
     */
    public DateTime(){
        super();
    }
    
    /**
     * Constructor - Initialize the data type.
     *
     * @param milliseconds Numeric value that contains a specific date/time in
     * milliseconds.
     */
    public DateTime(long milliseconds){
        super(milliseconds);
    }
}