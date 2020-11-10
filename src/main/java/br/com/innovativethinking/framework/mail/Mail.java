package br.com.innovativethinking.framework.mail;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.net.ssl.SSLSocketFactory;

import br.com.innovativethinking.framework.constants.Constants;
import br.com.innovativethinking.framework.exceptions.InternalErrorException;
import br.com.innovativethinking.framework.mail.helpers.MailMessage;
import br.com.innovativethinking.framework.mail.resources.MailResources;
import br.com.innovativethinking.framework.mail.resources.MailResourcesLoader;
import br.com.innovativethinking.framework.mail.types.MailStorageType;
import br.com.innovativethinking.framework.mail.types.MailTransportType;
import br.com.innovativethinking.framework.util.ByteUtil;
import br.com.innovativethinking.framework.util.FileUtil;
import br.com.innovativethinking.framework.util.PropertyUtil;
import br.com.innovativethinking.framework.util.helpers.DateTime;

/**
 * Class responsible to manipulate the mail services.
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
public class Mail{
	private static Map<String, Mail> instances = null;

	private Properties       properties  = null;
	private MailResources    resources   = null;
	private Collection<File> attachments = null;

	/**
	 * Returns the instance to manipulate the mail service.
	 *
	 * @return Instance that manipulates the mail service.
	 * @throws InternalErrorException Occurs when was not possible to execute the operation.
	 */
	public static Mail getInstance() throws InternalErrorException{
		MailResourcesLoader loader = new MailResourcesLoader();
		MailResources resources = loader.getDefault();

		return getInstance(resources);
	}

	/**
	 * Returns the instance to manipulate the mail service.
	 *
	 * @param resourcesId String that contains the identifier of the resources.
	 * @return Instance that manipulates the mail service.
	 * @throws InternalErrorException Occurs when was not possible to execute the operation.
	 */
	public static Mail getInstance(String resourcesId) throws InternalErrorException{
		MailResourcesLoader loader = new MailResourcesLoader();
		MailResources resources = loader.get(resourcesId);

		return getInstance(resources);
	}

	/**
	 * Returns the instance to manipulate the mail service.
	 *
	 * @param resourcesDirname String that contains the directory where the resources
	 * are stored.
	 * @param resourcesId String that contains the identifier of the resources.
	 * @return Instance that manipulates the mail service.
	 * @throws InternalErrorException Occurs when was not possible to execute the operation.
	 */
	public static Mail getInstance(String resourcesDirname, String resourcesId) throws InternalErrorException{
		MailResourcesLoader loader = new MailResourcesLoader(resourcesDirname);
		MailResources resources = loader.get(resourcesId);

		return getInstance(resources);
	}

	/**
	 * Returns the instance to manipulate the mail service.
	 *
	 * @param resources Instance that contains the resources.
	 * @return Instance that manipulates the mail service.
	 * @throws InternalErrorException Occurs when was not possible to execute the operation.
	 */
	public static Mail getInstance(MailResources resources) throws InternalErrorException{
		if(instances == null)
			instances = PropertyUtil.instantiate(Constants.DEFAULT_MAP_CLASS);

		Mail instance = instances.get(resources.getId());

		if(instance == null){
			instance = new Mail(resources);

			instances.put(resources.getId(), instance);
		}

		return instance;
	}

	/**
	 * Constructor - Defines the mail resources.
	 * 
	 * @param resources Instance that contains the resources.
	 * @throws InternalErrorException Occurs when it was not possible to
	 * instantiate the mail services based on the specified parameters.
	 */
	private Mail(MailResources resources) throws InternalErrorException{
		super();

		this.resources = resources;

		initialize();
	}

	/**
	 * Initialize the communication with the mail services.
	 * 
	 * @throws InternalErrorException Occurs when was not possible to establish
	 * the communication with the mail services.
	 */
	private void initialize() throws InternalErrorException{
		this.properties = new Properties();

		if(this.resources.getTransport() == MailTransportType.SMTP){
			this.properties.setProperty("mail.transport.protocol", MailTransportType.SMTP.toString().toLowerCase());
			this.properties.setProperty("mail.smtp.host", this.resources.getTransportServerName());
			this.properties.setProperty("mail.smtp.localhost", this.resources.getTransportServerName());
			this.properties.setProperty("mail.smtp.port", this.resources.getTransportServerPort().toString());

			if(this.resources.getTransportUserName() != null && this.resources.getTransportUserName().length() > 0)
				this.properties.setProperty("mail.smtp.auth", Boolean.TRUE.toString());

			if(this.resources.getTransportUseTls() != null && this.resources.getTransportUseTls())
				this.properties.put("mail.smtp.starttls.enable", Boolean.TRUE.toString());

			if(this.resources.getTransportUseSsl() != null && this.resources.getTransportUseSsl()){
				this.properties.put("mail.smtp.socketFactory.class", SSLSocketFactory.class.getName());
				this.properties.put("mail.smtp.socketFactory.port", this.resources.getTransportServerPort().toString());
			}
		}

		if(this.resources.getStorage() == MailStorageType.POP3){
			this.properties.setProperty("mail.store.protocol", MailStorageType.POP3.toString().toLowerCase());
			this.properties.setProperty("mail.pop3.host", this.resources.getStorageServerName());
			this.properties.setProperty("mail.pop3.port", this.resources.getStorageServerPort().toString());

			if(this.resources.getStorageUseTls() != null && this.resources.getStorageUseTls())
				this.properties.put("mail.pop3.starttls.enable", Boolean.TRUE.toString());

			if(this.resources.getStorageUseSsl() != null && this.resources.getStorageUseSsl()){
				this.properties.put("mail.pop3.socketFactory.class", SSLSocketFactory.class.getName());
				this.properties.put("mail.pop3.socketFactory.port", String.valueOf(this.resources.getStorageServerPort()));
			}
		}
		else if(this.resources.getStorage() == MailStorageType.IMAP){
			this.properties.setProperty("mail.store.protocol", MailStorageType.IMAP.toString().toLowerCase());
			this.properties.setProperty("mail.imap.host", this.resources.getStorageServerName());
			this.properties.setProperty("mail.imap.port", this.resources.getStorageServerPort().toString());

			if(this.resources.getStorageUseTls() != null && this.resources.getStorageUseTls())
				this.properties.put("mail.imap.starttls.enable", Boolean.TRUE.toString());

			if(this.resources.getStorageUseSsl() != null && this.resources.getStorageUseSsl()){
				this.properties.put("mail.imap.socketFactory.class", SSLSocketFactory.class.getName());
				this.properties.put("mail.imap.socketFactory.port", this.resources.getStorageServerPort().toString());
			}
		}
	}

	/**
	 * Builds the message headers.
	 * 
	 * @param message Instance that contains the message.
	 * @param sendMessage Instance that contains the message.
	 * @throws InternalErrorException Occurs when was not possible possible to
	 * build the message headers.
	 */
	private void buildHeader(MailMessage message, Message sendMessage) throws InternalErrorException{
		if(message == null || sendMessage == null)
			return;

		try{
			sendMessage.setFrom(message.getFrom());

			if(message.getToRecipients() != null && message.getToRecipients().size() > 0)
				for(Address item : message.getToRecipients())
					sendMessage.addRecipient(Message.RecipientType.TO, item);

			if(message.getCcRecipients() != null && message.getCcRecipients().size() > 0)
				for(Address item : message.getCcRecipients())
					sendMessage.addRecipient(Message.RecipientType.CC, item);

			if(message.getBccRecipients() != null && message.getBccRecipients().size() > 0)
				for(Address item : message.getBccRecipients())
					sendMessage.addRecipient(Message.RecipientType.BCC, item);

			sendMessage.setSubject(message.getSubject());
			sendMessage.setSentDate(message.getSentDateTime());
		}
		catch(MessagingException e){
			throw new InternalErrorException(e);
		}
	}

	/**
	 * Builds the message body.
	 * 
	 * @param message Instance that contains the message.
	 * @param sendMessage Instance that contains the message.
	 * @throws InternalErrorException Occurs when was not possible possible to
	 * build the message body.
	 */
	private void buildBody(MailMessage message, Message sendMessage) throws InternalErrorException{
		if(message == null || sendMessage == null)
			return;

		MimeMultipart parts = new MimeMultipart();
		MimeBodyPart part = new MimeBodyPart();

		try{
			part.setContent(message.getContent(), message.getContentType().getMimeType());

			parts.addBodyPart(part);
		}
		catch(MessagingException e){
			throw new InternalErrorException(e);
		}

		loadAttachments(message, sendMessage, parts);
	}

	/**
	 * Loads the message body.
	 * 
	 * @param message Instance that contains the message.
	 * @param sendMessage Instance that contains the message.
	 * @param parts Instance that contains the attachments.
	 * @throws InternalErrorException Occurs when was not possible possible to
	 * load the attachments.
	 */
	private void loadAttachments(MailMessage message, Message sendMessage, MimeMultipart parts) throws InternalErrorException{
		if(message == null || sendMessage == null || parts == null)
			return;

		if(message.getAttachments() != null && !message.getAttachments().isEmpty()){
			this.attachments = PropertyUtil.instantiate(Constants.DEFAULT_LIST_CLASS);

			MimeBodyPart attachPart = null;
			byte attachData[] = null;
			String filename = null;
			Object fileContent = null;
			File file = null;
			FileDataSource handler = null;
			int cont = 0;

			for(Map<String, Object> attach : message.getAttachments()){
				attachPart = new MimeBodyPart();
				filename = (String)attach.get(Constants.CONTENT_FILENAME_ATTRIBUTE_ID);
				fileContent = attach.get(Constants.CONTENT_ATTRIBUTE_ID);

				if(PropertyUtil.isByteArray(fileContent))
					attachData = (byte[])fileContent;
				else
					attachData = fileContent.toString().getBytes();

				if(attachData != null && attachData.length > 0){
					if(filename == null || filename.length() == 0){
						filename = "attachment-".concat(String.valueOf(cont));

						cont++;
					}

					try{
						FileUtil.toBinaryFile(filename, attachData);

						file = new File(filename);

						if(file.exists()){
							handler = new FileDataSource(file);

							attachPart.setDataHandler(new DataHandler(handler));
							attachPart.setFileName(handler.getName());

							parts.addBodyPart(attachPart);

							this.attachments.add(file);
						}
					}
					catch(MessagingException | IOException e){
						throw new InternalErrorException(e);
					}
				}
			}
		}

		try{
			sendMessage.setContent(parts);
		}
		catch(MessagingException e){
			throw new InternalErrorException(e);
		}
	}

	/**
	 * Clears all attachments.
	 */
	private void clearAttachments(){
		if(this.attachments != null && this.attachments.size() > 0)
			for(File file : this.attachments)
				file.delete();
	}

	/**
	 * Returns the instance of the transport session.
	 * 
	 * @return Instance that contains the transport session.
	 */
	private Session getTransportSession(){
		Session session = null;

		if(this.resources.getTransportUseSsl() != null && this.resources.getTransportUseSsl()){
			session = Session.getDefaultInstance(this.properties, new Authenticator(){
				protected PasswordAuthentication getPasswordAuthentication(){
					return new PasswordAuthentication(Mail.this.resources.getTransportUserName(), Mail.this.resources.getTransportPassword());
				}
			});
		}
		else
			session = Session.getDefaultInstance(this.properties, null);

		return session;
	}

	/**
	 * Returns the instance of the storage session.
	 * 
	 * @return Instance that contains the storage session.
	 */
	private Session getStorageSession(){
		Session session = null;

		if(this.resources.getTransportUseSsl() != null && this.resources.getTransportUseSsl()){
			session = Session.getDefaultInstance(this.properties, new Authenticator(){
				protected PasswordAuthentication getPasswordAuthentication(){
					return new PasswordAuthentication(Mail.this.resources.getStorageUserName(), Mail.this.resources.getStoragePassword());
				}
			});
		}
		else
			session = Session.getDefaultInstance(this.properties, null);

		return session;
	}

	/**
	 * Sends a message
	 * 
	 * @param message Instance that contains the message.
	 * @throws InternalErrorException Occurs when was not possible to send the
	 * message.
	 */
	public void send(MailMessage message) throws InternalErrorException{
		Transport transport = null;

		try{
			Session session = getTransportSession();

			transport = session.getTransport();

			Message sendMessage = new MimeMessage(session);

			buildHeader(message, sendMessage);
			buildBody(message, sendMessage);

			if(!transport.isConnected()){
				if(this.resources.getTransportUserName() != null && this.resources.getTransportUserName().length() > 0)
					transport.connect(this.resources.getTransportServerName(), this.resources.getTransportUserName(), this.resources.getTransportPassword());
				else
					transport.connect();
			}

			transport.sendMessage(sendMessage, sendMessage.getAllRecipients());
		}
		catch(MessagingException e){
			throw new InternalErrorException(e);
		}
		finally{
			clearAttachments();

			try{
				if(transport != null)
					transport.close();
			}
			catch(MessagingException e1){
			}
		}
	}

	/**
	 * Sends a message in a assynchronous way.
	 * 
	 * @param message Instance that contains the message.
	 */
	public void asyncSend(final MailMessage message){
		Thread thread = new Thread(){
			public void run(){
				try{
					send(message);
				}
				catch(InternalErrorException e){
				}
			}
		};

		thread.start();
	}

	/**
	 * Returns the list of folders in the storage.
	 * 
	 * @return List that contains the folders.
	 * @throws InternalErrorException Occurs when was not possible to get the
	 * folders.
	 */
	public Collection<Folder> getFolders() throws InternalErrorException{
		Collection<Folder> folders = null;
		Store storage = null;

		try{
			Session session = getStorageSession();

			storage = session.getStore();

			if(storage != null){
				Folder rootFolder = null;

				if(!storage.isConnected())
					storage.connect(this.resources.getStorageServerName(), this.resources.getStorageUserName(), this.resources.getStoragePassword());

				rootFolder = storage.getDefaultFolder();
				folders = Arrays.asList(rootFolder.list());
			}
		}
		catch(MessagingException e){
			throw new InternalErrorException(e);
		}
		finally{
			try{
				if(storage != null)
					storage.close();
			}
			catch(MessagingException e){
			}
		}

		return folders;
	}

	/**
	 * Builds a message.
	 * 
	 * @param message Instance that contains the message.
	 * @return Instance that contains the message.
	 * @throws InternalErrorException Occurs when was not possible to build the
	 * message.
	 */
	private MailMessage buildMessage(Message message) throws InternalErrorException{
		try{
			MailMessage mailMessage = new MailMessage();

			mailMessage.setToRecipients(Arrays.asList(message.getRecipients(Message.RecipientType.TO)));
			mailMessage.setCcRecipients(Arrays.asList(message.getRecipients(Message.RecipientType.CC)));
			mailMessage.setBccRecipients(Arrays.asList(message.getRecipients(Message.RecipientType.BCC)));
			mailMessage.setSubject(message.getSubject());
			mailMessage.setFrom(message.getFrom()[0]);

			if(message.getSentDate() != null)
				mailMessage.setSentDateTime(new DateTime(message.getSentDate().getTime()));
			else
				mailMessage.setSentDateTime(new DateTime());

			if(message.getReceivedDate() != null)
				mailMessage.setReceivedDateTime(new DateTime(message.getReceivedDate().getTime()));
			else
				mailMessage.setReceivedDateTime(new DateTime());

			mailMessage.setContentType(message.getContentType());
			mailMessage.setContent(buildContent(message, mailMessage));

			return mailMessage;
		}
		catch(MessagingException e){
			throw new InternalErrorException(e);
		}
	}

	/**
	 * Returns a specific message.
	 * 
	 * @param folderName String that contains the identifier of the folder where
	 * the message is stored.
	 * @param messageNumber Numeric value that contains the identifier of the
	 * message.
	 * @return Instance that contains the message.
	 * @throws InternalErrorException Occurs when was not possible to get the
	 * message.
	 */
	public MailMessage retrieve(String folderName, Integer messageNumber) throws InternalErrorException{
		Store storage = null;

		try{
			Folder folder = null;
			Message message = null;
			Session session = getStorageSession();

			storage = session.getStore();

			if(!storage.isConnected())
				storage.connect(this.resources.getStorageServerName(), this.resources.getStorageUserName(), this.resources.getStoragePassword());

			folder = storage.getFolder(folderName);

			folder.open(Folder.READ_ONLY);

			message = folder.getMessage(messageNumber);

			return buildMessage(message);
		}
		catch(MessagingException e){
			throw new InternalErrorException(e);
		}
		finally{
			try{
				if(storage != null)
					storage.close();
			}
			catch(MessagingException e){
			}
		}
	}

	/**
	 * Returns all messages.
	 * 
	 * @param folderName String that contains the identifier of the folder where
	 * the message is stored.
	 * @return List that contains all messages.
	 * @throws InternalErrorException Occurs when was not possible to get the
	 * messages.
	 */
	public Collection<MailMessage> retrieve(String folderName) throws InternalErrorException{
		Store storage = null;

		try{
			Folder folder = null;
			Message messages[] = null;
			Collection<MailMessage> mailMessages = null;
			MailMessage mailMessage = null;
			Session session = getStorageSession();

			storage = session.getStore();

			if(!storage.isConnected())
				storage.connect(this.resources.getStorageServerName(), this.resources.getStorageUserName(), this.resources.getStoragePassword());

			folder = storage.getFolder(folderName);

			folder.open(Folder.READ_ONLY);

			messages = folder.getMessages();

			if(messages != null && messages.length > 0){
				for(Message message : messages){
					mailMessage = buildMessage(message);

					if(mailMessages == null)
						mailMessages = PropertyUtil.instantiate(Constants.DEFAULT_LIST_CLASS);

					mailMessages.add(mailMessage);
				}
			}

			return mailMessages;
		}
		catch(MessagingException e){
			throw new InternalErrorException(e);
		}
		finally{
			try{
				if(storage != null)
					storage.close();
			}
			catch(MessagingException e){
			}
		}
	}

	/**
	 * Builds the message content.
	 * 
	 * @param part Instance that contains the content.
	 * @param mailMessage Instance that contains the message.
	 * @return Instance that contains the content.
	 * @throws InternalErrorException Occurs when was not possible to build the
	 * message content.
	 */
	private Object buildContent(Object part, MailMessage mailMessage) throws InternalErrorException{
		try{
			if(part instanceof Message){
				Message message = (Message)part;

				if(message.getContent() instanceof Multipart)
					return buildContent(message.getContent(), mailMessage);

				return message.getContent();
			}
			else if(part instanceof Multipart){
				Multipart parts = (Multipart)part;
				Part subPart = null;
				InputStream attach = null;
				Object buffer = null;

				for(int cont = 0 ; cont < parts.getCount() ; cont++){
					subPart = parts.getBodyPart(cont);
					attach = subPart.getDataHandler().getDataSource().getInputStream();

					if(attach instanceof InputStream || (subPart.getDisposition() != null && subPart.getDisposition().equals(Part.ATTACHMENT)))
						mailMessage.attach(subPart.getFileName(), ByteUtil.fromBinaryStream(attach));
					else{
						if(subPart.getContent() instanceof Multipart)
							buffer = buildContent(subPart.getContent(), mailMessage);
						else if(buffer == null)
							buffer = subPart.getContent();
					}
				}

				return buffer;
			}

			return part.toString();
		}
		catch(MessagingException | IOException e){
			throw new InternalErrorException(e);
		}
	}
}