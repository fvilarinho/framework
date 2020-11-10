package br.com.concepting.framework.network.constants;

import br.com.concepting.framework.resources.constants.ResourcesConstants;

/**
 * Class that defines the constants used in the manipulation of the network
 * services.
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
public abstract class NetworkConstants{
	public static final String IP_ATTRIBUTE_ID              = "ip";
	public static final String NETWORK_MASK_ATTRIBUTE_ID    = "networkMask";
	public static final String HOSTS_ATTRIBUTE_ID           = "hosts";
	public static final String HOSTNAME_ATTRIBUTE_ID        = "hostName";
	public static final String CANONICAL_NAME_ATTRIBUTE_ID  = "canonicalName";
	public static final String LOOKUP_PORT_ATTRIBUTE_ID     = "lookupPort";
	public static final String SERVER_NAME_ATTRIBUTE_ID     = "serverName";
	public static final String SERVER_PORT_ATTRIBUTE_ID     = "serverPort";
	public static final String USE_SSL_ATTRIBUTE_ID         = "useSsl";
	public static final String USE_TLS_ATTRIBUTE_ID         = "useTls";
	public static final String DEFAULT_HTTP_PROTOCOL_ID     = "http";
	public static final String DEFAULT_HTTPS_PROTOCOL_ID    = "https";
	public static final String DEFAULT_LOCALHOST_ADDRESS_ID = "127.0.0.1";
	public static final String DEFAULT_LOCALHOST_NAME_ID    = "localhost";
	public static final String DEFAULT_RESOURCES_ID         = ResourcesConstants.DEFAULT_RESOURCES_DIR.concat("networkResources.xml");
}