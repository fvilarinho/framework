package br.com.concepting.framework.controller.form.util;

import br.com.concepting.framework.controller.SystemController;
import br.com.concepting.framework.controller.action.types.ActionType;
import br.com.concepting.framework.controller.form.ActionFormController;
import br.com.concepting.framework.controller.form.BaseActionForm;
import br.com.concepting.framework.controller.form.helpers.ActionFormMessage;
import br.com.concepting.framework.controller.form.types.ActionFormMessageType;
import br.com.concepting.framework.controller.helpers.RequestParameterInfo;
import br.com.concepting.framework.exceptions.ExpectedException;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.helpers.ModelInfo;
import br.com.concepting.framework.model.types.ConditionType;
import br.com.concepting.framework.model.types.ValidationType;
import br.com.concepting.framework.model.util.ModelUtil;
import br.com.concepting.framework.security.controller.SecurityController;
import br.com.concepting.framework.security.exceptions.PermissionDeniedException;
import br.com.concepting.framework.security.model.LoginParameterModel;
import br.com.concepting.framework.security.model.LoginSessionModel;
import br.com.concepting.framework.security.model.UserModel;
import br.com.concepting.framework.util.*;
import br.com.concepting.framework.util.helpers.PropertyInfo;
import br.com.concepting.framework.util.types.ByteMetricType;
import br.com.concepting.framework.util.types.ContentType;
import br.com.concepting.framework.util.types.PropertyType;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class responsible to validate a data model.
 *
 * @author fvilarinho
 * @since 2.0.0
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
public class ActionFormValidator{
    private final SystemController systemController;
    private final ActionFormController actionFormController;
    private final SecurityController securityController;
    private final BaseActionForm<? extends BaseModel> actionForm;
    
    /**
     * Constructor - Initializes the validation.
     *
     * @param <F> Class that defines the form.
     * @param actionForm Instance that contains the form.
     * @param systemController Instance that contains the system controller.
     * @param actionFormController Instance that contains the action form controller.
     * @param securityController Instance that contains the security controller.
     */
    public <F extends BaseActionForm<? extends BaseModel>> ActionFormValidator(F actionForm, SystemController systemController, ActionFormController actionFormController, SecurityController securityController){
        super();
        
        this.actionForm = actionForm;
        this.systemController = systemController;
        this.actionFormController = actionFormController;
        this.securityController = securityController;
    }
    
    /**
     * Returns the instance that contains the current language.
     *
     * @return Instance that contains the current language.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    private Locale getCurrentLanguage() throws InternalErrorException{
        if(this.securityController != null){
            LoginSessionModel loginSession = this.securityController.getLoginSession();
            UserModel user = (loginSession != null ? loginSession.getUser() : null);
            LoginParameterModel loginParameter = (user != null ? user.getLoginParameter() : null);
            
            if(loginParameter != null && loginParameter.getLanguage() != null && !loginParameter.getLanguage().isEmpty())
                return LanguageUtil.getLanguageByString(loginParameter.getLanguage());
        }
        
        return LanguageUtil.getDefaultLanguage();
    }
    
    /**
     * Validates the data model.
     *
     * @return True/False.
     * @throws ExpectedException Occurs when an expected exception was caught.
     * @throws InternalErrorException Occurs when an internal error exception was caught.
     */
    public boolean validateModel() throws ExpectedException, InternalErrorException{
        boolean validateModel = this.actionForm.validateModel();
        
        if(!validateModel)
            return true;
        
        Map<String, RequestParameterInfo> requestParameters = this.systemController.getParameters();
        
        if(requestParameters != null && !requestParameters.isEmpty()){
            for(Entry<String, RequestParameterInfo> entry: requestParameters.entrySet()){
                RequestParameterInfo requestParameterInfo = entry.getValue();
                
                if(requestParameterInfo.getType() == PropertyType.MODEL){
                    if(ActionFormUtil.isDatasetProperty(entry.getKey()))
                        validateDatasetProperty(requestParameterInfo);
                    else
                        validateProperty(requestParameterInfo);
                }
            }
        }
        
        Collection<ActionFormMessage> actionFormMessages = this.actionFormController.getMessages(ActionFormMessageType.VALIDATION);
        
        if(actionFormMessages == null || actionFormMessages.isEmpty())
            actionFormMessages = this.actionFormController.getMessages(ActionFormMessageType.WARNING);
        
        if(actionFormMessages == null || actionFormMessages.isEmpty())
            actionFormMessages = this.actionFormController.getMessages(ActionFormMessageType.ERROR);
    
        return actionFormMessages == null || actionFormMessages.isEmpty();
    }
    
