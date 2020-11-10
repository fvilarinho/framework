package br.com.concepting.framework.util;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.resources.PropertiesResources;
import br.com.concepting.framework.resources.PropertiesResourcesLoader;
import br.com.concepting.framework.resources.exceptions.InvalidResourcesException;
import br.com.concepting.framework.util.constants.ReportConstants;
import br.com.concepting.framework.util.helpers.ReportDataSource;
import br.com.concepting.framework.util.types.ContentType;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXmlExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleHtmlReportConfiguration;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleTextReportConfiguration;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;
import net.sf.jasperreports.export.SimpleXmlExporterOutput;

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
public class ReportUtil{
	/**
	 * Generates a report.
	 * 
	 * @param resourcesId String that contains the identifier of the report.
	 * @param datasource Instance that contains the data source.
	 * @return Byte array that contains the processed report.
	 * @throws InternalErrorException Occurs when was not possible to execute
	 * the operation.
	 */
	public static byte[] generateReport(String resourcesId, Object datasource) throws InternalErrorException{
		if(resourcesId != null && resourcesId.length() > 0 && datasource != null)
			return generateReport(null, resourcesId, LanguageUtil.getDefaultLanguage(), datasource);

		return null;
	}

	/**
	 * Generates a report.
	 *
	 * @param resourcesDirname String that contains the storage directory of the
	 * report.
	 * @param resourcesId String that contains the identifier of the report.
	 * @param datasource Instance that contains the data source.
	 * @return Byte array that contains the processed report.
	 * @throws InternalErrorException Occurs when was not possible to execute
	 * the operation.
	 */
	public static byte[] generateReport(String resourcesDirname, String resourcesId, Object datasource) throws InternalErrorException{
		if(resourcesId != null && resourcesId.length() > 0 && datasource != null)
			return generateReport(resourcesDirname, resourcesId, LanguageUtil.getDefaultLanguage(), datasource);

		return null;
	}

	/**
	 * Generates a report.
	 *
	 * @param resourcesId String that contains the identifier of the report.
	 * @param language Instance that contains the language that will be used.
	 * @param datasource Instance that contains the data source.
	 * @return Byte array that contains the processed report.
	 * @throws InternalErrorException Occurs when was not possible to execute
	 * the operation.
	 */
	public static byte[] generateReport(String resourcesId, Locale language, Object datasource) throws InternalErrorException{
		if(resourcesId != null && resourcesId.length() > 0 && datasource != null)
			return generateReport(null, resourcesId, null, language, datasource);

		return null;
	}

	/**
	 * Generates a report.
	 *
	 * @param resourcesDirname String that contains the storage directory of the
	 * report.
	 * @param resourcesId String that contains the identifier of the report.
	 * @param language Instance that contains the language that will be used.
	 * @param datasource Instance that contains the data source.
	 * @return Byte array that contains the processed report.
	 * @throws InternalErrorException Occurs when was not possible to execute
	 * the operation.
	 */
	public static byte[] generateReport(String resourcesDirname, String resourcesId, Locale language, Object datasource) throws InternalErrorException{
		if(resourcesId != null && resourcesId.length() > 0 && datasource != null)
			return generateReport(resourcesDirname, resourcesId, null, language, datasource);

		return null;
	}

	/**
	 * Generates a report.
	 * 
	 * @param resourcesId String that contains the identifier of the report.
	 * @param reportParameters Instance that contains the parameters of the
	 * report.
	 * @param datasource Instance that contains the data source.
	 * @return Byte array that contains the processed report.
	 * @throws InternalErrorException Occurs when was not possible to execute
	 * the operation.
	 */
	public static byte[] generateReport(String resourcesId, Map<String, Object> reportParameters, Object datasource) throws InternalErrorException{
		if(resourcesId != null && resourcesId.length() > 0 && datasource != null)
			return generateReport(null, resourcesId, reportParameters, LanguageUtil.getDefaultLanguage(), datasource);

		return null;
	}

	/**
	 * Generates a report.
	 * 
	 * @param resourcesDirname String that contains the storage directory of the
	 * report.
	 * @param resourcesId String that contains the identifier of the report.
	 * @param reportParameters Instance that contains the parameters of the
	 * report.
	 * @param datasource Instance that contains the data source.
	 * @return Byte array that contains the processed report.
	 * @throws InternalErrorException Occurs when was not possible to execute
	 * the operation.
	 */
	public static byte[] generateReport(String resourcesDirname, String resourcesId, Map<String, Object> reportParameters, Object datasource) throws InternalErrorException{
		if(resourcesId != null && resourcesId.length() > 0 && datasource != null)
			return generateReport(resourcesDirname, resourcesId, reportParameters, LanguageUtil.getDefaultLanguage(), datasource);

		return null;
	}

