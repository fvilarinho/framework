package br.com.concepting.framework.security.util;

import br.com.concepting.framework.security.constants.SecurityConstants;

import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * Class that implements the AES Cryptography.
 *
 * @author fvilarinho
 * @since 3.1.0
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
public class CryptoAES extends BaseCrypto{
    /**
     * Constructor - Defines the cryptography default parameters.
     *
     * @throws InvalidKeyException Occurs when was not possible to execute the
     * operation.
     * @throws NoSuchAlgorithmException Occurs when was not possible to execute
     * the operation.
     * @throws NoSuchPaddingException Occurs when was not possible to execute
     * the operation.
     * @throws InvalidKeySpecException Occurs when was not possible to execute
     * the operation.
     * @throws UnsupportedEncodingException Occurs when was not possible to
     * execute the operation.
     */
    public CryptoAES() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, UnsupportedEncodingException{
        this(SecurityConstants.DEFAULT_CRYPTO_AES_KEY_SIZE);
    }
    
    /**
     * Constructor - Defines the cryptography parameters.
     *
     * @param keySize Numeric value that contains the length of the cryptography
     * key.
     * @throws InvalidKeyException Occurs when was not possible to execute the
     * operation.
     * @throws NoSuchAlgorithmException Occurs when was not possible to execute
     * the operation.
     * @throws NoSuchPaddingException Occurs when was not possible to execute
     * the operation.
     * @throws InvalidKeySpecException Occurs when was not possible to execute
     * the operation.
     * @throws UnsupportedEncodingException Occurs when was not possible to
     * execute the operation.
     */
    public CryptoAES(int keySize) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, UnsupportedEncodingException{
        this(keySize, true);
    }
    
    /**
     * Constructor - Defines the cryptography parameters.
     *
     * @param passPhrase String that contains a cryptography key.
     * @throws InvalidKeyException Occurs when was not possible to execute the
     * operation.
     * @throws NoSuchAlgorithmException Occurs when was not possible to execute
     * the operation.
     * @throws NoSuchPaddingException Occurs when was not possible to execute
     * the operation.
     * @throws InvalidKeySpecException Occurs when was not possible to execute
     * the operation.
     * @throws UnsupportedEncodingException Occurs when was not possible to
     * execute the operation.
     */
    public CryptoAES(String passPhrase) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, UnsupportedEncodingException{
        this(passPhrase, SecurityConstants.DEFAULT_CRYPTO_AES_KEY_SIZE);
    }
    
    /**
     * Constructor - Defines the cryptography parameters.
     *
     * @param passPhrase String that contains a cryptography key.
     * @param keySize Numeric value that contains the length of the cryptography
     * key.
     * @throws InvalidKeyException Occurs when was not possible to execute the
     * operation.
     * @throws NoSuchAlgorithmException Occurs when was not possible to execute
     * the operation.
     * @throws NoSuchPaddingException Occurs when was not possible to execute
     * the operation.
     * @throws InvalidKeySpecException Occurs when was not possible to execute
     * the operation.
     * @throws UnsupportedEncodingException Occurs when was not possible to
     * execute the operation.
     */
    public CryptoAES(String passPhrase, int keySize) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, UnsupportedEncodingException{
        this(passPhrase, keySize, true);
    }
    
    /**
     * Constructor - Defines the cryptography parameters.
     *
     * @param useBase64 Indicates if the encrypted message should be encoded
     * using Base64.
     * @throws InvalidKeyException Occurs when was not possible to execute the
     * operation.
     * @throws NoSuchAlgorithmException Occurs when was not possible to execute
     * the operation.
     * @throws NoSuchPaddingException Occurs when was not possible to execute
     * the operation.
     * @throws InvalidKeySpecException Occurs when was not possible to execute
     * the operation.
     * @throws UnsupportedEncodingException Occurs when was not possible to
     * execute the operation.
     */
    public CryptoAES(boolean useBase64) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, UnsupportedEncodingException{
        this(SecurityConstants.DEFAULT_CRYPTO_AES_KEY_SIZE, useBase64);
    }
    
    /**
     * Constructor - Defines the cryptography parameters.
     *
     * @param keySize Numeric value that contains the length of the cryptography
     * key.
     * @param useBase64 Indicates if the encrypted message should be encoded
     * using Base64.
     * @throws InvalidKeyException Occurs when was not possible to execute the
     * operation.
     * @throws NoSuchAlgorithmException Occurs when was not possible to execute
     * the operation.
     * @throws NoSuchPaddingException Occurs when was not possible to execute
     * the operation.
     * @throws InvalidKeySpecException Occurs when was not possible to execute
     * the operation.
     * @throws UnsupportedEncodingException Occurs when was not possible to
     * execute the operation.
     */
    public CryptoAES(int keySize, boolean useBase64) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, UnsupportedEncodingException{
        super(SecurityConstants.DEFAULT_CRYPTO_AES_ALGORITHM_ID, null, keySize, useBase64);
    }
    
    /**
     * Constructor - Defines the cryptography parameters.
     *
     * @param passPhrase String that contains a cryptography key.
     * @param useBase64 Indicates if the encrypted message should be encoded
     * using Base64.
     * @throws InvalidKeyException Occurs when was not possible to execute the
     * operation.
     * @throws NoSuchAlgorithmException Occurs when was not possible to execute
     * the operation.
     * @throws NoSuchPaddingException Occurs when was not possible to execute
     * the operation.
     * @throws InvalidKeySpecException Occurs when was not possible to execute
     * the operation.
     * @throws UnsupportedEncodingException Occurs when was not possible to
     * execute the operation.
     */
    public CryptoAES(String passPhrase, boolean useBase64) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, UnsupportedEncodingException{
        this(passPhrase, SecurityConstants.DEFAULT_CRYPTO_AES_KEY_SIZE, useBase64);
    }
    
    /**
     * Constructor - Defines the cryptography parameters.
     *
     * @param passPhrase String that contains a cryptography key.
     * @param keySize Numeric value that contains the length of the cryptography
     * key.
     * @param useBase64 Indicates if the encrypted message should be encoded
     * using Base64.
     * @throws InvalidKeyException Occurs when was not possible to execute the
     * operation.
     * @throws NoSuchAlgorithmException Occurs when was not possible to execute
     * the operation.
     * @throws NoSuchPaddingException Occurs when was not possible to execute
     * the operation.
     * @throws InvalidKeySpecException Occurs when was not possible to execute
     * the operation.
     * @throws UnsupportedEncodingException Occurs when was not possible to
     * execute the operation.
     */
    public CryptoAES(String passPhrase, int keySize, boolean useBase64) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, UnsupportedEncodingException{
        super(SecurityConstants.DEFAULT_CRYPTO_AES_ALGORITHM_ID, passPhrase, keySize, useBase64);
    }

    @Override
    protected SecretKey generateKey() throws InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException, UnsupportedEncodingException{
        if(this.passPhrase == null || this.passPhrase.isEmpty())
            return super.generateKey();
        
        return new SecretKeySpec(this.passPhrase.getBytes(), this.algorithm);
    }
}