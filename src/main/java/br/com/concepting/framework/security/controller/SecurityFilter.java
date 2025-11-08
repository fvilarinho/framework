package br.com.concepting.framework.security.controller;

import br.com.concepting.framework.constants.SystemConstants;
import br.com.concepting.framework.controller.SystemController;
import br.com.concepting.framework.controller.form.util.ActionFormUtil;
import br.com.concepting.framework.controller.helpers.RequestParameterInfo;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.model.*;
import br.com.concepting.framework.model.exceptions.ItemAlreadyExistsException;
import br.com.concepting.framework.model.exceptions.ItemNotFoundException;
import br.com.concepting.framework.processors.ExpressionProcessorUtil;
import br.com.concepting.framework.resources.SystemResources;
import br.com.concepting.framework.resources.SystemResourcesLoader;
import br.com.concepting.framework.resources.exceptions.InvalidResourcesException;
import br.com.concepting.framework.security.constants.SecurityConstants;
import br.com.concepting.framework.security.exceptions.InvalidMfaTokenException;
import br.com.concepting.framework.security.exceptions.LoginSessionExpiredException;
import br.com.concepting.framework.security.exceptions.PermissionDeniedException;
import br.com.concepting.framework.security.exceptions.UserNotAuthorizedException;
import br.com.concepting.framework.security.model.LoginParameterModel;
import br.com.concepting.framework.security.model.LoginSessionModel;
import br.com.concepting.framework.security.model.UserModel;
import br.com.concepting.framework.security.resources.SecurityResources;
import br.com.concepting.framework.security.resources.SecurityResourcesLoader;
import br.com.concepting.framework.security.service.interfaces.LoginSessionService;
import br.com.concepting.framework.service.interfaces.IService;
import br.com.concepting.framework.service.util.ServiceUtil;
import br.com.concepting.framework.util.DateTimeUtil;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.helpers.DateTime;
import br.com.concepting.framework.util.types.DateFieldType;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class responsible to apply security filters in the system requests.
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
@WebFilter(filterName = "securityFilter", urlPatterns = {"*.ui", "*.jsp", "/webServices/*"})
public class SecurityFilter implements Filter{
    private SystemResources systemResources = null;
    private SecurityResources securityResources = null;
    private SystemController systemController = null;
    private SecurityController securityController = null;
    
    /**
     * Returns the instance that contains the system resources.
     *
     * @return Instance that contains the system resources.
     */
    protected SystemResources getSystemResources(){
        return this.systemResources;
    }
    
    /**
     * Returns the instance that contains the security resources.
     *
     * @return Instance that contains the security resources.
     */
    protected SecurityResources getSecurityResources(){
        return this.securityResources;
    }
    
    /**
     * Returns the instance of the system controller.
     *
     * @return Instance that contains the system controller.
     */
    protected SystemController getSystemController(){
        return this.systemController;
    }
    
    /**
     * Returns the instance of the security controller.
     *
     * @return Instance that contains the security controller.
     */
    protected SecurityController getSecurityController(){
        return this.securityController;
    }
    
    /**
     * Returns the service implementation of a specific data model.
     *
     * @param <S> Class that defines the service implementation.
     * @param modelClass Class that defines the data model.
     * @return Instance that contains the service implementation of the data model.
     * @throws InternalErrorException Occurs when was not possible to
     * instantiate the service implementation.
     */
    protected <S extends IService<? extends BaseModel>> S getService(Class<? extends BaseModel> modelClass) throws InternalErrorException{
        if(modelClass != null){
            LoginSessionModel loginSession = this.securityController.getLoginSession();
            
            return ServiceUtil.getByModelClass(modelClass, loginSession);
        }
        
        return null;
    }
    
