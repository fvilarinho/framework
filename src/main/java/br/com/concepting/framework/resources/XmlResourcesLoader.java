package br.com.concepting.framework.resources;

import br.com.concepting.framework.caching.CachedObject;
import br.com.concepting.framework.caching.Cacher;
import br.com.concepting.framework.caching.CacherManager;
import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.model.exceptions.ItemAlreadyExistsException;
import br.com.concepting.framework.model.exceptions.ItemNotFoundException;
import br.com.concepting.framework.resources.exceptions.InvalidResourcesException;
import br.com.concepting.framework.util.FileUtil;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.XmlReader;
import br.com.concepting.framework.util.constants.XmlConstants;
import br.com.concepting.framework.util.helpers.XmlNode;
import javassist.tools.reflect.Loader;
import org.apache.commons.beanutils.ConstructorUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;

/**
 * Class that defines the basic implementation to manipulate resources in XML
 * format.
 *
 * @param <R> Class that defines the resources.
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
public abstract class XmlResourcesLoader<R extends BaseResources<XmlNode>> extends BaseResourcesLoader<XmlNode>{
    private Cacher<R> resourcesCacher = null;
    
    /**
     * Constructor - Manipulates specific resources.
     *
     * @param resourcesId String that contains the identifier of the resources.
     * @throws InvalidResourcesException Occurs when the resources could not be read.
     */
    protected XmlResourcesLoader(String resourcesId) throws InvalidResourcesException{
        this(null, resourcesId);
    }
    
    /**
     * Constructor - Manipulates specific resources.
     *
     * @param resourcesDirname String that contains the directory where the resources
     * are stored.
     * @param resourcesId String that contains the identifier of the resources.
     * @throws InvalidResourcesException Occurs when the resources could not be read.
     */
    protected XmlResourcesLoader(String resourcesDirname, String resourcesId) throws InvalidResourcesException{
        super(resourcesDirname, resourcesId);
        
        this.resourcesCacher = CacherManager.getInstance().getCacher(getClass());
    }
    
    /**
     * @see br.com.concepting.framework.resources.BaseResourcesLoader#parseContent()
     */
    protected XmlNode parseContent() throws InvalidResourcesException{
        String resourcesDirname = getResourcesDirname();
        String resourcesId = getResourcesId();
        
        try{
            InputStream contentStream = null;
            
            if(resourcesDirname == null || resourcesDirname.length() == 0){
                contentStream = getClass().getClassLoader().getResourceAsStream(resourcesId);
                
                if(contentStream == null)
                    throw new InvalidResourcesException(resourcesId);
            }
            else{
                StringBuilder contentId = new StringBuilder();
                
                contentId.append(resourcesDirname);
                contentId.append(FileUtil.getDirectorySeparator());
                contentId.append(resourcesId);
                
                contentStream = new FileInputStream(contentId.toString());
            }
            
            XmlReader contentReader = new XmlReader(contentStream);
            
            return contentReader.getRoot();
        }
        catch(IOException e){
            throw new InvalidResourcesException(resourcesDirname, resourcesId, e);
        }
    }
    
    /**
     * Returns the class that defines where the resources will be stored.
     *
     * @return Class that defines where the resources will be stored.
     * @throws ClassNotFoundException Occurs when was not possible to
     * instantiate the class.
     */
    @SuppressWarnings("unchecked")
    protected Class<R> getResourcesClass() throws ClassNotFoundException{
        String resourceClassId = StringUtil.replaceAll(getClass().getName(), Loader.class.getSimpleName(), "");
        
        return (Class<R>) Class.forName(resourceClassId);
    }
    
    /**
     * Parses the resources node.
     *
     * @param node Instance that contains the node.
     * @return Instance that contains the resources.
     * @throws InvalidResourcesException Occurs when was not possible to parse the node.
     */
    protected R parseResources(XmlNode node) throws InvalidResourcesException{
        try{
            R resources = null;
            
            if(node != null){
                resources = ConstructorUtils.invokeConstructor(getResourcesClass(), null);
                resources.setId(node.getAttribute(Constants.IDENTITY_ATTRIBUTE_ID));
                resources.setDefault(Boolean.valueOf(node.getAttribute(Constants.DEFAULT_ATTRIBUTE_ID)));
                resources.setContent(node);
            }
            
            return resources;
        }
        catch(ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e){
            throw new InvalidResourcesException(getResourcesDirname(), getResourcesId(), e);
        }
    }
    
    /**
     * Returns the instance of a specific resources.
     *
     * @param id String that contains the identifier of the resource.
     * @return Instance that contains the resource.
     * @throws InvalidResourcesException Occurs when was not possible to read the
     * resource.
     */
    public R get(String id) throws InvalidResourcesException{
        if(id == null)
            id = Constants.DEFAULT_ATTRIBUTE_ID;
        
        CachedObject<R> object = null;
        R resources = null;
        
        try{
            object = this.resourcesCacher.get(id);
            resources = object.getContent();
            
            return resources;
        }
        catch(ItemNotFoundException e){
            XmlNode contentNode = getContent();
            XmlNode resourcesNode = null;
            XmlNode defaultResourcesNode = null;
            
            try{
                if(!contentNode.getName().equals(XmlConstants.DEFAULT_RESOURCES_NODE_ID)){
                    int cont = 0;
                    
                    while(true){
                        resourcesNode = contentNode.getNode(cont);
                        
                        if(resourcesNode == null)
                            break;
                        
                        if(resourcesNode.getName().equals(XmlConstants.DEFAULT_RESOURCES_NODE_ID)){
                            if(Boolean.valueOf(resourcesNode.getAttribute(Constants.DEFAULT_ATTRIBUTE_ID)))
                                defaultResourcesNode = resourcesNode;
                            
                            if(id.equals(resourcesNode.getAttribute(Constants.IDENTITY_ATTRIBUTE_ID)))
                                break;
                        }
                        
                        cont++;
                    }
                    
                    if(resourcesNode == null)
                        resourcesNode = defaultResourcesNode;
                    
                    String resourcesDirname = getResourcesDirname();
                    String resourcesId = getResourcesId();
                    
                    if(resourcesNode == null)
                        throw new InvalidResourcesException(resourcesDirname, resourcesId);
                    
                    String idBuffer = resourcesNode.getAttribute(Constants.IDENTITY_ATTRIBUTE_ID);
                    
                    if(idBuffer == null || idBuffer.length() == 0)
                        throw new InvalidResourcesException(resourcesDirname, resourcesId, resourcesNode.getText());
                    
                    if(id.equals(idBuffer))
                        throw new ItemNotFoundException();
                    
                    id = idBuffer;
                }
                else{
                    resourcesNode = contentNode;
                    id = Constants.DEFAULT_ATTRIBUTE_ID;
                }
                
                object = this.resourcesCacher.get(id);
                resources = object.getContent();
                
                return resources;
            }
            catch(ItemNotFoundException e1){
                resources = parseResources(resourcesNode);
                
                object = new CachedObject<R>();
                object.setId(id);
                object.setContent(resources);
                
                try{
                    this.resourcesCacher.add(object);
                }
                catch(ItemAlreadyExistsException e2){
                }
                
                return resources;
            }
        }
    }
    
    /**
     * Returns the instance of the default resource.
     *
     * @return Instance that contains the resource.
     * @throws InvalidResourcesException Occurs when was not possible to get the
     * resource.
     */
    public R getDefault() throws InvalidResourcesException{
        return get(null);
    }
}