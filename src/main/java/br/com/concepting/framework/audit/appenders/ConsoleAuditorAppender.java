package br.com.concepting.framework.audit.appenders;

import br.com.concepting.framework.audit.Auditor;
import br.com.concepting.framework.audit.appenders.helpers.ConsoleLayout;
import br.com.concepting.framework.exceptions.InternalErrorException;

import java.io.PrintWriter;

/**
 * Class that defines the console storage for auditing's messages.
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
public class ConsoleAuditorAppender extends BaseAuditorAppender{
    /**
     * Constructor - Initialize the storage.
     *
     * @param auditor Instance that contains the auditing.
     */
    public ConsoleAuditorAppender(Auditor auditor){
        super(auditor);
    }
    
    @Override
    public boolean requiresLayout(){
        return true;
    }

    @Override
    public void initializeLayout() throws InternalErrorException{
        ConsoleLayout appenderLayout = new ConsoleLayout();
        
        setLayout(appenderLayout);
    }

    @Override
    public void activateOptions(){
        setWriter(new PrintWriter(System.out));
    }
}