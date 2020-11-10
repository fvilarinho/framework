package br.com.innovativethinking.framework.audit.appenders.helpers;

import org.apache.log4j.PatternLayout;
import org.apache.log4j.spi.LoggingEvent;

import br.com.innovativethinking.framework.audit.model.AuditorModel;
import br.com.innovativethinking.framework.model.util.ModelUtil;
import br.com.innovativethinking.framework.util.StringUtil;

/**
 * Class that defines the basic implementation for the layouts of the auditing's
 * messages.
 * 
 * @author fvilarinho
 * @since 1.0.0
 * ss
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
public class ConsoleLayout extends PatternLayout{
	private AuditorModel model = null;

	/**
	 * Returns the auditing data model.
	 *
	 * @param <A> Class that defines the data model.
	 * @return Instance that contains the data model.
	 */
	@SuppressWarnings("unchecked")
	public <A extends AuditorModel> A getModel(){
		return (A)this.model;
	}

	/**
	 * Defines the auditing data model.
	 *
	 * @param model Instance that contains the data model.
	 */
	public void setModel(AuditorModel model){
		this.model = model;
	}

	/**
	 * @see org.apache.log4j.PatternLayout#format(org.apache.log4j.spi.LoggingEvent)
	 */
	public String format(LoggingEvent event){
		if(event != null){
			try{
				StringBuilder result = new StringBuilder();
				
				result.append(ModelUtil.toAuditableString(this.model));
				result.append(StringUtil.getLineBreak());
				
				return result.toString();
			}
			catch(Throwable e){
			}
		}

		return null;
	}
}