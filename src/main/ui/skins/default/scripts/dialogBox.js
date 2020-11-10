/**
 * Defines the UI routines that manipulates the dialog box component. 
 * 
 * @author fvilarinho
 * @version 3.0.0
 */
 
/**
 * Show the dialog box.
 * 
 * @param name String that contains the identifier of the dialog box component.
 * @param modal Indicates if the dialog box has a modal behavior.
 */
var modalDialogBox = null;

function showDialogBox(name, modal){
	var dialogBoxObject = getObject(name + ".dialogBox");
	var windowWidth     = 0;
	var windowHeight    = 0;
	
	if(dialogBoxObject){
		dialogBoxObject.style.visibility = "VISIBLE";
		
		var pageShadeObject = getObject("pageShade");
		
		if(modal && pageShadeObject){
			windowLeft   = (isIe() ? document.body.scrollLeft : window.pageXOffset);
			windowTop    = (isIe() ? document.body.scrollTop : window.pageYOffset);
			windowWidth  = (isIe() ? document.body.scrollWidth : window.innerWidth);
			windowHeight = (isIe() ? document.body.scrollHeight : window.innerHeight);

			pageShadeObject.style.left       = windowLeft + "px";
			pageShadeObject.style.top        = (windowTop - 1) + "px";
			pageShadeObject.style.width      = windowWidth + "px";
			pageShadeObject.style.height     = windowHeight + "px";
			pageShadeObject.style.visibility = "VISIBLE";
			
			window.scroll(0, 0);
			
			modalDialogBox = name;
		}
		
		centralizeObject(dialogBoxObject);
	}
}

/**
 * Hides the dialog box.
 * 
 * @param name String that contains the identifier of the dialog box component.
 */
function hideDialogBox(name){
	var dialogBoxObject = getObject(name + ".dialogBox");
	
	if(dialogBoxObject){
		dialogBoxObject.style.visibility = "HIDDEN";
		
		if(modalDialogBox && name == modalDialogBox){
			var pageShadeObject = getObject("pageShade");
	
			if(pageShadeObject)
				pageShadeObject.style.visibility = "HIDDEN";
		}
	}
}

/**
 * Centralizes all dialog boxes.
 */
function centralizeDialogBoxes(){
	var pageShadeObject  = getObject("pageShade");
    var dialogBoxObject  = null;
	var dialogBoxObjects = document.getElementsByTagName("div"); 
    var dialogBoxId      = null;
    var cont             = 0;
    var windowLeft       = (isIe() ? document.body.offsetLeft : window.pageXOffset);
    var windowTop        = (isIe() ? document.body.offsetTop : window.pageYOffset);
    var windowWidth      = (isIe() ? document.body.offsetWidth - 20: window.innerWidth);
    var windowHeight     = (isIe() ? document.body.offsetHeight - 5 : window.innerHeight);
    
	if(pageShadeObject){
		if(pageShadeObject.style.visibility.toUpperCase() != "HIDDEN" && pageShadeObject.style.visibility != ""){
			pageShadeObject.style.width  = windowWidth + "px";
			pageShadeObject.style.height = windowHeight + "px";
			pageShadeObject.style.left   = windowLeft + "px";
			pageShadeObject.style.top    = windowTop + "px";
		}
	}

	for(cont = 0 ; cont < dialogBoxObjects.length ; cont++){
    		dialogBoxObject = dialogBoxObjects[cont];
    		dialogBoxId     = dialogBoxObject.id;
			
       	if(dialogBoxId && dialogBoxId.indexOf(".dialogBox") >= 0)
	       	centralizeObject(dialogBoxObject);
    }
}