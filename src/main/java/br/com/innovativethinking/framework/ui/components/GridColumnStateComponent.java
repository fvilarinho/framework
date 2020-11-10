package br.com.innovativethinking.framework.ui.components;

import br.com.innovativethinking.framework.exceptions.InternalErrorException;

/**
 * Class that defines the criteria to render grid columns with different styles.
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
public class GridColumnStateComponent extends GridColumnComponent{
	private static final long serialVersionUID = 1155474817351663846L;

	private String expression = null;

	/**
	 * Returns the criteria expression.
	 * 
	 * @return String that contains the expression.
	 */
	public String getExpression(){
		return this.expression;
	}

	/**
	 * Defines the criteria expression.
	 * 
	 * @param expression String that contains the expression.
	 */
	public void setExpression(String expression){
		this.expression = expression;
	}

	/**
	 * @see br.com.innovativethinking.framework.ui.components.BaseComponent#initialize()
	 */
	protected void initialize() throws InternalErrorException{
		if(this.expression == null)
			this.expression = Boolean.TRUE.toString();

		GridColumnComponent gridColumnComponent = (GridColumnComponent)findAncestorWithClass(this, GridColumnComponent.class);

		if(gridColumnComponent == null){
			try{
				gridColumnComponent = (GridColumnComponent)getParent();
			}
			catch(ClassCastException e){
			}
		}

		if(gridColumnComponent != null){
			try{
				GridColumnStateComponent columnStateComponent = (GridColumnStateComponent)this.clone();

				gridColumnComponent.addColumnStateComponent(columnStateComponent);
			}
			catch(CloneNotSupportedException e){
				throw new InternalErrorException(e);
			}
		}
	}

	/**
	 * @see br.com.innovativethinking.framework.ui.components.BasePropertyComponent#renderOpen()
	 */
	protected void renderOpen() throws InternalErrorException{
	}

	/**
	 * @see br.com.innovativethinking.framework.ui.components.BaseActionFormComponent#renderBody()
	 */
	protected void renderBody() throws InternalErrorException{
	}

	/**
	 * @see br.com.innovativethinking.framework.ui.components.BasePropertyComponent#renderClose()
	 */
	protected void renderClose() throws InternalErrorException{
	}

	/**
	 * @see br.com.innovativethinking.framework.ui.components.BaseComponent#clearAttributes()
	 */
	protected void clearAttributes() throws InternalErrorException{
		super.clearAttributes();

		setExpression(null);
	}
}