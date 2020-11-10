package br.com.innovativethinking.framework.ui.components;

import javax.servlet.jsp.tagext.BodyContent;

import br.com.innovativethinking.framework.exceptions.InternalErrorException;
import br.com.innovativethinking.framework.ui.constants.UIConstants;

/**
 * Class that defines the declaration of a script in the UI page.
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
public class ScriptComponent extends BaseComponent{
	private static final long serialVersionUID = 4577984120880234589L;

	private String url     = null;
	private String content = null;

	/**
	 * Returns the content of the script.
	 *
	 * @return String that contains the content of the script.
	 */
	public String getContent(){
		return this.content;
	}

	/**
	 * Defines the content of the script.
	 *
	 * @param content String that contains the content of the script.
	 */
	public void setContent(String content){
		this.content = content;
	}

	/**
	 * Returns the URL of the script.
	 * 
	 * @return String that contains the URL.
	 */
	public String getUrl(){
		return this.url;
	}

	/**
	 * Defines the URL of the script.
	 * 
	 * @param url String that contains the URL.
	 */
	public void setUrl(String url){
		this.url = url;
	}

	/**
	 * @see br.com.innovativethinking.framework.ui.components.BaseComponent#buildName()
	 */
	protected void buildName() throws InternalErrorException{
	}

	/**
	 * @see br.com.innovativethinking.framework.ui.components.BaseComponent#buildStyle()
	 */
	protected void buildStyle() throws InternalErrorException{
	}

	/**
	 * @see br.com.innovativethinking.framework.ui.components.BaseComponent#renderOpen()
	 */
	protected void renderOpen() throws InternalErrorException{
		String contextPath = getContextPath();
		String currentSkin = getCurrentSkin();

		if(contextPath == null || contextPath.length() == 0 || currentSkin == null || currentSkin.length() == 0)
			return;

		super.renderOpen();

		StringBuilder url = null;

		if(this.url != null && this.url.length() > 0){
			url = new StringBuilder();
			url.append(contextPath);

			if(this.url.startsWith("/")){
				url.append(UIConstants.DEFAULT_SKINS_RESOURCES_DIR);
				url.append(currentSkin);
				url.append(UIConstants.DEFAULT_SCRIPTS_RESOURCES_DIR);
				url.append(this.url.substring(1));
			}
			else{
				url.append("/");
				url.append(this.url);
			}
		}

		print("<script type=\"text/javascript\"");

		if(url != null && url.length() > 0){
			print(" src=\"");
			print(url);
			print("\">");
		}
		else
			println(">");
	}

	/**
	 * @see br.com.innovativethinking.framework.ui.components.BaseComponent#renderBody()
	 */
	protected void renderBody() throws InternalErrorException{
		if(this.url == null || this.url.length() == 0){
			BodyContent bodyContent = getBodyContent();
			String content = getContent();

			if(bodyContent != null && (content == null || content.length() == 0)){
				content = bodyContent.getString();

				setContent(content);
			}

			if(content != null && content.length() > 0)
				println(content);
		}
	}

	/**
	 * @see br.com.innovativethinking.framework.ui.components.BaseComponent#renderClose()
	 */
	protected void renderClose() throws InternalErrorException{
		println("</script>");

		super.renderClose();
	}

	/**
	 * @see br.com.innovativethinking.framework.ui.components.BaseComponent#clearAttributes()
	 */
	protected void clearAttributes() throws InternalErrorException{
		super.clearAttributes();

		setUrl(null);
		setContent(null);
	}
}