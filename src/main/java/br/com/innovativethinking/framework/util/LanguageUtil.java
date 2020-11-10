package br.com.innovativethinking.framework.util;

import java.util.Locale;

import br.com.innovativethinking.framework.exceptions.InternalErrorException;
import br.com.innovativethinking.framework.resources.SystemResources;
import br.com.innovativethinking.framework.resources.SystemResourcesLoader;

/**
 * Class responsible to manipulate the language resources.
 * 
 * @author fvilarinho
 * @since 3.0.0
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
public class LanguageUtil{
	/**
	 * Returns the instance of the language based on its identifier.
	 * 
	 * @param value String that contains the identifier of the language.
	 * @return Instance that contains the language.
	 */
	public static Locale getLanguageByString(String value){
		if(value != null && value.length() > 0){
			Locale language = null;
			String languageBuffer[] = StringUtil.split((String)value, "_");

			if(languageBuffer.length == 1)
				language = new Locale(languageBuffer[0]);
			else if(languageBuffer.length == 2)
				language = new Locale(languageBuffer[0], languageBuffer[1]);
			else if(languageBuffer.length == 3)
				language = new Locale(languageBuffer[0], languageBuffer[1], languageBuffer[2]);

			return language;
		}

		return null;
	}

	/**
	 * Returns the instance of the default language.
	 * 
	 * @return Instance that contains the language.
	 */
	public static Locale getDefaultLanguage(){
		try{
			SystemResourcesLoader loader = new SystemResourcesLoader();
			SystemResources resources = loader.getDefault();

			if(resources != null){
				Locale language = resources.getDefaultLanguage();

				if(language == null)
					language = Locale.getDefault();

				return language;
			}
		}
		catch(InternalErrorException e){
		}

		return Locale.getDefault();
	}
}