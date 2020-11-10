package br.com.innovativethinking.framework.ui.components;

import br.com.innovativethinking.framework.exceptions.InternalErrorException;

/**
 * Class that defines the criteria to render options with different styles.
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
public class OptionStateComponent extends BaseComponent{
	private static final long serialVersionUID = 4128806874828828898L;

	private String  expression = null;
	private Boolean remove     = null;

	/**
	 * Indicates if the option should be removed.
	 * 
	 * @return True/False.
	 */
	public Boolean remove(){
		return this.remove;
	}

	/**
	 * Defines if the option should be removed.
	 * 
	 * @param remove True/False.
	 */
	public void setRemove(Boolean remove){
		this.remove = remove;
	}

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

		try{
			GridColumnComponent gridColumnComponent = (GridColumnComponent)findAncestorWithClass(this, GridColumnComponent.class);

			if(gridColumnComponent == null){
				try{
					gridColumnComponent = (GridColumnComponent)getParent();
				}
				catch(ClassCastException e){
				}
			}

			if(gridColumnComponent != null){
				OptionStateComponent optionStateComponent = (OptionStateComponent)this.clone();

				gridColumnComponent.addOptionStateComponent(optionStateComponent);
			}

			OptionsPropertyComponent optionsPropertyComponent = (OptionsPropertyComponent)findAncestorWithClass(this, OptionsPropertyComponent.class);

			if(optionsPropertyComponent == null){
				try{
					optionsPropertyComponent = (OptionsPropertyComponent)getParent();
				}
				catch(ClassCastException e){
				}
			}

			if(optionsPropertyComponent != null){
				OptionStateComponent optionStateComponent = (OptionStateComponent)this.clone();

				optionsPropertyComponent.addOptionStateComponent(optionStateComponent);
			}
		}
		catch(CloneNotSupportedException e){
			throw new InternalErrorException(e);
		}
	}

	/**
	 * @see br.com.innovativethinking.framework.ui.components.BaseComponent#renderOpen()
	 */
	protected void renderOpen() throws InternalErrorException{
	}

	/**
	 * @see br.com.innovativethinking.framework.ui.components.BaseComponent#renderBody()
	 */
	protected void renderBody() throws InternalErrorException{
	}

	/**
	 * @see br.com.innovativethinking.framework.ui.components.BaseComponent#renderClose()
	 */
	protected void renderClose() throws InternalErrorException{
	}

	/**
	 * @see br.com.innovativethinking.framework.ui.components.BaseComponent#clearAttributes()
	 */
	protected void clearAttributes() throws InternalErrorException{
		super.clearAttributes();

		setExpression(null);
		setRemove(null);
	}
}