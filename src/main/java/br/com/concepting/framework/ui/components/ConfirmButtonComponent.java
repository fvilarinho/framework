package br.com.concepting.framework.ui.components;

import br.com.concepting.framework.controller.form.types.ActionFormMessageType;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.resources.PropertiesResources;
import br.com.concepting.framework.ui.components.types.EventType;
import br.com.concepting.framework.ui.constants.UIConstants;

import jakarta.servlet.jsp.JspException;

import java.io.Serial;

/**
 * Class that defines the confirmation button component.
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
public class ConfirmButtonComponent extends ButtonComponent{
    @Serial
    private static final long serialVersionUID = -1643950893623970881L;
    
    private String message = null;
    private String messageKey = null;
    private boolean showDialogBox = false;
    
    /**
     * Indicates if the confirmation box should be shown.
     *
     * @return True/False.
     */
    public boolean showDialogBox(){
        return this.showDialogBox;
    }
    
    /**
     * Indicates if the confirmation box should be shown.
     *
     * @return True/False.
     */
    public boolean getShowDialogBox(){
        return showDialogBox();
    }
    
    /**
     * Defines if the confirmation box should be shown.
     *
     * @param showDialogBox True/False.
     */
    public void setShowDialogBox(boolean showDialogBox){
        this.showDialogBox = showDialogBox;
    }
    
    /**
     * Returns the confirmation message.
     *
     * @return String that contains the message.
     */
    public String getMessage(){
        return this.message;
    }
    
    /**
     * Defines the confirmation message.
     *
     * @param message String that contains the message.
     */
    public void setMessage(String message){
        this.message = message;
    }
    
    /**
     * Returns the confirmation message key stored in the resource file.
     *
     * @return String that contains the key.
     */
    public String getMessageKey(){
        return this.messageKey;
    }
    
    /**
     * Defines the confirmation message key stored in the resource file.
     *
     * @param messageKey String that contains the key.
     */
    public void setMessageKey(String messageKey){
        this.messageKey = messageKey;
    }
    
    /**
     * Builds the confirmation message.
     *
     * @throws InternalErrorException Occurs when was not possible to execute
     * the operation.
     */
    protected void buildMessage() throws InternalErrorException{
        String name = getName();
        
        if(name != null && !name.isEmpty() && this.showDialogBox && this.messageKey != null && !this.messageKey.isEmpty() && this.message == null){
            PropertiesResources resources = getResources();
            PropertiesResources mainConsoleResources = getMainConsoleResources();
            PropertiesResources defaultResources = getDefaultResources();
            StringBuilder propertyId = new StringBuilder();
            
            propertyId.append(name);
            propertyId.append(".");
            propertyId.append(this.messageKey);
            
            String messageValue = (resources != null ? resources.getProperty(propertyId.toString(), false) : null);
            
            if(messageValue == null)
                messageValue = (mainConsoleResources != null ? mainConsoleResources.getProperty(propertyId.toString(), false) : null);
            
            if(messageValue == null)
                messageValue = (defaultResources != null ? defaultResources.getProperty(propertyId.toString(), false) : null);
            
            if(messageValue == null){
                propertyId.delete(0, propertyId.length());
                
                propertyId.append(getResourcesKey());
                propertyId.append(".");
                propertyId.append(this.messageKey);
                
                String resourcesKeyValue = (resources != null ? resources.getProperty(propertyId.toString(), false) : null);
                
                if(resourcesKeyValue == null)
                    resourcesKeyValue = (mainConsoleResources != null ? mainConsoleResources.getProperty(propertyId.toString(), false) : null);
                
                if(resourcesKeyValue == null)
                    resourcesKeyValue = (defaultResources != null ? defaultResources.getProperty(propertyId.toString()) : null);
                
                this.message = resourcesKeyValue;
            }
            else
                this.message = messageValue;
        }
    }

    @Override
    protected void buildResources() throws InternalErrorException{
        String resourcesKey = getResourcesKey();
        
        if(resourcesKey == null || resourcesKey.isEmpty()){
            resourcesKey = UIConstants.DEFAULT_CONFIRM_BUTTON_ID;
            
            setResourcesKey(resourcesKey);
        }
        
        if(this.messageKey == null || this.messageKey.isEmpty())
            this.messageKey = UIConstants.DEFAULT_CONFIRM_MESSAGE_ID;
        
        super.buildResources();
        
        buildMessage();
    }

    @Override
    protected void buildEvents() throws InternalErrorException{
        String name = getName();
        
        if(name != null && !name.isEmpty() && this.message != null && !this.message.isEmpty()){
            String currentOnClick = getOnClick();
            StringBuilder onClick = new StringBuilder();
            
            if(currentOnClick != null && !currentOnClick.isEmpty()){
                onClick.append(currentOnClick);
                
                if(!currentOnClick.endsWith(";"))
                    onClick.append(";");
                
                onClick.append(" ");
            }
            
            onClick.append("showDialogBox('");
            onClick.append(name);
            onClick.append("', true);");
            
            setOnClick(onClick.toString());
            
            buildEvent(EventType.ON_BLUR);
            buildEvent(EventType.ON_FOCUS);
            buildEvent(EventType.ON_MOUSE_OVER);
            buildEvent(EventType.ON_MOUSE_OUT);
        }
        else
            super.buildEvents();
    }

    @Override
    protected void buildStyleClass() throws InternalErrorException{
        String styleClass = getStyleClass();
        
        if(styleClass == null || styleClass.isEmpty()){
            styleClass = UIConstants.DEFAULT_CONFIRM_BUTTON_STYLE_CLASS;
            
            setStyleClass(styleClass);
        }
        
        super.buildStyleClass();
    }

    @Override
    protected void renderOpen() throws InternalErrorException{
        String actionFormName = getActionFormName();
        String name = getName();
        boolean enabled = isEnabled();
        boolean render = render();
        
        if(actionFormName != null && !actionFormName.isEmpty() && name != null && !name.isEmpty() && enabled && render && this.showDialogBox){
            ConfirmDialogBoxComponent confirmDialogBoxComponent = new ConfirmDialogBoxComponent();
            
            confirmDialogBoxComponent.setPageContext(this.pageContext);
            confirmDialogBoxComponent.setOutputStream(getOutputStream());
            confirmDialogBoxComponent.setActionFormName(actionFormName);
            confirmDialogBoxComponent.setMessageType(ActionFormMessageType.WARNING);
            confirmDialogBoxComponent.setResourcesId(getResourcesId());
            confirmDialogBoxComponent.setName(name);
            confirmDialogBoxComponent.setMessage(this.message);
            confirmDialogBoxComponent.setOnConfirmAction(getAction());
            confirmDialogBoxComponent.setOnConfirmForward(getForward());
            confirmDialogBoxComponent.setOnConfirmUpdateViews(getUpdateViews());
            confirmDialogBoxComponent.setOnConfirmValidateModel(validateModel());
            confirmDialogBoxComponent.setOnConfirmValidateModelProperties(getValidateModelProperties());
            
            try{
                confirmDialogBoxComponent.doStartTag();
                confirmDialogBoxComponent.doEndTag();
            }
            catch(JspException e){
                throw new InternalErrorException(e);
            }
        }
        
        super.renderOpen();
    }

    @Override
    protected void clearAttributes() throws InternalErrorException{
        super.clearAttributes();
        
        setMessage(null);
        setMessageKey(null);
        setShowDialogBox(false);
    }
}