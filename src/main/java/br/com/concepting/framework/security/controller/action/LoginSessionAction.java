package br.com.concepting.framework.security.controller.action;

import br.com.concepting.framework.controller.SystemController;
import br.com.concepting.framework.controller.action.BaseAction;
import br.com.concepting.framework.controller.form.ActionFormController;
import br.com.concepting.framework.controller.form.BaseActionForm;
import br.com.concepting.framework.security.constants.SecurityConstants;
import br.com.concepting.framework.security.controller.SecurityController;
import br.com.concepting.framework.security.exceptions.ExpiredPasswordException;
import br.com.concepting.framework.security.exceptions.LoginSessionExpiredException;
import br.com.concepting.framework.security.exceptions.PasswordWillExpireException;
import br.com.concepting.framework.security.model.LoginParameterModel;
import br.com.concepting.framework.security.model.LoginSessionModel;
import br.com.concepting.framework.security.model.UserModel;
import br.com.concepting.framework.security.service.interfaces.LoginSessionService;
import br.com.concepting.framework.security.util.SecurityUtil;

/**
 * Class that defines the basic implementation of login session form actions.
 *
 * @param <L> Class that defines the login session data model.
 * @param <U> Class that defines the user data model.
 * @param <LP> Class that defines the login parameter data model.
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
public abstract class LoginSessionAction<L extends LoginSessionModel, U extends UserModel, LP extends LoginParameterModel> extends BaseAction<L>{
    @Override
    public void init() throws Throwable{
        super.init();
        
        SystemController systemController = getSystemController();
        SecurityController securityController = getSecurityController();
        BaseActionForm<L> actionForm = getActionForm();
        
        if(systemController == null || securityController == null || actionForm == null)
            return;
        
        systemController.removeCookie(SecurityConstants.LOGIN_SESSION_ATTRIBUTE_ID);
        
        L loginSession = securityController.getLoginSession();
        
        loadRememberedUserAndPassword(loginSession);
        
        actionForm.setModel(loginSession);
        
        securityController.setLoginSession(loginSession);
    }
    
    /**
     * Does the login.
     *
     * @throws Throwable Occurs when was not possible to execute the operation.
     */
    @SuppressWarnings("unchecked")
    public void logIn() throws Throwable{
        SystemController systemController = getSystemController();
        SecurityController securityController = getSecurityController();
        
        if(systemController == null || securityController == null)
            return;
        
        BaseActionForm<L> actionForm = getActionForm();
        
        if(actionForm == null)
            return;
        
        L loginSession = actionForm.getModel();
        
        if(loginSession == null)
            return;
        
        U user = loginSession.getUser();
        
        if(user == null)
            return;
        
        LoginSessionService<L, U, LP> service = getService();
        LP loginParameter;

        try{
            loginSession = service.logIn(user);
            user = loginSession.getUser();
            loginParameter = user.getLoginParameter();
            
            if(loginParameter != null && loginParameter.isPasswordWillExpire())
                throw new PasswordWillExpireException(loginParameter.getDaysUntilExpire(), loginParameter.getHoursUntilExpire(), loginParameter.getMinutesUntilExpire(), loginParameter.getSecondsUntilExpire());

            securityController.setLoginSession(loginSession);
        }
        finally {
            systemController.addCookie(SecurityConstants.LOGIN_SESSION_ATTRIBUTE_ID, loginSession.getId(), true);

            actionForm.setModel((L) loginSession.clone());
        }

        if(loginParameter != null && loginParameter.changePassword()){
            loadChangePassword();

            throw new ExpiredPasswordException();
        }
    }
    
    /**
     * Loads the user data stored in a cookie.
     *
     * @param loginSession Instance that contains the properties of the login
     * session.
     * @throws Throwable Occurs when was not possible to execute the operation.
     */
    private void loadRememberedUserAndPassword(L loginSession) throws Throwable{
        SecurityController securityController = getSecurityController();
        
        if(securityController == null)
            return;
        
        loginSession = SecurityUtil.getLoginSession(loginSession);

        U user = loginSession.getUser();
        String rememberedUserName = securityController.getRememberedUserName();
        String rememberedPassword = securityController.getRememberedPassword();
        
        user.setName(rememberedUserName);
        user.setPassword(rememberedPassword);
        
        loginSession.setRememberUserAndPassword(rememberedUserName != null && !rememberedUserName.isEmpty());
    }
    
    /**
     * Loads the change password form.
     *
     * @throws Throwable Occurs when was not possible to execute the operation.
     */
    public void loadChangePassword() throws Throwable{
        BaseActionForm<L> actionForm = getActionForm();
        L loginSession = (actionForm != null ? actionForm.getModel() : null);
        U user = (loginSession != null ? loginSession.getUser() : null);
        
        if(user == null || user.getId() == null || user.getId() == 0){
            init();
            
            throw new LoginSessionExpiredException();
        }
        
        user.setPassword(null);
        user.setNewPassword(null);
        user.setConfirmPassword(null);
        
        LP loginParameter = user.getLoginParameter();
        
        if(loginParameter != null){
            loginParameter.setChangePassword(true);
            
            user.setLoginParameter(loginParameter);
            
            loginSession.setUser(user);
        }
        
        actionForm.setModel(loginSession);
    }
    
    /**
     * Validates the MFA Token.
     *
     * @throws Throwable Occurs when was not possible to execute the operation.
     */
    public void validateMfaToken() throws Throwable{
        ActionFormController actionFormController = getActionFormController();
        
        if(actionFormController == null)
            return;
        
        BaseActionForm<L> actionForm = getActionForm();
        
        if(actionForm == null)
            return;
        
        L loginSession = actionForm.getModel();
        
        if(loginSession == null)
            return;
        
        U user = loginSession.getUser();
        
        if(user == null)
            return;
        
        LoginSessionService<L, U, LP> service = getService();
        
        service.validateMfaToken(user);
        
        actionFormController.addSuccessMessage();
    }
    
    /**
     * Confirms the password change.
     *
     * @throws Throwable Occurs when was not possible to execute the operation.
     */
    public void changePassword() throws Throwable{
        ActionFormController actionFormController = getActionFormController();
        
        if(actionFormController == null)
            return;
        
        BaseActionForm<L> actionForm = getActionForm();
        
        if(actionForm == null)
            return;
        
        L loginSession = actionForm.getModel();
        
        if(loginSession == null)
            return;
        
        U user = loginSession.getUser();
        
        if(user == null)
            return;
        
        LoginSessionService<L, U, LP> service = getService();
        
        user = service.changePassword(user);
        
        loginSession.setUser(user);
        
        actionForm.setModel(loginSession);
        
        getSecurityController().setLoginSession(loginSession);
        
        actionFormController.addSuccessMessage();
    }
    
    /**
     * Cancels the password change
     *
     * @throws Throwable Occurs when was not possible to execute the operation.
     */
    public void cancelChangePassword() throws Throwable{
        BaseActionForm<L> actionForm = getActionForm();
        L loginSession = (actionForm != null ? actionForm.getModel() : null);
        U user = (loginSession != null ? loginSession.getUser() : null);
        
        if(user == null || user.getId() == null || user.getId() == 0){
            init();
            
            throw new LoginSessionExpiredException();
        }
        
        LP loginParameter = user.getLoginParameter();
        
        if(loginParameter != null){
            loginParameter.setChangePassword(false);
            
            user.setLoginParameter(loginParameter);
            
            loginSession.setUser(user);
        }
        
        actionForm.setModel(loginSession);
    }
    
    /**
     * Stores the user data in a cookie.
     *
     * @throws Throwable Occurs when was not possible to execute the operation.
     */
    public void rememberUserAndPassword() throws Throwable{
        SecurityController securityController = getSecurityController();
        
        if(securityController == null)
            return;
        
        BaseActionForm<L> actionForm = getActionForm();
        L loginSession = (actionForm != null ? actionForm.getModel() : null);
        
        if(loginSession != null && loginSession.rememberUserAndPassword()){
            U user = loginSession.getUser();
            
            if(user != null && !user.getName().isEmpty())
                getSecurityController().rememberUserAndPassword(user);
            else{
                loginSession.setRememberUserAndPassword(false);
                
                securityController.forgetUserAndPassword();
            }
        }
        else
            securityController.forgetUserAndPassword();
    }
    
    /**
     * Loads the forgot password form.
     *
     * @throws Throwable Occurs when was not possible to execute the operation.
     */
    public void loadForgotPassword() throws Throwable{
        BaseActionForm<L> actionForm = getActionForm();
        L loginSession = (actionForm != null ? actionForm.getModel() : null);
        U user = (loginSession != null ? loginSession.getUser() : null);
        
        if(user != null){
            user.setEmail(null);
            
            loginSession.setUser(user);
            
            actionForm.setModel(loginSession);
        }
    }
    
    /**
     * Sends the user forgotten password to the user.
     *
     * @throws Throwable Occurs when was not possible to execute the operation.
     */
    public void sendForgottenPassword() throws Throwable{
        ActionFormController actionFormController = getActionFormController();
        
        if(actionFormController == null)
            return;
        
        BaseActionForm<L> actionForm = getActionForm();
        
        if(actionForm == null)
            return;
        
        L loginSession = actionForm.getModel();
        
        if(loginSession == null)
            return;
        
        U user = loginSession.getUser();
        
        if(user == null)
            return;
        
        LoginSessionService<L, U, LP> service = getService();
        
        service.sendForgottenPassword(user);
        
        actionFormController.addSuccessMessage();
    }
    
    /**
     * Does the log-out.
     *
     * @throws Throwable Occurs when was not possible to execute the operation.
     */
    public void logOut() throws Throwable{
        SystemController systemController = getSystemController();
        
        if(systemController == null)
            return;
        
        SecurityController securityController = getSecurityController();
        
        if(securityController == null)
            return;
        
        LoginSessionService<L, U, LP> service = getService();
        
        service.logOut();
        
        L loginSession = securityController.getLoginSession();
        
        if(loginSession == null)
            return;
        
        loginSession.setId(null);
        loginSession.setStartDateTime(null);
        loginSession.setActive(null);
        loginSession.setUser(null);
        
        securityController.setLoginSession(loginSession);
        
        systemController.removeCookie(SecurityConstants.LOGIN_SESSION_ATTRIBUTE_ID);
        
        init();
    }
}