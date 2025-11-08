package br.com.concepting.framework.security.util;

import br.com.concepting.framework.security.constants.SecurityConstants;
import br.com.concepting.framework.util.ByteUtil;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Class that implements the digest.
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
public class CryptoDigest{
    private final MessageDigest digest;
    private final boolean useBase64;
    
    /**
     * Constructor - Defines the cryptography parameters.
     *
     * @throws NoSuchAlgorithmException Occurs when was not possible to execute
     * the operation.
     */
    public CryptoDigest() throws NoSuchAlgorithmException{
        this(true);
    }
    
    /**
     * Constructor - Defines the cryptography parameters.
     *
     * @param useBase64 Indicates if the encrypted message should be encoded
     * using Base64.
     * @throws NoSuchAlgorithmException Occurs when was not possible to execute
     * the operation.
     */
    public CryptoDigest(boolean useBase64) throws NoSuchAlgorithmException{
        this(SecurityConstants.DEFAULT_DIGEST_ALGORITHM_ID, useBase64);
    }
    
    /**
     * Constructor - Defines the cryptography parameters.
     *
     * @param algorithm String that contains the cryptography algorithm.
     * @throws NoSuchAlgorithmException Occurs when was not possible to execute
     * the operation.
     */
    public CryptoDigest(String algorithm) throws NoSuchAlgorithmException{
        this(algorithm, true);
    }
    
    /**
     * Constructor - Defines the cryptography parameters.
     *
     * @param algorithm String that contains the cryptography algorithm.
     * @param useBase64 Indicates if the encrypted message should be encoded
     * using Base64.
     * @throws NoSuchAlgorithmException Occurs when was not possible to execute
     * the operation.
     */
    public CryptoDigest(String algorithm, boolean useBase64) throws NoSuchAlgorithmException{
        super();
        
        this.useBase64 = useBase64;
        this.digest = MessageDigest.getInstance(algorithm);
    }
    
    /**
     * Encrypts the message
     *
     * @param message String that contains the decrypted message.
     * @return String that contains the encrypted message.
     * @throws UnsupportedEncodingException Occurs when was not possible to
     * execute the operation.
     */
    public String encrypt(String message) throws UnsupportedEncodingException{
        byte[] buffer = this.digest.digest(message.getBytes());
        
        return (this.useBase64 ? ByteUtil.toBase64(buffer) : ByteUtil.toHexadecimal(buffer));
    }
}