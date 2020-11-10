package br.com.concepting.framework.resources;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import br.com.concepting.framework.resources.exceptions.InvalidResourcesException;
import br.com.concepting.framework.util.FileUtil;
import br.com.concepting.framework.util.LanguageUtil;
import br.com.concepting.framework.util.StringUtil;

/**
 * Class that defines the basic implementation to manipulates 
 * resources as properties.
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
public class PropertiesResourcesLoader extends BaseResourcesLoader<PropertiesResources>{
	private Locale language = null;

	/**
	 * Constructor - Manipulates specific resources.
	 * 
	 * @param resourcesId String that contains the identifier of the resources.
	 * @param language Instance that contains the language of the resources.
	 * @throws InvalidResourcesException Occurs when the default resources could
	 * not be read.
	 */
	public PropertiesResourcesLoader(String resourcesId, Locale language) throws InvalidResourcesException{
		this(null, resourcesId, language);
	}

	/**
	 * Constructor - Manipulates specific resources.
	 * 
	 * @param resourcesDirname String that contains the directory where the resources
	 * are stored.
	 * @param resourcesId String that contains the identifier of the resources.
	 * @throws InvalidResourcesException Occurs when the default resources could
	 * not be read.
	 */
	public PropertiesResourcesLoader(String resourcesDirname, String resourcesId) throws InvalidResourcesException{
		this(resourcesDirname, resourcesId, LanguageUtil.getDefaultLanguage());
	}

	/**
	 * Constructor - Manipulates specific resources.
	 * 
	 * @param resourcesDirname String that contains the directory where the resources
	 * are stored.
	 * @param resourcesId String that contains the identifier of the resources.
	 * @param language Instance that contains the language of the resources.
	 * @throws InvalidResourcesException Occurs when the default resources could
	 * not be read.
	 */
	public PropertiesResourcesLoader(String resourcesDirname, String resourcesId, Locale language) throws InvalidResourcesException{
		super();

		setResourcesDirname(resourcesDirname);
		setResourcesId(resourcesId);
		setLanguage(language);

		loadContent();
	}

	/**
	 * Returns the language of the resource.
	 * 
	 * @return Instance that contains the language of the resource.
	 */
	public Locale getLanguage(){
		return this.language;
	}

	/**
	 * Defines the language of the resource.
	 * 
	 * @param language Instance that contains the language of the resource.
	 */
	public void setLanguage(Locale language){
		this.language = language;
	}

	/**
	 * @see br.com.concepting.framework.resources.BaseResourcesLoader#getContentId()
	 */
	protected String getContentId(){
		String resourcesDirname = getResourcesDirname();
		String resourcesId = getResourcesId();

		StringBuilder contentId = new StringBuilder();

		if(resourcesDirname != null && resourcesDirname.length() > 0){
			contentId.append(resourcesDirname);
			contentId.append(FileUtil.getDirectorySeparator());
		}

		contentId.append(resourcesId);
		contentId.append("_");
		contentId.append(this.language.toString());

		return contentId.toString();
	}

	/**
	 * @see br.com.concepting.framework.resources.BaseResourcesLoader#parseContent()
	 */
	protected PropertiesResources parseContent() throws InvalidResourcesException{
		String resourcesDirname = getResourcesDirname();
		String resourcesId = getResourcesId();

		try{
			ResourceBundle properties = null;
			String contentId = StringUtil.replaceAll(resourcesId, ".", "/");

			if(resourcesDirname == null || resourcesDirname.length() == 0)
				properties = ResourceBundle.getBundle(contentId, this.language);
			else{
				File resourcesDirnameFile = new File(resourcesDirname);
				URL[] urls = {resourcesDirnameFile.toURI().toURL()};
				ClassLoader loader = new URLClassLoader(urls);

				properties = ResourceBundle.getBundle(contentId, this.language, loader);
			}

			return new PropertiesResources(properties);
		}
		catch(IOException | MissingResourceException e){
			throw new InvalidResourcesException(resourcesDirname, resourcesId, e);
		}
	}
}