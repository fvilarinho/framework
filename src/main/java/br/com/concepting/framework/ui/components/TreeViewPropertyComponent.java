package br.com.concepting.framework.ui.components;

import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.controller.SystemController;
import br.com.concepting.framework.controller.action.types.ActionType;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.util.ModelUtil;
import br.com.concepting.framework.processors.ExpressionProcessorUtil;
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
import java.io.Serial;
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
@SuppressWarnings("unchecked")
public class TreeViewPropertyComponent extends OptionsPropertyComponent{
    @Serial
    private static final long serialVersionUID = -5398943872124965129L;
    
    private String nodeLabelProperty = null;
    private int expandLevel = 0;
    private String onExpand = null;
    private String onExpandAction = null;
    private String onExpandForward = null;
    private String onExpandUpdateViews = null;
    private boolean onExpandValidateModel = false;
    private String onExpandValidateModelProperties = null;
    private boolean selectUnselectParentsOrChildren = false;
    
    /**
     * Indicates if the parents and children should be selected or unselected.
     *
     * @return True/False.
     */
    public boolean selectUnselectParentsOrChildren(){
        return this.selectUnselectParentsOrChildren;
    }
    
    /**
     * Indicates if the parents and children should be selected or unselected.
     *
     * @return True/False.
     */
    public boolean getSelectUnselectParentsOrChildren(){
        return selectUnselectParentsOrChildren();
    }
    
