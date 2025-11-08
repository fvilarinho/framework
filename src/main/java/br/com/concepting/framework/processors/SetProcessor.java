package br.com.concepting.framework.processors;

import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.processors.constants.ProcessorConstants;
import br.com.concepting.framework.util.helpers.XmlNode;

import java.util.Locale;

/**
 * Class that defines the logic processor to declare variables.
 *
 * @author fvilarinho
 * @since 3.0.0
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
public class SetProcessor extends EvaluateProcessor{
    private String name = null;
    
    /**
     * Constructor - Defines the logic processor.
     */
    public SetProcessor(){
        super();
    }
    
    /**
     * Constructor - Defines the logic processor.
     *
     * @param declaration Instance that contains the object that will be
     * processed.
     */
    public SetProcessor(Object declaration){
        super(declaration);
    }
    
    /**
     * Constructor - Defines the logic processor.
     *
     * @param domain String that contains the domain of the processing.
     * @param declaration Instance that contains the object that will be
     * processed.
     */
    public SetProcessor(String domain, Object declaration){
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
    public SetProcessor(Object declaration, Locale language){
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
    public SetProcessor(String domain, Object declaration, Locale language){
        super(domain, declaration, language);
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
    public SetProcessor(String domain, Object declaration, XmlNode content, Locale language){
        super(domain, declaration, content, language);
    }
    
    /**
     * Constructor - Defines the logic processor.
     *
     * @param declaration Instance that contains the object that will be
     * processed.
     * @param content Instance that contains the XML content.
     * @param language Instance that contains the language used in the
     * processing.
     */
    public SetProcessor(Object declaration, XmlNode content, Locale language){
        super(ExpressionProcessorUtil.class.getName(), declaration, content, language);
    }
    
    /**
     * Returns the identifier of the variable.
     *
     * @return String that contains the identifier.
     */
    public String getName(){
        return this.name;
    }
    
    /**
     * Defines the identifier of the variable.
     *
     * @param name String that contains the identifier.
     */
    public void setName(String name){
        this.name = name;
    }

    @Override
    public <O> O evaluate() throws InternalErrorException{
        O value = super.evaluate();

        setVariable(this.name, value);

        return value;
    }

    @Override
    public String process() throws InternalErrorException{
        evaluate();
        
        return ProcessorConstants.DEFAULT_REMOVE_TAG_ID;
    }
}