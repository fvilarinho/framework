package br.com.innovativethinking.framework.ui.components;

import br.com.innovativethinking.framework.exceptions.InternalErrorException;
import br.com.innovativethinking.framework.util.types.ComponentType;

/**
 * Class that defines the text area component.
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
public class TextAreaPropertyComponent extends TextPropertyComponent{
	private static final long serialVersionUID = -4412598905102532413L;

	private Integer rows    = null;
	private Integer columns = null;

	/**
	 * Returns the number of columns.
	 * 
	 * @return Numeric value that contains the number of columns.
	 */
	public Integer getColumns(){
		return this.columns;
	}

	/**
	 * Defines the number of columns.
	 * 
	 * @param columns Numeric value that contains the number of columns.
	 */
	public void setColumns(Integer columns){
		this.columns = columns;
	}

	/**
	 * Returns the number of rows.
	 * 
	 * @return Numeric value that contains the number of rows.
	 */
	public Integer getRows(){
		return this.rows;
	}

	/**
	 * Defines the number of rows.
	 * 
	 * @param rows Numeric value that contains the number of rows.
	 */
	public void setRows(Integer rows){
		this.rows = rows;
	}

	/**
	 * @see br.com.innovativethinking.framework.ui.components.BasePropertyComponent#renderValue()
	 */
	protected void renderValue() throws InternalErrorException{
	}

	/**
	 * @see br.com.innovativethinking.framework.ui.components.BaseComponent#renderAttributes()
	 */
	protected void renderAttributes() throws InternalErrorException{
		super.renderAttributes();

		Integer rows = getRows();

		if(rows != null && rows > 0){
			print(" rows=\"");
			print(rows);
			print("\"");
		}

		Integer columns = getColumns();

		if(columns != null && columns > 0){
			print(" cols=\"");
			print(columns);
			print("\"");
		}
	}

	/**
	 * @see br.com.innovativethinking.framework.ui.components.TextPropertyComponent#initialize()
	 */
	protected void initialize() throws InternalErrorException{
		setComponentType(ComponentType.TEXT_AREA);

		super.initialize();
	}

	/**
	 * @see br.com.innovativethinking.framework.ui.components.BaseActionFormComponent#renderType()
	 */
	protected void renderType() throws InternalErrorException{
	}

	/**
	 * @see br.com.innovativethinking.framework.ui.components.BasePropertyComponent#renderOpen()
	 */
	protected void renderOpen() throws InternalErrorException{
		super.renderOpen();

		Boolean hasInvalidPropertyDefinition = hasInvalidPropertyDefinition();

		if(hasInvalidPropertyDefinition == null || !hasInvalidPropertyDefinition() || getValue() != null){
			print("<textarea");

			renderAttributes();

			print(">");
		}
	}

	/**
	 * @see br.com.innovativethinking.framework.ui.components.BaseComponent#renderBody()
	 */
	protected void renderBody() throws InternalErrorException{
		String formattedValue = getFormattedValue();
		Boolean hasInvalidPropertyDefinition = hasInvalidPropertyDefinition();

		if(hasInvalidPropertyDefinition == null || !hasInvalidPropertyDefinition()){
			if(formattedValue != null && formattedValue.length() > 0)
				println(formattedValue);
		}
		else
			super.renderBody();
	}

	/**
	 * @see br.com.innovativethinking.framework.ui.components.BasePropertyComponent#renderClose()
	 */
	protected void renderClose() throws InternalErrorException{
		Boolean hasInvalidPropertyDefinition = hasInvalidPropertyDefinition();

		if(hasInvalidPropertyDefinition == null || !hasInvalidPropertyDefinition() || getValue() != null)
			println("</textarea>");

		super.renderClose();
	}

	/**
	 * @see br.com.innovativethinking.framework.ui.components.TextPropertyComponent#clearAttributes()
	 */
	protected void clearAttributes() throws InternalErrorException{
		super.clearAttributes();

		setRows(null);
		setColumns(null);
	}
}