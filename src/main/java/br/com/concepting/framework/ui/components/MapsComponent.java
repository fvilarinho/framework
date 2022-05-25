package br.com.concepting.framework.ui.components;

import br.com.concepting.framework.constants.SystemConstants;
import br.com.concepting.framework.controller.action.types.ActionType;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.ui.constants.UIConstants;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.types.AlignmentType;
import br.com.concepting.framework.util.types.ComponentType;
import br.com.concepting.framework.util.types.ContentType;

import javax.servlet.jsp.JspException;

/**
 * Class that defines the maps component.
 *
 * @author fvilarinho
 * @since 3.5.0
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
public class MapsComponent extends BasePropertyComponent{
    private static final long serialVersionUID = 6479362456008135703L;
    
    private int zoom = UIConstants.DEFAULT_MAPS_NO_ZOOM;
    
    /**
     * Returns the zoom of the map.
     *
     * @return Numeric value that contains the zoom.
     */
    public int getZoom(){
        return this.zoom;
    }
    
    /**
     * Defines the zoom of the map.
     *
     * @param zoom Numeric value that contains the zoom.
     */
    public void setZoom(int zoom){
        this.zoom = zoom;
    }

    @Override
    protected void buildDimensions() throws InternalErrorException{
        String width = getWidth();
        
        if(width == null || width.length() == 0)
            setWidth(UIConstants.DEFAULT_MAPS_WIDTH);

        String height = getHeight();
        
        if(height == null || height.length() == 0)
            setHeight(UIConstants.DEFAULT_MAPS_HEIGHT);

        super.buildDimensions();
    }

    @Override
    protected void buildRestrictions() throws InternalErrorException{
        if(getPropertyInfo() == null)
            setShowLabel(false);
    }

    @Override
    protected void initialize() throws InternalErrorException{
        setComponentType(ComponentType.MAPS);
        
        super.initialize();
    }

    @Override
    protected void renderBody() throws InternalErrorException{
        String name = getName();
        
        if(name == null || name.length() == 0)
            return;
        
        boolean enabled = isEnabled();
        String width = getWidth();
        String height = getHeight();
        String value = getValue();
        boolean hasInvalidDefinition = hasInvalidDefinition();
        
        if(!hasInvalidDefinition){
            print("<table class=\"");
            print(UIConstants.DEFAULT_MAPS_CONTENT_STYLE_CLASS);
            println("\">");
            
            if(enabled){
                println("<tr>");
                print("<td align=\"");
                print(AlignmentType.CENTER);
                println("\">");
                
                TextPropertyComponent inputComponent = new TextPropertyComponent();
                
                inputComponent.setPageContext(this.pageContext);
                inputComponent.setActionFormName(getActionFormName());
                inputComponent.setPropertyInfo(getPropertyInfo());
                inputComponent.setName(name);
                inputComponent.setLabel(getLabel());
                inputComponent.setShowLabel(false);
                inputComponent.setTooltip(getTooltip());
                inputComponent.setOnBlur(getOnBlur());
                inputComponent.setOnBlurAction(getOnBlurAction());
                inputComponent.setOnBlurForward(getOnBlurForward());
                inputComponent.setOnBlurUpdateViews(getOnBlurUpdateViews());
                inputComponent.setOnBlurValidateModel(getOnBlurValidateModel());
                inputComponent.setOnBlurValidateModelProperties(getOnBlurValidateModelProperties());
                inputComponent.setOnFocus(getOnFocus());
                inputComponent.setOnFocusAction(getOnFocusAction());
                inputComponent.setOnFocusForward(getOnFocusForward());
                inputComponent.setOnFocusUpdateViews(getOnFocusUpdateViews());
                inputComponent.setOnFocusValidateModel(getOnFocusValidateModel());
                inputComponent.setOnFocusValidateModelProperties(getOnFocusValidateModelProperties());
                inputComponent.setOnChange(getOnChange());
                
                String onChangeAction = getOnChangeAction();
                
                if(onChangeAction == null || onChangeAction.length() == 0)
                    onChangeAction = ActionType.REFRESH.getMethod();
                
                inputComponent.setOnChangeAction(onChangeAction);
                inputComponent.setOnChangeForward(getOnChangeForward());
                inputComponent.setOnChangeUpdateViews(getOnChangeUpdateViews());
                inputComponent.setOnChangeValidateModel(getOnChangeValidateModel());
                inputComponent.setOnChangeValidateModelProperties(getOnChangeValidateModelProperties());
                inputComponent.setOnKeyPress(getOnKeyPress());
                inputComponent.setOnKeyPressAction(getOnKeyPressAction());
                inputComponent.setOnKeyPressForward(getOnKeyPressForward());
                inputComponent.setOnKeyPressUpdateViews(getOnKeyPressUpdateViews());
                inputComponent.setOnKeyPressValidateModel(getOnKeyPressValidateModel());
                inputComponent.setOnKeyPressValidateModelProperties(getOnKeyPressValidateModelProperties());
                inputComponent.setOnKeyUp(getOnKeyUp());
                inputComponent.setOnKeyUpAction(getOnKeyUpAction());
                inputComponent.setOnKeyUpForward(getOnKeyUpForward());
                inputComponent.setOnKeyUpUpdateViews(getOnKeyUpUpdateViews());
                inputComponent.setOnKeyUpValidateModel(getOnKeyUpValidateModel());
                inputComponent.setOnKeyUpValidateModelProperties(getOnKeyUpValidateModelProperties());
                inputComponent.setOnKeyDown(getOnKeyDown());
                inputComponent.setOnKeyDownAction(getOnKeyDownAction());
                inputComponent.setOnKeyDownForward(getOnKeyDownForward());
                inputComponent.setOnKeyDownUpdateViews(getOnKeyDownUpdateViews());
                inputComponent.setOnKeyDownValidateModel(getOnKeyDownValidateModel());
                inputComponent.setOnKeyDownValidateModelProperties(getOnKeyDownValidateModelProperties());
                inputComponent.setOnClick(getOnClick());
                inputComponent.setOnClickAction(getOnClickAction());
                inputComponent.setOnClickForward(getOnClickForward());
                inputComponent.setOnClickUpdateViews(getOnClickUpdateViews());
                inputComponent.setOnClickValidateModel(getOnClickValidateModel());
                inputComponent.setOnClickValidateModelProperties(getOnClickValidateModelProperties());
                inputComponent.setOnMouseOut(getOnMouseOut());
                inputComponent.setOnMouseOutAction(getOnMouseOutAction());
                inputComponent.setOnMouseOutForward(getOnMouseOutForward());
                inputComponent.setOnMouseOutUpdateViews(getOnMouseOutUpdateViews());
                inputComponent.setOnMouseOutValidateModel(getOnMouseOutValidateModel());
                inputComponent.setOnMouseOutValidateModelProperties(getOnMouseOutValidateModelProperties());
                inputComponent.setOnMouseOver(getOnMouseOver());
                inputComponent.setOnMouseOverAction(getOnMouseOverAction());
                inputComponent.setOnMouseOverForward(getOnMouseOverForward());
                inputComponent.setOnMouseOverUpdateViews(getOnMouseOverUpdateViews());
                inputComponent.setOnMouseOverValidateModel(getOnMouseOverValidateModel());
                inputComponent.setOnMouseOverValidateModelProperties(getOnMouseOverValidateModelProperties());
                inputComponent.setWidth(width);
                inputComponent.setSize(getSize());
                inputComponent.setMaximumLength(getMaximumLength());
                inputComponent.setMinimumValue(getMinimumValue());
                inputComponent.setMaximumValue(getMaximumValue());
                inputComponent.setAlignment(getAlignment());
                inputComponent.setVerticalAlignment(getVerticalAlignment());
                inputComponent.setStyleClass(UIConstants.DEFAULT_MAPS_INPUT_STYLE_CLASS);
                inputComponent.setValue(value);
                inputComponent.setRenderWhenAuthenticated(getRenderWhenAuthenticated());
                inputComponent.setRender(render());
                inputComponent.setEnabled(isEnabled());
                
                try{
                    inputComponent.doStartTag();
                    inputComponent.doEndTag();
                }
                catch(JspException e){
                    throw new InternalErrorException(e);
                }
                
                println("</td>");
                println("</tr>");
            }
            
            println("<tr>");
            println("<td>");
            
            ImageComponent mapsComponent = new ImageComponent();
            
            mapsComponent.setPageContext(this.pageContext);
            mapsComponent.setStyleClass(UIConstants.DEFAULT_MAPS_STYLE_CLASS);
            mapsComponent.setStyle(getStyle());
            
            StringBuilder valueBuffer = new StringBuilder();
            
            valueBuffer.append(UIConstants.DEFAULT_MAPS_URL);
            valueBuffer.append("&");
            valueBuffer.append(AlignmentType.CENTER.toString().toLowerCase());
            valueBuffer.append("=");
            valueBuffer.append(value);
            valueBuffer.append("&");
            valueBuffer.append(UIConstants.MAPS_ZOOM_ATTRIBUTE_ID);
            valueBuffer.append("=");
            
            if(value == null || value.length() == 0){
                if(this.zoom == 0)
                    this.zoom = UIConstants.DEFAULT_MAPS_NO_ZOOM;
            }
            else{
                String actionFormName = getActionFormName();
                
                if(actionFormName != null && actionFormName.length() > 0){
                    this.zoom = getUIController().getMapsZoom(actionFormName, getName(), this.zoom);
                    
                    String[] valueParts = StringUtil.split(value);
                    
                    if(valueParts != null && valueParts.length > 0)
                        this.zoom = valueParts.length * UIConstants.DEFAULT_MAPS_ZOOM_FACTOR;
                    else
                        this.zoom = UIConstants.DEFAULT_MAPS_NO_ZOOM;
                }
                else
                    this.zoom = UIConstants.DEFAULT_MAPS_NO_ZOOM;
            }
            
            valueBuffer.append(this.zoom);
            valueBuffer.append("&format=");
            valueBuffer.append(ContentType.PNG.getExtension().substring(1));
            
            if(width != null && width.length() > 0 && height != null && height.length() > 0){
                valueBuffer.append("&size=");
                valueBuffer.append(width);
                valueBuffer.append("x");
                valueBuffer.append(height);
            }
            
            if(value != null && value.length() > 0){
                valueBuffer.append("&markers=color:red|label:|");
                valueBuffer.append(value);
            }
            
            valueBuffer.append("&");
            valueBuffer.append(SystemConstants.LANGUAGE_ATTRIBUTE_ID);
            valueBuffer.append("=");
            valueBuffer.append(getCurrentLanguage());
            
            mapsComponent.setValue(valueBuffer.toString());
            
            try{
                mapsComponent.doStartTag();
                mapsComponent.doEndTag();
            }
            catch(JspException e){
                throw new InternalErrorException(e);
            }
            
            println("</td>");
            println("</tr>");
            println("</table>");
        }
        else
            super.renderBody();
    }

    @Override
    protected void clearAttributes() throws InternalErrorException{
        super.clearAttributes();
        
        setZoom(UIConstants.DEFAULT_MAPS_NO_ZOOM);
    }
}