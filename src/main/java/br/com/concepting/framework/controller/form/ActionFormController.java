package br.com.concepting.framework.controller.form;

import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.constants.ProjectConstants;
import br.com.concepting.framework.controller.SystemController;
import br.com.concepting.framework.controller.form.constants.ActionFormConstants;
import br.com.concepting.framework.controller.form.constants.ActionFormMessageConstants;
import br.com.concepting.framework.controller.form.helpers.ActionFormMessage;
import br.com.concepting.framework.controller.form.types.ActionFormMessageType;
import br.com.concepting.framework.controller.form.util.ActionFormMessageUtil;
import br.com.concepting.framework.controller.form.util.ActionFormValidationMessageUtil;
import br.com.concepting.framework.controller.types.ScopeType;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.constants.ModelConstants;
import br.com.concepting.framework.model.types.ConditionType;
import br.com.concepting.framework.resources.SystemResources;
import br.com.concepting.framework.resources.SystemResourcesLoader;
import br.com.concepting.framework.ui.constants.UIConstants;
import br.com.concepting.framework.util.NumberUtil;
import br.com.concepting.framework.util.PropertyUtil;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.types.ByteMetricType;
import br.com.concepting.framework.util.types.ContentType;
import org.apache.commons.beanutils.ConstructorUtils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.util.Collection;