    /**
     * Validate the data model property.
     *
     * @param requestParameterInfo Instance that contains the request parameter.
     * @throws ExpectedException Occurs when an expected exception was caught.
     * @throws InternalErrorException Occurs when an internal error exception was caught.
     */
    private void validateProperty(RequestParameterInfo requestParameterInfo) throws ExpectedException, InternalErrorException{
        if(requestParameterInfo == null)
            return;
        
        try{
            BaseModel model = this.actionForm.getModel();
            Class<? extends BaseModel> modelClass = model.getClass();
            ModelInfo modelInfo = ModelUtil.getInfo(modelClass);
            String propertyName = requestParameterInfo.getName();
            boolean mainPropertyIsForSearch = false;
            List<String> mainValidationActions = null;
            int pos = propertyName.indexOf(".");
            
            if(pos >= 0){
                PropertyInfo mainPropertyInfo = modelInfo.getPropertyInfo(propertyName.substring(0, pos));
                
                if(mainPropertyInfo != null){
                    mainPropertyIsForSearch = mainPropertyInfo.isForSearch();

                    String[] mainValidationActionsBuffer = mainPropertyInfo.getValidationActions();
                    
                    if(mainValidationActionsBuffer != null && mainValidationActionsBuffer.length > 0)
                        mainValidationActions = Arrays.asList(mainValidationActionsBuffer);
                }
            }
            
            String[] validateModelPropertiesBuffer = StringUtil.split(this.actionForm.getValidateModelProperties());
            Collection<String> validateModelProperties = (validateModelPropertiesBuffer != null && validateModelPropertiesBuffer.length > 0 ? Arrays.asList(validateModelPropertiesBuffer) : null);
            
            if(validateModelProperties == null || validateModelProperties.contains(propertyName)){
                PropertyInfo propertyInfo = modelInfo.getPropertyInfo(propertyName);
                ValidationType[] validations = propertyInfo.getValidations();

                if(validations != null && validations.length > 0){
                    List<String> validationActions;
                    String[] validationActionsBuffer = propertyInfo.getValidationActions();
                    
                    if(validationActionsBuffer != null && validationActionsBuffer.length > 0)
                        validationActions = Arrays.asList(validationActionsBuffer);
                    else
                        validationActions = mainValidationActions;
                    
                    if(mainPropertyIsForSearch && validationActions == null && this.actionForm.getAction().equals(ActionType.SEARCH.getMethod()))
                        validateProperty(requestParameterInfo, propertyInfo);
                    else if(!mainPropertyIsForSearch && validationActions == null)
                        validateProperty(requestParameterInfo, propertyInfo);
                    else if(validationActions != null && this.actionForm.getAction() != null && validationActions.contains(this.actionForm.getAction()))
                        validateProperty(requestParameterInfo, propertyInfo);
                }
            }
        }
        catch(NoSuchFieldException | NoSuchMethodException ignored){
        }
        catch(IllegalArgumentException | IllegalAccessException | InvocationTargetException | InstantiationException | ClassNotFoundException e){
            throw new InternalErrorException(e);
        }
    }
    
    /**
     * Validate a data value property.
     *
     * @param requestParameterInfo Instance that contains the request parameter.
     * @throws ExpectedException Occurs when an expected exception was caught.
     * @throws InternalErrorException Occurs when an internal error exception was caught.
     */
    @SuppressWarnings("unchecked")
    private void validateDatasetProperty(RequestParameterInfo requestParameterInfo) throws ExpectedException, InternalErrorException{
        if(requestParameterInfo == null)
            return;
        
        String propertyName = requestParameterInfo.getName();
        String[] datasetPropertyBuffer = StringUtil.split(propertyName, ":");
        
        if(datasetPropertyBuffer != null && datasetPropertyBuffer.length > 0){
            BaseModel model = this.actionForm.getModel();
            Class<? extends BaseModel> modelClass = model.getClass();
            
            try{
                ModelInfo modelInfo = ModelUtil.getInfo(modelClass);
                String datasetPropertyName = datasetPropertyBuffer[0];
                PropertyInfo datasetPropertyInfo = modelInfo.getPropertyInfo(datasetPropertyName);
                
                if(!datasetPropertyInfo.isModel() && !datasetPropertyInfo.hasModel()){
                    int pos = datasetPropertyName.lastIndexOf(".");
                    
                    if(pos >= 0){
                        datasetPropertyName = datasetPropertyName.substring(0, pos);
                        datasetPropertyInfo = modelInfo.getPropertyInfo(datasetPropertyName);
                    }
                }
                
                if(datasetPropertyInfo.isModel() || datasetPropertyInfo.hasModel()){
                    Class<? extends BaseModel> datasetModelClass = (datasetPropertyInfo.isModel() ? (Class<? extends BaseModel>) datasetPropertyInfo.getClazz() : (Class<? extends BaseModel>) datasetPropertyInfo.getCollectionItemsClass());
                    ModelInfo datasetModelInfo = ModelUtil.getInfo(datasetModelClass);
                    
                    datasetPropertyInfo = datasetModelInfo.getPropertyInfo(datasetPropertyBuffer[1]);

                    validateProperty(requestParameterInfo, datasetPropertyInfo);
                }
            }
            catch(IllegalArgumentException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException | ClassNotFoundException | NoSuchFieldException e){
                throw new InternalErrorException(e);
            }
        }
    }
    
