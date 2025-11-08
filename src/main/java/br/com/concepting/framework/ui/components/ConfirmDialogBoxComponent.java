package br.com.concepting.framework.ui.components;

import br.com.concepting.framework.controller.action.types.ActionType;
import br.com.concepting.framework.exceptions.InternalErrorException;

import javax.servlet.jsp.JspException;
import java.io.Serial;

/**
 * Class that defines the confirmation dialog box component.
 *
 * @author fvilarinho
 * @since 3.0.0
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
public class ConfirmDialogBoxComponent extends MessageBoxComponent{
    @Serial
    private static final long serialVersionUID = -4977589330940038040L;
    
    private String onConfirm = null;
    private String onConfirmAction = null;
    private String onConfirmForward = null;
    private String onConfirmUpdateViews = null;
    private boolean onConfirmValidateModel = false;
    private String onConfirmValidateModelProperties = null;
    
    /**
     * Returns the identifiers of the views that will be updated.
     *
     * @return String that contains the identifiers of the views.
     */
    public String getOnConfirmUpdateViews(){
        return this.onConfirmUpdateViews;
    }
    
    /**
     * Defines the identifiers of the views that will be updated.
     *
     * @param onConfirmUpdateViews String that contains the identifiers of the
     * views.
     */
    public void setOnConfirmUpdateViews(String onConfirmUpdateViews){
        this.onConfirmUpdateViews = onConfirmUpdateViews;
    }
    
    /**
     * Returns the validation properties of the data model.
     *
     * @return String that contains the identifiers of the properties.
     */
    public String getOnConfirmValidateModelProperties(){
        return this.onConfirmValidateModelProperties;
    }
    
    /**
     * Defines the validation properties of the data model.
     *
     * @param onConfirmValidateModelProperties String that contains the
     * identifiers of the properties.
     */
    public void setOnConfirmValidateModelProperties(String onConfirmValidateModelProperties){
        this.onConfirmValidateModelProperties = onConfirmValidateModelProperties;
    }
    
    /**
     * Indicates if the data model should be validated.
     *
     * @return True/False.
     */
    public boolean getOnConfirmValidateModel(){
        return this.onConfirmValidateModel;
    }
    
    /**
     * Defines if the data model should be validated.
     *
     * @param onConfirmValidateModel True/False.
     */
    public void setOnConfirmValidateModel(boolean onConfirmValidateModel){
        this.onConfirmValidateModel = onConfirmValidateModel;
    }
    
    /**
     * Returns the identifier of the action forward.
     *
     * @return String that contains the identifier of the forward.
     */
    public String getOnConfirmForward(){
        return this.onConfirmForward;
    }
    
    /**
     * Defines the identifier of the action forward.
     *
     * @param onConfirmForward String that contains the identifier of the
     * forward.
     */
    public void setOnConfirmForward(String onConfirmForward){
        this.onConfirmForward = onConfirmForward;
    }
    
    /**
     * Returns the identifier of the action.
     *
     * @return String that contains the action.
     */
    public String getOnConfirmAction(){
        return this.onConfirmAction;
    }
    
    /**
     * Defines the action.
     *
     * @param onConfirmActionType Instance that contains the identifier.
     */
    protected void setOnConfirmActionType(ActionType onConfirmActionType){
        if(onConfirmActionType != null)
            this.onConfirmAction = onConfirmActionType.getMethod();
        else
            this.onConfirmAction = null;
    }
    
    /**
     * Defines the identifier of the action.
     *
     * @param onConfirmAction String that contains the identifier.
     */
    public void setOnConfirmAction(String onConfirmAction){
        this.onConfirmAction = onConfirmAction;
    }
    
    /**
     * Returns the confirmation event.
     *
     * @return String that contains the event.
     */
    public String getOnConfirm(){
        return this.onConfirm;
    }
    
    /**
     * Defines the confirmation event.
     *
     * @param onConfirm String that contains the event.
     */
    public void setOnConfirm(String onConfirm){
        this.onConfirm = onConfirm;
    }

    @Override
    protected void buildEvents() throws InternalErrorException{
        String name = getName();
        
        if(name == null || name.isEmpty())
            return;
        
        StringBuilder confirmContent = new StringBuilder();
        
        confirmContent.append("hideDialogBox('");
        confirmContent.append(name);
        confirmContent.append("');");
        
        if(this.onConfirm != null && !this.onConfirm.isEmpty()){
            confirmContent.append(" ");
            confirmContent.append(this.onConfirm);
            
            if(!this.onConfirm.endsWith(";"))
                confirmContent.append(";");
        }
        
        this.onConfirm = confirmContent.toString();
        
        super.buildEvents();
    }

    @Override
    public void buildRestrictions() throws InternalErrorException{
        setModal(true);

        super.buildRestrictions();
    }

    @Override
    protected void renderControls() throws InternalErrorException{
        String actionFormName = getActionFormName();
        
        if(actionFormName != null && !actionFormName.isEmpty()){
            ConfirmButtonComponent confirmButtonComponent = new ConfirmButtonComponent();
            
            confirmButtonComponent.setPageContext(this.pageContext);
            confirmButtonComponent.setOutputStream(getOutputStream());
            confirmButtonComponent.setActionFormName(actionFormName);
            confirmButtonComponent.setResourcesId(getResourcesId());
            confirmButtonComponent.setOnClick(this.onConfirm);
            confirmButtonComponent.setAction(this.onConfirmAction);
            confirmButtonComponent.setForward(this.onConfirmForward);
            confirmButtonComponent.setValidateModel(this.onConfirmValidateModel);
            confirmButtonComponent.setValidateModelProperties(this.onConfirmValidateModelProperties);
            confirmButtonComponent.setUpdateViews(this.onConfirmUpdateViews);
            
            try{
                confirmButtonComponent.doStartTag();
                confirmButtonComponent.doEndTag();
            }
            catch(JspException e){
                throw new InternalErrorException(e);
            }
            
            super.renderControls();
        }
    }

    @Override
    protected void clearAttributes() throws InternalErrorException{
        super.clearAttributes();
        
        setOnConfirm(null);
        setOnConfirmAction(null);
        setOnConfirmForward(null);
        setOnConfirmUpdateViews(null);
        setOnConfirmValidateModelProperties(null);
        setOnConfirmValidateModel(false);
    }
}