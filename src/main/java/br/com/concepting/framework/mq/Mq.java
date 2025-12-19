package br.com.concepting.framework.mq;

import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.mq.helpers.MqListener;
import br.com.concepting.framework.mq.helpers.MqMessage;
import br.com.concepting.framework.mq.resources.MqResources;
import br.com.concepting.framework.mq.resources.MqResourcesLoader;
import br.com.concepting.framework.resources.exceptions.InvalidResourcesException;
import br.com.concepting.framework.util.ByteUtil;
import br.com.concepting.framework.util.DateTimeUtil;
import br.com.concepting.framework.util.LanguageUtil;
import br.com.concepting.framework.util.PropertyUtil;
import br.com.concepting.framework.util.helpers.DateTime;
import br.com.concepting.framework.util.helpers.PropertyInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.beanutils.ConstructorUtils;

import javax.jms.*;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * Class responsible to manipulate the MQ service.
 *
 * @author fvilarinho
 * @since 3.5.0
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
 */
public class Mq{
    private static final ObjectMapper propertyMapper = PropertyUtil.getMapper();
    
    private static Map<String, Mq> instances = null;
    
    private final MqResources resources;

    private ActiveMQConnection connection = null;
    private Session session = null;
    private Map<String, MessageProducer> messageProducers = null;
    private Map<String, MessageConsumer> messageConsumers = null;
    
    /**
     * Returns the default instance to manipulate the MQ service.
     *
     * @return Instance that manipulates the MQ service.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    public static Mq getInstance() throws InternalErrorException{
        MqResourcesLoader loader = new MqResourcesLoader();
        MqResources resources = loader.getDefault();
        
        return getInstance(resources);
    }
    
    /**
     * Returns the instance to manipulate the MQ service.
     *
     * @param resourcesId String that contains the identifier of the resources.
     * @return Instance that manipulates the MQ service.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    public static Mq getInstance(String resourcesId) throws InternalErrorException{
        MqResourcesLoader loader = new MqResourcesLoader();
        MqResources resources = loader.get(resourcesId);
        
        return getInstance(resources);
    }
    
    /**
     * Returns the instance to manipulate the MQ service.
     *
     * @param resourcesDirname String that contains the directory where the resources
     * are stored.
     * @param resourcesId String that contains the identifier of the resources.
     * @return Instance that manipulates the MQ service.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    public static Mq getInstance(String resourcesDirname, String resourcesId) throws InternalErrorException{
        MqResourcesLoader loader = new MqResourcesLoader(resourcesDirname);
        MqResources resources = loader.get(resourcesId);
        
        return getInstance(resources);
    }
    
    /**
     * Returns the instance to manipulate the MQ service.
     *
     * @param resources Instance that contains the resource.
     * @return Instance that manipulates the MQ service.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    public static Mq getInstance(MqResources resources) throws InternalErrorException{
        if(instances == null)
            instances = PropertyUtil.instantiate(Constants.DEFAULT_MAP_CLASS);

        if(instances != null) {
            Mq instance = instances.get(resources.getId());

            if (instance == null) {
                instance = new Mq(resources);

                instances.put(resources.getId(), instance);
            }

            return instance;
        }

        return null;
    }
    
    /**
     * Constructor - Defines the MQ resource.
     *
     * @param resources Instance that contains the resources.
     * @throws InternalErrorException Occurs when it was not possible to
     * instantiate the MQ services based on the specified parameters.
     */
    private Mq(MqResources resources) throws InternalErrorException{
        super();
        
        this.resources = resources;
        
        initialize();
    }
    
