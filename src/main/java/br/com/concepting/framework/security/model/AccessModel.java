package br.com.concepting.framework.security.model;

import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.UrlModel;
import br.com.concepting.framework.model.annotations.Model;
import br.com.concepting.framework.model.annotations.Property;
import br.com.concepting.framework.persistence.types.RelationJoinType;
import br.com.concepting.framework.persistence.types.RelationType;

import java.io.Serial;

/**
 * Class that defines the data model that stores the information of access.
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
@Model
public class AccessModel extends BaseModel{
    @Serial
    private static final long serialVersionUID = -412329727370193182L;
    
    @Property(isIdentity = true)
    private Integer id = null;
    
    @Property(relationType = RelationType.ONE_TO_ONE, relationJoinType = RelationJoinType.INNER_JOIN)
    private UrlModel url = null;
    
    @Property
    private Boolean blocked = null;
    
    /**
     * Returns the identifier.
     *
     * @return Numeric value that contains the identifier.
     */
    public Integer getId(){
        return this.id;
    }
    
    /**
     * Defines the identifier.
     *
     * @param id Numeric value that contains the identifier.
     */
    public void setId(Integer id){
        this.id = id;
    }
    
    /**
     * Returns the URL.
     *
     * @param <U> Class that defines the URL data model.
     * @return Instance that contains the URL.
     */
    @SuppressWarnings("unchecked")
    public <U extends UrlModel> U getUrl(){
        return (U) this.url;
    }
    
    /**
     * Defines the URL.
     *
     * @param url Instance that contains the URL.
     */
    public void setUrl(UrlModel url){
        this.url = url;
    }
    
    /**
     * Indicates if the URL is blocked.
     *
     * @return True/False.
     */
    public Boolean isBlocked(){
        return this.blocked;
    }
    
    /**
     * Indicates if the URL is blocked.
     *
     * @return True/False.
     */
    public Boolean getBlocked(){
        return isBlocked();
    }
    
    /**
     * Defines if the URL is blocked.
     *
     * @param blocked True/False.
     */
    public void setBlocked(Boolean blocked){
        this.blocked = blocked;
    }
}