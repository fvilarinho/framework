package br.com.concepting.framework.ui.components;

import javax.servlet.jsp.JspException;

import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.controller.form.constants.ActionFormMessageConstants;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.resources.PropertiesResources;
import br.com.concepting.framework.util.helpers.PropertyInfo;
import br.com.concepting.framework.util.types.ComponentType;

/**
 * Class that defines the list component.
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
public class ListPropertyComponent extends OptionsPropertyComponent{
	private static final long serialVersionUID = 1973970093371979505L;

	private Boolean showFirstOption         = null;
	private String  firstOptionResourcesKey = null;
	private String  firstOptionLabel        = null;

	/**
	 * Returns the label of the first option.
	 * 
	 * @return String that contains the label.
	 */
	public String getFirstOptionLabel(){
		return this.firstOptionLabel;
	}

	/**
	 * Defines the label of the first option.
	 * 
	 * @param firstOptionLabel String that contains the label.
	 */
	public void setFirstOptionLabel(String firstOptionLabel){
		this.firstOptionLabel = firstOptionLabel;
	}

	/**
	 * Returns the key of the i18n property for the label of the first option.
	 *
	 * @return String that contains the key of the property.
	 */
	public String getFirstOptionResourcesKey(){
		return this.firstOptionResourcesKey;
	}

	/**
	 * Defines the key of the i18n property for the label of the first option.
	 *
	 * @param firstOptionResourcesKey String that contains the key of the
	 * property.
	 */
	public void setFirstOptionResourcesKey(String firstOptionResourcesKey){
		this.firstOptionResourcesKey = firstOptionResourcesKey;
	}

	/**
	 * Indicates if the first option should be shown.
	 * 
	 * @return True/False.
	 */
	public Boolean isShowFirstOption(){
		return this.showFirstOption;
	}

	/**
	 * Indicates if the first option should be shown.
	 * 
	 * @return True/False.
	 */
	public Boolean getShowFirstOption(){
		return isShowFirstOption();
	}

	/**
	 * Defines if the first option should be shown.
	 * 
	 * @param showFirstOption True/False.
	 */
	public void setShowFirstOption(Boolean showFirstOption){
		this.showFirstOption = showFirstOption;
	}

	/**
	 * @see br.com.concepting.framework.ui.components.OptionsPropertyComponent#getOptionComponent()
	 */
	protected BaseOptionPropertyComponent getOptionComponent(){
		return new ListOptionPropertyComponent();
	}

	/**
	 * @see br.com.concepting.framework.ui.components.BaseOptionsPropertyComponent#buildRestrictions()
	 */
	protected void buildRestrictions() throws InternalErrorException{
		super.buildRestrictions();

		if(this.showFirstOption == null){
			Integer size = getSize();

			this.showFirstOption = (size == null || size <= 1);

			if(this.showFirstOption){
				Boolean hasMultipleSelection = hasMultipleSelection();

				this.showFirstOption = (hasMultipleSelection == null || !hasMultipleSelection);
			}
		}
	}

	/**
	 * @see br.com.concepting.framework.ui.components.BasePropertyComponent#buildResources()
	 */
	protected void buildResources() throws InternalErrorException{
		super.buildResources();

		if(this.showFirstOption != null && this.showFirstOption && (this.firstOptionLabel == null || this.firstOptionLabel.length() == 0)){
			if(this.firstOptionResourcesKey == null || this.firstOptionResourcesKey.length() == 0)
				this.firstOptionResourcesKey = ActionFormMessageConstants.DEFAULT_SELECT_AN_ITEM_ID;

			PropertyInfo propertyInfo = getPropertyInfo();

			if(propertyInfo != null){
				PropertiesResources resources = getResources();
				PropertiesResources defaultResources = getDefaultResources();

				this.firstOptionLabel = (resources != null ? resources.getProperty(this.firstOptionResourcesKey, false) : null);

				if(this.firstOptionLabel == null)
					this.firstOptionLabel = (defaultResources != null ? defaultResources.getProperty(this.firstOptionResourcesKey) : null);
			}
		}
	}

	/**
	 * @see br.com.concepting.framework.ui.components.BasePropertyComponent#buildSize()
	 */
	protected void buildSize() throws InternalErrorException{
		Integer size = getSize();

		if(size == null || size == 0){
			PropertyInfo propertyInfo = getPropertyInfo();

			if(propertyInfo != null){
				size = propertyInfo.getSize();

				setSize(size);
			}
		}
	}

	/**
	 * @see br.com.concepting.framework.ui.components.BasePropertyComponent#buildMaximumLength()
	 */
	protected void buildMaximumLength() throws InternalErrorException{
	}

	/**
	 * @see br.com.concepting.framework.ui.components.OptionsPropertyComponent#initialize()
	 */
	protected void initialize() throws InternalErrorException{
		setComponentType(ComponentType.LIST);

		super.initialize();
	}

	/**
	 * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#renderType()
	 */
	protected void renderType() throws InternalErrorException{
	}

	/**
	 * @see br.com.concepting.framework.ui.components.BasePropertyComponent#renderValue()
	 */
	protected void renderValue() throws InternalErrorException{
	}

	/**
	 * @see br.com.concepting.framework.ui.components.BaseComponent#renderAttributes()
	 */
	protected void renderAttributes() throws InternalErrorException{
		super.renderAttributes();

		Boolean multipleSelection = hasMultipleSelection();

		if(multipleSelection != null && multipleSelection)
			print(" multiple");

		Boolean enabled = isEnabled();

		if(enabled != null && !enabled){
			print(" ");
			print(Constants.DISABLED_ATTRIBUTE_ID);
		}
	}

	/**
	 * @see br.com.concepting.framework.ui.components.OptionsPropertyComponent#renderOpenGroup()
	 */
	protected void renderOpenGroup() throws InternalErrorException{
	}

	/**
	 * @see br.com.concepting.framework.ui.components.OptionsPropertyComponent#renderCloseGroup()
	 */
	protected void renderCloseGroup() throws InternalErrorException{
	}

	/**
	 * @see br.com.concepting.framework.ui.components.OptionsPropertyComponent#renderOptionsPanelOpen()
	 */
	protected void renderOptionsPanelOpen() throws InternalErrorException{
	}

	/**
	 * @see br.com.concepting.framework.ui.components.OptionsPropertyComponent#renderOptionsPanelClose()
	 */
	protected void renderOptionsPanelClose() throws InternalErrorException{
	}

	/**
	 * @see br.com.concepting.framework.ui.components.OptionsPropertyComponent#renderOptionPanelRowOpen()
	 */
	protected void renderOptionPanelRowOpen() throws InternalErrorException{
	}

	/**
	 * @see br.com.concepting.framework.ui.components.OptionsPropertyComponent#renderOptionPanelRowClose(java.lang.Integer)
	 */
	protected void renderOptionPanelRowClose(Integer row) throws InternalErrorException{
	}

	/**
	 * @see br.com.concepting.framework.ui.components.OptionsPropertyComponent#renderBody()
	 */
	protected void renderBody() throws InternalErrorException{
		Boolean hasInvalidPropertyDefinition = hasInvalidPropertyDefinition();

		if(hasInvalidPropertyDefinition != null && hasInvalidPropertyDefinition)
			super.renderInvalidPropertyMessage();
		else{
			print("<select");

			renderAttributes();

			println(">");

			renderOptions();

			println("</select>");
		}
	}

	/**
	 * @see br.com.concepting.framework.ui.components.OptionsPropertyComponent#renderOptions()
	 */
	protected void renderOptions() throws InternalErrorException{
		String name = getName();

		if(this.showFirstOption != null && this.showFirstOption && this.firstOptionLabel != null && this.firstOptionLabel.length() > 0 && name != null && name.length() > 0){
			ListOptionPropertyComponent firstOptionComponent = new ListOptionPropertyComponent();

			firstOptionComponent.setPageContext(this.pageContext);
			firstOptionComponent.setOutputStream(getOutputStream());
			firstOptionComponent.setName(name);
			firstOptionComponent.setLabel(this.firstOptionLabel);
			firstOptionComponent.setSelected(true);

			try{
				firstOptionComponent.doStartTag();
				firstOptionComponent.doEndTag();
			}
			catch(JspException e){
				throw new InternalErrorException(e);
			}
		}

		super.renderOptions();
	}

	/**
	 * @see br.com.concepting.framework.ui.components.OptionsPropertyComponent#clearAttributes()
	 */
	protected void clearAttributes() throws InternalErrorException{
		super.clearAttributes();

		setShowFirstOption(null);
		setFirstOptionResourcesKey(null);
		setFirstOptionLabel(null);
	}
}