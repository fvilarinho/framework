package br.com.concepting.framework.util.helpers;

import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.util.PropertyUtil;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.XmlUtil;
import org.dom4j.Attribute;
import org.dom4j.Element;

import java.util.List;
import java.util.Map;

/**
 * Class that defines a XML node.
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
public class XmlNode extends Node{
    private static final long serialVersionUID = 3468250465462747310L;
    
    private String namespace = null;
    private String name = null;
    private String value = null;
    private String text = null;
    private String body = null;
    private Map<String, String> attributes = null;
    
    /**
     * Constructor - Initializes the node.
     */
    public XmlNode(){
        super();
    }
    
    /**
     * Constructor - Initializes the node.
     *
     * @param name String that contains the identifier.
     */
    public XmlNode(String name){
        this();
        
        setName(name);
    }
    
    /**
     * Constructor - Initializes the node.
     *
     * @param name String that contains the identifier.
     * @param value String that contains the value.
     */
    public XmlNode(String name, String value){
        this(name);
        
        setValue(value);
    }
    
    /**
     * Constructor - Initializes the node.
     *
     * @param node Instance that contains the node data.
     * @param index Numeric value that contains the node index.
     * @param level Numeric value that contains the node level in the tree data
     * structure.
     */
    public XmlNode(Element node, Integer index, Integer level){
        this();
        
        setNamespace(node.getNamespacePrefix());
        setName(node.getName());
        setBody(XmlUtil.removeNamespaces(node.asXML()));
        setText(buildText(node));
        setValue(StringUtil.replaceAll(node.getText(), "\t", ""));
        setLevel(level);
        setIndex(index);
        
        List<Attribute> attributes = node.attributes();
        
        if(attributes != null && attributes.size() > 0)
            buildAttributes(attributes);
        
        buildChildren(node);
    }
    
    /**
     * Builds the node text.
     *
     * @param node Instance that contains the node data.
     * @return String that contains the text.
     */
    private String buildText(Element node){
        String text = null;
        
        if(node != null){
            text = XmlUtil.removeNamespaces(node.asXML());
            
            if(text != null && text.length() > 0){
                int pos = text.indexOf(">");
                
                if(pos >= 0)
                    text = text.substring(pos + 1);
                
                pos = text.lastIndexOf("</");
                
                if(pos >= 0)
                    text = text.substring(0, pos);
                
                if(text.length() > 0){
                    int asc = text.charAt(0);
                    
                    if(asc == 10)
                        text = text.substring(1);
                    
                    if(text.length() > 0){
                        asc = text.charAt(text.length() - 1);
                        
                        if(asc == 10)
                            text = text.substring(0, text.length() - 1);
                    }
                }
            }
        }
        
        return text;
    }
    
    /**
     * Builds the attributes mapping.
     *
     * @param attributes List that contains the attributes mapping.
     */
    private void buildAttributes(List<Attribute> attributes){
        if(attributes == null || attributes.size() == 0)
            return;
        
        this.attributes = PropertyUtil.instantiate(Constants.DEFAULT_MAP_CLASS);

        for(Attribute attribute: attributes){
            String attributeName;
            String attributeValue;

            if(attribute.getNamespacePrefix() != null && attribute.getNamespacePrefix().length() > 0){
                String namespaceUri = attribute.getNamespace().asXML();
                int pos = namespaceUri.indexOf("=");
                
                if(pos >= 0){
                    attributeName = namespaceUri.substring(0, pos);
                    attributeValue = StringUtil.replaceAll(namespaceUri.substring(pos + 1), "\"", "");
                    
                    this.attributes.put(attributeName, attributeValue);
                }
                
                StringBuilder buffer = new StringBuilder();
                
                buffer.append(attribute.getNamespacePrefix());
                buffer.append(":");
                buffer.append(attribute.getName());
                
                attributeName = buffer.toString();
            }
            else
                attributeName = attribute.getName();
            
            attributeValue = attribute.getValue();
            
            this.attributes.put(attributeName, attributeValue);
        }
    }
    
    /**
     * Builds the children.
     *
     * @param node Instance that contains the node data.
     */
    private void buildChildren(Element node){
        if(node != null){
            List<Element> nodes = node.elements();
            
            if(nodes != null && !nodes.isEmpty()){
                Integer index = 0;
                Integer level = getLevel();
                
                for(Element item: nodes){
                    if(item.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE){
                        XmlNode childNode = new XmlNode(item, index, level + 1);
                        
                        addChild(childNode);
                        
                        index++;
                    }
                }
            }
        }
    }
    
    /**
     * Returns the node namespace.
     *
     * @return String that contains the namespace.
     */
    public String getNamespace(){
        return this.namespace;
    }
    
    /**
     * Defines the node namespace.
     *
     * @param namespace String that contains the namespace.
     */
    public void setNamespace(String namespace){
        this.namespace = namespace;
    }
    
    /**
     * Returns the node text.
     *
     * @return String that contains the text.
     */
    public String getText(){
        return this.text;
    }
    
    /**
     * Defines the node text.
     *
     * @param text String that contains the text.
     */
    public void setText(String text){
        this.text = text;
    }
    
    /**
     * Returns the node value.
     *
     * @return String that contains the value.
     */
    public String getValue(){
        return this.value;
    }
    
    /**
     * Defines the node value.
     *
     * @param value String that contains the value.
     */
    public void setValue(String value){
        this.value = value;
    }
    
    /**
     * Returns the node body.
     *
     * @return String that contains the body.
     */
    public String getBody(){
        return this.body;
    }
    
    /**
     * Defines the node body.
     *
     * @param body String that contains the body.
     */
    public void setBody(String body){
        this.body = body;
    }
    
    /**
     * Returns the node identifier.
     *
     * @return String that contains the identifier.
     */
    public String getName(){
        return this.name;
    }
    
    /**
     * Defines the node identifier.
     *
     * @param name String that contains the identifier.
     */
    public void setName(String name){
        this.name = name;
    }
    
    /**
     * Returns the instance of a node using the XPath.
     *
     * @param xpath String that contains the XPath.
     * @return Instance that contains the node.
     */
    public XmlNode getNode(String xpath){
        String[] xpathParts = StringUtil.split(xpath, ".");
        List<XmlNode> nodes = getChildren();
        XmlNode node = null;
        
        if(nodes != null && xpathParts != null && xpathParts.length > 0){
            for (String xpathPart : xpathParts) {
                for (XmlNode item : nodes) {
                    node = item;

                    if (item.getName() != null && item.getName().equals(xpathPart)) {
                        if (item.hasChildren())
                            nodes = item.getChildren();

                        break;
                    }

                    node = null;
                }
            }
        }
        
        return node;
    }
    
    /**
     * Returns the instance of a node using the index.
     *
     * @param index Numeric value that contains the index.
     * @return Instance that contains the node.
     */
    public XmlNode getNode(Integer index){
        List<XmlNode> nodes = getChildren();
        
        if(nodes != null && nodes.size() > 0 && index != null && (nodes.size() - 1) >= index)
            return nodes.get(index);
        
        return null;
    }
    
    /**
     * Returns the value of an attribute of the node.
     *
     * @param name String that contains the identifier of the attribute.
     * @return String that contains the value.
     */
    public String getAttribute(String name){
        if(this.attributes == null || this.attributes.size() == 0)
            return null;
        
        return this.attributes.get(name);
    }
    
    /**
     * Adds a new attribute.
     *
     * @param name String that contains the identifier of the attribute.
     * @param value String that contains the value.
     */
    public void addAttribute(String name, String value){
        if(name != null && name.length() > 0 && value != null){
            if(this.attributes == null)
                this.attributes = PropertyUtil.instantiate(Constants.DEFAULT_MAP_CLASS);
            
            this.attributes.put(name, value);
        }
    }
    
    /**
     * Returns the attributes mapping.
     *
     * @return Instance that contains the attributes mapping.
     */
    public Map<String, String> getAttributes(){
        return this.attributes;
    }
    
    /**
     * Defines the attributes mapping.
     *
     * @param attributes Instance that contains the attributes mapping.
     */
    public void setAttributes(Map<String, String> attributes){
        this.attributes = attributes;
    }
}