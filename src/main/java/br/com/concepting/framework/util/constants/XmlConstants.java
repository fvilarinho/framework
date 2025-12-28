package br.com.concepting.framework.util.constants;

import br.com.concepting.framework.util.types.ContentType;

/**
 * Class that defines the constants used in the manipulation of XML content.
 *
 * @author fvilarinho
 * @since 3.0.0
 *
 * <pre>Copyright (C) 2007 Innovative Thinking.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <a href="https://www.gnu.org/licenses"></a>.</pre>
 */
public final class XmlConstants{
    public static final String W3C_NO_NAMESPACE_SCHEMA_LOCATION_ATTRIBUTE_ID = "xsi:noNamespaceSchemaLocation";
    public static final String W3C_SCHEMA_NAMESPACE_ATTRIBUTE_ID = "xmlns";
    public static final String W3C_SCHEMA_INSTANCE_ATTRIBUTE_ID = W3C_SCHEMA_NAMESPACE_ATTRIBUTE_ID.concat(":xsi");
    public static final String W3C_SCHEMA_LOCATION_ATTRIBUTE_ID = "xsi:schemaLocation";
    public static final String W3C_SCHEMA_VERSION_ATTRIBUTE_ID = "version";

    public static final String DEFAULT_APACHE_VALIDATION_FEATURE_ID = "https://apache.org/xml/features/nonvalidating/load-external-dtd";
    public static final String DEFAULT_FILE_EXTENSION = ContentType.XML.getExtension();
    public static final String DEFAULT_RESOURCES_NODE_ID = "resources";
    public static final String DEFAULT_SAX_VALIDATION_FEATURE_ID = "https://xml.org/sax/features/validation";
    public static final String DEFAULT_W3C_SCHEMA_INSTANCE_NAMESPACE_ID = "https://www.w3.org/2001/XMLSchema-instance";
}