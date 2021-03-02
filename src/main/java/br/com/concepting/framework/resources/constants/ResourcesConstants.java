package br.com.concepting.framework.resources.constants;

/**
 * Class that defines the constants used in the manipulation of resources.
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
@SuppressWarnings("javadoc")
public abstract class ResourcesConstants{
    public static final String OPTIONS_ATTRIBUTE_ID = "options";
    public static final String RESOURCES_ATTRIBUTE_ID = "resourcesId";
    public static final String DEFAULT_RESOURCES_DIR = "etc/resources/";
    public static final String DEFAULT_PROPERTIES_RESOURCES_DIR = DEFAULT_RESOURCES_DIR.concat("properties/");
    public static final String DEFAULT_PROPERTIES_RESOURCES_FILE_EXTENSION = ".properties";
}