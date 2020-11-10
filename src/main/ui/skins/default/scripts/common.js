/**
 * Defines the UI routines for general use. 
 * visuais.
 * 
 * @author fvilarinho
 * @version 1.0.0
 */
var currentRequestHandler = null;

/**
 * Indicates if the browser is the Microsoft Internet Explorer.
 * 
 * @return True/False.
 */
function isIe(){
	if(document.all)
		return true;
	
	return false;
}

/**
 * Replicates a string.
 * 
 * @param value String that will be replicated.
 * @param times Numeric value that contains the repeats.
 * @return String after the processing.
 */
function replicate(value, times){
	var cont   = 0;
	var result = "";
	
	for(cont = 0 ; cont < times ; cont++)
		result += value;
	
	return result;
}

/**
 * Replaces all occurrences of a string by another.
 *  
 * @param value String that will be used..
 * @param searchValue Search string.
 * @param replaceValue Replace string.
 * @return String after the processing.
 */
function replaceAll(value, searchValue, replaceValue){
	var pos    = 0;
	var result = "";
	
	pos = value.indexOf(searchValue);
	
	if(pos >= 0){
		result  = value.substring(0, pos) + replaceValue;
		result += value.substring(pos + searchValue.length);
			
		return replaceAll(result, searchValue, replaceValue);
	}

	return value;
}

/**
 * Returns the identifier of the current language.
 * 
 * @returns String that contains the identifier. 
 */
function getCurrentLanguage(){
	return replaceAll(getObjectValue("currentLanguage"), "_", "-");
}

/**
 * Returns the identifier of the current skin.
 * 
 * @returns String that contains the identifier. 
 */
function getCurrentSkin(){
	return replaceAll(getObjectValue("currentSkin"), "_", "-");
}

/**
 * Applies a general mask.
 * 
 * @param object Instance that contains the input component.
 * @param event Instance that contains the event.
 */
function applyGenericMask(object, event){
	if(!object || object.readOnly || object.disabled){
		if(window.event)
			window.event.returnValue = null;
		else
	       	event.preventDefault();
	       	
	    return;
	}
	
	var key = getKeyPressed(event);
	
	if(key == 8 || key == 13 || key == 0)
		return;

	var objectValue = object.value;
	var pattern     = getObjectValue(object.name + ".pattern");
	
	if(objectValue.length >= pattern.length){
		if(window.event)
			window.event.returnValue = null;
		else
	       	event.preventDefault();

		return;
	}
	
	var cursorPosition = getObjectCursorPosition(object);
	
	if(cursorPosition < objectValue.length){
		if(window.event)
			window.event.returnValue = null;
		else
	       	event.preventDefault();

		return;
	}
	
	var cont1           = 0;
	var pos             = 0;
	var delimitersPos   = "";
	var delimitersChars = "";

	for(cont1 = 0 ; cont1 < pattern.length ; cont1++){
		if(pattern.charAt(cont1) != "9" && pattern.charAt(cont1) != "#"){
			if(pos > 0){
				delimitersPos += "|";
				delimitersChars += "|";
			}

			delimitersPos   += cont1;
			delimitersChars += pattern.charAt(cont1);

			pos++;
		}
	}

	var bufferChar = String.fromCharCode(key);

	if(pattern.charAt(objectValue.length) == "9"){
		if(isNaN(bufferChar)){
			if(window.event)
				window.event.returnValue = null;
			else
		       	event.preventDefault();

			return;
		}
	}
	else{
		if(bufferChar == pattern.charAt(objectValue.length)){
			if(window.event)
				window.event.returnValue = null;
			else
		       	event.preventDefault();
		       	
		    object.value = objectValue + bufferChar;
		    
		    return;
		}
		
		if(pattern.charAt(objectValue.length + 1) == "9"){
			if(isNaN(bufferChar)){
				if(window.event)
					window.event.returnValue = null;
				else
			       	event.preventDefault();
	
				return;
			}
		}
	}

	if(delimitersPos.length > 0){
		var delimitersPosArray   = delimitersPos.split("|");
		var delimitersCharsArray = delimitersChars.split("|");
		var cont2                = 0;
		var cont3                = 0;
		
		for(cont1 = 0 ; cont1 <= delimitersPosArray.length ; cont1++)
			for(cont2 = 0 ; cont2 <= objectValue.length ; cont2++)
				if(cont2 == delimitersPosArray[cont1])
	  				if(objectValue.substring(cont2, cont2 + 1) != delimitersCharsArray[cont1])
	  					objectValue = objectValue.substring(0, cont2) + delimitersCharsArray[cont1] + objectValue.substring(cont2 + 1, objectValue.length);
		
		object.value = objectValue;
	}
}

