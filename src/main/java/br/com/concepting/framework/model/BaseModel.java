package br.com.concepting.framework.model;

import br.com.concepting.framework.model.annotations.Model;
import br.com.concepting.framework.model.annotations.Property;
import br.com.concepting.framework.model.helpers.ModelInfo;
import br.com.concepting.framework.model.util.ModelUtil;
import br.com.concepting.framework.util.CollectionUtil;
import br.com.concepting.framework.util.PropertyUtil;
import br.com.concepting.framework.util.helpers.Node;
import br.com.concepting.framework.util.helpers.PropertyInfo;
import org.apache.commons.beanutils.MethodUtils;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

/**
 * Class that defines the basic implementation of a data model.
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
@Model
public abstract class BaseModel extends Node implements Comparable<BaseModel>{
    private static final long serialVersionUID = -7144498720569063721L;
    
    @Property(pattern = "##0.00")
    private Double compareAccuracy = null;
    
    @Property
    private String sortPropertyId = null;
    
    /**
     * Returns the accuracy percentage between two data model.
     *
     * @return Numeric value that contains the accuracy percentage.
     */
    public Double getCompareAccuracy(){
        return this.compareAccuracy;
    }
    
    /**
     * Defines the accuracy percentage between two data model.
     *
     * @param compareAccuracy Numeric value that contains the accuracy
     * percentage.
     */
    public void setCompareAccuracy(Double compareAccuracy){
        this.compareAccuracy = compareAccuracy;
    }
    
    /**
     * Returns the identifier of the current sort property.
     *
     * @return String that contains the identifier.
     */
    public String getSortPropertyId(){
        return this.sortPropertyId;
    }
    
    /**
     * Defines the identifier of the current sort property.
     *
     * @param sortPropertyId String that contains the identifier.
     */
    public void setSortPropertyId(String sortPropertyId){
        this.sortPropertyId = sortPropertyId;
    }
    
    /**
     * Compares the data model with another one.
     *
     * @param compareObject Instance that contains the data model that will be compared.
     * @return Numeric value that indicates the comparison (0 - Equal, 1 or -1 -
     * Different).
     */
    public int compareTo(@NotNull BaseModel compareObject){
        int result = -1;

        try {
            Object compareValue;
            Object currentValue;

            if (this.sortPropertyId == null || this.sortPropertyId.isEmpty()) {
                currentValue = this;
                compareValue = compareObject;
            } else {
                currentValue = PropertyUtil.getValue(this, this.sortPropertyId);
                compareValue = PropertyUtil.getValue(compareObject, this.sortPropertyId);

                if (PropertyUtil.isNumber(currentValue) && PropertyUtil.isNumber(compareValue))
                    currentValue = PropertyUtil.convertTo(currentValue, compareValue.getClass());
            }

            if (currentValue != null && compareValue != null)
                result = (Integer) (MethodUtils.invokeMethod(currentValue, "compareTo", compareValue));
            else if (currentValue == null && compareValue == null)
                result = 0;
            else if (currentValue != null)
                result = 1;
        }
        catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException |
                 NullPointerException ignored) {
        }

        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object compareModel){
        boolean compareFlag = true;
        
        if((compareModel != null && compareModel.getClass().equals(getClass())) || (compareModel != null && compareModel.getClass().getSuperclass().equals(getClass().getSuperclass()))){
            try{
                ModelInfo modelInfo = ModelUtil.getInfo(getClass());
                Collection<PropertyInfo> identitiesInfo = modelInfo.getIdentityPropertiesInfo();
                Collection<PropertyInfo> uniquesInfo = modelInfo.getUniquePropertiesInfo();
                Collection<PropertyInfo> propertiesInfo = CollectionUtil.union(identitiesInfo, uniquesInfo);
                
                if(!propertiesInfo.isEmpty()){
                    for(PropertyInfo propertyInfo: propertiesInfo){
                        try{
                            Object value = PropertyUtil.getValue(this, propertyInfo.getId());
                            Object compareValue = PropertyUtil.getValue(compareModel, propertyInfo.getId());
                            
                            if(propertyInfo.isNumber()){
                                value = PropertyUtil.convertTo(value, propertyInfo.getClazz());
                                compareValue = PropertyUtil.convertTo(compareValue, propertyInfo.getClazz());
                                
                                if(value != null && compareValue != null)
                                    compareFlag = (PropertyUtil.compareTo(value, compareValue) == 0);
                            }
                            else{
                                if(value != null && compareValue != null)
                                    compareFlag = value.equals(compareValue);
                                else
                                    compareFlag = false;
                            }
                            
                            if(!compareFlag)
                                break;
                        }
                        catch(Throwable ignored){
                        }
                    }
                }
            }
            catch(IllegalArgumentException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException | ClassNotFoundException | NoSuchFieldException e){
                compareFlag = false;
            }
        }

        return compareFlag;
    }

    @Override
    public String toString(){
        try{
            ModelInfo modelInfo = ModelUtil.getInfo(getClass());
            String descriptionPattern = modelInfo.getDescriptionPattern();
            
            if(descriptionPattern != null && !descriptionPattern.isEmpty())
                return PropertyUtil.fillPropertiesInString(this, descriptionPattern);
        }
        catch(IllegalArgumentException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException | ClassNotFoundException | NoSuchFieldException ignored){
        }
        
        return super.toString();
    }
}