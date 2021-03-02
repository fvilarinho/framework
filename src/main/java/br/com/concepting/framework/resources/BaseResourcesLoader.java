package br.com.concepting.framework.resources;

import br.com.concepting.framework.caching.CachedObject;
import br.com.concepting.framework.caching.Cacher;
import br.com.concepting.framework.caching.CacherManager;
import br.com.concepting.framework.model.exceptions.ItemAlreadyExistsException;
import br.com.concepting.framework.model.exceptions.ItemNotFoundException;
import br.com.concepting.framework.resources.exceptions.InvalidResourcesException;
import br.com.concepting.framework.util.FileUtil;

/**
 * Class that defines the basic implementation to manipulate resources.
 *
 * @param <O> Class that defines the resources.
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
public abstract class BaseResourcesLoader<O>{
    private Cacher<O> contentCacher = null;
    private O content = null;
    private String resourcesDirname = null;
    private String resourcesId = null;
    
    /**
     * Constructor - Default constructor.
     */
    protected BaseResourcesLoader(){
        super();
        
        this.contentCacher = CacherManager.getInstance().getCacher(getClass());
    }
    
    /**
     * Constructor - Manipulates specific resources.
     *
     * @param resourcesId String that contains the identifier of the resources.
     * @throws InvalidResourcesException Occurs when the resources could not be read.
     */
    protected BaseResourcesLoader(String resourcesId) throws InvalidResourcesException{
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
    protected BaseResourcesLoader(String resourcesDirname, String resourcesId) throws InvalidResourcesException{
        this();
        
        setResourcesDirname(resourcesDirname);
        setResourcesId(resourcesId);
        
        loadContent();
    }
    
    /**
     * Returns the identifier of the content.
     *
     * @return String that contains the identifier.
     */
    protected String getContentId(){
        StringBuilder contentId = new StringBuilder();
        
        if(this.resourcesDirname != null && this.resourcesDirname.length() > 0){
            contentId.append(this.resourcesDirname);
            contentId.append(FileUtil.getDirectorySeparator());
        }
        
        contentId.append(this.resourcesId);
        
        return contentId.toString();
    }
    
    /**
     * Loads the content.
     *
     * @throws InvalidResourcesException Occurs when was not possible to load the
     * content.
     */
    protected void loadContent() throws InvalidResourcesException{
        CachedObject<O> object = null;
        
        try{
            object = this.contentCacher.get(getContentId());
            this.content = object.getContent();
        }
        catch(ItemNotFoundException e){
            this.content = parseContent();
            
            object = new CachedObject<O>();
            object.setId(getContentId());
            object.setContent(this.content);
            
            try{
                this.contentCacher.add(object);
            }
            catch(ItemAlreadyExistsException e1){
            }
        }
    }
    
    /**
     * Parse/Validate the content.
     *
     * @return Instance that contains the content.
     * @throws InvalidResourcesException Occurs when was not possible to
     * parse/validate the content.
     */
    protected O parseContent() throws InvalidResourcesException{
        return null;
    }
    
    /**
     * Returns the instance of the content.
     *
     * @return Instance that contains the content.
     */
    public O getContent(){
        return this.content;
    }
    
    /**
     * Returns the identifier of the resources.
     *
     * @return String that contains the identifier.
     */
    public String getResourcesId(){
        return this.resourcesId;
    }
    
    /**
     * Defines the identifier of the resources.
     *
     * @param resourcesId String that contains the identifier.
     */
    public void setResourcesId(String resourcesId){
        this.resourcesId = resourcesId;
    }
    
    /**
     * Returns the directory where the resources are stored.
     *
     * @return String that contains the directory.
     */
    public String getResourcesDirname(){
        return this.resourcesDirname;
    }
    
    /**
     * Defines the directory where the resources are stored.
     *
     * @param resourcesDirname String that contains the directory.
     */
    public void setResourcesDirname(String resourcesDirname){
        this.resourcesDirname = resourcesDirname;
    }
}