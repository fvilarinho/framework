package br.com.concepting.framework.util.constants;

import br.com.concepting.framework.resources.constants.ResourcesConstants;
import br.com.concepting.framework.util.types.ContentType;

/**
 * Class that defines the constants used in the manipulation of report files
 * (JasperReports).
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
public abstract class ReportConstants{
    public static final String EXPORT_TYPE_ATTRIBUTE_ID = "exportType";
    public static final String TEXT_PAGE_WIDTH_ATTRIBUTE_ID = "textPageWidth";
    public static final String TEXT_PAGE_HEIGHT_ATTRIBUTE_ID = "textPageHeight";
    public static final String SUB_REPORT_ATTRIBUTE_ID = "SUBREPORT_DIR";
    public static final ContentType DEFAULT_EXPORT_TYPE = ContentType.PDF;
    public static final Integer DEFAULT_TEXT_PAGE_WIDTH = 120;
    public static final Integer DEFAULT_TEXT_PAGE_HEIGHT = 80;
    public static final String DEFAULT_RESOURCES_DIR = ResourcesConstants.DEFAULT_RESOURCES_DIR.concat("reports/");
    public static final String DEFAULT_PROPERTIES_RESOURCES_DIR = ResourcesConstants.DEFAULT_PROPERTIES_RESOURCES_DIR.concat("reports/");
    public static final String DEFAULT_COMPILED_REPORT_FILE_EXTENSION = ".jasper";
    public static final String DEFAULT_SOURCE_REPORT_FILE_EXTENSION = ".jrxml";
}