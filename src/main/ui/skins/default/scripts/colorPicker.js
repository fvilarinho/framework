/**
 * Defines the UI routines to manipulate the color picker component.
 * 
 * @author fvilarinho
 * @version 3.0.0
 */
 
/**
 * Show/Hide the color picker component.
 * 
 * @param name String that contains the identifier of the color picker component.
 */
function showHideColorPickerDialog(name){
	let divs = document.getElementsByTagName("div");
	
	for(let i = 0 ; i < divs.length ; i++){
		let div = divs[i];
		let id  = div.id;
		
		if(id && id.indexOf(".colorPickerDialog") >= 0 && id !== (name + ".colorPickerDialog"))
			div.style.visibility = "HIDDEN";
	}
	
	let object = getObject(name + ".colorPickerDialog");
	
	if(object){
		if(object.style.visibility.toUpperCase() === "VISIBLE")
			object.style.visibility = "HIDDEN";
		else
			object.style.visibility = "VISIBLE";
	}
}

/**
 * Changes the color picker thumbnail.
 * 
 * @param name String that contains the identifier of the color picker component.
 */
function changeColorPickerThumbnail(name){
	let redValue   = getObjectValue(name + ".redValue");
	let greenValue = getObjectValue(name + ".greenValue");
	let blueValue  = getObjectValue(name + ".blueValue");
	let value      = "rgb(" + (redValue !== "" ? redValue : "0") + ", " + (greenValue !== "" ? greenValue : "0") + ", " + (blueValue !== "" ? blueValue: "0") + ")";
	
	setObjectValue(name, value);
	
	let thumbnailObject = getObject(name + ".colorPickerThumbnail");
	
	if(thumbnailObject)
		thumbnailObject.style.background = value;
}