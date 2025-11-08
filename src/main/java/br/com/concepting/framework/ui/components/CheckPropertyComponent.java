package br.com.concepting.framework.ui.components;

import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.util.NumberUtil;
import br.com.concepting.framework.util.helpers.PropertyInfo;
import br.com.concepting.framework.util.types.ComponentType;

import javax.servlet.jsp.JspException;
import java.io.Serial;
import java.security.SecureRandom;

/**
 * Class that defines the checkbox component.
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
public class CheckPropertyComponent extends BaseOptionPropertyComponent{
    @Serial
    private static final long serialVersionUID = 3956751502801574242L;

    @Override
    protected void buildEvents() throws InternalErrorException{
        String id = getId();
        
        if(id != null && !id.isEmpty() && isBoolean()){
            StringBuilder onClick = new StringBuilder();
            
            onClick.append("setObjectValue('");
            onClick.append(id);
            onClick.append("', this.checked);");
            
            String currentOnClick = getOnClick();
            
            if(currentOnClick != null && !currentOnClick.isEmpty()){
                onClick.append(" ");
                onClick.append(currentOnClick);
                
                if(!currentOnClick.endsWith(";"))
                    onClick.append(";");
            }
            
            setOnClick(onClick.toString());
        }
        
        super.buildEvents();
    }

    @Override
    protected void buildName() throws InternalErrorException{
        String id = getId();
        String name = getName();
        
        if(id == null && name != null && !name.isEmpty()){
            StringBuilder idBuffer = new StringBuilder();
            
            idBuffer.append(name);
            idBuffer.append(new SecureRandom().nextInt(NumberUtil.getMaximumRange(Integer.class)));
            
            setId(idBuffer.toString());
        }
        
        super.buildName();
    }

    @Override
    protected void initialize() throws InternalErrorException{
        setComponentType(ComponentType.CHECK_BOX);
        
        super.initialize();
    }

    @Override
    protected void renderOpen() throws InternalErrorException{
        BaseOptionsPropertyComponent optionsPropertyComponent = (BaseOptionsPropertyComponent) findAncestorWithClass(this, BaseOptionsPropertyComponent.class);
        
        if(optionsPropertyComponent == null){
            try{
                optionsPropertyComponent = (BaseOptionsPropertyComponent) getParent();
            }
            catch(ClassCastException ignored){
            }
        }
        
        PropertyInfo propertyInfo = getPropertyInfo();
        String actionFormName = getActionFormName();
        String id = getId();
        String name = getName();
        boolean enabled = isEnabled();
        
        if(optionsPropertyComponent == null && propertyInfo != null && actionFormName != null && !actionFormName.isEmpty() && id != null && !id.isEmpty() && name != null && !name.isEmpty() && enabled){
            HiddenPropertyComponent propertyComponent = new HiddenPropertyComponent();
            
            propertyComponent.setPageContext(this.pageContext);
            propertyComponent.setOutputStream(getOutputStream());
            propertyComponent.setActionFormName(actionFormName);
            propertyComponent.setPropertyInfo(propertyInfo);
            propertyComponent.setId(id);
            propertyComponent.setName(name);
            propertyComponent.setLabel(getLabel());
            propertyComponent.setResourcesId(getResourcesId());
            propertyComponent.setResourcesKey(getResourcesKey());
            propertyComponent.setValue(getValue());
            
            try{
                propertyComponent.doStartTag();
                propertyComponent.doEndTag();
            }
            catch(JspException e){
                throw new InternalErrorException(e);
            }
            
            setId(name);
        }
        
        super.renderOpen();
    }
}