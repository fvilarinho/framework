package br.com.innovativethinking.framework.mail.helpers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Map;

import javax.activation.MimetypesFileTypeMap;
import javax.mail.Address;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import br.com.innovativethinking.framework.constants.Constants;
import br.com.innovativethinking.framework.exceptions.InternalErrorException;
import br.com.innovativethinking.framework.network.helpers.Message;
import br.com.innovativethinking.framework.util.ByteUtil;
import br.com.innovativethinking.framework.util.PropertyUtil;

/**
 * Class responsible to store the mail message.
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
public class MailMessage extends Message<Object>{
	private static final long serialVersionUID = 6336269811220022270L;

	private Collection<Address>             to          = null;
	private Collection<Address>             cc          = null;
	private Collection<Address>             bcc         = null;
	private Address                         from        = null;
	private String                          subject     = null;
	private Collection<Map<String, Object>> attachments = null;

	/**
	 * Attaches a file in the message.
	 * 
	 * @param filename String that contains the filename.
	 * @throws InternalErrorException Occurs when was not possible to attach the
	 * file.
	 */
	public void attach(String filename) throws InternalErrorException{
		if(filename == null || filename.length() == 0)
			return;

		try{
			attach(filename, new FileInputStream(filename));
		}
		catch(IOException e){
			throw new InternalErrorException(e);
		}
	}

	/**
	 * Attaches a file in the message.
	 * 
	 * @param filename String that contains the filename.
	 * @param stream Instance that contains the file.
	 * @throws InternalErrorException Occurs when was not possible to attach the
	 * file.
	 */
	public void attach(String filename, InputStream stream) throws InternalErrorException{
		if(filename == null || filename.length() == 0 || stream == null)
			return;

		try{
			MimetypesFileTypeMap mimeTypes = new MimetypesFileTypeMap();
			String mimeType = mimeTypes.getContentType(new File(filename));

			if(mimeType.indexOf("plain") < 0)
				attach(filename, ByteUtil.fromBinaryStream(stream));
			else
				attach(filename, ByteUtil.fromTextStream(stream));
		}
		catch(IOException e){
			throw new InternalErrorException(e);
		}
	}

	/**
	 * Attaches a file content in the message.
	 * 
	 * @param filename String that contains the filename.
	 * @param content Instance that contains the data of the file.
	 */
	public void attach(String filename, byte content[]){
		if(content == null)
			return;

		Map<String, Object> attach = PropertyUtil.instantiate(Constants.DEFAULT_MAP_CLASS);

		if(filename != null){
			File temp = new File(filename);

			attach.put(Constants.CONTENT_FILENAME_ATTRIBUTE_ID, temp.getName());
		}

		attach.put(Constants.CONTENT_ATTRIBUTE_ID, content);

		if(this.attachments == null)
			this.attachments = PropertyUtil.instantiate(Constants.DEFAULT_LIST_CLASS);

		this.attachments.add(attach);
	}

	/**
	 * Attaches a file content in the message.
	 * 
	 * @param filename String that contains the filename.
	 * @param content String that contains the data of the file.
	 */
	public void attach(String filename, String content){
		if(content == null || content.length() == 0)
			return;

		Map<String, Object> attach = PropertyUtil.instantiate(Constants.DEFAULT_MAP_CLASS);

		if(filename != null){
			File temp = new File(filename);

			attach.put(Constants.CONTENT_FILENAME_ATTRIBUTE_ID, temp.getName());
		}

		attach.put(Constants.CONTENT_ATTRIBUTE_ID, content);

		if(this.attachments == null)
			this.attachments = PropertyUtil.instantiate(Constants.DEFAULT_LIST_CLASS);

		this.attachments.add(attach);
	}

	/**
	 * Returns the list of attachments of the message.
	 * 
	 * @return List that contains the attachments of the message.
	 */
	public Collection<Map<String, Object>> getAttachments(){
		return this.attachments;
	}

	/**
	 * Returns the title of the message.
	 * 
	 * @return String that contains the title.
	 */
	public String getSubject(){
		return this.subject;
	}

	/**
	 * Defines the title of the message.
	 * 
	 * @param subject String that contains the title.
	 */
	public void setSubject(String subject){
		this.subject = subject;
	}

	/**
	 * Returns the instance that contains the sender of the message.
	 * 
	 * @param <F> Class that defines the sender of the message.
	 * @return Instance that contains the sender.
	 */
	@SuppressWarnings("unchecked")
	public <F extends Address> F getFrom(){
		return (F)this.from;
	}

	/**
	 * Defines the instance that contains the sender of the message.
	 * 
	 * @param from Instance that contains the sender.
	 */
	public void setFrom(Address from){
		this.from = from;
	}

	/**
	 * Defines the instance that contains the sender of the message.
	 * 
	 * @param from String that contains the sender.
	 */
	public void setFrom(String from){
		if(from != null && from.length() > 0){
			try{
				this.setFrom(new InternetAddress(from));
			}
			catch(AddressException e){
			}
		}
	}

	/**
	 * Returns the list of recipients of the message.
	 * 
	 * @return List of recipients of the message.
	 */
	public Collection<? extends Address> getToRecipients(){
		return this.to;
	}

	/**
	 * Defines the list of recipients of the message.
	 * 
	 * @param to List of recipients of the message.
	 */
	public void setToRecipients(Collection<Address> to){
		this.to = to;
	}

	/**
	 * Returns the carbon copy list of recipients of the message.
	 * 
	 * @return to The carbon copy list of recipients of the message.
	 */
	public Collection<? extends Address> getCcRecipients(){
		return this.cc;
	}

	/**
	 * Defines the carbon copy list of recipients of the message.
	 * 
	 * @param cc The carbon copy list of recipients of the message.
	 */
	public void setCcRecipients(Collection<Address> cc){
		this.cc = cc;
	}

	/**
	 * Returns the blinded carbon copy list of recipients of the message.
	 * 
	 * @return The blinded carbon copy list of recipients of the message.
	 */
	public Collection<? extends Address> getBccRecipients(){
		return this.bcc;
	}

	/**
	 * Defines the blinded carbon copy list of recipients of the message.
	 * 
	 * @param bcc The blinded carbon copy list of recipients of the message.
	 */
	public void setBccRecipients(Collection<Address> bcc){
		this.bcc = bcc;
	}

	/**
	 * Adds a new recipient.
	 * 
	 * @param <R> Class that defines the recipient of the message.
	 * @param recipient Instance that contains the recipient.
	 */
	public <R extends Address> void addToRecipient(R recipient){
		if(recipient != null){
			if(this.to == null)
				this.to = PropertyUtil.instantiate(Constants.DEFAULT_LIST_CLASS);

			this.to.add(recipient);
		}
	}

	/**
	 * Adds a new carbon copy recipient.
	 * 
	 * @param <R> Class that defines the recipient of the message.
	 * @param recipient Instance that contains the recipient.
	 */
	public <R extends Address> void addCcRecipient(R recipient){
		if(recipient != null){
			if(this.cc == null)
				this.cc = PropertyUtil.instantiate(Constants.DEFAULT_LIST_CLASS);

			this.cc.add(recipient);
		}
	}

	/**
	 * Adds a new blinded carbon copy recipient.
	 * 
	 * @param <R> Class that defines the recipient of the message.
	 * @param recipient Instance that contains the recipient.
	 */
	public <R extends Address> void addBccRecipient(R recipient){
		if(recipient != null){
			if(this.bcc == null)
				this.bcc = PropertyUtil.instantiate(Constants.DEFAULT_LIST_CLASS);

			this.bcc.add(recipient);
		}
	}

	/**
	 * Adds a new blinded carbon copy recipient.
	 * 
	 * @param recipient String that contains the recipient.
	 */
	public void addBccRecipient(String recipient){
		if(recipient != null && recipient.length() > 0){
			try{
				this.addBccRecipient(new InternetAddress(recipient));
			}
			catch(AddressException e){
			}
		}
	}

	/**
	 * Adds a new carbon copy recipient.
	 * 
	 * @param recipient String that contains the recipient.
	 */
	public void addCcRecipient(String recipient){
		if(recipient != null && recipient.length() > 0){
			try{
				this.addCcRecipient(new InternetAddress(recipient));
			}
			catch(AddressException e){
			}
		}
	}

	/**
	 * Adds a recipient.
	 * 
	 * @param recipient String that contains the recipient.
	 */
	public void addToRecipient(String recipient){
		if(recipient != null && recipient.length() > 0){
			try{
				this.addToRecipient(new InternetAddress(recipient));
			}
			catch(AddressException e){
			}
		}
	}
}