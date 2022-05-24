package br.com.concepting.framework.ui.components;

import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.ui.constants.UIConstants;
import br.com.concepting.framework.util.NumberUtil;
import br.com.concepting.framework.util.PropertyUtil;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.types.ComponentType;

/**
 * Class that defines the progress bar component.
 *
 * @author fvilarinho
 * @since 1.0.0
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
public class ProgressBarComponent extends BasePropertyComponent{
    private static final long serialVersionUID = -583105661110523140L;

    private int currentWidth = 0;
    private double warningValue = UIConstants.DEFAULT_PROGERSS_BAR_WARNING_VALUE;
    private double criticalValue = UIConstants.DEFAULT_PROGRESS_BAR_CRITICAL_VALUE;
    private double percentage = UIConstants.DEFAULT_PROGRESS_BAR_MAXIMUM_VALUE;
    private int maximumWidth = UIConstants.DEFAULT_PROGRESS_BAR_WIDTH;
    
    /**
     * Returns the percentage of the component.
     *
     * @return Numeric value that contains the percentage.
     */
    public double getPercentage(){
        return this.percentage;
    }
    
    /**
     * Defines the percentage of the component.
     *
     * @param percentage Numeric value that contains the percentage.
     */
    public void setPercentage(double percentage){
        this.percentage = percentage;
    }
    
    /**
     * Returns the current width of the component.
     *
     * @return Numeric value that contains the current width.
     */
    protected int getCurrentWidth(){
        return this.currentWidth;
    }
    
    /**
     * Defines the current width of the component.
     *
     * @param currentWidth Numeric value that contains the current width.
     */
    protected void setCurrentWidth(int currentWidth){
        this.currentWidth = currentWidth;
    }
    
    /**
     * Returns the maximum width of the component.
     *
     * @return Numeric value that contains the maximum width.
     */
    protected int getMaximumWidth(){
        return this.maximumWidth;
    }
    
    /**
     * Defines the maximum width of the component.
     *
     * @param maximumWidth Numeric value that contains the maximum width.
     */
    protected void setMaximumWidth(int maximumWidth){
        this.maximumWidth = maximumWidth;
    }
    
    /**
     * Returns the value of the warning threshold.
     *
     * @return Numeric value that contains the value of the threshold.
     */
    public double getWarningValue(){
        return this.warningValue;
    }
    
    /**
     * Defines the value of the warning threshold.
     *
     * @param warningValue Numeric value that contains the value of the
     * threshold.
     */
    public void setWarningValue(double warningValue){
        this.warningValue = warningValue;
    }
    
    /**
     * Returns the value of the critical threshold.
     *
     * @return Numeric value that contains the value of the threshold.
     */
    public double getCriticalValue(){
        return this.criticalValue;
    }
    
    /**
     * Defines the value of the critical threshold.
     *
     * @param criticalValue Numeric value that contains the value of the
     * threshold.
     */
    public void setCriticalValue(double criticalValue){
        this.criticalValue = criticalValue;
    }

    @Override
    protected String getFormattedValue() throws InternalErrorException{
        StringBuilder result = new StringBuilder();

        result.append(NumberUtil.format(this.percentage, NumberUtil.getDefaultPrecision(this.percentage), getCurrentLanguage()));
        result.append(" %");

        return result.toString();
    }

    @Override
    protected void buildRestrictions() throws InternalErrorException{
        setMinimumValue(0);
        
        Number maximumValue = getMaximumValue();
        
        if(maximumValue == null){
            maximumValue = UIConstants.DEFAULT_PROGRESS_BAR_MAXIMUM_VALUE;
            
            setMaximumValue(maximumValue);
        }
        
        Number numberValue = getValue();
        
        try{
            this.percentage = PropertyUtil.convertTo(numberValue, Double.class);
            this.percentage = (this.percentage / PropertyUtil.convertTo(maximumValue, Double.class)) * 100d;
        }
        catch(Throwable e){
            this.percentage = 0d;
        }
        
        if(this.percentage > 100)
            this.percentage = 100d;
        else if(this.percentage < 0)
            this.percentage = 0d;
        
        super.buildRestrictions();
    }

    @Override
    protected void buildStyleClass() throws InternalErrorException{
        if(getStyleClass() == null || getStyleClass().length() == 0)
            setStyleClass(UIConstants.DEFAULT_PROGRESS_BAR_TEXT_STYLE_CLASS);
        
        super.buildStyleClass();
    }

    @Override
    protected void buildDimensions() throws InternalErrorException{
        String width = getWidth();
        
        if(width == null || width.length() == 0)
            this.maximumWidth = UIConstants.DEFAULT_PROGRESS_BAR_WIDTH;
        else{
            width = StringUtil.replaceAll(width.toLowerCase(), "px", "");
            width = StringUtil.replaceAll(width, "%", "");
            
            try{
                this.maximumWidth = NumberUtil.parseInt(width);
            }
            catch(Throwable e){
                this.maximumWidth = UIConstants.DEFAULT_PROGRESS_BAR_WIDTH;
            }
        }
        
        this.currentWidth = (int) (this.maximumWidth * (this.percentage / 100));
    }

    @Override
    protected void initialize() throws InternalErrorException{
        setComponentType(ComponentType.PROGRESS_BAR);
        
        super.initialize();
    }

    @Override
    protected boolean renderRequiredMark(){
        return false;
    }

    @Override
    protected void renderBody() throws InternalErrorException{
        boolean hasInvalidPropertyDefinition = hasInvalidPropertyDefinition();
        
        if( hasInvalidPropertyDefinition)
            super.renderInvalidPropertyMessage();
        else{
            print("<table class=\"");
            print(UIConstants.DEFAULT_CONTENT_PANEL_STYLE_CLASS);
            println("\">");
            println("<tr>");
            println("<td>");
            print("<table class=\"");
            print(UIConstants.DEFAULT_PROGRESS_BAR_STYLE_CLASS);
            println("\">");
            println("<tr>");
            
            String width = getWidth();
            String symbol = (width != null && width.toLowerCase().contains("%") ? "%" : "px");

            if(this.percentage > 0){
                print("<td class=\"");
                
                if(this.warningValue > 0){
                    if(this.percentage < warningValue)
                        print(UIConstants.DEFAULT_PROGRESS_BAR_NORMAL_STYLE_CLASS);
                    else{
                        if(this.criticalValue > 0){
                            if(this.percentage < this.criticalValue)
                                print(UIConstants.DEFAULT_PROGRESS_BAR_WARNING_STYLE_CLASS);
                            else
                                print(UIConstants.DEFAULT_PROGRESS_BAR_CRITICAL_STYLE_CLASS);
                        }
                        else
                            print(UIConstants.DEFAULT_PROGRESS_BAR_WARNING_STYLE_CLASS);
                    }
                }
                else{
                    if(this.criticalValue > 0){
                        if(this.percentage < this.criticalValue)
                            print(UIConstants.DEFAULT_PROGRESS_BAR_NORMAL_STYLE_CLASS);
                        else
                            print(UIConstants.DEFAULT_PROGRESS_BAR_CRITICAL_STYLE_CLASS);
                    }
                    else
                        print(UIConstants.DEFAULT_PROGRESS_BAR_GENERAL_STYLE_CLASS);
                }
                
                print("\" style=\"width: ");
                print(this.currentWidth);
                print(symbol);
                println(";\"></td>");
            }
            
            if(this.percentage < 100){
                print("<td class=\"");
                print(UIConstants.DEFAULT_PROGRESS_BAR_EMPTY_STYLE_CLASS);
                print("\" style=\"width: ");
                print(this.maximumWidth - this.currentWidth);
                print(symbol);
                println(";\"></td>");
            }
            
            println("</tr>");
            println("</table>");
            println("</td>");
            
            print("<td class=\"");
            print(getStyleClass());
            print("\"");
            
            String style = getStyle();
            
            if(style != null && style.length() > 0){
                print(" style=\"");
                print(style);
                
                if(!style.endsWith(";"))
                    print(";");
                
                print("\"");
            }
            
            print(">&nbsp;");
            print(getFormattedValue());
            println("&nbsp;</td>");
            println("</tr>");
            println("</table>");
        }
    }

    @Override
    protected void clearAttributes() throws InternalErrorException{
        super.clearAttributes();

        setCurrentWidth(0);
        setWarningValue(UIConstants.DEFAULT_PROGERSS_BAR_WARNING_VALUE);
        setCriticalValue(UIConstants.DEFAULT_PROGRESS_BAR_CRITICAL_VALUE);
        setMaximumWidth(UIConstants.DEFAULT_PROGRESS_BAR_WIDTH);
        setPercentage(UIConstants.DEFAULT_PROGRESS_BAR_MAXIMUM_VALUE);
    }
}