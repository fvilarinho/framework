package br.com.concepting.framework.ui.components;

import javax.servlet.jsp.JspException;

import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.util.NumberUtil;
import br.com.concepting.framework.util.helpers.PropertyInfo;
import br.com.concepting.framework.util.types.ComponentType;

/**
 * Class that defines the check box component.
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
public class CheckPropertyComponent extends BaseOptionPropertyComponent{
	private static final long serialVersionUID = 3956751502801574242L;

	/**
	 * @see br.com.concepting.framework.ui.components.BasePropertyComponent#buildEvents()
	 */
	protected void buildEvents() throws InternalErrorException{
		String id = getId();

		if(id != null && id.length() > 0 && isBoolean() != null && isBoolean()){
			StringBuilder onClick = new StringBuilder();

			onClick.append("setObjectValue('");
			onClick.append(id);
			onClick.append("', this.checked);");

			String currentOnClick = getOnClick();

			if(currentOnClick != null && currentOnClick.length() > 0){
				onClick.append(" ");
				onClick.append(currentOnClick);

				if(!currentOnClick.endsWith(";"))
					onClick.append(";");
			}

			setOnClick(onClick.toString());
		}

		super.buildEvents();
	}

	/**
	 * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#buildName()
	 */
	protected void buildName() throws InternalErrorException{
		String id = getId();
		String name = getName();

		if(id == null && name != null && name.length() > 0){
			StringBuilder idBuffer = new StringBuilder();

			idBuffer.append(name);
			idBuffer.append((int)(Math.random() * NumberUtil.getMaximumRange(Integer.class)));

			setId(idBuffer.toString());
		}

		super.buildName();
	}

	/**
	 * @see br.com.concepting.framework.ui.components.BaseOptionPropertyComponent#initialize()
	 */
	protected void initialize() throws InternalErrorException{
		setComponentType(ComponentType.CHECK_BOX);

		super.initialize();
	}

	/**
	 * @see br.com.concepting.framework.ui.components.BaseOptionPropertyComponent#renderOpen()
	 */
	protected void renderOpen() throws InternalErrorException{
		BaseOptionsPropertyComponent optionsPropertyComponent = (BaseOptionsPropertyComponent)findAncestorWithClass(this, BaseOptionsPropertyComponent.class);

		if(optionsPropertyComponent == null){
			try{
				optionsPropertyComponent = (BaseOptionsPropertyComponent)getParent();
			}
			catch(ClassCastException e){
			}
		}

		PropertyInfo propertyInfo = getPropertyInfo();
		String actionFormName = getActionFormName();
		String id = getId();
		String name = getName();
		Boolean enabled = isEnabled();

		if(optionsPropertyComponent == null && propertyInfo != null && actionFormName != null && actionFormName.length() > 0 && id != null && id.length() > 0 && name != null && name.length() > 0 && enabled != null && enabled){
			HiddenPropertyComponent propertyComponent = new HiddenPropertyComponent();

			propertyComponent.setPageContext(this.pageContext);
			propertyComponent.setOutputStream(getOutputStream());
			propertyComponent.setActionFormName(actionFormName);
			propertyComponent.setPropertyInfo(propertyInfo);
			propertyComponent.setId(id);
			propertyComponent.setName(name);
			propertyComponent.setLabel(getLabel());
			propertyComponent.setResourcesId(getResourcesId());
			propertyComponent.setResourcesKey(getResourcesKey());
			propertyComponent.setValue(getValue());

			try{
				propertyComponent.doStartTag();
				propertyComponent.doEndTag();
			}
			catch(JspException e){
				throw new InternalErrorException(e);
			}
			
			setId(name);
		}

		super.renderOpen();
	}
}