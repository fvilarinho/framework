package br.com.concepting.framework.ui.components;

import java.lang.reflect.InvocationTargetException;
import java.security.SecureRandom;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import javax.servlet.jsp.JspException;

import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.controller.action.types.ActionType;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.model.ObjectModel;
import br.com.concepting.framework.model.SystemSessionModel;
import br.com.concepting.framework.processors.ExpressionProcessor;
import br.com.concepting.framework.processors.ExpressionProcessorUtil;
import br.com.concepting.framework.resources.PropertiesResources;
import br.com.concepting.framework.security.controller.SecurityController;
import br.com.concepting.framework.security.model.LoginSessionModel;
import br.com.concepting.framework.ui.components.types.EventType;
import br.com.concepting.framework.ui.constants.UIConstants;
import br.com.concepting.framework.util.PropertyUtil;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.helpers.Node;
import br.com.concepting.framework.util.helpers.PropertyInfo;
import br.com.concepting.framework.util.types.AlignmentType;
import br.com.concepting.framework.util.types.ComponentType;
import br.com.concepting.framework.util.types.PositionType;

/**
 * Class that defines the options component.
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
public class OptionsPropertyComponent extends BaseOptionsPropertyComponent{
	private static final long serialVersionUID = 1340880138631153675L;

	private Integer optionsPerRow                     = null;
	private String  optionResourcesId                 = null;
	private String  optionLabelProperty               = null;
	private String  optionTooltipProperty             = null;
	private String  onSelect                          = null;
	private String  onSelectAction                    = null;
	private String  onSelectForward                   = null;
	private String  onSelectUpdateViews               = null;
	private Boolean onSelectValidateModel             = null;
	private String  onSelectValidateModelProperties   = null;
	private String  onUnSelect                        = null;
	private String  onUnSelectAction                  = null;
	private String  onUnSelectForward                 = null;
	private String  onUnSelectUpdateViews             = null;
	private Boolean onUnSelectValidateModel           = null;
	private String  onUnSelectValidateModelProperties = null;

	/**
	 * Returns the identifier of the resources for the label of the option.
	 * 
	 * @return String that contains the identifier of the resources.
	 */
	public String getOptionResourcesId(){
		return this.optionResourcesId;
	}

	/**
	 * Defines the identifier of the resource for the label of the option.
	 * 
	 * @param optionResourcesId String that contains the identifier of the
	 * resource.
	 */
	public void setOptionResourcesId(String optionResourcesId){
		this.optionResourcesId = optionResourcesId;
	}

	/**
	 * Returns the identifier of the action of the unselect event.
	 * 
	 * @return String that contains the identifier of the action.
	 */
	public String getOnUnSelectAction(){
		return this.onUnSelectAction;
	}

	/**
	 * Defines the identifier of the action of the unselect event.
	 * 
	 * @param onUnSelectAction String that contains the identifier of the
	 * action.
	 */
	public void setOnUnSelectAction(String onUnSelectAction){
		this.onUnSelectAction = onUnSelectAction;
	}

	/**
	 * Defines the action of the unselect event.
	 * 
	 * @param onUnSelectActionType Instance that contains the action.
	 */
	public void setOnUnSelectActionType(ActionType onUnSelectActionType){
		if(onUnSelectActionType != null)
			this.onUnSelectAction = onUnSelectActionType.getMethod();
		else
			this.onUnSelectAction = null;
	}

	/**
	 * Returns the identifier of the action forward of the unselect event.
	 * 
	 * @return String that contains the identifier of the action forward of the
	 * action.
	 */
	public String getOnUnSelectForward(){
		return this.onUnSelectForward;
	}

	/**
	 * Defines the identifier of the action forward of the unselect event.
	 * 
	 * @param onUnSelectForward String that contains the identifier of the
	 * action forward of the action.
	 */
	public void setOnUnSelectForward(String onUnSelectForward){
		this.onUnSelectForward = onUnSelectForward;
	}

	/**
	 * Returns the identifier of the views that should be updated on the select
	 * event.
	 * 
	 * @return String that contains the identifier of the views.
	 */
	public String getOnUnSelectUpdateViews(){
		return this.onUnSelectUpdateViews;
	}

	/**
	 * Defines the identifier of the views that should be updated on the select
	 * event.
	 * 
	 * @param onUnSelectUpdateViews String that contains the identifier of the
	 * views.
	 */
	public void setOnUnSelectUpdateViews(String onUnSelectUpdateViews){
		this.onUnSelectUpdateViews = onUnSelectUpdateViews;
	}

	/**
	 * Indicates if the data model should be validated on the select event.
	 * 
	 * @return True/False.
	 */
	public Boolean getOnUnSelectValidateModel(){
		return this.onUnSelectValidateModel;
	}

	/**
	 * Defines if the data model should be validated on the select event.
	 * 
	 * @param onUnSelectValidateModel True/False.
	 */
	public void setOnUnSelectValidateModel(Boolean onUnSelectValidateModel){
		this.onUnSelectValidateModel = onUnSelectValidateModel;
	}

	/**
	 * Returns the validation properties of the data model of the unselect
	 * event.
	 * 
	 * @return String that contains the identifiers of the properties.
	 */
	public String getOnUnSelectValidateModelProperties(){
		return this.onUnSelectValidateModelProperties;
	}

	/**
	 * Defines the validation properties of the data model of the unselect
	 * event.
	 * 
	 * @param onUnSelectValidateModelProperties String that contains the
	 * identifiers of the properties.
	 */
	public void setOnUnSelectValidateModelProperties(String onUnSelectValidateModelProperties){
		this.onUnSelectValidateModelProperties = onUnSelectValidateModelProperties;
	}

	/**
	 * Returns the identifier of the action of the select event.
	 * 
	 * @return String that contains the identifier of the action.
	 */
	public String getOnSelectAction(){
		return this.onSelectAction;
	}

	/**
	 * Defines the identifier of the action of the select event.
	 * 
	 * @param onSelectAction String that contains the identifier of the action.
	 */
	public void setOnSelectAction(String onSelectAction){
		this.onSelectAction = onSelectAction;
	}

	/**
	 * Defines the action of the select event.
	 * 
	 * @param onSelectActionType Instance that contains the action.
	 */
	public void setOnSelectActionType(ActionType onSelectActionType){
		if(onSelectActionType != null)
			this.onSelectAction = onSelectActionType.getMethod();
		else
			this.onSelectAction = null;
	}

	/**
	 * Returns the identifier of the action forward of the on select event.
	 * 
	 * @return String that contains the identifier of the action forward of the
	 * action.
	 */
	public String getOnSelectForward(){
		return this.onSelectForward;
	}

	/**
	 * Defines the identifier of the action forward of the on select event.
	 * 
	 * @param onSelectForward String that contains the identifier of the action
	 * forward of the action.
	 */
	public void setOnSelectForward(String onSelectForward){
		this.onSelectForward = onSelectForward;
	}

	/**
	 * Returns the identifier of the views that should be updated on the select
	 * event.
	 * 
	 * @return String that contains the identifier of the views.
	 */
	public String getOnSelectUpdateViews(){
		return this.onSelectUpdateViews;
	}

	/**
	 * Defines the identifier of the views that should be updated on the select
	 * event.
	 * 
	 * @param onSelectUpdateViews String that contains the identifier of the
	 * views.
	 */
	public void setOnSelectUpdateViews(String onSelectUpdateViews){
		this.onSelectUpdateViews = onSelectUpdateViews;
	}

	/**
	 * Indicates if the data model should be validated on the select event.
	 * 
	 * @return True/False.
	 */
	public Boolean getOnSelectValidateModel(){
		return this.onSelectValidateModel;
	}

	/**
	 * Defines if the data model should be validated on the select event.
	 * 
	 * @param onSelectValidateModel True/False.
	 */
	public void setOnSelectValidateModel(Boolean onSelectValidateModel){
		this.onSelectValidateModel = onSelectValidateModel;
	}

	/**
	 * Returns the validation properties of the data model of the on select
	 * event.
	 * 
	 * @return String that contains the identifiers of the properties.
	 */
	public String getOnSelectValidateModelProperties(){
		return this.onSelectValidateModelProperties;
	}

	/**
	 * Defines the validation properties of the data model of the on select
	 * event.
	 * 
	 * @param onSelectValidateModelProperties String that contains the
	 * identifiers of the properties.
	 */
	public void setOnSelectValidateModelProperties(String onSelectValidateModelProperties){
		this.onSelectValidateModelProperties = onSelectValidateModelProperties;
	}

	/**
	 * Returns the number of options per row that should be shown.
	 * 
	 * @return Numeric value that contains the number of options.
	 */
	public Integer getOptionsPerRow(){
		return this.optionsPerRow;
	}

	/**
	 * Defines the number of options per row that should be shown.
	 * 
	 * @param optionsPerRow Numeric value that contains the number of options.
	 */
	public void setOptionsPerRow(Integer optionsPerRow){
		this.optionsPerRow = optionsPerRow;
	}

	/**
	 * Returns the property of the data model for the tooltip of the option.
	 * 
	 * @return String that contains the property of the data model.
	 */
	public String getOptionTooltipProperty(){
		return this.optionTooltipProperty;
	}

	/**
	 * Defines the property of the data model for the tooltip of the option.
	 * 
	 * @param optionTooltipProperty String that contains the property of the
	 * data model.
	 */
	public void setOptionTooltipProperty(String optionTooltipProperty){
		this.optionTooltipProperty = optionTooltipProperty;
	}

	/**
	 * Returns the property of the data model for the label of the option.
	 * 
	 * @return String that contains the property of the data model.
	 */
	public String getOptionLabelProperty(){
		return this.optionLabelProperty;
	}

	/**
	 * Defines the property of the data model for the label of the option.
	 * 
	 * @param optionLabelProperty String that contains the property of the data
	 * model.
	 */
	public void setOptionLabelProperty(String optionLabelProperty){
		this.optionLabelProperty = optionLabelProperty;
	}

	/**
	 * Returns the on select event.
	 * 
	 * @return String that contains the event.
	 */
	public String getOnSelect(){
		return this.onSelect;
	}

	/**
	 * Defines the on select event.
	 * 
	 * @param onSelect String that contains the event.
	 */
	public void setOnSelect(String onSelect){
		this.onSelect = onSelect;
	}

	/**
	 * Returns the unselect event.
	 * 
	 * @return String that contains the event.
	 */
	public String getOnUnSelect(){
		return this.onUnSelect;
	}

	/**
	 * Defines the unselect event.
	 * 
	 * @param onUnSelect String that contains the event.
	 */
	public void setOnUnSelect(String onUnSelect){
		this.onUnSelect = onUnSelect;
	}

	/**
	 * Returns the option label.
	 * 
	 * @param option Instance that contains the option.
	 * @param level Numeric value that contains the level of the option.
	 * @return String that contains the option label.
	 * @throws InternalErrorException Occurs when was not possible to execute
	 * the operation.
	 */
	protected String getOptionLabel(Object option, Integer level) throws InternalErrorException{
		if(option == null)
			return null;

		Locale currentLanguage = getCurrentLanguage();
		PropertiesResources resources = getResources(getOptionResourcesId());
		PropertiesResources mainConsoleResources = getMainConsoleResources();
		PropertiesResources defaultResources = getDefaultResources();
		String optionResourceLabel = null;
		String optionValueLabel = null;

		if(option instanceof ObjectModel){
			ObjectModel object = (ObjectModel)option;

			if(object.getType() == ComponentType.MENU_ITEM_SEPARATOR)
				optionResourceLabel = "-";
			else{
				String name = object.getName();

				if(name != null && name.length() > 0){
					StringBuilder propertyId = new StringBuilder();

					propertyId.append(name);
					propertyId.append(".");
					propertyId.append(Constants.LABEL_ATTRIBUTE_ID);

					optionResourceLabel = (resources != null ? resources.getProperty(propertyId.toString(), false) : null);

					if(optionResourceLabel == null)
						optionResourceLabel = (mainConsoleResources != null ? mainConsoleResources.getProperty(propertyId.toString(), false) : null);

					if(optionResourceLabel == null)
						optionResourceLabel = (defaultResources != null ? defaultResources.getProperty(propertyId.toString()) : null);
				}
			}
		}
		else{
			try{
				if(this.optionLabelProperty != null && this.optionLabelProperty.length() > 0){
					PropertyInfo optionLabelPropertyInfo = PropertyUtil.getInfo(option.getClass(), this.optionLabelProperty);

					if(optionLabelPropertyInfo != null){
						Object optionValue = PropertyUtil.getValue(option, this.optionLabelProperty);

						optionValueLabel = PropertyUtil.format(optionValue, optionLabelPropertyInfo.getPattern(), optionLabelPropertyInfo.useAdditionalFormatting(), optionLabelPropertyInfo.getPrecision(), currentLanguage);
					}
					else
						optionValueLabel = PropertyUtil.format(option, getPattern(), useAdditionalFormatting(), getPrecision(), currentLanguage);
				}
				else
					optionValueLabel = PropertyUtil.format(option, getPattern(), useAdditionalFormatting(), getPrecision(), currentLanguage);
			}
			catch(IllegalArgumentException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException | ClassNotFoundException | NoSuchFieldException e){
				optionValueLabel = PropertyUtil.format(option, getPattern(), useAdditionalFormatting(), getPrecision(), currentLanguage);
			}
		}

		StringBuilder optionLabelBuffer = new StringBuilder();
		String indentation = getOptionLabelIndentation(level);

		if(indentation != null && indentation.length() > 0)    
			optionLabelBuffer.append(indentation);

		if(optionValueLabel != null && optionValueLabel.length() > 0)
			optionLabelBuffer.append(optionValueLabel);
		else
			optionLabelBuffer.append(optionResourceLabel);

		return optionLabelBuffer.toString();
	}

	/**
	 * Returns the label indentation of a specific level.
	 * 
	 * @param level Numeric value that contains the level.
	 * @return String that contains the label identation.
	 * @throws InternalErrorException Occurs when was not possible to execute
	 * the operation.
	 */
	protected String getOptionLabelIndentation(Integer level) throws InternalErrorException{
		if(level != null && level > 0)
			return StringUtil.replicate("&nbsp;", level * Constants.DEFAULT_INDENT_SIZE);

		return null;
	}

	/**
	 * Returns the option tooltip.
	 * 
	 * @param option Instance that contains the option.
	 * @return String that contains the option tooltip.
	 * @throws InternalErrorException Occurs when was not possible to execute
	 * the operation.
	 */
	protected String getOptionTooltip(Object option) throws InternalErrorException{
		if(option == null)
			return null;

		Locale currentLanguage = getCurrentLanguage();
		PropertiesResources resources = getResources(getOptionResourcesId());
		PropertiesResources mainConsoleResources = getMainConsoleResources();
		PropertiesResources defaultResources = getDefaultResources();
		String optionResourceTooltip = null;
		String optionValueTooltip = null;

		if(option instanceof ObjectModel){
			ObjectModel object = (ObjectModel)option;
			String name = object.getName();

			if(name != null && name.length() > 0){
				StringBuilder propertyId = new StringBuilder();

				propertyId.append(name);
				propertyId.append(".");
				propertyId.append(Constants.TOOLTIP_ATTRIBUTE_ID);

				optionResourceTooltip = (resources != null ? resources.getProperty(propertyId.toString(), false) : null);

				if(optionResourceTooltip == null)
					optionResourceTooltip = (mainConsoleResources != null ? mainConsoleResources.getProperty(propertyId.toString(), false) : null);

				if(optionResourceTooltip == null)
					optionResourceTooltip = (defaultResources != null ? defaultResources.getProperty(propertyId.toString()) : null);
			}
		}

		try{
			if(this.optionTooltipProperty == null || this.optionTooltipProperty.length() == 0)
				this.optionTooltipProperty = this.optionLabelProperty;
			
			if(this.optionTooltipProperty != null && this.optionTooltipProperty.length() > 0){
				PropertyInfo optionTooltipPropertyInfo = PropertyUtil.getInfo(option.getClass(), this.optionTooltipProperty);

				if(optionTooltipPropertyInfo != null){
					Object optionValue = PropertyUtil.getValue(option, this.optionTooltipProperty);

					optionValueTooltip = PropertyUtil.format(optionValue, optionTooltipPropertyInfo.getPattern(), optionTooltipPropertyInfo.useAdditionalFormatting(), optionTooltipPropertyInfo.getPrecision(), currentLanguage);
				}
				else
					optionValueTooltip = PropertyUtil.format(option, getPattern(), useAdditionalFormatting(), getPrecision(), currentLanguage);
			}
			else
				optionValueTooltip = PropertyUtil.format(option, getPattern(), useAdditionalFormatting(), getPrecision(), currentLanguage);
		}
		catch(IllegalArgumentException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException | ClassNotFoundException | NoSuchFieldException e){
			optionValueTooltip = PropertyUtil.format(option, getPattern(), useAdditionalFormatting(), getPrecision(), currentLanguage);
		}

		if(optionValueTooltip != null && optionValueTooltip.length() > 0)
			return optionValueTooltip;

		return optionResourceTooltip;
	}

	/**
	 * Returns the instance of the option component.
	 * 
	 * @return Instance that contains the option component.
	 * @throws InternalErrorException Occurs when was not possible to execute
	 * the operation.
	 */
	protected BaseOptionPropertyComponent getOptionComponent() throws InternalErrorException{
		Boolean multipleSelection = hasMultipleSelection();

		if(multipleSelection != null && multipleSelection)
			return new CheckPropertyComponent();

		return new RadioPropertyComponent();
	}

	/**
	 * @see br.com.concepting.framework.ui.components.BasePropertyComponent#buildEvents()
	 */
	protected void buildEvents() throws InternalErrorException{
		super.buildEvents();

		buildEvent(EventType.ON_SELECT);
		buildEvent(EventType.ON_UN_SELECT);
	}

	/**
	 * @see br.com.concepting.framework.ui.components.BasePropertyComponent#buildAlignment()
	 */
	protected void buildAlignment() throws InternalErrorException{
		if(getAlignmentType() == null)
			setAlignmentType(AlignmentType.LEFT);

		super.buildAlignment();
	}

	/**
	 * @see br.com.concepting.framework.ui.components.BaseOptionsPropertyComponent#buildRestrictions()
	 */
	protected void buildRestrictions() throws InternalErrorException{
		if(this.optionsPerRow == null || this.optionsPerRow == 0)
			this.optionsPerRow = UIConstants.DEFAULT_OPTIONS_PER_ROW;

		super.buildRestrictions();
	}

	/**
	 * @see br.com.concepting.framework.ui.components.BaseComponent#initialize()
	 */
	protected void initialize() throws InternalErrorException{
		ComponentType componentType = getComponentType();

		if(componentType == null){
			componentType = ComponentType.OPTIONS;

			setComponentType(componentType);
		}

		super.initialize();
	}

	/**
	 * Renders the grouping (tag opening) of the options.
	 * 
	 * @throws InternalErrorException Occurs when was not possible to render.
	 */
	protected void renderOpenGroup() throws InternalErrorException{
		print("<fieldset");

		renderStyle();
		renderTooltip();

		println(">");

		renderGroupLabel();
	}

	/**
	 * Renders the label of the group of options.
	 * 
	 * @throws InternalErrorException Occurs when was not possible to render.
	 */
	protected void renderGroupLabel() throws InternalErrorException{
		Boolean showLabel = showLabel();
		String label = getLabel();

		if(getLabelPositionType() == PositionType.TOP && showLabel && showLabel && label != null && label.length() > 0){
			renderGroupLabelOpen();
			renderGroupLabelBody();
			renderGroupLabelClose();
		}
	}

	/**
	 * Renders the label of the grouping (tag opening) of the options.
	 * 
	 * @throws InternalErrorException Occurs when was not possible to render.
	 */
	protected void renderGroupLabelOpen() throws InternalErrorException{
		print("<legend");

		String labelStyleClass = getLabelStyleClass();

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

		println(">");
	}

	/**
	 * Renders the label of the grouping (tag body) of the options.
	 * 
	 * @throws InternalErrorException Occurs when was not possible to render.
	 */
	protected void renderGroupLabelBody() throws InternalErrorException{
		super.renderLabelBody();
	}

	/**
	 * Renders the label of the grouping (tag closing) of the options.
	 * 
	 * @throws InternalErrorException Occurs when was not possible to render.
	 */
	protected void renderGroupLabelClose() throws InternalErrorException{
		println("</legend>");
	}

	/**
	 * Renders the grouping (tag closing) of the options.
	 * 
	 * @throws InternalErrorException Occurs when was not possible to render.
	 */
	protected void renderCloseGroup() throws InternalErrorException{
		println("</fieldset>");
	}

	/**
	 * @see br.com.concepting.framework.ui.components.BaseComponent#renderOpen()
	 */
	protected void renderOpen() throws InternalErrorException{
		renderDatasetAttributes();
		renderDatasetIndexesAttributes();

		Boolean showLabel = showLabel();

		if(showLabel != null && showLabel)
			if(getComponentType() == ComponentType.OPTIONS)
				if(getLabelPositionType() == PositionType.TOP)
					setShowLabel(false);

		super.renderOpen();

		setShowLabel(showLabel);
	}

	/**
	 * @see br.com.concepting.framework.ui.components.BasePropertyComponent#renderBody()
	 */
	protected void renderBody() throws InternalErrorException{
		renderOpenGroup();
		renderOptions();
		renderCloseGroup();
	}

	/**
	 * @see br.com.concepting.framework.ui.components.BaseComponent#renderClose()
	 */
	protected void renderClose() throws InternalErrorException{
		super.renderClose();

		String id = getId();
		String name = getName();
		Boolean multipleSelection = hasMultipleSelection();
		BaseOptionsPropertyComponent optionsPropertyComponent = null;

		try{
			optionsPropertyComponent = (BaseOptionsPropertyComponent)getParent();
		}
		catch(ClassCastException e){
		}

		if(id != null && id.length() > 0 && name != null && name.length() > 0 && multipleSelection != null && multipleSelection && optionsPropertyComponent == null){
			HiddenPropertyComponent propertyComponent = new HiddenPropertyComponent();

			propertyComponent.setPageContext(this.pageContext);
			propertyComponent.setOutputStream(getOutputStream());
			propertyComponent.setId(id);
			propertyComponent.setName(name);
			propertyComponent.setLabel(getLabel());

			try{
				propertyComponent.doStartTag();
				propertyComponent.doEndTag();
			}
			catch(JspException e){
				throw new InternalErrorException(e);
			}
		}
	}

	/**
	 * Renders the opening of the options panel.
	 * 
	 * @throws InternalErrorException Occurs when was not possible to render.
	 */
	protected void renderOptionsPanelOpen() throws InternalErrorException{
		print("<table class=\"");
		print(UIConstants.DEFAULT_CONTENT_PANEL_STYLE_CLASS);
		println("\">");
		println("<tr>");
	}

	/**
	 * Renders the closing of the options panel.
	 * 
	 * @throws InternalErrorException Occurs when was not possible to render.
	 */
	protected void renderOptionsPanelClose() throws InternalErrorException{
		println("</tr>");
		println("</table>");
	}

	/**
	 * Renders the opening of the options panel row.
	 * 
	 * @throws InternalErrorException Occurs when was not possible to render.
	 */
	protected void renderOptionPanelRowOpen() throws InternalErrorException{
		println("<td>");
	}

	/**
	 * Renders the closing of the options panel row.
	 * 
	 * @param row Numeric value that contains the row index.
	 * @throws InternalErrorException Occurs when was not possible to render.
	 */
	protected void renderOptionPanelRowClose(Integer row) throws InternalErrorException{
		println("</td>");

		if(this.optionsPerRow != null && (row % this.optionsPerRow) == 0){
			println("</tr>");
			println("<tr>");
		}
	}

	/**
	 * Renders the options.
	 * 
	 * @throws InternalErrorException Occurs when was not possible to render.
	 */
	protected void renderOptions() throws InternalErrorException{
		SecurityController securityController = getSecurityController();

		if(securityController == null)
			return;

		LoginSessionModel loginSession = securityController.getLoginSession();
		SystemSessionModel systemSession = (loginSession != null ? loginSession.getSystemSession() : null);

		if(systemSession == null)
			return;
		
		String domain = systemSession.getId();
		List<?> datasetValues = getDatasetValues();

		if(datasetValues != null && datasetValues.size() > 0)
			renderOptions(datasetValues, null, 0);
		
		ExpressionProcessorUtil.setVariable(domain, Constants.ITEM_ATTRIBUTE_ID, null);
	}

	/**
	 * Renders the options.
	 * 
	 * @param <N> Class that defines an option.
	 * @param options List that contains the options.
	 * @param parent Instance that contains the parent option.
	 * @param level String that contains the identifier of the cascade level of
	 * the options.
	 * @throws InternalErrorException Occurs when was not possible to render.
	 */
	@SuppressWarnings("unchecked")
	protected <N extends Node> void renderOptions(List<?> options, N parent, Integer level) throws InternalErrorException{
		if(options == null || options.size() == 0)
			return;

		SecurityController securityController = getSecurityController();

		if(securityController == null)
			return;

		LoginSessionModel loginSession = securityController.getLoginSession();
		SystemSessionModel systemSession = (loginSession != null ? loginSession.getSystemSession() : null);

		if(systemSession == null)
			return;

		String domain = systemSession.getId();

		try{
			ExpressionProcessor expressionProcessor = new ExpressionProcessor(domain, getCurrentLanguage());
			String expression = null;
			Boolean expressionResult = null;
			Object value = getValue();
			List<?> optionChildren = null;
			N optionChild = null;
			BaseOptionPropertyComponent optionComponent = null;
			Collection<OptionStateComponent> optionsStatesComponents = getOptionStatesComponents();
			String optionStyleClass = getOptionLabelStyleClass();
			String optionStyle = getOptionLabelStyle();
			Boolean remove = null;
			int cont = 0;

			renderOptionsPanelOpen();

			for(Object option :  options){
				if(option instanceof Node){
					optionChild = (N)option;

					if(optionChild.getIndex() == null)
						optionChild.setIndex(new SecureRandom().nextInt());
					
					optionChild.setLevel(level);
				}
				else
					optionChild = null;

				if(optionChild == null || (optionChild.getParent() == null && parent == null) || (parent != null && parent.equals(optionChild.getParent()))){
					ExpressionProcessorUtil.setVariable(domain, Constants.ITEM_ATTRIBUTE_ID, option);
	
					expressionResult = true;
					remove = false;

					if(optionsStatesComponents != null && !optionsStatesComponents.isEmpty()){
						expressionProcessor.setDeclaration(option);
	
						for(OptionStateComponent optionStateComponent : optionsStatesComponents){
							expression = optionStateComponent.getExpression();
							
							try{
								expressionResult = expressionProcessor.evaluate(expression);
							}
							catch(InternalErrorException e){
							}
	
							if(expressionResult != null && expressionResult){
								remove = optionStateComponent.remove();
								optionStyleClass = optionStateComponent.getStyleClass();
								optionStyle = optionStateComponent.getStyle();
	
								break;
							}
						}
					}
	
					if(remove == null || !remove){
						renderOptionPanelRowOpen();

						optionStyleClass = ExpressionProcessorUtil.fillVariablesInString(domain, optionStyleClass);
						optionStyle = ExpressionProcessorUtil.fillVariablesInString(domain, optionStyle);

						optionComponent = getOptionComponent();
						optionComponent.setPageContext(this.pageContext);
						optionComponent.setOutputStream(getOutputStream());
						optionComponent.setPropertyInfo(getPropertyInfo());
						optionComponent.setActionFormName(getActionFormName());
						optionComponent.setResourcesId(getResourcesId());
						optionComponent.setName(getName());
						optionComponent.setLabel(getOptionLabel(option, level));
						optionComponent.setTooltip(getOptionTooltip(option));
						optionComponent.setAlignmentType(AlignmentType.LEFT);
						optionComponent.setLabelAlignmentType(AlignmentType.LEFT);
						optionComponent.setLabelPositionType(PositionType.RIGHT);
						optionComponent.setStyleClass(optionStyleClass);
						optionComponent.setStyle(optionStyle);
						optionComponent.setOptionValue(option);
						optionComponent.setValue(value);
						optionComponent.setEnabled(isEnabled());
						optionComponent.setParent(this);

						try{
							optionComponent.doStartTag();
							optionComponent.doEndTag();
						}
						catch(JspException e){
							throw new InternalErrorException(e);
						}

						if(optionChild != null){
							if(optionChild.hasChildren()){
								optionChildren = optionChild.getChildren();

								renderOptions(optionChildren, optionChild, (level != null ? level + 1 : 1));
							}
						}

						renderOptionPanelRowClose(cont);

						cont++;
					}
				}
			}

			renderOptionsPanelClose();
		} 
		catch(IllegalArgumentException e){
			throw new InternalErrorException(e);
		}
		finally{
			ExpressionProcessorUtil.setVariable(domain, Constants.ITEM_ATTRIBUTE_ID, null);
		}
	}

	/**
	 * @see br.com.concepting.framework.ui.components.BasePropertyComponent#clearAttributes()
	 */
	protected void clearAttributes() throws InternalErrorException{
		super.clearAttributes();

		setOptionResourcesId(null);
		setOptionsPerRow(null);
		setOptionLabelProperty(null);
		setOnSelect(null);
		setOnSelectAction(null);
		setOnSelectForward(null);
		setOnSelectUpdateViews(null);
		setOnSelectValidateModel(null);
		setOnSelectValidateModelProperties(null);
		setOnUnSelect(null);
		setOnUnSelectAction(null);
		setOnUnSelectForward(null);
		setOnUnSelectUpdateViews(null);
		setOnUnSelectValidateModel(null);
		setOnUnSelectValidateModelProperties(null);
	}
}