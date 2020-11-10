package br.com.innovativethinking.framework.persistence.types;

import br.com.innovativethinking.framework.util.StringUtil;

/**
 * Class that defines the types of joins in a relationship.
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
public enum RelationJoinType{
	/**
	 * No join.
	 */
	NONE,

	/**
	 * Inner join.
	 */
	INNER_JOIN,

	/**
	 * Left join.
	 */
	LEFT_JOIN,

	/**
	 * Right join.
	 */
	RIGHT_JOIN;

	/**
	 * Returns the join operator.
	 * 
	 * @return String that contains the operator.
	 */
	public String getOperator(){
		return StringUtil.replaceAll(toString().toLowerCase(), "_", " ");
	}
}