    /**
     * Validate the data model property.
     *
     * @param requestParameterInfo Instance that contains the request parameter.
     * @param propertyInfo Instance that contains the property info.
     * @throws ExpectedException Occurs when an expected exception was caught.
     * @throws InternalErrorException Occurs when an internal error exception was caught.
     */
    private void validateProperty(RequestParameterInfo requestParameterInfo, PropertyInfo propertyInfo) throws ExpectedException, InternalErrorException{
        if(requestParameterInfo == null || propertyInfo == null)
            return;
        
        ValidationType[] propertyValidations = propertyInfo.getValidations();
        
        if(propertyValidations != null && propertyValidations.length > 0){
            String propertyName = requestParameterInfo.getName();
            
            for(ValidationType propertyValidation: propertyValidations){
                switch(propertyValidation){
                    case REQUIRED:{
                        String propertyValue = requestParameterInfo.getValue();
                        
                        if(propertyInfo.isNumber()){
                            Class<?> propertyClass = propertyInfo.getClazz();
                            boolean useGroupSeparator = propertyInfo.useGroupSeparator();
                            int precision = propertyInfo.getPrecision();
                            
                            processNumberRequiredValidation(propertyClass, propertyName, propertyValue, useGroupSeparator, precision);
                        }
                        else
                            processGeneralRequiredValidation(propertyName, propertyValue);
                        
                        break;
                    }
                    case DATE_TIME:{
                        String propertyValue = requestParameterInfo.getValue();
                        String propertyPattern = propertyInfo.getPattern();
                        
                        processDateTimeValidation(propertyName, propertyValue, propertyPattern);
                        
                        break;
                    }
                    case NUMBER:{
                        Class<?> propertyClass = propertyInfo.getClazz();
                        String propertyValue = requestParameterInfo.getValue();
                        boolean useGroupSeparator = propertyInfo.useGroupSeparator();
                        int precision = propertyInfo.getPrecision();
                        
                        processNumberValidation(propertyClass, propertyName, propertyValue, useGroupSeparator, precision);
                        
                        break;
                    }
                    case WORD_COUNT:{
                        String propertyValue = requestParameterInfo.getValue();
                        int propertyWordCount = propertyInfo.getWordCount();
                        
                        processWordCountValidation(propertyName, propertyValue, propertyWordCount);
                        
                        break;
                    }
                    case MINIMUM_LENGTH:{
                        String propertyValue = requestParameterInfo.getValue();
                        int propertyMinimumLength = propertyInfo.getMinimumLength();
                        
                        processMinimumLengthValidation(propertyName, propertyValue, propertyMinimumLength);
                        
                        break;
                    }
                    case MAXIMUM_LENGTH:{
                        String propertyValue = requestParameterInfo.getValue();
                        int propertyMaximumLength = propertyInfo.getMaximumLength();
                        
                        processMaximumLengthValidation(propertyName, propertyValue, propertyMaximumLength);
                        
                        break;
                    }
                    case COMPARE:{
                        String propertyValue = requestParameterInfo.getValue();
                        String comparePropertyId = propertyInfo.getComparePropertyId();
                        ConditionType comparePropertyCondition = propertyInfo.getCompareCondition();
                        
                        if(comparePropertyCondition == ConditionType.NONE)
                            comparePropertyCondition = ConditionType.EQUAL;
                        
                        if(propertyInfo.isDate()){
                            String propertyPattern = propertyInfo.getPattern();
                            
                            processDateTimeCompareValidation(propertyName, propertyValue, propertyPattern, comparePropertyId, comparePropertyCondition);
                        }
                        else{
                            Class<?> propertyClass = propertyInfo.getClazz();
                            boolean useGroupSeparator = propertyInfo.useGroupSeparator();
                            int precision = propertyInfo.getPrecision();
                            
                            processNumberCompareValidation(propertyClass, propertyName, propertyValue, useGroupSeparator, precision, comparePropertyId, comparePropertyCondition);
                        }
                        
                        break;
                    }
                    case RANGE:{
                        String propertyValue = requestParameterInfo.getValue();
                        String propertyMinimumValue = propertyInfo.getMinimumValue();
                        String propertyMaximumValue = propertyInfo.getMaximumValue();
                        
                        if(propertyInfo.isNumber()){
                            Class<?> propertyClass = propertyInfo.getClazz();
                            boolean useGroupSeparator = propertyInfo.useGroupSeparator();
                            int precision = propertyInfo.getPrecision();
                            
                            processNumberRangeValidation(propertyClass, propertyName, propertyValue, useGroupSeparator, precision, propertyMinimumValue, propertyMaximumValue);
                        }
                        else if(propertyInfo.isDate()){
                            String propertyPattern = propertyInfo.getPattern();
                            
                            processDateTimeRangeValidation(propertyName, propertyValue, propertyPattern, propertyMinimumValue, propertyMaximumValue);
                        }
                        
                        break;
                    }
                    case EMAIL:{
                        String propertyValue = requestParameterInfo.getValue();
                        
                        processEmailValidation(propertyName, propertyValue);
                        
                        break;
                    }
                    case PATTERN:{
                        String propertyValue = requestParameterInfo.getValue();
                        String propertyPattern = propertyInfo.getPattern();
                        
                        processPatternValidation(propertyName, propertyValue, propertyPattern);
                        
                        break;
                    }
                    case REGULAR_EXPRESSION:{
                        String propertyValue = requestParameterInfo.getValue();
                        String propertyRegex = propertyInfo.getRegularExpression();
                        
                        processRegularExpressionValidation(propertyName, propertyValue, propertyRegex);
                        
                        break;
                    }
                    case CONTENT_TYPE:{
                        ContentType propertyContentType = requestParameterInfo.getContentType();
                        ContentType[] propertyContentTypes = propertyInfo.getContentTypes();
                        
                        processContentTypeValidation(propertyName, propertyContentType, propertyContentTypes);

                        break;
                    }
                    case CONTENT_SIZE:{
                        ByteMetricType propertyContentSizeUnit = propertyInfo.getContentSizeUnit();
                        double propertyContentSize = (requestParameterInfo.getContent() != null ? requestParameterInfo.getContent().length * 1d : 0d);
                        double permittedContentSize = propertyInfo.getContentSize() * propertyContentSizeUnit.getValue();
                        
                        processContentSizeValidation(propertyName, propertyContentSize, permittedContentSize, propertyContentSizeUnit);
                        
                        break;
                    }
                    case CUSTOM:{
                        String propertyValue = requestParameterInfo.getValue();
                        String propertyCustomValidationId = propertyInfo.getCustomValidationId();
                        
                        processCustomValidation(propertyName, propertyValue, propertyCustomValidationId);
                        
                        break;
                    }
                    default:{
                    }
                }
            }
        }
    }
    
