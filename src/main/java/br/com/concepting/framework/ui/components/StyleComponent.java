package br.com.concepting.framework.ui.components;

import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.ui.constants.UIConstants;

/**
 * Class that defines the declaration of a style in the UI page.
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
 * along with this program.  If not, see <a href="http://www.gnu.org/licenses"></a>.</pre>
 */
public class StyleComponent extends BaseComponent{
    private static final long serialVersionUID = 1877514951600452014L;
    
    private String url = null;
    
    /**
     * Returns the URL of the style.
     *
     * @return String that contains the URL.
     */
    public String getUrl(){
        return this.url;
    }
    
    /**
     * Defines the URL of the style.
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
        
        if(this.url != null && !this.url.isEmpty()){
            StringBuilder url = new StringBuilder();
            
            url.append(contextPath);
            
            if(this.url.startsWith("/")){
                url.append(UIConstants.DEFAULT_SKINS_RESOURCES_DIR);
                url.append(currentSkin);
                url.append(UIConstants.DEFAULT_STYLES_RESOURCES_DIR);
                url.append(this.url.substring(1));
            }
            else{
                url.append("/");
                url.append(this.url);
            }
            
            print("<link href=\"");
            print(url);
            print("\" type=\"text/css\" rel=\"stylesheet\"");
        }
        else
            println("<style type=\"text/css\">");
    }

    @Override
    protected void renderClose() throws InternalErrorException{
        if(this.url != null && !this.url.isEmpty())
            println("/>");
        else
            println("</style>");
        
        super.renderClose();
    }

    @Override
    protected void clearAttributes() throws InternalErrorException{
        super.clearAttributes();
        
        setUrl(null);
    }
}