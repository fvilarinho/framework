package br.com.concepting.framework.network.helpers;

import br.com.concepting.framework.util.helpers.DateTime;
import br.com.concepting.framework.util.types.ContentType;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * Class responsible to store a generic message.
 *
 * @param <O> Class that defines the content of the message.
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
public class Message<O> implements Serializable{
    @Serial
    private static final long serialVersionUID = 6336269811220022270L;

    private DateTime sentDateTime = null;
    private DateTime receivedDateTime = null;
    private ContentType contentType = null;
    private O content = null;

    /**
     * Returns the content type of the message.
     *
     * @return Instance that contains the content type.
     */
    public ContentType getContentType(){
        return this.contentType;
    }
    
    /**
     * Defines the content type of the message.
     *
     * @param contentType Instance that contains the content type.
     */
    public void setContentType(ContentType contentType){
        this.contentType = contentType;
    }
    
    /**
     * Defines the content type of the message.
     *
     * @param contentType String that contains the identifier of the content
     * type.
     */
    public void setContentType(String contentType){
        try{
            this.contentType = ContentType.toContentType(contentType);
        }
        catch(IllegalArgumentException e){
            this.contentType = null;
        }
    }
    
    /**
     * Returns the message content.
     *
     * @return Instance that contains the message content.
     */
    public O getContent(){
        return this.content;
    }
    
    /**
     * Defines the message content.
     *
     * @param content Instance that contains the message content.
     */
    public void setContent(O content){
        this.content = content;
    }
    
    /**
     * Returns the date/time of the receipt.
     *
     * @return Instance that contains the date/time.
     */
    public Date getReceivedDateTime(){
        return this.receivedDateTime;
    }
    
    /**
     * Defines the date/time of the receipt.
     *
     * @param receivedDateTime Instance that contains the date/time.
     */
    public void setReceivedDateTime(DateTime receivedDateTime){
        this.receivedDateTime = receivedDateTime;
    }
    
    /**
     * Returns the date/time the message was sent.
     *
     * @return Instance that contains the date/time.
     */
    public DateTime getSentDateTime(){
        return this.sentDateTime;
    }
    
    /**
     * Defines the date/time the message was sent.
     *
     * @param sentDateTime Instance that contains the date/time.
     */
    public void setSentDateTime(DateTime sentDateTime){
        this.sentDateTime = sentDateTime;
    }
}