/**
 * Applies the date/time mask.
 * 
 * @param name String that contains the identifier of the input component.
 * @param event Instance that contains the event.
 */
function applyDateTimeMask(object, event){
	if(!object || object.readOnly || object.disabled){
		if(window.event)
			window.event.returnValue = null;
		else
	       	event.preventDefault();
	       	
	    return;
	}

	var key = getKeyPressed(event);
	
	if(key == 8 || key == 13 || key == 0)
		return;

	var objectValue = object.value;
	var pattern     = getObjectValue(object.name + ".pattern");
	
	if(objectValue.length >= pattern.length){
		if(window.event)
			window.event.returnValue = null;
		else
	       	event.preventDefault();

		return;
	}
	
	var cursorPosition = getObjectCursorPosition(object);
	
	if(cursorPosition < objectValue.length){
		if(window.event)
			window.event.returnValue = null;
		else
	       	event.preventDefault();

		return;
	}

	var cont1           = 0;
	var pos             = 0;
	var delimitersPos   = "";
	var delimitersChars = "";
	
	for(cont1 = 0 ; cont1 < pattern.length ; cont1++){
		if(pattern.charAt(cont1) != "d" && pattern.charAt(cont1) != "M" && pattern.charAt(cont1) != "m" && pattern.charAt(cont1) != "y" && pattern.charAt(cont1) != "H" && pattern.charAt(cont1) != "h" && pattern.charAt(cont1) != "s" && pattern.charAt(cont1) != "S" && pattern.charAt(cont1) != "a"){
			if(pos > 0){
				delimitersPos += "|";
				delimitersChars += "|";
			}

			delimitersPos   += cont1;
			delimitersChars += pattern.charAt(cont1);

			pos++;
		}
	}

	var bufferChar = String.fromCharCode(key);

	if(pattern.charAt(objectValue.length) == "d" || pattern.charAt(objectValue.length) == "M" || pattern.charAt(objectValue.length) == "m" || pattern.charAt(objectValue.length) == "y" || pattern.charAt(objectValue.length) == "H" || pattern.charAt(objectValue.length) == "h" || pattern.charAt(objectValue.length) == "s" || pattern.charAt(objectValue.length) == "S"){
		if(isNaN(bufferChar)){
			if(window.event)
				window.event.returnValue = null;
			else
		       	event.preventDefault();

			return;
		}
	}
	else{
		if(pattern.charAt(objectValue.length) == "a" || pattern.charAt(objectValue.length + 1) == "a"){
			var delimiter = "";
			
			if(pattern.charAt(objectValue.length) != "d" && pattern.charAt(objectValue.length) != "M" && pattern.charAt(objectValue.length) != "y" && pattern.charAt(objectValue.length) != "H" && pattern.charAt(objectValue.length) != "m" && pattern.charAt(objectValue.length) != "s" && pattern.charAt(objectValue.length) != "S" && pattern.charAt(objectValue.length) != "a")
				delimiter = pattern.charAt(objectValue.length);
			
			if(bufferChar.toLowerCase() == "a")
				bufferChar = "AM";
			else
				bufferChar = "PM";
			
			if(window.event)
				window.event.returnValue = null;
			else
		       	event.preventDefault();
			
		    object.value = objectValue + delimiter + bufferChar;
		    
		    if(object.onchange)
		    		object.onchange();
		    
		    return;
		}
		
		if(bufferChar == pattern.charAt(objectValue.length)){
			if(window.event)
				window.event.returnValue = null;
			else
		       	event.preventDefault();
		       	
		    object.value = objectValue + bufferChar;
		    
		    if(object.onchange)
		    		object.onchange();

		    return;
		}
		
		if(pattern.charAt(objectValue.length + 1) == "d" || pattern.charAt(objectValue.length + 1) == "M" || pattern.charAt(objectValue.length + 1) == "m" || pattern.charAt(objectValue.length + 1) == "y" || pattern.charAt(objectValue.length + 1) == "H" || pattern.charAt(objectValue.length + 1) == "h" || pattern.charAt(objectValue.length + 1) == "s" || pattern.charAt(objectValue.length + 1) == "S"){
			if(isNaN(bufferChar)){
				if(window.event)
					window.event.returnValue = null;
				else
			       	event.preventDefault();
	
				return;
			}
		}
	}

	if(delimitersPos.length > 0){
		var delimitersPosArray   = delimitersPos.split("|");
		var delimitersCharsArray = delimitersChars.split("|");
		var cont2                = 0;
		var cont3                = 0;
		
		for(cont1 = 0 ; cont1 <= delimitersPosArray.length ; cont1++)
			for(cont2 = 0 ; cont2 <= objectValue.length ; cont2++)
				if(cont2 == delimitersPosArray[cont1])
	  				if(objectValue.substring(cont2, cont2 + 1) != delimitersCharsArray[cont1])
	  					objectValue = objectValue.substring(0, cont2) + delimitersCharsArray[cont1] + objectValue.substring(cont2 + 1, objectValue.length);
		
		object.value = objectValue;

		if(object.onchange)
	    	object.onchange();
	}
}

