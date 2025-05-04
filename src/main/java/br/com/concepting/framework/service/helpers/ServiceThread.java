package br.com.concepting.framework.service.helpers;

import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.SystemSessionModel;
import br.com.concepting.framework.processors.ExpressionProcessorUtil;
import br.com.concepting.framework.security.constants.SecurityConstants;
import br.com.concepting.framework.security.model.LoginSessionModel;
import br.com.concepting.framework.service.interfaces.IService;
import br.com.concepting.framework.util.DateTimeUtil;
import br.com.concepting.framework.util.helpers.DateTime;
import br.com.concepting.framework.util.types.DateFieldType;

import java.text.ParseException;
import java.util.Calendar;

/**
 * Class that defines the service thread.
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
public class ServiceThread implements Runnable{
    private IService<? extends BaseModel> service = null;
    private DateTime nextExecution = null;
    private boolean executing = false;
    
    /**
     * Default constructor.
     *
     * @param service Instance that defines the service.
     */
    public ServiceThread(IService<? extends BaseModel> service){
        super();
        
        setService(service);
        setExecuting(false);
    }

    public IService<? extends BaseModel> getService() {
        return service;
    }

    protected void setService(IService<? extends BaseModel> service) {
        this.service = service;
    }

    /**
     * Indicates if the thread is executing.
     *
     * @return True/False.
     */
    public boolean isExecuting(){
        return getExecuting();
    }
    
    /**
     * Indicates if the thread is executing.
     *
     * @return True/False.
     */
    public boolean getExecuting(){
        return this.executing;
    }
    
    /**
     * Defines if the thread is executing.
     *
     * @param executing True/False.
     */
    protected void setExecuting(boolean executing){
        this.executing = executing;
    }

    @Override
    public void run(){
        try{
            LoginSessionModel loginSession = service.getLoginSession();
            SystemSessionModel systemSession = loginSession.getSystemSession();
            String domain = systemSession.getId();
        
            ExpressionProcessorUtil.setVariable(domain, SecurityConstants.LOGIN_SESSION_ATTRIBUTE_ID, loginSession);
        
            if(isExecuting())
                return;
        
            setExecuting(true);

            boolean active = (service != null && service.isActive());
            
            if(active){
                Integer pollingTime = service.getPollingTime();
                
                if(pollingTime != null && pollingTime >= 0){
                    Calendar calendar = Calendar.getInstance();
                    
                    calendar.set(Calendar.SECOND, 0);
                    calendar.set(Calendar.MILLISECOND, 0);
                    
                    DateTime now = new DateTime(calendar.getTimeInMillis());
                    
                    if(this.nextExecution == null){
                        try{
                            String startTime = service.getStartTime();
                            
                            if(startTime != null && !startTime.isEmpty()){
                                calendar.setTime(DateTimeUtil.parse(startTime, "HH:mm"));
                                calendar.set(Calendar.SECOND, 0);
                                calendar.set(Calendar.MILLISECOND, 0);
                                
                                this.nextExecution = new DateTime(calendar.getTimeInMillis());
                                
                                int diff = DateTimeUtil.diff(this.nextExecution, now, DateFieldType.MINUTES);
                                
                                if(diff < 0)
                                    this.nextExecution = DateTimeUtil.add(this.nextExecution, pollingTime, DateFieldType.MINUTES);
                            }
                            else
                                this.nextExecution = now;
                        }
                        catch(ParseException e){
                            throw new InternalErrorException(e);
                        }
                    }
                    
                    if(this.nextExecution == null || this.nextExecution.equals(now) || this.nextExecution.before(now)){
                        this.nextExecution = DateTimeUtil.add(now, pollingTime, DateFieldType.MINUTES);
                        
                        service.execute();
                    }
                }
            }
        }
        catch(InternalErrorException e){
            service.getAuditor().error(e);
        }
        finally{
            setExecuting(false);
        }
    }
}