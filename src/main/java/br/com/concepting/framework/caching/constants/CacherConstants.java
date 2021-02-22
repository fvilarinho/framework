package br.com.concepting.framework.caching.constants;

import br.com.concepting.framework.util.types.DateFieldType;

/**
 * Class that defines the constants used in caching.
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
@SuppressWarnings("javadoc")
public abstract class CacherConstants{
	public static final Long          DEFAULT_TIMEOUT      = DateFieldType.DAYS.getMilliseconds();
	public static final DateFieldType DEFAULT_TIMEOUT_TYPE = DateFieldType.MILLISECONDS;
}