/**
 * Applies the numeric mask.
 * 
 * @param object Instance that contains the input component.
 * @param minimumValue Numeric value that contains the minimum value permitted.
 * @param maximumValue Numeric value that contains the maximum value permitted.
 * @param useGroupSeparator Indicates the the mask should use the group separator.
 * @param groupSeparator String that contains the group separator.
 * @param precision Numeric value that contains the decimal precision.
 * @param decimalSeparator String that contains the decimal separator.
 * @param event Instance that contains the event.
 */
function applyNumericMask(object, minimumValue, maximumValue, useGroupSeparator, groupSeparator, precision, decimalSeparator, event){
	if(!object || object.readOnly || object.disabled){
		if(window.event)
			window.event.returnValue = null;
		else if(event)
	       	event.preventDefault();
	       	
	    return;
	}
	
	var key = 0;

	if(event){
		key = getKeyPressed(event);
		
		if(key == 8 || key == 13 || key == 0)
			return;
		
		if(window.event)
			window.event.returnValue = null;
		else
	       	event.preventDefault();
	}
	
	var objectValue = object.value;
	var isNegative  = objectValue.startsWith("-");
	var bufferChar  = (key != 0 ? String.fromCharCode(key) : "");
	
    if(bufferChar == "-" && isNegative)
    	return;   	
	
	if(bufferChar == "+"){
		objectValue = replaceAll(objectValue, "-", "");
		object.value = objectValue;
	}
	else if(bufferChar == "-"){
		objectValue = bufferChar + objectValue;
		object.value = objectValue;
	}
    else{
		if(isNaN(bufferChar))
			return;
		
		objectValue = replaceAll(objectValue, "-", "");

		if(useGroupSeparator && groupSeparator != "")
    		objectValue = replaceAll(objectValue, groupSeparator, "");

		if(precision == null)
			precision = 0;

		if(precision > 0 && decimalSeparator != "" && decimalSeparator != ".")
    		objectValue = replaceAll(objectValue, decimalSeparator, "");

		if(objectValue == ""){
			if(precision > 0){
				objectValue = "0";

				if(bufferChar == "0")
					objectValue += replicate("0", precision);
				else{
					objectValue += replicate("0", precision - 1);
					objectValue += bufferChar;
				}
			}
			else
				objectValue = bufferChar;
		}
		else{
			if(objectValue == "0"){
				if(bufferChar == "" || bufferChar == "0")
					return;

				objectValue = bufferChar;
			}
			else
				objectValue += bufferChar;
			
			if(precision > 0)
				if(objectValue.charAt(0) == "0")
					objectValue = objectValue.substring(1, objectValue.length)
		}
		
		var groupBuffer   = objectValue.substring(0, (objectValue.length - precision));
		var decimalBuffer = "";
		
		if(precision > 0 && decimalSeparator != "")
			decimalBuffer = objectValue.substring((objectValue.length - precision), objectValue.length);
    
		if(minimumValue != null && maximumValue != null){
    		var objectValueBuffer = parseFloat((isNegative ? "-" : "") + groupBuffer + (precision > 0 ? "." : "") + decimalBuffer);
    	
    		if(objectValueBuffer < minimumValue)
    			return;
    	
    		if(objectValueBuffer > maximumValue)
    			return;
		}

		if(useGroupSeparator && groupSeparator != ""){
			var pos    = 0;
			var buffer = "";
	    
			for(cont = groupBuffer.length ; cont >= 0 ; cont--){
				if(pos == 4){
					buffer = groupSeparator + buffer;
		
					pos = 1;
				}

				buffer = groupBuffer.charAt(cont) + buffer;

				pos++;
			}
	    
			groupBuffer = buffer;
		}
    
		objectValue  = (isNegative ? "-" : "") + groupBuffer + (precision > 0 ? decimalSeparator : "") + decimalBuffer;
		object.value = objectValue;
    }

  	if(object.onchange)
    	object.onchange();
}

