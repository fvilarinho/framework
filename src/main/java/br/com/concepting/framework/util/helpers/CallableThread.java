package br.com.concepting.framework.util.helpers;

/**
 * Class that defines a callable thread.
 *
 * @param <O> Class that defines the object that will be processed in the thread.
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
 * @author fvilarinho
 * @since 3.7.0
 */
public abstract class CallableThread<O> implements Runnable{
    private DateTime startDateTime = null;
    private O object = null;
    private Throwable exception = null;
    
    /**
     * Default constructor.
     *
     * @param object Instance that contains the object that will be processed.
     */
    public CallableThread(O object){
        super();
        
        set(object);
    }
    
    /**
     * Returns the current state of the object.
     *
     * @return Instance that contains the object.
     */
    public synchronized O get(){
        return this.object;
    }
    
    /**
     * Defines the current state of the object.
     *
     * @param object Instance that contains the object.
     */
    public synchronized void set(O object){
        this.object = object;
    }
    
    /**
     * Returns the start date/time.
     *
     * @return Instance that contains the start date/time.
     */
    public DateTime getStartDateTime(){
        return this.startDateTime;
    }
    
    /**
     * Defines the start date/time.
     *
     * @param startDateTime Instance that contains the start date/time.
     */
    public void setStartDateTime(DateTime startDateTime){
        this.startDateTime = startDateTime;
    }
    
    /**
     * Returns the current exception of the processing.
     *
     * @return Instance that contains the exception.
     */
    public Throwable getException(){
        return this.exception;
    }
    
    /**
     * Defines the current exception of the processing.
     *
     * @param exception Instance that contains the exception.
     */
    public void setException(Throwable exception){
        this.exception = exception;
    }
    
    /**
     * @see java.lang.Runnable#run()
     */
    public void run(){
        setStartDateTime(new DateTime());
    }
}