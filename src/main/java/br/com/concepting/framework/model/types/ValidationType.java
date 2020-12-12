package br.com.concepting.framework.model.types;

import br.com.concepting.framework.util.StringUtil;

/**
 * Class that defines the types of validation.
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
public enum ValidationType{
	/**
	 * No validation.
	 */
	NONE,

	/**
	 * Required validation.
	 */
	REQUIRED,

	/**
	 * Date/time validation.
	 */
	DATE_TIME,

	/**
	 * Numeric validation.
	 */
	NUMBER,

	/**
	 * Compare validation.
	 */
	COMPARE,

	/**
	 * Word count validation.
	 */
	WORD_COUNT,

	/**
	 * Minimum length validation.
	 */
	MINIMUM_LENGTH,

	/**
	 * Maximum length validation.
	 */
	MAXIMUM_LENGTH,

	/**
	 * Numeric range validation.
	 */
	RANGE,

	/**
	 * e-Mail validation.
	 */
	EMAIL,

	/**
	 * Pattern validation.
	 */
	PATTERN,

	/**
	 * Regular expression validation.
	 */
	REGULAR_EXPRESSION,

	/**
	 * Strong password validation.
	 */
	STRONG_PASSWORD,

	/**
	 * Content type validation.
	 */
	CONTENT_TYPE,

	/**
	 * Content size validation.
	 */
	CONTENT_SIZE,

	/**
	 * Custom validation.
	 */
	CUSTOM;

	/**
	 * Returns the identifier of the validation type.
	 * 
	 * @return String that contains the identifier.
	 */
	public String getId(){
		return StringUtil.normalize(toString());
	}
}