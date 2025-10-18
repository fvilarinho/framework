package br.com.concepting.framework.ui.components;

import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.controller.form.constants.ActionFormMessageConstants;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.resources.PropertiesResources;
import br.com.concepting.framework.util.helpers.PropertyInfo;
import br.com.concepting.framework.util.types.ComponentType;

import javax.servlet.jsp.JspException;

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
 * the Free Software Foundation, either version 3 of the License or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <a href="http://www.gnu.org/licenses"></a>.</pre>
 */
public class ListPropertyComponent extends OptionsPropertyComponent{
    private static final long serialVersionUID = 1973970093371979505L;
    
    private boolean showFirstOption = true;
    private String firstOptionResourcesKey = null;
    private String firstOptionLabel = null;
    
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
    public boolean isShowFirstOption(){
        return this.showFirstOption;
    }
    
    /**
     * Indicates if the first option should be shown.
     *
     * @return True/False.
     */
    public boolean getShowFirstOption(){
        return isShowFirstOption();
    }
    
    /**
     * Defines if the first option should be shown.
     *
     * @param showFirstOption True/False.
     */
    public void setShowFirstOption(boolean showFirstOption){
        this.showFirstOption = showFirstOption;
    }

    @Override
    protected BaseOptionPropertyComponent getOptionComponent(){
        return new ListOptionPropertyComponent();
    }

    @Override
    protected void buildRestrictions() throws InternalErrorException{
        super.buildRestrictions();
        
        int size = getSize();

        this.showFirstOption = (size <= 1);

        if(this.showFirstOption){
            boolean hasMultipleSelection = hasMultipleSelection();

            this.showFirstOption = !hasMultipleSelection;
        }
    }

    @Override
    protected void buildResources() throws InternalErrorException{
        super.buildResources();
        
        if(this.showFirstOption && (this.firstOptionLabel == null || this.firstOptionLabel.isEmpty())){
            if(this.firstOptionResourcesKey == null || this.firstOptionResourcesKey.isEmpty())
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

    @Override
    protected void buildSize() throws InternalErrorException{
        int size = getSize();
        
        if(size == 0){
            PropertyInfo propertyInfo = getPropertyInfo();
            
            if(propertyInfo != null){
                size = propertyInfo.getSize();
                
                setSize(size);
            }
        }
    }

    @Override
    protected void buildMaximumLength() throws InternalErrorException{
    }

    @Override
    protected void initialize() throws InternalErrorException{
        setComponentType(ComponentType.LIST);
        
        super.initialize();
    }

    @Override
    protected void renderType() throws InternalErrorException{
    }

    @Override
    protected void renderValue() throws InternalErrorException{
    }

    @Override
    protected void renderAttributes() throws InternalErrorException{
        super.renderAttributes();
        
        boolean multipleSelection = hasMultipleSelection();
        
        if(multipleSelection)
            print(" multiple");
        
        boolean enabled = isEnabled();
        
        if(!enabled){
            print(" ");
            print(Constants.DISABLED_ATTRIBUTE_ID);
        }
    }

    @Override
    protected void renderOpenGroup() throws InternalErrorException{
    }

    @Override
    protected void renderCloseGroup() throws InternalErrorException{
    }

    @Override
    protected void renderOptionsPanelOpen() throws InternalErrorException{
    }

    @Override
    protected void renderOptionsPanelClose() throws InternalErrorException{
    }

    @Override
    protected void renderOptionPanelRowOpen() throws InternalErrorException{
    }

    @Override
    protected void renderOptionPanelRowClose(int row) throws InternalErrorException{
    }

    @Override
    protected void renderBody() throws InternalErrorException{
        boolean hasInvalidDefinition = hasInvalidDefinition();
        
        if(hasInvalidDefinition)
            super.renderInvalidDefinitionMessage();
        else{
            print("<select");
            
            renderAttributes();
            
            println(">");
            
            renderOptions();
            
            println("</select>");
        }
    }

    @Override
    protected void renderOptions() throws InternalErrorException{
        String name = getName();
        
        if(this.showFirstOption && this.firstOptionLabel != null && !this.firstOptionLabel.isEmpty() && name != null && !name.isEmpty()){
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

    @Override
    protected void clearAttributes() throws InternalErrorException{
        super.clearAttributes();
        
        setShowFirstOption(true);
        setFirstOptionResourcesKey(null);
        setFirstOptionLabel(null);
    }
}