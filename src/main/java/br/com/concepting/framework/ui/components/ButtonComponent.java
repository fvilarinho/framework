package br.com.concepting.framework.ui.components;

import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.controller.action.types.ActionType;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.ui.components.types.PagerActionType;
import br.com.concepting.framework.ui.constants.UIConstants;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.types.AlignmentType;
import br.com.concepting.framework.util.types.ComponentType;
import br.com.concepting.framework.util.types.PositionType;

/**
 * Class that defines the basic implementation of a button component.
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
public class ButtonComponent extends BaseActionFormComponent{
	private static final long serialVersionUID = -6412461075636504749L;

	private String  iconStyleClass          = null;
	private String  iconStyle               = null;
	private String  action                  = null;
	private String  forward                 = null;
	private Boolean validateModel           = null;
	private String  validateModelProperties = null;
	private String  updateViews             = null;
	private String  pagerAction             = null;
	private Boolean showActionFormMessages  = null;

	/**
	 * Indicates if validation messages should be shown.
	 * 
	 * @return True/False.
	 */
	public Boolean showActionFormMessages(){
		return this.showActionFormMessages;
	}

	/**
	 * Defines if validation messages should be shown.
	 * 
	 * @param showActionFormMessages True/False.
	 */
	public void setShowActionFormMessages(Boolean showActionFormMessages){
		this.showActionFormMessages = showActionFormMessages;
	}

	/**
	 * Indicates if it has an action.
	 * 
	 * @return True/False.
	 */
	protected Boolean hasAction(){
		return (this.action != null && this.action.length() > 0);
	}

	/**
	 * Returns the identifiers of the views that show be updated after the
	 * action execution.
	 * 
	 * @return String that contains the identifiers of the views.
	 */
	public String getUpdateViews(){
		return this.updateViews;
	}

	/**
	 * Defines the identifiers of the views that show be updated after the
	 * action execution.
	 * 
	 * @param updateViews String that contains the identifiers of the views.
	 */
	public void setUpdateViews(String updateViews){
		this.updateViews = updateViews;
	}

	/**
	 * Indicates if the component should be shown only when dataset is present.
	 * 
	 * @return True/False.
	 */
	public Boolean showOnlyWithDataset(){
		return false;
	}

	/**
	 * Returns the pager action.
	 * 
	 * @return Instance that contains the pager action.
	 */
	protected PagerActionType getPagerActionType(){
		if(this.pagerAction != null && this.pagerAction.length() > 0){
			try{
				return PagerActionType.valueOf(this.pagerAction.toUpperCase());
			}
			catch(IllegalArgumentException e){
			}
		}

		return null;
	}

	/**
	 * Defines the pager action.
	 * 
	 * @param pagerActionType Instance that contains the pager action.
	 */
	protected void setPagerActionType(PagerActionType pagerActionType){
		if(this.pagerAction != null)
			this.pagerAction = pagerActionType.toString();
		else
			this.pagerAction = null;
	}

	/**
	 * Returns the pager action.
	 * 
	 * @return String that contains the pager action.
	 */
	public String getPagerAction(){
		return this.pagerAction;
	}

	/**
	 * Defines the pager action.
	 * 
	 * @param pagerAction String that contains the pager action.
	 */
	public void setPagerAction(String pagerAction){
		this.pagerAction = pagerAction;
	}

	/**
	 * Returns the action.
	 * 
	 * @return String that contains the action.
	 */
	public String getAction(){
		return this.action;
	}

	/**
	 * Defines the action.
	 * 
	 * @param action String that contains the action.
	 */
	public void setAction(String action){
		this.action = action;
	}

	/**
	 * Defines the action.
	 * 
	 * @param action Instance that contains the action.
	 */
	protected void setActionType(ActionType action){
		if(action != null)
			this.action = action.getMethod();
		else
			this.action = null;
	}

	/**
	 * Returns the identifier of the action forward.
	 * 
	 * @return String that contains the identifier of the forward.
	 */
	public String getForward(){
		return this.forward;
	}

	/**
	 * Defines the identifier of the action forward.
	 * 
	 * @param forward String that contains the identifier of the forward.
	 */
	public void setForward(String forward){
		this.forward = StringUtil.trim(forward);
	}

	/**
	 * Indicates if the data model should be validated.
	 * 
	 * @return True/False.
	 */
	public Boolean validateModel(){
		return this.validateModel;
	}

	/**
	 * Indicates if the data model should be validated.
	 * 
	 * @return True/False.
	 */
	public Boolean getValidateModel(){
		return validateModel();
	}

	/**
	 * Defines if the data model should be validated.
	 * 
	 * @param validateModel True/False.
	 */
	public void setValidateModel(Boolean validateModel){
		this.validateModel = validateModel;
	}

	/**
	 * Returns the CSS style for the icon of the component.
	 * 
	 * @return String that contains the CSS style for the icon.
	 */
	public String getIconStyle(){
		return this.iconStyle;
	}

	/**
	 * Defines the CSS style for the icon of the component.
	 * 
	 * @param iconStyle String that contains the CSS style for the icon.
	 */
	public void setIconStyle(String iconStyle){
		this.iconStyle = iconStyle;
	}

	/**
	 * Returns the CSS style for the icon of the component.
	 * 
	 * @return String that contains the CSS style for the icon.
	 */
	public String getIconStyleClass(){
		return this.iconStyleClass;
	}

	/**
	 * Defines the CSS style for the icon of the component.
	 * 
	 * @param iconStyleClass String that contains the CSS style for the icon.
	 */
	public void setIconStyleClass(String iconStyleClass){
		this.iconStyleClass = iconStyleClass;
	}

	/**
	 * Returns the validation properties of the data model.
	 * 
	 * @return String that contains the properties.
	 */
	public String getValidateModelProperties(){
		return this.validateModelProperties;
	}

	/**
	 * Defines the validation properties of the data model.
	 * 
	 * @param validateModelProperties String that contains the properties.
	 */
	public void setValidateModelProperties(String validateModelProperties){
		this.validateModelProperties = validateModelProperties;
	}

	/**
	 * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#buildEvents()
	 */
	protected void buildEvents() throws InternalErrorException{
		GridPropertyComponent gridComponent = (GridPropertyComponent)findAncestorWithClass(this, GridPropertyComponent.class);

		if(gridComponent == null){
			try{
				gridComponent = (GridPropertyComponent)getParent();
			}
			catch(ClassCastException e){
			}
		}

		if(gridComponent != null && gridComponent.getName() != null && gridComponent.getName().length() > 0){
			String currentOnClick = getOnClick();
			StringBuilder onClick = null;

			if(currentOnClick != null && currentOnClick.length() > 0){
				onClick = new StringBuilder();
				onClick.append(currentOnClick);

				if(currentOnClick.length() > 0 && !currentOnClick.endsWith(";"))
					onClick.append(";");
			}

			if(hasAction() && this.pagerAction != null && this.pagerAction.length() > 0){
				if(onClick == null)
					onClick = new StringBuilder();
				else
					onClick.append(" ");

				onClick.append("setObjectValue('");
				onClick.append(gridComponent.getName());
				onClick.append(".");
				onClick.append(UIConstants.PAGER_ACTION_ATTRIBUTE_ID);
				onClick.append("', '");
				onClick.append(this.pagerAction);
				onClick.append("');");
			}

			if(onClick != null && onClick.length() > 0)
				setOnClick(onClick.toString());
		}

		setOnClickAction(this.action);
		setOnClickForward(this.forward);
		setOnClickUpdateViews(this.updateViews);
		setOnClickValidateModel(this.validateModel);
		setOnClickValidateModelProperties(this.validateModelProperties);

		super.buildEvents();
	}

	/**
	 * @see br.com.concepting.framework.ui.components.BaseComponent#buildStyleClass()
	 */
	protected void buildStyleClass() throws InternalErrorException{
		ComponentType componentType = getComponentType();
		String resourcesKey = getResourcesKey();
		Boolean enabled = isEnabled();
		String styleClass = getStyleClass();
		StringBuilder styleClassBuffer = new StringBuilder();

		if(styleClass != null && styleClass.length() > 0)
			styleClassBuffer.append(styleClass);
		else{
			if(componentType != null)
				styleClassBuffer.append(componentType.getId());
			else
				styleClassBuffer.append(resourcesKey);
		}

		if(enabled == null || !enabled)
			styleClassBuffer.append(StringUtil.capitalize(Constants.DISABLED_ATTRIBUTE_ID));

		setStyleClass(styleClassBuffer.toString());

		styleClassBuffer.delete(0, styleClassBuffer.length());

		if(styleClass  != null && styleClass.length() > 0)
			styleClassBuffer.append(styleClass);
		else{
			if(componentType != null)
				styleClassBuffer.append(componentType.getId());
			else
				styleClassBuffer.append(resourcesKey);
		}

		styleClassBuffer.append(StringUtil.capitalize(UIConstants.DEFAULT_ICON_ID));

		if(enabled == null || !enabled)
			styleClassBuffer.append(StringUtil.capitalize(Constants.DISABLED_ATTRIBUTE_ID));

		this.iconStyleClass = styleClassBuffer.toString();

		String labelStyleClass = getLabelStyleClass();

		styleClassBuffer.delete(0, styleClassBuffer.length());

		if(labelStyleClass != null && labelStyleClass.length() > 0)
			styleClassBuffer.append(labelStyleClass);
		else{
			if(componentType != null){
				styleClassBuffer.append(componentType.getId());
				styleClassBuffer.append(StringUtil.capitalize(Constants.LABEL_ATTRIBUTE_ID));
			}
			else
				styleClassBuffer.append(Constants.LABEL_ATTRIBUTE_ID);
		}

		if(enabled == null || !enabled)
			styleClassBuffer.append(StringUtil.capitalize(Constants.DISABLED_ATTRIBUTE_ID));

		setLabelStyleClass(styleClassBuffer.toString());

		super.buildStyleClass();
	}

	/**
	 * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#buildAlignment()
	 */
	protected void buildAlignment() throws InternalErrorException{
		PositionType labelPosition = getLabelPositionType();

		if(labelPosition == null){
			labelPosition = PositionType.RIGHT;

			setLabelPositionType(labelPosition);
		}

		AlignmentType labelAlignment = getLabelAlignmentType();

		if(labelAlignment == null){
			if(labelPosition == PositionType.TOP || labelPosition == PositionType.BOTTOM)
				labelAlignment = AlignmentType.CENTER;
			else if(labelPosition == PositionType.LEFT)
				labelAlignment = AlignmentType.RIGHT;
			else if(labelPosition == PositionType.RIGHT)
				labelAlignment = AlignmentType.LEFT;

			setLabelAlignmentType(labelAlignment);
		}

		super.buildAlignment();
	}

	/**
	 * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#buildResources()
	 */
	protected void buildResources() throws InternalErrorException{
		String resourcesKey = getResourcesKey();

		if(resourcesKey == null || resourcesKey.length() == 0){
			ComponentType componentType = getComponentType();

			if(componentType != null && componentType == ComponentType.BUTTON){
				String name = getName();

				if(name != null && name.length() > 0)
					resourcesKey = name;
			}
			else if(componentType != null)
				resourcesKey = componentType.getId();

			setResourcesKey(resourcesKey);
		}

		super.buildResources();
	}

	/**
	 * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#buildRestrictions()
	 */
	protected void buildRestrictions() throws InternalErrorException{
		if(this.validateModel == null)
			this.validateModel = false;

		if(this.showActionFormMessages == null)
			this.showActionFormMessages = true;

		super.buildRestrictions();
	}

	/**
	 * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#initialize()
	 */
	protected void initialize() throws InternalErrorException{
		PagerActionType pagerActionType = getPagerActionType();

		if(pagerActionType == null){
			pagerActionType = PagerActionType.REFRESH_PAGE;

			setPagerActionType(pagerActionType);
		}

		ComponentType componentType = getComponentType();

		if(componentType == null){
			componentType = ComponentType.BUTTON;

			setComponentType(componentType);
		}

		super.initialize();

		Boolean render = render();

		if(render != null && render){
			GridPropertyComponent gridComponent = (GridPropertyComponent)findAncestorWithClass(this, GridPropertyComponent.class);

			if(gridComponent == null){
				try{
					gridComponent = (GridPropertyComponent)getParent();
				}
				catch(ClassCastException e){
				}
			}

			if(gridComponent != null){
				try{
					ButtonComponent buttonComponent = (ButtonComponent)this.clone();

					gridComponent.addButtonComponent(buttonComponent);
				}
				catch(CloneNotSupportedException e){
					throw new InternalErrorException(e);
				}
			}
		}
	}

	/**
	 * Renders the icon of the component.
	 * 
	 * @throws InternalErrorException Occurs when was not possible to render.
	 */
	protected void renderIcon() throws InternalErrorException{
		if((this.iconStyleClass != null && this.iconStyleClass.length() > 0) || (this.iconStyle != null && this.iconStyle.length() > 0)){
			print("<td align=\"");
			print(AlignmentType.CENTER);
			println("\" width=\"1\">");

			print("<div");

			if(this.iconStyleClass != null && this.iconStyleClass.length() > 0){
				print(" class=\"");
				print(this.iconStyleClass);
				print("\"");
			}

			if(this.iconStyle != null && this.iconStyle.length() > 0){
				print(" style=\"");
				print(this.iconStyle);

				if(!this.iconStyle.endsWith(";"))
					print(";");

				print("\"");
			}

			println("></div>");

			println("</td>");

			if(getLabelPositionType() == PositionType.BOTTOM){
				println("</tr>");
				println("<tr>");
			}
		}
	}

	/**
	 * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#renderType()
	 */
	protected void renderType() throws InternalErrorException{
	}

	/**
	 * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#renderSize()
	 */
	protected void renderSize() throws InternalErrorException{
	}

	/**
	 * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#renderLabel()
	 */
	protected void renderLabel() throws InternalErrorException{
		super.renderLabel();

		if(getLabelPositionType() == PositionType.TOP){
			println("</tr>");
			println("<tr>");
		}
	}

	/**
	 * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#renderLabelBody()
	 */
	protected void renderLabelBody() throws InternalErrorException{
		Boolean showLabel = showLabel();
		String label = getLabel();

		if(showLabel != null && showLabel && label != null && label.length() > 0)
			println(label);
	}

	/**
	 * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#renderOpen()
	 */
	protected void renderOpen() throws InternalErrorException{
		GridPropertyComponent gridComponent = (GridPropertyComponent)findAncestorWithClass(this, GridPropertyComponent.class);

		if(gridComponent == null){
			try{
				gridComponent = (GridPropertyComponent)getParent();
			}
			catch(ClassCastException e){
			}
		}

		if(gridComponent == null){
			print("<button");

			renderAttributes();

			println(">");
		}
	}

	/**
	 * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#renderBody()
	 */
	protected void renderBody() throws InternalErrorException{
		GridPropertyComponent gridComponent = (GridPropertyComponent)findAncestorWithClass(this, GridPropertyComponent.class);

		if(gridComponent == null){
			try{
				gridComponent = (GridPropertyComponent)getParent();
			}
			catch(ClassCastException e){
			}
		}

		if(gridComponent == null){
			print("<table class=\"");
			print(UIConstants.DEFAULT_CONTENT_PANEL_STYLE_CLASS);
			println("\">");
			println("<tr>");

			PositionType labelPosition = getLabelPositionType();

			if(labelPosition == PositionType.LEFT || labelPosition == PositionType.TOP){
				renderLabel();
				renderIcon();
			}
			else{
				renderIcon();
				renderLabel();
			}

			println("</tr>");
			println("</table>");
		}
	}

	/**
	 * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#renderClose()
	 */
	protected void renderClose() throws InternalErrorException{
		GridPropertyComponent gridComponent = (GridPropertyComponent)findAncestorWithClass(this, GridPropertyComponent.class);

		if(gridComponent == null){
			try{
				gridComponent = (GridPropertyComponent)getParent();
			}
			catch(ClassCastException e){
			}
		}

		if(gridComponent == null)
			println("</button>");
	}

	/**
	 * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#clearAttributes()
	 */
	protected void clearAttributes() throws InternalErrorException{
		super.clearAttributes();

		setIconStyle(null);
		setIconStyleClass(null);
		setAction(null);
		setForward(null);
		setValidateModel(null);
		setValidateModelProperties(null);
		setUpdateViews(null);
		setPagerAction(null);
		setShowActionFormMessages(null);
	}
}