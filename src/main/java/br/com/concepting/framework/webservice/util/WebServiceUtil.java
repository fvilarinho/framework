package br.com.concepting.framework.webservice.util;

import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.helpers.ModelInfo;
import br.com.concepting.framework.model.util.ModelUtil;
import br.com.concepting.framework.util.ByteUtil;
import br.com.concepting.framework.util.PropertyUtil;
import br.com.concepting.framework.util.helpers.PropertyInfo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Utility class responsible to manipulate web services.
 *
 * @author fvilarinho
 * @since 3.7.0
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
public class WebServiceUtil{
    private static final ObjectMapper propertyMapper = PropertyUtil.getMapper();
    
    /**
     * Returns the client instance.
     *
     * @return Instance of the client.
     */
    public static CloseableHttpClient getClient(){
        return HttpClients.createDefault();
    }
    
    /**
     * Returns the client instance.
     *
     * @param timeout Instance that contains the default timeout in seconds.
     * @return Instance of the client.
     */
    public static CloseableHttpClient getClient(int timeout){
        RequestConfig config = RequestConfig.custom().
                                             setConnectTimeout(timeout * 1000).
                                             setConnectionRequestTimeout(timeout * 1000).
                                             setSocketTimeout(timeout * 1000).build();
        return HttpClientBuilder.create().setDefaultRequestConfig(config).build();
    }
    
    /**
     * Deserializes a content.
     *
     * @param <O> Class that defines the content.
     * @param in Stream that contains the content.
     * @param clazz Class that defines the content.
     * @return Instance that contains the content.
     * @throws IllegalArgumentException Occurs when was not possible to deserialize the content.
     * @throws NoSuchMethodException Occurs when was not possible to deserialize the content.
     * @throws IllegalAccessException Occurs when was not possible to deserialize the content.
     * @throws InvocationTargetException Occurs when was not possible to deserialize the content.
     * @throws InstantiationException Occurs when was not possible to deserialize the content.
     * @throws ClassNotFoundException Occurs when was not possible to deserialize the content.
     * @throws NoSuchFieldException Occurs when was not possible to deserialize the content.
     * @throws IOException Occurs when was not possible to deserialize the content.
     */
    public static <O> O deserialize(InputStream in, Class<?> clazz) throws IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException, NoSuchFieldException, IOException{
        String responseContent = new String(ByteUtil.fromTextStream(in));
        
        return deserialize(responseContent, clazz);
    }
    
    /**
     * Deserializes a content.
     *
     * @param <O> Class that defines the content.
     * @param content String that contains the content.
     * @param clazz Class that defines the content.
     * @return Instance that contains the content.
     * @throws IllegalArgumentException Occurs when was not possible to deserialize the content.
     * @throws NoSuchMethodException Occurs when was not possible to deserialize the content.
     * @throws IllegalAccessException Occurs when was not possible to deserialize the content.
     * @throws InvocationTargetException Occurs when was not possible to deserialize the content.
     * @throws InstantiationException Occurs when was not possible to deserialize the content.
     * @throws ClassNotFoundException Occurs when was not possible to deserialize the content.
     * @throws NoSuchFieldException Occurs when was not possible to deserialize the content.
     * @throws IOException Occurs when was not possible to deserialize the content.
     */
    public static <O> O deserialize(String content, Class<?> clazz) throws IOException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException, NoSuchFieldException{
        if(clazz != null){
            Map<String, Object> contentMap = propertyMapper.readValue(content, new TypeReference<>(){});
            
            if(contentMap != null)
                return deserialize(contentMap, clazz);
        }
        
        return null;
    }
    
    @SuppressWarnings({"unchecked"})
    private static <O> O deserialize(Map<String, Object> contentMap, Class<?> clazz) throws IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException, NoSuchFieldException{
        O result = null;
        
        if(clazz != null){
            if(PropertyUtil.isModel(clazz)){
                Class<? extends BaseModel> modelClass = (Class<? extends BaseModel>) clazz;
                ModelInfo modelInfo = ModelUtil.getInfo(modelClass);
                Collection<PropertyInfo> propertiesInfos = modelInfo.getPropertiesInfo();
                
                if(propertiesInfos != null && !propertiesInfos.isEmpty()){
                    result = (O) propertyMapper.convertValue(contentMap, clazz);
                    
                    for(PropertyInfo propertyInfo: propertiesInfos){
                        if(propertyInfo.isModel()){
                            Map<String, Object> propertyMap = (Map<String, Object>) contentMap.get(propertyInfo.getId());
                            Class<? extends BaseModel> propertyClass = (Class<? extends BaseModel>) propertyInfo.getClazz();
                            O propertyValue = deserialize(propertyMap, propertyClass);
                            
                            if(propertyValue != null)
                                contentMap.put(propertyInfo.getId(), propertyValue);
                        }
                        else if(propertyInfo.hasModel()){
                            List<Map<String, Object>> propertyValuesMap = (List<Map<String, Object>>) contentMap.get(propertyInfo.getId());
                            
                            if(propertyValuesMap != null && !propertyValuesMap.isEmpty()){
                                Class<? extends BaseModel> propertyClass = (Class<? extends BaseModel>) propertyInfo.getCollectionItemsClass();
                                List<Object> propertyValues = PropertyUtil.instantiate(Constants.DEFAULT_LIST_CLASS);
                                
                                for(Map<String, Object> item: propertyValuesMap){
                                    Object propertyValue = deserialize(item, propertyClass);
                                    
                                    if(propertyValue != null)
                                        if(propertyValues != null)
                                            propertyValues.add(propertyValue);
                                }
                            }
                        }
                    }
                }
            }
        }
        
        return result;
    }
    
    /**
     * Serializes an object.
     *
     * @param value Instance of the object.
     * @param out Stream that should be used.
     * @throws IOException Occurs when an I/O issue was triggered..
     */
    public static void serialize(Object value, OutputStream out) throws IOException{
        propertyMapper.writeValue(out, value);
    }
    
    /**
     * Serializes an object.
     *
     * @param value Instance of the object.
     * @return String that contains the object.
     * @throws IOException Occurs when an I/O issue was triggered..
     */
    public static String serialize(Object value) throws IOException{
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        
        serialize(value, out);
        
        return out.toString();
    }
}