package br.com.innovativethinking.framework.audit.appenders;

import java.io.PrintWriter;

import br.com.innovativethinking.framework.audit.Auditor;
import br.com.innovativethinking.framework.audit.appenders.helpers.ConsoleLayout;
import br.com.innovativethinking.framework.exceptions.InternalErrorException;

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
public class ConsoleAuditorAppender extends BaseAuditorAppender{
	/**
	 * Constructor - Initialize the storage.
	 * 
	 * @param auditor Instance that contains the auditing.
	 */
	public ConsoleAuditorAppender(Auditor auditor){
		super(auditor);
	}

	/**
	 * @see org.apache.log4j.WriterAppender#requiresLayout()
	 */
	public boolean requiresLayout(){
		return true;
	}

	/**
	 * @see br.com.innovativethinking.framework.audit.appenders.BaseAuditorAppender#initializeLayout()
	 */
	public void initializeLayout() throws InternalErrorException{
		ConsoleLayout appenderLayout = new ConsoleLayout();

		setLayout(appenderLayout);
	}

	/**
	 * @see org.apache.log4j.spi.OptionHandler#activateOptions()
	 */
	public void activateOptions(){
		setWriter(new PrintWriter(System.out));
	}
}