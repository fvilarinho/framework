package br.com.concepting.framework.webservice.controller;

import br.com.concepting.framework.controller.SystemController;
import br.com.concepting.framework.controller.helpers.RequestParameterInfo;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.resources.SystemResources;
import br.com.concepting.framework.resources.SystemResourcesLoader;
import br.com.concepting.framework.resources.exceptions.InvalidResourcesException;
import br.com.concepting.framework.security.controller.SecurityController;
import br.com.concepting.framework.security.model.LoginParameterModel;
import br.com.concepting.framework.security.model.LoginSessionModel;
import br.com.concepting.framework.security.model.UserModel;
import br.com.concepting.framework.service.annotations.Transaction;
import br.com.concepting.framework.service.annotations.TransactionParam;
import br.com.concepting.framework.service.interfaces.IService;
import br.com.concepting.framework.service.util.ServiceUtil;
import br.com.concepting.framework.util.*;
import br.com.concepting.framework.util.types.ContentType;
import br.com.concepting.framework.util.types.MethodType;
import br.com.concepting.framework.webservice.constants.WebServiceConstants;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Class responsible to route the request to the right webservice.
 *
 * @author fvilarinho
 * @version 3.10.0
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
@WebServlet(name = "wenServices", urlPatterns = "/webServices/*")
public class WebServiceServlet extends HttpServlet {
    private SystemController systemController = null;
    private SecurityController securityController = null;
    private SystemResources systemResources = null;
    private String uri = null;
    private String serviceUrl = null;
    private String methodUrl = null;
    private ContentType consumes = null;
    private ContentType produces = null;
    private String encoding = null;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.execute(MethodType.GET, request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.execute(MethodType.POST, request, response);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.execute(MethodType.PUT, request, response);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.execute(MethodType.DELETE, request, response);
    }

    /**
     * Execute the request routing it to a webservice.
     *
     * @param methodType Instance that defines the request method.
     * @param request Instance that contains the request attributes.
     * @param response Instance that contains the response attributes.
     */
    protected void execute(MethodType methodType, HttpServletRequest request, HttpServletResponse response){
        this.init(request, response);

        try{
            Class<IService<? extends BaseModel>> serviceClass = this.lookupService();
            Method method = this.lookupMethod(serviceClass, methodType);
            String methodName = method.getName();
            Object[] methodParameters = this.lookupMethodParameters(method);
            LoginSessionModel loginSession = this.securityController.getLoginSession();
            IService<? extends BaseModel> service = ServiceUtil.getByServiceClass(serviceClass, loginSession);
            Object methodResult = MethodUtil.invokeMethod(service, methodName, methodParameters);

            if(this.produces == ContentType.JSON)
                this.systemController.outputContent(PropertyUtil.serialize(methodResult), this.produces);
            else if(this.produces == ContentType.XML)
                this.systemController.outputContent(XmlUtil.serialize(methodResult), this.produces);
            else if(this.produces == ContentType.BINARY)
                this.systemController.outputContent(ByteUtil.serialize(methodResult), this.produces);
            else
                this.systemController.outputContent(StringUtil.trim(methodResult), this.produces);
        }
        catch(Throwable e){
            this.systemController.forward(e);
        }
    }

    /**
     * Initializes the webservice processing.
     *
     * @param request Instance that contains the request attributes.
     * @param response Instance that contains the response attributes.
     */
    protected void init(HttpServletRequest request, HttpServletResponse response){
        try {
            this.systemController = new SystemController(request, response);
            this.securityController = this.systemController.getSecurityController();

            loadSystemResources();
            parseUrl();

            this.consumes = this.systemController.getAccept();
            this.produces = this.systemController.getContentType();
            this.encoding = this.systemController.getEncoding();
        }
        catch(InternalErrorException e){
            this.systemController.forward(e);
        }
    }

