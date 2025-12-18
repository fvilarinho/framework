package br.com.concepting.framework.audit.appenders;

import br.com.concepting.framework.audit.annotations.Auditable;
import br.com.concepting.framework.audit.constants.AuditorConstants;
import br.com.concepting.framework.audit.helpers.AuditorMessage;
import br.com.concepting.framework.audit.model.AuditorComplementModel;
import br.com.concepting.framework.audit.model.AuditorModel;
import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.helpers.ModelInfo;
import br.com.concepting.framework.model.util.ModelUtil;
import br.com.concepting.framework.util.ExceptionUtil;
import br.com.concepting.framework.util.PropertyUtil;
import br.com.concepting.framework.util.helpers.PropertyInfo;
import org.apache.commons.beanutils.ConstructorUtils;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.message.Message;

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
public abstract class BaseAuditorAppender extends AbstractAppender {
    private String modelClass = null;

    /**
     * Constructor - Initialize the appender.
     */
    public BaseAuditorAppender(final String name) {
        super(name, null, null, false, Property.EMPTY_ARRAY);
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
     * Returns the identifier of the entity that will be audited.
     *
     * @return String that contains the identifier.
     */
    private String getEntityId(Class<?> entity){
        String entityId = null;

        if(entity != null){
            Auditable auditableAnnotation = entity.getAnnotation(Auditable.class);
            
            if(auditableAnnotation == null){
                Class<?> superClass = entity;
                
                while(true){
                    if(superClass.getSuperclass() == null)
                        break;

                    superClass = superClass.getSuperclass();
                    auditableAnnotation = superClass.getAnnotation(Auditable.class);
                        
                    if(auditableAnnotation == null)
                        break;
                }
            }
            
            if(auditableAnnotation != null)
                entityId = auditableAnnotation.id();
            
            if(entityId == null || entityId.isEmpty())
                entityId = entity.getName();
        }
        
        return entityId;
    }
    
    /**
     * Returns the identifier of the business that will be audited.
     *
     * @return String that contains the identifier.
     */
    private String getBusinessId(Method business){
        String businessId = null;
        
        if(business != null){
            Auditable auditableAnnotation = business.getAnnotation(Auditable.class);
            
            if(auditableAnnotation != null)
                businessId = auditableAnnotation.id();
            
            if(businessId == null || businessId.isEmpty())
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
    public <A extends AuditorModel> A getModel(LogEvent event){
        A model = null;
        
        if(event != null){
            Message eventMessage = event.getMessage();

            if(eventMessage instanceof AuditorMessage auditorMessage) {
                try {
                    if (this.modelClass != null)
                        model = (A) ConstructorUtils.invokeConstructor(Class.forName(this.modelClass), null);
                    else
                        model = (A) new AuditorModel();
                }
                catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException | ClassNotFoundException e) {
                    model = (A) new AuditorModel();
                }

                model.setStartDateTime(auditorMessage.getStartDateTime());
                model.setLoginSession(auditorMessage.getLoginSession());
                model.setEntityId(getEntityId(auditorMessage.getEntity()));
                model.setBusinessId(getBusinessId(auditorMessage.getBusiness()));
                model.setBusinessComplement(buildBusinessComplement(model, auditorMessage.getBusinessComplementArgumentsIds(), auditorMessage.getBusinessComplementArgumentsTypes(), auditorMessage.getBusinessComplementArgumentsValues()));
                model.setResponseTime(auditorMessage.getResponseTime());
                model.setSeverity(event.getLevel().toString());

                Throwable exception = auditorMessage.getThrowable();

                if (exception != null)
                    model.setMessage(ExceptionUtil.getTrace(exception));
                else
                    model.setMessage(event.getMessage().getFormattedMessage());
            }
        }
        
        return model;
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
    protected <C extends Collection<AuditorComplementModel>> C buildBusinessComplement(AuditorModel auditorModel, String[] businessComplementArgumentsIds, Class<?>[] businessComplementArgumentsTypes, Object[] businessComplementArgumentValues){
        C businessComplement = null;

        if (businessComplementArgumentValues != null && businessComplementArgumentValues.length > 0) {
            try {
                businessComplement = PropertyUtil.instantiate(Constants.DEFAULT_LIST_CLASS);

                ModelInfo modelInfo = ModelUtil.getInfo(auditorModel.getClass());
                PropertyInfo propertyInfo = modelInfo.getPropertyInfo(AuditorConstants.BUSINESS_COMPLEMENT_ATTRIBUTE_ID);
                Class<AuditorComplementModel> businessComplementArgumentClass = (Class<AuditorComplementModel>) propertyInfo.getCollectionItemsClass();

                for (int cont = 0; cont < businessComplementArgumentValues.length; cont++) {
                    String businessComplementArgumentId = (businessComplementArgumentsIds != null && cont < businessComplementArgumentsIds.length ? businessComplementArgumentsIds[cont] : null);
                    Class<?> businessComplementArgumentType = (businessComplementArgumentsTypes != null && cont < businessComplementArgumentsTypes.length ? businessComplementArgumentsTypes[cont] : null);
                    Object businessComplementArgumentValue = businessComplementArgumentValues[cont];
                    C businessComplementItems = buildBusinessComplement(auditorModel, businessComplementArgumentId, businessComplementArgumentType, businessComplementArgumentValue, businessComplementArgumentClass);

                    if (businessComplementItems != null && !businessComplementItems.isEmpty())
                        if (businessComplement != null)
                            businessComplement.addAll(businessComplementItems);
                }
            }
            catch (IllegalArgumentException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException | ClassNotFoundException | NoSuchFieldException ignored) {
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
                            
                            if(auditablePropertyInfo.isModel()){
                                C items = buildBusinessComplement(auditor, name, type, value, businessComplementItemType);
                                
                                if(items != null && !items.isEmpty())
                                    if(businessComplement != null)
                                        businessComplement.addAll(items);
                            }
                            else{
                                AuditorComplementModel item = ConstructorUtils.invokeConstructor(businessComplementItemType, null);
                                
                                item.setAuditor(auditor);
                                item.setName(name);
                                item.setType(type.getName());
                                item.setValue(value);

                                if(businessComplement != null)
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
                            
                            if(items != null && !items.isEmpty())
                                if(businessComplement != null)
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

                    if(businessComplement != null)
                        businessComplement.add(item);
                }
            }
            catch(NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException | IllegalArgumentException | ClassNotFoundException | NoSuchFieldException ignored){
            }
        }
        
        return businessComplement;
    }

    @Override
    public void append(LogEvent event){
        try{
            process(event);

            flush();
        }
        catch(InternalErrorException e){
            e.printStackTrace(System.err);
        }
    }

    /**
     * Process the auditing.
     *
     * @param event Instance that contains the properties of the event.
     * @throws InternalErrorException Occurs when was not possible to process
     * the auditing.
     */
    protected abstract void process(LogEvent event) throws InternalErrorException;

    /**
     * Flushes the queue of messages.
     */
    protected void flush(){
    }
}