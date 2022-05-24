/**
 * Defines the UI routines to manipulate the grid component. 
 * 
 * @author fvilarinho
 * @version 1.0.0
 */
  
/**
 * Selects/Unselects all grid rows.
 * 
 * @param name String that contains the identifier of the grid component.
 * @param startIndex Numeric value that contains the start index.
 * @param endIndex Numeric value that contains the end index.
 */
function selectDeselectAllGridRows(name, startIndex, endIndex){
	let selectState = false;
	
	for(let cont = startIndex ; cont <= endIndex ; cont++){
		let object = getObject(name + "" + cont);
		
		if(object){
			if(!object.checked){
				selectState = true;
				
				break;
			}
		}
	}

	for(let cont = startIndex ; cont <= endIndex ; cont++){
		let object = getObject(name + "" + cont);
		
		if(object)
			if(object.checked !== selectState)
				object.checked = selectState;
	}
}