/**
 * Returns the current cursor position of a input.
 * 
 * @param object Instance that contains the input.
 * @return Numeric value that contains the cursor position.
 */
function getObjectCursorPosition(object){
	var range = null;
	
	if(typeof object.selectionStart == "number")
		return object.selectionStart;
	else if(document.selection){
		range = document.selection.createRange();
		
		range.collapse(true);
		range.moveStart("character", -object.value.length);

		return range.text.length;
	}
}

/**
 * Returns the instance of an object.
 * 
 * @param name String that contains the identifier of the object.
 * @returns Instance that contains the object.
 */
function getObject(name){
	var object = document.getElementById(name);
	
	return object;
}

/**
 * Returns the value of an object.
 * 
 * @param name String that contains the identifier of the object.
 * @returns Instance that contains the value of the object.
 */
function getObjectValue(name){
	var object = getObject(name);
	
	if(object)
		return object.value;
	
	return null;
}

/**
 * Defines the value of an object.
 * 
 * @param name String that contains the identifier of the object.
 * @param value Instance that contains the value of the object.
 */
function setObjectValue(name, value){
	var object = getObject(name);
	
	try{
		if(object)
			object.value = value;
	}
	catch(e){
	}
}

/**
 * Puts the focus in a object.
 * 
 * @param name String that contains the identifier of the object.
 */
function focusObject(name){
	try{
		var object = getObject(name);
		
		if(object){
			object.focus();
			object.select();
		}
	}
	catch(e){
	}
}

/**
 * Centralizes an object.
 * 
 * @param object Instance that contains the object.
 */
function centralizeObject(object){
	var windowWidth  = null;
	var windowHeight = null;
	var objectWidth  = null;
	var objectHeight = null;
	var objectTop    = null;

	objectWidth  = object.offsetWidth;
	objectHeight = object.offsetHeight;
	objectTop    = (isIe() ? document.body.offsetTop : window.pageYOffset);
  	windowWidth  = (isIe() ? document.body.scrollWidth : window.innerWidth);
	windowHeight = (isIe() ? document.body.scrollHeight : window.innerHeight);

	object.style.left = ((windowWidth - objectWidth) / 2) + "px";
	object.style.top  = (objectTop + (windowHeight - objectHeight) / 2) + "px";
}

/**
 * Returns the ASCII code of the key press event.
 * 
 * @param event Instance that contains the event.
 * @return Numeric value that contains the ASCII code.
 */
