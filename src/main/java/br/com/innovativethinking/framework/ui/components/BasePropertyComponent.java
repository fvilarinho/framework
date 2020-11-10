package br.com.innovativethinking.framework.ui.components;

import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;

import javax.servlet.jsp.JspException;

import br.com.innovativethinking.framework.constants.Constants;
import br.com.innovativethinking.framework.controller.SystemController;
import br.com.innovativethinking.framework.controller.action.types.ActionType;
import br.com.innovativethinking.framework.controller.form.ActionFormController;
import br.com.innovativethinking.framework.controller.form.BaseActionForm;
import br.com.innovativethinking.framework.controller.form.constants.ActionFormMessageConstants;
import br.com.innovativethinking.framework.exceptions.InternalErrorException;
import br.com.innovativethinking.framework.model.BaseModel;
import br.com.innovativethinking.framework.model.helpers.ModelInfo;
import br.com.innovativethinking.framework.model.types.ValidationType;
import br.com.innovativethinking.framework.model.util.ModelUtil;
import br.com.innovativethinking.framework.resources.PropertiesResources;
import br.com.innovativethinking.framework.ui.components.types.EventType;
import br.com.innovativethinking.framework.util.DateTimeUtil;
import br.com.innovativethinking.framework.util.NumberUtil;
import br.com.innovativethinking.framework.util.PropertyUtil;
import br.com.innovativethinking.framework.util.StringUtil;
import br.com.innovativethinking.framework.util.helpers.PropertyInfo;
import br.com.innovativethinking.framework.util.types.AlignmentType;
import br.com.innovativethinking.framework.util.types.InputType;
import br.com.innovativethinking.framework.util.types.PositionType;
import br.com.innovativethinking.framework.util.types.PropertyType;

