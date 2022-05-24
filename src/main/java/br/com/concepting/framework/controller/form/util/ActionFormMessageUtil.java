package br.com.concepting.framework.controller.form.util;

import br.com.concepting.framework.controller.form.helpers.ActionFormMessage;
import br.com.concepting.framework.controller.form.types.ActionFormMessageType;
import br.com.concepting.framework.util.ExceptionUtil;
import br.com.concepting.framework.util.LanguageUtil;
import br.com.concepting.framework.util.PropertyUtil;
import br.com.concepting.framework.util.StringUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class responsible to manipulate form messages.
 *
 * @author fvilarinho
 * @since 3.0.0
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
public class ActionFormMessageUtil{
    /**
     * Creates an information message.
     *
     * @param key String that contains the identifier.
     * @return Instance that contains the message.
     */
    public static ActionFormMessage createInfoMessage(String key){
        if(key != null && key.length() > 0)
            return new ActionFormMessage(ActionFormMessageType.INFO, key);
        
        return null;
    }
    
    /**
     * Creates a warning message.
     *
     * @param key String that contains the identifier.
     * @return Instance that contains the message.
     */
    public static ActionFormMessage createWarningMessage(String key){
        if(key != null && key.length() > 0)
            return new ActionFormMessage(ActionFormMessageType.WARNING, key);
        
        return null;
    }
    
    /**
     * Creates an error message.
     *
     * @param key String that contains the identifier.
     * @return Instance that contains the message.
     */
    public static ActionFormMessage createErrorMessage(String key){
        if(key != null && key.length() > 0)
            return new ActionFormMessage(ActionFormMessageType.ERROR, key);
        
        return null;
    }
    
    /**
     * Creates a message.
     *
     * @param e Instance that contains the caught exception.
     * @return Instance that contains the message.
     */
    public static ActionFormMessage createMessage(Throwable e){
        if(e == null)
            return null;
        
        String key = ExceptionUtil.getId(e);
        
        key = key.substring(0, 1).toLowerCase().concat(key.substring(1));
        
        ActionFormMessageType messageType;
        
        if(ExceptionUtil.isExpectedWarningException(e))
            messageType = ActionFormMessageType.WARNING;
        else
            messageType = ActionFormMessageType.ERROR;
        
        ActionFormMessage actionFormMessage = new ActionFormMessage(messageType, key);
        Field[] fields = e.getClass().getDeclaredFields();
        
        if(fields != null && fields.length > 0){
            for(Field field: fields){
                if(Modifier.isStatic(field.getModifiers()))
                    continue;
                
                String name = field.getName();
                
                try{
                    Object value = PropertyUtil.getValue(e, name);
                    
                    actionFormMessage.addAttribute(name, value);
                }
                catch(IllegalAccessException | InvocationTargetException | NoSuchMethodException ignored){
                }
            }
        }
        
        return actionFormMessage;
    }
    
    /**
     * Replaces the message attributes in a string. The attribute in a string
     * must be defined like following: <pre>#{&lt;attribute-name&gt;} E.g.: #{label}</pre>
     *
     * @param instance Instance that contains the message.
     * @param value String before the replacement.
     * @return String after the replacement.
     */
    public static String fillAttributesInString(ActionFormMessage instance, String value){
        return fillAttributesInString(instance, value, LanguageUtil.getDefaultLanguage());
    }
    
    /**
     * Replaces the message attributes in a string. The attribute in a string
     * must be defined like following: <pre>#{&lt;attribute-name&gt;} E.g.: #{label}</pre>
     *
     * @param instance Instance that contains the message.
     * @param value String before the replacement.
     * @param language Instance that contains the language.
     * @return String after the replacement.
     */
    public static String fillAttributesInString(ActionFormMessage instance, String value, Locale language){
        if(instance == null || value == null || value.length() == 0)
            return value;
        
        String buffer = value;
        Pattern pattern = Pattern.compile("\\#\\{(.*?)\\}");
        Matcher matcher = pattern.matcher(value);

        while(matcher.find()){
            String attributeExpression = matcher.group();
            String attributeName = matcher.group(1);
            String attributeValue = PropertyUtil.format(instance.getAttribute(attributeName), language);

            buffer = StringUtil.replaceAll(buffer, attributeExpression, attributeValue);
        }
        
        return buffer;
    }
}