package br.com.concepting.framework.service.interfaces;

import br.com.concepting.framework.audit.Auditor;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.persistence.interfaces.ICrud;
import br.com.concepting.framework.security.model.LoginSessionModel;

/**
 * Interface that defines the basic implementation of a service.
 *
 * @param <M> Class that defines the data model of the service.
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
 * @author fvilarinho
 * @since 1.0.0
 */
public interface IService<M extends BaseModel> extends ICrud<M>{
    /**
     * Returns the instance that contains the auditing.
     *
     * @return Instance that contains the auditing.
     */
    Auditor getAuditor();
    
    /**
     * Defines the instance that contains the auditing.
     *
     * @param auditor Class that defines the auditing.
     */
    void setAuditor(Auditor auditor);
    
    /**
     * Returns the current login session.
     *
     * @param <L> Class that defines the login session data model.
     * @return Instance that contains the login session.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    <L extends LoginSessionModel> L getLoginSession() throws InternalErrorException;
    
    /**
     * Defines the current login session.
     *
     * @param loginSession Instance that contains the login session.
     */
    void setLoginSession(LoginSessionModel loginSession);
    
    /**
     * Returns the current timeout of the service.
     *
     * @return Numeric value that contains the current timeout.
     */
    Integer getTimeout();
    
    /**
     * Defines the current timeout of the service.
     *
     * @param timeout Numeric value that contains the current timeout.
     */
    void setTimeout(Integer timeout);
    
    /**
     * Begins a transaction.
     *
     * @throws InternalErrorException Occurs when was not possible to begin the
     * transaction.
     */
    void begin() throws InternalErrorException;
    
    /**
     * Confirms all operations of the transaction.
     *
     * @throws InternalErrorException Occurs when was not possible to confirm
     * the operations.
     */
    void commit() throws InternalErrorException;
    
    /**
     * Discards all operations of the transaction.
     *
     * @throws InternalErrorException Occurs when was not possible to discard
     * the operations.
     */
    void rollback() throws InternalErrorException;
    
    /**
     * Executes the service main method.
     *
     * @throws InternalErrorException Occurs when was not possible to execute
     * the operation.
     */
    void execute() throws InternalErrorException;
    
    /**
     * Identifies if the service is active.
     *
     * @return True/False.
     * @throws InternalErrorException Occurs when was not possible to execute
     * the operation.
     */
    boolean isActive() throws InternalErrorException;
    
    /**
     * Returns the polling time of the service.
     *
     * @return Numeric value that contains the polling time (if the service is defined
     * as daemon and recurrent).
     * @throws InternalErrorException Occurs when was not possible to execute
     * the operation.
     */
    Integer getPollingTime() throws InternalErrorException;
    
    /**
     * Returns the start time of the service.
     *
     * @return String that contains the start time of the service.
     * @throws InternalErrorException Occurs when was not possible to execute
     * the operation.
     */
    String getStartTime() throws InternalErrorException;
}