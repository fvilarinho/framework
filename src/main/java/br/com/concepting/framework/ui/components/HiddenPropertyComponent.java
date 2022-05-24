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

    @Override
    protected void buildLabel() throws InternalErrorException{
    }

    @Override
    protected void buildTooltip() throws InternalErrorException{
    }

    @Override
    protected void buildAlignment() throws InternalErrorException{
    }

    @Override
    protected void buildDimensions() throws InternalErrorException{
    }

    @Override
    protected void buildRestrictions() throws InternalErrorException{
        setShowLabel(false);
        
        super.buildRestrictions();
    }

    @Override
    protected void buildStyle() throws InternalErrorException{
    }

    @Override
    protected void initialize() throws InternalErrorException{
        setComponentType(ComponentType.HIDDEN);
        
        super.initialize();
    }

    @Override
    protected void renderPlaceholder() throws InternalErrorException{
    }

    @Override
    protected void renderLabel() throws InternalErrorException{
    }

    @Override
    protected void renderLabelAttribute() throws InternalErrorException{
    }

    @Override
    protected void renderTooltip() throws InternalErrorException{
    }

    @Override
    protected void renderPatternAttribute() throws InternalErrorException{
    }

    @Override
    protected void renderStyle() throws InternalErrorException{
    }
}