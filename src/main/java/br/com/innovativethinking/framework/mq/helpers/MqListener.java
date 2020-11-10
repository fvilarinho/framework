package br.com.innovativethinking.framework.mq.helpers;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import br.com.innovativethinking.framework.constants.Constants;
import br.com.innovativethinking.framework.util.helpers.DateTime;

/**
 * Class responsible to listen queues of the MQ service.
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
public class MqListener implements MessageListener{
	/**
	 * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
	 */
	public void onMessage(Message m){
		try{
			MqMessage message = new MqMessage();

			message.setId(m.getJMSMessageID());
			message.setReceivedDateTime(new DateTime());
			message.setSentDateTime(new DateTime(m.getJMSTimestamp()));
			message.setContent(m.getObjectProperty(Constants.CONTENT_ATTRIBUTE_ID));

			onReceive(message);
		}
		catch(JMSException e){
		}
	}

	/**
	 * Called when a message was received.
	 *
	 * @param message Instance that contains the message.
	 */
	protected void onReceive(MqMessage message){
	}
}