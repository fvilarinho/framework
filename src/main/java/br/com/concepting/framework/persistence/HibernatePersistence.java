package br.com.concepting.framework.persistence;

import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.exceptions.ItemAlreadyExistsException;
import br.com.concepting.framework.model.exceptions.ItemNotFoundException;
import br.com.concepting.framework.model.helpers.ModelInfo;
import br.com.concepting.framework.model.util.ModelUtil;
import br.com.concepting.framework.persistence.helpers.Filter;
import br.com.concepting.framework.persistence.interfaces.IPersistence;
import br.com.concepting.framework.persistence.resources.PersistenceResources;
import br.com.concepting.framework.persistence.types.QueryType;
import br.com.concepting.framework.persistence.types.RelationJoinType;
import br.com.concepting.framework.persistence.util.HibernateQueryBuilder;
import br.com.concepting.framework.persistence.util.HibernateUtil;
import br.com.concepting.framework.util.PropertyUtil;
import br.com.concepting.framework.util.helpers.PropertyInfo;
import org.hibernate.*;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import javax.persistence.NoResultException;
import java.lang.InstantiationException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Class that defines the basic implementation of the persistence of data models
 * using Hibernate.
 *
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
 * @author fvilarinho
 * @since 1.0.0
 */
@SuppressWarnings("deprecation")
public abstract class HibernatePersistence<M extends BaseModel> extends BasePersistence<Session, Transaction, M>{
    /**
     * Constructor - Initializes the persistence.
     */
    public HibernatePersistence(){
        super();
    }
    
    /**
     * Constructor - Initializes the persistence.
     *
     * @param <D> Class that defines the persistence.
     * @param persistence Instance that contains the persistence.
     */
    public <D extends HibernatePersistence<? extends BaseModel>> HibernatePersistence(D persistence){
        super(persistence);
    }
    
