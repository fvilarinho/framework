package br.com.concepting.framework.processors;

import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.processors.constants.ProcessorConstants;
import br.com.concepting.framework.security.model.UserModel;
import br.com.concepting.framework.util.LanguageUtil;
import br.com.concepting.framework.util.PropertyUtil;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.helpers.XmlNode;
import org.apache.commons.beanutils.ConstructorUtils;
import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.jexl3.*;

import java.lang.reflect.InvocationTargetException;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class that defines the logic processor to evaluate expressions.
 *
 * @author fvilarinho
 * @since 3.0.0
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
public class EvaluateProcessor extends GenericProcessor{
    private static final JexlEngine engine = new JexlBuilder().create();
    
    private String value = null;
    
    /**
     * Constructor - Defines the logic processor.
     */
    public EvaluateProcessor(){
        this(ExpressionProcessorUtil.class.getName());
    }
    
    /**
     * Constructor - Defines the logic processor.
     *
     * @param domain String that contains the domain of the processing.
     */
    public EvaluateProcessor(String domain){
        this(domain, LanguageUtil.getDefaultLanguage());
    }
    
    /**
     * Constructor - Defines the logic processor.
     *
     * @param language Instance that contains the language used in the
     * processing.
     */
    public EvaluateProcessor(Locale language){
        this(ExpressionProcessorUtil.class.getName(), null, language);
    }
    
    /**
     * Constructor - Defines the logic processor.
     *
     * @param domain String that contains the domain of the processing.
     * @param language Instance that contains the language used in the
     * processing.
     */
    public EvaluateProcessor(String domain, Locale language){
        this(domain, null, language);
    }
    
    /**
     * Constructor - Defines the logic processor.
     *
     * @param declaration Instance that contains the object that will be
     * processed.
     */
    public EvaluateProcessor(Object declaration){
        this(ExpressionProcessorUtil.class.getName(), declaration);
    }
    
    /**
     * Constructor - Defines the logic processor.
     *
     * @param domain String that contains the domain of the processing.
     * @param declaration Instance that contains the object that will be
     * processed.
     */
    public EvaluateProcessor(String domain, Object declaration){
        this(domain, declaration, LanguageUtil.getDefaultLanguage());
    }
    
