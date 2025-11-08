package br.com.concepting.framework.ldap;

import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.ldap.constants.LdapConstants;
import br.com.concepting.framework.ldap.resources.LdapResources;
import br.com.concepting.framework.ldap.resources.LdapResourcesLoader;
import br.com.concepting.framework.util.PropertyUtil;
import br.com.concepting.framework.util.helpers.XmlNode;

import javax.naming.Context;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.Map;
import java.util.Properties;

/**
 * Class responsible to manipulate the LDAP service.
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
public class Ldap{
    private static Map<String, Ldap> instances = null;
    
    private final LdapResources resources;

    private DirContext context = null;
    
    /**
     * Returns the instance to manipulate the LDAP service.
     *
     * @return Instance that manipulates the LDAP service.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    public static Ldap getInstance() throws InternalErrorException{
        LdapResourcesLoader loader = new LdapResourcesLoader();
        LdapResources resources = loader.getDefault();
        
        return getInstance(resources);
    }
    
    /**
     * Returns the instance to manipulate the LDAP service.
     *
     * @param resourcesId String that contains the identifier of the resources.
     * @return Instance that manipulates the LDAP service.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    public static Ldap getInstance(String resourcesId) throws InternalErrorException{
        LdapResourcesLoader loader = new LdapResourcesLoader();
        LdapResources resources = loader.get(resourcesId);
        
        return getInstance(resources);
    }
    
    /**
     * Returns the instance to manipulate the LDAP service.
     *
     * @param resourcesDirname String that contains the directory where the resources
     * are stored.
     * @param resourcesId String that contains the identifier of the resources.
     * @return Instance that manipulates the LDAP service.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    public static Ldap getInstance(String resourcesDirname, String resourcesId) throws InternalErrorException{
        LdapResourcesLoader loader = new LdapResourcesLoader(resourcesDirname);
        LdapResources resources = loader.get(resourcesId);
        
        return getInstance(resources);
    }
    
    /**
     * Returns the instance to manipulate the LDAP service.
     *
     * @param resources Instance that contains the resource.
     * @return Instance that manipulates the LDAP service.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    public static Ldap getInstance(LdapResources resources) throws InternalErrorException{
        if(instances == null)
            instances = PropertyUtil.instantiate(Constants.DEFAULT_MAP_CLASS);

        Ldap instance = null;

        if(instances != null) {
            instance = instances.get(resources.getId());

            if(instance == null){
                instance = new Ldap(resources);

                instances.put(resources.getId(), instance);
            }
        }

        return instance;
    }
    
    /**
     * Constructor - Defines the LDAP resources.
     *
     * @param resources Instance that contains the resources.
     * @throws InternalErrorException Occurs when it was not possible to
     * instantiate the LDAP services based on the specified parameters.
     */
    private Ldap(LdapResources resources) throws InternalErrorException{
        super();
        
        this.resources = resources;
        
        initialize();
    }
    
    /**
     * Initialize the communication with the LDAP services.
     *
     * @throws InternalErrorException Occurs when was not possible to establish
     * the communication with the LDAP services.
     */
    private void initialize() throws InternalErrorException{
        Properties properties = new Properties();
        
        properties.put(Context.REFERRAL, "throw");
        
        String url = this.resources.getFactoryResources().getUri();
        
        url = PropertyUtil.fillPropertiesInString(this.resources, url);
        
        properties.put(Context.PROVIDER_URL, url);
        
        if(this.resources.getTimeout() > 0)
            properties.put(LdapConstants.TIMEOUT_ATTRIBUTE_ID, String.valueOf(this.resources.getTimeout() * 1000));
        
        if(this.resources.getUserName() != null && !this.resources.getUserName().isEmpty()){
            StringBuilder securityBuffer = new StringBuilder();
            
            securityBuffer.append("uid=");
            securityBuffer.append(this.resources.getUserName());
            
            if(this.resources.getUserDn() != null && !this.resources.getUserDn().isEmpty()){
                securityBuffer.append(",");
                securityBuffer.append(this.resources.getUserDn());
            }
            
            securityBuffer.append(",");
            securityBuffer.append(this.resources.getBaseDn());
            
            properties.put(Context.SECURITY_AUTHENTICATION, this.resources.getAuthenticationType());
            properties.put(Context.SECURITY_PRINCIPAL, securityBuffer.toString());
            properties.put(Context.SECURITY_CREDENTIALS, this.resources.getPassword());
        }
        
        try{
            this.context = new InitialDirContext(properties);
        }
        catch(NamingException e){
            throw new InternalErrorException(e);
        }
    }
    
    /**
     * Returns the instance of the LDAP service.
     *
     * @return Instance that contains the LDAP service.
     */
    public DirContext getContext(){
        return this.context;
    }
    
    /**
     * List all nodes based on a query string.
     *
     * @param dn String that contains the query.
     * @return List of all found nodes.
     * @throws InternalErrorException Occurs when was not possible to execute
     * the operation.
     */
    @SuppressWarnings("unchecked")
    public XmlNode list(String dn) throws InternalErrorException{
        try{
            NamingEnumeration<NameClassPair> list = getContext().list(dn);
            NameClassPair item;
            Attributes attributes;
            NamingEnumeration<Attribute> attributesList;
            Attribute attribute;
            XmlNode root;
            XmlNode child;
            XmlNode attributeChild;
            Object attributeValue = null;
            StringBuilder attributeValueBuffer = null;
            
            root = new XmlNode(Constants.ITEMS_ATTRIBUTE_ID);
            
            while(list.hasMore()){
                item = list.next();
                
                child = new XmlNode(Constants.ITEM_ATTRIBUTE_ID);
                child.addAttribute(Constants.NAME_ATTRIBUTE_ID, item.getName());
                
                attributes = getContext().getAttributes(item.getName().concat(",").concat(dn));
                
                if(attributes != null){
                    attributesList = (NamingEnumeration<Attribute>) attributes.getAll();
                    
                    while(attributesList.hasMore()){
                        attribute = attributesList.next();
                        
                        if(attributeValueBuffer == null)
                            attributeValueBuffer = new StringBuilder();
                        else
                            attributeValueBuffer.delete(0, attributeValueBuffer.length());
                        
                        for(NamingEnumeration<Attribute> e = (NamingEnumeration<Attribute>) attribute.getAll(); e.hasMore(); attributeValue = e.next()){
                            if(attributeValue != null){
                                if(!attributeValueBuffer.isEmpty())
                                    attributeValueBuffer.append(";");
                                
                                attributeValueBuffer.append(attributeValue);
                            }
                        }
                        
                        attributeChild = new XmlNode(attribute.getID(), attributeValueBuffer.toString());
                        
                        child.addChild(attributeChild);
                    }
                }
                
                root.addChild(child);
            }
            
            return root;
        }
        catch(NamingException e){
            throw new InternalErrorException(e);
        }
    }
}