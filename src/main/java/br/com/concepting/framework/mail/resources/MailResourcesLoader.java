package br.com.concepting.framework.mail.resources;

import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.mail.constants.MailConstants;
import br.com.concepting.framework.mail.types.MailStorageType;
import br.com.concepting.framework.mail.types.MailTransportType;
import br.com.concepting.framework.network.constants.NetworkConstants;
import br.com.concepting.framework.network.resources.NetworkResourcesLoader;
import br.com.concepting.framework.resources.exceptions.InvalidResourcesException;
import br.com.concepting.framework.security.constants.SecurityConstants;
import br.com.concepting.framework.util.NumberUtil;
import br.com.concepting.framework.util.helpers.XmlNode;

import java.text.ParseException;

/**
 * Class responsible to manipulate the mail resources.
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
public class MailResourcesLoader extends NetworkResourcesLoader<MailResources>{
    /**
     * Constructor - Manipulates the default resources.
     *
     * @throws InvalidResourcesException Occurs when the default resources could
     * not be read.
     */
    public MailResourcesLoader() throws InvalidResourcesException{
        super();
    }
    
    /**
     * Constructor - Manipulates specific resources.
     *
     * @param resourcesDirname String that contains the directory where the resources
     * are stored.
     * @throws InvalidResourcesException Occurs when the resources could not be read.
     */
    public MailResourcesLoader(String resourcesDirname) throws InvalidResourcesException{
        super(resourcesDirname);
    }

    @Override
    public MailResources parseResources(XmlNode resourcesNode) throws InvalidResourcesException{
        String resourcesDirname = getResourcesDirname();
        String resourcesId = getResourcesId();
        MailResources resources = super.parseResources(resourcesNode);
        XmlNode storageNode = resourcesNode.getNode(MailConstants.STORAGE_ATTRIBUTE_ID);
        
        if(storageNode != null){
            XmlNode serverNameNode = storageNode.getNode(NetworkConstants.SERVER_NAME_ATTRIBUTE_ID);
            
            if(serverNameNode != null){
                String serverName = serverNameNode.getValue();
                
                if(serverName == null || serverName.isEmpty())
                    throw new InvalidResourcesException(resourcesDirname, resourcesId, serverNameNode.getText());
                
                resources.setStorageServerName(serverName);
            }
            else
                resources.setStorageServerName(NetworkConstants.DEFAULT_LOCALHOST_NAME_ID);
            
            XmlNode serverPortNode = storageNode.getNode(NetworkConstants.SERVER_PORT_ATTRIBUTE_ID);
            
            if(serverPortNode != null){
                if(serverPortNode.getValue() == null || serverPortNode.getValue().isEmpty())
                    throw new InvalidResourcesException(resourcesDirname, resourcesId, serverPortNode.getText());
                
                try{
                    int serverPort = NumberUtil.parseInt(serverPortNode.getValue());
                    
                    resources.setStorageServerPort(serverPort);
                }
                catch(ParseException e){
                    throw new InvalidResourcesException(resourcesDirname, resourcesId, serverPortNode.getText(), e);
                }
            }
            else
                resources.setStorageServerPort(MailConstants.DEFAULT_STORAGE_PORT);
            
            String storageType = storageNode.getAttribute(Constants.TYPE_ATTRIBUTE_ID);
            
            if(storageType != null && !storageType.isEmpty()){
                try{
                    resources.setStorage(MailStorageType.valueOf(storageType.toUpperCase()));
                }
                catch(IllegalArgumentException e){
                    throw new InvalidResourcesException(resourcesDirname, resourcesId, storageNode.getText(), e);
                }
            }
            else
                resources.setStorage(MailConstants.DEFAULT_STORAGE_TYPE);
            
            XmlNode userNameNode = storageNode.getNode(SecurityConstants.USER_NAME_ATTRIBUTE_ID);
            
            if(userNameNode != null){
                String userName = userNameNode.getValue();
                
                if(userName != null && !userName.isEmpty())
                    resources.setStorageUserName(userName);
            }
            
            XmlNode passwordNode = storageNode.getNode(SecurityConstants.PASSWORD_ATTRIBUTE_ID);
            
            if(passwordNode != null){
                String password = passwordNode.getValue();
                
                if(password != null && !password.isEmpty())
                    resources.setStoragePassword(password);
            }
            
            boolean useSsl = Boolean.parseBoolean(storageNode.getAttribute(NetworkConstants.USE_SSL_ATTRIBUTE_ID));
            
            resources.setStorageUseSsl(useSsl);
            
            boolean useTls = Boolean.parseBoolean(storageNode.getAttribute(NetworkConstants.USE_TLS_ATTRIBUTE_ID));
            
            resources.setStorageUseTls(useTls);
        }
        else{
            resources.setStorage(MailConstants.DEFAULT_STORAGE_TYPE);
            resources.setStorageServerName(NetworkConstants.DEFAULT_LOCALHOST_NAME_ID);
            resources.setStorageServerPort(MailConstants.DEFAULT_STORAGE_PORT);
        }
        
        XmlNode transportNode = resourcesNode.getNode(MailConstants.TRANSPORT_ATTRIBUTE_ID);
        
        if(transportNode != null){
            XmlNode serverNameNode = transportNode.getNode(NetworkConstants.SERVER_NAME_ATTRIBUTE_ID);
            
            if(serverNameNode != null){
                String serverName = serverNameNode.getValue();
                
                if(serverName == null || serverName.isEmpty())
                    throw new InvalidResourcesException(resourcesDirname, resourcesId, serverNameNode.getText());
                
                resources.setTransportServerName(serverName);
            }
            else
                resources.setTransportServerName(NetworkConstants.DEFAULT_LOCALHOST_NAME_ID);
            
            XmlNode serverPortNode = transportNode.getNode(NetworkConstants.SERVER_PORT_ATTRIBUTE_ID);
            
            if(serverPortNode != null){
                if(serverPortNode.getValue() == null || serverPortNode.getValue().isEmpty())
                    throw new InvalidResourcesException(resourcesDirname, resourcesId, serverPortNode.getText());
                
                try{
                    int serverPort = NumberUtil.parseInt(serverPortNode.getValue());
                    
                    resources.setTransportServerPort(serverPort);
                }
                catch(ParseException e){
                    throw new InvalidResourcesException(resourcesDirname, resourcesId, serverPortNode.getText(), e);
                }
            }
            else
                resources.setTransportServerPort(MailConstants.DEFAULT_TRANSPORT_PORT);
            
            String transportType = transportNode.getAttribute(Constants.TYPE_ATTRIBUTE_ID);
            
            if(transportType != null && !transportType.isEmpty()){
                try{
                    resources.setTransport(MailTransportType.valueOf(transportType.toUpperCase()));
                }
                catch(IllegalArgumentException e){
                    throw new InvalidResourcesException(resourcesDirname, resourcesId, transportNode.getText(), e);
                }
            }
            else
                resources.setTransport(MailConstants.DEFAULT_TRANSPORT_TYPE);
            
            XmlNode userNameNode = transportNode.getNode(SecurityConstants.USER_NAME_ATTRIBUTE_ID);
            
            if(userNameNode != null){
                String userName = userNameNode.getValue();
                
                if(userName != null && !userName.isEmpty())
                    resources.setTransportUserName(userName);
            }
            
            XmlNode passwordNode = transportNode.getNode(SecurityConstants.PASSWORD_ATTRIBUTE_ID);
            
            if(passwordNode != null){
                String password = passwordNode.getValue();
                
                if(password != null && !password.isEmpty())
                    resources.setTransportPassword(password);
            }
            
            boolean useSsl = Boolean.parseBoolean(transportNode.getAttribute(NetworkConstants.USE_SSL_ATTRIBUTE_ID));
            
            resources.setTransportUseSsl(useSsl);
            
            boolean useTls = Boolean.parseBoolean(transportNode.getAttribute(NetworkConstants.USE_TLS_ATTRIBUTE_ID));
            
            resources.setTransportUseTls(useTls);
        }
        
        return resources;
    }

    @Override
    protected XmlNode parseContent() throws InvalidResourcesException{
        XmlNode contentNode = super.parseContent();

        if(contentNode == null)
            throw new InvalidResourcesException(getResourcesDirname(), getResourcesId());

        XmlNode resourcesNode = contentNode.getNode(MailConstants.DEFAULT_ID);
        
        if(resourcesNode == null)
            throw new InvalidResourcesException(getResourcesDirname(), getResourcesId(), contentNode.getText());
        
        return resourcesNode;
    }
}