package br.com.innovativethinking.framework.webservice.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import br.com.innovativethinking.framework.exceptions.InternalErrorException;
import br.com.innovativethinking.framework.util.ByteUtil;
import br.com.innovativethinking.framework.util.PropertyUtil;

/**
 * Utility class responsible to manipulate web services.
 * 
 * @author fvilarinho
 * @since 3.7.0
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
public class WebServiceUtil{
	/**
	 * Returns the client instance.
	 * 
	 * @return Instance of the client.
	 */
	public static Client getClient(){
		return ClientBuilder.newClient();
	}

	public static Client getClient(Integer timeout){
		ClientBuilder clientBuilder = ClientBuilder.newBuilder();
		
		clientBuilder.connectTimeout(timeout, TimeUnit.SECONDS);
		clientBuilder.readTimeout(timeout, TimeUnit.SECONDS);
		
		return clientBuilder.build();
	}

	/**
	 * Deserializes an object.
	 * 
	 * @param <O> Class that defines the object.
	 * @param in Stream that contains the content.
	 * @param clazz Class that defines the object.
	 * @return Instance of the object.
	 * @throws InternalErrorException Occurs when was not possible to execute the operation.
	 */
	public static <O> O deserialize(InputStream in, Class<?> clazz) throws InternalErrorException{
		try{
			String responseContent = new String(ByteUtil.fromTextStream(in));

			return deserialize(responseContent, clazz);
		}
		catch(IOException e){
			throw new InternalErrorException(e);
		}
	}

	/**
	 * Deserializes an object.
	 * 
	 * @param <O> Class that defines the object.
	 * @param content String that contains the content.
	 * @param clazz Class that defines the object.
	 * @return Instance that contains the deserializable content.
	 * @throws IOException Occurs when an I/O issue was triggered.
	 */
	@SuppressWarnings("unchecked")
	public static <O> O deserialize(String content, Class<?> clazz) throws IOException{
		if(clazz != null)
			return (O)PropertyUtil.getMapper().readValue(content, clazz);

		return (O)content;
	}

	/**
	 * Serializes an object.
	 * 
	 * @param value Instance of the object.
	 * @param out Stream that should be used.
	 * @throws IOException Occurs when an I/O issue was triggered..
	 */
	public static void serialize(Object value, OutputStream out) throws IOException{
		PropertyUtil.getMapper().writeValue(out, value);
	}

	/**
	 * Serializes an object.
	 * 
	 * @param value Instance of the object.
	 * @return String that contains the object.
	 * @throws IOException Occurs when an I/O issue was triggered..
	 */
	public static String serialize(Object value) throws IOException{
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		serialize(value, out);

		return new String(out.toByteArray());
	}
}