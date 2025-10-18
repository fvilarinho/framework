package br.com.concepting.framework.ui.components;

import br.com.concepting.framework.controller.form.constants.ActionFormConstants;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.model.util.ModelUtil;
import br.com.concepting.framework.security.constants.SecurityConstants;
import br.com.concepting.framework.security.controller.SecurityController;
import br.com.concepting.framework.security.model.LoginSessionModel;
import br.com.concepting.framework.security.model.UserModel;
import br.com.concepting.framework.security.resources.SecurityResources;
import br.com.concepting.framework.ui.constants.UIConstants;
import br.com.concepting.framework.util.DateTimeUtil;
import br.com.concepting.framework.util.helpers.DateTime;
import br.com.concepting.framework.util.types.ComponentType;
import br.com.concepting.framework.util.types.MethodType;
import br.com.concepting.framework.util.types.PositionType;

import javax.servlet.jsp.JspException;

/**
 * Class that defines the login session box component.
 *
 * @author fvilarinho
 * @since 3.2.0
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
public class LoginSessionBoxComponent extends BaseActionFormComponent{
    private static final long serialVersionUID = 1710312403838229576L;

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
    protected void buildEvents() throws InternalErrorException{
    }

    @Override
    protected void buildResources() throws InternalErrorException{
    }

    @Override
    protected void buildRestrictions() throws InternalErrorException{
        setRenderWhenAuthenticated(true);
        
        super.buildRestrictions();
    }

    @Override
    protected void initialize() throws InternalErrorException{
        setComponentType(ComponentType.LOGIN_SESSION_BOX);
        
        super.initialize();
    }

    @Override
    protected void renderType() throws InternalErrorException{
    }

    @Override
    protected void renderName() throws InternalErrorException{
    }

    @Override
    protected void renderEnabled() throws InternalErrorException{
    }

    @Override
    protected void renderOpen() throws InternalErrorException{
        print("<div");
        
        renderAttributes();
        
        println(">");
        
        renderLoggedAs();
        
        println("</div>");
    }
    
    /**
     * Renders the attribute 'logged as'.
     *
     * @throws InternalErrorException Occurs when was not possible to render.
     */
    protected void renderLoggedAs() throws InternalErrorException{
        SecurityController securityController = getSecurityController();
        LoginSessionModel loginSession = (securityController != null ? securityController.getLoginSession() : null);
        UserModel user = (loginSession != null ? loginSession.getUser() : null);
        String name = (user != null ? user.getName() : null);
        
        if(name != null && !name.isEmpty()){
            LabelComponent loggedAsComponent = new LabelComponent();
            
            loggedAsComponent.setPageContext(this.pageContext);
            loggedAsComponent.setOutputStream(getOutputStream());
            loggedAsComponent.setResourcesId(UIConstants.DEFAULT_LOGIN_SESSION_BOX_RESOURCES_ID);
            loggedAsComponent.setName(UIConstants.DEFAULT_LOGIN_SESSION_BOX_LOGGED_AS_ID);
            loggedAsComponent.setStyleClass(UIConstants.DEFAULT_LOGIN_SESSION_BOX_LOGGED_AS_STYLE_CLASS);
            loggedAsComponent.setLabelStyleClass(UIConstants.DEFAULT_LOGIN_SESSION_BOX_LOGGED_AS_LABEL_STYLE_CLASS);
            loggedAsComponent.setValue(name);
            loggedAsComponent.setOnClick("showHideLoginSessionBoxContent();");
            
            try{
                loggedAsComponent.doStartTag();
                loggedAsComponent.doEndTag();
            }
            catch(JspException e){
                throw new InternalErrorException(e);
            }
        }
    }
    
    /**
     * Renders the user details.
     *
     * @throws InternalErrorException Occurs when was not possible to render.
     */
    protected void renderUserDetails() throws InternalErrorException{
        SecurityController securityController = getSecurityController();
        LoginSessionModel loginSession = (securityController != null ? securityController.getLoginSession() : null);
        DateTime startDateTime = (loginSession != null ? loginSession.getStartDateTime() : null);
        UserModel user = (loginSession != null ? loginSession.getUser() : null);
        String fullName = (user != null ? user.getFullName() : null);
        byte[] logo = (user != null ? user.getLogo() : null);
        ImageComponent userLogoComponent = new ImageComponent();
        
        userLogoComponent.setPageContext(this.pageContext);
        userLogoComponent.setOutputStream(getOutputStream());
        userLogoComponent.setStyleClass(UIConstants.DEFAULT_LOGIN_SESSION_BOX_USER_LOGO_STYLE_CLASS);
        userLogoComponent.setTooltip(fullName);
        
        if(logo == null)
            userLogoComponent.setValue(UIConstants.DEFAULT_LOGIN_SESSION_BOX_NO_USER_LOGO_URL);
        else
            userLogoComponent.setValue(logo);
        
        try{
            userLogoComponent.doStartTag();
            userLogoComponent.doEndTag();
        }
        catch(JspException e){
            throw new InternalErrorException(e);
        }
        
        println("<br/>");
        
        LabelComponent userFullNameComponent = new LabelComponent();
        
        userFullNameComponent.setPageContext(this.pageContext);
        userFullNameComponent.setOutputStream(getOutputStream());
        userFullNameComponent.setStyleClass(UIConstants.DEFAULT_LOGIN_SESSION_BOX_USER_FULL_NAME_STYLE_CLASS);
        userFullNameComponent.setValue(fullName);
        
        try{
            userFullNameComponent.doStartTag();
            userFullNameComponent.doEndTag();
        }
        catch(JspException e){
            throw new InternalErrorException(e);
        }
        
        println("<br/><br/>");
        
        LabelComponent loggedInComponent = new LabelComponent();
        
        loggedInComponent.setPageContext(this.pageContext);
        loggedInComponent.setOutputStream(getOutputStream());
        loggedInComponent.setResourcesId(UIConstants.DEFAULT_LOGIN_SESSION_BOX_RESOURCES_ID);
        loggedInComponent.setName(UIConstants.DEFAULT_LOGIN_SESSION_BOX_LOGGED_IN_ID);
        loggedInComponent.setLabelPositionType(PositionType.TOP);
        loggedInComponent.setLabelStyleClass(UIConstants.DEFAULT_LOGIN_SESSION_BOX_LOGGED_IN_LABEL_STYLE_CLASS);
        loggedInComponent.setStyleClass(UIConstants.DEFAULT_LOGIN_SESSION_BOX_LOGGED_IN_STYLE_CLASS);
        loggedInComponent.setValue(DateTimeUtil.format(startDateTime, getCurrentLanguage()));
        
        try{
            loggedInComponent.doStartTag();
            loggedInComponent.doEndTag();
        }
        catch(JspException e){
            throw new InternalErrorException(e);
        }
    }
    
    /**
     * Renders the controls.
     *
     * @throws InternalErrorException Occurs when was not possible to render.
     */
    protected void renderControls() throws InternalErrorException{
        SecurityResources securityResources = getSecurityResources();
        
        if(securityResources == null)
            return;
        
        Class<? extends LoginSessionModel> modelClass = securityResources.getLoginSessionClass();
        
        if(modelClass == null)
            return;
        
        String contextPath = getContextPath();
        String actionFormUrl = ModelUtil.getUrlByModel(modelClass);
        
        if(contextPath == null || contextPath.isEmpty() || actionFormUrl == null || actionFormUrl.isEmpty())
            return;
        
        StringBuilder url = new StringBuilder();
        
        url.append(contextPath);
        url.append(actionFormUrl);
        url.append(ActionFormConstants.DEFAULT_ACTION_FILE_EXTENSION);
        
        StringBuilder onClick = new StringBuilder();

        onClick.append("submitRequest('");
        onClick.append(MethodType.POST);
        onClick.append("', '");
        onClick.append(url);
        onClick.append("', '");
        onClick.append(ActionFormConstants.ACTION_ATTRIBUTE_ID);
        onClick.append("=");
        onClick.append(SecurityConstants.DEFAULT_LOAD_CHANGE_PASSWORD_ID);
        onClick.append("&");
        onClick.append(ActionFormConstants.FORWARD_ATTRIBUTE_ID);
        onClick.append("=");
        onClick.append(ActionFormConstants.DEFAULT_FORWARD_ID);
        onClick.append("');");
        
        LinkComponent linkComponent = new LinkComponent();
        
        linkComponent.setPageContext(this.pageContext);
        linkComponent.setOutputStream(getOutputStream());
        linkComponent.setActionFormName(getActionFormName());
        linkComponent.setResourcesId(UIConstants.DEFAULT_LOGIN_SESSION_BOX_RESOURCES_ID);
        linkComponent.setResourcesKey(SecurityConstants.DEFAULT_CHANGE_PASSWORD_ID);
        linkComponent.setOnClick(onClick.toString());
        
        try{
            linkComponent.doStartTag();
            linkComponent.doEndTag();
        }
        catch(JspException e){
            throw new InternalErrorException(e);
        }
        
        println("<br/>");
        
        onClick.delete(0, onClick.length());
        onClick.append("submitRequest('");
        onClick.append(MethodType.POST);
        onClick.append("', '");
        onClick.append(url);
        onClick.append("', '");
        onClick.append(ActionFormConstants.ACTION_ATTRIBUTE_ID);
        onClick.append("=");
        onClick.append(SecurityConstants.DEFAULT_LOGOUT_ID);
        onClick.append("&");
        onClick.append(ActionFormConstants.FORWARD_ATTRIBUTE_ID);
        onClick.append("=");
        onClick.append(ActionFormConstants.DEFAULT_FORWARD_ID);
        onClick.append("');");
        
        linkComponent.setPageContext(this.pageContext);
        linkComponent.setOutputStream(getOutputStream());
        linkComponent.setActionFormName(getActionFormName());
        linkComponent.setResourcesId(UIConstants.DEFAULT_LOGIN_SESSION_BOX_RESOURCES_ID);
        linkComponent.setResourcesKey(SecurityConstants.DEFAULT_LOGOUT_ID);
        linkComponent.setOnClick(onClick.toString());
        
        try{
            linkComponent.doStartTag();
            linkComponent.doEndTag();
        }
        catch(JspException e){
            throw new InternalErrorException(e);
        }
    }

    @Override
    protected void renderBody() throws InternalErrorException{
        print("<div id=\"");
        print(UIConstants.DEFAULT_LOGIN_SESSION_BOX_CONTENT_ID);
        print("\" class=\"");
        print(UIConstants.DEFAULT_LOGIN_SESSION_BOX_CONTENT_STYLE_CLASS);
        println("\">");
        println("<br/>");
        
        renderUserDetails();
        
        println("<br/>");
        
        renderControls();
    }

    @Override
    protected void renderClose() throws InternalErrorException{
        println("</div>");
    }
}