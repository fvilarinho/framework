package br.com.concepting.framework.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.DocumentType;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.util.helpers.XmlNode;

/**
 * Class responsible to write XML contents.
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
public class XmlWriter{
	private OutputStream out          = null;
	private DocumentType documentType = null;
	private String       encoding     = null;

	/**
	 * Constructor - Defines the file that will be used to write the content.
	 *
	 * @param file Instance that contains the properties of the file.
	 * @throws IOException Occurs when was not possible to execute the
	 * operation.
	 */
	public XmlWriter(File file) throws IOException{
		this(file, null, null);
	}

	/**
	 * Constructor - Defines the file that will be used to write the content.
	 *
	 * @param file Instance that contains the properties of the file.
	 * @param encoding String that contains the encoding of the content.
	 * @throws IOException Occurs when was not possible to execute the
	 * operation.
	 */
	public XmlWriter(File file, String encoding) throws IOException{
		this(file, null, encoding);
	}

	/**
	 * Constructor - Defines the file that will be used to write the content.
	 *
	 * @param file Instance that contains the properties of the file.
	 * @param documentType Instance that contains the DTD.
	 * @throws IOException Occurs when was not possible to execute the
	 * operation.
	 */
	public XmlWriter(File file, DocumentType documentType) throws IOException{
		this(file, documentType, null);
	}

	/**
	 * Constructor - Defines the file that will be used to write the content.
	 *
	 * @param file Instance that contains the properties of the file.
	 * @param documentType Instance that contains the DTD.
	 * @param encoding String that contains the encoding of the content.
	 * @throws IOException Occurs when was not possible to execute the
	 * operation.
	 */
	public XmlWriter(File file, DocumentType documentType, String encoding) throws IOException{
		super();

		if(file != null){
			if(!file.exists()){
				File parentFile = file.getParentFile();
				
				if(!parentFile.exists())
					parentFile.mkdirs();
			}

			setOut(new FileOutputStream(file));
			setDocumentType(documentType);
			setEncoding(encoding);
		}
	}

	/**
	 * Constructor - Defines the stream that will be used to write the content.
	 *
	 * @param out Instance that contains the stream.
	 */
	public XmlWriter(OutputStream out){
		this(out, null, null);
	}

	/**
	 * Constructor - Defines the stream that will be used to write the content.
	 *
	 * @param out Instance that contains the stream.
	 * @param documentType Instance that contains the DTD.
	 */
	public XmlWriter(OutputStream out, DocumentType documentType){
		this(out, documentType, null);
	}

	/**
	 * Constructor - Defines the stream that will be used to write the content.
	 *
	 * @param out Instance that contains the stream.
	 * @param encoding String that contains the encoding of the content.
	 */
	public XmlWriter(OutputStream out, String encoding){
		this(out, null, encoding);
	}

	/**
	 * Constructor - Defines the stream that will be used to write the content.
	 *
	 * @param out Instance that contains the stream.
	 * @param documentType Instance that contains the DTD.
	 * @param encoding String that contains the encoding of the content.
	 */
	public XmlWriter(OutputStream out, DocumentType documentType, String encoding){
		super();

		setOut(out);
		setDocumentType(documentType);
		setEncoding(encoding);
	}

	/**
	 * Writes the content.
	 *
	 * @param value String that contains the content.
	 * @throws IOException Occurs when was not possible to execute the
	 * operation.
	 * @throws DocumentException Occurs when was not possible to execute the
	 * operation.
	 */
	public void write(String value) throws IOException, DocumentException{
		if(value != null && value.length() > 0){
			OutputFormat format = OutputFormat.createPrettyPrint();

			format.setIndent(Constants.DEFAULT_INDENT_CHARACTER);
			format.setIndentSize(Constants.DEFAULT_INDENT_SIZE);

			write(value, format);
		}
	}

	/**
	 * Writes the content.
	 *
	 * @param value String that contains the content.
	 * @param format Instance that contains the type of the content formatting.
	 * @throws IOException Occurs when was not possible to execute the
	 * operation.
	 * @throws DocumentException Occurs when was not possible to execute the
	 * operation.
	 */
	public void write(String value, OutputFormat format) throws DocumentException, IOException{
		if(value != null && value.length() > 0 && this.out != null && format != null){
			Document document = DocumentHelper.parseText(value);
			XMLWriter writer = new XMLWriter(this.out, format);

			if(this.documentType != null)
				document.setDocType(this.documentType);

			if(this.encoding != null && this.encoding.length() > 0)
				format.setEncoding(this.encoding);

			writer.write(document);
			writer.close();
		}
	}

	/**
	 * Writes the content.
	 *
	 * @param node Instance that contains the content.
	 * @throws IOException Occurs when was not possible to execute the
	 * operation.
	 * @throws DocumentException Occurs when was not possible to execute the
	 * operation.
	 */
	public void write(XmlNode node) throws DocumentException, IOException{
		if(node != null)
			write(XmlUtil.toString(node));
	}

	/**
	 * Writes the content.
	 *
	 * @param node Instance that contains the content.
	 * @param format Instance that contains the type of the content formatting.
	 * @throws IOException Occurs when was not possible to execute the
	 * operation.
	 * @throws DocumentException Occurs when was not possible to execute the
	 * operation.
	 */
	public void write(XmlNode node, OutputFormat format) throws DocumentException, IOException{
		if(node != null && format != null)
			write(XmlUtil.toString(node), format);
	}

	/**
	 * Returns the stream that will used to write the content.
	 *
	 * @return Instance that contains the stream.
	 */
	public OutputStream getOut(){
		return this.out;
	}

	/**
	 * Defines the stream that will used to write the content.
	 *
	 * @param out Instance that contains the stream.
	 */
	public void setOut(OutputStream out){
		this.out = out;
	}

	/**
	 * Returns the instance that contains the DTD.
	 *
	 * @return Instance that contains the DTD.
	 */
	public DocumentType getDocumentType(){
		return this.documentType;
	}

	/**
	 * Defines the instance that contains the DTD.
	 *
	 * @param documentType Instance that contains the DTD.
	 */
	public void setDocumentType(DocumentType documentType){
		this.documentType = documentType;
	}

	/**
	 * Returns that contains the content encoding.
	 *
	 * @return String that contains the encoding.
	 */
	public String getEncoding(){
		return this.encoding;
	}

	/**
	 * Defines that contains the content encoding.
	 *
	 * @param encoding String that contains the encoding.
	 */
	public void setEncoding(String encoding){
		this.encoding = encoding;
	}
}