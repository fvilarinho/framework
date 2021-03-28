package br.com.concepting.framework.audit.appenders;

import br.com.concepting.framework.audit.Auditor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Class that defines the file storage for auditing's messages.
 *
 * @author fvilarinho
 * @since 3.0.0
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
public class FileAuditorAppender extends ConsoleAuditorAppender{
    private String filename = null;
    
    /**
     * Constructor - Initialize the storage.
     *
     * @param auditor Instance that contains the auditing.
     */
    public FileAuditorAppender(Auditor auditor){
        super(auditor);
    }
    
    /**
     * Returns the filename.
     *
     * @return String that contains the filename.
     */
    public String getFilename(){
        return this.filename;
    }
    
    /**
     * Defines the filename.
     *
     * @param filename String that contains the filename.
     */
    public void setFilename(String filename){
        this.filename = filename;
    }
    
    /**
     * @see org.apache.log4j.spi.OptionHandler#activateOptions()
     */
    public void activateOptions(){
        if(this.filename != null && this.filename.length() > 0){
            try{
                setWriter(new PrintWriter(new FileOutputStream(new File(this.filename), true)));
            }
            catch(IOException e){
            }
        }
    }
}