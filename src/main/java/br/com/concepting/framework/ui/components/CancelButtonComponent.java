package br.com.concepting.framework.ui.components;

import br.com.concepting.framework.controller.action.types.ActionType;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.ui.constants.UIConstants;

/**
 * Class that defines the cancel button component.
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
public class CancelButtonComponent extends ConfirmButtonComponent{
    private static final long serialVersionUID = 4400681451550484210L;
    
    /**
     * @see br.com.concepting.framework.ui.components.ButtonComponent#buildResources()
     */
    protected void buildResources() throws InternalErrorException{
        setResourcesKey(UIConstants.DEFAULT_CANCEL_BUTTON_ID);
        
        super.buildResources();
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.ButtonComponent#buildStyleClass()
     */
    protected void buildStyleClass() throws InternalErrorException{
        setStyleClass(UIConstants.DEFAULT_CANCEL_BUTTON_STYLE_CLASS);
        
        super.buildStyleClass();
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.ButtonComponent#initialize()
     */
    protected void initialize() throws InternalErrorException{
        if(!hasAction())
            setActionType(ActionType.CANCEL);
        
        super.initialize();
    }
}