function getKeyPressed(event){
	var key;
  
	if(window.event)
		key = event.keyCode;
	else
     	key = event.which;
        
   	return key;
}

/**
 * Adds a load event.
 * 
 * @param functionId String that contains the function that will be executed.
 */
function addLoadEvent(functionId){
	var loadHistory = window.onload;

	if(typeof loadHistory != "function")
		window.onload = functionId;
	else{
		window.onload = function(){
							if(loadHistory)
		               			loadHistory();
		               		
		               		functionId();
		                }
	}
}

/**
 * Adds a resize event.
 * 
 * @param functionId String that contains the function that will be executed.
 */
function addResizeEvent(functionId){
	var resizeHistory = window.onresize;

	if(typeof resizeHistory != "function")
		window.onresize = functionId;
	else{
		window.onresize = function(){
								if(resizeHistory)
									resizeHistory();
		               			
		               			functionId();
		                  }
	}
} 

/**
 * Adds a mouse move event.
 * 
 * @param functionId String that contains the function that will be executed.
 */
function addMouseMoveEvent(functionId){
	var mouseMoveHistory = window.onmousemove;

	if(typeof mouseMoveHistory != "function")
		window.onmousemove = functionId;
	else{
		window.onmousemove = function(){
							 	if(mouseMoveHistory)
							 		mouseMoveHistory();
		               			
		               			functionId();
		                  	 }
	}
}

/**
 * Adds a scroll event.
 * 
 * @param functionId String that contains the function that will be executed.
 */
function addScrollEvent(functionId){
	var scrollHistory = window.onscroll;

	if(typeof scrollHistory != "function")
		window.onscroll = functionId;
	else{
		window.onscroll = function(){
								if(scrollHistory)
									scrollHistory();
		               			
		               			functionId();
		                  }
	}
}

/**
 * Adds a click event.
 * 
 * @param functionId String that contains the function that will be executed.
 */
function addClickEvent(functionId){
	var clickHistory = document.onclick;

	if(typeof clickHistory != "function")
		document.onclick = functionId;
	else{
		document.onclick = function(){
								if(clickHistory)
		               				clickHistory();
		               			
		               			functionId();
		                   }
	}
}

/**
 * Shows the loading box.
 */
function showLoadingBox(){
	var loadingBoxObject = getObject("loadingBox");
		
	if(loadingBoxObject){
		loadingBoxObject.style.visibility = "VISIBLE";
		loadingBoxObject.style.display = "";
		loadingBoxObject.style.opacity = 1;

		centralizeObject(loadingBoxObject);
	}
}

/**
 * Hides the loading box.
 */
function hideLoadingBox(){
	var loadingBoxObject = getObject("loadingBox");
		
	if(loadingBoxObject){
		loadingBoxObject.style.visibility = "HIDDEN";
		loadingBoxObject.style.display = "NONE";
		loadingBoxObject.style.opacity = 0;
	}
}

/**
 * Builds the action form request.
 * 
 * @param actionForm Instance that contains the action form.
 */
function buildActionFormRequest(actionForm){
	var cont1           = 0;
	var cont2           = 0;
	var result          = "";
	var elements        = actionForm.elements;
	var element         = null;
	var elementType     = null;
	var elementName     = null;
	var elementChecked  = null;
	var elementDisabled = null;
	var elementValue    = null;
	var elementOptions  = null;
	var elementOption   = null;
	
	for(cont1 = 0 ; cont1 < elements.length ; cont1++){
		element         = elements[cont1];
		elementType     = element.type;
		elementName     = element.name;
		elementDisabled = element.disabled;
		
		if(elementType != null){
			elementType = elementType.toUpperCase();
			
			if(elementType.indexOf("FILE") < 0){
				if(elementDisabled == false && elementName != ""){
					if(elementType.indexOf("SELECT-") >= 0){
						elementOptions = element.options;
						
						for(cont2 = 0 ; cont2 < elementOptions.length ; cont2++){
							elementOption = elementOptions[cont2];
							
							if(elementOption.selected == true){
								elementValue = elementOption.value;
								
								if(result.length > 0)
									result += "&";
							
								result += elementName;
								result += "=";
								
								if(elementValue != null && elementValue.length > 0)
									result += encodeURIComponent(elementValue);
							}
						}
					}
					else if(elementType == "RADIO" || elementType == "CHECKBOX"){
						elementChecked = element.checked;
						elementValue   = element.value;
						
						if(elementChecked == true){
							if(result.length > 0)
								result += "&";
								
							result += elementName;
							result += "=";
							
							if(elementValue != null && elementValue.length > 0)
								result += encodeURIComponent(elementValue);
						}		
					}
					else if(elementType != "BUTTON" && elementType != "SUBMIT"){
						elementValue = element.value;
						
						if(result.length > 0)
							result += "&";
							
						result += elementName;
						result += "=";
						
						if(elementValue != null && elementValue.length > 0)
							result += encodeURIComponent(elementValue);
					}	
				}
			}
		}
	}
	
	return result;
}

