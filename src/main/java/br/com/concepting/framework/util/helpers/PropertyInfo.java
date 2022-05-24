package br.com.concepting.framework.util.helpers;

import br.com.concepting.framework.model.types.ConditionOperationType;
import br.com.concepting.framework.model.types.ConditionType;
import br.com.concepting.framework.model.types.ValidationType;
import br.com.concepting.framework.persistence.types.RelationJoinType;
import br.com.concepting.framework.persistence.types.RelationType;
import br.com.concepting.framework.util.NumberUtil;
import br.com.concepting.framework.util.PropertyUtil;
import br.com.concepting.framework.util.types.*;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

/**
 * Class that stores the attributes of a property of a data model.
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
public class PropertyInfo implements Comparable<PropertyInfo>, Cloneable{
    private String id = null;
    private String propertyId = null;
    private String propertyTypeId = null;
    private Class<?> clazz = null;
    private Class<?> collectionItemsClass = null;
    private String classPropertyId = null;
    private boolean isIdentity = false;
    private boolean fromClass = false;
    private String sequenceId = null;
    private boolean isUnique = false;
    private boolean isSerializable = false;
    private boolean isForSearch = false;
    private boolean isAuditable = false;
    private boolean isEnum = false;
    private boolean hasEnum = false;
    private boolean isArray = false;
    private boolean isCollection = false;
    private boolean isDate = false;
    private boolean isTime = false;
    private boolean isBoolean = false;
    private boolean isNumber = false;
    private boolean isByte = false;
    private boolean isShort = false;
    private boolean isInteger = false;
    private boolean isLong = false;
    private boolean isBigInteger = false;
    private boolean isFloat = false;
    private boolean isDouble = false;
    private boolean isBigDecimal = false;
    private boolean isCurrency = false;
    private boolean isByteArray = false;
    private boolean isString = false;
    private boolean isModel = false;
    private boolean hasModel = false;
    private boolean constrained = false;
    private boolean nullable = false;
    private ConditionType searchCondition = null;
    private ConditionOperationType searchConditionOperation = null;
    private String searchPropertyId = null;
    private SearchType searchType = null;
    private RelationType relationType = null;
    private RelationJoinType relationJoinType = null;
    private String keyId = null;
    private String foreignKeyId = null;
    private boolean cascadeOnSave = false;
    private boolean cascadeOnDelete = false;
    private String[] propertiesIds = null;
    private String mappedPropertyId = null;
    private String mappedPropertyType = null;
    private String[] mappedPropertiesIds = null;
    private String[] mappedRelationPropertiesIds = null;
    private String mappedRelationRepositoryId = null;
    private String formulaExpression = null;
    private FormulaType formulaType = null;
    private SortOrderType sortOrder = null;
    private ValidationType[] validations = null;
    private String[] validationActions = null;
    private ConditionType compareCondition = null;
    private String comparePropertyId = null;
    private double phoneticAccuracy = 0D;
    private String phoneticPropertyId = null;
    private String regularExpression = null;
    private int wordCount = 0;
    private boolean useGroupSeparator = false;
    private int minimumLength = 0;
    private int maximumLength = 0;
    private String minimumValue = null;
    private String maximumValue = null;
    private int size = 0;
    private int precision = 0;
    private String pattern = null;
    private ContentType[] contentTypes = null;
    private double contentSize = 0D;
    private ByteMetricType contentSizeUnit = null;
    private String contentTypePropertyId = null;
    private String contentFilenamePropertyId = null;
    private String customValidationId = null;
    private boolean persistPattern = false;
    private InputType inputType = null;
    private String dataset = null;
    
    /**
     * Indicates if the property belongs to the child class.
     *
     * @return True/False.
     */
    public boolean getFromClass(){
        return fromClass;
    }
    
    /**
     * Defines if the property belongs to the child class.
     *
     * @param fromClass True/False.
     */
    public void setFromClass(boolean fromClass){
        this.fromClass = fromClass;
    }
    
    /**
     * Returns the property type identifier.
     *
     * @return String that contains the property type identifier.
     */
    public String getPropertyTypeId(){
        return this.propertyTypeId;
    }
    
    /**
     * Defines the property type identifier.
     *
     * @param propertyTypeId String that contains the property type identifier.
     */
    public void setPropertyTypeId(String propertyTypeId){
        this.propertyTypeId = propertyTypeId;
    }
    
    /**
     * Indicates if the property is an array.
     *
     * @return True/False.
     */
    public boolean isArray(){
        return this.isArray;
    }
    
    /**
     * Indicates if the property is an array.
     *
     * @return True/False.
     */
    public boolean getIsArray(){
        return isArray();
    }
    
    /**
     * Defines if the property is an array.
     *
     * @param isArray True/False.
     */
    public void setIsArray(boolean isArray){
        this.isArray = isArray;
    }
    
    /**
     * Indicates if the property is a float number.
     *
     * @return True/False.
     */
    public boolean isFloat(){
        return this.isFloat;
    }
    
    /**
     * Indicates if the property is a float number.
     *
     * @return True/False.
     */
    public boolean getIsFloat(){
        return isFloat();
    }
    
    /**
     * Defines if the property is a float number.
     *
     * @param isFloat True/False.
     */
    public void setIsFloat(boolean isFloat){
        this.isFloat = isFloat;
        
        if(isFloat)
            if(getPrecision() == 0)
                setPrecision(NumberUtil.getDefaultPrecision(Float.class));
    }
    
    /**
     * Indicates if the property is a double number.
     *
     * @return True/False.
     */
    public boolean isDouble(){
        return this.isDouble;
    }
    
    /**
     * Indicates if the property is a double number.
     *
     * @return True/False.
     */
    public boolean getIsDouble(){
        return isDouble();
    }
    
    /**
     * Defines if the property is a float number.
     *
     * @param isDouble True/False.
     */
    public void setIsDouble(boolean isDouble){
        this.isDouble = isDouble;
        
        if(isDouble)
            if(getPrecision() == 0)
                setPrecision(NumberUtil.getDefaultPrecision(Double.class));
    }
    
    /**
     * Indicates if the property is a byte number.
     *
     * @return True/False.
     */
    public boolean isByte(){
        return this.isByte;
    }
    
    /**
     * Indicates if the property is a byte number.
     *
     * @return True/False.
     */
    public boolean getIsByte(){
        return isByte();
    }
    
    /**
     * Defines if the property is a byte number.
     *
     * @param isByte True/False.
     */
    public void setIsByte(boolean isByte){
        this.isByte = isByte;
    }
    
    /**
     * Indicates if the property is a short number.
     *
     * @return True/False.
     */
    public boolean isShort(){
        return this.isShort;
    }
    
    /**
     * Indicates if the property is a short number.
     *
     * @return True/False.
     */
    public boolean getIsShort(){
        return isShort();
    }
    
    /**
     * Defines if the property is a short number.
     *
     * @param isShort True/False.
     */
    public void setIsShort(boolean isShort){
        this.isShort = isShort;
    }
    
    /**
     * Indicates if the property is an int number.
     *
     * @return True/False.
     */
    public boolean isInteger(){
        return this.isInteger;
    }
    
    /**
     * Indicates if the property is an int number.
     *
     * @return True/False.
     */
    public boolean getIsInteger(){
        return isInteger();
    }
    
    /**
     * Defines if the property is an int number.
     *
     * @param isInteger True/False.
     */
    public void setIsInteger(boolean isInteger){
        this.isInteger = isInteger;
    }
    
    /**
     * Indicates if the property is a long number.
     *
     * @return True/False.
     */
    public boolean isLong(){
        return this.isLong;
    }
    
    /**
     * Indicates if the property is a long number.
     *
     * @return True/False.
     */
    public boolean getIsLong(){
        return isLong();
    }
    
    /**
     * Defines if the property is a long number.
     *
     * @param isLong True/False.
     */
    public void setIsLong(boolean isLong){
        this.isLong = isLong;
    }
    
    /**
     * Indicates if the property is a big int number.
     *
     * @return True/False.
     */
    public boolean isBigInteger(){
        return this.isBigInteger;
    }
    
    /**
     * Indicates if the property is a big int number.
     *
     * @return True/False.
     */
    public boolean getIsBigInteger(){
        return isBigInteger();
    }
    
    /**
     * Defines if the property is a big int number.
     *
     * @param isBigInteger True/False.
     */
    public void setIsBigInteger(boolean isBigInteger){
        this.isBigInteger = isBigInteger;
    }
    
    /**
     * Indicates if the property is a big decimal number.
     *
     * @return True/False.
     */
    public boolean isBigDecimal(){
        return this.isBigDecimal;
    }
    
    /**
     * Indicates if the property is a big decimal number.
     *
     * @return True/False.
     */
    public boolean getIsBigDecimal(){
        return isBigDecimal();
    }
    
    /**
     * Defines if the property is a big decimal number.
     *
     * @param isBigDecimal True/False.
     */
    public void setIsBigDecimal(boolean isBigDecimal){
        this.isBigDecimal = isBigDecimal;
        
        if(isBigDecimal)
            if(getPrecision() == 0)
                setPrecision(NumberUtil.getDefaultPrecision(BigDecimal.class));
    }
    
    /**
     * Returns the search type of the property.
     *
     * @return Instance that contains the search type.
     */
    public SearchType getSearchType(){
        return this.searchType;
    }
    
    /**
     * Defines the search type of the property.
     *
     * @param searchType Instance that contains the search type.
     */
    public void setSearchType(SearchType searchType){
        this.searchType = searchType;
    }
    
    /**
     * Returns the input type of the property.
     *
     * @return Instance that contains the input type.
     */
    public InputType getInputType(){
        return this.inputType;
    }
    
    /**
     * Defines the search type of the property.
     *
     * @param inputType Instance that contains the search type.
     */
    public void setInputType(InputType inputType){
        this.inputType = inputType;
    }
    
    /**
     * Returns the search condition operator.
     *
     * @return Instance that contains the search condition operator.
     */
    public ConditionOperationType getSearchConditionOperation(){
        return this.searchConditionOperation;
    }
    
    /**
     * Defines the search condition operator.
     *
     * @param searchConditionOperation Instance that contains the search condition operator.
     */
    public void setSearchConditionOperation(ConditionOperationType searchConditionOperation){
        this.searchConditionOperation = searchConditionOperation;
    }
    
    /**
     * Returns the content size unit used in the validation.
     *
     * @return Instance that contains the content size unit.
     */
    public ByteMetricType getContentSizeUnit(){
        return this.contentSizeUnit;
    }
    
    /**
     * Defines the content size unit used in the validation.
     *
     * @param contentSizeUnit Instance that contains the content size unit.
     */
    public void setContentSizeUnit(ByteMetricType contentSizeUnit){
        this.contentSizeUnit = contentSizeUnit;
    }
    
    /**
     * Returns the content size used in the validation.
     *
     * @return Instance that contains the content size.
     */
    public Double getContentSize(){
        return this.contentSize;
    }
    
    /**
     * Defines the content size used in the validation.
     *
     * @param contentSize Instance that contains the content size.
     */
    public void setContentSize(Double contentSize){
        this.contentSize = contentSize;
    }
    
    /**
     * Returns the identifier of the property that stores the content type.
     *
     * @return String that contains the identifier.
     */
    public String getContentTypePropertyId(){
        return this.contentTypePropertyId;
    }
    
    /**
     * Defines the identifier of the property that stores the content type.
     *
     * @param contentTypePropertyId String that contains the identifier.
     */
    public void setContentTypePropertyId(String contentTypePropertyId){
        this.contentTypePropertyId = contentTypePropertyId;
    }
    
    /**
     * Returns the identifier of the property that stores the content filename.
     *
     * @return String that contains the identifier.
     */
    public String getContentFilenamePropertyId(){
        return this.contentFilenamePropertyId;
    }
    
    /**
     * Defines the identifier of the property that stores the content filename.
     *
     * @param contentFilenamePropertyId String that contains the identifier.
     */
    public void setContentFilenamePropertyId(String contentFilenamePropertyId){
        this.contentFilenamePropertyId = contentFilenamePropertyId;
    }
    
    /**
     * Returns the content types to be used in the validation.
     *
     * @return Array that contains the content types.
     */
    public ContentType[] getContentTypes(){
        return this.contentTypes;
    }
    
    /**
     * Defines the content types to be used in the validation.
     *
     * @param contentTypes Array that contains the content types.
     */
    public void setContentTypes(ContentType[] contentTypes){
        this.contentTypes = contentTypes;
    }
    
    /**
     * Returns the size of the property.
     *
     * @return Numeric value that contains of the size.
     */
    public int getSize(){
        return this.size;
    }
    
    /**
     * Defines the size of the property.
     *
     * @param size Numeric value that contains of the size.
     */
    public void setSize(int size){
        this.size = size;
    }
    
    /**
     * Returns the list of validation actions.
     *
     * @return List that contains the identifiers of the actions.
     */
    public String[] getValidationActions(){
        return this.validationActions;
    }
    
    /**
     * Defines the list of validation actions.
     *
     * @param validationActions List that contains the identifiers of the
     * actions.
     */
    public void setValidationActions(String[] validationActions){
        this.validationActions = validationActions;
    }
    
    /**
     * Returns the identifier of the property dataset.
     *
     * @return String that contains the identifier of the property.
     */
    public String getDataset(){
        return this.dataset;
    }
    
    /**
     * Defines the identifier of the property dataset.
     *
     * @param dataset String that contains the identifier of the
     * property.
     */
    public void setDataset(String dataset){
        this.dataset = dataset;
    }
    
    /**
     * Returns the identifier of the repository sequence of a data model.
     *
     * @return String that contains the identifier of the sequence.
     */
    public String getSequenceId(){
        return this.sequenceId;
    }
    
    /**
     * Defines the identifier of the repository sequence of a data model.
     *
     * @param sequenceId String that contains the identifier of the sequence.
     */
    public void setSequenceId(String sequenceId){
        this.sequenceId = sequenceId;
    }
    
    /**
     * Returns the formula type.
     *
     * @return Instance that contains the formula type.
     */
    public FormulaType getFormulaType(){
        return this.formulaType;
    }
    
    /**
     * Defines the formula type.
     *
     * @param formulaType Instance that contains the formula type.
     */
    public void setFormulaType(FormulaType formulaType){
        this.formulaType = formulaType;
    }
    
    /**
     * Returns the type of the mapped property.
     *
     * @return String that contains the type of property.
     */
    public String getMappedPropertyType(){
        return this.mappedPropertyType;
    }
    
    /**
     * Defines the type of the mapped property.
     *
     * @param mappedPropertyType String that contains the type of property.
     */
    public void setMappedPropertyType(String mappedPropertyType){
        this.mappedPropertyType = mappedPropertyType;
    }
    
    /**
     * Indicates if the property is currency.
     *
     * @return True/False.
     */
    public boolean isCurrency(){
        return this.isCurrency;
    }
    
    /**
     * Indicates if the property is currency.
     *
     * @return True/False.
     */
    public boolean getIsCurrency(){
        return isCurrency();
    }
    
    /**
     * Defines if the property is currency.
     *
     * @param isCurrency True/False.
     */
    public void setIsCurrency(boolean isCurrency){
        this.isCurrency = isCurrency;
    }
    
    /**
     * Returns the precision of the property.
     *
     * @return Numeric value that contains the precision.
     */
    public int getPrecision(){
        return this.precision;
    }
    
    /**
     * Defines the precision of the property.
     *
     * @param precision Numeric value that contains the precision.
     */
    public void setPrecision(int precision){
        this.precision = precision;
    }
    
    /**
     * Returns the regular expression used in the validation.
     *
     * @return String that contains regular expression.
     */
    public String getRegularExpression(){
        return this.regularExpression;
    }
    
    /**
     * Defines the regular expression used in the validation.
     *
     * @param regularExpression String that contains regular expression.
     */
    public void setRegularExpression(String regularExpression){
        this.regularExpression = regularExpression;
    }
    
    /**
     * Indicates if additional formatting should be used.
     *
     * @return True/False.
     */
    public boolean useAdditionalFormatting(){
        if(isDate())
            return isTime();
        else if(isNumber())
            return useGroupSeparator();
        
        return false;
    }
    
    /**
     * Returns the property of the data model that defines the class of the
     * property.
     *
     * @return String that contains the identifier of the property.
     */
    public String getClassPropertyId(){
        return this.classPropertyId;
    }
    
    /**
     * Defines the property of the data model that defines the class of the
     * property.
     *
     * @param classPropertyId String that contains the identifier of the
     * property.
     */
    public void setClassPropertyId(String classPropertyId){
        this.classPropertyId = classPropertyId;
    }
    
    /**
     * Indicates if the property accepts null.
     *
     * @return True/False.
     */
    public boolean isNullable(){
        return this.nullable;
    }
    
    /**
     * Indicates if the property accepts null.
     *
     * @return True/False.
     */
    public boolean getNullable(){
        return isNullable();
    }
    
    /**
     * Defines if the property accepts null.
     *
     * @param nullable True/False.
     */
    public void setNullable(boolean nullable){
        this.nullable = nullable;
    }
    
    /**
     * Indicates if the relationship should be constrained.
     *
     * @return True/False.
     */
    public boolean isConstrained(){
        return this.constrained;
    }
    
    /**
     * Indicates if the relationship should be constrained.
     *
     * @return True/False.
     */
    public boolean getConstrained(){
        return isConstrained();
    }
    
    /**
     * Defines if the relationship should be constrained.
     *
     * @param constrained True/False.
     */
    public void setConstrained(boolean constrained){
        this.constrained = constrained;
    }
    
    /**
     * Returns the identifier of the foreign key of the relationship.
     *
     * @return String that contains the identifier of the foreign key.
     */
    public String getForeignKeyId(){
        return this.foreignKeyId;
    }
    
    /**
     * Defines the identifier of the foreign key of the relationship.
     *
     * @param foreignKeyId String that contains the identifier of the foreign
     * key.
     */
    public void setForeignKeyId(String foreignKeyId){
        this.foreignKeyId = foreignKeyId;
    }
    
    /**
     * Returns the identifier of the key that contains the property.
     *
     * @return String that contains the identifier of the key.
     */
    public String getKeyId(){
        return this.keyId;
    }
    
    /**
     * Defines the identifier of the key that contains the property.
     *
     * @param keyId String that contains the identifier of the key.
     */
    public void setKeyId(String keyId){
        this.keyId = keyId;
    }
    
    /**
     * Returns the relationship join type.
     *
     * @return Instance that contains the relationship join type.
     */
    public RelationJoinType getRelationJoinType(){
        return this.relationJoinType;
    }
    
    /**
     * Defines the relationship join type.
     *
     * @param relationJoinType Instance that contains the relationship join
     * type.
     */
    public void setRelationJoinType(RelationJoinType relationJoinType){
        this.relationJoinType = relationJoinType;
    }
    
    /**
     * Indicates if the relationship cascades on save.
     *
     * @return True/False.
     */
    public boolean cascadeOnSave(){
        return this.cascadeOnSave;
    }
    
    /**
     * Indicates if the relationship cascades on save.
     *
     * @return True/False.
     */
    public boolean getCascadeOnSave(){
        return cascadeOnSave();
    }
    
    /**
     * Defines if the relationship cascades on save.
     *
     * @param cascadeOnSave True/False.
     */
    public void setCascadeOnSave(boolean cascadeOnSave){
        this.cascadeOnSave = cascadeOnSave;
    }
    
    /**
     * Indicates if the relationship cascades on delete.
     *
     * @return True/False.
     */
    public boolean cascadeOnDelete(){
        return this.cascadeOnDelete;
    }
    
    /**
     * Indicates if the relationship cascades on delete.
     *
     * @return True/False.
     */
    public boolean getCascadeOnDelete(){
        return cascadeOnDelete();
    }
    
    /**
     * Defines if the relationship cascades on delete.
     *
     * @param cascadeOnDelete True/False.
     */
    public void setCascadeOnDelete(boolean cascadeOnDelete){
        this.cascadeOnDelete = cascadeOnDelete;
    }
    
    /**
     * Returns the identifier of the property that should be used in the
     * phonetic search.
     *
     * @return String that contains the identifier of the property.
     */
    public String getPhoneticPropertyId(){
        return this.phoneticPropertyId;
    }
    
    /**
     * Defines the identifier of the property that should be used in the
     * phonetic search.
     *
     * @param phoneticPropertyId String that contains the identifier of the
     * property.
     */
    public void setPhoneticPropertyId(String phoneticPropertyId){
        this.phoneticPropertyId = phoneticPropertyId;
    }
    
    /**
     * Returns the identifier of the property that should be used in the search.
     *
     * @return String that contains the identifier of the property.
     */
    public String getSearchPropertyId(){
        return this.searchPropertyId;
    }
    
    /**
     * Defines the identifier of the property that should be used in the search.
     *
     * @param searchPropertyId String that contains the identifier of the
     * property.
     */
    public void setSearchPropertyId(String searchPropertyId){
        this.searchPropertyId = searchPropertyId;
    }
    
    /**
     * Indicates if the group separator should be used in the numeric
     * formatting.
     *
     * @return True/False.
     */
    public boolean getUseGroupSeparator(){
        return useGroupSeparator();
    }
    
    /**
     * Indicates if the key that contains the property is unique.
     *
     * @return True/False.
     */
    public boolean isUnique(){
        return this.isUnique;
    }
    
    /**
     * Indicates if the key that contains the property is unique.
     *
     * @return True/False.
     */
    public boolean getIsUnique(){
        return isUnique();
    }
    
    /**
     * Defines if the key that contains the property is unique.
     *
     * @param isUnique True/False.
     */
    public void setIsUnique(boolean isUnique){
        this.isUnique = isUnique;
    }
    
    /**
     * Indicates if the key that contains the property is serializable.
     *
     * @return True/False.
     */
    public boolean isSerializable(){
        return this.isSerializable;
    }
    
    /**
     * Indicates if the key that contains the property is serializable.
     *
     * @return True/False.
     */
    public boolean getIsSerializable(){
        return isSerializable();
    }
    
    /**
     * Defines if the key that contains the property is serializable.
     *
     * @param isSerializable True/False.
     */
    public void setIsSerializable(boolean isSerializable){
        this.isSerializable = isSerializable;
    }
    
    /**
     * Returns the mapping of the property in the persistence repository.
     *
     * @return String that contains the mapping.
     */
    public String getMappedPropertyId(){
        return this.mappedPropertyId;
    }
    
    /**
     * Defines the mapping of the property in the persistence repository.
     *
     * @param mappedPropertyId String that contains the mapping.
     */
    public void setMappedPropertyId(String mappedPropertyId){
        this.mappedPropertyId = mappedPropertyId;
    }
    
    /**
     * Returns the identifiers of the foreign keys of the relationship.
     *
     * @return String that contains the identifiers.
     */
    public String[] getMappedPropertiesIds(){
        return this.mappedPropertiesIds;
    }
    
    /**
     * Defines the identifiers of the foreign keys of the relationship.
     *
     * @param mappedPropertiesIds String that contains the identifiers.
     */
    public void setMappedPropertiesIds(String[] mappedPropertiesIds){
        this.mappedPropertiesIds = mappedPropertiesIds;
    }
    
    /**
     * Indicates if the property is a data model.
     *
     * @return True/False.
     */
    public boolean isModel(){
        return this.isModel;
    }
    
    /**
     * Indicates if the property is a data model.
     *
     * @return True/False.
     */
    public boolean getIsModel(){
        return isModel();
    }
    
    /**
     * Defines if the property is a data model.
     *
     * @param isModel True/False.
     */
    public void setIsModel(boolean isModel){
        this.isModel = isModel;
    }
    
    /**
     * Indicates if the property contains data models.
     *
     * @return True/False.
     */
    public boolean hasModel(){
        return this.hasModel;
    }
    
    /**
     * Indicates if the property contains data models.
     *
     * @return True/False.
     */
    public boolean getHasModel(){
        return hasModel();
    }
    
    /**
     * Defines if the property contains data models.
     *
     * @param hasModel True/False.
     */
    public void setHasModel(boolean hasModel){
        this.hasModel = hasModel;
    }
    
    /**
     * Indicates if the property is a date.
     *
     * @return True/False.
     */
    public boolean isDate(){
        return this.isDate;
    }
    
    /**
     * Indicates if the property is a date.
     *
     * @return True/False.
     */
    public boolean getIsDate(){
        return isDate();
    }
    
    /**
     * Indicates if the property is a time.
     *
     * @return True/False.
     */
    public boolean isTime(){
        return this.isTime;
    }
    
    /**
     * Indicates if the property is a time.
     *
     * @return True/False.
     */
    public boolean getIsTime(){
        return isTime();
    }
    
    /**
     * Indicates if the property is boolean.
     *
     * @return True/False.
     */
    public boolean isBoolean(){
        return this.isBoolean;
    }
    
    /**
     * Indicates if the property is boolean.
     *
     * @return True/False.
     */
    public boolean getIsBoolean(){
        return isBoolean();
    }
    
    /**
     * Indicates if the property is a numeric value.
     *
     * @return True/False.
     */
    public boolean isNumber(){
        return this.isNumber;
    }
    
    /**
     * Indicates if the property is a numeric value.
     *
     * @return True/False.
     */
    public boolean getIsNumber(){
        return isNumber();
    }
    
    /**
     * Returns the identifier of the property.
     *
     * @return String that contains the identifier.
     */
    public String getId(){
        return this.id;
    }
    
    /**
     * Defines the identifier of the property.
     *
     * @param id String that contains the identifier.
     */
    public void setId(String id){
        this.id = id;
    }
    
    /**
     * Returns the class of the property.
     *
     * @return Class that defines the property.
     */
    public Class<?> getClazz(){
        return this.clazz;
    }
    
    /**
     * Defines the class of the property.
     *
     * @param clazz Class that defines the property.
     */
    public void setClazz(Class<?> clazz){
        this.clazz = clazz;
        
        setIsEnum(PropertyUtil.isEnum(clazz));
        setIsBoolean(PropertyUtil.isBoolean(clazz));
        setIsArray(PropertyUtil.isArray(clazz));
        setIsCollection(PropertyUtil.isCollection(clazz));
        setIsByteArray(PropertyUtil.isByteArray(clazz));
        setIsDate(PropertyUtil.isDate(clazz));
        setIsTime(PropertyUtil.isTime(clazz));
        setIsModel(PropertyUtil.isModel(clazz));
        setIsNumber(PropertyUtil.isNumber(clazz));
        setIsByte(PropertyUtil.isByte(clazz));
        setIsShort(PropertyUtil.isShort(clazz));
        setIsInteger(PropertyUtil.isInteger(clazz));
        setIsLong(PropertyUtil.isLong(clazz));
        setIsBigInteger(PropertyUtil.isBigInteger(clazz));
        setIsFloat(PropertyUtil.isFloat(clazz));
        setIsDouble(PropertyUtil.isDouble(clazz));
        setIsBigDecimal(PropertyUtil.isBigDecimal(clazz));
        setIsCurrency(PropertyUtil.isCurrency(getClazz()));
        setIsString(PropertyUtil.isString(getClazz()));
    }
    
    /**
     * Returns the class of the collection.
     *
     * @return Class that defines the collection.
     */
    public Class<?> getCollectionItemsClass(){
        return this.collectionItemsClass;
    }
    
    /**
     * Defines the class of the collection.
     *
     * @param collectionItemsClass Class that defines the collection.
     */
    public void setCollectionItemsClass(Class<?> collectionItemsClass){
        this.collectionItemsClass = collectionItemsClass;
        
        setHasModel(PropertyUtil.isModel(collectionItemsClass));
        setHasEnum(PropertyUtil.isEnum(collectionItemsClass));
    }
    
    /**
     * Indicates if the property identifies a data model.
     *
     * @return True/False.
     */
    public boolean isIdentity(){
        return this.isIdentity;
    }
    
    /**
     * Indicates if the property identifies a data model.
     *
     * @return True/False.
     */
    public boolean getIsIdentity(){
        return isIdentity();
    }
    
    /**
     * Defines if the property identifies a data model.
     *
     * @param isIdentity True/False.
     */
    public void setIsIdentity(boolean isIdentity){
        this.isIdentity = isIdentity;
    }
    
    /**
     * Indicates if the property will be considered in the search.
     *
     * @return True/False.
     */
    public boolean isForSearch(){
        return this.isForSearch;
    }
    
    /**
     * Indicates if the property will be considered in the search.
     *
     * @return True/False.
     */
    public boolean getIsForSearch(){
        return isForSearch();
    }
    
    /**
     * Defines if the property will be considered in the search.
     *
     * @param isForSearch True/False.
     */
    public void setIsForSearch(boolean isForSearch){
        this.isForSearch = isForSearch;
    }
    
    /**
     * Returns the minimum length of the property.
     *
     * @return Numeric value that contains the minimum length.
     */
    public int getMinimumLength(){
        return this.minimumLength;
    }
    
    /**
     * Defines the minimum length of the property.
     *
     * @param minimumLength Numeric value that contains the minimum length.
     */
    public void setMinimumLength(int minimumLength){
        this.minimumLength = minimumLength;
    }
    
    /**
     * Returns the maximum length of the property.
     *
     * @return Numeric value that contains the maximum length.
     */
    public int getMaximumLength(){
        return this.maximumLength;
    }
    
    /**
     * Defines the maximum length of the property.
     *
     * @param maximumLength Numeric value that contains the maximum length.
     */
    public void setMaximumLength(int maximumLength){
        this.maximumLength = maximumLength;
    }
    
    /**
     * Returns the pattern used in the formatting and validation of the
     * property.
     *
     * @return String that contains the pattern.
     */
    public String getPattern(){
        return this.pattern;
    }
    
    /**
     * Defines the pattern used in the formatting and validation of the
     * property.
     *
     * @param pattern String that contains the pattern.
     */
    public void setPattern(String pattern){
        this.pattern = pattern;
    }
    
    /**
     * Returns the identifier of the custom validation rule.
     *
     * @return String that contains the identifier of validation.
     */
    public String getCustomValidationId(){
        return this.customValidationId;
    }
    
    /**
     * Defines the identifier of the custom validation rule.
     *
     * @param customValidationId String that contains the identifier of
     * validation.
     */
    public void setCustomValidationId(String customValidationId){
        this.customValidationId = customValidationId;
    }
    
    /**
     * Indica if the property will be audited.
     *
     * @return True/False.
     */
    public boolean isAuditable(){
        return this.isAuditable;
    }
    
    /**
     * Indica if the property will be audited.
     *
     * @return True/False.
     */
    public boolean getIsAuditable(){
        return isAuditable();
    }
    
    /**
     * Defines if the property will be audited.
     *
     * @param isAuditable True/False.
     */
    public void setIsAuditable(boolean isAuditable){
        this.isAuditable = isAuditable;
    }
    
    /**
     * Returns the property validation rules.
     *
     * @return List that contains the validation rules.
     */
    public ValidationType[] getValidations(){
        return this.validations;
    }
    
    /**
     * Defines the property validation rules.
     *
     * @param validations List that contains the validation rules.
     */
    public void setValidations(ValidationType[] validations){
        this.validations = validations;
    }
    
    /**
     * Indicates if the property has validation rules.
     *
     * @param validation Instance that contains the validation type.
     * @return True/False.
     */
    public boolean hasValidation(ValidationType validation){
        if(this.validations != null)
            for (ValidationType validationType : this.validations)
                if (validationType.equals(validation))
                    return true;
        
        return false;
    }
    
    /**
     * Defines if the property is boolean.
     *
     * @param isBoolean True/False.
     */
    public void setIsBoolean(boolean isBoolean){
        this.isBoolean = isBoolean;
    }
    
    /**
     * Defines if the property is a date.
     *
     * @param isDate True/False.
     */
    public void setIsDate(boolean isDate){
        this.isDate = isDate;
    }
    
    /**
     * Defines if the property is a time.
     *
     * @param isTime True/False.
     */
    public void setIsTime(boolean isTime){
        this.isTime = isTime;
    }
    
    /**
     * Defines if the property is a numeric value.
     *
     * @param isNumber True/False.
     */
    public void setIsNumber(boolean isNumber){
        this.isNumber = isNumber;
    }
    
    /**
     * Returns the type of relationship.
     *
     * @return Instance that contains the type of relationship.
     */
    public RelationType getRelationType(){
        return this.relationType;
    }
    
    /**
     * Defines the type of relationship.
     *
     * @param relationType Instance that contains the type of relationship.
     */
    public void setRelationType(RelationType relationType){
        this.relationType = relationType;
    }
    
    /**
     * Indicates if the property is a collection.
     *
     * @return True/False.
     */
    public boolean isCollection(){
        return this.isCollection;
    }
    
    /**
     * Indicates if the property is a collection.
     *
     * @return True/False.
     */
    public boolean getIsCollection(){
        return isCollection();
    }
    
    /**
     * Defines if the property is a collection.
     *
     * @param isCollection True/False.
     */
    public void setIsCollection(boolean isCollection){
        this.isCollection = isCollection;
    }
    
    /**
     * Indicates if the property is a byte array.
     *
     * @return True/False.
     */
    public boolean isByteArray(){
        return this.isByteArray;
    }
    
    /**
     * Indicates if the property is a byte array.
     *
     * @return True/False.
     */
    public boolean getIsByteArray(){
        return isByteArray();
    }
    
    /**
     * Defines if the property is a byte array.
     *
     * @param isByteArray True/False.
     */
    public void setIsByteArray(boolean isByteArray){
        this.isByteArray = isByteArray;
    }
    
    /**
     * Returns the mapping of the properties of a data model
     *
     * @return String that contains the mapping of the properties.
     */
    public String[] getPropertiesIds(){
        return this.propertiesIds;
    }
    
    /**
     * Defines the mapping of the properties of the data model.
     *
     * @param propertiesIds String that contains the mapping of the properties.
     */
    public void setPropertiesIds(String[] propertiesIds){
        this.propertiesIds = propertiesIds;
    }
    
    /**
     * Indicates if the property is an enumeration.
     *
     * @return True/False.
     */
    public boolean isEnum(){
        return this.isEnum;
    }
    
    /**
     * Indicates if the property is an enumeration.
     *
     * @return True/False.
     */
    public boolean getIsEnum(){
        return isEnum();
    }
    
    /**
     * Defines if the property is an enumeration.
     *
     * @param isEnum True/False.
     */
    public void setIsEnum(boolean isEnum){
        this.isEnum = isEnum;
    }
    
    /**
     * Indicates if the property contains enumerations.
     *
     * @return True/False.
     */
    public boolean hasEnum(){
        return this.hasEnum;
    }
    
    /**
     * Indicates if the property contains enumerations.
     *
     * @return True/False.
     */
    public boolean getHasEnum(){
        return hasEnum();
    }
    
    /**
     * Defines if the property contains enumerations.
     *
     * @param hasEnum True/False.
     */
    public void setHasEnum(boolean hasEnum){
        this.hasEnum = hasEnum;
    }
    
    /**
     * Defines the identifier of the repository for NxN relationships.
     *
     * @param mappedRelationRepositoryId String that contains the identifier of
     * the repository.
     */
    public void setMappedRelationRepositoryId(String mappedRelationRepositoryId){
        this.mappedRelationRepositoryId = mappedRelationRepositoryId;
    }
    
    /**
     * Returns the search condition of the property.
     *
     * @return Instance that contains the search condition.
     */
    public ConditionType getSearchCondition(){
        return this.searchCondition;
    }
    
    /**
     * Defines the search condition of the property.
     *
     * @param searchCondition Instance that contains the search condition.
     */
    public void setSearchCondition(ConditionType searchCondition){
        this.searchCondition = searchCondition;
    }
    
    /**
     * Returns the identifier of the repository for NxN relationships.
     *
     * @return String that contains the identifier of the repository.
     */
    public String getMappedRelationRepositoryId(){
        return this.mappedRelationRepositoryId;
    }
    
    /**
     * Returns the identifiers of the foreign keys of the relationship.
     *
     * @return String that contains the identifiers.
     */
    public String[] getMappedRelationPropertiesIds(){
        return this.mappedRelationPropertiesIds;
    }
    
    /**
     * Defines the identifiers of the foreign keys of the relationship.
     *
     * @param mappedRelationPropertiesIds String that contains the identifiers.
     */
    public void setMappedRelationPropertiesIds(String[] mappedRelationPropertiesIds){
        this.mappedRelationPropertiesIds = mappedRelationPropertiesIds;
    }
    
    /**
     * Indica if the group separator should be used in the numeric formatting..
     *
     * @return True/False.
     */
    public boolean useGroupSeparator(){
        return this.useGroupSeparator;
    }
    
    /**
     * Defines if the group separator should be used in the numeric formatting..
     *
     * @param useGroupSeparator True/False.
     */
    public void setUseGroupSeparator(boolean useGroupSeparator){
        this.useGroupSeparator = useGroupSeparator;
    }
    
    /**
     * Returns formula expression.
     *
     * @return String that contains formula expression.
     */
    public String getFormulaExpression(){
        return this.formulaExpression;
    }
    
    /**
     * Defines formula expression.
     *
     * @param formulaExpression String that contains formula expression.
     */
    public void setFormulaExpression(String formulaExpression){
        this.formulaExpression = formulaExpression;
    }
    
    /**
     * Returns the type of sort that should be used.
     *
     * @return Instance that contains the type of sort.
     */
    public SortOrderType getSortOrder(){
        return this.sortOrder;
    }
    
    /**
     * Defines the type of sort that should be used.
     *
     * @param sortOrder Instance that contains the type of sort.
     */
    public void setSortOrder(SortOrderType sortOrder){
        this.sortOrder = sortOrder;
    }
    
    /**
     * Returns the accuracy percentage to be used in the phonetic search.
     *
     * @return Numeric value that contains the accuracy percentage.
     */
    public Double getPhoneticAccuracy(){
        return this.phoneticAccuracy;
    }
    
    /**
     * Defines the accuracy percentage to be used in the phonetic search.
     *
     * @param phoneticAccuracy Numeric value that contains the accuracy
     * percentage.
     */
    public void setPhoneticAccuracy(Double phoneticAccuracy){
        this.phoneticAccuracy = phoneticAccuracy;
    }
    
    /**
     * Returns the identifier of the property of the data model that should be
     * mapped in the persistence repository.
     *
     * @return String that contains the identifier of the property.
     */
    public String getPropertyId(){
        return this.propertyId;
    }
    
    /**
     * Defines the identifier of the property of the data model that should be
     * mapped in the persistence repository.
     *
     * @param propertyId String that contains the identifier of the property.
     */
    public void setPropertyId(String propertyId){
        this.propertyId = propertyId;
    }
    
    /**
     * Returns the end range to be used in comparison validation.
     *
     * @return String that contains the maximum value.
     */
    public String getMaximumValue(){
        return this.maximumValue;
    }
    
    /**
     * Defines the maximum value to be used in range validation.
     *
     * @param maximumValue String that contains the maximum value.
     */
    public void setMaximumValue(String maximumValue){
        this.maximumValue = maximumValue;
    }
    
    /**
     * Returns the start range to be used in range validation.
     *
     * @return String that contains the minimum value.
     */
    public String getMinimumValue(){
        return this.minimumValue;
    }
    
    /**
     * Defines the start range to be used in range validation.
     *
     * @param minimumValue String that contains the minimum value.
     */
    public void setMinimumValue(String minimumValue){
        this.minimumValue = minimumValue;
    }
    
    /**
     * Returns if the pattern that will be persisted.
     *
     * @return True/False.
     */
    public boolean persistPattern(){
        return this.persistPattern;
    }
    
    /**
     * Returns if the pattern that will be persisted.
     *
     * @return True/False.
     */
    public boolean getPersistPattern(){
        return persistPattern();
    }
    
    /**
     * Defines if the pattern that will be persisted.
     *
     * @param persistPattern True/False.
     */
    public void setPersistPattern(boolean persistPattern){
        this.persistPattern = persistPattern;
    }
    
    /**
     * Returns the comparison condition used in the validation.
     *
     * @return Instance that contains the type of comparison.
     */
    public ConditionType getCompareCondition(){
        return this.compareCondition;
    }
    
    /**
     * Defines the comparison condition used in the validation.
     *
     * @param compareCondition Instance that contains the type of comparison.
     */
    public void setCompareCondition(ConditionType compareCondition){
        this.compareCondition = compareCondition;
    }
    
    /**
     * Returns the identifier of the comparison property used in the validation.
     *
     * @return String that contains the identifier of the property.
     */
    public String getComparePropertyId(){
        return this.comparePropertyId;
    }
    
    /**
     * Defines the identifier of the comparison property used in the validation.
     *
     * @param comparePropertyId String that contains the identifier of the
     * property.
     */
    public void setComparePropertyId(String comparePropertyId){
        this.comparePropertyId = comparePropertyId;
    }
    
    /**
     * Returns the word count to be used in the validation of the property.
     *
     * @return Numeric value that contains the word count.
     */
    public int getWordCount(){
        return this.wordCount;
    }
    
    /**
     * Defines the word count to be used in the validation of the property.
     *
     * @param wordCount Numeric value that contains the word count.
     */
    public void setWordCount(int wordCount){
        this.wordCount = wordCount;
    }
    
    /**
     * Indicates if the property is a string.
     *
     * @return True/False.
     */
    public boolean isString(){
        return this.isString;
    }
    
    /**
     * Indicates if the property is a string.
     *
     * @return True/False.
     */
    public boolean getIsString(){
        return isString();
    }
    
    /**
     * Defines if the property is a string.
     *
     * @param isString True/False.
     */
    public void setIsString(boolean isString){
        this.isString = isString;
    }

    @Override
    public boolean equals(Object object){
        PropertyInfo comparePropertyInfo = (PropertyInfo) object;
        
        if(comparePropertyInfo == null)
            return false;
        
        return (this.id != null && this.id.equals(comparePropertyInfo.getId()));
    }

    @Override
    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }

    @Override
    public int hashCode(){
        return (this.id != null ? this.id.hashCode() : -1);
    }

    @Override
    public int compareTo(@NotNull PropertyInfo comparePropertyInfo){
        return (comparePropertyInfo != null && this.id != null ? this.id.compareTo(comparePropertyInfo.getId()) : -1);
    }
}