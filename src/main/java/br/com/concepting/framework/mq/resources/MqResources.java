package br.com.concepting.framework.mq.resources;

import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.mq.constants.MqConstants;
import br.com.concepting.framework.resources.BaseResources;
import br.com.concepting.framework.util.PropertyUtil;
import br.com.concepting.framework.util.helpers.XmlNode;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;

/**
 * Class responsible for storage the MQ resources.
 *
 * @author fvilarinho
 * @since 3.5.0
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
public class MqResources extends BaseResources<XmlNode>{
    @Serial
    private static final long serialVersionUID = 3052749946121399692L;
    
    private String serverName = null;
    private int serverPort = MqConstants.DEFAULT_PORT;
    private String userName = null;
    private String password = null;
    private Collection<MqQueue> queues = null;
    
    /**
     * Returns the username to connect into the service.
     *
     * @return String that contains the username.
     */
    public String getUserName(){
        return this.userName;
    }
    
    /**
     * Defines the username to connect into the service.
     *
     * @param userName String that contains the username.
     */
    public void setUserName(String userName){
        this.userName = userName;
    }
    
    /**
     * Returns the password of the username to connect into the service.
     *
     * @return String that contains the password.
     */
    public String getPassword(){
        return this.password;
    }
    
    /**
     * Defines the password of the username to connect into the service.
     *
     * @param password String that contains the password.
     */
    public void setPassword(String password){
        this.password = password;
    }
    
    /**
     * Returns the list of queues.
     *
     * @return List that contains the queues.
     */
    public Collection<MqQueue> getQueues(){
        return this.queues;
    }
    
    /**
     * Defines the list of queues.
     *
     * @param queues List that contains the queues.
     */
    public void setQueues(Collection<MqQueue> queues){
        this.queues = queues;
    }
    
    /**
     * Adds a queue.
     *
     * @param queue Instance that contains the queue.
     */
    public void addQueue(MqQueue queue){
        if(queue != null){
            if(this.queues == null)
                this.queues = PropertyUtil.instantiate(Constants.DEFAULT_LIST_CLASS);

            if(this.queues != null)
                this.queues.add(queue);
        }
    }
    
    /**
     * Returns the listen port of the MQ service.
     *
     * @return Numeric value that contains the port.
     */
    public int getServerPort(){
        return this.serverPort;
    }
    
    /**
     * Defines the listen port of the MQ service.
     *
     * @param serverPort Numeric value that contains the port.
     */
    public void setServerPort(int serverPort){
        this.serverPort = serverPort;
    }
    
    /**
     * Returns the hostname/IP of the MQ service.
     *
     * @return String that contains the hostname/IP.
     */
    public String getServerName(){
        return this.serverName;
    }
    
    /**
     * Defines the hostname/IP of the MQ service.
     *
     * @param serverName String that contains the hostname/IP.
     */
    public void setServerName(String serverName){
        this.serverName = serverName;
    }

    public static class MqQueue implements Serializable {
        @Serial
        private static final long serialVersionUID = -4707395643493075904L;

        private String id = null;
        private String listenerClass = null;

        /**
         * Returns the identifier of the queue.
         *
         * @return String that contains the identifier.
         */
        public String getId(){
            return this.id;
        }

        /**
         * Defines the identifier of the queue.
         *
         * @param id String that contains the identifier.
         */
        public void setId(String id){
            this.id = id;
        }

        /**
         * Returns the identifier of the queue listener.
         *
         * @return String that contains the identifier.
         */
        public String getListenerClass(){
            return this.listenerClass;
        }

        /**
         * Defines the identifier of the queue listener.
         *
         * @param listenerClass String that contains the identifier.
         */
        public void setListenerClass(String listenerClass){
            this.listenerClass = listenerClass;
        }
    }
}