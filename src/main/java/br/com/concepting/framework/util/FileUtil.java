package br.com.concepting.framework.util;

import br.com.concepting.framework.constants.Constants;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;

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
public class FileUtil{
    /**
     * Returns the directory separator character.
     *
     * @return String that contains the character.
     */
    public static String getDirectorySeparator(){
        return FileSystems.getDefault().getSeparator();
    }

    /**
     * Returns the temporary directory path.
     *
     * @return String that contains the path.
     */
    public static String getTempDirectory(){
        return System.getProperty("java.io.tmpdir");
    }

    /**
     * Download the content from the url and writes it in a file.
     *
     * @param url String that contains the url.
     * @param filename String that contains the filename.
     * @throws IOException Occurs when was not possible to read the file.
     */
    public static void fromUrlToFile(String url, String filename) throws IOException{
        if(url == null || url.isEmpty() || filename == null || filename.isEmpty())
            return;

        File file = new File(filename);

        if(!file.getParentFile().exists())
            file.getParentFile().mkdirs();

        URL path = URI.create(url).toURL();

        try (InputStream in = path.openStream()) {
            try (FileOutputStream out = new FileOutputStream(filename)) {
                ReadableByteChannel inChannel = Channels.newChannel(in);
                FileChannel outChannel = out.getChannel();

                outChannel.transferFrom(inChannel, 0, Long.MAX_VALUE);
            }
        }
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
    private static FileOutputStream createStream(String filename, boolean append) throws IOException{
        if(filename == null || filename.isEmpty())
            return null;
        
        File file = new File(filename);
        
        if(!file.exists()){
            File parentFile = file.getParentFile();
            
            if(parentFile != null && !parentFile.exists())
                parentFile.mkdirs();
        }
        
        return new FileOutputStream(file, append);
    }
    
    /**
     * Returns the default encoding.
     *
     * @return String that contains the encoding.
     */
    public static String getDefaultEncoding(){
        String encoding = Charset.defaultCharset().displayName();
        
        if(encoding == null || encoding.isEmpty())
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
    public static void toFile(String filename, String value) throws IOException{
        if(filename != null && !filename.isEmpty() && value != null)
            toFile(filename, value, false);
    }
    
    /**
     * Writes to a text file.
     *
     * @param out Instance that contains the stream.
     * @param value String that contains the content of the file.
     * @throws IOException Occurs when was not possible to write the file.
     */
    public static void toFile(FileOutputStream out, String value) throws IOException{
        if(out != null && value != null)
            toFile(out, value, getDefaultEncoding());
    }
    
    /**
     * Writes to a text file.
     *
     * @param filename String that contains the filename.
     * @param value String that contains the data.
     * @param append Indicates if the data should be appended.
     * @throws IOException Occurs when was not possible to write the file.
     */
    public static void toFile(String filename, String value, boolean append) throws IOException{
        if(filename != null && !filename.isEmpty() && value != null)
            toFile(filename, value, getDefaultEncoding(), append);
    }
    
    /**
     * Writes to a text file.
     *
     * @param filename String that contains the filename.
     * @param value String that contains the data.
     * @param encoding String that contains the encoding.
     * @throws IOException Occurs when was not possible to write the file.
     */
    public static void toFile(String filename, String value, String encoding) throws IOException{
        if(filename != null && !filename.isEmpty() && value != null)
            toFile(filename, value, encoding, false);
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
    public static void toFile(String filename, String value, String encoding, boolean append) throws IOException{
        if(encoding == null || encoding.isEmpty())
            encoding = getDefaultEncoding();

        toFile(createStream(filename, append), value, encoding);
    }
    
    /**
     * Writes to a text file.
     *
     * @param out Instance that contains the stream.
     * @param value String that contains the data.
     * @param encoding String that contains the encoding.
     * @throws IOException Occurs when was not possible to write the file.
     */
    public static void toFile(FileOutputStream out, String value, String encoding) throws IOException{
        if(out == null || value == null)
            return;
        
        if(encoding == null || encoding.isEmpty())
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
    public static void toFile(String filename, byte[] value) throws IOException{
        if(filename != null && !filename.isEmpty() && value != null)
            toFile(createStream(filename, false), value);
    }
    
    /**
     * Writes to a binary file.
     *
     * @param out Instance that contains the stream.
     * @param value Byte array that contains the data.
     * @throws IOException Occurs when was not possible to write the file.
     */
    public static void toFile(FileOutputStream out, byte[] value) throws IOException{
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
        
        try{
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
        if(filename != null && !filename.isEmpty())
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
        
        try{
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
     * @param filename String that contains the definition of the file.
     * @return String that contains the data of the file.
     * @throws IOException Occurs when was not possible to read the file.
     */
    public static String fromTextFile(String filename) throws IOException{
        if(filename != null && !filename.isEmpty())
            return fromTextFile(new File(filename));

        return null;
    }
}