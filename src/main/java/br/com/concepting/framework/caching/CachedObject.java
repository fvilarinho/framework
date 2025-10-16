package br.com.concepting.framework.caching;

import br.com.concepting.framework.util.helpers.DateTime;

import java.io.Serializable;
import java.util.Date;

/**
 * Class responsible to store a cacheable content.
 *
 * @param <O> Class that defines the cacheable object.
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
public class CachedObject<O> implements Serializable{
    private static final long serialVersionUID = 8165512408423991030L;
    
    private String id = null;
    private DateTime lastAccess = null;
    private DateTime cacheDate = null;
    private O content = null;
    private Cacher<O> cacher = null;
    
    /**
     * Returns the content identifier.
     *
     * @return String that contains the identifier.
     */
    public String getId(){
        return this.id;
    }
    
    /**
     * Defines the content identifier.
     *
     * @param id String that contains the identifier.
     */
    public void setId(String id){
        this.id = id;
    }
    
    /**
     * Flag that indicates if the content is in the cache.
     *
     * @return True/False.
     */
    public boolean isCached(){
        return this.cacher != null;
    }
    
    /**
     * Returns the content instance.
     *
     * @return Instance that contains the content.
     */
    public O getContent(){
        return this.content;
    }
    
    /**
     * Defines the content instance.
     *
     * @param content Instance that contains the content.
     */
    public void setContent(O content){
        this.content = content;
    }
    
    /**
     * Returns the date/time when the content was stored in the cache.
     *
     * @return Date/time when the content was stored in the cache.
     */
    public Date getCacheDate(){
        return this.cacheDate;
    }
    
    /**
     * Defines the date/time when the content was stored in cache.
     *
     * @param cacheDate Date/time when the content was stored in the cache.
     */
    public void setCacheDate(DateTime cacheDate){
        this.cacheDate = cacheDate;
    }
    
    /**
     * Returns the date/time of the content's last access.
     *
     * @return Date/time of the content's last access.
     */
    public DateTime getLastAccess(){
        return this.lastAccess;
    }
    
    /**
     * Defines the date/time of the content's last access.
     *
     * @param lastAccess Date/time of the content's last access.
     */
    public void setLastAccess(DateTime lastAccess){
        this.lastAccess = lastAccess;
    }
    
    /**
     * Attach the content to a cache instance.
     *
     * @param cacher Cache instance.
     */
    protected void attach(Cacher<O> cacher){
        this.cacher = cacher;
    }
}