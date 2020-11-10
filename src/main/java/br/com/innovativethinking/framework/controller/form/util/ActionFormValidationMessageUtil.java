package br.com.innovativethinking.framework.controller.form.util;

import br.com.innovativethinking.framework.constants.Constants;
import br.com.innovativethinking.framework.controller.form.constants.ActionFormValidationConstants;
import br.com.innovativethinking.framework.controller.form.helpers.ValidationActionFormMessage;
import br.com.innovativethinking.framework.model.types.ConditionType;
import br.com.innovativethinking.framework.model.types.ValidationType;
import br.com.innovativethinking.framework.util.ByteUtil;
import br.com.innovativethinking.framework.util.types.ByteMetricType;
import br.com.innovativethinking.framework.util.types.ContentType;

/**
 * Class responsible to manipulate validation messages.
 * 
 * @author fvilarinho
 * @since 3.0.0
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
public class ActionFormValidationMessageUtil{
	/**
	 * Creates a content type validation message.
	 * 
	 * @param propertyName String that contains the identifier of the property.
	 * @param propertyLabel String that contains the propertyLabel of the property.
	 * @param propertyContentType Instance that contains the content type of the property.
	 * @param propertyContentTypes Array that contains the permitted content types.
	 * @return Instance that contains the validation message.
	 */
	public static ValidationActionFormMessage createContentTypeValidationMessage(String propertyName, String propertyLabel, ContentType propertyContentType, ContentType propertyContentTypes[]){
		if(propertyName != null && propertyName.length() > 0 && propertyLabel != null && propertyLabel.length() > 0 && propertyContentType != null && propertyContentTypes != null && propertyContentTypes.length > 0){
			ValidationActionFormMessage validationFormMessage = new ValidationActionFormMessage(ValidationType.CONTENT_TYPE);

			validationFormMessage.addAttribute(Constants.NAME_ATTRIBUTE_ID, propertyName);
			validationFormMessage.addAttribute(Constants.LABEL_ATTRIBUTE_ID, propertyLabel);
			validationFormMessage.addAttribute(Constants.CONTENT_TYPE_ATTRIBUTE_ID, propertyContentType.toString());

			StringBuilder contentTypesBuffer = new StringBuilder();

			for(int cont = 0 ; cont < propertyContentTypes.length ; cont++){
				propertyContentType = propertyContentTypes[cont];

				if(cont > 0)
					contentTypesBuffer.append(", ");

				contentTypesBuffer.append(propertyContentType.toString());
			}

			validationFormMessage.addAttribute(Constants.CONTENT_TYPES_ATTRIBUTE_ID, contentTypesBuffer.toString());

			return validationFormMessage;
		}

		return null;
	}

	/**
	 * Creates a content size validation message.
	 * 
	 * @param propertyName String that contains the identifier of the property.
	 * @param propertyLabel String that contains the propertyLabel of the property.
	 * @param propertyContentSize Numeric value that contains the size.
	 * @param propertyContentSizeUnit Instance that contains the size unit.
	 * @return Instance that contains the validation message.
	 */
	public static ValidationActionFormMessage createContentSizeValidationMessage(String propertyName, String propertyLabel, Double propertyContentSize, ByteMetricType propertyContentSizeUnit){
		if(propertyName != null && propertyName.length() > 0 && propertyLabel != null && propertyLabel.length() > 0 && propertyContentSize != null && propertyContentSizeUnit != null){
			ValidationActionFormMessage validationFormMessage = new ValidationActionFormMessage(ValidationType.CONTENT_SIZE);

			validationFormMessage.addAttribute(Constants.NAME_ATTRIBUTE_ID, propertyName);
			validationFormMessage.addAttribute(Constants.LABEL_ATTRIBUTE_ID, propertyLabel);
			validationFormMessage.addAttribute(Constants.CONTENT_SIZE_ATTRIBUTE_ID, ByteUtil.formatBytes(propertyContentSize, propertyContentSizeUnit));

			return validationFormMessage;
		}

		return null;
	}

	/**
	 * Creates a property required validation message.
	 * 
	 * @param propertyName String that contains the identifier of the property.
	 * @param propertyLabel String that contains the propertyLabel of the property.
	 * @return Instance that contains the validation message.
	 */
	public static ValidationActionFormMessage createRequiredValidationMessage(String propertyName, String propertyLabel){
		if(propertyName != null && propertyName.length() > 0 && propertyLabel != null && propertyLabel.length() > 0){
			ValidationActionFormMessage validationFormMessage = new ValidationActionFormMessage(ValidationType.REQUIRED);

			validationFormMessage.addAttribute(Constants.NAME_ATTRIBUTE_ID, propertyName);
			validationFormMessage.addAttribute(Constants.LABEL_ATTRIBUTE_ID, propertyLabel);

			return validationFormMessage;
		}

		return null;
	}

	/**
	 * Creates a date/time validation message.
	 * 
	 * @param propertyName String that contains the identifier of the property.
	 * @param propertyLabel String that contains the propertyLabel of the property.
	 * @param propertyPattern String that contains the propertyPattern of the property.
	 * @return Instance that contains the validation message.
	 */
	public static ValidationActionFormMessage createDateTimeValidationMessage(String propertyName, String propertyLabel, String propertyPattern){
		if(propertyName != null && propertyName.length() > 0 && propertyLabel != null && propertyLabel.length() > 0 && propertyPattern != null && propertyPattern.length() > 0){
			ValidationActionFormMessage validationFormMessage = new ValidationActionFormMessage(ValidationType.DATE_TIME);

			validationFormMessage.addAttribute(Constants.NAME_ATTRIBUTE_ID, propertyName);
			validationFormMessage.addAttribute(Constants.LABEL_ATTRIBUTE_ID, propertyLabel);
			validationFormMessage.addAttribute(Constants.PATTERN_ATTRIBUTE_ID, propertyPattern);

			return validationFormMessage;
		}

		return null;
	}

	/**
	 * Creates a numeric validation message.
	 * 
	 * @param propertyName String that contains the identifier of the property.
	 * @param propertyLabel String that contains the propertyLabel of the property.
	 * @return Instance that contains the validation message.
	 */
	public static ValidationActionFormMessage createNumberValidationMessage(String propertyName, String propertyLabel){
		if(propertyName != null && propertyName.length() > 0 && propertyLabel != null && propertyLabel.length() > 0){
			ValidationActionFormMessage validationFormMessage = new ValidationActionFormMessage(ValidationType.NUMBER);

			validationFormMessage.addAttribute(Constants.NAME_ATTRIBUTE_ID, propertyName);
			validationFormMessage.addAttribute(Constants.LABEL_ATTRIBUTE_ID, propertyLabel);

			return validationFormMessage;
		}

		return null;
	}

	/**
	 * Creates a comparison validation message.
	 * 
	 * @param propertyName String that contains the identifier of the property.
	 * @param propertyLabel String that contains the propertyLabel of the property.
	 * @param propertyCompareCondition Instance that contains the comparison type.
	 * @param comparePropertyName String that contains the identifier of the comparison
	 * property.
	 * @param propertyCompareLabel String that contains the propertyLabel of the comparison
	 * property.
	 * @return Instance that contains the validation message.
	 */
	public static ValidationActionFormMessage createCompareValidationMessage(String propertyName, String propertyLabel, ConditionType propertyCompareCondition, String comparePropertyName, String propertyCompareLabel){
		if(propertyName != null && propertyName.length() > 0 && propertyLabel != null && propertyName.length() > 0 && propertyCompareCondition != null && comparePropertyName != null && comparePropertyName.length() > 0 && propertyCompareLabel != null && propertyCompareLabel.length() > 0){
			ValidationActionFormMessage validationFormMessage = new ValidationActionFormMessage(ValidationType.COMPARE);

			validationFormMessage.addAttribute(Constants.NAME_ATTRIBUTE_ID, propertyName);
			validationFormMessage.addAttribute(Constants.LABEL_ATTRIBUTE_ID, propertyLabel);
			validationFormMessage.addAttribute(ActionFormValidationConstants.COMPARE_CONDITION_ATTRIBUTE_ID, propertyCompareCondition.getOperator());
			validationFormMessage.addAttribute(ActionFormValidationConstants.COMPARE_NAME_ATTRIBUTE_ID, comparePropertyName);
			validationFormMessage.addAttribute(ActionFormValidationConstants.COMPARE_LABEL_ATTRIBUTE_ID, propertyCompareLabel);

			return validationFormMessage;
		}

		return null;
	}

	/**
	 * Creates a word count validation message.
	 * 
	 * @param propertyName String that contains the identifier of the property.
	 * @param propertyLabel String that contains the propertyLabel of the property.
	 * @param propertyWordCount Numeric value that contains the word count.
	 * @return Instance that contains the validation message.
	 */
	public static ValidationActionFormMessage createWordCountValidationMessage(String propertyName, String propertyLabel, Integer propertyWordCount){
		if(propertyName != null && propertyName.length() > 0 && propertyLabel != null && propertyName.length() > 0 && propertyWordCount != null){
			ValidationActionFormMessage validationFormMessage = new ValidationActionFormMessage(ValidationType.WORD_COUNT);

			validationFormMessage.addAttribute(Constants.NAME_ATTRIBUTE_ID, propertyName);
			validationFormMessage.addAttribute(Constants.LABEL_ATTRIBUTE_ID, propertyLabel);
			validationFormMessage.addAttribute(ActionFormValidationConstants.WORD_COUNT_ATTRIBUTE_ID, propertyWordCount);

			return validationFormMessage;
		}

		return null;
	}

	/**
	 * Creates a minimum length validation message.
	 * 
	 * @param propertyName String that contains the identifier of the property.
	 * @param propertyLabel String that contains the propertyLabel of the property.
	 * @param propertyMinimumLength Numeric value that contains the minimum length.
	 * @return Instance that contains the validation message.
	 */
	public static ValidationActionFormMessage createMinimumLengthValidationMessage(String propertyName, String propertyLabel, Integer propertyMinimumLength){
		if(propertyName != null && propertyName.length() > 0 && propertyLabel != null && propertyName.length() > 0 && propertyMinimumLength != null){
			ValidationActionFormMessage validationFormMessage = new ValidationActionFormMessage(ValidationType.MINIMUM_LENGTH);

			validationFormMessage.addAttribute(Constants.NAME_ATTRIBUTE_ID, propertyName);
			validationFormMessage.addAttribute(Constants.LABEL_ATTRIBUTE_ID, propertyLabel);
			validationFormMessage.addAttribute(ActionFormValidationConstants.MINIMUM_LENGTH_ATTRIBUTE_ID, propertyMinimumLength);

			return validationFormMessage;
		}

		return null;
	}

	/**
	 * Creates a maximum length validation message.
	 * 
	 * @param propertyName String that contains the identifier of the property.
	 * @param propertyLabel String that contains the propertyLabel of the property.
	 * @param propertyMaximumLength Numeric value that contains the maximum length.
	 * @return Instance that contains the validation message.
	 */
	public static ValidationActionFormMessage createMaximumLengthValidationMessage(String propertyName, String propertyLabel, Integer propertyMaximumLength){
		if(propertyName != null && propertyName.length() > 0 && propertyLabel != null && propertyName.length() > 0 && propertyMaximumLength != null){
			ValidationActionFormMessage validationFormMessage = new ValidationActionFormMessage(ValidationType.MAXIMUM_LENGTH);

			validationFormMessage.addAttribute(Constants.NAME_ATTRIBUTE_ID, propertyName);
			validationFormMessage.addAttribute(Constants.LABEL_ATTRIBUTE_ID, propertyLabel);
			validationFormMessage.addAttribute(ActionFormValidationConstants.MAXIMUM_LENGTH_ATTRIBUTE_ID, propertyMaximumLength);

			return validationFormMessage;
		}

		return null;
	}

	/**
	 * Creates a regular expression validation message.
	 * 
	 * @param propertyName String that contains the identifier of the property.
	 * @param propertyLabel String that contains the propertyLabel of the property.
	 * @param propertyRegex String that contains the regular expression.
	 * @return Instance that contains the validation message.
	 */
	public static ValidationActionFormMessage createRegularExpressionValidationMessage(String propertyName, String propertyLabel, String propertyRegex){
		if(propertyName != null && propertyName.length() > 0 && propertyLabel != null && propertyName.length() > 0 && propertyRegex != null && propertyRegex.length() > 0){
			ValidationActionFormMessage validationFormMessage = new ValidationActionFormMessage(ValidationType.REGULAR_EXPRESSION);

			validationFormMessage.addAttribute(Constants.NAME_ATTRIBUTE_ID, propertyName);
			validationFormMessage.addAttribute(Constants.LABEL_ATTRIBUTE_ID, propertyLabel);
			validationFormMessage.addAttribute(ActionFormValidationConstants.REGULAR_EXPRESSION_ATTRIBUTE_ID, propertyRegex);

			return validationFormMessage;
		}

		return null;
	}

	/**
	 * Creates a e-Mail validation message.
	 * 
	 * @param propertyName String that contains the identifier of the property.
	 * @param propertyLabel String that contains the propertyLabel of the property.
	 * @return Instance that contains the validation message.
	 */
	public static ValidationActionFormMessage createEmailValidationMessage(String propertyName, String propertyLabel){
		if(propertyName != null && propertyName.length() > 0 && propertyLabel != null && propertyLabel.length() > 0){
			ValidationActionFormMessage validationFormMessage = new ValidationActionFormMessage(ValidationType.EMAIL);

			validationFormMessage.addAttribute(Constants.NAME_ATTRIBUTE_ID, propertyName);
			validationFormMessage.addAttribute(Constants.LABEL_ATTRIBUTE_ID, propertyLabel);

			return validationFormMessage;
		}

		return null;
	}

	/**
	 * Creates an e-Mail validation message.
	 * 
	 * @param propertyName String that contains the identifier of the property.
	 * @param propertyLabel String that contains the propertyLabel of the property.
	 * @param propertyPattern String that contains the propertyPattern of the property.
	 * @return Instance that contains the validation message.
	 */
	public static ValidationActionFormMessage createPatternValidationMessage(String propertyName, String propertyLabel, String propertyPattern){
		if(propertyName != null && propertyName.length() > 0 && propertyLabel != null && propertyLabel.length() > 0 && propertyPattern != null && propertyPattern.length() > 0){
			ValidationActionFormMessage validationFormMessage = new ValidationActionFormMessage(ValidationType.PATTERN);

			validationFormMessage.addAttribute(Constants.NAME_ATTRIBUTE_ID, propertyName);
			validationFormMessage.addAttribute(Constants.LABEL_ATTRIBUTE_ID, propertyLabel);
			validationFormMessage.addAttribute(Constants.PATTERN_ATTRIBUTE_ID, propertyPattern);

			return validationFormMessage;
		}

		return null;
	}

	/**
	 * Creates a numeric range validation message.
	 * 
	 * @param propertyName String that contains the identifier of the property.
	 * @param propertyLabel String that contains the propertyLabel of the property.
	 * @param minimumValue Numeric value that contains the start range.
	 * @param maximumValue Numeric value that contains the end range.
	 * @return Instance that contains the validation message.
	 */
	public static ValidationActionFormMessage createRangeValidationMessage(String propertyName, String propertyLabel, Object minimumValue, Object maximumValue){
		if(propertyName != null && propertyName.length() > 0 && propertyLabel != null && propertyLabel.length() > 0 && minimumValue != null && maximumValue != null){
			ValidationActionFormMessage validationFormMessage = new ValidationActionFormMessage(ValidationType.RANGE);

			validationFormMessage.addAttribute(Constants.NAME_ATTRIBUTE_ID, propertyName);
			validationFormMessage.addAttribute(Constants.LABEL_ATTRIBUTE_ID, propertyLabel);
			validationFormMessage.addAttribute(ActionFormValidationConstants.MINIMUM_VALUE_ATTRIBUTE_ID, minimumValue);
			validationFormMessage.addAttribute(ActionFormValidationConstants.MAXIMUM_VALUE_ATTRIBUTE_ID, maximumValue);

			return validationFormMessage;
		}

		return null;
	}

	/**
	 * Creates a custom validation message.
	 *
	 * @param validation String that contains the identifier of the custom
	 * message.
	 * @param propertyName String that contains the identifier of the property.
	 * @param propertyLabel String that contains the propertyLabel of the property.
	 * @return Instance that contains the validation message.
	 */
	public static ValidationActionFormMessage createCustomValidationMessage(String validation, String propertyName, String propertyLabel){
		if(validation != null && validation.length() > 0 && propertyName != null && propertyName.length() > 0 && propertyLabel != null && propertyLabel.length() > 0){
			ValidationActionFormMessage validationFormMessage = new ValidationActionFormMessage(validation);

			validationFormMessage.addAttribute(Constants.NAME_ATTRIBUTE_ID, propertyName);

			return validationFormMessage;
		}

		return null;
	}
}