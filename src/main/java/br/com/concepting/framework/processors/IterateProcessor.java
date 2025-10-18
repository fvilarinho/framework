package br.com.concepting.framework.processors;

import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.processors.constants.ProcessorConstants;
import br.com.concepting.framework.util.PropertyUtil;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.helpers.XmlNode;

import java.util.Collection;
import java.util.Locale;

/**
 * Class that defines the logic processor to iterate lists.
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
public class IterateProcessor extends EvaluateProcessor{
    private String name = null;
    private String index = null;
    private int start = 0;
    private int end = 0;
    
    /**
     * Constructor - Defines the logic processor.
     */
    public IterateProcessor(){
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
    public IterateProcessor(String domain, Object declaration, XmlNode content, Locale language){
        super(domain, declaration, content, language);
    }
    
    /**
     * Returns the iteration start index.
     *
     * @return Numeric value that contains the start index.
     */
    public int getStart(){
        return this.start;
    }
    
    /**
     * Defines the iteration start index.
     *
     * @param start Numeric value that contains the start index.
     */
    public void setStart(int start){
        this.start = start;
    }
    
    /**
     * Returns the iteration end index.
     *
     * @return Numeric value that contains the start index.
     */
    public int getEnd(){
        return this.end;
    }
    
    /**
     * Defines the iteration end index.
     *
     * @param end Numeric value that contains the start index.
     */
    public void setEnd(int end){
        this.end = end;
    }
    
    /**
     * Returns the identifier of the iteration variable.
     *
     * @return String that contains the identifier.
     */
    public String getName(){
        return this.name;
    }
    
    /**
     * Defines the identifier of the iteration variable.
     *
     * @param name String that contains the identifier.
     */
    public void setName(String name){
        this.name = name;
    }
    
    /**
     * Returns the identifier of the iteration index.
     *
     * @return String that contains the identifier.
     */
    public String getIndex(){
        return this.index;
    }
    
    /**
     * Defines the identifier of the iteration index.
     *
     * @param index String that contains the identifier.
     */
    public void setIndex(String index){
        this.index = index;
    }

    @Override
    protected boolean returnContent(){
        return true;
    }

    @Override
    public String process() throws InternalErrorException{
        Object value = evaluate();
        Object[] array = null;

        if(PropertyUtil.isCollection(value))
            array = ((Collection<?>) value).toArray();
        else if(PropertyUtil.isArray(value))
            array = (Object[]) value;
        
        StringBuilder buffer = new StringBuilder();
        
        if(array != null && array.length > 0){
            setValue(null);

            if(this.end == 0)
                this.end = array.length;

            for(int cont = this.start; cont < array.length; cont++){
                if(cont > this.end)
                    break;
                
                Object item = array[cont];
                
                if(this.name != null && !this.name.isEmpty())
                    setVariable(this.name, item);
                
                if(this.index != null && !this.index.isEmpty())
                    setVariable(this.index, cont);
                
                setDeclaration(item);
                
                String result = StringUtil.trim(super.process());
                
                if(!result.isEmpty()){
                    if(buffer.length() > 0)
                        buffer.append(StringUtil.getLineBreak());
                    
                    buffer.append(result);
                }
            }
        }
        
        if(buffer.length() == 0)
            return ProcessorConstants.DEFAULT_REMOVE_TAG_ID;
        
        return buffer.toString();
    }
}