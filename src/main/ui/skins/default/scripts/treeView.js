/**
 * Defines the UI routines to manipulate the tree view component.
 * 
 * @author fvilarinho
 * @version 1.0.0
 */
  
/**
 * Show/Hide a node.
 * 
 * @param name String that contains the identifier of the node.
 * @param onExpand String that contains the expand event.
 */
function showHideTreeViewNode(name, onExpand){
	var node       = getObject(name);
	var submitFlag = false;
	
	if(node){
		if(node.style.display.toUpperCase() == "NONE")
			node.style.display = "";
		else
			node.style.display = "NONE";
	}
	
	var nodeExpandIcon = getObject(name + ".treeViewNodeIcon");
	
	if(nodeExpandIcon){ 
		if(nodeExpandIcon.className == "collapsedTreeViewNodeIcon")
			nodeExpandIcon.className = "expandedTreeViewNodeIcon";
		else
			nodeExpandIcon.className = "collapsedTreeViewNodeIcon";
	} 
 
	var nodeIcon = getObject(name + ".treeViewNode");
	
	if(nodeIcon){
		if(nodeIcon.className == "closedTreeViewNodeIcon")
			nodeIcon.className = "openedTreeViewNodeIcon";
		else
			nodeIcon.className = "closedTreeViewNodeIcon";
	}
	
	var nodeExpanded = getObjectValue(name + ".isTreeViewNodeExpanded");
	
	if(nodeExpanded == "false")
		setObjectValue(name + ".isTreeViewNodeExpanded", true);
	else
		setObjectValue(name + ".isTreeViewNodeExpanded", false);
	
	if(onExpand)
		onExpand();
}

/**
 * select/unselect a node and its children.
 * 
 * @param name String that contains the identifier of the node.
 * @param nodeId String that contains the identifier of the node.
 * @param hasMultipleSelection Indicates if the component has multiple selection.
 * @param selectUnselectParentsOrChildren Indicates if the parents and children should be selected.
 * @param onSelect String that content the select event.
 * @param onUnSelect String that content the unselect event.
 */
function selectUnSelectTreeViewNode(name, nodeId, hasMultipleSelection, selectUnselectParentsOrChildren, onSelect, onUnSelect){
	var nodeLabel = getObject(nodeId + ".label");
	
	if(nodeLabel){	
		if(nodeLabel.className == "treeViewNodeLabel")
			selectTreeViewNode(name, nodeId, hasMultipleSelection, selectUnselectParentsOrChildren, false, onSelect);
		else
			unselectTreeViewNode(name, nodeId, hasMultipleSelection, selectUnselectParentsOrChildren, onUnSelect);
	}
}

/**
 * Unselect a node and its children.
 * 
 * @param name String that contains the identifier of the node.
 * @param nodeId String that contains the identifier of the node.
 * @param hasMultipleSelection Indicates if the component has multiple selection.
 * @param selectUnselectParentsOrChildren Indicates if the parents and children should be selected.
 * @param onUnSelect String that content the unselect event.
 */