	/**
	 * Generates a report.
	 * 
	 * @param resourcesId String that contains the identifier of the report.
	 * @param reportParameters Instance that contains the parameters of the
	 * report.
	 * @param language Instance that contains the language that will be used.
	 * @param datasource Instance that contains the data source.
	 * @return Byte array that contains the processed report.
	 * @throws InternalErrorException Occurs when was not possible to execute
	 * the operation.
	 */
	public static byte[] generateReport(String resourcesId, Map<String, Object> reportParameters, Locale language, Object datasource) throws InternalErrorException{
		if(resourcesId != null && resourcesId.length() > 0 && datasource != null)
			return generateReport(null, resourcesId, reportParameters, language, datasource);

		return null;
	}

	/**
	 * Generates a report.
	 * 
	 * @param resourcesDirname String that contains the storage directory of the
	 * report.
	 * @param resourcesId String that contains the identifier of the report.
	 * @param reportParameters Instance that contains the parameters of the
	 * report.
	 * @param language Instance that contains the language that will be used.
	 * @param datasource Instance that contains the data source.
	 * @return Byte array that contains the processed report.
	 * @throws InternalErrorException Occurs when was not possible to execute
	 * the operation.
	 */
	public static byte[] generateReport(String resourcesDirname, String resourcesId, Map<String, Object> reportParameters, Locale language, Object datasource) throws InternalErrorException{
		if(resourcesId != null && resourcesId.length() != 0 && datasource != null){
			String reportFileId = buildReportFilename(resourcesDirname, resourcesId);
			InputStream reportStream = null;

			try{
				if(resourcesDirname != null && resourcesDirname.length() > 0)
					reportStream = new FileInputStream(reportFileId);
				else
					reportStream = ReportUtil.class.getClassLoader().getResourceAsStream(reportFileId);

				if(reportStream == null)
					throw new InvalidResourcesException(resourcesDirname, resourcesId);

				reportParameters = prepareReportParameters(resourcesDirname, resourcesId, reportFileId, reportParameters, language);

				if(reportParameters != null && !reportParameters.isEmpty()){
					JasperPrint jasperPrint = null;

					if(datasource instanceof Connection)
						jasperPrint = JasperFillManager.fillReport(reportStream, reportParameters, (Connection)datasource);
					else
						jasperPrint = JasperFillManager.fillReport(reportStream, reportParameters, new ReportDataSource((Collection<?>)datasource));

					ContentType exportType = (ContentType)reportParameters.get(ReportConstants.EXPORT_TYPE_ATTRIBUTE_ID);
					ByteArrayOutputStream exportStream = new ByteArrayOutputStream();

					switch(exportType){
					case HTML:{
						SimpleHtmlReportConfiguration configuration = new SimpleHtmlReportConfiguration();

						configuration.setWhitePageBackground(true);

						HtmlExporter exporter = new HtmlExporter();

						exporter.setConfiguration(configuration);
						exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
						exporter.setExporterOutput(new SimpleHtmlExporterOutput(exportStream));
						exporter.exportReport();

						break;
					}
					case TXT:{
						SimpleTextReportConfiguration configuration = new SimpleTextReportConfiguration();

						configuration.setPageWidthInChars((Integer)reportParameters.get(ReportConstants.TEXT_PAGE_WIDTH_ATTRIBUTE_ID));
						configuration.setPageHeightInChars((Integer)reportParameters.get(ReportConstants.TEXT_PAGE_HEIGHT_ATTRIBUTE_ID));

						JRTextExporter exporter = new JRTextExporter();

						exporter.setConfiguration(configuration);
						exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
						exporter.setExporterOutput(new SimpleWriterExporterOutput(exportStream));

						break;
					}
					case XLS:{
						SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();

						configuration.setOnePagePerSheet(true);

						JRXlsExporter exporter = new JRXlsExporter();

						exporter.setConfiguration(configuration);
						exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
						exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(exportStream));
						exporter.exportReport();

						break;
					}
					case RTF:{
						JRRtfExporter exporter = new JRRtfExporter();

						exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
						exporter.setExporterOutput(new SimpleWriterExporterOutput(exportStream));
						exporter.exportReport();

						break;
					}
					case XML:{
						JRXmlExporter exporter = new JRXmlExporter();

						exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
						exporter.setExporterOutput(new SimpleXmlExporterOutput(exportStream));
						exporter.exportReport();

						break;
					}
					default:{
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
			catch(IOException e){
				throw new InvalidResourcesException(resourcesDirname, resourcesId, e);
			}
			catch(JRException e){
				throw new InternalErrorException(e);
			}
			finally{
				try{
					if(reportStream != null)
						reportStream.close();
				}
				catch(IOException e){
				}
			}
		}

		return null;
	}

	/**
	 * Returns the report filename.
	 * 
	 * @param resourcesDirname String that contains the storage directory of the
	 * report.
	 * @param resourcesId String that contains the identifier of the report.
	 * @return String that contains the filename.
	 */
	private static String buildReportFilename(String resourcesDirname, String resourcesId){
		if(resourcesId == null || resourcesId.length() == 0)
			return null;

		return buildReportFilename(resourcesDirname, resourcesId, true);
	}

	/**
	 * Returns the report filename.
	 * 
	 * @param resourcesDirname String that contains the storage directory of the
	 * report.
	 * @param resourcesId String that contains the identifier of the report.
	 * @param compiled Indicates if the report is compiled or not.
	 * @return String that contains the filename.
	 */
	private static String buildReportFilename(String resourcesDirname, String resourcesId, Boolean compiled){
		if(resourcesId == null || resourcesId.length() == 0)
			return null;

		StringBuilder resourcesIdBuffer = new StringBuilder();

		if(resourcesDirname != null && resourcesDirname.length() == 0)
			resourcesIdBuffer.append(ReportConstants.DEFAULT_RESOURCES_DIR);
		else
			resourcesIdBuffer.append(resourcesDirname);

		resourcesIdBuffer.append(resourcesId);

		if(compiled != null && compiled)
			resourcesIdBuffer.append(ReportConstants.DEFAULT_COMPILED_REPORT_FILE_EXTENSION);
		else
			resourcesIdBuffer.append(ReportConstants.DEFAULT_SOURCE_REPORT_FILE_EXTENSION);

		return resourcesIdBuffer.toString();
	}

	/**
	 * Defines the parameters of the report.
	 *
	 * @param resourcesDirname String that contains the storage directory of the
	 * report.
	 * @param resourcesId String that contains the identifier of the report.
	 * @param reportFilename String that contains the filename of the report.
	 * @param reportParameters Instance that contains the parameters of the
	 * report.
	 * @param language Instance that contains the language that will be used.
	 * @return Instance that contains the parameters of the report.
	 * @throws InternalErrorException Occurs when was not possible to execute
	 * the operation.
	 */
	private static Map<String, Object> prepareReportParameters(String resourcesDirname, String resourcesId, String reportFilename, Map<String, Object> reportParameters, Locale language) throws InternalErrorException{
		if(resourcesId == null || resourcesId.length() == 0 || reportFilename == null || reportFilename.length() == 0)
			return null;

		if(reportParameters == null)
			reportParameters = PropertyUtil.instantiate(Constants.DEFAULT_MAP_CLASS);

		reportParameters.put(JRParameter.REPORT_LOCALE, language);

		if(reportParameters.get(JRParameter.REPORT_RESOURCE_BUNDLE) == null){
			StringBuilder resourceIdBuffer = new StringBuilder();

			resourceIdBuffer.append(ReportConstants.DEFAULT_PROPERTIES_RESOURCES_DIR);
			resourceIdBuffer.append(resourcesId);

			PropertiesResourcesLoader loader = new PropertiesResourcesLoader(resourcesDirname, resourceIdBuffer.toString(), language);
			PropertiesResources resources = loader.getContent();
			ResourceBundle bundle = resources.getContent();

			reportParameters.put(JRParameter.REPORT_RESOURCE_BUNDLE, bundle);
		}

		URL url = ReportUtil.class.getClassLoader().getResource(reportFilename);

		if(url != null){
			String subReportDirname = url.getFile().substring(1);
			int pos = subReportDirname.lastIndexOf("/");

			if(pos >= 0){
				subReportDirname = subReportDirname.substring(0, pos + 1);

				reportParameters.put(ReportConstants.SUB_REPORT_ATTRIBUTE_ID, subReportDirname);
			}
		}

		ContentType exportType = (ContentType)reportParameters.get(ReportConstants.EXPORT_TYPE_ATTRIBUTE_ID);

		if(exportType == null)
			reportParameters.put(ReportConstants.EXPORT_TYPE_ATTRIBUTE_ID, ReportConstants.DEFAULT_EXPORT_TYPE);
		else{
			if(exportType == ContentType.TXT){
				Integer pageWidth = null;
				Integer pageHeight = null;

				pageWidth = (Integer)reportParameters.get(ReportConstants.TEXT_PAGE_WIDTH_ATTRIBUTE_ID);

				if(pageWidth == null)
					pageWidth = ReportConstants.DEFAULT_TEXT_PAGE_WIDTH;

				pageHeight = (Integer)reportParameters.get(ReportConstants.TEXT_PAGE_HEIGHT_ATTRIBUTE_ID);

				if(pageHeight == null)
					pageHeight = ReportConstants.DEFAULT_TEXT_PAGE_HEIGHT;

				reportParameters.put(ReportConstants.TEXT_PAGE_WIDTH_ATTRIBUTE_ID, pageWidth);
				reportParameters.put(ReportConstants.TEXT_PAGE_HEIGHT_ATTRIBUTE_ID, pageHeight);
			}
		}

		return reportParameters;
	}
}