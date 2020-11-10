/**
 * Defines the UI routines to manipulate the slider bar component. 
 * 
 * @author fvilarinho
 * @version 3.0.0
 */
var pagePosition         = null;
var currentSliderBarName = null;
var slidersBar           = new Object();

/**
 * Class that defines the slider bar component.
 */
function SliderBar(){
	var name;
	var width;
	var controlCurrentPosition;
	var controlWidth;
	var maximumValue;
	var useGroupSeparator;
	var groupSeparator;
	var precision;
	var decimalSeparator;
	var value;
	
	this.getUseGroupSeparator = function(){
		return useGroupSeparator;
	}
	
	this.setUseGroupSeparator = function(u){
		useGroupSeparator = u;
	}

	this.getGroupSeparator = function(){
		return groupSeparator;
	}
	
	this.setGroupSeparator = function(g){
		groupSeparator = g;
	}

	this.getPrecision = function(){
		return precision;
	}
	
	this.setPrecision = function(p){
		precision = p;
	}

	this.getDecimalSeparator = function(){
		return decimalSeparator;
	}
	
	this.setDecimalSeparator = function(d){
		decimalSeparator = d;
	}
	
	this.getName = function(){
		return name;
	}
	
	this.setName = function(n){
		name = n;
	}
	
	this.getWidth = function(){
		return width;
	}
	
	this.setWidth = function(w){
		width = w;
	}
	
	this.getControlCurrentPosition = function(){
		return controlCurrentPosition;
	}
	
	this.setControlCurrentPosition = function(cp){
		controlCurrentPosition = cp;
	}
	
	this.getControlWidth = function(){
		return controlWidth;
	}
	
	this.setControlWidth = function(cw){
		controlWidth = cw;
	}
	
	this.getMaximumValue = function(){
		return maximumValue;
	}
	
	this.setMaximumValue = function(mv){
		maximumValue = mv;
	}
	
	this.getValue = function(){
		return value;
	}
	
	this.setValue = function(v){
		value = v;
	}
}

/**
 * Initializes the component.
 * 
 * @param name String that contains the identifier of the slider bar component.
 * @param width Numeric value that contains the width.
 * @param maximumValue Numeric value that contains the maximum value permitted.
 * @param useGroupSeparator Indicates the the mask should use the group separator.
 * @param groupSeparator String that contains the group separator.
 * @param precision Numeric value that contains the decimal precision.
 * @param decimalSeparator String that contains the decimal separator.
 */
function initializeSliderBar(name, width, maximumValue, useGroupSeparator, groupSeparator, precision, decimalSeparator){
	var sliderBarObject        = getObject(name + ".sliderBar");
	var sliderBarControlObject = getObject(name + ".sliderBarControl");
	
	if(sliderBarObject && sliderBarControlObject){
		var sliderBar = slidersBar[name];
		
		if(!sliderBar)
			sliderBar = new SliderBar();
		
		sliderBar.setName(name);
		
		if(width != null)
			sliderBar.setWidth(width);
		else
			sliderBar.setWidth(sliderBarObject.offsetWidth);
		
		sliderBar.setControlWidth(17);
		sliderBar.setMaximumValue(maximumValue);
		sliderBar.setUseGroupSeparator(useGroupSeparator);
		sliderBar.setGroupSeparator(groupSeparator);
		sliderBar.setPrecision(precision);
		sliderBar.setDecimalSeparator(decimalSeparator);
		slidersBar[name] = sliderBar;
		
		setSliderBarPosition(name);
	}
}

/**
 * Defines the position of the slider bar.
 * 
 * @param name String that contains the identifier of the slider bar component.
 */
function setSliderBarPosition(name){
	var sliderBarObject        = getObject(name + ".sliderBar");
	var sliderBarControlObject = getObject(name + ".sliderBarControl");
	
	if(sliderBarObject && sliderBarControlObject){
		var sliderBar = slidersBar[name];
		var value     = getObjectValue(name);
		
		if(value < 0)
			value = 0;
		
		if(value > sliderBar.getMaximumValue())
			value = sliderBar.getMaximumValue();
		
		setObjectValue(name, value);
		
		sliderBar.setValue(value);
		sliderBar.setControlCurrentPosition(Math.round((sliderBar.getWidth() * sliderBar.getValue()) / sliderBar.getMaximumValue()));
		slidersBar[name] = sliderBar;
		
		sliderBarObject.style.width       = sliderBar.getWidth() + sliderBar.getControlWidth() + "px";
		sliderBarControlObject.style.left = sliderBar.getControlCurrentPosition() + "px";
	}
}
 
/**
 * Drags the slide bar.
 * 
 * @param name String that contains the identifier of the slider bar component.
 * @param event Instance that contains the event.
 */
function dragSliderBarControl(name, event){
	if(event){
		currentSliderBarName = name;
		pagePosition         = event.pageX;
	}
}

/**
 * Drops the slider bar.
 */
function dropSliderBarControl(){
	currentSliderBarName = null;
} 

/**
 * Slides the bar.
 * 
 * @param event Instance that contains the event.
 */
function slideIt(event){
	if(currentSliderBarName != null && pagePosition != null && event){
		var sliderBar              = slidersBar[currentSliderBarName];
		var currentPagePosition    = event.pageX;
		var currentPosition        = (sliderBar.getControlCurrentPosition() + event.pageX - pagePosition);
		var sliderBarObject        = getObject(currentSliderBarName);
		var sliderBarControlObject = getObject(currentSliderBarName + ".sliderBarControl");
		
		if(sliderBarObject && sliderBarControlObject){
			pagePosition = currentPagePosition;
		
			if(currentPosition < 0)
				currentPosition = 0;
		
			if(currentPosition > sliderBar.getWidth())
				currentPosition = sliderBar.getWidth();
		
			sliderBarControlObject.style.left = currentPosition; 
			sliderBarObject.value = sliderBar.getValue();
			
			sliderBar.setControlCurrentPosition(currentPosition);
			sliderBar.setValue(Math.round((sliderBar.getMaximumValue() * currentPosition) / sliderBar.getWidth()));
			slidersBar[currentSliderBarName] = sliderBar;
		
			applyNumericMask(sliderBarObject, 0, sliderBar.getMaximumValue(), sliderBar.getUseGroupSeparator(), sliderBar.getGroupSeparator(), sliderBar.getPrecision(), sliderBar.getDecimalSeparator());
		}
	}
}