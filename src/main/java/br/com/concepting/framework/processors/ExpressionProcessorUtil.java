package br.com.concepting.framework.processors;

import br.com.concepting.framework.caching.CachedObject;
import br.com.concepting.framework.caching.Cacher;
import br.com.concepting.framework.caching.CacherManager;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.model.exceptions.ItemAlreadyExistsException;
import br.com.concepting.framework.model.exceptions.ItemNotFoundException;
import br.com.concepting.framework.util.LanguageUtil;
import br.com.concepting.framework.util.PropertyUtil;
import br.com.concepting.framework.util.StringUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class responsible to manipulate expressions.
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
public class ExpressionProcessorUtil{
    /**
     * Returns the value of a variable.
     *
     * @param <O> Class that defines the variable value.
     * @param name String that contains the identifier of the variable.
     * @return Instance that contains the value of the variable.
     */
    public static <O> O getVariable(String name){
        if(name != null && name.length() > 0)
            return getVariable(ExpressionProcessorUtil.class.getName(), name);
        
        return null;
    }
    
    /**
     * Returns the value of a variable.
     *
     * @param <O> Class that defines the variable value.
     * @param domain String that contains the identifier of the domain.
     * @param name String that contains the identifier of the variable.
     * @return Instance that contains the value of the variable.
     */
    @SuppressWarnings("unchecked")
    public static <O> O getVariable(String domain, String name){
        if(domain == null)
            domain = ExpressionProcessorUtil.class.getName();
        
        Object variableValue = null;
        
        if(name != null && name.length() > 0){
            try{
                String[] variablesIds = StringUtil.split(name, ".");
                
                if(variablesIds != null && variablesIds.length > 0){
                    Cacher<O> cacher = CacherManager.getInstance().getCacher(domain);
                    CachedObject<O> cachedObject = cacher.get(variablesIds[0]);
                    
                    variableValue = cachedObject.getContent();
                    
                    if(variableValue != null){
                        for(int cont = 1; cont < variablesIds.length; cont++){
                            try{
                                variableValue = PropertyUtil.getValue(variableValue, variablesIds[cont]);
                            }
                            catch(IllegalAccessException | NoSuchMethodException | InvocationTargetException e){
                                variableValue = null;
                                
                                break;
                            }
                        }
                    }
                }
            }
            catch(ItemNotFoundException ignored){
            }
        }
        
        return (O) variableValue;
    }
    
    /**
     * Set a variable.
     *
     * @param name String that contains the identifier of the variable.
     * @param value Instance that contains the value of the variable.
     */
    public static void setVariable(String name, Object value){
        if(name != null && name.length() > 0)
            setVariable(ExpressionProcessorUtil.class.getName(), name, value);
    }
    
    /**
     * Set a new variable
     *
     * @param domain String that contains the identifier of the domain.
     * @param name String that contains the identifier of the variable.
     * @param value Instance that contains the value of the variable.
     */
    public static void setVariable(String domain, String name, Object value){
        if(domain == null)
            domain = ExpressionProcessorUtil.class.getName();
        
        if(name != null && name.length() > 0){
            Cacher<Object> cacher = CacherManager.getInstance().getCacher(domain);
            CachedObject<Object> cachedObject = new CachedObject<>();
            
            cachedObject.setId(name);
            cachedObject.setContent(value);
            
            try{
                cacher.add(cachedObject);
            }
            catch(ItemAlreadyExistsException e){
                try{
                    cacher.set(cachedObject);
                }
                catch(ItemNotFoundException ignored){
                }
            }
        }
    }
    
    /**
     * Replaces the environment variables in a string. The resource property
     * should be declared like following: <pre>${&lt;variable-name&gt;} E.g.: ${JAVA_HOME}</pre>
     *
     * @param value String before the processing.
     * @return String after the processing.
     */
    public static String fillEnvironmentInString(String value){
        if(value != null && value.length() > 0){
            Pattern pattern = Pattern.compile("\\$\\{(.*?)\\}");
            Matcher matcher = pattern.matcher(value);

            while(matcher.find()){
                String environmentExpression = matcher.group();
                String environmentName = matcher.group(1);
                String environmentValue = StringUtil.trim(System.getenv(environmentName));

                value = StringUtil.replaceAll(value, environmentExpression, environmentValue);
            }
        }
        
        return value;
    }
    
    /**
     * Replaces variables in a string. The variable must be defined like
     * following: <pre>@{&lt;variable-name&gt;} E.g.: @{var1}</pre>
     *
     * @param value String that will be processed.
     * @return String processed.
     */
    public static String fillVariablesInString(String value){
        return fillVariablesInString(ExpressionProcessorUtil.class.getName(), value);
    }
    