function unselectTreeViewNode(name, nodeId, hasMultipleSelection, selectUnselectParentsOrChildren, onUnSelect){
	var nodeLabel = getObject(nodeId + ".label");
		
	if(nodeLabel){
		nodeLabel.className = "treeViewNodeLabel";
		
		var nodeValue = nodeLabel.getAttribute("value");
		
		if(hasMultipleSelection){
			var options = getObject(name).options;
			var cont    = 0;
			
			for(cont = 0 ; cont < options.length ; cont++){
				if(options[cont].value == nodeValue && options[cont].selected){
					options[cont].selected = false;
					
					break;
				}
			}
			
			if(selectUnselectParentsOrChildren != null && selectUnselectParentsOrChildren){
				var parentNodeLabelId = nodeLabel.getAttribute("parent");
				var node              = null;
				
				if(parentNodeLabelId){
					node = getObject(parentNodeLabelId);
					
					if(node){
						var children       = node.childNodes;
						var child          = null;
						var childId        = null;
						var unselectParent = true;
						
						for(cont = 0 ; cont < children.length ; cont++){
							child   = children[cont];
							childId = child.id;
							
							if(childId){
								if(childId.indexOf(".treeViewNode") >= 0){
									child = getObject(childId + ".label");
	
									if(child){
										if(nodeLabel.className != child.className){
											unselectParent = false;
											
											break;
										}
									}
								}
							}
						}
						
						if(unselectParent)
							unselectTreeViewNode(name, parentNodeLabelId, hasMultipleSelection, selectUnselectParentsOrChildren);
					}
				}
			
				node = getObject(nodeId);
				
				if(node){
					var children = node.childNodes;
					var child    = null;
					var childId  = null;
					
					for(cont = 0 ; cont < children.length ; cont++){
						child   = children[cont];
						childId = child.id;
						
						if(childId){
							if(childId.indexOf(".treeViewNode") >= 0){
								child = getObject(childId + ".label");
	
								if(child)
									if(nodeLabel.className != child.className)
										unselectTreeViewNode(name, childId, hasMultipleSelection, selectUnselectParentsOrChildren);
							}
						}
					}
				}
			}
		}
		else{
			setObjectValue(name + ".currentTreeViewNode", "");
			setObjectValue(name, "");
		}
	}

	if(onUnSelect)
		onUnSelect();
}

/**
 * select/unselect a node and its children.
 * 
 * @param name String that contains the identifier of the node.
 * @param nodeId String that contains the identifier of the node.
 * @param hasMultipleSelection Indicates if the component has multiple selection.
 * @param selectUnselectParentsOrChildren Indicates if the parents and children should be selected.
 * @param onSelect String that content the select event.
 */
function selectTreeViewNode(name, nodeId, hasMultipleSelection, selectUnselectParentsOrChildren, dontSelectChildren, onSelect){
	var nodeLabel = getObject(nodeId + ".label");
	
	if(nodeLabel){
		nodeLabel.className = "selectedTreeViewNodeLabel";
		
		var nodeValue = nodeLabel.getAttribute("value");
		
		if(hasMultipleSelection){
			var options = getObject(name).options;
			var cont    = 0;
			
			for(cont = 0 ; cont < options.length ; cont++){
				if(options[cont].value == nodeValue && options[cont].selected == false){
					options[cont].selected = true;
					
					break;
				}
			}
			
			if(selectUnselectParentsOrChildren != null && selectUnselectParentsOrChildren){
				var parentNodeLabelId = nodeLabel.getAttribute("parent");
			
				selectTreeViewNode(name, parentNodeLabelId, hasMultipleSelection, selectUnselectParentsOrChildren, true);
			
				if(!dontSelectChildren || dontSelectChildren == false){
					var node = getObject(nodeId);
					
					if(node){
						var children = node.childNodes;
						var child    = null;
						var childId  = null;
						
						for(cont = 0 ; cont < children.length ; cont++){
							child   = children[cont];
							childId = child.id;
							
							if(childId){
								if(childId.indexOf(".treeViewNode") >= 0){
									child = getObject(childId + ".label");
		
									if(child)
										if(nodeLabel.className != child.className)
											selectTreeViewNode(name, childId, hasMultipleSelection, selectUnselectParentsOrChildren, false);
								}
							}
						}
					}
				}
			}
		}
		else{
			var currentNode = getObjectValue(name + ".currentTreeViewNode");
			var nodeLabel   = getObject(currentNode + ".label");
			
			if(nodeLabel)
				if(currentNode != nodeId)
					nodeLabel.className = "treeViewNodeLabel";
	
			setObjectValue(name + ".currentTreeViewNode", nodeId);
			setObjectValue(name, nodeValue);
		}

		if(onSelect)
			onSelect();
	}
}