    /**
     * Constructor - Defines the logic processor.
     *
     * @param declaration Instance that contains the object that will be
     * processed.
     * @param language Instance that contains the language used in the
     * processing.
     */
    public EvaluateProcessor(Object declaration, Locale language){
        this(ExpressionProcessorUtil.class.getName(), declaration, language);
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
    public EvaluateProcessor(String domain, Object declaration, Locale language){
        this(domain, declaration, null, language);
    }
    
    /**
     * Constructor - Defines the logic processor.
     *
     * @param domain String that contains the domain of the processing.
     * @param declaration Instance that contains the object that will be
     * processed.
     * @param content Instance that contains the XML content.
     */
    public EvaluateProcessor(String domain, Object declaration, XmlNode content){
        this(domain, declaration, content, LanguageUtil.getDefaultLanguage());
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
    public EvaluateProcessor(String domain, Object declaration, XmlNode content, Locale language){
        super(domain, declaration, content, language);
    }
    
    /**
     * @see br.com.concepting.framework.processors.GenericProcessor#returnContent()
     */
    protected Boolean returnContent(){
        return false;
    }
    
    /**
     * @see br.com.concepting.framework.processors.GenericProcessor#hasLogic()
     */
    protected Boolean hasLogic(){
        return true;
    }
    
    /**
     * Sets a variable.
     *
     * @param name String that contains the identifier of the variable.
     * @param value Instance that contains the content of the variable.
     */
    public void setVariable(String name, Object value){
        ExpressionProcessorUtil.setVariable(getDomain(), name, value);
    }
    
    /**
     * Returns the content of a variable.
     *
     * @param name String that contains the identifier of the variable.
     * @return Instance that contains the content of the variable.
     */
    public Object getVariable(String name){
        return ExpressionProcessorUtil.getVariable(getDomain(), name);
    }
    
    /**
     * Returns the expression that should be evaluated.
     *
     * @return String that contains the expression that should be evaluated.
     */
    public String getValue(){
        return this.value;
    }
    
    /**
     * Defines the expression that should be evaluated.
     *
     * @param value String that contains the expression that should be
     * evaluated.
     */
    public void setValue(String value){
        this.value = value;
    }
    
    /**
     * Evaluates the expression.
     *
     * @param <O> Class that defines the expression.
     * @return Instance that contains the processed expression.
     * @throws InternalErrorException Occurs when was not possible to execute
     * the operation.
     */
    public <O> O evaluate() throws InternalErrorException{
        return evaluate(this.value);
    }
    
    /**
     * Evaluates the expression.
     *
     * @param <O> Class that defines the expression.
     * @param value String that contains the expression.
     * @return Instance that contains the processed expression.
     * @throws InternalErrorException Occurs when was not possible to execute
     * the operation.
     */
    @SuppressWarnings("unchecked")
    public <O> O evaluate(String value) throws InternalErrorException{
        if(value == null)
            return null;
        
        try{
            Pattern pattern = Pattern.compile("^new (.+)\\((.*?)\\)$");
            Matcher matcher = pattern.matcher(value);
            
            if(matcher.find()){
                String clazzId = matcher.group(1);
                String parametersId = matcher.group(2);
                String[] parameters = StringUtil.split(parametersId);
                String parameter = null;
                Object parameterValue = null;
                Object[] parametersValues = (parameters != null && parameters.length > 0 ? new Object[parameters.length] : null);
                
                if(parameters != null && parameters.length > 0){
                    for(int cont = 0; cont < parameters.length; cont++){
                        parameter = parameters[cont];
                        
                        if(parameter != null && parameter.length() > 0 && ((parameter.startsWith("'") && parameter.endsWith("'")) || (parameter.startsWith("\"") && parameter.endsWith("\"")))){
                            parameter = StringUtil.replaceAll(parameter, "'", "");
                            parameter = StringUtil.replaceAll(parameter, "\"", "");
                        }
                        
                        parameterValue = evaluate(parameter);
                        parametersValues[cont] = parameterValue;
                    }
                }
                
                Class<?> clazz = Class.forName(clazzId);
                
                return (O) ConstructorUtils.invokeConstructor(clazz, parametersValues);
            }
            
            pattern = Pattern.compile("^(.+)\\((.*?)\\)$");
            matcher = pattern.matcher(value);
            
            if(matcher.find()){
                String clazzId = matcher.group(1);
                
                if(!clazzId.contains("#{") && !clazzId.contains("@{")){
                    String parametersId = matcher.group(2);
                    String[] parameters = StringUtil.split(parametersId);
                    String parameter = null;
                    Object parameterValue = null;
                    Object[] parametersValues = (parameters != null && parameters.length > 0 ? new Object[parameters.length] : null);
                    int pos = clazzId.lastIndexOf(".");
                    String methodId = clazzId.substring(pos + 1);
                    
                    if(pos >= 0){
                        clazzId = clazzId.substring(0, pos);
                        
                        Class<?> clazz = Class.forName(clazzId);
                        
                        if(parameters != null && parameters.length > 0){
                            for(int cont = 0; cont < parameters.length; cont++){
                                parameter = parameters[cont];
                                
                                if(parameter != null && parameter.length() > 0 && ((parameter.startsWith("'") && parameter.endsWith("'")) || (parameter.startsWith("\"") && parameter.endsWith("\"")))){
                                    parameter = StringUtil.replaceAll(parameter, "'", "");
                                    parameter = StringUtil.replaceAll(parameter, "\"", "");
                                }
                                
                                parameterValue = evaluate(parameter);
                                parametersValues[cont] = parameterValue;
                            }
                        }
                        
                        Object instance = clazz.newInstance();
                        
                        return (O) MethodUtils.invokeMethod(instance, methodId, parametersValues);
                    }
                }
            }
            
            pattern = Pattern.compile("\\#\\{(.*?)\\}|\\@\\{(.*?)\\}");
            matcher = pattern.matcher(value);
            
            Object declaration = getDeclaration();
            JexlContext context = new MapContext();
            String valueBuffer = value;
            String tokenExpression = null;
            String tokenName = null;
            Object tokenValue = null;
            
            if(matcher.find()){
                context.set("declaration", declaration);
                
                do{
                    tokenExpression = matcher.group();
                    tokenName = matcher.group(1);
                    
                    if(tokenName == null)
                        tokenName = matcher.group(2);
                    
                    if(tokenName != null && tokenName.length() > 0){
                        if(tokenExpression.contains("#{")){
                            if(!tokenName.equals("declaration")){
                                tokenName = StringUtil.replaceAll(tokenName, "declaration.", "");
                                tokenValue = PropertyUtil.getValue(declaration, tokenName);

                                context.set(tokenName, tokenValue);
                            }
                        }
                        else if(tokenExpression.contains("@{")){
                            tokenValue = getVariable(tokenName);

                            context.set(tokenName, tokenValue);
                        }
                        
                        valueBuffer = StringUtil.replaceAll(valueBuffer, tokenExpression, tokenName);
                    }
                }
                while(matcher.find());
            }
            else{
                valueBuffer = StringUtil.replaceAll(valueBuffer, "'", "");
                valueBuffer = StringUtil.replaceAll(valueBuffer, "\"", "");
                pattern = Pattern.compile("^(not|!)?\\ ?(true|false|[0-9]+)\\ ?(\\+|\\-|\\*|\\/|\\%|\\&+|\\|+)?\\ ?(not|!)?\\ ?(true|false|[0-9]+)?$");
                matcher = pattern.matcher(valueBuffer);
                
                if(!matcher.find()){
                    context.set("declaration", valueBuffer);
                    
                    JexlExpression expression = engine.createExpression("declaration");
                    
                    return (O) expression.evaluate(context);
                }
            }
            
            JexlExpression expression = engine.createExpression(valueBuffer);
            
            try{
                return (O) expression.evaluate(context);
            }
            catch(JexlException e){
                return null;
            }
        }
        catch(IllegalAccessException | InvocationTargetException | NoSuchMethodException | InstantiationException | ClassNotFoundException e){
            throw new InternalErrorException(e);
        }
    }
    
    /**
     * @see br.com.concepting.framework.processors.GenericProcessor#process()
     */
    public String process() throws InternalErrorException{
        if(returnContent() == null || !returnContent()){
            evaluate();
            
            return ProcessorConstants.DEFAULT_REMOVE_TAG_ID;
        }
        
        return super.process();
    }
}