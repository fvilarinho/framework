package br.com.concepting.framework.webservice.controller;

import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.constants.SystemConstants;
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
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpStatus;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.stream.Collectors;

@WebServlet(name = "wenServices", urlPatterns = "/webServices/*")
public class WebServiceServlet extends HttpServlet {
    private static final ObjectMapper mapper = PropertyUtil.getMapper();

    private SystemController systemController = null;
    private SecurityController securityController = null;
    private SystemResources systemResources = null;
    private String uri = null;
    private String serviceUrl = null;
    private String methodUrl = null;

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

    @SuppressWarnings({"unchecked", "rawtypes"})
    private void execute(MethodType methodType, HttpServletRequest request, HttpServletResponse response){
        this.init(request, response);

        try{
            String accept = StringUtil.trim(this.systemController.getHeader(SystemConstants.ACCEPT_ATTRIBUTE_ID));
            String contentType = StringUtil.trim(this.systemController.getHeader(SystemConstants.CONTENT_TYPE_ATTRIBUTE_ID));
            ContentType consumes;

            try {
                consumes = ContentType.toContentType(accept);
            }
            catch(Throwable e){
                consumes = ContentType.NONE;
            }

            ContentType produces;

            try {
                produces = ContentType.toContentType(contentType);
            }
            catch(Throwable e){
                produces = ContentType.NONE;
            }

            String encoding = StringUtil.trim(this.systemController.getEncoding());

            if(encoding == null)
                encoding = Constants.DEFAULT_UNICODE_ENCODING;

            LoginSessionModel loginSession = this.securityController.getLoginSession();
            Locale currentLanguage = getCurrentLanguage();
            Class<IService<? extends BaseModel>> serviceClass = this.lookupService();
            Method method = lookupMethod(serviceClass, methodType, consumes, produces);
            Parameter[] parameters = method.getParameters();
            Object[] parametersValues = null;

            if(parameters != null && parameters.length > 0){
                Map<String, RequestParameterInfo> requestParametersInfo = this.systemController.getParameters();
                int cont = 0;

                parametersValues = new Object[parameters.length];

                for(Parameter parameter : parameters){
                    Class<?> parameterType = parameter.getType();
                    TransactionParam transactionParam = parameter.getAnnotation(TransactionParam.class);
                    Object parameterValue = null;

                    if(transactionParam != null) {
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
                                }
                                else {
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
                        else{
                            if(transactionParam.isBody()){
                                byte[] body = IOUtils.toByteArray(request.getInputStream());

                                if(consumes == ContentType.JSON)
                                    parameterValue = mapper.readValue(body, parameterType);
                                else if(consumes == ContentType.XML) {
                                    //TODO
                                }
                                else if(consumes == ContentType.BINARY)
                                    parameterValue = ByteUtil.fromBytes(body);
                                else
                                    parameterValue = new String(body, encoding);
                            }
                        }
                    }

                    parametersValues[cont] = parameterValue;
                }
            }

            IService<? extends BaseModel> service = ServiceUtil.getByServiceClass(serviceClass, loginSession);
            Object result = MethodUtil.invokeMethod(service, method.getName(), parametersValues);
            Object body = null;

            response.setContentType(contentType);
            response.setStatus(HttpStatus.SC_OK);

            if(produces == ContentType.JSON)
                this.systemController.outputContent(mapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(result), contentType);
            else if(produces == ContentType.XML) {
                //TODO
            }
            else if(produces == ContentType.BINARY)
                this.systemController.outputContent(ByteUtil.toBytes(result), contentType);
            else
                this.systemController.outputContent(StringUtil.trim(result).getBytes(), contentType);
        }
        catch(Throwable e){
            this.systemController.forward(e);
        }
    }

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

    private void init(HttpServletRequest request, HttpServletResponse response){
        this.systemController = new SystemController(request, response);
        this.securityController = this.systemController.getSecurityController();

        try {
            loadSystemResources();
            parseUrl();
        }
        catch(InternalErrorException e){
            this.systemController.forward(e);
        }
    }

    @SuppressWarnings("unchecked")
    private Class<IService<? extends BaseModel>> lookupService() throws InternalErrorException {
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

    private Method lookupMethod(Class<IService<? extends BaseModel>> serviceClass, MethodType methodType, ContentType consumes, ContentType produces) throws InvalidResourcesException {
        Collection<Method> methods = Arrays.asList(serviceClass.getMethods());

        if(methods != null && !methods.isEmpty()){
            methods = methods.parallelStream().filter(s-> s.getAnnotation(Transaction.class) != null).collect(Collectors.toList());

            if(methods != null && !methods.isEmpty()){
                for(Method method : methods){
                    Transaction transaction = method.getAnnotation(Transaction.class);

                    if((transaction.path().equals(this.methodUrl) || transaction.path().equals("/".concat(this.methodUrl))) &&
                       transaction.type().equals(methodType) &&
                       (transaction.consumes().equals(consumes) || transaction.consumes().equals(ContentType.NONE)) &&
                       (transaction.produces().equals(produces) || transaction.produces().equals(ContentType.NONE)))
                        return method;
                }
            }
        }

        throw new InvalidResourcesException(this.uri);
    }

    private void loadSystemResources() throws InternalErrorException {
        SystemResourcesLoader loader = new SystemResourcesLoader();

        this.systemResources = loader.getDefault();
    }

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
}
