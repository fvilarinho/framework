package br.com.concepting.framework.resources;

import br.com.concepting.framework.caching.CachedObject;

/**
 * Class that defines the basic implementation to store resources.
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
public abstract class BaseResources<O> extends CachedObject<O>{
    private static final long serialVersionUID = 8856089972323762812L;
    
    private Boolean isDefault = false;
    
    /**
     * Defines if is the default resources.
     *
     * @param isDefault True/False.
     */
    public void setDefault(Boolean isDefault){
        this.isDefault = isDefault;
    }
    
    /**
     * Indicates if is the default resources.
     *
     * @return True/False.
     */
    public Boolean isDefault(){
        return this.isDefault;
    }
}