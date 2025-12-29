package br.com.concepting.framework.processors;

import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.processors.constants.ProcessorConstants;
import br.com.concepting.framework.util.LanguageUtil;
import br.com.concepting.framework.util.PropertyUtil;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.helpers.XmlNode;
import org.apache.commons.beanutils.ConstructorUtils;
import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.jexl3.*;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
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

    @Override
    protected boolean returnContent(){
        return false;
    }

    @Override
    protected boolean hasLogic(){
        return true;
    }
    
    /**
     * Sets a variable.
     *
     * @param name String that contains the identifier of the variable.
     * @param value Instance that contains the content of the variable.
     */
    protected void setVariable(String name, Object value){
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
            JexlContext context = new MapContext();
            Object declaration = getDeclaration();

            context.set("declaration", declaration);

            String valueBuffer = value;
            Pattern pattern = Pattern.compile("^new (.+)\\((.*?)\\)$");
            Matcher matcher = pattern.matcher(valueBuffer);

            if(matcher.find()){
                String clazzId = matcher.group(1);
                String parametersId = matcher.group(2);
                String[] parameters = StringUtil.split(parametersId);
                Object[] parametersValues = (parameters != null && parameters.length > 0 ? new Object[parameters.length] : null);

                if(parameters != null && parameters.length > 0){
                    for(int cont = 0; cont < parameters.length; cont++){
                        String parameter = parameters[cont];

                        if(parameter != null && !parameter.isEmpty() && ((parameter.startsWith("'") && parameter.endsWith("'")) || (parameter.startsWith("\"") && parameter.endsWith("\"")))){
                            parameter = StringUtil.replaceAll(parameter, "'", "");
                            parameter = StringUtil.replaceAll(parameter, "\"", "");
                        }

                        Object parameterValue = evaluate(parameter);

                        parametersValues[cont] = parameterValue;
                    }
                }

                Class<?> clazz = Class.forName(clazzId);

                return (O)ConstructorUtils.invokeConstructor(clazz, parametersValues);
            }

            pattern = Pattern.compile("^([a-zA-Z0-9.]+|[#{a-zA-Z0-9.}]+|[@{a-zA-Z0-9.}]+).([a-zA-Z0-9]+)\\((.*?)\\)$");
            matcher = pattern.matcher(valueBuffer);

            if(matcher.find()){
                String command = matcher.group();
                String instanceId = null;
                Object instance = null;
                String methodsIds = null;

                if(command.startsWith("#{") || command.startsWith("@{")) {
                    int pos = command.indexOf("}.");

                    if(pos >= 0) {
                        instanceId = command.substring(0, pos + 1);
                        methodsIds = command.substring(pos + 2);

                        if (instanceId.startsWith("@{")) {
                            instanceId = StringUtil.replaceAll(instanceId, "@{", "");
                            instanceId = StringUtil.replaceAll(instanceId, "}", "");

                            instance = getVariable(instanceId);
                        } else {
                            instanceId = StringUtil.replaceAll(instanceId, "#{", "");
                            instanceId = StringUtil.replaceAll(instanceId, "}", "");

                            if (instanceId.equals("declaration"))
                                instance = declaration;
                            else
                                instance = PropertyUtil.getValue(declaration, instanceId);
                        }
                    }
                }
                else {
                    int pos = command.indexOf("(");

                    if (pos >= 0) {
                        String instanceBuffer = command.substring(0, pos);

                        pos = instanceBuffer.lastIndexOf(".");

                        if (pos >= 0) {
                            instanceId = instanceBuffer.substring(0, pos);
                            methodsIds = StringUtil.replaceAll(command, instanceId.concat("."), "");

                            Class<?> clazz = Class.forName(instanceId);

                            instance = clazz.getDeclaredConstructor().newInstance();
                        }
                    }
                }

                if(methodsIds != null && !methodsIds.isEmpty()) {
                    String methodsIdsBuffer = methodsIds;
                    Collection<String> methods = null;

                    while (methodsIdsBuffer != null && !methodsIdsBuffer.isEmpty()) {
                        int pos = methodsIdsBuffer.indexOf(").");

                        if (methods == null)
                            methods = PropertyUtil.instantiate(Constants.DEFAULT_LIST_CLASS);

                        if (pos >= 0) {
                            if (methods != null)
                                methods.add(methodsIdsBuffer.substring(0, pos + 1));

                            methodsIdsBuffer = methodsIdsBuffer.substring(pos + 2);
                        } else {
                            if (methods != null)
                                methods.add(methodsIdsBuffer);

                            methodsIdsBuffer = null;
                        }
                    }

                    if (methods != null && !methods.isEmpty()) {
                        for (String method : methods) {
                            pattern = Pattern.compile("^(.+)\\((.*?)\\)$");
                            matcher = pattern.matcher(method);

                            if (matcher.find()) {
                                String methodId = matcher.group(1);
                                String parametersId = matcher.group(2);
                                String[] parameters = StringUtil.split(parametersId);
                                Object[] parametersValues = (parameters != null && parameters.length > 0 ? new Object[parameters.length] : null);

                                if (parameters != null && parameters.length > 0) {
                                    for (int cont = 0; cont < parameters.length; cont++) {
                                        String parameter = parameters[cont];

                                        if (!parameter.isEmpty() && ((parameter.startsWith("'") && parameter.endsWith("'")) || (parameter.startsWith("\"") && parameter.endsWith("\"")))) {
                                            parameter = StringUtil.replaceAll(parameter, "'", "");
                                            parameter = StringUtil.replaceAll(parameter, "\"", "");
                                        }

                                        Object parameterValue = evaluate(parameter);

                                        parametersValues[cont] = parameterValue;
                                    }
                                }

                                try {
                                    instance = MethodUtils.invokeMethod(instance, methodId, parametersValues);

                                    if (instance == null)
                                        break;
                                }
                                catch (NullPointerException e) {
                                    return null;
                                }
                            }
                        }
                    }
                }

                return (O)instance;
            }

            pattern = Pattern.compile("#\\{(.*?)}|@\\{(.*?)}");
            matcher = pattern.matcher(valueBuffer);

            if(matcher.find()){
                do {
                    String tokenExpression = matcher.group();
                    String tokenName = (tokenExpression.startsWith("#{") ? matcher.group(1) : matcher.group(2));
                    Object tokenValue;

                    if (tokenName != null && !tokenName.isEmpty()) {
                        if (tokenExpression.contains("#{")) {

                            if (!tokenName.equals("declaration")) {
                                tokenValue = PropertyUtil.getValue(declaration, tokenName);

                                context.set(tokenName, tokenValue);
                            }
                        }
                        else if (tokenExpression.contains("@{")) {
                            tokenValue = getVariable(tokenName);

                            context.set(tokenName, tokenValue);
                        }

                        valueBuffer = StringUtil.replaceAll(valueBuffer, tokenExpression, tokenName);
                    }
                }
                while (matcher.find());

                try {
                    JexlExpression expression = engine.createExpression(valueBuffer);

                    return (O) expression.evaluate(context);
                }
                catch(Throwable e) {
                    return (O)valueBuffer;
                }
            }

            return (O)valueBuffer;
        }
        catch(IllegalAccessException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException  e){
            throw new InternalErrorException(e);
        }
    }

    @Override
    public String process() throws InternalErrorException{
        if(!returnContent()){
            evaluate();
            
            return ProcessorConstants.DEFAULT_REMOVE_TAG_ID;
        }
        
        return super.process();
    }
}