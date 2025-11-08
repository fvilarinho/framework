package br.com.concepting.framework.ui.components;

import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.resources.PropertiesResources;
import br.com.concepting.framework.ui.constants.UIConstants;
import br.com.concepting.framework.util.types.AlignmentType;
import br.com.concepting.framework.util.types.ComponentType;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;
import java.io.Serial;

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
public class DialogBoxComponent extends BaseActionFormComponent{
    @Serial
    private static final long serialVersionUID = -3278696529902173481L;
    
    private String title = null;
    private boolean modal = false;
    private boolean showOnLoad = false;
    private boolean showButtons = true;
    
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
    public boolean showButtons(){
        return this.showButtons;
    }
    
    /**
     * Indicates if the buttons of the component should be shown.
     *
     * @return True/False.
     */
    public boolean getShowButtons(){
        return showButtons();
    }
    
    /**
     * Defines if the buttons of the component should be shown.
     *
     * @param showButtons True/False.
     */
    public void setShowButtons(boolean showButtons){
        this.showButtons = showButtons;
    }
    
    /**
     * Indicates if the component should be shown on the page load.
     *
     * @return True/False.
     */
    public boolean showOnLoad(){
        return this.showOnLoad;
    }
    
    /**
     * Indicates if the component should be shown on the page load.
     *
     * @return True/False.
     */
    public boolean getShowOnLoad(){
        return showOnLoad();
    }
    
    /**
     * Defines if the component should be shown on the page load.
     *
     * @param showOnLoad True/False.
     */
    public void setShowOnLoad(boolean showOnLoad){
        this.showOnLoad = showOnLoad;
    }
    
    /**
     * Indicates if the component is a modal.
     *
     * @return True/False.
     */
    public boolean isModal(){
        return this.modal;
    }
    
    /**
     * Indicates if the component is a modal.
     *
     * @return True/False.
     */
    public boolean getModal(){
        return isModal();
    }
    
    /**
     * Defines if the component is a modal.
     *
     * @param modal True/False.
     */
    public void setModal(boolean modal){
        this.modal = modal;
    }

    @Override
    protected void buildLabel() throws InternalErrorException{
    }

    @Override
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
        
        if((this.title == null || this.title.isEmpty()) && name != null && !name.isEmpty()){
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

    @Override
    protected void buildResources() throws InternalErrorException{
        super.buildResources();
        
        buildTitle();
    }

    @Override
    protected void buildStyleClass() throws InternalErrorException{
        String styleClass = getStyleClass();
        
        if(styleClass == null || styleClass.isEmpty()){
            styleClass = UIConstants.DEFAULT_DIALOG_BOX_STYLE_CLASS;
            
            setStyleClass(styleClass);
        }
        
        super.buildStyleClass();
    }

    @Override
    protected void initialize() throws InternalErrorException{
        ComponentType componentType = getComponentType();
        
        if(componentType == null){
            componentType = ComponentType.DIALOG_BOX;
            
            setComponentType(componentType);
        }
        
        super.initialize();
    }

    @Override
    protected void renderId() throws InternalErrorException{
        String id = getId();
        
        if(id != null && !id.isEmpty()){
            print(" id=\"");
            print(id);
            print(".");
            print(UIConstants.DEFAULT_DIALOG_BOX_ID);
            print("\"");
        }
    }

    @Override
    protected void renderName() throws InternalErrorException{
    }

    @Override
    protected void renderType() throws InternalErrorException{
    }

    @Override
    protected void renderEnabled() throws InternalErrorException{
    }

    @Override
    protected void renderOpen() throws InternalErrorException{
        print("<div");
        
        renderAttributes();
        
        println(">");
        
        print("<table class=\"");
        print(UIConstants.DEFAULT_DIALOG_BOX_CONTENT_STYLE_CLASS);
        println("\">");
        
        String title = getTitle();
        
        if(title != null && !title.isEmpty()){
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

    @Override
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
            
            if(content != null && !content.isEmpty())
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

    @Override
    protected void renderClose() throws InternalErrorException{
        println("</td>");
        println("</tr>");
        
        if(this.showButtons){
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
        
        if(name != null && !name.isEmpty() && this.showOnLoad){
            StringBuilder content = new StringBuilder();
            
            content.append("showDialogBox(\"");
            content.append(name);
            content.append("\", ");
            content.append(this.modal);
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

    @Override
    protected void clearAttributes() throws InternalErrorException{
        super.clearAttributes();
        
        setShowOnLoad(false);
        setShowButtons(true);
        setModal(false);
        setTitle(null);
    }
    
    /**
     * Class that defines the close button component.
     *
     * @author fvilarinho
     * @since 3.0.0
     */
    private static class CloseButtonComponent extends ButtonComponent{
        @Serial
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

        @Override
        protected void buildEvents() throws InternalErrorException{
            DialogBoxComponent dialogComponent = (DialogBoxComponent) getParent();
            String name = (dialogComponent != null ? dialogComponent.getName() : null);
            
            if(name != null && !name.isEmpty()){
                String currentOnClick = getOnClick();
                StringBuilder onClick = new StringBuilder();
                
                onClick.append("hideDialogBox('");
                onClick.append(name);
                onClick.append("');");
                
                if(currentOnClick != null && !currentOnClick.isEmpty()){
                    onClick.append(" ");
                    onClick.append(currentOnClick);
                    
                    if(!currentOnClick.endsWith(";"))
                        onClick.append(";");
                }
                
                setOnClick(onClick.toString());
                
                super.buildEvents();
            }
        }

        @Override
        protected void buildStyleClass() throws InternalErrorException{
            setStyleClass(UIConstants.DEFAULT_CLOSE_BUTTON_STYLE_CLASS);
            
            super.buildStyleClass();
        }

        @Override
        protected void buildResources() throws InternalErrorException{
            setResourcesKey(UIConstants.DEFAULT_CLOSE_BUTTON_ID);
            
            super.buildResources();
        }
    }
}