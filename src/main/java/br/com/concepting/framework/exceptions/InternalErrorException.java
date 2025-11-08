package br.com.concepting.framework.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Serial;

/**
 * Class that defines an internal error.
 *
 * @author fvilarinho
 * @since 1.0.0
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
 * along with this program.  If not, see <a href="https://www.gnu.org/licenses"></a>.</pre>
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "class")
@JsonIgnoreProperties({"stackTrace", "suppressed", "cause"})
public class InternalErrorException extends Exception{
    @Serial
    private static final long serialVersionUID = -8931105241132588446L;
    
    /**
     * Constructor - Initializes the internal error.
     */
    public InternalErrorException(){
        super();
    }
    
    /**
     * Constructor - Initializes the internal error.
     *
     * @param message String that contains the message.
     */
    public InternalErrorException(String message){
        super(message);
    }
    
    /**
     * Constructor - Initializes the internal error.
     *
     * @param exception Instance that contains the caught exception.
     */
    public InternalErrorException(Throwable exception){
        super(exception);
    }
}