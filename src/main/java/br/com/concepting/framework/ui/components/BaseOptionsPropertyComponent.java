package br.com.concepting.framework.ui.components;

import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.controller.SystemController;
import br.com.concepting.framework.controller.form.constants.ActionFormConstants;
import br.com.concepting.framework.controller.form.constants.ActionFormMessageConstants;
import br.com.concepting.framework.controller.types.ScopeType;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.model.constants.ModelConstants;
import br.com.concepting.framework.resources.PropertiesResources;
import br.com.concepting.framework.ui.constants.UIConstants;
import br.com.concepting.framework.util.PropertyUtil;
import br.com.concepting.framework.util.helpers.PropertyInfo;

import jakarta.servlet.jsp.JspException;

import java.io.Serial;
import java.util.Collection;

/**
 * Class that defines the basic implementation for an options' component.
 *
 * @author fvilarinho
 * @since 1.0.0
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
public abstract class BaseOptionsPropertyComponent extends BasePropertyComponent{
    @Serial
    private static final long serialVersionUID = -5223346768919041691L;
    
    private static String noDataMessage = null;
    
    private String optionLabelStyle = null;
    private String optionLabelStyleClass = null;
    private boolean multipleSelection = false;
    private Collection<OptionStateComponent> optionStatesComponents = null;
    private String dataset = null;
    private String datasetScope = null;
    private Collection<?> datasetValues = null;
    private int datasetStartIndex = 0;
    private int datasetEndIndex = 0;
    private boolean hasNoData = true;
    
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
    protected int getDatasetStartIndex(){
        return this.datasetStartIndex;
    }
    
    /**
     * Defines the start index of the dataset.
     *
     * @param datasetStartIndex Numeric value that contains the start index.
     */
    protected void setDatasetStartIndex(int datasetStartIndex){
        this.datasetStartIndex = datasetStartIndex;
    }
    
    /**
     * Returns the end index of the dataset.
     *
     * @return Numeric value that contains the end index.
     */
    protected int getDatasetEndIndex(){
        return this.datasetEndIndex;
    }
    
    /**
     * Defines the end index of the dataset.
     *
     * @param datasetEndIndex Numeric value that contains the end index.
     */
    protected void setDatasetEndIndex(int datasetEndIndex){
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

            if(this.optionStatesComponents != null)
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
        return (C) this.optionStatesComponents;
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
     * Returns the scope of the dataset storage.
     *
     * @return String that contains the scope of storage.
     */
    public String getDatasetScope(){
        return this.datasetScope;
    }
    
    /**
     * Returns the scope of the dataset storage.
     *
     * @return Instance that contains the scope of storage.
     */
    protected ScopeType getDatasetScopeType(){
        if(this.datasetScope != null && !this.datasetScope.isEmpty()){
            try{
                return ScopeType.valueOf(this.datasetScope.toUpperCase());
            }
            catch(IllegalArgumentException ignored){
            }
        }
        
        return null;
    }
    
    /**
     * Defines the scope of the dataset storage.
     *
     * @param datasetScope String that contains the scope of storage.
     */
    public void setDatasetScope(String datasetScope){
        this.datasetScope = datasetScope;
    }
    
    /**
     * Defines the scope of the dataset storage.
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
        return (C) this.datasetValues;
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
     * Indicates if the component permits multiple selection.
     *
     * @return True/False.
     */
    public boolean hasMultipleSelection(){
        return this.multipleSelection;
    }
    
    /**
     * Indicates if the component permits multiple selection.
     *
     * @return True/False.
     */
    public boolean getMultipleSelection(){
        return hasMultipleSelection();
    }
    
    /**
     * Defines if the component permits multiple selection.
     *
     * @param multipleSelection True/False.
     */
    public void setMultipleSelection(boolean multipleSelection){
        this.multipleSelection = multipleSelection;
    }
    
    /**
     * Returns the message when there is no data to show.
     *
     * @return String that contains the message.
     */
    protected static String getNoDataMessage(){
        return noDataMessage;
    }
    
    /**
     * Indicates if the component has no data to show.
     *
     * @return True/False.
     */
    protected boolean hasNoData(){
        return this.hasNoData;
    }
    
    /**
     * Defines if the component has no data to show.
     *
     * @param hasNoData True/False.
     */
    private void setHasNoData(boolean hasNoData){
        this.hasNoData = hasNoData;
    }

    @Override
    protected void buildResources() throws InternalErrorException{
        if(noDataMessage == null){
            PropertiesResources resources = getDefaultResources();
            
            if(resources != null){
                noDataMessage = resources.getProperty(ActionFormMessageConstants.DEFAULT_NO_DATA_KEY_ID);
                noDataMessage = PropertyUtil.fillPropertiesInString(this, noDataMessage);
                noDataMessage = PropertyUtil.fillResourcesInString(resources, noDataMessage);
            }
        }
        
        super.buildResources();
    }

    @Override
    protected void buildRestrictions() throws InternalErrorException{
        PropertyInfo propertyInfo = getPropertyInfo();
        
        if(propertyInfo != null)
            this.multipleSelection = propertyInfo.isCollection();
        
        super.buildRestrictions();
    }

    @Override
    protected void buildStyleClass() throws InternalErrorException{
        String optionLabelStyleClass = getOptionLabelStyleClass();
        
        if(optionLabelStyleClass == null || optionLabelStyleClass.isEmpty()){
            optionLabelStyleClass = UIConstants.DEFAULT_OPTIONS_LABEL_STYLE_CLASS;
            
            setOptionLabelStyleClass(optionLabelStyleClass);
        }
        
        super.buildStyleClass();
    }

    @Override
    protected void initialize() throws InternalErrorException{
        super.initialize();

        SystemController systemController = getSystemController();
        String actionFormName = getActionFormName();
        boolean render = render();
        
        if(systemController != null && actionFormName != null && !actionFormName.isEmpty() && this.dataset != null && !this.dataset.isEmpty() && (this.datasetValues == null || this.datasetValues.isEmpty()) && render){
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
            
            propertyId.append(this.dataset);
            
            this.datasetValues = systemController.getAttribute(propertyId.toString(), datasetScope);
        }
        
        if(this.datasetValues != null && !this.datasetValues.isEmpty())
            this.datasetEndIndex = this.datasetValues.size();
        
        setHasNoData(this.datasetValues == null || this.datasetValues.isEmpty());
    }
    
    /**
     * Renders the no data message.
     *
     * @throws InternalErrorException Occurs when was not possible to render.
     */
    protected void renderNoDataMessage() throws InternalErrorException{
        String labelStyleClass = getLabelStyleClass();
        
        print("<span");
        
        if(labelStyleClass != null && !labelStyleClass.isEmpty()){
            print(" class=\"");
            print(labelStyleClass);
            print("\"");
        }
        
        String labelStyle = getLabelStyle();
        
        if(labelStyle != null && !labelStyle.isEmpty()){
            print(" style=\"");
            print(labelStyle);
            
            if(!labelStyle.endsWith(";"))
                print(";");
            
            print("\"");
        }
        
        print(">");
        print(getNoDataMessage());
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
        
        if(actionFormName == null || actionFormName.isEmpty() || name == null || name.isEmpty())
            return;
        
        boolean hasInvalidDefinition = hasInvalidDefinition();
        BaseOptionsPropertyComponent optionsPropertyComponent = null;
        
        try{
            optionsPropertyComponent = (BaseOptionsPropertyComponent) getParent();
        }
        catch(ClassCastException ignored){
        }
        
        if(!hasInvalidDefinition && this.dataset != null && !this.dataset.isEmpty() && optionsPropertyComponent == null){
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
        
        if(actionFormName == null || actionFormName.isEmpty() || name == null || name.isEmpty())
            return;
        
        boolean hasInvalidDefinition = hasInvalidDefinition();
        boolean hasNoData = hasNoData();
        BaseOptionsPropertyComponent optionsPropertyComponent = null;
        
        try{
            optionsPropertyComponent = (BaseOptionsPropertyComponent) getParent();
        }
        catch(ClassCastException ignored){
        }
        
        if(!hasInvalidDefinition && !hasNoData && optionsPropertyComponent == null){
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

    @Override
    protected void clearAttributes() throws InternalErrorException{
        super.clearAttributes();
        
        setOptionLabelStyle(null);
        setOptionLabelStyleClass(null);
        setDataset(null);
        setDatasetScope(null);
        setDatasetValues(null);
        setDatasetStartIndex(0);
        setDatasetEndIndex(0);
        setOptionStatesComponents(null);
        setMultipleSelection(false);
        setHasNoData(true);
    }
}