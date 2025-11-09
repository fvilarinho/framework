package br.com.concepting.framework.ui.components;

import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.controller.action.types.ActionType;
import br.com.concepting.framework.controller.form.constants.ActionFormConstants;
import br.com.concepting.framework.controller.types.ScopeType;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.util.ModelUtil;
import br.com.concepting.framework.processors.ExpressionProcessor;
import br.com.concepting.framework.processors.ExpressionProcessorUtil;
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
import java.io.Serial;
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
 * the Free Software Foundation, either version 3 of the License or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <a href="https://www.gnu.org/licenses"></a>.</pre>
 */
public class GridPropertyComponent extends OptionsPropertyComponent{
    @Serial
    private static final long serialVersionUID = -5946996131314362186L;
    
    private boolean scrolling = false;
    private String scrollingWidth = null;
    private boolean showSelection = true;
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
    public boolean getScrolling(){
        return this.scrolling;
    }
    
    /**
     * Defines if the component should do scrolling.
     *
     * @param scrolling True/False.
     */
    public void setScrolling(boolean scrolling){
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
     * Returns the list of columns.
     *
     * @return List that contains the columns.
     */
    public Collection<GridColumnComponent> getColumnsComponents(){
        return this.columnsComponents;
    }
    
    /**
     * Defines the list of columns.
     *
     * @param columnsComponents List that contains the columns.
     */
    public void setColumnsComponents(List<GridColumnComponent> columnsComponents){
        this.columnsComponents = columnsComponents;
    }
    
    /**
     * Returns the list of buttons.
     *
     * @return List that contains the buttons.
     */
    public Collection<ButtonComponent> getButtonsComponents(){
        return this.buttonsComponents;
    }
    
    /**
     * Defines the list of buttons.
     *
     * @param buttonsComponents List that contains the buttons.
     */
    public void setButtonsComponents(Collection<ButtonComponent> buttonsComponents){
        this.buttonsComponents = buttonsComponents;
    }
    
    /**
     * Returns the list of row states.
     *
     * @return List that contains the row states.
     */
    public List<GridRowStateComponent> getRowStatesComponents(){
        return this.rowStatesComponents;
    }
    
    /**
     * Defines the list of row states.
     *
     * @param rowStatesComponents List that contains the row states.
     */
    public void setRowStatesComponents(List<GridRowStateComponent> rowStatesComponents){
        this.rowStatesComponents = rowStatesComponents;
    }
    
    /**
     * Indicates if the component shows the selection option.
     *
     * @return True/False.
     */
    protected boolean showSelection(){
        return this.showSelection;
    }
    
    /**
     * Indicates if the component shows the selection option.
     *
     * @return True/False.
     */
    protected boolean isShowSelection(){
        return showSelection();
    }
    
    /**
     * Indicates if the component shows the selection option.
     *
     * @return True/False.
     */
    protected boolean getShowSelection(){
        return isShowSelection();
    }
    
    /**
     * Defines if the component shows the selection option.
     *
     * @param showSelection True/False.
     */
    protected void setShowSelection(boolean showSelection){
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

            if(this.columnsComponents != null)
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

            if(this.rowStatesComponents != null)
                this.rowStatesComponents.add(rowStateComponent);
        }
    }

    @Override
    protected void buildName() throws InternalErrorException{
        String name = getName();
        String dataset = getDataset();
        
        if((name == null || name.isEmpty()) && dataset != null && !dataset.isEmpty()){
            name = dataset;
            
            setName(name);
        }
        
        super.buildName();
    }

    @Override
    protected void buildRestrictions() throws InternalErrorException {
        if(getPropertyInfo() == null)
            this.showSelection = false;

        super.buildRestrictions();
    }

    @Override
    protected void initialize() throws InternalErrorException{
        setComponentType(ComponentType.GRID);
        
        super.initialize();
    }