/**
 * Class that defines the basic implementation for the component that refers a
 * property of a data model.
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
public abstract class BasePropertyComponent extends BaseActionFormComponent{
	private static final long serialVersionUID = -7479880433732396484L;
	
	private static String invalidPropertyMessage = null;

	private Boolean      isNumber                          = null;
	private Boolean      isBoolean                         = null;
	private Boolean      isDate                            = null;
	private Boolean      isTime                            = null;
	private Number       minimumValue                      = null;
	private Number       maximumValue                      = null;
	private Integer      precision                         = null;
	private Boolean      useAdditionalFormatting           = null;
	private String       onChange                          = null;
	private String       onChangeAction                    = null;
	private String       onChangeForward                   = null;
	private String       onChangeUpdateViews               = null;
	private Boolean      onChangeValidateModel             = null;
	private String       onChangeValidateModelProperties   = null;
	private String       onKeyPress                        = null;
	private String       onKeyPressAction                  = null;
	private String       onKeyPressForward                 = null;
	private String       onKeyPressUpdateViews             = null;
	private Boolean      onKeyPressValidateModel           = null;
	private String       onKeyPressValidateModelProperties = null;
	private String       onKeyUp                           = null;
	private String       onKeyUpAction                     = null;
	private String       onKeyUpForward                    = null;
	private String       onKeyUpUpdateViews                = null;
	private Boolean      onKeyUpValidateModel              = null;
	private String       onKeyUpValidateModelProperties    = null;
	private String       onKeyDown                         = null;
	private String       onKeyDownAction                   = null;
	private String       onKeyDownForward                  = null;
	private String       onKeyDownUpdateViews              = null;
	private Boolean      onKeyDownValidateModel            = null;
	private String       onKeyDownValidateModelProperties  = null;
	private String       pattern                           = null;
	private Object       value                             = null;
	private Boolean      readOnly                          = null;
	private Boolean      hasInvalidPropertyDefinition      = null;
	private PropertyInfo propertyInfo                      = null;

	/**
	 * Indicates if the component should render a checkbox.
	 * 
	 * @return True/False.
	 */
	protected Boolean isBoolean(){
		return this.isBoolean;
	}

	/**
	 * Defines if the component should render a checkbox.
	 * 
	 * @param isBoolean True/False.
	 */
	protected void setIsBoolean(Boolean isBoolean){
		this.isBoolean = isBoolean;
	}
	
	/**
	 * Indicates if the component should render a date.
	 * 
	 * @return True/False.
	 */
	protected Boolean isDate(){
		return this.isDate;
	}

	/**
	 * Defines if the component should render a date.
	 * 
	 * @param isDate True/False.
	 */
	protected void setIsDate(Boolean isDate){
		this.isDate = isDate;
	}

	/**
	 * Indicates if the component should render a date/time.
	 * 
	 * @return True/False.
	 */
	protected Boolean isTime(){
		return this.isTime;
	}

	/**
	 * Defines if the component should render a date/time.
	 * 
	 * @param isTime True/False.
	 */
	protected void setIsTime(Boolean isTime){
		this.isTime = isTime;
	}

	/**
	 * Indicates if the component should render a number
	 * 
	 * @return True/False.
	 */
	protected Boolean isNumber(){
		return this.isNumber;
	}

	/**
	 * Defines if the component should render a number
	 * 
	 * @param isNumber True/False.
	 */
	protected void setIsNumber(Boolean isNumber){
		this.isNumber = isNumber;
	}

	/**
	 * Returns the input type.
	 *
	 * @return Instance that contains the input type.
	 */
	protected InputType getInputType(){
		InputType result = null;

		if(this.propertyInfo != null)
			result = this.propertyInfo.getInputType();

		return result;
	}

	/**
	 * Indicates if it is a read only component.
	 * 
	 * @return True/False.
	 */
	public Boolean isReadOnly(){
		return this.readOnly;
	}

	/**
	 * Indicates if it is a read only component.
	 * 
	 * @return True/False.
	 */
	public Boolean getReadOnly(){
		return isReadOnly();
	}

	/**
	 * Defines if it is a read only component.
	 * 
	 * @param readOnly True/False.
	 */
	public void setReadOnly(Boolean readOnly){
		this.readOnly = readOnly;
	}

	/**
	 * Defines the minimum value permitted.
	 * 
	 * @param minimumValue Numeric value that contains the minimum value.
	 */
	public void setMinimumValue(Number minimumValue){
		this.minimumValue = minimumValue;
	}

	/**
	 * Defines the maximum value permitted.
	 * 
	 * @param maximumValue Numeric value that contains the minimum value.
	 */
	public void setMaximumValue(Number maximumValue){
		this.maximumValue = maximumValue;
	}

	/**
	 * @see br.com.innovativethinking.framework.ui.components.BaseActionFormComponent#buildSize()
	 */
	protected void buildSize() throws InternalErrorException{
		super.buildSize();

		Integer size = getSize();

		if(size == null || size == 0){
			if(this.propertyInfo != null){
				size = this.propertyInfo.getSize();

				setSize(size);
			}
		}

		if(size == null || size == 0){
			if(this.isNumber != null && this.isNumber){
				Number minimumValue = getMinimumValue();
				Number maximumValue = getMaximumValue();

				if(minimumValue != null && maximumValue != null){
					Boolean useGroupSeparator = useAdditionalFormatting();
					Integer precision = getPrecision();
					Locale currentLanguage = getCurrentLanguage();
					Integer minimumValueLength = NumberUtil.format(minimumValue, useGroupSeparator, precision, currentLanguage).length();
					Integer maximumValueLength = NumberUtil.format(maximumValue, useGroupSeparator, precision, currentLanguage).length();
	
					size = (minimumValueLength < maximumValueLength ? maximumValueLength : minimumValueLength);

					setSize(size);
				}
			}
			else{
				String pattern = getPattern();

				if(pattern != null && pattern.length() > 0){
					if(size == null || size == 0){
						size = pattern.length();

						if(pattern.contains("a"))
							size += 2;

						setSize(size);
					}
				}
			}
		}

		Integer maximumLength = getMaximumLength();

		if((size == null || size == 0) && maximumLength != null && maximumLength > 0){
			size = getMaximumLength();

			setSize(size);
		}
	}

	/**
	 * @see br.com.innovativethinking.framework.ui.components.BaseActionFormComponent#buildMaximumLength()
	 */
	protected void buildMaximumLength() throws InternalErrorException{
		Integer maximumLength = getMaximumLength();

		if(maximumLength == null || maximumLength == 0){
			if(this.propertyInfo != null){
				maximumLength = this.propertyInfo.getMaximumLength();

				setMaximumLength(maximumLength);
			}
		}
	}

	/**
	 * Returns the event of the change.
	 * 
	 * @return String that contains the event.
	 */
	public String getOnChange(){
		return this.onChange;
	}

	/**
	 * Defines the event of the change.
	 * 
	 * @param onChange String that contains the event.
	 */
	public void setOnChange(String onChange){
		this.onChange = onChange;
	}

	/**
	 * Returns the identifier of the action of the change event.
	 * 
	 * @return String that contains the identifier of the action.
	 */
	public String getOnChangeAction(){
		return this.onChangeAction;
	}

	/**
	 * Defines the identifier of the action of the change event.
	 * 
	 * @param onChangeAction String that contains the identifier of the action.
	 */
	public void setOnChangeAction(String onChangeAction){
		this.onChangeAction = onChangeAction;
	}

	/**
	 * Defines the action of the change event.
	 * 
	 * @param onChangeActionType Instance that contains the action.
	 */
	protected void setOnChangeActionType(ActionType onChangeActionType){
		if(onChangeActionType != null)
			this.onChangeAction = onChangeActionType.getMethod();
		else
			this.onChangeAction = null;
	}

	/**
	 * Returns the identifier of the action forward of the change event.
	 * 
	 * @return String that contains the identifier of the action forward.
	 */
	public String getOnChangeForward(){
		return this.onChangeForward;
	}

	/**
	 * Defines the identifier of the action forward of the on change event.
	 * 
	 * @param onChangeForward String that contains the identifier of the action
	 * forward.
	 */
	public void setOnChangeForward(String onChangeForward){
		this.onChangeForward = onChangeForward;
	}

	/**
	 * Indicates if the data model of the form should be validated on the change
	 * event.
	 * 
	 * @return True/False.
	 */
	public Boolean getOnChangeValidateModel(){
		return this.onChangeValidateModel;
	}

	/**
	 * Defines if the data model of the form should be validated on the change
	 * event.
	 * 
	 * @param onChangeValidateModel True/False.
	 */
	public void setOnChangeValidateModel(Boolean onChangeValidateModel){
		this.onChangeValidateModel = onChangeValidateModel;
	}

	/**
	 * Returns the validation properties of the data model that should be
	 * validated on the change event.
	 * 
	 * @return String that contains the identifiers of the properties.
	 */
	public String getOnChangeValidateModelProperties(){
		return this.onChangeValidateModelProperties;
	}

	/**
	 * Defines the validation properties of the data model that should be
	 * validated on the change event.
	 * 
	 * @param onChangeValidateModelProperties String that contains the
	 * identifiers of the properties.
	 */
	public void setOnChangeValidateModelProperties(String onChangeValidateModelProperties){
		this.onChangeValidateModelProperties = onChangeValidateModelProperties;
	}

	/**
	 * Returns the identifier of the views that should be updated on the change
	 * event.
	 * 
	 * @return String that contains the identifier of the views.
	 */
	public String getOnChangeUpdateViews(){
		return this.onChangeUpdateViews;
	}

	/**
	 * Defines the identifier of the views that should be updated on the change
	 * event.
	 * 
	 * @param onChangeUpdateViews String that contains the identifier of the
	 * views.
	 */
	public void setOnChangeUpdateViews(String onChangeUpdateViews){
		this.onChangeUpdateViews = onChangeUpdateViews;
	}

	/**
	 * Returns the event of the key down.
	 * 
	 * @return String that contains the event.
	 */
	public String getOnKeyDown(){
		return this.onKeyDown;
	}

	/**
	 * Defines the event of the key down.
	 * 
	 * @param onKeyDown String that contains the event.
	 */
	public void setOnKeyDown(String onKeyDown){
		this.onKeyDown = onKeyDown;
	}

	/**
	 * Returns the identifier of the action of the key down event.
	 * 
	 * @return String that contains the identifier of the action.
	 */
	public String getOnKeyDownAction(){
		return this.onKeyDownAction;
	}

	/**
	 * Defines the identifier of the action of the key down event.
	 * 
	 * @param onKeyDownAction String that contains the identifier of the action.
	 */
	public void setOnKeyDownAction(String onKeyDownAction){
		this.onKeyDownAction = onKeyDownAction;
	}

	/**
	 * Defines the identifier of the action of the key down event.
	 * 
	 * @param onKeyDownActionType Instance that contains the action.
	 */
	protected void setOnKeyDownActionType(ActionType onKeyDownActionType){
		if(onKeyDownActionType != null)
			this.onKeyDownAction = onKeyDownActionType.getMethod();
		else
			this.onKeyDownAction = null;
	}

	/**
	 * Returns the identifier of the action forward of the on key down event.
	 * 
	 * @return String that contains the identifier of the action forward.
	 */
	public String getOnKeyDownForward(){
		return this.onKeyDownForward;
	}

	/**
	 * Defines the identifier of the action forward of the on key down event.
	 * 
	 * @param onKeyDownForward String that contains the identifier of the action
	 * forward.
	 */
	public void setOnKeyDownForward(String onKeyDownForward){
		this.onKeyDownForward = onKeyDownForward;
	}

	/**
	 * Indicates if the data model of the form should be validated on the key
	 * down event.
	 * 
	 * @return True/False.
	 */
	public Boolean getOnKeyDownValidateModel(){
		return this.onKeyDownValidateModel;
	}

	/**
	 * Defines if the data model of the form should be validated on the key down
	 * event.
	 * 
	 * @param onKeyDownValidateModel True/False.
	 */
	public void setOnKeyDownValidateModel(Boolean onKeyDownValidateModel){
		this.onKeyDownValidateModel = onKeyDownValidateModel;
	}

	/**
	 * Returns the validation properties of the data model that should be
	 * validated on the key down event.
	 * 
	 * @return String that contains the identifiers of the properties.
	 */
	public String getOnKeyDownValidateModelProperties(){
		return this.onKeyDownValidateModelProperties;
	}

	/**
	 * Defines the validation properties of the data model that should be
	 * validated on the key down event.
	 * 
	 * @param onKeyDownValidateModelProperties String that contains the
	 * identifiers of the properties.
	 */
	public void setOnKeyDownValidateModelProperties(String onKeyDownValidateModelProperties){
		this.onKeyDownValidateModelProperties = onKeyDownValidateModelProperties;
	}

	/**
	 * Returns the identifier of the views that should be downdated on the key
	 * down event.
	 * 
	 * @return String that contains the identifier of the views.
	 */
	public String getOnKeyDownUpdateViews(){
		return this.onKeyDownUpdateViews;
	}

	/**
	 * Defines the identifier of the views that should be downdated on the key
	 * down event.
	 * 
	 * @param onKeyDownUpdateViews String that contains the identifier of the
	 * views.
	 */
	public void setOnKeyDownUpdateViews(String onKeyDownUpdateViews){
		this.onKeyDownUpdateViews = onKeyDownUpdateViews;
	}

	/**
	 * Returns the event of the key up.
	 * 
	 * @return String that contains the event.
	 */
	public String getOnKeyUp(){
		return this.onKeyUp;
	}

	/**
	 * Defines the event of the key up.
	 * 
	 * @param onKeyUp String that contains the event.
	 */
	public void setOnKeyUp(String onKeyUp){
		this.onKeyUp = onKeyUp;
	}

	/**
	 * Returns the identifier of the action of the key up event.
	 * 
	 * @return String that contains the identifier of the action.
	 */
	public String getOnKeyUpAction(){
		return this.onKeyUpAction;
	}

	/**
	 * Defines the identifier of the action of the key up event.
	 * 
	 * @param onKeyUpAction String that contains the identifier of the action.
	 */
	public void setOnKeyUpAction(String onKeyUpAction){
		this.onKeyUpAction = onKeyUpAction;
	}

	/**
	 * Defines the identifier of the action of the key up event.
	 * 
	 * @param onKeyUpActionType Instance that contains the action.
	 */
	protected void setOnKeyUpActionType(ActionType onKeyUpActionType){
		if(onKeyUpActionType != null)
			this.onKeyUpAction = onKeyUpActionType.getMethod();
		else
			this.onKeyUpAction = null;
	}

	/**
	 * Returns the identifier of the action forward of the on key up event.
	 * 
	 * @return String that contains the identifier of the action forward.
	 */
	public String getOnKeyUpForward(){
		return this.onKeyUpForward;
	}

	/**
	 * Defines the identifier of the action forward of the on key up event.
	 * 
	 * @param onKeyUpForward String that contains the identifier of the action
	 * forward.
	 */
	public void setOnKeyUpForward(String onKeyUpForward){
		this.onKeyUpForward = onKeyUpForward;
	}

	/**
	 * Indicates if the data model of the form should be validated on the key up
	 * event.
	 * 
	 * @return True/False.
	 */
	public Boolean getOnKeyUpValidateModel(){
		return this.onKeyUpValidateModel;
	}

	/**
	 * Defines if the data model of the form should be validated on the key up
	 * event.
	 * 
	 * @param onKeyUpValidateModel True/False.
	 */
	public void setOnKeyUpValidateModel(Boolean onKeyUpValidateModel){
		this.onKeyUpValidateModel = onKeyUpValidateModel;
	}

	/**
	 * Returns the validation properties of the data model that should be
	 * validated on the key up event.
	 * 
	 * @return String that contains the identifiers of the properties.
	 */
	public String getOnKeyUpValidateModelProperties(){
		return this.onKeyUpValidateModelProperties;
	}

	/**
	 * Defines the validation properties of the data model that should be
	 * validated on the key up event.
	 * 
	 * @param onKeyUpValidateModelProperties String that contains the
	 * identifiers of the properties.
	 */
	public void setOnKeyUpValidateModelProperties(String onKeyUpValidateModelProperties){
		this.onKeyUpValidateModelProperties = onKeyUpValidateModelProperties;
	}

	/**
	 * Returns the identifier of the views that should be updated on the key up
	 * event.
	 * 
	 * @return String that contains the identifier of the views.
	 */
	public String getOnKeyUpUpdateViews(){
		return this.onKeyUpUpdateViews;
	}

	/**
	 * Defines the identifier of the views that should be updated on the key up
	 * event.
	 * 
	 * @param onKeyUpUpdateViews String that contains the identifier of the
	 * views.
	 */
	public void setOnKeyUpUpdateViews(String onKeyUpUpdateViews){
		this.onKeyUpUpdateViews = onKeyUpUpdateViews;
	}

	/**
	 * Returns the event of the key press.
	 * 
	 * @return String that contains the event.
	 */
	public String getOnKeyPress(){
		return this.onKeyPress;
	}

	/**
	 * Defines the event of the key press.
	 * 
	 * @param onKeyPress String that contains the event.
	 */
	public void setOnKeyPress(String onKeyPress){
		this.onKeyPress = onKeyPress;
	}

	/**
	 * Returns the identifier of the action of the key press event.
	 * 
	 * @return String that contains the identifier of the action.
	 */
	public String getOnKeyPressAction(){
		return this.onKeyPressAction;
	}

	/**
	 * Defines the identifier of the action of the key press event.
	 * 
	 * @param onKeyPressAction String that contains the identifier of the
	 * action.
	 */
	public void setOnKeyPressAction(String onKeyPressAction){
		this.onKeyPressAction = onKeyPressAction;
	}

	/**
	 * Defines the identifier of the action of the key press event.
	 * 
	 * @param onKeyPressActionType Instance that contains the action.
	 */
	protected void setOnKeyPressActionType(ActionType onKeyPressActionType){
		if(onKeyPressActionType != null)
			this.onKeyPressAction = onKeyPressActionType.getMethod();
		else
			this.onKeyPressAction = null;
	}

	/**
	 * Returns the identifier of the action forward of the on key press event.
	 * 
	 * @return String that contains the identifier of the action forward.
	 */
	public String getOnKeyPressForward(){
		return this.onKeyPressForward;
	}

	/**
	 * Defines the identifier of the action forward of the on key press event.
	 * 
	 * @param onKeyPressForward String that contains the identifier of the
	 * action forward.
	 */
	public void setOnKeyPressForward(String onKeyPressForward){
		this.onKeyPressForward = onKeyPressForward;
	}

	/**
	 * Indicates if the data model of the form should be validated on the key
	 * press event.
	 * 
	 * @return True/False.
	 */
	public Boolean getOnKeyPressValidateModel(){
		return this.onKeyPressValidateModel;
	}

	/**
	 * Defines if the data model of the form should be validated on the key
	 * press event.
	 * 
	 * @param onKeyPressValidateModel True/False.
	 */
	public void setOnKeyPressValidateModel(Boolean onKeyPressValidateModel){
		this.onKeyPressValidateModel = onKeyPressValidateModel;
	}

	/**
	 * Returns the validation properties of the data model that should be
	 * validated on the key press event.
	 * 
	 * @return String that contains the identifiers of the properties.
	 */
	public String getOnKeyPressValidateModelProperties(){
		return this.onKeyPressValidateModelProperties;
	}

	/**
	 * Defines the validation properties of the data model that should be
	 * validated on the key press event.
	 * 
	 * @param onKeyPressValidateModelProperties String that contains the
	 * identifiers of the properties.
	 */
	public void setOnKeyPressValidateModelProperties(String onKeyPressValidateModelProperties){
		this.onKeyPressValidateModelProperties = onKeyPressValidateModelProperties;
	}

	/**
	 * Returns the identifier of the views that should be updated on the key
	 * press event.
	 * 
	 * @return String that contains the identifier of the views.
	 */
	public String getOnKeyPressUpdateViews(){
		return this.onKeyPressUpdateViews;
	}

	/**
	 * Defines the identifier of the views that should be updated on the key
	 * press event.
	 * 
	 * @param onKeyPressUpdateViews String that contains the identifier of the
	 * views.
	 */
	public void setOnKeyPressUpdateViews(String onKeyPressUpdateViews){
		this.onKeyPressUpdateViews = onKeyPressUpdateViews;
	}

	/**
	 * Returns the instance that contains the attributes of the property of the
	 * data model.
	 * 
	 * @return Instance that contains the attributes of the property of the data
	 * model.
	 */
	protected PropertyInfo getPropertyInfo(){
		return this.propertyInfo;
	}

	/**
	 * Defines the instance that contains the attributes of the property of the
	 * data model.
	 * 
	 * @param propertyInfo Instance that contains the attributes of the property
	 * of the data model.
	 */
	protected void setPropertyInfo(PropertyInfo propertyInfo){
		this.propertyInfo = propertyInfo;
	}

	/**
	 * Returns a formatting pattern of the component.
	 * 
	 * @return String that contains the pattern.
	 * @throws InternalErrorException Occurs when was not possible to execute the operation.
	 */
	protected String getPattern() throws InternalErrorException{
		if(this.pattern == null || this.pattern.length() == 0)
			if(this.propertyInfo != null)
				this.pattern = this.propertyInfo.getPattern();

		if(this.pattern == null || this.pattern.length() == 0){
			if(this.isDate != null && this.isDate){
				Locale currentLanguage = getCurrentLanguage();

				if(this.isTime != null && this.isTime)
					this.pattern = DateTimeUtil.getDefaultDateTimePattern(currentLanguage);
				else
					this.pattern = DateTimeUtil.getDefaultDatePattern(currentLanguage);
			}
		}

		return this.pattern;
	}

	/**
	 * Defines a formatting pattern of the component.
	 * 
	 * @param pattern String that contains the pattern.
	 */
	public void setPattern(String pattern){
		this.pattern = pattern;
	}

	/**
	 * Returns the instance that contains the value of the component.
	 *
	 * @param <O> Class of the value of the component.
	 * @return Instance that contains the value of the component.
	 */
	@SuppressWarnings("unchecked")
	public <O> O getValue(){
		return (O)this.value;
	}

	/**
	 * Defines the instance that contains the value of the component.
	 * 
	 * @param <O> Class of the value of the component.
	 * @param value Instance that contains the value of the component.
	 */
	public <O> void setValue(O value){
		this.value = value;
	}

	/**
	 * Indicates if the component has an invalid property definition.
	 * 
	 * @return True/False.
	 */
	protected Boolean hasInvalidPropertyDefinition(){
		return this.hasInvalidPropertyDefinition;
	}

	/**
	 * Defines if the component has an invalid property definition.
	 * 
	 * @param hasInvalidPropertyDefinition True/False.
	 */
	protected void setHasInvalidPropertyDefinition(Boolean hasInvalidPropertyDefinition){
		this.hasInvalidPropertyDefinition = hasInvalidPropertyDefinition;
	}

	/**
	 * Returns the message that defines that the property is invalid.
	 * 
	 * @return String that contains the message.
	 */
	protected static String getInvalidPropertyMessage(){
		return invalidPropertyMessage;
	}

	/**
	 * Indicates if additional formatting should be used.
	 * 
	 * @return True/False.
	 */
	protected Boolean useAdditionalFormatting(){
		if(this.propertyInfo != null && this.useAdditionalFormatting == null)
			this.useAdditionalFormatting = this.propertyInfo.useAdditionalFormatting();
		
		return this.useAdditionalFormatting;
	}
	
	/**
	 * Defines if additional formatting should be used.
	 * 
	 * @param useAdditionalFormatting True/False.
	 */
	protected void setUseAdditionalFormatting(Boolean useAdditionalFormatting){
		this.useAdditionalFormatting = useAdditionalFormatting;
	}

	/**
	 * Returns the precision for numeric formatting.
	 * 
	 * @return Numeric value that contains the precision.
	 */
	protected Integer getPrecision(){
		if(this.propertyInfo != null && this.precision == null)
			this.precision = this.propertyInfo.getPrecision();

		return this.precision;
	}
	
	/**
	 * Defines the precision for numeric formatting.
	 * 
	 * @param precision Numeric value that contains the precision.
	 */
	protected void setPrecision(Integer precision){
		this.precision = precision;
	}

	/**
	 * Returns the maximum value that should be considered.
	 * 
	 * @param <N> Class that defines the numeric value.
	 * @return Numeric value that contains the maximum value.
	 */
	@SuppressWarnings("unchecked")
	protected <N extends Number> N getMaximumValue(){
		return (N)this.maximumValue;
	}

	/**
	 * Returns the minimum value that should be considered.
	 * 
	 * @param <N> Class that defines the numeric value.
	 * @return Numeric value that contains the minimum value.
	 */
	@SuppressWarnings("unchecked")
	protected <N extends Number> N getMinimumValue(){
		return (N)this.minimumValue;
	}

	/**
	 * Returns the formatted value.
	 * 
	 * @return String that contains the formatted value.
	 * @throws InternalErrorException Occurs when was not possible to execute the operation.
	 */
	protected String getFormattedValue() throws InternalErrorException{
		PropertyInfo propertyInfo = getPropertyInfo();
		
		if(propertyInfo != null && propertyInfo.isEnum())
			return ((Enum<?>)this.value).name();
		
		return PropertyUtil.format(this.value, getPattern(), useAdditionalFormatting(), getPrecision(), getCurrentLanguage());
	}

	/**
	 * Indicates if the component is used for searching.
	 * 
	 * @return True/False.
	 */
	protected Boolean isForSearch(){
		return (getSearchPropertiesGroupComponent() != null);
	}

	/**
	 * Returns the instance of the search properties group component.
	 * 
	 * @return Instance that contains the component of the search properties group
	 * component.
	 */
	protected SearchPropertiesGroupComponent getSearchPropertiesGroupComponent(){
		SearchPropertiesGroupComponent searchPropertiesGroup = (SearchPropertiesGroupComponent)findAncestorWithClass(this, SearchPropertiesGroupComponent.class);

		if(searchPropertiesGroup == null){
			try{
				searchPropertiesGroup = (SearchPropertiesGroupComponent)getParent();
			}
			catch(ClassCastException e){
			}
		}

		return searchPropertiesGroup;
	}

	/**
	 * @see br.com.innovativethinking.framework.ui.components.BaseActionFormComponent#buildStyle()
	 */
	protected void buildStyle() throws InternalErrorException{
		super.buildStyle();

		AlignmentType alignment = getAlignmentType();
		InputType input = getInputType();

		if(alignment != null || input != InputType.NONE){
			String currentStyle = getStyle();
			StringBuilder style = null;

			if(currentStyle != null && currentStyle.length() > 0){
				style = new StringBuilder();
				style.append(currentStyle);

				if(!currentStyle.endsWith(";"))
					style.append(";");
			}

			if(alignment != null){
				if(style == null)
					style = new StringBuilder();
				else
					style.append(" ");

				style.append("text-align: ");
				style.append(alignment);
				style.append(";");

				if(input != null && input != InputType.NONE){
					style.append(" text-transform: ");
					style.append(input);
					style.append(";");
				}   
			}
			else if(input != null && input != InputType.NONE){
				if(style == null)
					style = new StringBuilder();
				else
					style.append(" ");

				style.append("text-transform: ");
				style.append(input);
				style.append(";");
			}

			if(style != null)
				setStyle(style.toString());
		}
	}
	
	/**
	 * @see br.com.innovativethinking.framework.ui.components.BasePropertyComponent#buildEvents()
	 */
	protected void buildEvents() throws InternalErrorException{
		String pattern = getPattern();
		Boolean useAdditionalFormatting = useAdditionalFormatting();
		Integer precision = getPrecision();
		String currentOnKeyPressContent = getOnKeyPress();
		StringBuilder onKeyPressContent = null;

		if(this.isNumber != null && this.isNumber){
			DecimalFormatSymbols symbols = NumberUtil.getFormatSymbols(getCurrentLanguage());

			onKeyPressContent = new StringBuilder();
			
			if(currentOnKeyPressContent == null || !currentOnKeyPressContent.contains("applyNumericMask")){
				onKeyPressContent.append("applyNumericMask(this, ");
				onKeyPressContent.append((this.minimumValue != null ? this.minimumValue.toString() : Constants.DEFAULT_NULL_ID));
				onKeyPressContent.append(", ");
				onKeyPressContent.append((this.maximumValue != null ? this.maximumValue.toString() : Constants.DEFAULT_NULL_ID));
				onKeyPressContent.append(", ");
				onKeyPressContent.append((useAdditionalFormatting != null ? useAdditionalFormatting : Constants.DEFAULT_NULL_ID));
				onKeyPressContent.append(", '");
				onKeyPressContent.append((useAdditionalFormatting != null && useAdditionalFormatting ? symbols.getGroupingSeparator() : ""));
				onKeyPressContent.append("', ");
				onKeyPressContent.append((precision != null ? precision : Constants.DEFAULT_NULL_ID));
				onKeyPressContent.append(", '");
				onKeyPressContent.append((precision != null && precision > 0 ? symbols.getDecimalSeparator() : ""));
				onKeyPressContent.append("', event);");
			}
		}
		else if(pattern != null && pattern.length() > 0){
			onKeyPressContent = new StringBuilder();

			if(this.isDate != null && this.isDate){
				if(currentOnKeyPressContent == null || !currentOnKeyPressContent.contains("applyDateTimeMask"))
					onKeyPressContent.append("applyDateTimeMask(this, event);");
			}
			else{
				if(currentOnKeyPressContent == null || !currentOnKeyPressContent.contains("applyGenericMask"))
					onKeyPressContent.append("applyGenericMask(this, event);");
			}
		}

		if(currentOnKeyPressContent != null && currentOnKeyPressContent.length() > 0){
			if(onKeyPressContent == null) 
				onKeyPressContent = new StringBuilder();
			else if(onKeyPressContent.length() > 0)
				onKeyPressContent.append(" ");

			onKeyPressContent.append(currentOnKeyPressContent);

			if(!currentOnKeyPressContent.endsWith(";"))
				onKeyPressContent.append(";");
		}

		if(onKeyPressContent != null && onKeyPressContent.length() > 0)
			setOnKeyPress(onKeyPressContent.toString());

		buildEvent(EventType.ON_CHANGE);
		buildEvent(EventType.ON_KEY_PRESS);
		buildEvent(EventType.ON_KEY_UP);
		buildEvent(EventType.ON_KEY_DOWN);

		super.buildEvents();
	}

	/**
	 * @see br.com.innovativethinking.framework.ui.components.BaseActionFormComponent#buildAlignment()
	 */
	protected void buildAlignment() throws InternalErrorException{
		AlignmentType alignment = getAlignmentType();

		if(alignment == null){
			if(this.isNumber != null && this.isNumber)
				alignment = AlignmentType.RIGHT;
			else if((this.isDate != null && this.isDate) || (this.isBoolean != null && this.isBoolean))
				alignment = AlignmentType.CENTER;

			setAlignmentType(alignment);
		}

		AlignmentType labelAlignment = getLabelAlignmentType();

		if(labelAlignment == null){
			if(getLabelPositionType() == PositionType.TOP){
				labelAlignment = AlignmentType.LEFT;

				setLabelAlignmentType(labelAlignment);
			}
		}

		super.buildAlignment();
	}

	/**
	 * @see br.com.innovativethinking.framework.ui.components.BaseActionFormComponent#buildResources()
	 */
	protected void buildResources() throws InternalErrorException{
		if(invalidPropertyMessage == null){
			PropertiesResources resources = getDefaultResources();
	
			if(resources != null){
				invalidPropertyMessage = resources.getProperty(ActionFormMessageConstants.DEFAULT_INVALID_PROPERTY_ID);
				invalidPropertyMessage = PropertyUtil.fillPropertiesInString(this, invalidPropertyMessage);
				invalidPropertyMessage = PropertyUtil.fillResourcesInString(resources, invalidPropertyMessage);
			}
		}

		super.buildResources();
	}

	/**
	 * @see br.com.innovativethinking.framework.ui.components.BaseActionFormComponent#buildRestrictions()
	 */
	@SuppressWarnings("unchecked")
	protected void buildRestrictions() throws InternalErrorException{
		if(this.propertyInfo != null){
			if(this.isNumber == null)
				this.isNumber = this.propertyInfo.isNumber();
			
			if(this.isBoolean == null)
				this.isBoolean = this.propertyInfo.isBoolean();

			if(this.isDate == null)
				this.isDate = this.propertyInfo.isDate();
			
			if(this.isTime == null)
				this.isTime = this.propertyInfo.isTime();
		}

		if(this.isNumber != null && this.isNumber){
			if(this.propertyInfo != null){
				if(this.minimumValue == null){
					Class<? extends Number> propertyClass = (Class<? extends Number>)this.propertyInfo.getClazz();

					if(this.propertyInfo.getMinimumValue() != null && this.propertyInfo.getMinimumValue().length() > 0){
						try{
							this.minimumValue = NumberUtil.parse(propertyClass, this.propertyInfo.getMinimumValue());
						}
						catch(ParseException e){
						}
					}
				}

				if(this.maximumValue == null){
					Class<? extends Number> propertyClass = (Class<? extends Number>)this.propertyInfo.getClazz();

					if(this.propertyInfo.getMaximumValue() != null && this.propertyInfo.getMaximumValue().length() > 0){
						try{
							this.maximumValue = NumberUtil.parse(propertyClass, this.propertyInfo.getMaximumValue());
						}
						catch(ParseException e){
						}
					}
				}
			}
		}

		super.buildRestrictions();
	}

	/**
	 * @see br.com.innovativethinking.framework.ui.components.BaseComponent#initialize()
	 */
	protected void initialize() throws InternalErrorException{
		SystemController systemController = getSystemController();
		ActionFormController actionFormController = getActionFormController();
		String actionFormName = getActionFormName();
		String name = getName();

		if(systemController != null && actionFormController != null && actionFormName != null && actionFormName.length() > 0 && name != null && name.length() > 0 && this.propertyInfo == null && this.value == null){
			PropertyType propertyType = PropertyUtil.getType(name);

			if(propertyType == PropertyType.MODEL){
				BaseActionForm<? extends BaseModel> actionFormInstance = actionFormController.getActionFormInstance();
				BaseModel model = (actionFormInstance != null ? actionFormInstance.getModel() : null);

				if(model != null){
					try{
						Class<? extends BaseModel> modelClass = model.getClass();
						ModelInfo modelInfo = ModelUtil.getInfo(modelClass);

						this.propertyInfo = modelInfo.getPropertyInfo(name);

						if(this.propertyInfo != null){
							if(actionFormController.hasValidationMessage(name))
								this.value = systemController.getRequestParameterValue(name);
							else{
								try{
									this.value = PropertyUtil.getValue(model, name);
								} 
								catch(IllegalAccessException | InvocationTargetException | NoSuchMethodException e){
									throw new InternalErrorException(e);
								}
							}

							this.hasInvalidPropertyDefinition = false;
						}
						else{
							this.value = null;
							this.hasInvalidPropertyDefinition = true;
						}
					}
					catch(NoSuchFieldException | IllegalArgumentException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException | ClassNotFoundException e){
						this.propertyInfo = null;
						this.value = null;
						this.hasInvalidPropertyDefinition = true;
					}
				}
				else{
					this.propertyInfo = null;
					this.value = null;
					this.hasInvalidPropertyDefinition = true;
				}
			}
		}

		super.initialize();
	}

	/**
	 * Renders the attribute that defines the label of the component.
	 * 
	 * @throws InternalErrorException Occurs when was not possible to render.
	 */
	protected void renderLabelAttribute() throws InternalErrorException{
		BaseOptionsPropertyComponent optionsPropertyComponent = null;

		try{
			optionsPropertyComponent = (BaseOptionsPropertyComponent)getParent();
		}
		catch(ClassCastException e){
		}

		Boolean enabled = isEnabled();

		if(enabled != null && enabled && this.propertyInfo != null && optionsPropertyComponent == null)
			super.renderLabelAttribute();
	}

	/**
	 * Renders the attribute that defines the formatting pattern of the
	 * component.
	 * 
	 * @throws InternalErrorException Occurs when was not possible to render.
	 */
	protected void renderPatternAttribute() throws InternalErrorException{
		String actionFormName = getActionFormName();
		String id = getId();
		String name = getName();
		Boolean enabled = isEnabled();
		String pattern = getPattern();
		BaseOptionsPropertyComponent optionsPropertyComponent = null;

		try{
			optionsPropertyComponent = (BaseOptionsPropertyComponent)getParent();
		}
		catch(ClassCastException e){
		}

		if(actionFormName != null && actionFormName.length() > 0 && id != null && id.length() > 0 && name != null && name.length() > 0 && enabled != null && enabled && pattern != null && pattern.length() > 0 && this.propertyInfo != null && optionsPropertyComponent == null){
			StringBuilder idBuffer = new StringBuilder();

			idBuffer.append(id);
			idBuffer.append(".");
			idBuffer.append(Constants.PATTERN_ATTRIBUTE_ID);

			StringBuilder nameBuffer = new StringBuilder();

			nameBuffer.append(name);
			nameBuffer.append(".");
			nameBuffer.append(Constants.PATTERN_ATTRIBUTE_ID);

			HiddenPropertyComponent patternPropertyComponent = new HiddenPropertyComponent();

			patternPropertyComponent.setPageContext(this.pageContext);
			patternPropertyComponent.setOutputStream(getOutputStream());
			patternPropertyComponent.setActionFormName(actionFormName);
			patternPropertyComponent.setId(idBuffer.toString());
			patternPropertyComponent.setName(nameBuffer.toString());
			patternPropertyComponent.setValue(pattern);

			try{
				patternPropertyComponent.doStartTag();
				patternPropertyComponent.doEndTag();
			}
			catch(JspException e){
				throw new InternalErrorException(e);
			}
		}
	}

	/**
	 * @see br.com.innovativethinking.framework.ui.components.BaseActionFormComponent#renderOpen()
	 */
	protected void renderOpen() throws InternalErrorException{
		renderLabelAttribute();
		renderPatternAttribute();

		super.renderOpen();
	}

	/**
	 * Renders the invalid property message.
	 * 
	 * @throws InternalErrorException Occurs when was not possible to render.
	 */
	protected void renderInvalidPropertyMessage() throws InternalErrorException{
		String labelStyleClass = getLabelStyleClass();

		print("<span");

		if(labelStyleClass != null && labelStyleClass.length() > 0){
			print(" class=\"");
			print(labelStyleClass);
			print("\"");
		}

		String labelStyle = getLabelStyle();

		if(labelStyle != null && labelStyle.length() > 0){
			print(" style=\"");
			print(labelStyle);

			if(!labelStyle.endsWith(";"))
				print(";");

			print("\"");
		}

		print(">");
		print(getInvalidPropertyMessage());
		println("</span>");
	}

	/**
	 * @see br.com.innovativethinking.framework.ui.components.BaseActionFormComponent#renderBody()
	 */
	protected void renderBody() throws InternalErrorException{
		Boolean hasInvalidPropertyDefinition = hasInvalidPropertyDefinition();

		if(hasInvalidPropertyDefinition != null && hasInvalidPropertyDefinition)
			renderInvalidPropertyMessage();
		else
			super.renderBody();
	}

	/**
	 * Renders the value of the component.
	 * 
	 * @throws InternalErrorException Occurs when was not possible to render.
	 */
	protected void renderValue() throws InternalErrorException{
		String formattedValue = getFormattedValue();

		print(" value=\"");

		if(formattedValue != null && formattedValue.length() > 0)
			print(formattedValue);

		print("\"");
	}

	/**
	 * @see br.com.innovativethinking.framework.ui.components.BaseComponent#renderAttributes()
	 */
	protected void renderAttributes() throws InternalErrorException{
		super.renderAttributes();

		renderPlaceholder();
		renderValue();
		renderReadOnly();
	}

	/**
	 * Renders the read-only attribute.
	 * 
	 * @throws InternalErrorException Occurs when was not possible to render.
	 */
	protected void renderReadOnly() throws InternalErrorException{
		if(this.readOnly != null && this.readOnly)
			print(" readOnly");
	}

	/**
	 * Renders the placeholder attribute.
	 * 
	 * @throws InternalErrorException Occurs when was not possible to render.
	 */
	protected void renderPlaceholder() throws InternalErrorException{
		PositionType labelPosition = getLabelPositionType();
		Boolean showLabel = showLabel();
		String label = getLabel();

		if(showLabel != null && showLabel && label != null && label.length() > 0 && labelPosition == PositionType.INSIDE){
			print(" placeholder=\"");
			print(label);
			print("\"");
		}
	}

	/**
	 * @see br.com.innovativethinking.framework.ui.components.BaseComponent#renderEvents()
	 */
	protected void renderEvents() throws InternalErrorException{
		super.renderEvents();

		if(this.onKeyPress != null && this.onKeyPress.length() > 0){
			print(" onKeyPress=\"");
			print(this.onKeyPress);
			print("\"");
		}

		if(this.onKeyDown != null && this.onKeyDown.length() > 0){
			print(" onKeyDown=\"");
			print(this.onKeyDown);
			print("\"");
		}

		if(this.onKeyUp != null && this.onKeyUp.length() > 0){
			print(" onKeyUp=\"");
			print(this.onKeyUp);
			print("\"");
		}

		if(this.onChange != null && this.onChange.length() > 0){
			print(" onChange=\"");
			print(this.onChange);
			print("\"");
		}
	}

	/**
	 * Indicates if the require mark should be render.
	 * 
	 * @return True/False
	 */
	protected Boolean renderRequiredMark(){
		Boolean isEnabled = isEnabled();
		Boolean isReadOnly = isReadOnly();

		return (isEnabled != null && isEnabled && (isReadOnly == null || !isReadOnly));
	}

	/**
	 * @see br.com.innovativethinking.framework.ui.components.BaseActionFormComponent#renderLabelBody()
	 */
	protected void renderLabelBody() throws InternalErrorException{
		String name = getName();
		Boolean showLabel = showLabel();
		String label = getLabel();

		if(name != null && name.length() > 0 && showLabel != null && showLabel && label != null && label.length() > 0 && renderRequiredMark()){
			SearchPropertiesGroupComponent searchPropertiesGroupComponent = getSearchPropertiesGroupComponent();
			String validationModelPropertiesBuffer[] = (searchPropertiesGroupComponent != null ? StringUtil.split(searchPropertiesGroupComponent.getValidateModelProperties()) : null);
			Collection<String> validationModelProperties = (validationModelPropertiesBuffer != null && validationModelPropertiesBuffer.length > 0 ? Arrays.asList(validationModelPropertiesBuffer) : null);
			ValidationType validations[] = (this.propertyInfo != null ? this.propertyInfo.getValidations() : null);

			if(validations != null && validations.length > 0 && ((validationModelProperties != null && validationModelProperties.size() > 0 && validationModelProperties.contains(name)) || (searchPropertiesGroupComponent == null && (validationModelProperties == null || validationModelProperties.size() == 0)))){
				for(ValidationType validation : validations){
					if(validation == ValidationType.REQUIRED){
						println("(*) ");

						break;
					}
				}
			}
		}

		super.renderLabelBody();
	}

	/**
	 * @see br.com.innovativethinking.framework.ui.components.BaseComponent#clearAttributes()
	 */
	protected void clearAttributes() throws InternalErrorException{
		super.clearAttributes();

		setIsNumber(null);
		setIsBoolean(null);
		setIsDate(null);
		setIsTime(null);
		setMaximumLength(null);
		setSize(null);
		setMinimumValue(null);
		setMaximumValue(null);
		setUseAdditionalFormatting(null);
		setPrecision(null);
		setOnChange(null);
		setOnChangeAction(null);
		setOnChangeForward(null);
		setOnChangeUpdateViews(null);
		setOnChangeValidateModel(null);
		setOnChangeValidateModelProperties(null);
		setOnKeyPress(null);
		setOnKeyPressAction(null);
		setOnKeyPressForward(null);
		setOnKeyPressUpdateViews(null);
		setOnKeyPressValidateModel(null);
		setOnKeyPressValidateModelProperties(null);
		setOnKeyDown(null);
		setOnKeyDownAction(null);
		setOnKeyDownForward(null);
		setOnKeyDownUpdateViews(null);
		setOnKeyDownValidateModel(null);
		setOnKeyDownValidateModelProperties(null);
		setOnKeyUp(null);
		setOnKeyUpAction(null);
		setOnKeyUpForward(null);
		setOnKeyUpUpdateViews(null);
		setOnKeyUpValidateModel(null);
		setOnKeyUpValidateModelProperties(null);
		setPattern(null);
		setFocus(null);
		setValue(null);
		setHasInvalidPropertyDefinition(null);
		setPropertyInfo(null);
	}
}