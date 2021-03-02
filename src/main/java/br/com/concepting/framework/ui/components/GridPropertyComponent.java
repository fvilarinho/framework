package br.com.concepting.framework.ui.components;

import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.controller.action.types.ActionType;
import br.com.concepting.framework.controller.form.constants.ActionFormConstants;
import br.com.concepting.framework.controller.types.ScopeType;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.SystemSessionModel;
import br.com.concepting.framework.model.util.ModelUtil;
import br.com.concepting.framework.processors.ExpressionProcessor;
import br.com.concepting.framework.processors.ExpressionProcessorUtil;
import br.com.concepting.framework.security.controller.SecurityController;
import br.com.concepting.framework.security.model.LoginSessionModel;
import br.com.concepting.framework.ui.components.types.PagerActionType;
import br.com.concepting.framework.ui.constants.UIConstants;
import br.com.concepting.framework.ui.controller.UIController;
import br.com.concepting.framework.util.PropertyUtil;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.helpers.PropertyInfo;
import br.com.concepting.framework.util.types.AlignmentType;
import br.com.concepting.framework.util.types.ComponentType;
import br.com.concepting.framework.util.types.SortOrderType;

import javax.servlet.jsp.JspException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.security.SecureRandom;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

/**
 * Class that defines the grid component.
 *
 * @author fvilarinho
 * @since 2.0.0
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
public class GridPropertyComponent extends OptionsPropertyComponent{
    private static final long serialVersionUID = -5946996131314362186L;
    
    private Boolean scrolling = null;
    private String scrollingWidth = null;
    private Boolean showSelection = null;
    private String headerStyle = null;
    private String headerStyleClass = null;
    private List<GridColumnComponent> columnsComponents = null;
    private PagerComponent pagerComponent = null;
    private Collection<ButtonComponent> buttonsComponents = null;
    private List<GridRowStateComponent> rowStatesComponents = null;
    
    /**
     * Returns the scrolling width of the component.
     *
     * @return String that contains the scrolling width.
     */
    public String getScrollingWidth(){
        return this.scrollingWidth;
    }
    
    /**
     * Defines the scrolling width of the component.
     *
     * @param scrollingWidth String that contains the scrolling width.
     */
    public void setScrollingWidth(String scrollingWidth){
        this.scrollingWidth = scrollingWidth;
    }
    
    /**
     * Indicates if the component should do scrolling.
     *
     * @return True/False.
     */
    public Boolean getScrolling(){
        return this.scrolling;
    }
    
    /**
     * Defines if the component should do scrolling.
     *
     * @param scrolling True/False.
     */
    public void setScrolling(Boolean scrolling){
        this.scrolling = scrolling;
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
     * Returns the list of columns of the component.
     *
     * @return List that contains the columns.
     */
    public Collection<GridColumnComponent> getColumnsComponents(){
        return this.columnsComponents;
    }
    
    /**
     * Defines the list of columns of the component.
     *
     * @param columnsComponents List that contains the columns.
     */
    public void setColumnsComponents(List<GridColumnComponent> columnsComponents){
        this.columnsComponents = columnsComponents;
    }
    
    /**
     * Returns the list of buttons of the component.
     *
     * @return List that contains the buttons.
     */
    public Collection<ButtonComponent> getButtonsComponents(){
        return this.buttonsComponents;
    }
    
    /**
     * Defines the list of buttons of the component.
     *
     * @param buttonsComponents List that contains the buttons.
     */
    public void setButtonsComponents(Collection<ButtonComponent> buttonsComponents){
        this.buttonsComponents = buttonsComponents;
    }
    
    /**
     * Returns the list of row states of the component.
     *
     * @return List that contains the row states.
     */
    public List<GridRowStateComponent> getRowStatesComponents(){
        return this.rowStatesComponents;
    }
    
    /**
     * Defines the list of row states of the component.
     *
     * @param rowStatesComponents List that contains the row states.
     */
    public void setRowStatesComponents(List<GridRowStateComponent> rowStatesComponents){
        this.rowStatesComponents = rowStatesComponents;
    }
    
    /**
     * Indicates if the component will show the selection option.
     *
     * @return True/False.
     */
    protected Boolean showSelection(){
        return this.showSelection;
    }
    
    /**
     * Indicates if the component will show the selection option.
     *
     * @return True/False.
     */
    protected Boolean isShowSelection(){
        return showSelection();
    }
    
    /**
     * Indicates if the component will show the selection option.
     *
     * @return True/False.
     */
    protected Boolean getShowSelection(){
        return isShowSelection();
    }
    
    /**
     * Defines if the component will show the selection option.
     *
     * @param showSelection True/False.
     */
    protected void setShowSelection(Boolean showSelection){
        this.showSelection = showSelection;
    }
    
    /**
     * Defines the instance of the pager component
     *
     * @param pagerComponent Instance that contains the properties of the
     * component.
     */
    protected void setPagerComponent(PagerComponent pagerComponent){
        if(pagerComponent != null){
            pagerComponent.setPageContext(this.pageContext);
            pagerComponent.setOutputStream(getOutputStream());
            pagerComponent.setParent(null);
        }
        
        this.pagerComponent = pagerComponent;
    }
    
    /**
     * Returns the instance of the pager component
     *
     * @return Instance that contains the properties of the component.
     */
    protected PagerComponent getPagerComponent(){
        return this.pagerComponent;
    }
    
    /**
     * Adds a new button.
     *
     * @param buttonComponent Instance that contains the button component.
     */
    protected void addButtonComponent(ButtonComponent buttonComponent){
        if(buttonComponent != null){
            if(getButtonsComponents() == null)
                this.buttonsComponents = PropertyUtil.instantiate(Constants.DEFAULT_LIST_CLASS);
            
            buttonComponent.setPageContext(this.pageContext);
            buttonComponent.setOutputStream(getOutputStream());
            buttonComponent.setParent(null);
            
            this.buttonsComponents.add(buttonComponent);
        }
    }
    
    /**
     * Adds a new column.
     *
     * @param columnComponent Instance that contains the column component.
     */
    protected void addColumnComponent(GridColumnComponent columnComponent){
        if(columnComponent != null){
            if(this.columnsComponents == null)
                this.columnsComponents = PropertyUtil.instantiate(Constants.DEFAULT_LIST_CLASS);
            
            this.columnsComponents.add(columnComponent);
        }
    }
    
    /**
     * Adds a new row state.
     *
     * @param rowStateComponent Instance that contains the row state component.
     */
    protected void addRowStateComponent(GridRowStateComponent rowStateComponent){
        if(rowStateComponent != null){
            if(this.rowStatesComponents == null)
                this.rowStatesComponents = PropertyUtil.instantiate(Constants.DEFAULT_LIST_CLASS);
            
            this.rowStatesComponents.add(rowStateComponent);
        }
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#buildName()
     */
    protected void buildName() throws InternalErrorException{
        String name = getName();
        String dataset = getDataset();
        
        if((name == null || name.length() == 0) && dataset != null && dataset.length() > 0){
            name = dataset;
            
            setName(name);
        }
        
        super.buildName();
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.OptionsPropertyComponent#buildRestrictions()
     */
    protected void buildRestrictions() throws InternalErrorException{
        String name = getName();
        
        if(name != null && name.length() > 0 && this.showSelection == null)
            this.showSelection = true;
        
        if(this.scrolling == null)
            this.scrolling = false;
        
        super.buildRestrictions();
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BaseOptionsPropertyComponent#initialize()
     */
    protected void initialize() throws InternalErrorException{
        setComponentType(ComponentType.GRID);
        
        super.initialize();
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BaseOptionsPropertyComponent#renderDatasetIndexesAttributes()
     */
    protected void renderDatasetIndexesAttributes() throws InternalErrorException{
        String actionFormName = getActionFormName();
        String name = getName();
        Boolean hasInvalidPropertyDefinition = hasInvalidPropertyDefinition();
        
        if(actionFormName != null && actionFormName.length() > 0 && name != null && name.length() > 0 && (hasInvalidPropertyDefinition == null || !hasInvalidPropertyDefinition)){
            super.renderDatasetIndexesAttributes();
            
            StringBuilder nameBuffer = new StringBuilder();
            
            nameBuffer.append(name);
            nameBuffer.append(".");
            nameBuffer.append(UIConstants.SORT_PROPERTY_ATTRIBUTE_ID);
            
            HiddenPropertyComponent sortPropertyComponent = new HiddenPropertyComponent();
            
            sortPropertyComponent.setPageContext(this.pageContext);
            sortPropertyComponent.setOutputStream(getOutputStream());
            sortPropertyComponent.setActionFormName(actionFormName);
            sortPropertyComponent.setName(nameBuffer.toString());
            sortPropertyComponent.setValue(getUIController().getSortProperty(name));
            
            try{
                sortPropertyComponent.doStartTag();
                sortPropertyComponent.doEndTag();
            }
            catch(JspException e){
                throw new InternalErrorException(e);
            }
            
            nameBuffer.delete(0, nameBuffer.length());
            nameBuffer.append(name);
            nameBuffer.append(".");
            nameBuffer.append(UIConstants.SORT_ORDER_ATTRIBUTE_ID);
            
            HiddenPropertyComponent sortOrderPropertyComponent = new HiddenPropertyComponent();
            
            sortOrderPropertyComponent.setPageContext(this.pageContext);
            sortOrderPropertyComponent.setOutputStream(getOutputStream());
            sortOrderPropertyComponent.setActionFormName(actionFormName);
            sortOrderPropertyComponent.setName(nameBuffer.toString());
            sortOrderPropertyComponent.setValue(getUIController().getSortOrder(name).toString());
            
            try{
                sortOrderPropertyComponent.doStartTag();
                sortOrderPropertyComponent.doEndTag();
            }
            catch(JspException e){
                throw new InternalErrorException(e);
            }
        }
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BaseComponent#renderName()
     */
    protected void renderName() throws InternalErrorException{
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BaseComponent#renderId()
     */
    protected void renderId() throws InternalErrorException{
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
     * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#renderEnabled()
     */
    protected void renderEnabled() throws InternalErrorException{
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BasePropertyComponent#renderReadOnly()
     */
    protected void renderReadOnly() throws InternalErrorException{
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BasePropertyComponent#renderEvents()
     */
    protected void renderEvents() throws InternalErrorException{
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BasePropertyComponent#renderValue()
     */
    protected void renderValue() throws InternalErrorException{
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#renderOpen()
     */
    protected void renderOpen() throws InternalErrorException{
        if(this.scrolling){
            print("<div style=\"overflow: auto;");
            
            if(this.scrollingWidth != null && this.scrollingWidth.length() > 0){
                print(" width: ");
                print(this.scrollingWidth);
                
                if(!this.scrollingWidth.endsWith("px") && !this.scrollingWidth.endsWith("%"))
                    println("px");
                
                print(";");
            }
            
            println("\">");
        }
        
        print("<table");
        
        super.renderAttributes();
        
        println(">");
        println("<tr>");
        println("<td>");
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BaseComponent#renderBody()
     */
    protected void renderBody() throws InternalErrorException{
        Boolean hasInvalidPropertyDefinition = hasInvalidPropertyDefinition();
        
        if(hasInvalidPropertyDefinition != null && hasInvalidPropertyDefinition)
            super.renderInvalidPropertyMessage();
        else{
            renderHeader();
            renderDetails();
            renderFooter();
        }
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BaseOptionsPropertyComponent#renderNoDatasetMessage()
     */
    public void renderNoDatasetMessage() throws InternalErrorException{
        String message = getNoDatasetMessage();
        
        if(message != null && message.length() > 0){
            println("<tr>");
            print("<td align=\"");
            print(AlignmentType.CENTER);
            print("\" height=\"50\" class=\"");
            print(UIConstants.DEFAULT_GRID_DETAIL_STYLE_CLASS);
            print("\"");
            
            if(this.columnsComponents != null && !this.columnsComponents.isEmpty()){
                print(" colspan=\"");
                print(this.columnsComponents.size() + (this.showSelection != null && this.showSelection ? 1 : 0));
                print("\"");
            }
            
            print(">");
            print(message);
            println("</td>");
            println("</tr>");
        }
    }
    
    /**
     * Renders the details of the component.
     *
     * @throws InternalErrorException Occurs when was not possible to render.
     */
    private void renderDetails() throws InternalErrorException{
        SecurityController securityController = getSecurityController();
        UIController uiController = getUIController();
        String actionFormName = getActionFormName();
        String name = getName();
        
        if(securityController == null || uiController == null || actionFormName == null || actionFormName.length() == 0 || name == null || name.length() == 0)
            return;
        
        LoginSessionModel loginSession = securityController.getLoginSession();
        SystemSessionModel systemSession = (loginSession != null ? loginSession.getSystemSession() : null);
        
        if(systemSession == null)
            return;
        
        String domain = systemSession.getId();
        Boolean hasNodeDataset = hasNoDataset();
        
        try{
            if(hasNodeDataset != null && hasNodeDataset)
                renderNoDatasetMessage();
            else{
                PropertyInfo propertyInfo = getPropertyInfo();
                Locale currentLanguage = getCurrentLanguage();
                ExpressionProcessor expressionProcessor = new ExpressionProcessor(domain, currentLanguage);
                StringBuilder nameBuffer = null;
                Object value = getValue();
                String sortPropertyId = getUIController().getSortProperty(name);
                SortOrderType sortOrder = getUIController().getSortOrder(name);
                BaseModel currentModel = null;
                StringBuilder optionComponentId = null;
                BaseOptionPropertyComponent optionComponent = null;
                Object optionValue = null;
                ImageComponent imageComponent = null;
                BasePropertyComponent columnPropertyComponent = null;
                GridColumnGroupComponent columnGroupComponent = null;
                PropertyInfo columnPropertyInfo = null;
                Boolean columnIsNumber = null;
                Boolean columnIsBoolean = null;
                Boolean columnIsDate = null;
                Boolean columnIsTime = null;
                String columnName = null;
                String columnLabel = null;
                String columnTooltip = null;
                AlignmentType columnAlignment = null;
                String columnWidth = null;
                String columnStyleClass = null;
                String columnStyle = null;
                String columnPattern = null;
                Boolean columnUseAdditionalFormatting = null;
                Integer columnPrecision = null;
                Integer columnSize = null;
                Integer columnMaximumLength = null;
                Number columnMinimumValue = null;
                Number columnMaximumValue = null;
                Integer columnRows = null;
                Integer columnColumns = null;
                Class<?> columnClass = null;
                Object columnValue = null;
                String columnValueLabel = null;
                String columnDataset = null;
                ScopeType columnDatasetScope = null;
                Boolean columnEditable = null;
                Boolean columnHasMultipleLines = null;
                String columnOnSelect = null;
                String columnOnBlur = null;
                String columnOnClick = null;
                String columnOnChange = null;
                String columnOnFocus = null;
                String columnOnKeyPress = null;
                String columnOnKeyUp = null;
                String columnOnKeyDown = null;
                String columnOnMouseOver = null;
                String columnOnMouseOut = null;
                GridColumnStateComponent columnStateComponent = null;
                List<GridColumnStateComponent> columnStatesComponents = null;
                Boolean rowRemove = null;
                GridRowStateComponent rowStateComponent = null;
                Boolean expressionResult = null;
                Integer aggregateColumnCount = null;
                String aggregateStyleClass = null;
                String aggregateStyle = null;
                Object aggregatePropertValue = null;
                Object[] aggregatePropertiesValues = null;
                Integer aggregatePropertiesValuesIndex = null;
                Collection<String> aggregatePropertiesList = null;
                String[] aggregateProperties = null;
                Boolean found = null;
                
                if(this.columnsComponents != null && !this.columnsComponents.isEmpty()){
                    List<? extends BaseModel> datasetValues = getDatasetValues();
                    
                    if(datasetValues != null && !datasetValues.isEmpty()){
                        for(GridColumnComponent columnComponent: this.columnsComponents){
                            if(columnComponent.getParent() instanceof GridColumnGroupComponent)
                                columnGroupComponent = (GridColumnGroupComponent) columnComponent.getParent();
                            else
                                columnGroupComponent = null;
                            
                            columnName = columnComponent.getName();
                            
                            if(columnName != null && columnName.length() > 0 && columnGroupComponent != null && columnGroupComponent.aggregate() != null && columnGroupComponent.aggregate()){
                                if(aggregateColumnCount == null)
                                    aggregateColumnCount = 1;
                                else
                                    aggregateColumnCount++;
                                
                                if(aggregatePropertiesList == null)
                                    aggregatePropertiesList = PropertyUtil.instantiate(Constants.DEFAULT_LIST_CLASS);
                                
                                aggregatePropertiesList.add(columnName);
                            }
                        }
                        
                        try{
                            if(aggregatePropertiesList != null && !aggregatePropertiesList.isEmpty()){
                                aggregatePropertiesValues = new Object[aggregatePropertiesList.size()];
                                aggregateProperties = new String[aggregatePropertiesList.size()];
                                aggregateProperties = aggregatePropertiesList.toArray(aggregateProperties);
                                datasetValues = ModelUtil.aggregateAndSort(datasetValues, aggregateProperties, sortPropertyId, sortOrder);
                            }
                            else{
                                if(sortOrder != null && sortPropertyId != null && sortPropertyId.length() > 0)
                                    ModelUtil.sort(datasetValues, sortPropertyId, sortOrder);
                            }
                        }
                        catch(NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException | ClassCastException | IllegalArgumentException | UnsupportedEncodingException e){
                        }
                        
                        setDatasetValues(datasetValues);
                    }
                    
                    Integer datasetStartIndex = getDatasetStartIndex();
                    Integer datasetEndIndex = getDatasetEndIndex();
                    
                    if(this.pagerComponent != null){
                        datasetStartIndex = this.pagerComponent.getDatasetStartIndex();
                        datasetEndIndex = this.pagerComponent.getDatasetEndIndex();
                        
                        setDatasetStartIndex(datasetStartIndex);
                        setDatasetEndIndex(datasetEndIndex);
                    }
                    
                    for(int cont1 = datasetStartIndex; cont1 < datasetEndIndex; cont1++){
                        currentModel = (cont1 < datasetValues.size() ? datasetValues.get(cont1) : null);
                        
                        if(currentModel != null){
                            if(currentModel.getIndex() == null)
                                currentModel.setIndex(new SecureRandom().nextInt());
                            
                            ExpressionProcessorUtil.setVariable(domain, Constants.ITEM_ATTRIBUTE_ID, currentModel);
                            
                            expressionProcessor.setDeclaration(currentModel);
                            
                            rowStateComponent = null;
                            columnStateComponent = null;
                            columnStyleClass = null;
                            columnStyle = null;
                            expressionResult = true;
                            rowRemove = false;
                            found = false;
                            
                            if(this.rowStatesComponents != null && !this.rowStatesComponents.isEmpty()){
                                
                                for(int cont2 = 0; cont2 < this.rowStatesComponents.size(); cont2++){
                                    rowStateComponent = this.rowStatesComponents.get(cont2);
                                    
                                    try{
                                        expressionResult = expressionProcessor.evaluate(rowStateComponent.getExpression());
                                    }
                                    catch(InternalErrorException e){
                                    }
                                    
                                    if(expressionResult != null && expressionResult){
                                        rowRemove = rowStateComponent.remove();
                                        found = true;
                                        
                                        break;
                                    }
                                }
                            }
                            
                            if(rowRemove != null && rowRemove){
                                datasetValues.remove(cont1);
                                
                                if(this.pagerComponent != null){
                                    this.pagerComponent.setDatasetValues(datasetValues);
                                    this.pagerComponent.refreshPageIndexes();
                                    
                                    datasetEndIndex = this.pagerComponent.getDatasetEndIndex();
                                    
                                    setDatasetEndIndex(datasetEndIndex);
                                }
                                
                                cont1--;
                                
                                continue;
                            }
                            
                            if(!found)
                                rowStateComponent = null;
                            
                            if(aggregatePropertiesList != null && !aggregatePropertiesList.isEmpty()){
                                aggregatePropertiesValuesIndex = 0;
                                
                                for(GridColumnComponent columnComponent: this.columnsComponents){
                                    columnName = columnComponent.getName();
                                    columnPattern = columnComponent.getPattern();
                                    
                                    if(columnComponent.getParent() instanceof GridColumnGroupComponent)
                                        columnGroupComponent = (GridColumnGroupComponent) columnComponent.getParent();
                                    else
                                        columnGroupComponent = null;
                                    
                                    if(columnGroupComponent != null && columnGroupComponent.aggregate() != null && columnGroupComponent.aggregate()){
                                        aggregatePropertValue = aggregatePropertiesValues[aggregatePropertiesValuesIndex];
                                        
                                        try{
                                            if(columnName != null && columnName.length() > 0)
                                                columnValue = PropertyUtil.getValue(currentModel, columnName);
                                            else
                                                columnValue = columnComponent.getValue();
                                        }
                                        catch(IllegalAccessException | InvocationTargetException | NoSuchMethodException e){
                                            columnValue = columnComponent.getValue();
                                        }
                                        
                                        columnUseAdditionalFormatting = columnComponent.useAdditionalFormatting();
                                        columnPrecision = columnComponent.getPrecision();
                                        columnValueLabel = PropertyUtil.format(columnValue, columnPattern, columnUseAdditionalFormatting, columnPrecision, currentLanguage);
                                        
                                        if((aggregatePropertValue == null && columnValueLabel != null) || (aggregatePropertValue != null && columnValueLabel != null && !aggregatePropertValue.equals(columnValueLabel))){
                                            aggregateStyleClass = columnGroupComponent.getStyleClass();
                                            aggregateStyle = columnGroupComponent.getStyle();
                                            
                                            if(aggregateStyleClass == null || aggregateStyleClass.length() == 0)
                                                aggregateStyleClass = UIConstants.DEFAULT_GRID_AGGREGATE_STYLE_CLASS;
                                            
                                            println("<tr>");
                                            print("<td class=\"");
                                            print(aggregateStyleClass);
                                            print("\"");
                                            
                                            if(aggregateStyle != null && aggregateStyle.length() > 0){
                                                print(" style=\"");
                                                print(aggregateStyle);
                                                
                                                if(!aggregateStyle.endsWith(";"))
                                                    print(";");
                                                
                                                print("\"");
                                            }
                                            
                                            print(" colspan=\"");
                                            print((this.columnsComponents.size() + (this.showSelection != null && this.showSelection ? 1 : 0)) - (aggregateColumnCount != null ? aggregateColumnCount : 0));
                                            print("\">");
                                            
                                            if(columnValueLabel != null && columnValueLabel.length() > 0){
                                                print("&nbsp;");
                                                println(columnValueLabel);
                                            }
                                            
                                            println("</td>");
                                            println("</tr>");
                                            
                                            aggregatePropertiesValues[aggregatePropertiesValuesIndex] = columnValueLabel;
                                            
                                            for(int pos = (aggregatePropertiesValuesIndex + 1); pos < aggregatePropertiesValues.length; pos++)
                                                aggregatePropertiesValues[pos] = null;
                                            
                                            aggregatePropertiesValuesIndex++;
                                        }
                                    }
                                }
                            }
                            
                            println("<tr>");
                            
                            if(this.showSelection != null && this.showSelection){
                                if(rowStateComponent != null){
                                    columnStyleClass = rowStateComponent.getStyleClass();
                                    columnStyle = rowStateComponent.getStyle();
                                }
                                
                                if(columnStyleClass == null || columnStyleClass.length() == 0)
                                    columnStyleClass = UIConstants.DEFAULT_GRID_DETAIL_STYLE_CLASS;
                                
                                columnStyleClass = ExpressionProcessorUtil.fillVariablesInString(domain, columnStyleClass);
                                columnStyle = ExpressionProcessorUtil.fillVariablesInString(domain, columnStyle);
                                columnOnSelect = ExpressionProcessorUtil.fillVariablesInString(domain, columnOnSelect, currentLanguage);
                                
                                print("<td");
                                
                                if(columnStyleClass != null && columnStyleClass.length() > 0){
                                    print(" class=\"");
                                    print(columnStyleClass);
                                    print("\"");
                                }
                                
                                if(columnStyle != null && columnStyle.length() > 0){
                                    print(" style=\"");
                                    print(columnStyle);
                                    print("\"");
                                }
                                
                                print(" align=\"");
                                print(AlignmentType.CENTER);
                                println("\" width=\"1\">");
                                
                                optionValue = currentModel;
                                
                                if((propertyInfo.isModel() == null || !propertyInfo.isModel()) && (propertyInfo.hasModel() == null || !propertyInfo.hasModel())){
                                    try{
                                        optionValue = PropertyUtil.getValue(optionValue, propertyInfo.getId());
                                    }
                                    catch(IllegalAccessException | InvocationTargetException | NoSuchMethodException e){
                                        throw new InternalErrorException(e);
                                    }
                                }
                                
                                if(optionComponentId == null)
                                    optionComponentId = new StringBuilder();
                                else
                                    optionComponentId.delete(0, optionComponentId.length());
                                
                                optionComponentId.append(name);
                                optionComponentId.append(cont1);
                                
                                if(propertyInfo.isCollection() != null && propertyInfo.isCollection())
                                    optionComponent = new CheckPropertyComponent();
                                else
                                    optionComponent = new RadioPropertyComponent();
                                
                                optionComponent.setPageContext(this.pageContext);
                                optionComponent.setOutputStream(getOutputStream());
                                optionComponent.setPropertyInfo(propertyInfo);
                                optionComponent.setActionFormName(actionFormName);
                                optionComponent.setId(optionComponentId.toString());
                                optionComponent.setName(name);
                                optionComponent.setShowLabel(false);
                                optionComponent.setOptionValue(optionValue);
                                optionComponent.setValue(value);
                                optionComponent.setOnClick(columnOnSelect);
                                optionComponent.setOnClickAction(getOnSelectAction());
                                optionComponent.setOnClickForward(getOnSelectForward());
                                optionComponent.setOnClickUpdateViews(getOnSelectUpdateViews());
                                optionComponent.setOnClickValidateModel(getOnSelectValidateModel());
                                optionComponent.setOnClickValidateModelProperties(getOnSelectValidateModelProperties());
                                optionComponent.setEnabled(isEnabled());
                                optionComponent.setParent(this);
                                
                                try{
                                    optionComponent.doStartTag();
                                    optionComponent.doEndTag();
                                }
                                catch(JspException e){
                                    throw new InternalErrorException(e);
                                }
                                
                                println("</td>");
                            }
                            
                            for(GridColumnComponent columnComponent: this.columnsComponents){
                                columnPropertyInfo = columnComponent.getPropertyInfo();
                                columnName = columnComponent.getName();
                                columnLabel = columnComponent.getLabel();
                                columnTooltip = columnComponent.getTooltip();
                                columnWidth = columnComponent.getWidth();
                                columnSize = columnComponent.getSize();
                                columnMaximumLength = columnComponent.getMaximumLength();
                                columnMinimumValue = columnComponent.getMinimumValue();
                                columnMaximumValue = columnComponent.getMaximumValue();
                                columnRows = columnComponent.getRows();
                                columnColumns = columnComponent.getColumns();
                                columnDataset = columnComponent.getDataset();
                                columnDatasetScope = columnComponent.getDatasetScopeType();
                                columnOnBlur = columnComponent.getOnBlur();
                                columnOnFocus = columnComponent.getOnFocus();
                                columnOnChange = columnComponent.getOnChange();
                                columnOnClick = columnComponent.getOnClick();
                                columnOnKeyPress = columnComponent.getOnKeyPress();
                                columnOnKeyUp = columnComponent.getOnKeyUp();
                                columnOnKeyDown = columnComponent.getOnKeyDown();
                                columnOnMouseOver = columnComponent.getOnMouseOver();
                                columnOnMouseOut = columnComponent.getOnMouseOut();
                                columnUseAdditionalFormatting = columnComponent.useAdditionalFormatting();
                                columnPrecision = columnComponent.getPrecision();
                                columnPattern = columnComponent.getPattern();
                                columnStyleClass = (rowStateComponent != null ? rowStateComponent.getStyleClass() : columnComponent.getStyleClass());
                                columnStyle = (rowStateComponent != null ? rowStateComponent.getStyle() : columnComponent.getStyle());
                                columnEditable = columnComponent.isEditable();
                                columnHasMultipleLines = (columnComponent.getRows() != null && columnComponent.getRows() > 0) || (columnComponent.getColumns() != null && columnComponent.getColumns() > 0);
                                columnValue = columnComponent.getValue();
                                
                                try{
                                    if(columnValue == null)
                                        columnValue = PropertyUtil.getValue(currentModel, columnName);
                                    else
                                        columnValue = expressionProcessor.evaluate(columnValue.toString());
                                }
                                catch(InternalErrorException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e){
                                    columnValue = getInvalidPropertyMessage();
                                    columnPropertyInfo = null;
                                }
                                
                                if(columnPropertyInfo != null && columnPropertyInfo.getPropertyTypeId() != null && columnPropertyInfo.getPropertyTypeId().length() > 0)
                                    columnClass = PropertyUtil.getValue(currentModel, columnPropertyInfo.getPropertyTypeId());
                                else
                                    columnClass = (columnValue != null ? columnValue.getClass() : null);
                                
                                columnIsNumber = (PropertyUtil.isNumber(columnClass) || (columnComponent.isNumber() != null && columnComponent.isNumber()));
                                columnIsBoolean = (PropertyUtil.isBoolean(columnClass) || (columnComponent.isBoolean() != null && columnComponent.isBoolean()));
                                columnIsDate = (PropertyUtil.isDate(columnClass) || (columnComponent.isDate() != null && columnComponent.isDate()));
                                columnIsTime = (PropertyUtil.isTime(columnClass) || (columnComponent.isTime() != null && columnComponent.isTime()));
                                
                                if(columnComponent.getParent() instanceof GridColumnGroupComponent)
                                    columnGroupComponent = (GridColumnGroupComponent) columnComponent.getParent();
                                else
                                    columnGroupComponent = null;
                                
                                if(columnGroupComponent == null || (columnGroupComponent.aggregate() == null || !columnGroupComponent.aggregate())){
                                    columnStatesComponents = columnComponent.getColumnStatesComponents();
                                    columnStateComponent = null;
                                    expressionResult = true;
                                    found = false;
                                    
                                    if(columnStatesComponents != null && !columnStatesComponents.isEmpty()){
                                        for(int cont2 = 0; cont2 < columnStatesComponents.size(); cont2++){
                                            columnStateComponent = columnStatesComponents.get(cont2);
                                            
                                            try{
                                                expressionResult = expressionProcessor.evaluate(columnStateComponent.getExpression());
                                            }
                                            catch(InternalErrorException e){
                                            }
                                            
                                            if(expressionResult != null && expressionResult){
                                                found = true;
                                                
                                                break;
                                            }
                                        }
                                    }
                                    
                                    if(!found)
                                        columnStateComponent = null;
                                    
                                    if(columnStateComponent != null){
                                        if(columnStateComponent.isEditable() != null)
                                            columnEditable = columnStateComponent.isEditable();
                                        
                                        if((columnStateComponent.getRows() != null && columnStateComponent.getRows() > 0) || (columnStateComponent.getColumns() != null && columnStateComponent.getColumns() > 0))
                                            columnHasMultipleLines = true;
                                        
                                        if(columnStateComponent.getSize() != null && columnStateComponent.getSize() > 0)
                                            columnSize = columnStateComponent.getSize();
                                        
                                        if(columnStateComponent.getMaximumLength() != null && columnStateComponent.getMaximumLength() > 0)
                                            columnMaximumLength = columnStateComponent.getMaximumLength();
                                        
                                        if(columnStateComponent.getMinimumValue() != null)
                                            columnMinimumValue = columnStateComponent.getMinimumValue();
                                        
                                        if(columnStateComponent.getMaximumValue() != null)
                                            columnMaximumValue = columnStateComponent.getMaximumValue();
                                        
                                        if(columnStateComponent.getRows() != null && columnStateComponent.getRows() > 0)
                                            columnRows = columnStateComponent.getRows();
                                        
                                        if(columnStateComponent.getColumns() != null && columnStateComponent.getColumns() > 0)
                                            columnColumns = columnStateComponent.getColumns();
                                        
                                        if(columnStateComponent.getPattern() != null && columnStateComponent.getPattern().length() > 0)
                                            columnPattern = columnStateComponent.getPattern();
                                        
                                        if(columnStateComponent.getOnBlur() != null && columnStateComponent.getOnBlur().length() > 0)
                                            columnOnBlur = columnStateComponent.getOnBlur();
                                        
                                        if(columnStateComponent.getOnFocus() != null && columnStateComponent.getOnFocus().length() > 0)
                                            columnOnFocus = columnStateComponent.getOnFocus();
                                        
                                        if(columnStateComponent.getOnChange() != null && columnStateComponent.getOnChange().length() > 0)
                                            columnOnChange = columnStateComponent.getOnChange();
                                        
                                        if(columnStateComponent.getOnClick() != null && columnStateComponent.getOnClick().length() > 0)
                                            columnOnClick = columnStateComponent.getOnClick();
                                        
                                        if(columnStateComponent.getOnKeyPress() != null && columnStateComponent.getOnKeyPress().length() > 0)
                                            columnOnKeyPress = columnStateComponent.getOnKeyPress();
                                        
                                        if(columnStateComponent.getOnKeyUp() != null && columnStateComponent.getOnKeyUp().length() > 0)
                                            columnOnKeyUp = columnStateComponent.getOnKeyUp();
                                        
                                        if(columnStateComponent.getOnKeyDown() != null && columnStateComponent.getOnKeyDown().length() > 0)
                                            columnOnKeyDown = columnStateComponent.getOnKeyDown();
                                        
                                        if(columnStateComponent.getOnMouseOver() != null && columnStateComponent.getOnMouseOver().length() > 0)
                                            columnOnMouseOver = columnStateComponent.getOnMouseOver();
                                        
                                        if(columnStateComponent.getOnMouseOut() != null && columnStateComponent.getOnMouseOut().length() > 0)
                                            columnOnMouseOut = columnStateComponent.getOnMouseOut();
                                        
                                        if(columnStateComponent.getDataset() != null && columnStateComponent.getDataset().length() > 0)
                                            columnDataset = columnStateComponent.getDataset();
                                        
                                        if(columnStateComponent.getDatasetScope() != null)
                                            columnDatasetScope = columnStateComponent.getDatasetScopeType();
                                        
                                        if(columnStateComponent.getValue() != null){
                                            try{
                                                columnValue = expressionProcessor.evaluate(columnStateComponent.getValue());
                                            }
                                            catch(InternalErrorException e){
                                            }
                                        }
                                    }
                                    
                                    if(columnStateComponent != null){
                                        columnStyleClass = columnStateComponent.getStyleClass();
                                        columnStyle = columnStateComponent.getStyle();
                                    }
                                    
                                    if(columnStyleClass == null || columnStyleClass.length() == 0)
                                        columnStyleClass = UIConstants.DEFAULT_GRID_DETAIL_STYLE_CLASS;
                                    
                                    columnStyleClass = ExpressionProcessorUtil.fillVariablesInString(domain, columnStyleClass, currentLanguage);
                                    columnStyle = ExpressionProcessorUtil.fillVariablesInString(domain, columnStyle, currentLanguage);
                                    
                                    print("<td");
                                    
                                    if(columnStyleClass != null && columnStyleClass.length() > 0){
                                        print(" class=\"");
                                        print(columnStyleClass);
                                        print("\"");
                                    }
                                    
                                    if(columnStyle != null && columnStyle.length() > 0){
                                        print(" style=\"");
                                        print(columnStyle);
                                        print("\"");
                                    }
                                    
                                    if(columnWidth != null && columnWidth.length() > 0){
                                        print(" width=\"");
                                        print(columnWidth);
                                        print("\"");
                                    }
                                    
                                    columnOnBlur = ExpressionProcessorUtil.fillVariablesInString(domain, columnOnBlur, currentLanguage);
                                    columnOnFocus = ExpressionProcessorUtil.fillVariablesInString(domain, columnOnFocus, currentLanguage);
                                    columnOnChange = ExpressionProcessorUtil.fillVariablesInString(domain, columnOnChange, currentLanguage);
                                    columnOnClick = ExpressionProcessorUtil.fillVariablesInString(domain, columnOnClick, currentLanguage);
                                    columnOnKeyPress = ExpressionProcessorUtil.fillVariablesInString(domain, columnOnKeyPress, currentLanguage);
                                    columnOnKeyUp = ExpressionProcessorUtil.fillVariablesInString(domain, columnOnKeyUp, currentLanguage);
                                    columnOnKeyDown = ExpressionProcessorUtil.fillVariablesInString(domain, columnOnKeyDown, currentLanguage);
                                    columnOnMouseOver = ExpressionProcessorUtil.fillVariablesInString(domain, columnOnMouseOver, currentLanguage);
                                    columnOnMouseOut = ExpressionProcessorUtil.fillVariablesInString(domain, columnOnMouseOut, currentLanguage);
                                    columnValueLabel = PropertyUtil.format(columnValue, columnPattern, columnUseAdditionalFormatting, columnPrecision, currentLanguage);
                                    columnValueLabel = StringUtil.replaceAll(columnValueLabel, StringUtil.getLineBreak(), "");
                                    columnTooltip = ExpressionProcessorUtil.fillVariablesInString(domain, columnTooltip, currentLanguage);
                                    columnAlignment = columnComponent.getAlignmentType();
                                    
                                    if(columnAlignment != null){
                                        print(" align=\"");
                                        print(columnAlignment);
                                        print("\"");
                                    }
                                    
                                    println(">");
                                    
                                    if(nameBuffer == null)
                                        nameBuffer = new StringBuilder();
                                    else
                                        nameBuffer.delete(0, nameBuffer.length());
                                    
                                    nameBuffer.append(name);
                                    nameBuffer.append(":");
                                    nameBuffer.append(columnName);
                                    nameBuffer.append(":");
                                    nameBuffer.append(cont1);
                                    
                                    if(columnComponent.showAsImage() == null || !columnComponent.showAsImage()){
                                        if((columnEditable != null && columnEditable) || columnIsBoolean){
                                            if(columnIsDate){
                                                columnPropertyComponent = new CalendarPropertyComponent();
                                                
                                                columnPropertyComponent.setPattern(columnPattern);
                                                columnPropertyComponent.setIsTime(columnIsTime);
                                            }
                                            else if(columnIsBoolean){
                                                columnPropertyComponent = new CheckPropertyComponent();
                                                
                                                columnPropertyComponent.setIsBoolean(true);
                                                columnPropertyComponent.setEnabled((columnEditable != null && columnEditable));
                                            }
                                            else if(columnDataset != null && columnDataset.length() > 0){
                                                columnPropertyComponent = new ListPropertyComponent();
                                                
                                                ((ListPropertyComponent) columnPropertyComponent).setOptionStatesComponents(columnComponent.getOptionStatesComponents());
                                                ((ListPropertyComponent) columnPropertyComponent).setDataset(columnDataset);
                                                ((ListPropertyComponent) columnPropertyComponent).setDatasetScopeType(columnDatasetScope);
                                                columnPropertyComponent.setWidth("100%");
                                            }
                                            else if(columnComponent.showAsLanguage() != null && columnComponent.showAsLanguage()){
                                                columnPropertyComponent = new LanguageSelectorComponent();
                                                ((LanguageSelectorComponent) columnPropertyComponent).setOptionStatesComponents(columnComponent.getOptionStatesComponents());
                                            }
                                            else{
                                                if(!columnHasMultipleLines)
                                                    columnPropertyComponent = new TextPropertyComponent();
                                                else{
                                                    columnPropertyComponent = new TextAreaPropertyComponent();
                                                    
                                                    if(columnRows != null && columnRows > 0)
                                                        ((TextAreaPropertyComponent) columnPropertyComponent).setRows(columnRows);
                                                    
                                                    if(columnColumns != null && columnColumns > 0)
                                                        ((TextAreaPropertyComponent) columnPropertyComponent).setColumns(columnColumns);
                                                }
                                                
                                                columnPropertyComponent.setWidth("100%");
                                            }
                                            
                                            columnPropertyComponent.setPageContext(this.pageContext);
                                            columnPropertyComponent.setOutputStream(getOutputStream());
                                            columnPropertyComponent.setPropertyInfo(columnPropertyInfo);
                                            columnPropertyComponent.setActionFormName(actionFormName);
                                            columnPropertyComponent.setName(nameBuffer.toString());
                                            columnPropertyComponent.setLabel(columnLabel);
                                            columnPropertyComponent.setValue(columnValue);
                                            columnPropertyComponent.setShowLabel(false);
                                            columnPropertyComponent.setOnBlur(columnOnBlur);
                                            columnPropertyComponent.setOnChange(columnOnChange);
                                            columnPropertyComponent.setOnFocus(columnOnFocus);
                                            columnPropertyComponent.setOnKeyPress(columnOnKeyPress);
                                            columnPropertyComponent.setOnKeyUp(columnOnKeyUp);
                                            columnPropertyComponent.setOnKeyDown(columnOnKeyDown);
                                            columnPropertyComponent.setOnMouseOver(columnOnMouseOver);
                                            columnPropertyComponent.setOnMouseOut(columnOnMouseOut);
                                            columnPropertyComponent.setTooltip(columnTooltip);
                                            columnPropertyComponent.setFocus(columnComponent.focus());
                                            columnPropertyComponent.setAlignmentType(columnAlignment);
                                            columnPropertyComponent.setPattern(columnPattern);
                                            columnPropertyComponent.setSize(columnSize);
                                            columnPropertyComponent.setMinimumValue(columnMinimumValue);
                                            columnPropertyComponent.setMaximumValue(columnMaximumValue);
                                            columnPropertyComponent.setMaximumLength(columnMaximumLength);
                                            columnPropertyComponent.setUseAdditionalFormatting(columnUseAdditionalFormatting);
                                            columnPropertyComponent.setPrecision(columnPrecision);
                                            columnPropertyComponent.setIsNumber(columnIsNumber);
                                            
                                            try{
                                                columnPropertyComponent.doStartTag();
                                                columnPropertyComponent.doEndTag();
                                            }
                                            catch(JspException e){
                                                throw new InternalErrorException(e);
                                            }
                                        }
                                        else{
                                            if(columnPropertyInfo == null && columnValue == null)
                                                columnValueLabel = getInvalidPropertyMessage();
                                            
                                            if((columnMaximumLength != null && columnMaximumLength > 0 && columnValueLabel != null && columnValueLabel.length() > 0 && columnValueLabel.length() > columnMaximumLength) || (columnOnClick != null && columnOnClick.length() > 0) || (columnTooltip != null && columnTooltip.length() > 0)){
                                                print("<a");
                                                
                                                if(columnStyle != null && columnStyle.length() > 0){
                                                    print(" style=\"");
                                                    print(columnStyle);
                                                    print("\"");
                                                }
                                                
                                                if(columnOnClick != null && columnOnClick.length() > 0){
                                                    print(" onClick=\"");
                                                    print(columnOnClick);
                                                    print("\"");
                                                }
                                                
                                                if((columnMaximumLength != null && columnMaximumLength > 0 && columnValueLabel != null && columnValueLabel.length() > columnMaximumLength)){
                                                    print(" title=\"");
                                                    print(columnValueLabel);
                                                    println("\"");
                                                }
                                                else{
                                                    print(" title=\"");
                                                    print(columnTooltip);
                                                    println("\"");
                                                }
                                                
                                                print(">");
                                            }
                                            
                                            if(columnValueLabel == null || columnValueLabel.length() == 0)
                                                println("&nbsp;");
                                            else{
                                                if(columnMaximumLength != null && columnMaximumLength > 0 && columnValueLabel.length() > columnMaximumLength){
                                                    print(columnValueLabel.substring(0, columnMaximumLength));
                                                    println("...");
                                                }
                                                else
                                                    println(columnValueLabel);
                                            }
                                            
                                            if((columnMaximumLength != null && columnMaximumLength > 0 && columnValueLabel != null && columnValueLabel.length() > 0 && columnValueLabel.length() > columnMaximumLength) || (columnOnClick != null && columnOnClick.length() > 0) || (columnTooltip != null && columnTooltip.length() > 0))
                                                println("</a>");
                                        }
                                    }
                                    else{
                                        imageComponent = new ImageComponent();
                                        imageComponent.setPageContext(this.pageContext);
                                        imageComponent.setOutputStream(getOutputStream());
                                        imageComponent.setPropertyInfo(columnPropertyInfo);
                                        imageComponent.setActionFormName(actionFormName);
                                        imageComponent.setName(nameBuffer.toString());
                                        imageComponent.setValue(columnValue);
                                        imageComponent.setWidth(columnComponent.getImageWidth());
                                        imageComponent.setHeight(columnComponent.getImageHeight());
                                        imageComponent.setOnClick(columnOnClick);
                                        imageComponent.setOnClickAction(getOnClickAction());
                                        imageComponent.setOnClickForward(getOnClickForward());
                                        imageComponent.setOnClickUpdateViews(getOnClickUpdateViews());
                                        imageComponent.setOnClickValidateModel(getOnClickValidateModel());
                                        imageComponent.setOnClickValidateModelProperties(getOnClickValidateModelProperties());
                                        imageComponent.setOnMouseOver(columnOnMouseOver);
                                        imageComponent.setOnMouseOverAction(getOnMouseOverAction());
                                        imageComponent.setOnMouseOverForward(getOnMouseOverForward());
                                        imageComponent.setOnMouseOverUpdateViews(getOnMouseOverUpdateViews());
                                        imageComponent.setOnMouseOverValidateModel(getOnMouseOverValidateModel());
                                        imageComponent.setOnMouseOverValidateModelProperties(getOnMouseOverValidateModelProperties());
                                        imageComponent.setOnMouseOut(columnOnMouseOut);
                                        imageComponent.setOnMouseOutAction(getOnMouseOutAction());
                                        imageComponent.setOnMouseOutForward(getOnMouseOutForward());
                                        imageComponent.setOnMouseOutUpdateViews(getOnMouseOutUpdateViews());
                                        imageComponent.setOnMouseOutValidateModel(getOnMouseOutValidateModel());
                                        imageComponent.setOnMouseOutValidateModelProperties(getOnMouseOutValidateModelProperties());
                                        imageComponent.setTooltip(columnTooltip);
                                        imageComponent.setShowLabel(false);
                                        
                                        try{
                                            imageComponent.doStartTag();
                                            imageComponent.doEndTag();
                                        }
                                        catch(JspException e){
                                            throw new InternalErrorException(e);
                                        }
                                    }
                                    
                                    println("</td>");
                                }
                            }
                            
                            println("</tr>");
                        }
                    }
                }
            }
        }
        catch(NoSuchMethodException | IllegalAccessException | InvocationTargetException | IllegalArgumentException e){
            throw new InternalErrorException(e);
        }
        finally{
            ExpressionProcessorUtil.setVariable(domain, Constants.ITEM_ATTRIBUTE_ID, null);
        }
    }
    
    /**
     * Renders the header of the component.
     *
     * @throws InternalErrorException Occurs when was not possible to render.
     */
    private void renderHeader() throws InternalErrorException{
        UIController uiController = getUIController();
        String actionFormName = getActionFormName();
        String name = getName();
        
        if(uiController == null || actionFormName == null || actionFormName.length() == 0 || name == null || name.length() == 0)
            return;
        
        print("<table class=\"");
        print(UIConstants.DEFAULT_CONTENT_PANEL_STYLE_CLASS);
        println("\">");
        println("<tr>");
        
        if(this.showSelection != null && this.showSelection){
            println("<td></td>");
            println("</tr>");
            println("<tr>");
        }
        
        if(this.columnsComponents != null && !this.columnsComponents.isEmpty()){
            PropertyInfo propertyInfo = getPropertyInfo();
            GridColumnGroupComponent columnGroupComponent = null;
            String columnGroupLabel = null;
            String columnGroupTooltip = null;
            AlignmentType columnGroupAlignment = null;
            String columnGroupHeaderStyleClass = null;
            String columnGroupHeaderStyle = null;
            Integer columnsInGroup = 0;
            Boolean hasColumnsInGroup = false;
            
            for(GridColumnComponent columnComponent: this.columnsComponents){
                if(columnComponent.getParent() instanceof GridColumnGroupComponent){
                    hasColumnsInGroup = true;
                    
                    break;
                }
            }
            
            if(hasColumnsInGroup){
                if(this.showSelection != null && this.showSelection)
                    println("<td></td>");
                
                for(GridColumnComponent columnComponent: this.columnsComponents){
                    if(columnComponent.getParent() instanceof GridColumnGroupComponent){
                        columnGroupComponent = (GridColumnGroupComponent) columnComponent.getParent();
                        
                        if(columnGroupComponent.aggregate() == null || !columnGroupComponent.aggregate()){
                            columnGroupLabel = columnGroupComponent.getLabel();
                            columnGroupTooltip = columnGroupComponent.getTooltip();
                            columnGroupAlignment = columnGroupComponent.getAlignmentType();
                            columnGroupHeaderStyleClass = columnGroupComponent.getHeaderStyleClass();
                            columnGroupHeaderStyle = columnGroupComponent.getHeaderStyle();
                            
                            if(columnGroupHeaderStyleClass == null || columnGroupHeaderStyleClass.length() == 0)
                                columnGroupHeaderStyleClass = this.headerStyleClass;
                            
                            if(columnGroupHeaderStyleClass == null || columnGroupHeaderStyleClass.length() == 0)
                                columnGroupHeaderStyleClass = UIConstants.DEFAULT_GRID_HEADER_STYLE_CLASS;
                            
                            if(columnGroupHeaderStyle == null || columnGroupHeaderStyle.length() == 0)
                                columnGroupHeaderStyle = this.headerStyle;
                            
                            columnsInGroup++;
                        }
                    }
                    else{
                        if(columnsInGroup > 0){
                            print("<td");
                            
                            if(columnGroupHeaderStyleClass != null && columnGroupHeaderStyleClass.length() > 0){
                                print(" class=\"");
                                print(columnGroupHeaderStyleClass);
                                print("\"");
                            }
                            
                            if(columnGroupHeaderStyle != null && columnGroupHeaderStyle.length() > 0){
                                print(" style=\"");
                                print(columnGroupHeaderStyle);
                                
                                if(!columnGroupHeaderStyle.endsWith(";"))
                                    print(";");
                                
                                print("\"");
                            }
                            
                            print(" colspan=\"");
                            print(columnsInGroup);
                            print("\"");
                            
                            if(columnGroupAlignment != null){
                                print(" align=\"");
                                print(columnGroupAlignment);
                                print("\"");
                            }
                            
                            println(">");
                            
                            if(columnGroupTooltip != null && columnGroupTooltip.length() > 0){
                                print("<a title=\"");
                                print(columnGroupTooltip);
                                print("\">");
                            }
                            
                            if(columnGroupLabel != null && columnGroupLabel.length() > 0)
                                println(columnGroupLabel);
                            
                            if(columnGroupTooltip != null && columnGroupTooltip.length() > 0)
                                println("</a>");
                            
                            println("</td>");
                            
                            columnsInGroup = 0;
                        }
                        
                        println("<td></td>");
                    }
                }
                
                if(columnsInGroup > 0){
                    print("<td");
                    
                    if(columnGroupHeaderStyleClass != null && columnGroupHeaderStyleClass.length() > 0){
                        print(" class=\"");
                        print(columnGroupHeaderStyleClass);
                        print("\"");
                    }
                    
                    if(columnGroupHeaderStyle != null && columnGroupHeaderStyle.length() > 0){
                        print(" style=\"");
                        print(columnGroupHeaderStyle);
                        
                        if(!columnGroupHeaderStyle.endsWith(";"))
                            print(";");
                        
                        print("\"");
                    }
                    
                    print(" colspan=\"");
                    print(columnsInGroup);
                    print("\"");
                    
                    if(columnGroupAlignment != null){
                        print(" align=\"");
                        print(columnGroupAlignment);
                        print("\"");
                    }
                    
                    println(">");
                    
                    if(columnGroupTooltip != null && columnGroupTooltip.length() > 0){
                        print("<a title=\"");
                        print(columnGroupTooltip);
                        print("\">");
                    }
                    
                    if(columnGroupLabel != null && columnGroupLabel.length() > 0)
                        println(columnGroupLabel);
                    
                    if(columnGroupTooltip != null && columnGroupTooltip.length() > 0)
                        println("</a>");
                    
                    println("</td>");
                }
                
                println("</tr>");
                println("<tr>");
            }
            
            String columnHeaderStyleClass = this.headerStyleClass;
            String columnHeaderStyle = this.headerStyle;
            
            if(columnHeaderStyleClass == null || columnHeaderStyleClass.length() == 0)
                columnHeaderStyleClass = UIConstants.DEFAULT_GRID_HEADER_STYLE_CLASS;
            
            if(this.showSelection != null && this.showSelection){
                print("<td class=\"");
                print(columnHeaderStyleClass);
                print("\"");
                
                if(columnHeaderStyle != null && columnHeaderStyle.length() > 0){
                    print(" style=\"");
                    print(columnHeaderStyle);
                    
                    if(!columnHeaderStyle.endsWith(";"))
                        print(";");
                    
                    print("\"");
                }
                
                print(" align=\"");
                print(AlignmentType.CENTER);
                println("\" width=\"1\">");
                
                if(propertyInfo != null && propertyInfo.isCollection() != null && propertyInfo.isCollection()){
                    print("<a href=\"javascript:selectDeselectAllGridRows('");
                    print(name);
                    print("', ");
                    print(getDatasetStartIndex());
                    print(", ");
                    print(getDatasetEndIndex());
                    print(");\">");
                }
                
                if(propertyInfo != null && propertyInfo.isCollection() != null && propertyInfo.isCollection())
                    println("(*)</a>");
                else
                    println("&nbsp;");
                
                println("</td>");
            }
            
            Double aggregatesWidth = 0d;
            String columnWidth = null;
            Double columnsWidth = 100d;
            Integer columnsSize = this.columnsComponents.size();
            SortOrderType sortOrder = uiController.getSortOrder(name);
            String sortOrderProperty = uiController.getSortProperty(name);
            
            for(GridColumnComponent columnComponent: this.columnsComponents){
                columnWidth = columnComponent.getWidth();
                
                if(columnComponent.getParent() instanceof GridColumnGroupComponent)
                    columnGroupComponent = (GridColumnGroupComponent) columnComponent.getParent();
                else
                    columnGroupComponent = null;
                
                if(columnGroupComponent != null && columnGroupComponent.aggregate() != null && columnGroupComponent.aggregate()){
                    columnsSize--;
                    
                    if(columnWidth != null && columnWidth.length() > 0){
                        aggregatesWidth += Double.parseDouble(StringUtil.replaceAll(columnWidth, "%", ""));
                        columnsWidth -= aggregatesWidth;
                    }
                }
                else{
                    if(columnWidth != null && columnWidth.length() > 0){
                        columnsSize--;
                        columnsWidth -= Double.parseDouble(StringUtil.replaceAll(columnWidth, "%", ""));
                    }
                }
            }
            
            String columnName = null;
            String columnLabel = null;
            AlignmentType columnAlignment = null;
            Double columnWidthValue = 0d;
            
            for(GridColumnComponent columnComponent: this.columnsComponents){
                columnName = columnComponent.getName();
                columnLabel = columnComponent.getLabel();
                columnAlignment = columnComponent.getAlignmentType();
                columnWidth = columnComponent.getWidth();
                columnHeaderStyleClass = columnComponent.getHeaderStyleClass();
                columnHeaderStyle = columnComponent.getHeaderStyle();
                
                if(columnHeaderStyleClass == null || columnHeaderStyleClass.length() == 0)
                    columnHeaderStyleClass = this.headerStyleClass;
                
                if(columnHeaderStyleClass == null || columnHeaderStyleClass.length() == 0)
                    columnHeaderStyleClass = UIConstants.DEFAULT_GRID_HEADER_STYLE_CLASS;
                
                if(columnHeaderStyle == null || columnHeaderStyle.length() == 0)
                    columnHeaderStyle = this.headerStyle;
                
                if(columnComponent.getParent() instanceof GridColumnGroupComponent)
                    columnGroupComponent = (GridColumnGroupComponent) columnComponent.getParent();
                else
                    columnGroupComponent = null;
                
                if(columnGroupComponent == null || columnGroupComponent.aggregate() == null || !columnGroupComponent.aggregate()){
                    print("<td class=\"");
                    print(columnHeaderStyleClass);
                    print("\"");
                    
                    if(columnHeaderStyle != null && columnHeaderStyle.length() > 0){
                        print(" style=\"");
                        print(columnHeaderStyle);
                        
                        if(!columnHeaderStyle.endsWith(";"))
                            print(";");
                        
                        print("\"");
                    }
                    
                    print(" width=\"");
                    
                    if(columnWidth != null && columnWidth.length() > 0){
                        columnWidthValue = Double.parseDouble(StringUtil.replaceAll(columnWidth, "%", ""));
                        columnWidthValue = (columnWidthValue + (aggregatesWidth / (columnsSize == 0 ? 1 : columnsSize)));
                    }
                    else
                        columnWidthValue = (columnsWidth / columnsSize);
                    
                    columnWidthValue = Math.floor(columnWidthValue);
                    
                    print(columnWidthValue);
                    
                    if(columnWidth == null || columnWidth.length() == 0 || columnWidth.endsWith("%"))
                        print("%");
                    
                    print("\"");
                    
                    if(columnAlignment != null){
                        print(" align=\"");
                        print(columnAlignment);
                        print("\"");
                    }
                    
                    println(">");
                    
                    print("<table class=\"");
                    print(UIConstants.DEFAULT_CONTENT_PANEL_STYLE_CLASS);
                    println("\">");
                    println("<tr>");
                    print("<td");
                    
                    if(columnAlignment != null){
                        print(" align=\"");
                        print(columnAlignment);
                        print("\"");
                    }
                    
                    println(">");
                    
                    print("<a");
                    
                    if(columnName != null && columnName.length() > 0){
                        print(" href=\"javascript: setObjectValue('");
                        print(name);
                        print(".");
                        print(UIConstants.SORT_PROPERTY_ATTRIBUTE_ID);
                        print("', '");
                        print(columnName);
                        print("'); setObjectValue('");
                        print(name);
                        print(".");
                        print(UIConstants.SORT_ORDER_ATTRIBUTE_ID);
                        print("', '");
                        
                        if(sortOrder == SortOrderType.ASCEND)
                            print(SortOrderType.DESCEND);
                        else
                            print(SortOrderType.ASCEND);
                        
                        print("');");
                        
                        if(this.pagerComponent != null){
                            print(" setObjectValue('");
                            print(name);
                            print(".");
                            print(UIConstants.PAGER_ACTION_ATTRIBUTE_ID);
                            print("', '");
                            print(PagerActionType.REFRESH_PAGE);
                            print("');");
                        }
                        
                        print(" document.");
                        print(actionFormName);
                        print(".");
                        print(ActionFormConstants.ACTION_ATTRIBUTE_ID);
                        print(".value = '");
                        print(ActionType.REFRESH.getMethod());
                        print("'; submitActionForm(document.");
                        print(actionFormName);
                        print(");\"");
                    }
                    
                    print(">");
                    
                    if(columnLabel != null && columnLabel.length() > 0){
                        print("<b>");
                        print(columnLabel);
                        print("</b>");
                    }
                    
                    println("</a>");
                    
                    if(columnName != null && columnName.length() > 0 && sortOrderProperty != null && columnName.equals(sortOrderProperty)){
                        println("</td>");
                        
                        print("<td align=\"");
                        print(AlignmentType.RIGHT);
                        print("\" width=\"1\">");
                        print("<div class=\"");
                        print(sortOrder.getId());
                        println("endOrderIcon\"></div>");
                    }
                    
                    println("</td>");
                    println("</tr>");
                    println("</table>");
                    
                    println("</td>");
                }
            }
        }
        
        println("</tr>");
    }
    
    /**
     * Renders the controls of the component.
     *
     * @throws InternalErrorException Occurs when was not possible to render.
     */
    private void renderControls() throws InternalErrorException{
        println("<table class=\"");
        print(UIConstants.DEFAULT_GRID_CONTROL_STYLE_CLASS);
        println("\">");
        println("<tr>");
        
        renderPager();
        renderButtons();
        
        println("</tr>");
        println("</table>");
    }
    
    /**
     * Renders the pager component.
     *
     * @throws InternalErrorException Occurs when was not possible to render.
     */
    private void renderPager() throws InternalErrorException{
        if(this.pagerComponent != null){
            print("<td class=\"");
            print(UIConstants.DEFAULT_GRID_PAGER_STYLE_CLASS);
            println("\">");
            
            this.pagerComponent.renderOpen();
            this.pagerComponent.renderBody();
            this.pagerComponent.renderClose();
            
            println("</td>");
        }
    }
    
    /**
     * Renders the buttons of the component.
     *
     * @throws InternalErrorException Occurs when was not possible to render.
     */
    private void renderButtons() throws InternalErrorException{
        if(this.buttonsComponents != null && !this.buttonsComponents.isEmpty()){
            Collection<? extends BaseModel> datasetValues = getDatasetValues();
            
            for(ButtonComponent buttonComponent: this.buttonsComponents){
                if(buttonComponent.showOnlyWithDataset() != null && buttonComponent.showOnlyWithDataset()){
                    if(datasetValues != null && !datasetValues.isEmpty()){
                        print("<td class=\"");
                        print(UIConstants.DEFAULT_GRID_BUTTONS_STYLE_CLASS);
                        println("\">");
                        
                        buttonComponent.renderOpen();
                        buttonComponent.renderBody();
                        buttonComponent.renderClose();
                        
                        println("</td>");
                    }
                }
                else{
                    print("<td class=\"");
                    print(UIConstants.DEFAULT_GRID_BUTTONS_STYLE_CLASS);
                    println("\">");
                    
                    buttonComponent.renderOpen();
                    buttonComponent.renderBody();
                    buttonComponent.renderClose();
                    
                    println("</td>");
                }
            }
        }
    }
    
    /**
     * Renders the footer of the component.
     *
     * @throws InternalErrorException Occurs when was not possible to render.
     */
    private void renderFooter() throws InternalErrorException{
        if(!this.scrolling){
            println("<tr>");
            print("<td");
            
            if(this.columnsComponents != null && !this.columnsComponents.isEmpty()){
                Integer columnsSize = this.columnsComponents.size();
                
                if(this.showSelection != null && this.showSelection)
                    columnsSize++;
                
                print(" colspan=\"");
                print(columnsSize);
                print("\"");
            }
            
            print(" align=\"");
            print(AlignmentType.CENTER);
            println("\">");
            
            renderControls();
        }
        
        println("</td>");
        println("</tr>");
        println("</table>");
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BasePropertyComponent#renderClose()
     */
    protected void renderClose() throws InternalErrorException{
        println("</td>");
        println("</tr>");
        println("</table>");
        
        if(this.scrolling){
            println("</div>");
            
            renderControls();
        }
        
        renderLabelAttribute();
        renderPatternAttribute();
        renderDatasetAttributes();
        renderDatasetIndexesAttributes();
        
        PropertyInfo propertyInfo = getPropertyInfo();
        Boolean multipleSelection = hasMultipleSelection();
        
        if(propertyInfo != null && multipleSelection != null && multipleSelection && this.showSelection != null && this.showSelection){
            HiddenPropertyComponent propertyComponent = new HiddenPropertyComponent();
            
            propertyComponent.setPageContext(this.pageContext);
            propertyComponent.setOutputStream(getOutputStream());
            propertyComponent.setActionFormName(getActionFormName());
            propertyComponent.setPropertyInfo(propertyInfo);
            propertyComponent.setId(getId());
            propertyComponent.setName(getName());
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
     * @see br.com.concepting.framework.ui.components.BasePropertyComponent#clearAttributes()
     */
    protected void clearAttributes() throws InternalErrorException{
        super.clearAttributes();
        
        setOnSelect(null);
        setOnSelectAction(null);
        setOnSelectForward(null);
        setOnSelectUpdateViews(null);
        setOnSelectValidateModel(null);
        setOnSelectValidateModelProperties(null);
        setShowSelection(null);
        setScrolling(null);
        setColumnsComponents(null);
        setButtonsComponents(null);
        setRowStatesComponents(null);
        setPagerComponent(null);
    }
}