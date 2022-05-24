package br.com.concepting.framework.ui.components;

import br.com.concepting.framework.controller.action.types.ActionType;
import br.com.concepting.framework.controller.form.ActionFormController;
import br.com.concepting.framework.controller.form.BaseActionForm;
import br.com.concepting.framework.controller.form.constants.ActionFormConstants;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.helpers.ModelInfo;
import br.com.concepting.framework.model.util.ModelUtil;
import br.com.concepting.framework.ui.components.types.EventType;
import br.com.concepting.framework.ui.components.types.VisibilityType;
import br.com.concepting.framework.ui.constants.UIConstants;
import br.com.concepting.framework.util.PropertyUtil;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.helpers.PropertyInfo;
import br.com.concepting.framework.util.types.ComponentType;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Locale;

/**
 * Class that defines the suggestion box component.
 *
 * @author fvilarinho
 * @since 3.5.0
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
public class SuggestionBoxComponent extends BaseOptionsPropertyComponent{
    private static final long serialVersionUID = -8455531154293038105L;
    
    private String action = null;
    private String onSelect = null;
    private String onSelectAction = null;
    private String onSelectForward = null;
    private String onSelectUpdateViews = null;
    private boolean onSelectValidateModel = false;
    private String onSelectValidateModelProperties = null;
    private String optionLabelProperty = null;
    private String optionTooltipProperty = null;
    
    /**
     * Returns the identifier of the action to be executed
     * when any text is typed in the component.
     *
     * @return String that contains the identifier of the action.
     */
    public String getAction(){
        return this.action;
    }
    
    /**
     * Defines the identifier of the action to be executed
     * when any text is typed in the component.
     *
     * @param action String that contains the identifier of the action.
     */
    public void setAction(String action){
        this.action = action;
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
     * Returns the identifier of the action forward of the select event.
     *
     * @return String that contains the identifier of the action forward.
     */
    public String getOnSelectForward(){
        return this.onSelectForward;
    }
    
    /**
     * Defines the identifier of the action forward of the select event.
     *
     * @param onSelectForward String that contains the identifier of the action
     * forward.
     */
    public void setOnSelectForward(String onSelectForward){
        this.onSelectForward = onSelectForward;
    }
    
    /**
     * Returns the identifier of the views that should be updated.
     *
     * @return String that contains the identifier of the views.
     */
    public String getOnSelectUpdateViews(){
        return this.onSelectUpdateViews;
    }
    
    /**
     * Defines the identifier of the views that should be updated.
     *
     * @param onSelectUpdateViews String that contains the identifier of the
     * views.
     */
    public void setOnSelectUpdateViews(String onSelectUpdateViews){
        this.onSelectUpdateViews = onSelectUpdateViews;
    }
    
    /**
     * Indicates if the data model should be validated.
     *
     * @return True/False.
     */
    public boolean getOnSelectValidateModel(){
        return this.onSelectValidateModel;
    }
    
    /**
     * Defines if the data model should be validated.
     *
     * @param onSelectValidateModel True/False.
     */
    public void setOnSelectValidateModel(boolean onSelectValidateModel){
        this.onSelectValidateModel = onSelectValidateModel;
    }
    
    /**
     * Returns the validation properties of the data model that should be
     * validated.
     *
     * @return String that contains the identifiers of the properties.
     */
    public String getOnSelectValidateModelProperties(){
        return this.onSelectValidateModelProperties;
    }
    
    /**
     * Defines the validation properties of the data model that should be
     * validated.
     *
     * @param onSelectValidateModelProperties String that contains the
     * identifiers of the properties.
     */
    public void setOnSelectValidateModelProperties(String onSelectValidateModelProperties){
        this.onSelectValidateModelProperties = onSelectValidateModelProperties;
    }
    
    /**
     * Returns the identifier of the option label property of the data model.
     *
     * @return String that contains the identifier.
     */
    public String getOptionLabelProperty(){
        return this.optionLabelProperty;
    }
    
    /**
     * Defines the identifier of the option label property of the data model.
     *
     * @param optionLabelProperty String that contains the identifier.
     */
    public void setOptionLabelProperty(String optionLabelProperty){
        this.optionLabelProperty = optionLabelProperty;
    }
    
    /**
     * Returns the identifier of the option tooltip property of the data model.
     *
     * @return String that contains the identifier.
     */
    public String getOptionTooltipProperty(){
        return this.optionTooltipProperty;
    }
    
    /**
     * Defines the identifier of the option tooltip property of the data model.
     *
     * @param optionTooltipProperty String that contains the identifier.
     */
    public void setOptionTooltipProperty(String optionTooltipProperty){
        this.optionTooltipProperty = optionTooltipProperty;
    }

    @Override
    protected void buildEvents() throws InternalErrorException{
        String actionFormName = getActionFormName();
        String name = getName();
        
        if(actionFormName != null && actionFormName.length() > 0 && name != null && name.length() > 0){
            buildEvent(EventType.ON_SELECT);
            
            super.buildEvents();
            
            if(this.action != null && this.action.length() > 0){
                StringBuilder onKeyPress = new StringBuilder();
                
                onKeyPress.append("function ");
                onKeyPress.append(StringUtil.replaceAll(name, ".", "_"));
                onKeyPress.append(StringUtil.capitalize(UIConstants.DEFAULT_TIMER_ID));
                onKeyPress.append("(){ ");
                onKeyPress.append("document.");
                onKeyPress.append(actionFormName);
                onKeyPress.append(".");
                onKeyPress.append(ActionFormConstants.ACTION_ATTRIBUTE_ID);
                onKeyPress.append(".value = '");
                onKeyPress.append(this.action);
                onKeyPress.append("'; document.");
                onKeyPress.append(actionFormName);
                onKeyPress.append(".");
                onKeyPress.append(UIConstants.UPDATE_VIEWS_ATTRIBUTE_ID);
                onKeyPress.append(".value = '");
                onKeyPress.append(name);
                onKeyPress.append(".");
                onKeyPress.append(UIConstants.DEFAULT_SUGGESTION_BOX_ID);
                onKeyPress.append("'; submitActionForm(document.");
                onKeyPress.append(actionFormName);
                onKeyPress.append("); document.");
                onKeyPress.append(actionFormName);
                onKeyPress.append(".");
                onKeyPress.append(UIConstants.UPDATE_VIEWS_ATTRIBUTE_ID);
                onKeyPress.append(".value = ''; }; clearTimeout(suggestionBoxTimer); suggestionBoxTimer = setTimeout(");
                onKeyPress.append(StringUtil.replaceAll(name, ".", "_"));
                onKeyPress.append(StringUtil.capitalize(UIConstants.DEFAULT_TIMER_ID));
                onKeyPress.append(", 1000);");
                
                setOnKeyPress(onKeyPress.toString());
            }
        }
    }

    @Override
    protected void buildStyleClass() throws InternalErrorException{
        String styleClass = getStyleClass();
        
        if(styleClass == null || styleClass.length() == 0){
            styleClass = UIConstants.DEFAULT_TEXTBOX_STYLE_CLASS;
            
            setStyleClass(styleClass);
        }
        
        super.buildStyleClass();
    }

    @Override
    protected void initialize() throws InternalErrorException{
        setComponentType(ComponentType.SUGGESTION_BOX);
        
        super.initialize();
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void renderBody() throws InternalErrorException{
        super.renderBody();
        
        ActionFormController actionFormController = getActionFormController();
        PropertyInfo propertyInfo = getPropertyInfo();
        String name = getName();
        
        if(actionFormController == null || propertyInfo == null || name == null || name.length() == 0)
            return;
        
        boolean hasInvalidPropertyDefinition = hasInvalidPropertyDefinition();
        
        if(!hasInvalidPropertyDefinition){
            BaseActionForm<? extends BaseModel> actionForm = actionFormController.getActionFormInstance();
            Collection<?> propertyValues = (actionForm != null ? actionFormController.getPropertyDatasetValues(name) : null);
            
            print("<div id=\"");
            print(name);
            print(".");
            print(UIConstants.DEFAULT_SUGGESTION_BOX_ID);
            print("\" class=\"");
            print(UIConstants.DEFAULT_SUGGESTION_BOX_STYLE_CLASS);
            print("\"");
            
            String style = getStyle();
            
            if(style != null && style.length() > 0){
                print(" style=\"");
                print(style);
                
                if(!style.endsWith(";"))
                    print(";");
                
                if(propertyValues != null && propertyValues.size() > 0 && this.action != null && actionForm.getAction() != null && this.action.equals(actionForm.getAction())){
                    print(" visibility: ");
                    print(VisibilityType.VISIBLE);
                    print(";");
                }
                
                print("\"");
            }
            else{
                if(propertyValues != null && propertyValues.size() > 0 && this.action != null && actionForm.getAction() != null && this.action.equals(actionForm.getAction())){
                    print(" style=\"visibility: ");
                    print(VisibilityType.VISIBLE);
                    print(";\"");
                }
            }
            
            println(">");
            
            if(propertyValues != null && !propertyValues.isEmpty()){
                Locale currentLanguage = getCurrentLanguage();
                String objectValue = null;
                String optionValueLabel = null;
                String optionValueTooltip = null;
                Collection<PropertyInfo> propertiesInfo = null;
                int pos = name.lastIndexOf(".");
                
                if(pos >= 0){
                    name = name.substring(0, pos);
                    
                    try{
                        BaseModel model = actionForm.getModel();
                        PropertyInfo modelPropertyInfo = (model != null ? PropertyUtil.getInfo(model.getClass(), name) : null);
                        Class<? extends BaseModel> modelClass = (modelPropertyInfo != null ? (Class<? extends BaseModel>) modelPropertyInfo.getClazz() : null);
                        ModelInfo modelInfo = (modelClass != null ? ModelUtil.getInfo(modelClass) : null);
                        
                        propertiesInfo = (modelInfo != null ? modelInfo.getPropertiesInfo() : null);
                    }
                    catch(IllegalArgumentException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException | ClassNotFoundException | NoSuchFieldException ignored){
                    }
                }
                
                print("<table class=\"");
                print(UIConstants.DEFAULT_CONTENT_PANEL_STYLE_CLASS);
                println("\">");
                
                for(Object propertyValue: propertyValues){
                    println("<tr>");
                    print("<td class=\"");
                    print(UIConstants.DEFAULT_SUGGESTION_BOX_ITEM_STYLE_CLASS);
                    print("\"");
                    
                    if(propertiesInfo != null && !propertiesInfo.isEmpty()){
                        int cont = 0;
                        
                        print(" onClick=\"");
                        
                        for(PropertyInfo item: propertiesInfo){
                            if(item.isCollection())
                                continue;
                            
                            try{
                                objectValue = PropertyUtil.getValue(propertyValue, item.getId());
                                
                                if(objectValue == null)
                                    continue;
                                
                                objectValue = PropertyUtil.format(objectValue, item.getPattern(), item.useAdditionalFormatting(), item.getPrecision(), currentLanguage);
                            }
                            catch(Throwable ignored){
                            }
                            
                            if(objectValue != null && objectValue.length() > 0){
                                if(cont > 0)
                                    print(" ");
                                
                                print("setObjectValue('");
                                print(name);
                                print(".");
                                print(item.getId());
                                print("', '");
                                print(objectValue);
                                print("');");
                                
                                cont++;
                            }
                        }
                        
                        if(cont > 0)
                            print(" ");
                        
                        print("hideSuggestionBox('");
                        print(name);
                        print(".");
                        print(propertyInfo.getId());
                        print("');");
                        
                        if(this.onSelect != null && this.onSelect.length() > 0){
                            print(" ");
                            print(this.onSelect);
                            
                            if(!this.onSelect.endsWith(";"))
                                print(";");
                        }
                        
                        print("\"");
                        
                        try{
                            if(this.optionTooltipProperty != null && this.optionTooltipProperty.length() > 0)
                                optionValueTooltip = PropertyUtil.format(PropertyUtil.getValue(propertyValue, this.optionTooltipProperty), getPattern(), useAdditionalFormatting(), getPrecision(), currentLanguage);
                            else
                                optionValueTooltip = PropertyUtil.format(PropertyUtil.getValue(propertyValue, propertyInfo.getId()), getPattern(), useAdditionalFormatting(), getPrecision(), currentLanguage);
                        }
                        catch(IllegalAccessException | InvocationTargetException | NoSuchMethodException e){
                            try{
                                optionValueTooltip = PropertyUtil.format(PropertyUtil.getValue(propertyValue, propertyInfo.getId()), getPattern(), useAdditionalFormatting(), getPrecision(), currentLanguage);
                            }
                            catch(IllegalAccessException | InvocationTargetException | NoSuchMethodException ignored){
                            }
                        }
                        
                        if(optionValueTooltip != null && optionValueTooltip.length() > 0){
                            print(" title=\"");
                            print(optionValueTooltip);
                            print("\"");
                        }
                        
                        print(">");
                        
                        try{
                            if(this.optionLabelProperty != null && this.optionLabelProperty.length() > 0)
                                optionValueLabel = PropertyUtil.format(PropertyUtil.getValue(propertyValue, this.optionLabelProperty), getPattern(), useAdditionalFormatting(), getPrecision(), currentLanguage);
                            else
                                optionValueLabel = PropertyUtil.format(PropertyUtil.getValue(propertyValue, propertyInfo.getId()), getPattern(), useAdditionalFormatting(), getPrecision(), currentLanguage);
                        }
                        catch(IllegalAccessException | InvocationTargetException | NoSuchMethodException e){
                            try{
                                optionValueLabel = PropertyUtil.format(PropertyUtil.getValue(propertyValue, propertyInfo.getId()), getPattern(), useAdditionalFormatting(), getPrecision(), currentLanguage);
                            }
                            catch(IllegalAccessException | InvocationTargetException | NoSuchMethodException ignored){
                            }
                        }
                        
                        if(optionValueLabel != null && optionValueLabel.length() > 0){
                            print("&nbsp;");
                            print(optionValueLabel);
                        }
                    }
                    else{
                        print(" onClick=\"setObjectValue('");
                        print(name);
                        print("', '");
                        
                        try{
                            objectValue = PropertyUtil.format(PropertyUtil.getValue(propertyValue, propertyInfo.getId()), propertyInfo.getPattern(), propertyInfo.useAdditionalFormatting(), propertyInfo.getPrecision(), currentLanguage);
                        }
                        catch(IllegalAccessException | InvocationTargetException | NoSuchMethodException ignored){
                        }
                        
                        if(objectValue != null && objectValue.length() > 0)
                            print(objectValue);
                        
                        print("'); hideSuggestionBox('");
                        print(propertyInfo.getId());
                        print("');");
                        
                        if(this.onSelect != null && this.onSelect.length() > 0){
                            print(" ");
                            print(this.onSelect);
                            
                            if(!this.onSelect.endsWith(";"))
                                print(";");
                        }
                        
                        print("\"");
                        
                        try{
                            if(this.optionTooltipProperty != null && this.optionTooltipProperty.length() > 0)
                                optionValueTooltip = PropertyUtil.format(PropertyUtil.getValue(propertyValue, this.optionTooltipProperty), getPattern(), useAdditionalFormatting(), getPrecision(), currentLanguage);
                            else
                                optionValueTooltip = PropertyUtil.format(PropertyUtil.getValue(propertyValue, propertyInfo.getId()), getPattern(), useAdditionalFormatting(), getPrecision(), currentLanguage);
                        }
                        catch(IllegalAccessException | InvocationTargetException | NoSuchMethodException e){
                            try{
                                optionValueTooltip = PropertyUtil.format(PropertyUtil.getValue(propertyValue, propertyInfo.getId()), getPattern(), useAdditionalFormatting(), getPrecision(), currentLanguage);
                            }
                            catch(IllegalAccessException | InvocationTargetException | NoSuchMethodException ignored){
                            }
                        }
                        
                        if(optionValueTooltip != null && optionValueTooltip.length() > 0){
                            print(" title=\"");
                            print(optionValueTooltip);
                            print("\"");
                        }
                        
                        print(">");
                        
                        try{
                            if(this.optionLabelProperty != null && this.optionLabelProperty.length() > 0)
                                optionValueLabel = PropertyUtil.format(PropertyUtil.getValue(propertyValue, this.optionLabelProperty), getPattern(), useAdditionalFormatting(), getPrecision(), currentLanguage);
                            else
                                optionValueLabel = PropertyUtil.format(PropertyUtil.getValue(propertyValue, propertyInfo.getId()), getPattern(), useAdditionalFormatting(), getPrecision(), currentLanguage);
                        }
                        catch(IllegalAccessException | InvocationTargetException | NoSuchMethodException e){
                            try{
                                optionValueLabel = PropertyUtil.format(PropertyUtil.getValue(propertyValue, propertyInfo.getId()), getPattern(), useAdditionalFormatting(), getPrecision(), currentLanguage);
                            }
                            catch(IllegalAccessException | InvocationTargetException | NoSuchMethodException ignored){
                            }
                        }
                        
                        if(optionValueLabel != null && optionValueLabel.length() > 0){
                            print("&nbsp;");
                            print(optionValueLabel);
                        }
                    }
                    
                    println("</td>");
                    println("</tr>");
                }
                
                println("</table>");
            }
            
            println("</div>");
        }
    }

    @Override
    protected void renderClose() throws InternalErrorException{
        super.renderClose();
        
        renderDatasetAttributes();
        renderDatasetIndexesAttributes();
    }

    @Override
    protected void clearAttributes() throws InternalErrorException{
        super.clearAttributes();
        
        setAction(null);
        setOnSelect(null);
        setOnSelectAction(null);
        setOnSelectForward(null);
        setOnSelectValidateModel(false);
        setOnSelectValidateModelProperties(null);
        setOptionLabelProperty(null);
        setOptionTooltipProperty(null);
    }
}