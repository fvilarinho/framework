package br.com.concepting.framework.mq.resources.helpers;

import java.io.Serializable;

/**
 * Class that defines a MQ queue.
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
public class MqQueue implements Serializable{
    private static final long serialVersionUID = -4707395643493075904L;
    
    private String id = null;
    private String listenerClass = null;
    
    /**
     * Returns the identifier of the queue.
     *
     * @return String that contains the identifier.
     */
    public String getId(){
        return this.id;
    }
    
    /**
     * Defines the identifier of the queue.
     *
     * @param id String that contains the identifier.
     */
    public void setId(String id){
        this.id = id;
    }
    
    /**
     * Returns the identifier of the queue listener.
     *
     * @return String that contains the identifier.
     */
    public String getListenerClass(){
        return this.listenerClass;
    }
    
    /**
     * Defines the identifier of the queue listener.
     *
     * @param listenerClass String that contains the identifier.
     */
    public void setListenerClass(String listenerClass){
        this.listenerClass = listenerClass;
    }
}