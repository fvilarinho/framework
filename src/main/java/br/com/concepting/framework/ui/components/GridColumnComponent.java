package br.com.concepting.framework.ui.components;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;

import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.controller.form.ActionFormController;
import br.com.concepting.framework.controller.form.BaseActionForm;
import br.com.concepting.framework.controller.form.constants.ActionFormConstants;
import br.com.concepting.framework.controller.types.ScopeType;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.helpers.ModelInfo;
import br.com.concepting.framework.model.util.ModelUtil;
import br.com.concepting.framework.util.PropertyUtil;
import br.com.concepting.framework.util.helpers.PropertyInfo;
import br.com.concepting.framework.util.types.ComponentType;

/**
 * Class that defines the grid column component.
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
public class GridColumnComponent extends BaseOptionsPropertyComponent{
	private static final long serialVersionUID = 4958728620516766106L;

	private Boolean                        showAsImage            = null;
	private Boolean                        showAsLanguage         = null;
	private Boolean                        showCalendarButton     = null;
	private String                         imageWidth             = null;
	private String                         imageHeight            = null;
	private Boolean                        isEditable             = null;
	private Integer                        rows                   = null;
	private Integer                        columns                = null;
	private String                         headerStyleClass       = null;
	private String                         headerStyle            = null;
	private List<GridColumnStateComponent> columnStatesComponents = null;
	
	/**
	 * Indicates if the language selector should be shown.
	 * 
	 * @return True/False.
	 */
	public Boolean showAsLanguage(){
		return this.showAsLanguage;
	}

	/**
	 * Indicates if the language selector should be shown.
	 * 
	 * @return True/False.
	 */
	public Boolean getShowAsLanguage(){
		return showAsLanguage();
	}

	/**
	 * Defines if the language selector should be shown.
	 * 
	 * @param showAsLanguage True/False.
	 */
	public void setShowAsLanguage(Boolean showAsLanguage){
		this.showAsLanguage = showAsLanguage;
	}

	/**
	 * Indicates if the calendar button should be shown.
	 *  
	 * @return True/False.
	 */
	public Boolean showCalendarButton(){
		return this.showCalendarButton;
	}

	/**
	 * Indicates if the calendar button should be shown.
	 *  
	 * @return True/False.
	 */
	public Boolean getShowCalendarButton(){
		return showCalendarButton();
	}

	/**
	 * Defines if the calendar button should be shown.
	 *  
	 * @param showCalendarButton True/False.
	 */
	public void setShowCalendarButton(Boolean showCalendarButton){
		this.showCalendarButton = showCalendarButton;
	}

	/**
	 * Returns the image width.
	 * 
	 * @return String that contains the width.
	 */
	public String getImageWidth(){
		return this.imageWidth;
	}

	/**
	 * Defines the image width.
	 * 
	 * @param imageWidth String that contains the width.
	 */
	public void setImageWidth(String imageWidth){
		this.imageWidth = imageWidth;
	}

	/**
	 * Returns the image height.
	 * 
	 * @return String that contains the height.
	 */
	public String getImageHeight(){
		return this.imageHeight;
	}

	/**
	 * Defines the image height.
	 * 
	 * @param imageHeight String that contains the height.
	 */
	public void setImageHeight(String imageHeight){
		this.imageHeight = imageHeight;
	}

	/**
	 * Returns the header CSS style.
	 * 
	 * @return String that contains the header CSS style.
	 */
	public String getHeaderStyleClass(){
		return this.headerStyleClass;
	}

	/**
	 * Defines the header CSS style.
	 * 
	 * @param headerStyleClass String that contains the header CSS style.
	 */
	public void setHeaderStyleClass(String headerStyleClass){
		this.headerStyleClass = headerStyleClass;
	}

	/**
	 * Returns the header CSS style.
	 * 
	 * @return String that contains the header CSS style.
	 */
	public String getHeaderStyle(){
		return this.headerStyle;
	}

	/**
	 * Defines the header CSS style.
	 * 
	 * @param headerStyle String that contains the header CSS style.
	 */
	public void setHeaderStyle(String headerStyle){
		this.headerStyle = headerStyle;
	}

	/**
	 * Indicates if the column content should be shown as an image.
	 * 
	 * @return True/False.
	 */
	public Boolean showAsImage(){
		return this.showAsImage;
	}

	/**
	 * Indicates if the column content should be shown as an image.
	 * 
	 * @return True/False.
	 */
	public Boolean getShowAsImage(){
		return showAsImage();
	}

	/**
	 * Defines if the column content should be shown as an image.
	 * 
	 * @param showAsImage True/False.
	 */
	public void setShowAsImage(Boolean showAsImage){
		this.showAsImage = showAsImage;
	}

	/**
	 * Returns the number of rows used in an editable content.
	 * 
	 * @return Numeric value that contains the number of rows.
	 */
	public Integer getRows(){
		return this.rows;
	}

	/**
	 * Defines the number of rows used in an editable content.
	 * 
	 * @param rows Numeric value that contains the number of rows.
	 */
	public void setRows(Integer rows){
		this.rows = rows;
	}

	/**
	 * Returns the number of columns used in an editable content.
	 * 
	 * @return Numeric value that contains the number of columns.
	 */
	public Integer getColumns(){
		return this.columns;
	}

	/**
	 * Defines the number of columns used in an editable content.
	 * 
	 * @param columns Numeric value that contains the number of columns.
	 */
	public void setColumns(Integer columns){
		this.columns = columns;
	}

	/**
	 * Adds a column state component.
	 * 
	 * @param columnStateComponent Instance that contains the column state
	 * component.
	 */
	public void addColumnStateComponent(GridColumnStateComponent columnStateComponent){
		if(columnStateComponent != null){
			if(this.columnStatesComponents == null)
				this.columnStatesComponents = PropertyUtil.instantiate(Constants.DEFAULT_LIST_CLASS);

			this.columnStatesComponents.add(columnStateComponent);
		}
	}

	/**
	 * Returns the list of column states.
	 * 
	 * @return List that contains the column states.
	 */
	protected List<GridColumnStateComponent> getColumnStatesComponents(){
		return this.columnStatesComponents;
	}

	/**
	 * Defines the list of column states.
	 * 
	 * @param columnStatesComponents List that contains the column states.
	 */
	protected void setColumnStatesComponents(List<GridColumnStateComponent> columnStatesComponents){
		this.columnStatesComponents = columnStatesComponents;
	}

	/**
	 * Indicates if the content is editable.
	 * 
	 * @return True/False.
	 */
	public Boolean isEditable(){
		return this.isEditable;
	}

	/**
	 * Indicates if the content is editable.
	 * 
	 * @return True/False.
	 */
	public Boolean getIsEditable(){
		return isEditable();
	}

	/**
	 * Defines if the content is editable.
	 * 
	 * @param isEditable True/False.
	 */
	public void setIsEditable(Boolean isEditable){
		this.isEditable = isEditable;
	}

	/**
	 * @see br.com.concepting.framework.ui.components.BasePropertyComponent#buildRestrictions()
	 */
	protected void buildRestrictions() throws InternalErrorException{
		if(this.showCalendarButton == null)
			this.showCalendarButton = true;

		super.buildRestrictions();
	}

	/**
	 * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#buildResources()
	 */
	protected void buildResources() throws InternalErrorException{
		GridPropertyComponent gridComponent = (GridPropertyComponent)findAncestorWithClass(this, GridPropertyComponent.class);

		if(gridComponent != null){
			String resourceId = getResourcesId();

			if(resourceId == null || resourceId.length() == 0){
				resourceId = gridComponent.getResourcesId();

				setResourcesId(resourceId);
			}

			String resourcesKey = getResourcesKey();
			StringBuilder resourcesKeyBuffer = new StringBuilder();

			if(resourcesKey == null || resourcesKey.length() == 0){
				String gridComponentName = gridComponent.getName();
				String name = getName();

				if(name == null || name.length() == 0 || gridComponentName == null || gridComponentName.length() == 0)
					return;

				resourcesKeyBuffer.append(gridComponentName);
				resourcesKeyBuffer.append(".");
				resourcesKeyBuffer.append(name);

				resourcesKey = resourcesKeyBuffer.toString();

				setResourcesKey(resourcesKey);
			}
		}

		super.buildResources();
	}

	/**
	 * @see br.com.concepting.framework.ui.components.BaseOptionsPropertyComponent#buildStyleClass()
	 */
	protected void buildStyleClass() throws InternalErrorException{
	}

	/**
	 * @see br.com.concepting.framework.ui.components.BasePropertyComponent#buildStyle()
	 */
	protected void buildStyle() throws InternalErrorException{
	}

	/**
	 * @see br.com.concepting.framework.ui.components.BasePropertyComponent#initialize()
	 */
	@SuppressWarnings("unchecked")
	protected void initialize() throws InternalErrorException{
		GridPropertyComponent gridComponent = (GridPropertyComponent)findAncestorWithClass(this, GridPropertyComponent.class);

		if(gridComponent == null){
			try{
				gridComponent = (GridPropertyComponent)getParent();
			}
			catch(ClassCastException e){
			}
		}

		if(gridComponent != null){
			ActionFormController actionFormController = getActionFormController();
			String actionFormName = gridComponent.getActionFormName();
			String name = getName();

			if(actionFormController != null && actionFormName != null && actionFormName.length() > 0 && name != null && name.length() > 0){
				setActionFormName(actionFormName);
				
				ScopeType datasetScope = gridComponent.getDatasetScopeType();

				if(datasetScope == null){
					datasetScope = ActionFormConstants.DEFAULT_DATASET_SCOPE_TYPE;

					setDatasetScopeType(datasetScope);
				}
				
				try{
					String dataset = gridComponent.getDataset();
	
					if(dataset != null && dataset.length() > 0){
						Object instance = null;
						
						if(datasetScope == ScopeType.MODEL){
							BaseActionForm<? extends BaseModel> actionFormInstance = actionFormController.getActionFormInstance();
							
							instance = (actionFormInstance != null ? actionFormInstance.getModel() : null);
							
							if(instance != null){
								Class<? extends BaseModel> modelClass = (Class<? extends BaseModel>)instance.getClass();
								ModelInfo modelInfo = ModelUtil.getInfo(modelClass);
								PropertyInfo propertyInfo = modelInfo.getPropertyInfo(dataset);
	
								if(propertyInfo != null){
									Boolean hasModel = propertyInfo.hasModel();
	
									if(hasModel != null && hasModel){
										Class<? extends BaseModel> collectionItemsClass = (Class<? extends BaseModel>)propertyInfo.getCollectionItemsClass();
	
										if(collectionItemsClass != null){
											modelInfo = ModelUtil.getInfo(collectionItemsClass);
	
											if(modelInfo != null)
												setPropertyInfo(modelInfo.getPropertyInfo(name));
											else
												setPropertyInfo(null);
										}
										else
											setPropertyInfo(null);
									}
									else
										setPropertyInfo(null);
								}
								else
									setPropertyInfo(null);
							}
							else
								setPropertyInfo(null);
						}
						else{
							Collection<?> datasetValues = getSystemController().getAttribute(dataset, datasetScope);
	
							if(datasetValues != null && !datasetValues.isEmpty()){
								instance = datasetValues.iterator().next();
	
								Class<? extends BaseModel> modelClass = (Class<? extends BaseModel>)instance.getClass();
								ModelInfo modelInfo = ModelUtil.getInfo(modelClass);
	
								if(modelInfo != null)
									setPropertyInfo(modelInfo.getPropertyInfo(name));
								else
									setPropertyInfo(null);
							}
							else
								setPropertyInfo(null);
						}
					}
					else
						setPropertyInfo(null);
				} 
				catch(IllegalArgumentException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException | ClassNotFoundException | NoSuchFieldException e){
					setPropertyInfo(null);
				}
			}
			else
				setPropertyInfo(null);
		
			setComponentType(ComponentType.GRID_COLUMN);

			super.initialize();
		}
	}

	/**
	 * @see br.com.concepting.framework.ui.components.BasePropertyComponent#renderOpen()
	 */
	protected void renderOpen() throws InternalErrorException{
	}

	/**
	 * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#renderBody()
	 */
	protected void renderBody() throws InternalErrorException{
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
					if(!(this instanceof GridColumnGroupComponent)){
						GridColumnComponent gridColumnComponent = (GridColumnComponent)this.clone();

						if(getParent() instanceof GridColumnGroupComponent){
							GridColumnGroupComponent gridColumnGroupComponent = (GridColumnGroupComponent)getParent();

							if(gridColumnGroupComponent != null)
								gridColumnComponent.setParent((GridColumnGroupComponent)gridColumnGroupComponent.clone());
						}

						gridComponent.addColumnComponent(gridColumnComponent);
					}
				}
				catch(CloneNotSupportedException e){
					throw new InternalErrorException(e);
				}
			}
		}
	}

	/**
	 * @see br.com.concepting.framework.ui.components.BasePropertyComponent#renderClose()
	 */
	protected void renderClose() throws InternalErrorException{
	}

	/**
	 * @see br.com.concepting.framework.ui.components.BasePropertyComponent#clearAttributes()
	 */
	protected void clearAttributes() throws InternalErrorException{
		super.clearAttributes();

		setShowAsImage(null);
		setShowCalendarButton(null);
		setImageWidth(null);
		setImageHeight(null);
		setIsEditable(null);
		setRows(null);
		setColumns(null);
		setHeaderStyleClass(null);
		setHeaderStyle(null);
		setColumnStatesComponents(null);
	}
}