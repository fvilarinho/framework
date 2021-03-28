package br.com.concepting.framework.mail.resources;

import br.com.concepting.framework.mail.types.MailStorageType;
import br.com.concepting.framework.mail.types.MailTransportType;
import br.com.concepting.framework.resources.BaseResources;
import br.com.concepting.framework.util.helpers.XmlNode;

/**
 * Class responsible to store the mail resources.
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
public class MailResources extends BaseResources<XmlNode>{
    private static final long serialVersionUID = -4631808238382919312L;
    
    private MailTransportType transport = null;
    private String transportServerName = null;
    private Integer transportServerPort = null;
    private String transportUserName = null;
    private String transportPassword = null;
    private Boolean transportUseSsl = null;
    private Boolean transportUseTls = null;
    private MailStorageType storage = null;
    private String storageServerName = null;
    private Integer storageServerPort = null;
    private String storageUserName = null;
    private String storagePassword = null;
    private Boolean storageUseSsl = null;
    private Boolean storageUseTls = null;
    
    /**
     * Returns the type of transport of messages.
     *
     * @return Instance that contains the type of transport.
     */
    public MailTransportType getTransport(){
        return this.transport;
    }
    
    /**
     * Defines the type of transport of messages.
     *
     * @param transport Instance that contains the type of transport.
     */
    public void setTransport(MailTransportType transport){
        this.transport = transport;
    }
    
    /**
     * Returns the type of storage of messages.
     *
     * @return Instance that contains the type of transport.
     */
    public MailStorageType getStorage(){
        return this.storage;
    }
    
    /**
     * Defines the type of storage of messages.
     *
     * @param storage Instance that contains the type of transport.
     */
    public void setStorage(MailStorageType storage){
        this.storage = storage;
    }
    
    /**
     * Returns the hostname/IP of the mail transport server.
     *
     * @return String that contains the hostname/IP.
     */
    public String getTransportServerName(){
        return this.transportServerName;
    }
    
    /**
     * Defines the hostname/IP of the mail transport server.
     *
     * @param transportServerName String that contains the hostname/IP.
     */
    public void setTransportServerName(String transportServerName){
        this.transportServerName = transportServerName;
    }
    
    /**
     * Returns the port of the mail transport server.
     *
     * @return Numeric value that contains the port.
     */
    public Integer getTransportServerPort(){
        return this.transportServerPort;
    }
    
    /**
     * Defines the port of the mail transport server.
     *
     * @param transportServerPort Numeric value that contains the port.
     */
    public void setTransportServerPort(Integer transportServerPort){
        this.transportServerPort = transportServerPort;
    }
    
    /**
     * Returns the user name to be used in the mail transport server.
     *
     * @return String that contains the user name.
     */
    public String getTransportUserName(){
        return this.transportUserName;
    }
    
    /**
     * Defines the user name to be used in the mail transport server.
     *
     * @param transportUserName String that contains the user name.
     */
    public void setTransportUserName(String transportUserName){
        this.transportUserName = transportUserName;
    }
    
    /**
     * Returns the user password to be used in the mail transport server.
     *
     * @return String that contains the user password.
     */
    public String getTransportPassword(){
        return this.transportPassword;
    }
    
    /**
     * Defines the user password to be used in the mail transport server.
     *
     * @param transportPassword String that contains the user password.
     */
    public void setTransportPassword(String transportPassword){
        this.transportPassword = transportPassword;
    }
    
    /**
     * Indicates if the SSL must be used in the mail transport.
     *
     * @return True/False.
     */
    public Boolean getTransportUseSsl(){
        return this.transportUseSsl;
    }
    
    /**
     * Defines if the SSL must be used in the mail transport.
     *
     * @param transportUseSsl True/False.
     */
    public void setTransportUseSsl(Boolean transportUseSsl){
        this.transportUseSsl = transportUseSsl;
    }
    
    /**
     * Indicates if the TLS must be used in the mail transport.
     *
     * @return True/False.
     */
    public Boolean getTransportUseTls(){
        return this.transportUseTls;
    }
    
    /**
     * Defines if the TLS must be used in the mail transport.
     *
     * @param transportUseTls True/False.
     */
    public void setTransportUseTls(Boolean transportUseTls){
        this.transportUseTls = transportUseTls;
    }
    
    /**
     * Returns the hostname/IP the mail storage server.
     *
     * @return String that contains the hostname/IP.
     */
    public String getStorageServerName(){
        return this.storageServerName;
    }
    
    /**
     * Defines the hostname/IP the mail storage server.
     *
     * @param storageServerName String that contains the hostname/IP.
     */
    public void setStorageServerName(String storageServerName){
        this.storageServerName = storageServerName;
    }
    
    /**
     * Returns the mail storage port.
     *
     * @return Numeric value that contains the port.
     */
    public Integer getStorageServerPort(){
        return this.storageServerPort;
    }
    
    /**
     * Defines the mail storage port.
     *
     * @param storageServerPort Numeric value that contains the port.
     */
    public void setStorageServerPort(Integer storageServerPort){
        this.storageServerPort = storageServerPort;
    }
    
    /**
     * Returns the user name to be used in the mail storage server.
     *
     * @return String that contains the user name.
     */
    public String getStorageUserName(){
        return this.storageUserName;
    }
    
    /**
     * Defines the user name to be used in the mail storage server.
     *
     * @param storageUserName String that contains the user name.
     */
    public void setStorageUserName(String storageUserName){
        this.storageUserName = storageUserName;
    }
    
    /**
     * Returns the user password to be used in the mail storage server.
     *
     * @return String that contains the user password.
     */
    public String getStoragePassword(){
        return this.storagePassword;
    }
    
    /**
     * Defines the user password to be used in the mail storage server.
     *
     * @param storagePassword String that contains the user password.
     */
    public void setStoragePassword(String storagePassword){
        this.storagePassword = storagePassword;
    }
    
    /**
     * Returns if the SSL must be used in the mail storage.
     *
     * @return True/False.
     */
    public Boolean getStorageUseSsl(){
        return this.storageUseSsl;
    }
    
    /**
     * Defines if the SSL must be used in the mail storage.
     *
     * @param storageUseSsl True/False.
     */
    public void setStorageUseSsl(Boolean storageUseSsl){
        this.storageUseSsl = storageUseSsl;
    }
    
    /**
     * Returns if the TLS must be used in the mail storage.
     *
     * @return True/False.
     */
    public Boolean getStorageUseTls(){
        return this.storageUseTls;
    }
    
    /**
     * Defines if the TLS must be used in the mail storage.
     *
     * @param storageUseTls True/False.
     */
    public void setStorageUseTls(Boolean storageUseTls){
        this.storageUseTls = storageUseTls;
    }
}