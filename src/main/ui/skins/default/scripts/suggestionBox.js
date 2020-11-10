/**
 * Defines the UI routines to manipulate the suggestion box component. 
 * 
 * @author fvilarinho
 * @version 3.3.0
 */

var suggestionBoxTimer = null;

function hideSuggestionBox(name){
	var object = getObject(name + ".suggestionBox");
	
	if(object)
		object.style.visibility = "HIDDEN";
}