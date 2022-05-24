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
let modalDialogBox = null;

function showDialogBox(name, modal){
	let dialogBoxObject = getObject(name + ".dialogBox");

	if(dialogBoxObject){
		dialogBoxObject.style.visibility = "VISIBLE";
		
		let pageShadeObject = getObject("pageShade");
		
		if(modal && pageShadeObject){
			let windowLeft   = document.body.offsetLeft;
			let windowTop    = document.body.offsetTop;
			let windowWidth  = window.innerWidth;
			let windowHeight = window.innerHeight;

			pageShadeObject.style.left       = windowLeft + "px";
			pageShadeObject.style.top        = windowTop + "px";
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
	let dialogBoxObject = getObject(name + ".dialogBox");
	
	if(dialogBoxObject){
		dialogBoxObject.style.visibility = "HIDDEN";
		
		if(modalDialogBox && name === modalDialogBox){
			let pageShadeObject = getObject("pageShade");
	
			if(pageShadeObject)
				pageShadeObject.style.visibility = "HIDDEN";
		}
	}
}

/**
 * Centralizes all dialog boxes.
 */
function centralizeDialogBoxes(){
	let pageShadeObject  = getObject("pageShade");
    let dialogBoxObject  = null;
	let dialogBoxObjects = document.getElementsByTagName("div"); 
    let dialogBoxId      = null;
    let windowLeft       = document.body.offsetLeft;
    let windowTop        = document.body.offsetTop;
    let windowWidth      = window.innerWidth;
    let windowHeight     = window.innerHeight;
    
	if(pageShadeObject){
		if(pageShadeObject.style.visibility.toUpperCase() !== "HIDDEN" && pageShadeObject.style.visibility !== ""){
			pageShadeObject.style.left   = windowLeft + "px";
			pageShadeObject.style.top    = windowTop + "px";
			pageShadeObject.style.width  = windowWidth + "px";
			pageShadeObject.style.height = windowHeight + "px";
		}
	}

	for(let cont = 0 ; cont < dialogBoxObjects.length ; cont++){
		dialogBoxObject = dialogBoxObjects[cont];
		dialogBoxId     = dialogBoxObject.id;
			
       	if(dialogBoxId && dialogBoxId.indexOf(".dialogBox") >= 0)
	       	centralizeObject(dialogBoxObject);
    }
}