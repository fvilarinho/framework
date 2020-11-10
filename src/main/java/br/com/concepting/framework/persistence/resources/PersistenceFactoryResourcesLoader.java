package br.com.concepting.framework.persistence.resources;

import br.com.concepting.framework.persistence.constants.PersistenceConstants;
import br.com.concepting.framework.resources.FactoryResourcesLoader;
import br.com.concepting.framework.resources.exceptions.InvalidResourcesException;
import br.com.concepting.framework.util.helpers.XmlNode;

/**
 * Class responsible to manipulate the factory resources of the persistence.
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
public class PersistenceFactoryResourcesLoader extends FactoryResourcesLoader{
	/**
	 * Constructor - Manipulates the default resources.
	 * 
	 * @throws InvalidResourcesException Occurs when the default resources could
	 * not be read.
	 */
	public PersistenceFactoryResourcesLoader() throws InvalidResourcesException{
		super();
	}

	/**
	 * Constructor - Manipulates specific resources.
	 * 
	 * @param resourcesDirname String that contains the directory where the resources
	 * are stored.
	 * @throws InvalidResourcesException Occurs when the resources could not be read.
	 */
	public PersistenceFactoryResourcesLoader(String resourcesDirname) throws InvalidResourcesException{
		super(resourcesDirname);
	}

	/**
	 * @see br.com.concepting.framework.resources.XmlResourcesLoader#parseContent()
	 */
	protected XmlNode parseContent() throws InvalidResourcesException{
		XmlNode contentNode = super.parseContent();
		XmlNode resourcesNode = (contentNode != null ? contentNode.getNode(PersistenceConstants.DEFAULT_ID) : null);

		if(resourcesNode == null)
			throw new InvalidResourcesException(getResourcesDirname(), getResourcesId(), contentNode.getText());

		return resourcesNode;
	}
}