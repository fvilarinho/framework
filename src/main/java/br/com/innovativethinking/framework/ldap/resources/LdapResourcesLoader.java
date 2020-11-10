package br.com.innovativethinking.framework.ldap.resources;

import java.text.ParseException;

import br.com.innovativethinking.framework.constants.Constants;
import br.com.innovativethinking.framework.ldap.constants.LdapConstants;
import br.com.innovativethinking.framework.network.constants.NetworkConstants;
import br.com.innovativethinking.framework.network.resources.NetworkResourcesLoader;
import br.com.innovativethinking.framework.resources.FactoryResources;
import br.com.innovativethinking.framework.resources.constants.FactoryConstants;
import br.com.innovativethinking.framework.resources.exceptions.InvalidResourcesException;
import br.com.innovativethinking.framework.security.constants.SecurityConstants;
import br.com.innovativethinking.framework.util.NumberUtil;
import br.com.innovativethinking.framework.util.helpers.XmlNode;

/**
 * Class responsible to manipulate the LDAP resources.
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
public class LdapResourcesLoader extends NetworkResourcesLoader<LdapResources>{
	/**
	 * Constructor - Manipulates the default resources.
	 * 
	 * @throws InvalidResourcesException Occurs when the default resources could
	 * not be read.
	 */
	public LdapResourcesLoader() throws InvalidResourcesException{
		super();
	}

	/**
	 * Constructor - Manipulates specific resources.
	 * 
	 * @param resourcesDirname String that contains the directory where the resources
	 * are stored.
	 * @throws InvalidResourcesException Occurs when the resources could not be read.
	 */
	public LdapResourcesLoader(String resourcesDirname) throws InvalidResourcesException{
		super(resourcesDirname);
	}

	/**
	 * @see br.com.innovativethinking.framework.resources.XmlResourcesLoader#parseResources(br.com.innovativethinking.framework.util.helpers.XmlNode)
	 */
	public LdapResources parseResources(XmlNode resourcesNode) throws InvalidResourcesException{
		String resourcesDirname = getResourcesDirname();
		String resourcesId = getResourcesId();
		LdapResources resources = super.parseResources(resourcesNode);
		XmlNode serverNameNode = resourcesNode.getNode(NetworkConstants.SERVER_NAME_ATTRIBUTE_ID);

		if(serverNameNode != null){
			String serverName = serverNameNode.getValue();

			if(serverName == null || serverName.length() == 0)
				throw new InvalidResourcesException(resourcesDirname, resourcesId, serverNameNode.getText());

			resources.setServerName(serverName);
		}
		else
			resources.setServerName(NetworkConstants.DEFAULT_LOCALHOST_NAME_ID);

		XmlNode serverPortNode = resourcesNode.getNode(NetworkConstants.SERVER_PORT_ATTRIBUTE_ID);

		if(serverPortNode != null){
			try{
				Integer serverPort = NumberUtil.parseInt(serverPortNode.getValue());

				resources.setServerPort(serverPort);
			}
			catch(ParseException e){
				throw new InvalidResourcesException(resourcesDirname, resourcesId, serverPortNode.getText(), e);
			}
		}
		else
			resources.setServerPort(LdapConstants.DEFAULT_PORT);

		XmlNode authenticationTypeNode = resourcesNode.getNode(SecurityConstants.AUTHENTICATION_TYPE_ATTRIBUTE_ID);

		if(authenticationTypeNode != null){
			String authenticationType = authenticationTypeNode.getValue();

			if(authenticationType == null || authenticationType.length() == 0)
				throw new InvalidResourcesException(resourcesDirname, resourcesId, authenticationTypeNode.getText());

			resources.setAuthenticationType(authenticationType);
		}
		else
			resources.setAuthenticationType(LdapConstants.DEFAULT_AUTHENTICATION_TYPE);

		XmlNode timeoutNode = resourcesNode.getNode(Constants.TIMEOUT_ATTRIBUTE_ID);

		if(timeoutNode != null){
			try{
				Long timeout = NumberUtil.parseLong(timeoutNode.getValue());

				resources.setTimeout(timeout);
			}
			catch(ParseException e){
				throw new InvalidResourcesException(resourcesDirname, resourcesId, timeoutNode.getText(), e);
			}
		}
		else
			resources.setTimeout(LdapConstants.DEFAULT_TIMEOUT);

		XmlNode userNameNode = resourcesNode.getNode(SecurityConstants.USER_NAME_ATTRIBUTE_ID);

		if(userNameNode != null){
			String userName = userNameNode.getValue();

			if(userName != null && userName.length() > 0)
				resources.setUserName(userName);
		}

		XmlNode passwordNode = resourcesNode.getNode(SecurityConstants.PASSWORD_ATTRIBUTE_ID);

		if(passwordNode != null){
			String password = passwordNode.getValue();

			if(password != null && password.length() > 0)
				resources.setPassword(password);
		}

		XmlNode baseDnNode = resourcesNode.getNode(LdapConstants.BASE_DN_ATTRIBUTE_ID);

		if(baseDnNode != null){
			String baseDn = baseDnNode.getValue();

			if(baseDn != null && baseDn.length() > 0)
				resources.setBaseDn(baseDn);
			else
				throw new InvalidResourcesException(resourcesDirname, resourcesId, baseDnNode.getText());
		}
		else
			throw new InvalidResourcesException(resourcesDirname, resourcesId, resourcesNode.getText());

		XmlNode userDnNode = resourcesNode.getNode(LdapConstants.USER_DN_ATTRIBUTE_ID);

		if(userDnNode != null){
			String userDn = userDnNode.getValue();

			if(userDn != null && userDn.length() > 0)
				resources.setUserDn(userDn);
		}

		String factoryResourcesId = resourcesNode.getAttribute(FactoryConstants.RESOURCES_ATTRIBUTE_ID);
		LdapFactoryResourcesLoader loader = new LdapFactoryResourcesLoader(resourcesDirname);
		FactoryResources factoryResources = loader.get(factoryResourcesId);

		resources.setFactoryResources(factoryResources);

		return resources;
	}

	/**
	 * @see br.com.innovativethinking.framework.resources.BaseResourcesLoader#parseContent()
	 */
	protected XmlNode parseContent() throws InvalidResourcesException{
		XmlNode contentNode = super.parseContent();
		XmlNode resourcesNode = contentNode.getNode(LdapConstants.DEFAULT_ID);

		if(resourcesNode == null)
			throw new InvalidResourcesException(getResourcesDirname(), getResourcesId(), contentNode.getText());

		return resourcesNode;
	}
}