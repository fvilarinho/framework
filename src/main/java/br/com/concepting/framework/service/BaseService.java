package br.com.concepting.framework.service;

import br.com.concepting.framework.audit.Auditor;
import br.com.concepting.framework.audit.annotations.Auditable;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.exceptions.ItemAlreadyExistsException;
import br.com.concepting.framework.model.exceptions.ItemNotFoundException;
import br.com.concepting.framework.model.helpers.ModelInfo;
import br.com.concepting.framework.model.util.ModelUtil;
import br.com.concepting.framework.persistence.helpers.Filter;
import br.com.concepting.framework.persistence.interfaces.IPersistence;
import br.com.concepting.framework.persistence.resources.PersistenceResources;
import br.com.concepting.framework.persistence.resources.PersistenceResourcesLoader;
import br.com.concepting.framework.persistence.util.PersistenceUtil;
import br.com.concepting.framework.security.model.LoginSessionModel;
import br.com.concepting.framework.service.annotations.Service;
import br.com.concepting.framework.service.annotations.Transaction;
import br.com.concepting.framework.service.interfaces.IService;
import br.com.concepting.framework.service.util.ServiceUtil;
import org.apache.commons.beanutils.ConstructorUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

/**
 * Class that defines the basic implementation for the services.
 *
 * @param <M> Class that defines the data model of the service.
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
@Auditable
public abstract class BaseService<M extends BaseModel> implements IService<M>{
    private LoginSessionModel loginSession = null;
    private Auditor auditor = null;
    private Integer timeout = null;
    private IPersistence<? extends BaseModel> currentPersistence = null;
    
    @Override
    public Auditor getAuditor(){
        return this.auditor;
    }
    
    @Override
    public void setAuditor(Auditor auditor){
        this.auditor = auditor;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public <L extends LoginSessionModel> L getLoginSession() throws InternalErrorException{
        return (L) this.loginSession;
    }
    
    @Override
    public void setLoginSession(LoginSessionModel loginSession){
        this.loginSession = loginSession;
    }
    
    @Override
    public Integer getTimeout(){
        return this.timeout;
    }
    
    @Override
    public void setTimeout(Integer timeout){
        this.timeout = timeout;
    }
    
    /**
     * Returns the service implementation of the default data model.
     *
     * @param <S> Class that defines the service implementation.
     * @return Instance that contains the service implementation of the default data model.
     * @throws InternalErrorException Occurs when was not possible to
     * instantiate the service implementation.
     */
    protected <S extends IService<M>> S getService() throws InternalErrorException{
        return getService(null);
    }
    
    /**
     * Returns the service implementation of a specific data model.
     *
     * @param <S> Class that defines the service implementation.
     * @param modelClass Class that defines the data model.
     * @return Instance that contains the service implementation of the data model.
     * @throws InternalErrorException Occurs when was not possible to
     * instantiate the service implementation.
     */
    protected <S extends IService<? extends BaseModel>> S getService(Class<? extends BaseModel> modelClass) throws InternalErrorException{
        commit();
        
        if(modelClass == null){
            try{
                modelClass = ServiceUtil.getModelClassByService(getClass());
            }
            catch(ClassNotFoundException e){
                throw new InternalErrorException(e);
            }
        }
        
        LoginSessionModel loginSession = getLoginSession();
        
        return ServiceUtil.getByModelClass(modelClass, loginSession);
    }
    
    @Override
    public void begin() throws InternalErrorException{
    }
    
    @Override
    public void commit() throws InternalErrorException{
        if(this.currentPersistence != null)
            this.currentPersistence.commit();
        
        this.currentPersistence = null;
        this.timeout = null;
    }
    
    @Override
    public void rollback() throws InternalErrorException{
        if(this.currentPersistence != null)
            this.currentPersistence.rollback();
        
        this.currentPersistence = null;
        this.timeout = null;
    }
    
    /**
     * Returns the instance of the persistence implementation.
     *
     * @param <P> Class of the persistence implementation.
     * @param modelClass Class that defines the data model.
     * @return Instance that contains the persistence implementation.
     * @throws InternalErrorException Occurs when was not possible to execute
     * the operation.
     */
    @SuppressWarnings("unchecked")
    protected <P extends IPersistence<? extends BaseModel>> P getPersistence(Class<? extends BaseModel> modelClass) throws InternalErrorException{
        P persistenceInstance = null;
        
        if(modelClass != null){
            try{
                ModelInfo modelInfo = ModelUtil.getInfo(modelClass);
                Class<IPersistence<M>> persistenceClass = PersistenceUtil.getPersistenceClassByModel(modelInfo.getClazz());
                PersistenceResources resources = (this.currentPersistence != null ? this.currentPersistence.getResources() : null);
                
                if(resources == null){
                    String persistenceResourcesId = modelInfo.getPersistenceResourcesId();
                    PersistenceResourcesLoader loader = new PersistenceResourcesLoader();
                    
                    resources = loader.get(persistenceResourcesId);
                }
                
                if(this.currentPersistence == null){
                    persistenceInstance = (P) ConstructorUtils.invokeConstructor(persistenceClass, null);
                    persistenceInstance.setResources(resources);
                    
                    if(this.timeout != null && this.timeout > 0)
                        persistenceInstance.setTimeout(this.timeout);
                    
                    persistenceInstance.begin();
                    
                    this.currentPersistence = persistenceInstance;
                }
                else{
                    this.currentPersistence.setResources(resources);
                    this.currentPersistence.begin();
                    
                    persistenceInstance = (P) ConstructorUtils.invokeConstructor(persistenceClass, this.currentPersistence);
                }
            }
            catch(ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException | IllegalArgumentException | NoSuchFieldException e){
                throw new InternalErrorException(e);
            }
        }
        
        return persistenceInstance;
    }
    
    /**
     * Returns the instance of the persistence implementation.
     *
     * @param <P> Class of the persistence implementation.
     * @return Instance that contains the persistence implementation.
     * @throws InternalErrorException Occurs when was not possible to execute
     * the operation.
     */
    protected <P extends IPersistence<M>> P getPersistence() throws InternalErrorException{
        try{
            Class<M> modelClass = ServiceUtil.getModelClassByService(getClass());
            
            return getPersistence(modelClass);
        }
        catch(ClassNotFoundException e){
            throw new InternalErrorException(e);
        }
    }
    
    @Override
    public Collection<M> list() throws InternalErrorException{
        IPersistence<M> persistence = getPersistence();
        
        return persistence.list();
    }
    
    @Override
    public Collection<M> search(M model) throws InternalErrorException{
        IPersistence<M> persistence = getPersistence();
        
        return persistence.search(model);
    }
    
    @Override
    public Collection<M> filter(Filter filter) throws InternalErrorException{
        return filter(null, filter);
    }
    
    @Override
    public Collection<M> filter(M model, Filter filter) throws InternalErrorException{
        IPersistence<M> persistence = getPersistence();
        
        return persistence.filter(model, filter);
    }
    
    @Override
    public M find(M model) throws ItemNotFoundException, InternalErrorException{
        IPersistence<M> persistence = getPersistence();
        
        return persistence.find(model);
    }
    
    @Auditable
    @Transaction
    @Override
    public void delete(M model) throws InternalErrorException{
        IPersistence<M> persistence = getPersistence();
        
        persistence.delete(model);
    }
    
    @Auditable
    @Transaction
    @Override
    public void deleteAll(Collection<M> modelList) throws InternalErrorException{
        if(modelList != null && !modelList.isEmpty()){
            for(M item: modelList)
                delete(item);
        }
    }
    
    @Auditable
    @Transaction
    @Override
    public M save(M model) throws ItemAlreadyExistsException, InternalErrorException{
        IPersistence<M> persistence = getPersistence();
        
        return persistence.save(model);
    }
    
    @Auditable
    @Transaction
    @Override
    public void saveAll(Collection<M> modelList) throws ItemAlreadyExistsException, InternalErrorException{
        if(modelList != null && !modelList.isEmpty()){
            IPersistence<M> persistence = getPersistence();
            
            for(M item: modelList)
                persistence.save(item);
        }
    }
    
    @Auditable
    @Transaction
    @Override
    public M insert(M model) throws ItemAlreadyExistsException, InternalErrorException{
        IPersistence<M> persistence = getPersistence();
        
        return persistence.insert(model);
    }
    
    @Auditable
    @Transaction
    @Override
    public void insertAll(Collection<M> modelList) throws ItemAlreadyExistsException, InternalErrorException{
        if(modelList != null && !modelList.isEmpty()){
            IPersistence<M> persistence = getPersistence();
            
            for(M item: modelList)
                persistence.insert(item);
        }
    }
    
    @Auditable
    @Transaction
    @Override
    public void update(M model) throws InternalErrorException{
        IPersistence<M> persistence = getPersistence();
        
        persistence.update(model);
    }
    
    @Auditable
    @Transaction
    @Override
    public void updateAll(Collection<M> modelList) throws InternalErrorException{
        if(modelList != null && !modelList.isEmpty()){
            IPersistence<M> persistence = getPersistence();
            
            for(M item: modelList)
                persistence.update(item);
        }
    }
    
    @Transaction
    @Override
    public <R extends BaseModel> M loadReference(M model, String referencePropertyId) throws InternalErrorException{
        IPersistence<M> persistence = getPersistence();
        
        return persistence.loadReference(model, referencePropertyId);
    }
    
    @Auditable
    @Transaction
    @Override
    public void execute() throws InternalErrorException{
    }

    @Override
    public boolean isActive() throws InternalErrorException{
        return true;
    }
    
    @Override
    public Integer getPollingTime() throws InternalErrorException{
        Service serviceAnnotation = getClass().getAnnotation(Service.class);
        
        if(serviceAnnotation != null && serviceAnnotation.isDaemon() && serviceAnnotation.isRecurrent())
            return serviceAnnotation.pollingTime();
        
        return null;
    }
    
    @Override
    public String getStartTime() throws InternalErrorException{
        Service serviceAnnotation = getClass().getAnnotation(Service.class);
        
        if(serviceAnnotation != null && serviceAnnotation.isDaemon())
            return serviceAnnotation.startTime();
        
        return null;
    }
    
    @Override
    public void saveReference(M model, String referenceId) throws InternalErrorException{
        IPersistence<M> persistence = getPersistence();
        
        persistence.saveReference(model, referenceId);
    }
}