    /**
     * Process the content type validation.
     *
     * @param propertyName String that contains the identifier of the property.
     * @param propertyContentType Instance that contains the content type of the property.
     * @param permittedContentTypes Array that contains the permitted content types.
     * @throws ExpectedException Occurs when an expected exception was caught.
     * @throws InternalErrorException Occurs when an internal error exception was caught.
     */
    protected void processContentTypeValidation(String propertyName, ContentType propertyContentType, ContentType[] permittedContentTypes) throws ExpectedException, InternalErrorException{
        if(propertyName != null && !propertyName.isEmpty() && propertyContentType != null && permittedContentTypes != null && permittedContentTypes.length > 0){
            boolean found = false;
            
            for(ContentType item: permittedContentTypes){
                if(item.equals(propertyContentType)){
                    found = true;
                    
                    break;
                }
            }
            
            if(!found){
                String propertyLabel = this.actionFormController.getPropertyLabel(propertyName);
                
                this.actionFormController.addContentTypeValidationMessage(propertyName, propertyLabel, propertyContentType, permittedContentTypes);
            }
        }
    }
    
    /**
     * Process the content size validation.
     *
     * @param propertyName String that contains the identifier of the property.
     * @param propertyContentSize String that contains the content size.
     * @param permittedContentSize Numeric value that contains the permitted content sizes.
     * @param propertyContentSizeUnit Instance that contains the content size unit.
     * @throws ExpectedException Occurs when an expected exception was caught.
     * @throws InternalErrorException Occurs when an internal error exception was caught.
     */
    protected void processContentSizeValidation(String propertyName, double propertyContentSize, double permittedContentSize, ByteMetricType propertyContentSizeUnit) throws ExpectedException, InternalErrorException{
        if(propertyName != null && !propertyName.isEmpty() && permittedContentSize > 0 && propertyContentSize > permittedContentSize){
            String propertyLabel = this.actionFormController.getPropertyLabel(propertyName);
            
            this.actionFormController.addContentSizeValidationMessage(propertyName, propertyLabel, permittedContentSize, propertyContentSizeUnit);
        }
    }
    
    /**
     * Process a customized validation.
     *
     * @param propertyName String that contains the identifier of the property.
     * @param propertyValue String that contains the property value.
     * @param propertyCustomValidationId String that contains the identifier of the validation.
     * @throws ExpectedException Occurs when an expected exception was caught.
     * @throws InternalErrorException Occurs when an internal error exception was caught.
     */
    protected void processCustomValidation(String propertyName, String propertyValue, String propertyCustomValidationId) throws ExpectedException, InternalErrorException{
        if(propertyName != null && !propertyName.isEmpty() && propertyValue != null && !propertyValue.isEmpty() && propertyCustomValidationId != null && !propertyCustomValidationId.isEmpty()){
            Method[] methods = getClass().getMethods();

            for (Method method : methods) {
                if (method.getName().equals(propertyCustomValidationId)) {
                    try {
                        String propertyLabel = this.actionFormController.getPropertyLabel(propertyName);
                        boolean result = (boolean) method.invoke(this, propertyName, propertyValue);

                        if (!result)
                            this.actionFormController.addCustomValidationMessage(propertyCustomValidationId, propertyName, propertyLabel);

                        break;
                    } catch (InvocationTargetException e) {
                        Throwable exception = ExceptionUtil.getCause(e);

                        if (ExceptionUtil.isInternalErrorException(exception))
                            throw (InternalErrorException) exception;
                        else if (ExceptionUtil.isExpectedException(exception))
                            this.actionFormController.addMessage(exception);
                        else
                            throw new InternalErrorException(e);
                    } catch (IllegalAccessException | IllegalArgumentException e) {
                        throw new PermissionDeniedException(e);
                    }
                }
            }
        }
    }
    
    /**
     * Process a general required validation.
     *
     * @param propertyName String that contains the identifier of the property.
     * @param propertyValue String that contains the property value.
     * @throws ExpectedException Occurs when an expected exception was caught.
     * @throws InternalErrorException Occurs when an internal error exception was caught.
     */
    protected void processGeneralRequiredValidation(String propertyName, String propertyValue) throws ExpectedException, InternalErrorException{
        if(propertyValue == null || propertyValue.isEmpty()){
            String propertyLabel = this.actionFormController.getPropertyLabel(propertyName);
            
            this.actionFormController.addRequiredValidationMessage(propertyName, propertyLabel);
        }
    }
    
    /**
     * Process a number required validation.
     *
     * @param propertyClass Class that defines the number.
     * @param propertyName String that contains the identifier of the property.
     * @param propertyValue String that contains the property value.
     * @param useGroupSeparator True/False.
     * @param precision Numeric value that contains the precision of the property.
     * @throws ExpectedException Occurs when an expected exception was caught.
     * @throws InternalErrorException Occurs when an internal error exception was caught.
     */
    protected void processNumberRequiredValidation(Class<?> propertyClass, String propertyName, String propertyValue, boolean useGroupSeparator, int precision) throws ExpectedException, InternalErrorException{
        boolean result = true;
        
        try{
            Number propertyNumber = NumberUtil.parse(propertyClass, propertyValue, useGroupSeparator, precision, getCurrentLanguage());
            
            if(propertyNumber == null || propertyNumber.doubleValue() == 0d)
                result = false;
        }
        catch(ParseException e){
            result = false;
        }
        
        if(!result){
            String propertyLabel = this.actionFormController.getPropertyLabel(propertyName);
            
            this.actionFormController.addRequiredValidationMessage(propertyName, propertyLabel);
        }
    }
    
