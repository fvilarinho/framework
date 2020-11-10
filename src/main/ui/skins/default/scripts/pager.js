/**
 * Defines the UI routines to manipulate the pager component.
 * 
 * @author fvilarinho
 * @version 1.0.0
 */

/**
 * Executes a pager action.
 * 
 * @param name String that contains the identifier of the pager component.
 * @param action String that contains the identifier of the action.
 * @param updateViews String that contains the identifier of the view that will be updated.
 * @param actionFormName String that contains the identifier of the action form.
 */
function executePagerAction(name, action, updateViews, actionFormName){
	if(!actionFormName)
		return;

	setObjectValue(actionFormName + "." + name + ".pagerAction", action);
	setObjectValue("updateViews", updateViews);

	document.forms[actionFormName].action.value = "refresh";

	submitActionForm(document.forms[actionFormName]);
}

/**
 * Refresh the current page.
 * 
 * @param name String that contains the identifier of the pager component.
 * @param updateViews String that contains the identifier of the view that will be updated.
 * @param actionFormName String that contains the identifier of the action form.
 */
function refreshPage(name, updateViews, actionFormName){
	executePagerAction(name, "REFRESH_PAGE", updateViews, actionFormName);
}

/**
 * Moves to the first page.
 * 
 * @param name String that contains the identifier of the pager component.
 * @param updateViews String that contains the identifier of the view that will be updated.
 * @param actionFormName String that contains the identifier of the action form.
 */
function moveToFirstPage(name, updateViews, actionFormName){
	executePagerAction(name, "FIRST_PAGE", updateViews, actionFormName);
}

/**
 * Moves to the previous page.
 * 
 * @param name String that contains the identifier of the pager component.
 * @param updateViews String that contains the identifier of the view that will be updated.
 * @param actionFormName String that contains the identifier of the action form.
 */
function moveToPreviousPage(name, updateViews, actionFormName){
	executePagerAction(name, "PREVIOUS_PAGE", updateViews, actionFormName);
}

/**
 * Moves to the next page.
 * 
 * @param name String that contains the identifier of the pager component.
 * @param updateViews String that contains the identifier of the view that will be updated.
 * @param actionFormName String that contains the identifier of the action form.
 */
function moveToNextPage(name, updateViews, actionFormName, value){
	executePagerAction(name, "NEXT_PAGE", updateViews, actionFormName);
}

/**
 * Moves to the last page.
 * 
 * @param name String that contains the identifier of the pager component.
 * @param updateViews String that contains the identifier of the view that will be updated.
 * @param actionFormName String that contains the identifier of the action form.
 */
function moveToLastPage(name, updateViews, actionFormName){
	executePagerAction(name, "LAST_PAGE", updateViews, actionFormName);
}