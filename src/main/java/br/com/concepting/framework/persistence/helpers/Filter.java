package br.com.concepting.framework.persistence.helpers;

import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.model.types.ConditionOperationType;
import br.com.concepting.framework.model.types.ConditionType;
import br.com.concepting.framework.persistence.constants.PersistenceConstants;
import br.com.concepting.framework.persistence.types.RelationJoinType;
import br.com.concepting.framework.util.PropertyUtil;
import br.com.concepting.framework.util.types.FormulaType;
import br.com.concepting.framework.util.types.SortOrderType;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * Class responsible to store the criteria that will be used to customize the
 * search queries of data models.
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
 * along with this program.  If not, see <a href="https://www.gnu.org/licenses"></a>.</pre>
 */
public class Filter implements Serializable{
    @Serial
    private static final long serialVersionUID = -4835660120485569201L;
    
    private Collection<String> returnProperties = null;
    private Map<String, FormulaType> propertiesFormulas = null;
    private Map<String, String> propertiesAliases = null;
    private Map<String, RelationJoinType> propertiesRelationsJoins = null;
    private Map<String, Collection<ConditionType>> propertiesConditions = null;
    private Map<String, ConditionOperationType> propertiesConditionsOperations = null;
    private Map<String, Collection<Object>> propertiesValues = null;
    private Map<String, SortOrderType> propertiesSortOrders = null;
    private Collection<String> groupByProperties = null;
    private int maximumResults = PersistenceConstants.DEFAULT_MAXIMUM_RESULTS;
    
    /**
     * Returns the group by properties.
     *
     * @return List that contains the group by properties.
     */
    public Collection<String> getGroupByProperties(){
        return groupByProperties;
    }
    
    /**
     * Defines the group by properties.
     *
     * @param groupByProperties List that contains the group by properties.
     */
    public void setGroupByProperties(Collection<String> groupByProperties){
        this.groupByProperties = groupByProperties;
    }
    
    /**
     * Adds a group by property.
     *
     * @param propertyId String that contains the property identifier.
     */
    public void addGroupByProperty(String propertyId){
        if(this.groupByProperties == null)
            this.groupByProperties = PropertyUtil.instantiate(Constants.DEFAULT_LIST_CLASS);
        
        if(this.groupByProperties != null && !this.groupByProperties.contains(propertyId))
            this.groupByProperties.add(propertyId);
    }
    
    /**
     * Returns all properties' aliases.
     *
     * @return Map that contains all properties' aliases.
     */
    public Map<String, String> getPropertiesAliases(){
        return this.propertiesAliases;
    }
    
    /**
     * Defines all properties' aliases.
     *
     * @param propertiesAliases Map that contains all properties' aliases.
     */
    public void setPropertiesAliases(Map<String, String> propertiesAliases){
        this.propertiesAliases = propertiesAliases;
    }
    
    /**
     * Adds a property alias.
     *
     * @param propertyId String that contains the property identifier.
     * @param propertyAlias String that contains the property alias.
     */
    public void addPropertyAlias(String propertyId, String propertyAlias){
        if(propertiesAliases == null)
            propertiesAliases = PropertyUtil.instantiate(Constants.DEFAULT_MAP_CLASS);

        if(propertiesAliases != null)
            propertiesAliases.put(propertyId, propertyAlias);
    }
    
    /**
     * Returns all properties' formulas.
     *
     * @return Map that contains all properties' formulas.
     */
    public Map<String, FormulaType> getPropertiesFormulas(){
        return this.propertiesFormulas;
    }
    
    /**
     * Returns all properties' formulas.
     *
     * @param propertiesFormulas Map that contains all properties' formulas.
     */
    public void setPropertiesFormulas(Map<String, FormulaType> propertiesFormulas){
        this.propertiesFormulas = propertiesFormulas;
    }
    
