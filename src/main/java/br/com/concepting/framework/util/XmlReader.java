package br.com.concepting.framework.util;

import br.com.concepting.framework.util.constants.XmlConstants;
import br.com.concepting.framework.util.helpers.XmlNode;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.DocumentType;
import org.dom4j.io.SAXReader;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Class responsible to read/parse an XML content.
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
public class XmlReader{
    private XmlNode root = null;
    private DocumentType documentType = null;
    private String encoding = null;
    
    /**
     * Constructor - Reads a content.
     *
     * @param file Instance that contains the file.
     * @throws IOException Occurs when was not possible to read the content.
     */
    public XmlReader(File file) throws IOException{
        this(file, false);
    }
    
    /**
     * Constructor - Reads a content.
     *
     * @param file Instance that contains the file.
     * @param validate Indicates if the content validation must be done.
     * @throws IOException Occurs when was not possible to read the content.
     */
    public XmlReader(File file, boolean validate) throws IOException{
        this(new FileInputStream(file), validate);
    }
    
    /**
     * Constructor - Reads a content.
     *
     * @param stream Instance that contains the content.
     * @throws IOException Occurs when was not possible to read the content.
     */
    public XmlReader(InputStream stream) throws IOException{
        this(stream, false);
    }
    
    /**
     * Constructor - Reads a content.
     *
     * @param stream Instance that contains the content.
     * @param validate Indicates if the content validation must be done.
     * @throws IOException Occurs when was not possible to read the content.
     */
    public XmlReader(InputStream stream, boolean validate) throws IOException{
        super();
        
        if(stream != null){
            try{
                SAXReader reader = new SAXReader(validate);
                
                try{
                    reader.setFeature(XmlConstants.DEFAULT_APACHE_VALIDATION_FEATURE_ID, validate);
                    reader.setFeature(XmlConstants.DEFAULT_SAX_VALIDATION_FEATURE_ID, validate);
                }
                catch(SAXException ignored){
                }
                
                parseDocument(reader.read(stream));
            }
            catch(DocumentException e){
                throw new IOException(e);
            }
        }
    }

    /**
     * Constructor - Parses a content.
     *
     * @param value String of the content.
     * @throws IOException Occurs when was not possible to parse the
     * content.
     */
    public XmlReader(byte[] value) throws IOException{
        this(new String(value));
    }

    /**
     * Constructor - Parses a content.
     *
     * @param value String of the content.
     * @throws IOException Occurs when was not possible to parse the
     * content.
     */
    public XmlReader(String value) throws IOException{
        try{
            if(value != null && !value.isEmpty())
                parseDocument(DocumentHelper.parseText(value));
        }
        catch(DocumentException e){
            throw new IOException(e);
        }
    }
    
    /**
     * Parses a content.
     *
     * @param document Instance that contains the content.
     */
    private void parseDocument(Document document){
        if(document != null){
            setDocumentType(document.getDocType());
            setEncoding(document.getXMLEncoding());
            setRoot(new XmlNode(document.getRootElement()));
        }
    }
    
    /**
     * Returns the instance of the root node.
     *
     * @return Instance that contains the root node.
     */
    public XmlNode getRoot(){
        return this.root;
    }
    
    /**
     * Returns the DTD.
     *
     * @return Instance that contains the DTD.
     */
    public DocumentType getDocumentType(){
        return this.documentType;
    }
    
    /**
     * Defines the DTD.
     *
     * @param documentType Instance that contains the DTD.
     */
    public void setDocumentType(DocumentType documentType){
        this.documentType = documentType;
    }
    
    /**
     * Returns the encoding.
     *
     * @return String that contains the encoding.
     */
    public String getEncoding(){
        return this.encoding;
    }
    
    /**
     * Defines the encoding.
     *
     * @param encoding String that contains the encoding.
     */
    public void setEncoding(String encoding){
        this.encoding = encoding;
    }
    
    /**
     * Defines the instance of the root node.
     *
     * @param root Instance that contains the root node.
     */
    public void setRoot(XmlNode root){
        this.root = root;
    }
}