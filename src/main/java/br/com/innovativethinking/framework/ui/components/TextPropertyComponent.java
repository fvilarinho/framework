package br.com.innovativethinking.framework.ui.components;

import br.com.innovativethinking.framework.exceptions.InternalErrorException;
import br.com.innovativethinking.framework.util.types.ComponentType;

/**
 * Class that defines the text component.
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
public class TextPropertyComponent extends BasePropertyComponent{
	private static final long serialVersionUID = 950781708118352441L;

	/**
	 * @see br.com.innovativethinking.framework.ui.components.BaseComponent#initialize()
	 */
	protected void initialize() throws InternalErrorException{
		ComponentType componentType = getComponentType();

		if(componentType == null){
			componentType = ComponentType.TEXT_BOX;

			setComponentType(componentType);
		}

		super.initialize();
	}
}