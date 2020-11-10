package br.com.innovativethinking.framework.ui.components;

import br.com.innovativethinking.framework.constants.Constants;
import br.com.innovativethinking.framework.exceptions.InternalErrorException;
import br.com.innovativethinking.framework.ui.constants.UIConstants;
import br.com.innovativethinking.framework.util.NumberUtil;
import br.com.innovativethinking.framework.util.PropertyUtil;
import br.com.innovativethinking.framework.util.StringUtil;
import br.com.innovativethinking.framework.util.types.ComponentType;

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

	private Double  warningValue  = null;
	private Double  criticalValue = null;
	private Double  percentage    = null;
	private Integer currentWidth  = null;
	private Integer maximumWidth  = null;

	/**
	 * Returns the percentage of the component.
	 *
	 * @return Numeric value that contains the percentage.
	 */
	public Double getPercentage(){
		return this.percentage;
	}

	/**
	 * Defines the percentage of the component.
	 *
	 * @param percentage Numeric value that contains the percentage.
	 */
	public void setPercentage(Double percentage){
		this.percentage = percentage;
	}

	/**
	 * Returns the current width of the component.
	 * 
	 * @return Numeric value that contains the current width.
	 */
	protected Integer getCurrentWidth(){
		return this.currentWidth;
	}

	/**
	 * Defines the current width of the component.
	 * 
	 * @param currentWidth Numeric value that contains the current width.
	 */
	protected void setCurrentWidth(Integer currentWidth){
		this.currentWidth = currentWidth;
	}

	/**
	 * Returns the maximum width of the component.
	 * 
	 * @return Numeric value that contains the maximum width.
	 */
	protected Integer getMaximumWidth(){
		return this.maximumWidth;
	}

	/**
	 * Defines the maximum width of the component.
	 * 
	 * @param maximumWidth Numeric value that contains the maximum width.
	 */
	protected void setMaximumWidth(Integer maximumWidth){
		this.maximumWidth = maximumWidth;
	}

	/**
	 * Returns the value of the warning threshold.
	 * 
	 * @return Numeric value that contains the value of the threshold.
	 */
	public Double getWarningValue(){
		return this.warningValue;
	}

	/**
	 * Defines the value of the warning threshold.
	 * 
	 * @param warningValue Numeric value that contains the value of the
	 * threshold.
	 */
	public void setWarningValue(Double warningValue){
		this.warningValue = warningValue;
	}

	/**
	 * Returns the value of the critical threshold.
	 * 
	 * @return Numeric value that contains the value of the threshold.
	 */
	public Double getCriticalValue(){
		return this.criticalValue;
	}

	/**
	 * Defines the value of the critical threshold.
	 * 
	 * @param criticalValue Numeric value that contains the value of the
	 * threshold.
	 */
	public void setCriticalValue(Double criticalValue){
		this.criticalValue = criticalValue;
	}

	/**
	 * @see br.com.innovativethinking.framework.ui.components.BasePropertyComponent#getFormattedValue()
	 */
	protected String getFormattedValue() throws InternalErrorException{
		if(this.percentage != null){
			StringBuilder result = new StringBuilder();

			result.append(NumberUtil.format(this.percentage, Constants.DEFAULT_DECIMAL_PRECISION, getCurrentLanguage()));
			result.append(" %");

			return result.toString();
		}

		return null;
	}

	/**
	 * @see br.com.innovativethinking.framework.ui.components.TextPropertyComponent#buildRestrictions()
	 */
	protected void buildRestrictions() throws InternalErrorException{
		setMinimumValue(0);

		Long maximumValue = getMaximumValue();

		if(maximumValue == null){
			maximumValue = UIConstants.DEFAULT_PROGRESS_BAR_MAXIMUM_VALUE;

			setMaximumValue(maximumValue);
		}

		Number numberValue = (Number)getValue();
		
		try{
			this.percentage = PropertyUtil.convertTo(numberValue, Double.class);
			this.percentage = (this.percentage / maximumValue) * 100d;
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

	/**
	 * @see br.com.innovativethinking.framework.ui.components.BaseActionFormComponent#buildStyleClass()
	 */
	protected void buildStyleClass() throws InternalErrorException{
		if(getStyleClass() == null || getStyleClass().length() == 0)
			setStyleClass(UIConstants.DEFAULT_PROGRESS_BAR_TEXT_STYLE_CLASS);
		
		super.buildStyleClass();
	}

	/**
	 * @see br.com.innovativethinking.framework.ui.components.BaseComponent#buildDimensions()
	 */
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

		this.currentWidth = (int)(this.maximumWidth * (this.percentage / 100));
	}

	/**
	 * @see br.com.innovativethinking.framework.ui.components.BasePropertyComponent#initialize()
	 */
	protected void initialize() throws InternalErrorException{
		setComponentType(ComponentType.PROGRESS_BAR);

		super.initialize();
	}

	/**
	 * @see br.com.innovativethinking.framework.ui.components.BasePropertyComponent#renderRequiredMark()
	 */
	protected Boolean renderRequiredMark(){
		return false;
	}

	/**
	 * @see br.com.innovativethinking.framework.ui.components.BasePropertyComponent#renderBody()
	 */
	protected void renderBody() throws InternalErrorException{
		Boolean hasInvalidPropertyDefinition = hasInvalidPropertyDefinition();

		if(hasInvalidPropertyDefinition != null && hasInvalidPropertyDefinition)
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
			Double warningValue = getWarningValue();
			Double criticalValue = getCriticalValue();

			if(this.percentage != null && this.percentage > 0){
				print("<td class=\"");
	
				if(warningValue != null && warningValue > 0){
					if(this.percentage < warningValue)
						print(UIConstants.DEFAULT_PROGRESS_BAR_NORMAL_STYLE_CLASS);
					else{
						if(criticalValue != null && criticalValue > 0){
							if(this.percentage < criticalValue)
								print(UIConstants.DEFAULT_PROGRESS_BAR_WARNING_STYLE_CLASS);
							else
								print(UIConstants.DEFAULT_PROGRESS_BAR_CRITICAL_STYLE_CLASS);
						}
						else
							print(UIConstants.DEFAULT_PROGRESS_BAR_WARNING_STYLE_CLASS);
					}
				}
				else{
					if(criticalValue != null && criticalValue > 0){
						if(this.percentage < criticalValue)
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
			
			if(this.percentage != null && this.percentage < 100){
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

	/**
	 * @see br.com.innovativethinking.framework.ui.components.BasePropertyComponent#clearAttributes()
	 */
	protected void clearAttributes() throws InternalErrorException{
		super.clearAttributes();

		setWarningValue(null);
		setCriticalValue(null);
		setMaximumWidth(null);
		setCurrentWidth(null);
		setPercentage(null);
	}
}