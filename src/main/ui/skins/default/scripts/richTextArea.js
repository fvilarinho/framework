/**
 * Defines the UI routines to manipulate the rich text area component.
 * 
 * @author fvilarinho
 * @version 1.0.0
 */

/**
 * Initializes the component.
 * 
 * @param name String that contains the identifier of the rich text area component.
 */
function initializeRichTextArea(name){
	let contentObject = getObject(name + ".content");
	
	if(contentObject)
		contentObject.addEventListener("keyup", updateRichTextArea, true);
} 

/**
 * Executes a command.
 * 
 * @param name String that contains the identifier of the rich text area component.
 * @param command String that contains the command.
 * @param args List that contains the command arguments.
 */
function executeCommand(name, command, args){
	let contentObject = getObject(name + ".content");
	
	if(contentObject)
		document.execCommand(command, false, args);
}

/**
 * Updates the rich text area component.
 * 
 * @param e Instance that contains the event.
 */
function updateRichTextArea(e){
	let divs = document.getElementsByTagName("div");

	if(divs){
		for(let i = 0 ; i < divs.length ; i++){
			let div = divs[i];
			let id  = div.id;
			
			if(id && id.indexOf(".content") >= 0){
				id = replaceAll(id, ".content", "");
				
				if(div)
					setObjectValue(id, div.innerHTML);
			}
		}
	}
}