    @Override
    protected void renderDatasetIndexesAttributes() throws InternalErrorException{
        String actionFormName = getActionFormName();
        String name = getName();
        boolean hasInvalidDefinition = hasInvalidDefinition();
        
        if(actionFormName != null && !actionFormName.isEmpty() && name != null && !name.isEmpty() && !hasInvalidDefinition){
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

    @Override
    protected void renderName() throws InternalErrorException{
    }

    @Override
    protected void renderId() throws InternalErrorException{
    }

    @Override
    protected void renderType() throws InternalErrorException{
    }

    @Override
    protected void renderSize() throws InternalErrorException{
    }

    @Override
    protected void renderEnabled() throws InternalErrorException{
    }

    @Override
    protected void renderReadOnly() throws InternalErrorException{
    }

    @Override
    protected void renderEvents() throws InternalErrorException{
    }

    @Override
    protected void renderValue() throws InternalErrorException{
    }

    @Override
    protected void renderOpen() throws InternalErrorException{
        if(this.scrolling){
            print("<div style=\"overflow: auto;");
            
            if(this.scrollingWidth != null && !this.scrollingWidth.isEmpty()){
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

    @Override
    protected void renderBody() throws InternalErrorException{
        boolean hasInvalidDefinition = hasInvalidDefinition();
        
        if(hasInvalidDefinition)
            super.renderInvalidDefinitionMessage();
        else{
            renderHeader();
            renderDetails();
            renderFooter();
        }
    }

    @Override
    public void renderNoDataMessage() throws InternalErrorException{
        String message = getNoDataMessage();
        
        if(message != null && !message.isEmpty()){
            println("<tr>");
            print("<td align=\"");
            print(AlignmentType.CENTER);
            print("\" height=\"50\" class=\"");
            print(UIConstants.DEFAULT_GRID_DETAIL_STYLE_CLASS);
            print("\"");
            
            if(this.columnsComponents != null && !this.columnsComponents.isEmpty()){
                print(" colspan=\"");
                print(this.columnsComponents.size() + (this.showSelection ? 1 : 0));
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
        UIController uiController = getUIController();
        String actionFormName = getActionFormName();
        String name = getName();
        
        if(uiController == null || actionFormName == null || actionFormName.isEmpty() || name == null || name.isEmpty())
            return;
        
        String domain = String.valueOf(System.currentTimeMillis());
        boolean hasData = hasNoData();
        
        try{
            if(hasData)
                renderNoDataMessage();
            else{
                PropertyInfo propertyInfo = getPropertyInfo();
                Locale currentLanguage = getCurrentLanguage();
                String sortPropertyId = getUIController().getSortProperty(name);
                SortOrderType sortOrder = getUIController().getSortOrder(name);
                ExpressionProcessor expressionProcessor = new ExpressionProcessor(domain, currentLanguage);
                Object value = getValue();
                int aggregateColumnCount = 0;
                Object[] aggregatePropertiesValues = null;
                Collection<String> aggregatePropertiesList = null;

                if(this.columnsComponents != null && !this.columnsComponents.isEmpty()){
                    List<? extends BaseModel> datasetValues = getDatasetValues();
                    
                    if(datasetValues != null && !datasetValues.isEmpty()){
                        for(GridColumnComponent item: this.columnsComponents){
                            GridColumnGroupComponent columnGroupComponent;

                            if(item.getParent() instanceof GridColumnGroupComponent)
                                columnGroupComponent = (GridColumnGroupComponent) item.getParent();
                            else
                                columnGroupComponent = null;
                            
                            String columnName = item.getName();
                            
                            if(columnName != null && !columnName.isEmpty() && columnGroupComponent != null && columnGroupComponent.aggregate()){
                                aggregateColumnCount++;
                                
                                if(aggregatePropertiesList == null)
                                    aggregatePropertiesList = PropertyUtil.instantiate(Constants.DEFAULT_LIST_CLASS);
                                
                                aggregatePropertiesList.add(columnName);
                            }
                        }
                        
                        try{
                            if(aggregatePropertiesList != null && !aggregatePropertiesList.isEmpty()){
                                String[] aggregatePropertiesNames = new String[aggregatePropertiesList.size()];

                                aggregatePropertiesNames = aggregatePropertiesList.toArray(aggregatePropertiesNames);
                                aggregatePropertiesValues = new Object[aggregatePropertiesList.size()];

                                datasetValues = ModelUtil.aggregateAndSort(datasetValues, aggregatePropertiesNames, sortPropertyId, sortOrder);
                            }
                            else{
                                if(sortOrder != null && sortPropertyId != null && !sortPropertyId.isEmpty())
                                    ModelUtil.sort(datasetValues, sortPropertyId, sortOrder);
                            }
                        }
                        catch(NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException | ClassCastException | IllegalArgumentException | UnsupportedEncodingException ignored){
                        }
                        
                        setDatasetValues(datasetValues);
                    }
                    
                    int datasetStartIndex = getDatasetStartIndex();
                    int datasetEndIndex = getDatasetEndIndex();
                    
                    if(this.pagerComponent != null){
                        datasetStartIndex = this.pagerComponent.getDatasetStartIndex();
                        datasetEndIndex = this.pagerComponent.getDatasetEndIndex();
                        
                        setDatasetStartIndex(datasetStartIndex);
                        setDatasetEndIndex(datasetEndIndex);
                    }
                    
                    for(int cont1 = datasetStartIndex; cont1 < datasetEndIndex; cont1++){
                        BaseModel currentModel = (cont1 < datasetValues.size() ? datasetValues.get(cont1) : null);
                        
                        if(currentModel != null){
                            if(currentModel.getIndex() == null)
                                currentModel.setIndex(new SecureRandom().nextInt());
                            
                            ExpressionProcessorUtil.setVariable(domain, Constants.ITEM_ATTRIBUTE_ID, currentModel);
                            
                            expressionProcessor.setDeclaration(currentModel);

                            boolean expressionResult = true;
                            boolean rowRemove = false;
                            GridRowStateComponent rowStateComponent = null;

                            if(this.rowStatesComponents != null && !this.rowStatesComponents.isEmpty()){
                                for (GridRowStateComponent item : this.rowStatesComponents) {
                                    rowStateComponent = item;

                                    try {
                                        expressionResult = expressionProcessor.evaluate(rowStateComponent.getExpression());
                                    }
                                    catch (InternalErrorException ignored) {
                                    }

                                    if (expressionResult) {
                                        rowRemove = rowStateComponent.remove();

                                        break;
                                    }

                                    rowStateComponent = null;
                                }
                            }
                            
                            if(rowRemove){
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
                            
                            if(aggregatePropertiesList != null && !aggregatePropertiesList.isEmpty()){
                                int aggregatePropertiesValuesIndex = 0;
                                
                                for(GridColumnComponent columnComponent: this.columnsComponents){
                                    String columnName = columnComponent.getName();
                                    String columnPattern = columnComponent.getPattern();
                                    GridColumnGroupComponent columnGroupComponent;
                                    
                                    if(columnComponent.getParent() instanceof GridColumnGroupComponent)
                                        columnGroupComponent = (GridColumnGroupComponent) columnComponent.getParent();
                                    else
                                        columnGroupComponent = null;
                                    
                                    if(columnGroupComponent != null && columnGroupComponent.aggregate()){
                                        Object aggregatePropertyValue = aggregatePropertiesValues[aggregatePropertiesValuesIndex];
                                        Object columnValue;
                                        
                                        try{
                                            if(columnName != null && !columnName.isEmpty())
                                                columnValue = PropertyUtil.getValue(currentModel, columnName);
                                            else
                                                columnValue = columnComponent.getValue();
                                        }
                                        catch(IllegalAccessException | InvocationTargetException | NoSuchMethodException e){
                                            columnValue = columnComponent.getValue();
                                        }
                                        
                                        boolean columnUseAdditionalFormatting = columnComponent.useAdditionalFormatting();
                                        int columnPrecision = columnComponent.getPrecision();
                                        String columnValueLabel = PropertyUtil.format(columnValue, columnPattern, columnUseAdditionalFormatting, columnPrecision, currentLanguage);
                                        
                                        if((aggregatePropertyValue == null && columnValueLabel != null) || (aggregatePropertyValue != null && columnValueLabel != null && !aggregatePropertyValue.equals(columnValueLabel))){
                                            String aggregateStyleClass = columnGroupComponent.getStyleClass();
                                            String aggregateStyle = columnGroupComponent.getStyle();
                                            
                                            if(aggregateStyleClass == null || aggregateStyleClass.isEmpty())
                                                aggregateStyleClass = UIConstants.DEFAULT_GRID_AGGREGATE_STYLE_CLASS;
                                            
                                            println("<tr>");
                                            print("<td class=\"");
                                            print(aggregateStyleClass);
                                            print("\"");
                                            
                                            if(aggregateStyle != null && !aggregateStyle.isEmpty()){
                                                print(" style=\"");
                                                print(aggregateStyle);
                                                
                                                if(!aggregateStyle.endsWith(";"))
                                                    print(";");
                                                
                                                print("\"");
                                            }
                                            
                                            print(" colspan=\"");
                                            print((this.columnsComponents.size() + (this.showSelection ? 1 : 0)) - aggregateColumnCount);
                                            print("\">");
                                            
                                            if(columnValueLabel != null && !columnValueLabel.isEmpty()){
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
                            
                            if(this.showSelection){
                                String columnStyleClass = null;
                                String columnStyle = null;

                                if(rowStateComponent != null){
                                    columnStyleClass = rowStateComponent.getStyleClass();
                                    columnStyle = rowStateComponent.getStyle();
                                }

                                if(columnStyleClass == null || columnStyleClass.isEmpty())
                                    columnStyleClass = UIConstants.DEFAULT_GRID_DETAIL_STYLE_CLASS;
                                
                                columnStyleClass = ExpressionProcessorUtil.fillVariablesInString(domain, columnStyleClass);
                                columnStyle = ExpressionProcessorUtil.fillVariablesInString(domain, columnStyle);

                                print("<td");
                                
                                if(columnStyleClass != null && !columnStyleClass.isEmpty()){
                                    print(" class=\"");
                                    print(columnStyleClass);
                                    print("\"");
                                }
                                
                                if(columnStyle != null && !columnStyle.isEmpty()){
                                    print(" style=\"");
                                    print(columnStyle);
                                    print("\"");
                                }
                                
                                print(" align=\"");
                                print(AlignmentType.CENTER);
                                println("\" width=\"1\">");
                                
                                Object optionValue = currentModel;

                                if (!propertyInfo.isModel() && !propertyInfo.hasModel()) {
                                    try {
                                        optionValue = PropertyUtil.getValue(optionValue, propertyInfo.getId());
                                    }
                                    catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                                        throw new InternalErrorException(e);
                                    }
                                }

                                String columnOnSelect = ExpressionProcessorUtil.fillVariablesInString(domain, getOnSelect(), currentLanguage);
                                StringBuilder optionComponentId = new StringBuilder();

                                optionComponentId.append(name);
                                optionComponentId.append(cont1);

                                BaseOptionPropertyComponent optionComponent;
                                
                                if(propertyInfo.isCollection())
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
                                PropertyInfo columnPropertyInfo = columnComponent.getPropertyInfo();
                                String columnName = columnComponent.getName();
                                String columnLabel = columnComponent.getLabel();
                                String columnTooltip = columnComponent.getTooltip();
                                String columnWidth = columnComponent.getWidth();
                                int columnSize = columnComponent.getSize();
                                int columnMaximumLength = columnComponent.getMaximumLength();
                                Number columnMinimumValue = columnComponent.getMinimumValue();
                                Number columnMaximumValue = columnComponent.getMaximumValue();
                                int columnRows = columnComponent.getRows();
                                int columnColumns = columnComponent.getColumns();
                                String columnDataset = columnComponent.getDataset();
                                ScopeType columnDatasetScope = columnComponent.getDatasetScopeType();
                                String columnOnBlur = columnComponent.getOnBlur();
                                String columnOnFocus = columnComponent.getOnFocus();
                                String columnOnChange = columnComponent.getOnChange();
                                String columnOnClick = columnComponent.getOnClick();
                                String columnOnKeyPress = columnComponent.getOnKeyPress();
                                String columnOnKeyUp = columnComponent.getOnKeyUp();
                                String columnOnKeyDown = columnComponent.getOnKeyDown();
                                String columnOnMouseOver = columnComponent.getOnMouseOver();
                                String columnOnMouseOut = columnComponent.getOnMouseOut();
                                boolean columnUseAdditionalFormatting = columnComponent.useAdditionalFormatting();
                                int columnPrecision = columnComponent.getPrecision();
                                String columnPattern = columnComponent.getPattern();
                                String columnStyleClass = (rowStateComponent != null ? rowStateComponent.getStyleClass() : columnComponent.getStyleClass());
                                String columnStyle = (rowStateComponent != null ? rowStateComponent.getStyle() : columnComponent.getStyle());
                                boolean columnEditable = columnComponent.isEditable();
                                boolean columnHasMultipleLines = (columnComponent.getRows() > 0 || columnComponent.getColumns() > 0);
                                Object columnValue = columnComponent.getValue();

                                try{
                                    if(columnValue == null)
                                        columnValue = PropertyUtil.getValue(currentModel, columnName);
                                    else
                                        columnValue = expressionProcessor.evaluate(columnValue.toString());
                                }
                                catch(InternalErrorException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e){
                                    columnValue = getInvalidDefinitionMessage();
                                    columnPropertyInfo = null;
                                }

                                String columnValueLabel = PropertyUtil.format(columnValue, columnPattern, columnUseAdditionalFormatting, columnPrecision, currentLanguage);

                                columnValueLabel = StringUtil.replaceAll(columnValueLabel, StringUtil.getLineBreak(), "");

                                Class<?> columnClass;

                                if(columnPropertyInfo != null && columnPropertyInfo.getPropertyTypeId() != null && !columnPropertyInfo.getPropertyTypeId().isEmpty())
                                    columnClass = PropertyUtil.getValue(currentModel, columnPropertyInfo.getPropertyTypeId());
                                else
                                    columnClass = (columnValue != null ? columnValue.getClass() : null);
                                
                                boolean columnIsNumber = (PropertyUtil.isNumber(columnClass) || columnComponent.isNumber());
                                boolean columnIsBoolean = (PropertyUtil.isBoolean(columnClass) || columnComponent.isBoolean());
                                boolean columnIsDate = (PropertyUtil.isDate(columnClass) || columnComponent.isDate());
                                boolean columnIsTime = (PropertyUtil.isTime(columnClass) || columnComponent.isTime());
                                GridColumnGroupComponent columnGroupComponent;
                                
                                if(columnComponent.getParent() instanceof GridColumnGroupComponent)
                                    columnGroupComponent = (GridColumnGroupComponent) columnComponent.getParent();
                                else
                                    columnGroupComponent = null;
                                
                                if(columnGroupComponent == null || !columnGroupComponent.aggregate()){
                                    List<GridColumnStateComponent> columnStatesComponents = columnComponent.getColumnStatesComponents();
                                    GridColumnStateComponent columnStateComponent = null;
                                    expressionResult = true;

                                    if(columnStatesComponents != null && !columnStatesComponents.isEmpty()){
                                        for (GridColumnStateComponent item : columnStatesComponents) {
                                            columnStateComponent = item;

                                            try {
                                                expressionResult = expressionProcessor.evaluate(columnStateComponent.getExpression());
                                            }
                                            catch (Throwable ignored) {
                                            }

                                            if (expressionResult)
                                                break;

                                            columnStateComponent = null;
                                        }
                                    }

                                    if(columnStateComponent != null){
                                        columnEditable = columnStateComponent.isEditable();
                                        
                                        if(columnStateComponent.getRows() > 0 || columnStateComponent.getColumns() > 0)
                                            columnHasMultipleLines = true;
                                        
                                        if(columnStateComponent.getSize() > 0)
                                            columnSize = columnStateComponent.getSize();
                                        
                                        if(columnStateComponent.getMaximumLength() > 0)
                                            columnMaximumLength = columnStateComponent.getMaximumLength();
                                        
                                        if(columnStateComponent.getMinimumValue() != null)
                                            columnMinimumValue = columnStateComponent.getMinimumValue();
                                        
                                        if(columnStateComponent.getMaximumValue() != null)
                                            columnMaximumValue = columnStateComponent.getMaximumValue();
                                        
                                        if(columnStateComponent.getRows() > 0)
                                            columnRows = columnStateComponent.getRows();
                                        
                                        if(columnStateComponent.getColumns() > 0)
                                            columnColumns = columnStateComponent.getColumns();
                                        
                                        if(columnStateComponent.getPattern() != null && !columnStateComponent.getPattern().isEmpty())
                                            columnPattern = columnStateComponent.getPattern();
                                        
                                        if(columnStateComponent.getOnBlur() != null && !columnStateComponent.getOnBlur().isEmpty())
                                            columnOnBlur = columnStateComponent.getOnBlur();
                                        
                                        if(columnStateComponent.getOnFocus() != null && !columnStateComponent.getOnFocus().isEmpty())
                                            columnOnFocus = columnStateComponent.getOnFocus();
                                        
                                        if(columnStateComponent.getOnChange() != null && !columnStateComponent.getOnChange().isEmpty())
                                            columnOnChange = columnStateComponent.getOnChange();
                                        
                                        if(columnStateComponent.getOnClick() != null && !columnStateComponent.getOnClick().isEmpty())
                                            columnOnClick = columnStateComponent.getOnClick();
                                        
                                        if(columnStateComponent.getOnKeyPress() != null && !columnStateComponent.getOnKeyPress().isEmpty())
                                            columnOnKeyPress = columnStateComponent.getOnKeyPress();
                                        
                                        if(columnStateComponent.getOnKeyUp() != null && !columnStateComponent.getOnKeyUp().isEmpty())
                                            columnOnKeyUp = columnStateComponent.getOnKeyUp();
                                        
                                        if(columnStateComponent.getOnKeyDown() != null && !columnStateComponent.getOnKeyDown().isEmpty())
                                            columnOnKeyDown = columnStateComponent.getOnKeyDown();
                                        
                                        if(columnStateComponent.getOnMouseOver() != null && !columnStateComponent.getOnMouseOver().isEmpty())
                                            columnOnMouseOver = columnStateComponent.getOnMouseOver();
                                        
                                        if(columnStateComponent.getOnMouseOut() != null && !columnStateComponent.getOnMouseOut().isEmpty())
                                            columnOnMouseOut = columnStateComponent.getOnMouseOut();
                                        
                                        if(columnStateComponent.getDataset() != null && !columnStateComponent.getDataset().isEmpty())
                                            columnDataset = columnStateComponent.getDataset();
                                        
                                        if(columnStateComponent.getDatasetScope() != null)
                                            columnDatasetScope = columnStateComponent.getDatasetScopeType();
                                        
                                        if(columnStateComponent.getValue() != null){
                                            try{
                                                columnValue = expressionProcessor.evaluate(columnStateComponent.getValue());
                                            }
                                            catch(Throwable ignored){
                                            }
                                        }
                                    }
                                    
                                    if(columnStateComponent != null){
                                        columnStyleClass = columnStateComponent.getStyleClass();
                                        columnStyle = columnStateComponent.getStyle();
                                    }
                                    
                                    if(columnStyleClass == null || columnStyleClass.isEmpty())
                                        columnStyleClass = UIConstants.DEFAULT_GRID_DETAIL_STYLE_CLASS;
                                    
                                    columnStyleClass = ExpressionProcessorUtil.fillVariablesInString(domain, columnStyleClass, currentLanguage);
                                    columnStyle = ExpressionProcessorUtil.fillVariablesInString(domain, columnStyle, currentLanguage);
                                    
                                    print("<td");
                                    
                                    if(columnStyleClass != null && !columnStyleClass.isEmpty()){
                                        print(" class=\"");
                                        print(columnStyleClass);
                                        print("\"");
                                    }
                                    
                                    if(columnStyle != null && !columnStyle.isEmpty()){
                                        print(" style=\"");
                                        print(columnStyle);
                                        print("\"");
                                    }
                                    
                                    if(columnWidth != null && !columnWidth.isEmpty()){
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
                                    columnTooltip = ExpressionProcessorUtil.fillVariablesInString(domain, columnTooltip, currentLanguage);

                                    AlignmentType columnAlignment = columnComponent.getAlignmentType();
                                    
                                    if(columnAlignment != null){
                                        print(" align=\"");
                                        print(columnAlignment);
                                        print("\"");
                                    }
                                    
                                    println(">");

                                    StringBuilder columnPropertyName = new StringBuilder();

                                    columnPropertyName.append(name);
                                    columnPropertyName.append(":");
                                    columnPropertyName.append(columnName);
                                    columnPropertyName.append(":");
                                    columnPropertyName.append(cont1);

                                    BasePropertyComponent columnPropertyComponent;

                                    if(!columnComponent.showAsImage()){
                                        if(columnEditable || columnIsBoolean){
                                            if(columnIsDate){
                                                columnPropertyComponent = new CalendarPropertyComponent();
                                                
                                                columnPropertyComponent.setPattern(columnPattern);
                                                columnPropertyComponent.setIsTime(columnIsTime);
                                            }
                                            else if(columnIsBoolean){
                                                columnPropertyComponent = new CheckPropertyComponent();
                                                
                                                columnPropertyComponent.setIsBoolean(true);
                                                columnPropertyComponent.setEnabled(columnEditable);
                                            }
                                            else if(columnDataset != null && !columnDataset.isEmpty()){
                                                columnPropertyComponent = new ListPropertyComponent();
                                                
                                                ((ListPropertyComponent) columnPropertyComponent).setOptionStatesComponents(columnComponent.getOptionStatesComponents());
                                                ((ListPropertyComponent) columnPropertyComponent).setDataset(columnDataset);
                                                ((ListPropertyComponent) columnPropertyComponent).setDatasetScopeType(columnDatasetScope);
                                                columnPropertyComponent.setWidth("100%");
                                            }
                                            else if(columnComponent.showAsLanguage()){
                                                columnPropertyComponent = new LanguageSelectorComponent();
                                                ((LanguageSelectorComponent) columnPropertyComponent).setOptionStatesComponents(columnComponent.getOptionStatesComponents());
                                            }
                                            else{
                                                if(!columnHasMultipleLines)
                                                    columnPropertyComponent = new TextPropertyComponent();
                                                else{
                                                    columnPropertyComponent = new TextAreaPropertyComponent();
                                                    
                                                    if(columnRows > 0)
                                                        ((TextAreaPropertyComponent) columnPropertyComponent).setRows(columnRows);
                                                    
                                                    if(columnColumns > 0)
                                                        ((TextAreaPropertyComponent) columnPropertyComponent).setColumns(columnColumns);
                                                }
                                                
                                                columnPropertyComponent.setWidth("100%");
                                            }
                                            
                                            columnPropertyComponent.setPageContext(this.pageContext);
                                            columnPropertyComponent.setOutputStream(getOutputStream());
                                            columnPropertyComponent.setPropertyInfo(columnPropertyInfo);
                                            columnPropertyComponent.setActionFormName(actionFormName);
                                            columnPropertyComponent.setName(columnPropertyName.toString());
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
                                                columnValueLabel = getInvalidDefinitionMessage();

                                            boolean renderLink = (columnMaximumLength > 0 && columnValueLabel != null && !columnValueLabel.isEmpty() && columnValueLabel.length() > columnMaximumLength) || (columnOnClick != null && !columnOnClick.isEmpty()) || (columnTooltip != null && !columnTooltip.isEmpty());

                                            if(renderLink){
                                                print("<a");

                                                if(columnStyle != null && !columnStyle.isEmpty()){
                                                    print(" style=\"");
                                                    print(columnStyle);
                                                    print("\"");
                                                }

                                                if(columnOnClick != null && !columnOnClick.isEmpty()){
                                                    print(" onClick=\"");
                                                    print(columnOnClick);
                                                    print("\"");
                                                }

                                                if((columnMaximumLength > 0 && columnValueLabel != null && columnValueLabel.length() > columnMaximumLength)){
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

                                            if(columnValueLabel == null || columnValueLabel.isEmpty())
                                                println("&nbsp;");
                                            else{
                                                if(columnMaximumLength > 0 && columnValueLabel.length() > columnMaximumLength){
                                                    print(columnValueLabel.substring(0, columnMaximumLength));
                                                    println("...");
                                                }
                                                else
                                                    println(columnValueLabel);
                                            }
                                            
                                            if(renderLink)
                                                println("</a>");
                                        }
                                    }
                                    else{
                                        ImageComponent imageComponent = new ImageComponent();

                                        imageComponent.setPageContext(this.pageContext);
                                        imageComponent.setOutputStream(getOutputStream());
                                        imageComponent.setPropertyInfo(columnPropertyInfo);
                                        imageComponent.setActionFormName(actionFormName);
                                        imageComponent.setName(columnPropertyName.toString());
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
            ExpressionProcessorUtil.clearVariables(domain);
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
        
        if(uiController == null || actionFormName == null || actionFormName.isEmpty() || name == null || name.isEmpty())
            return;
        
        print("<table class=\"");
        print(UIConstants.DEFAULT_CONTENT_PANEL_STYLE_CLASS);
        println("\">");
        println("<tr>");
        
        if(this.showSelection){
            println("<td></td>");
            println("</tr>");
            println("<tr>");
        }
        
        if(this.columnsComponents != null && !this.columnsComponents.isEmpty()){
            PropertyInfo propertyInfo = getPropertyInfo();
            String columnGroupLabel = null;
            String columnGroupTooltip = null;
            AlignmentType columnGroupAlignment = null;
            String columnGroupHeaderStyleClass = null;
            String columnGroupHeaderStyle = null;
            int columnsInGroup = 0;
            boolean hasColumnsInGroup = false;
            
            for(GridColumnComponent item: this.columnsComponents){
                if(item.getParent() instanceof GridColumnGroupComponent){
                    hasColumnsInGroup = true;
                    
                    break;
                }
            }
            
            if(hasColumnsInGroup){
                if(this.showSelection)
                    println("<td></td>");
                
                for(GridColumnComponent item: this.columnsComponents){
                    if(item.getParent() instanceof GridColumnGroupComponent columnGroupComponent){

                        if(!columnGroupComponent.aggregate()){
                            columnGroupLabel = columnGroupComponent.getLabel();
                            columnGroupTooltip = columnGroupComponent.getTooltip();
                            columnGroupAlignment = columnGroupComponent.getAlignmentType();
                            columnGroupHeaderStyleClass = columnGroupComponent.getHeaderStyleClass();
                            columnGroupHeaderStyle = columnGroupComponent.getHeaderStyle();
                            
                            if(columnGroupHeaderStyleClass == null || columnGroupHeaderStyleClass.isEmpty())
                                columnGroupHeaderStyleClass = this.headerStyleClass;
                            
                            if(columnGroupHeaderStyleClass == null || columnGroupHeaderStyleClass.isEmpty())
                                columnGroupHeaderStyleClass = UIConstants.DEFAULT_GRID_HEADER_STYLE_CLASS;
                            
                            if(columnGroupHeaderStyle == null || columnGroupHeaderStyle.isEmpty())
                                columnGroupHeaderStyle = this.headerStyle;
                            
                            columnsInGroup++;
                        }
                    }
                    else{
                        if(columnsInGroup > 0){
                            print("<td class=\"");
                            print(columnGroupHeaderStyleClass);
                            print("\"");

                            if(columnGroupHeaderStyle != null && !columnGroupHeaderStyle.isEmpty()){
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
                            
                            if(columnGroupTooltip != null && !columnGroupTooltip.isEmpty()){
                                print("<a title=\"");
                                print(columnGroupTooltip);
                                print("\">");
                            }
                            
                            if(columnGroupLabel != null && !columnGroupLabel.isEmpty())
                                println(columnGroupLabel);
                            
                            if(columnGroupTooltip != null && !columnGroupTooltip.isEmpty())
                                println("</a>");
                            
                            println("</td>");
                            
                            columnsInGroup = 0;
                        }
                        
                        println("<td></td>");
                    }
                }
                
                if(columnsInGroup > 0){
                    print("<td class=\"");
                    print(columnGroupHeaderStyleClass);
                    print("\"");

                    if(columnGroupHeaderStyle != null && !columnGroupHeaderStyle.isEmpty()){
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
                    
                    if(columnGroupTooltip != null && !columnGroupTooltip.isEmpty()){
                        print("<a title=\"");
                        print(columnGroupTooltip);
                        print("\">");
                    }
                    
                    if(columnGroupLabel != null && !columnGroupLabel.isEmpty())
                        println(columnGroupLabel);
                    
                    if(columnGroupTooltip != null && !columnGroupTooltip.isEmpty())
                        println("</a>");
                    
                    println("</td>");
                }
                
                println("</tr>");
                println("<tr>");
            }
            
            String columnHeaderStyleClass = this.headerStyleClass;
            String columnHeaderStyle = this.headerStyle;
            
            if(columnHeaderStyleClass == null || columnHeaderStyleClass.isEmpty())
                columnHeaderStyleClass = UIConstants.DEFAULT_GRID_HEADER_STYLE_CLASS;
            
            if(this.showSelection){
                print("<td class=\"");
                print(columnHeaderStyleClass);
                print("\"");
                
                if(columnHeaderStyle != null && !columnHeaderStyle.isEmpty()){
                    print(" style=\"");
                    print(columnHeaderStyle);
                    
                    if(!columnHeaderStyle.endsWith(";"))
                        print(";");
                    
                    print("\"");
                }
                
                print(" align=\"");
                print(AlignmentType.CENTER);
                println("\" width=\"1\">");
                
                if(propertyInfo != null && propertyInfo.isCollection()){
                    print("<a href=\"javascript:selectDeselectAllGridRows('");
                    print(name);
                    print("', ");
                    print(getDatasetStartIndex());
                    print(", ");
                    print(getDatasetEndIndex());
                    print(");\">");
                }
                
                if(propertyInfo != null && propertyInfo.isCollection())
                    println("(*)</a>");
                else
                    println("&nbsp;");
                
                println("</td>");
            }
            
            double aggregatesWidth = 0D;
            double columnsWidth = 100D;
            int columnsSize = this.columnsComponents.size();
            SortOrderType sortOrder = uiController.getSortOrder(name);
            String sortOrderProperty = uiController.getSortProperty(name);
            
            for(GridColumnComponent item: this.columnsComponents){
                String columnWidth = item.getWidth();
                GridColumnGroupComponent columnGroupComponent;

                if(item.getParent() instanceof GridColumnGroupComponent)
                    columnGroupComponent = (GridColumnGroupComponent) item.getParent();
                else
                    columnGroupComponent = null;
                
                if(columnGroupComponent != null && columnGroupComponent.aggregate()){
                    columnsSize--;
                    
                    if(columnWidth != null && !columnWidth.isEmpty()){
                        aggregatesWidth += Double.parseDouble(StringUtil.replaceAll(columnWidth, "%", ""));
                        columnsWidth -= aggregatesWidth;
                    }
                }
                else{
                    if(columnWidth != null && !columnWidth.isEmpty()){
                        columnsSize--;
                        columnsWidth -= Double.parseDouble(StringUtil.replaceAll(columnWidth, "%", ""));
                    }
                }
            }

            for(GridColumnComponent item: this.columnsComponents){
                String columnName = item.getName();
                String columnLabel = item.getLabel();
                AlignmentType columnAlignment = item.getAlignmentType();
                String columnWidth = item.getWidth();

                columnHeaderStyleClass = item.getHeaderStyleClass();
                columnHeaderStyle = item.getHeaderStyle();
                
                if(columnHeaderStyleClass == null || columnHeaderStyleClass.isEmpty())
                    columnHeaderStyleClass = this.headerStyleClass;
                
                if(columnHeaderStyleClass == null || columnHeaderStyleClass.isEmpty())
                    columnHeaderStyleClass = UIConstants.DEFAULT_GRID_HEADER_STYLE_CLASS;
                
                if(columnHeaderStyle == null || columnHeaderStyle.isEmpty())
                    columnHeaderStyle = this.headerStyle;

                GridColumnGroupComponent columnGroupComponent;
                
                if(item.getParent() instanceof GridColumnGroupComponent)
                    columnGroupComponent = (GridColumnGroupComponent) item.getParent();
                else
                    columnGroupComponent = null;
                
                if(columnGroupComponent == null || !columnGroupComponent.aggregate()){
                    print("<td class=\"");
                    print(columnHeaderStyleClass);
                    print("\"");
                    
                    if(columnHeaderStyle != null && !columnHeaderStyle.isEmpty()){
                        print(" style=\"");
                        print(columnHeaderStyle);
                        
                        if(!columnHeaderStyle.endsWith(";"))
                            print(";");
                        
                        print("\"");
                    }
                    
                    print(" width=\"");

                    double columnWidthValue;

                    if(columnWidth != null && !columnWidth.isEmpty()){
                        columnWidthValue = Double.parseDouble(StringUtil.replaceAll(columnWidth, "%", ""));
                        columnWidthValue = (columnWidthValue + (aggregatesWidth / (columnsSize == 0 ? 1 : columnsSize)));
                    }
                    else
                        columnWidthValue = (columnsWidth / columnsSize);
                    
                    columnWidthValue = Math.floor(columnWidthValue);
                    
                    print(columnWidthValue);
                    
                    if(columnWidth == null || columnWidth.isEmpty() || columnWidth.endsWith("%"))
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
                    
                    if(columnName != null && !columnName.isEmpty()){
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
                    
                    if(columnLabel != null && !columnLabel.isEmpty()){
                        print("<b>");
                        print(columnLabel);
                        print("</b>");
                    }
                    
                    println("</a>");
                    
                    if(columnName != null && !columnName.isEmpty() && columnName.equals(sortOrderProperty)){
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
                if(buttonComponent.showOnlyWithData()){
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
                int columnsSize = this.columnsComponents.size();
                
                if(this.showSelection)
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

    @Override
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
        boolean multipleSelection = hasMultipleSelection();
        
        if(propertyInfo != null && multipleSelection && this.showSelection){
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

    @Override
    protected void clearAttributes() throws InternalErrorException{
        super.clearAttributes();
        
        setOnSelect(null);
        setOnSelectAction(null);
        setOnSelectForward(null);
        setOnSelectUpdateViews(null);
        setOnSelectValidateModel(false);
        setOnSelectValidateModelProperties(null);
        setShowSelection(true);
        setScrolling(false);
        setColumnsComponents(null);
        setButtonsComponents(null);
        setRowStatesComponents(null);
        setPagerComponent(null);
    }
}