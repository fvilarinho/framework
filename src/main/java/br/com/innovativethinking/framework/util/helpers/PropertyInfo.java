package br.com.innovativethinking.framework.util.helpers;

import br.com.innovativethinking.framework.constants.Constants;
import br.com.innovativethinking.framework.model.types.ConditionOperationType;
import br.com.innovativethinking.framework.model.types.ConditionType;
import br.com.innovativethinking.framework.model.types.ValidationType;
import br.com.innovativethinking.framework.persistence.types.RelationJoinType;
import br.com.innovativethinking.framework.persistence.types.RelationType;
import br.com.innovativethinking.framework.util.PropertyUtil;
import br.com.innovativethinking.framework.util.types.ByteMetricType;
import br.com.innovativethinking.framework.util.types.ContentType;
import br.com.innovativethinking.framework.util.types.FormulaType;
import br.com.innovativethinking.framework.util.types.InputType;
import br.com.innovativethinking.framework.util.types.SearchType;
import br.com.innovativethinking.framework.util.types.SortOrderType;

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
	private String                 id                            = null;
	private String                 propertyId                    = null;
	private String                 propertyTypeId                = null;
	private Class<?>               clazz                         = null;
	private Class<?>               collectionItemsClass          = null;
	private String                 classPropertyId               = null;
	private Boolean                isIdentity                    = null;
	private Boolean                fromClass                     = null;
	private String                 sequenceId                    = null;
	private Boolean                isUnique                      = null;
	private Boolean                isSerializable                = null;
	private Boolean                isForSearch                   = null;
	private Boolean                isAuditable                   = null;
	private Boolean                isEnum                        = null;
	private Boolean                hasEnum                       = null;
	private Boolean                isArray                       = null;
	private Boolean                isCollection                  = null;
	private Boolean                isDate                        = null;
	private Boolean                isTime                        = null;
	private Boolean                isBoolean                     = null;
	private Boolean                isNumber                      = null;
	private Boolean                isByte                        = null;
	private Boolean                isShort                       = null;
	private Boolean                isInteger                     = null;
	private Boolean                isLong                        = null;
	private Boolean                isBigInteger                  = null;
	private Boolean                isFloat                       = null;
	private Boolean                isDouble                      = null;
	private Boolean                isBigDecimal                  = null;
	private Boolean                isCurrency                    = null;
	private Boolean                isByteArray                   = null;
	private Boolean                isString                      = null;
	private Boolean                isModel                       = null;
	private Boolean                hasModel                      = null;
	private Boolean                constrained                   = null;
	private Boolean                nullable                      = null;
	private ConditionType          searchCondition               = null;
	private ConditionOperationType searchConditionOperation      = null;
	private String                 searchPropertyId              = null;
	private SearchType             searchType                    = null;
	private RelationType           relationType                  = null;
	private RelationJoinType       relationJoinType              = null;
	private String                 keyId                         = null;
	private String                 foreignKeyId                  = null;
	private Boolean                cascadeOnSave                 = null;
	private Boolean                cascadeOnDelete               = null;
	private String                 propertiesIds[]               = null;
	private String                 mappedPropertyId              = null;
	private String                 mappedPropertyType            = null;
	private String                 mappedPropertiesIds[]         = null;
	private String                 mappedRelationPropertiesIds[] = null;
	private String                 mappedRelationRepositoryId    = null;
	private String                 formulaExpression             = null;
	private FormulaType            formulaType                   = null;
	private SortOrderType          sortOrder                     = null;
	private ValidationType         validations[]                 = null;
	private String                 validationActions[]           = null;
	private ConditionType          compareCondition              = null;
	private String                 comparePropertyId             = null;
	private Double                 phoneticAccuracy              = null;
	private String                 phoneticPropertyId            = null;
	private String                 regularExpression             = null;
	private Integer                wordCount                     = null;
	private Boolean                useGroupSeparator             = null;
	private Integer                minimumLength                 = null;
	private Integer                maximumLength                 = null;
	private String                 minimumValue                  = null;
	private String                 maximumValue                  = null;
	private Integer                size                          = null;
	private Integer                precision                     = null;
	private String                 pattern                       = null;
	private ContentType            contentTypes[]                = null;
	private Double                 contentSize                   = null;
	private ByteMetricType         contentSizeUnit               = null;
	private String                 contentTypePropertyId         = null;
	private String                 contentFilenamePropertyId     = null;
	private String                 customValidationId            = null;
	private Boolean                persistPattern                = null;
	private InputType              inputType                     = null;
	private String                 dataset                       = null;
	
	public Boolean getFromClass(){
		return fromClass;
	}

	public void setFromClass(Boolean fromClass){
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
	public Boolean isArray(){
		return this.isArray;
	}

	/**
	 * Indicates if the property is an array.
	 *
	 * @return True/False.
	 */
	public Boolean getIsArray(){
		return isArray();
	}

	/**
	 * Defines if the property is an array.
	 *
	 * @param isArray True/False.
	 */
	public void setIsArray(Boolean isArray){
		this.isArray = isArray;
	}

	/**
	 * Indicates if the property is a float number.
	 *
	 * @return True/False.
	 */
	public Boolean isFloat(){
		return this.isFloat;
	}

	/**
	 * Indicates if the property is a float number.
	 *
	 * @return True/False.
	 */
	public Boolean getIsFloat(){
		return isFloat();
	}

	/**
	 * Defines if the property is a float number.
	 *
	 * @param isFloat True/False.
	 */
	public void setIsFloat(Boolean isFloat){
		this.isFloat = isFloat;
				
		if(isFloat != null && isFloat)
			if(getPrecision() == null || getPrecision() == 0)
				setPrecision(Constants.DEFAULT_DECIMAL_PRECISION);
	}

	/**
	 * Indicates if the property is a double number.
	 *
	 * @return True/False.
	 */
	public Boolean isDouble(){
		return this.isDouble;
	}

	/**
	 * Indicates if the property is a double number.
	 *
	 * @return True/False.
	 */
	public Boolean getIsDouble(){
		return isDouble();
	}

	/**
	 * Defines if the property is a float number.
	 *
	 * @param isDouble True/False.
	 */
	public void setIsDouble(Boolean isDouble){
		this.isDouble = isDouble;
		
		if(isDouble != null && isDouble)
			if(getPrecision() == null || getPrecision() == 0)
				setPrecision(Constants.DEFAULT_DECIMAL_PRECISION);
	}

	/**
	 * Indicates if the property is a byte number.
	 *
	 * @return True/False.
	 */
	public Boolean isByte(){
		return this.isByte;
	}

	/**
	 * Indicates if the property is a byte number.
	 *
	 * @return True/False.
	 */
	public Boolean getIsByte(){
		return isByte();
	}

	/**
	 * Defines if the property is a byte number.
	 *
	 * @param isByte True/False.
	 */
	public void setIsByte(Boolean isByte){
		this.isByte = isByte;
	}

	/**
	 * Indicates if the property is a short number.
	 *
	 * @return True/False.
	 */
	public Boolean isShort(){
		return this.isShort;
	}

	/**
	 * Indicates if the property is a short number.
	 *
	 * @return True/False.
	 */
	public Boolean getIsShort(){
		return isShort();
	}

	/**
	 * Defines if the property is a short number.
	 *
	 * @param isShort True/False.
	 */
	public void setIsShort(Boolean isShort){
		this.isShort = isShort;
	}

	/**
	 * Indicates if the property is an integer number.
	 *
	 * @return True/False.
	 */
	public Boolean isInteger(){
		return this.isInteger;
	}

	/**
	 * Indicates if the property is an integer number.
	 *
	 * @return True/False.
	 */
	public Boolean getIsInteger(){
		return isInteger();
	}

	/**
	 * Defines if the property is an integer number.
	 *
	 * @param isInteger True/False.
	 */
	public void setIsInteger(Boolean isInteger){
		this.isInteger = isInteger;
	}

	/**
	 * Indicates if the property is a long number.
	 *
	 * @return True/False.
	 */
	public Boolean isLong(){
		return this.isLong;
	}

	/**
	 * Indicates if the property is a long number.
	 *
	 * @return True/False.
	 */
	public Boolean getIsLong(){
		return isLong();
	}

	/**
	 * Defines if the property is a long number.
	 *
	 * @param isLong True/False.
	 */
	public void setIsLong(Boolean isLong){
		this.isLong = isLong;
	}

	/**
	 * Indicates if the property is a big integer number.
	 *
	 * @return True/False.
	 */
	public Boolean isBigInteger(){
		return this.isBigInteger;
	}

	/**
	 * Indicates if the property is a big integer number.
	 *
	 * @return True/False.
	 */
	public Boolean getIsBigInteger(){
		return isBigInteger();
	}

	/**
	 * Defines if the property is a big integer number.
	 *
	 * @param isBigInteger True/False.
	 */
	public void setIsBigInteger(Boolean isBigInteger){
		this.isBigInteger = isBigInteger;
	}

	/**
	 * Indicates if the property is a big decimal number.
	 *
	 * @return True/False.
	 */
	public Boolean isBigDecimal(){
		return this.isBigDecimal;
	}

	/**
	 * Indicates if the property is a big decimal number.
	 *
	 * @return True/False.
	 */
	public Boolean getIsBigDecimal(){
		return isBigDecimal();
	}

	/**
	 * Defines if the property is a big decimal number.
	 *
	 * @param isBigDecimal True/False.
	 */
	public void setIsBigDecimal(Boolean isBigDecimal){
		this.isBigDecimal = isBigDecimal;
		
		if(isBigDecimal != null && isBigDecimal)
			if(getPrecision() == null || getPrecision() == 0)
				setPrecision(Constants.DEFAULT_DECIMAL_PRECISION);
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
	public Integer getSize(){
		return this.size;
	}

	/**
	 * Defines the size of the property.
	 * 
	 * @param size Numeric value that contains of the size.
	 */
	public void setSize(Integer size){
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
	public Boolean isCurrency(){
		return this.isCurrency;
	}

	/**
	 * Indicates if the property is currency.
	 * 
	 * @return True/False.
	 */
	public Boolean getIsCurrency(){
		return isCurrency();
	}

	/**
	 * Defines if the property is currency.
	 * 
	 * @param isCurrency True/False.
	 */
	public void setIsCurrency(Boolean isCurrency){
		this.isCurrency = isCurrency;
	}

	/**
	 * Returns the precision of the property.
	 * 
	 * @return Numeric value that contains the precision.
	 */
	public Integer getPrecision(){
		return this.precision;
	}

	/**
	 * Defines the precision of the property.
	 * 
	 * @param precision Numeric value that contains the precision.
	 */
	public void setPrecision(Integer precision){
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
	public Boolean useAdditionalFormatting(){
		if(isDate())
			return isTime();
		else if(isNumber())
			return useGroupSeparator();

		return false;
	}

	/**
	 * Returns the property of the data model that defines the class of the
	 * property..
	 * 
	 * @return String that contains the identifier of the property.
	 */
	public String getClassPropertyId(){
		return this.classPropertyId;
	}

	/**
	 * Defines the property of the data model that defines the class of the
	 * property..
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
	public Boolean isNullable(){
		return this.nullable;
	}

	/**
	 * Indicates if the property accepts null.
	 * 
	 * @return True/False.
	 */
	public Boolean getNullable(){
		return isNullable();
	}

	/**
	 * Defines if the property accepts null.
	 * 
	 * @param nullable True/False.
	 */
	public void setNullable(Boolean nullable){
		this.nullable = nullable;
	}

	/**
	 * Indicates if the relationship should be constrained.
	 * 
	 * @return True/False.
	 */
	public Boolean isConstrained(){
		return this.constrained;
	}

	/**
	 * Indicates if the relationship should be constrained.
	 * 
	 * @return True/False.
	 */
	public Boolean getConstrained(){
		return isConstrained();
	}

	/**
	 * Defines if the relationship should be constrained.
	 * 
	 * @param constrained True/False.
	 */
	public void setConstrained(Boolean constrained){
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
	 * Indicates if the relationship has cascade on save.
	 * 
	 * @return True/False.
	 */
	public Boolean cascadeOnSave(){
		return this.cascadeOnSave;
	}

	/**
	 * Indicates if the relationship has cascade on save.
	 * 
	 * @return True/False.
	 */
	public Boolean getCascadeOnSave(){
		return cascadeOnSave();
	}

	/**
	 * Defines if the relationship has cascade on save.
	 * 
	 * @param cascadeOnSave True/False.
	 */
	public void setCascadeOnSave(Boolean cascadeOnSave){
		this.cascadeOnSave = cascadeOnSave;
	}

	/**
	 * Indicates if the relationship has cascade on delete.
	 * 
	 * @return True/False.
	 */
	public Boolean cascadeOnDelete(){
		return this.cascadeOnDelete;
	}

	/**
	 * Indicates if the relationship has cascade on delete.
	 * 
	 * @return True/False.
	 */
	public Boolean getCascadeOnDelete(){
		return cascadeOnDelete();
	}

	/**
	 * Defines if the relationship has cascade on delete.
	 * 
	 * @param cascadeOnDelete True/False.
	 */
	public void setCascadeOnDelete(Boolean cascadeOnDelete){
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
	public Boolean getUseGroupSeparator(){
		return useGroupSeparator();
	}

	/**
	 * Indicates if the key that contains the property is unique.
	 * 
	 * @return True/False.
	 */
	public Boolean isUnique(){
		return this.isUnique;
	}

	/**
	 * Indicates if the key that contains the property is unique.
	 * 
	 * @return True/False.
	 */
	public Boolean getIsUnique(){
		return isUnique();
	}

	/**
	 * Defines if the key that contains the property is unique.
	 * 
	 * @param isUnique True/False.
	 */
	public void setIsUnique(Boolean isUnique){
		this.isUnique = isUnique;
	}

	/**
	 * Indicates if the key that contains the property is serializable.
	 * 
	 * @return True/False.
	 */
	public Boolean isSerializable(){
		return this.isSerializable;
	}

	/**
	 * Indicates if the key that contains the property is serializable.
	 * 
	 * @return True/False.
	 */
	public Boolean getIsSerializable(){
		return isSerializable();
	}

	/**
	 * Defines if the key that contains the property is serializable.
	 * 
	 * @param isSerializable True/False.
	 */
	public void setIsSerializable(Boolean isSerializable){
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
	public void setMappedPropertiesIds(String mappedPropertiesIds[]){
		this.mappedPropertiesIds = mappedPropertiesIds;
	}

	/**
	 * Indicates if the property is a data model.
	 * 
	 * @return True/False.
	 */
	public Boolean isModel(){
		return this.isModel;
	}

	/**
	 * Indicates if the property is a data model.
	 * 
	 * @return True/False.
	 */
	public Boolean getIsModel(){
		return isModel();
	}

	/**
	 * Defines if the property is a data model.
	 * 
	 * @param isModel True/False.
	 */
	public void setIsModel(Boolean isModel){
		this.isModel = isModel;
	}

	/**
	 * Indicates if the property contains data models.
	 * 
	 * @return True/False.
	 */
	public Boolean hasModel(){
		return this.hasModel;
	}

	/**
	 * Indicates if the property contains data models.
	 * 
	 * @return True/False.
	 */
	public Boolean getHasModel(){
		return hasModel();
	}

	/**
	 * Defines if the property contains data models.
	 * 
	 * @param hasModel True/False.
	 */
	public void setHasModel(Boolean hasModel){
		this.hasModel = hasModel;
	}

	/**
	 * Indicates if the property is a date.
	 * 
	 * @return True/False.
	 */
	public Boolean isDate(){
		return this.isDate;
	}

	/**
	 * Indicates if the property is a date.
	 * 
	 * @return True/False.
	 */
	public Boolean getIsDate(){
		return isDate();
	}

	/**
	 * Indicates if the property is a time.
	 * 
	 * @return True/False.
	 */
	public Boolean isTime(){
		return this.isTime;
	}

	/**
	 * Indicates if the property is a time.
	 * 
	 * @return True/False.
	 */
	public Boolean getIsTime(){
		return isTime();
	}

	/**
	 * Indicates if the property is boolean.
	 * 
	 * @return True/False.
	 */
	public Boolean isBoolean(){
		return this.isBoolean;
	}

	/**
	 * Indicates if the property is boolean.
	 * 
	 * @return True/False.
	 */
	public Boolean getIsBoolean(){
		return isBoolean();
	}

	/**
	 * Indicates if the property is a numeric value.
	 * 
	 * @return True/False.
	 */
	public Boolean isNumber(){
		return this.isNumber;
	}

	/**
	 * Indicates if the property is a numeric value.
	 * 
	 * @return True/False.
	 */
	public Boolean getIsNumber(){
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
	public Boolean isIdentity(){
		return this.isIdentity;
	}

	/**
	 * Indicates if the property identifies a data model.
	 * 
	 * @return True/False.
	 */
	public Boolean getIsIdentity(){
		return isIdentity();
	}

	/**
	 * Defines if the property identifies a data model.
	 * 
	 * @param isIdentity True/False.
	 */
	public void setIsIdentity(Boolean isIdentity){
		this.isIdentity = isIdentity;
	}

	/**
	 * Indicates if the property will be considered in the search.
	 * 
	 * @return True/False.
	 */
	public Boolean isForSearch(){
		return this.isForSearch;
	}

	/**
	 * Indicates if the property will be considered in the search.
	 * 
	 * @return True/False.
	 */
	public Boolean getIsForSearch(){
		return isForSearch();
	}

	/**
	 * Defines if the property will be considered in the search.
	 * 
	 * @param isForSearch True/False.
	 */
	public void setIsForSearch(Boolean isForSearch){
		this.isForSearch = isForSearch;
	}

	/**
	 * Returns the minimum length of the property.
	 * 
	 * @return Numeric value that contains the minimum length.
	 */
	public Integer getMinimumLength(){
		return this.minimumLength;
	}

	/**
	 * Defines the minimum length of the property.
	 * 
	 * @param minimumLength Numeric value that contains the minimum length.
	 */
	public void setMinimumLength(Integer minimumLength){
		this.minimumLength = minimumLength;
	}

	/**
	 * Returns the maximum length of the property.
	 * 
	 * @return Numeric value that contains the maximum length.
	 */
	public Integer getMaximumLength(){
		return this.maximumLength;
	}

	/**
	 * Defines the maximum length of the property.
	 * 
	 * @param maximumLength Numeric value that contains the maximum length.
	 */
	public void setMaximumLength(Integer maximumLength){
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
	public Boolean isAuditable(){
		return this.isAuditable;
	}

	/**
	 * Indica if the property will be audited.
	 * 
	 * @return True/False.
	 */
	public Boolean getIsAuditable(){
		return isAuditable();
	}

	/**
	 * Defines if the property will be audited.
	 * 
	 * @param isAuditable True/False.
	 */
	public void setIsAuditable(Boolean isAuditable){
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
	public void setValidations(ValidationType validations[]){
		this.validations = validations;
	}

	/**
	 * Indicates if the property has validation rules.
	 * 
	 * @param validation Instance that contains the validation type.
	 * @return True/False.
	 */
	public Boolean hasValidation(ValidationType validation){
		if(this.validations != null)
			for(int cont = 0 ; cont < this.validations.length ; cont++)
				if(this.validations[cont].equals(validation))
					return true;

		return false;
	}

	/**
	 * Defines if the property is boolean.
	 * 
	 * @param isBoolean True/False.
	 */
	public void setIsBoolean(Boolean isBoolean){
		this.isBoolean = isBoolean;
	}

	/**
	 * Defines if the property is a date.
	 * 
	 * @param isDate True/False.
	 */
	public void setIsDate(Boolean isDate){
		this.isDate = isDate;
	}

	/**
	 * Defines if the property is a time.
	 * 
	 * @param isTime True/False.
	 */
	public void setIsTime(Boolean isTime){
		this.isTime = isTime;
	}

	/**
	 * Defines if the property is a numeric value.
	 * 
	 * @param isNumber True/False.
	 */
	public void setIsNumber(Boolean isNumber){
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
	public Boolean isCollection(){
		return this.isCollection;
	}

	/**
	 * Indicates if the property is a collection.
	 * 
	 * @return True/False.
	 */
	public Boolean getIsCollection(){
		return isCollection();
	}

	/**
	 * Defines if the property is a collection.
	 * 
	 * @param isCollection True/False.
	 */
	public void setIsCollection(Boolean isCollection){
		this.isCollection = isCollection;
	}

	/**
	 * Indicates if the property is a byte array.
	 * 
	 * @return True/False.
	 */
	public Boolean isByteArray(){
		return this.isByteArray;
	}

	/**
	 * Indicates if the property is a byte array.
	 * 
	 * @return True/False.
	 */
	public Boolean getIsByteArray(){
		return isByteArray();
	}

	/**
	 * Defines if the property is a byte array.
	 * 
	 * @param isByteArray True/False.
	 */
	public void setIsByteArray(Boolean isByteArray){
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
	public Boolean isEnum(){
		return this.isEnum;
	}

	/**
	 * Indicates if the property is an enumeration.
	 * 
	 * @return True/False.
	 */
	public Boolean getIsEnum(){
		return isEnum();
	}

	/**
	 * Defines if the property is an enumeration.
	 * 
	 * @param isEnum True/False.
	 */
	public void setIsEnum(Boolean isEnum){
		this.isEnum = isEnum;
	}

	/**
	 * Indicates if the property contains enumerations.
	 * 
	 * @return True/False.
	 */
	public Boolean hasEnum(){
		return this.hasEnum;
	}

	/**
	 * Indicates if the property contains enumerations.
	 * 
	 * @return True/False.
	 */
	public Boolean getHasEnum(){
		return hasEnum();
	}

	/**
	 * Defines if the property contains enumerations.
	 * 
	 * @param hasEnum True/False.
	 */
	public void setHasEnum(Boolean hasEnum){
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
	public Boolean useGroupSeparator(){
		return this.useGroupSeparator;
	}

	/**
	 * Defines if the group separator should be used in the numeric formatting..
	 * 
	 * @param useGroupSeparator True/False.
	 */
	public void setUseGroupSeparator(Boolean useGroupSeparator){
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
	public Boolean persistPattern(){
		return this.persistPattern;
	}

	/**
	 * Returns if the pattern that will be persisted.
	 * 
	 * @return True/False.
	 */
	public Boolean getPersistPattern(){
		return persistPattern();
	}

	/**
	 * Defines if the pattern that will be persisted.
	 * 
	 * @param persistPattern True/False.
	 */
	public void setPersistPattern(Boolean persistPattern){
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
	public Integer getWordCount(){
		return this.wordCount;
	}

	/**
	 * Defines the word count to be used in the validation of the property.
	 * 
	 * @param wordCount Numeric value that contains the word count.
	 */
	public void setWordCount(Integer wordCount){
		this.wordCount = wordCount;
	}

	/**
	 * Indicates if the property is a string.
	 * 
	 * @return True/False.
	 */
	public Boolean isString(){
		return this.isString;
	}

	/**
	 * Indicates if the property is a string.
	 * 
	 * @return True/False.
	 */
	public Boolean getIsString(){
		return isString();
	}

	/**
	 * Defines if the property is a string.
	 * 
	 * @param isString True/False.
	 */
	public void setIsString(Boolean isString){
		this.isString = isString;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object object){
		PropertyInfo comparePropertyInfo = (PropertyInfo)object;

		if(comparePropertyInfo == null)
			return false;

		return (this.id != null && this.id.equals(comparePropertyInfo.getId()));
	}

	/**
	 * @see java.lang.Object#clone()
	 */
	public Object clone() throws CloneNotSupportedException{
		return super.clone();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode(){
		return (this.id != null ? this.id.hashCode() : -1);
	}

	/**
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(PropertyInfo comparePropertyInfo){
		if(comparePropertyInfo == null)
			return -1;

		return (this.id != null ? this.id.compareTo(comparePropertyInfo.getId()) : -1);
	}
}