package br.com.concepting.framework.ui.components;

import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.ui.constants.UIConstants;
import br.com.concepting.framework.util.NumberUtil;
import br.com.concepting.framework.util.types.ComponentType;
import br.com.concepting.framework.util.types.PositionType;

import javax.servlet.jsp.JspException;
import java.io.Serial;
import java.text.DecimalFormatSymbols;

/**
 * Class that defines the spinner component.
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
public class SpinnerComponent extends TextPropertyComponent{
    @Serial
    private static final long serialVersionUID = 2739899668872823020L;
    
    private int step = 1;
    
    /**
     * Returns the step of the component.
     *
     * @return Numeric value that defines the step.
     */
    public int getStep(){
        return this.step;
    }
    
    /**
     * Defines the step of the component.
     *
     * @param step Numeric value that defines the step.
     */
    public void setStep(int step){
        this.step = step;
    }

    @Override
    protected void initialize() throws InternalErrorException{
        setComponentType(ComponentType.SPINNER);
        
        super.initialize();
    }
    
    /**
     * Renders the controls of the component.
     *
     * @throws InternalErrorException Occurs when was not possible to render.
     */
    protected void renderControls() throws InternalErrorException{
        println("<td width=\"1\">");
        
        AddSpinnerButtonComponent addButtonComponent = new AddSpinnerButtonComponent(this);
        
        try{
            addButtonComponent.doStartTag();
            addButtonComponent.doEndTag();
        }
        catch(JspException e){
            throw new InternalErrorException(e);
        }
        
        println("</td>");
        println("<td width=\"1\">");
        
        SubtractSpinnerButtonComponent subtractButtonComponent = new SubtractSpinnerButtonComponent(this);
        
        try{
            subtractButtonComponent.doStartTag();
            subtractButtonComponent.doEndTag();
        }
        catch(JspException e){
            throw new InternalErrorException(e);
        }
        
        println("</td>");
    }

    @Override
    protected void renderOpen() throws InternalErrorException{
        super.renderOpen();
        
        boolean hasInvalidDefinition = hasInvalidDefinition();
        
        if(!hasInvalidDefinition){
            PositionType labelPositionType = getLabelPositionType();
            
            if(labelPositionType == PositionType.TOP || labelPositionType == PositionType.BOTTOM){
                println("<table>");
                println("<tr>");
                println("<td>");
            }
            
            print("<table class=\"");
            print(UIConstants.DEFAULT_CONTENT_PANEL_STYLE_CLASS);
            println("\">");
            println("<tr>");
            println("<td>");
        }
    }

    @Override
    protected void renderClose() throws InternalErrorException{
        boolean hasInvalidDefinition = hasInvalidDefinition();
        
        if(!hasInvalidDefinition){
            println("</td>");
            
            println("<td width=\"5\"></td>");
            
            renderControls();
            
            println("</tr>");
            println("</table>");
            
            PositionType labelPositionType = getLabelPositionType();
            
            if(labelPositionType == PositionType.TOP || labelPositionType == PositionType.BOTTOM){
                println("</td>");
                println("</tr>");
                println("</table>");
            }
        }
        
        super.renderClose();
    }

    @Override
    protected void clearAttributes() throws InternalErrorException{
        super.clearAttributes();
        
        setStep(1);
    }
    
    /**
     * Class that defines the add button component.
     *
     * @author fvilarinho
     * @since 3.0.0
     */
    private class AddSpinnerButtonComponent extends ButtonComponent{
        @Serial
        private static final long serialVersionUID = -7170110437405939054L;
        
        /**
         * Constructor - Defines the component.
         *
         * @param spinnerComponent Instance that contains the calendar component.
         */
        public AddSpinnerButtonComponent(SpinnerComponent spinnerComponent){
            super();
            
            if(spinnerComponent != null){
                setPageContext(spinnerComponent.getPageContext());
                setOutputStream(spinnerComponent.getOutputStream());
                setActionFormName(spinnerComponent.getActionFormName());
                setOnChange(spinnerComponent.getOnChange());
                setOnBlur(spinnerComponent.getOnBlur());
                setOnClick(spinnerComponent.getOnClick());
                setOnFocus(spinnerComponent.getOnFocus());
                setOnKeyDown(spinnerComponent.getOnKeyDown());
                setOnKeyUp(spinnerComponent.getOnKeyUp());
                setOnKeyPress(spinnerComponent.getOnKeyPress());
                setOnMouseOut(spinnerComponent.getOnMouseOut());
                setOnMouseOver(spinnerComponent.getOnMouseOver());
                setParent(spinnerComponent);
            }
        }

        @Override
        protected void buildEvents() throws InternalErrorException{
            SpinnerComponent spinnerComponent = (SpinnerComponent) getParent();
            String name = (spinnerComponent != null ? spinnerComponent.getName() : null);
            
            if(name != null && !name.isEmpty()){
                Number minimumValue = spinnerComponent.getMinimumValue();
                Number maximumValue = spinnerComponent.getMaximumValue();
                int step = spinnerComponent.getStep();
                DecimalFormatSymbols symbols = NumberUtil.getFormatSymbols(getCurrentLanguage());
                boolean useGroupSeparator = spinnerComponent.useAdditionalFormatting();
                int precision = spinnerComponent.getPrecision();
                StringBuilder onClick = new StringBuilder();
                
                onClick.append("addSpinnerValue('");
                onClick.append(name);
                onClick.append("', ");
                onClick.append((minimumValue != null ? minimumValue.toString() : Constants.DEFAULT_NULL_ID));
                onClick.append(", ");
                onClick.append((maximumValue != null ? maximumValue.toString() : Constants.DEFAULT_NULL_ID));
                onClick.append(", ");
                onClick.append(useGroupSeparator);
                onClick.append(", '");
                onClick.append(symbols.getGroupingSeparator());
                onClick.append("', ");
                onClick.append(precision);
                onClick.append(", '");
                onClick.append(symbols.getDecimalSeparator());
                onClick.append("', ");
                onClick.append(step);
                onClick.append(");");
                
                setOnClick(onClick.toString());
                
                super.buildEvents();
            }
        }

        @Override
        protected void buildResources() throws InternalErrorException{
            setResourcesId(UIConstants.DEFAULT_SPINNER_RESOURCES_ID);
            setResourcesKey(UIConstants.DEFAULT_SPINNER_ADD_BUTTON_ID);
            
            super.buildResources();
        }

        @Override
        protected void buildStyleClass() throws InternalErrorException{
            setStyleClass(UIConstants.DEFAULT_SPINNER_ADD_BUTTON_STYLE_CLASS);
            
            super.buildStyleClass();
        }
    }
    
    /**
     * Class that defines the subtract button component.
     *
     * @author fvilarinho
     * @since 3.0.0
     */
    private class SubtractSpinnerButtonComponent extends ButtonComponent{
        @Serial
        private static final long serialVersionUID = -7170110437405939054L;
        
        /**
         * Constructor - Defines the component.
         *
         * @param spinnerComponent Instance that contains the calendar component.
         */
        public SubtractSpinnerButtonComponent(SpinnerComponent spinnerComponent){
            super();
            
            if(spinnerComponent != null){
                setPageContext(spinnerComponent.getPageContext());
                setOutputStream(spinnerComponent.getOutputStream());
                setActionFormName(spinnerComponent.getActionFormName());
                setOnChange(spinnerComponent.getOnChange());
                setOnBlur(spinnerComponent.getOnBlur());
                setOnClick(spinnerComponent.getOnClick());
                setOnFocus(spinnerComponent.getOnFocus());
                setOnKeyDown(spinnerComponent.getOnKeyDown());
                setOnKeyUp(spinnerComponent.getOnKeyUp());
                setOnKeyPress(spinnerComponent.getOnKeyPress());
                setOnMouseOut(spinnerComponent.getOnMouseOut());
                setOnMouseOver(spinnerComponent.getOnMouseOver());
                setParent(spinnerComponent);
            }
        }

        @Override
        protected void buildEvents() throws InternalErrorException{
            SpinnerComponent spinnerComponent = (SpinnerComponent) getParent();
            String name = (spinnerComponent != null ? spinnerComponent.getName() : null);
            
            if(name != null && !name.isEmpty()){
                Number minimumValue = spinnerComponent.getMinimumValue();
                Number maximumValue = spinnerComponent.getMaximumValue();
                int step = spinnerComponent.getStep();
                DecimalFormatSymbols symbols = NumberUtil.getFormatSymbols(getCurrentLanguage());
                boolean useGroupSeparator = spinnerComponent.useAdditionalFormatting();
                int precision = spinnerComponent.getPrecision();
                StringBuilder onClick = new StringBuilder();
                
                onClick.append("subtractSpinnerValue('");
                onClick.append(name);
                onClick.append("', ");
                onClick.append((minimumValue != null ? minimumValue.toString() : Constants.DEFAULT_NULL_ID));
                onClick.append(", ");
                onClick.append((maximumValue != null ? maximumValue.toString() : Constants.DEFAULT_NULL_ID));
                onClick.append(", ");
                onClick.append(useGroupSeparator);
                onClick.append(", '");
                onClick.append(symbols.getGroupingSeparator());
                onClick.append("', ");
                onClick.append(precision);
                onClick.append(", '");
                onClick.append(symbols.getDecimalSeparator());
                onClick.append("', ");
                onClick.append(step);
                onClick.append(");");
                
                setOnClick(onClick.toString());
                
                super.buildEvents();
            }
        }

        @Override
        protected void buildResources() throws InternalErrorException{
            setResourcesId(UIConstants.DEFAULT_SPINNER_RESOURCES_ID);
            setResourcesKey(UIConstants.DEFAULT_SPINNER_SUBTRACT_BUTTON_ID);
            
            super.buildResources();
        }

        @Override
        protected void buildStyleClass() throws InternalErrorException{
            setStyleClass(UIConstants.DEFAULT_SPINNER_SUBTRACT_BUTTON_STYLE_CLASS);
            
            super.buildStyleClass();
        }
    }
}