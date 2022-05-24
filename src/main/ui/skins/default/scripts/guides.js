/**
 * Defines the UI routines to manipulate the guides component. 
 * 
 * @author fvilarinho
 * @version 1.0.0
 */
  
/**
 * Defines the current guide.
 * 
 * @param guideName String that contains the identifier of the guide
 * @param guidesName String that contains the identifier of the guides component.
 * @param guideOnSelect String that contains the select event.
 */
function setCurrentGuide(guideName, guidesName, guideOnSelect){
	let currentGuideName = getObjectValue(guidesName + ".currentGuide");
	let currentGuide = getObject(guidesName + "." + currentGuideName + ".guide");
	let currentGuideContent = getObject(guidesName + "." + currentGuideName + ".guideContent");
	let guide = getObject(guidesName + "." + guideName + ".guide");
	let guideContent = getObject(guidesName + "." + guideName + ".guideContent");

	if(guide && guideContent){
		if(currentGuide && currentGuideContent){
			currentGuide.className = "guide";
	
			currentGuideContent.style.display = "NONE";
		}
		
		guide.className = "currentGuide";

		guideContent.style.display = "";
	}

	setObjectValue(guidesName + ".currentGuide", guideName);
	
	if(guideOnSelect && guideOnSelect.trim().length > 0)
		eval(guideOnSelect);
}