package br.com.concepting.framework.ui.components;

import javax.servlet.jsp.JspException;

import br.com.concepting.framework.controller.action.types.ActionType;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.ui.components.types.VisibilityType;
import br.com.concepting.framework.ui.constants.UIConstants;
import br.com.concepting.framework.util.types.ComponentType;
import br.com.concepting.framework.util.types.ContentType;
import br.com.concepting.framework.util.types.PositionType;

/**
 * Class that defines the download box component.
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
public class UploadPropertyComponent extends DownloadPropertyComponent{
	private static final long serialVersionUID = -1340422255244638637L;

	/**
	 * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#asynchronousEvents()
	 */
	protected Boolean asynchronousEvents(){
		return false;
	}

	/**
	 * @see br.com.concepting.framework.ui.components.BasePropertyComponent#buildEvents()
	 */
	protected void buildEvents() throws InternalErrorException{
		String actionFormName = getActionFormName();

		if(actionFormName == null || actionFormName.length() == 0)
			return;

		String currentOnChnge = getOnChange();
		StringBuilder onChange = new StringBuilder();

		onChange.append("showLoadingBox(); document.");
		onChange.append(actionFormName);
		onChange.append(".enctype = '");
		onChange.append(ContentType.MULTIPART_DATA.getMimeType());
		onChange.append("'; ");

		if(currentOnChnge != null && currentOnChnge.length() > 0){
			onChange.append(" ");
			onChange.append(currentOnChnge);

			if(!currentOnChnge.endsWith(";"))
				onChange.append(";");
		}

		setOnChange(onChange.toString());

		super.buildEvents();
	}

	/**
	 * @see br.com.concepting.framework.ui.components.BasePropertyComponent#initialize()
	 */
	protected void initialize() throws InternalErrorException{
		setComponentType(ComponentType.UPLOAD);

		String onChangeAction = getOnChangeAction();

		if(onChangeAction == null || onChangeAction.length() == 0)
			setOnChangeActionType(ActionType.UPLOAD);

		super.initialize();
	}

	/**
	 * @see br.com.concepting.framework.ui.components.BasePropertyComponent#renderBody()
	 */
	protected void renderBody() throws InternalErrorException{
		Boolean hasInvalidPropertyDefinition = hasInvalidPropertyDefinition();

		if(hasInvalidPropertyDefinition != null && hasInvalidPropertyDefinition)
			super.renderInvalidPropertyMessage();
		else{
			ComponentType componentType = getComponentType();
			String name = getName();

			if(componentType == null || name == null || name.length() == 0)
				return;

			print("<input type=\"");
			print(componentType.getType());
			print("\" id=\"");
			print(name);
			print("\" name=\"");
			print(name);
			print("\" style=\"display: ");
			print(VisibilityType.NONE);
			print(";\"");

			renderEvents();

			println(">");

			PositionType labelPositionType = getLabelPositionType();

			if(labelPositionType == PositionType.TOP || labelPositionType == PositionType.BOTTOM){
				println("<table>");
				println("<tr>");
				println("<td>");
			}

			print("<table class=\"");
			print(UIConstants.DEFAULT_UPLOAD_STYLE_CLASS);
			println("\">");
			println("<tr>");
			println("<td>");

			super.renderBody();

			println("</td>");
			println("<td width=\"10\"></td>");
			println("<td width=\"1\">");

			UploadButtonComponent uploadButtonComponent = new UploadButtonComponent(this);

			try{
				uploadButtonComponent.doStartTag();
				uploadButtonComponent.doEndTag();
			}
			catch(JspException e){
				throw new InternalErrorException(e);
			}

			println("</td>");
			println("</tr>");
			println("</table>");

			if(labelPositionType == PositionType.TOP || labelPositionType == PositionType.BOTTOM){
				println("</td>");
				println("</tr>");
				println("</table>");
			}
		}
	}

	/**
	 * Class that defines the file select button of the upload property
	 * component.
	 * 
	 * @author fvilarinho
	 * @since 1.0.0
	 */
	private class UploadButtonComponent extends ButtonComponent{
		private static final long serialVersionUID = -7170110437405939054L;

		/**
		 * Constructor - Initializes the component.
		 * 
		 * @param uploadPropertyComponent Instance that contains the upload property
		 * component.
		 */
		public UploadButtonComponent(UploadPropertyComponent uploadPropertyComponent){
			super();

			if(uploadPropertyComponent != null){
				setPageContext(uploadPropertyComponent.getPageContext());
				setOutputStream(uploadPropertyComponent.getOutputStream());
				setActionFormName(uploadPropertyComponent.getActionFormName());
				setResourcesId(uploadPropertyComponent.getResourcesId());
				setParent(uploadPropertyComponent);
			}
		}

		/**
		 * @see br.com.concepting.framework.ui.components.ButtonComponent#buildEvents()
		 */
		protected void buildEvents() throws InternalErrorException{
			UploadPropertyComponent uploadPropertyComponent = (UploadPropertyComponent)getParent();
			String name = (uploadPropertyComponent != null ? uploadPropertyComponent.getName() : null);

			if(name != null && name.length() > 0){
				StringBuilder onClick = new StringBuilder();

				onClick.append("var object = getObject('");
				onClick.append(name);
				onClick.append("'); if(object) object.click();");

				setOnClick(onClick.toString());

				super.buildEvents();
			}
		}

		/**
		 * @see br.com.concepting.framework.ui.components.ButtonComponent#buildResources()
		 */
		protected void buildResources() throws InternalErrorException{
			setResourcesKey(UIConstants.DEFAULT_UPLOAD_BUTTON_ID);

			super.buildResources();
		}

		/**
		 * @see br.com.concepting.framework.ui.components.ButtonComponent#buildStyleClass()
		 */
		protected void buildStyleClass() throws InternalErrorException{
			setStyleClass(UIConstants.DEFAULT_UPLOAD_BUTTON_STYLE_CLASS);

			super.buildStyleClass();
		}

		/**
		 * @see br.com.concepting.framework.ui.components.ButtonComponent#initialize()
		 */
		protected void initialize() throws InternalErrorException{
			setOnChangeActionType(ActionType.UPLOAD);

			super.initialize();
		}
	}
}