/**
 * Submits the action form.
 * 
 * @param actionForm Instance that contains the action form.
 */
function submitActionForm(actionForm){
	showLoadingBox();
	
	var type        = "POST";
	var url         = actionForm.attributes["action"].value;
	var parameters  = buildActionFormRequest(actionForm);
	var enctype     = (actionForm.attributes["enctype"] ? actionForm.attributes["enctype"].value : null);
	var updateViews = actionForm.elements["updateViews"].value;
	var target      = (actionForm.attributes["target"] ? actionForm.attributes["target"].value : null);
	
	submitRequest(type, url, parameters, enctype, updateViews, target);
}

/**
 * Submits a request.
 * 
 * @param type Instance that contains the type of the request.
 * @param url String that contains the URL of the request.
 * @param parameters List that contains the request parameters.
 * @param enctype String that contains the mime type of the request.
 * @param updateViews String that contains the views that will be updated.
 * @param target String that contains the target of the request.
 * @returns True/False.
 */
function submitRequest(type, url, parameters, enctype, updateViews, target){
	if(currentRequestHandler != null)
		return false;

	var requestHandler = null;
	
	if(window.XMLHttpRequest)
		requestHandler = new XMLHttpRequest();
	else if(window.ActiveXObject){
		try{
			requestHandler = new ActiveXObject("Msxml2.XMLHTTP");
		}
		catch(e){
			try{
				requestHandler = new ActiveXObject("Microsoft.XMLHTTP");
			}
			catch(e1){
				hideLoadingBox();
					
				return false;
			}
		}
	}
	else{
		hideLoadingBox();
			
		return false;
	}	
		
	showLoadingBox();

	requestHandler.onreadystatechange = function(){
											processSubmitResponse(requestHandler, updateViews, target);
										}
		
	requestHandler.open(type, url, true);
	
	if(enctype)
		requestHandler.setRequestHeader("Content-type", enctype);
	else
		requestHandler.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	
	requestHandler.send(parameters);
	
	currentRequestHandler = requestHandler;
}

/**
 * Process the submit response.
 * 
 * @param requestHandler Instance that contains the request.
 * @param updateViews String that contains the views that will be updated.
 * @param target String that contains the target of the request.
 */