    /**
     * Loads the system resources.
     *
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    private void loadSystemResources() throws InternalErrorException {
        SystemResourcesLoader loader = new SystemResourcesLoader();

        this.systemResources = loader.getDefault();
    }

    /**
     * Parse the requet url.
     *
     * @throws InvalidResourcesException Occurs when was not possible to execute the operation.
     */
    private void parseUrl() throws InvalidResourcesException{
        String contextPath = this.systemController.getContextPath();

        this.uri = this.systemController.getURI();
        this.uri = StringUtil.replaceAll(uri, contextPath, "");
        this.uri = StringUtil.replaceAll(uri, WebServiceConstants.DEFAULT_URL_ID, "");

        int pos = this.uri.lastIndexOf("/");

        if(pos < 0)
            throw new InvalidResourcesException(this.uri);

        this.serviceUrl = this.uri.substring(1, pos);

        if(this.serviceUrl.length() == 0)
            throw new InvalidResourcesException(this.uri);

        this.methodUrl = this.uri.substring(pos + 1);

        if(this.methodUrl.length() == 0)
            this.methodUrl = "execute";
    }

    /**
     * Find the service implementation based on the request url.
     *
     * @return Class that defines the service implementation.
     * @throws InvalidResourcesException Occurs when the service was not found.
     */
    @SuppressWarnings("unchecked")
    private Class<IService<? extends BaseModel>> lookupService() throws InvalidResourcesException {
        Collection<SystemResources.ServiceResources> servicesResources = this.systemResources.getServices();

        if(servicesResources != null && !servicesResources.isEmpty()){
            try {
                Optional<SystemResources.ServiceResources> result = servicesResources.parallelStream().filter(s -> s.getPath() != null && (s.getPath().equals(this.serviceUrl) || s.getPath().equals("/".concat(this.serviceUrl)))).findAny();
                SystemResources.ServiceResources serviceResource;

                if(result.isEmpty())
                    throw new InvalidResourcesException(this.uri);

                serviceResource = result.get();

                return (Class<IService<? extends BaseModel>>)Class.forName(serviceResource.getClazz());
            }
            catch(ClassNotFoundException | NoSuchElementException e){
                throw new InvalidResourcesException(this.uri, e);
            }
        }

        throw new InvalidResourcesException(this.uri);
    }

    /**
     * Find the service method to be executed based on the request url.
     *
     * @param serviceClass Class that defines the service implementation.
     * @param methodType Instance that defines the method type of the request.
     * @return Instance that defines the method to be executed.
     * @throws InvalidResourcesException Occurs when the method was not found.
     */
    private Method lookupMethod(Class<IService<? extends BaseModel>> serviceClass, MethodType methodType) throws InvalidResourcesException {
        Collection<Method> methods = Arrays.asList(serviceClass.getMethods());

        if(methods != null && !methods.isEmpty()){
            methods = methods.parallelStream().filter(s-> s.getAnnotation(Transaction.class) != null).collect(Collectors.toList());

            if(methods != null && !methods.isEmpty()){
                for(Method method : methods){
                    Transaction transaction = method.getAnnotation(Transaction.class);

                    if((transaction.path().equals(this.methodUrl) || transaction.path().equals("/".concat(this.methodUrl))) &&
                       transaction.type().equals(methodType) &&
                       transaction.produces().equals(this.produces)) {
                        ContentType[] permittedConsumption = transaction.consumes();

                        for (ContentType item : permittedConsumption)
                            if (item.equals(this.consumes))
                                return method;
                    }
                }
            }
        }

        throw new InvalidResourcesException(this.uri);
    }

