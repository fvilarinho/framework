package br.com.innovativethinking.framework.security.exceptions;

import br.com.innovativethinking.framework.exceptions.ExpectedWarningException;

/**
 * Class that defines the exception when the password doesn't have the minimum length..
 * 
 * @author fvilarinho
 * @since 3.3.0
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
public class PasswordWithoutMinimumLengthException extends ExpectedWarningException{
	private static final long serialVersionUID = 5978487541406636762L;

	private Integer length        = null;
	private Integer minimumLength = null;

	/**
	 * Constructor - Initializes the exception.
	 * 
	 * @param length Numeric value that contains the password length.
	 * @param minimumLength Numeric value that contains the minimumpassword length.
	 */
	public PasswordWithoutMinimumLengthException(Integer length, Integer minimumLength){
		super();

		setLength(length);
		setMinimumLength(minimumLength);
	}

	/**
	 * Returns the password length.
	 * 
	 * @return Numeric value that contains the password length.
	 */
	public Integer getLength(){
		return this.length;
	}

	/**
	 * Defines the password length.
	 * 
	 * @param length Numeric value that contains the password length.
	 */
	public void setLength(Integer length){
		this.length = length;
	}

	/**
	 * Returns the minimum password length.
	 * 
	 * @return Numeric value that contains the minimum password length.
	 */
	public Integer getMinimumLength(){
		return this.minimumLength;
	}

	/**
	 * Defines the minimum password length.
	 * 
	 * @param minimumLength Numeric value that contains the minimum password length.
	 */
	public void setMinimumLength(Integer minimumLength){
		this.minimumLength = minimumLength;
	}
}