/**
 * Class that defines the form controller.
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
public class ActionFormController{
    private final SystemController systemController;

    private String actionFormName = null;

    /**
     * Constructor - Initialize the controller.
     *
     * @param systemController Instance that contains the system controller.
     */
    public ActionFormController(SystemController systemController){
        super();
        
        this.systemController = systemController;
    }
    
    /**
     * Defines the identifier of the form.
     *
     * @param actionFormName String that contains the identifier of the form.
     */
    public void setActionFormName(String actionFormName){
        this.actionFormName = actionFormName;
    }
    
    /**
     * Returns the instance of the form by its name.
     *
     * @param name String that contains the identifier of the form.
     * @return Instance that contains the form.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    @SuppressWarnings("unchecked")
    public BaseActionForm<? extends BaseModel> getActionFormInstanceByName(String name) throws InternalErrorException{
        BaseActionForm<? extends BaseModel> actionFormInstance = null;
        
        if(name != null && !name.isEmpty()){
            actionFormInstance = this.systemController.getAttribute(name, ScopeType.SESSION);
            
            if(actionFormInstance == null){
                try{
                    SystemResourcesLoader loader = new SystemResourcesLoader();
                    SystemResources systemResources = loader.getDefault();
                    Collection<SystemResources.ActionFormResources> actionFormsResources = (systemResources != null ? systemResources.getActionForms() : null);
                    
                    if(actionFormsResources != null && !actionFormsResources.isEmpty()){
                        for(SystemResources.ActionFormResources actionFormResources: actionFormsResources){
                            if(name.equals(actionFormResources.getName())){
                                this.actionFormName = name;
                                
                                Class<? extends BaseActionForm<? extends BaseModel>> actionFormClass = (Class<? extends BaseActionForm<? extends BaseModel>>) Class.forName(actionFormResources.getClazz());
                                
                                actionFormInstance = ConstructorUtils.invokeConstructor(actionFormClass, null);
                                actionFormInstance.setName(this.actionFormName);
                                
                                this.systemController.setAttribute(this.actionFormName, actionFormInstance, ScopeType.SESSION);
                                
                                return actionFormInstance;
                            }
                        }
                    }
                }
                catch(NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException | ClassNotFoundException e){
                    throw new InternalErrorException(e);
                }
            }
        }
        
        return actionFormInstance;
    }
    
    /**
     * Returns the instance of the form by its action URL.
     *
     * @param action String that contains the action URL of the form.
     * @return Instance that contains the form.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    @SuppressWarnings("unchecked")
    public BaseActionForm<? extends BaseModel> getActionFormInstanceByAction(String action) throws InternalErrorException{
        BaseActionForm<? extends BaseModel> actionFormInstance = null;
        
        if(action != null && !action.isEmpty()){
            try{
                SystemResourcesLoader loader = new SystemResourcesLoader();
                SystemResources systemResources = loader.getDefault();
                Collection<SystemResources.ActionFormResources> actionFormsResources = (systemResources != null ? systemResources.getActionForms() : null);
                
                if(actionFormsResources != null && !actionFormsResources.isEmpty()){
                    for(SystemResources.ActionFormResources actionFormResources: actionFormsResources){
                        if(action.equals(actionFormResources.getAction())){
                            this.actionFormName = actionFormResources.getName();
                            
                            actionFormInstance = this.systemController.getAttribute(this.actionFormName, ScopeType.SESSION);
                            
                            if(actionFormInstance == null){
                                Class<? extends BaseActionForm<? extends BaseModel>> actionFormClass = (Class<? extends BaseActionForm<? extends BaseModel>>) Class.forName(actionFormResources.getClazz());
                                
                                actionFormInstance = ConstructorUtils.invokeConstructor(actionFormClass, null);
                                actionFormInstance.setName(this.actionFormName);
                                
                                this.systemController.setAttribute(this.actionFormName, actionFormInstance, ScopeType.SESSION);
                            }
                            
                            return actionFormInstance;
                        }
                    }
                }
            }
            catch(NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException | ClassNotFoundException e){
                throw new InternalErrorException(e);
            }
        }
        
        return null;
    }
    
    /**
     * Returns the instance of the form.
     *
     * @return Instance that contains the form.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    public BaseActionForm<? extends BaseModel> getActionFormInstance() throws InternalErrorException{
        BaseActionForm<? extends BaseModel> actionFormInstance;
        
        if(this.actionFormName == null || this.actionFormName.isEmpty()){
            String action = StringUtil.replaceAll(this.systemController.getURI(), this.systemController.getContextPath(), "");
            
            action = StringUtil.replaceAll(action, ProjectConstants.DEFAULT_UI_PAGES_DIR, "");
            action = StringUtil.replaceAll(action, ActionFormConstants.DEFAULT_ACTION_FILE_EXTENSION, "");
            
            int pos = action.indexOf(UIConstants.DEFAULT_PAGE_FILE_EXTENSION);
            
            if(pos >= 0){
                action = action.substring(0, pos);
                pos = action.lastIndexOf("/");
                
                if(pos >= 0)
                    action = action.substring(0, pos);
            }
            
            actionFormInstance = getActionFormInstanceByAction(action);
        }
        else
            actionFormInstance = getActionFormInstanceByName(this.actionFormName);
        
        return actionFormInstance;
    }
    
    /**
     * Returns the identifier of the property dataset.
     *
     * @param propertyName String that contains the identifier of the property.
     * @return String that contains the identifier of the property dataset.
     */
    private String getPropertyDataset(String propertyName){
        if(propertyName != null && !propertyName.isEmpty()){
            StringBuilder key = new StringBuilder();
            
            key.append(propertyName);
            key.append(".");
            key.append(ActionFormConstants.DATASET_ATTRIBUTE_ID);
            
            String keyValue = this.systemController.getParameterValue(key.toString());
            
            if(keyValue != null && !keyValue.isEmpty()){
                StringBuilder result = new StringBuilder();
                ScopeType propertyDatasetScope = getPropertyDatasetScope(propertyName);
                
                if(propertyDatasetScope == ScopeType.MODEL){
                    result.append(this.actionFormName);
                    result.append(".");
                    result.append(ModelConstants.DEFAULT_ID);
                    result.append(".");
                }
                
                result.append(keyValue);
                
                return result.toString();
            }
        }
        
        return null;
    }
    
    /**
     * Returns the instance of the property dataset.
     *
     * @param <C> Class that defines the data set.
     * @param propertyName String that contains the identifier of the property.
     * @return Instance that contains the property dataset.
     */
    public <C extends Collection<?>> C getPropertyDatasetValues(String propertyName){
        if(propertyName != null && !propertyName.isEmpty()){
            String propertyDataset = getPropertyDataset(propertyName);
            
            if(propertyDataset != null && !propertyDataset.isEmpty())
                return this.systemController.getAttribute(propertyDataset, ScopeType.SESSION);
        }
        
        return null;
    }
    
    /**
     * Returns the instance of the scope type for the property dataset.
     *
     * @param propertyName String that contains the identifier of the property.
     * @return Instance that contains the scope type of the property dataset.
     */
    private ScopeType getPropertyDatasetScope(String propertyName){
        if(propertyName != null && !propertyName.isEmpty()){
            StringBuilder key = new StringBuilder();
            
            key.append(propertyName);
            key.append(".");
            key.append(ActionFormConstants.DATASET_SCOPE_ATTRIBUTE_ID);
            
            try{
                String scopeType = this.systemController.getParameterValue(key.toString());
                
                if(scopeType != null)
                    return ScopeType.valueOf(scopeType.toUpperCase());
                else
                    return ActionFormConstants.DEFAULT_DATASET_SCOPE_TYPE;
            }
            catch(IllegalArgumentException e){
                return ActionFormConstants.DEFAULT_DATASET_SCOPE_TYPE;
            }
        }
        
        return null;
    }
    
    /**
     * Returns the start index of the property dataset.
     *
     * @param propertyName String that contains the identifier of the property.
     * @return Numeric value that contains the index.
     */
    public int getPropertyDatasetStartIndex(String propertyName){
        int propertyIndex = 0;

        if(propertyName != null && !propertyName.isEmpty()){
            StringBuilder key = new StringBuilder();
            
            key.append(propertyName);
            key.append(".");
            key.append(ActionFormConstants.DATASET_START_INDEX_ATTRIBUTE_ID);
            
            String value = this.systemController.getParameterValue(key.toString());

            if(value != null && !value.isEmpty()){
                try{
                    propertyIndex = NumberUtil.parseInt(value);
                }
                catch(ParseException ignored){
                }
            }
        }

        return propertyIndex;
    }
    
    /**
     * Returns the end index of the property dataset.
     *
     * @param propertyName String that contains the identifier of the property.
     * @return Numeric value that contains the index.
     */
    public int getPropertyDatasetEndIndex(String propertyName){
        int propertyIndex = 0;

        if(propertyName != null && !propertyName.isEmpty()){
            StringBuilder key = new StringBuilder();
            
            key.append(propertyName);
            key.append(".");
            key.append(ActionFormConstants.DATASET_END_INDEX_ATTRIBUTE_ID);
            
            String value = this.systemController.getParameterValue(key.toString());

            if(value != null && !value.isEmpty()){
                try{
                    propertyIndex = NumberUtil.parseInt(value);
                }
                catch(ParseException ignored){
                }
            }
        }

        return propertyIndex;
    }
    
    /**
     * Returns the pattern of the property.
     *
     * @param propertyName String that contains the identifier of the property.
     * @return String that contains the pattern.
     */
    public String getPropertyPattern(String propertyName){
        if(propertyName != null && !propertyName.isEmpty()){
            StringBuilder key = new StringBuilder();
            
            key.append(propertyName);
            key.append(".");
            key.append(Constants.PATTERN_ATTRIBUTE_ID);
            
            String encoding = this.systemController.getEncoding();
            String value = this.systemController.getParameterValue(key.toString());
            
            if(value != null && !value.isEmpty()){
                try{
                    value = URLDecoder.decode(value, encoding);
                }
                catch(UnsupportedEncodingException ignored){
                }
            }
            
            return value;
        }
        
        return null;
    }
    
    /**
     * Returns the label of the property.
     *
     * @param propertyName String that contains the identifier of the property.
     * @return String that contains the label.
     */
    public String getPropertyLabel(String propertyName){
        if(propertyName != null && !propertyName.isEmpty()){
            StringBuilder key = new StringBuilder();
            
            key.append(propertyName);
            key.append(".");
            key.append(Constants.LABEL_ATTRIBUTE_ID);
            
            String encoding = this.systemController.getEncoding();
            String value = this.systemController.getParameterValue(key.toString());
            
            if(value != null && !value.isEmpty()){
                try{
                    value = URLDecoder.decode(value, encoding);
                }
                catch(UnsupportedEncodingException ignored){
                }
            }
            
            if(value == null || value.isEmpty())
                value = propertyName;
            
            return value;
        }
        
        return null;
    }
    
    /**
     * Clear all messages.
     *
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    public void clearAllMessages() throws InternalErrorException{
        clearMessages(ActionFormMessageType.INFO);
        clearMessages(ActionFormMessageType.ERROR);
        clearMessages(ActionFormMessageType.WARNING);
        clearMessages(ActionFormMessageType.VALIDATION);
        
        if(this.systemController != null)
            this.systemController.setCurrentException(null);
    }
    
    /**
     * Clear the messages of a specific type.
     *
     * @param type Instance that contains the type of the message.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    public void clearMessages(ActionFormMessageType type) throws InternalErrorException{
        BaseActionForm<? extends BaseModel> actionFormInstance = getActionFormInstance();
        
        if(actionFormInstance != null)
            actionFormInstance.clearMessages(type);
    }
    
    /**
     * Returns a list of messages for a specific type.
     *
     * @param <C> Class that defines the list of messages.
     * @param type Instance that contains the type of the message.
     * @return Instance that contains the list of messages.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    public <C extends Collection<ActionFormMessage>> C getMessages(ActionFormMessageType type) throws InternalErrorException{
        if(type != null){
            BaseActionForm<? extends BaseModel> actionFormInstance = getActionFormInstance();
            
            if(actionFormInstance != null)
                return actionFormInstance.getMessages(type);
        }
        
        return null;
    }
    
    /**
     * Verifies if a property of a data model has validation messages.
     *
     * @param propertyName String that contains the identifier of the property.
     * @return True/False.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    public boolean hasValidationMessage(String propertyName) throws InternalErrorException{
        if(propertyName != null && !propertyName.isEmpty()){
            Collection<ActionFormMessage> validationMessages = getValidationMessages(propertyName);
            
            return (validationMessages != null && !validationMessages.isEmpty());
        }
        
        return false;
    }
    
    /**
     * Returns a list containing all validation messages.
     *
     * @param <C> Class that defines the list of messages.
     * @param propertyName String that contains the identifier of the property.
     * @return List containing all validation messages.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    @SuppressWarnings("unchecked")
    public <C extends Collection<ActionFormMessage>> C getValidationMessages(String propertyName) throws InternalErrorException{
        if(propertyName != null && !propertyName.isEmpty()){
            Collection<ActionFormMessage> validationMessages = getMessages(ActionFormMessageType.VALIDATION);
            
            if(validationMessages != null && !validationMessages.isEmpty()){
                Collection<ActionFormMessage> result = null;
                
                for(ActionFormMessage validationMessage: validationMessages){
                    String value = validationMessage.getAttribute(Constants.NAME_ATTRIBUTE_ID);
                    
                    if(value != null && value.equals(propertyName)){
                        if(result == null)
                            result = PropertyUtil.instantiate(Constants.DEFAULT_LIST_CLASS);

                        if(result != null)
                            result.add(validationMessage);
                    }
                }
                
                return (C) result;
            }
        }
        
        return null;
    }
    
    /**
     * Adds a message.
     *
     * @param actionFormMessage Instance that contains the message.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    public void addMessage(ActionFormMessage actionFormMessage) throws InternalErrorException{
        if(actionFormMessage != null){
            BaseActionForm<? extends BaseModel> actionFormInstance = getActionFormInstance();
            
            if(actionFormInstance != null)
                actionFormInstance.addMessage(actionFormMessage);
        }
    }
    
    /**
     * Adds a success information message.
     *
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    public void addSuccessMessage() throws InternalErrorException{
        addInfoMessage(ActionFormMessageConstants.DEFAULT_SUCCESS_ID);
    }
    
    /**
     * Adds an information message.
     *
     * @param key String that contains the identifier.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    public void addInfoMessage(String key) throws InternalErrorException{
        if(key != null && !key.isEmpty())
            addMessage(ActionFormMessageUtil.createInfoMessage(key));
    }
    
    /**
     * Adds a warning message.
     *
     * @param key String that contains the identifier.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    public void addWarningMessage(String key) throws InternalErrorException{
        if(key != null && !key.isEmpty())
            addMessage(ActionFormMessageUtil.createWarningMessage(key));
    }
    
    /**
     * Adds an error message.
     *
     * @param key String that contains the identifier.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    public void addErrorMessage(String key) throws InternalErrorException{
        if(key != null && !key.isEmpty())
            addMessage(ActionFormMessageUtil.createErrorMessage(key));
    }
    
    /**
     * Adds a message based on a caught exception.
     *
     * @param e Instance that contains the caught exception.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    public void addMessage(Throwable e) throws InternalErrorException{
        if(e != null)
            addMessage(ActionFormMessageUtil.createMessage(e));
    }
    
    /**
     * Adds a content type validation message.
     *
     * @param propertyName String that contains the identifier of the property.
     * @param propertyContentType Instance that contains the content type of the property.
     * @param permittedContentTypes Array that contains the permitted content types.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    public void addContentTypeValidationMessage(String propertyName, ContentType propertyContentType, ContentType[] permittedContentTypes) throws InternalErrorException{
        if(propertyName != null && !propertyName.isEmpty() && propertyContentType != null && permittedContentTypes != null && permittedContentTypes.length > 0)
            addContentTypeValidationMessage(propertyName, getPropertyLabel(propertyName), propertyContentType, permittedContentTypes);
    }
    
    /**
     * Adds a content type validation message.
     *
     * @param propertyName String that contains the identifier of the property.
     * @param propertyLabel String that contains the label of the property.
     * @param propertyContentType Instance that contains the content type of the property.
     * @param permittedContentTypes Array that contains the permitted content types.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    public void addContentTypeValidationMessage(String propertyName, String propertyLabel, ContentType propertyContentType, ContentType[] permittedContentTypes) throws InternalErrorException{
        if(propertyName != null && !propertyName.isEmpty() && propertyContentType != null && permittedContentTypes != null && permittedContentTypes.length > 0)
            addMessage(ActionFormValidationMessageUtil.createContentTypeValidationMessage(propertyName, propertyLabel, propertyContentType, permittedContentTypes));
    }
    
    /**
     * Adds a content size validation message.
     *
     * @param propertyName String that contains the identifier of the property.
     * @param propertyContentSize Numeric value that contains the size of the content.
     * @param propertyContentSizeUnit Instance that contains the content size unit.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    public void addContentSizeValidationMessage(String propertyName, Double propertyContentSize, ByteMetricType propertyContentSizeUnit) throws InternalErrorException{
        if(propertyName != null && !propertyName.isEmpty() && propertyContentSize != null && propertyContentSize > 0 && propertyContentSizeUnit != null)
            addContentSizeValidationMessage(propertyName, getPropertyLabel(propertyName), propertyContentSize, propertyContentSizeUnit);
    }
    
    /**
     * Adds a content size validation message.
     *
     * @param propertyName String that contains the identifier of the property.
     * @param propertyLabel String that contains the label of the property.
     * @param propertyContentSize Numeric value that contains the size of the content.
     * @param propertyContentSizeUnit Instance that contains the content size unit.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    public void addContentSizeValidationMessage(String propertyName, String propertyLabel, Double propertyContentSize, ByteMetricType propertyContentSizeUnit) throws InternalErrorException{
        if(propertyName != null && !propertyName.isEmpty() && propertyContentSize != null && propertyContentSize > 0 && propertyContentSizeUnit != null)
            addMessage(ActionFormValidationMessageUtil.createContentSizeValidationMessage(propertyName, propertyLabel, propertyContentSize, propertyContentSizeUnit));
    }
    
    /**
     * Adds a validation message for the required property input.
     *
     * @param propertyName String that contains the identifier of the property.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    public void addRequiredValidationMessage(String propertyName) throws InternalErrorException{
        if(propertyName != null && !propertyName.isEmpty())
            addRequiredValidationMessage(propertyName, getPropertyLabel(propertyName));
    }
    
    /**
     * Adds a validation message for the required property.
     *
     * @param propertyName String that contains the identifier of the property.
     * @param propertyLabel String that contains the label of the property.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    public void addRequiredValidationMessage(String propertyName, String propertyLabel) throws InternalErrorException{
        if(propertyName != null && !propertyName.isEmpty())
            addMessage(ActionFormValidationMessageUtil.createRequiredValidationMessage(propertyName, propertyLabel));
    }
    
    /**
     * Adds a date/time validation message for a data model property.
     *
     * @param propertyName String that contains the identifier of the property.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    public void addDateTimeValidationMessage(String propertyName) throws InternalErrorException{
        if(propertyName != null && !propertyName.isEmpty())
            addDateTimeValidationMessage(propertyName, getPropertyLabel(propertyName), getPropertyPattern(propertyName));
    }
    
    /**
     * Adds a date/time validation message for a data model property.
     *
     * @param propertyName String that contains the identifier of the property.
     * @param propertyLabel String that contains the label of the property.
     * @param propertyPattern String that contains the pattern.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    public void addDateTimeValidationMessage(String propertyName, String propertyLabel, String propertyPattern) throws InternalErrorException{
        if(propertyName != null && !propertyName.isEmpty())
            addMessage(ActionFormValidationMessageUtil.createDateTimeValidationMessage(propertyName, propertyLabel, propertyPattern));
    }
    
    /**
     * Adds a numeric validation message for a data model property.
     *
     * @param propertyName String that contains the identifier of the property.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    public void addNumberValidationMessage(String propertyName) throws InternalErrorException{
        if(propertyName != null && !propertyName.isEmpty())
            addNumberValidationMessage(propertyName, getPropertyLabel(propertyName));
    }
    
    /**
     * Adds a numeric validation message for a data model property.
     *
     * @param propertyName String that contains the identifier of the property.
     * @param propertyLabel String that contains the label of the property.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    public void addNumberValidationMessage(String propertyName, String propertyLabel) throws InternalErrorException{
        if(propertyName != null && !propertyName.isEmpty())
            addMessage(ActionFormValidationMessageUtil.createNumberValidationMessage(propertyName, propertyLabel));
    }
    
    /**
     * Adds a comparison validation message for a data model property.
     *
     * @param propertyName String that contains the identifier of the property.
     * @param propertyCompareCondition Instance that contains the comparison
     * type.
     * @param propertyCompareName String that contains the identifier of the
     * comparison property.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    public void addCompareValidationMessage(String propertyName, ConditionType propertyCompareCondition, String propertyCompareName) throws InternalErrorException{
        if(propertyName != null && !propertyName.isEmpty() && propertyCompareCondition != null && propertyCompareName != null && !propertyCompareName.isEmpty())
            addCompareValidationMessage(propertyName, getPropertyLabel(propertyName), propertyCompareCondition, propertyCompareName, getPropertyLabel(propertyName));
    }
    
    /**
     * Adds a comparison validation message for a data model property.
     *
     * @param propertyName String that contains the identifier of the property.
     * @param propertyLabel String that contains the label of the property.
     * @param propertyCompareCondition Instance that contains the comparison
     * type.
     * @param propertyCompareName String that contains the identifier of the
     * comparison property.
     * @param propertyCompareLabel String that contains the label of the
     * comparison property.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    public void addCompareValidationMessage(String propertyName, String propertyLabel, ConditionType propertyCompareCondition, String propertyCompareName, String propertyCompareLabel) throws InternalErrorException{
        if(propertyName != null && !propertyName.isEmpty() && propertyCompareCondition != null && propertyCompareName != null && !propertyCompareName.isEmpty())
            addMessage(ActionFormValidationMessageUtil.createCompareValidationMessage(propertyName, propertyLabel, propertyCompareCondition, propertyCompareName, propertyCompareLabel));
    }
    
    /**
     * Adds a word count validation message for a data model property.
     *
     * @param propertyName String that contains the identifier of the property.
     * @param propertyWordCount Numeric value that contains the word count.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    public void addWordCountValidationMessage(String propertyName, int propertyWordCount) throws InternalErrorException{
        if(propertyName != null && !propertyName.isEmpty() && propertyWordCount > 0)
            addWordCountValidationMessage(propertyName, getPropertyLabel(propertyName), propertyWordCount);
    }
    
    /**
     * Adds a word count validation message for a data model property.
     *
     * @param propertyName String that contains the identifier of the property.
     * @param propertyLabel String that contains the label of the property.
     * @param propertyWordCount Numeric value that contains the word count.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    public void addWordCountValidationMessage(String propertyName, String propertyLabel, int propertyWordCount) throws InternalErrorException{
        if(propertyName != null && !propertyName.isEmpty() && propertyWordCount > 0)
            addMessage(ActionFormValidationMessageUtil.createWordCountValidationMessage(propertyName, propertyLabel, propertyWordCount));
    }
    
    /**
     * Adds a minimum length validation message for a data model property.
     *
     * @param propertyName String that contains the identifier of the property.
     * @param propertyMinimumLength Numeric value that contains the minimum
     * length.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    public void addMinimumLengthValidationMessage(String propertyName, int propertyMinimumLength) throws InternalErrorException{
        if(propertyName != null && !propertyName.isEmpty() && propertyMinimumLength > 0)
            addMinimumLengthValidationMessage(propertyName, getPropertyLabel(propertyName), propertyMinimumLength);
    }
    
    /**
     * Adds a minimum length validation message for a data model property.
     *
     * @param propertyName String that contains the identifier of the property.
     * @param propertyLabel String that contains the label of the property.
     * @param propertyMinimumLength Numeric value that contains the minimum
     * length.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    public void addMinimumLengthValidationMessage(String propertyName, String propertyLabel, int propertyMinimumLength) throws InternalErrorException{
        if(propertyName != null && !propertyName.isEmpty() && propertyMinimumLength > 0)
            addMessage(ActionFormValidationMessageUtil.createMinimumLengthValidationMessage(propertyName, propertyLabel, propertyMinimumLength));
    }
    
    /**
     * Adds a maximum length validation message for a data model property.
     *
     * @param propertyName String that contains the identifier of the property.
     * @param propertyMaximumLength Numeric value that contains the maximum
     * length.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    public void addMaximumLengthValidationMessage(String propertyName, int propertyMaximumLength) throws InternalErrorException{
        if(propertyName != null && !propertyName.isEmpty() && propertyMaximumLength > 0)
            addMaximumLengthValidationMessage(propertyName, getPropertyLabel(propertyName), propertyMaximumLength);
    }
    
    /**
     * Adds a maximum length validation message for a data model property.
     *
     * @param propertyName String that contains the identifier of the property.
     * @param propertyLabel String that contains the label of the property.
     * @param propertyMaximumLength Numeric value that contains the maximum
     * length.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    public void addMaximumLengthValidationMessage(String propertyName, String propertyLabel, int propertyMaximumLength) throws InternalErrorException{
        if(propertyName != null && !propertyName.isEmpty() && propertyMaximumLength > 0)
            addMessage(ActionFormValidationMessageUtil.createMaximumLengthValidationMessage(propertyName, propertyLabel, propertyMaximumLength));
    }
    
    /**
     * Adds a regular expression validation message for a data model property.
     *
     * @param propertyName String that contains the identifier of the property.
     * @param propertyRegex String that contains the regular expression.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    public void addRegularExpressionValidationMessage(String propertyName, String propertyRegex) throws InternalErrorException{
        if(propertyName != null && !propertyName.isEmpty() && propertyRegex != null && !propertyRegex.isEmpty())
            addRegularExpressionValidationMessage(propertyName, getPropertyLabel(propertyName), propertyRegex);
    }
    
    /**
     * Adds a regular expression validation message for a data model property.
     *
     * @param propertyName String that contains the identifier of the property.
     * @param propertyLabel String that contains the label of the property.
     * @param propertyRegex String that contains the regular expression.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    public void addRegularExpressionValidationMessage(String propertyName, String propertyLabel, String propertyRegex) throws InternalErrorException{
        if(propertyName != null && !propertyName.isEmpty() && propertyRegex != null && !propertyRegex.isEmpty())
            addMessage(ActionFormValidationMessageUtil.createRegularExpressionValidationMessage(propertyName, propertyLabel, propertyRegex));
    }
    
    /**
     * Adds an e-Mail validation message for a data model property.
     *
     * @param propertyName String that contains the identifier of the property.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    public void addEmailValidationMessage(String propertyName) throws InternalErrorException{
        if(propertyName != null && !propertyName.isEmpty())
            addEmailValidationMessage(propertyName, getPropertyLabel(propertyName));
    }
    
    /**
     * Adds an e-Mail validation message for a data model property.
     *
     * @param propertyName String that contains the identifier of the property.
     * @param propertyLabel String that contains the label of the property.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    public void addEmailValidationMessage(String propertyName, String propertyLabel) throws InternalErrorException{
        if(propertyName != null && !propertyName.isEmpty())
            addMessage(ActionFormValidationMessageUtil.createEmailValidationMessage(propertyName, propertyLabel));
    }
    
    /**
     * Adds a pattern validation message for a data model property.
     *
     * @param propertyName String that contains the identifier of the property.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    public void addPatternValidationMessage(String propertyName) throws InternalErrorException{
        if(propertyName != null && !propertyName.isEmpty())
            addPatternValidationMessage(propertyName, getPropertyLabel(propertyName), getPropertyPattern(propertyName));
    }
    
    /**
     * Adds a pattern validation message for a data model property.
     *
     * @param propertyName String that contains the identifier of the property.
     * @param propertyLabel String that contains the label of the property.
     * @param propertyPattern String that contains the pattern of the property.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    public void addPatternValidationMessage(String propertyName, String propertyLabel, String propertyPattern) throws InternalErrorException{
        if(propertyName != null && !propertyName.isEmpty() && propertyPattern != null && !propertyPattern.isEmpty())
            addMessage(ActionFormValidationMessageUtil.createPatternValidationMessage(propertyName, propertyLabel, propertyPattern));
    }
    
    /**
     * Adds a numeric range validation message for a data model property.
     *
     * @param propertyName String that contains the identifier of the property.
     * @param propertyStartRange Numeric value that contains the start range.
     * @param propertyEndRange Numeric value that contains the end range.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    public void addRangeValidationMessage(String propertyName, Object propertyStartRange, Object propertyEndRange) throws InternalErrorException{
        if(propertyName != null && !propertyName.isEmpty() && propertyStartRange != null && propertyEndRange != null)
            addRangeValidationMessage(propertyName, getPropertyLabel(propertyName), propertyStartRange, propertyEndRange);
    }
    
    /**
     * Adds a numeric range validation message for a data model property.
     *
     * @param propertyName String that contains the identifier of the property.
     * @param propertyLabel String that contains the label of the property.
     * @param propertyStartRange Numeric value that contains the start range.
     * @param propertyEndRange Numeric value that contains the end range.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    public void addRangeValidationMessage(String propertyName, String propertyLabel, Object propertyStartRange, Object propertyEndRange) throws InternalErrorException{
        if(propertyName != null && !propertyName.isEmpty() && propertyStartRange != null && propertyEndRange != null)
            addMessage(ActionFormValidationMessageUtil.createRangeValidationMessage(propertyName, propertyLabel, propertyStartRange, propertyEndRange));
    }
    
    /**
     * Adds a custom validation message for a data model property.
     *
     * @param propertyValidation String that contains the identifier of the
     * validation.
     * @param propertyName String that contains the identifier of the property.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    public void addCustomValidationMessage(String propertyValidation, String propertyName) throws InternalErrorException{
        if(propertyValidation != null && !propertyValidation.isEmpty() && propertyName != null && !propertyName.isEmpty())
            addCustomValidationMessage(propertyValidation, propertyName, getPropertyLabel(propertyName));
    }
    
    /**
     * Adds a custom validation message for a data model property.
     *
     * @param propertyValidation String that contains the identifier of the
     * validation.
     * @param propertyName String that contains the identifier of the property.
     * @param propertyLabel String that contains the label of the property.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    public void addCustomValidationMessage(String propertyValidation, String propertyName, String propertyLabel) throws InternalErrorException{
        if(propertyValidation != null && !propertyValidation.isEmpty() && propertyName != null && !propertyName.isEmpty())
            addMessage(ActionFormValidationMessageUtil.createCustomValidationMessage(propertyValidation, propertyName, propertyLabel));
    }
    
    /**
     * Finds the forward of the action form.
     *
     * @return Instance that contains the forward of the action form.
     * @throws InternalErrorException Occurs when was not possible to execute
     * the operation.
     */
    public SystemResources.ActionFormResources.ActionFormForwardResources findForward() throws InternalErrorException{
        BaseActionForm<? extends BaseModel> actionForm = getActionFormInstance();
        
        if(actionForm != null){
            String actionFormForwardName = actionForm.getForward();
            
            if(actionFormForwardName == null || actionFormForwardName.isEmpty())
                actionFormForwardName = ActionFormConstants.DEFAULT_FORWARD_ID;
            
            SystemResourcesLoader loader = new SystemResourcesLoader();
            SystemResources systemResources = loader.getDefault();
            
            if(systemResources != null){
                Collection<SystemResources.ActionFormResources> actionFormsResources = systemResources.getActionForms();
                
                if(actionFormsResources != null && !actionFormsResources.isEmpty())
                    for(SystemResources.ActionFormResources actionFormResources: actionFormsResources)
                        if(actionFormResources.getClazz().equals(actionForm.getClass().getName()))
                            return actionFormResources.getForward(actionFormForwardName);
            }
        }
        
        return null;
    }
}