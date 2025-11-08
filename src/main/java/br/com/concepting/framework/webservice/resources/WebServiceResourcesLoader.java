package br.com.concepting.framework.webservice.resources;

import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.network.resources.NetworkResourcesLoader;
import br.com.concepting.framework.processors.ExpressionProcessorUtil;
import br.com.concepting.framework.resources.exceptions.InvalidResourcesException;
import br.com.concepting.framework.util.NumberUtil;
import br.com.concepting.framework.util.helpers.XmlNode;
import br.com.concepting.framework.util.types.MethodType;
import br.com.concepting.framework.webservice.constants.WebServiceConstants;

import java.text.ParseException;
import java.util.List;

/**
 * Class responsible to manipulate the web service resources.
 *
 * @author fvilarinho
 * @since 3.7.0
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
 * along with this program.  If not, see <a href="https://www.gnu.org/licenses">...</a>.</pre>
 */
public class WebServiceResourcesLoader extends NetworkResourcesLoader<WebServiceResources>{
    /**
     * Constructor - Manipulates the default resources.
     *
     * @throws InvalidResourcesException Occurs when the default resources could
     * not be read.
     */
    public WebServiceResourcesLoader() throws InvalidResourcesException{
        super();
    }
    
    /**
     * Constructor - Manipulates specific resources.
     *
     * @param resourcesDirname String that contains the directory where the resources
     * are stored.
     * @throws InvalidResourcesException Occurs when the resources could not be read.
     */
    public WebServiceResourcesLoader(String resourcesDirname) throws InvalidResourcesException{
        super(resourcesDirname);
    }

    @Override
    public WebServiceResources parseResources(XmlNode resourcesNode) throws InvalidResourcesException{
        String resourcesDirname = getResourcesDirname();
        String resourcesId = getResourcesId();
        WebServiceResources resources = super.parseResources(resourcesNode);
        XmlNode methodNode = resourcesNode.getNode(WebServiceConstants.METHOD_ATTRIBUTE_ID);
        
        if(methodNode != null){
            String method = ExpressionProcessorUtil.fillEnvironmentInString(methodNode.getValue());
            
            if(method == null || method.isEmpty())
                throw new InvalidResourcesException(resourcesDirname, resourcesId, methodNode.getText());
            
            resources.setMethod(MethodType.valueOf(method.toUpperCase()));
        }
        else
            throw new InvalidResourcesException(resourcesDirname, resourcesId, resourcesNode.getText());
        
        XmlNode timeoutNode = resourcesNode.getNode(Constants.TIMEOUT_ATTRIBUTE_ID);
        
        if(timeoutNode != null){
            try{
                int timeout = NumberUtil.parseInt(ExpressionProcessorUtil.fillEnvironmentInString(timeoutNode.getValue()));
                
                resources.setTimeout(timeout);
            }
            catch(ParseException e){
                throw new InvalidResourcesException(resourcesDirname, resourcesId, timeoutNode.getText());
            }
        }

        XmlNode tokenNode = resourcesNode.getNode(WebServiceConstants.TOKEN_ATTRIBUTE_ID);

        if(tokenNode != null){
            String token = ExpressionProcessorUtil.fillEnvironmentInString(tokenNode.getValue());

            if(token == null || token.isEmpty())
                throw new InvalidResourcesException(resourcesDirname, resourcesId, tokenNode.getText());

            resources.setToken(token);
        }

        XmlNode userNameNode = resourcesNode.getNode(WebServiceConstants.USERNAME_ATTRIBUTE_ID);

        if(userNameNode != null){
            String userName = ExpressionProcessorUtil.fillEnvironmentInString(userNameNode.getValue());

            if(userName == null || userName.isEmpty())
                throw new InvalidResourcesException(resourcesDirname, resourcesId, userNameNode.getText());

            resources.setUserName(userName);
        }

        XmlNode passwordNode = resourcesNode.getNode(WebServiceConstants.PASSWORD_ATTRIBUTE_ID);

        if(passwordNode != null){
            String password = ExpressionProcessorUtil.fillEnvironmentInString(passwordNode.getValue());

            if(password == null || password.isEmpty())
                throw new InvalidResourcesException(resourcesDirname, resourcesId, passwordNode.getText());

            resources.setPassword(password);
        }

        XmlNode urlNode = resourcesNode.getNode(WebServiceConstants.URL_ATTRIBUTE_ID);
        
        if(urlNode != null){
            String url = ExpressionProcessorUtil.fillEnvironmentInString(urlNode.getValue());
            
            if(url == null || url.isEmpty())
                throw new InvalidResourcesException(resourcesDirname, resourcesId, urlNode.getText());
            
            resources.setUrl(url);
        }
        else
            throw new InvalidResourcesException(resourcesDirname, resourcesId, resourcesNode.getText());

        XmlNode dataNode = resourcesNode.getNode(WebServiceConstants.DATA_ATTRIBUTE_ID);
        
        if(dataNode != null){
            String escape = dataNode.getAttribute(WebServiceConstants.ESCAPE_ATTRIBUTE_ID);

            if(escape != null && !escape.isEmpty())
                resources.setEscapeData(Boolean.parseBoolean(escape));

            String data = ExpressionProcessorUtil.fillEnvironmentInString(dataNode.getValue());
            
            if(data == null || data.isEmpty())
                throw new InvalidResourcesException(resourcesDirname, resourcesId, dataNode.getText());
            
            resources.setData(data);
        }
        
        XmlNode headersNode = resourcesNode.getNode(WebServiceConstants.HEADERS_ATTRIBUTE_ID);
        
        if(headersNode != null){
            List<XmlNode> headersNodes = headersNode.getChildren();
            
            if(headersNodes == null || headersNodes.isEmpty())
                throw new InvalidResourcesException(resourcesDirname, resourcesId, headersNode.getText());
            
            for(XmlNode headerNode: headersNodes){
                String id = ExpressionProcessorUtil.fillEnvironmentInString(headerNode.getAttribute(Constants.IDENTITY_ATTRIBUTE_ID));
                
                if(id == null || id.isEmpty())
                    throw new InvalidResourcesException(resourcesDirname, resourcesId, headerNode.getText());
                
                String value = ExpressionProcessorUtil.fillEnvironmentInString(headerNode.getAttribute(Constants.VALUE_ATTRIBUTE_ID));
                
                if(value == null || value.isEmpty())
                    throw new InvalidResourcesException(resourcesDirname, resourcesId, headerNode.getText());
                
                resources.addHeader(id, value);
            }
        }
        
        return resources;
    }

    @Override
    protected XmlNode parseContent() throws InvalidResourcesException{
        XmlNode contentNode = super.parseContent();
        XmlNode resourcesNode = (contentNode != null ? contentNode.getNode(WebServiceConstants.DEFAULT_ID) : null);
        
        if(resourcesNode == null) {
            if(contentNode != null)
                throw new InvalidResourcesException(getResourcesDirname(), getResourcesId(), contentNode.getText());

            throw new InvalidResourcesException(getResourcesDirname(), getResourcesId());
        }
        
        return resourcesNode;
    }
}