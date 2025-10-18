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
public class ButtonComponent extends BaseActionFormComponent{
    private static final long serialVersionUID = -6412461075636504749L;
    
    private String iconStyleClass = null;
    private String iconStyle = null;
    private String action = null;
    private String forward = null;
    private boolean validateModel = false;
    private String validateModelProperties = null;
    private String updateViews = null;
    private String pagerAction = null;
    private boolean showActionFormMessages = false;
    
    /**
     * Indicates if validation messages should be shown.
     *
     * @return True/False.
     */
    public boolean showActionFormMessages(){
        return this.showActionFormMessages;
    }
    
    /**
     * Defines if validation messages should be shown.
     *
     * @param showActionFormMessages True/False.
     */
    public void setShowActionFormMessages(boolean showActionFormMessages){
        this.showActionFormMessages = showActionFormMessages;
    }
    
    /**
     * Indicates if it has an action.
     *
     * @return True/False.
     */
    protected boolean hasAction(){
        return (this.action != null && !this.action.isEmpty());
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
     * Indicates if the component should be shown only when data is present.
     *
     * @return True/False.
     */
    public boolean showOnlyWithData(){
        return false;
    }
    
    /**
     * Returns the pager action.
     *
     * @return Instance that contains the pager action.
     */
    protected PagerActionType getPagerActionType(){
        if(this.pagerAction != null && !this.pagerAction.isEmpty()){
            try{
                return PagerActionType.valueOf(this.pagerAction.toUpperCase());
            }
            catch(IllegalArgumentException ignored){
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
        if(pagerActionType != null)
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
    public boolean validateModel(){
        return this.validateModel;
    }
    
    /**
     * Indicates if the data model should be validated.
     *
     * @return True/False.
     */
    public boolean getValidateModel(){
        return validateModel();
    }
    
    /**
     * Defines if the data model should be validated.
     *
     * @param validateModel True/False.
     */
    public void setValidateModel(boolean validateModel){
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

    @Override
    protected void buildEvents() throws InternalErrorException{
        GridPropertyComponent gridComponent = (GridPropertyComponent) findAncestorWithClass(this, GridPropertyComponent.class);
        
        if(gridComponent == null){
            try{
                gridComponent = (GridPropertyComponent) getParent();
            }
            catch(ClassCastException ignored){
            }
        }
        
        if(gridComponent != null && gridComponent.getName() != null && !gridComponent.getName().isEmpty()){
            String currentOnClick = getOnClick();
            StringBuilder onClick = null;
            
            if(currentOnClick != null && !currentOnClick.isEmpty()){
                onClick = new StringBuilder();
                onClick.append(currentOnClick);
                
                if(!currentOnClick.endsWith(";"))
                    onClick.append(";");
            }
            
            if(hasAction() && this.pagerAction != null && !this.pagerAction.isEmpty()){
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

    @Override
    protected void buildStyleClass() throws InternalErrorException{
        ComponentType componentType = getComponentType();
        String resourcesKey = getResourcesKey();
        boolean enabled = isEnabled();
        String styleClass = getStyleClass();
        StringBuilder styleClassBuffer = new StringBuilder();
        
        if(styleClass != null && !styleClass.isEmpty())
            styleClassBuffer.append(styleClass);
        else{
            if(componentType != null)
                styleClassBuffer.append(componentType.getId());
            else
                styleClassBuffer.append(resourcesKey);
        }
        
        if(!enabled)
            styleClassBuffer.append(StringUtil.capitalize(Constants.DISABLED_ATTRIBUTE_ID));
        
        setStyleClass(styleClassBuffer.toString());
        
        styleClassBuffer.delete(0, styleClassBuffer.length());
        
        if(styleClass != null && !styleClass.isEmpty())
            styleClassBuffer.append(styleClass);
        else{
            if(componentType != null)
                styleClassBuffer.append(componentType.getId());
            else
                styleClassBuffer.append(resourcesKey);
        }
        
        styleClassBuffer.append(StringUtil.capitalize(UIConstants.DEFAULT_ICON_ID));
        
        if(!enabled)
            styleClassBuffer.append(StringUtil.capitalize(Constants.DISABLED_ATTRIBUTE_ID));
        
        this.iconStyleClass = styleClassBuffer.toString();
        
        String labelStyleClass = getLabelStyleClass();
        
        styleClassBuffer.delete(0, styleClassBuffer.length());
        
        if(labelStyleClass != null && !labelStyleClass.isEmpty())
            styleClassBuffer.append(labelStyleClass);
        else{
            if(componentType != null){
                styleClassBuffer.append(componentType.getId());
                styleClassBuffer.append(StringUtil.capitalize(Constants.LABEL_ATTRIBUTE_ID));
            }
            else
                styleClassBuffer.append(Constants.LABEL_ATTRIBUTE_ID);
        }
        
        if(!enabled)
            styleClassBuffer.append(StringUtil.capitalize(Constants.DISABLED_ATTRIBUTE_ID));
        
        setLabelStyleClass(styleClassBuffer.toString());
        
        super.buildStyleClass();
    }

    @Override
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

    @Override
    protected void buildResources() throws InternalErrorException{
        String resourcesKey = getResourcesKey();
        
        if(resourcesKey == null || resourcesKey.isEmpty()){
            ComponentType componentType = getComponentType();
            
            if(componentType == ComponentType.BUTTON){
                String name = getName();
                
                if(name != null && !name.isEmpty())
                    resourcesKey = name;
            }
            else if(componentType != null)
                resourcesKey = componentType.getId();
            
            setResourcesKey(resourcesKey);
        }
        
        super.buildResources();
    }

    @Override
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
        
        boolean render = render();
        
        if(render){
            GridPropertyComponent gridComponent = (GridPropertyComponent) findAncestorWithClass(this, GridPropertyComponent.class);
            
            if(gridComponent == null){
                try{
                    gridComponent = (GridPropertyComponent) getParent();
                }
                catch(ClassCastException ignored){
                }
            }
            
            if(gridComponent != null){
                try{
                    ButtonComponent buttonComponent = (ButtonComponent) this.clone();
                    
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
        if((this.iconStyleClass != null && !this.iconStyleClass.isEmpty()) || (this.iconStyle != null && !this.iconStyle.isEmpty())){
            print("<td align=\"");
            print(AlignmentType.CENTER);
            println("\" width=\"1\">");
            
            print("<div");
            
            if(this.iconStyleClass != null && !this.iconStyleClass.isEmpty()){
                print(" class=\"");
                print(this.iconStyleClass);
                print("\"");
            }
            
            if(this.iconStyle != null && !this.iconStyle.isEmpty()){
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

    @Override
    protected void renderType() throws InternalErrorException{
    }

    @Override
    protected void renderSize() throws InternalErrorException{
    }

    @Override
    protected void renderLabel() throws InternalErrorException{
        super.renderLabel();
        
        if(getLabelPositionType() == PositionType.TOP){
            println("</tr>");
            println("<tr>");
        }
    }

    @Override
    protected void renderLabelBody() throws InternalErrorException{
        boolean showLabel = showLabel();
        String label = getLabel();
        
        if(showLabel && label != null && !label.isEmpty())
            println(label);
    }

    @Override
    protected void renderOpen() throws InternalErrorException{
        GridPropertyComponent gridComponent = (GridPropertyComponent) findAncestorWithClass(this, GridPropertyComponent.class);
        
        if(gridComponent == null){
            try{
                gridComponent = (GridPropertyComponent) getParent();
            }
            catch(ClassCastException ignored){
            }
        }
        
        if(gridComponent == null){
            print("<button");
            
            renderAttributes();
            
            println(">");
        }
    }

    @Override
    protected void renderBody() throws InternalErrorException{
        GridPropertyComponent gridComponent = (GridPropertyComponent) findAncestorWithClass(this, GridPropertyComponent.class);
        
        if(gridComponent == null){
            try{
                gridComponent = (GridPropertyComponent) getParent();
            }
            catch(ClassCastException ignored){
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

    @Override
    protected void renderClose() throws InternalErrorException{
        GridPropertyComponent gridComponent = (GridPropertyComponent) findAncestorWithClass(this, GridPropertyComponent.class);
        
        if(gridComponent == null){
            try{
                gridComponent = (GridPropertyComponent) getParent();
            }
            catch(ClassCastException ignored){
            }
        }
        
        if(gridComponent == null)
            println("</button>");
    }

    @Override
    protected void clearAttributes() throws InternalErrorException{
        super.clearAttributes();
        
        setIconStyle(null);
        setIconStyleClass(null);
        setAction(null);
        setForward(null);
        setValidateModel(false);
        setValidateModelProperties(null);
        setUpdateViews(null);
        setPagerAction(null);
        setShowActionFormMessages(false);
    }
}