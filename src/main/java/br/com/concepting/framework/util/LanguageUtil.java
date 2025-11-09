package br.com.concepting.framework.util;

import br.com.concepting.framework.resources.SystemResources;
import br.com.concepting.framework.resources.SystemResourcesLoader;
import br.com.concepting.framework.resources.exceptions.InvalidResourcesException;

import java.util.Locale;

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
 * the Free Software Foundation, either version 3 of the License or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <a href="https://www.gnu.org/licenses"></a>.</pre>
 */
public class LanguageUtil{
    /**
     * Returns the instance of the language based on its identifier.
     *
     * @param value String that contains the identifier of the language.
     * @return Instance that contains the language.
     */
    public static Locale getLanguageByString(String value){
        Locale language = null;

        if(value != null && !value.isEmpty()){
            String[] languageBuffer = StringUtil.split(value, "_");
            
            if(languageBuffer.length == 1)
                language = Locale.of(languageBuffer[0]);
            else if(languageBuffer.length == 2)
                language = Locale.of(languageBuffer[0], languageBuffer[1]);
            else
                language = Locale.of(languageBuffer[0], languageBuffer[1], languageBuffer[2]);
        }
        
        return language;
    }

    protected static SystemResources getResources() {
        try {
            SystemResourcesLoader loader = new SystemResourcesLoader();

            return loader.getDefault();
        }
        catch(InvalidResourcesException ignored){
        }

        return null;
    }

    /**
     * Returns the instance of the default language.
     *
     * @return Instance that contains the language.
     */
    public static Locale getDefaultLanguage(){
        SystemResources resources = getResources();

        if(resources != null)
            return resources.getDefaultLanguage();

        return Locale.getDefault();
    }
}