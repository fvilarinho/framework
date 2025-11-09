/**
 * Defines the UI routines to manipulate the calendar component. 
 * 
 * @author fvilarinho
 * @version 1.0.0
 */
let weekNames  = null;
let monthNames = null;
 
/**
 * Initializes the list of month names.
 */
function initializeCalendarMonthNames(){
	monthNames = initializeCalendarMonthNames.arguments;
}

/**
 * Initializes the list of week names.
 */
function initializeCalendarWeekNames(){
	weekNames = initializeCalendarWeekNames.arguments;
}

/**
 * Moves to next month.
 * 
 * @param name String that contains the identifier of the calendar component.
 */
function moveToNextMonth(name){
	let currentDate = parseCalendarInput(name);
	
	if(currentDate){
		currentDate.setMonth(currentDate.getMonth() + 1);
		
		updateCalendarInput(name, currentDate);
		renderCalendarDate(name, currentDate);
	}
}

/**
 * Moves to next year.
 * 
 * @param name String that contains the identifier of the calendar component.
 */
function moveToNextYear(name){
	let currentDate = parseCalendarInput(name);

	if(currentDate){
		if(currentDate.getFullYear() < 1900)
			currentDate.setYear((currentDate.getFullYear() + 1) + 1900);
		else
			currentDate.setYear((currentDate.getFullYear() + 1));

		updateCalendarInput(name, currentDate);
		renderCalendarDate(name, currentDate);
	}
}

/**
 * Moves to previous month.
 * 
 * @param name String that contains the identifier of the calendar component.
 */
function moveToPreviousMonth(name){
	let currentDate = parseCalendarInput(name);
	
	if(currentDate){
		currentDate.setMonth(currentDate.getMonth() - 1);
		
		updateCalendarInput(name, currentDate);
		renderCalendarDate(name, currentDate);
	}
}

/**
 * Moves to previous year.
 * 
 * @param name String that contains the identifier of the calendar component.
 */
function moveToPreviousYear(name){
	let currentDate = parseCalendarInput(name);
	
	if(currentDate){
		if(currentDate.getFullYear().toString().length <= 2)
			currentDate.setYear((currentDate.getFullYear() - 1) + 1900);
		else
			currentDate.setYear(currentDate.getFullYear() - 1);
 
		updateCalendarInput(name, currentDate);
		renderCalendarDate(name, currentDate);
	}
}

/**
 * Selects a specific day.
 * 
 * @param day Instance that contains the day.
 */
function selectCalendarDay(day){
	if(day.className !== "currentCalendarDay")
		day.className = "selectedCalendarDay";
}

/**
 * Unselects a specific day.
 * 
 * @param day Instance that contains the day.
 */
function unselectCalendarDay(day){
	if(day.className !== "currentCalendarDay")
		day.className = "calendarDay";
}

/**
 * Updates the current hours of the calendar component.
 * 
 * @param object Instance that contains the calendar component.
 */
function setCurrentCalendarHours(object){
	let name        = replaceAll(object.id, ".calendarHours", "");
	let currentDate = parseCalendarInput(name);
	
	if(currentDate){
		let hours = parseInt(object.value);
		
		if(isNaN(hours)){
			hours = 0;
			
			object.value = hours;
		}
		
		currentDate.setHours(hours);
		
		updateCalendarInput(name, currentDate);
	}
}

/**
 * Updates the current minutes of the calendar component.
 * 
 * @param object Instance that contains the calendar component.
 */
function setCurrentCalendarMinutes(object){
	let name        = replaceAll(object.id, ".calendarMinutes", "");
	let currentDate = parseCalendarInput(name);
	
	if(currentDate){
		let minutes = parseInt(object.value);
		
		if(isNaN(minutes)){
			minutes = 0;
			
			object.value = minutes;
		}

		currentDate.setMinutes(minutes);
		
		updateCalendarInput(name, currentDate);
	}
}

/**
 * Defines the current seconds of the calendar component.
 * 
 * @param object Instance that contains the calendar component.
 */