    /**
     * Process a date/time validation.
     *
     * @param propertyName String that contains the identifier of the property.
     * @param propertyValue String that contains the property value.
     * @param propertyPattern String that contains the property pattern.
     * @throws ExpectedException Occurs when an expected exception was caught.
     * @throws InternalErrorException Occurs when an internal error exception was caught.
     */
    protected void processDateTimeValidation(String propertyName, String propertyValue, String propertyPattern) throws ExpectedException, InternalErrorException{
        if(propertyName != null && !propertyName.isEmpty() && propertyValue != null && !propertyValue.isEmpty()){
            String propertyPatternBuffer = this.actionFormController.getPropertyPattern(propertyName);
            
            if(propertyPatternBuffer != null && !propertyPatternBuffer.isEmpty())
                propertyPattern = propertyPatternBuffer;
            
            try{
                if(propertyPattern != null && !propertyPattern.isEmpty())
                    DateTimeUtil.parse(propertyValue, propertyPattern);
                else
                    DateTimeUtil.parse(propertyValue, getCurrentLanguage());
            }
            catch(ParseException e){
                String propertyLabel = this.actionFormController.getPropertyLabel(propertyName);
                
                this.actionFormController.addDateTimeValidationMessage(propertyName, propertyLabel, propertyPattern);
            }
        }
    }
    
    /**
     * Process the number validation.
     *
     * @param propertyClass Class that defines the number.
     * @param propertyName String that contains the identifier of the property.
     * @param propertyValue String that contains the value of the property.
     * @param useGroupSeparator True/False.
     * @param precision Numeric value that contains the precision of the property.
     * @throws ExpectedException Occurs when an expected exception was caught.
     * @throws InternalErrorException Occurs when an internal error exception was caught.
     */
    protected void processNumberValidation(Class<?> propertyClass, String propertyName, String propertyValue, boolean useGroupSeparator, int precision) throws ExpectedException, InternalErrorException{
        if(propertyClass != null && propertyName != null && !propertyName.isEmpty() && propertyValue != null && !propertyValue.isEmpty()){
            try{
                NumberUtil.parse(propertyClass, propertyValue, useGroupSeparator, precision, getCurrentLanguage());
            }
            catch(ParseException e){
                String propertyLabel = this.actionFormController.getPropertyLabel(propertyName);
                
                this.actionFormController.addNumberValidationMessage(propertyName, propertyLabel);
            }
        }
    }
    
    /**
     * Process the compare date/time validation.
     *
     * @param propertyName String that contains the identifier of the property.
     * @param propertyValue String that contains the value of the property.
     * @param propertyPattern String that contains the pattern of the property.
     * @param comparePropertyId String that contains the identifier of the property that will be compared.
     * @param comparePropertyCondition Instance that contains the condition of comparison.
     * @throws ExpectedException Occurs when an expected exception was caught.
     * @throws InternalErrorException Occurs when an internal error exception was caught.
     */
    protected void processDateTimeCompareValidation(String propertyName, String propertyValue, String propertyPattern, String comparePropertyId, ConditionType comparePropertyCondition) throws ExpectedException, InternalErrorException{
        if(propertyName != null && !propertyName.isEmpty() && propertyValue != null && !propertyValue.isEmpty() && comparePropertyId != null && !comparePropertyId.isEmpty() && comparePropertyCondition != null){
            StringBuilder comparePropertyIdBuffer = new StringBuilder();
            int pos = propertyName.lastIndexOf(".");
            
            if(pos > 0){
                comparePropertyIdBuffer.append(propertyName, 0, pos);
                comparePropertyIdBuffer.append(".");
            }
            
            comparePropertyIdBuffer.append(comparePropertyId);
            
            comparePropertyId = comparePropertyIdBuffer.toString();
            
            boolean result;
            Locale currentLanguage = getCurrentLanguage();
            String comparePropertyValue = this.systemController.getParameterValue(comparePropertyIdBuffer.toString());
            String propertyPatternBuffer = this.actionFormController.getPropertyPattern(propertyName);
            
            if(propertyPatternBuffer != null && !propertyPatternBuffer.isEmpty())
                propertyPattern = propertyPatternBuffer;
            
            try{
                Date propertyDateTime;
                Date comparePropertyDateTime;
                
                if(propertyPattern != null && !propertyPattern.isEmpty()){
                    propertyDateTime = DateTimeUtil.parse(propertyValue, propertyPattern);
                    comparePropertyDateTime = DateTimeUtil.parse(comparePropertyValue, propertyPattern);
                }
                else{
                    propertyDateTime = DateTimeUtil.parse(propertyValue, currentLanguage);
                    comparePropertyDateTime = DateTimeUtil.parse(comparePropertyValue, currentLanguage);
                }
                
                int compareFlag = (propertyDateTime.compareTo(comparePropertyDateTime));
                
                switch(comparePropertyCondition){
                    case GREATER_THAN:{
                        result = (compareFlag > 0);
                        
                        break;
                    }
                    case LESS_THAN_EQUAL:{
                        result = (compareFlag <= 0);
                        
                        break;
                    }
                    case LESS_THAN:{
                        result = (compareFlag < 0);
                        
                        break;
                    }
                    default:{
                        result = (compareFlag >= 0);
                        
                        break;
                    }
                }
            }
            catch(ParseException e){
                result = true;
            }
            
            if(!result){
                String propertyLabel = this.actionFormController.getPropertyLabel(propertyName);
                String comparePropertyLabel = this.actionFormController.getPropertyLabel(comparePropertyIdBuffer.toString());
                
                this.actionFormController.addCompareValidationMessage(propertyName, propertyLabel, comparePropertyCondition, comparePropertyId, comparePropertyLabel);
            }
        }
    }
    
