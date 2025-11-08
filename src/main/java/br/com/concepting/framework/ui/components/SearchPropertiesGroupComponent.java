package br.com.concepting.framework.ui.components;

import br.com.concepting.framework.controller.action.types.ActionType;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.ui.constants.UIConstants;
import br.com.concepting.framework.util.types.AlignmentType;
import br.com.concepting.framework.util.types.ComponentType;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;
import java.io.Serial;

/**
 * Class that defines the search properties group component.
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
public class SearchPropertiesGroupComponent extends BaseGroupComponent{
    @Serial
    private static final long serialVersionUID = 8751796595605205394L;
    
    private String action = null;
    private String forward = null;
    private String updateViews = null;
    private boolean validateModel = false;
    private String validateModelProperties = null;
    
    /**
     * Returns the identifiers of the views that should be updated.
     *
     * @return String that contains the identifiers of the views.
     */
    public String getUpdateViews(){
        return this.updateViews;
    }
    
    /**
     * Defines the identifiers of the views that should be updated.
     *
     * @param updateViews String that contains the identifiers of the views.
     */
    public void setUpdateViews(String updateViews){
        this.updateViews = updateViews;
    }
    
    /**
     * Returns the identifier of the action.
     *
     * @return String that contains the identifier of the action.
     */
    public String getAction(){
        return this.action;
    }
    
    /**
     * Defines the identifier of the action.
     *
     * @param action String that contains the identifier of the action.
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
     * @return String that contains the identifier of the action forward.
     */
    public String getForward(){
        return this.forward;
    }
    
    /**
     * Defines the identifier of the action forward.
     *
     * @param forward String that contains the identifier of the action forward.
     */
    public void setForward(String forward){
        this.forward = forward;
    }
    
    /**
     * Indicates if the data model of the form should be validated.
     *
     * @return True/False.
     */
    public boolean validateModel(){
        return this.validateModel;
    }
    
    /**
     * Indicates if the data model of the form should be validated.
     *
     * @return True/False.
     */
    public boolean getValidateModel(){
        return validateModel();
    }
    
    /**
     * Defines if the data model of the form should be validated.
     *
     * @param validateModel True/False.
     */
    public void setValidateModel(boolean validateModel){
        this.validateModel = validateModel;
    }
    
    /**
     * Returns the validation properties of the data model that should be
     * validated.
     *
     * @return String that contains the identifiers of the properties.
     */
    public String getValidateModelProperties(){
        return this.validateModelProperties;
    }
    
    /**
     * Defines the validation properties of the data model that should be
     * validated.
     *
     * @param validateModelProperties String that contains the identifiers of
     * the properties.
     */
    public void setValidateModelProperties(String validateModelProperties){
        this.validateModelProperties = validateModelProperties;
    }

    @Override
    protected void buildResources() throws InternalErrorException{
        setResourcesKey(UIConstants.DEFAULT_SEARCH_PROPERTIES_GROUP_ID);
        
        super.buildResources();
    }

    @Override
    protected void buildStyleClass() throws InternalErrorException{
        String styleClass = getStyleClass();
        
        if(styleClass == null || styleClass.isEmpty()){
            styleClass = UIConstants.DEFAULT_SEARCH_PROPERTIES_GROUP_STYLE_CLASS;
            
            setStyleClass(styleClass);
        }
        
        String labelStyleClass = getLabelStyleClass();
        
        if(labelStyleClass == null || labelStyleClass.isEmpty()){
            labelStyleClass = UIConstants.DEFAULT_SEARCH_PROPERTIES_GROUP_LABEL_STYLE_CLASS;
            
            setLabelStyleClass(labelStyleClass);
        }
        
        super.buildStyleClass();
    }

    @Override
    protected void initialize() throws InternalErrorException{
        if(this.action == null || this.action.isEmpty())
            this.action = ActionType.SEARCH.getMethod();
        
        setComponentType(ComponentType.SEARCH_PROPERTIES_GROUP);
        
        super.initialize();
    }

    @Override
    protected void renderBody() throws InternalErrorException{
        String actionFormName = getActionFormName();
        
        if(actionFormName == null || actionFormName.isEmpty())
            return;
        
        print("<table class=\"");
        print(UIConstants.DEFAULT_CONTENT_PANEL_STYLE_CLASS);
        println("\">");
        
        println("<tr>");
        println("<td height=\"5\"></td>");
        println("</tr>");
        
        println("<tr>");
        println("<td>");
        
        BodyContent bodyContent = getBodyContent();
        
        if(bodyContent != null){
            String content = bodyContent.getString();
            
            if(content != null && !content.isEmpty())
                println(content);
        }
        
        println("</td>");
        
        print("<td align=\"");
        print(AlignmentType.RIGHT);
        print("\" valign=\"");
        print(AlignmentType.TOP);
        print("\" width=\"1\">");
        
        SearchButtonComponent searchButtonComponent = new SearchButtonComponent();
        
        searchButtonComponent.setPageContext(this.pageContext);
        searchButtonComponent.setOutputStream(getOutputStream());
        searchButtonComponent.setActionFormName(actionFormName);
        searchButtonComponent.setResourcesId(getResourcesId());
        searchButtonComponent.setAction(this.action);
        searchButtonComponent.setForward(this.forward);
        searchButtonComponent.setUpdateViews(this.updateViews);
        searchButtonComponent.setValidateModel(this.validateModel);
        searchButtonComponent.setValidateModelProperties(this.validateModelProperties);
        
        try{
            searchButtonComponent.doStartTag();
            searchButtonComponent.doEndTag();
        }
        catch(JspException e){
            throw new InternalErrorException(e);
        }
        
        println("&nbsp;");
        println("</td>");
        println("</tr>");
        
        println("<tr>");
        println("<td height=\"5\"></td>");
        println("</tr>");
        
        println("</table>");
    }

    @Override
    protected void clearAttributes() throws InternalErrorException{
        super.clearAttributes();
        
        setAction(null);
        setForward(null);
        setValidateModel(false);
        setValidateModelProperties(null);
        setUpdateViews(null);
    }
}