function setCurrentCalendarSeconds(object){
	let name        = replaceAll(object.id, ".calendarSeconds", "");
	let currentDate = parseCalendarInput(name);
	
	if(currentDate){
		let seconds = parseInt(object.value);
		
		if(isNaN(seconds)){
			seconds = 0;
			
			object.value = seconds;
		}

		currentDate.setSeconds(seconds);
		
		updateCalendarInput(name, currentDate);
	}
}

/**
 * Defines the current milliseconds of the calendar component.
 * 
 * @param object Instance that contains the calendar component.
 */
function setCurrentCalendarMilliseconds(object){
	let name        = replaceAll(object.id, ".calendarMilliseconds", "");
	let currentDate = parseCalendarInput(name);
	
	if(currentDate){
		let milliseconds = parseInt(object.value);
		
		if(isNaN(milliseconds))
			milliseconds = 0;

		currentDate.setMilliseconds(milliseconds);
		
		updateCalendarInput(name, currentDate);
	}
}	

/**
 * Defines the AM/PM settings of the calendar component.
 *
 * @param object Instance that contains the calendar component.
 */
function setCurrentCalendarAm(object){
	let name = replaceAll(object.id, ".calendarAmPm", "");
	
	object = getObject(name);
	
	if(object)
		object.value = replaceAll(object.value, "PM", "AM");
}

/**
 * Defines the AM/PM settings of the calendar component.
 *
 * @param object Instance that contains the calendar component.
 */
function setCurrentCalendarPm(object){
	let name = replaceAll(object.id, ".calendarAmPm", "");
	
	object = getObject(name);
	
	if(object)
		object.value = replaceAll(object.value, "AM", "PM");
}

/**
 * Selects the current day of the calendar component. 
 * 
 * @param name String that contains the identifier of the calendar component.
 * @param day Instance of the day.
 */
function setCurrentCalendarDay(name, day){
	let currentDate = parseCalendarInput(name);
	
	if(currentDate){
		let dayValue  = day.innerHTML;
		let dayObject = getObject(name + ".day" + currentDate.getDate());
	
		if(dayObject && currentDate){
			dayObject.className = "calendarDay";
	
			currentDate.setDate(dayValue);
	
			dayObject = getObject(name + ".day" + dayValue);
			
			if(dayObject){
				dayObject.className = "currentCalendarDay";
	
				updateCalendarInput(name, currentDate);
			}
		}
	}
	
	showHideCalendar(name);
}

/**
 * Updates the input based on the selected day.
 * 
 * @param name String that contains the identifier of the calendar component.
 * @param currentDate Instance that contains the date/time. 
 */
function updateCalendarInput(name, currentDate){
	let calendar = getObject(name);
	
	if(calendar){
		let objectValue  = getObjectValue(name);
	    let pattern      = getObjectValue(name + ".pattern");
		let date         = "" + currentDate.getDate();
		let month        = "" + (currentDate.getMonth() + 1);
		let year         = "" + currentDate.getFullYear();
		let hours        = "" + (pattern.indexOf("hh") >= 0 ? (currentDate.getHours() > 12 ? currentDate.getHours() - 12 : currentDate.getHours()) : currentDate.getHours());
		let minutes      = "" + currentDate.getMinutes();
		let seconds      = "" + currentDate.getSeconds();
		let milliseconds = "" + currentDate.getMilliseconds();
		
		if(date.length < 2)
			date = replicate("0", 2 - date.length) + date;
		
		if(month.length < 2)
			month = replicate("0", 2 - month.length) + month;

		if(year.length < 4)
			year = replicate("0", 4 - year.length) + year;

		if(hours.length < 2)
			hours = replicate("0", 2 - hours.length) + hours;

		if(minutes.length < 2)
			minutes = replicate("0", 2 - minutes.length) + minutes;

		if(seconds.length < 2)
			seconds = replicate("0", 2 - seconds.length) + seconds;
		
		if(milliseconds.length < 3)
			milliseconds = replicate("0", 3 - milliseconds.length) + milliseconds;
		
		pattern = replaceAll(pattern, "dd", date);
		pattern = replaceAll(pattern, "MM", month);
		pattern = replaceAll(pattern, "yyyy", year);
		
		if(objectValue.indexOf("PM") >= 0)
			pattern = replaceAll(pattern, "a", "PM");
		else
			pattern = replaceAll(pattern, "a", "AM");
		
		pattern = replaceAll(pattern, "HH", hours);
		pattern = replaceAll(pattern, "hh", hours);
		pattern = replaceAll(pattern, "mm", minutes);
		pattern = replaceAll(pattern, "ss", seconds);
		pattern = replaceAll(pattern, "SSS", milliseconds);
		
		calendar.value = pattern;
	}
}

