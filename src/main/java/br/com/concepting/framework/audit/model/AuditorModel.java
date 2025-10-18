package br.com.concepting.framework.audit.model;

import br.com.concepting.framework.audit.annotations.Auditable;
import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.annotations.Model;
import br.com.concepting.framework.model.annotations.Property;
import br.com.concepting.framework.persistence.types.RelationType;
import br.com.concepting.framework.security.model.LoginSessionModel;
import br.com.concepting.framework.util.helpers.DateTime;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.Collection;

/**
 * Class responsible to perform auditing.
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
@Model
@Auditable
public class AuditorModel extends BaseModel{
    private static final long serialVersionUID = 4560619844140960680L;
    
    @Property(isIdentity = true)
    private Long id = null;
    
    @Property(isForSearch = true)
    @Auditable
    private DateTime startDateTime = null;
    
    @Property
    @Auditable
    private String severity = null;
    
    @Property
    @Auditable
    private LoginSessionModel loginSession = null;
    
    @Property(isForSearch = true)
    @Auditable
    private String entityId = null;
    
    @Property(isForSearch = true)
    @Auditable
    private String businessId = null;
    
    @Auditable
    @Property(relationType = RelationType.ONE_TO_MANY, cascadeOnDelete = true, cascadeOnSave = true)
    @JsonManagedReference(value = "auditorBusinessComplement")
    private Collection<? extends AuditorComplementModel> businessComplement = null;
    
    @Property
    @Auditable
    private String message = null;
    
    @Property
    @Auditable
    private Long responseTime = null;
    
    /**
     * Returns the response time of the processing.
     *
     * @return Numeric value containing the response time in milliseconds.
     */
    public Long getResponseTime(){
        return this.responseTime;
    }
    
    /**
     * Defines the response time of the processing.
     *
     * @param responseTime Numeric value containing the response time in
     * milliseconds.
     */
    public void setResponseTime(Long responseTime){
        this.responseTime = responseTime;
    }
    
    /**
     * Returns the login session.
     *
     * @param <L> Class that defines the login session.
     * @return Instance that contains the login session.
     */
    @SuppressWarnings("unchecked")
    public <L extends LoginSessionModel> L getLoginSession(){
        return (L) this.loginSession;
    }
    
    /**
     * Defines the identifier of the login session.
     *
     * @param loginSession Instance that contains the login session.
     */
    public void setLoginSession(LoginSessionModel loginSession){
        this.loginSession = loginSession;
    }
    
    /**
     * Returns the identifier of the entity's business.
     *
     * @return String that contains the identifier.
     */
    public String getBusinessId(){
        return this.businessId;
    }
    
    /**
     * Defines the identifier of the entity's business.
     *
     * @param businessId String that contains the identifier.
     */
    public void setBusinessId(String businessId){
        this.businessId = businessId;
    }
    
    /**
     * Returns the instance of the business complement.
     *
     * @param <C> Class that defines the type of the list that stores the business
     * complement.
     * @param <AB> Class that defines the type of the business complement.
     * @return Instance that contains the list that stores the business complement.
     */
    @SuppressWarnings("unchecked")
    public <AB extends AuditorComplementModel, C extends Collection<AB>> C getBusinessComplement(){
        return (C) this.businessComplement;
    }
    
    /**
     * Defines the instance of the business complement.
     *
     * @param businessComplement Instance that contains the list that stores the
     * business complement.
     */
    public void setBusinessComplement(Collection<? extends AuditorComplementModel> businessComplement){
        this.businessComplement = businessComplement;
    }
    
    /**
     * Returns the date/time of the auditing.
     *
     * @return Date/time of the auditing.
     */
    public DateTime getStartDateTime(){
        return this.startDateTime;
    }
    
    /**
     * Defines the date/time of the auditing.
     *
     * @param startDateTime Date/time of the auditing.
     */
    public void setStartDateTime(DateTime startDateTime){
        this.startDateTime = startDateTime;
    }
    
    /**
     * Returns the identifier of the entity.
     *
     * @return string that contains the identifier.
     */
    public String getEntityId(){
        return this.entityId;
    }
    
    /**
     * Defines the identifier of the entity.
     *
     * @param entityId string that contains the identifier.
     */
    public void setEntityId(String entityId){
        this.entityId = entityId;
    }
    
    /**
     * Returns the auditing identifier.
     *
     * @return Numeric value that contains of identifier.
     */
    public Long getId(){
        return this.id;
    }
    
    /**
     * Defines the auditing identifier.
     *
     * @param id Numeric value that contains of identifier.
     */
    public void setId(Long id){
        this.id = id;
    }
    
    /**
     * Returns the auditing severity.
     *
     * @return String that contains the severity.
     */
    public String getSeverity(){
        return this.severity;
    }
    
    /**
     * Defines the auditing severity.
     *
     * @param severity String that contains the severity.
     */
    public void setSeverity(String severity){
        this.severity = severity;
    }
    
    /**
     * Returns the auditing message.
     *
     * @return String that contains the message.
     */
    public String getMessage(){
        return this.message;
    }
    
    /**
     * Defines the auditing message.
     *
     * @param message String that contains the message.
     */
    public void setMessage(String message){
        this.message = message;
    }
}