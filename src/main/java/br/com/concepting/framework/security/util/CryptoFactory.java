package br.com.concepting.framework.security.util;

import br.com.concepting.framework.security.constants.SecurityConstants;
import br.com.concepting.framework.security.util.interfaces.ICrypto;

import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * Class that defines the cryptography factory.
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
 * along with this program.  If not, see <a href="https://www.gnu.org/licenses"></a>.</pre>
 */
public abstract class CryptoFactory{
    /**
     * Returns the default implementation of the cryptography.
     *
     * @return Instance that contains the implementation of the cryptography.
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
    public static ICrypto getDefaultInstance() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, UnsupportedEncodingException{
        return getInstance(SecurityConstants.DEFAULT_CRYPTO_AES_ALGORITHM_ID);
    }
    
    /**
     * Returns a specific implementation of the cryptography.
     *
     * @param algorithm String that contains the cryptography algorithm.
     * @return Instance that contains the implementation of the cryptography.
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
    public static ICrypto getInstance(String algorithm) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, UnsupportedEncodingException{
        if (SecurityConstants.DEFAULT_CRYPTO_AES_ALGORITHM_ID.equals(algorithm))
            return new CryptoAES(true);

        throw new NoSuchAlgorithmException(algorithm);
    }
    
    /**
     * Returns the default implementation of the cryptography.
     *
     * @param useBase64 Indicates if the encrypted message should be encoded
     * using Base64.
     * @return Instance that contains the implementation of the cryptography.
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
    public static ICrypto getDefaultInstance(boolean useBase64) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, UnsupportedEncodingException{
        return getInstance(SecurityConstants.DEFAULT_CRYPTO_AES_ALGORITHM_ID, useBase64);
    }
    
    /**
     * Returns a specific implementation of the cryptography.
     *
     * @param algorithm String that contains the cryptography algorithm.
     * @param useBase64 Indicates if the encrypted message should be encoded
     * using Base64.
     * @return Instance that contains the implementation of the cryptography.
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
    public static ICrypto getInstance(String algorithm, boolean useBase64) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, UnsupportedEncodingException{
        if (SecurityConstants.DEFAULT_CRYPTO_AES_ALGORITHM_ID.equals(algorithm))
            return new CryptoAES(useBase64);

        throw new NoSuchAlgorithmException(algorithm);
    }
    
    /**
     * Returns the default implementation of the cryptography.
     *
     * @param passPhrase String that contains a cryptography key.
     * @param keySize Numeric value that contains the length of the cryptography
     * key.
     * @param useBase64 Indicates if the encrypted message should be encoded
     * using Base64.
     * @return Instance that contains the implementation of the cryptography.
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
    public static ICrypto getDefaultInstance(String passPhrase, Integer keySize, boolean useBase64) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, UnsupportedEncodingException{
        return getInstance(SecurityConstants.DEFAULT_CRYPTO_AES_ALGORITHM_ID, passPhrase, keySize, useBase64);
    }
    
    /**
     * Returns a specific implementation of the cryptography.
     *
     * @param algorithm String that contains the cryptography algorithm.
     * @param passPhrase String that contains a cryptography key.
     * @param keySize Numeric value that contains the length of the cryptography
     * key.
     * @param useBase64 Indicates if the encrypted message should be encoded
     * using Base64.
     * @return Instance that contains the implementation of the cryptography.
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
    public static ICrypto getInstance(String algorithm, String passPhrase, Integer keySize, boolean useBase64) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, UnsupportedEncodingException{
        if (SecurityConstants.DEFAULT_CRYPTO_AES_ALGORITHM_ID.equals(algorithm))
            return new CryptoAES(passPhrase, keySize, useBase64);

        throw new NoSuchAlgorithmException(algorithm);
    }
    
    /**
     * Returns the default implementation of the cryptography.
     *
     * @param passPhrase String that contains a cryptography key.
     * @param useBase64 Indicates if the encrypted message should be encoded
     * using Base64.
     * @return Instance that contains the implementation of the cryptography.
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
    public static ICrypto getDefaultInstance(String passPhrase, boolean useBase64) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, UnsupportedEncodingException{
        return getInstance(SecurityConstants.DEFAULT_CRYPTO_AES_ALGORITHM_ID, passPhrase, useBase64);
    }
    
    /**
     * Returns a specific implementation of the cryptography.
     *
     * @param algorithm String that contains the cryptography algorithm.
     * @param passPhrase String that contains a cryptography key.
     * @param useBase64 Indicates if the encrypted message should be encoded
     * using Base64.
     * @return Instance that contains the implementation of the cryptography.
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
    public static ICrypto getInstance(String algorithm, String passPhrase, boolean useBase64) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, UnsupportedEncodingException{
        if (SecurityConstants.DEFAULT_CRYPTO_AES_ALGORITHM_ID.equals(algorithm))
            return new CryptoAES(passPhrase, useBase64);

        throw new NoSuchAlgorithmException(algorithm);
    }
    
    /**
     * Returns the default implementation of the cryptography.
     *
     * @param passPhrase String that contains a cryptography key.
     * @return Instance that contains the implementation of the cryptography.
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
    public static ICrypto getDefaultInstance(String passPhrase) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, UnsupportedEncodingException{
        return getInstance(SecurityConstants.DEFAULT_CRYPTO_AES_ALGORITHM_ID, passPhrase);
    }
    
    /**
     * Returns a specific implementation of the cryptography.
     *
     * @param algorithm String that contains the cryptography algorithm.
     * @param passPhrase String that contains a cryptography key.
     * @return Instance that contains the implementation of the cryptography.
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
    public static ICrypto getInstance(String algorithm, String passPhrase) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, UnsupportedEncodingException{
        if (SecurityConstants.DEFAULT_CRYPTO_AES_ALGORITHM_ID.equals(algorithm))
            return new CryptoAES(passPhrase);

        throw new NoSuchAlgorithmException(algorithm);
    }
}