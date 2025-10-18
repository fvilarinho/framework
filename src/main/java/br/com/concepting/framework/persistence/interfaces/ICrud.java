package br.com.concepting.framework.persistence.interfaces;

import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.exceptions.ItemAlreadyExistsException;
import br.com.concepting.framework.model.exceptions.ItemNotFoundException;
import br.com.concepting.framework.persistence.helpers.Filter;

import java.util.Collection;

/**
 * Interface that defines the basic implementation of the CRUD.
 *
 * @param <M> Class that defines the data model.
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
public interface ICrud<M extends BaseModel>{
    /**
     * Returns a list of all data models.
     *
     * @return List that contains the data models.
     * @throws InternalErrorException Occurs when was not possible to list.
     */
    Collection<M> list() throws InternalErrorException;
    
    /**
     * Returns a list of data models that satisfies the search criteria.
     *
     * @param model Instance that contains the data model that contains the search
     * criteria.
     * @return List that contains the data models.
     * @throws InternalErrorException Occurs when was not possible to search.
     */
    Collection<M> search(M model) throws InternalErrorException;
    
    /**
     * Returns a list of data models that satisfies the filter criteria.
     *
     * @param filter Instance that contains the customized search criteria.
     * @return List that contains the data models.
     * @throws InternalErrorException Occurs when was not possible to search
     * data models.
     */
    Collection<M> filter(Filter filter) throws InternalErrorException;

    /**
     * Finds a data model.
     *
     * @param model Instance that contains the data model that contains the find criteria.
     * @return Instance that contains the found data model.
     * @throws ItemNotFoundException Occurs when the data model was not found.
     * @throws InternalErrorException Occurs when was not possible to find the
     * data model.
     */
    M find(M model) throws ItemNotFoundException, InternalErrorException;
    
    /**
     * Delete a data model.
     *
     * @param model Instance that contains the data model.
     * @throws InternalErrorException Occurs when was not possible to delete the
     * data model.
     */
    void delete(M model) throws InternalErrorException;
    
    /**
     * Delete the list of data models.
     *
     * @param modelList List of data models.
     * @throws InternalErrorException Occurs when was not possible to delete the
     * list of data models.
     */
    void deleteAll(Collection<M> modelList) throws InternalErrorException;
    
    /**
     * Inserts/Updates a data model.
     *
     * @param model Instance that contains the data model.
     * @return List of the data models.
     * @throws ItemAlreadyExistsException Occurs when a data model already
     * exists.
     * @throws InternalErrorException Occurs when was not possible to save the
     * data model.
     */
    M save(M model) throws ItemAlreadyExistsException, InternalErrorException;
    
    /**
     * Inserts/Updates a list of data models.
     *
     * @param modelList List of the data models.
     * @throws ItemAlreadyExistsException Occurs when a data model already
     * exists.
     * @throws InternalErrorException Occurs when was not possible to save the
     * data models.
     */
    void saveAll(Collection<M> modelList) throws ItemAlreadyExistsException, InternalErrorException;
    
    /**
     * Inserts a list of data models.
     *
     * @param modelList List of the data models.
     * @throws ItemAlreadyExistsException Occurs when a data model already
     * exists.
     * @throws InternalErrorException Occurs when was not possible to insert the
     * data models.
     */
    void insertAll(Collection<M> modelList) throws ItemAlreadyExistsException, InternalErrorException;
    
    /**
     * Updates a list of data models.
     *
     * @param modelList List of the data models.
     * @throws InternalErrorException Occurs when was not possible to update the
     * data models.
     */
    void updateAll(Collection<M> modelList) throws InternalErrorException;
    
    /**
     * Inserts a data model.
     *
     * @param model Instance that contains the data model.
     * @return Instance that contains the data model.
     * @throws ItemAlreadyExistsException Occurs when a data model already
     * exists.
     * @throws InternalErrorException Occurs when was not possible to insert the
     * data model.
     */
    M insert(M model) throws ItemAlreadyExistsException, InternalErrorException;
    
    /**
     * Updates a data model.
     *
     * @param model Instance that contains the data model.
     * @throws InternalErrorException Occurs when was not possible to update the
     * data model.
     */
    void update(M model) throws InternalErrorException;
    
    /**
     * Loads the relationship.
     *
     * @param <R> Class that defines the relationship.
     * @param model Instance that contains the data model.
     * @param referencePropertyId String that contains the name of the
     * relationship.
     * @return Instance that contains the data model with the relationship loaded.
     * @throws InternalErrorException Occurs when was not possible to load the
     * relationship.
     */
    <R extends BaseModel> M loadReference(M model, String referencePropertyId) throws InternalErrorException;
    
    /**
     * Saves the relationship.
     *
     * @param model Instance that contains the data model.
     * @param referencePropertyId String that contains the name of the
     * relationship.
     * @throws InternalErrorException Occurs when was not possible to save the
     * relationship.
     */
    void saveReference(M model, String referencePropertyId) throws InternalErrorException;
}