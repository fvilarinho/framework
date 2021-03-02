package br.com.concepting.framework.ui.components;

import br.com.concepting.framework.exceptions.InternalErrorException;

/**
 * Class that defines the option of the list component.
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
public class ListOptionPropertyComponent extends BaseOptionPropertyComponent{
    private static final long serialVersionUID = 5718731968797128195L;
    
    /**
     * @see br.com.concepting.framework.ui.components.BaseOptionPropertyComponent#renderSelectionAttribute()
     */
    protected void renderSelectionAttribute() throws InternalErrorException{
        Boolean selected = isSelected();
        
        if(selected != null && selected)
            print(" selected");
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#renderLabel()
     */
    protected void renderLabel() throws InternalErrorException{
        print(getLabel());
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#renderType()
     */
    protected void renderType() throws InternalErrorException{
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BaseComponent#renderName()
     */
    protected void renderName() throws InternalErrorException{
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BaseComponent#renderId()
     */
    protected void renderId() throws InternalErrorException{
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#renderOpen()
     */
    protected void renderOpen() throws InternalErrorException{
        print("<option");
        
        renderAttributes();
        
        print(">");
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BaseComponent#renderBody()
     */
    protected void renderBody() throws InternalErrorException{
        renderLabel();
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BasePropertyComponent#renderClose()
     */
    protected void renderClose() throws InternalErrorException{
        println("</option>");
    }
}