package br.com.concepting.framework.model;

import br.com.concepting.framework.audit.annotations.Auditable;
import br.com.concepting.framework.model.annotations.Model;
import br.com.concepting.framework.model.annotations.Property;

import java.io.Serial;

/**
 * Class that defines the basic implementation of a data model that stores
 * information of the access.
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
 * along with this program.  If not, see <a href="https://www.gnu.org/licenses"></a>.</pre>
 */
@Model(descriptionPattern = "#{path}")
@Auditable
public class UrlModel extends BaseModel{
    @Serial
    private static final long serialVersionUID = 5960805163339246391L;
    
    @Property(isIdentity = true)
    private Integer id = null;
    
    @Property(isForSearch = true, isUnique = true, maximumLength = 1000)
    @Auditable
    private String path = null;
    
    /**
     * Returns the identifier of the URL.
     *
     * @return Numeric value that contains the identifier.
     */
    public Integer getId(){
        return this.id;
    }
    
    /**
     * Defines the identifier of the URL.
     *
     * @param id Numeric value that contains the identifier.
     */
    public void setId(Integer id){
        this.id = id;
    }
    
    /**
     * Returns the path of the URL.
     *
     * @return String that contains the path.
     */
    public String getPath(){
        return this.path;
    }
    
    /**
     * Defines the path of the URL.
     *
     * @param path String that contains the path.
     */
    public void setPath(String path){
        this.path = path;
    }
}