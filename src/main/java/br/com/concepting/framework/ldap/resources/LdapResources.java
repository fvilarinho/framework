package br.com.concepting.framework.ldap.resources;

import br.com.concepting.framework.ldap.constants.LdapConstants;
import br.com.concepting.framework.resources.BaseResources;
import br.com.concepting.framework.resources.FactoryResources;
import br.com.concepting.framework.util.helpers.XmlNode;

/**
 * Class responsible for storage the LDAP resources.
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
 * along with this program.  If not, see <a href="http://www.gnu.org/licenses"></a>.</pre>
 */
public class LdapResources extends BaseResources<XmlNode>{
    private static final long serialVersionUID = 3052749946121399692L;
    
    private String serverName = null;
    private int serverPort = LdapConstants.DEFAULT_PORT;
    private String authenticationType = null;
    private String userName = null;
    private String password = null;
    private String baseDn = null;
    private String userDn = null;
    private int timeout = LdapConstants.DEFAULT_TIMEOUT;
    private FactoryResources factoryResources = null;
    
    /**
     * Returns the instance that contains the factory resources of the LDAP
     * services resources.
     *
     * @return Instance that contains the factory resources
     */
    public FactoryResources getFactoryResources(){
        return this.factoryResources;
    }
    
    /**
     * Defines the instance that contains the factory resources of the LDAP
     * services resources.
     *
     * @param factoryResources Instance that contains the factory resources
     */
    public void setFactoryResources(FactoryResources factoryResources){
        this.factoryResources = factoryResources;
    }
    
    /**
     * Returns the query string for authentication.
     *
     * @return String that contains the query string.
     */
    public String getUserDn(){
        return this.userDn;
    }
    
    /**
     * Defines the query string for authentication.
     *
     * @param userDn String that contains the query string.
     */
    public void setUserDn(String userDn){
        this.userDn = userDn;
    }
    
    /**
     * Returns the timeout of the communication with the LDAP services.
     *
     * @return Numeric value that contains the timeout of communication.
     */
    public int getTimeout(){
        return this.timeout;
    }
    
    /**
     * Defines the timeout of the communication with the LDAP services.
     *
     * @param timeout Numeric value that contains the timeout of communication.
     */
    public void setTimeout(int timeout){
        this.timeout = timeout;
    }
    
    /**
     * Returns the identifier of the authentication type.
     *
     * @return String that contains the identifier.
     */
    public String getAuthenticationType(){
        return this.authenticationType;
    }
    
    /**
     * Defines the identifier of the authentication type.
     *
     * @param authenticationType String that contains the identifier.
     */
    public void setAuthenticationType(String authenticationType){
        this.authenticationType = authenticationType;
    }
    
    /**
     * Returns the listen port of the LDAP service.
     *
     * @return Numeric value that contains the port.
     */
    public int getServerPort(){
        return this.serverPort;
    }
    
    /**
     * Defines the listen port of the LDAP service.
     *
     * @param serverPort Numeric value that contains the port.
     */
    public void setServerPort(int serverPort){
        this.serverPort = serverPort;
    }
    
    /**
     * Returns the hostname/IP of the LDAP service.
     *
     * @return String that contains the hostname/IP.
     */
    public String getServerName(){
        return this.serverName;
    }
    
    /**
     * Defines the hostname/IP of the LDAP service.
     *
     * @param serverName String that contains the hostname/IP.
     */
    public void setServerName(String serverName){
        this.serverName = serverName;
    }
    
    /**
     * Returns the username of the LDAP service.
     *
     * @return String that contains the username.
     */
    public String getUserName(){
        return this.userName;
    }
    
    /**
     * Defines the username of the LDAP service.
     *
     * @param userName String that contains the username.
     */
    public void setUserName(String userName){
        this.userName = userName;
    }
    
    /**
     * Returns the user password of the LDAP service.
     *
     * @return String that contains the user password.
     */
    public String getPassword(){
        return this.password;
    }
    
    /**
     * Defines the user password of the LDAP service.
     *
     * @param password String that contains the user password.
     */
    public void setPassword(String password){
        this.password = password;
    }
    
    /**
     * Returns the base query string.
     *
     * @return String that contains the query string.
     */
    public String getBaseDn(){
        return this.baseDn;
    }
    
    /**
     * Defines the base query string.
     *
     * @param baseDn String that contains the query string.
     */
    public void setBaseDn(String baseDn){
        this.baseDn = baseDn;
    }
}