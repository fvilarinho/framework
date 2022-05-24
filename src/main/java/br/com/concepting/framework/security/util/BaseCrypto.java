package br.com.concepting.framework.security.util;

import br.com.concepting.framework.security.util.interfaces.ICrypto;
import br.com.concepting.framework.util.ByteUtil;
import org.apache.commons.codec.DecoderException;

import javax.crypto.*;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * Class that defines the basic implementations of cryptography.
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
public abstract class BaseCrypto implements ICrypto{
    protected final Cipher encrypt;
    protected final Cipher decrypt;
    protected final String algorithm;
    protected final String passPhrase;
    protected final boolean useBase64;
    protected final int keySize;
    
    /**
     * Constructor - Defines the cryptography parameters of the cryptography.
     *
     * @param algorithm String that contains the cryptography algorithm.
     * @param passPhrase String that contains cryptography key.
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
    public BaseCrypto(String algorithm, String passPhrase, int keySize, boolean useBase64) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException, UnsupportedEncodingException{
        super();
        
        this.algorithm = algorithm;
        this.passPhrase = passPhrase;
        this.keySize = keySize;
        this.useBase64 = useBase64;
        
        SecretKey key = generateKey();
        
        this.encrypt = Cipher.getInstance(key.getAlgorithm());
        this.encrypt.init(Cipher.ENCRYPT_MODE, key);
        
        this.decrypt = Cipher.getInstance(algorithm);
        this.decrypt.init(Cipher.DECRYPT_MODE, key);
    }
    
    /**
     * Generates the cryptography key.
     *
     * @return Instance that contains the cryptography key.
     * @throws InvalidKeyException Occurs when was not possible to execute the
     * operation.
     * @throws NoSuchAlgorithmException Occurs when was not possible to execute
     * the operation.
     * @throws InvalidKeySpecException Occurs when was not possible to execute
     * the operation.
     * @throws UnsupportedEncodingException Occurs when was not possible to
     * execute the operation.
     */
    protected SecretKey generateKey() throws InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException, UnsupportedEncodingException{
        KeyGenerator generator = KeyGenerator.getInstance(this.algorithm);
        
        if(this.keySize > 0)
            generator.init(this.keySize);
        
        return generator.generateKey();
    }

    @Override
    public String encrypt(String message) throws IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException{
        byte[] buffer = message.getBytes();
        byte[] enc = this.encrypt.doFinal(buffer);
        
        return (this.useBase64 ? ByteUtil.toBase64(enc) : ByteUtil.toHexadecimal(enc));
    }

    @Override
    public String decrypt(String message) throws IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, DecoderException {
        byte[] dec = (this.useBase64 ? ByteUtil.fromBase64(message) : ByteUtil.fromHexadecimal(message));
        byte[] buffer = this.decrypt.doFinal(dec);
        
        return new String(buffer);
    }
}