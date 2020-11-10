/**
 * Defines the UI routines to manipulate the accordion component.
 * 
 * @author fvilarinho
 * @version 3.0.0
 */
 
/**
 * Defines the current sections.
 * 
 * @param accordionName String that contains the identifier of the accordion component.
 * @param sectionName String that contains the identifier of the section component.
 * @param hasMultipleSelection Indicates if the component has multiple selection.
 * @param onSelect String that defines the select event.
 * @param onUnSelect String that defines the unselect event.
 */
function setCurrentSections(sectionName, accordionName, hasMultipleSelection, onSelect, onUnSelect){
	var currentSections = getObject(accordionName + ".currentSections");
	var sectionHeader   = null;
	var sectionContent  = null;
	
	if(currentSections){
		if(!hasMultipleSelection){
			if(currentSections.value != "" && currentSections.value != sectionName){
				sectionHeader  = getObject(accordionName + "." + currentSections.value + ".sectionHeader");
				sectionContent = getObject(accordionName + "." + currentSections.value + ".sectionContent");
				
				if(sectionHeader && sectionContent){
					var firstSection = sectionHeader.getAttribute("firstSection");
					var lastSection  = sectionHeader.getAttribute("lastSection");
					
					if(lastSection == "true")
						sectionHeader.className = "lastSectionHeader";
					
					if(firstSection == "true")
						sectionHeader.className = "firstSectionHeader";
					
					if(lastSection == "false" && firstSection == "false")
						sectionHeader.className = "sectionHeader";

					sectionContent.style.display = "NONE";
				}
			}
		}
		else{
			var currentSectionOptions = currentSections.options;
			
			if(currentSectionOptions){
				for(cont = 0 ; cont < currentSectionOptions.length ; cont++){
					currentSectionOption = currentSectionOptions[cont];
					
					if(currentSectionOption.value == sectionName){
						if(currentSectionOption.selected == true)
							currentSectionOption.selected = false;
						else
							currentSectionOption.selected = true;
					}
				}
			}
		}
	}
	
	sectionHeader  = getObject(accordionName + "." + sectionName + ".sectionHeader");
	sectionContent = getObject(accordionName + "." + sectionName + ".sectionContent");
	
	if(sectionContent){
		if(sectionContent.style.display.toUpperCase() == "NONE"){
			var firstSection = sectionHeader.getAttribute("firstSection");
			var lastSection  = sectionHeader.getAttribute("lastSection");
			
			if(lastSection == "true")
				sectionHeader.className = "sectionHeader";
			
			if(firstSection == "true")
				sectionHeader.className = "firstSectionHeader";
			
			if(lastSection == "false" && firstSection == "false")
				sectionHeader.className = "sectionHeader";

			sectionContent.style.display = "";

			if(!hasMultipleSelection)
				currentSections.value = sectionName;
			
			if(onSelect)
				onSelect();
		}
		else{
			var firstSection = sectionHeader.getAttribute("firstSection");
			var lastSection  = sectionHeader.getAttribute("lastSection");
			
			if(lastSection == "true")
				sectionHeader.className = "lastSectionHeader";
			
			if(firstSection == "true")
				sectionHeader.className = "firstSectionHeader";
			
			if(lastSection == "false" && firstSection == "false")
				sectionHeader.className = "sectionHeader";

			sectionContent.style.display = "NONE";
			
			if(!hasMultipleSelection)
				currentSections.value = "";

			if(onUnSelect)
				onUnSelect();
		}
	}
}