package br.com.concepting.framework.audit.appenders;

import br.com.concepting.framework.audit.Auditor;

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

    @Override
    public void activateOptions(){
        if(this.filename != null && !this.filename.isEmpty()){
            try{
                setWriter(new PrintWriter(new FileOutputStream(this.filename, true)));
            }
            catch(IOException ignored){
            }
        }
    }
}