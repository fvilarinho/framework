package br.com.innovativethinking.framework.util.types;

/**
 * Class that defines the types of a formula.
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
public enum FormulaType{
	/**
	 * No formula.
	 */
	NONE,
	
	/**
	 * Count values formula.
	 */
	COUNT,

	/**
	 * Sum values formula.
	 */
	SUM,

	/**
	 * Minimum values formula.
	 */
	MIN,

	/**
	 * Maximum values formula.
	 */
	MAX,

	/**
	 * Average values formula.
	 */
	AVERAGE("avg");

	private String id = null;

	/**
	 * Constructor - Defines the type.
	 */
	private FormulaType(){
		setId(toString().toLowerCase());
	}

	/**
	 * Constructor - Defines the type.
	 * 
	 * @param id String that contains the identifier of the formula.
	 */
	private FormulaType(String id){
		setId(id);
	}

	/**
	 * Returns the identifier of the formula.
	 * 
	 * @return String that contains the identifier.
	 */
	public String getId(){
		return this.id;
	}

	/**
	 * Defines the identifier of the formula.
	 * 
	 * @param id String that contains the identifier.
	 */
	public void setId(String id){
		this.id = id;
	}
	
	public static void main(String args[]) {
		System.out.println(AVERAGE.getId());
	}
}