    /**
     * Returns the relations joins mapping.
     *
     * @return Instance that contains the relations joins mapping.
     */
    public Map<String, RelationJoinType> getPropertiesRelationsJoins(){
        return this.propertiesRelationsJoins;
    }
    
    /**
     * Defines the relations joins mapping.
     *
     * @param propertiesRelationsJoins Instance that contains the relations joins mapping.
     */
    public void setPropertiesRelationsJoins(Map<String, RelationJoinType> propertiesRelationsJoins){
        this.propertiesRelationsJoins = propertiesRelationsJoins;
    }
    
    /**
     * Adds a relation join to the mapping.
     *
     * @param id String that contains the identifier of the relation.
     * @param relationJoinType Instance that defines the join type.
     */
    public void addPropertyRelationJoin(String id, RelationJoinType relationJoinType){
        if(this.propertiesRelationsJoins == null)
            this.propertiesRelationsJoins = PropertyUtil.instantiate(Constants.DEFAULT_MAP_CLASS);

        if(this.propertiesRelationsJoins != null)
            this.propertiesRelationsJoins.put(id, relationJoinType);
    }
    
    /**
     * Adds a property formula
     *
     * @param propertyId String that contains the property identifier.
     * @param formulaType Instance that contains the formula.
     */
    public void addPropertyFormula(String propertyId, FormulaType formulaType){
        if(this.propertiesFormulas == null)
            this.propertiesFormulas = PropertyUtil.instantiate(Constants.DEFAULT_MAP_CLASS);

        if(propertiesFormulas != null)
            this.propertiesFormulas.put(propertyId, formulaType);
    }
    
    /**
     * Returns the maximum number of data models that should be returned.
     *
     * @return Numeric value that contains the maximum number.
     */
    public int getMaximumResults(){
        return this.maximumResults;
    }
    
    /**
     * Defines the maximum number of data models that should be returned.
     *
     * @param maximumResults Numeric value that contains the maximum number.
     */
    public void setMaximumResults(int maximumResults){
        this.maximumResults = maximumResults;
    }
    
    /**
     * Returns the list of properties that should return after the search query execution.
     *
     * @return List that contains the identifiers of the properties.
     */
    public Collection<String> getReturnProperties(){
        return this.returnProperties;
    }
    
    /**
     * Defines the list of properties that should return after the search query execution.
     *
     * @param returnProperties List that contains the identifiers of the
     * properties.
     */
    public void setReturnProperties(Collection<String> returnProperties){
        this.returnProperties = returnProperties;
    }
    
    /**
     * Adds a return property.
     *
     * @param id String that contains the identifier of the property.
     */
    public void addReturnProperty(String id){
        if(id != null && !id.isEmpty()){
            if(this.returnProperties == null)
                this.returnProperties = PropertyUtil.instantiate(Constants.DEFAULT_LIST_CLASS);

            if(this.returnProperties != null)
                this.returnProperties.add(id);
        }
    }
    
    /**
     * Returns the mapping of the properties' conditions.
     *
     * @return Instance that contains the mapping of the properties' conditions.
     */
    public Map<String, Collection<ConditionType>> getPropertiesConditions(){
        return this.propertiesConditions;
    }
    
    /**
     * Defines the mapping of the properties' conditions.
     *
     * @param conditions Instance that contains the mapping of the properties' conditions.
     */
    public void setPropertiesConditions(Map<String, Collection<ConditionType>> conditions){
        this.propertiesConditions = conditions;
    }
    
    /**
     * Adds a property condition.
     *
     * @param id String that contains the identifier of the property.
     * @param condition Instance that contains the condition.
     */
    public void addPropertyCondition(String id, ConditionType condition){
        if(id != null && !id.isEmpty() && condition != null){
            if(this.propertiesConditions == null)
                this.propertiesConditions = PropertyUtil.instantiate(Constants.DEFAULT_MAP_CLASS);

            if(this.propertiesConditions != null) {
                Collection<ConditionType> conditions = this.propertiesConditions.get(id);

                if (conditions == null)
                    conditions = PropertyUtil.instantiate(Constants.DEFAULT_LIST_CLASS);

                if (conditions != null)
                    conditions.add(condition);

                this.propertiesConditions.put(id, conditions);
            }
        }
    }
    
