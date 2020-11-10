package br.com.concepting.framework.ui.components;

import java.awt.Color;

import javax.servlet.jsp.JspException;

import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.ui.constants.UIConstants;
import br.com.concepting.framework.util.ColorUtil;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.helpers.PropertyInfo;
import br.com.concepting.framework.util.types.AlignmentType;
import br.com.concepting.framework.util.types.ComponentType;

/**
 * Class that defines the color picker component.
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
public class ColorPickerPropertyComponent extends BasePropertyComponent{
	private static final long serialVersionUID = -3503305568245070969L;

	private Boolean showInDialog    = null;
	private String  thumbnailWidth  = null;
	private String  thumbnailHeight = null;

	/**
	 * Returns the width for the color thumbnail.
	 * 
	 * @return String that contains the width.
	 */
	public String getThumbnailWidth(){
		return this.thumbnailWidth;
	}

	/**
	 * Defines the width for the color thumbnail.
	 * 
	 * @param thumbnailWidth String that contains the width.
	 */
	public void setThumbnailWidth(String thumbnailWidth){
		this.thumbnailWidth = StringUtil.trim(thumbnailWidth);
	}

	/**
	 * Returns the height for the color thumbnail.
	 * 
	 * @return String that contains the height.
	 */
	public String getThumbnailHeight(){
		return this.thumbnailHeight;
	}

	/**
	 * Defines the height for the color thumbnail.
	 * 
	 * @param thumbnailHeight String that contains the height.
	 */
	public void setThumbnailHeight(String thumbnailHeight){
		this.thumbnailHeight = StringUtil.trim(thumbnailHeight);
	}

	/**
	 * Indicates if component should be rendered as a dialog box.
	 * 
	 * @return True/False.
	 */
	public Boolean showInDialog(){
		return this.showInDialog;
	}

	/**
	 * Indicates if component should be rendered as a dialog box.
	 * 
	 * @return True/False.
	 */
	public Boolean getShowInDialog(){
		return showInDialog();
	}

	/**
	 * Defines if component should be rendered as a dialog box.
	 * 
	 * @param showInDialog True/False.
	 */
	public void setShowInDialog(Boolean showInDialog){
		this.showInDialog = showInDialog;
	}

	/**
	 * @see br.com.concepting.framework.ui.components.BasePropertyComponent#buildEvents()
	 */
	protected void buildEvents() throws InternalErrorException{
		String name = getName();

		if(name == null || name.length() == 0)
			return;

		String currentOnChange = getOnChange();
		StringBuilder onChange = new StringBuilder();

		onChange.append("changeColorPickerThumbnail('");
		onChange.append(name);
		onChange.append("');");

		if(currentOnChange != null && currentOnChange.length() > 0){
			onChange.append(" ");
			onChange.append(currentOnChange);

			if(!currentOnChange.endsWith(";"))
				onChange.append(";");
		}

		setOnChange(onChange.toString());

		super.buildEvents();
	}

	/**
	 * @see br.com.concepting.framework.ui.components.BasePropertyComponent#buildRestrictions()
	 */
	protected void buildRestrictions() throws InternalErrorException{
		if(this.showInDialog == null)
			this.showInDialog = false;

		super.buildRestrictions();
	}

	/**
	 * @see br.com.concepting.framework.ui.components.BasePropertyComponent#initialize()
	 */
	protected void initialize() throws InternalErrorException{
		setComponentType(ComponentType.COLOR_PICKER);

		super.initialize();
	}

	/**
	 * @see br.com.concepting.framework.ui.components.BasePropertyComponent#renderOpen()
	 */
	protected void renderOpen() throws InternalErrorException{
		super.renderOpen();

		String actionFormName = getActionFormName();
		PropertyInfo propertyInfo = getPropertyInfo();
		String name = getName();

		if(name == null || name.length() == 0 || actionFormName == null || actionFormName.length() == 0 || propertyInfo == null)
			return;

		HiddenPropertyComponent propertyComponent = new HiddenPropertyComponent();

		propertyComponent.setPageContext(this.pageContext);
		propertyComponent.setOutputStream(getOutputStream());
		propertyComponent.setActionFormName(actionFormName);
		propertyComponent.setPropertyInfo(propertyInfo);
		propertyComponent.setName(name);
		propertyComponent.setValue(getValue());

		try{
			propertyComponent.doStartTag();
			propertyComponent.doEndTag();
		}
		catch(JspException e){
			throw new InternalErrorException(e);
		}

		if(this.showInDialog != null && this.showInDialog){
			renderThumbnail();

			print("<div id=\"");
			print(name);
			print(".");
			print(UIConstants.DEFAULT_COLOR_PICKER_DIALOG_ID);
			print("\" class=\"");
			print(UIConstants.DEFAULT_COLOR_PICKER_DIALOG_STYLE_CLASS);
			println("\">");
		}
	}

	/**
	 * @see br.com.concepting.framework.ui.components.BasePropertyComponent#renderBody()
	 */
	protected void renderBody() throws InternalErrorException{
		String actionFormName = getActionFormName();
		String name = getName();

		if(actionFormName == null || actionFormName.length() == 0 || name == null || name.length() == 0)
			return;

		print("<table class=\"");
		print(UIConstants.DEFAULT_CONTENT_PANEL_STYLE_CLASS);
		println("\">");

		println("<tr>");
		println("<td>");

		StringBuilder nameBuffer = new StringBuilder();

		nameBuffer.append(name);
		nameBuffer.append(".");
		nameBuffer.append(UIConstants.COLOR_PICKER_RED_VALUE_ATTRIBUTE_ID);

		String resourcesId = getResourcesId();
		String onChange = getOnChange();
		String value = getValue();
		Color color = (value != null && value.length() > 0 ? ColorUtil.toColor(value) : Color.WHITE);
		SliderBarComponent redValuePropertyComponent = new SliderBarComponent();

		redValuePropertyComponent.setPageContext(this.pageContext);
		redValuePropertyComponent.setOutputStream(getOutputStream());
		redValuePropertyComponent.setActionFormName(actionFormName);
		redValuePropertyComponent.setResourcesId(resourcesId);
		redValuePropertyComponent.setName(nameBuffer.toString());
		redValuePropertyComponent.setLabel("R");
		redValuePropertyComponent.setLabelStyleClass(UIConstants.DEFAULT_COLOR_PICKER_LABEL_STYLE_CLASS);
		redValuePropertyComponent.setAlignmentType(AlignmentType.RIGHT);
		redValuePropertyComponent.setOnChange(onChange);
		redValuePropertyComponent.setSize(3);
		redValuePropertyComponent.setMaximumLength(3);
		redValuePropertyComponent.setMaximumValue(255l);
		redValuePropertyComponent.setValue(color.getRed());

		try{
			redValuePropertyComponent.doStartTag();
			redValuePropertyComponent.doEndTag();
		}
		catch(JspException e){
			throw new InternalErrorException(e);
		}

		nameBuffer.delete(0, nameBuffer.length());
		nameBuffer.append(name);
		nameBuffer.append(".");
		nameBuffer.append(UIConstants.COLOR_PICKER_GREEN_VALUE_ATTRIBUTE_ID);

		SliderBarComponent greenValuePropertyComponent = new SliderBarComponent();

		greenValuePropertyComponent.setPageContext(this.pageContext);
		greenValuePropertyComponent.setOutputStream(getOutputStream());
		greenValuePropertyComponent.setActionFormName(actionFormName);
		greenValuePropertyComponent.setResourcesId(resourcesId);
		greenValuePropertyComponent.setName(nameBuffer.toString());
		greenValuePropertyComponent.setLabel("G");
		greenValuePropertyComponent.setLabelStyleClass(UIConstants.DEFAULT_COLOR_PICKER_LABEL_STYLE_CLASS);
		greenValuePropertyComponent.setAlignmentType(AlignmentType.RIGHT);
		greenValuePropertyComponent.setOnChange(onChange);
		greenValuePropertyComponent.setSize(3);
		greenValuePropertyComponent.setMaximumLength(3);
		greenValuePropertyComponent.setMaximumValue(255l);
		greenValuePropertyComponent.setValue(color.getGreen());

		try{
			greenValuePropertyComponent.doStartTag();
			greenValuePropertyComponent.doEndTag();
		}
		catch(JspException e){
			throw new InternalErrorException(e);
		}

		nameBuffer.delete(0, nameBuffer.length());
		nameBuffer.append(name);
		nameBuffer.append(".");
		nameBuffer.append(UIConstants.COLOR_PICKER_BLUE_VALUE_ATTRIBUTE_ID);

		SliderBarComponent blueValuePropertyComponent = new SliderBarComponent();

		blueValuePropertyComponent.setPageContext(this.pageContext);
		blueValuePropertyComponent.setOutputStream(getOutputStream());
		blueValuePropertyComponent.setActionFormName(actionFormName);
		blueValuePropertyComponent.setResourcesId(resourcesId);
		blueValuePropertyComponent.setName(nameBuffer.toString());
		blueValuePropertyComponent.setLabel("B");
		blueValuePropertyComponent.setLabelStyleClass(UIConstants.DEFAULT_COLOR_PICKER_LABEL_STYLE_CLASS);
		blueValuePropertyComponent.setAlignmentType(AlignmentType.RIGHT);
		blueValuePropertyComponent.setOnChange(onChange);
		blueValuePropertyComponent.setSize(3);
		blueValuePropertyComponent.setMaximumLength(3);
		blueValuePropertyComponent.setMaximumValue(255l);
		blueValuePropertyComponent.setValue(color.getBlue());

		try{
			blueValuePropertyComponent.doStartTag();
			blueValuePropertyComponent.doEndTag();
		}
		catch(JspException e){
			throw new InternalErrorException(e);
		}

		println("</td>");

		if(this.showInDialog == null || !this.showInDialog){
			println("<td width=\"5\"></td>");

			println("<td>");

			renderThumbnail();

			println("</td>");
		}

		println("</tr>");
		println("</table>");
	}

	/**
	 * Renders the color thumbnail.
	 * 
	 * @throws InternalErrorException Occurs when was not possible to render.
	 */
	protected void renderThumbnail() throws InternalErrorException{
		String name = getName();

		if(name == null || name.length() == 0)
			return;

		print("<div id=\"");
		print(name);
		print(".");
		print(UIConstants.DEFAULT_COLOR_PICKER_THUMBNAIL_ID);
		print("\" class=\"");
		print(UIConstants.DEFAULT_COLOR_PICKER_THUMBNAIL_STYLE_CLASS);
		print("\"");

		String width = getWidth();
		String height = getHeight();

		if((width != null && width.length() > 0) || (height != null && height.length() > 0)){
			print(" style=\"");

			if(width != null && width.length() > 0){
				print("width: ");
				print(width);

				if(!width.endsWith(";"))
					print(";");
			}

			if(height != null && height.length() > 0){
				if(width != null && width.length() > 0)
					print(" ");

				print("height: ");
				print(height);

				if(!height.endsWith(";"))
					print(";");
			}

			print("\"");
		}

		if(this.showInDialog != null && this.showInDialog){
			print(" onClick=\"showHideColorPickerDialog('");
			print(name);
			print("');\"");
		}

		println("></div>");
	}

	/**
	 * @see br.com.concepting.framework.ui.components.BasePropertyComponent#renderClose()
	 */
	protected void renderClose() throws InternalErrorException{
		super.renderClose();

		if(this.showInDialog != null && this.showInDialog)
			println("</div>");

		String onChange = getOnChange();

		if(onChange != null && onChange.length() > 0){
			ScriptComponent scriptComponent = new ScriptComponent();

			scriptComponent.setPageContext(this.pageContext);
			scriptComponent.setOutputStream(getOutputStream());
			scriptComponent.setContent(onChange);

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
	 * @see br.com.concepting.framework.ui.components.BasePropertyComponent#clearAttributes()
	 */
	protected void clearAttributes() throws InternalErrorException{
		super.clearAttributes();

		setShowInDialog(null);
		setThumbnailWidth(null);
		setThumbnailHeight(null);
	}
}