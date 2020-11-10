package br.com.innovativethinking.framework.mq.resources;

import java.util.Collection;

import br.com.innovativethinking.framework.constants.Constants;
import br.com.innovativethinking.framework.mq.resources.helpers.MqQueue;
import br.com.innovativethinking.framework.resources.BaseResources;
import br.com.innovativethinking.framework.util.PropertyUtil;
import br.com.innovativethinking.framework.util.helpers.XmlNode;

/**
 * Class responsible to storage the MQ resources.
 * 
 * @author fvilarinho
 * @since 3.5.0
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
public class MqResources extends BaseResources<XmlNode>{
	private static final long serialVersionUID = 3052749946121399692L;

	private String              serverName = null;
	private Integer             serverPort = null;
	private String              userName   = null;
	private String              password   = null;
	private Collection<MqQueue> queues     = null;

	/**
	 * Returns the user name to connect into the service.
	 * 
	 * @return String that contains the user name.
	 */
	public String getUserName(){
		return this.userName;
	}

	/**
	 * Defines the user name to connect into the service.
	 * 
	 * @param userName String that contains the user name.
	 */
	public void setUserName(String userName){
		this.userName = userName;
	}

	/**
	 * Returns the password of the user name to connect into the service.
	 * 
	 * @return String that contains the password.
	 */
	public String getPassword(){
		return this.password;
	}

	/**
	 * Defines the password of the user name to connect into the service.
	 * 
	 * @param password String that contains the password.
	 */
	public void setPassword(String password){
		this.password = password;
	}

	/**
	 * Returns the list of queues.
	 *
	 * @return List that contains the queues.
	 */
	public Collection<MqQueue> getQueues(){
		return this.queues;
	}

	/**
	 * Defines the list of queues.
	 *
	 * @param queues List that contains the queues.
	 */
	public void setQueues(Collection<MqQueue> queues){
		this.queues = queues;
	}

	/**
	 * Adds a queue.
	 *
	 * @param queue Instance that contains the queue.
	 */
	public void addQueue(MqQueue queue){
		if(queue != null){
			if(this.queues == null)
				this.queues = PropertyUtil.instantiate(Constants.DEFAULT_LIST_CLASS);

			this.queues.add(queue);
		}
	}

	/**
	 * Returns the listen port of the MQ service.
	 * 
	 * @return Numeric value that contains the port.
	 */
	public Integer getServerPort(){
		return this.serverPort;
	}

	/**
	 * Defines the listen port of the MQ service.
	 * 
	 * @param serverPort Numeric value that contains the port.
	 */
	public void setServerPort(Integer serverPort){
		this.serverPort = serverPort;
	}

	/**
	 * Returns the hostname/IP of the MQ service.
	 * 
	 * @return String that contains the hostname/IP.
	 */
	public String getServerName(){
		return this.serverName;
	}

	/**
	 * Defines the hostname/IP of the MQ service.
	 * 
	 * @param serverName String that contains the hostname/IP.
	 */
	public void setServerName(String serverName){
		this.serverName = serverName;
	}
}