    /**
     * Instantiante all method parameters based on the request.
     *
     * @param method Instance that defines the method.
     * @return Array that contains the method parameters.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private Object[] lookupMethodParameters(Method method) throws InternalErrorException{
        try {
            Parameter[] parameters = method.getParameters();
            Object[] parametersValues = null;

            if (parameters != null && parameters.length > 0) {
                Locale currentLanguage = this.getCurrentLanguage();
                Map<String, RequestParameterInfo> requestParametersInfo = this.systemController.getParameters();
                int cont = 0;

                parametersValues = new Object[parameters.length];

                for (Parameter parameter : parameters) {
                    Class<?> parameterType = parameter.getType();
                    TransactionParam transactionParam = parameter.getAnnotation(TransactionParam.class);
                    Object parameterValue = null;

                    if (transactionParam != null) {
                        String parameterName = transactionParam.name();

                        if (parameterName != null && parameterName.length() > 0) {
                            RequestParameterInfo requestParameterInfo = requestParametersInfo.get(parameterName);

                            if (requestParameterInfo != null) {
                                String value = requestParameterInfo.getValue();
                                String[] values = requestParameterInfo.getValues();

                                if (PropertyUtil.isCollection(parameterType)) {
                                    if (values == null || values.length == 0)
                                        parameterValue = null;
                                    else {
                                        parameterValue = PropertyUtil.instantiate(parameterType);

                                        String collectionType = parameter.toString();
                                        int pos = collectionType.indexOf("<");

                                        if (pos >= 0) {
                                            collectionType = collectionType.substring(pos + 1);
                                            pos = collectionType.indexOf(">");

                                            if (pos >= 0)
                                                collectionType = collectionType.substring(0, pos);
                                        }

                                        collectionType = StringUtil.replaceAll(collectionType, "? ", "");
                                        collectionType = StringUtil.replaceAll(collectionType, "extends ", "");

                                        Class<?> collectionParameterType = Class.forName(collectionType);

                                        for (String collectionValue : values) {
                                            Object collectionParameterValue = null;

                                            if (PropertyUtil.isNumber(collectionParameterType))
                                                collectionParameterValue = NumberUtil.parse(parameterType, value, currentLanguage);
                                            else if (PropertyUtil.isDate(parameterType))
                                                collectionParameterValue = DateTimeUtil.parse(value, currentLanguage);
                                            else if (PropertyUtil.isBoolean(parameterType))
                                                collectionParameterValue = Boolean.parseBoolean(value);
                                            else if (PropertyUtil.isEnum(parameterType))
                                                collectionParameterValue = Enum.valueOf((Class) parameterType, value);
                                            else if (PropertyUtil.isString(parameterType))
                                                collectionParameterValue = collectionValue;

                                            if (collectionParameterValue != null)
                                                ((Collection) parameterValue).add(collectionValue);
                                        }
                                    }
                                } else {
                                    if (value != null) {
                                        if (PropertyUtil.isNumber(parameterType))
                                            parameterValue = NumberUtil.parse(parameterType, value, currentLanguage);
                                        else if (PropertyUtil.isDate(parameterType))
                                            parameterValue = DateTimeUtil.parse(value, currentLanguage);
                                        else if (PropertyUtil.isBoolean(parameterType))
                                            parameterValue = Boolean.parseBoolean(value);
                                        else if (PropertyUtil.isEnum(parameterType))
                                            parameterValue = Enum.valueOf((Class) parameterType, value);
                                        else if (PropertyUtil.isString(parameterType))
                                            parameterValue = value;
                                    }
                                }
                            }
                        }
                        else {
                            if (transactionParam.isBody()) {
                                byte[] body = IOUtils.toByteArray(this.systemController.getInputStream());

                                if (this.consumes == ContentType.JSON)
                                    parameterValue = PropertyUtil.deserialize(body, parameterType);
                                else if (this.consumes == ContentType.XML)
                                    parameterValue = XmlUtil.deserialize(body, parameterType);
                                else if (this.consumes == ContentType.BINARY)
                                    parameterValue = ByteUtil.deserialize(body);
                                else
                                    parameterValue = new String(body, encoding);
                            }
                        }
                    }

                    parametersValues[cont] = parameterValue;
                }
            }

            return parametersValues;
        }
        catch(Throwable e){
            throw new InternalErrorException(e);
        }
    }

    /**
     * Returns the instance that contains the current language.
     *
     * @return Instance that contains the current language.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    private Locale getCurrentLanguage() throws InternalErrorException{
        if(this.securityController != null){
            LoginSessionModel loginSession = this.securityController.getLoginSession();
            UserModel user = (loginSession != null ? loginSession.getUser() : null);
            LoginParameterModel loginParameter = (user != null ? user.getLoginParameter() : null);

            if(loginParameter != null && loginParameter.getLanguage() != null && loginParameter.getLanguage().length() > 0)
                return LanguageUtil.getLanguageByString(loginParameter.getLanguage());
        }

        return LanguageUtil.getDefaultLanguage();
    }
}