    /**
     * Process the compare numbers validation.
     *
     * @param propertyClass Class that defines the number.
     * @param propertyName String that contains the identifier of the property.
     * @param propertyValue String that contains the value of the property.
     * @param useGroupSeparator True/False.
     * @param precision Numeric value that contains the precision of the property.
     * @param comparePropertyId String that contains the identifier of the property that will be compared.
     * @param comparePropertyCondition Instance that contains the condition of comparison.
     * @throws ExpectedException Occurs when an expected exception was caught.
     * @throws InternalErrorException Occurs when an internal error exception was caught.
     */
    protected void processNumberCompareValidation(Class<?> propertyClass, String propertyName, String propertyValue, boolean useGroupSeparator, int precision, String comparePropertyId, ConditionType comparePropertyCondition) throws ExpectedException, InternalErrorException{
        if(propertyName != null && !propertyName.isEmpty() && propertyValue != null && !propertyValue.isEmpty() && comparePropertyId != null && !comparePropertyId.isEmpty() && comparePropertyCondition != null){
            StringBuilder comparePropertyIdBuffer = new StringBuilder();
            int pos = propertyName.lastIndexOf(".");
            
            if(pos > 0){
                comparePropertyIdBuffer.append(propertyName, 0, pos);
                comparePropertyIdBuffer.append(".");
            }
            
            comparePropertyIdBuffer.append(comparePropertyId);
            
            comparePropertyId = comparePropertyIdBuffer.toString();
            
            boolean result;
            Locale currentLanguage = getCurrentLanguage();
            String comparePropertyValue = this.systemController.getParameterValue(comparePropertyId);
            
            try{
                Number propertyNumber = NumberUtil.parse(propertyClass, propertyValue, useGroupSeparator, precision, currentLanguage);
                Number comparePropertyNumber = NumberUtil.parse(propertyClass, comparePropertyValue, useGroupSeparator, precision, currentLanguage);
                int compareFlag = PropertyUtil.compareTo(propertyNumber, comparePropertyNumber);
                
                switch(comparePropertyCondition){
                    case GREATER_THAN:{
                        result = (compareFlag > 0);
                        
                        break;
                    }
                    case LESS_THAN_EQUAL:{
                        result = (compareFlag <= 0);
                        
                        break;
                    }
                    case LESS_THAN:{
                        result = (compareFlag < 0);
                        
                        break;
                    }
                    default:{
                        result = (compareFlag >= 0);
                        
                        break;
                    }
                }
            }
            catch(ParseException e){
                result = true;
            }
            
            if(!result){
                String propertyLabel = this.actionFormController.getPropertyLabel(propertyName);
                String comparePropertyLabel = this.actionFormController.getPropertyLabel(comparePropertyId);
                
                this.actionFormController.addCompareValidationMessage(propertyName, propertyLabel, comparePropertyCondition, comparePropertyId, comparePropertyLabel);
            }
        }
    }
    
    /**
     * Process the word count validation.
     *
     * @param propertyName String that contains the identifier of the property.
     * @param propertyValue String that contains the value of the property.
     * @param propertyWordCount Numeric value that contains the word count of the property.
     * @throws ExpectedException Occurs when an expected exception was caught.
     * @throws InternalErrorException Occurs when an internal error exception was caught.
     */
    protected void processWordCountValidation(String propertyName, String propertyValue, int propertyWordCount) throws ExpectedException, InternalErrorException{
        if(propertyName != null && !propertyName.isEmpty() && propertyValue != null && !propertyValue.isEmpty() && propertyWordCount > 0){
            String[] words = StringUtil.split(propertyValue, " ");
            
            if(words.length < propertyWordCount){
                String propertyLabel = this.actionFormController.getPropertyLabel(propertyName);
                
                this.actionFormController.addWordCountValidationMessage(propertyName, propertyLabel, propertyWordCount);
            }
        }
    }
    
    /**
     * Process the minimum length validation.
     *
     * @param propertyName String that contains the identifier of the property.
     * @param propertyValue String that contains the value of the property.
     * @param propertyMinimumLength Numeric value that contains the minimum length of the property.
     * @throws ExpectedException Occurs when an expected exception was caught.
     * @throws InternalErrorException Occurs when an internal error exception was caught.
     */
    protected void processMinimumLengthValidation(String propertyName, String propertyValue, int propertyMinimumLength) throws ExpectedException, InternalErrorException{
        if(propertyName != null && !propertyName.isEmpty() && propertyValue != null && !propertyValue.isEmpty() && propertyValue.length() < propertyMinimumLength){
            String propertyLabel = this.actionFormController.getPropertyLabel(propertyName);
            
            this.actionFormController.addMinimumLengthValidationMessage(propertyName, propertyLabel, propertyMinimumLength);
        }
    }
    
    /**
     * Process the maximum length validation.
     *
     * @param propertyName String that contains the identifier of the property.
     * @param propertyValue String that contains the value of the property.
     * @param propertyMaximumLength Numeric value that contains the maximum length of the property.
     * @throws ExpectedException Occurs when an expected exception was caught.
     * @throws InternalErrorException Occurs when an internal error exception was caught.
     */
    protected void processMaximumLengthValidation(String propertyName, String propertyValue, int propertyMaximumLength) throws ExpectedException, InternalErrorException{
        if(propertyName != null && !propertyName.isEmpty() && propertyValue != null && !propertyValue.isEmpty() && propertyValue.length() > propertyMaximumLength){
            String propertyLabel = this.actionFormController.getPropertyLabel(propertyName);
            
            this.actionFormController.addMaximumLengthValidationMessage(propertyName, propertyLabel, propertyMaximumLength);
        }
    }
    
