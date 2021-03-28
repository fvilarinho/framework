package br.com.concepting.framework.audit.appenders;

import br.com.concepting.framework.audit.Auditor;
import br.com.concepting.framework.audit.annotations.Auditable;
import br.com.concepting.framework.audit.appenders.helpers.ConsoleLayout;
import br.com.concepting.framework.audit.constants.AuditorConstants;
import br.com.concepting.framework.audit.model.AuditorComplementModel;
import br.com.concepting.framework.audit.model.AuditorModel;
import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.helpers.ModelInfo;
import br.com.concepting.framework.model.util.ModelUtil;
import br.com.concepting.framework.util.ExceptionUtil;
import br.com.concepting.framework.util.PropertyUtil;
import br.com.concepting.framework.util.helpers.DateTime;
import br.com.concepting.framework.util.helpers.PropertyInfo;
import org.apache.commons.beanutils.ConstructorUtils;
import org.apache.log4j.Layout;
import org.apache.log4j.WriterAppender;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.ThrowableInformation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;

/**
 * Class that defines the basic implementation of the storage for auditing's
 * messages.
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
public abstract class BaseAuditorAppender extends WriterAppender{
    private Auditor auditor = null;
    private String modelClass = null;
    
    /**
     * Constructor - Initialize the storage.
     *
     * @param auditor Instance that contains the auditing.
     */
    public BaseAuditorAppender(Auditor auditor){
        super();
        
        setAuditor(auditor);
    }
    
    /**
     * Returns the identifier of the auditing data model.
     *
     * @return String that contains the identifier.
     */
    public String getModelClass(){
        return this.modelClass;
    }
    
    /**
     * Defines the identifier of the auditing data model.
     *
     * @param modelClass String that contains the identifier.
     */
    public void setModelClass(String modelClass){
        this.modelClass = modelClass;
    }
    
    /**
     * Defines the auditing.
     *
     * @param auditor Instance that contains the auditing.
     */
    public void setAuditor(Auditor auditor){
        this.auditor = auditor;
    }
    
    /**
     * Returns the auditing.
     *
     * @return Instance that contains the auditing.
     */
    public Auditor getAuditor(){
        return this.auditor;
    }
    
    /**
     * Returns the identifier of the entity that will be audited.
     *
     * @param entity Class that defines the entity that will be audited.
     * @return String that contains the identifier.
     */
    private String getEntityId(Class<?> entity){
        return getEntityId(entity, true);
    }
    
    /**
     * Returns the identifier of the entity that will be audited.
     *
     * @param entity Class that defines the entity that will be audited.
     * @param lastIteration True/False.
     * @return String that contains the identifier.
     */
    private String getEntityId(Class<?> entity, Boolean lastIteration){
        String entityId = null;
        
        if(entity != null){
            Auditable auditableAnnotation = entity.getAnnotation(Auditable.class);
            
            if(auditableAnnotation == null){
                Class<?> superClass = entity;
                
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
            
            if(auditableAnnotation != null)
                entityId = auditableAnnotation.id();
            
            if(lastIteration != null && lastIteration && (entityId == null || entityId.length() == 0))
                entityId = entity.getName();
        }
        
        return entityId;
    }
    
    /**
     * Returns the identifier of the business that will be audited.
     *
     * @param method Method that defines the business that will be audited.
     * @return String that contains the identifier.
     */
    private String getBusinessId(Method method){
        String businessId = null;
        
        if(this.auditor != null && method != null){
            Method business = this.auditor.getBusiness();
            Auditable auditableAnnotation = (business != null ? business.getAnnotation(Auditable.class) : null);
            
            if(auditableAnnotation != null)
                businessId = auditableAnnotation.id();
            
            if(businessId == null || businessId.length() == 0)
                businessId = business.getName();
        }
        
        return businessId;
    }
    
    /**
     * Returns the auditing.
     *
     * @param <A> Class that defines the data model.
     * @param event Instance that contains the properties of the event.
     * @return Instance that contains the data model.
     */
    @SuppressWarnings("unchecked")
    public <A extends AuditorModel> A getModel(LoggingEvent event){
        A model = null;
        
        if(event != null && this.auditor != null){
            try{
                if(this.modelClass != null)
                    model = (A) ConstructorUtils.invokeConstructor(Class.forName(this.modelClass), null);
                else
                    model = (A) new AuditorModel();
            }
            catch(NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException | ClassNotFoundException e){
                model = (A) new AuditorModel();
            }
            
            model.setStartDateTime(new DateTime());
            model.setLoginSession(this.auditor.getLoginSession());
            model.setEntityId(getEntityId(this.auditor.getEntity()));
            model.setBusinessId(getBusinessId(this.auditor.getBusiness()));
            model.setBusinessComplement(buildBusinessComplement(model));
            model.setSeverity(event.getLevel().toString());
            model.setMessage(event.getMessage().toString());
            model.setResponseTime(this.auditor.getResponseTime());
            
            ThrowableInformation information = event.getThrowableInformation();
            
            if(information != null){
                Throwable e = information.getThrowable();
                
                if(e != null)
                    model.setMessage(ExceptionUtil.getTrace(e));
            }
        }
        
        return model;
    }
    
    /**
     * Initialize the layout.
     *
     * @throws InternalErrorException Occurs when was not possible to initialize
     * the layout.
     */
    public void initializeLayout() throws InternalErrorException{
    }
    
    /**
     * Builds the business complement.
     *
     * @param <C> Class that defines the list that stores the business
     * complement.
     * @param auditorModel Instance that contains the auditing.
     * @return List containing the business complement.
     */
    @SuppressWarnings("unchecked")
    protected <C extends Collection<AuditorComplementModel>> C buildBusinessComplement(AuditorModel auditorModel){
        C businessComplement = null;
        
        if(this.auditor != null && auditorModel != null){
            String[] businessComplementArgumentIds = this.auditor.getBusinessComplementArgumentsIds();
            Class<?>[] businessComplementArgumentTypes = this.auditor.getBusinessComplementArgumentsTypes();
            Object[] businessComplementArgumentValues = this.auditor.getBusinessComplementArgumentsValues();
            
            if(businessComplementArgumentValues != null && businessComplementArgumentValues.length > 0){
                try{
                    businessComplement = PropertyUtil.instantiate(Constants.DEFAULT_LIST_CLASS);
                    
                    ModelInfo modelInfo = ModelUtil.getInfo(auditorModel.getClass());
                    PropertyInfo propertyInfo = modelInfo.getPropertyInfo(AuditorConstants.BUSINESS_COMPLEMENT_ATTRIBUTE_ID);
                    Class<AuditorComplementModel> businessComplementArgumentClass = (Class<AuditorComplementModel>) propertyInfo.getCollectionItemsClass();
                    
                    for(int cont = 0; cont < businessComplementArgumentValues.length; cont++){
                        String businessComplementArgumentId = (businessComplementArgumentIds != null && cont < businessComplementArgumentIds.length ? businessComplementArgumentIds[cont] : null);
                        Class<?> businessComplementArgumentType = (businessComplementArgumentTypes != null && cont < businessComplementArgumentTypes.length ? businessComplementArgumentTypes[cont] : null);
                        Object businessComplementArgumentValue = businessComplementArgumentValues[cont];
                        C businessComplementItems = buildBusinessComplement(auditorModel, businessComplementArgumentId, businessComplementArgumentType, businessComplementArgumentValue, businessComplementArgumentClass);
                        
                        if(businessComplementItems != null && businessComplementItems.size() > 0)
                            businessComplement.addAll(businessComplementItems);
                    }
                }
                catch(IllegalArgumentException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException | ClassNotFoundException | NoSuchFieldException e){
                }
            }
        }
        
        return businessComplement;
    }
    
    /**
     * Builds the business complement.
     *
     * @param <C> Class that defines the list that stores the business
     * complement.
     * @param auditor Instance that contains the auditing.
     * @param businessComplementId String that contains the identifier
     * of the business complement argument.
     * @param businessComplementType String that contains the type of
     * the business complement argument.
     * @param businessComplementValue Instance that contains the business
     * complement argument.
     * @param businessComplementItemType Class that defines the business
     * complement.
     * @return List containing the business complement.
     */
    @SuppressWarnings("unchecked")
    private <C extends Collection<AuditorComplementModel>> C buildBusinessComplement(AuditorModel auditor, String businessComplementId, Class<?> businessComplementType, Object businessComplementValue, Class<? extends AuditorComplementModel> businessComplementItemType){
        C businessComplement = null;
        
        if(auditor != null && businessComplementValue != null){
            try{
                businessComplement = PropertyUtil.instantiate(Constants.DEFAULT_LIST_CLASS);
                
                if(PropertyUtil.isModel(businessComplementType)){
                    ModelInfo modelInfo = ModelUtil.getInfo((Class<? extends BaseModel>) businessComplementType);
                    Collection<PropertyInfo> auditablePropertiesInfo = modelInfo.getAuditablePropertiesInfo();
                    
                    if(auditablePropertiesInfo != null && !auditablePropertiesInfo.isEmpty()){
                        for(PropertyInfo auditablePropertyInfo: auditablePropertiesInfo){
                            String name = auditablePropertyInfo.getId();
                            Class<?> type = auditablePropertyInfo.getClazz();
                            Object value = PropertyUtil.getValue(businessComplementValue, name);
                            
                            if(value == null)
                                continue;
                            
                            if(auditablePropertyInfo.isModel() != null && auditablePropertyInfo.isModel()){
                                C items = buildBusinessComplement(auditor, name, type, value, businessComplementItemType);
                                
                                if(items != null && items.size() > 0)
                                    businessComplement.addAll(items);
                            }
                            else{
                                AuditorComplementModel item = ConstructorUtils.invokeConstructor(businessComplementItemType, null);
                                
                                item.setAuditor(auditor);
                                item.setName(name);
                                item.setType(type.getName());
                                item.setValue(value);
                                
                                businessComplement.add(item);
                            }
                        }
                    }
                }
                else if(PropertyUtil.isCollection(businessComplementType)){
                    Collection<?> businessComplementValues = (Collection<?>) businessComplementValue;
                    
                    for(Object businessComplementValuesItem: businessComplementValues){
                        if(businessComplementValuesItem != null){
                            Class<?> businessComplementValuesItemType = businessComplementValuesItem.getClass();
                            C items = buildBusinessComplement(auditor, businessComplementId, businessComplementValuesItemType, businessComplementValuesItem, businessComplementItemType);
                            
                            if(items != null && items.size() > 0)
                                businessComplement.addAll(items);
                        }
                    }
                }
                else{
                    AuditorComplementModel item = ConstructorUtils.invokeConstructor(businessComplementItemType, null);
                    
                    item.setAuditor(auditor);
                    item.setName(businessComplementId);
                    
                    if(businessComplementType == null)
                        businessComplementType = businessComplementValue.getClass();
                    
                    item.setType(businessComplementType.getName());
                    item.setValue(businessComplementValue);
                    
                    businessComplement.add(item);
                }
            }
            catch(NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException | IllegalArgumentException | ClassNotFoundException | NoSuchFieldException e){
            }
        }
        
        return businessComplement;
    }
    
    /**
     * @see org.apache.log4j.Appender#setLayout(org.apache.log4j.Layout)
     */
    public void setLayout(Layout layout){
        super.setLayout(layout);
        
        activateOptions();
    }
    
    /**
     * @see org.apache.log4j.AppenderSkeleton#append(org.apache.log4j.spi.LoggingEvent)
     */
    public void append(LoggingEvent event){
        try{
            process(event);
            
            flush();
        }
        catch(InternalErrorException e){
        }
    }
    
    /**
     * Process the auditing.
     *
     * @param event Instance that contains the properties of the event.
     * @throws InternalErrorException Occurs when was not possible to process
     * the audting.
     */
    protected void process(LoggingEvent event) throws InternalErrorException{
        Layout layout = getLayout();
        
        if(layout instanceof ConsoleLayout){
            AuditorModel model = getModel(event);
            
            if(model != null){
                ConsoleLayout auditorLayout = (ConsoleLayout) layout;
                
                auditorLayout.setModel(model);
            }
        }
        
        super.append(event);
    }
    
    /**
     * Flushes the queue of messages.
     */
    protected void flush(){
    }
}