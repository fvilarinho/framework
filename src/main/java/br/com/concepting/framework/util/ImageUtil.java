package br.com.concepting.framework.util;

import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.util.types.ContentType;
import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicException;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import net.sf.jmimemagic.MagicParseException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Class responsible to manipulate images.
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
public class ImageUtil{
    /**
     * Returns the unique identifier of an image.
     *
     * @param imageData Array that contains the image data.
     * @return String that contains the unique identifier.
     * @throws IOException Occurs when was not possible to execute the operation.
     */
    public static String getImageId(byte[] imageData) throws IOException{
        if(imageData != null && imageData.length > 0){
            try{
                MessageDigest digest = MessageDigest.getInstance(Constants.DEFAULT_DIGEST_ALGORITHM_ID);
                
                return ByteUtil.toHexadecimal(digest.digest(imageData));
            }
            catch(NoSuchAlgorithmException e){
                throw new IOException(e);
            }
        }
        
        return null;
    }
    
    /**
     * Returns the unique identifier of an image.
     *
     * @param imageStream Stream that contains the image data.
     * @return String that contains the unique identifier.
     * @throws IOException Occurs when was not possible to execute the operation.
     */
    public static String getImageId(InputStream imageStream) throws IOException{
        if(imageStream == null)
            return null;
        
        return getImageId(ByteUtil.fromBinaryStream(imageStream));
    }
    
    /**
     * Returns the unique identifier of an image.
     *
     * @param imageFile Instance that contains the file information of the image.
     * @return String that contains the unique identifier.
     * @throws IOException Occurs when was not possible to execute the operation.
     */
    public static String getImageId(File imageFile) throws IOException{
        if(imageFile == null)
            return null;
        
        return getImageId(new FileInputStream(imageFile));
    }
    
    /**
     * Returns the unique identifier of an image.
     *
     * @param imageFilename Instance that contains the file path of the image.
     * @return String that contains the unique identifier.
     * @throws IOException Occurs when was not possible to execute the operation.
     */
    public static String getImageId(String imageFilename) throws IOException{
        if(imageFilename == null || imageFilename.isEmpty())
            return null;
        
        return getImageId(new File(imageFilename));
    }
    
    /**
     * Returns the content-type of an image.
     *
     * @param imageData Byte array of the image.
     * @return Instance that contains the content type.
     * @throws IOException Occurs when was not possible to get the content type.
     */
    public static ContentType getImageFormat(byte[] imageData) throws IOException{
        if(imageData != null && imageData.length > 0){
            try{
                String contentType = Magic.getMagicMatch(imageData, false).getMimeType();
                
                return ContentType.toContentType(contentType);
            }
            catch(MagicParseException | MagicMatchNotFoundException | MagicException e){
                throw new IOException(e);
            }
        }
        
        return null;
    }
    
    /**
     * Returns the content-type of an image.
     *
     * @param imageStream Byte array of the image.
     * @return Instance that contains the content type.
     * @throws IOException Occurs when was not possible to get the content type.
     */
    public static ContentType getImageFormat(InputStream imageStream) throws IOException{
        if(imageStream == null)
            return null;
        
        return getImageFormat(ByteUtil.fromBinaryStream(imageStream));
    }
    
    /**
     * Returns the content-type of an image.
     *
     * @param imageFilename String that contains the image filename.
     * @return Instance that contains the content type.
     * @throws IOException Occurs when was not possible to get the content type.
     */
    public static ContentType getImageFormat(String imageFilename) throws IOException{
        if(imageFilename != null && !imageFilename.isEmpty())
            return getImageFormat(new File(imageFilename));
        
        return null;
    }
    
    /**
     * Returns the content-type of an image.
     *
     * @param imageFile Instance that contains the image file.
     * @return Instance that contains the content type.
     * @throws IOException Occurs when was not possible to get the content type.
     */
    public static ContentType getImageFormat(File imageFile) throws IOException{
        if(imageFile != null){
            FileInputStream imageStream = new FileInputStream(imageFile);
            
            return getImageFormat(imageStream);
        }
        
        return null;
    }
}