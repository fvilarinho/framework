package br.com.concepting.framework.resources;

import br.com.concepting.framework.util.StringUtil;

import java.io.Serial;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Class responsible to store resources in text format.
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
public class PropertiesResources extends BaseResources<ResourceBundle>{
    @Serial
    private static final long serialVersionUID = -2809340386475717260L;
    
    /**
     * Constructor - Initializes the resources.
     *
     * @param properties Instance that contains the resources.
     */
    public PropertiesResources(ResourceBundle properties){
        super();
        
        setContent(properties);
    }
    
    /**
     * Returns a property value.
     *
     * @param propertyId String that contains the identifier of the property.
     * @param parameters List that contains the parameters of the property.
     * @param returnInvalidIdentifier Indicates if the return should be null if
     * the property is invalid.
     * @return String that contains the value of the property.
     */
    public String getProperty(String propertyId, Object[] parameters, boolean returnInvalidIdentifier){
        String value = getProperty(propertyId, returnInvalidIdentifier);
        
        if(parameters != null && parameters.length > 0 && value != null && !value.isEmpty()){
            StringBuilder replaceValueBuffer = new StringBuilder();
            
            for(int cont = 0; cont < parameters.length; cont++){
                if(!replaceValueBuffer.isEmpty())
                    replaceValueBuffer.delete(0, replaceValueBuffer.length());
                
                replaceValueBuffer.append("{");
                replaceValueBuffer.append(cont);
                replaceValueBuffer.append("}");
                
                value = StringUtil.replaceAll(value, replaceValueBuffer.toString(), parameters[cont].toString());
            }
        }
        
        return value;
    }
    
    /**
     * Returns a property value.
     *
     * @param propertyId String that contains the identifier of the property.
     * @param parameters List that contains the parameters of the property.
     * @return String that contains the value of the property.
     */
    public String getProperty(String propertyId, Object[] parameters){
        return getProperty(propertyId, parameters, true);
    }
    
    /**
     * Returns a property value.
     *
     * @param propertyId String that contains the identifier of the property.
     * @param returnInvalidIdentifier Indicates if the return should be null or if
     * the property is invalid.
     * @return String that contains the value of the property.
     */
    public String getProperty(String propertyId, boolean returnInvalidIdentifier){
        String value = null;
        
        if(propertyId != null && !propertyId.isEmpty()){
            ResourceBundle properties = getContent();
            
            try{
                value = properties.getString(propertyId);
            }
            catch(MissingResourceException e){
                if(returnInvalidIdentifier){
                    StringBuilder propertyIdBuffer = new StringBuilder();
                    
                    propertyIdBuffer.append("???");
                    propertyIdBuffer.append(propertyId);
                    propertyIdBuffer.append("???");
                    
                    value = propertyIdBuffer.toString();
                }
            }
        }
        
        return value;
    }
    
    /**
     * Returns a property value.
     *
     * @param propertyId String that contains the identifier of the property.
     * @return String that contains the value of the property.
     */
    public String getProperty(String propertyId){
        return getProperty(propertyId, true);
    }
}