package br.com.concepting.framework.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Class that defines an expected exception.
 *
 * @author fvilarinho
 * @since 1.0.0
 *
 * <pre>Copyright (C) 2007 Innovative Thinking.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses.</pre>
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "class")
@JsonIgnoreProperties({"stackTrace", "suppressed", "cause"})
public class ExpectedException extends Exception{
    private static final long serialVersionUID = -2046392122293087621L;
    
    /**
     * Constructor - Initializes the exception.
     */
    public ExpectedException(){
        super();
    }
    
    /**
     * Constructor - Initializes the exception.
     *
     * @param exception Instance that contains the caught exception.
     */
    public ExpectedException(Throwable exception){
        super(exception);
    }
    
    /**
     * Constructor - Initializes the exception.
     *
     * @param message String that contains the exception message.
     */
    public ExpectedException(String message){
        super(message);
    }
}