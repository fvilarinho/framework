package br.com.concepting.framework.ui.components;

import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.resources.PropertiesResources;
import br.com.concepting.framework.ui.constants.UIConstants;
import br.com.concepting.framework.util.types.AlignmentType;
import br.com.concepting.framework.util.types.ComponentType;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;

/**
 * Class that defines the dialog box component.
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
public class DialogBoxComponent extends BaseActionFormComponent{
    private static final long serialVersionUID = -3278696529902173481L;
    
    private String title = null;
    private Boolean modal = null;
    private Boolean showOnLoad = null;
    private Boolean showButtons = null;
    
    /**
     * Returns the title of the component.
     *
     * @return String that contains the title.
     */
    public String getTitle(){
        return this.title;
    }
    
    /**
     * Defines the title of the component.
     *
     * @param title String that contains the title.
     */
    public void setTitle(String title){
        this.title = title;
    }
    
    /**
     * Indicates if the buttons of the component should be shown.
     *
     * @return True/False.
     */
    public Boolean showButtons(){
        return this.showButtons;
    }
    
    /**
     * Indicates if the buttons of the component should be shown.
     *
     * @return True/False.
     */
    public Boolean getShowButtons(){
        return showButtons();
    }
    
    /**
     * Defines if the buttons of the component should be shown.
     *
     * @param showButtons True/False.
     */
    public void setShowButtons(Boolean showButtons){
        this.showButtons = showButtons;
    }
    
    /**
     * Indicates if the component should be shown on the page load.
     *
     * @return True/False.
     */
    public Boolean showOnLoad(){
        return this.showOnLoad;
    }
    
    /**
     * Indicates if the component should be shown on the page load.
     *
     * @return True/False.
     */
    public Boolean getShowOnLoad(){
        return showOnLoad();
    }
    
    /**
     * Defines if the component should be shown on the page load.
     *
     * @param showOnLoad True/False.
     */
    public void setShowOnLoad(Boolean showOnLoad){
        this.showOnLoad = showOnLoad;
    }
    
    /**
     * Indicates if the component is a modal.
     *
     * @return True/False.
     */
    public Boolean isModal(){
        return this.modal;
    }
    
    /**
     * Indicates if the component is a modal.
     *
     * @return True/False.
     */
    public Boolean getModal(){
        return isModal();
    }
    
    /**
     * Defines if the component is a modal.
     *
     * @param modal True/False.
     */
    public void setModal(Boolean modal){
        this.modal = modal;
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#buildLabel()
     */
    protected void buildLabel() throws InternalErrorException{
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#buildTooltip()
     */
    protected void buildTooltip() throws InternalErrorException{
    }
    
    /**
     * Builds the title of the component.
     *
     * @throws InternalErrorException Occurs when was not possible to execute
     * the operation.
     */
    protected void buildTitle() throws InternalErrorException{
        String name = getName();
        
        if((this.title == null || this.title.length() == 0) && name != null && name.length() > 0){
            PropertiesResources resources = getResources();
            PropertiesResources defaultResources = getDefaultResources();
            StringBuilder propertyId = new StringBuilder();
            
            propertyId.append(name);
            propertyId.append(".");
            propertyId.append(Constants.LABEL_ATTRIBUTE_ID);
            
            this.title = (resources != null ? resources.getProperty(propertyId.toString(), false) : null);
            
            if(this.title == null)
                this.title = (defaultResources != null ? defaultResources.getProperty(propertyId.toString(), false) : null);
        }
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#buildResources()
     */
    protected void buildResources() throws InternalErrorException{
        super.buildResources();
        
        buildTitle();
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#buildStyleClass()
     */
    protected void buildStyleClass() throws InternalErrorException{
        String styleClass = getStyleClass();
        
        if(styleClass == null || styleClass.length() == 0){
            styleClass = UIConstants.DEFAULT_DIALOG_BOX_STYLE_CLASS;
            
            setStyleClass(styleClass);
        }
        
        super.buildStyleClass();
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#buildRestrictions()
     */
    protected void buildRestrictions() throws InternalErrorException{
        if(this.showOnLoad == null)
            this.showOnLoad = false;
        
        if(this.showButtons == null)
            this.showButtons = true;
        
        if(this.modal == null)
            this.modal = false;
        
        super.buildRestrictions();
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BaseComponent#initialize()
     */
    protected void initialize() throws InternalErrorException{
        ComponentType componentType = getComponentType();
        
        if(componentType == null){
            componentType = ComponentType.DIALOG_BOX;
            
            setComponentType(componentType);
        }
        
        super.initialize();
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
            print(UIConstants.DEFAULT_DIALOG_BOX_ID);
            print("\"");
        }
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BaseComponent#renderName()
     */
    protected void renderName() throws InternalErrorException{
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#renderType()
     */
    protected void renderType() throws InternalErrorException{
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#renderEnabled()
     */
    protected void renderEnabled() throws InternalErrorException{
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BaseComponent#renderOpen()
     */
    protected void renderOpen() throws InternalErrorException{
        print("<div");
        
        renderAttributes();
        
        println(">");
        
        print("<table class=\"");
        print(UIConstants.DEFAULT_DIALOG_BOX_CONTENT_STYLE_CLASS);
        println("\">");
        
        String title = getTitle();
        
        if(title != null && title.length() > 0){
            println("<tr>");
            print("<td class=\"");
            print(UIConstants.DEFAULT_DIALOG_BOX_TITLE_STYLE_CLASS);
            print("\">");
            print(title);
            println("</td>");
            println("</tr>");
            
            println("<tr>");
            println("<td height=\"10\"></td>");
            println("</tr>");
        }
        
        println("<tr>");
        println("<td>");
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#renderBody()
     */
    protected void renderBody() throws InternalErrorException{
        print("<table class=\"");
        print(UIConstants.DEFAULT_CONTENT_PANEL_STYLE_CLASS);
        println("\">");
        println("<tr>");
        println("<td width=\"10\"></td>");
        print("<td class=\"");
        print(UIConstants.DEFAULT_DIALOG_BOX_TEXT_STYLE_CLASS);
        println("\">");
        
        BodyContent bodyContent = getBodyContent();
        
        if(bodyContent != null){
            String content = bodyContent.getString();
            
            if(content != null && content.length() > 0)
                println(content);
        }
        
        println("</td>");
        println("<td width=\"10\"></td>");
        println("</tr>");
        println("</table>");
    }
    
    /**
     * Renders the controls.
     *
     * @throws InternalErrorException Occurs when was not possible to render.
     */
    protected void renderControls() throws InternalErrorException{
        CloseButtonComponent closeButtonComponent = new CloseButtonComponent(this);
        
        try{
            closeButtonComponent.doStartTag();
            closeButtonComponent.doEndTag();
        }
        catch(JspException e){
            throw new InternalErrorException(e);
        }
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BaseComponent#renderClose()
     */
    protected void renderClose() throws InternalErrorException{
        println("</td>");
        println("</tr>");
        
        if(this.showButtons != null && this.showButtons){
            println("<tr>");
            print("<td valign=\"");
            print(AlignmentType.BOTTOM);
            print("\" align=\"");
            print(AlignmentType.CENTER);
            println("\" height=\"1\">");
            println("<br/>");
            
            renderControls();
            
            println("</td>");
            println("</tr>");
        }
        
        println("<tr>");
        println("<td height=\"10\"></td>");
        println("</tr>");
        println("</table>");
        
        println("</div>");
        
        String name = getName();
        
        if(name != null && name.length() > 0 && this.showOnLoad != null && this.showOnLoad){
            StringBuilder content = new StringBuilder();
            
            content.append("showDialogBox(\"");
            content.append(name);
            content.append("\"");
            
            if(this.modal != null){
                content.append(", ");
                content.append(this.modal);
            }
            
            content.append(");");
            
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
    
    /**
     * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#clearAttributes()
     */
    protected void clearAttributes() throws InternalErrorException{
        super.clearAttributes();
        
        setShowOnLoad(null);
        setShowButtons(null);
        setModal(null);
        setTitle(null);
    }
    
    /**
     * Class that defines the close button component.
     *
     * @author fvilarinho
     * @since 3.0.0
     */
    private class CloseButtonComponent extends ButtonComponent{
        private static final long serialVersionUID = -374340863206135380L;
        
        public CloseButtonComponent(DialogBoxComponent dialogBoxComponent){
            super();
            
            if(dialogBoxComponent != null){
                setPageContext(dialogBoxComponent.getPageContext());
                setOutputStream(dialogBoxComponent.getOutputStream());
                setActionFormName(dialogBoxComponent.getActionFormName());
                setParent(dialogBoxComponent);
            }
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.ButtonComponent#buildEvents()
         */
        protected void buildEvents() throws InternalErrorException{
            DialogBoxComponent dialogComponent = (DialogBoxComponent) getParent();
            String name = (dialogComponent != null ? dialogComponent.getName() : null);
            
            if(name != null && name.length() > 0){
                String currentOnClick = getOnClick();
                StringBuilder onClick = new StringBuilder();
                
                onClick.append("hideDialogBox('");
                onClick.append(name);
                onClick.append("');");
                
                if(currentOnClick != null && currentOnClick.length() > 0){
                    onClick.append(" ");
                    onClick.append(currentOnClick);
                    
                    if(!currentOnClick.endsWith(";"))
                        onClick.append(";");
                }
                
                setOnClick(onClick.toString());
                
                super.buildEvents();
            }
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.ButtonComponent#buildStyleClass()
         */
        protected void buildStyleClass() throws InternalErrorException{
            setStyleClass(UIConstants.DEFAULT_CLOSE_BUTTON_STYLE_CLASS);
            
            super.buildStyleClass();
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.ButtonComponent#buildResources()
         */
        protected void buildResources() throws InternalErrorException{
            setResourcesKey(UIConstants.DEFAULT_CLOSE_BUTTON_ID);
            
            super.buildResources();
        }
    }
}