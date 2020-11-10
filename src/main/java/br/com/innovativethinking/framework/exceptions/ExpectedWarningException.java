package br.com.innovativethinking.framework.exceptions;

/**
 * Class that defines an expected warning.
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
public class ExpectedWarningException extends ExpectedException{
	private static final long serialVersionUID = -8727920706814785344L;

	/**
	 * Constructor - Initializes the exception.
	 */
	public ExpectedWarningException(){
		super();
	}

	/**
	 * Constructor - Initializes the exception.
	 * 
	 * @param exception Instance that contains the caught exception.
	 */
	public ExpectedWarningException(Throwable exception){
		super(exception);
	}

	/**
	 * Constructor - Initializes the exception.
	 * 
	 * @param message String that contains the exception message.
	 */
	public ExpectedWarningException(String message){
		super(message);
	}

}