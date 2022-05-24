package br.com.concepting.framework.ui.components;

import br.com.concepting.framework.controller.SystemController;
import br.com.concepting.framework.controller.action.types.ActionType;
import br.com.concepting.framework.controller.form.constants.ActionFormConstants;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.SystemModuleModel;
import br.com.concepting.framework.model.util.ModelUtil;
import br.com.concepting.framework.resources.SystemResources;
import br.com.concepting.framework.security.constants.SecurityConstants;
import br.com.concepting.framework.security.controller.SecurityController;
import br.com.concepting.framework.security.model.LoginParameterModel;
import br.com.concepting.framework.security.model.LoginSessionModel;
import br.com.concepting.framework.security.model.UserModel;
import br.com.concepting.framework.security.resources.SecurityResources;
import br.com.concepting.framework.ui.constants.UIConstants;

import java.util.Objects;

/**
 * Class that defines the component that renders the system.
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
public class SystemComponent extends BaseComponent{
    private static final long serialVersionUID = 7420316295230472764L;

    @Override
    protected void buildName() throws InternalErrorException{
    }

    @Override
    protected void buildEvents() throws InternalErrorException{
    }

    @Override
    protected void buildStyle() throws InternalErrorException{
    }

    @Override
    protected void renderOpen() throws InternalErrorException{
    }

    @Override
    protected void renderClose() throws InternalErrorException{
    }

    @Override
    protected void renderBody() throws InternalErrorException{
        SystemResources systemResources = getSystemResources();
        SecurityResources securityResources = getSecurityResources();
        SystemController systemController = getSystemController();
        SecurityController securityController = getSecurityController();
        LoginSessionModel loginSession = (securityController != null ? securityController.getLoginSession() : null);
        SystemModuleModel systemModule = (loginSession != null ? loginSession.getSystemModule() : null);
        
        if(systemModule != null && systemModule.getId() != null && systemModule.getId() > 0 && systemModule.getActive() != null && systemModule.getActive() && systemResources != null && securityResources != null && systemController != null && securityController != null){
            UserModel user = (loginSession != null ? loginSession.getUser() : null);
            LoginParameterModel loginParameter = (user != null ? user.getLoginParameter() : null);
            String action = systemController.getParameterValue(ActionFormConstants.ACTION_ATTRIBUTE_ID);
            Class<? extends BaseModel> modelClass;
            String actionMethod;
            StringBuilder url = new StringBuilder();
            
            if(securityController.isLoginSessionAuthenticated()){
                if(loginParameter != null){
                    if(loginParameter.hasMfa() != null && loginParameter.hasMfa() && (loginParameter.isMfaTokenValidated() == null || !loginParameter.isMfaTokenValidated())){
                        modelClass = securityResources.getLoginSessionClass();
                        actionMethod = ActionType.REFRESH.getMethod();
                    }
                    else{
                        if(loginParameter.changePassword() == null || !loginParameter.changePassword()){
                            modelClass = systemResources.getMainConsoleClass();
                            actionMethod = ActionType.INIT.getMethod();
                        }
                        else{
                            modelClass = securityResources.getLoginSessionClass();
                            actionMethod = ActionType.REFRESH.getMethod();
                        }
                    }
                }
                else{
                    modelClass = securityResources.getLoginSessionClass();
                    actionMethod = ActionType.REFRESH.getMethod();
                }
            }
            else{
                modelClass = securityResources.getLoginSessionClass();
                
                if(action != null && action.equals(SecurityConstants.DEFAULT_SEND_FORGOTTEN_PASSWORD_ID))
                    actionMethod = ActionType.REFRESH.getMethod();
                else
                    actionMethod = ActionType.INIT.getMethod();
            }
            
            if(modelClass != null){
                String actionFormUrl = ModelUtil.getUrlByModel(modelClass);
                
                url.append(actionFormUrl);
                url.append(ActionFormConstants.DEFAULT_ACTION_FILE_EXTENSION);
                url.append("?");
                url.append(ActionFormConstants.ACTION_ATTRIBUTE_ID);
                url.append("=");
                url.append(actionMethod);
                url.append("&");
                url.append(ActionFormConstants.FORWARD_ATTRIBUTE_ID);
                url.append("=");
                url.append(ActionFormConstants.DEFAULT_FORWARD_ID);

                systemController.forward(url.toString());
            }
        }
        else
            systemController.forward(UIConstants.DEFAULT_ERROR_URL);
    }
}