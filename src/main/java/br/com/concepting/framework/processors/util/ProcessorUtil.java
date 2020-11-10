package br.com.concepting.framework.processors.util;

import java.util.Map;

import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.processors.EvaluateProcessor;
import br.com.concepting.framework.processors.ExpressionProcessor;
import br.com.concepting.framework.processors.GenericProcessor;
import br.com.concepting.framework.processors.IterateProcessor;
import br.com.concepting.framework.processors.SetProcessor;
import br.com.concepting.framework.processors.constants.ProcessorConstants;
import br.com.concepting.framework.util.PropertyUtil;
import br.com.concepting.framework.util.helpers.XmlNode;

/**
 * Class responsible to manipulate the processors used to generate code.
 * 
 * @author fvilarinho
 * @since 2.0.0
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
public abstract class ProcessorUtil{
	private static Map<String, Class<? extends GenericProcessor>> validProcessors          = null;
	private static Map<String, String>                            validProcessorAttributes = null;

	static{
		validProcessors = PropertyUtil.instantiate(Constants.DEFAULT_MAP_CLASS);
		validProcessors.put(ProcessorConstants.DEFAULT_EVALUATE_PROCESSOR_ID, EvaluateProcessor.class);
		validProcessors.put(ProcessorConstants.DEFAULT_ITERATE_PROCESSOR_ID, IterateProcessor.class);
		validProcessors.put(ProcessorConstants.DEFAULT_FOR_PROCESSOR_ID, IterateProcessor.class);
		validProcessors.put(ProcessorConstants.DEFAULT_EXPRESSION_PROCESSOR_ID, ExpressionProcessor.class);
		validProcessors.put(ProcessorConstants.DEFAULT_IF_PROCESSOR_ID, ExpressionProcessor.class);
		validProcessors.put(ProcessorConstants.DEFAULT_SET_PROCESSOR_ID, SetProcessor.class);

		validProcessorAttributes = PropertyUtil.instantiate(Constants.DEFAULT_MAP_CLASS);
		validProcessorAttributes.put(ProcessorConstants.DEFAULT_EXPRESSION_PROCESSOR_ID, Constants.VALUE_ATTRIBUTE_ID);
		validProcessorAttributes.put(ProcessorConstants.DEFAULT_EXPRESSION_PROCESSOR_ID.substring(0, 4), Constants.VALUE_ATTRIBUTE_ID);
		validProcessorAttributes.put(Constants.VALUES_ATTRIBUTE_ID, Constants.VALUE_ATTRIBUTE_ID);
		validProcessorAttributes.put(ProcessorConstants.VAR_ATTRIBUTE_ID, Constants.NAME_ATTRIBUTE_ID);
	}

	/**
	 * Returns the class of the logic processor based on a XML content.
	 * 
	 * @param node Instance that contains the content XML.
	 * @return Class of the logic processor.
	 */
	public static Class<? extends GenericProcessor> getClass(XmlNode node){
		Class<? extends GenericProcessor> clazz = null;

		if(node != null && (node.getNamespace() == null || node.getNamespace().length() == 0)){
			clazz = validProcessors.get(node.getName());

			if(clazz == null)
				clazz = GenericProcessor.class;
		}
		else
			clazz = GenericProcessor.class;

		return clazz;
	}

	/**
	 * Returns the identifier of the attribute of the logic processor.
	 * 
	 * @param alias String that contains the identifier/alias.
	 * @return String that contains the identifier.
	 */
	public static String getAttributeNameByAlias(String alias){
		String result = null;

		if(alias != null && alias.length() > 0){
			result = validProcessorAttributes.get(alias);

			if(result == null || result.length() == 0)
				result = alias;
		}

		return result;
	}
}