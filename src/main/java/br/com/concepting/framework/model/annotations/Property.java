package br.com.concepting.framework.model.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.com.concepting.framework.model.types.ConditionOperationType;
import br.com.concepting.framework.model.types.ConditionType;
import br.com.concepting.framework.model.types.ValidationType;
import br.com.concepting.framework.persistence.types.RelationJoinType;
import br.com.concepting.framework.persistence.types.RelationType;
import br.com.concepting.framework.util.types.ByteMetricType;
import br.com.concepting.framework.util.types.ContentType;
import br.com.concepting.framework.util.types.FormulaType;
import br.com.concepting.framework.util.types.InputType;
import br.com.concepting.framework.util.types.SearchType;
import br.com.concepting.framework.util.types.SortOrderType;

/**
 * Class that defines the annotation of a property of the data model.
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
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Property{
	/**
	 * Defines the identifier of the property.
	 * 
	 * @return String that contains the identifier.
	 */
	String propertyId() default "";

	/**
	 * Defines the properties mappings.
	 * 
	 * @return List that contains the properties mappings.
	 */
	String[] propertiesIds() default {};
	
	/**
	 * Defines the property type identifier.
	 * 
	 * @return String that contains the property type identifier.
	 */
	String propertyTypeId() default "";

	/**
	 * Indicates if the property is the identifier of the data model.
	 * 
	 * @return True/False.
	 */
	boolean isIdentity() default false;

	/**
	 * Defines the identifier sequence of a data model.
	 * 
	 * @return String that contains the identifier sequence.
	 */
	String sequenceId() default "";

	/**
	 * Indicates if the property is unique in the data model.
	 *
	 * @return True/False.
	 */
	boolean isUnique() default false;
	
	/**
	 * Indicates if the property is serializable in the data model.
	 *
	 * @return True/False.
	 */
	boolean isSerializable() default false;

	/**
	 * Defines the key of property of the data model.
	 * 
	 * @return String that contains the key.
	 */
	String keyId() default "";

	/**
	 * Defines the foreign key of property of the data model.
	 * 
	 * @return String that contains the foreign key.
	 */
	String foreignKeyId() default "";

	/**
	 * Defines the property mapping.
	 * 
	 * @return String that contains the mapping.
	 */
	String mappedPropertyId() default "";

	/**
	 * Defines the type of the mapping of the property.
	 * 
	 * @return String that contains the type of mapping.
	 */
	String mappedPropertyType() default "";

	/**
	 * Indicates if the property accepts null values.
	 * 
	 * @return True/False.
	 */
	boolean nullable() default true;

	/**
	 * Defines the formula expression of the property.
	 * 
	 * @return String that contains the formula expression.
	 */
	String formulaExpression() default "";

	/**
	 * Defines the formula type of the property.
	 * 
	 * @return Instance that contains the formula type.
	 */
	FormulaType formulaType() default FormulaType.NONE;

	/**
	 * Defines the sort type of the property.
	 * 
	 * @return Instance that contains the sort type.
	 */
	SortOrderType sortOrder() default SortOrderType.NONE;

	/**
	 * Defines the relationship type.
	 * 
	 * @return Instance that contains the relationship type.
	 */
	RelationType relationType() default RelationType.NONE;

	/**
	 * Defines the relationship join type.
	 * 
	 * @return Instance that contains the relationship join type.
	 */
	RelationJoinType relationJoinType() default RelationJoinType.NONE;

	/**
	 * Defines the class of the relationship of the property.
	 * 
	 * @return Class of the relationship.
	 */
	Class<?> relationClass() default Object.class;

	/**
	 * Defines the class of a relationship NxN or 1xN.
	 * 
	 * @return Class of the relationship.
	 */
	Class<?> relationCollectionItemsClass() default Object.class;

	/**
	 * Defines the properties mappings.
	 * 
	 * @return List that contains the properties mappings.
	 */
	String[] mappedPropertiesIds() default {};

	/**
	 * Defines the relationship properties.
	 * 
	 * @return List that contains the relationship properties.
	 */
	String[] mappedRelationPropertiesIds() default {};

	/**
	 * Defines the relationship repository.
	 * 
	 * @return String that contains the relationship repository.
	 */
	String mappedRelationRepositoryId() default "";

	/**
	 * Indicates if the cascade should the done on save operations.
	 *
	 * @return True/False.
	 */
	boolean cascadeOnSave() default false;

	/**
	 * Indicates if the cascade should the done on delete operations.
	 *
	 * @return True/False.
	 */
	boolean cascadeOnDelete() default false;

	/**
	 * Indicates if the relationship of the property of the data model is
	 * constrained.
	 * 
	 * @return True/False.
	 */
	boolean constrained() default true;

	/**
	 * Indicates if the property is used in search queries.
	 * 
	 * @return True/False.
	 */
	boolean isForSearch() default false;

	/**
	 * Defines the type of the search condition of the property.
	 * 
	 * @return Instance that contains the type of the search condition.
	 */
	ConditionType searchCondition() default ConditionType.NONE;

	/**
	 * Defines the type of the search condition operation of the property.
	 * 
	 * @return Instance that contains the type of the search condition operation.
	 */
	ConditionOperationType searchConditionOperation() default ConditionOperationType.NONE;

	/**
	 * Indicates if the search will be done considering case sensitive or insensitive.
	 * 
	 * @return Instance that contains the search type.
	 */
	SearchType searchType() default SearchType.NONE;

	/**
	 * Defines the identifier of the search property.
	 * 
	 * @return String that contains the identifier.
	 */
	String searchPropertyId() default "";

	/**
	 * Defines the identifier of the phonetic property.
	 * 
	 * @return String that contains the identifier.
	 */
	String phoneticPropertyId() default "";

	/**
	 * Defines the accuracy percentage to be considered in the phonetic search.
	 * 
	 * @return Numeric value that contains the accuracy percentage.
	 */
	double phoneticAccuracy() default 0d;

	/**
	 * Defines the identifier of the comparison property.
	 * 
	 * @return String that contains the identifier.
	 */
	String comparePropertyId() default "";

	/**
	 * Defines the compare condition of the property.
	 * 
	 * @return Instance that contains the compare condition.
	 */
	ConditionType compareCondition() default ConditionType.NONE;

	/**
	 * Defines the types of the validations of the property.
	 * 
	 * @return List that contains the types of the validations.
	 */
	ValidationType[] validations() default {ValidationType.NONE};

	/**
	 * Defines the identifiers of the validation actions.
	 * 
	 * @return List that contains the identifiers.
	 */
	String[] validationActions() default {};

	/**
	 * Returns the content types to be used in the validation.
	 * 
	 * @return Array that contains the content types.
	 */
	ContentType[] contentTypes() default ContentType.NONE;

	/**
	 * Returns the content size to be used in the validation.
	 * 
	 * @return Numeric value that contains the content size.
	 */
	double contentSize() default 0d;

	/**
	 * Returns the content size unit to be used in the validation.
	 * 
	 * @return Instance that contains the content size unit.
	 */
	ByteMetricType contentSizeUnit() default ByteMetricType.BYTE;

	/**
	 * Returns the identifier of the property that stores the content type.
	 * 
	 * @return String that contains the identifier of the property.
	 */
	String contentTypePropertyId() default "";

	/**
	 * Returns the identifier of the property that stores the content filename.
	 * 
	 * @return String that contains the identifier of the property.
	 */
	String contentFilenamePropertyId() default "";

	/**
	 * Defines the identifier of the custom validation.
	 * 
	 * @return String that contains the identifier.
	 */
	String customValidationId() default "";

	/**
	 * Returns the regular expression to be used in the validation.
	 * 
	 * @return String that contains the regular expression.
	 */
	String regularExpression() default "";

	/**
	 * Defines the number of words to be used in the validation.
	 * 
	 * @return Numeric value that contains the number of words.
	 */
	int wordCount() default 0;

	/**
	 * Defines the size of the property.
	 * 
	 * @return Numeric value that contains of the size.
	 */
	int size() default 0;

	/**
	 * Defines the minimum number of characters to be used in the validation.
	 * 
	 * @return Numeric value that contains the minimum number of characters.
	 */
	int minimumLength() default 0;

	/**
	 * Defines the maximum number of characters to be used in the validation.
	 * 
	 * @return Numeric value that contains the maximum number of characters.
	 */
	int maximumLength() default 0;

	/**
	 * Defines the minimum value to be used in the range validation.
	 * 
	 * @return String that contains the minimum value.
	 */
	String minimumValue() default "";

	/**
	 * Defines the maximum value to be used in the range validation.
	 * 
	 * @return String that contains the maximum value.
	 */
	String maximumValue() default "";

	/**
	 * Indicates if the group separator should be used in the numeric
	 * formatting.
	 * 
	 * @return True/False.
	 */
	boolean useGroupSeparator() default false;

	/**
	 * Defines the precision of the property.
	 * 
	 * @return Numeric value that contains the precision.
	 */
	int precision() default 0;

	/**
	 * Defines the pattern to be used in the validation and formatting.
	 * 
	 * @return String that contains the pattern.
	 */
	String pattern() default "";

	/**
	 * Indicates if the pattern should be persisted.
	 * 
	 * @return True/False.
	 */
	boolean persistPattern() default true;

	/**
	 * Indicates if the input should be in upper/lower case or capitalized.
	 *
	 * @return Instance that contains the input type.
	 */
	InputType inputType() default InputType.NONE;

	/**
	 * Defines the identifier of the property dataset.
	 * 
	 * @return String that contains the identifier.
	 */
	String dataset() default "";
}