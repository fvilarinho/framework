package br.com.concepting.framework.audit.resources;

import br.com.concepting.framework.audit.constants.AuditorConstants;
import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.resources.FactoryResources;
import br.com.concepting.framework.resources.XmlResourcesLoader;
import br.com.concepting.framework.resources.constants.ResourcesConstants;
import br.com.concepting.framework.resources.exceptions.InvalidResourcesException;
import br.com.concepting.framework.util.helpers.XmlNode;

/**
 * Class responsible to manipulate the auditing resources.
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
public class AuditorResourcesLoader extends XmlResourcesLoader<AuditorResources>{
    /**
     * Constructor - Manipulates the default resources.
     *
     * @throws InvalidResourcesException Occurs when the default resources could
     * not be read.
     */
    public AuditorResourcesLoader() throws InvalidResourcesException{
        super(AuditorConstants.DEFAULT_RESOURCES_ID);
    }
    
    /**
     * Constructor - Manipulates specific resources.
     *
     * @param resourcesDirname String that contains the directory where the resources
     * are stored.
     * @throws InvalidResourcesException Occurs when the resources could not be read.
     */
    public AuditorResourcesLoader(String resourcesDirname) throws InvalidResourcesException{
        super(resourcesDirname, AuditorConstants.DEFAULT_RESOURCES_ID);
    }

    @Override
    public AuditorResources parseResources(XmlNode resourcesNode) throws InvalidResourcesException{
        String resourcesDirname = getResourcesDirname();
        String resourcesId = getResourcesId();
        AuditorResources resources = super.parseResources(resourcesNode);
        String level = resourcesNode.getAttribute(AuditorConstants.LEVEL_ATTRIBUTE_ID);
        
        if(level == null || level.length() == 0)
            throw new InvalidResourcesException(resourcesDirname, resourcesId, resourcesNode.getText());
        
        resources.setLevel(level);
        
        XmlNode appendersNode = resourcesNode.getNode(AuditorConstants.APPENDERS_ATTRIBUTE_ID);
        
        if(appendersNode == null)
            throw new InvalidResourcesException(resourcesDirname, resourcesId, resourcesNode.getText());
        
        FactoryResources appender;
        XmlNode appenderNode;
        String appenderName;
        String appenderClass;
        int cont1 = 0;
        int cont2;
        
        while(true){
            appenderNode = appendersNode.getNode(cont1);
            
            if(appenderNode == null)
                break;
            
            appenderName = appenderNode.getName();
            
            if(appenderName != null && appenderName.equals(AuditorConstants.APPENDER_ENTRY_ATTRIBUTE_ID)){
                appenderClass = appenderNode.getAttribute(Constants.CLASS_ATTRIBUTE_ID);
                
                if(appenderClass == null || appenderClass.length() == 0)
                    throw new InvalidResourcesException(resourcesDirname, resourcesId, appenderNode.getText());
                
                appender = new FactoryResources();
                appender.setClazz(appenderClass);
                
                XmlNode optionsNode = appenderNode.getNode(ResourcesConstants.OPTIONS_ATTRIBUTE_ID);
                
                if(optionsNode != null){
                    XmlNode optionNode;
                    String optionId;
                    String optionValue;
                    
                    cont2 = 0;
                    
                    while(true){
                        optionNode = optionsNode.getNode(cont2);
                        
                        if(optionNode == null)
                            break;
                        
                        optionId = optionNode.getAttribute(Constants.IDENTITY_ATTRIBUTE_ID);
                        
                        if(optionId == null || optionId.length() == 0)
                            throw new InvalidResourcesException(resourcesDirname, resourcesId, optionNode.getText());
                        
                        optionValue = optionNode.getAttribute(Constants.VALUE_ATTRIBUTE_ID);
                        
                        if(optionValue != null)
                            appender.addOption(optionId, optionValue);
                        
                        cont2++;
                    }
                }
                
                resources.addAppender(appender);
            }
            
            cont1++;
        }
        
        return resources;
    }
}