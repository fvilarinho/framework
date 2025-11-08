package br.com.concepting.framework.ui.components;

import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.util.PropertyUtil;
import br.com.concepting.framework.util.types.ComponentType;

import java.io.Serial;

/**
 * Class that defines the label component.
 *
 * @author fvilarinho
 * @since 1.0.0
 *
 * <pre>Copyright (C) 2007 Innovative Thinking.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <a href="https://www.gnu.org/licenses"></a>.</pre>
 */
public class LabelComponent extends BasePropertyComponent{
    @Serial
    private static final long serialVersionUID = 4419772883440170233L;

    @Override
    protected String getFormattedValue() throws InternalErrorException{
        return PropertyUtil.format(getValue(), getPattern(), useAdditionalFormatting(), getPrecision(), getCurrentLanguage());
    }

    @Override
    protected void buildRestrictions() throws InternalErrorException{
        String resourcesKey = getResourcesKey();
        String name = getName();
        Object value = getValue();
        
        if((name == null || name.isEmpty()) && value != null)
            setShowLabel(false);
        
        if((name == null || name.isEmpty()) && (resourcesKey != null && !resourcesKey.isEmpty()))
            setShowLabel(false);
        
        super.buildRestrictions();
    }

    @Override
    protected void initialize() throws InternalErrorException{
        ComponentType componentType = getComponentType();
        
        if(componentType == null){
            componentType = ComponentType.LABEL;
            
            setComponentType(componentType);
        }
        
        super.initialize();
        
        if(getPropertyInfo() == null && getValue() == null)
            setValue(getLabel());
    }

    @Override
    protected boolean renderRequiredMark(){
        return false;
    }

    @Override
    protected void renderValue() throws InternalErrorException{
    }

    @Override
    protected void renderPlaceholder() throws InternalErrorException{
    }

    @Override
    protected void renderName() throws InternalErrorException{
    }

    @Override
    protected void renderType() throws InternalErrorException{
    }

    @Override
    protected void renderSize() throws InternalErrorException{
    }

    @Override
    protected void renderEnabled() throws InternalErrorException{
    }

    @Override
    protected void renderReadOnly() throws InternalErrorException{
    }

    @Override
    protected void renderBody() throws InternalErrorException{
        boolean hasInvalidDefinition = hasInvalidDefinition();
        
        if(hasInvalidDefinition)
            super.renderInvalidDefinitionMessage();
        else{
            print("<span");
            
            renderAttributes();
            
            print(">");
            
            String formattedValue = getFormattedValue();
            
            if(formattedValue != null && !formattedValue.isEmpty())
                print(formattedValue);
            
            println("</span>");
        }
    }
}