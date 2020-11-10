package br.com.concepting.framework.audit;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.ConstructorUtils;
import org.apache.log4j.Appender;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;

import br.com.concepting.framework.audit.annotations.Auditable;
import br.com.concepting.framework.audit.appenders.BaseAuditorAppender;
import br.com.concepting.framework.audit.resources.AuditorResources;
import br.com.concepting.framework.audit.resources.AuditorResourcesLoader;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.resources.FactoryResources;
import br.com.concepting.framework.resources.exceptions.InvalidResourcesException;
import br.com.concepting.framework.security.model.LoginSessionModel;
import br.com.concepting.framework.util.ExceptionUtil;
import br.com.concepting.framework.util.PropertyUtil;
import br.com.concepting.framework.util.types.StatusType;

/**
 * Class responsible to perform auditing.
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
public class Auditor{
	private Logger            logger                              = null;
	private Class<?>          entity                              = null;
	private Method            business                            = null;
	private String            businessComplementArgumentsIds[]    = null;
	private Class<?>          businessComplementArgumentsTypes[]  = null;
	private Object            businessComplementArgumentsValues[] = null;
	private Boolean           transactionEnded                    = null;
	private Long              transactionStartTime                = null;
	private LoginSessionModel loginSession                        = null;
	private AuditorResources  resources                           = null;

	/**
	 * Constructor - Defines the auditing's parameters.
	 * 
	 * @param entity Class that defines the entity that will be audited.
	 * @param business Method of the entity that defines the business that will
	 * be audited.
	 * @param businessComplementArgumentsValues List of business arguments that
	 * will be audited.
	 * @throws InternalErrorException Occurs when it was not possible to
	 * instantiate the auditing based on the specified parameters.
	 */
	public Auditor(Class<?> entity, Method business, Object businessComplementArgumentsValues[]) throws InternalErrorException{
		this(entity, business, businessComplementArgumentsValues, null, null);
	}

	/**
	 * Constructor - Defines the auditing's parameters.
	 * 
	 * @param entity Class that defines the entity that will be audited.
	 * @param business Method of the entity that defines the business that will
	 * be audited.
	 * @param businessComplementArgumentsValues List of business arguments that
	 * will be audited.
	 * @param loginSession Instance that contains the login session data of a
	 * user.
	 * @throws InternalErrorException Occurs when it was not possible to
	 * instantiate the auditing based on the specified parameters.
	 */
	public Auditor(Class<?> entity, Method business, Object businessComplementArgumentsValues[], LoginSessionModel loginSession) throws InternalErrorException{
		this(entity, business, businessComplementArgumentsValues, loginSession, null);
	}

	/**
	 * Constructor - Defines the auditing's parameters.
	 * 
	 * @param entity Class that defines the entity that will be audited.
	 * @param business Method of the entity that defines the business that will
	 * be audited.
	 * @param businessComplementArgumentsValues List of business arguments that
	 * will be audited.
	 * @param loginSession Instance that contains the login session data of a
	 * user.
	 * @param resources Instance that contains the auditing resources.
	 * @throws InternalErrorException Occurs when it was not possible to
	 * instantiate the auditing based on the specified parameters.
	 */
	public Auditor(Class<?> entity, Method business, Object businessComplementArgumentsValues[], LoginSessionModel loginSession, AuditorResources resources) throws InternalErrorException{
		super();

		setResources(resources);
		setLoginSession(loginSession);
		setEntity(entity);
		setBusiness(business, businessComplementArgumentsValues);

		initialize();
		loadResources();
		loadLevel();
		loadAppenders();
	}

	/**
	 * Returns the response time of the processing of the entity's business.
	 * 
	 * @return Numeric value containing the response time in milliseconds.
	 */
	public Long getResponseTime(){
		if(this.transactionEnded != null && this.transactionEnded && this.transactionStartTime != null)
			return (System.currentTimeMillis() - this.transactionStartTime);

		return null;
	}

	/**
	 * Returns the instance that contains the login session data of a user.
	 * 
	 * @param <L> Class that defines the data model of the login session.
	 * @return Instance that contains the login session data of a user.
	 */
	@SuppressWarnings("unchecked")
	public <L extends LoginSessionModel> L getLoginSession(){
		return (L)this.loginSession;
	}

	/**
	 * Defines the instance that contains the login session data of a user.
	 * 
	 * @param loginSession Instance that contains the login session data of a
	 * user.
	 */
	public void setLoginSession(LoginSessionModel loginSession){
		this.loginSession = loginSession;
	}

	/**
	 * Returns the list of business arguments that will be audited.
	 *
	 * @return List of business arguments that will be audited.
	 */
	public Class<?>[] getBusinessComplementArgumentsTypes(){
		return this.businessComplementArgumentsTypes;
	}

	/**
	 * Defines the list of business arguments that will be audited.
	 *
	 * @param businessComplementArgumentsTypes List of business arguments that
	 * will be audited.
	 */
	public void setBusinessComplementArgumentsTypes(Class<?>[] businessComplementArgumentsTypes){
		this.businessComplementArgumentsTypes = businessComplementArgumentsTypes;
	}

	/**
	 * Returns the identifiers of the business arguments that will be audited.
	 *
	 * @return List of the identifiers of the business arguments that will be
	 * audited.
	 */
	public String[] getBusinessComplementArgumentsIds(){
		return this.businessComplementArgumentsIds;
	}

	/**
	 * Defines the identifiers of the business arguments that will be audited.
	 *
	 * @param businessComplementArgumentsIds List of the identifiers of the
	 * business arguments that will be audited.
	 */
	private void setBusinessComplementArgumentsIds(String businessComplementArgumentsIds[]){
		this.businessComplementArgumentsIds = businessComplementArgumentsIds;
	}

	/**
	 * Returns the values of the business arguments that will be audited.
	 *
	 * @param <O> Class that defines the type of the value.
	 * @return List of the values of the business arguments that will be
	 * audited.
	 */
	@SuppressWarnings("unchecked")
	public <O> O[] getBusinessComplementArgumentsValues(){
		return (O[])this.businessComplementArgumentsValues;
	}

	/**
	 * Defines the values of the business arguments that will be audited.
	 *
	 * @param <O> Class that defines the type of the value.
	 * @param businessComplementArgumentsValues List of the values of the
	 * business arguments that will be audited.
	 */
	private <O> void setBusinessComplementArgumentsValues(O businessComplementArgumentsValues[]){
		this.businessComplementArgumentsValues = businessComplementArgumentsValues;
	}

	/**
	 * Returns the class that defines the entity that will be audited.
	 * 
	 * @return Class that defines the entity that will be audited.
	 */
	public Class<?> getEntity(){
		return this.entity;
	}

	/**
	 * Defines the class that defines the entity that will be audited.
	 * 
	 * @param entity Class that defines the entity that will be audited.
	 */
	public void setEntity(Class<?> entity){
		this.entity = entity;

		updateAppenders();
	}

	/**
	 * Returns the method of the entity that defines the business that will be
	 * audited.
	 * 
	 * @return Method of the entity that defines the business that will be
	 * audited.
	 */
	public Method getBusiness(){
		return this.business;
	}

	/**
	 * Defines the method of the entity that defines the business that will be
	 * audited.
	 * 
	 * @param business Method of the entity that defines the business that will
	 * be audited.
	 * @param businessComplementArgumentsValues List that contains the arguments
	 * of the business that will be audited.
	 */
	public void setBusiness(Method business, Object businessComplementArgumentsValues[]){
		this.business = business;

		if(business != null){
			setBusinessComplementArgumentsValues(businessComplementArgumentsValues);

			if(business.getParameterCount() > 0 && businessComplementArgumentsValues != null && businessComplementArgumentsValues.length == business.getParameterCount()){
				this.businessComplementArgumentsIds = new String[business.getParameterCount()];
				this.businessComplementArgumentsTypes = new Class[business.getParameterCount()];

				int cont = 0;
				Object businessComplementArgumentsValue = null;

				for(Parameter businessComplementArgument : business.getParameters()){
					businessComplementArgumentsValue = businessComplementArgumentsValues[cont];

					this.businessComplementArgumentsIds[cont] = businessComplementArgument.getName();
					this.businessComplementArgumentsTypes[cont] = (businessComplementArgumentsValue != null ? businessComplementArgumentsValue.getClass() : businessComplementArgument.getType());

					cont++;
				}
			}
			else{
				setBusinessComplementArgumentsIds(null);
				setBusinessComplementArgumentsTypes(null);
			}
		}
		else{
			setBusinessComplementArgumentsIds(null);
			setBusinessComplementArgumentsTypes(null);
			setBusinessComplementArgumentsValues(null);
		}

		updateAppenders();
	}

	/**
	 * Returns the auditing resources.
	 * 
	 * @return Instance that contains the auditing resources.
	 */
	public AuditorResources getResources(){
		return this.resources;
	}

	/**
	 * Defines the auditing resources.
	 * 
	 * @param resources Instance that contains the auditing resources.
	 */
	public void setResources(AuditorResources resources){
		this.resources = resources;
	}

	/**
	 * Initialize the auditing resources.
	 */
	@SuppressWarnings("unchecked")
	private void initialize(){
		Enumeration<Appender> enumeration = Logger.getRootLogger().getAllAppenders();
		
		if(enumeration == null || !enumeration.hasMoreElements())
			BasicConfigurator.configure();
		
		this.logger = Logger.getLogger(this.entity);
	}

	/**
	 * Loads the auditing resources.
	 * 
	 * @throws InvalidResourcesException Occurs when the resources are invalid or
	 * could not be read.
	 */
	private void loadResources() throws InvalidResourcesException{
		if(this.resources == null){
			if(this.entity != null){
				Auditable auditableAnnotation = this.entity.getAnnotation(Auditable.class);

				if(auditableAnnotation == null){
					Class<?> superClass = this.entity;

					do{
						superClass = superClass.getSuperclass();

						if(superClass != null){
							auditableAnnotation = superClass.getAnnotation(Auditable.class);

							if(auditableAnnotation == null)
								break;
						}
					}
					while(superClass != null && auditableAnnotation == null);
				}

				String resourcesId = (auditableAnnotation != null ? auditableAnnotation.resourcesId() : null);
				AuditorResourcesLoader loader = new AuditorResourcesLoader();

				this.resources = loader.get(resourcesId);
			}
		}
	}

	/**
	 * Defines the level of the auditing's messages. The supported levels are
	 * debug, info and error.
	 */
	private void loadLevel(){
		if(this.resources != null){
			Level level = Level.OFF;
			String levelBuffer = this.resources.getLevel();

			if(levelBuffer != null && levelBuffer.length() > 0)
				level = Level.toLevel(levelBuffer.toUpperCase());

			this.logger.setLevel(level);
			
			Logger.getRootLogger().setLevel(level);
		}
	}

	@SuppressWarnings("unchecked")
	private void updateAppenders(){
		if(this.logger != null){
			Enumeration<Appender> allAppenders = this.logger.getAllAppenders();
			Appender appenderInstance = null;

			while(allAppenders.hasMoreElements()){
				appenderInstance = allAppenders.nextElement();

				if(appenderInstance instanceof BaseAuditorAppender){
					((BaseAuditorAppender)appenderInstance).setAuditor(this);

					break;
				}
			}
		}
	}

	/**
	 * Loads the storages of auditing's messages.
	 * 
	 * @throws InternalErrorException Occurs when was not possible to load the
	 * storages of auditing's messages.
	 */
	@SuppressWarnings("unchecked")
	private void loadAppenders() throws InternalErrorException{
		Enumeration<Appender> enumeration = Logger.getRootLogger().getAllAppenders();
		
		if(enumeration != null){
			while(enumeration.hasMoreElements()){
				Appender appender = enumeration.nextElement();
				
				appender.addFilter(new Filter(){
					public int decide(LoggingEvent event){
						try{
							Class<?> entity = Class.forName(event.getLoggerName());
							
							if(entity.equals(Auditor.this.entity))
								return -1;
						}
						catch(Throwable e){
						}
						
						return 0;
					}
				});
			}
		}
		
		if(this.resources != null){
			Collection<FactoryResources> appendersResources = this.resources.getAppenders();

			if(appendersResources != null){
				Enumeration<Appender> allAppenders = this.logger.getAllAppenders();
				Class<?> appenderClass = null;
				Appender appenderInstance = null;
				Map<String, String> appenderOptions = null;
				Boolean hasAppender = null;

				for(FactoryResources appenderResources : appendersResources){
					appenderOptions = appenderResources.getOptions();
					hasAppender = false;

					while(allAppenders != null && allAppenders.hasMoreElements()){
						appenderInstance = allAppenders.nextElement();

						if(appenderResources.getClazz() != null && appenderResources.getClazz().equals(appenderInstance.getClass().getName())){
							hasAppender = true;

							if(appenderInstance instanceof BaseAuditorAppender)
								((BaseAuditorAppender)appenderInstance).setAuditor(this);

							break;
						}
					}

					if(!hasAppender){
						try{
							appenderClass = Class.forName(appenderResources.getClazz());

							try{
								appenderInstance = (Appender)ConstructorUtils.invokeConstructor(appenderClass, this);
							}
							catch(NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e1){
								appenderInstance = (Appender)ConstructorUtils.invokeConstructor(appenderClass, null);
							}

							if(appenderOptions != null && appenderOptions.size() > 0)
								if(appenderInstance.requiresLayout())
									for(Entry<String, String> entry : appenderOptions.entrySet())
										PropertyUtil.setValue(appenderInstance, entry.getKey(), entry.getValue());

							if(appenderInstance instanceof BaseAuditorAppender)
								((BaseAuditorAppender)appenderInstance).initializeLayout();

							this.logger.addAppender(appenderInstance);
						}
						catch(ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e){
							throw new InternalErrorException(e);
						}
					}
				}
			}
		}
	}

	/**
	 * Creates an information message.
	 *
	 * @param <O> Class that defines the message type.
	 * @param message Instance that contains the message.
	 */
	public <O> void info(O message){
		if(message != null)
			this.logger.info(message);
	}

	/**
	 * Creates a debug message.
	 * 
	 * @param <O> Class that defines the message type.
	 * @param message Instance that contains the message.
	 */
	public <O> void debug(O message){
		if(message != null)
			this.logger.debug(message);
	}

	/**
	 * Creates an error message.
	 * 
	 * @param <O> Class that defines the message type.
	 * @param message Instance that contains the message.
	 */
	public <O> void error(O message){
		if(message != null)
			this.logger.error(message);
	}

	/**
	 * Creates an error message.
	 * 
	 * @param e Instance that contains the caught exception.
	 */
	public void error(Throwable e){
		if(e != null)
			this.logger.error(ExceptionUtil.getTrace(e));
	}

	/**
	 * Begins the auditing.
	 */
	public void start(){
		this.transactionEnded = false;
		this.transactionStartTime = System.currentTimeMillis();

		info(StatusType.PROCESSING);
	}

	/**
	 * Ends the auditing when everything gone right.
	 */
	public void end(){
		this.transactionEnded = true;

		info(StatusType.PROCESSED);

		this.transactionStartTime = null;
	}

	/**
	 * Ends the auditing when everything gone wrong.
	 * 
	 * @param exception Instance that contains the caught exception.
	 */
	public void end(Throwable exception){
		error(exception);

		this.transactionEnded = true;

		info(StatusType.PROCESSED_WITH_ERROR);

		this.transactionStartTime = null;
	}
}