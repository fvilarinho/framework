/**
 * Defines the UI routines to manipulate the clock component.
 *
 * @author fvilarinho
 * @version 3.0.0
 */
let clockTimer = null;

/**
 * Shows the component.
 */
function showClock(){
	if(clockTimer)
		clearTimeout(clockTimer);

	let clockObject  = getObject("clock");
	let clockPattern = getObject("clock.pattern");

	if(clockObject && clockPattern){
		let pattern      = clockPattern.value;
		let now          = new Date();
		let hours        = now.getHours();
		let minutes      = now.getMinutes();
		let seconds      = now.getSeconds();
		let milliseconds = now.getMilliseconds();
		let ampm         = (hours > 12 ? "PM" : "AM");
		
		pattern = replaceAll(pattern, "HH", (hours < 10 ? ("0" + hours) : hours));
		pattern = replaceAll(pattern, "hh", (hours > 12 ? ((hours - 12) < 10 ? ("0" + (hours - 12)) : (hours - 12)) : (hours < 10 ? ("0" + hours) : hours)));
		pattern = replaceAll(pattern, "h", (hours > 12 ? hours - 12 : hours));
		pattern = replaceAll(pattern, "H", hours);
		pattern = replaceAll(pattern, "mm", (minutes < 10 ? ("0" + minutes) : minutes));
		pattern = replaceAll(pattern, "ss", (seconds < 10 ? ("0" + seconds) : seconds));
		pattern = replaceAll(pattern, "SSS", milliseconds);
		pattern = replaceAll(pattern, "a", ampm);

		clockObject.innerHTML = pattern;

		clockTimer = setTimeout("showClock()", 1000);
	}
}