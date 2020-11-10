package br.com.innovativethinking.framework.resources;

import java.util.Map;

import br.com.innovativethinking.framework.constants.Constants;
import br.com.innovativethinking.framework.util.PropertyUtil;
import br.com.innovativethinking.framework.util.helpers.XmlNode;

/**
 * Class responsible to store factory resources.
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
public class FactoryResources extends BaseResources<XmlNode>{
	private static final long serialVersionUID = 4087229074759796309L;

	private String              clazz   = null;
	private String              uri     = null;
	private String              type    = null;
	private Map<String, String> options = null;

	/**
	 * Returns the factory type.
	 * 
	 * @return String that contains the factory type.
	 */
	public String getType(){
		return this.type;
	}

	/**
	 * Defines the factory type.
	 * 
	 * @param type String that contains the factory type.
	 */
	public void setType(String type){
		this.type = type;
	}

	/**
	 * Returns the factory class.
	 * 
	 * @return String that contains the factory class.
	 */
	public String getClazz(){
		return this.clazz;
	}

	/**
	 * Defines the factory class.
	 * 
	 * @param clazz String that contains the factory class.
	 */
	public void setClazz(String clazz){
		this.clazz = clazz;
	}

	/**
	 * Returns the factory URI.
	 * 
	 * @return String that contains the factory URI.
	 */
	public String getUri(){
		return this.uri;
	}

	/**
	 * Defines the factory URI.
	 * 
	 * @param uri String that contains the factory URI.
	 */
	public void setUri(String uri){
		this.uri = uri;
	}

	/**
	 * Adds an factory option.
	 * 
	 * @param id String that contains the identifier of the option.
	 * @param value String that contains the value of the option.
	 */
	public void addOption(String id, String value){
		if(id != null && id.length() > 0 && value != null && value.length() > 0){
			if(this.options == null)
				this.options = PropertyUtil.instantiate(Constants.DEFAULT_MAP_CLASS);

			this.options.put(id, value);
		}
	}

	/**
	 * Defines the factory options.
	 * 
	 * @param options Instance that contains the factory options.
	 */
	public void setOptions(Map<String, String> options){
		this.options = options;
	}

	/**
	 * Returns the factory options.
	 * 
	 * @return Instance that contains the factory options.
	 */
	public Map<String, String> getOptions(){
		return this.options;
	}
}