    /**
     * Initialize the communication with the MQ service.
     *
     * @throws InternalErrorException Occurs when was not possible to establish
     * the communication with the MQ service.
     */
    @SuppressWarnings("unchecked")
    private void initialize() throws InternalErrorException{
        try{
            StringBuilder connectorUri = new StringBuilder();
            
            connectorUri.append("tcp://");
            connectorUri.append(this.resources.getServerName());
            connectorUri.append(":");
            connectorUri.append(this.resources.getServerPort());
            
            ActiveMQConnectionFactory connectionFactory;
            
            if(this.resources.getUserName() != null && !this.resources.getUserName().isEmpty())
                connectionFactory = new ActiveMQConnectionFactory(this.resources.getUserName(), this.resources.getPassword(), connectorUri.toString());
            else
                connectionFactory = new ActiveMQConnectionFactory(connectorUri.toString());
            
            this.connection = (ActiveMQConnection) connectionFactory.createConnection();
            this.connection.start();
            
            this.session = this.connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            
            Collection<MqResources.MqQueue> queues = this.resources.getQueues();
            
            if(queues != null && !queues.isEmpty()){
                for(MqResources.MqQueue queue: queues){
                    if(this.messageProducers == null)
                        this.messageProducers = PropertyUtil.instantiate(Constants.DEFAULT_MAP_CLASS);
                    
                    if(this.messageConsumers == null)
                        this.messageConsumers = PropertyUtil.instantiate(Constants.DEFAULT_MAP_CLASS);
                    
                    Queue messageQueue = this.session.createQueue(queue.getId());
                    MessageProducer messageProducer = this.session.createProducer(messageQueue);
                    
                    this.messageProducers.put(queue.getId(), messageProducer);
                    
                    MessageConsumer messageConsumer = this.session.createConsumer(messageQueue);
                    String listenerClassName = queue.getListenerClass();
                    
                    if(listenerClassName != null && !listenerClassName.isEmpty()){
                        Class<? extends MqListener> listenerClass = (Class<? extends MqListener>) Class.forName(listenerClassName);
                        MqListener listener = ConstructorUtils.invokeConstructor(listenerClass, null);
                        
                        messageConsumer.setMessageListener(listener);
                    }
                    
                    this.messageConsumers.put(queue.getId(), messageConsumer);
                }
            }
        }
        catch(Throwable e){
            throw new InternalErrorException(e);
        }
    }
    
