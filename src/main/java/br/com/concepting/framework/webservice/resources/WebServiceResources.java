package br.com.concepting.framework.webservice.resources;

import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.resources.BaseResources;
import br.com.concepting.framework.util.PropertyUtil;
import br.com.concepting.framework.util.helpers.XmlNode;
import br.com.concepting.framework.util.types.MethodType;
import br.com.concepting.framework.webservice.constants.WebServiceConstants;

import java.util.Map;

/**
 * Class responsible to store the web service resources.
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
 * along with this program.  If not, see <a href="http://www.gnu.org/licenses">...</a>.</pre>
 */
public class WebServiceResources extends BaseResources<XmlNode>{
    private static final long serialVersionUID = -4631808238382919312L;
    
    private MethodType method = null;
    private String url = null;
    private int timeout = WebServiceConstants.DEFAULT_TIMEOUT;
    private String data = null;
    private String userName = null;
    private String password = null;
    private String token = null;
    private Boolean escapeData = false;
    private Map<String, String> headers = null;

    public Boolean getEscapeData() {
        return this.escapeData;
    }

    public void setEscapeData(Boolean escapeData) {
        this.escapeData = escapeData;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the timeout (in seconds).
     *
     * @return Numeric value that contains the timeout.
     */
    public int getTimeout(){
        return this.timeout;
    }
    
    /**
     * Defines the timeout (in seconds).
     *
     * @param timeout Numeric value that contains the timeout.
     */
    public void setTimeout(int timeout){
        this.timeout = timeout;
    }
    
    /**
     * Returns the URL.
     *
     * @return String that contains the URL.
     */
    public String getUrl(){
        return this.url;
    }
    
    /**
     * Defines the URL.
     *
     * @param url String that contains the URL.
     */
    public void setUrl(String url){
        this.url = url;
    }
    
    /**
     * Returns the data.
     *
     * @return String that contains the data.
     */
    public String getData(){
        return this.data;
    }
    
    /**
     * Defines the data.
     *
     * @param data String that contains the data.
     */
    public void setData(String data){
        this.data = data;
    }
    
    /**
     * Returns the method.
     *
     * @return Instance that contains the method.
     */
    public MethodType getMethod(){
        return this.method;
    }
    
    /**
     * Defines the method.
     *
     * @param method Instance that contains the method.
     */
    public void setMethod(MethodType method){
        this.method = method;
    }
    
    /**
     * Returns the headers.
     *
     * @return Map that contains the headers.
     */
    public Map<String, String> getHeaders(){
        return this.headers;
    }
    
    /**
     * Defines the headers.
     *
     * @param headers String that contains the headers.
     */
    public void setHeaders(Map<String, String> headers){
        this.headers = headers;
    }
    
    /**
     * Add a new header.
     *
     * @param name String that contains the name.
     * @param value String that contains the value.
     */
    public void addHeader(String name, String value){
        if(this.headers == null)
            this.headers = PropertyUtil.instantiate(Constants.DEFAULT_MAP_CLASS);

        if(this.headers != null)
            this.headers.put(name, value);
    }
}