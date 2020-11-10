package br.com.concepting.framework.controller.helpers;

import br.com.concepting.framework.util.types.ContentType;
import br.com.concepting.framework.util.types.PropertyType;

/**
 * Class responsible to store the request parameters.
 * 
 * @author fvilarinho
 * @since 3.3.0
 * 
 * <pre>Copyright (C) 2007 Innovative Thinking.
 *
 * This program is free software: you can reØdistribute it and/or modify
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
public class RequestParameterInfo implements Comparable<RequestParameterInfo>{
	private PropertyType type            = null;
	private String       name            = null;
	private String       value           = null;
	private String       values[]        = null;
	private byte         content[]       = null;
	private ContentType  contentType     = null;
	private String       contentFilename = null;

	/**
	 * Returns the type of the parameter.
	 * 
	 * @return Instance that contains the type.
	 */
	public PropertyType getType(){
		return this.type;
	}

	/**
	 * Defines the type of the parameter.
	 * 
	 * @param type Instance that contains the type.
	 */
	public void setType(PropertyType type){
		this.type = type;
	}

	/**
	 * Returns an array with all values of a request parameter.
	 * 
	 * @return Array that contains the values.
	 */
	public String[] getValues(){
		return this.values;
	}

	/**
	 * Defines an array with all values of a request parameter.
	 * 
	 * @param values Array that contains the values.
	 */
	public void setValues(String[] values){
		this.values = values;
	}

	/**
	 * Returns the content of a upload.
	 * 
	 * @return Array that contains the upload content.
	 */
	public byte[] getContent(){
		return this.content;
	}

	/**
	 * Defines the content of a upload.
	 * 
	 * @param content Array that contains the upload content.
	 */
	public void setContent(byte[] content){
		this.content = content;
	}

	/**
	 * Returns the identifier of the request parameter.
	 * 
	 * @return String that contains the identifier.
	 */
	public String getName(){
		return this.name;
	}

	/**
	 * Defines the identifier of the request parameter.
	 * 
	 * @param name String that contains the identifier.
	 */
	public void setName(String name){
		this.name = name;
	}

	/**
	 * Returns the first value of the request parameter.
	 * 
	 * @return String that contains the value.
	 */
	public String getValue(){
		return this.value;
	}

	/**
	 * Defines the first value of the request parameter.
	 * 
	 * @param value String that contains the value.
	 */
	public void setValue(String value){
		this.value = value;
	}

	/**
	 * Returns the content type of the upload.
	 * 
	 * @return Instance that contains the content type.
	 */
	public ContentType getContentType(){
		return this.contentType;
	}

	/**
	 * Defines the content type of the upload.
	 * 
	 * @param contentType Instance that contains the content type.
	 */
	public void setContentType(ContentType contentType){
		this.contentType = contentType;
	}

	/**
	 * Returns the content filename of the upload.
	 * 
	 * @return String that contains the filename.
	 */
	public String getContentFilename(){
		return this.contentFilename;
	}

	/**
	 * Defines the content filename of the upload.
	 * 
	 * @param contentFilename String that contains the filename.
	 */
	public void setContentFilename(String contentFilename){
		this.contentFilename = contentFilename;
	}

	@Override
	public int compareTo(RequestParameterInfo o){
		Integer result = 0;
		String compareValue = o.getName();
		String currentValue = getName();

		if(currentValue != null && compareValue != null)
			result = currentValue.compareTo(compareValue);
		else if(currentValue == null && compareValue != null)
			result = -1;
		else if(currentValue != null && compareValue == null)
			result = 1;

		return result;
	}
}