/**
 * Renders the calendar component.
 * 
 * @param name String that contains the identifier of the calendar component.
 */
function renderCalendar(name){
	let currentDate = parseCalendarInput(name);
	
	renderCalendarDate(name, currentDate);
	renderCalendarTime(name, currentDate);
}

/**
 * Renders the date of the calendar component.
 * 
 * @param name String that contains the identifier of the calendar component.
 * @param currentDate Instance that contains the date/time. 
 */
function renderCalendarDate(name, currentDate){
	let calendar      = getObject(name + ".calendar");
	let calendarDays  = getObject(name + ".calendarDays");
	let calendarMonth = getObject(name + ".calendarMonth");
	
	if(calendar && calendarDays && calendarMonth && currentDate){
		let firstDate = new Date(currentDate.getFullYear(), currentDate.getMonth(), 1);
		let weekDay   = firstDate.getDay();
		let rows      = 40;
		let html      = "";
		let date      = null;

		html += monthNames[currentDate.getMonth()].substring(0, 3);
		html += ", ";
		html += currentDate.getFullYear();
		
		calendarMonth.innerHTML = html;
		
		html  = "";
		html += "<table>";
		html += "<tr>";

		let cont;

		for(cont = 0 ; cont < 7 ; cont++){
			html += "<td class=\"calendarWeek\">";
			html += weekNames[cont].substring(0, 1).toUpperCase();
			html += "</td>";
		}

		html += "</tr><tr>";	
		
		for(cont = 0 ; cont < weekDay ; cont++)
			html += "<td></td>";
		
		rows -= cont;
		cont  = 1;
		
		while(cont <= rows){
			date = new Date(currentDate.getFullYear(), currentDate.getMonth(), cont);

			if(date.getMonth() === currentDate.getMonth()){
				html += "<td class=\"";
				
				if(cont === currentDate.getDate())
					html += "currentCalendarDay";
				else
					html += "calendarDay";
					
				html += "\"";
				html += " id=\"";
				html += name;
				html += ".day";
				html += cont;
				html += "\" onClick=\"setCurrentCalendarDay('";
				html += name;
				html += "', this)\" onMouseOut=\"unselectCalendarDay(this);\" onMouseOver=\"selectCalendarDay(this);\">";
				html += date.getDate();
				html += "</td>";

				if(((cont + weekDay) % 7) === 0)
					html += "</tr><tr>";
			}
			else
				break;

			cont++;
		}
		
		if(rows > 35){
			html += "</tr>";
			html += "<tr>";
			html += "<td>&nbsp;</td>";
		}

		html += "</tr>";
		html += "</table>";
		
		calendarDays.innerHTML = html;
	}
}

/**
 * Renders the time of the calendar component.
 * 
 * @param name String that contains the identifier of the calendar component.
 * @param currentDate Instance that contains the date/time. 
 */
