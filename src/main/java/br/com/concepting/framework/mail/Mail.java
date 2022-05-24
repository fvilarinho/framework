package br.com.concepting.framework.mail;

import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.mail.helpers.MailMessage;
import br.com.concepting.framework.mail.resources.MailResources;
import br.com.concepting.framework.mail.resources.MailResourcesLoader;
import br.com.concepting.framework.mail.types.MailStorageType;
import br.com.concepting.framework.mail.types.MailTransportType;
import br.com.concepting.framework.util.ByteUtil;
import br.com.concepting.framework.util.FileUtil;
import br.com.concepting.framework.util.PropertyUtil;
import br.com.concepting.framework.util.helpers.DateTime;

import jakarta.activation.DataHandler;
import jakarta.activation.FileDataSource;

import jakarta.mail.*;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import javax.net.ssl.SSLSocketFactory;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;

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

    private final MailResources resources;

    private Properties properties = null;
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
            this.properties.setProperty("mail.smtp.port", String.valueOf(this.resources.getTransportServerPort()));
            
            if(this.resources.getTransportUserName() != null && this.resources.getTransportUserName().length() > 0)
                this.properties.setProperty("mail.smtp.auth", Boolean.TRUE.toString());
            
            if(this.resources.getTransportUseTls())
                this.properties.put("mail.smtp.starttls.enable", Boolean.TRUE.toString());
            
            if(this.resources.getTransportUseSsl()){
                this.properties.put("mail.smtp.socketFactory.class", SSLSocketFactory.class.getName());
                this.properties.put("mail.smtp.socketFactory.port", String.valueOf(this.resources.getTransportServerPort()));
            }
        }
        
        if(this.resources.getStorage() == MailStorageType.POP3){
            this.properties.setProperty("mail.store.protocol", MailStorageType.POP3.toString().toLowerCase());
            this.properties.setProperty("mail.pop3.host", this.resources.getStorageServerName());
            this.properties.setProperty("mail.pop3.port", String.valueOf(this.resources.getStorageServerPort()));
            
            if(this.resources.getStorageUseTls())
                this.properties.put("mail.pop3.starttls.enable", Boolean.TRUE.toString());
            
            if(this.resources.getStorageUseSsl()){
                this.properties.put("mail.pop3.socketFactory.class", SSLSocketFactory.class.getName());
                this.properties.put("mail.pop3.socketFactory.port", String.valueOf(this.resources.getStorageServerPort()));
            }
        }
        else if(this.resources.getStorage() == MailStorageType.IMAP || this.resources.getStorage() == MailStorageType.IMAPS){
            this.properties.setProperty("mail.store.protocol", MailStorageType.IMAPS.toString().toLowerCase());
            
            if(this.resources.getStorage() == MailStorageType.IMAP){
                this.properties.setProperty("mail.imap.host", this.resources.getStorageServerName());
                this.properties.setProperty("mail.imap.port", String.valueOf(this.resources.getStorageServerPort()));
    
                if(this.resources.getStorageUseTls())
                    this.properties.put("mail.imap.starttls.enable", Boolean.TRUE.toString());
    
                if(this.resources.getStorageUseSsl()){
                    this.properties.put("mail.imap.ssl.enable", Boolean.TRUE.toString());
                    this.properties.put("mail.imap.socketFactory.class", SSLSocketFactory.class.getName());
                    this.properties.put("mail.imap.socketFactory.port", String.valueOf(this.resources.getStorageServerPort()));
                }
            }
            else{
                this.properties.setProperty("mail.imaps.host", this.resources.getStorageServerName());
                this.properties.setProperty("mail.imaps.port", String.valueOf(this.resources.getStorageServerPort()));
    
                if(this.resources.getStorageUseTls())
                    this.properties.put("mail.imaps.starttls.enable", Boolean.TRUE.toString());
    
                if(this.resources.getStorageUseSsl()){
                    this.properties.put("mail.imaps.ssl.enable", Boolean.TRUE.toString());
                    this.properties.put("mail.imaps.socketFactory.class", SSLSocketFactory.class.getName());
                    this.properties.put("mail.imaps.socketFactory.port", String.valueOf(this.resources.getStorageServerPort()));
                }
            }
        }
    }
    
    /**
     * Builds the message headers.
     *
     * @param message Instance that contains the message.
     * @param sendMessage Instance that contains the message.
     * @throws MessagingException Occurs when was not possible to
     * build the message headers.
     */
    private void buildHeader(MailMessage message, Message sendMessage) throws MessagingException {
        if(message == null || sendMessage == null)
            return;
        
        sendMessage.setFrom(message.getFrom());
        
        if(message.getToRecipients() != null && message.getToRecipients().size() > 0)
            for(Address item: message.getToRecipients())
                sendMessage.addRecipient(Message.RecipientType.TO, item);
        
        if(message.getCcRecipients() != null && message.getCcRecipients().size() > 0)
            for(Address item: message.getCcRecipients())
                sendMessage.addRecipient(Message.RecipientType.CC, item);
        
        if(message.getBccRecipients() != null && message.getBccRecipients().size() > 0)
            for(Address item: message.getBccRecipients())
                sendMessage.addRecipient(Message.RecipientType.BCC, item);
        
        sendMessage.setSubject(message.getSubject());
        sendMessage.setSentDate(message.getSentDateTime());
    }
    
    /**
     * Builds the message body.
     *
     * @param message Instance that contains the message.
     * @param sendMessage Instance that contains the message.
     * @throws MessagingException Occurs when was not possible to
     * build the message body.
     * @throws IOException Occurs when was not possible to
     * build the message body.
     */
    private void buildBody(MailMessage message, Message sendMessage) throws MessagingException, IOException{
        if(message == null || sendMessage == null)
            return;
        
        MimeMultipart parts = new MimeMultipart();
        MimeBodyPart part = new MimeBodyPart();
        
        part.setContent(message.getContent(), message.getContentType().getMimeType());
        
        parts.addBodyPart(part);
        
        loadAttachments(message, sendMessage, parts);
    }
    
    /**
     * Loads the message body.
     *
     * @param message Instance that contains the message.
     * @param sendMessage Instance that contains the message.
     * @param parts Instance that contains the attachments.
     * @throws MessagingException Occurs when was not possible to
     * load the attachments.
     * @throws IOException Occurs when was not possible to
     * load the attachments.
     */
    private void loadAttachments(MailMessage message, Message sendMessage, MimeMultipart parts) throws MessagingException, IOException{
        if(message == null || sendMessage == null || parts == null)
            return;
        
        if(message.getAttachments() != null && !message.getAttachments().isEmpty()){
            this.attachments = PropertyUtil.instantiate(Constants.DEFAULT_LIST_CLASS);
            
            MimeBodyPart attachPart;
            byte[] attachData;
            String filename;
            Object fileContent;
            File file;
            FileDataSource handler;
            int cont = 0;
            
            for(Map<String, Object> attach: message.getAttachments()){
                attachPart = new MimeBodyPart();
                filename = (String) attach.get(Constants.CONTENT_FILENAME_ATTRIBUTE_ID);
                fileContent = attach.get(Constants.CONTENT_ATTRIBUTE_ID);
                
                if(PropertyUtil.isByteArray(fileContent))
                    attachData = (byte[]) fileContent;
                else
                    attachData = fileContent.toString().getBytes();
                
                if(attachData != null && attachData.length > 0){
                    if(filename == null || filename.length() == 0){
                        filename = "attachment-".concat(String.valueOf(cont));
                        
                        cont++;
                    }
                    
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
            }
        }
        
        sendMessage.setContent(parts);
    }
    
    /**
     * Clears all attachments.
     */
    private void clearAttachments(){
        if(this.attachments != null && this.attachments.size() > 0)
            for(File file: this.attachments)
                file.delete();
    }
    
    /**
     * Returns the instance of the transport session.
     *
     * @return Instance that contains the transport session.
     */
    private Session getTransportSession(){
        return Session.getDefaultInstance(this.properties, new Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(Mail.this.resources.getTransportUserName(), Mail.this.resources.getTransportPassword());
            }
        });
    }
    
    /**
     * Returns the instance of the storage session.
     *
     * @return Instance that contains the storage session.
     */
    private Session getStorageSession(){
        return Session.getDefaultInstance(this.properties, new Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(Mail.this.resources.getStorageUserName(), Mail.this.resources.getStoragePassword());
            }
        });
    }
    
    public MailResources getResources(){
        return this.resources;
    }
    
    /**
     * Sends a message
     *
     * @param message Instance that contains the message.
     * @throws MessagingException Occurs when was not possible to send the
     * message.
     * @throws IOException Occurs when was not possible to send the
     * message.
     */
    public void send(MailMessage message) throws MessagingException, IOException{
        Transport transport = null;
        
        try{
            Session session = getTransportSession();
            
            transport = session.getTransport();
            
            Message sendMessage = new MimeMessage(session);
            
            buildHeader(message, sendMessage);
            buildBody(message, sendMessage);
            
            if(!transport.isConnected())
                transport.connect(this.resources.getTransportServerName(), this.resources.getTransportServerPort(), this.resources.getTransportUserName(), this.resources.getTransportPassword());
            
            transport.sendMessage(sendMessage, sendMessage.getAllRecipients());
        }
        finally{
            clearAttachments();
            
            try{
                if(transport != null)
                    transport.close();
            }
            catch(MessagingException ignored){
            }
        }
    }
    
    /**
     * Sends a message in an assynchronous way.
     *
     * @param message Instance that contains the message.
     */
    public void asyncSend(final MailMessage message){
        Thread thread = new Thread(() -> {
            try{
                send(message);
            }
            catch(MessagingException | IOException ignored){
            }
        });
        
        thread.start();
    }
    
    /**
     * Returns the list of folders in the storage.
     *
     * @return List that contains the folders.
     * @throws MessagingException Occurs when was not possible to get the
     * folders.
     */
    public Collection<Folder> getFolders() throws MessagingException{
        Collection<Folder> folders = null;
        Store storage = null;
        
        try{
            Session session = getStorageSession();
            
            storage = session.getStore();
            
            if(storage != null){
                Folder rootFolder;
                
                if(!storage.isConnected())
                    storage.connect(this.resources.getStorageServerName(), this.resources.getStorageUserName(), this.resources.getStoragePassword());
                
                rootFolder = storage.getDefaultFolder();
                folders = Arrays.asList(rootFolder.list());
            }
        }
        finally{
            try{
                if(storage != null)
                    storage.close();
            }
            catch(MessagingException ignored){
            }
        }
        
        return folders;
    }
    
    /**
     * Builds a message.
     *
     * @param message Instance that contains the message.
     * @return Instance that contains the message.
     * message.
     * @throws MessagingException Occurs when was not possible to get the
     * message.
     * @throws IOException Occurs when was not possible to get the
     * message.
     */
    private MailMessage buildMessage(Message message) throws MessagingException, IOException{
        MailMessage mailMessage = new MailMessage();
        Address[] to = message.getRecipients(Message.RecipientType.TO);
        Address[] cc = message.getRecipients(Message.RecipientType.CC);
        Address[] bcc = message.getRecipients(Message.RecipientType.BCC);
        
        if(to != null && to.length > 0)
            mailMessage.setToRecipients(Arrays.asList(to));
        
        if(cc != null && cc.length > 0)
            mailMessage.setCcRecipients(Arrays.asList(cc));
        
        if(bcc != null && bcc.length > 0)
            mailMessage.setBccRecipients(Arrays.asList(bcc));
        
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
    
    /**
     * Returns a specific message.
     *
     * @param folderName String that contains the identifier of the folder where
     * the message is stored.
     * @param messageNumber Numeric value that contains the identifier of the
     * message.
     * @return Instance that contains the message.
     * @throws MessagingException Occurs when was not possible to get the
     * message.
     * @throws IOException Occurs when was not possible to get the
     * message.
     */
    public MailMessage retrieve(String folderName, Integer messageNumber) throws MessagingException, IOException{
        Store storage = null;
        
        try{
            Folder folder;
            Message message;
            Session session = getStorageSession();
            
            storage = session.getStore();
            
            if(!storage.isConnected())
                storage.connect(this.resources.getStorageServerName(), this.resources.getStorageUserName(), this.resources.getStoragePassword());
            
            folder = storage.getFolder(folderName);
            
            folder.open(Folder.READ_ONLY);
            
            message = folder.getMessage(messageNumber);
            
            return buildMessage(message);
        }
        finally{
            try{
                if(storage != null)
                    storage.close();
            }
            catch(MessagingException ignored){
            }
        }
    }
    
    /**
     * Retrieves all messages from a specific inbox.
     *
     * @param folderName String that contains the inbox identifier.
     * @return List that contains the messages.
     * @throws MessagingException Occurs when was not possible to read/parse a message.
     * @throws IOException Occurs when was not possible to connect into the inbox.
     */
    public Collection<MailMessage> retrieve(String folderName) throws MessagingException, IOException{
        return retrieve(folderName, false);
    }
    
    /**
     * Retrieves only the unread messages from a specific inbox.
     *
     * @param folderName String that contains the inbox identifier.
     * @return List that contains the messages.
     * @throws MessagingException Occurs when was not possible to read/parse a message.
     * @throws IOException Occurs when was not possible to connect into the inbox.
     */
    public Collection<MailMessage> retrieveUnread(String folderName) throws MessagingException, IOException{
        return retrieve(folderName, true);
    }
    
    /**
     * Retrieves messages from a specific inbox.
     *
     * @param folderName String that contains the inbox identifier.
     * @param onlyUnread True/False.
     * @return List that contains the messages.
     * @throws MessagingException Occurs when was not possible to read/parse a message.
     * @throws IOException Occurs when was not possible to connect into the inbox.
     */
    public Collection<MailMessage> retrieve(String folderName, boolean onlyUnread) throws MessagingException, IOException{
        Store storage = null;
        
        try{
            Folder folder;
            Message[] messages;
            Collection<MailMessage> mailMessages = null;
            MailMessage mailMessage;
            Session session = getStorageSession();
            
            storage = session.getStore();
            
            if(!storage.isConnected())
                storage.connect(this.resources.getStorageServerName(), this.resources.getStorageServerPort(), this.resources.getStorageUserName(), this.resources.getStoragePassword());
            
            folder = storage.getFolder(folderName);
            
            folder.open(Folder.READ_WRITE);
            
            messages = folder.getMessages();
            
            if(messages != null && messages.length > 0){
                for(Message message: messages){
                    if(onlyUnread)
                        if(message.getFlags().contains(Flags.Flag.SEEN))
                            continue;
                    
                    message.setFlag(Flags.Flag.SEEN, true);
                    
                    mailMessage = buildMessage(message);
                    
                    if(mailMessages == null)
                        mailMessages = PropertyUtil.instantiate(Constants.DEFAULT_LIST_CLASS);
                    
                    mailMessages.add(mailMessage);
                }
            }
            
            return mailMessages;
        }
        finally{
            try{
                if(storage != null)
                    storage.close();
            }
            catch(MessagingException ignored){
            }
        }
    }
    
    /**
     * Builds the message content.
     *
     * @param part Instance that contains the content.
     * @param mailMessage Instance that contains the message.
     * @return Instance that contains the content.
     * @throws MessagingException Occurs when was not possible to build the
     * message content.
     * @throws IOException Occurs when was not possible to build the
     * message content.
     */
    private Object buildContent(Object part, MailMessage mailMessage) throws IOException, MessagingException{
        if(part instanceof Message){
            Message message = (Message) part;
            
            if(message.getContent() instanceof Multipart)
                return buildContent(message.getContent(), mailMessage);
            
            return message.getContent();
        }
        else if(part instanceof Multipart){
            Multipart parts = (Multipart) part;
            Part subPart;
            InputStream attach;
            Object buffer = null;
            
            for(int cont = 0; cont < parts.getCount(); cont++){
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
}