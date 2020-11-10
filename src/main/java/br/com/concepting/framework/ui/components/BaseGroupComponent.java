package br.com.concepting.framework.ui.components;

import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.ui.constants.UIConstants;

/**
 * Class that defines the basic implementation for groups components.
 * 
 * @author fvilarinho
 * @since 3.3.0
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
public abstract class BaseGroupComponent extends BaseActionFormComponent{
	private static final long serialVersionUID = 8751796595605205394L;

	/**
	 * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#buildStyleClass()
	 */
	protected void buildStyleClass() throws InternalErrorException{
		String styleClass = getStyleClass();

		if(styleClass == null || styleClass.length() == 0){
			styleClass = UIConstants.DEFAULT_GROUP_STYLE_CLASS;

			setStyleClass(styleClass);
		}

		String labelStyleClass = getLabelStyleClass();

		if(labelStyleClass == null || labelStyleClass.length() == 0){
			labelStyleClass = UIConstants.DEFAULT_GROUP_LABEL_STYLE_CLASS;

			setLabelStyleClass(labelStyleClass);
		}

		super.buildStyleClass();
	}

	/**
	 * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#renderLabelOpen()
	 */
	protected void renderLabelOpen() throws InternalErrorException{
		print("<legend");

		String labelStyleClass = getLabelStyleClass();

		if(labelStyleClass != null && labelStyleClass.length() > 0){
			print(" class=\"");
			print(labelStyleClass);
			print("\"");
		}

		String labelStyle = getLabelStyle();

		if(labelStyle != null && labelStyle.length() > 0){
			print(" style=\"");
			print(labelStyle);

			if(!labelStyle.endsWith(";"))
				print(";");

			print("\"");
		}

		println(">");
	}

	/**
	 * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#renderLabelClose()
	 */
	protected void renderLabelClose() throws InternalErrorException{
		println("</legend>");
	}

	/**
	 * @see br.com.concepting.framework.ui.components.BaseComponent#renderOpen()
	 */
	protected void renderOpen() throws InternalErrorException{
		print("<fieldset");

		String styleClass = getStyleClass();

		if(styleClass != null && styleClass.length() > 0){
			print(" class=\"");
			print(styleClass);
			print("\"");
		}

		String style = getStyle();

		if(style != null && style.length() > 0){
			print(" style=\"");
			print(style);

			if(!style.endsWith(";"))
				print(";");

			print("\"");
		}

		super.renderTooltip();

		println(">");

		Boolean showLabel = showLabel();
		String label = getLabel();

		if(showLabel != null && showLabel && label != null && label.length() > 0)
			super.renderLabel();
	}

	/**
	 * @see br.com.concepting.framework.ui.components.BaseComponent#renderClose()
	 */
	protected void renderClose() throws InternalErrorException{
		println("</fieldset>");
	}
}