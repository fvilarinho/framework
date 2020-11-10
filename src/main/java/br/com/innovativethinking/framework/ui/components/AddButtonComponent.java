package br.com.innovativethinking.framework.ui.components;

import br.com.innovativethinking.framework.controller.action.types.ActionType;
import br.com.innovativethinking.framework.exceptions.InternalErrorException;
import br.com.innovativethinking.framework.ui.constants.UIConstants;

/**
 * Class that defines the add button component.
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
public class AddButtonComponent extends ButtonComponent{
	private static final long serialVersionUID = -5443454096406239821L;

	/**
	 * @see br.com.innovativethinking.framework.ui.components.ButtonComponent#buildResources()
	 */
	protected void buildResources() throws InternalErrorException{
		setResourcesKey(UIConstants.DEFAULT_ADD_BUTTON_ID);

		super.buildResources();
	}

	/**
	 * @see br.com.innovativethinking.framework.ui.components.ButtonComponent#buildStyleClass()
	 */
	protected void buildStyleClass() throws InternalErrorException{
		setStyleClass(UIConstants.DEFAULT_ADD_BUTTON_STYLE_CLASS);

		super.buildStyleClass();
	}

	/**
	 * @see br.com.innovativethinking.framework.ui.components.ButtonComponent#initialize()
	 */
	protected void initialize() throws InternalErrorException{
		if(!hasAction())
			setActionType(ActionType.ADD);

		super.initialize();
	}
}