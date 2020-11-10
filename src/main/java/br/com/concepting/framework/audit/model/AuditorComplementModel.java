package br.com.concepting.framework.audit.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.concepting.framework.audit.annotations.Auditable;
import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.annotations.Model;
import br.com.concepting.framework.model.annotations.Property;
import br.com.concepting.framework.persistence.types.RelationJoinType;
import br.com.concepting.framework.persistence.types.RelationType;

/**
 * Class that defines the data model that stores additional information of the
 * auditing.
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
@Model
public class AuditorComplementModel extends BaseModel{
	private static final long serialVersionUID=-5973758121126490161L;
	
	@Property(isIdentity=true)
	private Integer id=null;

	@Property(relationType=RelationType.ONE_TO_ONE, relationJoinType=RelationJoinType.INNER_JOIN)
	@JsonBackReference(value="auditorBusinessComplement")
	private AuditorModel auditor=null;
	
	@Property
	@JsonIgnore
	private String type=null;
	
	@Property
	@Auditable
	private String name=null;

	@Property
	@Auditable
	private Object value=null;
	
	/**
	 * Returns the identifier of the property of the audited data model
	 * 
	 * @return Numeric value that contains the identifier.
	 */
	public Integer getId(){
		return this.id;
	}

	/**
	 * Defines the identifier of the property of the audited data model
	 * 
	 * @param id Numeric value that contains the identifier.
	 */
	public void setId(Integer id){
		this.id=id;
	}

	/**
	 * Returns the type of the property of the audited data model
	 *
	 * @return String that contains the type.
	 */
	public String getType(){
		return this.type;
	}

	/**
	 * Defines the type of the property of the audited data model.
	 *
	 * @param type String that contains the type.
	 */
	public void setType(String type){
		this.type=type;
	}

	/**
	 * Returns the instance of the auditing.
	 * 
	 * @param <A> Class that defines the data model of the auditing.
	 * @return Instance that contains the auditing.
	 */
	@SuppressWarnings("unchecked")
	public <A extends AuditorModel> A getAuditor(){
		return (A)this.auditor;
	}

	/**
	 * Defines the instance of the auditing.
	 * 
	 * @param auditor Instance that contains the auditing.
	 */
	public void setAuditor(AuditorModel auditor){
		this.auditor=auditor;
	}

	/**
	 * Returns the name of the property of the audited data model
	 *
	 * @return String that contains the name.
	 */
	public String getName(){
		return this.name;
	}

	/**
	 * Defines the name of the property of the audited data model.
	 *
	 * @param name String that contains the name.
	 */
	public void setName(String name){
		this.name=name;
	}

	/**
	 * Returns the value of the property of the audited data model.
	 *
	 * @param <O> Class that defines the value of the property.
	 * @return Instance that contains the value.
	 */
	@SuppressWarnings("unchecked")
	public <O> O getValue(){
		return (O)this.value;
	}

	/**
	 * Defines the value of the property of the audited data model.
	 *
	 * @param <O> Class that defines the value of the property.
	 * @param value Instance that contains the value.
	 */
	public <O> void setValue(O value){
		this.value=value;
	}
}