    /**
     * Process the date/time range validation.
     *
     * @param propertyName String that contains the identifier of the property.
     * @param propertyValue String that contains the value of the property.
     * @param propertyPattern String that contains the pattern of the property.
     * @param propertyMinimumValue Numeric value that contains the minimum value of the property.
     * @param propertyMaximumValue Numeric value that contains the maximum value of the property.
     * @throws ExpectedException Occurs when an expected exception was caught.
     * @throws InternalErrorException Occurs when an internal error exception was caught.
     */
    protected void processDateTimeRangeValidation(String propertyName, String propertyValue, String propertyPattern, String propertyMinimumValue, String propertyMaximumValue) throws ExpectedException, InternalErrorException{
        if(propertyName != null && !propertyName.isEmpty() && propertyValue != null && !propertyValue.isEmpty() && propertyMinimumValue != null && !propertyMinimumValue.isEmpty() && propertyMaximumValue != null && !propertyMaximumValue.isEmpty()){
            Locale currentLanguage = getCurrentLanguage();
            String propertyPatternBuffer = this.actionFormController.getPropertyPattern(propertyName);
            Date propertyStartDateTime = null;
            Date propertyEndDateTime = null;
            Date propertyDateTimeValue = null;
            
            if(propertyPatternBuffer != null && !propertyPatternBuffer.isEmpty())
                propertyPattern = propertyPatternBuffer;
            
            try{
                if(propertyPattern != null && !propertyPattern.isEmpty())
                    propertyDateTimeValue = DateTimeUtil.parse(propertyValue, propertyPattern);
                else
                    propertyDateTimeValue = DateTimeUtil.parse(propertyValue, currentLanguage);
            }
            catch(ParseException ignored){
            }
            
            try{
                if(propertyPattern != null && !propertyPattern.isEmpty())
                    propertyStartDateTime = DateTimeUtil.parse(propertyMinimumValue, propertyPattern);
                else
                    propertyStartDateTime = DateTimeUtil.parse(propertyMinimumValue, currentLanguage);
            }
            catch(ParseException ignored){
            }
            
            try{
                if(propertyPattern != null && !propertyPattern.isEmpty())
                    propertyEndDateTime = DateTimeUtil.parse(propertyMaximumValue, propertyPattern);
                else
                    propertyEndDateTime = DateTimeUtil.parse(propertyMaximumValue, currentLanguage);
            }
            catch(ParseException ignored){
            }
            
            if(propertyDateTimeValue != null && propertyStartDateTime != null && propertyEndDateTime != null){
                String propertyLabel = this.actionFormController.getPropertyLabel(propertyName);
                int compareFlag = propertyStartDateTime.compareTo(propertyDateTimeValue);
                
                if(compareFlag <= 0){
                    compareFlag = propertyEndDateTime.compareTo(propertyDateTimeValue);
                    
                    if(compareFlag < 0)
                        this.actionFormController.addRangeValidationMessage(propertyName, propertyLabel, propertyStartDateTime, propertyEndDateTime);
                }
                else
                    this.actionFormController.addRangeValidationMessage(propertyName, propertyLabel, propertyStartDateTime, propertyEndDateTime);
            }
        }
    }
    
    /**
     * Process the number range validation.
     *
     * @param propertyClass Class that defines the number.
     * @param propertyName String that contains the identifier of the property.
     * @param propertyValue String that contains the value of the property.
     * @param useGroupSeparator True/False.
     * @param precision Numeric value that contains the precision of the property.
     * @param propertyMinimumValue Numeric value that contains the minimum value of the property.
     * @param propertyMaximumValue Numeric value that contains the maximum value of the property.
     * @throws ExpectedException Occurs when an expected exception was caught.
     * @throws InternalErrorException Occurs when an internal error exception was caught.
     */
    protected void processNumberRangeValidation(Class<?> propertyClass, String propertyName, String propertyValue, boolean useGroupSeparator, int precision, String propertyMinimumValue, String propertyMaximumValue) throws ExpectedException, InternalErrorException{
        if(propertyName != null && !propertyName.isEmpty() && propertyValue != null && !propertyValue.isEmpty() && propertyMinimumValue != null && !propertyMinimumValue.isEmpty() && propertyMaximumValue != null && !propertyMaximumValue.isEmpty()){
            Locale currentLanguage = getCurrentLanguage();
            Number[] numberRange = NumberUtil.getRange(propertyClass);
            Number propertyStartValue;
            Number propertyEndValue;
            Number propertyCompareValue = null;
            
            try{
                propertyCompareValue = NumberUtil.parse(propertyClass, propertyValue, useGroupSeparator, precision, currentLanguage);
            }
            catch(ParseException ignored){
            }
            
            try{
                propertyStartValue = NumberUtil.parse(propertyClass, propertyMinimumValue, useGroupSeparator, precision, currentLanguage);
            }
            catch(ParseException ignored){
                propertyStartValue = numberRange[0];
            }
            
            try{
                propertyEndValue = NumberUtil.parse(propertyClass, propertyMaximumValue, useGroupSeparator, precision, currentLanguage);
            }
            catch(ParseException ignored){
                propertyEndValue = numberRange[1];
            }
            
            if(propertyCompareValue != null && propertyStartValue != null && propertyEndValue != null){
                String propertyLabel = this.actionFormController.getPropertyLabel(propertyName);
                int compareFlag = PropertyUtil.compareTo(propertyStartValue, propertyCompareValue);
                
                if(compareFlag <= 0){
                    compareFlag = PropertyUtil.compareTo(propertyEndValue, propertyCompareValue);
                    
                    if(compareFlag < 0)
                        this.actionFormController.addRangeValidationMessage(propertyName, propertyLabel, propertyStartValue, propertyEndValue);
                }
                else
                    this.actionFormController.addRangeValidationMessage(propertyName, propertyLabel, propertyStartValue, propertyEndValue);
            }
        }
    }
    
