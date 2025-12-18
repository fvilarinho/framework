package br.com.concepting.framework.security.controller;

import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.constants.SystemConstants;
import br.com.concepting.framework.controller.SystemController;
import br.com.concepting.framework.controller.types.ScopeType;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.model.SystemModuleModel;
import br.com.concepting.framework.model.SystemSessionModel;
import br.com.concepting.framework.resources.SystemResources;
import br.com.concepting.framework.resources.SystemResourcesLoader;
import br.com.concepting.framework.security.constants.SecurityConstants;
import br.com.concepting.framework.security.model.LoginParameterModel;
import br.com.concepting.framework.security.model.LoginSessionModel;
import br.com.concepting.framework.security.model.UserModel;
import br.com.concepting.framework.security.util.SecurityUtil;
import br.com.concepting.framework.util.LanguageUtil;
import br.com.concepting.framework.util.StringUtil;

import jakarta.servlet.http.Cookie;

/**
 * Class that defines the security controller.
 *
 * @author fvilarinho
 * @since 3.0.0
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
public class SecurityController{
    private final SystemController systemController;
    
    /**
     * Constructor - Initialize the controller.
     *
     * @param systemController Instance that contains the system controller.
     */
    public SecurityController(SystemController systemController){
        super();
        
        this.systemController = systemController;
    }
    
    /**
     * Indicates if the user of the login session is authenticated.
     *
     * @return True/False.
     */
    public boolean isLoginSessionAuthenticated(){
        try{
            LoginSessionModel loginSession = getLoginSession();
            
            return (loginSession != null && loginSession.getId() != null && !loginSession.getId().isEmpty() && loginSession.isActive() != null && loginSession.isActive());
        }
        catch(InternalErrorException e){
            return false;
        }
    }
    
    /**
     * Indicates if the login session has expired.
     *
     * @return True/False.
     */
    public boolean isLoginSessionExpired(){
        if(!isLoginSessionAuthenticated()){
            Cookie cookie = this.systemController.getCookie(SecurityConstants.LOGIN_SESSION_ATTRIBUTE_ID);
            
            return (cookie != null && cookie.getValue() != null && !cookie.getValue().isEmpty());
        }
        
        return false;
    }
    
    /**
     * Returns the instance that contains the login session data model.
     *
     * @param <L> Class that defines the data model.
     * @return Instance that contains the login session data model.
     * @throws InternalErrorException Occurs when was not possible to get the
     * login session.
     */
    @SuppressWarnings("unchecked")
    public <L extends LoginSessionModel> L getLoginSession() throws InternalErrorException{
        if(this.systemController == null)
            return null;
        
        boolean isWebServicesRequest = this.systemController.isWebServicesRequest();
        LoginSessionModel loginSession = this.systemController.getAttribute(SecurityConstants.LOGIN_SESSION_ATTRIBUTE_ID,  (isWebServicesRequest ? ScopeType.REQUEST : ScopeType.SESSION));
        
        loginSession = SecurityUtil.getLoginSession(loginSession);
        
        setLoginSession(loginSession);
        
        String loginSessionId = this.systemController.getHeader(SecurityConstants.LOGIN_SESSION_ATTRIBUTE_ID);
        
        if(loginSessionId != null && !loginSessionId.isEmpty() && isWebServicesRequest)
            loginSession.setId(loginSessionId);
        else{
            SystemSessionModel systemSession = loginSession.getSystemSession();
            
            if(systemSession != null){
                String currentSystemSessionId = systemSession.getId();
                
                if(currentSystemSessionId == null || currentSystemSessionId.isEmpty()){
                    if(isWebServicesRequest)
                        systemSession.setId(SecurityUtil.generateToken());
                    else
                        systemSession.setId(this.systemController.getSessionId());
                    
                    SystemModuleModel systemModule = loginSession.getSystemModule();
                    
                    if(systemModule != null)
                        systemModule.setUrl(this.systemController.getContextPath());
                }
                
                String ip = this.systemController.getIp();
                
                if(ip != null && !ip.isEmpty()){
                    String currentIp = systemSession.getIp();
                    
                    if(currentIp == null || !currentIp.equals(ip)){
                        systemSession.setIp(ip);
                        systemSession.setHostName(this.systemController.getHostName());
                    }
                }
            }
            
            UserModel user = loginSession.getUser();
            LoginParameterModel loginParameter = user.getLoginParameter();
            
            if(loginParameter != null){
                if(loginParameter.getLanguage() == null || loginParameter.getLanguage().isEmpty()){
                    Cookie languageCookie = this.systemController.getCookie(SystemConstants.CURRENT_LANGUAGE_ATTRIBUTE_ID);
                    
                    if(languageCookie == null){
                        String language = loginParameter.getLanguage();
                        
                        if(language == null || language.isEmpty()){
                            String acceptLanguage = this.systemController.getAcceptLanguage();
                            
                            if(acceptLanguage != null && !acceptLanguage.isEmpty()){
                                int pos = acceptLanguage.indexOf(Constants.DEFAULT_DELIMITER);
                                
                                if(pos >= 0){
                                    acceptLanguage = acceptLanguage.substring(0, pos);
                                    
                                    loginParameter.setLanguage(acceptLanguage);
                                }
                                else
                                    loginParameter.setLanguage(LanguageUtil.getDefaultLanguage().toString());
                            }
                            else
                                loginParameter.setLanguage(LanguageUtil.getDefaultLanguage().toString());
                        }
                        
                        this.systemController.addCookie(SystemConstants.CURRENT_LANGUAGE_ATTRIBUTE_ID, loginParameter.getLanguage());
                    }
                    else
                        loginParameter.setLanguage(languageCookie.getValue());
                }
                
                if(loginParameter.getSkin() == null || loginParameter.getSkin().isEmpty()){
                    Cookie skinCookie = this.systemController.getCookie(SystemConstants.CURRENT_SKIN_ATTRIBUTE_ID);
                    
                    if(skinCookie == null){
                        String skin = loginParameter.getSkin();
                        
                        if(skin == null || skin.isEmpty()){
                            SystemResourcesLoader systemResourcesLoader = new SystemResourcesLoader();
                            SystemResources systemResources = systemResourcesLoader.getDefault();
                            
                            loginParameter.setSkin(systemResources.getDefaultSkin());
                        }
                        
                        this.systemController.addCookie(SystemConstants.CURRENT_SKIN_ATTRIBUTE_ID, loginParameter.getSkin());
                    }
                    else
                        loginParameter.setSkin(skinCookie.getValue());
                }
            }
        }
        
        return (L) loginSession;
    }
    
    /**
     * Defines the instance that contains the login session data model.
     *
     * @param loginSession Instance that contains the login session data model.
     */
    public void setLoginSession(LoginSessionModel loginSession){
        boolean isWebServicesRequest = (this.systemController != null && this.systemController.isWebServicesRequest());

        if(this.systemController != null)
            this.systemController.setAttribute(SecurityConstants.LOGIN_SESSION_ATTRIBUTE_ID, loginSession, (isWebServicesRequest ? ScopeType.REQUEST : ScopeType.SESSION));
    }
    
    /**
     * Returns the name of the user that is stored in the cookie to be remembered
     * later.
     *
     * @return String that contains the name of the user.
     */
    public String getRememberedUserName(){
        Cookie cookie = this.systemController.getCookie(SecurityConstants.REMEMBER_USER_AND_PASSWORD_ATTRIBUTE_ID);
        
        if(cookie != null && cookie.getValue() != null){
            String[] values = StringUtil.split(cookie.getValue(), "|");
            
            if(values != null && values.length > 1)
                return values[0];
        }
        
        return null;
    }
    
    /**
     * Returns the password of the user that is stored in the cookie to be
     * remembered later.
     *
     * @return String that contains the password of the user.
     */
    public String getRememberedPassword(){
        Cookie cookie = this.systemController.getCookie(SecurityConstants.REMEMBER_USER_AND_PASSWORD_ATTRIBUTE_ID);
        
        if(cookie != null && cookie.getValue() != null){
            String[] values = StringUtil.split(cookie.getValue(), "|");
            
            if(values != null && values.length > 1)
                return values[1];
        }
        
        return null;
    }
    
    /**
     * Stores the username and password in a cookie to be remembered later.
     *
     * @param user Instance that contains the user data model.
     */
    public void rememberUserAndPassword(UserModel user){
        if(user != null && user.getName() != null && !user.getName().isEmpty()){
            String userName = user.getName();
            String password = user.getPassword();
            String value = userName.concat("|").concat(password);
            
            this.systemController.addCookie(SecurityConstants.REMEMBER_USER_AND_PASSWORD_ATTRIBUTE_ID, value, true);
        }
    }
    
    /**
     * Clears the remembered username and password.
     */
    public void forgetUserAndPassword(){
        this.systemController.removeCookie(SecurityConstants.REMEMBER_USER_AND_PASSWORD_ATTRIBUTE_ID);
    }
}