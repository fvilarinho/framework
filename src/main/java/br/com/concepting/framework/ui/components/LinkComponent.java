package br.com.concepting.framework.ui.components;

import br.com.concepting.framework.constants.SystemConstants;
import br.com.concepting.framework.controller.form.ActionFormController;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.model.FormModel;
import br.com.concepting.framework.model.ObjectModel;
import br.com.concepting.framework.model.SystemModuleModel;
import br.com.concepting.framework.resources.PropertiesResources;
import br.com.concepting.framework.security.controller.SecurityController;
import br.com.concepting.framework.security.model.LoginSessionModel;
import br.com.concepting.framework.util.types.ComponentType;

/**
 * Class that defines the link component.
 *
 * @author fvilarinho
 * @since 3.0.0
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
public class LinkComponent extends BaseActionFormComponent{
    private static final long serialVersionUID = 7816895487661041101L;
    
    private String url = null;
    private String target = null;
    
    /**
     * Returns the URL of the component.
     *
     * @return String that contains the URL.
     */
    public String getUrl(){
        return this.url;
    }
    
    /**
     * Defines the URL of the component.
     *
     * @param url String that contains the URL.
     */
    public void setUrl(String url){
        this.url = url;
    }
    
    /**
     * Returns the link target.
     *
     * @return String that contains the target.
     */
    public String getTarget(){
        return this.target;
    }
    
    /**
     * Defines the link target.
     *
     * @param target String that contains the target.
     */
    public void setTarget(String target){
        this.target = target;
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#buildResources()
     */
    protected void buildResources() throws InternalErrorException{
        super.buildResources();
        
        ActionFormController actionFormController = getActionFormController();
        SecurityController securityController = getSecurityController();
        String name = getName();
        String actionFormName = getActionFormName();
        
        if(name != null && name.length() > 0 && actionFormName != null && actionFormName.length() > 0 && actionFormController != null && securityController != null){
            LoginSessionModel loginSession = securityController.getLoginSession();
            SystemModuleModel systemModule = (loginSession != null ? loginSession.getSystemModule() : null);
            FormModel form = (systemModule != null ? systemModule.getForm(actionFormName) : null);
            ObjectModel object = (form != null ? form.getObject(name) : null);
            
            if(object != null){
                if(this.url == null)
                    this.url = object.getAction();
                
                if(this.target == null)
                    this.target = object.getActionTarget();
            }
        }
        
        String resourcesKey = getResourcesKey();
        
        if((this.url == null || this.url.length() == 0) && ((name != null && name.length() > 0) || (resourcesKey != null && resourcesKey.length() > 0))){
            StringBuilder propertyId = new StringBuilder();
            
            if(resourcesKey != null && resourcesKey.length() > 0)
                propertyId.append(resourcesKey);
            else
                propertyId.append(name);
            
            propertyId.append(".");
            propertyId.append(SystemConstants.URL_ATTRIBUTE_ID);
            
            PropertiesResources resources = getResources();
            PropertiesResources mainConsoleResources = getMainConsoleResources();
            PropertiesResources defaultResources = getDefaultResources();
            
            this.url = (resources != null ? resources.getProperty(propertyId.toString(), false) : null);
            
            if(this.url == null)
                this.url = (mainConsoleResources != null ? mainConsoleResources.getProperty(propertyId.toString(), false) : null);
            
            if(this.url == null)
                this.url = (defaultResources != null ? defaultResources.getProperty(propertyId.toString(), false) : null);
            
            if(this.url == null){
                ComponentType componentType = getComponentType();
                
                propertyId.delete(0, propertyId.length());
                propertyId.append(componentType.getId());
                propertyId.append(".");
                propertyId.append(SystemConstants.URL_ATTRIBUTE_ID);
                
                this.url = (resources != null ? resources.getProperty(propertyId.toString(), false) : null);
                
                if(this.url == null)
                    this.url = (mainConsoleResources != null ? mainConsoleResources.getProperty(propertyId.toString(), false) : null);
                
                if(this.url == null)
                    this.url = (defaultResources != null ? defaultResources.getProperty(propertyId.toString(), false) : null);
            }
        }
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#initialize()
     */
    protected void initialize() throws InternalErrorException{
        setComponentType(ComponentType.LINK);
        
        super.initialize();
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#renderAttributes()
     */
    protected void renderAttributes() throws InternalErrorException{
        String onClick = getOnClick();
        
        if(this.url != null && this.url.length() > 0 && (onClick == null || onClick.length() == 0)){
            print(" href=\"");
            print(this.url);
            print("\"");
            
            if(this.target != null && this.target.length() > 0){
                print(" target=\"");
                print(this.target);
                print("\"");
            }
        }
        
        super.renderAttributes();
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#renderOpen()
     */
    protected void renderOpen() throws InternalErrorException{
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#renderType()
     */
    protected void renderType() throws InternalErrorException{
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BaseComponent#renderName()
     */
    protected void renderName() throws InternalErrorException{
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#renderSize()
     */
    protected void renderSize() throws InternalErrorException{
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#renderEnabled()
     */
    protected void renderEnabled() throws InternalErrorException{
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#renderLabel()
     */
    protected void renderLabel() throws InternalErrorException{
        Boolean showLabel = showLabel();
        String label = getLabel();
        
        if(showLabel != null && showLabel && label != null && label.length() > 0)
            print(label);
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#renderBody()
     */
    protected void renderBody() throws InternalErrorException{
        print("<a");
        
        renderAttributes();
        
        print(">");
        
        renderLabel();
        
        println("</a>");
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#renderClose()
     */
    protected void renderClose() throws InternalErrorException{
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#clearAttributes()
     */
    protected void clearAttributes() throws InternalErrorException{
        super.clearAttributes();
        
        setUrl(null);
        setTarget(null);
    }
}