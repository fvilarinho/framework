package br.com.concepting.framework.service.controller;

import br.com.concepting.framework.audit.Auditor;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.security.model.LoginSessionModel;
import br.com.concepting.framework.service.annotations.Transaction;
import br.com.concepting.framework.service.interfaces.IService;
import br.com.concepting.framework.util.ExceptionUtil;
import br.com.concepting.framework.util.Interceptor;

import java.lang.reflect.Method;

/**
 * Class responsible to intercept the service implementation methods execution.
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
public class ServiceInterceptor extends Interceptor{
    @Override
    public void before() throws InternalErrorException{
        IService<? extends BaseModel> service = getInterceptableInstance();
        LoginSessionModel loginSession = service.getLoginSession();
        Auditor auditor = getAuditor();
        
        if(auditor != null)
            auditor.setLoginSession(loginSession);
        
        super.before();
        
        Method interceptableMethod = getInterceptableMethod();
        Transaction transactionAnnotation = interceptableMethod.getAnnotation(Transaction.class);
        
        if(transactionAnnotation != null && transactionAnnotation.timeout() > 0)
            service.setTimeout(transactionAnnotation.timeout());
        
        service.setAuditor(getAuditor());
        service.begin();
    }

    @Override
    public void after() throws InternalErrorException{
        try{
            IService<? extends BaseModel> service = getInterceptableInstance();
            
            service.commit();
        }
        finally{
            super.after();
        }
    }

    @Override
    public void beforeThrow(Throwable e) throws InternalErrorException{
        try{
            IService<? extends BaseModel> service = getInterceptableInstance();
            Transaction transactionAnnotation = getInterceptableMethod().getAnnotation(Transaction.class);
            
            if(transactionAnnotation != null){
                Class<? extends Throwable> exception = e.getClass();
                Class<? extends Throwable>[] rollbackFor = transactionAnnotation.rollbackFor();
                
                for(Class<? extends Throwable> item: rollbackFor){
                    if(ExceptionUtil.belongsTo(exception, item)){
                        service.rollback();
                        
                        return;
                    }
                }
                
                service.commit();
            }
            else
                service.rollback();
        }
        finally{
            super.beforeThrow(e);
        }
    }
}