    /**
     * Returns the mapping of the properties conditions operations.
     *
     * @return Instance that contains the mapping of the properties conditions operations.
     */
    public Map<String, ConditionOperationType> getPropertiesConditionsOperations(){
        return this.propertiesConditionsOperations;
    }
    
    /**
     * Defines the mapping of the properties conditions operations.
     *
     * @param propertiesConditionsOperations Instance that contains the mapping of the properties conditions operations.
     */
    public void setPropertiesConditionsOperations(Map<String, ConditionOperationType> propertiesConditionsOperations){
        this.propertiesConditionsOperations = propertiesConditionsOperations;
    }
    
    /**
     * Adds a property condition operation.
     *
     * @param id String that contains the identifier of the property.
     * @param conditionOperation Instance that contains the condition operation.
     */
    public void addPropertyConditionOperation(String id, ConditionOperationType conditionOperation){
        if(id != null && !id.isEmpty() && conditionOperation != null){
            if(this.propertiesConditionsOperations == null)
                this.propertiesConditionsOperations = PropertyUtil.instantiate(Constants.DEFAULT_MAP_CLASS);

            if(this.propertiesConditionsOperations != null)
                this.propertiesConditionsOperations.put(id, conditionOperation);
        }
    }
    
    /**
     * Returns the properties values mappings.
     *
     * @return Instance that contains the properties values mappings.
     */
    public Map<String, Collection<Object>> getPropertiesValues(){
        return this.propertiesValues;
    }
    
    /**
     * Defines the properties values mappings.
     *
     * @param propertiesValues Instance that contains the properties values
     * mappings.
     */
    public void setPropertiesValues(Map<String, Collection<Object>> propertiesValues){
        this.propertiesValues = propertiesValues;
    }
    
    /**
     * Adds a property value.
     *
     * @param id String that contains the identifier of the property.
     * @param value Instance that contains the value.
     */
    public void addPropertyValue(String id, Object value){
        if(id != null && !id.isEmpty()){
            if(this.propertiesValues == null)
                this.propertiesValues = PropertyUtil.instantiate(Constants.DEFAULT_MAP_CLASS);

            if(this.propertiesValues != null) {
                Collection<Object> values = this.propertiesValues.get(id);

                if (values == null)
                    values = PropertyUtil.instantiate(Constants.DEFAULT_LIST_CLASS);

                if (values != null)
                    values.add(value);

                this.propertiesValues.put(id, values);
            }
        }
    }
    
    /**
     * Returns all properties' sort order mappings.
     *
     * @return Instance that contains all properties' sort order mappings.
     */
    public Map<String, SortOrderType> getPropertiesSortOrders(){
        return this.propertiesSortOrders;
    }
    
    /**
     * Defines all properties' sort order mappings.
     *
     * @param propertiesSortOrders Instance that contains all properties' sort
     * order mappings.
     */
    public void setPropertiesSortOrders(Map<String, SortOrderType> propertiesSortOrders){
        this.propertiesSortOrders = propertiesSortOrders;
    }
    
    /**
     * Adds a property sort order.
     *
     * @param id String that contains the identifier of the property.
     * @param sortOrder Instance that contains the sort order.
     */
    public void addPropertySortOrder(String id, SortOrderType sortOrder){
        if(id != null && !id.isEmpty() && sortOrder != null){
            if(this.propertiesSortOrders == null)
                this.propertiesSortOrders = PropertyUtil.instantiate(Constants.DEFAULT_MAP_CLASS);

            if(this.propertiesSortOrders != null)
                this.propertiesSortOrders.put(id, sortOrder);
        }
    }
}