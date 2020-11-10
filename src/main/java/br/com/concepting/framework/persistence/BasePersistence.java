package br.com.concepting.framework.persistence;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.ConstructorUtils;

import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.persistence.interfaces.IPersistence;
import br.com.concepting.framework.persistence.resources.PersistenceResources;
import br.com.concepting.framework.persistence.util.PersistenceUtil;

/**
 * Class that defines the basic implementation of the persistence of data
 * models.
 * 
 * @author fvilarinho
 * @param <CN> Class that defines the connection implementation.
 * @param <T> Class that defines the transaction implementation.
 * @param <M> Class that defines the data model of the persistence.
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
public abstract class BasePersistence<CN, T, M extends BaseModel> implements IPersistence<M>{
	private PersistenceResources resources   = null;
	private CN                   connection  = null;
	private T                    transaction = null;
	private Integer              timeout     = null;

	/**
	 * Constructor - Initializes the persistence.
	 */
	public BasePersistence(){
		super();
	}

	/**
	 * Constructor - Initializes the persistence.
	 * 
	 * @param <D> Class that defines the persistence.
	 * @param persistence Instance that contains the persistence.
	 */
	public <D extends BasePersistence<CN, T, ? extends BaseModel>> BasePersistence(D persistence){
		this();

		if(persistence != null){
			setConnection(persistence.getConnection());
			setResources(persistence.getResources());
			setTimeout(persistence.getTimeout());
		}

		setTransaction(null);
	}

	/**
	 * @see br.com.concepting.framework.persistence.interfaces.IPersistence#getTimeout()
	 */
	public Integer getTimeout(){
		return this.timeout;
	}

	/**
	 * @see br.com.concepting.framework.persistence.interfaces.IPersistence#setTimeout(java.lang.Integer)
	 */
	public void setTimeout(Integer timeout){
		this.timeout = timeout;
	}

	/**
	 * @see br.com.concepting.framework.persistence.interfaces.IPersistence#setResources(br.com.concepting.framework.persistence.resources.PersistenceResources)
	 */
	public void setResources(PersistenceResources resources){
		this.resources = resources;
	}

	/**
	 * @see br.com.concepting.framework.persistence.interfaces.IPersistence#getResources()
	 */
	public PersistenceResources getResources(){
		return this.resources;
	}

	/**
	 * Opens the persistence connection.
	 *
	 * @throws InternalErrorException Occurs when was not possible to open the connection.
	 */
	protected void openConnection() throws InternalErrorException{
	}

	/**
	 * Closes the persistence connection.
	 *
	 * @throws InternalErrorException Occurs when was not possible to open the connection.
	 */
	protected void closeConnection() throws InternalErrorException{
		setTransaction(null);
		setConnection(null);
		setTimeout(null);
	}

	/**
	 * Returns the instance of the persistence connection.
	 * 
	 * @return Instance that contains the persistence connection.
	 */
	public CN getConnection(){
		return this.connection;
	}

	/**
	 * Defines the instance of the persistence connection.
	 * 
	 * @param connection Instance that contains the persistence connection.
	 */
	protected void setConnection(CN connection){
		this.connection = connection;
	}

	/**
	 * Returns the instance of the persistence transaction.
	 * 
	 * @return Instance that contains the persistence transaction.
	 */
	protected T getTransaction(){
		return this.transaction;
	}

	/**
	 * Defines the instance of the persistence transaction.
	 * 
	 * @param transaction Instance that contains the persistence transaction.
	 */
	protected void setTransaction(T transaction){
		this.transaction = transaction;
	}

	/**
	 * Returns the instance of a persistence based on a data model.
	 *
	 * @param <D> Class that defines the persistence.
	 * @param modelClass Class that defines the data model.
	 * @return Instance that contains the persistence.
	 * @throws InternalErrorException Occurs when was not possible to
	 * instantiate the persistence.
	 */
	protected <D extends IPersistence<? extends BaseModel>> D getPersistence(Class<? extends BaseModel> modelClass) throws InternalErrorException{
		try{
			Class<D> persistenceClass = PersistenceUtil.getPersistenceClassByModel(modelClass);

			if(persistenceClass != null){
				D persistenceInstance = ConstructorUtils.invokeConstructor(persistenceClass, this);

				return persistenceInstance;
			}

			return null;
		}
		catch(NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException | ClassNotFoundException e){
			throw new InternalErrorException(e);
		}
	}
}