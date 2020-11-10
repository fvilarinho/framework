package br.com.innovativethinking.framework.security.model;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import br.com.innovativethinking.framework.audit.annotations.Auditable;
import br.com.innovativethinking.framework.model.BaseModel;
import br.com.innovativethinking.framework.model.ObjectModel;
import br.com.innovativethinking.framework.model.SystemModuleModel;
import br.com.innovativethinking.framework.model.annotations.Model;
import br.com.innovativethinking.framework.model.annotations.Property;
import br.com.innovativethinking.framework.model.types.ConditionType;
import br.com.innovativethinking.framework.model.types.ValidationType;
import br.com.innovativethinking.framework.persistence.types.RelationJoinType;
import br.com.innovativethinking.framework.persistence.types.RelationType;
import br.com.innovativethinking.framework.util.helpers.DateTime;
import br.com.innovativethinking.framework.util.types.ByteMetricType;
import br.com.innovativethinking.framework.util.types.ContentType;
import br.com.innovativethinking.framework.util.types.SearchType;
import br.com.innovativethinking.framework.util.types.SortOrderType;

/**
 * Class that defines the data model of a user.
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
@Model(descriptionPattern = "#{name}")
public class UserModel extends BaseModel{
	private static final long serialVersionUID = -5952606948187054532L;

	@Property(isIdentity = true)
	private Integer id = null;

	@Auditable
	@Property(isForSearch = true, searchType = SearchType.CASE_INSENSITIVE, validations = ValidationType.REQUIRED)
	private String name = null;

	@Property(isForSearch = true, searchType = SearchType.CASE_INSENSITIVE, searchCondition = ConditionType.CONTAINS, validations = ValidationType.REQUIRED, sortOrder = SortOrderType.ASCEND)
	private String fullName = null;

	@Property
	@JsonProperty(access=Access.WRITE_ONLY)
	private String password = null;

	@Property(validations = {ValidationType.STRONG_PASSWORD, ValidationType.MINIMUM_LENGTH}, minimumLength = 8)
	@JsonProperty(access=Access.WRITE_ONLY)
	private String newPassword = null;

	@Property
	@JsonProperty(access=Access.WRITE_ONLY)
	private String confirmPassword = null;
	
	@Property
	@JsonProperty(access=Access.WRITE_ONLY)
	private String mfaToken = null;

	@Property(isForSearch = true, searchType = SearchType.CASE_INSENSITIVE, validations = {ValidationType.REQUIRED, ValidationType.EMAIL})
	private String email = null;

	@Property(pattern="+99 99 99999-9999")
	private String phoneNumber = null;

	@Property
	private DateTime creation = null;

	@Property
	private DateTime lastUpdate = null;

	@Property
	private DateTime lastLogin = null;

	@Property(isForSearch = true)
	private Boolean active = null;

	@Property(isForSearch = true)
	private Boolean system = null;

	@Property
	private Boolean superUser = null;

	@Property(validations = {ValidationType.CONTENT_TYPE, ValidationType.CONTENT_SIZE}, contentTypes = {ContentType.JPG, ContentType.PNG, ContentType.GIF}, contentSizeUnit = ByteMetricType.MEGA_BYTE)
	private byte logo[] = null;

	@Property(relationType = RelationType.ONE_TO_ONE, relationJoinType = RelationJoinType.LEFT_JOIN, cascadeOnSave = true, cascadeOnDelete = true)
	private LoginParameterModel loginParameter = null;

	@Property(relationType = RelationType.MANY_TO_MANY)
	private Collection<? extends GroupModel> groups = null;
	
	/**
	 * Indicates if it is a system user.
	 * 
	 * @return True/False.
	 */
	public Boolean getSystem(){
		return isSystem();
	}

	/**
	 * Indicates if it is a system user.
	 * 
	 * @return True/False.
	 */
	public Boolean isSystem(){
		return this.system;
	}

	/**
	 * Defines if it is a system user.
	 * 
	 * @param system True/False.
	 */
	public void setSystem(Boolean system){
		this.system = system;
	}

	/**
	 * Returns the phone number.
	 * 
	 * @return String that contains the phone number.
	 */
	public String getPhoneNumber(){
		return this.phoneNumber;
	}

	/**
	 * Defines the phone number.
	 * 
	 * @param phoneNumber String that contains the phone number.
	 */
	public void setPhoneNumber(String phoneNumber){
		this.phoneNumber = phoneNumber;
	}
	
	/**
	 * Returns the MFA token.
	 * 
	 * @return String that contains the MFA token.
	 */
	public String getMfaToken(){
		return this.mfaToken;
	}

	/**
	 * Defines the MFA token.
	 * 
	 * @param mfaToken String that contains the MFA token.
	 */
	public void setMfaToken(String mfaToken){
		this.mfaToken = mfaToken;
	}

	/**
	 * Returns the date/time of the last login of the user
	 * 
	 * @return Instance the date/time of the last login.
	 */
	public DateTime getLastLogin(){
		return this.lastLogin;
	}

	/**
	 * Defines the date/time of the last login of the user
	 * 
	 * @param lastLogin Instance the date/time of the last login.
	 */
	public void setLastLogin(DateTime lastLogin){
		this.lastLogin = lastLogin;
	}

	/**
	 * Returns the byte array that contains the logo
	 * 
	 * @return Byte array that contains the logo
	 */
	public byte[] getLogo(){
		return this.logo;
	}

	/**
	 * Defines the byte array that contains the logo
	 * 
	 * @param logo Byte array that contains the logo
	 */
	public void setLogo(byte[] logo){
		this.logo = logo;
	}

	/**
	 * Indicates if the user is active.
	 * 
	 * @return True/False.
	 */
	public Boolean isActive(){
		return this.active;
	}

	/**
	 * Indicates if the user is active.
	 * 
	 * @return True/False.
	 */
	public Boolean getActive(){
		return isActive();
	}

	/**
	 * Defines if the user is active.
	 * 
	 * @param active True/False.
	 */
	public void setActive(Boolean active){
		this.active = active;
	}

	/**
	 * Returns the list of groups of the user.
	 *
	 * @return Instance that contains the list of groups.
	 */
	public Collection<? extends GroupModel> getGroups(){
		return this.groups;
	}

	/**
	 * Defines the list of groups of the user.
	 *
	 * @param groups Instance that contains the list of groups.
	 */
	public void setGroups(Collection<? extends GroupModel> groups){
		this.groups = groups;
	}

	/**
	 * Returns the identifier of the user.
	 * 
	 * @return Numeric value that contains the identifier of the user.
	 */
	public Integer getId(){
		return this.id;
	}

	/**
	 * Defines the identifier of the user.
	 * 
	 * @param id Numeric value that contains the identifier of the user.
	 */
	public void setId(Integer id){
		this.id = id;
	}

	/**
	 * Returns the name of the user.
	 * 
	 * @return String that contains the name of the user.
	 */
	public String getName(){
		return this.name;
	}

	/**
	 * Defines the name of the user.
	 * 
	 * @param name String that contains the name of the user.
	 */
	public void setName(String name){
		this.name = name;
	}

	/**
	 * Returns the password of the user.
	 * 
	 * @return String that contains the password of the user.
	 */
	public String getPassword(){
		return this.password;
	}

	/**
	 * Defines the password of the user.
	 * 
	 * @param password String that contains the password of the user.
	 */
	public void setPassword(String password){
		this.password = password;
	}

	/**
	 * Returns the e-Mail of the user.
	 * 
	 * @return String that contains the e-Mail of the user.
	 */
	public String getEmail(){
		return this.email;
	}

	/**
	 * Defines the e-Mail of the user.
	 * 
	 * @param email String that contains the e-Mail of the user.
	 */
	public void setEmail(String email){
		this.email = email;
	}

	/**
	 * Returns the date/time of the creation of the user.
	 * 
	 * @return Instance that contains the date/time of the creation of the user.
	 */
	public DateTime getCreation(){
		return this.creation;
	}

	/**
	 * Defines the date/time of the creation of the user.
	 * 
	 * @param creation Instance that contains the date/time of the creation of
	 * the user.
	 */
	public void setCreation(DateTime creation){
		this.creation = creation;
	}

	/**
	 * Returns the date/time the last update of the user.
	 * 
	 * @return Instance that contains the date/time the last update of the user.
	 */
	public DateTime getLastUpdate(){
		return this.lastUpdate;
	}

	/**
	 * Defines the date/time the last update of the user.
	 * 
	 * @param lastUpdate Instance that contains the date/time the last update of
	 * the user.
	 */
	public void setLastUpdate(DateTime lastUpdate){
		this.lastUpdate = lastUpdate;
	}

	/**
	 * Returns the full name of the user.
	 * 
	 * @return String that contains the full name of the user.
	 */
	public String getFullName(){
		return this.fullName;
	}

	/**
	 * Defines the full name of the user.
	 * 
	 * @param fullName String that contains the full name of the user.
	 */
	public void setFullName(String fullName){
		this.fullName = fullName;
	}

	/**
	 * Indicates if it is a superuser.
	 * 
	 * @return True/False.
	 */
	public Boolean isSuperUser(){
		return this.superUser;
	}

	/**
	 * Indicates if it is a superuser.
	 * 
	 * @return True/False.
	 */
	public Boolean getSuperUser(){
		return isSuperUser();
	}

	/**
	 * Defines if it is a superuser.
	 * 
	 * @param superUser True/False.
	 */
	public void setSuperUser(Boolean superUser){
		this.superUser = superUser;
	}

	/**
	 * Returns the password confirmation.
	 * 
	 * @return String that contains the password.
	 */
	public String getConfirmPassword(){
		return this.confirmPassword;
	}

	/**
	 * Defines the password confirmation.
	 * 
	 * @param confirmPassword String that contains the password.
	 */
	public void setConfirmPassword(String confirmPassword){
		this.confirmPassword = confirmPassword;
	}

	/**
	 * Returns the new password of the user.
	 * 
	 * @return String that contains the new password of the user.
	 */
	public String getNewPassword(){
		return this.newPassword;
	}

	/**
	 * Defines the new password of the user.
	 * 
	 * @param newPassword String that contains the new password of the user.
	 */
	public void setNewPassword(String newPassword){
		this.newPassword = newPassword;
	}

	/**
	 * Returns the instance that contains the login session parameters.
	 *
	 * @param <LP> Class that defines the login parameter data model.
	 * @return Instance that contains the login session parameters.
	 */
	@SuppressWarnings("unchecked")
	public <LP extends LoginParameterModel> LP getLoginParameter(){
		return (LP)this.loginParameter;
	}

	/**
	 * Defines the instance that contains the login session parameters.
	 *
	 * @param loginParameter Instance that contains the login session
	 * parameters.
	 */
	public void setLoginParameter(LoginParameterModel loginParameter){
		this.loginParameter = loginParameter;
	}

	/**
	 * Indicates if the user has permissions.
	 * 
	 * @return True/False.
	 */
	public Boolean hasPermissions(){
		try{
			if(this.groups != null && !this.groups.isEmpty()){
				GroupModel groupItem = null;

				for(Object item : this.groups){
					groupItem = (GroupModel)item;

					if(groupItem.hasPermissions())
						return true;
				}

				return false;
			}
		}
		catch(Throwable e){
		}

		return true;
	}

	/**
	 * Indicates if the user has permissions to access the path.
	 * 
	 * @param path String that contains the path.
	 * @return True/False.
	 */
	public Boolean hasPermission(String path){
		try{
			if(this.groups != null && !this.groups.isEmpty()){
				GroupModel groupItem = null;

				for(Object item : this.groups){
					groupItem = (GroupModel)item;

					if(groupItem.hasPermission(path))
						return true;
				}

				return false;
			}
		}
		catch(Throwable e){
		}

		return true;
	}

	/**
	 * Indicates if the user has permissions in the system.
	 * 
	 * @param compareSystemModule Instance that contains the system module.
	 * @return True/False.
	 */
	public Boolean hasPermission(SystemModuleModel compareSystemModule){
		try{
			if(this.groups != null && this.groups.size() > 0 && compareSystemModule != null){
				GroupModel groupItem = null;

				for(Object item : this.groups){
					groupItem = (GroupModel)item;

					if(groupItem.hasPermission(compareSystemModule))
						return true;
				}

				return false;
			}
		}
		catch(Throwable e){
		}

		return true;
	}

	/**
	 * Indicates if the user has permissions to manipulate a specific object.
	 * 
	 * @param compareObject Instance that contains the object.
	 * @return True/False.
	 */
	public Boolean hasPermission(ObjectModel compareObject){
		try{
			if(this.groups != null && this.groups.size() > 0 && compareObject != null){
				GroupModel groupItem = null;

				for(Object item : this.groups){
					groupItem = (GroupModel)item;

					if(groupItem.hasPermission(compareObject))
						return true;
				}

				return false;
			}
		}
		catch(Throwable e){
		}

		return true;
	}
}