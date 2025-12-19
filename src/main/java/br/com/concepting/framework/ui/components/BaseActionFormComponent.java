package br.com.concepting.framework.ui.components;

import br.com.concepting.framework.common.model.MainConsoleModel;
import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.controller.action.types.ActionType;
import br.com.concepting.framework.controller.form.ActionFormController;
import br.com.concepting.framework.controller.form.constants.ActionFormConstants;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.model.FormModel;
import br.com.concepting.framework.model.ObjectModel;
import br.com.concepting.framework.model.SystemModuleModel;
import br.com.concepting.framework.model.constants.ModelConstants;
import br.com.concepting.framework.model.util.ModelUtil;
import br.com.concepting.framework.resources.PropertiesResources;
import br.com.concepting.framework.resources.SystemResources;
import br.com.concepting.framework.security.controller.SecurityController;
import br.com.concepting.framework.security.model.LoginSessionModel;
import br.com.concepting.framework.security.model.UserModel;
import br.com.concepting.framework.ui.components.types.EventType;
import br.com.concepting.framework.ui.constants.UIConstants;
import br.com.concepting.framework.ui.controller.UIController;
import br.com.concepting.framework.util.NumberUtil;
import br.com.concepting.framework.util.PropertyUtil;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.types.AlignmentType;
import br.com.concepting.framework.util.types.ComponentType;
import br.com.concepting.framework.util.types.PositionType;

import jakarta.servlet.jsp.JspException;

import java.io.Serial;
import java.lang.reflect.InvocationTargetException;
import java.security.SecureRandom;

