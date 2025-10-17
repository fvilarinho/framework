package br.com.concepting.framework.util;

import br.com.concepting.framework.constants.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.concurrent.TimeoutException;

/**
 * Class responsible to manipulate processes.
 *
 * @author fvilarinho
 * @since 1.0.0
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
 * along with this program.  If not, see <a href="http://www.gnu.org/licenses"></a>.</pre>
 */
public class ProcessLoader{
    private static ProcessLoader instance = null;
    
    /**
     * Returns the instance.
     *
     * @return Instance that contains the class.
     */
    public static ProcessLoader getInstance(){
        if(instance == null)
            instance = new ProcessLoader();
        
        return instance;
    }
    
    /**
     * Executes a process with parameters.
     *
     * @param commandParameters List that contains the parameters.
     * @return Instance that contains the stream of the execution.
     * @throws IOException Occurs when was not possible to execute the
     * operation.
     */
    public String execute(Collection<String> commandParameters) throws IOException{
        return execute(commandParameters, false, Constants.DEFAULT_PROCESS_TIMEOUT);
    }
    
    /**
     * Executes a process with parameters.
     *
     * @param commandParameters List that contains the parameters.
     * @param timeout Numeric value that contains the timeout.
     * @return Instance that contains the stream of the execution.
     * @throws IOException Occurs when was not possible to execute the
     * operation.
     */
    public String execute(Collection<String> commandParameters, int timeout) throws IOException{
        return execute(commandParameters, false, timeout);
    }
    
    /**
     * Executes a process with parameters.
     *
     * @param commandParameters List that contains the parameters.
     * @param async True/False.
     * @param timeout Numeric value that contains the timeout.
     * @return Instance that contains the stream of the execution.
     * @throws IOException Occurs when was not possible to execute the
     * operation.
     */
    public String execute(Collection<String> commandParameters, boolean async, int timeout) throws IOException{
        if(commandParameters != null && !commandParameters.isEmpty()){
            StringBuilder command = new StringBuilder();
            
            for(String commandParameter: commandParameters){
                if(command.length() > 0)
                    command.append(" ");
                
                command.append(commandParameter);
            }
            
            return execute(command.toString(), async, timeout);
        }
        
        return null;
    }
    
    /**
     * Executes an asynchronous process with parameters.
     *
     * @param commandParameters List that contains the parameters.
     * @throws IOException Occurs when was not possible to execute the
     * operation.
     */
    public void asyncExecute(Collection<String> commandParameters) throws IOException{
        execute(commandParameters, true, Constants.DEFAULT_PROCESS_TIMEOUT);
    }
    
    /**
     * Executes a process with parameters.
     *
     * @param command List that contains the parameters.
     * @return Instance that contains the stream of the execution.
     * @throws IOException Occurs when was not possible to execute the
     * operation.
     */
    public String execute(String command) throws IOException{
        return execute(command, false, Constants.DEFAULT_PROCESS_TIMEOUT);
    }
    
    /**
     * Executes a process with parameters.
     *
     * @param command List that contains the parameters.
     * @param timeout Numeric value that contains the timeout.
     * @return Instance that contains the stream of the execution.
     * @throws IOException Occurs when was not possible to execute the
     * operation.
     */
    public String execute(String command, int timeout) throws IOException{
        return execute(command, false, timeout);
    }
    
    /**
     * Executes a process with parameters.
     *
     * @param command List that contains the parameters.
     * @param async True/False.
     * @param timeout Numeric value that contains the timeout.
     * @return Instance that contains the stream of the execution.
     * @throws IOException Occurs when was not possible to execute the
     * operation.
     */
    private String execute(String command, boolean async, int timeout) throws IOException{
        if(command != null && !command.isEmpty()){
            Process child = Runtime.getRuntime().exec(command);
            
            if(!async){
                Worker worker = new Worker(child);
                
                worker.start();
                
                try{
                    if(timeout == 0)
                        timeout = Constants.DEFAULT_PROCESS_TIMEOUT;
                    
                    worker.join(timeout * 1000L);
                    
                    if(worker.exit != null){
                        BufferedReader reader = new BufferedReader(new InputStreamReader(worker.in));
                        StringBuilder result = new StringBuilder();
                        String line;

                        while((line = reader.readLine()) != null){
                            if(result.length() > 0)
                                result.append(StringUtil.getLineBreak());
                            
                            result.append(line);
                        }
                        
                        return result.toString();
                    }
                    
                    throw new IOException(new TimeoutException(String.valueOf(timeout)));
                }
                catch(InterruptedException e){
                    worker.interrupt();
                    
                    return null;
                }
                finally{
                    child.destroyForcibly();
                }
            }
        }
        
        return null;
    }
    
    /**
     * Executes an asynchronous process with parameters.
     *
     * @param command List that contains the parameters.
     * @throws IOException Occurs when was not possible to execute the
     * operation.
     */
    public void asyncExecute(String command) throws IOException{
        execute(command, true, Constants.DEFAULT_PROCESS_TIMEOUT);
    }
    
    /**
     * Class responsible to capture the stream of a process.
     *
     * @author fvilarinho
     * @since 3.4.0
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
     * along with this program.  If not, see <a href="http://www.gnu.org/licenses"></a>.</pre>
     */
    private static class Worker extends Thread{
        private final Process process;

        private Integer exit = null;
        private InputStream in = null;
        
        /**
         * Constructor - Defines the worker.
         *
         * @param process Instance that contains the process.
         */
        private Worker(Process process){
            super();
            
            this.process = process;
        }

        @Override
        public void run(){
            try{
                this.exit = this.process.waitFor();

                if (this.exit == 0)
                    this.in = this.process.getInputStream();
                else
                    this.in = this.process.getErrorStream();
            }
            catch(InterruptedException ignored){
            }
        }
    }
}