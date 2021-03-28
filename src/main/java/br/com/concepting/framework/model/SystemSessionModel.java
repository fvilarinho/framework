package br.com.concepting.framework.model;

import br.com.concepting.framework.audit.annotations.Auditable;
import br.com.concepting.framework.model.annotations.Model;
import br.com.concepting.framework.model.annotations.Property;
import br.com.concepting.framework.model.types.ConditionType;
import br.com.concepting.framework.util.helpers.DateTime;
import br.com.concepting.framework.util.types.SearchType;
import br.com.concepting.framework.util.types.SortOrderType;

/**
 * Class that defines the basic implementation of the data model that stores
 * information of a system session.
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
@Auditable
@Model
public class SystemSessionModel extends BaseModel{
    private static final long serialVersionUID = -6089610099334045707L;
    
    @Auditable
    @Property(isIdentity = true)
    private String id = null;
    
    @Auditable
    @Property(sortOrder = SortOrderType.DESCEND)
    private DateTime startDateTime = null;
    
    @Auditable
    @Property(isForSearch = true, searchCondition = ConditionType.EQUAL, searchType = SearchType.CASE_INSENSITIVE)
    private String ip = null;
    
    @Auditable
    @Property(isForSearch = true, searchCondition = ConditionType.EQUAL, searchType = SearchType.CASE_INSENSITIVE)
    private String hostName = null;
    
    /**
     * Returns the host name of the system session.
     *
     * @return String that contains the host name.
     */
    public String getHostName(){
        return this.hostName;
    }
    
    /**
     * Defines the host name of the system session.
     *
     * @param hostName String that contains the host name.
     */
    public void setHostName(String hostName){
        this.hostName = hostName;
    }
    
    /**
     * Returns the identifier of the system session.
     *
     * @return String that contains the identifier of the system session.
     */
    public String getId(){
        return this.id;
    }
    
    /**
     * Defines the identifier of the system session.
     *
     * @param id String that contains the identifier of the system
     * session.
     */
    public void setId(String id){
        this.id = id;
    }
    
    /**
     * Returns o IP of the system session.
     *
     * @return String that contains o IP of the system session.
     */
    public String getIp(){
        return this.ip;
    }
    
    /**
     * Defines o IP of the system session.
     *
     * @param ip String that contains o IP of the system session.
     */
    public void setIp(String ip){
        this.ip = ip;
    }
    
    /**
     * Returns the date/time of the system session.
     *
     * @return Instance that contains the date/time of the system session.
     */
    public DateTime getStartDateTime(){
        return this.startDateTime;
    }
    
    /**
     * Defines the date/time of the system session.
     *
     * @param startDateTime Instance that contains the date/time of the system
     * session.
     */
    public void setStartDateTime(DateTime startDateTime){
        this.startDateTime = startDateTime;
    }
}