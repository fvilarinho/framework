/**
 * Defines the UI routines to manipulate the timer component. 
 * 
 * @author fvilarinho
 * @version 3.0.0
 */
let timers = [];

/**
 * Clears all timers of the page.
 */
function clearAllTimers(){
	if(timers !== null && timers.length){
		for(let cont = 0 ; cont < timers.length ; cont++){
			let timer = timers[cont];
			
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
