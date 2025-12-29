package br.com.concepting.framework.model.helpers;

import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.controller.form.util.ActionFormValidator;
import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.types.ValidationType;
import br.com.concepting.framework.model.util.ModelUtil;
import br.com.concepting.framework.util.PropertyUtil;
import br.com.concepting.framework.util.helpers.PropertyInfo;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

/**
 * Class that stores the characteristics of a data model.
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
public class ModelInfo{
    private Class<? extends BaseModel> clazz = null;
    private boolean isAbstract = false;
    private Collection<PropertyInfo> propertiesInfo = null;
    private Collection<PropertyInfo> identityPropertiesInfo = null;
    private Collection<PropertyInfo> uniquePropertiesInfo = null;
    private Collection<PropertyInfo> serializablePropertiesInfo = null;
    private Collection<PropertyInfo> searchPropertiesInfo = null;
    private Collection<PropertyInfo> validationPropertiesInfo = null;
    private Collection<PropertyInfo> auditablePropertiesInfo = null;
    private String mappedRepositoryId = null;
    private String persistenceResourcesId = null;
    private Class<? extends ActionFormValidator> actionFormValidatorClass = null;
    private String ui = null;
    private String templateId = null;
    private String descriptionPattern = null;
    private boolean generateService = false;
    private boolean generatePersistence = false;
    private boolean generateUi = false;
    private boolean cacheable = false;

    /**
     * Indicates if the data model is abstract.
     *
     * @return True/False.
     */
    public boolean isAbstract(){
        return this.isAbstract;
    }

    /**
     * Indicates if the data model is abstract.
     *
     * @return True/False.
     */
    public boolean getIsAbstract(){
        return this.isAbstract();
    }

    /**
     * Defines if the data model is abstract.
     *
     * @param isAbstract True/False.
     */
    public void setIsAbstract(boolean isAbstract){
        this.isAbstract = isAbstract;
    }

    /**
     * Indicates if the data model is cacheable.
     *
     * @return True/False.
     */
    public boolean isCacheable(){
        return this.cacheable;
    }

    /**
     * Indicates if the data model is cacheable.
     *
     * @return True/False.
     */
    public boolean getCacheable(){
        return isCacheable();
    }

    /**
     * Defines if the data model is cacheable.
     *
     * @param cacheable True/False.
     */
    public void setCacheable(boolean cacheable){
        this.cacheable = cacheable;
    }

    /**
     * Indicates if the UI of the data model should be generated.
     *
     * @return True/False.
     */
    public boolean generateUi(){
        return this.generateUi;
    }

    /**
     * Indicates if the UI of the data model should be generated.
     *
     * @return True/False.
     */
    public boolean getGenerateUi(){
        return generateUi();
    }

    /**
     * Defines if the UI of the data model should be generated.
     *
     * @param generateUi True/False.
     */
    public void setGenerateUi(boolean generateUi){
        this.generateUi = generateUi;
    }

    /**
     * Indicates if the service implementation of the data model should be
     * generated.
     *
     * @return True/False.
     */
    public boolean generateService(){
        return this.generateService;
    }

    /**
     * Indicates if the service implementation of the data model should be
     * generated.
     *
     * @return True/False.
     */
    public boolean getGenerateService(){
        return generateService();
    }

    /**
     * Defines if the service implementation of the data model should be
     * generated.
     *
     * @param generateService True/False.
     */
    public void setGenerateService(boolean generateService){
        this.generateService = generateService;
    }

    /**
     * Indicates if the persistence implementation of the data model should be
     * generated.
     *
     * @return True/False.
     */
    public boolean generatePersistence(){
        return this.generatePersistence;
    }

    /**
     * Indicates if the persistence implementation of the data model should be
     * generated.
     *
     * @return True/False.
     */
    public boolean getGeneratePersistence(){
        return generatePersistence();
    }

    /**
     * Defines if the persistence implementation of the data model should be
     * generated.
     *
     * @param generatePersistence True/False.
     */
    public void setGeneratePersistence(boolean generatePersistence){
        this.generatePersistence = generatePersistence;
    }

    /**
     * Returns the identifier of the persistence resources.
     *
     * @return String that contains the identifier of the persistence resources.
     */
    public String getPersistenceResourcesId(){
        return this.persistenceResourcesId;
    }

    /**
     * Defines the identifier of the persistence resources.
     *
     * @param persistenceResourcesId String that contains the identifier of the
     * persistence resources.
     */
    public void setPersistenceResourcesId(String persistenceResourcesId){
        this.persistenceResourcesId = persistenceResourcesId;
    }

    /**
     * Returns the identifier of the template used in the code generation.
     *
     * @return String that contains the identifier of the template.
     */
    public String getTemplateId(){
        return this.templateId;
    }

    /**
     * Defines the identifier of the template used in the code generation.
     *
     * @param templateId String that contains the identifier of the template.
     */
    public void setTemplateId(String templateId){
        this.templateId = templateId;
    }

    /**
     * Returns the list of properties that defines that the data model is
     * unique.
     *
     * @return List that contains the properties.
     */
    public Collection<PropertyInfo> getUniquePropertiesInfo(){
        return this.uniquePropertiesInfo;
    }

    /**
     * Defines the list of properties that defines that the data model is
     * unique.
     *
     * @param uniquePropertiesInfo List that contains the properties.
     */
    public void setUniquePropertiesInfo(Collection<PropertyInfo> uniquePropertiesInfo){
        this.uniquePropertiesInfo = uniquePropertiesInfo;
    }

    /**
     * Returns the list of properties that defines that the data model is
     * serializable.
     *
     * @return List that contains the properties.
     */
    public Collection<PropertyInfo> getSerializablePropertiesInfo(){
        return this.serializablePropertiesInfo;
    }

    /**
     * Defines the list of properties that defines that the data model is
     * serializable.
     *
     * @param serializablePropertiesInfo List that contains the properties.
     */
    public void setSerializablePropertiesInfo(Collection<PropertyInfo> serializablePropertiesInfo){
        this.serializablePropertiesInfo = serializablePropertiesInfo;
    }

    /**
     * Returns the class that defines the data model.
     *
     * @return Class that defines the data model.
     */
    public Class<? extends BaseModel> getClazz(){
        return this.clazz;
    }

    /**
     * Defines the class that defines the data model.
     *
     * @param clazz Class that defines the data model.
     */
    public void setClazz(Class<? extends BaseModel> clazz){
        this.clazz = clazz;
    }

    /**
     * Returns the list that contains the properties of the data model
     *
     * @return List that contains the properties of the data model.
     */
    public Collection<PropertyInfo> getPropertiesInfo(){
        return this.propertiesInfo;
    }

    /**
     * Defines the list that contains the properties of the data model
     *
     * @param propertiesInfo List that contains the properties of the data
     * model.
     */
    public void setPropertiesInfo(Collection<PropertyInfo> propertiesInfo){
        this.propertiesInfo = propertiesInfo;
        this.identityPropertiesInfo = PropertyUtil.instantiate(Constants.DEFAULT_LIST_CLASS);
        this.uniquePropertiesInfo = PropertyUtil.instantiate(Constants.DEFAULT_LIST_CLASS);
        this.serializablePropertiesInfo = PropertyUtil.instantiate(Constants.DEFAULT_LIST_CLASS);
        this.searchPropertiesInfo = PropertyUtil.instantiate(Constants.DEFAULT_LIST_CLASS);
        this.validationPropertiesInfo = PropertyUtil.instantiate(Constants.DEFAULT_LIST_CLASS);
        this.auditablePropertiesInfo = PropertyUtil.instantiate(Constants.DEFAULT_LIST_CLASS);

        for(PropertyInfo propertyInfo: propertiesInfo){
            if(propertyInfo.isIdentity() && this.identityPropertiesInfo != null)
                this.identityPropertiesInfo.add(propertyInfo);

            if(propertyInfo.isUnique() && this.uniquePropertiesInfo != null)
                this.uniquePropertiesInfo.add(propertyInfo);

            if(propertyInfo.isSerializable() && this.serializablePropertiesInfo != null)
                this.serializablePropertiesInfo.add(propertyInfo);

            if(propertyInfo.isForSearch() && this.searchPropertiesInfo != null)
               this.searchPropertiesInfo.add(propertyInfo);

            if(propertyInfo.isAuditable() && this.auditablePropertiesInfo != null)
                this.auditablePropertiesInfo.add(propertyInfo);

            if(propertyInfo.getValidations() != null && propertyInfo.getValidations().length > 0 && propertyInfo.getValidations()[0] == ValidationType.NONE && this.validationPropertiesInfo != null)
                this.validationPropertiesInfo.add(propertyInfo);
        }

        if(this.identityPropertiesInfo != null && !this.identityPropertiesInfo.isEmpty())
            setIdentityPropertiesInfo(this.identityPropertiesInfo);

        if(this.uniquePropertiesInfo != null && !this.uniquePropertiesInfo.isEmpty())
            setUniquePropertiesInfo(this.uniquePropertiesInfo);

        if(this.serializablePropertiesInfo != null && !this.serializablePropertiesInfo.isEmpty())
            setSerializablePropertiesInfo(this.serializablePropertiesInfo);

        if(this.searchPropertiesInfo != null && !this.searchPropertiesInfo.isEmpty())
            setSearchPropertiesInfo(this.searchPropertiesInfo);

        if(this.validationPropertiesInfo != null && !this.validationPropertiesInfo.isEmpty())
            setValidationPropertiesInfo(this.validationPropertiesInfo);

        if(this.auditablePropertiesInfo != null && !this.auditablePropertiesInfo.isEmpty())
            setAuditablePropertiesInfo(this.auditablePropertiesInfo);
    }

    /**
     * Returns the list of identity properties.
     *
     * @return List that contains the properties.
     */
    public Collection<PropertyInfo> getIdentityPropertiesInfo(){
        return this.identityPropertiesInfo;
    }

    /**
     * Defines the list of identity properties.
     *
     * @param identityPropertiesInfo List that contains the properties.
     */
    public void setIdentityPropertiesInfo(Collection<PropertyInfo> identityPropertiesInfo){
        this.identityPropertiesInfo = identityPropertiesInfo;
    }

    /**
     * Returns the list of search properties.
     *
     * @return List that contains the properties.
     */
    public Collection<PropertyInfo> getSearchPropertiesInfo(){
        return this.searchPropertiesInfo;
    }

    /**
     * Defines the list of search properties.
     *
     * @param searchPropertiesInfo List that contains the properties.
     */
    public void setSearchPropertiesInfo(Collection<PropertyInfo> searchPropertiesInfo){
        this.searchPropertiesInfo = searchPropertiesInfo;
    }

    /**
     * Returns the list of validation properties.
     *
     * @return List that contains the properties.
     */
    public Collection<PropertyInfo> getValidationPropertiesInfo(){
        return this.validationPropertiesInfo;
    }

    /**
     * Defines the list of validation properties.
     *
     * @param validationPropertiesInfo List that contains the properties.
     */
    public void setValidationPropertiesInfo(Collection<PropertyInfo> validationPropertiesInfo){
        this.validationPropertiesInfo = validationPropertiesInfo;
    }

    /**
     * Returns the list of validation properties.
     *
     * @return List that contains the properties.
     */
    public Collection<PropertyInfo> getAuditablePropertiesInfo(){
        return this.auditablePropertiesInfo;
    }

    /**
     * Defines the list of auditable properties.
     *
     * @param auditablePropertiesInfo List that contains the properties.
     */
    public void setAuditablePropertiesInfo(Collection<PropertyInfo> auditablePropertiesInfo){
        this.auditablePropertiesInfo = auditablePropertiesInfo;
    }

    /**
     * Returns the instance that contains a specific property of a data model.
     *
     * @param propertyId String that contains the identifier of the property.
     * @return Instance that contains the property.
     * @throws NoSuchFieldException Occurs when was not possible to find the
     * property.
     */
    @SuppressWarnings("unchecked")
    public PropertyInfo getPropertyInfo(String propertyId) throws NoSuchFieldException{
        if(this.propertiesInfo != null && !this.propertiesInfo.isEmpty()){
            Class<? extends BaseModel> modelClass;
            ModelInfo modelInfo;
            int pos = propertyId.indexOf(".");
            String propertyIdBuffer = (pos >= 0 ? propertyId.substring(0, pos) : propertyId);

            for(PropertyInfo propertyInfo: this.propertiesInfo){
                if(propertyIdBuffer.equals(propertyInfo.getId())){
                    if(propertyInfo.isModel() && pos >= 0){
                        try{
                            modelClass = (Class<? extends BaseModel>) propertyInfo.getClazz();
                            modelInfo = ModelUtil.getInfo(modelClass);
                            propertyIdBuffer = propertyId.substring(pos + 1);

                            return modelInfo.getPropertyInfo(propertyIdBuffer);
                        }
                        catch(ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e){
                            continue;
                        }
                    }

                    if(propertyIdBuffer.equals(propertyId))
                        return propertyInfo;
                }
            }
        }

        throw new NoSuchFieldException(propertyId);
    }

    /**
     * Returns the form validator class.
     *
     * @return Class that defines the form validator.
     */
    public Class<? extends ActionFormValidator> getActionFormValidatorClass(){
        return this.actionFormValidatorClass;
    }

    /**
     * Defines the form validator class.
     *
     * @param actionFormValidatorClass Class that defines the form validator.
     */
    public void setActionFormValidatorClass(Class<? extends ActionFormValidator> actionFormValidatorClass){
        this.actionFormValidatorClass = actionFormValidatorClass;
    }

    /**
     * Indicates if the data model has validation properties.
     *
     * @return True/False.
     */
    public boolean hasPropertiesValidation(){
        return (this.validationPropertiesInfo != null && !this.validationPropertiesInfo.isEmpty());
    }

    /**
     * Returns the identifier of the persistence repository.
     *
     * @return String that contains the identifier of the repository.
     */
    public String getMappedRepositoryId(){
        return this.mappedRepositoryId;
    }

    /**
     * Defines the identifier of the persistence repository.
     *
     * @param mappedRepositoryId String that contains the identifier of the
     * repository.
     */
    public void setMappedRepositoryId(String mappedRepositoryId){
        this.mappedRepositoryId = mappedRepositoryId;
    }

    /**
     * Returns the identifier of the UI.
     *
     * @return String that contains the identifier of the UI.
     */
    public String getUi(){
        return this.ui;
    }

    /**
     * Defines the identifier of the UI.
     *
     * @param ui String that contains the identifier of the UI.
     */
    public void setUi(String ui){
        this.ui = ui;
    }

    /**
     * Returns the pattern that defines a data model.
     *
     * @return String that contains the pattern that defines a data model.
     */
    public String getDescriptionPattern(){
        return this.descriptionPattern;
    }

    /**
     * Defines the pattern that defines a data model.
     *
     * @param descriptionPattern String that contains the pattern that defines a
     * data model.
     */
    public void setDescriptionPattern(String descriptionPattern){
        this.descriptionPattern = descriptionPattern;
    }
}