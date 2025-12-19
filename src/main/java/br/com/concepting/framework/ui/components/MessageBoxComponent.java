package br.com.concepting.framework.ui.components;

import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.constants.SystemConstants;
import br.com.concepting.framework.controller.SystemController;
import br.com.concepting.framework.controller.form.ActionFormController;
import br.com.concepting.framework.controller.form.helpers.ActionFormMessage;
import br.com.concepting.framework.controller.form.types.ActionFormMessageType;
import br.com.concepting.framework.controller.form.util.ActionFormMessageUtil;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.model.SystemSessionModel;
import br.com.concepting.framework.processors.ExpressionProcessorUtil;
import br.com.concepting.framework.resources.PropertiesResources;
import br.com.concepting.framework.resources.exceptions.InvalidResourcesException;
import br.com.concepting.framework.security.controller.SecurityController;
import br.com.concepting.framework.security.exceptions.PermissionDeniedException;
import br.com.concepting.framework.security.exceptions.UserNotAuthorizedException;
import br.com.concepting.framework.security.model.LoginSessionModel;
import br.com.concepting.framework.ui.components.types.VisibilityType;
import br.com.concepting.framework.ui.constants.UIConstants;
import br.com.concepting.framework.util.ExceptionUtil;
import br.com.concepting.framework.util.PropertyUtil;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.types.AlignmentType;
import br.com.concepting.framework.util.types.ComponentType;
import br.com.concepting.framework.util.types.PositionType;
import jakarta.servlet.jsp.ErrorData;
import jakarta.servlet.jsp.JspException;
import org.apache.http.HttpStatus;

import java.io.Serial;
import java.util.Collection;
import java.util.Locale;

