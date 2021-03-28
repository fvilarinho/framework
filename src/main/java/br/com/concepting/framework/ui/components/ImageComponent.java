package br.com.concepting.framework.ui.components;

import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.ui.constants.UIConstants;
import br.com.concepting.framework.util.ByteUtil;
import br.com.concepting.framework.util.ImageUtil;
import br.com.concepting.framework.util.PropertyUtil;
import br.com.concepting.framework.util.types.ComponentType;

import java.io.IOException;

/**
 * Class that defines the image component.
 *
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
public class ImageComponent extends BasePropertyComponent{
    private static final long serialVersionUID = -3933038010029461283L;
    
    /**
     * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#buildRestrictions()
     */
    protected void buildRestrictions() throws InternalErrorException{
        String name = getName();
        
        if(name == null || name.length() == 0 || getPropertyInfo() == null)
            setShowLabel(false);
        
        super.buildRestrictions();
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BaseOptionsPropertyComponent#initialize()
     */
    protected void initialize() throws InternalErrorException{
        if(getComponentType() == null)
            setComponentType(ComponentType.IMAGE);
        
        super.initialize();
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BaseComponent#renderAttributes()
     */
    protected void renderAttributes() throws InternalErrorException{
        String contextPath = getContextPath();
        String currentSkin = getCurrentSkin();
        
        if(contextPath == null || contextPath.length() == 0 || currentSkin == null || currentSkin.length() == 0)
            return;
        
        super.renderAttributes();
        
        Object value = getValue();
        
        if(!PropertyUtil.isByteArray(value)){
            String url = (value != null ? value.toString() : null);
            
            if(url != null){
                print(" src=\"");
                
                if(url.startsWith("/")){
                    print(contextPath);
                    print(UIConstants.DEFAULT_SKINS_RESOURCES_DIR);
                    print(currentSkin);
                    print(UIConstants.DEFAULT_IMAGES_RESOURCES_DIR);
                    print(url.substring(1));
                }
                else
                    print(url);
                
                print("\"");
            }
        }
        else{
            try{
                print(" src=\"data:");
                print(ImageUtil.getImageFormat((byte[]) value).getMimeType());
                print(";base64,");
                print(ByteUtil.toBase64((byte[]) value));
                print("\"");
            }
            catch(IOException e){
                throw new InternalErrorException(e);
            }
        }
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#renderType()
     */
    protected void renderType() throws InternalErrorException{
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BasePropertyComponent#renderValue()
     */
    protected void renderValue() throws InternalErrorException{
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#renderSize()
     */
    protected void renderSize() throws InternalErrorException{
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BasePropertyComponent#renderReadOnly()
     */
    protected void renderReadOnly() throws InternalErrorException{
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#renderEnabled()
     */
    protected void renderEnabled() throws InternalErrorException{
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BaseComponent#renderBody()
     */
    protected void renderBody() throws InternalErrorException{
        Boolean hasInvalidPropertyDefinition = hasInvalidPropertyDefinition();
        
        if(hasInvalidPropertyDefinition != null && hasInvalidPropertyDefinition)
            super.renderInvalidPropertyMessage();
        else{
            if(getValue() != null){
                print("<img");
                
                renderAttributes();
                
                println(">");
            }
        }
    }
}