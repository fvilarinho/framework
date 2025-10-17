package br.com.concepting.framework.util;

import br.com.concepting.framework.audit.Auditor;
import br.com.concepting.framework.audit.annotations.Auditable;
import br.com.concepting.framework.exceptions.ExpectedException;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.security.exceptions.PermissionDeniedException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Class responsible to intercept an instance methods execution.
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
public class Interceptor{
    private Object interceptableInstance = null;
    private Class<?> interceptableInterfaceClass = null;
    private Method interceptableMethod = null;
    private String[] interceptableMethodArgumentsNames = null;
    private Object[] interceptableMethodArgumentsValues = null;
    private Auditor auditor = null;
    
    /**
     * Returns the instance that contains the interceptable class.
     *
     * @param <I> Class that defines the interceptable instance.
     * @return Instance that contains the interceptable class.
     */
    @SuppressWarnings("unchecked")
    protected <I> I getInterceptableInstance(){
        return (I) this.interceptableInstance;
    }
    
    /**
     * Defines the instance that contains the interceptable class.
     *
     * @param interceptableInstance Instance that contains the interceptable class.
     */
    protected void setInterceptableInstance(Object interceptableInstance){
        this.interceptableInstance = interceptableInstance;
    }
    
    /**
     * Returns the interface of the interceptable class.
     *
     * @return Interface of the interceptable class.
     */
    protected Class<?> getInterceptableInterfaceClass(){
        return this.interceptableInterfaceClass;
    }
    
    /**
     * Defines the interface of the interceptable class.
     *
     * @param interceptableInterfaceClass Interface of the interceptable class.
     */
    protected void setInterceptableInterfaceClass(Class<?> interceptableInterfaceClass){
        this.interceptableInterfaceClass = interceptableInterfaceClass;
    }
    
    /**
     * Returns the instance that contains the interceptable method.
     *
     * @return Instance that contains the interceptable method.
     */
    protected Method getInterceptableMethod(){
        return this.interceptableMethod;
    }
    
    /**
     * Defines the instance that contains the interceptable method.
     *
     * @param interceptableMethod Instance that contains the interceptable
     * method.
     */
    protected void setInterceptableMethod(Method interceptableMethod){
        this.interceptableMethod = interceptableMethod;
    }
    
    /**
     * Returns the argument names of the interceptable method.
     *
     * @return Array that contains the argument names.
     */
    protected String[] getInterceptableMethodArgumentsNames(){
        return this.interceptableMethodArgumentsNames;
    }
    
    /**
     * Defines the argument names of the interceptable method.
     *
     * @param interceptableMethodArgumentsNames Array that contains the argument name.
     */
    protected void setInterceptableMethodArgumentsNames(String[] interceptableMethodArgumentsNames){
        this.interceptableMethodArgumentsNames = interceptableMethodArgumentsNames;
    }
    
    /**
     * Returns the argument values of the interceptable method.
     *
     * @return Array that contains the argument values.
     */
    protected Object[] getInterceptableMethodArgumentsValues(){
        return this.interceptableMethodArgumentsValues;
    }
    
    /**
     * Defines the argument values of the interceptable method.
     *
     * @param interceptableMethodArgumentsValues Array that contains the argument values.
     */
    protected void setInterceptableMethodArgumentsValues(Object[] interceptableMethodArgumentsValues){
        this.interceptableMethodArgumentsValues = interceptableMethodArgumentsValues;
    }
    
    /**
     * Returns the instance that contains the auditing.
     *
     * @return Instance that contains the auditing.
     * @throws InternalErrorException Occurs when was not possible to instantiate
     * the data model.
     */
    protected Auditor getAuditor() throws InternalErrorException{
        Class<?>[] interceptableMethodArgumentsTypes = this.interceptableMethod.getParameterTypes();
        Class<?> interceptableInstanceClass = this.interceptableInstance.getClass();
        
        try{
            this.interceptableMethod = interceptableInstanceClass.getMethod(this.interceptableMethod.getName(), interceptableMethodArgumentsTypes);
        }
        catch(NoSuchMethodException ignored){
        }
        
        Auditable auditableAnnotation = this.interceptableMethod.getAnnotation(Auditable.class);
        
        if(auditableAnnotation != null){
            if(this.auditor == null)
                this.auditor = new Auditor(this.interceptableInterfaceClass, this.interceptableMethod, this.interceptableMethodArgumentsValues);
            else{
                this.auditor.setEntity(this.interceptableInterfaceClass);
                this.auditor.setBusiness(this.interceptableMethod, this.interceptableMethodArgumentsValues);
            }
        }
        else
            this.auditor = null;
        
        return this.auditor;
    }
    
    /**
     * Pre-execution of the interceptable method when an exception occurred.
     *
     * @throws ExpectedException Occurs when an expected exception was caught.
     * @throws InternalErrorException Occurs when an internal error exception was caught.
     */
    public void before() throws ExpectedException, InternalErrorException{
        Auditor auditor = getAuditor();
        
        if(auditor != null)
            auditor.start();
    }
    
    /**
     * Post-execution of the interceptable method.
     *
     * @throws ExpectedException Occurs when an expected exception was caught.
     * @throws InternalErrorException Occurs when an internal error exception was caught.
     */
    public void after() throws ExpectedException, InternalErrorException{
        Auditor auditor = getAuditor();
        
        if(auditor != null)
            auditor.end();
    }
    
    /**
     * Post-execution of the interceptable method when an exception occurred.
     *
     * @param e Instance that contains the caught exception.
     * @throws ExpectedException Occurs when an expected exception was caught.
     * @throws InternalErrorException Occurs when an internal error exception was caught.
     */
    public void beforeThrow(Throwable e) throws ExpectedException, InternalErrorException{
        Auditor auditor = getAuditor();
        
        if(auditor != null)
            auditor.end(e);
    }
    
    /**
     * Executes the interceptable method.
     *
     * @param <O> Class that defines the execution result.
     * @return Instance that contains the execution result.
     * @throws ExpectedException Occurs when an expected exception was caught.
     * @throws InternalErrorException Occurs when an internal error exception was caught.
     */
    @SuppressWarnings("unchecked")
    public <O> O execute() throws ExpectedException, InternalErrorException {
        try{
            return (O) this.interceptableMethod.invoke(this.interceptableInstance, this.interceptableMethodArgumentsValues);
        }
        catch (InvocationTargetException e) {
            Throwable exception = ExceptionUtil.getCause(e);

            if(ExceptionUtil.isInternalErrorException(exception))
                throw (InternalErrorException)exception;
            else if(ExceptionUtil.isExpectedException(exception))
                throw (ExpectedException)exception;
            else
                throw new InternalErrorException(e);
        }
        catch(IllegalAccessException e){
            throw new PermissionDeniedException(e);
        }
        catch(IllegalArgumentException e){
            throw new InternalErrorException(e);
        }
    }
}