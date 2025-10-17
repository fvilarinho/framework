package br.com.concepting.framework.util;

import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.util.helpers.TagIndent;
import br.com.concepting.framework.util.helpers.XmlNode;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Class responsible to manipulate XML content.
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
 * along with this program.  If not, see <a href="http://www.gnu.org/licenses"></a>.</pre>
 */
public class XmlUtil{
    private static XmlMapper mapper = null;

    @SuppressWarnings("deprecation")
    public static XmlMapper getMapper(){
        if(mapper == null){
            mapper = new XmlMapper();
            mapper.registerModule(new Hibernate5Module());
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            mapper.enable(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS);
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            mapper.setDateFormat(new SimpleDateFormat(Constants.DEFAULT_DATE_TIME_PATTERN));
        }

        return mapper;
    }

    /**
     * Removes the default namespaces.
     *
     * @param value String that contains the default namespaces.
     * @return String without default namespaces.
     */
    public static String removeNamespaces(String value){
        if(value != null && !value.isEmpty()){
            value = StringUtil.replaceAll(value, " xmlns:concepting=\"default namespace\"", "");
            value = StringUtil.replaceAll(value, " xmlns:c=\"default namespace\"", "");
            value = StringUtil.replaceAll(value, " xmlns:jsp=\"default namespace\"", "");
        }
        
        return value;
    }
    
    /**
     * Transforms a XML content into a string.
     *
     * @param node Instance that contains the XML content.
     * @return String that contains the XML content.
     * @throws UnsupportedEncodingException Occurs when was not possible to execute the operation.
     */
    public static String toString(XmlNode node) throws UnsupportedEncodingException{
        if(node != null)
            return toString(node, false);

        return StringUtils.EMPTY;
    }
    
    /**
     * Transforms a XML content into a string.
     *
     * @param node Instance that contains the XML content.
     * @param encode Indicates if the content should be encoded.
     * @return String that contains the XML content.
     * @throws UnsupportedEncodingException Occurs when was not possible to execute the operation.
     */
    public static String toString(XmlNode node, boolean encode) throws UnsupportedEncodingException{
        if(node != null)
            return toString(node, encode, LanguageUtil.getDefaultLanguage());

        return StringUtils.EMPTY;
    }
    
    /**
     * Transforms a XML content into a string.
     *
     * @param node Instance that contains the XML content.
     * @param language Instance that contains the language that will be used.
     * @return String that contains the XML content.
     * @throws UnsupportedEncodingException Occurs when was not possible to execute the operation.
     */
    public static String toString(XmlNode node, Locale language) throws UnsupportedEncodingException{
        if(node != null)
            return toString(node, false, language);

        return StringUtils.EMPTY;
    }
    
    /**
     * Transforms a XML content into a string.
     *
     * @param node Instance that contains the XML content.
     * @param encode Indicates if the content should be encoded.
     * @param language Instance that contains the language that will be used.
     * @return String that contains the XML content.
     * @throws UnsupportedEncodingException Occurs when was not possible to execute the operation.
     */
    public static String toString(XmlNode node, boolean encode, Locale language) throws UnsupportedEncodingException{
        if(node != null){
            if(language == null)
                return toString(node, encode);
            
            StringBuilder buffer = new StringBuilder();
            
            buffer.append("<");
            
            if(node.getNamespace() != null && !node.getNamespace().isEmpty()){
                buffer.append(node.getNamespace());
                buffer.append(":");
            }
            
            buffer.append(node.getName());
            
            Map<String, String> attributes = node.getAttributes();
            
            if(attributes != null && !attributes.isEmpty()){
                for(Entry<String, String> entry: attributes.entrySet()){
                    buffer.append(" ");
                    buffer.append(entry.getKey());
                    buffer.append("=\"");
                    
                    String value = PropertyUtil.format(entry.getValue(), language);
                    
                    if(value != null && !value.isEmpty() && encode)
                        value = URLEncoder.encode(value, Constants.DEFAULT_UNICODE_ENCODING);
                    
                    if(value != null && !value.isEmpty())
                        buffer.append(value);
                    
                    buffer.append("\"");
                }
            }
            
            buffer.append(">");
            buffer.append(StringUtil.getLineBreak());
            
            String value = PropertyUtil.format(node.getValue(), language);
            
            if(value != null && !value.isEmpty() && encode)
                value = URLEncoder.encode(value, Constants.DEFAULT_UNICODE_ENCODING);
            
            if(value != null && !value.isEmpty()){
                buffer.append(value);
                
                buffer.append(StringUtil.getLineBreak());
            }
            
            List<XmlNode> children = node.getChildren();
            
            if(children != null && !children.isEmpty()){
                for(XmlNode childNode: children){
                    String childBuffer = toString(childNode, encode, language);
                    
                    if(childBuffer != null && !childBuffer.isEmpty())
                        buffer.append(childBuffer);
                }
            }
            
            buffer.append("</");
            
            if(node.getNamespace() != null && !node.getNamespace().isEmpty()){
                buffer.append(node.getNamespace());
                buffer.append(":");
            }
            
            buffer.append(node.getName());
            buffer.append(">");
            buffer.append(StringUtil.getLineBreak());
            
            return buffer.toString();
        }
        
        return StringUtils.EMPTY;
    }
    