/**
 * Class that defines the basic implementation of a form component.
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
public abstract class BaseActionFormComponent extends BaseComponent{
    @Serial
    private static final long serialVersionUID = 1879613642083701618L;
    
    private int size = 0;
    private int maximumLength = 0;
    private String label = null;
    private String labelStyleClass = null;
    private String labelStyle = null;
    private String labelAlignment = null;
    private String labelVerticalAlignment = null;
    private String labelPosition = null;
    private boolean showLabel = true;
    private String tooltip = null;
    private String alignment = null;
    private String verticalAlignment = null;
    private boolean hasPermission = true;
    private String onBlurAction = null;
    private String onBlurForward = null;
    private String onBlurUpdateViews = null;
    private boolean onBlurValidateModel = false;
    private String onBlurValidateModelProperties = null;
    private String onFocusAction = null;
    private String onFocusForward = null;
    private String onFocusUpdateViews = null;
    private boolean onFocusValidateModel = false;
    private String onFocusValidateModelProperties = null;
    private String onClickAction = null;
    private String onClickForward = null;
    private String onClickUpdateViews = null;
    private boolean onClickValidateModel = false;
    private String onClickValidateModelProperties = null;
    private String onMouseOverAction = null;
    private String onMouseOverForward = null;
    private String onMouseOverUpdateViews = null;
    private boolean onMouseOverValidateModel = false;
    private String onMouseOverValidateModelProperties = null;
    private String onMouseOutAction = null;
    private String onMouseOutForward = null;
    private String onMouseOutUpdateViews = null;
    private boolean onMouseOutValidateModel = false;
    private String onMouseOutValidateModelProperties = null;
    private String actionFormName = null;
    private PropertiesResources mainConsoleResources = null;
    private ActionFormComponent actionFormComponent = null;
    private ActionFormController actionFormController = null;
    
    /**
     * Returns the size of the component.
     *
     * @return Numeric value that contains the size.
     */
    public int getSize(){
        return this.size;
    }
    
    /**
     * Defines the size of the component.
     *
     * @param size Numeric value that contains the size.
     */
    public void setSize(int size){
        this.size = size;
    }
    
    /**
     * Returns the maximum number of characters permitted.
     *
     * @return Numeric value that contains the maximum number of characters.
     */
    public int getMaximumLength(){
        return this.maximumLength;
    }
    
    /**
     * Defines the maximum number of characters permitted.
     *
     * @param maximumLength Numeric value that contains the maximum number of
     * characters.
     */
    public void setMaximumLength(int maximumLength){
        this.maximumLength = maximumLength;
    }
    
    /**
     * Defines the instance that contains the main console resource.
     *
     * @param mainConsoleResources Instance that contains the resource.
     */
    private void setMainConsoleResources(PropertiesResources mainConsoleResources){
        this.mainConsoleResources = mainConsoleResources;
    }
    
    /**
     * Returns the instance that contains the main console resource.
     *
     * @return Instance that contains the resource.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    protected PropertiesResources getMainConsoleResources() throws InternalErrorException{
        if(this.mainConsoleResources == null){
            SystemResources systemResources = getSystemResources();
            
            if(systemResources != null){
                Class<? extends MainConsoleModel> mainConsoleClass = systemResources.getMainConsoleClass();
                
                if(mainConsoleClass != null){
                    String resourcesId = ModelUtil.getResourcesIdByModel(mainConsoleClass);

                    setMainConsoleResources(getResources(resourcesId));
                }
            }
        }
        
        return this.mainConsoleResources;
    }
    
    /**
     * Indicates if the component supports asynchronous events.
     *
     * @return True/False.
     */
    protected boolean asynchronousEvents(){
        return true;
    }
    
    /**
     * Returns the type of vertical alignment.
     *
     * @return String that contains the type of vertical alignment.
     */
    public String getVerticalAlignment(){
        return this.verticalAlignment;
    }
    
    /**
     * Returns a type of vertical alignment.
     *
     * @return Instance that contains the vertical alignment.
     */
    protected AlignmentType getVerticalAlignmentType(){
        if(this.verticalAlignment != null && !this.verticalAlignment.isEmpty()){
            try{
                return AlignmentType.valueOf(this.verticalAlignment.toUpperCase());
            }
            catch(IllegalArgumentException ignored){
            }
        }
        
        return null;
    }
    
    /**
     * Defines the type of vertical alignment.
     *
     * @param verticalAlignment String that contains the type of vertical
     * alignment.
     */
    public void setVerticalAlignment(String verticalAlignment){
        this.verticalAlignment = verticalAlignment;
    }
    
    /**
     * Defines the type of alignment.
     *
     * @param verticalAlignment Instance that contains the type of alignment.
     */
    protected void setVerticalAlignmentType(AlignmentType verticalAlignment){
        if(verticalAlignment != null)
            this.verticalAlignment = verticalAlignment.toString();
        else
            this.verticalAlignment = null;
    }
    
    /**
     * Indicates if the data model of the form should be validated on the blur
     * event.
     *
     * @return True/False.
     */
    public boolean getOnBlurValidateModel(){
        return this.onBlurValidateModel;
    }
    
    /**
     * Defines if the data model of the form should be validated on the blur
     * event.
     *
     * @param onBlurValidateModel True/False.
     */
    public void setOnBlurValidateModel(boolean onBlurValidateModel){
        this.onBlurValidateModel = onBlurValidateModel;
    }
    
    /**
     * Returns the validation properties of the data model that should be
     * validated on the blur event.
     *
     * @return String that contains the identifiers of the properties.
     */
    public String getOnBlurValidateModelProperties(){
        return this.onBlurValidateModelProperties;
    }
    
    /**
     * Defines the validation properties of the data model that should be
     * validated on the blur event.
     *
     * @param onBlurValidateModelProperties String that contains the identifiers
     * of the properties.
     */
    public void setOnBlurValidateModelProperties(String onBlurValidateModelProperties){
        this.onBlurValidateModelProperties = onBlurValidateModelProperties;
    }
    
    /**
     * Indicates if the data model of the form should be validated on the focus
     * event.
     *
     * @return True/False.
     */
    public boolean getOnFocusValidateModel(){
        return this.onFocusValidateModel;
    }
    
    /**
     * Defines if the data model of the form should be validated on the focus
     * event.
     *
     * @param onFocusValidateModel True/False.
     */
    public void setOnFocusValidateModel(boolean onFocusValidateModel){
        this.onFocusValidateModel = onFocusValidateModel;
    }
    
    /**
     * Returns the validation properties of the data model that should be
     * validated on the focus event.
     *
     * @return String that contains the identifiers of the properties.
     */
    public String getOnFocusValidateModelProperties(){
        return this.onFocusValidateModelProperties;
    }
    
    /**
     * Defines the validation properties of the data model that should be
     * validated on the focus event.
     *
     * @param onFocusValidateModelProperties String that contains the
     * identifiers of the properties.
     */
    public void setOnFocusValidateModelProperties(String onFocusValidateModelProperties){
        this.onFocusValidateModelProperties = onFocusValidateModelProperties;
    }
    
    /**
     * Indicates if the data model of the form should be validated on the click
     * event.
     *
     * @return True/False.
     */
    public boolean getOnClickValidateModel(){
        return this.onClickValidateModel;
    }
    
    /**
     * Defines if the data model of the form should be validated on the click
     * event.
     *
     * @param onClickValidateModel True/False.
     */
    public void setOnClickValidateModel(boolean onClickValidateModel){
        this.onClickValidateModel = onClickValidateModel;
    }
    
    /**
     * Returns the validation properties of the data model that should be
     * validated on the click event.
     *
     * @return String that contains the identifiers of the properties.
     */
    public String getOnClickValidateModelProperties(){
        return this.onClickValidateModelProperties;
    }
    
    /**
     * Defines the validation properties of the data model that should be
     * validated on the click event.
     *
     * @param onClickValidateModelProperties String that contains the
     * identifiers of the properties.
     */
    public void setOnClickValidateModelProperties(String onClickValidateModelProperties){
        this.onClickValidateModelProperties = onClickValidateModelProperties;
    }
    
    /**
     * Indicates if the data model of the form should be validated on the mouse
     * over event.
     *
     * @return True/False.
     */
    public boolean getOnMouseOverValidateModel(){
        return this.onMouseOverValidateModel;
    }
    
    /**
     * Defines if the data model of the form should be validated on the mouse
     * over event.
     *
     * @param onMouseOverValidateModel String that contains the identifiers of
     * the properties.
     */
    public void setOnMouseOverValidateModel(boolean onMouseOverValidateModel){
        this.onMouseOverValidateModel = onMouseOverValidateModel;
    }
    
    /**
     * Returns the validation properties of the data model that should be
     * validated on the mouse over event.
     *
     * @return String that contains the identifiers of the properties.
     */
    public String getOnMouseOverValidateModelProperties(){
        return this.onMouseOverValidateModelProperties;
    }
    
    /**
     * Defines the validation properties of the data model that should be
     * validated on the mouse over event.
     *
     * @param onMouseOverValidateModelProperties String that contains the
     * identifiers of the properties.
     */
    public void setOnMouseOverValidateModelProperties(String onMouseOverValidateModelProperties){
        this.onMouseOverValidateModelProperties = onMouseOverValidateModelProperties;
    }
    
    /**
     * Indicates if the data model of the form should be validated on the mouse
     * out event.
     *
     * @return True/False.
     */
    public boolean getOnMouseOutValidateModel(){
        return this.onMouseOutValidateModel;
    }
    
    /**
     * Defines if the data model of the form should be validated on the mouse
     * out event.
     *
     * @param onMouseOutValidateModel True/False.
     */
    public void setOnMouseOutValidateModel(boolean onMouseOutValidateModel){
        this.onMouseOutValidateModel = onMouseOutValidateModel;
    }
    
    /**
     * Returns the validation properties of the data model that should be
     * validated on the mouse out event.
     *
     * @return String that contains the identifiers of the properties.
     */
    public String getOnMouseOutValidateModelProperties(){
        return this.onMouseOutValidateModelProperties;
    }
    
    /**
     * Defines the validation properties of the data model that should be
     * validated on the mouse out event.
     *
     * @param onMouseOutValidateModelProperties String that contains the
     * identifiers of the properties.
     */
    public void setOnMouseOutValidateModelProperties(String onMouseOutValidateModelProperties){
        this.onMouseOutValidateModelProperties = onMouseOutValidateModelProperties;
    }
    
    /**
     * Returns the identifier of the views that should be updated on the blur
     * event.
     *
     * @return String that contains the identifier of the views.
     */
    public String getOnBlurUpdateViews(){
        return this.onBlurUpdateViews;
    }
    
    /**
     * Defines the identifier of the views that should be updated on the blur
     * event.
     *
     * @param onBlurUpdateViews String that contains the identifier of the
     * views.
     */
    public void setOnBlurUpdateViews(String onBlurUpdateViews){
        this.onBlurUpdateViews = onBlurUpdateViews;
    }
    
    /**
     * Returns the identifier of the views that should be updated on the focus
     * event.
     *
     * @return String that contains the identifier of the views.
     */
    public String getOnFocusUpdateViews(){
        return this.onFocusUpdateViews;
    }
    
    /**
     * Defines the identifier of the views that should be updated on the focus
     * event.
     *
     * @param onFocusUpdateViews String that contains the identifier of the
     * views.
     */
    public void setOnFocusUpdateViews(String onFocusUpdateViews){
        this.onFocusUpdateViews = onFocusUpdateViews;
    }
    
    /**
     * Returns the identifier of the views that should be updated on the click
     * event.
     *
     * @return String that contains the identifier of the views.
     */
    public String getOnClickUpdateViews(){
        return this.onClickUpdateViews;
    }
    
    /**
     * Defines the identifier of the views that should be updated on the click
     * event.
     *
     * @param onClickUpdateViews String that contains the identifier of the
     * views.
     */
    public void setOnClickUpdateViews(String onClickUpdateViews){
        this.onClickUpdateViews = onClickUpdateViews;
    }
    
    /**
     * Returns the identifier of the views that should be updated on the mouse
     * over event.
     *
     * @return String that contains the identifier of the views.
     */
    public String getOnMouseOverUpdateViews(){
        return this.onMouseOverUpdateViews;
    }
    
    /**
     * Defines the identifier of the views that should be updated on the mouse
     * over event.
     *
     * @param onMouseOverUpdateViews String that contains the identifier of the
     * views.
     */
    public void setOnMouseOverUpdateViews(String onMouseOverUpdateViews){
        this.onMouseOverUpdateViews = onMouseOverUpdateViews;
    }
    
    /**
     * Returns the identifier of the views that should be updated on the mouse
     * out event.
     *
     * @return String that contains the identifier of the views.
     */
    public String getOnMouseOutUpdateViews(){
        return this.onMouseOutUpdateViews;
    }
    
    /**
     * Defines the identifier of the views that should be updated on the mouse
     * out event.
     *
     * @param onMouseOutUpdateViews String that contains the identifier of the
     * views.
     */
    public void setOnMouseOutUpdateViews(String onMouseOutUpdateViews){
        this.onMouseOutUpdateViews = onMouseOutUpdateViews;
    }
    
    /**
     * Returns the identifier of the action forward of the on blur event.
     *
     * @return String that contains the identifier of the action forward.
     */
    public String getOnBlurForward(){
        return this.onBlurForward;
    }
    
    /**
     * Defines the identifier of the action forward of the on blur event.
     *
     * @param onBlurForward String that contains the identifier of the action
     * forward.
     */
    public void setOnBlurForward(String onBlurForward){
        this.onBlurForward = onBlurForward;
    }
    
    /**
     * Returns the identifier of the action forward of the on focus event.
     *
     * @return String that contains the identifier of the action forward.
     */
    public String getOnFocusForward(){
        return this.onFocusForward;
    }
    
    /**
     * Defines the identifier of the action forward of the on focus event.
     *
     * @param onFocusForward String that contains the identifier of the action
     * forward.
     */
    public void setOnFocusForward(String onFocusForward){
        this.onFocusForward = onFocusForward;
    }
    
    /**
     * Returns the identifier of the click action forward.
     *
     * @return String that contains the identifier of the action forward.
     */
    public String getOnClickForward(){
        return this.onClickForward;
    }
    
    /**
     * Defines the identifier of the click action forward.
     *
     * @param onClickForward String that contains the identifier of the action
     * forward.
     */
    public void setOnClickForward(String onClickForward){
        this.onClickForward = onClickForward;
    }
    
    /**
     * Returns the identifier of the action forward of the on mouse over event.
     *
     * @return String that contains the identifier of the action forward.
     */
    public String getOnMouseOverForward(){
        return this.onMouseOverForward;
    }
    
    /**
     * Defines the identifier of the action forward of the on mouse over event.
     *
     * @param onMouseOverForward String that contains the identifier of the
     * action forward.
     */
    public void setOnMouseOverForward(String onMouseOverForward){
        this.onMouseOverForward = onMouseOverForward;
    }
    
    /**
     * Returns the identifier of the action forward of the on mouse out event.
     *
     * @return String that contains the identifier of the action forward.
     */
    public String getOnMouseOutForward(){
        return this.onMouseOutForward;
    }
    
    /**
     * Defines the identifier of the action forward of the on mouse out event.
     *
     * @param onMouseOutForward String that contains the identifier of the
     * action forward.
     */
    public void setOnMouseOutForward(String onMouseOutForward){
        this.onMouseOutForward = onMouseOutForward;
    }
    
    /**
     * Returns the identifier of the blur action.
     *
     * @return String that contains the identifier of the action.
     */
    public String getOnBlurAction(){
        return this.onBlurAction;
    }
    
    /**
     * Defines the identifier of the blur action.
     *
     * @param onBlurAction String that contains the identifier of the action.
     */
    public void setOnBlurAction(String onBlurAction){
        this.onBlurAction = onBlurAction;
    }
    
    /**
     * Defines the identifier of the blur action.
     *
     * @param onBlurActionType Instance that contains the action.
     */
    protected void setOnBlurActionType(ActionType onBlurActionType){
        if(onBlurActionType != null)
            setOnBlurAction(onBlurActionType.getMethod());
        else
            setOnBlurAction(null);
    }
    
    /**
     * Returns the identifier of the focus action.
     *
     * @return String that contains the identifier of the action.
     */
    public String getOnFocusAction(){
        return this.onFocusAction;
    }
    
    /**
     * Defines the identifier of the focus action.
     *
     * @param onFocusAction String that contains the identifier of the action.
     */
    public void setOnFocusAction(String onFocusAction){
        this.onFocusAction = onFocusAction;
    }
    
    /**
     * Defines the identifier of the focus action.
     *
     * @param onFocusActionType Instance that contains the action.
     */
    protected void setOnFocusActionType(ActionType onFocusActionType){
        if(onFocusActionType != null)
            setOnFocusAction(onFocusActionType.getMethod());
        else
            setOnFocusAction(null);
    }
    
    /**
     * Returns the identifier of the click action.
     *
     * @return String that contains the identifier of the action.
     */
    public String getOnClickAction(){
        return this.onClickAction;
    }
    
    /**
     * Defines the identifier of the click action.
     *
     * @param onClickAction String that contains the identifier of the action.
     */
    public void setOnClickAction(String onClickAction){
        this.onClickAction = onClickAction;
    }
    
    /**
     * Defines the identifier of the click action.
     *
     * @param onClickActionType Instance that contains the action.
     */
    protected void setOnClickActionType(ActionType onClickActionType){
        if(onClickActionType != null)
            setOnClickAction(onClickActionType.getMethod());
        else
            setOnClickAction(null);
    }
    
    /**
     * Returns the identifier of the mouse-over action.
     *
     * @return String that contains the identifier of the action.
     */
    public String getOnMouseOverAction(){
        return this.onMouseOverAction;
    }
    
    /**
     * Defines the identifier of the mouse-over action.
     *
     * @param onMouseOverAction String that contains the identifier of the
     * action.
     */
    public void setOnMouseOverAction(String onMouseOverAction){
        this.onMouseOverAction = onMouseOverAction;
    }
    
    /**
     * Defines the identifier of the mouse-over action.
     *
     * @param onMouseOverActionType Instance that contains the action.
     */
    protected void setOnMouseOverActionType(ActionType onMouseOverActionType){
        if(onMouseOverActionType != null)
            setOnMouseOverAction(onMouseOverActionType.getMethod());
        else
            setOnMouseOverAction(null);
    }
    
    /**
     * Returns the identifier of the mouse-out action.
     *
     * @return String that contains the identifier of the action.
     */
    public String getOnMouseOutAction(){
        return this.onMouseOutAction;
    }
    
    /**
     * Defines the identifier of the mouse-out action.
     *
     * @param onMouseOutAction String that contains the identifier of the
     * action.
     */
    public void setOnMouseOutAction(String onMouseOutAction){
        this.onMouseOutAction = onMouseOutAction;
    }
    
    /**
     * Defines the identifier of the mouse-out action.
     *
     * @param onMouseOutActionType Instance that contains the action.
     */
    protected void setOnMouseOutActionType(ActionType onMouseOutActionType){
        if(onMouseOutActionType != null)
            setOnMouseOutAction(onMouseOutActionType.getMethod());
        else
            setOnMouseOutAction(null);
    }
    
    /**
     * Returns the type of alignment.
     *
     * @return String that contains the type of alignment.
     */
    public String getAlignment(){
        return this.alignment;
    }
    
    /**
     * Returns a type of alignment.
     *
     * @return Instance that contains the alignment.
     */
    protected AlignmentType getAlignmentType(){
        if(this.alignment != null && !this.alignment.isEmpty()){
            try{
                return AlignmentType.valueOf(this.alignment.toUpperCase());
            }
            catch(IllegalArgumentException ignored){
            }
        }
        
        return null;
    }
    
    /**
     * Defines the type of alignment.
     *
     * @param alignment String that contains the type of alignment.
     */
    public void setAlignment(String alignment){
        this.alignment = alignment;
    }
    
    /**
     * Defines the type of alignment.
     *
     * @param alignment Instance that contains the type of alignment.
     */
    protected void setAlignmentType(AlignmentType alignment){
        if(alignment != null)
            this.alignment = alignment.toString();
        else
            this.alignment = null;
    }
    
    /**
     * Returns the type of label alignment.
     *
     * @return String that contains the type of alignment.
     */
    public String getLabelAlignment(){
        return this.labelAlignment;
    }
    
    /**
     * Returns the type of label alignment.
     *
     * @return Instance that contains the type of alignment.
     */
    protected AlignmentType getLabelAlignmentType(){
        if(this.labelAlignment != null && !this.labelAlignment.isEmpty()){
            try{
                return AlignmentType.valueOf(this.labelAlignment.toUpperCase());
            }
            catch(IllegalArgumentException ignored){
            }
        }
        
        return null;
    }
    
    /**
     * Defines the type of label alignment.
     *
     * @param labelAlignment String that contains the type of alignment.
     */
    public void setLabelAlignment(String labelAlignment){
        this.labelAlignment = labelAlignment;
    }
    
    /**
     * Defines the type of label alignment.
     *
     * @param labelAlignment Instance that contains the type of alignment.
     */
    protected void setLabelAlignmentType(AlignmentType labelAlignment){
        if(labelAlignment != null)
            this.labelAlignment = labelAlignment.toString();
        else
            this.labelAlignment = null;
    }
    
    /**
     * Returns the type of label vertical alignment.
     *
     * @return String that contains the type of vertical alignment.
     */
    public String getLabelVerticalAlignment(){
        return this.labelVerticalAlignment;
    }
    
    /**
     * Returns the type of label vertical alignment
     *
     * @return Instance that contains the type of vertical alignment.
     */
    protected AlignmentType getLabelVerticalAlignmentType(){
        if(this.labelVerticalAlignment != null && !this.labelVerticalAlignment.isEmpty()){
            try{
                return AlignmentType.valueOf(this.labelVerticalAlignment.toUpperCase());
            }
            catch(IllegalArgumentException ignored){
            }
        }
        
        return null;
    }
    
    /**
     * Defines the type of label vertical alignment
     *
     * @param labelVerticalAlignment String that contains the type of vertical
     * alignment.
     */
    public void setLabelVerticalAlignment(String labelVerticalAlignment){
        this.labelVerticalAlignment = labelVerticalAlignment;
    }
    
    /**
     * Defines the type of label vertical alignment.
     *
     * @param labelVerticalAlignment Instance that contains the type of vertical
     * alignment.
     */
    protected void setLabelVerticalAlignmentType(AlignmentType labelVerticalAlignment){
        if(labelVerticalAlignment != null)
            setLabelVerticalAlignment(labelVerticalAlignment.toString());
        else
            setLabelVerticalAlignment(null);
    }
    
    /**
     * Returns the identifier of the form.
     *
     * @return String that contains the identifier of the form.
     */
    public String getActionFormName(){
        return this.actionFormName;
    }
    
    /**
     * Defines the identifier of the form.
     *
     * @param actionFormName String that contains the identifier of the form.
     */
    public void setActionFormName(String actionFormName){
        this.actionFormName = actionFormName;
    }
    
    /**
     * Returns the position of the label.
     *
     * @return String that contains the position of the label.
     */
    public String getLabelPosition(){
        return this.labelPosition;
    }
    
    /**
     * Returns the position of the label.
     *
     * @return Instance that contains the position of the label.
     */
    protected PositionType getLabelPositionType(){
        if(this.labelPosition != null && !this.labelPosition.isEmpty()){
            try{
                return PositionType.valueOf(this.labelPosition.toUpperCase());
            }
            catch(IllegalArgumentException ignored){
            }
        }
        
        return null;
    }
    
    /**
     * Defines the position of the label.
     *
     * @param labelPosition String that contains the position of the label.
     */
    public void setLabelPosition(String labelPosition){
        this.labelPosition = labelPosition;
    }
    
    /**
     * Defines the position of the label.
     *
     * @param labelPosition Instance that contains the position of the label.
     */
    protected void setLabelPositionType(PositionType labelPosition){
        if(labelPosition != null)
            this.labelPosition = labelPosition.toString();
        else
            this.labelPosition = null;
    }
    
    /**
     * Returns the tooltip of the component.
     *
     * @return String that contains the tooltip.
     */
    public String getTooltip(){
        return this.tooltip;
    }
    
    /**
     * Defines the tooltip of the component.
     *
     * @param tooltip String that contains the tooltip.
     */
    public void setTooltip(String tooltip){
        this.tooltip = tooltip;
    }
    
    /**
     * Returns the label of the component.
     *
     * @return String that contains the label.
     */
    public String getLabel(){
        return this.label;
    }
    
    /**
     * Defines the label of the component.
     *
     * @param label String that contains the label.
     */
    public void setLabel(String label){
        this.label = label;
    }
    
    /**
     * Indicates if the label of the component should be shown.
     *
     * @return True/False.
     */
    public boolean showLabel(){
        return this.showLabel;
    }
    
    /**
     * Defines if the label of the component should be shown.
     *
     * @param showLabel True/False.
     */
    public void setShowLabel(boolean showLabel){
        this.showLabel = showLabel;
    }
    
    /**
     * Returns the CSS style for the label of the component.
     *
     * @return String that contains the CSS style for the label.
     */
    public String getLabelStyleClass(){
        return this.labelStyleClass;
    }
    
    /**
     * Defines the CSS style for the label of the component.
     *
     * @param labelStyleClass String that contains the CSS style for the label.
     */
    public void setLabelStyleClass(String labelStyleClass){
        this.labelStyleClass = labelStyleClass;
    }
    
    /**
     * Returns the CSS style for the label of the component.
     *
     * @return String that contains the CSS style for the label.
     */
    public String getLabelStyle(){
        return this.labelStyle;
    }
    
    /**
     * Defines the CSS style for the label of the component.
     *
     * @param labelStyle String that contains the CSS style for the label.
     */
    public void setLabelStyle(String labelStyle){
        this.labelStyle = labelStyle;
    }
    
    /**
     * Indicates if the user has permission to manipulate the component.
     *
     * @return True/False.
     */
    protected boolean hasPermission(){
        return this.hasPermission;
    }
    
    /**
     * Defines if the user has permission to manipulate the component.
     *
     * @param hasPermission True/False.
     */
    protected void setHasPermission(boolean hasPermission){
        this.hasPermission = hasPermission;
    }
    
    /**
     * Returns the instance of the action form controller.
     *
     * @return Instance that contains the action form controller.
     * @throws InternalErrorException Occurs when was not possible to execute
     * the operation.
     */
    protected ActionFormController getActionFormController() throws InternalErrorException{
        ActionFormComponent actionFormComponent = getActionFormComponent();
        
        if(actionFormComponent != null)
            this.actionFormController = actionFormComponent.getActionFormController();
        
        return this.actionFormController;
    }
    
    /**
     * Returns the form component instance.
     *
     * @return Instance that contains the form component.
     * @throws InternalErrorException Occurs when was not possible to execute
     * the operation.
     */
    protected ActionFormComponent getActionFormComponent() throws InternalErrorException{
        if(this.actionFormName == null || this.actionFormName.isEmpty()){
            this.actionFormComponent = (ActionFormComponent) findAncestorWithClass(this, ActionFormComponent.class);
            
            if(this.actionFormComponent == null){
                try{
                    this.actionFormComponent = (ActionFormComponent) getParent();
                }
                catch(ClassCastException ignored){
                }
            }
            
            if(this.actionFormComponent != null)
                this.actionFormName = this.actionFormComponent.getName();
        }
        
        if(this.actionFormName != null && !this.actionFormName.isEmpty()){
            UIController uiController = getUIController();
            
            if(uiController != null)
                this.actionFormComponent = uiController.getActionFormComponentInstance(this.actionFormName);
        }
        
        return this.actionFormComponent;
    }

    @Override
    protected void buildName() throws InternalErrorException{
        String name = getName();
        ComponentType componentType = getComponentType();
        
        if((name == null || name.isEmpty()) && componentType != null){
            StringBuilder nameBuffer = new StringBuilder();
            
            nameBuffer.append(componentType.getId());
            nameBuffer.append(new SecureRandom().nextInt(NumberUtil.getMaximumRange(Integer.class)));
            
            setName(nameBuffer.toString());
        }
        
        super.buildName();
    }
    
    /**
     * Builds the label of the component.
     *
     * @throws InternalErrorException Occurs when was not possible to execute
     * the operation.
     */
    protected void buildLabel() throws InternalErrorException{
        if(this.label != null)
            return;
        
        SecurityController securityController = getSecurityController();
        String resourcesKey = getResourcesKey();
        String name = getName();
        
        if(this.actionFormName != null && !this.actionFormName.isEmpty() && name != null && !name.isEmpty() && (resourcesKey == null || resourcesKey.isEmpty())){
            LoginSessionModel loginSession = (securityController != null ? securityController.getLoginSession() : null);
            SystemModuleModel systemModule = (loginSession != null ? loginSession.getSystemModule() : null);
            FormModel form = (systemModule != null ? systemModule.getForm(this.actionFormName) : null);
            ObjectModel object = (form != null ? form.getObject(name) : null);
            
            if(object != null)
                this.label = object.getTitle();
        }
        
        PropertiesResources resources = getResources();
        PropertiesResources mainConsoleResources = getMainConsoleResources();
        PropertiesResources defaultResources = getDefaultResources();
        String resourcesKeyValue = null;
        String labelValue = null;
        StringBuilder propertyId = null;
        
        if(resourcesKey != null && !resourcesKey.isEmpty() && this.label == null){
            propertyId = new StringBuilder();
            propertyId.append(resourcesKey);
            propertyId.append(".");
            propertyId.append(Constants.LABEL_ATTRIBUTE_ID);
            
            resourcesKeyValue = (resources != null ? resources.getProperty(propertyId.toString(), false) : null);
            
            if(resourcesKeyValue == null)
                resourcesKeyValue = (mainConsoleResources != null ? mainConsoleResources.getProperty(propertyId.toString(), false) : null);
            
            if(resourcesKeyValue == null)
                resourcesKeyValue = (defaultResources != null ? defaultResources.getProperty(propertyId.toString()) : null);
        }
        
        if(this.label == null){
            if(propertyId == null)
                propertyId = new StringBuilder();
            else
                propertyId.delete(0, propertyId.length());
            
            propertyId.append(name);
            propertyId.append(".");
            propertyId.append(Constants.LABEL_ATTRIBUTE_ID);
            
            labelValue = (resources != null ? resources.getProperty(propertyId.toString(), false) : null);
            
            if(labelValue == null)
                labelValue = (mainConsoleResources != null ? mainConsoleResources.getProperty(propertyId.toString(), false) : null);
            
            if(labelValue == null)
                labelValue = (defaultResources != null ? defaultResources.getProperty(propertyId.toString()) : null);
        }
        
        if(resourcesKeyValue != null)
            this.label = resourcesKeyValue;
        else if(labelValue != null)
            this.label = labelValue;
    }
    
    /**
     * Builds the tooltip of the component.
     *
     * @throws InternalErrorException Occurs when was not possible to execute
     * the operation.
     */
    protected void buildTooltip() throws InternalErrorException{
        if(this.tooltip != null)
            return;
        
        String resourcesKey = getResourcesKey();
        String name = getName();
        
        if(this.actionFormName != null && !this.actionFormName.isEmpty() && name != null && !name.isEmpty() && (resourcesKey == null || resourcesKey.isEmpty())){
            SecurityController securityController = getSecurityController();
            LoginSessionModel loginSession = (securityController != null ? securityController.getLoginSession() : null);
            SystemModuleModel systemModule = (loginSession != null ? loginSession.getSystemModule() : null);
            FormModel form = (systemModule != null ? systemModule.getForm(this.actionFormName) : null);
            ObjectModel object = (form != null ? form.getObject(name) : null);
            
            if(object != null)
                if(this.tooltip == null)
                    this.tooltip = object.getTooltip();
        }
        
        PropertiesResources resources = getResources();
        PropertiesResources mainConsoleResources = getMainConsoleResources();
        PropertiesResources defaultResources = getDefaultResources();
        String resourcesKeyValue = null;
        String tooltipValue = null;
        StringBuilder propertyId = null;
        
        if(resourcesKey != null && !resourcesKey.isEmpty() && this.tooltip == null){
            propertyId = new StringBuilder();
            propertyId.append(resourcesKey);
            propertyId.append(".");
            propertyId.append(Constants.TOOLTIP_ATTRIBUTE_ID);
            
            resourcesKeyValue = (resources != null ? resources.getProperty(propertyId.toString(), false) : null);
            
            if(resourcesKeyValue == null)
                resourcesKeyValue = (mainConsoleResources != null ? mainConsoleResources.getProperty(propertyId.toString(), false) : null);
            
            if(resourcesKeyValue == null)
                resourcesKeyValue = (defaultResources != null ? defaultResources.getProperty(propertyId.toString(), false) : null);
        }
        
        if(this.tooltip == null){
            if(propertyId == null)
                propertyId = new StringBuilder();
            else
                propertyId.delete(0, propertyId.length());
            
            propertyId.append(name);
            propertyId.append(".");
            propertyId.append(Constants.TOOLTIP_ATTRIBUTE_ID);
            
            tooltipValue = (resources != null ? resources.getProperty(propertyId.toString(), false) : null);
            
            if(tooltipValue == null)
                tooltipValue = (mainConsoleResources != null ? mainConsoleResources.getProperty(propertyId.toString(), false) : null);
            
            if(tooltipValue == null)
                tooltipValue = (defaultResources != null ? defaultResources.getProperty(propertyId.toString(), false) : null);
        }
        
        if(resourcesKeyValue != null)
            this.tooltip = resourcesKeyValue;
        else if(tooltipValue != null)
            this.tooltip = tooltipValue;
    }

    @Override
    protected void buildAlignment() throws InternalErrorException{
        AlignmentType verticalAlignment = getVerticalAlignmentType();
        
        if(verticalAlignment == null){
            verticalAlignment = AlignmentType.MIDDLE;
            
            setVerticalAlignmentType(verticalAlignment);
        }
        
        PositionType labelPosition = getLabelPositionType();
        
        if(labelPosition == null){
            labelPosition = PositionType.LEFT;
            
            setLabelPositionType(labelPosition);
        }
        
        AlignmentType labelAlignment = getLabelAlignmentType();
        
        if(labelAlignment == null){
            if(labelPosition == PositionType.LEFT){
                labelAlignment = AlignmentType.RIGHT;
                
                setLabelAlignmentType(labelAlignment);
            }
            else if(labelPosition == PositionType.RIGHT){
                labelAlignment = AlignmentType.LEFT;
                
                setLabelAlignmentType(labelAlignment);
            }
        }
        
        super.buildAlignment();
    }

    @Override
    protected void buildStyleClass() throws InternalErrorException{
        buildLabelStyleClass();
        
        ComponentType componentType = getComponentType();
        String styleClass = getStyleClass();
        
        if((styleClass == null || styleClass.isEmpty()) && componentType != null){
            styleClass = componentType.getId();
            
            setStyleClass(styleClass);
        }
        
        super.buildStyleClass();
    }
    
    /**
     * Builds the CSS style of the label.
     *
     * @throws InternalErrorException Occurs when was not possible to execute
     * the operation.
     */
    protected void buildLabelStyleClass() throws InternalErrorException{
        if(this.labelStyleClass == null || this.labelStyleClass.isEmpty())
            this.labelStyleClass = UIConstants.DEFAULT_LABEL_STYLE_CLASS;
    }

    @Override
    protected void buildResources() throws InternalErrorException{
        ActionFormComponent actionFormComponent = getActionFormComponent();
        
        if(actionFormComponent != null){
            String resourcesId = getResourcesId();
            
            if(resourcesId == null || resourcesId.isEmpty()){
                resourcesId = actionFormComponent.getResourcesId();
                
                setResourcesId(resourcesId);
            }
        }
        
        super.buildResources();
        
        buildLabel();
        buildTooltip();
    }

    @Override
    protected void buildEvents() throws InternalErrorException{
        buildEvent(EventType.ON_BLUR);
        buildEvent(EventType.ON_FOCUS);
        buildEvent(EventType.ON_CLICK);
        buildEvent(EventType.ON_MOUSE_OVER);
        buildEvent(EventType.ON_MOUSE_OUT);
        
        super.buildEvents();
    }
    
    /**
     * Builds a specific event.
     *
     * @param eventType Instance that contains the event.
     * @throws InternalErrorException Occurs when was not possible to execute
     * the operation.
     */
    protected void buildEvent(EventType eventType) throws InternalErrorException{
        try{
            StringBuilder eventContent = null;
            String eventId = eventType.getId();
            String currentEventContent = PropertyUtil.getValue(this, eventId);
            String eventAction = PropertyUtil.getValue(this, eventId.concat(StringUtil.capitalize(ActionFormConstants.ACTION_ATTRIBUTE_ID)));
            
            if(this.actionFormName != null && !this.actionFormName.isEmpty()){
                String eventForward = PropertyUtil.getValue(this, eventId.concat(StringUtil.capitalize(ActionFormConstants.FORWARD_ATTRIBUTE_ID)));
                
                if(eventForward != null && !eventForward.isEmpty()){
                    eventContent = new StringBuilder();
                    eventContent.append("document.");
                    eventContent.append(this.actionFormName);
                    eventContent.append(".");
                    eventContent.append(ActionFormConstants.FORWARD_ATTRIBUTE_ID);
                    eventContent.append(".value = '");
                    eventContent.append(eventForward);
                    eventContent.append("';");
                }
                
                String eventUpdateViews = PropertyUtil.getValue(this, eventId.concat(StringUtil.capitalize(UIConstants.UPDATE_VIEWS_ATTRIBUTE_ID)));
                
                if(eventUpdateViews != null && !eventUpdateViews.isEmpty()){
                    if(eventContent == null)
                        eventContent = new StringBuilder();
                    else if(!eventContent.isEmpty())
                        eventContent.append(" ");
                    
                    eventContent.append("document.");
                    eventContent.append(this.actionFormName);
                    eventContent.append(".");
                    eventContent.append(UIConstants.UPDATE_VIEWS_ATTRIBUTE_ID);
                    eventContent.append(".value = '");
                    eventContent.append(eventUpdateViews);
                    eventContent.append("';");
                }
                
                boolean eventValidate = PropertyUtil.getValue(this, eventId.concat(StringUtil.capitalize(ModelConstants.VALIDATE_MODEL_ATTRIBUTE_ID)));
                
                if(eventValidate){
                    if(eventContent == null)
                        eventContent = new StringBuilder();
                    else if(!eventContent.isEmpty())
                        eventContent.append(" ");
                    
                    eventContent.append("document.");
                    eventContent.append(this.actionFormName);
                    eventContent.append(".");
                    eventContent.append(ModelConstants.VALIDATE_MODEL_ATTRIBUTE_ID);
                    eventContent.append(".value = true;");
                    
                    String eventValidateModelProperties = PropertyUtil.getValue(this, eventId.concat(StringUtil.capitalize(ModelConstants.VALIDATE_MODEL_PROPERTIES_ATTRIBUTE_ID)));
                    
                    if(eventValidateModelProperties != null && !eventValidateModelProperties.isEmpty()){
                        eventContent.append(" document.");
                        eventContent.append(this.actionFormName);
                        eventContent.append(".");
                        eventContent.append(ModelConstants.VALIDATE_MODEL_PROPERTIES_ATTRIBUTE_ID);
                        eventContent.append(".value = '");
                        eventContent.append(eventValidateModelProperties);
                        eventContent.append("';");
                    }
                }
                
                if(eventAction != null && !eventAction.isEmpty()){
                    if(eventContent == null)
                        eventContent = new StringBuilder();
                    else if(!eventContent.isEmpty())
                        eventContent.append(" ");
                    
                    eventContent.append("document.");
                    eventContent.append(this.actionFormName);
                    eventContent.append(".");
                    eventContent.append(ActionFormConstants.ACTION_ATTRIBUTE_ID);
                    eventContent.append(".value = '");
                    eventContent.append(eventAction);
                    eventContent.append("';");
                }
            }
            
            if(currentEventContent != null && !currentEventContent.isEmpty()){
                if(eventContent == null)
                    eventContent = new StringBuilder();
                else if(!eventContent.isEmpty())
                    eventContent.append(" ");
                
                eventContent.append(currentEventContent);
                
                if(!currentEventContent.endsWith(";"))
                    eventContent.append(";");
            }
            
            if(this.actionFormName != null && !this.actionFormName.isEmpty() && eventAction != null && !eventAction.isEmpty()){
                if(!eventContent.isEmpty())
                    eventContent.append(" ");
                
                if(asynchronousEvents()){
                    eventContent.append("submitActionForm(document.");
                    eventContent.append(this.actionFormName);
                    eventContent.append(");");
                }
                else{
                    eventContent.append("document.");
                    eventContent.append(this.actionFormName);
                    eventContent.append(".submit();");
                }
            }
            
            if(eventContent != null && !eventContent.isEmpty())
                PropertyUtil.setValue(this, eventId, eventContent.toString());
        }
        catch(IllegalArgumentException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e){
            throw new InternalErrorException(e);
        }
    }

    @Override
    protected void buildRestrictions() throws InternalErrorException{
        buildMaximumLength();
        buildSize();
        
        super.buildRestrictions();
    }
    
    /**
     * Builds the size of the component.
     *
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    protected void buildSize() throws InternalErrorException{
    }
    
    /**
     * Builds the maximum length of the component.
     *
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    protected void buildMaximumLength() throws InternalErrorException{
    }

    @Override
    protected void buildPermissions() throws InternalErrorException{
        String name = getName();
        
        if(this.actionFormName != null && !this.actionFormName.isEmpty() && name != null && !name.isEmpty()){
            SecurityController securityController = getSecurityController();
            LoginSessionModel loginSession = (securityController != null ? securityController.getLoginSession() : null);
            SystemModuleModel systemModule = (loginSession != null ? loginSession.getSystemModule() : null);
            FormModel form = (systemModule != null ? systemModule.getForm(this.actionFormName) : null);
            ObjectModel object = (form != null ? form.getObject(name) : null);
            ComponentType componentType = getComponentType();
            
            if(object != null && object.getType() != null && componentType != null && componentType.equals(object.getType())){
                setHasPermission(securityController.isLoginSessionAuthenticated());
                
                if(hasPermission()){
                    UserModel user = loginSession.getUser();
                    Boolean superUser = user.isSuperUser();
                    
                    if(superUser != null && superUser)
                        setHasPermission(true);
                    else
                        setHasPermission(user.hasPermission(object));
                }

                boolean render = render();

                if(render)
                    setRender(hasPermission());
            }
        }
        
        super.buildPermissions();
    }
    
    /**
     * Renders the attribute that defines the label of the component.
     *
     * @throws InternalErrorException Occurs when was not possible to render.
     */
    protected void renderLabelAttribute() throws InternalErrorException{
        String id = getId();
        String name = getName();
        boolean enabled = isEnabled();
        
        if(id != null && !id.isEmpty() && name != null && !name.isEmpty() && enabled){
            StringBuilder idBuffer = new StringBuilder();
            
            idBuffer.append(id);
            idBuffer.append(".");
            idBuffer.append(Constants.LABEL_ATTRIBUTE_ID);
            
            StringBuilder nameBuffer = new StringBuilder();
            
            nameBuffer.append(name);
            nameBuffer.append(".");
            nameBuffer.append(Constants.LABEL_ATTRIBUTE_ID);
            
            HiddenPropertyComponent labelPropertyComponent = new HiddenPropertyComponent();
            
            labelPropertyComponent.setPageContext(this.pageContext);
            labelPropertyComponent.setOutputStream(getOutputStream());
            labelPropertyComponent.setActionFormName(getActionFormName());
            labelPropertyComponent.setId(idBuffer.toString());
            labelPropertyComponent.setName(nameBuffer.toString());
            labelPropertyComponent.setValue(getLabel());
            
            try{
                labelPropertyComponent.doStartTag();
                labelPropertyComponent.doEndTag();
            }
            catch(JspException e){
                throw new InternalErrorException(e);
            }
        }
    }
    
    /**
     * Renders the label (tag opening) of the component.
     *
     * @throws InternalErrorException Occurs when was not possible to render.
     */
    protected void renderLabelOpen() throws InternalErrorException{
        PositionType labelPosition = getLabelPositionType();
        
        if(this.showLabel && this.label != null && !this.label.isEmpty() && labelPosition != PositionType.INSIDE){
            print("<td class=\"");
            print(this.labelStyleClass);
            print("\"");
            
            if(this.labelStyle != null && !this.labelStyle.isEmpty()){
                print(" style=\"");
                print(this.labelStyle);
                
                if(!this.labelStyle.endsWith(";"))
                    print(";");
                
                print("\"");
            }
            
            if(this.labelAlignment != null && !this.labelAlignment.isEmpty()){
                print(" align=\"");
                print(this.labelAlignment);
                print("\"");
            }
            
            if(this.labelVerticalAlignment != null && !this.labelVerticalAlignment.isEmpty()){
                print(" valign=\"");
                print(this.labelVerticalAlignment);
                print("\"");
            }
            
            renderTooltip();
            
            println(">");
        }
    }
    
    /**
     * Renders the label (tag body) of the component.
     *
     * @throws InternalErrorException Occurs when was not possible to render.
     */
    protected void renderLabelBody() throws InternalErrorException{
        PositionType labelPosition = getLabelPositionType();
        
        if(this.showLabel && this.label != null && !this.label.isEmpty() && labelPosition != PositionType.INSIDE){
            print(this.label);
            
            PositionType labelPositionType = getLabelPositionType();
            
            if(labelPositionType != PositionType.RIGHT && labelPositionType != PositionType.BOTTOM){
                if(!this.label.endsWith("?") && !this.label.endsWith(":"))
                    print(":");
                
                print("&nbsp;");
            }
            
            println();
        }
    }
    
    /**
     * Renders the label (tag close) of the component.
     *
     * @throws InternalErrorException Occurs when was not possible to render.
     */
    protected void renderLabelClose() throws InternalErrorException{
        PositionType labelPosition = getLabelPositionType();
        
        if(this.showLabel && this.label != null && !this.label.isEmpty() && labelPosition != PositionType.INSIDE)
            println("</td>");
    }
    
    /**
     * Renders the tooltip of the component.
     *
     * @throws InternalErrorException Occurs when was not possible to render.
     */
    protected void renderTooltip() throws InternalErrorException{
        if(this.tooltip != null && !this.tooltip.isEmpty()){
            print(" title=\"");
            print(this.tooltip);
            print("\"");
        }
    }
    
    /**
     * Renders the title of the component.
     *
     * @throws InternalErrorException Occurs when was not possible to render.
     */
    protected void renderLabel() throws InternalErrorException{
        if(this.showLabel && this.label != null && !this.label.isEmpty()){
            renderLabelOpen();
            renderLabelBody();
            renderLabelClose();
        }
    }
    
    /**
     * Renders the type of the component.
     *
     * @throws InternalErrorException Occurs when was not possible to render.
     */
    protected void renderType() throws InternalErrorException{
        ComponentType componentType = getComponentType();
        
        if(componentType != null){
            print(" type=\"");
            print(componentType.getType());
            print("\"");
        }
    }
    
    /**
     * Renders the input size of the component.
     *
     * @throws InternalErrorException Occurs when was not possible to render.
     */
    protected void renderSize() throws InternalErrorException{
        if(this.size > 0){
            print(" size=\"");
            print(this.size);
            print("\"");
        }
        
        if(this.maximumLength > 0){
            print(" maxlength=\"");
            print(this.maximumLength);
            print("\"");
        }
    }

    @Override
    protected void renderAttributes() throws InternalErrorException{
        renderType();
        
        super.renderAttributes();
        
        renderSize();
        renderTooltip();
        renderEnabled();
    }
    
    /**
     * Renders the enabling attribute.
     *
     * @throws InternalErrorException Occurs when was not possible to render.
     */
    protected void renderEnabled() throws InternalErrorException{
        boolean enabled = isEnabled();
        
        if(!enabled){
            print(" ");
            print(Constants.DISABLED_ATTRIBUTE_ID);
        }
    }

    @Override
    protected void renderOpen() throws InternalErrorException{
        super.renderOpen();
        
        PositionType labelPosition = getLabelPositionType();
        
        if(this.showLabel && this.label != null && !this.label.isEmpty() && labelPosition != PositionType.INSIDE){
            println("<table>");
            println("<tr>");
            
            if(labelPosition == PositionType.LEFT || labelPosition == PositionType.TOP){
                renderLabel();
                
                if(labelPosition == PositionType.TOP){
                    println("</tr>");
                    println("<tr>");
                }
            }
            
            print("<td");
            
            if(this.alignment != null && !this.alignment.isEmpty()){
                print(" align=\"");
                print(this.alignment);
                print("\"");
            }
            
            if(this.verticalAlignment != null && !this.verticalAlignment.isEmpty()){
                print(" valign=\"");
                print(this.verticalAlignment);
                print("\"");
            }
            
            print(">");
        }
    }

    @Override
    protected void renderBody() throws InternalErrorException{
        print("<input ");
        
        renderAttributes();
        
        println(">");
    }

    @Override
    protected void renderClose() throws InternalErrorException{
        if(this.showLabel && this.label != null && !this.label.isEmpty()){
            println("</td>");
            
            PositionType labelPosition = getLabelPositionType();
            
            if(labelPosition == PositionType.RIGHT || labelPosition == PositionType.BOTTOM){
                if(labelPosition == PositionType.BOTTOM){
                    println("</tr>");
                    println("<tr>");
                }
                
                renderLabel();
            }
            
            println("</tr>");
            println("</table>");
        }
        
        super.renderClose();
    }

    @Override
    protected void clearAttributes() throws InternalErrorException{
        super.clearAttributes();
        
        setLabel(null);
        setLabelStyle(null);
        setLabelStyleClass(null);
        setLabelPosition(null);
        setLabelAlignment(null);
        setLabelVerticalAlignment(null);
        setShowLabel(true);
        setTooltip(null);
        setAlignment(null);
        setVerticalAlignment(null);
        setHasPermission(true);
        setOnBlurAction(null);
        setOnBlurForward(null);
        setOnBlurUpdateViews(null);
        setOnBlurValidateModel(false);
        setOnBlurValidateModelProperties(null);
        setOnFocusAction(null);
        setOnFocusForward(null);
        setOnFocusUpdateViews(null);
        setOnFocusValidateModel(false);
        setOnFocusValidateModelProperties(null);
        setOnClickAction(null);
        setOnClickForward(null);
        setOnClickUpdateViews(null);
        setOnClickValidateModel(false);
        setOnClickValidateModelProperties(null);
        setOnMouseOverAction(null);
        setOnMouseOverForward(null);
        setOnMouseOverUpdateViews(null);
        setOnMouseOverValidateModel(false);
        setOnMouseOverValidateModelProperties(null);
        setOnMouseOutAction(null);
        setOnMouseOutForward(null);
        setOnMouseOutUpdateViews(null);
        setOnMouseOutValidateModel(false);
        setOnMouseOutValidateModelProperties(null);
        setMainConsoleResources(null);
        setActionFormName(null);
    }
}