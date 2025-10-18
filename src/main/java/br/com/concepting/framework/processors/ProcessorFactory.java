package br.com.concepting.framework.processors;

import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.processors.util.ProcessorUtil;
import br.com.concepting.framework.util.LanguageUtil;
import br.com.concepting.framework.util.PropertyUtil;
import br.com.concepting.framework.util.XmlUtil;
import br.com.concepting.framework.util.helpers.XmlNode;
import org.apache.commons.beanutils.ConstructorUtils;
import org.dom4j.DocumentException;

import java.lang.reflect.InvocationTargetException;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Class that defines a factory of logic processors used in the code generation.
 *
 * @author fvilarinho
 * @since 2.0.0
 *
 * <pre>Copyright (C) 2007 Concepting Inc.
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
public class ProcessorFactory{
    private static ProcessorFactory instance = null;
    
    /**
     * Constructor - Initializes the processor factory.
     */
    private ProcessorFactory(){
        super();
    }
    
    /**
     * Returns the instance of the processor factory.
     *
     * @return Instance that contains the processor factory.
     */
    public static ProcessorFactory getInstance(){
        if(instance == null)
            instance = new ProcessorFactory();
        
        return instance;
    }
    
    /**
     * Returns a logic processor based on XML content.
     *
     * @param <L> Class that defines the logic processor.
     * @param declaration Instance that contains the object that will be
     * processed.
     * @param content String that contains the XML content.
     * @return Instance if the logic processor.
     * @throws InternalErrorException Occurs when was not possible to execute
     * the operation.
     */
    public <L extends GenericProcessor> L getProcessor(Object declaration, String content) throws InternalErrorException{
        return getProcessor(ExpressionProcessorUtil.class.getName(), declaration, content);
    }
    
    /**
     * Returns a logic processor based on XML content.
     *
     * @param <L> Class that defines the logic processor.
     * @param declaration Instance that contains the object that will be
     * processed.
     * @param contentNode Instance that contains the XML content.
     * @return Instance if the logic processor.
     * @throws InternalErrorException Occurs when was not possible to execute
     * the operation.
     */
    public <L extends GenericProcessor> L getProcessor(Object declaration, XmlNode contentNode) throws InternalErrorException{
        return getProcessor(ExpressionProcessorUtil.class.getName(), declaration, contentNode);
    }
    
    /**
     * Returns a logic processor based on XML content.
     *
     * @param <L> Class that defines the logic processor.
     * @param domain String that contains the domain of the processing.
     * @param declaration Instance that contains the object that will be
     * processed.
     * @param content String that contains the XML content.
     * @return Instance if the logic processor.
     * @throws InternalErrorException Occurs when was not possible to execute
     * the operation.
     */
    public <L extends GenericProcessor> L getProcessor(String domain, Object declaration, String content) throws InternalErrorException{
        return getProcessor(domain, declaration, content, LanguageUtil.getDefaultLanguage());
    }
    
    /**
     * Returns a logic processor based on XML content.
     *
     * @param <L> Class that defines the logic processor.
     * @param domain String that contains the domain of the processing.
     * @param declaration Instance that contains the object that will be
     * processed.
     * @param contentNode Instance that contains the XML content.
     * @return Instance if the logic processor.
     * @throws InternalErrorException Occurs when was not possible to execute
     * the operation.
     */
    public <L extends GenericProcessor> L getProcessor(String domain, Object declaration, XmlNode contentNode) throws InternalErrorException{
        return getProcessor(domain, declaration, contentNode, LanguageUtil.getDefaultLanguage());
    }
    
    /**
     * Returns a logic processor based on XML content.
     *
     * @param <L> Class that defines the logic processor.
     * @param declaration Instance that contains the object that will be
     * processed.
     * @param content String that contains the XML content.
     * @param language Instance that contains the language used in the
     * processing.
     * @return Instance if the logic processor.
     * @throws InternalErrorException Occurs when was not possible to execute
     * the operation.
     */
    public <L extends GenericProcessor> L getProcessor(Object declaration, String content, Locale language) throws InternalErrorException{
        return getProcessor(ExpressionProcessorUtil.class.getName(), declaration, content, language);
    }
    