function renderCalendarTime(name, currentDate){
	let calendarHours = getObject(name + ".calendarHours");

	if(calendarHours){
		let pattern = getObjectValue(name + ".pattern");
		
		calendarHours.value = (pattern.indexOf("hh") >= 0 ? (currentDate.getHours() > 12 ? currentDate.getHours() - 12 : currentDate.getHours()) : currentDate.getHours());

		setSliderBarPosition(name + ".calendarHours");
	}

	let calendarMinutes = getObject(name + ".calendarMinutes");

	if(calendarMinutes){
		calendarMinutes.value = currentDate.getMinutes();

		setSliderBarPosition(name + ".calendarMinutes");
	}

	let calendarSeconds = getObject(name + ".calendarSeconds");

	if(calendarSeconds){
		calendarSeconds.value = currentDate.getSeconds();

		setSliderBarPosition(name + ".calendarSeconds");
	}
	
	let calendarMilliseconds = getObject(name + ".calendarMilliseconds");

	if(calendarMilliseconds){
		calendarMilliseconds.value = currentDate.getMilliseconds();

		setSliderBarPosition(name + ".calendarMilliseconds");
	}
}

/**
 * Show/Hide the calendar component.
 * 
 * @param name String that contains the identifier of the calendar component.
 */
function showHideCalendar(name){
	let divs   = document.getElementsByTagName("div");
	let suffix = ".calendar";
	
	for(let i = 0 ; i < divs.length ; i++){
		let div = divs[i];
		let id  = div.id;
		
		if(id && id.indexOf(suffix, id.length - suffix.length) >= 0 && id !== (name + suffix))
			div.style.visibility = "HIDDEN";
	}

	let calendar = getObject(name + suffix);
	
	if(calendar){
		if(calendar.style.visibility.toUpperCase() === "HIDDEN" || calendar.style.visibility === ""){
			renderCalendar(name);
	
			calendar.style.visibility = "VISIBLE";
		}
		else
			calendar.style.visibility = "HIDDEN";
	}
}

/**
 * Parses the input of the calendar component.
 *
 * @param name String that contains the identifier of the calendar component.
 * @returns Instance that contains the date/time.
 */
function parseCalendarInput(name){
	let value        = getObjectValue(name);
	let pattern      = getObjectValue(name + ".pattern");
	let pos          = pattern.indexOf("dd");
	let day          = null; 
	let month        = null;
	let year         = null;  
	let hours        = null;
	let minutes      = null;
	let seconds      = null;
	let milliseconds = null;
	
	if(pos >= 0)
		day = parseInt(value.substring(pos, pos + 2));
	
	pos = pattern.indexOf("MM")
	if(pos >= 0)
		month = parseInt(value.substring(pos, pos + 2)) - 1;
	
	pos = pattern.indexOf("yyyy")
	if(pos >= 0)
		year = parseInt(value.substring(pos, pos + 4));
	
	pos = pattern.indexOf("HH")
	if(pos >= 0)
		hours = parseInt(value.substring(pos, pos + 2));
	else{
		pos = pattern.indexOf("hh")
		if(pos >= 0)
			hours = parseInt(value.substring(pos, pos + 2));
	}
	
	pos = pattern.indexOf("mm")
	if(pos >= 0)
		minutes = parseInt(value.substring(pos, pos + 2));
	
	pos = pattern.indexOf("ss")
	if(pos >= 0)
		seconds = parseInt(value.substring(pos, pos + 2));

	pos = pattern.indexOf("SSS")
	if(pos >= 0)
		milliseconds = parseInt(value.substring(pos, pos + 3));

	let currentDate = new Date();
	
	if(day !== null && !isNaN(day))
		currentDate.setDate(day);
	
	if(month !== null && !isNaN(month))
		currentDate.setMonth(month);
	
	if(year !== null && !isNaN(year))
		currentDate.setFullYear(year);
	
	if(hours !== null && !isNaN(hours))
		currentDate.setHours(hours);

	if(minutes !== null && !isNaN(minutes))
		currentDate.setMinutes(minutes);
	
	if(seconds !== null && !isNaN(seconds))
		currentDate.setSeconds(seconds);
	
	if(milliseconds !== null && !isNaN(milliseconds))
		currentDate.setMilliseconds(milliseconds);
	
	return currentDate;
}