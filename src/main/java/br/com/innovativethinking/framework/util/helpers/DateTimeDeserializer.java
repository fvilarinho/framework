package br.com.innovativethinking.framework.util.helpers;

import java.io.IOException;
import java.text.ParseException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.gson.JsonParseException;

import br.com.innovativethinking.framework.constants.Constants;
import br.com.innovativethinking.framework.util.DateTimeUtil;

/**
 * Class that defines the date/time deserializae.
 * 
 * @author fvilarinho
 * @since 3.7.0
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
public class DateTimeDeserializer extends JsonDeserializer<DateTime>{
	/**
	 * @see com.fasterxml.jackson.databind.JsonDeserializer#deserialize(com.fasterxml.jackson.core.JsonParser, com.fasterxml.jackson.databind.DeserializationContext)
	 */
	public DateTime deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException{
		try{
			return DateTimeUtil.parse(parser.getText(), Constants.DEFAULT_DATE_TIME_PATTERN);
		} 
		catch(ParseException e){
			throw new JsonParseException(e);
		}
	}
}