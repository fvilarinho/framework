/**
 * Defines the UI routines to manipulate the message box component.
 * 
 * @author fvilarinho
 * @version 1.0.0
 */
 
/**
 * Show/Hide the error trace.
 * 
 * @param name String that contains the identifier of the message box component.
 */
function showHideErrorTrace(name){
	let messageBoxObject           = getObject(name + ".dialogBox");
	let messageBoxErrorTraceObject = getObject(name + ".errorTrace");

	if(messageBoxObject && messageBoxErrorTraceObject){
		if(messageBoxErrorTraceObject.style.display.toUpperCase() === "NONE")
			messageBoxErrorTraceObject.style.display = "";
		else
			messageBoxErrorTraceObject.style.display = "NONE";
		
		centralizeObject(messageBoxObject);
	}
}