package br.com.innovativethinking.framework.security.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.HttpMethod;

import br.com.innovativethinking.framework.constants.SystemConstants;
import br.com.innovativethinking.framework.controller.SystemController;
import br.com.innovativethinking.framework.controller.form.util.ActionFormUtil;
import br.com.innovativethinking.framework.controller.helpers.RequestParameterInfo;
import br.com.innovativethinking.framework.exceptions.InternalErrorException;
import br.com.innovativethinking.framework.model.BaseModel;
import br.com.innovativethinking.framework.model.FormModel;
import br.com.innovativethinking.framework.model.SystemModuleModel;
import br.com.innovativethinking.framework.model.SystemSessionModel;
import br.com.innovativethinking.framework.model.UrlModel;
import br.com.innovativethinking.framework.model.exceptions.ItemAlreadyExistsException;
import br.com.innovativethinking.framework.model.exceptions.ItemNotFoundException;
import br.com.innovativethinking.framework.processors.ExpressionProcessorUtil;
import br.com.innovativethinking.framework.resources.SystemResources;
import br.com.innovativethinking.framework.resources.SystemResourcesLoader;
import br.com.innovativethinking.framework.resources.exceptions.InvalidResourcesException;
import br.com.innovativethinking.framework.security.constants.SecurityConstants;
import br.com.innovativethinking.framework.security.exceptions.InvalidMfaTokenException;
import br.com.innovativethinking.framework.security.exceptions.LoginSessionExpiredException;
import br.com.innovativethinking.framework.security.exceptions.PermissionDeniedException;
import br.com.innovativethinking.framework.security.exceptions.UserNotAuthorizedException;
import br.com.innovativethinking.framework.security.model.LoginParameterModel;
import br.com.innovativethinking.framework.security.model.LoginSessionModel;
import br.com.innovativethinking.framework.security.model.UserModel;
import br.com.innovativethinking.framework.security.resources.SecurityResources;
import br.com.innovativethinking.framework.security.resources.SecurityResourcesLoader;
import br.com.innovativethinking.framework.security.service.interfaces.LoginSessionService;
import br.com.innovativethinking.framework.service.interfaces.IService;
import br.com.innovativethinking.framework.service.util.ServiceUtil;
import br.com.innovativethinking.framework.util.DateTimeUtil;
import br.com.innovativethinking.framework.util.StringUtil;
import br.com.innovativethinking.framework.util.helpers.DateTime;
import br.com.innovativethinking.framework.util.types.DateFieldType;

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
@WebFilter(filterName = "securityFilter", urlPatterns = {"*.ui", "*.jsp", "/webServices/*"})
public class SecurityFilter implements Filter{
	private SystemResources    systemResources    = null;
	private SecurityResources  securityResources  = null;
	private SystemController   systemController   = null;
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
	 * @param <M> Class that defines a data model.
	 * @param <S> Class that defines the service implementation.
	 * @param modelClass Class that defines the data model.
	 * @return Instance that contains the service implementation of the data model.
	 * @throws InternalErrorException Occurs when was not possible to
	 * instantiate the service implementation.
	 */
	protected <M extends BaseModel, S extends IService<M>> S getService(Class<M> modelClass) throws InternalErrorException{
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
		String requestUri = this.systemController.getRequestURI();

		if(requestUri != null && requestUri.length() > 0){
			StringBuilder requestUriBuffer = new StringBuilder();

			requestUriBuffer.append(requestUri);

			Map<String, RequestParameterInfo> requestParameters = this.systemController.getRequestParameters();

			if(requestParameters != null && !requestParameters.isEmpty()){
				requestUriBuffer.append("?");

				int cont2 = 0;

				for(Entry<String, RequestParameterInfo> entry : requestParameters.entrySet()){
					String requestParameterName = entry.getKey();
					RequestParameterInfo requestParameterInfo = entry.getValue();

					if(requestParameterInfo.getContent() == null){
						String requestParameterValues[] = requestParameterInfo.getValues();

						if(requestParameterValues != null && requestParameterValues.length > 0){
							for(int cont1 = 0 ; cont1 < requestParameterValues.length ; cont1++){
								if(cont2 > 0)
									requestUriBuffer.append("&");

								requestUriBuffer.append(requestParameterName);
								requestUriBuffer.append("=");
								requestUriBuffer.append(requestParameterValues[cont1]);

								cont2++;
							}
						}
					}
				}
			}

			requestUri = requestUriBuffer.toString();
		}
		
		requestUri = StringUtil.replaceAll(requestUri, this.systemController.getContextPath(), "");

		UserModel user = loginSession.getUser();
		Boolean excludeUrl = false;
		
		if(exclusionUrls != null && !exclusionUrls.isEmpty()){
			for(UrlModel exclusionUrl : exclusionUrls){
				String urlPattern = StringUtil.toRegex(exclusionUrl.getPath());
				Pattern regex = Pattern.compile(urlPattern);
				Matcher matcher = regex.matcher(requestUri.toString());

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
			if(!user.isSuperUser() && !user.hasPermission(requestUri))
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
	protected void initialize() throws UserNotAuthorizedException, PermissionDeniedException, InternalErrorException{
		this.securityController = this.systemController.getSecurityController();

		LoginSessionModel loginSession = (this.securityController != null ? this.securityController.getLoginSession() : null);

		if(loginSession != null){
			Boolean isWebServicesRequest = (this.systemController != null ? this.systemController.isWebServicesRequest() : null);

			if(isWebServicesRequest != null && isWebServicesRequest){
				if(loginSession.getId() != null && loginSession.getId().length() > 0){
					Class<LoginSessionModel> loginSessionClass = (Class<LoginSessionModel>) loginSession.getClass();
					LoginSessionService<LoginSessionModel, UserModel> loginSessionService = null;

					try{
						loginSessionService = getService(loginSessionClass);
					}
					catch(InternalErrorException e){
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
					Long ttl = DateTimeUtil.diff(now, startDateTime, DateFieldType.MINUTES);

					if(this.securityResources.getLoginSessionTimeout() == null || ttl >= this.securityResources.getLoginSessionTimeout()){
						if(loginSessionService != null)
							loginSessionService.logOut();

						this.systemController.removeCookie(SecurityConstants.LOGIN_SESSION_ATTRIBUTE_ID);

						throw new LoginSessionExpiredException();
					}
				}
			}

			SystemModuleModel systemModule = loginSession.getSystemModule();
			Class<SystemModuleModel> modelClass = (Class<SystemModuleModel>)systemModule.getClass();
			IService<SystemModuleModel> systemModuleService = null;

			try{
				systemModuleService = getService(modelClass);
			}
			catch(InternalErrorException e){
			}

			if(systemModuleService != null){
				try{
					systemModule = systemModuleService.find(systemModule);

					if(systemModule.isActive() == null || !systemModule.isActive())
						throw new PermissionDeniedException();
				}
				catch(ItemNotFoundException e){
					throw new PermissionDeniedException();
				}
			}

			systemModule = systemModuleService.loadReference(systemModule, SystemConstants.EXCLUSION_URLS_ATTRIBUTE_ID);

			if(isWebServicesRequest == null || !isWebServicesRequest){
				systemModule = systemModuleService.loadReference(systemModule, SystemConstants.FORMS_ATTRIBUTE_ID);

				FormModel form = systemModule.getForm(ActionFormUtil.getActionFormIdByModel(this.systemResources.getMainConsoleClass()));

				if(form != null){
					Class<FormModel> formClass = (Class<FormModel>)form.getClass();
					IService<FormModel> formService = getService(formClass);

					form = formService.loadReference(form, SystemConstants.OBJECTS_ATTRIBUTE_ID);

					systemModule.setForm(form);
				}
			}

			loginSession.setSystemModule(systemModule);

			SystemSessionModel systemSession = loginSession.getSystemSession();

			if(systemSession != null && systemSession.getId() != null && systemSession.getId().length() > 0){
				if(systemSession.getStartDateTime() == null) {
					systemSession.setStartDateTime(new DateTime());

					Class<SystemSessionModel> systemSessionClass = (Class<SystemSessionModel>) systemSession.getClass();
					IService<SystemSessionModel> systemSessionService = null;
	
					try{
						systemSessionService = getService(systemSessionClass);
					}
					catch(InternalErrorException e){
					}

					if(systemSessionService != null){
						try{
							systemSession = systemSessionService.save(systemSession);
						}
						catch (ItemAlreadyExistsException e){
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

	/**
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException{
        ((HttpServletResponse)response).setHeader("Access-Control-Allow-Origin", "*");
        ((HttpServletResponse)response).setHeader("Access-Control-Allow-Methods", "HEAD, DELETE, PUT, POST, GET, OPTIONS");
        ((HttpServletResponse)response).setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, ".concat(SecurityConstants.LOGIN_SESSION_ATTRIBUTE_ID));
        
		if(((HttpServletRequest)request).getMethod().equals(HttpMethod.OPTIONS))
			filterChain.doFilter(request, response);
		else{
			this.systemController = new SystemController((HttpServletRequest)request, (HttpServletResponse)response);
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

	/**
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
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

	/**
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy(){
	}
}