function processSubmitResponse(requestHandler, updateViews, target){
	if(requestHandler.readyState == 4){
		var node            = null;
		var responseText    = requestHandler.responseText;
		var parser          = new DOMParser();
		var newDocument     = parser.parseFromString(responseText, "text/html");

		if(updateViews && updateViews.length > 0){
			updateViews = updateViews.split(",");
			
			var documentObject     = null;
			var newDocumentObject  = null;
			var updateView         = null;
			
			for(cont = 0 ; cont < updateViews.length ; cont++){
				updateView = updateViews[cont];
				documentObject = getObject(updateView);
				newDocumentObject = newDocument.getElementById(updateView);
				
				if(documentObject){
					if(newDocumentObject)
						documentObject.outerHTML = newDocumentObject.outerHTML;
					else
						documentObject.outerHTML = responseText;
				}
			}
			
			setObjectValue("updateViews", "");
		}	
		else{
			var currentScripts  = document.head.getElementsByTagName("script");
			var newScripts      = newDocument.head.getElementsByTagName("script");
			var currentScript   = null;
			var newScript       = null;
			var scriptReference = null;
			var currentLinks    = document.head.getElementsByTagName("link");
			var newLinks        = newDocument.head.getElementsByTagName("link");
			var currentLink     = null;
			var newLink         = null;
			var linkReference   = null;
			var currentStyles   = document.head.getElementsByTagName("style");
			var newStyles       = newDocument.head.getElementsByTagName("style");
			var currentStyle    = null;
			var newStyle        = null;
			var styleReference  = null;
			var found           = null;
			
			for(cont1 = 0 ; cont1 < currentScripts.length ; cont1++){
				currentScript = currentScripts[cont1];
				found = false;
				
				if(currentScript != null){
					for(cont2 = 0 ; cont2 < newScripts.length ; cont2++){
						newScript = newScripts[cont2];
						
						if(newScript != null && 
						   newScript.src != null && 
						   newScript.src.trim().length > 0 && 
						   currentScript.src != null && 
						   currentScript.src.trim().length > 0 &&
						   newScript.src == currentScript.src){
							found = true;
							
							break;
						} 
						else if(newScript != null && 
								newScript.innerHTML != null && 
								newScript.innerHTML.trim().length > 0 && 
								currentScript.innerHTML != null && 
								currentScript.innerHTML.trim().length > 0 &&
								newScript.innerHTML == currentScript.innerHTML){
							found = true;
									
							break;
						} 
					}
				}
				
				if(!found)
					document.head.removeChild(currentScript);
			}
			
			for(cont1 = 0 ; cont1 < newScripts.length ; cont1++){
				newScript = newScripts[cont1];
				found = false;
				
				if(newScript != null){
					for(cont2 = 0 ; cont2 < currentScripts.length ; cont2++){
						currentScript = currentScripts[cont2];
						
						if(newScript != null && 
						   newScript.src != null && 
						   newScript.src.trim().length > 0 && 
						   currentScript.src != null && 
						   currentScript.src.trim().length > 0 &&
						   newScript.src == currentScript.src){
							found = true;
							
							break;
						} 
						else if(newScript != null && 
								newScript.innerHTML != null && 
								newScript.innerHTML.trim().length > 0 && 
								currentScript.innerHTML != null && 
								currentScript.innerHTML.trim().length > 0 &&
								newScript.innerHTML == currentScript.innerHTML){
							found = true;
									
							break;
						} 
					}
				}
				
				if(!found){
					scriptReference = document.createElement("script");
					scriptReference.setAttribute("type", "text/javascript");
					
					if(newScript.src != null && newScript.src.trim().length > 0)
						scriptReference.setAttribute("src", newScript.src);
					else
						scriptReference.innerHTML = newScript.innerHTML;
			        
			        document.head.appendChild(scriptReference);
				}
			}
			
			for(cont1 = 0 ; cont1 < currentLinks.length ; cont1++){
				currentLink = currentLinks[cont1];
				found = false;
				
				if(currentLink != null){
					for(cont2 = 0 ; cont2 < newLinks.length ; cont2++){
						newLink = newLinks[cont2];
						
						if(newLink != null && 
						   newLink.href != null && 
						   newLink.href.trim().length > 0 && 
						   currentLink.href != null && 
						   currentLink.href.trim().length > 0 &&
						   newLink.href == currentLink.href){
							found = true;
							
							break;
						} 
					}
				}
				
				if(!found)
					document.head.removeChild(currentLink);
			}
			
			for(cont1 = 0 ; cont1 < newLinks.length ; cont1++){
				newLink = newLinks[cont1];
				found = false;
				
				if(newLink != null){
					for(cont2 = 0 ; cont2 < currentLinks.length ; cont2++){
						currentLink = currentLinks[cont2];
						
						if(newLink != null && 
						   newLink.href != null && 
						   newLink.href.trim().length > 0 && 
						   currentLink.href != null && 
						   currentLink.href.trim().length > 0 &&
						   newLink.href == currentLink.href){
							found = true;
							
							break;
						} 
					}
				}
				
				if(!found){
					linkReference = document.createElement("link");
					linkReference.setAttribute("type", "text/css");
					linkReference.setAttribute("rel", "stylesheet");
					linkReference.setAttribute("href", newLink.href);
			        
			        document.head.appendChild(linkReference);
				}
			}
			
			for(cont1 = 0 ; cont1 < currentStyles.length ; cont1++){
				currentStyle = currentStyles[cont1];
				found = false;
				
				if(currentStyle != null){
					for(cont2 = 0 ; cont2 < newStyles.length ; cont2++){
						newStyle = newStyles[cont2];
						
						if(newStyle != null && 
						   newStyle.innerHTML != null && 
						   newStyle.innerHTML.trim().length > 0 && 
						   currentStyle.innerHTML != null && 
						   currentStyle.innerHTML.trim().length > 0 &&
						   newStyle.innerHTML == currentStyle.innerHTML){
							found = true;
									
							break;
						} 
					}
				}
				
				if(!found)
					document.head.removeChild(currentStyle);
			}
			
			for(cont1 = 0 ; cont1 < newStyles.length ; cont1++){
				newStyle = newStyles[cont1];
				found = false;
				
				if(newStyle != null){
					for(cont2 = 0 ; cont2 < currentStyles.length ; cont2++){
						currentStyle = currentStyles[cont2];
						
						if(newStyle != null && 
						   newStyle.innerHTML != null && 
						   newStyle.innerHTML.trim().length > 0 && 
						   currentStyle.innerHTML != null && 
						   currentStyle.innerHTML.trim().length > 0 &&
						   newStyle.innerHTML == currentStyle.innerHTML){
							found = true;
									
							break;
						} 
					}
				}
				
				if(!found){
					styleReference = document.createElement("style");
					styleReference.setAttribute("type", "text/css");
					styleReference.innerHTML = newStyle.innerHTML;
			        
			        document.head.appendChild(styleReference);
				}
			}
			
			document.body.innerHTML = newDocument.body.innerHTML;

			processSubmitResponseScripts();
		}
		
		clearAllTimers();
		centralizeDialogBoxes();
		hideLoadingBox();
	}
	
	currentRequestHandler = null;
}

