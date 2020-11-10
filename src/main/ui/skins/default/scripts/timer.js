/**
 * Defines the UI routines to manipulate the timer component. 
 * 
 * @author fvilarinho
 * @version 3.0.0
 */

var timers = new Array();

/**
 * Clears all timers of the page.
 */
function clearAllTimers(){
	if(timers != null && timers.length){
		for(var cont = 0 ; cont < timers.length ; cont++){
			var timer = timers[cont];
			
			clearInterval(timer);
		}
	}
}

/**
 * Adds a new timer.
 * 
 * @param timer Instance of the timer.
 */
function addTimer(timer){
	timers.push(timer);
}
