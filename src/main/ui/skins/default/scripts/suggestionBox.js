/**
 * Defines the UI routines to manipulate the suggestion box component. 
 * 
 * @author fvilarinho
 * @version 3.3.0
 */
let suggestionBoxTimer = null;

function hideSuggestionBox(name){
	let object = getObject(name + ".suggestionBox");
	
	if(object)
		object.style.visibility = "HIDDEN";

	clearTimeout(suggestionBoxTimer);
}