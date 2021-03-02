package br.com.concepting.framework.util;

import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.util.helpers.CallableThread;
import br.com.concepting.framework.util.helpers.DateTime;
import br.com.concepting.framework.util.types.DateFieldType;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Class that defines the thread manager.
 *
 * @param <O> Class that defines the object that will be processed.
 * @author fvilarinho
 * @since 3.7.0
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
public class ThreadManager<O>{
    private List<Future<O>> futureAndPromises = PropertyUtil.instantiate(Constants.DEFAULT_LIST_CLASS);
    private List<CallableThread<O>> threads = PropertyUtil.instantiate(Constants.DEFAULT_LIST_CLASS);
    private ExecutorService executor = null;
    private Integer timeoutPerThread = null;
    
    
    /**
     * Default constructor.
     *
     * @param timeoutPerThread Numeric value that contains the timeout.
     */
    public ThreadManager(Integer timeoutPerThread){
        super();
        
        setTimeoutPerThread(timeoutPerThread);
    }
    
    /**
     * Returns the timeout (in seconds) per thread.
     *
     * @return Numeric value that contains the timeout.
     */
    public Integer getTimeoutPerThread(){
        return this.timeoutPerThread;
    }
    
    /**
     * Defines the timeout (in seconds) per thread.
     *
     * @param timeoutPerThread Numeric value that contains the timeout.
     */
    public void setTimeoutPerThread(Integer timeoutPerThread){
        this.timeoutPerThread = timeoutPerThread;
    }
    
    /**
     * Add a thread to the processing queue.
     *
     * @param callable Instance that contains the thread implementation.
     */
    @SuppressWarnings("unchecked")
    public void addToQueue(CallableThread<O> callable){
        if(this.executor == null)
            this.executor = Executors.newCachedThreadPool();
        
        Future<O> futureAndPromise = (Future<O>) this.executor.submit(callable);
        
        this.threads.add(callable);
        this.futureAndPromises.add(futureAndPromise);
    }
    
    /**
     * Wait until all threads in the queue finish if the queue size reached the maximum number of threads specified.
     *
     * @param maximumThreads Numeric value that contains the maximum number of threads.
     */
    public void waitUntilFinish(Integer maximumThreads){
        if(this.futureAndPromises != null && this.futureAndPromises.size() >= maximumThreads)
            waitUntilAllFinish();
    }
    
    /**
     * Wait until all threads in the queue finish.
     */
    public void waitUntilAllFinish(){
        if(this.futureAndPromises != null && !this.futureAndPromises.isEmpty()){
            while(this.futureAndPromises != null && !this.futureAndPromises.isEmpty()){
                for(int cont = 0; cont < this.futureAndPromises.size(); cont++){
                    Future<O> futureAndPromise = this.futureAndPromises.get(cont);
                    CallableThread<O> thread = this.threads.get(cont);
                    
                    if(futureAndPromise.isDone() || futureAndPromise.isCancelled()){
                        this.futureAndPromises.remove(cont);
                        
                        cont--;
                    }
                    else{
                        DateTime startDateTime = thread.getStartDateTime();
                        
                        if(startDateTime != null){
                            DateTime now = new DateTime();
                            Integer aliveInSeconds = DateTimeUtil.diff(now, startDateTime, DateFieldType.SECONDS).intValue();
                            
                            if(aliveInSeconds > (this.timeoutPerThread)){
                                futureAndPromise.cancel(true);
                                
                                this.futureAndPromises.remove(cont);
                                
                                cont--;
                            }
                        }
                    }
                }
                
                try{
                    Thread.sleep(100);
                }
                catch(InterruptedException e){
                    break;
                }
            }
        }
    }
    
    /**
     * Returns the instance of the threads.
     *
     * @return List that contains the threads.
     */
    public Collection<CallableThread<O>> getThreads(){
        return this.threads;
    }
    
    /**
     * Returns the instance of the processed objects.
     *
     * @return List that contains the threads.
     */
    public Collection<O> getResults(){
        Collection<O> results = null;
        
        if(this.threads != null && !this.threads.isEmpty()){
            for(CallableThread<O> thread: this.threads){
                if(results == null)
                    results = PropertyUtil.instantiate(Constants.DEFAULT_LIST_CLASS);
                
                results.add(thread.get());
            }
        }
        
        return results;
    }
    
    /**
     * Flush the manager.
     */
    public void flush(){
        this.threads.clear();
        this.futureAndPromises.clear();
    }
}