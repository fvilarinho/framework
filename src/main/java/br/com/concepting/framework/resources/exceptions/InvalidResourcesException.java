package br.com.concepting.framework.resources.exceptions;

import br.com.concepting.framework.exceptions.InternalErrorException;

/**
 * Class that defines the exception when the resources are invalid.
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
public class InvalidResourcesException extends InternalErrorException{
    private static final long serialVersionUID = -2484544753246799231L;
    
    private String resourcesDirname = null;
    private String resourcesId = null;
    private String content = null;
    
    /**
     * Constructor - Defines the exception.
     *
     * @param resourcesId String that contains the identifier of the resources.
     */
    public InvalidResourcesException(String resourcesId){
        this(null, resourcesId, null, null);
    }
    
    /**
     * Constructor - Defines the exception.
     *
     * @param resourcesId String that contains the identifier of the resources.
     * @param e Instance that contains the caught exception.
     */
    public InvalidResourcesException(String resourcesId, Throwable e){
        this(null, resourcesId, null, e);
    }
    
    /**
     * Constructor - Defines the exception.
     *
     * @param resourcesDirname String that contains the directory where the resources
     * are stored.
     * @param resourcesId String that contains the identifier of the resources.
     */
    public InvalidResourcesException(String resourcesDirname, String resourcesId){
        this(resourcesDirname, resourcesId, null, null);
    }
    
    /**
     * Constructor - Defines the exception.
     *
     * @param resourcesDirname String that contains the directory where the resources
     * are stored.
     * @param resourcesId String that contains the identifier of the resources.
     * @param e Instance that contains the caught exception.
     */
    public InvalidResourcesException(String resourcesDirname, String resourcesId, Throwable e){
        this(resourcesDirname, resourcesId, null, e);
    }
    
    /**
     * Constructor - Defines the exception.
     *
     * @param resourcesDirname String that contains the directory where the resources
     * are stored.
     * @param resourcesId String that contains the identifier of the resources.
     * @param content String of the invalid content.
     */
    public InvalidResourcesException(String resourcesDirname, String resourcesId, String content){
        this(resourcesDirname, resourcesId, content, null);
    }
    
    /**
     * Constructor - Defines the exception.
     *
     * @param resourcesDirname String that contains the directory where the resources
     * are stored.
     * @param resourcesId String that contains the identifier of the resources.
     * @param content String of the invalid content.
     * @param e Instance that contains the caught exception.
     */
    public InvalidResourcesException(String resourcesDirname, String resourcesId, String content, Throwable e){
        super(e);
        
        setResourcesDirname(resourcesDirname);
        setResourcesId(resourcesId);
        setContent(content);
    }
    
    /**
     * Returns a string of the invalid content.
     *
     * @return String of the invalid content.
     */
    public String getContent(){
        return this.content;
    }
    
    /**
     * Defines a string of the invalid content.
     *
     * @param content String of the invalid content.
     */
    public void setContent(String content){
        this.content = content;
    }
    
    /**
     * Returns the storage directory of the resources.
     *
     * @return String that contains the identifier of the storage directory.
     */
    public String getResourcesDirname(){
        return this.resourcesDirname;
    }
    
    /**
     * Defines the storage directory of the resources.
     *
     * @param resourcesDirname String that contains the identifier of the storage
     * directory.
     */
    public void setResourcesDirname(String resourcesDirname){
        this.resourcesDirname = resourcesDirname;
    }
    
    /**
     * Returns the identifier of the resources.
     *
     * @return String that contains the identifier of the resources.
     */
    public String getResourcesId(){
        return this.resourcesId;
    }
    
    /**
     * Defines the identifier of the resources.
     *
     * @param resourcesId String that contains the identifier of the resources.
     */
    public void setResourcesId(String resourcesId){
        this.resourcesId = resourcesId;
    }
}