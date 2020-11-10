package br.com.concepting.framework.service;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import br.com.concepting.framework.audit.annotations.Auditable;
import br.com.concepting.framework.controller.SystemController;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.exceptions.ItemAlreadyExistsException;
import br.com.concepting.framework.model.exceptions.ItemNotFoundException;
import br.com.concepting.framework.model.helpers.ModelInfo;
import br.com.concepting.framework.model.util.ModelUtil;
import br.com.concepting.framework.util.PropertyUtil;
import br.com.concepting.framework.util.helpers.Filter;
import br.com.concepting.framework.util.helpers.PropertyInfo;
import br.com.concepting.framework.security.controller.SecurityController;
import br.com.concepting.framework.security.model.LoginSessionModel;
import br.com.concepting.framework.service.interfaces.IService;

/**
 * Class that defines the basic implementation for the services.
 * 
 * @author fvilarinho
 * @since 3.1.0
 * @param <M> Class that defines the data model of the service. 
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
public abstract class BaseWebService<M extends BaseModel> extends BaseService<M> implements IService<M>{
	@Context
	protected ServletContext context = null;

	@Context
	protected HttpServletRequest request = null;

	@Context
	protected HttpServletResponse response           = null;
	private SystemController      systemController   = null;
	private SecurityController    securityController = null;

	/**
	 * Returns the instance of the system controller.
	 * 
	 * @return Instance that contains the system controller.
	 */
	protected SystemController getSystemController(){
		if(this.systemController == null)
			this.systemController = new SystemController(this.request, this.response);

		return this.systemController;
	}

	/**
	 * Returns the instance of the security controller.
	 * 
	 * @return Instance that contains the security controller.
	 */
	protected SecurityController getSecurityController(){
		if(this.securityController == null)
			this.securityController = getSystemController().getSecurityController();

		return this.securityController;
	}
	
	/**
	 * @see br.com.concepting.framework.service.BaseService#getLoginSession()
	 */
	@Override
	public <L extends LoginSessionModel> L getLoginSession() throws InternalErrorException{
		return getSecurityController().getLoginSession();
	}

	/**
	 * @see br.com.concepting.framework.service.BaseService#list()
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("list")
	@Auditable
	@Override
	public Collection<M> list() throws InternalErrorException{
		IService<M> service = getService();

		return service.list();
	}

	/**
	 * @see br.com.concepting.framework.persistence.interfaces.ICrud#search(br.com.concepting.framework.model.BaseModel)
	 */
	@SuppressWarnings("unchecked")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("search")
	@Override
	public Collection<M> search(M model) throws InternalErrorException{
		Class<M> modelClass = (Class<M>)model.getClass();
		IService<M> service = getService(modelClass);

		return service.search(model);
	}

	/**
	 * @see br.com.concepting.framework.persistence.interfaces.ICrud#filter(br.com.concepting.framework.util.helpers.Filter)
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("filter")
	@Override
	public Collection<M> filter(Filter filter) throws InternalErrorException{
		IService<M> service = getService();

		return service.filter(filter);
	}

	/**
	 * @see br.com.concepting.framework.persistence.interfaces.ICrud#find(br.com.concepting.framework.model.BaseModel)
	 */
	@SuppressWarnings("unchecked")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("find")
	@Override
	public M find(M model) throws ItemNotFoundException, InternalErrorException{
		Class<M> modelClass = (Class<M>)model.getClass();
		IService<M> service = getService(modelClass);

		return service.find(model);
	}

	/**
	 * @see br.com.concepting.framework.persistence.interfaces.ICrud#delete(br.com.concepting.framework.model.BaseModel)
	 */
	@SuppressWarnings("unchecked")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("delete")
	@Auditable
	@Override
	public void delete(M model) throws InternalErrorException{
		Class<M> modelClass = (Class<M>)model.getClass();
		IService<M> service = getService(modelClass);

		service.delete(model);
	}

	/**
	 * @see br.com.concepting.framework.persistence.interfaces.ICrud#deleteAll(java.util.Collection)
	 */
	@SuppressWarnings("unchecked")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("deleteAll")
	@Auditable
	@Override
	public void deleteAll(Collection<M> modelList) throws InternalErrorException{
		if(modelList != null && !modelList.isEmpty()){
			M model = modelList.iterator().next();
			Class<M> modelClass = (Class<M>)model.getClass();
			IService<M> service = getService(modelClass);

			service.deleteAll(modelList);
		}
	}
	
	@SuppressWarnings("unchecked")
	private <O extends BaseModel> O mergeModel(O model) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, InternalErrorException, NoSuchFieldException, ClassNotFoundException {
		Class<O> modelClass = (Class<O>) model.getClass();
		ModelInfo modelInfo = ModelUtil.getInfo(modelClass);
		Collection<PropertyInfo> identitiesInfo = modelInfo.getIdentityPropertiesInfo();
		O modelBuffer = null;
		
		if(identitiesInfo != null && !identitiesInfo.isEmpty()){
			for(PropertyInfo identityInfo : identitiesInfo){
				Object propertyValue = PropertyUtil.getValue(model, identityInfo.getId());
				
				if(propertyValue != null){
					if(modelBuffer == null)
						modelBuffer = (O)modelClass.newInstance();

					PropertyUtil.setValue(modelBuffer, identityInfo.getId(), propertyValue);
				}
			}
			
			if(modelBuffer != null){
				try{
					IService<O> service = getService(modelClass);

					modelBuffer = service.find(modelBuffer);
				
					Collection<PropertyInfo> propertiesInfo = modelInfo.getPropertiesInfo();
					
					if(propertiesInfo != null && !propertiesInfo.isEmpty()){
						for(PropertyInfo propertyInfo : propertiesInfo){
							Object propertyValue = PropertyUtil.getValue(model, propertyInfo.getId());
							
							if(propertyValue == null){
								propertyValue = PropertyUtil.getValue(modelBuffer, propertyInfo.getId());
							
								PropertyUtil.setValue(model, propertyInfo.getId(), propertyValue);
							}
							else if(propertyInfo.isModel()){
								propertyValue = mergeModel((O)propertyValue);
								
								PropertyUtil.setValue(model, propertyInfo.getId(), propertyValue);
							}
						}
						
						return model;
					}
				}
				catch(ItemNotFoundException e){
				}
			}
		}
		
		return null;
	}
	
	/**
	 * @see br.com.concepting.framework.persistence.interfaces.ICrud#save(br.com.concepting.framework.model.BaseModel)
	 */
	@SuppressWarnings("unchecked")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("save")
	@Auditable
	@Override
	public M save(M model) throws ItemAlreadyExistsException, InternalErrorException{
		try{
			Class<M> modelClass = (Class<M>)model.getClass();
			IService<M> service = getService(modelClass);
			M modelBuffer = mergeModel(model);

			if(modelBuffer != null)
				return service.save(modelBuffer);

			return service.save(model);
		}
		catch(InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException | IllegalArgumentException | ClassNotFoundException | NoSuchFieldException e){
			throw new InternalErrorException(e);
		}
	}

	/**
	 * @see br.com.concepting.framework.persistence.interfaces.ICrud#saveAll(java.util.Collection)
	 */
	@SuppressWarnings("unchecked")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("saveAll")
	@Auditable
	@Override
	public void saveAll(Collection<M> modelList) throws ItemAlreadyExistsException, InternalErrorException{
		if(modelList != null && !modelList.isEmpty()){
			M model = modelList.iterator().next();
			Class<M> modelClass = (Class<M>)model.getClass();
			IService<M> service = getService(modelClass);

			service.saveAll(modelList);
		}
	}

	/**
	 * @see br.com.concepting.framework.persistence.interfaces.ICrud#insert(br.com.concepting.framework.model.BaseModel)
	 */
	@SuppressWarnings("unchecked")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("insert")
	@Auditable
	@Override
	public M insert(M model) throws ItemAlreadyExistsException, InternalErrorException{
		Class<M> modelClass = (Class<M>)model.getClass();
		IService<M> service = getService(modelClass);

		return service.insert(model);
	}

	/**
	 * @see br.com.concepting.framework.persistence.interfaces.ICrud#insertAll(java.util.Collection)
	 */
	@SuppressWarnings("unchecked")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("insertAll")
	@Auditable
	@Override
	public void insertAll(Collection<M> modelList) throws ItemAlreadyExistsException, InternalErrorException{
		if(modelList != null && !modelList.isEmpty()){
			M model = modelList.iterator().next();
			Class<M> modelClass = (Class<M>)model.getClass();
			IService<M> service = getService(modelClass);

			service.insertAll(modelList);
		}
	}

	/**
	 * @see br.com.concepting.framework.persistence.interfaces.ICrud#update(br.com.concepting.framework.model.BaseModel)
	 */
	@SuppressWarnings("unchecked")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("update")
	@Auditable
	@Override
	public void update(M model) throws InternalErrorException{
		try{
			Class<M> modelClass = (Class<M>)model.getClass();
			IService<M> service = getService(modelClass);
			M modelBuffer = mergeModel(model);

			if(modelBuffer != null)
				service.update(modelBuffer);
		}
		catch(InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | NoSuchFieldException | ClassNotFoundException e){
			throw new InternalErrorException(e);
		}
	}

	/**
	 * @see br.com.concepting.framework.persistence.interfaces.ICrud#updateAll(java.util.Collection)
	 */
	@SuppressWarnings("unchecked")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("updateAll")
	@Auditable
	@Override
	public void updateAll(Collection<M> modelList) throws InternalErrorException{
		if(modelList != null && !modelList.isEmpty()){
			M model = modelList.iterator().next();
			Class<M> modelClass = (Class<M>)model.getClass();
			IService<M> service = getService(modelClass);

			service.updateAll(modelList);
		}
	}

	/**
	 * @see br.com.concepting.framework.persistence.interfaces.ICrud#loadReference(br.com.concepting.framework.model.BaseModel, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("loadReference/{referencePropertyId}")
	@Auditable
	@Override
	public <R extends BaseModel> M loadReference(M model, @PathParam("referencePropertyId") String referencePropertyId) throws InternalErrorException{
		Class<M> modelClass = (Class<M>)model.getClass();
		IService<M> service = getService(modelClass);

		return service.loadReference(model, referencePropertyId);
	}
	
	/**
	 * @see br.com.concepting.framework.persistence.interfaces.ICrud#loadReference(br.com.concepting.framework.model.BaseModel, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("saveReference/{referencePropertyId}")
	@Auditable
	@Override
	public void saveReference(M model, @PathParam("referencePropertyId") String referencePropertyId) throws InternalErrorException{
		Class<M> modelClass = (Class<M>)model.getClass();
		IService<M> service = getService(modelClass);

		service.saveReference(model, referencePropertyId);
	}

	/**
	 * @see br.com.concepting.framework.service.BaseService#begin()
	 */
	@Override
	public void begin() throws InternalErrorException{
	}

	/**
	 * @see br.com.concepting.framework.service.BaseService#commit()
	 */
	@Override
	public void commit() throws InternalErrorException{
	}

	/**
	 * @see br.com.concepting.framework.service.BaseService#rollback()
	 */
	@Override
	public void rollback() throws InternalErrorException{
	}
}