    /**
     * Reconnects the data model to the persistence.
     *
     * @param model Instance that contains the data model.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    private void reattachModel(M model) throws InternalErrorException{
        if(model != null){
            Session connection = getConnection();
            
            if(connection != null)
                connection.buildLockRequest(LockOptions.NONE).lock(model);
        }
    }

    @Override
    public void begin() throws InternalErrorException{
        openConnection();
        
        Session connection = getConnection();
        
        if(connection != null){
            Transaction transaction = connection.getTransaction();
            
            if(transaction != null && transaction.getStatus() != TransactionStatus.ACTIVE){
                Integer timeout = getTimeout();
                
                if(timeout != null && timeout > 0)
                    transaction.setTimeout(timeout);
                
                try{
                    transaction.begin();
                    
                    setTransaction(transaction);
                }
                catch(HibernateException e){
                    throw new InternalErrorException(e);
                }
            }
        }
    }

    @Override
    public void commit() throws InternalErrorException{
        Transaction transaction = getTransaction();
        
        try{
            if(transaction != null)
                transaction.commit();
        }
        catch(Throwable ignored){
        }
        finally{
            if(transaction != null){
                setTransaction(null);
                closeConnection();
            }
        }
    }
    
    @Override
    public void rollback() throws InternalErrorException{
        Transaction transaction = getTransaction();
        
        try{
            if(transaction != null)
                transaction.rollback();
        }
        catch(Throwable ignored){
        }
        finally{
            if(transaction != null){
                setTransaction(null);
                closeConnection();
            }
        }
    }
    
    @Override
    protected void openConnection() throws InternalErrorException{
        super.openConnection();
        
        Session connection = getConnection();
        
        if(connection == null || !connection.isOpen()){
            PersistenceResources resources = getResources();
            
            connection = HibernateUtil.getSession(resources);
            
            setConnection(connection);
        }
    }
    
    @Override
    protected void closeConnection() throws InternalErrorException{
        Session connection = getConnection();
        
        try{
            if(connection != null)
                connection.clear();
        }
        catch(Throwable ignored){
        }
        
        try{
            if(connection != null)
                connection.close();
        }
        catch(Throwable ignored){
        }
        
        super.closeConnection();
    }
    
    @Override
    public void delete(M model) throws InternalErrorException{
        if(model == null)
            return;
        
        Session connection = getConnection();
        
        try{
            if(connection != null && connection.isOpen()){
                try{
                    connection.delete(model);
                }
                catch(ObjectNotFoundException | ObjectDeletedException | StaleStateException | ConstraintViolationException ignored){
                }
                
                connection.flush();
            }
        }
        catch(HibernateException e){
            throw new InternalErrorException(e);
        }
    }
    
    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public M find(M model) throws InternalErrorException, ItemNotFoundException{
        if(model == null)
            throw new ItemNotFoundException();
        
        try{
            Query query = HibernateQueryBuilder.build(QueryType.FIND, model, this);
            M queryResult = (M) query.uniqueResult();
            
            if(queryResult == null)
                throw new ItemNotFoundException();
            
            return queryResult;
        }
        catch(NoResultException | ObjectNotFoundException | NonUniqueResultException e){
            throw new ItemNotFoundException();
        }
        catch(HibernateException e){
            throw new InternalErrorException(e);
        }
    }

    @Override
    public Collection<M> list() throws InternalErrorException{
        return search(null);
    }
    
    @Override
    public Collection<M> search(M model) throws InternalErrorException{
        return filter(model, null);
    }

    @Override
    public Collection<M> filter(Filter filter) throws InternalErrorException{
        return filter(null, filter);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private Collection<M> filter(M model, Filter filter) throws InternalErrorException{
        Query query = HibernateQueryBuilder.build(QueryType.SEARCH, model, filter, this);
        List<M> modelList = (List<M>) query.getResultList();
        
        if(modelList != null && !modelList.isEmpty()){
            Map<String, RelationJoinType> relationJoins = (filter != null ? filter.getPropertiesRelationsJoins() : null);
            
            if(relationJoins != null && !relationJoins.isEmpty()){
                for(int cont = 0; cont < modelList.size(); cont++){
                    M item = modelList.get(cont);
                    
                    for(Entry<String, RelationJoinType> relationJoin: relationJoins.entrySet()){
                        item = loadReference(item, relationJoin.getKey());
                        
                        modelList.set(cont, item);
                    }
                }
            }
            
            try{
                modelList = ModelUtil.filterByPhonetic(model, modelList);
            }
            catch(IllegalArgumentException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException | ClassNotFoundException | NoSuchFieldException e){
                throw new InternalErrorException(e);
            }
        }
        
        return modelList;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public <R extends BaseModel> M loadReference(M model, String referencePropertyId) throws InternalErrorException{
        if(model == null || referencePropertyId == null || referencePropertyId.length() == 0)
            return model;
        
        try{
            reattachModel(model);
        }
        catch(Throwable ignored){
        }
        
        try{
            Class<? extends BaseModel> modelClass = model.getClass();
            ModelInfo modelInfo = ModelUtil.getInfo(modelClass);
            PropertyInfo referencePropertyInfo = modelInfo.getPropertyInfo(referencePropertyId);
            
            if(referencePropertyInfo.getRelationJoinType() == RelationJoinType.NONE){
                Class<R> referenceModelClass = (Class<R>) referencePropertyInfo.getClazz();
                Object referenceProperty = PropertyUtil.getValue(model, referencePropertyId);
                
                if(referencePropertyInfo.hasModel()){
                    Collection<R> modelList = (Collection<R>) referenceProperty;
                    
                    if(modelList != null && !modelList.isEmpty()){
                        Iterator<R> iterator = modelList.iterator();

                        if (iterator.hasNext()) {
                            do iterator.next();
                            while (iterator.hasNext());
                        }
                    }
                }
                else{
                    R referenceModel = (R) referenceProperty;
                    
                    if(referenceModel != null){
                        IPersistence<R> persistence = getPersistence(referenceModelClass);
                        
                        referenceModel = persistence.find(referenceModel);
                        
                        PropertyUtil.setValue(model, referencePropertyId, referenceModel);
                    }
                }
            }
            
            return model;
        }
        catch(ItemNotFoundException | IllegalArgumentException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException | ClassNotFoundException | NoSuchFieldException e){
            throw new InternalErrorException(e);
        }
    }

    @Override
    public void saveReference(M model, String referencePropertyId) throws InternalErrorException{
        if(model == null || referencePropertyId == null || referencePropertyId.length() == 0)
            return;
        
        try{
            Object referenceProperty = PropertyUtil.getProperty(model, referencePropertyId);
            
            try{
                reattachModel(model);
            }
            catch(Throwable ignored){
            }
            
            PropertyUtil.setProperty(model, referencePropertyId, referenceProperty);
            
            update(model);
        }
        catch(IllegalAccessException | InvocationTargetException | NoSuchMethodException e){
            throw new InternalErrorException(e);
        }
    }

    @Override
    public void deleteAll(Collection<M> modelList) throws InternalErrorException{
        if(modelList == null || modelList.size() == 0)
            return;
        
        Session connection = getConnection();
        
        try{
            if(connection != null && connection.isOpen()){
                Iterator<M> iterator = modelList.iterator();
                
                while(iterator.hasNext()){
                    try{
                        connection.delete(iterator.next());
                    }
                    catch(ObjectNotFoundException | ObjectDeletedException | StaleStateException | ConstraintViolationException ignored){
                    }
                }
                
                connection.flush();
            }
        }
        catch(HibernateException e){
            throw new InternalErrorException(e);
        }
    }

    @Override
    public M save(M model) throws ItemAlreadyExistsException, InternalErrorException{
        if(model == null)
            return model;
        
        Session connection = getConnection();
        
        try{
            if(connection != null && connection.isOpen()){
                ModelUtil.fillPhoneticProperties(model);
                
                try{
                    connection.saveOrUpdate(model);
                }
                catch(NonUniqueObjectException | ObjectNotFoundException | StaleStateException ignored){
                }
                
                connection.flush();
            }
        }
        catch(ConstraintViolationException e){
            if(e.getMessage().toLowerCase().contains("duplicate") || e.getCause().getMessage().toLowerCase().contains("duplicate"))
                throw new ItemAlreadyExistsException();
            
            throw new InternalErrorException(e);
        }
        catch(HibernateException | IllegalArgumentException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException | ClassNotFoundException | NoSuchFieldException e){
            throw new InternalErrorException(e);
        }
        
        return model;
    }

    @Override
    public M insert(M model) throws ItemAlreadyExistsException, InternalErrorException{
        if(model == null)
            return model;
        
        Session connection = getConnection();
        
        try{
            if(connection != null && connection.isOpen()){
                ModelUtil.fillPhoneticProperties(model);
                
                try{
                    connection.save(model);
                }
                catch(NonUniqueObjectException | ObjectNotFoundException | StaleStateException ignored){
                }
                
                connection.flush();
            }
        }
        catch(ConstraintViolationException e){
            if(e.getMessage().toLowerCase().contains("duplicate") || e.getCause().getMessage().toLowerCase().contains("duplicate"))
                throw new ItemAlreadyExistsException();
            
            throw new InternalErrorException(e);
        }
        catch(HibernateException | IllegalArgumentException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException | ClassNotFoundException | NoSuchFieldException e){
            throw new InternalErrorException(e);
        }
        
        return model;
    }

    @Override
    public void update(M model) throws InternalErrorException{
        if(model == null)
            return;
        
        Session connection = getConnection();
        
        try{
            if(connection != null && connection.isOpen()){
                ModelUtil.fillPhoneticProperties(model);
                
                try{
                    connection.merge(model);
                }
                catch(NonUniqueObjectException | StaleStateException | ObjectNotFoundException | ConstraintViolationException ignored){
                }
                
                connection.flush();
            }
        }
        catch(HibernateException | IllegalArgumentException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException | ClassNotFoundException | NoSuchFieldException e){
            throw new InternalErrorException(e);
        }
    }

    @Override
    public void saveAll(Collection<M> modelList) throws ItemAlreadyExistsException, InternalErrorException{
        if(modelList != null && !modelList.isEmpty()){
            try{
                Session connection = getConnection();
                
                if(connection != null && connection.isOpen()){

                    for (M item : modelList) {
                        ModelUtil.fillPhoneticProperties(item);

                        try {
                            connection.saveOrUpdate(item);
                        }
                        catch (NonUniqueObjectException | ObjectNotFoundException | StaleStateException ignored) {
                        }
                    }
                    
                    connection.flush();
                }
            }
            catch(ConstraintViolationException e){
                if(e.getMessage().toLowerCase().contains("duplicate") || e.getCause().getMessage().toLowerCase().contains("duplicate"))
                    throw new ItemAlreadyExistsException();
                
                throw new InternalErrorException(e);
            }
            catch(HibernateException | IllegalArgumentException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException | ClassNotFoundException | NoSuchFieldException e){
                throw new InternalErrorException(e);
            }
        }
    }

    @Override
    public void insertAll(Collection<M> modelList) throws ItemAlreadyExistsException, InternalErrorException{
        if(modelList != null && !modelList.isEmpty()){
            try{
                Session connection = getConnection();
                
                if(connection != null && connection.isOpen()){
                    Iterator<M> iterator = modelList.iterator();
                    
                    while(iterator.hasNext()){
                        M item = iterator.next();
                        
                        ModelUtil.fillPhoneticProperties(item);
                        
                        try{
                            connection.save(iterator.next());
                        }
                        catch(NonUniqueObjectException | ObjectNotFoundException | StaleStateException ignored){
                        }
                    }
                    
                    connection.flush();
                }
            }
            catch(ConstraintViolationException e){
                if(e.getMessage().toLowerCase().contains("duplicate") || e.getCause().getMessage().toLowerCase().contains("duplicate"))
                    throw new ItemAlreadyExistsException();
                
                throw new InternalErrorException(e);
            }
            catch(HibernateException | IllegalArgumentException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException | ClassNotFoundException | NoSuchFieldException e){
                throw new InternalErrorException(e);
            }
        }
    }

    @Override
    public void updateAll(Collection<M> modelList) throws InternalErrorException{
        if(modelList != null && !modelList.isEmpty()){
            try{
                Session connection = getConnection();
                
                if(connection != null && connection.isOpen()){
                    Iterator<M> iterator = modelList.iterator();
                    
                    while(iterator.hasNext()){
                        M item = iterator.next();
                        
                        ModelUtil.fillPhoneticProperties(item);
                        
                        try{
                            connection.merge(iterator.next());
                        }
                        catch(NonUniqueObjectException | StaleStateException | ObjectNotFoundException | ConstraintViolationException ignored){
                        }
                    }
                    
                    connection.flush();
                }
            }
            catch(HibernateException | IllegalArgumentException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException | ClassNotFoundException | NoSuchFieldException e){
                throw new InternalErrorException(e);
            }
        }
    }
}