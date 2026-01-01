package br.com.concepting.framework.webservice;

import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.processors.ExpressionProcessorUtil;
import br.com.concepting.framework.processors.GenericProcessor;
import br.com.concepting.framework.processors.ProcessorFactory;
import br.com.concepting.framework.util.LanguageUtil;
import br.com.concepting.framework.util.PropertyUtil;
import br.com.concepting.framework.util.types.ContentType;
import br.com.concepting.framework.util.types.MethodType;
import br.com.concepting.framework.webservice.resources.WebServiceResources;
import br.com.concepting.framework.webservice.resources.WebServiceResourcesLoader;
import br.com.concepting.framework.webservice.util.WebServiceUtil;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Class responsible to manipulate the web services.
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
 * along with this program.  If not, see <a href="https://www.gnu.org/licenses">...</a>.</pre>
 */
public class WebService{
    private static Map<String, WebService> instances = null;
    
    private final WebServiceResources resources;

    private CloseableHttpClient client = null;
    
    /**
     * Returns the instance to manipulate the web service.
     *
     * @return Instance that manipulates the web service.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    public static WebService getInstance() throws InternalErrorException{
        WebServiceResourcesLoader loader = new WebServiceResourcesLoader();
        WebServiceResources resources = loader.getDefault();
        
        return getInstance(resources);
    }
    
    /**
     * Returns the instance to manipulate the web service.
     *
     * @param resourcesId String that contains the identifier of the resources.
     * @return Instance that manipulates the web service.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    public static WebService getInstance(String resourcesId) throws InternalErrorException{
        WebServiceResourcesLoader loader = new WebServiceResourcesLoader();
        WebServiceResources resources = loader.get(resourcesId);
        
        return getInstance(resources);
    }
    
    /**
     * Returns the instance to manipulate the web service.
     *
     * @param resourcesDirname String that contains the directory where the resources
     * are stored.
     * @param resourcesId String that contains the identifier of the resources.
     * @return Instance that manipulates the web service.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    public static WebService getInstance(String resourcesDirname, String resourcesId) throws InternalErrorException{
        WebServiceResourcesLoader loader = new WebServiceResourcesLoader(resourcesDirname);
        WebServiceResources resources = loader.get(resourcesId);
        
        return getInstance(resources);
    }
    
    /**
     * Returns the instance to manipulate the web service.
     *
     * @param resources Instance that contains the resources.
     * @return Instance that manipulates the web service.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    public static WebService getInstance(WebServiceResources resources) throws InternalErrorException{
        if(instances == null)
            instances = PropertyUtil.instantiate(Constants.DEFAULT_MAP_CLASS);

        if (instances != null) {
            WebService instance = instances.get(resources.getId());

            if(instance == null){
                instance = new WebService(resources);

                instances.put(resources.getId(), instance);
            }

            return instance;
        }

        return null;
    }
    
    /**
     * Constructor - Defines the web resources.
     *
     * @param resources Instance that contains the resources.
     * @throws InternalErrorException Occurs when it was not possible to
     * instantiate the web services based on the specified parameters.
     */
    private WebService(WebServiceResources resources) throws InternalErrorException{
        super();
        
        this.resources = resources;
        
        initialize();
    }
    
