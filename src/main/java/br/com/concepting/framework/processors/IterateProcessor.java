package br.com.concepting.framework.processors;

import java.util.Collection;
import java.util.Locale;

import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.processors.constants.ProcessorConstants;
import br.com.concepting.framework.util.PropertyUtil;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.helpers.XmlNode;

/**
 * Class that defines the logic processor to iterate lists.
 * 
 * @author fvilarinho
 * @since 1.0.0
 *
 * <pre>Copyright (C) 2007 Concepting Inc. 
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
public class IterateProcessor extends EvaluateProcessor{
	private String  name  = null;
	private String  index = null;
	private Integer start = null;
	private Integer end   = null;
	
	/**
	 * Constructor - Defines the logic processor.
	 */
	public IterateProcessor(){
		super();
	}

	/**
	 * Constructor - Defines the logic processor.
	 * 
	 * @param domain String that contains the domain of the processing.
	 * @param declaration Instance that contains the object that will be
	 * processed.
	 * @param content Instance that contains the XML content.
	 * @param language Instance that contains the language used in the
	 * processing.
	 */
	public IterateProcessor(String domain, Object declaration, XmlNode content, Locale language){
		super(domain, declaration, content, language);
	}

	/**
	 * Returns the iteration start index.
	 * 
	 * @return Numeric value that contains the start index.
	 */
	public Integer getStart(){
		return this.start;
	}

	/**
	 * Defines the iteration start index.
	 * 
	 * @param start Numeric value that contains the start index.
	 */
	public void setStart(Integer start){
		this.start = start;
	}

	/**
	 * Returns the iteration end index.
	 * 
	 * @return Numeric value that contains the start index.
	 */
	public Integer getEnd(){
		return this.end;
	}

	/**
	 * Defines the iteration end index.
	 * 
	 * @param end Numeric value that contains the start index.
	 */
	public void setEnd(Integer end){
		this.end = end;
	}

	/**
	 * Returns the identifier of the iteration variable.
	 * 
	 * @return String that contains the identifier.
	 */
	public String getName(){
		return this.name;
	}

	/**
	 * Defines the identifier of the iteration variable.
	 * 
	 * @param name String that contains the identifier.
	 */
	public void setName(String name){
		this.name = name;
	}

	/**
	 * Returns the identifier of the iteration index.
	 * 
	 * @return String that contains the identifier.
	 */
	public String getIndex(){
		return this.index;
	}

	/**
	 * Defines the identifier of the iteration index.
	 * 
	 * @param index String that contains the identifier.
	 */
	public void setIndex(String index){
		this.index = index;
	}

	/**
	 * @see br.com.concepting.framework.processors.EvaluateProcessor#returnContent()
	 */
	protected Boolean returnContent(){
		return true;
	}

	/**
	 * @see br.com.concepting.framework.processors.GenericProcessor#process()
	 */
	public String process() throws InternalErrorException{
		Object value = evaluate();
		Object array[] = null;
		Object item = null;

		if(PropertyUtil.isCollection(value))
			array = ((Collection<?>)value).toArray();
		else if(PropertyUtil.isArray(value))
			array = (Object[])value;

		StringBuilder buffer = new StringBuilder();

		if(array != null && array.length > 0){
			setValue(null);

			if(this.start == null)
				this.start = 0;

			if(this.end == null)
				this.end = array.length;

			String result = null;

			for(int cont = this.start ; cont < array.length ; cont++){
				if(cont > this.end)
					break;

				item = array[cont];

				if(this.name != null && this.name.length() > 0)
					setVariable(this.name, item);

				if(this.index != null && this.index.length() > 0)
					setVariable(this.index, cont);

				setDeclaration(item);

				result = StringUtil.trim(super.process());

				if(result.length() > 0){
					if(buffer.length() > 0)
						buffer.append(StringUtil.getLineBreak());

					buffer.append(result);
				}
			}
		}

		if(buffer.length() == 0)
			return ProcessorConstants.DEFAULT_REMOVE_TAG_ID;

		return buffer.toString();
	}
}