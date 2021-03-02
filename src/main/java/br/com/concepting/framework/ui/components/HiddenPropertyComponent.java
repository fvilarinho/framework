package br.com.concepting.framework.ui.components;

import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.util.types.ComponentType;

/**
 * Class that defines the hidden property component.
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
public class HiddenPropertyComponent extends BasePropertyComponent{
    private static final long serialVersionUID = 3128570106990502918L;
    
    /**
     * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#buildLabel()
     */
    protected void buildLabel() throws InternalErrorException{
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#buildTooltip()
     */
    protected void buildTooltip() throws InternalErrorException{
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BasePropertyComponent#buildAlignment()
     */
    protected void buildAlignment() throws InternalErrorException{
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BaseComponent#buildDimensions()
     */
    protected void buildDimensions() throws InternalErrorException{
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BaseComponent#buildRestrictions()
     */
    protected void buildRestrictions() throws InternalErrorException{
        setShowLabel(false);
        
        super.buildRestrictions();
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BaseComponent#buildStyle()
     */
    protected void buildStyle() throws InternalErrorException{
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BasePropertyComponent#initialize()
     */
    protected void initialize() throws InternalErrorException{
        setComponentType(ComponentType.HIDDEN);
        
        super.initialize();
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BasePropertyComponent#renderPlaceholder()
     */
    protected void renderPlaceholder() throws InternalErrorException{
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#renderLabel()
     */
    protected void renderLabel() throws InternalErrorException{
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BasePropertyComponent#renderLabelAttribute()
     */
    protected void renderLabelAttribute() throws InternalErrorException{
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#renderTooltip()
     */
    protected void renderTooltip() throws InternalErrorException{
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BasePropertyComponent#renderPatternAttribute()
     */
    protected void renderPatternAttribute() throws InternalErrorException{
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BaseComponent#renderStyle()
     */
    protected void renderStyle() throws InternalErrorException{
    }
}