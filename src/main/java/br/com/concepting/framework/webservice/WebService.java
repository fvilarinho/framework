package br.com.concepting.framework.webservice;

import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.processors.ExpressionProcessorUtil;
import br.com.concepting.framework.processors.GenericProcessor;
import br.com.concepting.framework.processors.ProcessorFactory;
import br.com.concepting.framework.util.LanguageUtil;
import br.com.concepting.framework.util.PropertyUtil;
import br.com.concepting.framework.util.types.MethodType;
import br.com.concepting.framework.webservice.constants.WebServiceConstants;
import br.com.concepting.framework.webservice.resources.WebServiceResources;
import br.com.concepting.framework.webservice.resources.WebServiceResourcesLoader;
import br.com.concepting.framework.webservice.util.WebServiceUtil;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
public class WebService{
    private static Map<String, WebService> instances = null;
    
    private WebServiceResources resources = null;
    private Client client = null;
    
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
        
        WebService instance = instances.get(resources.getId());
        
        if(instance == null){
            instance = new WebService(resources);
            
            instances.put(resources.getId(), instance);
        }
        
        return instance;
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
    public Response process() throws InternalErrorException{
        return process(null);
    }
    
    /**
     * Process the web service.
     *
     * @param value Instance that should be used in the processing.
     * @return Instance that contains the response of the web service.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    public Response process(Object value) throws InternalErrorException{
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
    public Response process(Object value, String language) throws InternalErrorException{
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
    public Response process(Object value, Locale language) throws InternalErrorException{
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
    public Response process(String domain, Object value) throws InternalErrorException{
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
    public Response process(String domain, Object value, String language) throws InternalErrorException{
        if(language == null || language.length() == 0)
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
    public Response process(String domain, Object value, Locale language) throws InternalErrorException{
        try{
            if(language == null)
                language = LanguageUtil.getDefaultLanguage();
            
            String url = PropertyUtil.fillPropertiesInString(value, ExpressionProcessorUtil.fillVariablesInString(domain, this.resources.getUrl(), false, true, language), false, true, language);
            WebTarget target = this.client.target(url);
            Builder request = target.request();
            Map<String, String> headers = this.resources.getHeaders();
            String accept = MediaType.TEXT_PLAIN;
            
            if(headers != null && !headers.isEmpty()){
                for(Entry<String, String> header: headers.entrySet()){
                    String headerName = PropertyUtil.fillPropertiesInString(value, ExpressionProcessorUtil.fillVariablesInString(domain, header.getKey(), false, true, language), false, true, language);
                    String headerValue = PropertyUtil.fillPropertiesInString(value, ExpressionProcessorUtil.fillVariablesInString(domain, header.getValue(), false, true, language), false, true, language);
                    
                    if(headerName.equalsIgnoreCase(WebServiceConstants.ACCEPT_ATTRIBUTE_ID))
                        accept = headerValue;
                    
                    request = request.header(headerName, headerValue);
                }
            }
            
            Response response = null;
            
            if(this.resources.getMethod() == MethodType.POST || this.resources.getMethod() == MethodType.PUT){
                GenericProcessor processor = ProcessorFactory.getInstance().
                        getProcessor(domain, value, PropertyUtil.fillPropertiesInString(value, ExpressionProcessorUtil.fillVariablesInString(domain, this.resources.getData(), false, true, language), false, true, language), language);
                String contentData = processor.process();
                
                response = request.post(Entity.entity(contentData, accept));
            }
            else if(this.resources.getMethod() == MethodType.GET)
                response = request.get();
            else if(this.resources.getMethod() == MethodType.DELETE)
                response = request.delete();
            else if(this.resources.getMethod() == MethodType.HEAD)
                response = request.head();
            
            return response;
        }
        catch(Throwable e){
            throw new InternalErrorException(e);
        }
    }
}