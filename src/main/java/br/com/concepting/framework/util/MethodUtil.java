package br.com.concepting.framework.util;

import java.lang.reflect.Method;

import org.apache.commons.beanutils.MethodUtils;

/**
 * Class responsible to manipulate methods of a class.
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
public class MethodUtil extends MethodUtils{
	public static Method getMethod(Class<?> instanceClass, String methodName){
		try{
			return instanceClass.getMethod(methodName);
		}
		catch(Throwable e){
			return null;
		}
	}
	
	/**
	 * Returns the instance of a method in the JVM stack trace.
	 *
	 * @param level Numeric value that contains the desired level in stack
	 * trace.
	 * @return Instance that contains the method.
	 * @throws ClassNotFoundException Occurs when was not possible to execute
	 * the operation..
	 */
	public static Method getMethodFromStackTrace(Integer level) throws ClassNotFoundException{
		if(level == null)
			return null;

		Thread currentThread = Thread.currentThread();
		StackTraceElement stackTraceElement = null;
		StackTraceElement stackTrace[] = currentThread.getStackTrace();
		int cont = 0;

		for(cont = 0 ; cont < stackTrace.length ; cont++){
			stackTraceElement = stackTrace[cont];

			if(stackTraceElement.getMethodName().equals("getMethodFromStackTrace")){
				cont += level;

				stackTraceElement = stackTrace[cont];

				break;
			}

			stackTraceElement = null;
		}

		if(stackTraceElement == null)
			return null;

		Class<?> clazz = Class.forName(stackTraceElement.getClassName());
		Method methods[] = clazz.getDeclaredMethods();

		for(Method method : methods)
			if(method.getName().equals(stackTraceElement.getMethodName()))
				return method;

		return null;
	}
}