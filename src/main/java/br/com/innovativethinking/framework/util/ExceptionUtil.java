package br.com.innovativethinking.framework.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;

import br.com.innovativethinking.framework.exceptions.ExpectedErrorException;
import br.com.innovativethinking.framework.exceptions.ExpectedException;
import br.com.innovativethinking.framework.exceptions.ExpectedWarningException;
import br.com.innovativethinking.framework.exceptions.InternalErrorException;
import br.com.innovativethinking.framework.model.exceptions.ItemNotFoundException;
import br.com.innovativethinking.framework.resources.exceptions.InvalidResourcesException;
import br.com.innovativethinking.framework.security.exceptions.PermissionDeniedException;
import br.com.innovativethinking.framework.security.exceptions.UserNotAuthorizedException;

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
			if(e instanceof InvocationTargetException)
				e = ((InvocationTargetException)e).getTargetException();

			if(e instanceof UndeclaredThrowableException)
				e = ((UndeclaredThrowableException)e).getUndeclaredThrowable();
		}

		return e;
	}

	/**
	 * Returns the stack trace of a exception.
	 * 
	 * @param exception Instance that contains the exception.
	 * @return String that contains the stack trace.
	 */
	public static String getTrace(Throwable exception){
		if(exception != null){
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			PrintStream stream = new PrintStream(buffer);

			exception.printStackTrace(stream);

			return new String(buffer.toByteArray());
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
	public static Boolean belongsTo(Class<?> exception, Class<?> parent){
		if(exception != null && parent != null){
			if(exception.equals(parent))
				return true;

			Class<?> e = exception.getSuperclass();

			if(e == null)
				return false;

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
	public static Boolean isExpectedException(Throwable exception){
		return (exception instanceof ExpectedException);
	}

	/**
	 * Indicates if the instance is a warning exception.
	 * 
	 * @param exception Instance that contains the exception.
	 * @return True/False.
	 */
	public static Boolean isExpectedWarningException(Throwable exception){
		return (exception instanceof ExpectedWarningException);
	}

	/**
	 * Indicates if the instance is an error exception.
	 * 
	 * @param exception Instance that contains the exception.
	 * @return True/False.
	 */
	public static Boolean isExpectedErrorException(Throwable exception){
		return (exception instanceof ExpectedErrorException);
	}

	/**
	 * Indicates if the instance is an internal error exception.
	 * 
	 * @param exception Instance that contains the exception.
	 * @return True/False.
	 */
	public static Boolean isInternalErrorException(Throwable exception){
		return (exception instanceof InternalErrorException);
	}

	/**
	 * Indicates if the instance is an invalid resources exception.
	 * 
	 * @param exception Instance that contains the exception.
	 * @return True/False.
	 */
	public static Boolean isInvalidResourceException(Throwable exception){
		return (exception instanceof InvalidResourcesException || exception instanceof ItemNotFoundException);
	}

	/**
	 * Indicates if the instance is a permission denied exception.
	 * 
	 * @param exception Instance that contains the exception.
	 * @return True/False.
	 */
	public static Boolean isPermissionDeniedException(Throwable exception){
		return (exception instanceof PermissionDeniedException);
	}

	/**
	 * Indicates if the instance is an exception when the user is not authorized.
	 * 
	 * @param exception Instance that contains the exception.
	 * @return True/False.
	 */
	public static Boolean isUserNotAuthorized(Throwable exception){
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