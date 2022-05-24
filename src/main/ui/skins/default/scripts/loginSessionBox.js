/**
 * Defines the UI routines to manipulate the login session box component. 
 * 
 * @author fvilarinho
 * @version 1.0.0
 */

/**
 * Show/Hide the login session box component.
 */
 function showHideLoginSessionBoxContent(){
	let object = getObject("loginSessionBoxContent");
	
	if(object){
		if(object.style.visibility.toUpperCase() === "HIDDEN" || object.style.visibility === "")
			object.style.visibility = "VISIBLE";
		else
			object.style.visibility = "HIDDEN";
	}
}