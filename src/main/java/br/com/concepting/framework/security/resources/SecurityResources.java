package br.com.concepting.framework.security.resources;

import br.com.concepting.framework.resources.BaseResources;
import br.com.concepting.framework.security.model.LoginSessionModel;
import br.com.concepting.framework.util.helpers.XmlNode;

/**
 * Class responsible to store the security resources.
 * 
 * @author fvilarinho
 * @since 3.0.0
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
public class SecurityResources extends BaseResources<XmlNode>{
	private static final long serialVersionUID = -2135486169744118837L;

	private Class<? extends LoginSessionModel> loginSessionClass     = null;
	private Integer                            loginSessionTimeout   = null;
	private String                             criptographyAlgorithm = null;
	private Integer                            criptographyKeySize   = null;

	/**
	 * Returns the size of the cryptography key.
	 * 
	 * @return Numeric value that contains the size of the key.
	 */
	public Integer getCriptographyKeySize(){
		return this.criptographyKeySize;
	}

	/**
	 * Defines the size of the cryptography key.
	 * 
	 * @param criptographyKeySize Numeric value that contains the size of the
	 * key.
	 */
	public void setCriptographyKeySize(Integer criptographyKeySize){
		this.criptographyKeySize = criptographyKeySize;
	}

	/**
	 * Returns the algorithm of the cryptography.
	 * 
	 * @return String that contains the identifier of the algorithm.
	 */
	public String getCriptographyAlgorithm(){
		return this.criptographyAlgorithm;
	}

	/**
	 * Defines the algorithm of the cryptography.
	 * 
	 * @param criptographyAlgorithm String that contains the identifier of the
	 * algorithm.
	 */
	public void setCriptographyAlgorithm(String criptographyAlgorithm){
		this.criptographyAlgorithm = criptographyAlgorithm;
	}

	/**
	 * Returns the class of the login session.
	 * 
	 * @return Class of the login session.
	 */
	public Class<? extends LoginSessionModel> getLoginSessionClass(){
		return this.loginSessionClass;
	}

	/**
	 * Defines the class of the login session.
	 * 
	 * @param loginSessionClassName String that contains the identifier of the
	 * login session class.
	 * @throws ClassNotFoundException Occurs when was not possible to execute
	 * the operation.
	 * @throws ClassCastException Occurs when was not possible to execute the
	 * operation.
	 */
	@SuppressWarnings("unchecked")
	public void setLoginSessionClass(String loginSessionClassName) throws ClassNotFoundException, ClassCastException{
		if(loginSessionClassName != null && loginSessionClassName.length() > 0)
			setLoginSessionClass((Class<? extends LoginSessionModel>)Class.forName(loginSessionClassName));
	}

	/**
	 * Defines the class of the login session.
	 * 
	 * @param loginSessionClass Class of the login session.
	 */
	public void setLoginSessionClass(Class<? extends LoginSessionModel> loginSessionClass){
		this.loginSessionClass = loginSessionClass;
	}

	/**
	 * Returns the login session timeout.
	 * 
	 * @return Numeric value that contains the login session timeout.
	 */
	public Integer getLoginSessionTimeout(){
		return this.loginSessionTimeout;
	}

	/**
	 * Defines the login session timeout.
	 * 
	 * @param loginSessionTimeout Numeric value that contains the login session
	 * timeout.
	 */
	public void setLoginSessionTimeout(Integer loginSessionTimeout){
		this.loginSessionTimeout = loginSessionTimeout;
	}
}