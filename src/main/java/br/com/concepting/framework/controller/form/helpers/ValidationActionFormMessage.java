package br.com.concepting.framework.controller.form.helpers;

import br.com.concepting.framework.controller.form.types.ActionFormMessageType;
import br.com.concepting.framework.model.types.ValidationType;

/**
 * Class that defines validation message for a data model property.
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
public class ValidationActionFormMessage extends ActionFormMessage{
	private static final long serialVersionUID = 8110226146359639868L;

	/**
	 * Constructor - Defines the validation message.
	 * 
	 * @param key Instance that contains the identifier of the message.
	 */
	public ValidationActionFormMessage(ValidationType key){
		this(key.getId());
	}

	/**
	 * Constructor - Defines the validation message.
	 * 
	 * @param key String that contains the identifier of the message.
	 */
	public ValidationActionFormMessage(String key){
		super(ActionFormMessageType.VALIDATION, key);
	}
}