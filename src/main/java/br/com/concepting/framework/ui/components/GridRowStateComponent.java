package br.com.concepting.framework.ui.components;

import br.com.concepting.framework.exceptions.InternalErrorException;

/**
 * Class that defines the criteria to render grid rows with different styles.
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
public class GridRowStateComponent extends OptionStateComponent{
    private static final long serialVersionUID = -2939940944274909545L;
    
    /**
     * @see br.com.concepting.framework.ui.components.BaseComponent#initialize()
     */
    protected void initialize() throws InternalErrorException{
        String expression = getExpression();
        
        if(expression == null){
            expression = Boolean.TRUE.toString();
            
            setExpression(expression);
        }
        
        GridPropertyComponent gridComponent = (GridPropertyComponent) findAncestorWithClass(this, GridPropertyComponent.class);
        
        if(gridComponent == null){
            try{
                gridComponent = (GridPropertyComponent) getParent();
            }
            catch(ClassCastException e){
            }
        }
        
        if(gridComponent != null){
            try{
                GridRowStateComponent rowStateComponent = (GridRowStateComponent) this.clone();
                
                gridComponent.addRowStateComponent(rowStateComponent);
            }
            catch(CloneNotSupportedException e){
                throw new InternalErrorException(e);
            }
        }
    }
}