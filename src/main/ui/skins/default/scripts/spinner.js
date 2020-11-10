/**
 * Defines the UI routines to manipulate the spinner component.
 * 
 * @author fvilarinho
 * @version 3.0.0
 */
 
/**
 * Adds the value.
 * 
 * @param name String that contains the identifier of the spinner component.
 * @param minimumValue Numeric value that contains the minimum value permitted.
 * @param maximumValue Numeric value that contains the maximum value permitted.
 * @param useGroupSeparator Indicates the the mask should use the group separator.
 * @param groupSeparator String that contains the group separator.
 * @param precision Numeric value that contains the decimal precision.
 * @param decimalSeparator String that contains the decimal separator.
 * @param step Numeric value that contains the step to be added.
 */
function addSpinnerValue(name, minimumValue, maximumValue, useGroupSeparator, groupSeparator, precision, decimalSeparator, step){
	var object = getObject(name);
	
	if(object){
		var objectValue = object.value;
		
		if(useGroupSeparator && groupSeparator != "")
			objectValue = replaceAll(objectValue, groupSeparator, "");

		if(precision > 0 && decimalSeparator != "")
			objectValue = replaceAll(objectValue, decimalSeparator, ".");
		
		objectValue = parseFloat(objectValue);
		
		if(isNaN(objectValue))
			objectValue = minimumValue;
		else{
			objectValue += step;
				
			if(maximumValue != null && objectValue > maximumValue)
				return;
		}
			
		object.value = objectValue;
		
		applyNumericMask(object, minimumValue, maximumValue, useGroupSeparator, groupSeparator, precision, decimalSeparator);
		
		if(object.onchange)
			object.onchange();
	}
}
 
/**
 * Subtracts the value.
 * 
 * @param name String that contains the identifier of the spinner component.
 * @param minimumValue Numeric value that contains the minimum value permitted.
 * @param maximumValue Numeric value that contains the maximum value permitted.
 * @param useGroupSeparator Indicates the the mask should use the group separator.
 * @param groupSeparator String that contains the group separator.
 * @param precision Numeric value that contains the decimal precision.
 * @param decimalSeparator String that contains the decimal separator.
 * @param step Numeric value that contains the step to be subtracted.
 */
function subtractSpinnerValue(name, minimumValue, maximumValue, useGroupSeparator, groupSeparator, precision, decimalSeparator, step){
	var object = getObject(name);
	
	if(object){
		var objectValue = object.value;
		
		if(useGroupSeparator && groupSeparator != "")
			objectValue = replaceAll(objectValue, groupSeparator, "");

		if(precision > 0 && decimalSeparator != "")
			objectValue = replaceAll(objectValue, decimalSeparator, ".");
		
		objectValue = parseFloat(objectValue);
		
		if(isNaN(objectValue))
			objectValue = minimumValue;
		else{
			objectValue -= step;
		
			if(minimumValue != null && objectValue < minimumValue)
				return;
		}
		
		object.value = objectValue;
			
		applyNumericMask(object, minimumValue, maximumValue, useGroupSeparator, groupSeparator, precision, decimalSeparator);
		
		if(object.onchange)
			object.onchange();
	}
}