package br.com.innovativethinking.framework.controller.action.types;

import br.com.innovativethinking.framework.util.StringUtil;

/**
 * Class that defines the action types of a form.
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
public enum ActionType{
	/**
	 * Initialization action.
	 */
	INIT,

	/**
	 * Refresh action.
	 */
	REFRESH,

	/**
	 * Print action.
	 */
	PRINT,

	/**
	 * Add action.
	 */
	ADD,

	/**
	 * Edit action.
	 */
	EDIT,

	/**
	 * Save action.
	 */
	SAVE,

	/**
	 * Back action.
	 */
	BACK,

	/**
	 * Cancel action.
	 */
	CANCEL,

	/**
	 * Search action.
	 */
	SEARCH,

	/**
	 * Insert action.
	 */
	INSERT,

	/**
	 * Update action.
	 */
	UPDATE,

	/**
	 * Delete action.
	 */
	DELETE,

	/**
	 * Change current language action.
	 */
	CHANGE_CURRENT_LANGUAGE,

	/**
	 * Change current skin action.
	 */
	CHANGE_CURRENT_SKIN,

	/**
	 * Download action.
	 */
	DOWNLOAD,

	/**
	 * Upload action.
	 */
	UPLOAD;

	/**
	 * Returns the action identifier.
	 * 
	 * @return String that contains the identifier.
	 */
	public String getMethod(){
		return StringUtil.normalize(toString().toLowerCase());
	}
}