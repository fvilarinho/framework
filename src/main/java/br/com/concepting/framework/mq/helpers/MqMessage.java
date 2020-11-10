package br.com.concepting.framework.mq.helpers;

import br.com.concepting.framework.model.annotations.Property;
import br.com.concepting.framework.network.helpers.Message;
import br.com.concepting.framework.util.types.StatusType;

/**
 * Class that defines a MQ message.
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
public class MqMessage extends Message<Object>{
	private static final long serialVersionUID = 7795087518762967454L;

	private String id = null;

	@Property
	private StatusType status = null;

	@Property
	private String statusMessage = null;

	/**
	 * Returns the status of the message.
	 * 
	 * @return Instance that contains the status.
	 */
	public StatusType getStatus(){
		return this.status;
	}

	/**
	 * Defines the status of the message.
	 * 
	 * @param status Instance that contains the status.
	 */
	public void setStatus(StatusType status){
		this.status = status;
	}

	/**
	 * Returns the status message.
	 * 
	 * @return Instance that contains the status message.
	 */
	public String getStatusMessage(){
		return this.statusMessage;
	}

	/**
	 * Defines the status message.
	 * 
	 * @param statusMessage Instance that contains the status message.
	 */
	public void setStatusMessage(String statusMessage){
		this.statusMessage = statusMessage;
	}

	/**
	 * Returns the identifier of the message.
	 *
	 * @return String that contains the identifier.
	 */
	public String getId(){
		return this.id;
	}

	/**
	 * Defines the identifier of the message.
	 *
	 * @param id String that contains the identifier.
	 */
	public void setId(String id){
		this.id = id;
	}
}