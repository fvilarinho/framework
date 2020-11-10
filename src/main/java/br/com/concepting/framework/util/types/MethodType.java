package br.com.concepting.framework.util.types;

/**
 * Class that defines the types of methods.
 * 
 * @author fvilarinho
 * @since 3.6.0
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
public enum MethodType{
	/**
	 * Defines the GET method.
	 */
	GET,

	/**
	 * Defines the POST method.
	 */
	POST,

	/**
	 * Defines the PUT method.
	 */
	PUT,

	/**
	 * Defines the DELETE method.
	 */
	DELETE,

	/**
	 * Defines the TRACE method.
	 */
	TRACE,

	/**
	 * Defines the OPTIONS method.
	 */
	OPTIONS,

	/**
	 * Defines the HEAD method.
	 */
	HEAD;
}