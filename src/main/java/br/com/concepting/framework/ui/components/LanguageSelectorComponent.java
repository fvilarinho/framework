package br.com.concepting.framework.ui.components;

import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.constants.SystemConstants;
import br.com.concepting.framework.controller.action.types.ActionType;
import br.com.concepting.framework.controller.form.constants.ActionFormConstants;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.model.MainConsoleModel;
import br.com.concepting.framework.model.util.ModelUtil;
import br.com.concepting.framework.resources.SystemResources;
import br.com.concepting.framework.ui.constants.UIConstants;
import br.com.concepting.framework.util.LanguageUtil;
import br.com.concepting.framework.util.PropertyUtil;
import br.com.concepting.framework.util.types.MethodType;

import java.util.Collection;
import java.util.Locale;

/**
 * Class that defines the language selector component.
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
 * along with this program.  If not, see <a href="http://www.gnu.org/licenses"></a>.</pre>
 */
public class LanguageSelectorComponent extends ListPropertyComponent{
    private static final long serialVersionUID = -3260096810965124526L;

    @Override
    protected String getOptionLabel(Object option, int level) throws InternalErrorException{
        if(option != null){
            Locale language = LanguageUtil.getLanguageByString(option.toString());
            
            return language.getDisplayName(getCurrentLanguage());
        }
        
        return null;
    }

    @Override
    protected void buildResources() throws InternalErrorException{
        setResourcesId(UIConstants.DEFAULT_COMMON_RESOURCES_ID);
        setResourcesKey(SystemConstants.CURRENT_LANGUAGE_ATTRIBUTE_ID);
        
        super.buildResources();
    }

    @Override
    protected void buildName() throws InternalErrorException{
        String name = getName();
        
        if(name == null || name.isEmpty())
            setName(SystemConstants.CURRENT_LANGUAGE_ATTRIBUTE_ID);
        
        super.buildName();
    }

    @Override
    protected void buildEvents() throws InternalErrorException{
        if(getPropertyInfo() == null){
            SystemResources systemResources = getSystemResources();
            
            if(systemResources != null){
                Class<? extends MainConsoleModel> modelClass = systemResources.getMainConsoleClass();
                
                if(modelClass != null){
                    String contextPath = getContextPath();
                    String actionFormUrl = ModelUtil.getUrlByModel(modelClass);
                    
                    if(contextPath != null && !contextPath.isEmpty() && actionFormUrl != null && !actionFormUrl.isEmpty()){
                        StringBuilder url = new StringBuilder();
                        
                        url.append(contextPath);
                        url.append(actionFormUrl);
                        url.append(ActionFormConstants.DEFAULT_ACTION_FILE_EXTENSION);
                        
                        StringBuilder onChange = new StringBuilder();

                        onChange.append("submitRequest('");
                        onChange.append(MethodType.POST);
                        onChange.append("', '");
                        onChange.append(url);
                        onChange.append("', '");
                        onChange.append(ActionFormConstants.ACTION_ATTRIBUTE_ID);
                        onChange.append("=");
                        onChange.append(ActionType.CHANGE_CURRENT_LANGUAGE.getMethod());
                        onChange.append("&");
                        onChange.append(ActionFormConstants.FORWARD_ATTRIBUTE_ID);
                        onChange.append("=");
                        onChange.append(ActionFormConstants.DEFAULT_ROOT_FORWARD_ID);
                        onChange.append("&");
                        onChange.append(SystemConstants.CURRENT_LANGUAGE_ATTRIBUTE_ID);
                        onChange.append("=' + this.value);");
                        
                        setOnChange(onChange.toString());
                        
                        super.buildEvents();
                    }
                }
            }
        }
        else
            super.buildEvents();
    }

    @Override
    protected void initialize() throws InternalErrorException{
        SystemResources systemResources = getSystemResources();
        
        if(systemResources != null){
            Collection<Locale> availableLanguages = systemResources.getLanguages();
            
            if(availableLanguages != null && !availableLanguages.isEmpty()) {
                Collection<String> languages = PropertyUtil.instantiate(Constants.DEFAULT_LIST_CLASS);

                if(languages != null)
                    for (Locale availableLanguage : availableLanguages)
                        languages.add(availableLanguage.toString());

                setDatasetValues(languages);
            }
                
            String name = getName();

            if(name == null || name.isEmpty()){
                Locale currentLanguage = getCurrentLanguage();

                if(currentLanguage != null)
                    setValue(currentLanguage.toString());
            }
        }

        super.initialize();
    }
}