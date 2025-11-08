package br.com.concepting.framework.persistence.resources;

import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.persistence.constants.PersistenceConstants;
import br.com.concepting.framework.resources.BaseResources;
import br.com.concepting.framework.resources.FactoryResources;
import br.com.concepting.framework.util.PropertyUtil;
import br.com.concepting.framework.util.helpers.XmlNode;

import java.io.Serial;
import java.util.Collection;
import java.util.Map;

/**
 * Class responsible to store persistence resources.
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
 * along with this program.  If not, see <a href="https://www.gnu.org/licenses"></a>.</pre>
 */
public class PersistenceResources extends BaseResources<XmlNode>{
    @Serial
    private static final long serialVersionUID = 5519581642532631902L;
    
    private String serverName = null;
    private int serverPort = PersistenceConstants.DEFAULT_REPOSITORY_TYPE.getDefaultPort();
    private String instanceId = null;
    private String repositoryId = null;
    private String userName = null;
    private String password = null;
    private FactoryResources factoryResources = null;
    private Map<String, String> options = null;
    private Collection<String> mappings = null;
    
    /**
     * Returns the list with the mappings of the data models.
     *
     * @return Instance that contains the list with the mappings.
     */
    public Collection<String> getMappings(){
        return this.mappings;
    }
    
    /**
     * Defines the list with the mappings of the data models.
     *
     * @param mappings Instance that contains the list with the mappings.
     */
    public void setMappings(Collection<String> mappings){
        this.mappings = mappings;
    }
    
    /**
     * Add a mapping of a data model.
     *
     * @param mapping String that contains the mapping.
     */
    public void addMapping(String mapping){
        if(this.mappings == null)
            this.mappings = PropertyUtil.instantiate(Constants.DEFAULT_LIST_CLASS);

        if(this.mappings != null)
            this.mappings.add(mapping);
    }
    
    /**
     * Returns the identifier of the instance.
     *
     * @return String that contains the identifier.
     */
    public String getInstanceId(){
        return this.instanceId;
    }
    
    /**
     * Defines the identifier of the instance.
     *
     * @param instanceId String that contains the identifier.
     */
    public void setInstanceId(String instanceId){
        this.instanceId = instanceId;
    }
    
    /**
     * Returns the instance that contains the factory resources of the
     * persistence.
     *
     * @return Instance that contains the factory resources.
     */
    public FactoryResources getFactoryResources(){
        return this.factoryResources;
    }
    
    /**
     * Defines the instance that contains the factory resources of the
     * persistence.
     *
     * @param factoryResources Instance that contains the factory resources.
     */
    public void setFactoryResources(FactoryResources factoryResources){
        this.factoryResources = factoryResources;
    }
    
    /**
     * Returns the user password of the persistence service.
     *
     * @return String that contains the user password.
     */
    public String getPassword(){
        return this.password;
    }
    
    /**
     * Defines the user password of the persistence service.
     *
     * @param password String that contains the user password.
     */
    public void setPassword(String password){
        this.password = password;
    }
    
    /**
     * Returns the identifier of the persistence repository.
     *
     * @return String that contains the identifier of the persistence repository.
     */
    public String getRepositoryId(){
        return this.repositoryId;
    }
    
    /**
     * Defines the identifier of the persistence repository.
     *
     * @param repositoryId String that contains the identifier of the
     * persistence repository.
     */
    public void setRepositoryId(String repositoryId){
        this.repositoryId = repositoryId;
    }
    
    /**
     * Returns the hostname/IP of the persistence service.
     *
     * @return String that contains hostname/IP.
     */
    public String getServerName(){
        return this.serverName;
    }
    
    /**
     * Defines the hostname/IP of the persistence service.
     *
     * @param serverName String that contains hostname/IP.
     */
    public void setServerName(String serverName){
        this.serverName = serverName;
    }
    
    /**
     * Returns the listen port of the persistence service.
     *
     * @return Numeric value that contains the port.
     */
    public int getServerPort(){
        return this.serverPort;
    }
    
    /**
     * Defines the listen port of the persistence service.
     *
     * @param serverPort Numeric value that contains the port.
     */
    public void setServerPort(int serverPort){
        this.serverPort = serverPort;
    }
    
    /**
     * Returns the username of the persistence service.
     *
     * @return String that contains the username.
     */
    public String getUserName(){
        return this.userName;
    }
    
    /**
     * Defines the username of the persistence service.
     *
     * @param userName String that contains the username.
     */
    public void setUserName(String userName){
        this.userName = userName;
    }
    
    /**
     * Returns the persistence service options.
     *
     * @return Instance that contains the options.
     */
    public Map<String, String> getOptions(){
        return this.options;
    }
    
    /**
     * Defines the persistence service options.
     *
     * @param options Instance that contains the options.
     */
    public void setOptions(Map<String, String> options){
        this.options = options;
    }
    
    /**
     * Adds a new persistence service option.
     *
     * @param id String that contains the identifier of the option.
     * @param value String that contains the value of the option.
     */
    public void addOption(String id, String value){
        if(id != null && !id.isEmpty() && value != null && !value.isEmpty()){
            if(this.options == null)
                this.options = PropertyUtil.instantiate(Constants.DEFAULT_MAP_CLASS);

            if(this.options != null)
                this.options.put(id, value);
        }
    }
}