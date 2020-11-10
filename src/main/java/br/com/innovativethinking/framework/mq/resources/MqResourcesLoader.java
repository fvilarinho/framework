package br.com.innovativethinking.framework.mq.resources;

import java.text.ParseException;
import java.util.List;

import br.com.innovativethinking.framework.constants.Constants;
import br.com.innovativethinking.framework.mq.constants.MqConstants;
import br.com.innovativethinking.framework.mq.resources.helpers.MqQueue;
import br.com.innovativethinking.framework.network.constants.NetworkConstants;
import br.com.innovativethinking.framework.network.resources.NetworkResourcesLoader;
import br.com.innovativethinking.framework.processors.ExpressionProcessorUtil;
import br.com.innovativethinking.framework.resources.exceptions.InvalidResourcesException;
import br.com.innovativethinking.framework.security.constants.SecurityConstants;
import br.com.innovativethinking.framework.util.NumberUtil;
import br.com.innovativethinking.framework.util.helpers.XmlNode;

/**
 * Class responsible to manipulate the MQ resources.
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
public class MqResourcesLoader extends NetworkResourcesLoader<MqResources>{
	/**
	 * Constructor - Manipulates the default resources.
	 * 
	 * @throws InvalidResourcesException Occurs when the default resources could
	 * not be read.
	 */
	public MqResourcesLoader() throws InvalidResourcesException{
		super();
	}

	/**
	 * Constructor - Manipulates specific resource.
	 * 
	 * @param resourcesDirname String that contains the directory where the resources
	 * are stored.
	 * @throws InvalidResourcesException Occurs when the resources could not be read.
	 */
	public MqResourcesLoader(String resourcesDirname) throws InvalidResourcesException{
		super(resourcesDirname);
	}

	/**
	 * @see br.com.innovativethinking.framework.resources.XmlResourcesLoader#parseResources(br.com.innovativethinking.framework.util.helpers.XmlNode)
	 */
	public MqResources parseResources(XmlNode resourcesNode) throws InvalidResourcesException{
		String resourcesDirname = getResourcesDirname();
		String resourcesId = getResourcesId();
		MqResources resources = super.parseResources(resourcesNode);
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
			if(serverPortNode.getValue() == null || serverPortNode.getValue().length() == 0)
				throw new InvalidResourcesException(resourcesDirname, resourcesId, serverPortNode.getText());

			try{
				Integer serverPort = NumberUtil.parseInt(serverPortNode.getValue());

				resources.setServerPort(serverPort);
			}
			catch(ParseException e){
				throw new InvalidResourcesException(resourcesDirname, resourcesId, serverPortNode.getText(), e);
			}
		}
		else
			resources.setServerPort(MqConstants.DEFAULT_PORT);

		XmlNode userNameNode = resourcesNode.getNode(SecurityConstants.USER_NAME_ATTRIBUTE_ID);

		if(userNameNode != null){
			String userName = userNameNode.getValue();

			if(userName != null && userName.length() > 0){
				userName = ExpressionProcessorUtil.fillEnvironmentInString(userName);

				resources.setUserName(userName);
			}
		}

		XmlNode passwordNode = resourcesNode.getNode(SecurityConstants.PASSWORD_ATTRIBUTE_ID);

		if(passwordNode != null){
			String password = passwordNode.getValue();

			if(password != null && password.length() > 0){
				password = ExpressionProcessorUtil.fillEnvironmentInString(password);

				resources.setPassword(password);
			}
		}

		XmlNode queuesNode = resourcesNode.getNode(MqConstants.QUEUES_ATTRIBUTE_ID);

		if(queuesNode != null){
			List<XmlNode> queuesNodeList = queuesNode.getChildren();
			String queueId = null;
			String queueListenerClass = null;

			if(queuesNodeList != null && !queuesNodeList.isEmpty()){
				for(XmlNode queueNode : queuesNodeList){
					queueId = queueNode.getAttribute(Constants.IDENTITY_ATTRIBUTE_ID);

					if(queueId == null || queueId.length() == 0)
						throw new InvalidResourcesException(resourcesDirname, resourcesId, queueNode.getText());

					MqQueue queue = new MqQueue();

					queue.setId(queueId);

					queueListenerClass = queueNode.getAttribute(MqConstants.LISTENER_CLASS_ATTRIBUTE_ID);

					if(queueListenerClass != null && queueListenerClass.length() > 0)
						queue.setListenerClass(queueListenerClass);

					resources.addQueue(queue);
				}
			}
		}

		return resources;
	}

	/**
	 * @see br.com.innovativethinking.framework.resources.BaseResourcesLoader#parseContent()
	 */
	protected XmlNode parseContent() throws InvalidResourcesException{
		XmlNode contentNode = super.parseContent();
		XmlNode resourcesNode = (contentNode != null ? contentNode.getNode(MqConstants.DEFAULT_ID) : null);

		if(resourcesNode == null)
			throw new InvalidResourcesException(getResourcesDirname(), getResourcesId(), contentNode.getText());

		return resourcesNode;
	}
}