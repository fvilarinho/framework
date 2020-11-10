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
	var divs = document.getElementsByTagName("div");
	
	for(var i = 0 ; i < divs.length ; i++){
		var div = divs[i];
		var id  = div.id;
		
		if(id && id.indexOf(".colorPickerDialog") >= 0 && id != (name + ".colorPickerDialog"))
			div.style.visibility = "HIDDEN";
	}
	
	var object = getObject(name + ".colorPickerDialog");
	
	if(object){
		if(object.style.visibility.toUpperCase() == "VISIBLE")
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
	var redValue   = getObjectValue(name + ".redValue");
	var greenValue = getObjectValue(name + ".greenValue");
	var blueValue  = getObjectValue(name + ".blueValue");
	var value      = "rgb(" + (redValue != "" ? redValue : "0") + ", " + (greenValue != "" ? greenValue : "0") + ", " + (blueValue != "" ? blueValue: "0") + ")";
	
	setObjectValue(name, value);
	
	var thumbnailObject = getObject(name + ".colorPickerThumbnail");
	
	if(thumbnailObject)
		thumbnailObject.style.background = value;
}