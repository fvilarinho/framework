package br.com.concepting.framework.audit.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Class that defines the auditing's annotation. This should be used in methods
 * or classes to enable the auditing.
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
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
public @interface Auditable{
	/**
	 * Defines the identifier of the auditing resources.
	 * 
	 * @return String that contains the identifier.
	 */
	String resourcesId() default "";

	/**
	 * Defines the identifier of the element that will be audited.
	 * 
	 * @return String that contains the identifier.
	 */
	String id() default "";
}