    /**
     * Replaces variables in a string. The variable must be defined like
     * following: <pre>@{&lt;variable-name&gt;} E.g.: @{var1}</pre>
     *
     * @param domain String that contains the identifier of the domain.
     * @param value String that will be processed.
     * @return String processed.
     */
    public static String fillVariablesInString(String domain, String value){
        return fillVariablesInString(domain, value, LanguageUtil.getDefaultLanguage());
    }
    
    /**
     * Replaces variables in a string. The variable must be defined like
     * following: <pre>@{&lt;variable-name&gt;} E.g.: @{var1}</pre>
     *
     * @param value String that will be processed.
     * @param language Instance that contains the language.
     * @return String processed.
     */
    public static String fillVariablesInString(String value, Locale language){
        return fillVariablesInString(ExpressionProcessorUtil.class.getName(), value, language);
    }
    
    /**
     * Replaces variables in a string. The variable must be defined like
     * following: <pre>@{&lt;variable-name&gt;} E.g.: @{var1}</pre>
     *
     * @param domain String that contains the identifier of the domain.
     * @param value String that will be processed.
     * @param language Instance that contains the language.
     * @return String processed.
     */
    public static String fillVariablesInString(String domain, String value, Locale language){
        return fillVariablesInString(domain, value, true, language);
    }
    
    /**
     * Replaces variables in a string. The variable must be defined like
     * following: <pre>@{&lt;variable-name&gt;} E.g.: @{var1}</pre>
     *
     * @param domain String that contains the identifier of the domain.
     * @param value String that will be processed.
     * @param replaceNotFoundMatches Indicates if the variables that were not
     * found should be replaced with blank.
     * @param language Instance that contains the language.
     * @return String processed.
     */
    public static String fillVariablesInString(String domain, String value, boolean replaceNotFoundMatches, Locale language){
        return fillVariablesInString(domain, value, replaceNotFoundMatches, false, language);
    }
    
    /**
     * Replaces variables in a string. The variable must be defined like
     * following: <pre>@{&lt;variable-name&gt;} E.g.: @{var1}</pre>
     *
     * @param domain String that contains the identifier of the domain.
     * @param value String that will be processed.
     * @param replaceNotFoundMatches Indicates if the variables that were not
     * found should be replaced with blank.
     * @param escapeCrlf Indicates if it should escape CRLF in the replacements.
     * @param language Instance that contains the language.
     * @return String processed.
     */
    public static String fillVariablesInString(String domain, String value, boolean replaceNotFoundMatches, boolean escapeCrlf, Locale language){
        if(domain == null)
            domain = ExpressionProcessorUtil.class.getName();
        
        if(value != null && value.length() > 0){
            Pattern pattern = Pattern.compile("\\@\\{(.*?)(\\((.*?)\\))?\\}");
            Matcher matcher = pattern.matcher(value);
            ExpressionProcessor expressionProcessor = new ExpressionProcessor(domain, language);
            
            while(matcher.find()){
                String variableExpression = matcher.group();
                String variablePattern = matcher.group(3);
                String variableExpressionBuffer;
                Object variableValue;

                try{
                    if(variablePattern != null && variablePattern.length() > 0){
                        variableExpressionBuffer = StringUtil.replaceAll(variableExpression, variablePattern, "");
                        variableExpressionBuffer = StringUtil.replaceAll(variableExpressionBuffer, "(", "");
                        variableExpressionBuffer = StringUtil.replaceAll(variableExpressionBuffer, ")", "");
                    }
                    else
                        variableExpressionBuffer = variableExpression;
                    
                    variableValue = expressionProcessor.evaluate(variableExpressionBuffer);
                    
                    if(variableValue == null && !replaceNotFoundMatches)
                        continue;
                }
                catch(InternalErrorException e){
                    if(replaceNotFoundMatches)
                        variableValue = null;
                    else
                        continue;
                }
                
                String buffer = null;
                
                if(variableValue != null){
                    buffer = PropertyUtil.format(variableValue, variablePattern, language);
                    
                    if(escapeCrlf)
                        buffer = buffer.replaceAll(StringUtil.getLineBreak(), "\\\\n");
                }
                
                value = StringUtil.replaceAll(value, variableExpression, buffer);
            }
        }
        
        return value;
    }
    
    /**
     * Clear variables of the domain.
     *
     * @param domain String that contains the domain.
     */
    public static void clearVariables(String domain){
        Cacher<Object> cacher = CacherManager.getInstance().getCacher(domain);
        
        if(cacher != null)
            cacher.clear();
    }
}