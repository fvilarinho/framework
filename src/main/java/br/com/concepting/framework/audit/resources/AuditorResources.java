package br.com.concepting.framework.audit.resources;

import java.util.Collection;

import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.resources.BaseResources;
import br.com.concepting.framework.resources.FactoryResources;
import br.com.concepting.framework.util.PropertyUtil;
import br.com.concepting.framework.util.helpers.XmlNode;

/**
 * Class responsible to store the auditing resources.
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
public class AuditorResources extends BaseResources<XmlNode>{
	private static final long serialVersionUID = -4997789974946981759L;

	private String                       level     = null;
	private Collection<FactoryResources> appenders = null;

	/**
	 * Returns the auditing level of the messages.
	 *
	 * @return String that contains the auditing level of the messages.
	 */
	public String getLevel(){
		return this.level;
	}

	/**
	 * Defines the auditins level of the messages.
	 *
	 * @param level String that contains the auditing level of the messages.
	 */
	public void setLevel(String level){
		this.level = level;
	}

	/**
	 * Returns the list of all storages for the auditing's messages.
	 * 
	 * @param <C> Class that defines the type of list of the storages.
	 * @return List of all storages for the auditing's messages.
	 */
	@SuppressWarnings("unchecked")
	public <C extends Collection<FactoryResources>> C getAppenders(){
		return (C)this.appenders;
	}

	/**
	 * Adds a storage for the auditing's messages.
	 * 
	 * @param appender Instance that contains the storage.
	 */
	public void addAppender(FactoryResources appender){
		if(appender != null){
			if(this.appenders == null)
				this.appenders = PropertyUtil.instantiate(Constants.DEFAULT_LIST_CLASS);

			this.appenders.add(appender);
		}
	}
}