/**
 * Process the submit response scripts.
 */
function processSubmitResponseScripts(){
	var childNodes = document.head.getElementsByTagName("script");
	var childNode  = null;
	var cont       = 0;
	
	if(childNodes){
		for(cont = 0 ; cont < childNodes.length ; cont++){
			childNode = childNodes[cont];
			
			if(childNode.innerHTML.trim().length > 0)
				processSubmitResponseScript(childNode.innerHTML);
		}
	}

	childNodes = document.body.getElementsByTagName("script");
	childNode  = null;
	cont       = 0;
	
	if(childNodes){
		for(cont = 0 ; cont < childNodes.length ; cont++){
			childNode = childNodes[cont];
			
			if(childNode.innerHTML.trim().length > 0)
				processSubmitResponseScript(childNode.innerHTML);
		}
	}
}

/**
 * Process a submit response script.
 * 
 * @param scriptCode Script to be processed.
 */
function processSubmitResponseScript(scriptCode){
	var object = new Function(scriptCode);
	
	setTimeout(object(), 0);
}

/**
 * DOM Parser for old browsers.
 */
(function(DOMParser){
    "use strict";

    var proto = DOMParser.prototype, nativeParse = proto.parseFromString;

    try{
        if((new DOMParser()).parseFromString("", "text/html"))
            return;
    } 
    catch(ex){
    }

    proto.parseFromString = function(markup, type){
        if(/^\s*text\/html\s*(?:;|$)/i.test(type)){
            var doc = document.implementation.createHTMLDocument("");
                
            if(markup.toLowerCase().indexOf('<!doctype') > -1)
            	doc.documentElement.innerHTML = markup;
            else
                doc.body.innerHTML = markup;

            return doc;
        } 
        else
            return nativeParse.apply(this, arguments);
    };
}(DOMParser));