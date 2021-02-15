package br.com.concepting.framework.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

import org.apache.commons.io.FileUtils;

import br.com.concepting.framework.constants.Constants;

/**
 * Class responsible to manipulate files.
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
public class FileUtil extends FileUtils{
	/**
	 * Returns the directory separator character.
	 * 
	 * @return String that contains the character.
	 */
	public static String getDirectorySeparator(){
		return System.getProperty("file.separator");
	}

	/**
	 * Download a content from an URL and writes it in a file.
	 * 
	 * @param url String that contains the URL.
	 * @param filename String that contains the filename.
	 * @throws IOException Occurs when was not possible to read the file.
	 */
	public static void fromUrlToFile(String url, String filename) throws IOException{
		if(url == null || url.length() == 0 || filename == null || filename.length() == 0)
			return;

		File file = new File(filename);
		
		if(!file.getParentFile().exists())
			file.getParentFile().mkdirs();
		
		URL path = new URL(url);
		InputStream in = path.openStream();
		FileOutputStream out = new FileOutputStream(filename);
		ReadableByteChannel inChannel = Channels.newChannel(in);
		FileChannel outChannel = out.getChannel();

		outChannel.transferFrom(inChannel, 0, Long.MAX_VALUE);
		out.close();
	}

	/**
	 * Creates a stream for a specific file.
	 * 
	 * @param filename String that contains the filename.
	 * @param append Indicates if the data should be appended.
	 * @return Instance that contains the stream.
	 * @throws IOException Occurs when was not possible to execute the
	 * operation.
	 */
	private static FileOutputStream createStream(String filename, Boolean append) throws IOException{
		if(filename == null || filename.length() == 0)
			return null;

		File file = new File(filename);
		
		if(!file.exists()){
			File parentFile = file.getParentFile();
			
			if(parentFile != null && !parentFile.exists())
				parentFile.mkdirs();
		}

		return new FileOutputStream(file, (append != null && append));
	}

	/**
	 * Returns the default encoding.
	 * 
	 * @return String that contains the encoding.
	 */
	public static String getDefaultEncoding(){
		String encoding = System.getProperty("file.encoding");

		if(encoding == null || encoding.length() == 0)
			encoding = Constants.DEFAULT_UNICODE_ENCODING;

		return encoding;
	}

	/**
	 * Writes to a text file.
	 * 
	 * @param filename String that contains the filename.
	 * @param value String that contains the data.
	 * @throws IOException Occurs when was not possible to write the file.
	 */
	public static void toTextFile(String filename, String value) throws IOException{
		if(filename != null && filename.length() > 0 && value != null)
			toTextFile(filename, value, false);
	}

	/**
	 * Writes to a text file.
	 * 
	 * @param out Instance that contains the stream.
	 * @param value String of the content of the file.
	 * @throws IOException Occurs when was not possible write the file.
	 */
	public static void toTextFile(FileOutputStream out, String value) throws IOException{
		if(out != null && value != null)
			toTextFile(out, value, getDefaultEncoding());
	}

	/**
	 * Writes to a text file.
	 * 
	 * @param filename String that contains the filename.
	 * @param value String that contains the data.
	 * @param append Indicates if the data should be appended.
	 * @throws IOException Occurs when was not possible to write the file.
	 */
	public static void toTextFile(String filename, String value, Boolean append) throws IOException{
		if(filename != null && filename.length() > 0 && value != null)
			toTextFile(filename, value, getDefaultEncoding(), append);
	}

	/**
	 * Writes to a text file.
	 * 
	 * @param filename String that contains the filename.
	 * @param value String that contains the data.
	 * @param encoding String that contains the encoding.
	 * @throws IOException Occurs when was not possible to write the file.
	 */
	public static void toTextFile(String filename, String value, String encoding) throws IOException{
		if(filename != null && filename.length() > 0 && value != null)
			toTextFile(filename, value, encoding, false);
	}

	/**
	 * Writes to a text file.
	 * 
	 * @param filename String that contains the filename.
	 * @param value String that contains the data.
	 * @param encoding String that contains the encoding.
	 * @param append Indicates if the data should be appended.
	 * @throws IOException Occurs when was not possible to write the file.
	 */
	public static void toTextFile(String filename, String value, String encoding, Boolean append) throws IOException{
		if(encoding == null || encoding.length() == 0)
			encoding = getDefaultEncoding();

		toTextFile(createStream(filename, append), value, encoding);
	}

	/**
	 * Writes to a text file.
	 * 
	 * @param out Instance that contains the stream.
	 * @param value String that contains the data.
	 * @param encoding String that contains the encoding.
	 * @throws IOException Occurs when was not possible to write the file.
	 */
	public static void toTextFile(FileOutputStream out, String value, String encoding) throws IOException{
		if(out == null || value == null)
			return;

		if(encoding == null || encoding.length() == 0)
			encoding = getDefaultEncoding();

		OutputStreamWriter writer = new OutputStreamWriter(out, encoding);

		writer.write(value);
		writer.close();
	}

	/**
	 * Writes to a binary file.
	 * 
	 * @param filename String that contains the filename.
	 * @param value Byte array that contains the data.
	 * @throws IOException Occurs when was not possible to write the file.
	 */
	public static void toBinaryFile(String filename, byte[] value) throws IOException{
		if(filename != null && filename.length() > 0 && value != null)
			toBinaryFile(createStream(filename, false), value);
	}

	/**
	 * Writes to a binary file.
	 * 
	 * @param out Instance that contains the stream.
	 * @param value Byte array that contains the data.
	 * @throws IOException Occurs when was not possible to write the file.
	 */
	public static void toBinaryFile(FileOutputStream out, byte[] value) throws IOException{
		if(out != null && value != null){
			out.write(value);
			out.close();
		}
	}

	/**
	 * Reads a binary file.
	 * 
	 * @param file Instance that contains the definition of the file.
	 * @return Byte array that contains the data of the file.
	 * @throws IOException Occurs when was not possible to read the file.
	 */
	public static byte[] fromBinaryFile(File file) throws IOException{
		FileInputStream stream = null;
		
		try {
			if(file != null){
				stream = new FileInputStream(file);
				
				return ByteUtil.fromBinaryStream(stream);
			}
	
			return null;
		}
		finally{
			if(stream != null)
				stream.close();
		}
	}

	/**
	 * Reads a binary file.
	 * 
	 * @param filename String that contains the name of the file.
	 * @return Byte array that contains the data of the file.
	 * @throws IOException Occurs when was not possible to read the file.
	 */
	public static byte[] fromBinaryFile(String filename) throws IOException{
		if(filename != null && filename.length() > 0)
			return fromBinaryFile(new File(filename));

		return null;
	}

	/**
	 * Reads a text file.
	 * 
	 * @param file Instance that contains the definition of the file.
	 * @return String that contains the data of the file.
	 * @throws IOException Occurs when was not possible to read the file.
	 */
	public static String fromTextFile(File file) throws IOException{
		FileInputStream stream = null;
		
		try {
			if(file != null){
				stream = new FileInputStream(file);
				
				return new String(ByteUtil.fromTextStream(stream), getDefaultEncoding());
			}
	
			return null;
		}
		finally{
			if(stream != null)
				stream.close();
		}
	}

	/**
	 * Reads a text file.
	 * 
	 * @param filename String that contains the name of the file.
	 * @return String that contains the data of the file.
	 * @throws IOException Occurs when was not possible to read the file.
	 */
	public static String fromTextFile(String filename) throws IOException{
		if(filename != null && filename.length() > 0)
			return fromTextFile(new File(filename));

		return null;
	}
}