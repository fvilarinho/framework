package br.com.concepting.framework.ui.components;

import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.controller.SystemController;
import br.com.concepting.framework.controller.action.types.ActionType;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.SystemSessionModel;
import br.com.concepting.framework.model.util.ModelUtil;
import br.com.concepting.framework.processors.ExpressionProcessorUtil;
import br.com.concepting.framework.security.controller.SecurityController;
import br.com.concepting.framework.security.model.LoginSessionModel;
import br.com.concepting.framework.ui.components.types.EventType;
import br.com.concepting.framework.ui.components.types.VisibilityType;
import br.com.concepting.framework.ui.constants.UIConstants;
import br.com.concepting.framework.ui.controller.UIController;
import br.com.concepting.framework.util.MethodUtil;
import br.com.concepting.framework.util.PropertyUtil;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.helpers.Node;
import br.com.concepting.framework.util.helpers.PropertyInfo;
import br.com.concepting.framework.util.types.AlignmentType;
import br.com.concepting.framework.util.types.ComponentType;

import javax.servlet.jsp.JspException;
import java.lang.reflect.InvocationTargetException;
import java.security.SecureRandom;
import java.util.Collection;
import java.util.Locale;

/**
 * Class that defines the tree view component.
 *
 * @author fvilarinho
 * @since 2.0.0
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
@SuppressWarnings("unchecked")
public class TreeViewPropertyComponent extends OptionsPropertyComponent{
    private static final long serialVersionUID = -5398943872124965129L;
    
    private String nodeLabelProperty = null;
    private Integer expandLevel = null;
    private String onExpand = null;
    private String onExpandAction = null;
    private String onExpandForward = null;
    private String onExpandUpdateViews = null;
    private Boolean onExpandValidateModel = null;
    private String onExpandValidateModelProperties = null;
    private Boolean selectUnselectParentsOrChildren = null;
    
    /**
     * Indicates if the parents and children should be selected or unselected.
     *
     * @return True/False.
     */
    public Boolean selectUnselectParentsOrChildren(){
        return this.selectUnselectParentsOrChildren;
    }
    
    /**
     * Indicates if the parents and children should be selected or unselected.
     *
     * @return True/False.
     */
    public Boolean getSelectUnselectParentsOrChildren(){
        return selectUnselectParentsOrChildren();
    }
    
    /**
     * Defines if the parents and children should be selected or unselected.
     *
     * @param selectUnselectParentsOrChildren True/False.
     */
    public void setSelectUnselectParentsOrChildren(Boolean selectUnselectParentsOrChildren){
        this.selectUnselectParentsOrChildren = selectUnselectParentsOrChildren;
    }
    
    /**
     * Returns the identifier of the data model property that stores the label
     * of the node.
     *
     * @return String that contains the identifier of the property.
     */
    public String getNodeLabelProperty(){
        return this.nodeLabelProperty;
    }
    
    /**
     * Defines the identifier of the data model property that stores the label
     * of the node.
     *
     * @param nodeLabelProperty String that contains the identifier of the
     * property.
     */
    public void setNodeLabelProperty(String nodeLabelProperty){
        this.nodeLabelProperty = nodeLabelProperty;
        
        super.setOptionLabelProperty(nodeLabelProperty);
    }
    
    /**
     * Returns the event code that will be executed on the node's expansion.
     *
     * @return String that contains the event code
     */
    public String getOnExpand(){
        return this.onExpand;
    }
    
    /**
     * Defines the event code that will be executed on the node's expansion.
     *
     * @param onExpand String that contains the event code
     */
    public void setOnExpand(String onExpand){
        this.onExpand = onExpand;
    }
    
    /**
     * Returns the identifier of the action that will be executed on the node's
     * expansion.
     *
     * @return String that contains the identifier of the action.
     */
    public String getOnExpandAction(){
        return this.onExpandAction;
    }
    
    /**
     * Defines the identifier of the action that will be executed on the node's
     * expansion.
     *
     * @param onExpandAction String that contains the identifier of the action.
     */
    public void setOnExpandAction(String onExpandAction){
        this.onExpandAction = onExpandAction;
    }
    
    /**
     * Defines the action that will be executed on the node's expansion.
     *
     * @param onExpandActionType Instance that contains the action.
     */
    protected void setOnExpandActionType(ActionType onExpandActionType){
        if(onExpandActionType != null)
            this.onExpandAction = onExpandActionType.getMethod();
        else
            this.onExpandAction = null;
    }
    
    /**
     * Returns the identifier of the action forward on the node's expansion.
     *
     * @return String that contains the identifier of the forward.
     */
    public String getOnExpandForward(){
        return this.onExpandForward;
    }
    
    /**
     * Defines the identifier of the action forward on the node's expansion.
     *
     * @param onExpandForward String that contains the identifier of the
     * forward.
     */
    public void setOnExpandForward(String onExpandForward){
        this.onExpandForward = onExpandForward;
    }
    
    /**
     * Returns the identifiers of the views that will be updated on the node's
     * expansion.
     *
     * @return String that contains the identifiers of the views.
     */
    public String getOnExpandUpdateViews(){
        return this.onExpandUpdateViews;
    }
    
    /**
     * Defines the identifiers of the views that will be updated on the node's
     * expansion.
     *
     * @param onExpandUpdateViews String that contains the identifiers of the
     * views.
     */
    public void setOnExpandUpdateViews(String onExpandUpdateViews){
        this.onExpandUpdateViews = onExpandUpdateViews;
    }
    
    /**
     * Indicates if the data model should be validated on the node's expansion.
     *
     * @return True/False.
     */
    public Boolean getOnExpandValidateModel(){
        return this.onExpandValidateModel;
    }
    
    /**
     * Defines if the data model should be validated on the node's expansion.
     *
     * @param onExpandValidateModel True/False.
     */
    public void setOnExpandValidateModel(Boolean onExpandValidateModel){
        this.onExpandValidateModel = onExpandValidateModel;
    }
    
    /**
     * Returns the validation properties of the data model of the node's
     * expansion.
     *
     * @return String that contains the identifiers of the properties.
     */
    public String getOnExpandValidateModelProperties(){
        return this.onExpandValidateModelProperties;
    }
    
    /**
     * Defines the validation properties of the data model of the node's
     * expansion.
     *
     * @param onExpandValidateModelProperties String that contains the
     * identifiers of the properties.
     */
    public void setOnExpandValidateModelProperties(String onExpandValidateModelProperties){
        this.onExpandValidateModelProperties = onExpandValidateModelProperties;
    }
    
    /**
     * Returns the identifier of the level that should be expanded.
     *
     * @return Numeric value that contains the identifier of the level.
     */
    public Integer getExpandLevel(){
        return this.expandLevel;
    }
    
    /**
     * Defines the identifier of the level that should be expanded.
     *
     * @param expandLevel Numeric value that contains the identifier of the
     * level.
     */
    public void setExpandLevel(Integer expandLevel){
        this.expandLevel = expandLevel;
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.OptionsPropertyComponent#getOptionLabelIndentation(java.lang.Integer)
     */
    protected String getOptionLabelIndentation(Integer level) throws InternalErrorException{
        return null;
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.OptionsPropertyComponent#buildRestrictions()
     */
    protected void buildRestrictions() throws InternalErrorException{
        super.buildRestrictions();
        
        if(this.selectUnselectParentsOrChildren == null)
            this.selectUnselectParentsOrChildren = false;
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BasePropertyComponent#buildEvents()
     */
    protected void buildEvents() throws InternalErrorException{
        super.buildEvents();
        
        buildEvent(EventType.ON_EXPAND);
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.OptionsPropertyComponent#buildAlignment()
     */
    protected void buildAlignment() throws InternalErrorException{
        setAlignmentType(AlignmentType.LEFT);
        
        super.buildAlignment();
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BaseOptionsPropertyComponent#initialize()
     */
    protected void initialize() throws InternalErrorException{
        setComponentType(ComponentType.TREE_VIEW);
        
        super.initialize();
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BasePropertyComponent#renderLabelAttribute()
     */
    protected void renderLabelAttribute() throws InternalErrorException{
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BasePropertyComponent#renderPatternAttribute()
     */
    protected void renderPatternAttribute() throws InternalErrorException{
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BaseComponent#renderOpen()
     */
    protected void renderOpen() throws InternalErrorException{
        UIController uiController = getUIController();
        String actionFormName = getActionFormName();
        String name = getName();
        
        if(uiController == null || actionFormName == null || actionFormName.length() == 0 || name == null || name.length() == 0)
            return;
        
        Boolean hasInvalidPropertyDefinition = hasInvalidPropertyDefinition();
        
        if(hasInvalidPropertyDefinition == null || !hasInvalidPropertyDefinition){
            StringBuilder nameBuffer = new StringBuilder();
            
            nameBuffer.append(name);
            nameBuffer.append(".");
            nameBuffer.append(UIConstants.CURRENT_TREE_VIEW_NODE_ATTRIBUTE_ID);
            
            HiddenPropertyComponent currentTreeViewNodePropertyComponent = new HiddenPropertyComponent();
            
            currentTreeViewNodePropertyComponent.setPageContext(this.pageContext);
            currentTreeViewNodePropertyComponent.setOutputStream(getOutputStream());
            currentTreeViewNodePropertyComponent.setActionFormName(actionFormName);
            currentTreeViewNodePropertyComponent.setName(nameBuffer.toString());
            currentTreeViewNodePropertyComponent.setValue(uiController.getCurrentTreeViewNode(name));
            
            try{
                currentTreeViewNodePropertyComponent.doStartTag();
                currentTreeViewNodePropertyComponent.doEndTag();
            }
            catch(JspException e){
                throw new InternalErrorException(e);
            }
            
            Boolean multipleSelection = hasMultipleSelection();
            
            if(multipleSelection != null && multipleSelection){
                ListPropertyComponent propertyComponent = new ListPropertyComponent();
                
                propertyComponent.setPageContext(this.pageContext);
                propertyComponent.setOutputStream(getOutputStream());
                propertyComponent.setActionFormName(actionFormName);
                propertyComponent.setPropertyInfo(getPropertyInfo());
                propertyComponent.setResourcesId(getResourcesId());
                propertyComponent.setResourcesKey(getResourcesKey());
                propertyComponent.setName(name);
                propertyComponent.setValue(getValue());
                propertyComponent.setLabel(getLabel());
                propertyComponent.setOptionLabelProperty(this.nodeLabelProperty);
                propertyComponent.setShowLabel(false);
                propertyComponent.setDataset(getDataset());
                propertyComponent.setDatasetScope(getDatasetScope());
                
                StringBuilder styleBuffer = new StringBuilder();
                
                styleBuffer.append("display: ");
                styleBuffer.append(VisibilityType.NONE);
                styleBuffer.append(";");
                
                propertyComponent.setStyle(styleBuffer.toString());
                propertyComponent.setParent(this);
                
                try{
                    propertyComponent.doStartTag();
                    propertyComponent.doEndTag();
                }
                catch(JspException e){
                    throw new InternalErrorException(e);
                }
            }
            else{
                HiddenPropertyComponent propertyComponent = new HiddenPropertyComponent();
                
                propertyComponent.setPageContext(this.pageContext);
                propertyComponent.setOutputStream(getOutputStream());
                propertyComponent.setActionFormName(actionFormName);
                propertyComponent.setPropertyInfo(getPropertyInfo());
                propertyComponent.setName(name);
                propertyComponent.setValue(getValue());
                propertyComponent.setParent(this);
                
                try{
                    propertyComponent.doStartTag();
                    propertyComponent.doEndTag();
                }
                catch(JspException e){
                    throw new InternalErrorException(e);
                }
            }
        }
        
        super.renderOpen();
        
        print("<div id=\"");
        print(name);
        print(".");
        print(UIConstants.DEFAULT_TREE_VIEW_ID);
        print("\"");
        
        String styleClass = getStyleClass();
        
        if(styleClass != null && styleClass.length() > 0){
            print(" class=\"");
            print(styleClass);
            print("\"");
        }
        
        String style = getStyle();
        
        if(style != null && style.length() > 0){
            print(" style=\"");
            print(style);
            
            if(!style.endsWith(";"))
                print(";");
            
            print("\"");
        }
        
        renderTooltip();
        
        println("\">");
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BasePropertyComponent#renderBody()
     */
    protected void renderBody() throws InternalErrorException{
        Boolean hasInvalidPropertyDefinition = hasInvalidPropertyDefinition();
        
        if(hasInvalidPropertyDefinition != null && hasInvalidPropertyDefinition)
            super.renderInvalidPropertyMessage();
        else{
            String name = getName();
            
            if(name != null && name.length() > 0){
                print("<div id=\"");
                print(name);
                print(".");
                print(UIConstants.DEFAULT_TREE_VIEW_CONTENT_ID);
                print("\" class=\"");
                print(UIConstants.DEFAULT_TREE_VIEW_CONTENT_STYLE_CLASS);
                println("\">");
                
                renderNodes();
                
                println("</div>");
            }
        }
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BaseComponent#renderClose()
     */
    protected void renderClose() throws InternalErrorException{
        println("</div>");
        
        super.renderClose();
    }
    
    /**
     * Renders the nodes of the component.
     *
     * @param <C> Class that defines the collection of nodes.
     * @param <N> Class that defines a node.
     * @throws InternalErrorException Occurs when was not possible to render.
     */
    protected <N extends Node, C extends Collection<N>> void renderNodes() throws InternalErrorException{
        SystemController systemController = getSystemController();
        SecurityController securityController = getSecurityController();
        
        if(systemController == null || securityController == null)
            return;
        
        LoginSessionModel loginSession = securityController.getLoginSession();
        SystemSessionModel systemSession = (loginSession != null ? loginSession.getSystemSession() : null);
        
        if(systemSession == null)
            return;
        
        String domain = systemSession.getId();
        C nodes = getDatasetValues();
        
        if(nodes != null && nodes.size() > 0)
            renderNodes(nodes, null, null, 0);
        
        ExpressionProcessorUtil.setVariable(domain, Constants.ITEM_ATTRIBUTE_ID, null);
    }
    
    /**
     * Renders the nodes of the component.
     *
     * @param <C> Class that defines the collection of nodes.
     * @param <N> Class that defines a node.
     * @param nodes List that contains the nodes.
     * @param parent Instance that contains the parent node.
     * @param index String that contains the current node index.
     * @param level Numeric value that contains the current expansion level.
     * @throws InternalErrorException Occurs when was not possible to render.
     */
    protected <N extends Node, C extends Collection<N>> void renderNodes(C nodes, N parent, String index, Integer level) throws InternalErrorException{
        SystemController systemController = getSystemController();
        SecurityController securityController = getSecurityController();
        
        if(systemController == null || securityController == null)
            return;
        
        PropertyInfo propertyInfo = getPropertyInfo();
        String actionFormName = getActionFormName();
        String name = getName();
        
        if(propertyInfo == null || actionFormName == null || actionFormName.length() == 0 || name == null || name.length() == 0)
            return;
        
        LoginSessionModel loginSession = securityController.getLoginSession();
        SystemSessionModel systemSession = (loginSession != null ? loginSession.getSystemSession() : null);
        
        if(systemSession == null)
            return;
        
        String domain = systemSession.getId();
        Locale currentLanguage = getCurrentLanguage();
        Boolean multipleSelection = hasMultipleSelection();
        Boolean enabled = isEnabled();
        Object value = getValue();
        C nodeChildren = null;
        StringBuilder nodeIndex = null;
        StringBuilder nodeId = null;
        StringBuilder parentNodeId = null;
        StringBuilder nodeTrace = null;
        String nodeIndentation = null;
        Object nodeValue = null;
        String nodeValueLabel = null;
        StringBuilder expandedNodeId = null;
        HiddenPropertyComponent expandedNodePropertyComponent = null;
        String nodeIsExpandedBuffer = null;
        Boolean isExpandedNode = null;
        Boolean isSelectedNode = null;
        String onSelectNodeContent = null;
        String onUnSelectNodeContent = null;
        int cont = 0;
        
        try{
            for(N node: nodes){
                if(node.getIndex() == null)
                    node.setIndex(new SecureRandom().nextInt());
                
                node.setLevel(level);
                
                if((parent == null && node.getParent() == null) || (parent != null && parent.equals(node.getParent()))){
                    ExpressionProcessorUtil.setVariable(domain, Constants.ITEM_ATTRIBUTE_ID, node);
                    
                    onSelectNodeContent = ExpressionProcessorUtil.fillVariablesInString(domain, getOnSelect(), currentLanguage);
                    onUnSelectNodeContent = ExpressionProcessorUtil.fillVariablesInString(domain, getOnUnSelect(), currentLanguage);
                    nodeValueLabel = getOptionLabel(node, level);
                    
                    if((propertyInfo.isModel() != null && propertyInfo.isModel()) || (propertyInfo.hasModel() != null && propertyInfo.hasModel()))
                        nodeValue = node;
                    else{
                        try{
                            nodeValue = PropertyUtil.getValue(node, propertyInfo.getId());
                        }
                        catch(IllegalAccessException | InvocationTargetException | NoSuchMethodException e){
                            nodeValue = node;
                        }
                    }
                    
                    if(nodeIndex == null)
                        nodeIndex = new StringBuilder();
                    else
                        nodeIndex.delete(0, nodeIndex.length());
                    
                    if(index != null && index.length() > 0){
                        nodeIndex.append(index);
                        nodeIndex.append("_");
                    }
                    
                    nodeIndex.append(cont);
                    
                    if(nodeId == null)
                        nodeId = new StringBuilder();
                    else
                        nodeId.delete(0, nodeId.length());
                    
                    nodeId.append(name);
                    nodeId.append(".");
                    nodeId.append(UIConstants.DEFAULT_TREE_VIEW_NODE_ID);
                    nodeId.append(nodeIndex.toString().hashCode());
                    
                    if(parent != null){
                        if(parentNodeId == null)
                            parentNodeId = new StringBuilder();
                        else
                            parentNodeId.delete(0, parentNodeId.length());
                        
                        parentNodeId.append(name);
                        parentNodeId.append(".");
                        parentNodeId.append(UIConstants.DEFAULT_TREE_VIEW_NODE_ID);
                        parentNodeId.append(index.hashCode());
                    }
                    
                    println("<table>");
                    println("<tr>");
                    
                    if(nodeTrace == null)
                        nodeTrace = new StringBuilder();
                    else
                        nodeTrace.delete(0, nodeTrace.length());
                    
                    nodeTrace.append("<td class=\"");
                    nodeTrace.append(UIConstants.DEFAULT_TREE_VIEW_TRACE_STYLE_CLASS);
                    nodeTrace.append("\"></td>");
                    
                    if(level != null && level > 0)
                        nodeIndentation = StringUtil.replicate(nodeTrace.toString(), level);
                    else
                        nodeIndentation = null;
                    
                    if(nodeIndentation != null && nodeIndentation.length() > 0)
                        println(nodeIndentation);
                    
                    if((node.hasChildren() == null || !node.hasChildren()) && (this.onExpand == null || this.onExpand.length() == 0)){
                        print("<td class=\"");
                        print(UIConstants.DEFAULT_TREE_VIEW_TRACE_STYLE_CLASS);
                        println("\"></td>");
                        
                        print("<td class=\"");
                        print(UIConstants.DEFAULT_TREE_VIEW_NODE_ICON_STYLE_CLASS);
                        println("\"></td>");
                    }
                    else{
                        if(expandedNodeId == null)
                            expandedNodeId = new StringBuilder();
                        else
                            expandedNodeId.delete(0, expandedNodeId.length());
                        
                        expandedNodeId.append(nodeId);
                        expandedNodeId.append(".");
                        expandedNodeId.append(UIConstants.IS_TREE_VIEW_NODE_EXPANDED_ATTRIBUTE_ID);
                        
                        nodeIsExpandedBuffer = systemController.getRequestParameterValue(expandedNodeId.toString());
                        
                        if(nodeIsExpandedBuffer == null || nodeIsExpandedBuffer.length() == 0){
                            isExpandedNode = this.expandLevel == null || level == null || this.expandLevel > level;
                        }
                        else
                            isExpandedNode = Boolean.valueOf(nodeIsExpandedBuffer);
                        
                        print("<td id=\"");
                        print(nodeId);
                        print(".");
                        print(UIConstants.DEFAULT_TREE_VIEW_NODE_ICON_ID);
                        print("\" class=\"");
                        
                        if(isExpandedNode != null && isExpandedNode)
                            print(UIConstants.DEFAULT_TREE_VIEW_NODE_EXPANDED_ICON_STYLE_CLASS);
                        else
                            print(UIConstants.DEFAULT_TREE_VIEW_NODE_COLLAPSED_ICON_STYLE_CLASS);
                        
                        print("\" onClick=\"");
                        
                        if(this.onExpandAction != null && this.onExpandAction.length() > 0){
                            print("selectUnSelectTreeViewNode('");
                            print(name);
                            print("', '");
                            print(nodeId);
                            print("', ");
                            print(multipleSelection != null ? multipleSelection : false);
                            print(", ");
                            print(this.selectUnselectParentsOrChildren != null ? this.selectUnselectParentsOrChildren : false);
                            print("); ");
                        }
                        
                        print("showHideTreeViewNode('");
                        print(nodeId);
                        print("'");
                        
                        if(this.onExpand != null && this.onExpand.length() > 0){
                            print(", function(){");
                            print(this.onExpand);
                            print("}");
                        }
                        
                        println(");\"></td>");
                        
                        print("<td id=\"");
                        print(nodeId);
                        print(".");
                        print(UIConstants.DEFAULT_TREE_VIEW_NODE_ID);
                        print("\" class=\"");
                        
                        if(isExpandedNode == null || !isExpandedNode)
                            print(UIConstants.DEFAULT_TREE_VIEW_NODE_CLOSED_ICON_STYLE_CLASS);
                        else
                            print(UIConstants.DEFAULT_TREE_VIEW_NODE_OPENED_ICON_STYLE_CLASS);
                        
                        println("\"></td>");
                    }
                    
                    print("<td id=\"");
                    print(nodeId);
                    print(".");
                    print(Constants.LABEL_ATTRIBUTE_ID);
                    print("\" class=\"");
                    
                    if(value != null){
                        if(propertyInfo.isCollection() != null && propertyInfo.isCollection()){
                            try{
                                isSelectedNode = (Boolean) MethodUtil.invokeMethod(value, "contains", nodeValue);
                            }
                            catch(NoSuchMethodException | IllegalAccessException | InvocationTargetException e){
                                isSelectedNode = false;
                            }
                            
                            if(isSelectedNode)
                                print(UIConstants.DEFAULT_TREE_VIEW_NODE_SELECTED_LABEL_STYLE_CLASS);
                            else
                                print(UIConstants.DEFAULT_TREE_VIEW_NODE_LABEL_STYLE_CLASS);
                        }
                        else{
                            isSelectedNode = value.equals(nodeValue);
                            
                            if(isSelectedNode)
                                print(UIConstants.DEFAULT_TREE_VIEW_NODE_SELECTED_LABEL_STYLE_CLASS);
                            else
                                print(UIConstants.DEFAULT_TREE_VIEW_NODE_LABEL_STYLE_CLASS);
                        }
                    }
                    else{
                        isSelectedNode = false;
                        
                        print(UIConstants.DEFAULT_TREE_VIEW_NODE_LABEL_STYLE_CLASS);
                    }
                    
                    print("\" value=\"");
                    
                    if((propertyInfo.isModel() != null && propertyInfo.isModel()) || (propertyInfo.hasModel() != null && propertyInfo.hasModel())){
                        BaseModel model = (BaseModel) node;
                        
                        try{
                            nodeValue = ModelUtil.toIdentifierString(model);
                        }
                        catch(Throwable e){
                            nodeValue = null;
                        }
                    }
                    else
                        nodeValue = PropertyUtil.format(nodeValue, getPattern(), useAdditionalFormatting(), getPrecision(), currentLanguage);
                    
                    print(nodeValue);
                    print("\"");
                    
                    if(parent != null){
                        print(" parent=\"");
                        print(parentNodeId);
                        print("\"");
                    }
                    
                    if(enabled != null && enabled){
                        print(" onClick=\"selectUnSelectTreeViewNode('");
                        print(name);
                        print("', '");
                        print(nodeId);
                        print("', ");
                        print(multipleSelection != null ? multipleSelection : false);
                        print(", ");
                        print(this.selectUnselectParentsOrChildren != null ? this.selectUnselectParentsOrChildren : false);
                        
                        if((onSelectNodeContent != null && onSelectNodeContent.length() > 0) || (onUnSelectNodeContent == null || onUnSelectNodeContent.length() > 0)){
                            if(onSelectNodeContent != null && onSelectNodeContent.length() > 0){
                                print(", ");
                                print("function(){");
                                print(onSelectNodeContent);
                                print("}");
                            }
                            
                            if(onUnSelectNodeContent != null && onUnSelectNodeContent.length() > 0){
                                print(", ");
                                print(Constants.DEFAULT_NULL_ID);
                                print(", function(){");
                                print(onUnSelectNodeContent);
                                print("}");
                            }
                        }
                        
                        print(");\"");
                    }
                    
                    print(">");
                    
                    if(nodeValueLabel != null){
                        print("&nbsp;");
                        print(nodeValueLabel);
                        println("&nbsp;");
                    }
                    
                    println("</td>");
                    println("</tr>");
                    println("</table>");
                    
                    if(multipleSelection == null || !multipleSelection){
                        if(isSelectedNode){
                            StringBuilder content = new StringBuilder();
                            
                            content.append("setObjectValue('");
                            content.append(name);
                            content.append("', '");
                            content.append(nodeValue);
                            content.append("'); setObjectValue('");
                            content.append(name);
                            content.append(".");
                            content.append(UIConstants.CURRENT_TREE_VIEW_NODE_ATTRIBUTE_ID);
                            content.append("', '");
                            content.append(nodeId);
                            content.append("');");
                            content.append(StringUtil.getLineBreak());
                            
                            ScriptComponent scriptComponent = new ScriptComponent();
                            
                            scriptComponent.setPageContext(this.pageContext);
                            scriptComponent.setOutputStream(getOutputStream());
                            scriptComponent.setContent(content.toString());
                            
                            try{
                                scriptComponent.doStartTag();
                                scriptComponent.doEndTag();
                            }
                            catch(JspException e){
                                throw new InternalErrorException(e);
                            }
                        }
                    }
                    
                    print("<div id=\"");
                    print(nodeId);
                    print("\"");
                    
                    if(isExpandedNode == null || !isExpandedNode){
                        print(" style=\"display: ");
                        print(VisibilityType.NONE);
                        print(";\"");
                    }
                    
                    println(">");
                    
                    if((node.hasChildren() != null && node.hasChildren()) || (this.onExpand != null && this.onExpand.length() > 0)){
                        expandedNodePropertyComponent = new HiddenPropertyComponent();
                        expandedNodePropertyComponent.setPageContext(this.pageContext);
                        expandedNodePropertyComponent.setOutputStream(getOutputStream());
                        expandedNodePropertyComponent.setActionFormName(actionFormName);
                        expandedNodePropertyComponent.setName(expandedNodeId.toString());
                        expandedNodePropertyComponent.setValue(isExpandedNode);
                        
                        try{
                            expandedNodePropertyComponent.doStartTag();
                            expandedNodePropertyComponent.doEndTag();
                        }
                        catch(JspException e){
                            throw new InternalErrorException(e);
                        }
                        
                        if(node.hasChildren() != null && node.hasChildren()){
                            nodeChildren = (C) node.getChildren();
                            
                            renderNodes(nodeChildren, node, nodeIndex.toString(), (level != null ? level + 1 : 1));
                        }
                    }
                    
                    println("</div>");
                }
                
                cont++;
            }
        }
        catch(IllegalArgumentException e){
            throw new InternalErrorException(e);
        }
        finally{
            ExpressionProcessorUtil.setVariable(domain, Constants.ITEM_ATTRIBUTE_ID, null);
        }
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BasePropertyComponent#clearAttributes()
     */
    protected void clearAttributes() throws InternalErrorException{
        super.clearAttributes();
        
        setNodeLabelProperty(null);
        setOnExpand(null);
        setOnExpandAction(null);
        setOnExpandForward(null);
        setOnExpandUpdateViews(null);
        setOnExpandValidateModel(null);
        setOnExpandValidateModelProperties(null);
        setExpandLevel(null);
        setSelectUnselectParentsOrChildren(null);
    }
}