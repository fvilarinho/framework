package br.com.innovativethinking.framework.persistence.interfaces;

import br.com.innovativethinking.framework.exceptions.InternalErrorException;
import br.com.innovativethinking.framework.model.BaseModel;
import br.com.innovativethinking.framework.persistence.resources.PersistenceResources;

/**
 * Interface that defines the basic implementation of the persistence of data
 * models.
 *
 * @author fvilarinho
 * @since 1.0.0
 * @param <M> Class that defines the data model of the persistence. 
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
public interface IPersistence<M extends BaseModel> extends ICrud<M>{
    /**
     * Begins the transaction.
     * 
     * @throws InternalErrorException Occurs when was not possible to begin the
     * transaction.
     */
    public void begin() throws InternalErrorException;

    /**
     * Confirms the operations of the transaction.
     * 
     * @throws InternalErrorException Occurs when was not possible to confirm
     * the operations.
     */
    public void commit() throws InternalErrorException;

    /**
     * Discards the operations of the transaction.
     * 
     * @throws InternalErrorException Occurs when was not possible to discard
     * the operations.
     */
    public void rollback() throws InternalErrorException;

    /**
     * Defines the instance that contains the persistence resources.
     * 
     * @param resources Instance that contains the persistence
     * resources.
     * @throws InternalErrorException Occurs when was not possible to execute
     * the operation.
     */
    public void setResources(PersistenceResources resources) throws InternalErrorException;

    /**
     * Returns the instance that contains the persistence resources.
     * 
     * @return Instance that contains the persistence resources.
     * @throws InternalErrorException Occurs when was not possible to execute
     * the operation.
     */
    public PersistenceResources getResources() throws InternalErrorException;

    /**
     * Defines the timeout of the persistence service.
     * 
     * @param timeout Numeric value that contains the timeout.
     * @throws InternalErrorException Occurs when was not possible to execute
     * the operation.
     */
    public void setTimeout(Integer timeout) throws InternalErrorException;

    /**
     * Returns the timeout of the persistence service.
     * 
     * @return Numeric value that contains the timeout.
     * @throws InternalErrorException Occurs when was not possible to execute
     * the operation.
     */
    public Integer getTimeout() throws InternalErrorException;
}