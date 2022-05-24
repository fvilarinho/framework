package br.com.concepting.framework.processors;

import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.processors.constants.ProcessorConstants;
import br.com.concepting.framework.util.PropertyUtil;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.helpers.XmlNode;
import org.apache.commons.text.StringEscapeUtils;

import java.util.List;
import java.util.Locale;

/**
 * Class that defines the basic implementation of a logic processor.
 *
 * @author fvilarinho
 * @since 1.0.0
 *
 * <pre>Copyright (C) 2007 Concepting Inc.
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
public class GenericProcessor{
    private String domain = null;
    private Object declaration = null;
    private XmlNode content = null;
    private Locale language = null;
    
    /**
     * Constructor - Defines the logic processor.
     */
    public GenericProcessor(){
        super();
    }
    
    /**
     * Constructor - Defines the logic processor.
     *
     * @param domain String that contains the domain of the processing.
     * @param declaration Instance that contains the object that will be
     * processed.
     * @param content Instance that contains the XML content.
     * @param language Instance that contains the language used in the
     * processing.
     */
    public GenericProcessor(String domain, Object declaration, XmlNode content, Locale language){
        this();
        
        setDomain(domain);
        setDeclaration(declaration);
        setContent(content);
        setLanguage(language);
    }
    
    /**
     * Indicates if the logic processor returns content.
     *
     * @return True/False.
     */
    protected boolean returnContent(){
        return true;
    }
    
    /**
     * Indicates if it is a logic processor.
     *
     * @return True/False.
     */
    protected boolean hasLogic(){
        return false;
    }
    
    /**
     * Returns the identifier of the domain of the logic processor.
     *
     * @return String that contains the identifier.
     */
    public String getDomain(){
        return this.domain;
    }
    
    /**
     * Defines the identifier of the domain of the logic processor.
     *
     * @param domain String that contains the identifier.
     */
    public void setDomain(String domain){
        this.domain = domain;
    }
    
    /**
     * Returns the instance of the language that will be used in the processing.
     *
     * @return Instance that contains the language.
     */
    public Locale getLanguage(){
        return this.language;
    }
    
    /**
     * Defines the instance of the language that will be used in the processing.
     *
     * @param language Instance that contains the language.
     */
    public void setLanguage(Locale language){
        this.language = language;
    }
    
    /**
     * Returns the instance of the object that will be processed.
     *
     * @return Instance that contains the object.
     */
    public Object getDeclaration(){
        return this.declaration;
    }
    
    /**
     * Defines the instance of the object that will be processed.
     *
     * @param declaration Instance that contains the object.
     */
    public void setDeclaration(Object declaration){
        this.declaration = declaration;
    }
    
    /**
     * Returns the XML content that will be processed.
     *
     * @return Instance that contains the XML content.
     */
    public XmlNode getContent(){
        return this.content;
    }
    
    /**
     * Defines the XML content that will be processed.
     *
     * @param content Instance that contains the XML content.
     */
    public void setContent(XmlNode content){
        this.content = content;
    }
    
    /**
     * Process the XML content.
     *
     * @return String that contains the processing results.
     * @throws InternalErrorException Occurs when there are syntax errors in the
     * content that will be processed.
     */
    public String process() throws InternalErrorException{
        ProcessorFactory processorFactory = ProcessorFactory.getInstance();
        List<?> nodeChildren = this.content.getChildren();
        String nodeText = StringUtil.trim(this.content.getText());
        int cont = 0;
        
        if(nodeChildren != null && !nodeChildren.isEmpty()){
            while(true){
                XmlNode node = this.content.getNode(cont);
                
                if(node == null)
                    break;

                GenericProcessor processor = processorFactory.getProcessor(this.domain, this.declaration, node, this.language);
                String nodeBody = StringUtil.trim(node.getBody());
                String nodeValue = StringUtil.trim(processor.process());
                
                if(processor.hasLogic())
                    nodeText = StringUtil.replaceAll(nodeText, nodeBody, nodeValue);
                else{
                    nodeBody = StringUtil.trim(node.getText());
                    
                    if(!nodeBody.equals(nodeValue))
                        nodeText = StringUtil.replaceAll(nodeText, nodeBody, nodeValue);
                }
                
                cont++;
            }
        }
        
        nodeText = StringEscapeUtils.unescapeXml(nodeText);
        nodeText = StringUtil.trim(nodeText);
        nodeText = StringUtil.replaceAll(nodeText, ProcessorConstants.DEFAULT_REMOVE_TAG_ID.concat(StringUtil.getLineBreak()), "");
        nodeText = StringUtil.replaceAll(nodeText, ProcessorConstants.DEFAULT_REMOVE_TAG_ID, "");
        nodeText = ExpressionProcessorUtil.fillVariablesInString(this.domain, nodeText, false, this.language);
        nodeText = PropertyUtil.fillPropertiesInString(this.declaration, nodeText, false, this.language);
        
        return nodeText;
    }
}