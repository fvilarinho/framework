package br.com.concepting.framework.caching;

import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.util.PropertyUtil;
import br.com.concepting.framework.util.types.DateFieldType;

import java.util.Map;

/**
 * Class responsible to manage cache instances.
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
public class CacherManager{
    private static CacherManager instance = null;
    
    private final Map<String, Cacher<?>> cachers;
    
    /**
     * Constructor - Initialize the cache manager.
     */
    private CacherManager(){
        super();

        this.cachers = PropertyUtil.instantiate(Constants.DEFAULT_MAP_CLASS);
    }
    
    /**
     * Returns the cache manager instance.
     *
     * @return Instance that contains the cache manager.
     */
    public static CacherManager getInstance(){
        if(instance == null)
            instance = new CacherManager();
        
        return instance;
    }
    
    /**
     * Returns the cache instance.
     *
     * @param <O> Class that defines the type of the cache content.
     * @param id String that contains the identifier.
     * @param timeout Numeric value containing the timeout.
     * @return Instance that contains the cache.
     */
    public <O> Cacher<O> getCacher(String id, Long timeout){
        Cacher<O> cacher = getCacher(id);
        
        if(cacher != null && timeout != null && timeout > 0)
            cacher.setTimeout(timeout);
        
        return cacher;
    }
    
    /**
     * Returns the cache instance.
     *
     * @param <O> Class that defines the type of the cache content.
     * @param id String that contains the identifier.
     * @param timeout Numeric value containing the timeout.
     * @param timeoutType Instance that contains the timeout unit
     * @return Instance that contains the cache.
     */
    public <O> Cacher<O> getCacher(String id, Long timeout, DateFieldType timeoutType){
        Cacher<O> cacher = getCacher(id, timeout);
        
        if(cacher != null && timeoutType != null)
            cacher.setTimeoutType(timeoutType);
        
        return cacher;
    }

    /**
     * Returns the default cache instance.
     *
     * @param <O> Class that defines the type of the cache content.
     * @return Instance that contains the cache.
     */
    public <O> Cacher<O> getDefaultCacher(){
        return getCacher((String)null);
    }
    
    /**
     * Returns the cache instance.
     *
     * @param <O> Class that defines the type of the cache content.
     * @param id String that contains the identifier.
     * @return Instance that contains the cache.
     */
    @SuppressWarnings("unchecked")
    public <O> Cacher<O> getCacher(String id){
        Cacher<O> cacher;
        
        if(id == null || id.isEmpty())
            id = Cacher.class.getName();
        
        cacher = (Cacher<O>) this.cachers.get(id);
        
        if(cacher == null){
            cacher = new Cacher<>(id);
            
            this.cachers.put(id, cacher);
        }
        
        return cacher;
    }
    
    /**
     * Returns the cache instance.
     *
     * @param <O> Class that defines the type of the cache content.
     * @param clazz Class that contains the identifier.
     * @return Instance that contains the cache.
     */
    public <O> Cacher<O> getCacher(Class<?> clazz){
        if(clazz != null)
            return getCacher(clazz.getName());
        
        return null;
    }
    
    /**
     * Returns the cache instance.
     *
     * @param <O> Class that defines the type of the cache content.
     * @param clazz Class that contains the identifier.
     * @param timeout Numeric value containing the timeout.
     * @return Instance that contains the cache.
     */
    public <O> Cacher<O> getCacher(Class<?> clazz, Long timeout){
        if(clazz != null && timeout != null && timeout > 0)
            return getCacher(clazz.getName(), timeout);
        
        return null;
    }
    
    /**
     * Returns the cache instance.
     *
     * @param <O> Class that defines the type of the cache content.
     * @param clazz String that contains the identifier.
     * @param timeout Numeric value containing the timeout.
     * @param timeoutType Instance that contains the timeout unit
     * @return Instance that contains the cache.
     */
    public <O> Cacher<O> getCacher(Class<?> clazz, Long timeout, DateFieldType timeoutType){
        if(clazz != null && timeout != null && timeout > 0 && timeoutType != null)
            return getCacher(clazz.getName(), timeout, timeoutType);
        
        return null;
    }
}