package br.com.concepting.framework.security.exceptions;

import br.com.concepting.framework.exceptions.ExpectedErrorException;

/**
 * Class that defines the exception when the user doesn't have permission to log into
 * the system.
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
public class UserNotAuthorizedException extends ExpectedErrorException{
    private static final long serialVersionUID = 2218995807861634081L;
    
    /**
     * Constructor - Initializes the exception.
     */
    public UserNotAuthorizedException(){
        super();
    }
    
    /**
     * Constructor - Initializes the exception.
     *
     * @param message String that contains the message.
     */
    public UserNotAuthorizedException(String message){
        super(message);
    }
    
    /**
     * Constructor - Initializes the exception.
     *
     * @param exception Instance that contains the caught exception.
     */
    public UserNotAuthorizedException(Throwable exception){
        super(exception);
    }
}