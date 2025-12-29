package br.com.concepting.framework.ui.components;

import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.helpers.ModelInfo;
import br.com.concepting.framework.model.util.ModelUtil;
import br.com.concepting.framework.util.PropertyUtil;
import br.com.concepting.framework.util.helpers.PropertyInfo;
import br.com.concepting.framework.util.types.ComponentType;

import java.io.Serial;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

/**
 * Class that defines the basic implementation for an option component.
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
public abstract class BaseOptionPropertyComponent extends BasePropertyComponent{
    @Serial
    private static final long serialVersionUID = 8280294646254910196L;

    private boolean selected = false;
    private Object optionValue = null;

    /**
     * Returns the instance that contains the value of the option.
     *
     * @return Instance that contains the value of the option.
     */
    public Object getOptionValue(){
        return this.optionValue;
    }
    
    /**
     * Defines the instance that contains the value of the option.
     *
     * @param optionValue Instance that contains the value of the option.
     */
    public void setOptionValue(Object optionValue){
        this.optionValue = optionValue;
    }
    
    /**
     * Indicates if the option is selected.
     *
     * @return True/False.
     */
    protected boolean isSelected(){
        return this.selected;
    }
    
    /**
     * Defines if the option is selected.
     *
     * @param selected True/False.
     */
    protected void setSelected(boolean selected){
        this.selected = selected;
    }

    @Override
    protected String getFormattedValue() throws InternalErrorException{
        if(this.optionValue != null){
            PropertyInfo propertyInfo = getPropertyInfo();
            
            if(propertyInfo != null){
                boolean isModel = propertyInfo.isModel();
                
                if(!isModel){
                    try{
                        this.optionValue = PropertyUtil.getValue(this.optionValue, propertyInfo.getId());
                    }
                    catch(Throwable ignored){
                    }
                }
            }
        }
        
        if(this.optionValue != null){
            if(PropertyUtil.isModel(this.optionValue)){
                BaseModel model = (BaseModel) this.optionValue;
                
                try{
                    return ModelUtil.toIdentifierString(model);
                }
                catch(Throwable ignored){
                }
            }
            else if(PropertyUtil.isEnum(this.optionValue))
                return ((Enum<?>) this.optionValue).name();
            
            return PropertyUtil.format(this.optionValue, getPattern(), useAdditionalFormatting(), getPrecision(), getCurrentLanguage());
        }
        
        return super.getFormattedValue();
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void initialize() throws InternalErrorException{
        ComponentType componentType = getComponentType();
        
        if(componentType == null){
            componentType = ComponentType.OPTION;
            
            setComponentType(componentType);
        }
        
        super.initialize();
        
        PropertyInfo propertyInfo = getPropertyInfo();
        Object value = getValue();
        
        if(PropertyUtil.isCollection(value) && (propertyInfo != null && propertyInfo.isCollection())){
            if(this.optionValue != null){
                Collection<?> values = (Collection<?>) value;
                
                this.selected = (values.contains(this.optionValue));
            }
            else
                this.selected = false;
        }
        else if(PropertyUtil.isModel(this.optionValue) && !PropertyUtil.isModel(value)){
            try{
                Class<? extends BaseModel> modelClass = (Class<? extends BaseModel>) this.optionValue.getClass();
                ModelInfo modelInfo = ModelUtil.getInfo(modelClass);
                Collection<PropertyInfo> identityPropertiesInfo = modelInfo.getIdentityPropertiesInfo();
                
                if(identityPropertiesInfo != null && identityPropertiesInfo.size() == 1){
                    PropertyInfo identityPropertyInfo = identityPropertiesInfo.iterator().next();
                    Object identityValue = PropertyUtil.getValue(this.optionValue, identityPropertyInfo.getId());
                    
                    this.selected = (identityValue != null && (value != null && value.equals(identityValue)));
                }
                else
                    this.selected = false;
            }
            catch(IllegalAccessException | InvocationTargetException | NoSuchMethodException | IllegalArgumentException | InstantiationException | ClassNotFoundException | NoSuchFieldException e){
                this.selected = false;
            }
        }
        else if(this.optionValue != null)
            this.selected = (value != null && value.equals(this.optionValue));
        else if(this.isBoolean())
            this.selected = (value != null && Boolean.parseBoolean(value.toString()));
    }

    @Override
    protected void renderAttributes() throws InternalErrorException{
        super.renderAttributes();
        
        renderSelectionAttribute();
    }
    
    /**
     * Renders the selection attribute of the option.
     *
     * @throws InternalErrorException Occurs when was not possible to render.
     */
    protected void renderSelectionAttribute() throws InternalErrorException{
        boolean selected = isSelected();
        
        if(selected)
            print(" checked");
    }

    @Override
    protected void renderLabelBody() throws InternalErrorException{
        boolean showLabel = showLabel();
        String label = getLabel();
        
        if(showLabel && label != null && !label.isEmpty()){
            if(this.optionValue != null)
                println(label);
            else
                super.renderLabelBody();
        }
    }

    @Override
    protected void clearAttributes() throws InternalErrorException{
        super.clearAttributes();
        
        setSelected(false);
        setOptionValue(null);
    }
}