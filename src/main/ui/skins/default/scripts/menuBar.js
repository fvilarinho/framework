/**
 * Defines the UI routines to manipulate the menu bar component. 
 * 
 * @author fvilarinho
 * @version 1.0.0
 */
var currentMenu = null;
 
/**
 * Finds the parent menu.
 * 
 * @param menu Instance that contains the selected menu item.
 * @return Instance that contains the parent menu.
 */
function findParentMenu(menu){
	var parentMenu   = menu;
    var parentMenuId = "";
    
    while(true){
    		parentMenu = parentMenu.parentNode;
        
       	if(!parentMenu)
       	  	break;
            
       	parentMenuId = parentMenu.id;
        
       	if(parentMenuId)
       		if(parentMenuId.indexOf(".menuBox") >= 0 || parentMenuId.indexOf(".menuBar") >= 0)
           		break;
    }
    
    return parentMenu;
}

/**
 * Selects a menu item.
 * 
 * @param menuItem Instance that contains the menu item.
 * @param menuItemClass String that contains the CSS style of the menu item.
 */
function selectMenuItem(menuItem, menuItemClass){
	if(menuItem){
		var currentMenuId = "";
		var menuItemId    = replaceAll(menuItem.id, ".menuItem", "");
       	var parentMenu    = findParentMenu(menuItem);
       	var parentMenuId  = parentMenu.id;
       	var menu          = getObject(menuItemId + ".menuBox");

       	if(parentMenuId.indexOf(".menuBar") < 0)
	     	menuItem.parentNode.className = menuItemClass;

    		menuItem.className = menuItemClass;
	    
       	if(currentMenu){
       		currentMenuId = currentMenu.id;
        	
           	if(currentMenuId != parentMenuId){
               	var bufferMenuId = "";
               	var bufferMenu   = currentMenu;

               	while(true){
                   	if(bufferMenu){
    	               		bufferMenuId = bufferMenu.id;
                        
                   		if(bufferMenuId == parentMenuId)
                       		break;

                   		bufferMenu.style.visibility = "HIDDEN";
                   	}
                   	else
                   		break;

                   	bufferMenu = findParentMenu(bufferMenu);
               	}
           	}
        }
	
        if(menu){
          	if(parentMenuId.indexOf(".menuBar") >= 0){
        	   		menu.style.left = parseInt(menuItem.offsetLeft) - 1;
               	menu.style.top  = parseInt(parentMenu.offsetHeight);
            }
            else{
               	menu.style.left = parseInt(menuItem.parentNode.offsetWidth) + 4;
               	menu.style.top  = parseInt(menuItem.offsetTop) - 1;
            }

            menu.style.visibility = "VISIBLE";
        
            currentMenu = menu;
        }
	}
}

/**
 * Unselects a menu item.
 * 
 * @param menuItem Instance that contains the menu item.
 * @param menuItemClass String that contains the CSS style of the menu item.
 */
function unselectMenuItem(menuItem, menuItemClass){
	if(menuItem){
		var parentMenu   = findParentMenu(menuItem);
       	var parentMenuId = parentMenu.id;
        
   		menuItem.parentNode.className = menuItemClass;
       	menuItem.className            = menuItemClass;
    }
}
 
/**
 * Renders a fixed menu bar.
 * 
 * @param name String that contains the identifier of the menu bar component.
 */
function renderFixedMenuBar(name){
	var object = getObject(name + ".menuBar");
	
	if(object){
		object.style.left = 0;
		object.style.top = document.body.scrollTop;
	}
}