package br.com.innovativethinking.framework.ui.components;

import br.com.innovativethinking.framework.exceptions.InternalErrorException;
import br.com.innovativethinking.framework.util.PropertyUtil;
import br.com.innovativethinking.framework.util.types.ComponentType;

/**
 * Class that defines the label component.
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
public class LabelComponent extends BasePropertyComponent{
	private static final long serialVersionUID = 4419772883440170233L;

	/**
	 * @see br.com.innovativethinking.framework.ui.components.BasePropertyComponent#getFormattedValue()
	 */
	protected String getFormattedValue() throws InternalErrorException{
		return PropertyUtil.format(getValue(), getPattern(), useAdditionalFormatting(), getPrecision(), getCurrentLanguage());
	}

	/**	
	 * @see br.com.innovativethinking.framework.ui.components.BasePropertyComponent#buildRestrictions()
	 */
	protected void buildRestrictions() throws InternalErrorException{
		String resourcesKey = getResourcesKey();
		String name = getName();
		Object value = getValue();

		if((name == null || name.length() == 0) && value != null)
			setShowLabel(false);

		if((name == null || name.length() == 0) && (resourcesKey != null && resourcesKey.length() > 0))
			setShowLabel(false);

		super.buildRestrictions();
	}

	/**
	 * @see br.com.innovativethinking.framework.ui.components.BasePropertyComponent#initialize()
	 */
	protected void initialize() throws InternalErrorException{
		ComponentType componentType = getComponentType();

		if(componentType == null){
			componentType = ComponentType.LABEL;

			setComponentType(componentType);
		}

		super.initialize();

		if(getPropertyInfo() == null && getValue() == null)
			setValue(getLabel());
	}

	/**
	 * @see br.com.innovativethinking.framework.ui.components.BasePropertyComponent#renderRequiredMark()
	 */
	protected Boolean renderRequiredMark(){
		return false;
	}

	/**
	 * @see br.com.innovativethinking.framework.ui.components.BasePropertyComponent#renderValue()
	 */
	protected void renderValue() throws InternalErrorException{
	}

	/**
	 * @see br.com.innovativethinking.framework.ui.components.BasePropertyComponent#renderPlaceholder()
	 */
	protected void renderPlaceholder() throws InternalErrorException{
	}

	/**
	 * @see br.com.innovativethinking.framework.ui.components.BaseComponent#renderName()
	 */
	protected void renderName() throws InternalErrorException{
	}

	/**
	 * @see br.com.innovativethinking.framework.ui.components.BaseActionFormComponent#renderType()
	 */
	protected void renderType() throws InternalErrorException{
	}

	/**
	 * @see br.com.innovativethinking.framework.ui.components.BaseActionFormComponent#renderSize()
	 */
	protected void renderSize() throws InternalErrorException{
	}

	/**
	 * @see br.com.innovativethinking.framework.ui.components.BaseActionFormComponent#renderEnabled()
	 */
	protected void renderEnabled() throws InternalErrorException{
	}

	/**
	 * @see br.com.innovativethinking.framework.ui.components.BasePropertyComponent#renderReadOnly()
	 */
	protected void renderReadOnly() throws InternalErrorException{
	}
	
	/**
	 * @see br.com.innovativethinking.framework.ui.components.BaseActionFormComponent#renderBody()
	 */
	protected void renderBody() throws InternalErrorException{
		Boolean hasInvalidPropertyDefinition = hasInvalidPropertyDefinition();

		if(hasInvalidPropertyDefinition != null && hasInvalidPropertyDefinition)
			super.renderInvalidPropertyMessage();
		else{
			print("<span");

			renderAttributes();

			print(">");

			String formattedValue = getFormattedValue();

			if(formattedValue != null && formattedValue.length() > 0)
				print(formattedValue);

			println("</span>");
		}
	}
}