    /**
     * Process the regular expression validation.
     *
     * @param propertyName String that contains the identifier of the property.
     * @param propertyValue String that contains the value of the property.
     * @param propertyRegex String that contains the regular expression of the property.
     * @throws ExpectedException Occurs when an expected exception was caught.
     * @throws InternalErrorException Occurs when an internal error exception was caught.
     */
    protected void processRegularExpressionValidation(String propertyName, String propertyValue, String propertyRegex) throws ExpectedException, InternalErrorException{
        if(propertyName != null && !propertyName.isEmpty() && propertyValue != null && !propertyValue.isEmpty() && propertyRegex != null && !propertyRegex.isEmpty()){
            Pattern regexp = Pattern.compile(propertyRegex);
            Matcher matcher = regexp.matcher(propertyValue);
            
            if(!matcher.matches()){
                String propertyLabel = this.actionFormController.getPropertyLabel(propertyName);
                
                this.actionFormController.addRegularExpressionValidationMessage(propertyName, propertyLabel, propertyRegex);
            }
        }
    }
    
    /**
     * Process the e-Mail validation.
     *
     * @param propertyName String that contains the identifier of the property.
     * @param propertyValue String that contains the value of the property.
     * @throws ExpectedException Occurs when an expected exception was caught.
     * @throws InternalErrorException Occurs when an internal error exception was caught.
     */
    protected void processEmailValidation(String propertyName, String propertyValue) throws ExpectedException, InternalErrorException{
        if(propertyName != null && !propertyName.isEmpty() && propertyValue != null && !propertyValue.isEmpty()){
            String pattern = ".+@.+\\..+";
            Pattern regexp = Pattern.compile(pattern);
            Matcher matcher = regexp.matcher(propertyValue);
            
            if(!matcher.matches()){
                String propertyLabel = this.actionFormController.getPropertyLabel(propertyName);
                
                this.actionFormController.addEmailValidationMessage(propertyName, propertyLabel);
            }
        }
    }
    
    /**
     * Process the pattern validation.
     *
     * @param propertyName String that contains the identifier of the property.
     * @param propertyValue String that contains the value of the property.
     * @param propertyPattern String that contains the pattern of the property.
     * @throws ExpectedException Occurs when an expected exception was caught.
     * @throws InternalErrorException Occurs when an internal error exception was caught.
     */
    protected void processPatternValidation(String propertyName, String propertyValue, String propertyPattern) throws ExpectedException, InternalErrorException{
        if(propertyName != null && !propertyName.isEmpty() && propertyValue != null && !propertyValue.isEmpty()){
            String propertyPatternBuffer = this.actionFormController.getPropertyPattern(propertyName);
            
            if(propertyPatternBuffer != null && !propertyPatternBuffer.isEmpty())
                propertyPattern = propertyPatternBuffer;
            
            if(propertyPattern != null && !propertyPattern.isEmpty()){
                Integer numberCount = 0;
                Integer characterCount = 0;
                StringBuilder regex = new StringBuilder();
                
                for(int cont = 0; cont < propertyPattern.length(); cont++){
                    if(propertyPattern.charAt(cont) == '9'){
                        if(characterCount > 0){
                            regex.append("([a-zA-Z0-9]{1,");
                            regex.append(characterCount);
                            regex.append("})");
                            
                            characterCount = 0;
                        }
                        
                        numberCount++;
                    }
                    else if(propertyPattern.charAt(cont) == '#'){
                        if(numberCount > 0){
                            regex.append("([0-9]{1,");
                            regex.append(numberCount);
                            regex.append("})");
                            
                            numberCount = 0;
                        }
                        
                        characterCount++;
                    }
                    else{
                        if(numberCount > 0){
                            regex.append("([0-9]{1,");
                            regex.append(numberCount);
                            regex.append("})");
                            
                            numberCount = 0;
                        }
                        
                        if(characterCount > 0){
                            regex.append("([a-zA-Z0-9]{1,");
                            regex.append(characterCount);
                            regex.append("})");
                            
                            characterCount = 0;
                        }
                        
                        regex.append("\\");
                        regex.append(propertyPattern.charAt(cont));
                    }
                }
                
                if(numberCount > 0){
                    regex.append("([0-9]{1,");
                    regex.append(numberCount);
                    regex.append("})");
                }
                
                if(characterCount > 0){
                    regex.append("([a-zA-Z0-9]{1,");
                    regex.append(characterCount);
                    regex.append("})");
                }
                
                Pattern regexp = Pattern.compile(regex.toString());
                Matcher matcher = regexp.matcher(propertyValue);
                
                if(!matcher.matches()){
                    String propertyLabel = this.actionFormController.getPropertyLabel(propertyName);
                    
                    this.actionFormController.addPatternValidationMessage(propertyName, propertyLabel, propertyPattern);
                }
            }
        }
    }
}