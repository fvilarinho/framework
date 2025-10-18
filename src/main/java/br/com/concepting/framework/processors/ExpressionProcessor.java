package br.com.concepting.framework.processors;

import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.processors.constants.ProcessorConstants;
import br.com.concepting.framework.util.helpers.XmlNode;

import java.util.Locale;

/**
 * Class that defines the logic processor to check expressions.
 *
 * @author fvilarinho
 * @since 1.0.0
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
public class ExpressionProcessor extends EvaluateProcessor{
    /**
     * Constructor - Defines the logic processor.
     */
    public ExpressionProcessor(){
        super();
    }
    
    /**
     * Constructor - Defines the logic processor.
     *
     * @param domain String that contains the domain of the processing.
     */
    public ExpressionProcessor(String domain){
        super(domain);
    }
    
    /**
     * Constructor - Defines the logic processor.
     *
     * @param language Instance that contains the language used in the
     * processing.
     */
    public ExpressionProcessor(Locale language){
        super(language);
    }
    
    /**
     * Constructor - Defines the logic processor.
     *
     * @param domain String that contains the domain of the processing.
     * @param language Instance that contains the language used in the
     * processing.
     */
    public ExpressionProcessor(String domain, Locale language){
        super(domain, language);
    }
    
    /**
     * Constructor - Defines the logic processor.
     *
     * @param declaration Instance that contains the object that will be
     * processed.
     */
    public ExpressionProcessor(Object declaration){
        super(declaration);
    }
    
    /**
     * Constructor - Defines the logic processor.
     *
     * @param domain String that contains the domain of the processing.
     * @param declaration Instance that contains the object that will be
     * processed.
     */
    public ExpressionProcessor(String domain, Object declaration){
        super(domain, declaration);
    }
    
    /**
     * Constructor - Defines the logic processor.
     *
     * @param declaration Instance that contains the object that will be
     * processed.
     * @param language Instance that contains the language used in the
     * processing.
     */
    public ExpressionProcessor(Object declaration, Locale language){
        super(declaration, language);
    }
    
    /**
     * Constructor - Defines the logic processor.
     *
     * @param domain String that contains the domain of the processing.
     * @param declaration Instance that contains the object that will be
     * processed.
     * @param language Instance that contains the language used in the
     * processing.
     */
    public ExpressionProcessor(String domain, Object declaration, Locale language){
        super(domain, declaration, language);
    }
    
    /**
     * Constructor - Defines the logic processor.
     *
     * @param domain String that contains the domain of the processing.
     * @param declaration Instance that contains the object that will be
     * processed.
     * @param content Instance that contains the XML content.
     */
    public ExpressionProcessor(String domain, Object declaration, XmlNode content){
        super(domain, declaration, content);
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
    public ExpressionProcessor(String domain, Object declaration, XmlNode content, Locale language){
        super(domain, declaration, content, language);
    }

    @Override
    protected boolean returnContent(){
        return true;
    }

    @Override
    public String process() throws InternalErrorException{
        try{
            boolean result = super.evaluate();
            
            if(result)
                return super.process();
        }
        catch(ClassCastException ignored){
        }
        
        return ProcessorConstants.DEFAULT_REMOVE_TAG_ID;
    }
}