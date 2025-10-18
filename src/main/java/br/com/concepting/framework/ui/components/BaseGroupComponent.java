package br.com.concepting.framework.ui.components;

import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.ui.constants.UIConstants;

/**
 * Class that defines the basic implementation for a group component.
 *
 * @author fvilarinho
 * @since 3.3.0
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
 * along with this program.  If not, see <a href="http://www.gnu.org/licenses"></a>.</pre>
 */
public abstract class BaseGroupComponent extends BaseActionFormComponent{
    private static final long serialVersionUID = 8751796595605205394L;

    @Override
    protected void buildStyleClass() throws InternalErrorException{
        String styleClass = getStyleClass();
        
        if(styleClass == null || styleClass.isEmpty()){
            styleClass = UIConstants.DEFAULT_GROUP_STYLE_CLASS;
            
            setStyleClass(styleClass);
        }
        
        String labelStyleClass = getLabelStyleClass();
        
        if(labelStyleClass == null || labelStyleClass.isEmpty()){
            labelStyleClass = UIConstants.DEFAULT_GROUP_LABEL_STYLE_CLASS;
            
            setLabelStyleClass(labelStyleClass);
        }
        
        super.buildStyleClass();
    }

    @Override
    protected void renderLabelOpen() throws InternalErrorException{
        print("<legend");
        
        String labelStyleClass = getLabelStyleClass();
        
        if(labelStyleClass != null && !labelStyleClass.isEmpty()){
            print(" class=\"");
            print(labelStyleClass);
            print("\"");
        }
        
        String labelStyle = getLabelStyle();
        
        if(labelStyle != null && !labelStyle.isEmpty()){
            print(" style=\"");
            print(labelStyle);
            
            if(!labelStyle.endsWith(";"))
                print(";");
            
            print("\"");
        }
        
        println(">");
    }

    @Override
    protected void renderLabelClose() throws InternalErrorException{
        println("</legend>");
    }

    @Override
    protected void renderOpen() throws InternalErrorException{
        print("<fieldset");
        
        String styleClass = getStyleClass();
        
        if(styleClass != null && !styleClass.isEmpty()){
            print(" class=\"");
            print(styleClass);
            print("\"");
        }
        
        String style = getStyle();
        
        if(style != null && !style.isEmpty()){
            print(" style=\"");
            print(style);
            
            if(!style.endsWith(";"))
                print(";");
            
            print("\"");
        }
        
        super.renderTooltip();
        
        println(">");
        
        boolean showLabel = showLabel();
        String label = getLabel();
        
        if(showLabel && label != null && !label.isEmpty())
            super.renderLabel();
    }

    @Override
    protected void renderClose() throws InternalErrorException{
        println("</fieldset>");
    }
}