    /**
     * Process the security filter.
     *
     * @throws UserNotAuthorizedException Occurs when the user is not authorized.
     * @throws PermissionDeniedException Occurs when the user doesn't have permission.
     * @throws InternalErrorException Occurs when was not possible to process
     * the filter.
     */
    protected void process() throws UserNotAuthorizedException, PermissionDeniedException, InternalErrorException{
        if(this.securityController.isLoginSessionExpired()){
            this.systemController.removeCookie(SecurityConstants.LOGIN_SESSION_ATTRIBUTE_ID);
            
            throw new LoginSessionExpiredException();
        }
        
        LoginSessionModel loginSession = this.securityController.getLoginSession();
        SystemModuleModel systemModule = loginSession.getSystemModule();
        Collection<? extends UrlModel> exclusionUrls = systemModule.getExclusionUrls();
        String uri = this.systemController.getURI();
        
        if(uri != null && !uri.isEmpty()){
            StringBuilder requestUriBuffer = new StringBuilder();

            requestUriBuffer.append(uri);

            Map<String, RequestParameterInfo> requestParameters = this.systemController.getParameters();

            if(requestParameters != null && !requestParameters.isEmpty()){
                requestUriBuffer.append("?");

                int cont = 0;

                for(Entry<String, RequestParameterInfo> entry: requestParameters.entrySet()){
                    String requestParameterName = entry.getKey();
                    RequestParameterInfo requestParameterInfo = entry.getValue();

                    if(requestParameterInfo.getContent() == null){
                        String[] requestParameterValues = requestParameterInfo.getValues();

                        if(requestParameterValues != null){
                            for (String requestParameterValue : requestParameterValues) {
                                if (cont > 0)
                                    requestUriBuffer.append("&");

                                requestUriBuffer.append(requestParameterName);
                                requestUriBuffer.append("=");
                                requestUriBuffer.append(requestParameterValue);

                                cont++;
                            }
                        }
                    }
                }
            }

            uri = requestUriBuffer.toString();
        }
        
        uri = StringUtil.replaceAll(uri, this.systemController.getContextPath(), "");
        
        UserModel user = loginSession.getUser();
        boolean excludeUrl = false;
        
        if(exclusionUrls != null && !exclusionUrls.isEmpty() && uri != null && !uri.isEmpty()){
            for(UrlModel exclusionUrl: exclusionUrls){
                String urlPattern = StringUtil.toRegex(exclusionUrl.getPath());
                Pattern regex = Pattern.compile(urlPattern);
                Matcher matcher = regex.matcher(uri);
                
                if(matcher.matches()){
                    excludeUrl = true;
                    
                    break;
                }
            }
            
            if(!excludeUrl)
                if(!this.securityController.isLoginSessionAuthenticated())
                    throw new PermissionDeniedException();
        }
        
        if(this.securityController.isLoginSessionAuthenticated())
            if(!user.isSuperUser() && !user.hasPermission(uri))
                throw new PermissionDeniedException();
        
        if(!excludeUrl){
            LoginParameterModel loginParameter = user.getLoginParameter();
            
            if(loginParameter != null && loginParameter.hasMfa() != null && loginParameter.hasMfa())
                if(loginParameter.isMfaTokenValidated() == null || !loginParameter.isMfaTokenValidated())
                    throw new InvalidMfaTokenException();
        }
        
        SystemSessionModel systemSession = loginSession.getSystemSession();
        String domain = systemSession.getId();
        
        ExpressionProcessorUtil.setVariable(domain, SecurityConstants.LOGIN_SESSION_ATTRIBUTE_ID, loginSession);
    }

