package br.com.innovativethinking.framework.security.util.interfaces;

import java.io.UnsupportedEncodingException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

/**
 * Interface that defines the basic implementation of the cryptography routines.
 * 
 * @author fvilarinho
 * @since 3.1.0
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
public interface ICrypto{
	/**
	 * Encrypts a message.
	 * 
	 * @param message String that contains the decrypted message.
	 * @return String that contains the encrypted message.
	 * @throws IllegalBlockSizeException Occurs when the cryptography key is
	 * invalid.
	 * @throws BadPaddingException Occurs when the cryptography key is invalid.
	 * @throws UnsupportedEncodingException Occurs when the cryptography key is
	 * invalid.
	 */
	public String encrypt(String message) throws IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException;

	/**
	 * Decrypts a message
	 * 
	 * @param message String that contains the encrypted message
	 * @return String that contains the decrypted message
	 * @throws UnsupportedEncodingException Occurs when the cryptography key is
	 * invalid.
	 * @throws BadPaddingException Occurs when the cryptography key is invalid.
	 * @throws IllegalBlockSizeException Occurs when the cryptography key is
	 * invalid.
	 */
	public String decrypt(String message) throws UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException;
}