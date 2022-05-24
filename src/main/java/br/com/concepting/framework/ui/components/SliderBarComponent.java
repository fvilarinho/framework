package br.com.concepting.framework.ui.components;

import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.ui.constants.UIConstants;
import br.com.concepting.framework.util.NumberUtil;
import br.com.concepting.framework.util.types.ComponentType;

import javax.servlet.jsp.JspException;
import java.text.DecimalFormatSymbols;

/**
 * Class that defines the slider bar component.
 *
 * @author fvilarinho
 * @since 3.0.0
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
public class SliderBarComponent extends TextPropertyComponent{
    private static final long serialVersionUID = 1573821017694529722L;

    @Override
    protected void buildEvents() throws InternalErrorException{
        String name = getName();
        
        if(name != null && name.length() > 0){
            StringBuilder onBlurContent = new StringBuilder();
            
            onBlurContent.append("setSliderBarPosition('");
            onBlurContent.append(name);
            onBlurContent.append("');");
            
            String currentOnBlurContent = getOnBlur();
            
            if(currentOnBlurContent != null && currentOnBlurContent.length() > 0){
                onBlurContent.append(" ");
                onBlurContent.append(currentOnBlurContent);
                
                if(!currentOnBlurContent.endsWith(";"))
                    onBlurContent.append(";");
            }
            
            setOnBlur(onBlurContent.toString());
        }
        
        super.buildEvents();
    }

    @Override
    protected void initialize() throws InternalErrorException{
        setMinimumValue(0);
        
        String width = getWidth();
        
        if(width != null && width.length() > 0)
            setWidth(null);
        
        setComponentType(ComponentType.SLIDER_BAR);
        
        super.initialize();
        
        if(width == null || width.length() == 0)
            width = String.valueOf(UIConstants.DEFAULT_SLIDER_BAR_WIDTH);
        
        setWidth(width);
    }
    
    /**
     * Renders the controls of the component.
     *
     * @throws InternalErrorException Occurs when was not possible to render.
     */
    protected void renderControls() throws InternalErrorException{
        String name = getName();
        
        if(name == null || name.length() == 0)
            return;
        
        boolean enabled = isEnabled();
        
        print("<div id=\"");
        print(name);
        print(".");
        print(UIConstants.DEFAULT_SLIDER_BAR_CONTROL_ID);
        print("\" class=\"");
        print(UIConstants.DEFAULT_SLIDER_BAR_CONTROL_STYLE_CLASS);
        print("\"");
        
        if(enabled){
            print(" onMouseDown=\"dragSliderBarControl('");
            print(name);
            print("', event);\" onMouseUp=\"dropSliderBarControl();\" onMouseOut=\"dropSliderBarControl();\" onMouseMove=\"slideIt(event);\"");
        }
        
        println("></div>");
        
        print("<div id=\"");
        print(name);
        print(".");
        print(UIConstants.DEFAULT_SLIDER_BAR_ID);
        print("\" class=\"");
        print(UIConstants.DEFAULT_SLIDER_BAR_STYLE_CLASS);
        print("\"");
        
        String width = getWidth();
        
        if(width != null && width.length() > 0){
            print(" style=\"width: ");
            print(width);
            
            if(!width.endsWith(";"))
                print(";");
            
            print("\"");
        }
        
        println("></div>");
    }

    @Override
    protected void renderOpen() throws InternalErrorException{
        super.renderOpen();
        
        boolean hasInvalidPropertyDefinition = hasInvalidPropertyDefinition();
        
        if(!hasInvalidPropertyDefinition){
            print("<table class=\"");
            print(UIConstants.DEFAULT_CONTENT_PANEL_STYLE_CLASS);
            println("\">");
            println("<tr>");
            println("<td>");
            
            renderControls();
            
            println("</td>");
            
            println("<td width=\"5\"></td>");
            
            println("<td>");
        }
    }

    @Override
    protected void renderClose() throws InternalErrorException{
        boolean hasInvalidPropertyDefinition = hasInvalidPropertyDefinition();
        
        if(!hasInvalidPropertyDefinition){
            println("</td>");
            println("</tr>");
            println("</table>");
        }
        
        super.renderClose();
        
        if(!hasInvalidPropertyDefinition){
            String name = getName();
            
            if(name != null && name.length() > 0){
                DecimalFormatSymbols symbols = NumberUtil.getFormatSymbols(getCurrentLanguage());
                String width = getWidth();
                Number maximumValue = getMaximumValue();
                boolean useGroupSeparator = useAdditionalFormatting();
                int precision = getPrecision();
                Object value = getValue();
                StringBuilder content = new StringBuilder();
                
                content.append("addLoadEvent(initializeSliderBar('");
                content.append(name);
                content.append("', ");
                content.append((width != null && width.length() > 0 ? width : Constants.DEFAULT_NULL_ID));
                content.append(", ");
                content.append((maximumValue != null ? maximumValue.toString() : Constants.DEFAULT_NULL_ID));
                content.append(", ");
                content.append(useGroupSeparator);
                content.append(", '");
                content.append(symbols.getGroupingSeparator());
                content.append("', ");
                content.append(precision);
                content.append(", '");
                content.append(symbols.getDecimalSeparator());
                content.append("', ");
                content.append((value != null ? value : Constants.DEFAULT_NULL_ID));
                content.append("));");
                
                ScriptComponent scriptComponent = new ScriptComponent();
                
                scriptComponent.setPageContext(this.pageContext);
                scriptComponent.setOutputStream(getOutputStream());
                scriptComponent.setContent(content.toString());
                
                try{
                    scriptComponent.doStartTag();
                    scriptComponent.doEndTag();
                }
                catch(JspException e){
                    throw new InternalErrorException(e);
                }
            }
        }
    }
}