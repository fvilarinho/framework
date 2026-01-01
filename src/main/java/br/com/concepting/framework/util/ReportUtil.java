package br.com.concepting.framework.util;

import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.resources.PropertiesResources;
import br.com.concepting.framework.resources.PropertiesResourcesLoader;
import br.com.concepting.framework.resources.exceptions.InvalidResourcesException;
import br.com.concepting.framework.util.constants.ReportConstants;
import br.com.concepting.framework.util.helpers.ReportDataSource;
import br.com.concepting.framework.util.types.ContentType;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.*;
import net.sf.jasperreports.export.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Class responsible to manipulate reports (JasperReports)u.
 *
 * @author fvilarinho
 * @since 1.0.0
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
public class ReportUtil{
    /**
     * Generate the report using the default parameters.
     *
     * @param resourcesId String that contains the report identifier.
     * @param datasource Instance that contains the datasource (List of objects or database connection) for the report.
     * @return Array of bytes that contains the report.
     * @throws InternalErrorException Occurs when was not possible to generate the report.
     */
    public static byte[] generateReport(String resourcesId, Object datasource) throws InternalErrorException{
        return generateReport(resourcesId, datasource, false);
    }

    /**
     * Generate the report using the default parameters.
     *
     * @param resourcesId String that contains the report identifier.
     * @param datasource Instance that contains the datasource (List of objects or database connection) for the report.
     * @param compiled Indicates if the report is compiled (binary) or not.
     * @return Array of bytes that contains the report.
     * @throws InternalErrorException Occurs when was not possible to generate the report.
     */
    public static byte[] generateReport(String resourcesId, Object datasource, boolean compiled) throws InternalErrorException{
        return generateReport(resourcesId, datasource, compiled, null);
    }

    /**
     * Generate the report using the default parameters.
     *
     * @param resourcesId String that contains the report identifier.
     * @param datasource Instance that contains the datasource (List of objects or database connection) for the report.
     * @param language Instance that contains the language to be used in the report.
     * @return Array of bytes that contains the report.
     * @throws InternalErrorException Occurs when was not possible to generate the report.
     */
    public static byte[] generateReport(String resourcesId, Object datasource, Locale language) throws InternalErrorException{
        return generateReport(resourcesId, datasource, false, language);
    }

    /**
     * Generate the report using the default parameters.
     *
     * @param resourcesId String that contains the report identifier.
     * @param datasource Instance that contains the datasource (List of objects or database connection) for the report.
     * @param compiled Indicates if the report is compiled (binary) or not.
     * @param language Instance that contains the language to be used in the report.
     * @return Array of bytes that contains the report.
     * @throws InternalErrorException Occurs when was not possible to generate the report.
     */
    public static byte[] generateReport(String resourcesId, Object datasource, boolean compiled, Locale language) throws InternalErrorException{
        return generateReport(resourcesId, null, datasource, compiled, language);
    }

    /**
     * Generate the report using the specific parameters.
     *
     * @param resourcesId String that contains the report identifier.
     * @param reportParameters Map that contains the parameters of the report.
     * @param datasource Instance that contains the datasource (List of objects or database connection) for the report.
     * @return Array of bytes that contains the report.
     * @throws InternalErrorException Occurs when was not possible to generate the report.
     */
    public static byte[] generateReport(String resourcesId, Map<String, Object> reportParameters, Object datasource) throws InternalErrorException{
        return generateReport(resourcesId, reportParameters, datasource, null);
    }

    /**
     * Generate the report using the specific parameters.
     *
     * @param resourcesId String that contains the report identifier.
     * @param reportParameters Map that contains the parameters of the report.
     * @param datasource Instance that contains the datasource (List of objects or database connection) for the report.
     * @param language Instance that contains the language to be used in the report.
     * @return Array of bytes that contains the report.
     * @throws InternalErrorException Occurs when was not possible to generate the report.
     */
    public static byte[] generateReport(String resourcesId, Map<String, Object> reportParameters, Object datasource, Locale language) throws InternalErrorException{
        return generateReport(resourcesId, reportParameters, datasource, false, language);
    }