    /**
     * Initialize the communication with the web service.
     *
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    private void initialize() throws InternalErrorException{
        this.client = WebServiceUtil.getClient(this.resources.getTimeout());
    }
    
    /**
     * Process the web service.
     *
     * @return Instance that contains the response of the web service.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    public CloseableHttpResponse process() throws InternalErrorException{
        return process(null);
    }
    
    /**
     * Process the web service.
     *
     * @param value Instance that should be used in the processing.
     * @return Instance that contains the response of the web service.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    public CloseableHttpResponse process(Object value) throws InternalErrorException{
        return process(WebService.class.getName(), value);
    }
    
    /**
     * Process the web service.
     *
     * @param value Instance that should be used in the processing.
     * @param language String that contains the language that should be used in the processing.
     * @return Instance that contains the response of the web service.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    public CloseableHttpResponse process(Object value, String language) throws InternalErrorException{
        return process(WebService.class.getName(), value, language);
    }
    
    /**
     * Process the web service.
     *
     * @param value Instance that should be used in the processing.
     * @param language Instance that contains the language that should be used in the processing.
     * @return Instance that contains the response of the web service.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    public CloseableHttpResponse process(Object value, Locale language) throws InternalErrorException{
        return process(WebService.class.getName(), value, language);
    }
    
    /**
     * Process the web service.
     *
     * @param domain String that contains the domain of the processing.
     * @param value Instance that should be used in the processing.
     * @return Instance that contains the response of the web service.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    public CloseableHttpResponse process(String domain, Object value) throws InternalErrorException{
        return process(domain, value, LanguageUtil.getDefaultLanguage());
    }
    
    /**
     * Process the web service.
     *
     * @param domain String that contains the domain of the processing.
     * @param value Instance that should be used in the processing.
     * @param language String that contains the language that should be used in the processing.
     * @return Instance that contains the response of the web service.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    public CloseableHttpResponse process(String domain, Object value, String language) throws InternalErrorException{
        if(language == null || language.isEmpty())
            return process(domain, value, LanguageUtil.getDefaultLanguage());
        
        return process(domain, value, LanguageUtil.getLanguageByString(language));
    }
    
    /**
     * Process the web service.
     *
     * @param domain String that contains the domain of the processing.
     * @param value Instance that should be used in the processing.
     * @param language Instance that contains the language that should be used in the processing.
     * @return Instance that contains the response of the web service.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    public CloseableHttpResponse process(String domain, Object value, Locale language) throws InternalErrorException{
        try{
            HttpUriRequest request = buildRequest(domain, value, language);

            return client.execute(request);
        }
        catch(Throwable e){
            throw new InternalErrorException(e);
        }
    }

    /**
     * Set the authentication strategy of the web service.
     *
     * @param request Instance that contains the request of the web service.
     * @param domain String that contains the domain of the processing.
     * @param value Instance that should be used in the processing.
     * @param language Instance that contains the language that should be used in the processing.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    private void setAuthentication(HttpUriRequest request, String domain, Object value, Locale language) throws InternalErrorException {
        boolean escapeData = this.resources.getEscapeData();

        if(this.resources.getToken() != null && !this.resources.getToken().isEmpty()) {
            String token = ExpressionProcessorUtil.fillVariablesInString(domain, this.resources.getToken(), false, escapeData, language);

            token = PropertyUtil.fillPropertiesInString(value, token, false, escapeData, language);

            request.setHeader(HttpHeaders.AUTHORIZATION, "Basic " + token);
        }

        if(this.resources.getUserName() != null &&
           !this.resources.getUserName().isEmpty() &&
           this.resources.getPassword() != null &&
           !this.resources.getPassword().isEmpty()){
            String user = ExpressionProcessorUtil.fillVariablesInString(domain, this.resources.getUserName(), false, escapeData, language);

            user = PropertyUtil.fillPropertiesInString(value, user,false, escapeData, language);

            String password = ExpressionProcessorUtil.fillVariablesInString(domain, this.resources.getPassword(),false, escapeData, language);

            password = PropertyUtil.fillPropertiesInString(value, password,false, escapeData, language);

            String encoding = Base64.getEncoder().encodeToString((user + ":" + password).getBytes());

            request.setHeader(HttpHeaders.AUTHORIZATION, "Basic " + encoding);
        }
    }

    /**
     * Set the headers of the web service.
     *
     * @param request Instance that contains the request of the web service.
     * @param domain String that contains the domain of the processing.
     * @param value Instance that should be used in the processing.
     * @param language Instance that contains the language that should be used in the processing.
     */
    private void setRequestHeaders(HttpUriRequest request, String domain, Object value, Locale language) {
        request.setHeader(HttpHeaders.CONTENT_TYPE, ContentType.TXT.getMimeType());
        request.setHeader(HttpHeaders.ACCEPT, ContentType.TXT.getMimeType());

        Map<String, String> headers = this.resources.getHeaders();

        if(headers != null && !headers.isEmpty()){
            boolean escapeData = this.resources.getEscapeData();

            for(Entry<String, String> header: headers.entrySet()){
                String headerName = ExpressionProcessorUtil.fillVariablesInString(domain, header.getKey(), false, escapeData, language);

                headerName = PropertyUtil.fillPropertiesInString(value, headerName,false, escapeData, language);

                String headerValue = ExpressionProcessorUtil.fillVariablesInString(domain, header.getValue(), false, escapeData, language);

                headerValue = PropertyUtil.fillPropertiesInString(value, headerValue, false, escapeData, language);

                request.addHeader(headerName, headerValue);
            }
        }
    }

    /**
     * Build the request of the web service.
     *
     * @param domain String that contains the domain of the processing.
     * @param value Instance that should be used in the processing.
     * @param language Instance that contains the language that should be used in the processing.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    private HttpUriRequest buildRequest(String domain, Object value, Locale language) throws InternalErrorException {
        if(language == null)
            language = LanguageUtil.getDefaultLanguage();

        boolean escapeData = this.resources.getEscapeData();
        String url = ExpressionProcessorUtil.fillVariablesInString(domain, this.resources.getUrl(), false, escapeData, language);

        url = PropertyUtil.fillPropertiesInString(value, url, false, escapeData, language);

        HttpUriRequest request;

        if(this.resources.getMethod() == MethodType.POST)
            request = new HttpPost(url);
        else if(this.resources.getMethod() == MethodType.PUT)
            request = new HttpPut(url);
        else if(this.resources.getMethod() == MethodType.DELETE)
            request = new HttpDelete(url);
        else if(this.resources.getMethod() == MethodType.HEAD)
            request = new HttpHead(url);
        else if(this.resources.getMethod() == MethodType.OPTIONS)
            request = new HttpOptions(url);
        else if(this.resources.getMethod() == MethodType.TRACE)
            request = new HttpTrace(url);
        else
            request = new HttpGet(url);

        if(this.resources.getMethod() == MethodType.POST || this.resources.getMethod() == MethodType.PUT){
            String data = ExpressionProcessorUtil.fillVariablesInString(domain, this.resources.getData(), false, escapeData, language);

            data = PropertyUtil.fillPropertiesInString(value, data, false, escapeData, language);

            GenericProcessor processor = ProcessorFactory.getInstance().getProcessor(domain, value, data, language);

            data = processor.process();

            try {
                if (request instanceof HttpPost post)
                    post.setEntity(new StringEntity(data));
                else if (request instanceof HttpPut put)
                    put.setEntity(new StringEntity(data));
            }
            catch (UnsupportedEncodingException e) {
                throw new InternalErrorException(e);
            }
        }

        setRequestHeaders(request, domain, value, language);
        setAuthentication(request, domain, value, language);

        return request;
    }
}