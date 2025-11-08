package br.com.concepting.framework.ui.components;

import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.ui.constants.UIConstants;

import javax.servlet.jsp.tagext.BodyContent;
import java.io.Serial;

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
public class ScriptComponent extends BaseComponent{
    @Serial
    private static final long serialVersionUID = 4577984120880234589L;
    
    private String url = null;
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

    @Override
    protected void buildName() throws InternalErrorException{
    }

    @Override
    protected void renderOpen() throws InternalErrorException{
        String contextPath = getContextPath();
        String currentSkin = getCurrentSkin();
        
        if(contextPath == null || contextPath.isEmpty() || currentSkin == null || currentSkin.isEmpty())
            return;
        
        super.renderOpen();
        
        StringBuilder url = null;
        
        if(this.url != null && !this.url.isEmpty()){
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
        
        if(url != null && !url.isEmpty()){
            print(" src=\"");
            print(url);
            print("\">");
        }
        else
            println(">");
    }

    @Override
    protected void renderBody() throws InternalErrorException{
        if(this.url == null || this.url.isEmpty()){
            BodyContent bodyContent = getBodyContent();
            String content = getContent();
            
            if(bodyContent != null && (content == null || content.isEmpty())){
                content = bodyContent.getString();
                
                setContent(content);
            }
            
            if(content != null && !content.isEmpty())
                println(content);
        }
    }

    @Override
    protected void renderClose() throws InternalErrorException{
        println("</script>");
        
        super.renderClose();
    }

    @Override
    protected void clearAttributes() throws InternalErrorException{
        super.clearAttributes();
        
        setUrl(null);
        setContent(null);
    }
}