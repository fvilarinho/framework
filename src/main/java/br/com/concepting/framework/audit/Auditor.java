package br.com.concepting.framework.audit;

import br.com.concepting.framework.audit.annotations.Auditable;
import br.com.concepting.framework.audit.helpers.AuditorMessage;
import br.com.concepting.framework.audit.resources.AuditorResources;
import br.com.concepting.framework.audit.resources.AuditorResourcesLoader;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.resources.FactoryResources;
import br.com.concepting.framework.resources.exceptions.InvalidResourcesException;
import br.com.concepting.framework.security.model.LoginSessionModel;
import br.com.concepting.framework.util.PropertyUtil;
import br.com.concepting.framework.util.helpers.DateTime;
import br.com.concepting.framework.util.types.StatusType;
import org.apache.commons.beanutils.ConstructorUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.LoggerConfig;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Collection;
import java.util.Map;


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
public class Auditor{
    private static final LoggerContext loggersContext = (LoggerContext)LogManager.getContext(false);

    private Logger logger = null;
    private Class<?> entity = null;
    private Method business = null;
    private String[] businessComplementArgumentsIds = null;
    private Class<?>[] businessComplementArgumentsTypes = null;
    private Object[] businessComplementArgumentsValues = null;
    private boolean transactionEnded = false;
    private long transactionStartTime = 0;
    private LoginSessionModel loginSession = null;
    private AuditorResources resources = null;

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
    public Auditor(Class<?> entity, Method business, Object... businessComplementArgumentsValues) throws InternalErrorException{
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
    public Auditor(Class<?> entity, Method business, Object[] businessComplementArgumentsValues, LoginSessionModel loginSession) throws InternalErrorException{
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
    public Auditor(Class<?> entity, Method business, Object[] businessComplementArgumentsValues, LoginSessionModel loginSession, AuditorResources resources) throws InternalErrorException{
        super();
        
        setResources(resources);
        setLoginSession(loginSession);
        setEntity(entity);
        setBusiness(business, businessComplementArgumentsValues);

        initialize();
    }

    private void initialize() throws InvalidResourcesException {
        this.logger = LogManager.getLogger(this.entity.getName());

        LoggerConfig loggerConfiguration = loggersContext.getConfiguration().getLoggerConfig(this.entity.getName());

        if(loggerConfiguration != null)
            loggerConfiguration.setParent(null);

        loadResources();
        loadLevel();
        loadAppenders();
    }
    
    /**
     * Returns the response time of the processing.
     *
     * @return Numeric value containing the response time in milliseconds.
     */
    public Long getResponseTime(){
        if(this.transactionEnded && this.transactionStartTime > 0)
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
        return (L) this.loginSession;
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
     * @return Array of business arguments that will be audited.
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
     * @return Array that contains identifiers of the business arguments that will be
     * audited.
     */
    public String[] getBusinessComplementArgumentsIds(){
        return this.businessComplementArgumentsIds;
    }
    
    /**
     * Defines the identifiers of the business arguments that will be audited.
     *
     * @param businessComplementArgumentsIds Array that contains the identifiers of the
     * business arguments that will be audited.
     */
    private void setBusinessComplementArgumentsIds(String[] businessComplementArgumentsIds){
        this.businessComplementArgumentsIds = businessComplementArgumentsIds;
    }
    
    /**
     * Returns the values of the business arguments that will be audited.
     *
     * @param <O> Class that defines the type of the value.
     * @return Array that contains the values of the business arguments that will be
     * audited.
     */
    @SuppressWarnings("unchecked")
    public <O> O[] getBusinessComplementArgumentsValues(){
        return (O[]) this.businessComplementArgumentsValues;
    }
    
    /**
     * Defines the values of the business arguments that will be audited.
     *
     * @param <O> Class that defines the type of the value.
     * @param businessComplementArgumentsValues Array that contains the values of the
     * business arguments that will be audited.
     */
    private <O> void setBusinessComplementArgumentsValues(O[] businessComplementArgumentsValues){
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
    public void setEntity(Class<?> entity) {
        this.entity = entity;
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
    public void setBusiness(Method business, Object[] businessComplementArgumentsValues){
        this.business = business;
        
        if(business != null){
            setBusinessComplementArgumentsValues(businessComplementArgumentsValues);
            
            if(business.getParameterCount() > 0 && businessComplementArgumentsValues != null && businessComplementArgumentsValues.length == business.getParameterCount()){
                String[] businessComplementArgumentsIds = new String[business.getParameterCount()];
                Class<?>[] businessComplementArgumentsTypes = new Class<?>[business.getParameterCount()];
                Object businessComplementArgumentsValue;
                int cont = 0;

                for(Parameter businessComplementArgument: business.getParameters()){
                    businessComplementArgumentsValue = businessComplementArgumentsValues[cont];
                    
                    businessComplementArgumentsIds[cont] = businessComplementArgument.getName();
                    businessComplementArgumentsTypes[cont] = (businessComplementArgumentsValue != null ? businessComplementArgumentsValue.getClass() : businessComplementArgument.getType());
                    
                    cont++;
                }

                setBusinessComplementArgumentsIds(businessComplementArgumentsIds);
                setBusinessComplementArgumentsTypes(businessComplementArgumentsTypes);
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
     * Loads the auditing resources.
     *
     * @throws InvalidResourcesException Occurs when the resources are invalid or
     * could not be read.
     */
    private void loadResources() throws InvalidResourcesException{
        if(this.resources == null && this.entity != null){
            Auditable auditableAnnotation = this.entity.getAnnotation(Auditable.class);

            if(auditableAnnotation == null){
                Class<?> superClass = this.entity;

                while(true){
                    if(superClass.getSuperclass() == null)
                        break;

                    superClass = superClass.getSuperclass();
                    auditableAnnotation = superClass.getAnnotation(Auditable.class);

                    if(auditableAnnotation == null)
                        break;
                }
            }

            String resourcesId = (auditableAnnotation != null ? auditableAnnotation.resourcesId() : null);
            AuditorResourcesLoader loader = new AuditorResourcesLoader();

            this.resources = loader.get(resourcesId);
        }
    }

    /**
     * Loads the auditing level.
     */
    private void loadLevel() {
        if(this.resources != null && this.logger != null)
            Configurator.setLevel(this.entity.getName(), Level.toLevel(this.resources.getLevel().toUpperCase()));
    }

    /**
     * Loads the appenders of the auditing.
     */
    private void loadAppenders(){
        if(this.resources == null)
            return;

        Collection<FactoryResources> appendersResources = this.resources.getAppenders();

        if(appendersResources == null)
            return;

        Configuration loggersConfiguration = loggersContext.getConfiguration();

        if(loggersConfiguration == null)
            return;

        LoggerConfig loggerConfiguration = loggersConfiguration.getLoggerConfig(this.entity.getName());

        if(loggerConfiguration == null)
            return;

        Map<String, Appender> appenders = loggerConfiguration.getAppenders();

        for (FactoryResources appenderResources : appendersResources) {
            boolean hasAppender = false;

            for(Appender appender : appenders.values()) {
                if (appenderResources.getClazz().equals(appender.getClass().getName())) {
                    hasAppender = true;

                    break;
                }
            }

            if(!hasAppender) {
                try {
                    Class<?> appenderClass = Class.forName(appenderResources.getClazz());
                    Map<String, String> appenderOptions = appenderResources.getOptions();
                    Appender appenderInstance = (Appender) ConstructorUtils.invokeConstructor(appenderClass, appenderResources.getClazz());

                    if (appenderOptions != null && !appenderOptions.isEmpty())
                        for (Map.Entry<String, String> entry : appenderOptions.entrySet())
                            PropertyUtil.setValue(appenderInstance, entry.getKey(), entry.getValue());

                    appenderInstance.start();

                    loggersConfiguration.addAppender(appenderInstance);

                    loggerConfiguration.addAppender(appenderInstance, Level.toLevel(this.resources.getLevel().toUpperCase()), null);
                }
                catch (Throwable e) {
                    e.printStackTrace(System.err);
                }
            }
        }
    }

    private AuditorMessage prepareAuditorMessage(String message){
        AuditorMessage auditorMessage = new AuditorMessage();

        auditorMessage.setStartDateTime(new DateTime());
        auditorMessage.setEntity(this.entity);
        auditorMessage.setBusiness(this.business);
        auditorMessage.setBusinessComplementArgumentsIds(this.businessComplementArgumentsIds);
        auditorMessage.setBusinessComplementArgumentsTypes(this.businessComplementArgumentsTypes);
        auditorMessage.setBusinessComplementArgumentsValues(this.businessComplementArgumentsValues);
        auditorMessage.setFormattedMessage(message);
        auditorMessage.setLoginSession(this.loginSession);
        auditorMessage.setResponseTime(getResponseTime());

        return auditorMessage;
    }

    private AuditorMessage prepareAuditorMessage(Throwable exception){
        AuditorMessage auditorMessage = this.prepareAuditorMessage((String)null);

        auditorMessage.setThrowable(exception);

        return auditorMessage;
    }

    /**
     * Creates an information message.
     *
     * @param message Instance that contains the message.
     */
    public void info(String message){
        if(message != null && !message.isEmpty())
            this.logger.info(prepareAuditorMessage(message));
    }

    /**
     * Creates a warning message.
     *
     * @param message Instance that contains the message.
     */
    public void warn(String message){
        if(message != null && !message.isEmpty())
            this.logger.warn(prepareAuditorMessage(message));
    }

    /**
     * Creates a debug message.
     *
     * @param message Instance that contains the message.
     */
    public void debug(String message){
        if(message != null && !message.isEmpty())
            this.logger.debug(prepareAuditorMessage(message));
    }
    
    /**
     * Creates an error message.
     *
     * @param message Instance that contains the message.
     */
    public void error(String message){
        if(message != null && !message.isEmpty())
            this.logger.error(prepareAuditorMessage(message));
    }
    
    /**
     * Creates an error message.
     *
     * @param e Instance that contains the caught exception.
     */
    public void error(Throwable e){
        if(e != null)
            this.logger.error(prepareAuditorMessage(e));
    }
    
    /**
     * Begins the auditing.
     */
    public void start(){
        this.transactionEnded = false;
        this.transactionStartTime = System.currentTimeMillis();

        info(StatusType.PROCESSING.toString());
    }
    
    /**
     * Ends the auditing when everything gone right.
     */
    public void end(){
        this.transactionEnded = true;

        info(StatusType.PROCESSED.toString());
        
        this.transactionStartTime = 0;
    }
    
    /**
     * Ends the auditing when everything gone wrong.
     *
     * @param exception Instance that contains the caught exception.
     */
    public void end(Throwable exception){
        this.transactionEnded = true;

        error(exception);

        info(StatusType.PROCESSED_WITH_ERROR.toString());
        
        this.transactionStartTime = 0;
    }
}