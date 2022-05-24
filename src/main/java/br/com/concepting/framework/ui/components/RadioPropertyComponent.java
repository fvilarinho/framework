package br.com.concepting.framework.ui.components;

import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.util.types.ComponentType;

import javax.servlet.jsp.tagext.Tag;

/**
 * Class that defines the radion button component.
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
public class RadioPropertyComponent extends BaseOptionPropertyComponent{
    private static final long serialVersionUID = 7631291170352730770L;

    @Override
    protected void buildEvents() throws InternalErrorException{
        Tag parent = findAncestorWithClass(this, OptionsPropertyComponent.class);
        
        if(parent == null)
            parent = findAncestorWithClass(this, GridPropertyComponent.class);
        
        if(parent == null)
            parent = getParent();
        
        if(parent == null){
            String id = getId();
            
            if(id != null && id.length() > 0){
                String currentOnClick = getOnClick();
                StringBuilder onClick = new StringBuilder();
                
                onClick.append("if(this.checked){ setObjectValue('");
                onClick.append(id);
                onClick.append("', this.value); } else { setObjectValue('");
                onClick.append(id);
                onClick.append("', ''); }");
                
                if(currentOnClick != null && currentOnClick.length() > 0){
                    onClick.append(" ");
                    onClick.append(currentOnClick);
                    
                    if(!currentOnClick.endsWith(";"))
                        onClick.append(";");
                }
                
                setOnClick(onClick.toString());
            }
        }
        
        super.buildEvents();
    }

    @Override
    protected void initialize() throws InternalErrorException{
        setComponentType(ComponentType.RADIO);
        
        super.initialize();
    }
}