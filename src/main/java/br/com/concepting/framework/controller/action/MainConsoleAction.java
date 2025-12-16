package br.com.concepting.framework.controller.action;

import br.com.concepting.framework.common.controller.form.MainConsoleActionForm;
import br.com.concepting.framework.common.model.MainConsoleModel;
import br.com.concepting.framework.constants.SystemConstants;
import br.com.concepting.framework.controller.SystemController;
import br.com.concepting.framework.security.controller.SecurityController;
import br.com.concepting.framework.security.model.LoginParameterModel;
import br.com.concepting.framework.security.model.LoginSessionModel;
import br.com.concepting.framework.security.model.UserModel;

/**
 * Class that defines the basic implementation of the main console.
 *
 * @param <M> Class that defines the main console data model.
 * @author fvilarinho
 * @version 3.2.0
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
public class MainConsoleAction<M extends MainConsoleModel> extends BaseAction<M>{
    /**
     * Change the current language.
     *
     * @param <F> Class that defines the form.
     * @throws Throwable Occurs when was not possible to execute the action.
     */
    public <F extends MainConsoleActionForm<M>> void changeCurrentLanguage() throws Throwable{
        SystemController systemController = this.getSystemController();
        SecurityController securityController = this.getSecurityController();
        LoginSessionModel loginSession = (securityController != null ? securityController.getLoginSession() : null);
        UserModel user = (loginSession != null ? loginSession.getUser() : null);
        LoginParameterModel loginParameter = (user != null ? user.getLoginParameter() : null);
        F actionForm = getActionForm();
        M model = (actionForm != null ? actionForm.getModel() : null);
        
        if(loginParameter != null && model != null && model.getCurrentLanguage() != null && !model.getCurrentLanguage().isEmpty()){
            loginParameter.setLanguage(model.getCurrentLanguage());
            
            systemController.addCookie(SystemConstants.CURRENT_LANGUAGE_ATTRIBUTE_ID, loginParameter.getLanguage(), true);
        }
    }
    
    /**
     * Change the current skin.
     *
     * @param <F> Class that defines the form.
     * @throws Throwable Occurs when was not possible to execute the action.
     */
    public <F extends MainConsoleActionForm<M>> void changeCurrentSkin() throws Throwable{
        SystemController systemController = this.getSystemController();
        SecurityController securityController = this.getSecurityController();
        LoginSessionModel loginSession = (securityController != null ? securityController.getLoginSession() : null);
        UserModel user = (loginSession != null ? loginSession.getUser() : null);
        LoginParameterModel loginParameter = (user != null ? user.getLoginParameter() : null);
        F actionForm = getActionForm();
        M model = (actionForm != null ? actionForm.getModel() : null);
        
        if(loginParameter != null && model != null && model.getCurrentSkin() != null && !model.getCurrentSkin().isEmpty()){
            loginParameter.setSkin(model.getCurrentSkin());
            
            systemController.addCookie(SystemConstants.CURRENT_SKIN_ATTRIBUTE_ID, loginParameter.getSkin(), true);
        }
    }
}