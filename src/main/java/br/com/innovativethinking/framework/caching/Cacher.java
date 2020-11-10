package br.com.innovativethinking.framework.caching;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import br.com.innovativethinking.framework.caching.constants.CacherConstants;
import br.com.innovativethinking.framework.constants.Constants;
import br.com.innovativethinking.framework.model.exceptions.ItemAlreadyExistsException;
import br.com.innovativethinking.framework.model.exceptions.ItemNotFoundException;
import br.com.innovativethinking.framework.util.DateTimeUtil;
import br.com.innovativethinking.framework.util.PropertyUtil;
import br.com.innovativethinking.framework.util.helpers.DateTime;
import br.com.innovativethinking.framework.util.types.DateFieldType;

/**
 * Class that implements the cache.
 * 
 * @author fvilarinho
 * @since 1.0.0
 * @param <O> Class that defines the cacheable object.
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
public class Cacher<O> implements Serializable{
	private static final long serialVersionUID = -441305081843827888L;

	private String                       id          = null;
	private Map<String, CachedObject<O>> history     = null;
	private Long                         timeout     = null;
	private DateFieldType                timeoutType = null;

	/**
	 * Constructor - Initialize the cache.
	 */
	private Cacher(){
		super();

		this.history = PropertyUtil.instantiate(Constants.DEFAULT_MAP_CLASS);
	}

	/**
	 * Constructor - Initialize the cache.
	 * 
	 * @param id String that contains the cache identifier.
	 */
	public Cacher(String id){
		this(id, CacherConstants.DEFAULT_TIMEOUT, CacherConstants.DEFAULT_TIMEOUT_TYPE);
	}

	/**
	 * Constructor - Initialize the cache.
	 * 
	 * @param id String that contains the identifier.
	 * @param timeout Numeric value containing the timeout.
	 */
	public Cacher(String id, Long timeout){
		this(id, timeout, CacherConstants.DEFAULT_TIMEOUT_TYPE);
	}

	/**
	 * Constructor - Initialize the cache.
	 * 
	 * @param id Strins that contains the identifier.
	 * @param timeout Numeric value containing the timeout.
	 * @param timeoutType Instance that contains the timeout unit
	 */
	public Cacher(String id, Long timeout, DateFieldType timeoutType){
		this();

		setId(id);
		setTimeout(timeout);
		setTimeoutType(timeoutType);
	}

	/**
	 * Returns the timeout unit.
	 *
	 * @return Instance that contains the timeout unit.
	 */
	public DateFieldType getTimeoutType(){
		return this.timeoutType;
	}

	/**
	 * Defines the timeout unit.
	 *
	 * @param timeoutType Instance that contains the timeout unit.
	 */
	protected void setTimeoutType(DateFieldType timeoutType){
		this.timeoutType = timeoutType;
	}

	/**
	 * Returns the identifier.
	 * 
	 * @return String that contains the identifier.
	 */
	public String getId(){
		return this.id;
	}

	/**
	 * Defines the identifier.
	 * 
	 * @param id String that contains the identifier.
	 */
	public void setId(String id){
		this.id = id;
	}

	/**
	 * Expires all cache content.
	 */
	public void expire(){
		if(this.history != null && this.history.size() > 0)
			this.history.clear();
	}

	/**
	 * Adds a new cacheable content.
	 * 
	 * @param object Instance that contains the content.
	 * @throws ItemAlreadyExistsException Occurs when the content is already
	 * stored in cache.
	 */
	public synchronized void add(CachedObject<O> object) throws ItemAlreadyExistsException{
		if(object != null && this.history != null){
			if(!contains(object)){
				object.setCacheDate(new DateTime());
				object.setLastAccess(null);

				this.history.put(object.getId(), object);
			}
			else
				throw new ItemAlreadyExistsException();
		}
	}

	/**
	 * Updates a content.
	 * 
	 * @param object Instance that contains the content.
	 * @throws ItemNotFoundException Occurs when the content isn't stored in
	 * cache.
	 */
	public synchronized void set(CachedObject<O> object) throws ItemNotFoundException{
		if(object != null){
			if(contains(object) && this.history != null && !this.history.isEmpty()){
				object.setCacheDate(new DateTime());
				object.setLastAccess(null);

				this.history.put(object.getId(), object);
			}
			else
				throw new ItemNotFoundException();
		}
	}

	/**
	 * Removes a content.
	 * 
	 * @param object Instance that contains the content.
	 * @throws ItemNotFoundException Occurs when the content isn't stored in
	 * cache.
	 */
	public synchronized void remove(CachedObject<O> object) throws ItemNotFoundException{
		if(object != null){
			if(contains(object) && this.history != null && this.history.size() > 0)
				this.history.remove(object.getId());
			else
				throw new ItemNotFoundException();
		}
	}

	/**
	 * Retrieves a content.
	 * 
	 * @param <C> Class that defines the content.
	 * @param id String that contains the identifier.
	 * @return Instance that contains the content.
	 * @throws ItemNotFoundException Occurs when the content isn't stored in
	 * cache.
	 */
	@SuppressWarnings("unchecked")
	public synchronized <C extends CachedObject<O>> C get(String id) throws ItemNotFoundException{
		Date timeoutDate = null;

		if(id != null && id.length() > 0){
			for(CachedObject<O> cachedObject : this.history.values()){
				if(id.equals(cachedObject.getId())){
					if(this.timeout != null && this.timeout > 0){
						timeoutDate = cachedObject.getLastAccess();

						if(timeoutDate == null)
							timeoutDate = cachedObject.getCacheDate();

						if(DateTimeUtil.diff(new DateTime(), timeoutDate, this.timeoutType) >= this.timeout){
							remove(cachedObject);

							throw new ItemNotFoundException();
						}
					}

					cachedObject.setLastAccess(new DateTime());

					return (C)cachedObject;
				}
			}
		}

		throw new ItemNotFoundException();
	}

	/**
	 * Verifies if the content is in cache.
	 * 
	 * @param object Instance that contains the content.
	 * @return True/False.
	 */
	public Boolean contains(CachedObject<O> object){
		return (this.history != null && this.history.size() > 0 ? this.history.containsKey(object.getId()) : false);
	}

	/**
	 * Returns the timeout.
	 * 
	 * @return Numeric value containing the timeout.
	 */
	public Long getTimeout(){
		return this.timeout;
	}

	/**
	 * Defines the timeout.
	 * 
	 * @param timeout Numeric value containing the timeout.
	 */
	protected void setTimeout(Long timeout){
		this.timeout = timeout;
	}

	/**
	 * Returns the number of contents in cache.
	 * 
	 * @return Numeric value containing the number of contents in cache.
	 */
	public Integer getSize(){
		return (this.history != null ? this.history.size() : null);
	}
	
	/**
	 * Clear cache.
	 */
	public void clear(){
		if(this.history != null)
			this.history.clear();
	}
}