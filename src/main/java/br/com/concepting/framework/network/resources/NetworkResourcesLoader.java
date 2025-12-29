package br.com.concepting.framework.network.resources;

import br.com.concepting.framework.network.constants.NetworkConstants;
import br.com.concepting.framework.resources.BaseResources;
import br.com.concepting.framework.resources.XmlResourcesLoader;
import br.com.concepting.framework.resources.exceptions.InvalidResourcesException;
import br.com.concepting.framework.util.helpers.XmlNode;

/**
 * Class responsible to manipulate the network services resources.
 *
 * @param <R> Class that defines the resources.
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
 * @author fvilarinho
 * @since 1.0.0
 */
public abstract class NetworkResourcesLoader<R extends BaseResources<XmlNode>> extends XmlResourcesLoader<R>{
    /**
     * Constructor - Manipulates the default resources.
     *
     * @throws InvalidResourcesException Occurs when the default resources could
     * not be read.
     */
    protected NetworkResourcesLoader() throws InvalidResourcesException{
        super(NetworkConstants.DEFAULT_RESOURCES_ID);
    }
    
    /**
     * Constructor - Manipulates specific resources.
     *
     * @param resourcesDirname String that contains the directory where the resources
     * are stored.
     * @throws InvalidResourcesException Occurs when the resources could not be read.
     */
    protected NetworkResourcesLoader(String resourcesDirname) throws InvalidResourcesException{
        super(resourcesDirname, NetworkConstants.DEFAULT_RESOURCES_ID);
    }
}