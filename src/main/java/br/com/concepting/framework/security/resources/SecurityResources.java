package br.com.concepting.framework.security.resources;

import br.com.concepting.framework.resources.BaseResources;
import br.com.concepting.framework.security.constants.SecurityConstants;
import br.com.concepting.framework.security.model.LoginSessionModel;
import br.com.concepting.framework.util.helpers.XmlNode;

import java.io.Serial;

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
public class SecurityResources extends BaseResources<XmlNode>{
    @Serial
    private static final long serialVersionUID = -2135486169744118837L;
    
    private Class<? extends LoginSessionModel> loginSessionClass = null;
    private int loginSessionTimeout = SecurityConstants.DEFAULT_LOGIN_SESSION_TIMEOUT;
    private String cryptographyAlgorithm = null;
    private int cryptographyKeySize = SecurityConstants.DEFAULT_CRYPTO_KEY_SIZE;
    
    /**
     * Returns the size of the cryptography key.
     *
     * @return Numeric value that contains the size of the key.
     */
    public int getCryptographyKeySize(){
        return this.cryptographyKeySize;
    }
    
    /**
     * Defines the size of the cryptography key.
     *
     * @param cryptographyKeySize Numeric value that contains the size of the
     * key.
     */
    public void setCryptographyKeySize(int cryptographyKeySize){
        this.cryptographyKeySize = cryptographyKeySize;
    }
    
    /**
     * Returns the algorithm of the cryptography.
     *
     * @return String that contains the identifier of the algorithm.
     */
    public String getCryptographyAlgorithm(){
        return this.cryptographyAlgorithm;
    }
    
    /**
     * Defines the algorithm of the cryptography.
     *
     * @param cryptographyAlgorithm String that contains the identifier of the
     * algorithm.
     */
    public void setCryptographyAlgorithm(String cryptographyAlgorithm){
        this.cryptographyAlgorithm = cryptographyAlgorithm;
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
        if(loginSessionClassName != null && !loginSessionClassName.isEmpty())
            setLoginSessionClass((Class<? extends LoginSessionModel>) Class.forName(loginSessionClassName));
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
    public int getLoginSessionTimeout(){
        return this.loginSessionTimeout;
    }
    
    /**
     * Defines the login session timeout.
     *
     * @param loginSessionTimeout Numeric value that contains the login session
     * timeout.
     */
    public void setLoginSessionTimeout(int loginSessionTimeout){
        this.loginSessionTimeout = loginSessionTimeout;
    }
}