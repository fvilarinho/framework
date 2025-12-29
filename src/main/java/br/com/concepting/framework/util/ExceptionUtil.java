package br.com.concepting.framework.util;

import br.com.concepting.framework.exceptions.ExpectedErrorException;
import br.com.concepting.framework.exceptions.ExpectedException;
import br.com.concepting.framework.exceptions.ExpectedWarningException;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.resources.exceptions.InvalidResourcesException;
import br.com.concepting.framework.security.exceptions.PermissionDeniedException;
import br.com.concepting.framework.security.exceptions.UserNotAuthorizedException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;

/**
 * Class responsible to manipulate exception.
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
public class ExceptionUtil{
    /**
     * Returns the exception cause.
     *
     * @param exception Instance that contains the exception.
     * @return Instance that contains the cause.
     */
    public static Throwable getCause(Throwable exception){
        Throwable e = exception;
        
        while(e instanceof InvocationTargetException || e instanceof UndeclaredThrowableException){
            if(e instanceof InvocationTargetException e1)
                e = e1.getTargetException();
            
            if(e instanceof UndeclaredThrowableException e1)
                e = e1.getUndeclaredThrowable();
        }
        
        return e;
    }
    
    /**
     * Returns the stack trace of an exception.
     *
     * @param exception Instance that contains the exception.
     * @return String that contains the stack trace.
     */
    public static String getTrace(Throwable exception){
        if(exception != null){
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            PrintStream stream = new PrintStream(buffer);
            
            exception.printStackTrace(stream);
            
            return buffer.toString();
        }
        
        return null;
    }
    
    /**
     * Indicates if an exception belongs to another one.
     *
     * @param exception Class that defines the exception.
     * @param parent Class that defines the exception parent.
     * @return True/False.
     */
    public static boolean belongsTo(Class<?> exception, Class<?> parent){
        if(exception != null && parent != null){
            if(exception.equals(parent))
                return true;
            
            Class<?> e = exception.getSuperclass();

            return belongsTo(e, parent);
        }
        
        return false;
    }
    
    /**
     * Indicates if the exception is expected.
     *
     * @param exception Instance that contains the exception.
     * @return True/False.
     */
    public static boolean isExpectedException(Throwable exception){
        return (exception instanceof ExpectedException);
    }
    
    /**
     * Indicates if the instance is a warning exception.
     *
     * @param exception Instance that contains the exception.
     * @return True/False.
     */
    public static boolean isExpectedWarningException(Throwable exception){
        return (exception instanceof ExpectedWarningException);
    }
    
    /**
     * Indicates if the instance is an error exception.
     *
     * @param exception Instance that contains the exception.
     * @return True/False.
     */
    public static boolean isExpectedErrorException(Throwable exception){
        return (exception instanceof ExpectedErrorException);
    }
    
    /**
     * Indicates if the instance is an internal error exception.
     *
     * @param exception Instance that contains the exception.
     * @return True/False.
     */
    public static boolean isInternalErrorException(Throwable exception){
        return (exception instanceof InternalErrorException);
    }
    
    /**
     * Indicates if the instance is an invalid resource exception.
     *
     * @param exception Instance that contains the exception.
     * @return True/False.
     */
    public static boolean isInvalidResourceException(Throwable exception){
        return (exception instanceof InvalidResourcesException);
    }
    
    /**
     * Indicates if the instance is a permission denied's exception.
     *
     * @param exception Instance that contains the exception.
     * @return True/False.
     */
    public static boolean isPermissionDeniedException(Throwable exception){
        return (exception instanceof PermissionDeniedException);
    }
    
    /**
     * Indicates if the instance is an exception when the user is not authorized.
     *
     * @param exception Instance that contains the exception.
     * @return True/False.
     */
    public static boolean isUserNotAuthorized(Throwable exception){
        return (exception instanceof UserNotAuthorizedException);
    }
    
    /**
     * Returns the identifier of the exception.
     *
     * @param exception Instance that contains the exception.
     * @return String that contains the identifier of the exception.
     */
    public static String getId(Throwable exception){
        if(exception != null)
            return getId(exception.getClass());
        
        return null;
    }
    
    /**
     * Returns the identifier of the exception.
     *
     * @param exceptionClass Class that defines the exception.
     * @return String that contains the identifier of the exception.
     */
    public static String getId(Class<? extends Throwable> exceptionClass){
        if(exceptionClass != null){
            String exceptionId = StringUtil.replaceLast(exceptionClass.getSimpleName(), Exception.class.getSimpleName(), "");
            StringBuilder buffer = new StringBuilder();
            
            buffer.append(exceptionId.substring(0, 1).toLowerCase());
            buffer.append(exceptionId.substring(1));
            
            return buffer.toString();
        }
        
        return null;
    }
}