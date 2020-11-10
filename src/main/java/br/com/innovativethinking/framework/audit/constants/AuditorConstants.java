package br.com.innovativethinking.framework.audit.constants;

import br.com.innovativethinking.framework.resources.constants.ResourcesConstants;

/**
 * Class that defines the constants used in auditing.
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
public abstract class AuditorConstants{
	public static final String APPENDERS_ATTRIBUTE_ID           = "appenders";
	public static final String APPENDER_ENTRY_ATTRIBUTE_ID      = "appender";
	public static final String BUSINESS_COMPLEMENT_ATTRIBUTE_ID = "businessComplement";
	public static final String LEVEL_ATTRIBUTE_ID               = "level";
	public static final String DEFAULT_RESOURCES_ID             = ResourcesConstants.DEFAULT_RESOURCES_DIR.concat("auditorResources.xml");
}