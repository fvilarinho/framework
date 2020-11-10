package br.com.innovativethinking.framework.security.resources;

import java.text.ParseException;

import br.com.innovativethinking.framework.constants.Constants;
import br.com.innovativethinking.framework.resources.XmlResourcesLoader;
import br.com.innovativethinking.framework.resources.exceptions.InvalidResourcesException;
import br.com.innovativethinking.framework.security.constants.SecurityConstants;
import br.com.innovativethinking.framework.util.NumberUtil;
import br.com.innovativethinking.framework.util.helpers.XmlNode;

/**
 * Class responsible to manipulate the security resources.
 * 
 * @author fvilarinho
 * @since 3.0.0
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
public class SecurityResourcesLoader extends XmlResourcesLoader<SecurityResources>{
	/**
	 * Constructor - Manipulates the default resources.
	 * 
	 * @throws InvalidResourcesException Occurs when the default resources could
	 * not be read.
	 */
	public SecurityResourcesLoader() throws InvalidResourcesException{
		super(SecurityConstants.DEFAULT_RESOURCES_ID);
	}

	/**
	 * Constructor - Manipulates specific resources.
	 * 
	 * @param resourcesDirname String that contains the directory where the resources
	 * are stored.
	 * @throws InvalidResourcesException Occurs when the resources could not be read.
	 */
	public SecurityResourcesLoader(String resourcesDirname) throws InvalidResourcesException{
		super(resourcesDirname, SecurityConstants.DEFAULT_RESOURCES_ID);
	}

	/**
	 * @see br.com.innovativethinking.framework.resources.XmlResourcesLoader#parseResources(br.com.innovativethinking.framework.util.helpers.XmlNode)
	 */
	public SecurityResources parseResources(XmlNode resourcesNode) throws InvalidResourcesException{
		String resourcesDirname = getResourcesDirname();
		String resourcesId = getResourcesId();
		SecurityResources resources = new SecurityResources();

		resources.setId(Constants.DEFAULT_ATTRIBUTE_ID);
		resources.setDefault(true);

		XmlNode loginSessionNode = resourcesNode.getNode(SecurityConstants.LOGIN_SESSION_ATTRIBUTE_ID);

		if(loginSessionNode != null){
			XmlNode classNode = loginSessionNode.getNode(Constants.CLASS_ATTRIBUTE_ID);

			if(classNode != null){
				if(classNode.getValue() == null || classNode.getValue().length() == 0)
					throw new InvalidResourcesException(resourcesDirname, resourcesId, classNode.getText());

				try{
					resources.setLoginSessionClass(classNode.getValue());
				}
				catch(ClassNotFoundException | ClassCastException e){
					throw new InvalidResourcesException(resourcesDirname, resourcesId, classNode.getText(), e);
				}
			}

			XmlNode timeoutNode = loginSessionNode.getNode(Constants.TIMEOUT_ATTRIBUTE_ID);

			if(timeoutNode != null){
				if(timeoutNode.getValue() == null || timeoutNode.getValue().length() == 0)
					throw new InvalidResourcesException(resourcesDirname, resourcesId, timeoutNode.getText());

				try{
					resources.setLoginSessionTimeout(NumberUtil.parseInt(timeoutNode.getValue()));
				}
				catch(ParseException e){
					throw new InvalidResourcesException(resourcesDirname, resourcesId, timeoutNode.getText(), e);
				}
			}
			else
				resources.setLoginSessionTimeout(SecurityConstants.DEFAULT_LOGIN_SESSION_TIMEOUT);
		}
		else
			throw new InvalidResourcesException(resourcesDirname, resourcesId, resourcesNode.getText());

		XmlNode cryptographyNode = resourcesNode.getNode(SecurityConstants.CRYPTOGRAPHY_ATTRIBUTE_ID);

		if(cryptographyNode != null){
			String cryptographyAlgorithm = cryptographyNode.getAttribute(SecurityConstants.CRYPTOGRAPHY_ALGORITHM_ATTRIBUTE_ID);

			if(cryptographyAlgorithm != null && cryptographyAlgorithm.length() > 0)
				resources.setCriptographyAlgorithm(cryptographyAlgorithm);
			else
				resources.setCriptographyAlgorithm(SecurityConstants.DEFAULT_CRYPTO_ALGORITHM_ID);

			String cryptographKeySizeBuffer = cryptographyNode.getAttribute(SecurityConstants.CRYPTOGRAPHY_KEY_SIZE_ATTRIBUTE_ID);

			if(cryptographKeySizeBuffer == null || cryptographKeySizeBuffer.length() == 0)
				throw new InvalidResourcesException(resourcesDirname, resourcesId, cryptographyNode.getText());

			try{
				Integer cryptographyKeySize = NumberUtil.parseInt(cryptographKeySizeBuffer);

				resources.setCriptographyKeySize(cryptographyKeySize);
			}
			catch(ParseException e){
				throw new InvalidResourcesException(resourcesDirname, resourcesId, cryptographyNode.getText(), e);
			}
		}
		else
			throw new InvalidResourcesException(resourcesDirname, resourcesId, resourcesNode.getText());

		return resources;
	}
}