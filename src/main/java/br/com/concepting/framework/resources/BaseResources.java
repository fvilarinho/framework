package br.com.concepting.framework.resources;

import br.com.concepting.framework.caching.CachedObject;

import java.io.Serial;

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
public abstract class BaseResources<O> extends CachedObject<O>{
    @Serial
    private static final long serialVersionUID = 8856089972323762812L;
    
    private boolean isDefault = false;
    
    /**
     * Defines if is the default resources.
     *
     * @param isDefault True/False.
     */
    public void setDefault(boolean isDefault){
        this.isDefault = isDefault;
    }
    
    /**
     * Indicates if is the default resources.
     *
     * @return True/False.
     */
    public boolean isDefault(){
        return this.isDefault;
    }
}