/**
 * Class that defines the message box component.
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
public class MessageBoxComponent extends DialogBoxComponent{
    @Serial
    private static final long serialVersionUID = 6254384099423430512L;
    
    private String type = null;
    private String message = null;
    private Collection<String> messages = null;
    private Throwable exception = null;
    
    /**
     * Defines the component exception.
     *
     * @param exception Instance that contains the exception.
     */
    public void setException(Throwable exception){
        this.exception = exception;
    }

    /**
     * Returns the component exception.
     *
     * @return Instance that contains the exception.
     */
    public Throwable getException() {
        return this.exception;
    }
    
    /**
     * Returns the type of the messages.
     *
     * @return String that contains the type of the messages.
     */
    public String getType(){
        return this.type;
    }
    
    /**
     * Defines the type of the messages.
     *
     * @param type String that contains the type of the messages.
     */
    public void setType(String type){
        this.type = type;
    }
    
    /**
     * Returns the type of the messages.
     *
     * @return Instance that contains the type of the messages.
     */
    protected ActionFormMessageType getMessageType(){
        if(this.type != null && !this.type.isEmpty()){
            try{
                return ActionFormMessageType.valueOf(this.type.toUpperCase());
            }
            catch(IllegalArgumentException ignored){
            }
        }
        
        return null;
    }
    
    /**
     * Defines the type of the messages.
     *
     * @param messageType Instance that contains the type of the messages.
     */
    protected void setMessageType(ActionFormMessageType messageType){
        if(messageType != null)
            this.type = messageType.toString();
    }
    
    /**
     * Defines the current list of messages.
     *
     * @param messages List that contains the messages.
     */
    public void setMessages(Collection<String> messages){
        this.messages = messages;
    }

    /**
     * Returns the current list of messages.
     *
     * @return List that contains the messages.
     */
    public Collection<String> getMessages() {
        return this.messages;
    }
    
    /**
     * Returns the message.
     *
     * @return String that contains the message.
     */
    public String getMessage(){
        return this.message;
    }
    
    /**
     * Defines the message.
     *
     * @param message String that contains the message.
     */
    public void setMessage(String message){
        this.message = message;
    }

    @Override
    protected void buildResources() throws InternalErrorException{
        super.buildResources();
        
        SystemController systemController = getSystemController();
        
        if(systemController == null)
            return;
        
        ActionFormMessageType messageType = getMessageType();
        Throwable exception = getException();
        
        if(messageType == null){
            if(exception == null){
                exception = systemController.getCurrentException();
                
                if(exception == null)
                    exception = this.pageContext.getException();

                if(exception != null)
                    exception = ExceptionUtil.getCause(exception);

                setException(exception);

                systemController.setCurrentException(null);
            }
            
            if(exception == null){
                ErrorData errorData = this.pageContext.getErrorData();
                
                if(errorData != null && errorData.getStatusCode() > 0){
                    if(errorData.getStatusCode() == HttpStatus.SC_UNAUTHORIZED)
                        exception = new UserNotAuthorizedException();
                    else if(errorData.getStatusCode() == HttpStatus.SC_FORBIDDEN)
                        exception = new PermissionDeniedException();
                    else if(errorData.getStatusCode() == HttpStatus.SC_NOT_FOUND || errorData.getStatusCode() == HttpStatus.SC_NOT_ACCEPTABLE || errorData.getStatusCode() == HttpStatus.SC_METHOD_NOT_ALLOWED)
                        exception = new InvalidResourcesException(errorData.getRequestURI());
                    else if(errorData.getStatusCode() == HttpStatus.SC_INTERNAL_SERVER_ERROR)
                        exception = new InternalErrorException(errorData.getThrowable());

                    setException(exception);
                }
            }
            
            if(exception != null){
                if(ExceptionUtil.isExpectedException(exception)){
                    if(ExceptionUtil.isExpectedWarningException(exception))
                        messageType = ActionFormMessageType.WARNING;
                    else
                        messageType = ActionFormMessageType.ERROR;
                }
                else
                    messageType = ActionFormMessageType.ERROR;

                setType(messageType.toString());
                
                if(ExceptionUtil.isInvalidResourceException(exception) || ExceptionUtil.isExpectedException(exception)){
                    StringBuilder resourcesKey = new StringBuilder();
                    
                    resourcesKey.append(messageType.toString().toLowerCase());
                    resourcesKey.append(".");
                    resourcesKey.append(ExceptionUtil.getId(exception));
                    
                    setResourcesKey(resourcesKey.toString());
                }
                
                if(exception.getMessage() != null && !exception.getMessage().isEmpty())
                    setMessage(exception.getMessage());
            }
        }
        
        buildTitle();
        buildMessages();
        
        if(ExceptionUtil.isInvalidResourceException(exception) || ExceptionUtil.isExpectedException(exception))
            setException(null);
    }

    @Override
    protected void buildTitle() throws InternalErrorException{
        String title = getTitle();
        
        if(title == null || title.isEmpty()){
            PropertiesResources resources = getResources();
            PropertiesResources defaultResources = getDefaultResources();
            ActionFormMessageType messageType = getMessageType();
            String name = getName();
            StringBuilder propertyId = new StringBuilder();
            
            if(messageType != null)
                propertyId.append(messageType.toString().toLowerCase());
            else
                propertyId.append(name);
            
            propertyId.append(".");
            propertyId.append(Constants.LABEL_ATTRIBUTE_ID);
            
            title = (resources != null ? resources.getProperty(propertyId.toString(), false) : null);
            
            if(title == null)
                title = (defaultResources != null ? defaultResources.getProperty(propertyId.toString(), false) : null);
            
            setTitle(title);
        }
    }
    
    /**
     * Builds the messages of the dialog box.
     *
     * @throws InternalErrorException Occurs when was not possible to execute
     * the operation.
     */
    protected void buildMessages() throws InternalErrorException{
        SystemController systemController = getSystemController();
        
        if(systemController == null)
            return;
        
        SecurityController securityController = systemController.getSecurityController();
        
        if(securityController == null)
            return;
        
        LoginSessionModel loginSession = securityController.getLoginSession();
        SystemSessionModel systemSession = (loginSession != null ? loginSession.getSystemSession() : null);
        
        if(systemSession == null)
            return;

        Throwable exception = getException();
        
        if(exception != null)
            if(!ExceptionUtil.isInvalidResourceException(exception) && !ExceptionUtil.isExpectedException(exception))
                setMessage(ExceptionUtil.getTrace(exception));
        
        String domain = systemSession.getId();
        Locale currentLanguage = getCurrentLanguage();
        String resourcesKey = getResourcesKey();
        String message = getMessage();
        
        if((resourcesKey == null || resourcesKey.isEmpty()) && (message == null || message.isEmpty())){
            ActionFormController actionFormController = getActionFormController();
            Collection<ActionFormMessage> actionFormMessages = null;
            ActionFormMessageType messageType = getMessageType();
            
            if(actionFormController != null && messageType != null)
                actionFormMessages = actionFormController.getMessages(messageType);
            
            if(actionFormMessages != null && !actionFormMessages.isEmpty()){
                PropertiesResources resources = getResources();
                PropertiesResources defaultResources = getDefaultResources();
                StringBuilder propertyId = null;

                for(ActionFormMessage actionFormMessage: actionFormMessages){
                    if(propertyId == null)
                        propertyId = new StringBuilder();
                    else
                        propertyId.delete(0, propertyId.length());
                    
                    propertyId.append(actionFormMessage.getType().toString().toLowerCase());
                    propertyId.append(".");
                    propertyId.append(actionFormMessage.getKey());

                    message = (resources != null ? resources.getProperty(propertyId.toString(), false) : null);
                    
                    if(message == null)
                        message = (defaultResources != null ? defaultResources.getProperty(propertyId.toString()) : null);
                    
                    if(message != null && !message.isEmpty()){
                        message = ExpressionProcessorUtil.fillVariablesInString(domain, message, currentLanguage);
                        message = ActionFormMessageUtil.fillAttributesInString(actionFormMessage, message, currentLanguage);

                        Collection<String> messages = getMessages();

                        if(messages == null) {
                            messages = PropertyUtil.instantiate(Constants.DEFAULT_LIST_CLASS);

                            setMessages(messages);
                        }

                        if(messages != null && !messages.contains(message))
                            messages.add(message);
                    }
                }

                actionFormController.clearMessages(messageType);

                message = null;
            }
        }
        else if(resourcesKey != null && !resourcesKey.isEmpty() && (message == null || message.isEmpty())){
            PropertiesResources resources = getResources();
            PropertiesResources defaultResources = getDefaultResources();

            message = (resources != null ? resources.getProperty(resourcesKey, false) : null);

            if(message == null)
                message = (defaultResources != null ? defaultResources.getProperty(resourcesKey) : null);

            if(message != null && !message.isEmpty()){
                message = ExpressionProcessorUtil.fillVariablesInString(domain, message, currentLanguage);
                message = PropertyUtil.fillPropertiesInString(exception, message, currentLanguage);

                setMessage(message);
            }
        }
        
        if(message != null && !message.isEmpty()){
            Collection<String> messages = getMessages();

            if(messages == null) {
                messages = PropertyUtil.instantiate(Constants.DEFAULT_LIST_CLASS);

                setMessages(messages);
            }

            if(messages != null && !messages.contains(message))
                messages.add(message);
        }
    }

    @Override
    protected void buildPermissions() throws InternalErrorException{
        setRender(this.exception != null || (this.message != null && !this.message.isEmpty()) || (this.messages != null && !this.messages.isEmpty()));
        
        super.buildPermissions();
    }

    @Override
    protected void initialize() throws InternalErrorException{
        setComponentType(ComponentType.MESSAGE_BOX);
        
        super.initialize();
    }

    @Override
    protected void renderBody() throws InternalErrorException{
        String name = getName();
        
        if(name != null && !name.isEmpty()){
            print("<table class=\"");
            print(UIConstants.DEFAULT_CONTENT_PANEL_STYLE_CLASS);
            println("\">");
            println("<tr>");
            
            if(this.type != null && !this.type.isEmpty()){
                println("<td width=\"10\"></td>");
                
                print("<td align=\"");
                print(AlignmentType.RIGHT);
                print("\" valign=\"");
                print(AlignmentType.TOP);
                print("\" width=\"1\">");
                print("<div class=\"");
                print(this.type.toLowerCase());
                print(StringUtil.capitalize(UIConstants.DEFAULT_ICON_ID));
                println("\"></div>");
                println("</td>");
            }
            
            println("<td width=\"10\"></td>");
            
            println("<td>");
            print("<table class=\"");
            print(UIConstants.DEFAULT_CONTENT_PANEL_STYLE_CLASS);
            println("\">");
            println("<tr>");
            
            if(this.exception != null){
                println("<td>");
                
                String actionFormName = getActionFormName();
                LabelComponent errorIdComponent = new LabelComponent();
                
                errorIdComponent.setPageContext(this.pageContext);
                errorIdComponent.setOutputStream(getOutputStream());
                errorIdComponent.setActionFormName(actionFormName);
                errorIdComponent.setResourcesId(UIConstants.DEFAULT_MESSAGE_BOX_RESOURCES_ID);
                errorIdComponent.setResourcesKey(SystemConstants.ERROR_ID_ATTRIBUTE_ID);
                errorIdComponent.setLabelPositionType(PositionType.TOP);
                errorIdComponent.setValue(this.exception.getClass().getName());
                
                try{
                    errorIdComponent.doStartTag();
                    errorIdComponent.doEndTag();
                }
                catch(JspException e){
                    throw new InternalErrorException(e);
                }
                
                println("</td>");
                println("</tr>");
                
                println("<tr>");
                println("<td>");
                println("<br/>");
                
                StringBuilder content = new StringBuilder();
                
                content.append("showHideErrorTrace('");
                content.append(name);
                content.append("');");
                
                LinkComponent linkComponent = new LinkComponent();
                
                linkComponent.setPageContext(this.pageContext);
                linkComponent.setOutputStream(getOutputStream());
                linkComponent.setActionFormName(actionFormName);
                linkComponent.setResourcesId(UIConstants.DEFAULT_MESSAGE_BOX_RESOURCES_ID);
                linkComponent.setResourcesKey(SystemConstants.ERROR_TRACE_ATTRIBUTE_ID);
                linkComponent.setOnClick(content.toString());
                
                try{
                    linkComponent.doStartTag();
                    linkComponent.doEndTag();
                }
                catch(JspException e){
                    throw new InternalErrorException(e);
                }
                
                println("</td>");
                println("</tr>");
                
                println("<tr>");
                println("<td>");
                
                StringBuilder nameBuffer = new StringBuilder();
                
                nameBuffer.append(name);
                nameBuffer.append(".");
                nameBuffer.append(SystemConstants.ERROR_TRACE_ATTRIBUTE_ID);
                
                TextAreaPropertyComponent errorTraceComponent = new TextAreaPropertyComponent();
                
                errorTraceComponent.setPageContext(this.pageContext);
                errorTraceComponent.setOutputStream(getOutputStream());
                errorTraceComponent.setActionFormName(actionFormName);
                errorTraceComponent.setName(nameBuffer.toString());
                errorTraceComponent.setValue(this.message);
                errorTraceComponent.setShowLabel(false);
                errorTraceComponent.setResourcesId(UIConstants.DEFAULT_MESSAGE_BOX_RESOURCES_ID);
                errorTraceComponent.setResourcesKey(SystemConstants.ERROR_TRACE_ATTRIBUTE_ID);
                errorTraceComponent.setStyleClass(UIConstants.DEFAULT_ERROR_TRACE_STYLE_CLASS);
                
                StringBuilder errorTraceStyle = new StringBuilder();
                
                errorTraceStyle.append("display: ");
                errorTraceStyle.append(VisibilityType.NONE);
                errorTraceStyle.append(";");
                
                errorTraceComponent.setStyle(errorTraceStyle.toString());
                errorTraceComponent.setReadOnly(true);
                
                try{
                    errorTraceComponent.doStartTag();
                    errorTraceComponent.doEndTag();
                }
                catch(JspException e){
                    throw new InternalErrorException(e);
                }
                
                println("</td>");
            }
            else{
                print("<td class=\"");
                print(UIConstants.DEFAULT_DIALOG_BOX_TEXT_STYLE_CLASS);
                println("\">");
                
                if(this.messages != null && !this.messages.isEmpty()){
                    for(String message: this.messages){
                        if(this.messages.size() > 1)
                            print("&#8226;&nbsp;");
                        
                        print(message);
                        println("<br/>");
                    }
                }
                
                println("</td>");
            }
            
            println("<td width=\"10\"></td>");
            
            println("</tr>");
            println("</table>");
            
            println("</td>");
            println("</tr>");
            println("</table>");
        }
    }

    @Override
    protected void clearAttributes() throws InternalErrorException{
        super.clearAttributes();
        
        setType(null);
        setMessage(null);
        setMessages(null);
        setException(null);
    }
}