package br.com.concepting.framework.util.types;

/**
 * Class that defines the types of content.
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
 * along with this program.  If not, see <a href="http://www.gnu.org/licenses"></a>.</pre>
 */
public enum ContentType{
    /**
     * No content type.
     */
    NONE,
    
    /**
     * Adobe PDF.
     */
    PDF("application/pdf", ".pdf"),
    
    /**
     * Hypertext Markup Language.
     */
    HTML("text/html", ".html"),
    
    /**
     * Javascript Language.
     */
    JS("text/javascript", ".js"),
    
    /**
     * CSS file.
     */
    CSS("text/css", ".css"),
    
    /**
     * Plain text.
     */
    TXT("text/plain", ".txt"),
    
    /**
     * CSV file.
     */
    CSV("text/csv", ".csv"),
    
    /**
     * Microsoft Word 2003 or higher.
     */
    DOCX("application/vnd.openxmlformats-officedocument.wordprocessingml.document", ".docx"),
    
    /**
     * Microsoft Word 2000 or lower.
     */
    DOC("application/msword", ".doc"),
    
    /**
     * Microsoft Excel 2003 or higher.
     */
    XLSX("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", ".xlsx"),
    
    /**
     * Microsoft Excel 2000 or lower.
     */
    XLS("application/vnd.ms-excel", ".xls"),
    
    /**
     * Microsoft PowerPoint 2003 or higher.
     */
    PPTX("application/vnd.openxmlformats-officedocument.presentationml.presentation", ".pptx"),
    
    /**
     * Microsoft PowerPoint 2000 or lower.
     */
    PPT("application/vnd.ms-powerpoint", ".ppt"),
    
    /**
     * eXtensible Markup Language.
     */
    XML("text/xml", ".xml"),
    
    /**
     * Rich Text Files.
     */
    RTF("text/rtf", ".rtf"),
    
    /**
     * Portable Network Graphics.
     */
    PNG("image/png", ".png"),
    
    /**
     * Joint Photographics Experts Group.
     */
    JPG("image/jpeg", ".jpg"),
    
    /**
     * Graphics Interchange Format.
     */
    GIF("image/gif", ".gif"),
    
    /**
     * JavaScript Object Notation.
     */
    JSON("application/json", ".json"),
    
    /**
     * Binary content.
     */
    BINARY("application/octet-stream"),
    
    /**
     * Action form data.
     */
    ACTION_FORM_DATA("application/x-www-form-urlencoded"),
    
    /**
     * Multipart content.
     */
    MULTIPART_DATA("multipart/form-data"),
    
    /**
     * Multipart content.
     */
    MULTIPART_ALTERNATIVE("multipart/alternative"),
    
    /**
     * Multipart content.
     */
    MULTIPART_RELATED("multipart/related"),
    
    /**
     * Multipart content.
     */
    MULTIPART_MIXED("multipart/mixed");
    
    private String mimeType;
    private String extension;

    /**
     * Constructor - Defines the content type.
     */
    ContentType(){
    }

    /**
     * Constructor - Defines the content type.
     *
     * @param mimeType String that contains the identifier of the type.
     */
    ContentType(String mimeType){
        this();

        setMimeType(mimeType);
    }
    
    /**
     * Constructor - Defines the content type.
     *
     * @param mimeType String that contains the identifier of the type.
     * @param extension String that contains the file extension.
     */
    ContentType(String mimeType, String extension){
        this(mimeType);
        
        setExtension(extension);
    }
    
    /**
     * Returns the identifier of the type.
     *
     * @return String that contains the identifier of the type.
     */
    public String getMimeType(){
        return this.mimeType;
    }
    
    /**
     * Defines the identifier of the type.
     *
     * @param mimeType String that contains the identifier of the type.
     */
    public void setMimeType(String mimeType){
        this.mimeType = mimeType;
    }
    
    /**
     * Returns the file extension of the type.
     *
     * @return String that contains the file extension.
     */
    public String getExtension(){
        return this.extension;
    }
    
    /**
     * Defines the file extension of the type.
     *
     * @param extension String that contains the file extension.
     */
    public void setExtension(String extension){
        this.extension = extension;
    }
    
    /**
     * Returns the instance of the content type.
     *
     * @param contentType String that contains the identifier of the type.
     * @return Instance that contains the content type.
     * @throws IllegalArgumentException Occurs when was not possible to identify
     * the content type.
     */
    public static ContentType toContentType(String contentType) throws IllegalArgumentException{
        if(contentType == null)
            return ContentType.NONE;
        
        int pos = contentType.indexOf(";");
        
        if(pos >= 0)
            contentType = contentType.substring(0, pos);
        
        for(ContentType constant: values())
            if((constant.getMimeType() != null && constant.getMimeType().equalsIgnoreCase(contentType)) || (constant.getExtension() != null && contentType.toLowerCase().endsWith(constant.getExtension().toLowerCase())))
                return constant;
        
        return valueOf(contentType.toUpperCase());
    }
}