    /**
     * Generate the report using the specific parameters.
     *
     * @param resourcesId String that contains the report identifier.
     * @param reportParameters Map that contains the parameters of the report.
     * @param datasource Instance that contains the datasource (List of objects or database connection) for the report.
     * @param compiled Indicates if the report is compiled (binary) or not.
     * @return Array of bytes that contains the report.
     * @throws InternalErrorException Occurs when was not possible to generate the report.
     */
    public static byte[] generateReport(String resourcesId, Map<String, Object> reportParameters, Object datasource, boolean compiled)  throws InternalErrorException{
        return generateReport(resourcesId, reportParameters, datasource, compiled, null);
    }

    /**
     * Generate the report using the specific parameters.
     *
     * @param resourcesId String that contains the report identifier.
     * @param reportParameters Map that contains the parameters of the report.
     * @param datasource Instance that contains the datasource (List of objects or database connection) for the report.
     * @param compiled Indicates if the report is compiled (binary) or not.
     * @param language Instance that contains the language to be used in the report.
     * @return Array of bytes that contains the report.
     * @throws InternalErrorException Occurs when was not possible to generate the report.
     */
    public static byte[] generateReport(String resourcesId, Map<String, Object> reportParameters, Object datasource, boolean compiled, Locale language) throws InternalErrorException{
        if (language == null)
            language = LanguageUtil.getDefaultLanguage();

        if(resourcesId != null && !resourcesId.isEmpty() && datasource != null){
            String reportFileId = buildReportFilename(resourcesId, compiled);

            try (InputStream reportStream = ReportUtil.class.getClassLoader().getResourceAsStream(reportFileId)) {
                if (reportStream == null)
                    throw new InvalidResourcesException(reportFileId);

                JasperReport compiledReport = null;

                if (!compiled)
                    compiledReport = JasperCompileManager.compileReport(reportStream);

                reportParameters = prepareReportParameters(resourcesId, reportFileId, reportParameters, language);

                if (reportParameters != null && !reportParameters.isEmpty()) {
                    JasperPrint jasperPrint;

                    if (compiledReport != null) {
                        if (datasource instanceof Connection connection)
                            jasperPrint = JasperFillManager.fillReport(compiledReport, reportParameters, connection);
                        else
                            jasperPrint = JasperFillManager.fillReport(compiledReport, reportParameters, new ReportDataSource((Collection<?>) datasource));
                    }
                    else {
                        if (datasource instanceof Connection connection)
                            jasperPrint = JasperFillManager.fillReport(reportStream, reportParameters, connection);
                        else
                            jasperPrint = JasperFillManager.fillReport(reportStream, reportParameters, new ReportDataSource((Collection<?>) datasource));
                    }

                    ContentType exportType = (ContentType) reportParameters.get(ReportConstants.EXPORT_TYPE_ATTRIBUTE_ID);
                    ByteArrayOutputStream exportStream = new ByteArrayOutputStream();

                    switch (exportType) {
                        case HTML: {
                            SimpleHtmlReportConfiguration configuration = new SimpleHtmlReportConfiguration();

                            configuration.setWhitePageBackground(true);

                            HtmlExporter exporter = new HtmlExporter();

                            exporter.setConfiguration(configuration);
                            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                            exporter.setExporterOutput(new SimpleHtmlExporterOutput(exportStream));
                            exporter.exportReport();

                            break;
                        }
                        case TXT: {
                            SimpleTextReportConfiguration configuration = new SimpleTextReportConfiguration();

                            configuration.setPageWidthInChars((Integer) reportParameters.get(ReportConstants.TEXT_PAGE_WIDTH_ATTRIBUTE_ID));
                            configuration.setPageHeightInChars((Integer) reportParameters.get(ReportConstants.TEXT_PAGE_HEIGHT_ATTRIBUTE_ID));

                            JRTextExporter exporter = new JRTextExporter();

                            exporter.setConfiguration(configuration);
                            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                            exporter.setExporterOutput(new SimpleWriterExporterOutput(exportStream));

                            break;
                        }
                        case XLS: {
                            SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();

                            configuration.setOnePagePerSheet(true);

                            JRXlsExporter exporter = new JRXlsExporter();

                            exporter.setConfiguration(configuration);
                            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(exportStream));
                            exporter.exportReport();

                            break;
                        }
                        case RTF: {
                            JRRtfExporter exporter = new JRRtfExporter();

                            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                            exporter.setExporterOutput(new SimpleWriterExporterOutput(exportStream));
                            exporter.exportReport();

                            break;
                        }
                        case XML: {
                            JRXmlExporter exporter = new JRXmlExporter();

                            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                            exporter.setExporterOutput(new SimpleXmlExporterOutput(exportStream));
                            exporter.exportReport();

                            break;
                        }
                        default: {
                            JRPdfExporter exporter = new JRPdfExporter();

                            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(exportStream));
                            exporter.exportReport();

                            break;
                        }
                    }

                    return exportStream.toByteArray();
                }
            }
            catch (IOException | JRException e) {
                throw new InternalErrorException(e);
            }
        }
        
        return null;
    }

    /**
     * Returns the report filename.
     *
     * @param resourcesId String that contains the identifier of the report.
     * @param compiled Indicates if the report is compiled or not.
     * @return String that contains the filename.
     */
    private static String buildReportFilename(String resourcesId, boolean compiled){
        if(resourcesId == null || resourcesId.isEmpty())
            return null;
        
        StringBuilder resourcesIdBuffer = new StringBuilder();
        
        resourcesIdBuffer.append(ReportConstants.DEFAULT_RESOURCES_DIR);
        resourcesIdBuffer.append(resourcesId);

        if(compiled)
            resourcesIdBuffer.append(ReportConstants.DEFAULT_COMPILED_REPORT_FILE_EXTENSION);
        else
            resourcesIdBuffer.append(ReportConstants.DEFAULT_SOURCE_REPORT_FILE_EXTENSION);

        return resourcesIdBuffer.toString();
    }

    private static Map<String, Object> prepareReportParameters(String resourcesId, String reportFilename, Map<String, Object> reportParameters, Locale language) throws InternalErrorException{
        if(resourcesId == null || resourcesId.isEmpty() || reportFilename == null || reportFilename.isEmpty())
            return null;
        
        if(reportParameters == null)
            reportParameters = PropertyUtil.instantiate(Constants.DEFAULT_MAP_CLASS);

        if (reportParameters != null) {
            reportParameters.put(JRParameter.REPORT_LOCALE, language);

            if (reportParameters.get(JRParameter.REPORT_RESOURCE_BUNDLE) == null) {
                StringBuilder resourceIdBuffer = new StringBuilder();

                resourceIdBuffer.append(ReportConstants.DEFAULT_PROPERTIES_RESOURCES_DIR);
                resourceIdBuffer.append(resourcesId);

                try {
                    PropertiesResourcesLoader loader = new PropertiesResourcesLoader(resourceIdBuffer.toString(), language);
                    PropertiesResources resources = loader.getContent();
                    ResourceBundle bundle = resources.getContent();

                    reportParameters.put(JRParameter.REPORT_RESOURCE_BUNDLE, bundle);
                }
                catch(InvalidResourcesException ignored) {}
            }

            URL url = ReportUtil.class.getClassLoader().getResource(reportFilename);

            if (url != null) {
                String subReportDirname = url.getFile().substring(1);
                int pos = subReportDirname.lastIndexOf("/");

                if (pos >= 0) {
                    subReportDirname = subReportDirname.substring(0, pos + 1);

                    reportParameters.put(ReportConstants.SUB_REPORT_ATTRIBUTE_ID, subReportDirname);
                }
            }

            ContentType exportType = (ContentType) reportParameters.get(ReportConstants.EXPORT_TYPE_ATTRIBUTE_ID);

            if (exportType == null)
                reportParameters.put(ReportConstants.EXPORT_TYPE_ATTRIBUTE_ID, ReportConstants.DEFAULT_EXPORT_TYPE);
            else {
                if (exportType == ContentType.TXT) {
                    int pageWidth;

                    try {
                        pageWidth = (int) reportParameters.get(ReportConstants.TEXT_PAGE_WIDTH_ATTRIBUTE_ID);
                    }
                    catch (Throwable e) {
                        pageWidth = ReportConstants.DEFAULT_TEXT_PAGE_WIDTH;
                    }

                    int pageHeight;

                    try {
                        pageHeight = (int) reportParameters.get(ReportConstants.TEXT_PAGE_HEIGHT_ATTRIBUTE_ID);
                    }
                    catch (Throwable e) {
                        pageHeight = ReportConstants.DEFAULT_TEXT_PAGE_HEIGHT;
                    }

                    reportParameters.put(ReportConstants.TEXT_PAGE_WIDTH_ATTRIBUTE_ID, pageWidth);
                    reportParameters.put(ReportConstants.TEXT_PAGE_HEIGHT_ATTRIBUTE_ID, pageHeight);
                }
            }
        }
        
        return reportParameters;
    }
}