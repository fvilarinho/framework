package br.com.concepting.framework.ui.components;

import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.ui.components.types.VisibilityType;
import br.com.concepting.framework.ui.constants.UIConstants;
import br.com.concepting.framework.ui.controller.UIController;
import br.com.concepting.framework.util.helpers.PropertyInfo;

import jakarta.servlet.jsp.JspException;

import java.awt.*;
import java.io.Serial;
import java.util.Arrays;
import java.util.Collection;

/**
 * Class that defines the rich text area component.
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
public class RichTextAreaPropertyComponent extends BasePropertyComponent{
    @Serial
    private static final long serialVersionUID = -3254930243069817268L;

    @Override
    protected void renderOpen() throws InternalErrorException{
        super.renderOpen();
        
        PropertyInfo propertyInfo = getPropertyInfo();
        String name = getName();
        
        if(propertyInfo != null && name != null && !name.isEmpty()){
            renderControls();
            
            print("<div id=\"");
            print(name);
            print(".");
            print(Constants.DEFAULT_CONTENT_ID);
            print("\" unselectable=\"off\" contentEditable=\"");
            print(Boolean.TRUE);
            print("\" class=\"");
            print(UIConstants.DEFAULT_RICH_TEXT_AREA_STYLE_CLASS);
            print("\"");
            
            String width = getWidth();
            String height = getHeight();
            
            if((width != null && !width.isEmpty()) || (height != null && !height.isEmpty())){
                print(" style=\"");
                
                if(width != null && !width.isEmpty()){
                    print("width: ");
                    print(width);
                    
                    if(!width.endsWith(";"))
                        print(";");
                }
                
                if(height != null && !height.isEmpty()){
                    if(width != null && !width.isEmpty())
                        print(" ");
                    
                    print("height: ");
                    print(height);
                    
                    if(!height.endsWith(";"))
                        print(";");
                }
                
                print("\"");
            }
            
            println(">");
        }
    }
    
    /**
     * Renders the controls.
     *
     * @throws InternalErrorException Occurs when was not possible to render.
     */
    protected void renderControls() throws InternalErrorException{
        UIController uiController = getUIController();
        String actionFormName = getActionFormName();
        String name = getName();
        
        if(uiController == null || actionFormName == null || actionFormName.isEmpty() || name == null || name.isEmpty())
            return;
        
        print("<div class=\"");
        print(UIConstants.DEFAULT_RICH_TEXT_AREA_TOOLBAR_STYLE_CLASS);
        print("\"");
        
        String width = getWidth();
        
        if(width != null && !width.isEmpty()){
            print(" style=\"width: ");
            print(width);
            
            if(!width.endsWith(";"))
                print(";");
            
            print("\"");
        }
        
        println(">");
        
        print("<table class=\"");
        print(UIConstants.DEFAULT_CONTENT_PANEL_STYLE_CLASS);
        println("\">");
        
        println("<tr>");
        println("<td>");
        
        ButtonComponent boldButtonComponent = new ButtonComponent();
        
        boldButtonComponent.setPageContext(this.pageContext);
        boldButtonComponent.setOutputStream(getOutputStream());
        boldButtonComponent.setActionFormName(actionFormName);
        boldButtonComponent.setResourcesId(UIConstants.DEFAULT_RICH_TEXT_AREA_RESOURCES_ID);
        boldButtonComponent.setResourcesKey("boldButton");
        boldButtonComponent.setStyleClass("boldButton");
        
        try{
            boldButtonComponent.doStartTag();
            boldButtonComponent.doEndTag();
        }
        catch(JspException e){
            throw new InternalErrorException(e);
        }
        
        ButtonComponent italicButtonComponent = new ButtonComponent();
        
        italicButtonComponent.setPageContext(this.pageContext);
        italicButtonComponent.setOutputStream(getOutputStream());
        italicButtonComponent.setActionFormName(actionFormName);
        italicButtonComponent.setResourcesId(UIConstants.DEFAULT_RICH_TEXT_AREA_RESOURCES_ID);
        italicButtonComponent.setResourcesKey("italicButton");
        italicButtonComponent.setStyleClass("italicButton");
        
        try{
            italicButtonComponent.doStartTag();
            italicButtonComponent.doEndTag();
        }
        catch(JspException e){
            throw new InternalErrorException(e);
        }
        
        ButtonComponent underlineButtonComponent = new ButtonComponent();
        
        underlineButtonComponent.setPageContext(this.pageContext);
        underlineButtonComponent.setOutputStream(getOutputStream());
        underlineButtonComponent.setActionFormName(actionFormName);
        underlineButtonComponent.setResourcesId(UIConstants.DEFAULT_RICH_TEXT_AREA_RESOURCES_ID);
        underlineButtonComponent.setResourcesKey("underlineButton");
        underlineButtonComponent.setStyleClass("underlineButton");
        
        try{
            underlineButtonComponent.doStartTag();
            underlineButtonComponent.doEndTag();
        }
        catch(JspException e){
            throw new InternalErrorException(e);
        }
        
        ButtonComponent strikeButtonComponent = new ButtonComponent();
        
        strikeButtonComponent.setPageContext(this.pageContext);
        strikeButtonComponent.setOutputStream(getOutputStream());
        strikeButtonComponent.setActionFormName(actionFormName);
        strikeButtonComponent.setResourcesId(UIConstants.DEFAULT_RICH_TEXT_AREA_RESOURCES_ID);
        strikeButtonComponent.setResourcesKey("strikeButton");
        strikeButtonComponent.setStyleClass("strikeButton");
        
        try{
            strikeButtonComponent.doStartTag();
            strikeButtonComponent.doEndTag();
        }
        catch(JspException e){
            throw new InternalErrorException(e);
        }
        
        ButtonComponent leftButtonComponent = new ButtonComponent();
        
        leftButtonComponent.setPageContext(this.pageContext);
        leftButtonComponent.setOutputStream(getOutputStream());
        leftButtonComponent.setActionFormName(actionFormName);
        leftButtonComponent.setResourcesId(UIConstants.DEFAULT_RICH_TEXT_AREA_RESOURCES_ID);
        leftButtonComponent.setResourcesKey("leftButton");
        leftButtonComponent.setStyleClass("leftButton");
        
        try{
            leftButtonComponent.doStartTag();
            leftButtonComponent.doEndTag();
        }
        catch(JspException e){
            throw new InternalErrorException(e);
        }
        
        ButtonComponent centerButtonComponent = new ButtonComponent();
        
        centerButtonComponent.setPageContext(this.pageContext);
        centerButtonComponent.setOutputStream(getOutputStream());
        centerButtonComponent.setActionFormName(actionFormName);
        centerButtonComponent.setResourcesId(UIConstants.DEFAULT_RICH_TEXT_AREA_RESOURCES_ID);
        centerButtonComponent.setResourcesKey("centerButton");
        centerButtonComponent.setStyleClass("centerButton");
        
        try{
            centerButtonComponent.doStartTag();
            centerButtonComponent.doEndTag();
        }
        catch(JspException e){
            throw new InternalErrorException(e);
        }
        
        ButtonComponent rightButtonComponent = new ButtonComponent();
        
        rightButtonComponent.setPageContext(this.pageContext);
        rightButtonComponent.setOutputStream(getOutputStream());
        rightButtonComponent.setActionFormName(actionFormName);
        rightButtonComponent.setResourcesId(UIConstants.DEFAULT_RICH_TEXT_AREA_RESOURCES_ID);
        rightButtonComponent.setResourcesKey("rightButton");
        rightButtonComponent.setStyleClass("rightButton");
        
        try{
            rightButtonComponent.doStartTag();
            rightButtonComponent.doEndTag();
        }
        catch(JspException e){
            throw new InternalErrorException(e);
        }
        
        ButtonComponent justifyButtonComponent = new ButtonComponent();
        
        justifyButtonComponent.setPageContext(this.pageContext);
        justifyButtonComponent.setOutputStream(getOutputStream());
        justifyButtonComponent.setActionFormName(actionFormName);
        justifyButtonComponent.setResourcesId(UIConstants.DEFAULT_RICH_TEXT_AREA_RESOURCES_ID);
        justifyButtonComponent.setResourcesKey("rightButton");
        justifyButtonComponent.setStyleClass("rightButton");
        
        try{
            justifyButtonComponent.doStartTag();
            justifyButtonComponent.doEndTag();
        }
        catch(JspException e){
            throw new InternalErrorException(e);
        }
        
        ButtonComponent unorderedListButtonComponent = new ButtonComponent();
        
        unorderedListButtonComponent.setPageContext(this.pageContext);
        unorderedListButtonComponent.setOutputStream(getOutputStream());
        unorderedListButtonComponent.setActionFormName(actionFormName);
        unorderedListButtonComponent.setResourcesId(UIConstants.DEFAULT_RICH_TEXT_AREA_RESOURCES_ID);
        unorderedListButtonComponent.setResourcesKey("unorderedListButton");
        unorderedListButtonComponent.setStyleClass("unorderedListButton");
        
        try{
            unorderedListButtonComponent.doStartTag();
            unorderedListButtonComponent.doEndTag();
        }
        catch(JspException e){
            throw new InternalErrorException(e);
        }
        
        ButtonComponent orderedListButtonComponent = new ButtonComponent();
        
        orderedListButtonComponent.setPageContext(this.pageContext);
        orderedListButtonComponent.setOutputStream(getOutputStream());
        orderedListButtonComponent.setActionFormName(actionFormName);
        orderedListButtonComponent.setResourcesId(UIConstants.DEFAULT_RICH_TEXT_AREA_RESOURCES_ID);
        orderedListButtonComponent.setResourcesKey("orderedListButton");
        orderedListButtonComponent.setStyleClass("orderedListButton");
        
        try{
            orderedListButtonComponent.doStartTag();
            orderedListButtonComponent.doEndTag();
        }
        catch(JspException e){
            throw new InternalErrorException(e);
        }
        
        ButtonComponent addTabButtonComponent = new ButtonComponent();
        
        addTabButtonComponent.setPageContext(this.pageContext);
        addTabButtonComponent.setOutputStream(getOutputStream());
        addTabButtonComponent.setActionFormName(actionFormName);
        addTabButtonComponent.setResourcesId(UIConstants.DEFAULT_RICH_TEXT_AREA_RESOURCES_ID);
        addTabButtonComponent.setResourcesKey("addTabButton");
        addTabButtonComponent.setStyleClass("addTabButton");
        
        try{
            addTabButtonComponent.doStartTag();
            addTabButtonComponent.doEndTag();
        }
        catch(JspException e){
            throw new InternalErrorException(e);
        }
        
        ButtonComponent removeTabButtonComponent = new ButtonComponent();
        
        removeTabButtonComponent.setPageContext(this.pageContext);
        removeTabButtonComponent.setOutputStream(getOutputStream());
        removeTabButtonComponent.setActionFormName(actionFormName);
        removeTabButtonComponent.setResourcesId(UIConstants.DEFAULT_RICH_TEXT_AREA_RESOURCES_ID);
        removeTabButtonComponent.setResourcesKey("removeTabButton");
        removeTabButtonComponent.setStyleClass("removeTabButton");
        
        try{
            removeTabButtonComponent.doStartTag();
            removeTabButtonComponent.doEndTag();
        }
        catch(JspException e){
            throw new InternalErrorException(e);
        }
        
        ButtonComponent linkButtonComponent = new ButtonComponent();
        
        linkButtonComponent.setPageContext(this.pageContext);
        linkButtonComponent.setOutputStream(getOutputStream());
        linkButtonComponent.setActionFormName(actionFormName);
        linkButtonComponent.setResourcesId(UIConstants.DEFAULT_RICH_TEXT_AREA_RESOURCES_ID);
        linkButtonComponent.setResourcesKey("linkButton");
        linkButtonComponent.setStyleClass("linkButton");
        
        try{
            linkButtonComponent.doStartTag();
            linkButtonComponent.doEndTag();
        }
        catch(JspException e){
            throw new InternalErrorException(e);
        }
        
        ButtonComponent imageButtonComponent = new ButtonComponent();
        
        imageButtonComponent.setPageContext(this.pageContext);
        imageButtonComponent.setOutputStream(getOutputStream());
        imageButtonComponent.setActionFormName(actionFormName);
        imageButtonComponent.setResourcesId(UIConstants.DEFAULT_RICH_TEXT_AREA_RESOURCES_ID);
        imageButtonComponent.setResourcesKey("imageButton");
        imageButtonComponent.setStyleClass("imageButton");
        
        try{
            imageButtonComponent.doStartTag();
            imageButtonComponent.doEndTag();
        }
        catch(JspException e){
            throw new InternalErrorException(e);
        }
        
        ButtonComponent tableButtonComponent = new ButtonComponent();
        
        tableButtonComponent.setPageContext(this.pageContext);
        tableButtonComponent.setOutputStream(getOutputStream());
        tableButtonComponent.setActionFormName(actionFormName);
        tableButtonComponent.setResourcesId(UIConstants.DEFAULT_RICH_TEXT_AREA_RESOURCES_ID);
        tableButtonComponent.setResourcesKey("tableButton");
        tableButtonComponent.setStyleClass("tableButton");
        
        try{
            tableButtonComponent.doStartTag();
            tableButtonComponent.doEndTag();
        }
        catch(JspException e){
            throw new InternalErrorException(e);
        }
        
        ButtonComponent checkButtonComponent = new ButtonComponent();
        
        checkButtonComponent.setPageContext(this.pageContext);
        checkButtonComponent.setOutputStream(getOutputStream());
        checkButtonComponent.setActionFormName(actionFormName);
        checkButtonComponent.setResourcesId(UIConstants.DEFAULT_RICH_TEXT_AREA_RESOURCES_ID);
        checkButtonComponent.setResourcesKey("checkButton");
        checkButtonComponent.setStyleClass("checkButton");
        
        try{
            checkButtonComponent.doStartTag();
            checkButtonComponent.doEndTag();
        }
        catch(JspException e){
            throw new InternalErrorException(e);
        }
        
        ButtonComponent cutButtonComponent = new ButtonComponent();
        
        cutButtonComponent.setPageContext(this.pageContext);
        cutButtonComponent.setOutputStream(getOutputStream());
        cutButtonComponent.setActionFormName(actionFormName);
        cutButtonComponent.setResourcesId(UIConstants.DEFAULT_RICH_TEXT_AREA_RESOURCES_ID);
        cutButtonComponent.setResourcesKey("cutButton");
        cutButtonComponent.setStyleClass("cutButton");
        
        try{
            cutButtonComponent.doStartTag();
            cutButtonComponent.doEndTag();
        }
        catch(JspException e){
            throw new InternalErrorException(e);
        }
        
        ButtonComponent copyButtonComponent = new ButtonComponent();
        
        copyButtonComponent.setPageContext(this.pageContext);
        copyButtonComponent.setOutputStream(getOutputStream());
        copyButtonComponent.setActionFormName(actionFormName);
        copyButtonComponent.setResourcesId(UIConstants.DEFAULT_RICH_TEXT_AREA_RESOURCES_ID);
        copyButtonComponent.setResourcesKey("copyButton");
        copyButtonComponent.setStyleClass("copyButton");
        
        try{
            copyButtonComponent.doStartTag();
            copyButtonComponent.doEndTag();
        }
        catch(JspException e){
            throw new InternalErrorException(e);
        }
        
        ButtonComponent pasteButtonComponent = new ButtonComponent();
        
        pasteButtonComponent.setPageContext(this.pageContext);
        pasteButtonComponent.setOutputStream(getOutputStream());
        pasteButtonComponent.setActionFormName(actionFormName);
        pasteButtonComponent.setResourcesId(UIConstants.DEFAULT_RICH_TEXT_AREA_RESOURCES_ID);
        pasteButtonComponent.setResourcesKey("pasteButton");
        pasteButtonComponent.setStyleClass("pasteButton");
        
        try{
            pasteButtonComponent.doStartTag();
            pasteButtonComponent.doEndTag();
        }
        catch(JspException e){
            throw new InternalErrorException(e);
        }
        
        ButtonComponent undoButtonComponent = new ButtonComponent();
        
        undoButtonComponent.setPageContext(this.pageContext);
        undoButtonComponent.setOutputStream(getOutputStream());
        undoButtonComponent.setActionFormName(actionFormName);
        undoButtonComponent.setResourcesId(UIConstants.DEFAULT_RICH_TEXT_AREA_RESOURCES_ID);
        undoButtonComponent.setResourcesKey("undoButton");
        undoButtonComponent.setStyleClass("undoButton");
        
        try{
            undoButtonComponent.doStartTag();
            undoButtonComponent.doEndTag();
        }
        catch(JspException e){
            throw new InternalErrorException(e);
        }
        
        ButtonComponent redoButtonComponent = new ButtonComponent();
        
        redoButtonComponent.setPageContext(this.pageContext);
        redoButtonComponent.setOutputStream(getOutputStream());
        redoButtonComponent.setActionFormName(actionFormName);
        redoButtonComponent.setResourcesId(UIConstants.DEFAULT_RICH_TEXT_AREA_RESOURCES_ID);
        redoButtonComponent.setResourcesKey("redoButton");
        redoButtonComponent.setStyleClass("redoButton");
        
        try{
            redoButtonComponent.doStartTag();
            redoButtonComponent.doEndTag();
        }
        catch(JspException e){
            throw new InternalErrorException(e);
        }
        
        println("</td>");
        println("<td>");
        
        Collection<String> fontsName = Arrays.asList(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames());
        StringBuilder nameBuffer = new StringBuilder();
        
        nameBuffer.append(name);
        nameBuffer.append(".");
        nameBuffer.append(UIConstants.RICH_TEXT_AREA_TOOLBAR_FONT_NAME_ATTRIBUTE_ID);
        
        ListPropertyComponent fontNameComponent = new ListPropertyComponent();
        
        fontNameComponent.setPageContext(this.pageContext);
        fontNameComponent.setOutputStream(getOutputStream());
        fontNameComponent.setShowFirstOption(false);
        fontNameComponent.setName(nameBuffer.toString());
        fontNameComponent.setValue(uiController.getRichTextAreaFontName(name));
        fontNameComponent.setLabelStyleClass(UIConstants.DEFAULT_RICH_TEXT_AREA_TOOLBAR_LABEL_STYLE_CLASS);
        fontNameComponent.setStyleClass(UIConstants.DEFAULT_RICH_TEXT_AREA_TOOLBAR_FONT_NAME_STYLE_CLASS);
        fontNameComponent.setResourcesId(UIConstants.DEFAULT_RICH_TEXT_AREA_RESOURCES_ID);
        fontNameComponent.setResourcesKey(UIConstants.DEFAULT_RICH_TEXT_AREA_TOOLBAR_FONT_NAME_ID);
        fontNameComponent.setDatasetValues(fontsName);
        
        try{
            fontNameComponent.doStartTag();
            fontNameComponent.doEndTag();
        }
        catch(JspException e){
            throw new InternalErrorException(e);
        }
        
        println("</td>");
        println("<td>");
        
        nameBuffer.delete(0, nameBuffer.length());
        nameBuffer.append(name);
        nameBuffer.append(".");
        nameBuffer.append(UIConstants.RICH_TEXT_AREA_TOOLBAR_FONT_SIZE_ATTRIBUTE_ID);
        
        SpinnerComponent fontSizeComponent = new SpinnerComponent();
        
        fontSizeComponent.setPageContext(this.pageContext);
        fontSizeComponent.setOutputStream(getOutputStream());
        fontSizeComponent.setName(nameBuffer.toString());
        fontSizeComponent.setValue(uiController.getRichTextAreaFontSize(name));
        fontSizeComponent.setLabelStyleClass(UIConstants.DEFAULT_RICH_TEXT_AREA_TOOLBAR_LABEL_STYLE_CLASS);
        fontSizeComponent.setStyleClass(UIConstants.DEFAULT_RICH_TEXT_AREA_TOOLBAR_FONT_SIZE_STYLE_CLASS);
        fontSizeComponent.setResourcesId(UIConstants.DEFAULT_RICH_TEXT_AREA_RESOURCES_ID);
        fontSizeComponent.setResourcesKey(UIConstants.DEFAULT_RICH_TEXT_AREA_TOOLBAR_FONT_SIZE_ID);
        
        try{
            fontSizeComponent.doStartTag();
            fontSizeComponent.doEndTag();
        }
        catch(JspException e){
            throw new InternalErrorException(e);
        }
        
        println("</td>");
        println("<td>");
        
        nameBuffer.delete(0, nameBuffer.length());
        nameBuffer.append(name);
        nameBuffer.append(".");
        nameBuffer.append(UIConstants.RICH_TEXT_AREA_TOOLBAR_FONT_COLOR_ATTRIBUTE_ID);
        
        ColorPickerPropertyComponent fontColorComponent = new ColorPickerPropertyComponent();
        
        fontColorComponent.setPageContext(this.pageContext);
        fontColorComponent.setOutputStream(getOutputStream());
        fontColorComponent.setName(nameBuffer.toString());
        fontColorComponent.setValue(uiController.getRichTextAreaFontColor(name));
        fontColorComponent.setLabelStyleClass(UIConstants.DEFAULT_RICH_TEXT_AREA_TOOLBAR_LABEL_STYLE_CLASS);
        fontColorComponent.setStyleClass(UIConstants.DEFAULT_RICH_TEXT_AREA_TOOLBAR_FONT_COLOR_STYLE_CLASS);
        fontColorComponent.setResourcesId(UIConstants.DEFAULT_RICH_TEXT_AREA_RESOURCES_ID);
        fontColorComponent.setResourcesKey(UIConstants.DEFAULT_RICH_TEXT_AREA_TOOLBAR_FONT_COLOR_ID);
        
        try{
            fontColorComponent.doStartTag();
            fontColorComponent.doEndTag();
        }
        catch(JspException e){
            throw new InternalErrorException(e);
        }
        
        println("</td>");
        println("<td>");
        
        ColorPickerPropertyComponent backgroundColorComponent = new ColorPickerPropertyComponent();
        
        backgroundColorComponent.setPageContext(this.pageContext);
        backgroundColorComponent.setOutputStream(getOutputStream());
        backgroundColorComponent.setName(nameBuffer.toString());
        backgroundColorComponent.setValue(uiController.getRichTextAreaBackgroundColor(name));
        backgroundColorComponent.setLabelStyleClass(UIConstants.DEFAULT_RICH_TEXT_AREA_TOOLBAR_LABEL_STYLE_CLASS);
        backgroundColorComponent.setStyleClass(UIConstants.DEFAULT_RICH_TEXT_AREA_TOOLBAR_BACKGROUND_COLOR_STYLE_CLASS);
        backgroundColorComponent.setResourcesId(UIConstants.DEFAULT_RICH_TEXT_AREA_RESOURCES_ID);
        backgroundColorComponent.setResourcesKey(UIConstants.DEFAULT_RICH_TEXT_AREA_TOOLBAR_BACKGROUND_COLOR_ID);
        
        try{
            backgroundColorComponent.doStartTag();
            backgroundColorComponent.doEndTag();
        }
        catch(JspException e){
            throw new InternalErrorException(e);
        }
        
        println("</td>");
        println("</tr>");
        println("</table>");
        
        println("</td>");
        println("</tr>");
        println("</table>");
        
        println("</div>");
    }

    @Override
    protected void renderBody() throws InternalErrorException{
        renderValue();
    }

    @Override
    protected void renderValue() throws InternalErrorException{
        String formattedValue = getFormattedValue();
        
        if(formattedValue != null && !formattedValue.isEmpty())
            println(formattedValue);
    }

    @Override
    protected void renderClose() throws InternalErrorException{
        PropertyInfo propertyInfo = getPropertyInfo();
        String actionFormName = getActionFormName();
        String name = getName();
        
        if(propertyInfo != null && actionFormName != null && !actionFormName.isEmpty() && name != null && !name.isEmpty()){
            println("</div>");
            
            StringBuilder content = new StringBuilder();
            
            content.append("initializeRichTextArea(\"");
            content.append(name);
            content.append("\");");
            
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
            
            TextAreaPropertyComponent textAreaPropertyComponent = new TextAreaPropertyComponent();
            
            textAreaPropertyComponent.setPageContext(this.pageContext);
            textAreaPropertyComponent.setOutputStream(getOutputStream());
            textAreaPropertyComponent.setActionFormName(actionFormName);
            textAreaPropertyComponent.setPropertyInfo(propertyInfo);
            textAreaPropertyComponent.setShowLabel(false);
            textAreaPropertyComponent.setName(name);
            textAreaPropertyComponent.setValue(getValue());
            
            content.delete(0, content.length());
            content.append("display: ");
            content.append(VisibilityType.NONE);
            content.append(";");
            
            textAreaPropertyComponent.setStyle(content.toString());
            
            try{
                textAreaPropertyComponent.doStartTag();
                textAreaPropertyComponent.doEndTag();
            }
            catch(JspException e){
                throw new InternalErrorException(e);
            }
        }
        
        super.renderClose();
    }
}