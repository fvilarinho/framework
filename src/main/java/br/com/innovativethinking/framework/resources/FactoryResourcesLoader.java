package br.com.innovativethinking.framework.resources;

import java.util.List;

import br.com.innovativethinking.framework.constants.Constants;
import br.com.innovativethinking.framework.resources.constants.FactoryConstants;
import br.com.innovativethinking.framework.resources.constants.ResourcesConstants;
import br.com.innovativethinking.framework.resources.exceptions.InvalidResourcesException;
import br.com.innovativethinking.framework.util.helpers.XmlNode;

/**
 * Class that defines the basic implementation to manipulate factory resources.
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
public abstract class FactoryResourcesLoader extends XmlResourcesLoader<FactoryResources>{
	/**
	 * Constructor - Manipulates the default resource.
	 * 
	 * @throws InvalidResourcesException Occurs when the resources could not be read.
	 */
	public FactoryResourcesLoader() throws InvalidResourcesException{
		super(FactoryConstants.DEFAULT_RESOURCES_ID);
	}

	/**
	 * Constructor - Manipulates a specific resource.
	 * 
	 * @param resourcesDirname String that contains the directory where the resource
	 * is stored.
	 * @throws InvalidResourcesException Occurs when the resources could not be read.
	 */
	public FactoryResourcesLoader(String resourcesDirname) throws InvalidResourcesException{
		super(resourcesDirname, FactoryConstants.DEFAULT_RESOURCES_ID);
	}

	/**
	 * @see br.com.innovativethinking.framework.resources.XmlResourcesLoader#getResourcesClass()
	 */
	protected Class<FactoryResources> getResourcesClass() throws ClassNotFoundException{
		return FactoryResources.class;
	}

	/**
	 * @see br.com.innovativethinking.framework.resources.XmlResourcesLoader#parseResources(br.com.innovativethinking.framework.util.helpers.XmlNode)
	 */
	public FactoryResources parseResources(XmlNode resourcesNode) throws InvalidResourcesException{
		String resourcesDirname = getResourcesDirname();
		String resourcesId = getResourcesId();
		FactoryResources resources = super.parseResources(resourcesNode);
		String type = resourcesNode.getAttribute(Constants.TYPE_ATTRIBUTE_ID);

		if(type == null || type.length() == 0)
			throw new InvalidResourcesException(resourcesDirname, resourcesId, resourcesNode.getText());

		resources.setType(type);

		XmlNode classNode = resourcesNode.getNode(Constants.CLASS_ATTRIBUTE_ID);

		if(classNode != null){
			String clazz = classNode.getValue();

			if(clazz == null || clazz.length() == 0)
				throw new InvalidResourcesException(resourcesDirname, resourcesId, classNode.getText());

			resources.setClazz(clazz);
		}
		else
			throw new InvalidResourcesException(resourcesDirname, resourcesId, resourcesNode.getText());

		XmlNode uriNode = resourcesNode.getNode(FactoryConstants.URI_ATTRIBUTE_ID);

		if(uriNode != null){
			String uri = uriNode.getValue();

			if(uri == null || uri.length() == 0)
				throw new InvalidResourcesException(resourcesDirname, resourcesId, uriNode.getText());

			resources.setUri(uri);
		}
		else
			throw new InvalidResourcesException(resourcesDirname, resourcesId, resourcesNode.getText());

		XmlNode optionsNode = resourcesNode.getNode(ResourcesConstants.OPTIONS_ATTRIBUTE_ID);

		if(optionsNode != null){
			List<XmlNode> childNodes = optionsNode.getChildren();
			String optionId = null;
			String optionValue = null;

			for(XmlNode childNode : childNodes){
				optionId = childNode.getAttribute(Constants.IDENTITY_ATTRIBUTE_ID);

				if(optionId == null || optionId.length() == 0)
					throw new InvalidResourcesException(resourcesDirname, resourcesId, childNode.getText());

				optionValue = childNode.getAttribute(Constants.VALUE_ATTRIBUTE_ID);

				if(optionValue == null)
					throw new InvalidResourcesException(resourcesDirname, resourcesId, childNode.getText());

				resources.addOption(optionId, optionValue);
			}
		}

		return resources;
	}
}