    /**
     * Initializes the security filter.
     *
     * @throws UserNotAuthorizedException Occurs when the user is not authorized.
     * @throws PermissionDeniedException Occurs when the user doesn't have permission.
     * @throws InternalErrorException Occurs when was not possible to process
     * the filter.
     */
    @SuppressWarnings("unchecked")
    protected <L extends LoginSessionModel, U extends UserModel, LP extends LoginParameterModel> void initialize() throws UserNotAuthorizedException, PermissionDeniedException, InternalErrorException{
        this.securityController = this.systemController.getSecurityController();
        
        L loginSession = (this.securityController != null ? this.securityController.getLoginSession() : null);
        
        if(loginSession != null){
            boolean isWebServicesRequest = this.systemController.isWebServicesRequest();
            
            if(isWebServicesRequest){
                if(loginSession.getId() != null && !loginSession.getId().isEmpty()){
                    Class<L> loginSessionClass = (Class<L>)loginSession.getClass();
                    LoginSessionService<L, U, LP> loginSessionService = null;
                    
                    try{
                        loginSessionService = getService(loginSessionClass);
                    }
                    catch(InternalErrorException ignored){
                    }
                    
                    if(loginSessionService != null){
                        try{
                            loginSession = loginSessionService.find(loginSession);
                            
                            if(loginSession == null || !loginSession.isActive())
                                throw new ItemNotFoundException();
                        }
                        catch(ItemNotFoundException e1){
                            this.systemController.removeCookie(SecurityConstants.LOGIN_SESSION_ATTRIBUTE_ID);
                            
                            throw new PermissionDeniedException();
                        }
                    }
                    
                    DateTime now = new DateTime();
                    DateTime startDateTime = loginSession.getStartDateTime();
                    int ttl = DateTimeUtil.diff(now, startDateTime, DateFieldType.MINUTES);
                    
                    if(ttl >= this.securityResources.getLoginSessionTimeout()){
                        if(loginSessionService != null)
                            loginSessionService.logOut();
                        
                        this.systemController.removeCookie(SecurityConstants.LOGIN_SESSION_ATTRIBUTE_ID);
                        
                        throw new LoginSessionExpiredException();
                    }
                }
            }
            
            SystemModuleModel systemModule = loginSession.getSystemModule();
            Class<SystemModuleModel> modelClass = (Class<SystemModuleModel>) systemModule.getClass();
            IService<SystemModuleModel> systemModuleService = null;
            
            try{
                systemModuleService = getService(modelClass);
            }
            catch(InternalErrorException ignored){
            }
            
            if(systemModuleService != null) {
                try {
                    systemModule = systemModuleService.find(systemModule);

                    if (systemModule.isActive() == null || !systemModule.isActive())
                        throw new PermissionDeniedException();
                }
                catch (ItemNotFoundException e) {
                    throw new PermissionDeniedException();
                }

                systemModule = systemModuleService.loadReference(systemModule, SystemConstants.EXCLUSION_URLS_ATTRIBUTE_ID);
            }
            
            if(!isWebServicesRequest){
                if(systemModuleService != null)
                    systemModule = systemModuleService.loadReference(systemModule, SystemConstants.FORMS_ATTRIBUTE_ID);
                
                FormModel form = systemModule.getForm(ActionFormUtil.getActionFormIdByModel(this.systemResources.getMainConsoleClass()));
                
                if(form != null){
                    Class<FormModel> formClass = (Class<FormModel>) form.getClass();
                    IService<FormModel> formService = getService(formClass);
                    
                    form = formService.loadReference(form, SystemConstants.OBJECTS_ATTRIBUTE_ID);
                    
                    systemModule.setForm(form);
                }
            }
            
            loginSession.setSystemModule(systemModule);
            
            SystemSessionModel systemSession = loginSession.getSystemSession();
            
            if(systemSession != null && systemSession.getId() != null && !systemSession.getId().isEmpty()){
                if(systemSession.getStartDateTime() == null){
                    systemSession.setStartDateTime(new DateTime());
                    
                    Class<SystemSessionModel> systemSessionClass = (Class<SystemSessionModel>) systemSession.getClass();
                    IService<SystemSessionModel> systemSessionService = null;
                    
                    try{
                        systemSessionService = getService(systemSessionClass);
                    }
                    catch(InternalErrorException ignored){
                    }
                    
                    if(systemSessionService != null){
                        try{
                            systemSession = systemSessionService.save(systemSession);
                        }
                        catch(ItemAlreadyExistsException ignored){
                        }
                    }
                    
                    loginSession.setSystemSession(systemSession);
                }
            }
            else
                throw new PermissionDeniedException();
            
            this.securityController.setLoginSession(loginSession);
        }
        else
            throw new PermissionDeniedException();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException{
        ((HttpServletResponse) response).setHeader("Access-Control-Allow-Origin", "*");
        ((HttpServletResponse) response).setHeader("Access-Control-Allow-Methods", "HEAD, DELETE, PUT, POST, GET, OPTIONS");
        ((HttpServletResponse) response).setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, ".concat(SecurityConstants.LOGIN_SESSION_ATTRIBUTE_ID));
        
        if(((HttpServletRequest) request).getMethod().equals("OPTIONS"))
            filterChain.doFilter(request, response);
        else{
            this.systemController = new SystemController((HttpServletRequest) request, (HttpServletResponse) response);
            this.systemController.setCurrentException(null);
            
            try{
                initialize();
                process();
                
                filterChain.doFilter(request, response);
            }
            catch(UserNotAuthorizedException | PermissionDeniedException | InternalErrorException e){
                this.systemController.forward(e);
            }
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException{
        try{
            SystemResourcesLoader systemResourcesLoader = new SystemResourcesLoader();
            
            this.systemResources = systemResourcesLoader.getDefault();
            
            SecurityResourcesLoader securityResourcesLoader = new SecurityResourcesLoader();
            
            this.securityResources = securityResourcesLoader.getDefault();
        }
        catch(InvalidResourcesException e){
            this.systemController.forward(e);
        }
    }

    @Override
    public void destroy(){
    }
}