    /**
     * Pushes a message in a queue.
     *
     * @param queueId String that contains the identifier of the queue.
     * @param m Instance that contains the message.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    public void push(String queueId, MqMessage m) throws InternalErrorException{
        try{
            if(queueId == null || m == null)
                return;
            
            MessageProducer messageProducer = this.messageProducers.get(queueId);
            
            if(messageProducer == null)
                throw new InvalidResourcesException(queueId);
            
            Class<?> clazz = m.getClass();
            Message message = this.session.createMessage();
            
            message.setStringProperty(Constants.CLASS_ATTRIBUTE_ID, clazz.getName());
            
            Collection<PropertyInfo> propertiesInfos = PropertyUtil.getInfos(clazz);
            
            if(propertiesInfos != null && !propertiesInfos.isEmpty()){
                Object propertyValue;
                
                for(PropertyInfo propertyInfo: propertiesInfos){
                    propertyValue = PropertyUtil.getValue(m, propertyInfo.getId());
                    
                    if(propertyValue == null)
                        continue;
                    
                    if(PropertyUtil.isByte(propertyValue))
                        message.setByteProperty(propertyInfo.getId(), (Byte) propertyValue);
                    else if(PropertyUtil.isShort(propertyValue))
                        message.setIntProperty(propertyInfo.getId(), ((Short) propertyValue).intValue());
                    else if(PropertyUtil.isInteger(propertyValue))
                        message.setIntProperty(propertyInfo.getId(), (Integer) propertyValue);
                    else if(PropertyUtil.isFloat(propertyValue))
                        message.setFloatProperty(propertyInfo.getId(), (Float) propertyValue);
                    else if(PropertyUtil.isDouble(propertyValue))
                        message.setDoubleProperty(propertyInfo.getId(), (Double) propertyValue);
                    else if(PropertyUtil.isLong(propertyValue))
                        message.setLongProperty(propertyInfo.getId(), (Long) propertyValue);
                    else if(PropertyUtil.isBigDecimal(propertyValue))
                        message.setDoubleProperty(propertyInfo.getId(), ((BigDecimal) propertyValue).doubleValue());
                    else if(PropertyUtil.isBigInteger(propertyValue))
                        message.setLongProperty(propertyInfo.getId(), ((BigInteger) propertyValue).longValue());
                    else if(propertyInfo.isEnum())
                        message.setStringProperty(propertyInfo.getId(), propertyValue.toString());
                    else if(propertyInfo.isString())
                        message.setStringProperty(propertyInfo.getId(), (String) propertyValue);
                    else if(propertyInfo.isTime())
                        message.setStringProperty(propertyInfo.getId(), DateTimeUtil.format((DateTime) propertyValue, LanguageUtil.getDefaultLanguage()));
                    else if(propertyInfo.isDate())
                        message.setStringProperty(propertyInfo.getId(), DateTimeUtil.format((Date) propertyValue, LanguageUtil.getDefaultLanguage()));
                    else if(propertyInfo.isByteArray())
                        message.setStringProperty(propertyInfo.getId(), ByteUtil.toBase64((byte[]) propertyValue));
                    else
                        message.setStringProperty(propertyInfo.getId(), propertyMapper.writeValueAsString(propertyValue));
                }
            }
            
            messageProducer.send(message);
        }
        catch(JMSException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException | NoSuchFieldException | UnsupportedEncodingException | JsonProcessingException e){
            throw new InternalErrorException(e);
        }
    }
    
    /**
     * Waits and pulls a message from a queue.
     *
     * @param <M> Class that defines the message.
     * @param queueId String that contains the identifier of the queue.
     * @return Instance that contains the message.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    public <M extends MqMessage> M pull(String queueId) throws InternalErrorException{
        return pull(queueId, true);
    }
    
    /**
     * Pulls a message from a queue.
     *
     * @param <M> Class that defines the message.
     * @param queueId String that contains the identifier of the queue.
     * @param wait True/False.
     * @return Instance that contains the message.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public <M extends MqMessage> M pull(String queueId, boolean wait) throws InternalErrorException{
        try{
            if(queueId == null)
                return null;
            
            MessageConsumer messageConsumer = this.messageConsumers.get(queueId);
            
            if(messageConsumer == null)
                throw new InvalidResourcesException(queueId);
            
            Message m = (wait ? messageConsumer.receive() : messageConsumer.receiveNoWait());
            M message = null;
            
            if(m != null){
                Class<?> clazz = Class.forName(m.getStringProperty(Constants.CLASS_ATTRIBUTE_ID));
                
                message = (M) ConstructorUtils.invokeConstructor(clazz, null);
                message.setId(m.getJMSMessageID());
                message.setReceivedDateTime(new DateTime());
                message.setSentDateTime(new DateTime(m.getJMSTimestamp()));
                
                Collection<PropertyInfo> propertiesInfos = PropertyUtil.getInfos(clazz);
                
                if(propertiesInfos != null && !propertiesInfos.isEmpty()){
                    Object propertyValue;
                    
                    for(PropertyInfo propertyInfo: propertiesInfos){
                        try{
                            if(propertyInfo.isByte())
                                propertyValue = m.getByteProperty(propertyInfo.getId());
                            else if(propertyInfo.isShort())
                                propertyValue = ((Integer) m.getIntProperty(propertyInfo.getId())).shortValue();
                            else if(propertyInfo.isInteger())
                                propertyValue = m.getIntProperty(propertyInfo.getId());
                            else if(propertyInfo.isFloat())
                                propertyValue = m.getFloatProperty(propertyInfo.getId());
                            else if(propertyInfo.isDouble())
                                propertyValue = m.getDoubleProperty(propertyInfo.getId());
                            else if(propertyInfo.isLong())
                                propertyValue = m.getLongProperty(propertyInfo.getId());
                            else if(propertyInfo.isBigDecimal())
                                propertyValue = BigDecimal.valueOf(m.getDoubleProperty(propertyInfo.getId()));
                            else if(propertyInfo.isBigInteger())
                                propertyValue = BigInteger.valueOf(m.getLongProperty(propertyInfo.getId()));
                            else if(propertyInfo.isEnum())
                                propertyValue = Enum.valueOf((Class) propertyInfo.getClazz(), m.getStringProperty(propertyInfo.getId()));
                            else if(propertyInfo.isString())
                                propertyValue = m.getStringProperty(propertyInfo.getId());
                            else if(propertyInfo.isDate())
                                propertyValue = DateTimeUtil.parse(m.getStringProperty(propertyInfo.getId()), LanguageUtil.getDefaultLanguage());
                            else if(propertyInfo.isByteArray())
                                propertyValue = ByteUtil.fromBase64(m.getStringProperty(propertyInfo.getId()));
                            else
                                propertyValue = propertyMapper.readValue(m.getStringProperty(propertyInfo.getId()), propertyInfo.getClazz());
                            
                            PropertyUtil.setValue(message, propertyInfo.getId(), propertyValue);
                        }
                        catch(Throwable ignored){
                        }
                    }
                }
            }
            
            return message;
        }
        catch(JMSException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException | NoSuchFieldException | ClassNotFoundException e){
            throw new InternalErrorException(e);
        }
    }
    
    /**
     * Shutdowns the MQ service.
     */
    public void shutdown(){
        try{
            this.connection.cleanup();
        }
        catch(Throwable ignored){
        }
        
        try{
            this.connection.close();
        }
        catch(Throwable ignored){
        }

        try{
            this.session.close();
        }
        catch(Throwable ignored){
        }
    }
}