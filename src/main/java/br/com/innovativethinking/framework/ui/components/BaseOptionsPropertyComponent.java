package br.com.innovativethinking.framework.ui.components;

import java.util.Collection;

import javax.servlet.jsp.JspException;

import br.com.innovativethinking.framework.constants.Constants;
import br.com.innovativethinking.framework.controller.SystemController;
import br.com.innovativethinking.framework.controller.form.constants.ActionFormConstants;
import br.com.innovativethinking.framework.controller.form.constants.ActionFormMessageConstants;
import br.com.innovativethinking.framework.controller.types.ScopeType;
import br.com.innovativethinking.framework.exceptions.InternalErrorException;
import br.com.innovativethinking.framework.model.constants.ModelConstants;
import br.com.innovativethinking.framework.resources.PropertiesResources;
import br.com.innovativethinking.framework.ui.constants.UIConstants;
import br.com.innovativethinking.framework.util.PropertyUtil;
import br.com.innovativethinking.framework.util.helpers.PropertyInfo;

/**
 * Class that defines the basic implementation for the options component.
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
public abstract class BaseOptionsPropertyComponent extends BasePropertyComponent{
	private static final long serialVersionUID = -5223346768919041691L;

	private static String noDatasetMessage = null;

	private String                           optionLabelStyle       = null;
	private String                           optionLabelStyleClass  = null;
	private Boolean                          multipleSelection      = null;
	private Collection<OptionStateComponent> optionStatesComponents = null;
	private String                           dataset                = null;
	private String                           datasetScope           = null;
	private Collection<?>                    datasetValues          = null;
	private Integer                          datasetStartIndex      = null;
	private Integer                          datasetEndIndex        = null;
	private Boolean                          hasNoDataset           = null;

	/**
	 * Returns the CSS style for the label of the options.
	 * 
	 * @return String that contains the CSS style for the label.
	 */
	public String getOptionLabelStyle(){
		return this.optionLabelStyle;
	}

	/**
	 * Defines the CSS style for the label of the options.
	 * 
	 * @param optionLabelStyle String that contains the CSS style for the label.
	 */
	public void setOptionLabelStyle(String optionLabelStyle){
		this.optionLabelStyle = optionLabelStyle;
	}

	/**
	 * Returns the CSS style for the label of the options.
	 * 
	 * @return String that contains the CSS style for the label.
	 */
	public String getOptionLabelStyleClass(){
		return this.optionLabelStyleClass;
	}

	/**
	 * Defines the CSS style for the label of the options.
	 * 
	 * @param optionLabelStyleClass String that contains the CSS style for the
	 * label.
	 */
	public void setOptionLabelStyleClass(String optionLabelStyleClass){
		this.optionLabelStyleClass = optionLabelStyleClass;
	}

	/**
	 * Returns the start index of the dataset.
	 * 
	 * @return Numeric value that contains the start index.
	 */
	protected Integer getDatasetStartIndex(){
		return this.datasetStartIndex;
	}

	/**
	 * Defines the start index of the dataset.
	 * 
	 * @param datasetStartIndex Numeric value that contains the start index.
	 */
	protected void setDatasetStartIndex(Integer datasetStartIndex){
		this.datasetStartIndex = datasetStartIndex;
	}

	/**
	 * Returns the end index of the dataset.
	 * 
	 * @return Numeric value that contains the end index.
	 */
	protected Integer getDatasetEndIndex(){
		return this.datasetEndIndex;
	}

	/**
	 * Defines the end index of the dataset.
	 * 
	 * @param datasetEndIndex Numeric value that contains the end index.
	 */
	protected void setDatasetEndIndex(Integer datasetEndIndex){
		this.datasetEndIndex = datasetEndIndex;
	}

	/**
	 * Defines the list that contains the criteria to render the options.
	 *
	 * @param optionStatesComponents List that contains the criteria.
	 */
	protected void setOptionStatesComponents(Collection<OptionStateComponent> optionStatesComponents){
		this.optionStatesComponents = optionStatesComponents;
	}

	/**
	 * Adds a new criteria.
	 *
	 * @param optionStateComponent Instance that contains the criteria.
	 */
	protected void addOptionStateComponent(OptionStateComponent optionStateComponent){
		if(optionStateComponent != null){
			if(this.optionStatesComponents == null)
				this.optionStatesComponents = PropertyUtil.instantiate(Constants.DEFAULT_LIST_CLASS);

			this.optionStatesComponents.add(optionStateComponent);
		}
	}

	/**
	 * Returns the list that contains the criteria to render the options.
	 *
	 * @param <C> Class that defines the list.
	 * @return List that contains the criteria.
	 */
	@SuppressWarnings("unchecked")
	protected <C extends Collection<OptionStateComponent>> C getOptionStatesComponents(){
		return (C)this.optionStatesComponents;
	}

	/**
	 * Returns the identifier of the dataset where options are stored.
	 *
	 * @return String that contains the identifier of the dataset.
	 */
	public String getDataset(){
		return this.dataset;
	}

	/**
	 * Defines the identifier of the dataset where options are stored.
	 *
	 * @param dataset String that contains the identifier of the dataset.
	 */
	public void setDataset(String dataset){
		this.dataset = dataset;
	}

	/**
	 * Returns the scope of storage of the dataset.
	 *
	 * @return String that contains the scope of storage.
	 */
	public String getDatasetScope(){
		return this.datasetScope;
	}

	/**
	 * Returns the scope of storage of the dataset.
	 *
	 * @return Instance that contains the scope of storage.
	 */
	protected ScopeType getDatasetScopeType(){
		if(this.datasetScope != null && this.datasetScope.length() > 0){
			try{
				return ScopeType.valueOf(this.datasetScope.toUpperCase());
			}
			catch(IllegalArgumentException e){
			}
		}

		return null;
	}

	/**
	 * Defines the scope of storage of the data
	 *
	 * @param datasetScope String that contains the scope of storage.
	 */
	public void setDatasetScope(String datasetScope){
		this.datasetScope = datasetScope;
	}

	/**
	 * Defines the scope of storage of the dataset.
	 *
	 * @param datasetScope Instance that contains the scope of storage.
	 */
	protected void setDatasetScopeType(ScopeType datasetScope){
		if(datasetScope != null)
			this.datasetScope = datasetScope.toString();
		else
			this.datasetScope = null;
	}

	/**
	 * Returns the list of the options.
	 *
	 * @param <C> Class that defines the list.
	 * @return Instance that contains the list of options.
	 */
	@SuppressWarnings("unchecked")
	protected <C extends Collection<?>> C getDatasetValues(){
		return (C)this.datasetValues;
	}

	/**
	 * Defines the list of options.
	 *
	 * @param datasetValues Instance that contains the list of options.
	 */
	protected void setDatasetValues(Collection<?> datasetValues){
		this.datasetValues = datasetValues;
	}

	/**
	 * Indicates if the component will permits multiple selection.
	 * 
	 * @return True/False.
	 */
	public Boolean hasMultipleSelection(){
		return this.multipleSelection;
	}

	/**
	 * Indicates if the component will permits multiple selection.
	 * 
	 * @return True/False.
	 */
	public Boolean getMultipleSelection(){
		return hasMultipleSelection();
	}

	/**
	 * Defines if the component will permits multiple selection.
	 * 
	 * @param multipleSelection True/False.
	 */
	public void setMultipleSelection(Boolean multipleSelection){
		this.multipleSelection = multipleSelection;
	}

	/**
	 * Returns the message when there is not dataset to show.
	 *
	 * @return String that contains the message.
	 */
	protected static String getNoDatasetMessage(){
		return noDatasetMessage;
	}

	/**
	 * Indicates if the component has no dataset to show.
	 * 
	 * @return True/False.
	 */
	protected Boolean hasNoDataset(){
		return this.hasNoDataset;
	}

	/**
	 * Defines if the component has no dataset to show.
	 * 
	 * @param hasNoDataset True/False.
	 */
	private void setHasNoDataset(Boolean hasNoDataset){
		this.hasNoDataset = hasNoDataset;
	}

	/**
	 * @see br.com.innovativethinking.framework.ui.components.BasePropertyComponent#buildResources()
	 */
	protected void buildResources() throws InternalErrorException{
		if(noDatasetMessage == null){
			PropertiesResources resources = getDefaultResources();
	
			if(resources != null){
				noDatasetMessage = resources.getProperty(ActionFormMessageConstants.DEFAULT_NO_DATA_KEY_ID);
				noDatasetMessage = PropertyUtil.fillPropertiesInString(this, noDatasetMessage);
				noDatasetMessage = PropertyUtil.fillResourcesInString(resources, noDatasetMessage);
			}
		}

		super.buildResources();
	}

	/**
	 * @see br.com.innovativethinking.framework.ui.components.BasePropertyComponent#buildRestrictions()
	 */
	protected void buildRestrictions() throws InternalErrorException{
		PropertyInfo propertyInfo = getPropertyInfo();

		if(propertyInfo != null)
			this.multipleSelection = propertyInfo.isCollection();

		super.buildRestrictions();
	}

	/**
	 * @see br.com.innovativethinking.framework.ui.components.BasePropertyComponent#buildStyleClass()
	 */
	protected void buildStyleClass() throws InternalErrorException{
		String optionLabelStyleClass = getOptionLabelStyleClass();

		if(optionLabelStyleClass == null || optionLabelStyleClass.length() == 0){
			optionLabelStyleClass = UIConstants.DEFAULT_OPTIONS_LABEL_STYLE_CLASS;

			setOptionLabelStyleClass(optionLabelStyleClass);
		}

		super.buildStyleClass();
	}

	/**
	 * @see br.com.innovativethinking.framework.ui.components.BasePropertyComponent#initialize()
	 */
	protected void initialize() throws InternalErrorException{
		super.initialize();

		String name = getName();
		String dataset = getDataset();

		if(name != null && dataset != null && name.equals(dataset))
			setHasInvalidPropertyDefinition(false);

		this.datasetStartIndex = 0;
		this.datasetEndIndex = 0;

		SystemController systemController = getSystemController();
		String actionFormName = getActionFormName();
		Boolean render = render();

		if(systemController != null && actionFormName != null && actionFormName.length() > 0 && dataset != null && dataset.length() > 0 && (this.datasetValues == null || this.datasetValues.size() == 0) && render != null && render){
			ScopeType datasetScope = getDatasetScopeType();

			if(datasetScope == null){
				datasetScope = ActionFormConstants.DEFAULT_DATASET_SCOPE_TYPE;

				setDatasetScopeType(datasetScope);
			}

			StringBuilder propertyId = new StringBuilder();

			if(datasetScope == ScopeType.MODEL){
				propertyId.append(actionFormName);
				propertyId.append(".");
				propertyId.append(ModelConstants.DEFAULT_ID);
				propertyId.append(".");
			}

			propertyId.append(dataset);

			this.datasetValues = systemController.getAttribute(propertyId.toString(), datasetScope);
		}

		if(this.datasetValues != null && this.datasetValues.size() > 0)
			this.datasetEndIndex = this.datasetValues.size();

		this.hasNoDataset = (this.datasetValues == null || this.datasetValues.size() == 0);
	}

	/**
	 * Renders the no dataset message.
	 * 
	 * @throws InternalErrorException Occurs when was not possible to render.
	 */
	protected void renderNoDatasetMessage() throws InternalErrorException{
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
		print(getNoDatasetMessage());
		println("</span>");
	}

	/**
	 * Renders the dataset attributes of the component.
	 * 
	 * @throws InternalErrorException Occurs when was not possible to render.
	 */
	protected void renderDatasetAttributes() throws InternalErrorException{
		String actionFormName = getActionFormName();
		String name = getName();

		if(actionFormName == null || actionFormName.length() == 0 || name == null || name.length() == 0)
			return;

		Boolean hasInvalidPropertyDefinition = hasInvalidPropertyDefinition();
		BaseOptionsPropertyComponent optionsPropertyComponent = null;

		try{
			optionsPropertyComponent = (BaseOptionsPropertyComponent)getParent();
		}
		catch(ClassCastException e){
		}

		if((hasInvalidPropertyDefinition == null || !hasInvalidPropertyDefinition) && this.dataset != null && this.dataset.length() > 0 && optionsPropertyComponent == null){
			StringBuilder nameBuffer = new StringBuilder();

			nameBuffer.append(name);
			nameBuffer.append(".");
			nameBuffer.append(ActionFormConstants.DATASET_ATTRIBUTE_ID);

			HiddenPropertyComponent dataPropertyComponent = new HiddenPropertyComponent();

			dataPropertyComponent.setPageContext(this.pageContext);
			dataPropertyComponent.setOutputStream(getOutputStream());
			dataPropertyComponent.setActionFormName(actionFormName);
			dataPropertyComponent.setName(nameBuffer.toString());
			dataPropertyComponent.setValue(this.dataset);

			try{
				dataPropertyComponent.doStartTag();
				dataPropertyComponent.doEndTag();
			}
			catch(JspException e){
				throw new InternalErrorException(e);
			}

			nameBuffer.delete(0, nameBuffer.length());
			nameBuffer.append(name);
			nameBuffer.append(".");
			nameBuffer.append(ActionFormConstants.DATASET_SCOPE_ATTRIBUTE_ID);

			HiddenPropertyComponent datasetScopePropertyComponent = new HiddenPropertyComponent();

			datasetScopePropertyComponent.setPageContext(this.pageContext);
			datasetScopePropertyComponent.setOutputStream(getOutputStream());
			datasetScopePropertyComponent.setActionFormName(actionFormName);
			datasetScopePropertyComponent.setName(nameBuffer.toString());
			datasetScopePropertyComponent.setValue(this.datasetScope);

			try{
				datasetScopePropertyComponent.doStartTag();
				datasetScopePropertyComponent.doEndTag();
			}
			catch(JspException e){
				throw new InternalErrorException(e);
			}
		}
	}

	/**
	 * Renders the indexes of the dataset attributes.
	 * 
	 * @throws InternalErrorException Occurs when was not possible to render.
	 */
	protected void renderDatasetIndexesAttributes() throws InternalErrorException{
		String actionFormName = getActionFormName();
		String name = getName();

		if(actionFormName == null || actionFormName.length() == 0 || name == null || name.length() == 0)
			return;

		Boolean hasInvalidPropertyDefinition = hasInvalidPropertyDefinition();
		Boolean hasNoDataset = hasNoDataset();
		BaseOptionsPropertyComponent optionsPropertyComponent = null;

		try{
			optionsPropertyComponent = (BaseOptionsPropertyComponent)getParent();
		}
		catch(ClassCastException e){
		}

		if((hasInvalidPropertyDefinition == null || !hasInvalidPropertyDefinition) && (hasNoDataset == null || !hasNoDataset) && optionsPropertyComponent == null){
			StringBuilder nameBuffer = new StringBuilder();

			nameBuffer.append(name);
			nameBuffer.append(".");
			nameBuffer.append(ActionFormConstants.DATASET_START_INDEX_ATTRIBUTE_ID);

			HiddenPropertyComponent datasetStartIndexPropertyComponent = new HiddenPropertyComponent();

			datasetStartIndexPropertyComponent.setPageContext(this.pageContext);
			datasetStartIndexPropertyComponent.setOutputStream(getOutputStream());
			datasetStartIndexPropertyComponent.setActionFormName(actionFormName);
			datasetStartIndexPropertyComponent.setName(nameBuffer.toString());
			datasetStartIndexPropertyComponent.setValue(this.datasetStartIndex);

			try{
				datasetStartIndexPropertyComponent.doStartTag();
				datasetStartIndexPropertyComponent.doEndTag();
			}
			catch(JspException e){
				throw new InternalErrorException(e);
			}

			nameBuffer.delete(0, nameBuffer.length());
			nameBuffer.append(name);
			nameBuffer.append(".");
			nameBuffer.append(ActionFormConstants.DATASET_END_INDEX_ATTRIBUTE_ID);

			HiddenPropertyComponent datasetEndIndexPropertyComponent = new HiddenPropertyComponent();

			datasetEndIndexPropertyComponent.setPageContext(this.pageContext);
			datasetEndIndexPropertyComponent.setOutputStream(getOutputStream());
			datasetEndIndexPropertyComponent.setActionFormName(actionFormName);
			datasetEndIndexPropertyComponent.setName(nameBuffer.toString());
			datasetEndIndexPropertyComponent.setValue(this.datasetEndIndex);

			try{
				datasetEndIndexPropertyComponent.doStartTag();
				datasetEndIndexPropertyComponent.doEndTag();
			}
			catch(JspException e){
				throw new InternalErrorException(e);
			}
		}
	}

	/**
	 * @see br.com.innovativethinking.framework.ui.components.BasePropertyComponent#clearAttributes()
	 */
	protected void clearAttributes() throws InternalErrorException{
		super.clearAttributes();

		setOptionLabelStyle(null);
		setOptionLabelStyleClass(null);
		setDataset(null);
		setDatasetScope(null);
		setDatasetValues(null);
		setDatasetStartIndex(null);
		setDatasetEndIndex(null);
		setOptionStatesComponents(null);
		setMultipleSelection(null);
		setHasNoDataset(null);
	}
}