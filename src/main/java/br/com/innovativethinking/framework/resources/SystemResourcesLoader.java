package br.com.innovativethinking.framework.resources;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

import br.com.innovativethinking.framework.constants.Constants;
import br.com.innovativethinking.framework.constants.SystemConstants;
import br.com.innovativethinking.framework.controller.form.constants.ActionFormConstants;
import br.com.innovativethinking.framework.resources.exceptions.InvalidResourcesException;
import br.com.innovativethinking.framework.resources.helpers.ActionFormForwardResources;
import br.com.innovativethinking.framework.resources.helpers.ActionFormResources;
import br.com.innovativethinking.framework.service.constants.ServiceConstants;
import br.com.innovativethinking.framework.util.LanguageUtil;
import br.com.innovativethinking.framework.util.PropertyUtil;
import br.com.innovativethinking.framework.util.helpers.XmlNode;

/**
 * Class responsible to manipulate of the system resources.
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
public class SystemResourcesLoader extends XmlResourcesLoader<SystemResources>{
	/**
	 * Constructor - Manipulates the default resources.
	 * 
	 * @throws InvalidResourcesException Occurs when the resources could not be read.
	 */
	public SystemResourcesLoader() throws InvalidResourcesException{
		super(SystemConstants.DEFAULT_RESOURCES_ID);
	}

	/**
	 * Constructor - Manipulates specific resources.
	 * 
	 * @param resourcesDirname String that contains the directory where the resources
	 * are stored.
	 * @throws InvalidResourcesException Occurs when the resources could not be read.
	 */
	public SystemResourcesLoader(String resourcesDirname) throws InvalidResourcesException{
		super(resourcesDirname, SystemConstants.DEFAULT_RESOURCES_ID);
	}

	/**
	 * @see br.com.innovativethinking.framework.resources.XmlResourcesLoader#parseResources(br.com.innovativethinking.framework.util.helpers.XmlNode)
	 */
	public SystemResources parseResources(XmlNode resourcesNode) throws InvalidResourcesException{
		String resourcesDirname = getResourcesDirname();
		String resourcesId = getResourcesId();
		SystemResources resources = new SystemResources();

		resources.setId(Constants.DEFAULT_ATTRIBUTE_ID);
		resources.setDefault(true);

		XmlNode mainConsoleNode = resourcesNode.getNode(SystemConstants.MAIN_CONSOLE_ATTRIBUTE_ID);

		if(mainConsoleNode != null){
			XmlNode classNode = mainConsoleNode.getNode(Constants.CLASS_ATTRIBUTE_ID);

			if(classNode != null){
				String value = classNode.getValue();
	
				if(value != null && value.length() > 0){
					try{
						resources.setMainConsoleClass(classNode.getValue());
					}
					catch(ClassNotFoundException | ClassCastException e){
						throw new InvalidResourcesException(resourcesDirname, resourcesId, classNode.getText());
					}
				}
			}
		}

		XmlNode skinsNode = resourcesNode.getNode(SystemConstants.SKINS_ATTRIBUTE_ID);

		if(skinsNode == null)
			throw new InvalidResourcesException(resourcesDirname, resourcesId, resourcesNode.getText());

		List<XmlNode> skinsChildNodes = skinsNode.getChildren();

		if(skinsChildNodes == null || skinsChildNodes.size() == 0)
			throw new InvalidResourcesException(resourcesDirname, resourcesId, skinsNode.getText());

		Collection<String> skins = PropertyUtil.instantiate(Constants.DEFAULT_LIST_CLASS);
		String skin = null;
		String defaultSkin = null;

		for(XmlNode skinChildNode : skinsChildNodes){
			skin = skinChildNode.getAttribute(Constants.IDENTITY_ATTRIBUTE_ID);

			if(skin != null && skin.length() > 0){
				if(Boolean.valueOf(skinChildNode.getAttribute(Constants.DEFAULT_ATTRIBUTE_ID)))
					defaultSkin = skin;

				skins.add(skin);
			}
		}

		if(skins == null || skins.size() == 0)
			throw new InvalidResourcesException(resourcesDirname, resourcesId, skinsNode.getText());

		if(defaultSkin == null || defaultSkin.length() == 0)
			defaultSkin = skins.iterator().next();

		resources.setSkins(skins);
		resources.setDefaultSkin(defaultSkin);

		XmlNode languagesNode = resourcesNode.getNode(SystemConstants.LANGUAGES_ATTRIBUTE_ID);

		if(languagesNode == null)
			throw new InvalidResourcesException(resourcesDirname, resourcesId, resourcesNode.getText());

		List<XmlNode> languagesChildNodes = languagesNode.getChildren();

		if(languagesChildNodes == null || languagesChildNodes.size() == 0)
			throw new InvalidResourcesException(resourcesDirname, resourcesId, languagesNode.getText());

		Collection<Locale> languages = PropertyUtil.instantiate(Constants.DEFAULT_LIST_CLASS);
		String languageBuffer = null;
		Locale language = null;
		Locale defaultLanguage = null;

		for(XmlNode languageChildNode : languagesChildNodes){
			languageBuffer = languageChildNode.getAttribute(Constants.IDENTITY_ATTRIBUTE_ID);

			if(languageBuffer != null && languageBuffer.length() > 0){
				language = LanguageUtil.getLanguageByString(languageBuffer);

				if(Boolean.valueOf(languageChildNode.getAttribute(Constants.DEFAULT_ATTRIBUTE_ID)))
					defaultLanguage = language;

				languages.add(language);
			}
		}

		if(languages == null || languages.size() == 0)
			throw new InvalidResourcesException(resourcesDirname, resourcesId, languagesNode.getText());

		if(defaultLanguage == null)
			defaultLanguage = languages.iterator().next();

		resources.setLanguages(languages);
		resources.setDefaultLanguage(defaultLanguage);

		XmlNode actionFormsNode = resourcesNode.getNode(ActionFormConstants.ACTION_FORMS_ATTRIBUTE_ID);

		if(actionFormsNode != null){
			List<XmlNode> actionFormNodes = actionFormsNode.getChildren();

			if(actionFormNodes != null && !actionFormNodes.isEmpty()){
				List<XmlNode> forwardNodes = null;
				XmlNode forwardsNode = null;
				Collection<ActionFormForwardResources> forwards = null;
				ActionFormForwardResources forward = null;
				ActionFormResources actionForm = null;
				Collection<ActionFormResources> actionForms = PropertyUtil.instantiate(Constants.DEFAULT_LIST_CLASS);

				for(XmlNode actionFormNode : actionFormNodes){
					actionForm = new ActionFormResources();
					actionForm.setName(actionFormNode.getAttribute(Constants.NAME_ATTRIBUTE_ID));
					actionForm.setClazz(actionFormNode.getAttribute(Constants.CLASS_ATTRIBUTE_ID));
					actionForm.setAction(actionFormNode.getAttribute(ActionFormConstants.ACTION_ATTRIBUTE_ID));

					forwardsNode = actionFormNode.getNode(ActionFormConstants.FORWARDS_ATTRIBUTE_ID);

					if(forwardsNode != null){
						forwardNodes = forwardsNode.getChildren();

						if(forwardNodes != null && !forwardNodes.isEmpty()){
							forwards = PropertyUtil.instantiate(Constants.DEFAULT_LIST_CLASS);

							for(XmlNode forwardNode : forwardNodes){
								forward = new ActionFormForwardResources();
								forward.setName(forwardNode.getAttribute(Constants.NAME_ATTRIBUTE_ID));
								forward.setUrl(forwardNode.getAttribute(SystemConstants.URL_ATTRIBUTE_ID));

								forwards.add(forward);
							}

							actionForm.setForwards(forwards);
						}
					}

					actionForms.add(actionForm);
				}

				resources.setActionForms(actionForms);
			}
		}

		XmlNode servicesNode = resourcesNode.getNode(ServiceConstants.SERVICES_ATTRIBUTE_ID);

		if(servicesNode != null){
			List<XmlNode> servicesNodes = servicesNode.getChildren();

			if(servicesNodes != null && servicesNodes.size() > 0)
				for(XmlNode serviceNode : servicesNodes)
					resources.addService(serviceNode.getValue());
		}

		return resources;
	}
}