    /**
     * Defines if the parents and children should be selected or unselected.
     *
     * @param selectUnselectParentsOrChildren True/False.
     */
    public void setSelectUnselectParentsOrChildren(boolean selectUnselectParentsOrChildren){
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
    public boolean getOnExpandValidateModel(){
        return this.onExpandValidateModel;
    }
    
    /**
     * Defines if the data model should be validated on the node's expansion.
     *
     * @param onExpandValidateModel True/False.
     */
    public void setOnExpandValidateModel(boolean onExpandValidateModel){
        this.onExpandValidateModel = onExpandValidateModel;
    }
    
    /**
     * Returns the validation properties of node's expansion.
     *
     * @return String that contains the identifiers of the properties.
     */
    public String getOnExpandValidateModelProperties(){
        return this.onExpandValidateModelProperties;
    }
    
    /**
     * Defines the validation properties of node's expansion.
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
    public int getExpandLevel(){
        return this.expandLevel;
    }
    
    /**
     * Defines the identifier of the level that should be expanded.
     *
     * @param expandLevel Numeric value that contains the identifier of the
     * level.
     */
    public void setExpandLevel(int expandLevel){
        this.expandLevel = expandLevel;
    }

    @Override
    protected String getOptionLabelIndentation(int level) throws InternalErrorException{
        return null;
    }

    @Override
    protected void buildEvents() throws InternalErrorException{
        super.buildEvents();
        
        buildEvent(EventType.ON_EXPAND);
    }

    @Override
    protected void buildAlignment() throws InternalErrorException{
        setAlignmentType(AlignmentType.LEFT);
        
        super.buildAlignment();
    }

    @Override
    protected void initialize() throws InternalErrorException{
        setComponentType(ComponentType.TREE_VIEW);
        
        super.initialize();
    }

    @Override
    protected void renderLabelAttribute() throws InternalErrorException{
    }

    @Override
    protected void renderPatternAttribute() throws InternalErrorException{
    }

    @Override
    protected void renderOpen() throws InternalErrorException{
        UIController uiController = getUIController();
        String actionFormName = getActionFormName();
        String name = getName();
        
        if(uiController == null || actionFormName == null || actionFormName.isEmpty() || name == null || name.isEmpty())
            return;
        
        boolean hasInvalidDefinition = hasInvalidDefinition();
        
        if(!hasInvalidDefinition){
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
            
            boolean multipleSelection = hasMultipleSelection();
            
            if(multipleSelection){
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
        
        if(styleClass != null && !styleClass.isEmpty()){
            print(" class=\"");
            print(styleClass);
            print("\"");
        }
        
        String style = getStyle();
        
        if(style != null && !style.isEmpty()){
            print(" style=\"");
            print(style);
            
            if(!style.endsWith(";"))
                print(";");
            
            print("\"");
        }
        
        renderTooltip();
        
        println("\">");
    }

    @Override
    protected void renderBody() throws InternalErrorException{
        boolean hasInvalidDefinition = hasInvalidDefinition();
        
        if(hasInvalidDefinition)
            super.renderInvalidDefinitionMessage();
        else{
            String name = getName();
            
            if(name != null && !name.isEmpty()){
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

    @Override
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
        C nodes = getDatasetValues();
        
        if(nodes != null && !nodes.isEmpty())
            renderNodes(nodes, null, null, 0);
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
    protected <N extends Node, C extends Collection<N>> void renderNodes(C nodes, N parent, String index, int level) throws InternalErrorException{
        SystemController systemController = getSystemController();
        PropertyInfo propertyInfo = getPropertyInfo();
        String actionFormName = getActionFormName();
        String name = getName();
        
        if(propertyInfo == null || actionFormName == null || actionFormName.isEmpty() || name == null || name.isEmpty())
            return;
        
        String domain = String.valueOf(System.currentTimeMillis());
        Locale currentLanguage = getCurrentLanguage();
        boolean hasMultipleSelection = hasMultipleSelection();
        boolean enabled = isEnabled();
        Object value = getValue();
        StringBuilder nodeIndex = null;
        StringBuilder nodeId = null;
        StringBuilder parentNodeId = null;
        StringBuilder nodeTrace = null;
        StringBuilder expandedNodeId = null;
        boolean isExpandedNode = false;
        int cont = 0;
        
        try{
            for(N node: nodes){
                if(node.getIndex() == null)
                    node.setIndex(new SecureRandom().nextInt());
                
                node.setLevel(level);
                
                if((parent == null && node.getParent() == null) || (parent != null && parent.equals(node.getParent()))){
                    ExpressionProcessorUtil.setVariable(domain, Constants.ITEM_ATTRIBUTE_ID, node);
                    
                    String onSelectNodeContent = ExpressionProcessorUtil.fillVariablesInString(domain, getOnSelect(), currentLanguage);
                    String onUnSelectNodeContent = ExpressionProcessorUtil.fillVariablesInString(domain, getOnUnSelect(), currentLanguage);
                    String nodeValueLabel = getOptionLabel(node, level);
                    Object nodeValue;

                    if(propertyInfo.isModel() || propertyInfo.hasModel())
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
                    
                    if(index != null && !index.isEmpty()){
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

                    String nodeIndentation;

                    if(level > 0)
                        nodeIndentation = StringUtil.replicate(nodeTrace.toString(), level);
                    else
                        nodeIndentation = null;
                    
                    if(nodeIndentation != null && !nodeIndentation.isEmpty())
                        println(nodeIndentation);
                    
                    if(!node.hasChildren() && (this.onExpand == null || this.onExpand.isEmpty())){
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
                        
                        String nodeIsExpandedBuffer = systemController.getParameterValue(expandedNodeId.toString());
                        
                        if(nodeIsExpandedBuffer == null || nodeIsExpandedBuffer.isEmpty())
                            isExpandedNode = this.expandLevel > level;
                        else
                            isExpandedNode = Boolean.parseBoolean(nodeIsExpandedBuffer);
                        
                        print("<td id=\"");
                        print(nodeId);
                        print(".");
                        print(UIConstants.DEFAULT_TREE_VIEW_NODE_ICON_ID);
                        print("\" class=\"");
                        
                        if(isExpandedNode)
                            print(UIConstants.DEFAULT_TREE_VIEW_NODE_EXPANDED_ICON_STYLE_CLASS);
                        else
                            print(UIConstants.DEFAULT_TREE_VIEW_NODE_COLLAPSED_ICON_STYLE_CLASS);
                        
                        print("\" onClick=\"");
                        
                        if(this.onExpandAction != null && !this.onExpandAction.isEmpty()){
                            print("selectUnSelectTreeViewNode('");
                            print(name);
                            print("', '");
                            print(nodeId);
                            print("', ");
                            print(hasMultipleSelection);
                            print(", ");
                            print(this.selectUnselectParentsOrChildren);
                            print("); ");
                        }
                        
                        print("showHideTreeViewNode('");
                        print(nodeId);
                        print("'");
                        
                        if(this.onExpand != null && !this.onExpand.isEmpty()){
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
                        
                        if(!isExpandedNode)
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

                    boolean isSelectedNode;

                    if(value != null){
                        if(propertyInfo.isCollection()){
                            try{
                                isSelectedNode = (Boolean) MethodUtil.invokeMethod(value, "contains", nodeValue);
                            }
                            catch(NoSuchMethodException | IllegalAccessException | InvocationTargetException e){
                                isSelectedNode = false;
                            }
                        }
                        else
                            isSelectedNode = value.equals(nodeValue);

                        if(isSelectedNode)
                            print(UIConstants.DEFAULT_TREE_VIEW_NODE_SELECTED_LABEL_STYLE_CLASS);
                        else
                            print(UIConstants.DEFAULT_TREE_VIEW_NODE_LABEL_STYLE_CLASS);
                    }
                    else{
                        isSelectedNode = false;
                        
                        print(UIConstants.DEFAULT_TREE_VIEW_NODE_LABEL_STYLE_CLASS);
                    }
                    
                    print("\" value=\"");
                    
                    if(propertyInfo.isModel() || propertyInfo.hasModel()){
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
                    
                    if(enabled){
                        print(" onClick=\"selectUnSelectTreeViewNode('");
                        print(name);
                        print("', '");
                        print(nodeId);
                        print("', ");
                        print(hasMultipleSelection);
                        print(", ");
                        print(this.selectUnselectParentsOrChildren);
                        
                        if((onSelectNodeContent != null && !onSelectNodeContent.isEmpty()) || (onUnSelectNodeContent == null || !onUnSelectNodeContent.isEmpty())){
                            if(onSelectNodeContent != null && !onSelectNodeContent.isEmpty()){
                                print(", ");
                                print("function(){");
                                print(onSelectNodeContent);
                                print("}");
                            }
                            
                            if(onUnSelectNodeContent != null && !onUnSelectNodeContent.isEmpty()){
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
                    
                    if(!hasMultipleSelection){
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
                    
                    if(!isExpandedNode){
                        print(" style=\"display: ");
                        print(VisibilityType.NONE);
                        print(";\"");
                    }
                    
                    println(">");
                    
                    if(node.hasChildren() || (this.onExpand != null && !this.onExpand.isEmpty())){
                        HiddenPropertyComponent expandedNodePropertyComponent = new HiddenPropertyComponent();

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
                        
                        if(node.hasChildren()){
                            C nodeChildren = (C) node.getChildren();
                            
                            renderNodes(nodeChildren, node, nodeIndex.toString(), level + 1);
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
            ExpressionProcessorUtil.clearVariables(domain);
        }
    }

    @Override
    protected void clearAttributes() throws InternalErrorException{
        super.clearAttributes();
        
        setNodeLabelProperty(null);
        setOnExpand(null);
        setOnExpandAction(null);
        setOnExpandForward(null);
        setOnExpandUpdateViews(null);
        setOnExpandValidateModel(false);
        setOnExpandValidateModelProperties(null);
        setExpandLevel(0);
        setSelectUnselectParentsOrChildren(false);
    }
}