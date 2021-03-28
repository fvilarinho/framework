package br.com.concepting.framework.ui.components;

import br.com.concepting.framework.controller.action.types.ActionType;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.ui.components.types.EventType;
import br.com.concepting.framework.ui.constants.UIConstants;
import br.com.concepting.framework.util.PropertyUtil;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.helpers.PropertyInfo;
import br.com.concepting.framework.util.types.ComponentType;

import javax.servlet.jsp.JspException;

/**
 * Class that defines the timer component.
 *
 * @author fvilarinho
 * @since 3.0.0
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
public class TimerComponent extends LabelComponent{
    private static final long serialVersionUID = -3881406429829663145L;
    
    private String onTrigger = null;
    private String onTriggerAction = null;
    private String onTriggerForward = null;
    private String onTriggerUpdateViews = null;
    private Boolean onTriggerValidateModel = null;
    private String onTriggerValidateModelProperties = null;
    
    /**
     * Returns the event of the trigger.
     *
     * @return String that contains the event.
     */
    public String getOnTrigger(){
        return this.onTrigger;
    }
    
    /**
     * Defines the event of the trigger.
     *
     * @param onTrigger String that contains the event.
     */
    public void setOnTrigger(String onTrigger){
        this.onTrigger = onTrigger;
    }
    
    /**
     * Returns the identifier of the action of the trigger event.
     *
     * @return String that contains the identifier of the action.
     */
    public String getOnTriggerAction(){
        return this.onTriggerAction;
    }
    
    /**
     * Defines the identifier of the action of the trigger event.
     *
     * @param onTriggerAction String that contains the identifier of the action.
     */
    public void setOnTriggerAction(String onTriggerAction){
        this.onTriggerAction = onTriggerAction;
    }
    
    /**
     * Defines the action of the trigger event.
     *
     * @param onTriggerActionType Instance that contains the action.
     */
    public void setOnTriggerActionType(ActionType onTriggerActionType){
        if(onTriggerActionType != null)
            this.onTriggerAction = onTriggerActionType.getMethod();
        else
            this.onTriggerAction = null;
    }
    
    /**
     * Returns the identifier of the action forward of the on trigger event.
     *
     * @return String that contains the identifier of the action forward.
     */
    public String getOnTriggerForward(){
        return this.onTriggerForward;
    }
    
    /**
     * Defines the identifier of the action forward of the on trigger event.
     *
     * @param onTriggerForward String that contains the identifier of the action
     * forward.
     */
    public void setOnTriggerForward(String onTriggerForward){
        this.onTriggerForward = onTriggerForward;
    }
    
    /**
     * Indicates if the data model of the form should be validated on the
     * trigger event.
     *
     * @return True/False.
     */
    public Boolean getOnTriggerValidateModel(){
        return this.onTriggerValidateModel;
    }
    
    /**
     * Defines if the data model of the form should be validated on the trigger
     * event.
     *
     * @param onTriggerValidateModel True/False.
     */
    public void setOnTriggerValidateModel(Boolean onTriggerValidateModel){
        this.onTriggerValidateModel = onTriggerValidateModel;
    }
    
    /**
     * Returns the validation properties of the data model that should be
     * validated on the trigger event.
     *
     * @return String that contains the identifiers of the properties.
     */
    public String getOnTriggerValidateModelProperties(){
        return this.onTriggerValidateModelProperties;
    }
    
    /**
     * Defines the validation properties of the data model that should be
     * validated on the trigger event.
     *
     * @param onTriggerValidateModelProperties String that contains the
     * identifiers of the properties.
     */
    public void setOnTriggerValidateModelProperties(String onTriggerValidateModelProperties){
        this.onTriggerValidateModelProperties = onTriggerValidateModelProperties;
    }
    
    /**
     * Returns the identifier of the views that should be updated on the trigger
     * event.
     *
     * @return String that contains the identifier of the views.
     */
    public String getOnTriggerUpdateViews(){
        return this.onTriggerUpdateViews;
    }
    
    /**
     * Defines the identifier of the views that should be updated on the trigger
     * event.
     *
     * @param onTriggerUpdateViews String that contains the identifier of the
     * views.
     */
    public void setOnTriggerUpdateViews(String onTriggerUpdateViews){
        this.onTriggerUpdateViews = onTriggerUpdateViews;
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BasePropertyComponent#buildEvents()
     */
    protected void buildEvents() throws InternalErrorException{
        buildEvent(EventType.ON_TRIGGER);
        
        super.buildEvents();
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BasePropertyComponent#initialize()
     */
    protected void initialize() throws InternalErrorException{
        setComponentType(ComponentType.TIMER);
        
        super.initialize();
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BasePropertyComponent#renderOpen()
     */
    protected void renderOpen() throws InternalErrorException{
        super.renderOpen();
        
        PropertyInfo propertyInfo = getPropertyInfo();
        String actionFormName = getActionFormName();
        Boolean hasInvalidPropertyDefinition = hasInvalidPropertyDefinition();
        
        if(propertyInfo != null && actionFormName != null && actionFormName.length() > 0 && (hasInvalidPropertyDefinition == null || !hasInvalidPropertyDefinition)){
            HiddenPropertyComponent propertyComponent = new HiddenPropertyComponent();
            
            propertyComponent.setPageContext(this.pageContext);
            propertyComponent.setOutputStream(getOutputStream());
            propertyComponent.setActionFormName(getActionFormName());
            propertyComponent.setPropertyInfo(getPropertyInfo());
            propertyComponent.setName(getName());
            propertyComponent.setValue(getValue());
            
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
     * @see br.com.concepting.framework.ui.components.BaseComponent#renderId()
     */
    protected void renderId() throws InternalErrorException{
        String id = getId();
        
        if(id != null && id.length() > 0){
            print(" id=\"");
            print(id);
            print(".");
            print(UIConstants.DEFAULT_TIMER_ID);
            print("\"");
        }
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BaseComponent#renderName()
     */
    protected void renderName() throws InternalErrorException{
        String name = getName();
        
        if(name != null && name.length() > 0){
            print(" name=\"");
            print(name);
            print(".");
            print(UIConstants.DEFAULT_TIMER_ID);
            print("\"");
        }
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BasePropertyComponent#renderClose()
     */
    protected void renderClose() throws InternalErrorException{
        super.renderClose();
        
        String name = getName();
        Boolean hasInvalidPropertyDefinition = hasInvalidPropertyDefinition();
        
        if((hasInvalidPropertyDefinition == null || !hasInvalidPropertyDefinition) && name != null && name.length() > 0){
            Number value = getValue();
            Integer compare = (value != null ? PropertyUtil.compareTo(value, 0) : 0);
            
            if(compare > 0){
                StringBuilder content = new StringBuilder();
                
                content.append("var ");
                content.append(StringUtil.replaceAll(name, ".", "_"));
                content.append(" = null;");
                content.append(StringUtil.getLineBreak());
                content.append(StringUtil.getLineBreak());
                content.append("function ");
                content.append(StringUtil.replaceAll(name, ".", "_"));
                content.append(StringUtil.capitalize(UIConstants.DEFAULT_TIMER_ID));
                content.append("(){");
                content.append(StringUtil.getLineBreak());
                content.append("\tvar timerValue = getObjectValue(\"");
                content.append(name);
                content.append("\");");
                content.append(StringUtil.getLineBreak());
                content.append(StringUtil.getLineBreak());
                content.append("\ttimerValue = (parseInt(timerValue) - 1);");
                content.append(StringUtil.getLineBreak());
                content.append("\tif(timerValue <= 0)");
                content.append(StringUtil.getLineBreak());
                content.append("\t\ttimerValue = 0;");
                content.append(StringUtil.getLineBreak());
                content.append(StringUtil.getLineBreak());
                content.append("\tsetObjectValue(\"");
                content.append(name);
                content.append("\", timerValue);");
                content.append(StringUtil.getLineBreak());
                content.append(StringUtil.getLineBreak());
                content.append("\tvar timerDisplay = getObject(\"");
                content.append(name);
                content.append(".");
                content.append(UIConstants.DEFAULT_TIMER_ID);
                content.append("\");");
                content.append(StringUtil.getLineBreak());
                content.append(StringUtil.getLineBreak());
                content.append("\tif(timerDisplay)");
                content.append(StringUtil.getLineBreak());
                content.append("\t\ttimerDisplay.innerHTML = timerValue;");
                content.append(StringUtil.getLineBreak());
                content.append(StringUtil.getLineBreak());
                content.append("\tif(timerValue <= 0){");
                content.append("\t\t");
                content.append(StringUtil.getLineBreak());
                content.append("\t\t");
                content.append("clearInterval(");
                content.append(StringUtil.replaceAll(name, ".", "_"));
                content.append(");");
                content.append(StringUtil.getLineBreak());
                content.append("\t\t");
                content.append(this.onTrigger);
                content.append(StringUtil.getLineBreak());
                content.append("\t}");
                content.append(StringUtil.getLineBreak());
                content.append("}");
                content.append(StringUtil.getLineBreak());
                content.append(StringUtil.getLineBreak());
                content.append("clearInterval(");
                content.append(StringUtil.replaceAll(name, ".", "_"));
                content.append(");");
                content.append(StringUtil.getLineBreak());
                content.append(StringUtil.replaceAll(name, ".", "_"));
                content.append(" = setInterval(");
                content.append(StringUtil.replaceAll(name, ".", "_"));
                content.append(StringUtil.capitalize(UIConstants.DEFAULT_TIMER_ID));
                content.append(", 1000);");
                content.append(StringUtil.getLineBreak());
                content.append("addTimer(");
                content.append(StringUtil.replaceAll(name, ".", "_"));
                content.append(");");
                content.append(StringUtil.getLineBreak());
                
                ScriptComponent scriptComponent = new ScriptComponent();
                
                scriptComponent.setPageContext(this.pageContext);
                scriptComponent.setOutputStream(getOutputStream());
                scriptComponent.setContent(content.toString());
                
                try{
                    scriptComponent.doStartTag();
                    scriptComponent.doEndTag();
                }
                catch(JspException e){
                    throw new InternalErrorException(e);
                }
            }
        }
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BasePropertyComponent#clearAttributes()
     */
    protected void clearAttributes() throws InternalErrorException{
        super.clearAttributes();
        
        setOnTriggerAction(null);
        setOnTriggerForward(null);
        setOnTriggerUpdateViews(null);
        setOnTriggerValidateModel(null);
        setOnTriggerValidateModelProperties(null);
    }
}