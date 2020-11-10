package br.com.concepting.framework.ui.components.types;

/**
 * Class that defines the types of visibility of a component.
 * 
 * @author fvilarinho
 * @since 3.0.0
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
public enum VisibilityType{
	/**
	 * The component is not rendered.
	 */
	NONE,

	/**
	 * The component is rendered but is invisible.
	 */
	HIDDEN,

	/**
	 * The component is rendered and visible.
	 */
	VISIBLE;
}