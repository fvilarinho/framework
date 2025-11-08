package br.com.concepting.framework.controller.form.helpers;

import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.controller.form.types.ActionFormMessageType;
import br.com.concepting.framework.util.PropertyUtil;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

/**
 * Class that defines a message of a form.
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
public class ActionFormMessage implements Serializable{
    @Serial
    private static final long serialVersionUID = -7121791309962868261L;
    
    private ActionFormMessageType type = null;
    private String key = null;
    private Map<String, Object> attributes = null;
    
    /**
     * Constructor - Initializes the form message.
     *
     * @param type Instance that contains the message type.
     * @param key String that contains the message identifier.
     */
    public ActionFormMessage(ActionFormMessageType type, String key){
        super();
        
        setKey(key);
        setType(type);
    }
    
    /**
     * Returns the identifier of the message.
     *
     * @return String that contains the identifier.
     */
    public String getKey(){
        return this.key;
    }
    
    /**
     * Defines the identifier of the message.
     *
     * @param key String that contains the identifier.
     */
    public void setKey(String key){
        this.key = key;
    }
    
    /**
     * Returns the instance of the message type.
     *
     * @return Instance that contains the message type.
     */
    public ActionFormMessageType getType(){
        return this.type;
    }
    
    /**
     * Defines the instance of the message type.
     *
     * @param type Instance that contains the message type.
     */
    public void setType(ActionFormMessageType type){
        this.type = type;
    }
    
    /**
     * Adds a message attribute.
     *
     * @param name String that contains the identifier.
     * @param value String that contains the value.
     */
    public void addAttribute(String name, Object value){
        if(name != null && !name.isEmpty() && value != null){
            if(this.attributes == null)
                this.attributes = PropertyUtil.instantiate(Constants.DEFAULT_MAP_CLASS);

            if(this.attributes != null)
                this.attributes.put(name, value);
        }
    }
    
    /**
     * Returns a message attribute.
     *
     * @param <O> Class that defines the value of the attribute.
     * @param name String that contains the identifier.
     * @return String that contains the value.
     */
    @SuppressWarnings("unchecked")
    public <O> O getAttribute(String name){
        if(this.attributes != null && !this.attributes.isEmpty())
            return (O) this.attributes.get(name);
        
        return null;
    }
    
    /**
     * Returns the attributes' mappings.
     *
     * @return Instance that contains the attributes' mappings.
     */
    public Map<String, Object> getAttributes(){
        return this.attributes;
    }
    
    /**
     * Defines the attributes' mappings.
     *
     * @param attributes Instance that contains the attributes' mappings.
     */
    public void setAttributes(Map<String, Object> attributes){
        this.attributes = attributes;
    }
}