    /**
     * Transforms a string into XML content.
     *
     * @param value String that contains the XML.
     * @return Instance that contains the XML content.
     * @throws DocumentException Occurs when was not possible to parse the content.
     */
    public static XmlNode parseString(String value) throws DocumentException{
        if(value != null && !value.isEmpty())
            return parseString(value, Constants.DEFAULT_UNICODE_ENCODING);
        
        return null;
    }
    
    /**
     * Transforms a string into XML content.
     *
     * @param value String that contains the XML content.
     * @param encoding String that contains the content encoding.
     * @return Instance that contains the XML content.
     * @throws DocumentException Occurs when was not possible to parse the content.
     */
    public static XmlNode parseString(String value, String encoding) throws DocumentException{
        if(value != null && !value.isEmpty()){
            Document document = DocumentHelper.parseText(StringEscapeUtils.unescapeXml(value));
            
            if(encoding == null || encoding.isEmpty())
                encoding = Constants.DEFAULT_UNICODE_ENCODING;
            
            document.setXMLEncoding(encoding);
            
            return new XmlNode(document.getRootElement(), 0, 0);
        }
        
        return null;
    }
    
    /**
     * Indents a XML content.
     *
     * @param value String that contains the XML content.
     * @return String after the indentation.
     * @throws IOException Occurs when was not possible to execute the operation.
     * @throws InstantiationException Occurs when was not possible to execute the operation.
     * @throws InvocationTargetException Occurs when was not possible to execute the operation.
     * @throws IllegalAccessException Occurs when was not possible to execute the operation.
     * @throws NoSuchMethodException Occurs when was not possible to execute the operation
     */
    public static String indent(String value) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException{
        if(value != null && !value.isEmpty())
            return StringUtil.indent(value, TagIndent.getRules());
        
        return StringUtils.EMPTY;
    }

    /**
     * Serialize an object.
     *
     * @param object Instance of the object.
     * @return String that contains the serialized object in XML format.
     * @throws IOException Occurs when was not possible to deserialize.
     */
    public static String serialize(Object object) throws IOException {
        if(object == null)
            return StringUtils.EMPTY;

        XmlMapper mapper = getMapper();

        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
    }

    /**
     * Deserialize an object.
     *
     * @param value String that contains the serialized object in JSON format.
     * @param clazz Class that defines the object.
     * @return Instance of the object.
     * @throws IOException Occurs when was not possible to deserialize.
     */
    public static <O> O deserialize(String value, Class<O> clazz) throws ClassNotFoundException, IOException {
        if (value == null || value.isEmpty())
            return null;

        return deserialize(value.getBytes(), clazz);
    }

    /**
     * Deserialize an object.
     *
     * @param value Byte array that contains the serialized object in JSON format.
     * @param clazz Class that defines the object.
     * @return Instance of the object.
     * @throws IOException Occurs when was not possible to deserialize.
     */
    @SuppressWarnings("unchecked")
    public static <O> O deserialize(byte[] value, Class<O> clazz) throws ClassNotFoundException, IOException{
        if(value == null || value.length == 0 || clazz == null)
            return null;

        XmlReader reader = new XmlReader(value);
        XmlNode rootNode = reader.getRoot();
        String rootClassName = rootNode.getAttribute("class");

        if(rootClassName != null && !rootClassName.isEmpty())
            clazz = (Class<O>)Class.forName(rootClassName);

        XmlMapper mapper = getMapper();

        return mapper.readValue(value, clazz);
    }
}