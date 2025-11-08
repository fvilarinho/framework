package br.com.concepting.framework.security.exceptions;

import br.com.concepting.framework.exceptions.ExpectedErrorException;

import java.io.Serial;

/**
 * Class that defines the exception when the user doesn't have permission to access the resource.
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
public class PermissionDeniedException extends ExpectedErrorException{
    @Serial
    private static final long serialVersionUID = 2218995807861634081L;
    
    /**
     * Constructor - Initializes the exception.
     */
    public PermissionDeniedException(){
        super();
    }
    
    /**
     * Constructor - Initializes the exception.
     *
     * @param message String that contains the message.
     */
    public PermissionDeniedException(String message){
        super(message);
    }
    
    /**
     * Constructor - Initializes the exception.
     *
     * @param exception Instance that contains the caught exception.
     */
    public PermissionDeniedException(Throwable exception){
        super(exception);
    }
}