    /**
     * Returns a logic processor based on XML content.
     *
     * @param <L> Class that defines the logic processor.
     * @param declaration Instance that contains the object that will be
     * processed.
     * @param contentNode Instance that contains the XML content.
     * @param language Instance that contains the language used in the
     * processing.
     * @return Instance if the logic processor.
     * @throws InternalErrorException Occurs when was not possible to execute
     * the operation.
     */
    public <L extends GenericProcessor> L getProcessor(Object declaration, XmlNode contentNode, Locale language) throws InternalErrorException{
        return getProcessor(ExpressionProcessorUtil.class.getName(), declaration, contentNode, language);
    }
    
    /**
     * Returns a logic processor based on XML content.
     *
     * @param <L> Class that defines the logic processor.
     * @param domain String that contains the domain of the processing.
     * @param declaration Instance that contains the object that will be
     * processed.
     * @param content String that contains the XML content.
     * @param language Instance that contains the language used in the
     * processing.
     * @return Instance if the logic processor.
     * @throws InternalErrorException Occurs when was not possible to execute
     * the operation.
     */
    public <L extends GenericProcessor> L getProcessor(String domain, Object declaration, String content, Locale language) throws InternalErrorException{
        try{
            StringBuilder contentBuffer = new StringBuilder();
            
            contentBuffer.append("<root>");
            contentBuffer.append(content);
            contentBuffer.append("</root>");
            
            XmlNode contentNode = XmlUtil.parseString(contentBuffer.toString());
            
            return getProcessor(domain, declaration, contentNode, language);
        }
        catch(DocumentException e){
            throw new InternalErrorException(e);
        }
    }
    
    /**
     * Returns a logic processor based on XML content.
     *
     * @param <L> Class that defines the logic processor.
     * @param domain String that contains the domain of the processing.
     * @param declaration Instance that contains the object that will be
     * processed.
     * @param contentNode Instance that contains the XML content.
     * @param language Instance that contains the language used in the
     * processing.
     * @return Instance if the logic processor.
     * @throws InternalErrorException Occurs when was not possible to execute
     * the operation.
     */
    @SuppressWarnings("unchecked")
    public <L extends GenericProcessor> L getProcessor(String domain, Object declaration, XmlNode contentNode, Locale language) throws InternalErrorException{
        Class<?> clazz = ProcessorUtil.getClass(contentNode);

        try{
            if(domain == null)
                domain = ExpressionProcessorUtil.class.getName();
            
            L processor = (L) clazz.getDeclaredConstructor().newInstance();

            processor.setDomain(domain);
            processor.setDeclaration(declaration);
            processor.setContent(contentNode);
            processor.setLanguage(language);

            if(processor.hasLogic()){
                Map<String, String> attributes = contentNode.getAttributes();

                for(Entry<String, String> entry: attributes.entrySet()){
                    String name = ProcessorUtil.getAttributeNameByAlias(entry.getKey());
                    Object value = entry.getValue();

                    try{
                        clazz = processor.getClass();
                        clazz = PropertyUtil.getClass(clazz, name);
                        value = ConstructorUtils.invokeConstructor(clazz, value);

                        PropertyUtil.setValue(processor, name, value);
                    }
                    catch(NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException | IllegalArgumentException | ClassNotFoundException | NoSuchFieldException e){
                        throw new InternalErrorException(e);
                    }
                }
            }

            return processor;
        }
        catch(IllegalAccessException | InstantiationException e){
            throw new InternalErrorException(e);
        }
        catch (InvocationTargetException | NoSuchMethodException e){
            throw new RuntimeException(e);
        }
    }
}