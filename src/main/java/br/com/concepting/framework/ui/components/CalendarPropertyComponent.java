package br.com.concepting.framework.ui.components;

import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.ui.constants.UIConstants;
import br.com.concepting.framework.util.helpers.DateTime;
import br.com.concepting.framework.util.helpers.PropertyInfo;
import br.com.concepting.framework.util.types.AlignmentType;
import br.com.concepting.framework.util.types.ComponentType;
import br.com.concepting.framework.util.types.PositionType;

import javax.servlet.jsp.JspException;
import java.util.Calendar;

/**
 * Class that defines the calendar component.
 *
 * @author fvilarinho
 * @since 2.0.0
 *
 * <pre>Copyright (C) 2007 Innovative Thinking.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses.</pre>
 */
public class CalendarPropertyComponent extends TextPropertyComponent{
    private static final long serialVersionUID = -2047638037702135816L;
    
    private Boolean showButton = null;
    
    /**
     * Indicates if the calendar button should be shown.
     *
     * @return True/False.
     */
    protected Boolean showButton(){
        return this.showButton;
    }
    
    /**
     * Indicates if the calendar button should be shown.
     *
     * @return True/False.
     */
    protected Boolean getShowButton(){
        return showButton();
    }
    
    /**
     * Defines if the calendar button should be shown.
     *
     * @param showButton True/False.
     */
    public void setShowButton(Boolean showButton){
        this.showButton = showButton;
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#buildAlignment()
     */
    protected void buildAlignment() throws InternalErrorException{
        if(getAlignmentType() == null)
            setAlignmentType(AlignmentType.CENTER);
        
        super.buildAlignment();
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BasePropertyComponent#buildRestrictions()
     */
    protected void buildRestrictions() throws InternalErrorException{
        if(this.showButton == null)
            this.showButton = true;
        
        setIsDate(true);
        
        super.buildRestrictions();
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BasePropertyComponent#buildEvents()
     */
    protected void buildEvents() throws InternalErrorException{
        String name = getName();
        
        if(name == null || name.length() == 0)
            return;
        
        String currentOnBlur = getOnBlur();
        StringBuilder onBlur = new StringBuilder();
        
        onBlur.append("renderCalendar('");
        onBlur.append(name);
        onBlur.append("');");
        
        if(currentOnBlur != null && currentOnBlur.length() > 0){
            onBlur.append(" ");
            onBlur.append(currentOnBlur);
            
            if(!currentOnBlur.endsWith(";"))
                onBlur.append(";");
        }
        
        setOnBlur(onBlur.toString());
        
        super.buildEvents();
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.TextPropertyComponent#initialize()
     */
    protected void initialize() throws InternalErrorException{
        setComponentType(ComponentType.CALENDAR);
        
        super.initialize();
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BasePropertyComponent#renderOpen()
     */
    protected void renderOpen() throws InternalErrorException{
        super.renderOpen();
        
        Boolean enabled = isEnabled();
        Boolean readOnly = isReadOnly();
        Boolean hasInvalidPropertyDefinition = hasInvalidPropertyDefinition();
        
        if(enabled != null && enabled && (readOnly == null || !readOnly) && (hasInvalidPropertyDefinition == null || !hasInvalidPropertyDefinition)){
            PositionType labelPositionType = getLabelPositionType();
            
            if(labelPositionType == PositionType.TOP || labelPositionType == PositionType.BOTTOM){
                print("<table class=\"");
                print(UIConstants.DEFAULT_CONTENT_PANEL_STYLE_CLASS);
                println("\">");
                println("<tr>");
                println("<td>");
            }
            
            print("<table class=\"");
            print(UIConstants.DEFAULT_CONTENT_PANEL_STYLE_CLASS);
            println("\">");
            println("<tr>");
            println("<td>");
        }
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BasePropertyComponent#renderClose()
     */
    protected void renderClose() throws InternalErrorException{
        Boolean enabled = isEnabled();
        Boolean readOnly = isReadOnly();
        Boolean hasInvalidPropertyDefinition = hasInvalidPropertyDefinition();
        
        if(enabled != null && enabled && (readOnly == null || !readOnly) && (hasInvalidPropertyDefinition == null || !hasInvalidPropertyDefinition)){
            println("</td>");
            
            renderControls();
        }
        
        super.renderClose();
    }
    
    /**
     * Renders the component controls.
     *
     * @throws InternalErrorException Occurs when was not possible to render.
     */
    protected void renderControls() throws InternalErrorException{
        PropertyInfo propertyInfo = getPropertyInfo();
        String name = getName();
        Boolean enabled = isEnabled();
        Boolean readOnly = isReadOnly();
        
        if(propertyInfo != null && name != null && name.length() > 0 && enabled != null && enabled && (readOnly == null || !readOnly)){
            if(this.showButton != null && this.showButton){
                println("<td width=\"5\"></td>");
                
                println("<td>");
                
                ShowCalendarButtonComponent showCalendarButtonComponent = new ShowCalendarButtonComponent(this);
                
                try{
                    showCalendarButtonComponent.doStartTag();
                    showCalendarButtonComponent.doEndTag();
                }
                catch(JspException e){
                    throw new InternalErrorException(e);
                }
                
                println("</td>");
            }
            
            println("</tr>");
            println("</table>");
            
            PositionType labelPositionType = getLabelPositionType();
            
            if(labelPositionType == PositionType.TOP || labelPositionType == PositionType.BOTTOM){
                println("</td>");
                println("</tr>");
                println("</table>");
            }
            
            print("<div id=\"");
            print(name);
            print(".");
            print(UIConstants.DEFAULT_CALENDAR_ID);
            print("\" class=\"");
            print(UIConstants.DEFAULT_CALENDAR_STYLE_CLASS);
            print("\"");
            
            String style = getStyle();
            
            if(style != null && style.length() > 0){
                print(" style=\"");
                print(style);
                
                if(!style.endsWith(";"))
                    print(";");
                
                print("\"");
            }
            
            println(">");
            
            String pattern = getPattern();
            
            if(pattern != null && (pattern.contains("d") || pattern.contains("M") || pattern.contains("y"))){
                print("<table class=\"");
                print(UIConstants.DEFAULT_CALENDAR_CONTENT_STYLE_CLASS);
                println("\">");
                println("<tr>");
                println("<td width=\"1\">");
                
                PreviousYearButtonComponent previousYearButtonComponent = new PreviousYearButtonComponent(this);
                
                try{
                    previousYearButtonComponent.doStartTag();
                    previousYearButtonComponent.doEndTag();
                }
                catch(JspException e){
                    throw new InternalErrorException(e);
                }
                
                println("</td>");
                
                println("<td width=\"1\">");
                
                PreviousMonthButtonComponent previousMonthButtonComponent = new PreviousMonthButtonComponent(this);
                
                try{
                    previousMonthButtonComponent.doStartTag();
                    previousMonthButtonComponent.doEndTag();
                }
                catch(JspException e){
                    throw new InternalErrorException(e);
                }
                
                println("</td>");
                
                print("<td align=\"");
                print(AlignmentType.CENTER);
                println("\">");
                print("<span id=\"");
                print(name);
                print(".");
                print(UIConstants.DEFAULT_CALENDAR_MONTH_ID);
                print("\" class=\"");
                print(UIConstants.DEFAULT_CALENDAR_MONTH_STYLE_CLASS);
                print("\"></span>");
                println("</td>");
                
                println("<td width=\"1\">");
                
                NextMonthButtonComponent nextMonthButtonComponent = new NextMonthButtonComponent(this);
                
                try{
                    nextMonthButtonComponent.doStartTag();
                    nextMonthButtonComponent.doEndTag();
                }
                catch(JspException e){
                    throw new InternalErrorException(e);
                }
                
                println("</td>");
                
                println("<td width=\"1\">");
                
                NextYearButtonComponent nextYearButtonComponent = new NextYearButtonComponent(this);
                
                try{
                    nextYearButtonComponent.doStartTag();
                    nextYearButtonComponent.doEndTag();
                }
                catch(JspException e){
                    throw new InternalErrorException(e);
                }
                
                println("</td>");
                println("</tr>");
                println("</table>");
                
                print("<span id=\"");
                print(name);
                print(".");
                print(UIConstants.DEFAULT_CALENDAR_DAYS_ID);
                print("\"></span>");
                println("<br/>");
            }
            
            if(pattern != null && (pattern.contains("hh") || pattern.contains("HH"))){
                HoursPropertyComponent hoursPropertyComponent = new HoursPropertyComponent(this);
                
                try{
                    hoursPropertyComponent.doStartTag();
                    hoursPropertyComponent.doEndTag();
                }
                catch(JspException e){
                    throw new InternalErrorException(e);
                }
            }
            
            if(pattern != null && pattern.contains("mm")){
                MinutesPropertyComponent minutesPropertyComponent = new MinutesPropertyComponent(this);
                
                try{
                    minutesPropertyComponent.doStartTag();
                    minutesPropertyComponent.doEndTag();
                }
                catch(JspException e){
                    throw new InternalErrorException(e);
                }
            }
            
            if(pattern != null && pattern.contains("ss")){
                SecondsPropertyComponent secondsPropertyComponent = new SecondsPropertyComponent(this);
                
                try{
                    secondsPropertyComponent.doStartTag();
                    secondsPropertyComponent.doEndTag();
                }
                catch(JspException e){
                    throw new InternalErrorException(e);
                }
            }
            
            if(pattern != null && pattern.contains("SSS")){
                MillisecondsPropertyComponent millisecondsPropertyComponent = new MillisecondsPropertyComponent(this);
                
                try{
                    millisecondsPropertyComponent.doStartTag();
                    millisecondsPropertyComponent.doEndTag();
                }
                catch(JspException e){
                    throw new InternalErrorException(e);
                }
            }
            
            if(pattern != null && pattern.contains("a")){
                println("<center>");
                println("<table>");
                println("<tr>");
                println("<td>");
                
                AmPropertyComponent amPropertyComponent = new AmPropertyComponent(this);
                
                try{
                    amPropertyComponent.doStartTag();
                    amPropertyComponent.doEndTag();
                }
                catch(JspException e){
                    throw new InternalErrorException(e);
                }
                
                println("</td>");
                println("<td>");
                
                PmPropertyComponent pmPropertyComponent = new PmPropertyComponent(this);
                
                try{
                    pmPropertyComponent.doStartTag();
                    pmPropertyComponent.doEndTag();
                }
                catch(JspException e){
                    throw new InternalErrorException(e);
                }
                
                println("</td>");
                println("</tr>");
                println("</table>");
                println("</center>");
            }
            
            println("<br/>");
            println("</div>");
        }
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BasePropertyComponent#clearAttributes()
     */
    protected void clearAttributes() throws InternalErrorException{
        super.clearAttributes();
        
        setShowButton(null);
    }
    
    /**
     * Class that defines the show calendar button component.
     *
     * @author fvilarinho
     * @since 2.0.0
     */
    private class ShowCalendarButtonComponent extends ButtonComponent{
        private static final long serialVersionUID = -7577282331598735497L;
        
        /**
         * Constructor - Defines the component.
         *
         * @param calendarPropertyComponent Instance that contains the calendar component.
         */
        public ShowCalendarButtonComponent(CalendarPropertyComponent calendarPropertyComponent){
            super();
            
            if(calendarPropertyComponent != null){
                setPageContext(calendarPropertyComponent.getPageContext());
                setOutputStream(calendarPropertyComponent.getOutputStream());
                setActionFormName(calendarPropertyComponent.getActionFormName());
                setParent(calendarPropertyComponent);
            }
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.ButtonComponent#buildEvents()
         */
        protected void buildEvents() throws InternalErrorException{
            CalendarPropertyComponent calendarPropertyComponent = (CalendarPropertyComponent) getParent();
            String name = (calendarPropertyComponent != null ? calendarPropertyComponent.getName() : null);
            
            if(name != null && name.length() > 0){
                StringBuilder onClick = new StringBuilder();
                
                onClick.append("showHideCalendar('");
                onClick.append(name);
                onClick.append("');");
                
                setOnClick(onClick.toString());
                
                super.buildEvents();
            }
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#buildResources()
         */
        protected void buildResources() throws InternalErrorException{
            setResourcesId(UIConstants.DEFAULT_CALENDAR_RESOURCES_ID);
            setResourcesKey(UIConstants.DEFAULT_CALENDAR_SHOW_BUTTON_ID);
            
            super.buildResources();
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.ButtonComponent#buildStyleClass()
         */
        protected void buildStyleClass() throws InternalErrorException{
            setStyleClass(UIConstants.DEFAULT_CALENDAR_SHOW_BUTTON_STYLE_CLASS);
            
            super.buildStyleClass();
        }
    }
    
    /**
     * Class that defines the previous year button component.
     *
     * @author fvilarinho
     * @since 2.0.0
     */
    private class PreviousYearButtonComponent extends ButtonComponent{
        private static final long serialVersionUID = 6998272659624256987L;
        
        /**
         * Constructor - Defines the component.
         *
         * @param calendarPropertyComponent Instance that contains the calendar component.
         */
        public PreviousYearButtonComponent(CalendarPropertyComponent calendarPropertyComponent){
            super();
            
            if(calendarPropertyComponent != null){
                setPageContext(calendarPropertyComponent.getPageContext());
                setOutputStream(calendarPropertyComponent.getOutputStream());
                setActionFormName(calendarPropertyComponent.getActionFormName());
                setParent(calendarPropertyComponent);
            }
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.ButtonComponent#buildEvents()
         */
        protected void buildEvents() throws InternalErrorException{
            CalendarPropertyComponent calendarPropertyComponent = (CalendarPropertyComponent) getParent();
            String name = (calendarPropertyComponent != null ? calendarPropertyComponent.getName() : null);
            
            if(name != null && name.length() > 0){
                StringBuilder onClick = new StringBuilder();
                
                onClick.append("moveToPreviousYear('");
                onClick.append(name);
                onClick.append("');");
                
                setOnClick(onClick.toString());
                
                super.buildEvents();
            }
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#buildResources()
         */
        protected void buildResources() throws InternalErrorException{
            setResourcesId(UIConstants.DEFAULT_CALENDAR_RESOURCES_ID);
            setResourcesKey(UIConstants.DEFAULT_CALENDAR_PREVIOUS_YEAR_BUTTON_ID);
            
            super.buildResources();
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.ButtonComponent#buildStyleClass()
         */
        protected void buildStyleClass() throws InternalErrorException{
            setStyleClass(UIConstants.DEFAULT_CALENDAR_PREVIOUS_YEAR_BUTTON_STYLE_CLASS);
            
            super.buildStyleClass();
        }
    }
    
    /**
     * Class that defines the previous month button component.
     *
     * @author fvilarinho
     * @since 2.0.0
     */
    private class PreviousMonthButtonComponent extends ButtonComponent{
        private static final long serialVersionUID = -3641327767947261579L;
        
        /**
         * Constructor - Defines the component.
         *
         * @param calendarPropertyComponent Instance that contains the calendar component.
         */
        public PreviousMonthButtonComponent(CalendarPropertyComponent calendarPropertyComponent){
            super();
            
            if(calendarPropertyComponent != null){
                setPageContext(calendarPropertyComponent.getPageContext());
                setOutputStream(calendarPropertyComponent.getOutputStream());
                setActionFormName(calendarPropertyComponent.getActionFormName());
                setParent(calendarPropertyComponent);
            }
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.ButtonComponent#buildEvents()
         */
        protected void buildEvents() throws InternalErrorException{
            CalendarPropertyComponent calendarPropertyComponent = (CalendarPropertyComponent) getParent();
            String name = (calendarPropertyComponent != null ? calendarPropertyComponent.getName() : null);
            
            if(name != null && name.length() > 0){
                StringBuilder onClick = new StringBuilder();
                
                onClick.append("moveToPreviousMonth('");
                onClick.append(name);
                onClick.append("');");
                
                setOnClick(onClick.toString());
                
                super.buildEvents();
            }
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#buildResources()
         */
        protected void buildResources() throws InternalErrorException{
            setResourcesId(UIConstants.DEFAULT_CALENDAR_RESOURCES_ID);
            setResourcesKey(UIConstants.DEFAULT_CALENDAR_PREVIOUS_MONTH_BUTTON_ID);
            
            super.buildResources();
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.ButtonComponent#buildStyleClass()
         */
        protected void buildStyleClass() throws InternalErrorException{
            setStyleClass(UIConstants.DEFAULT_CALENDAR_PREVIOUS_MONTH_BUTTON_STYLE_CLASS);
            
            super.buildStyleClass();
        }
    }
    
    /**
     * Class that defines the next month button component.
     *
     * @author fvilarinho
     * @since 2.0.0
     */
    private class NextMonthButtonComponent extends ButtonComponent{
        private static final long serialVersionUID = 7443293371718716410L;
        
        /**
         * Constructor - Defines the component.
         *
         * @param calendarPropertyComponent Instance that contains the calendar component.
         */
        public NextMonthButtonComponent(CalendarPropertyComponent calendarPropertyComponent){
            super();
            
            if(calendarPropertyComponent != null){
                setPageContext(calendarPropertyComponent.getPageContext());
                setOutputStream(calendarPropertyComponent.getOutputStream());
                setActionFormName(calendarPropertyComponent.getActionFormName());
                setParent(calendarPropertyComponent);
            }
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.ButtonComponent#buildEvents()
         */
        protected void buildEvents() throws InternalErrorException{
            CalendarPropertyComponent calendarPropertyComponent = (CalendarPropertyComponent) getParent();
            String name = (calendarPropertyComponent != null ? calendarPropertyComponent.getName() : null);
            
            if(name != null && name.length() > 0){
                StringBuilder onClick = new StringBuilder();
                
                onClick.append("moveToNextMonth('");
                onClick.append(name);
                onClick.append("');");
                
                setOnClick(onClick.toString());
                
                super.buildEvents();
            }
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#buildResources()
         */
        protected void buildResources() throws InternalErrorException{
            setResourcesId(UIConstants.DEFAULT_CALENDAR_RESOURCES_ID);
            setResourcesKey(UIConstants.DEFAULT_CALENDAR_NEXT_MONTH_BUTTON_ID);
            
            super.buildResources();
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.ButtonComponent#buildStyleClass()
         */
        protected void buildStyleClass() throws InternalErrorException{
            setStyleClass(UIConstants.DEFAULT_CALENDAR_NEXT_MONTH_BUTTON_STYLE_CLASS);
            
            super.buildStyleClass();
        }
    }
    
    /**
     * Class that defines the next year button component.
     *
     * @author fvilarinho
     * @since 2.0.0
     */
    private class NextYearButtonComponent extends ButtonComponent{
        private static final long serialVersionUID = -3340079892458275397L;
        
        /**
         * Constructor - Defines the component.
         *
         * @param calendarPropertyComponent Instance that contains the calendar component.
         */
        public NextYearButtonComponent(CalendarPropertyComponent calendarPropertyComponent){
            super();
            
            if(calendarPropertyComponent != null){
                setPageContext(calendarPropertyComponent.getPageContext());
                setOutputStream(calendarPropertyComponent.getOutputStream());
                setActionFormName(calendarPropertyComponent.getActionFormName());
                setParent(calendarPropertyComponent);
            }
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.ButtonComponent#buildEvents()
         */
        protected void buildEvents() throws InternalErrorException{
            CalendarPropertyComponent calendarPropertyComponent = (CalendarPropertyComponent) getParent();
            String name = (calendarPropertyComponent != null ? calendarPropertyComponent.getName() : null);
            
            if(name != null && name.length() > 0){
                StringBuilder onClick = new StringBuilder();
                
                onClick.append("moveToNextYear('");
                onClick.append(name);
                onClick.append("');");
                
                setOnClick(onClick.toString());
                
                super.buildEvents();
            }
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#buildResources()
         */
        protected void buildResources() throws InternalErrorException{
            setResourcesId(UIConstants.DEFAULT_CALENDAR_RESOURCES_ID);
            setResourcesKey(UIConstants.DEFAULT_CALENDAR_NEXT_YEAR_BUTTON_ID);
            
            super.buildResources();
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.ButtonComponent#buildStyleClass()
         */
        protected void buildStyleClass() throws InternalErrorException{
            setStyleClass(UIConstants.DEFAULT_CALENDAR_NEXT_YEAR_BUTTON_STYLE_CLASS);
            
            super.buildStyleClass();
        }
    }
    
    /**
     * Class that defines the hours input of the calendar component.
     *
     * @author fvilarinho
     * @since 3.3.0
     */
    private class HoursPropertyComponent extends SliderBarComponent{
        private static final long serialVersionUID = -8176506397233683603L;
        
        /**
         * Constructor - Defines the component.
         *
         * @param calendarPropertyComponent Instance that contains the calendar component.
         */
        public HoursPropertyComponent(CalendarPropertyComponent calendarPropertyComponent){
            super();
            
            if(calendarPropertyComponent != null){
                setPageContext(calendarPropertyComponent.getPageContext());
                setOutputStream(calendarPropertyComponent.getOutputStream());
                setActionFormName(calendarPropertyComponent.getActionFormName());
                setParent(calendarPropertyComponent);
            }
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.BasePropertyComponent#buildResources()
         */
        protected void buildResources() throws InternalErrorException{
            setResourcesId(UIConstants.DEFAULT_CALENDAR_RESOURCES_ID);
            setResourcesKey(UIConstants.DEFAULT_CALENDAR_HOURS_ID);
            
            super.buildResources();
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#buildName()
         */
        protected void buildName() throws InternalErrorException{
            CalendarPropertyComponent calendarPropertyComponent = (CalendarPropertyComponent) getParent();
            String name = (calendarPropertyComponent != null ? calendarPropertyComponent.getName() : null);
            
            if(name != null && name.length() > 0){
                StringBuilder nameBuffer = new StringBuilder();
                
                nameBuffer.append(name);
                nameBuffer.append(".");
                nameBuffer.append(UIConstants.DEFAULT_CALENDAR_HOURS_ID);
                
                setName(nameBuffer.toString());
                
                super.buildName();
            }
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.BasePropertyComponent#buildStyleClass()
         */
        protected void buildStyleClass() throws InternalErrorException{
            setLabelStyle("width: 40px;");
            setStyleClass(UIConstants.DEFAULT_CALENDAR_TIME_STYLE_CLASS);
            
            super.buildStyleClass();
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.SliderBarComponent#buildEvents()
         */
        protected void buildEvents() throws InternalErrorException{
            String currentOnChange = getOnChange();
            StringBuilder onChange = new StringBuilder();
            
            onChange.append("setCurrentCalendarHours(this);");
            
            if(currentOnChange != null && currentOnChange.length() > 0){
                onChange.append(" ");
                onChange.append(currentOnChange);
                
                if(!currentOnChange.endsWith(";"))
                    onChange.append(";");
            }
            
            setOnChange(onChange.toString());
            
            super.buildEvents();
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.BasePropertyComponent#buildSize()
         */
        protected void buildSize() throws InternalErrorException{
            setSize(2);
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.BasePropertyComponent#buildMaximumLength()
         */
        protected void buildMaximumLength() throws InternalErrorException{
            setMaximumLength(2);
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.TextPropertyComponent#buildRestrictions()
         */
        protected void buildRestrictions() throws InternalErrorException{
            setIsNumber(true);
            
            CalendarPropertyComponent calendarPropertyComponent = (CalendarPropertyComponent) getParent();
            
            if(calendarPropertyComponent != null){
                String pattern = calendarPropertyComponent.getPattern();
                
                if(pattern != null && pattern.contains("hh"))
                    setMaximumValue(12l);
                else
                    setMaximumValue(23l);
                
                super.buildRestrictions();
            }
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.BasePropertyComponent#buildAlignment()
         */
        protected void buildAlignment() throws InternalErrorException{
            setAlignmentType(AlignmentType.RIGHT);
            
            super.buildAlignment();
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.SliderBarComponent#initialize()
         */
        protected void initialize() throws InternalErrorException{
            super.initialize();
            
            DateTime dateTime = getValue();
            Calendar calendar = Calendar.getInstance(getCurrentLanguage());
            
            if(dateTime != null)
                calendar.setTimeInMillis(dateTime.getTime());
            
            setValue(calendar.get(Calendar.HOUR_OF_DAY));
        }
    }
    
    /**
     * Class that defines the minutes input of the calendar component.
     *
     * @author fvilarinho
     * @since 3.3.0
     */
    private class MinutesPropertyComponent extends SliderBarComponent{
        private static final long serialVersionUID = -8176506397233683603L;
        
        /**
         * Constructor - Defines the component.
         *
         * @param calendarPropertyComponent Instance that contains the calendar component.
         */
        public MinutesPropertyComponent(CalendarPropertyComponent calendarPropertyComponent){
            super();
            
            if(calendarPropertyComponent != null){
                setPageContext(calendarPropertyComponent.getPageContext());
                setOutputStream(calendarPropertyComponent.getOutputStream());
                setActionFormName(calendarPropertyComponent.getActionFormName());
                setParent(calendarPropertyComponent);
            }
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.BasePropertyComponent#buildResources()
         */
        protected void buildResources() throws InternalErrorException{
            setResourcesId(UIConstants.DEFAULT_CALENDAR_RESOURCES_ID);
            setResourcesKey(UIConstants.DEFAULT_CALENDAR_MINUTES_ID);
            
            super.buildResources();
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#buildName()
         */
        protected void buildName() throws InternalErrorException{
            CalendarPropertyComponent calendarPropertyComponent = (CalendarPropertyComponent) getParent();
            String name = (calendarPropertyComponent != null ? calendarPropertyComponent.getName() : null);
            
            if(name != null && name.length() > 0){
                StringBuilder nameBuffer = new StringBuilder();
                
                nameBuffer.append(name);
                nameBuffer.append(".");
                nameBuffer.append(UIConstants.DEFAULT_CALENDAR_MINUTES_ID);
                
                setName(nameBuffer.toString());
                
                super.buildName();
            }
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.BasePropertyComponent#buildStyleClass()
         */
        protected void buildStyleClass() throws InternalErrorException{
            setLabelStyle("width: 40px;");
            setStyleClass(UIConstants.DEFAULT_CALENDAR_TIME_STYLE_CLASS);
            
            super.buildStyleClass();
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.SliderBarComponent#buildEvents()
         */
        protected void buildEvents() throws InternalErrorException{
            String currentOnChange = getOnChange();
            StringBuilder onChange = new StringBuilder();
            
            onChange.append("setCurrentCalendarMinutes(this);");
            
            if(currentOnChange != null && currentOnChange.length() > 0){
                onChange.append(" ");
                onChange.append(currentOnChange);
                
                if(!currentOnChange.endsWith(";"))
                    onChange.append(";");
            }
            
            setOnChange(onChange.toString());
            
            super.buildEvents();
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.BasePropertyComponent#buildSize()
         */
        protected void buildSize() throws InternalErrorException{
            setSize(2);
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.BasePropertyComponent#buildMaximumLength()
         */
        protected void buildMaximumLength() throws InternalErrorException{
            setMaximumLength(2);
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.TextPropertyComponent#buildRestrictions()
         */
        protected void buildRestrictions() throws InternalErrorException{
            setIsNumber(true);
            setMaximumValue(59);
            
            super.buildRestrictions();
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.BasePropertyComponent#buildAlignment()
         */
        protected void buildAlignment() throws InternalErrorException{
            setAlignmentType(AlignmentType.RIGHT);
            
            super.buildAlignment();
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.SliderBarComponent#initialize()
         */
        protected void initialize() throws InternalErrorException{
            super.initialize();
            
            DateTime dateTime = getValue();
            Calendar calendar = Calendar.getInstance(getCurrentLanguage());
            
            if(dateTime != null)
                calendar.setTimeInMillis(dateTime.getTime());
            
            setValue(calendar.get(Calendar.MINUTE));
        }
    }
    
    /**
     * Class that defines the seconds input of the calendar component.
     *
     * @author fvilarinho
     * @since 3.3.0
     */
    private class SecondsPropertyComponent extends SliderBarComponent{
        private static final long serialVersionUID = -8176506397233683603L;
        
        /**
         * Constructor - Defines the component.
         *
         * @param calendarPropertyComponent Instance that contains the calendar component.
         */
        public SecondsPropertyComponent(CalendarPropertyComponent calendarPropertyComponent){
            super();
            
            if(calendarPropertyComponent != null){
                setPageContext(calendarPropertyComponent.getPageContext());
                setOutputStream(calendarPropertyComponent.getOutputStream());
                setActionFormName(calendarPropertyComponent.getActionFormName());
                setParent(calendarPropertyComponent);
            }
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.BasePropertyComponent#buildResources()
         */
        protected void buildResources() throws InternalErrorException{
            setResourcesId(UIConstants.DEFAULT_CALENDAR_RESOURCES_ID);
            setResourcesKey(UIConstants.DEFAULT_CALENDAR_SECONDS_ID);
            
            super.buildResources();
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#buildName()
         */
        protected void buildName() throws InternalErrorException{
            CalendarPropertyComponent calendarPropertyComponent = (CalendarPropertyComponent) getParent();
            String name = (calendarPropertyComponent != null ? calendarPropertyComponent.getName() : null);
            
            if(name != null && name.length() > 0){
                StringBuilder nameBuffer = new StringBuilder();
                
                nameBuffer.append(name);
                nameBuffer.append(".");
                nameBuffer.append(UIConstants.DEFAULT_CALENDAR_SECONDS_ID);
                
                setName(nameBuffer.toString());
                
                super.buildName();
            }
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.BasePropertyComponent#buildStyleClass()
         */
        protected void buildStyleClass() throws InternalErrorException{
            setLabelStyle("width: 40px;");
            setStyleClass(UIConstants.DEFAULT_CALENDAR_TIME_STYLE_CLASS);
            
            super.buildStyleClass();
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.SliderBarComponent#buildEvents()
         */
        protected void buildEvents() throws InternalErrorException{
            String currentOnChange = getOnChange();
            StringBuilder onChange = new StringBuilder();
            
            onChange.append("setCurrentCalendarSeconds(this);");
            
            if(currentOnChange != null && currentOnChange.length() > 0){
                onChange.append(" ");
                onChange.append(currentOnChange);
                
                if(!currentOnChange.endsWith(";"))
                    onChange.append(";");
            }
            
            setOnChange(onChange.toString());
            
            super.buildEvents();
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.BasePropertyComponent#buildSize()
         */
        protected void buildSize() throws InternalErrorException{
            setSize(2);
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.BasePropertyComponent#buildMaximumLength()
         */
        protected void buildMaximumLength() throws InternalErrorException{
            setMaximumLength(2);
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.TextPropertyComponent#buildRestrictions()
         */
        protected void buildRestrictions() throws InternalErrorException{
            setIsNumber(true);
            setMaximumValue(59);
            
            super.buildRestrictions();
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.BasePropertyComponent#buildAlignment()
         */
        protected void buildAlignment() throws InternalErrorException{
            setAlignmentType(AlignmentType.RIGHT);
            
            super.buildAlignment();
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.SliderBarComponent#initialize()
         */
        protected void initialize() throws InternalErrorException{
            super.initialize();
            
            DateTime dateTime = getValue();
            Calendar calendar = Calendar.getInstance(getCurrentLanguage());
            
            if(dateTime != null)
                calendar.setTimeInMillis(dateTime.getTime());
            
            setValue(calendar.get(Calendar.SECOND));
        }
    }
    
    /**
     * Class that defines the milliseconds input of the calendar component.
     *
     * @author fvilarinho
     * @since 3.3.0
     */
    private class MillisecondsPropertyComponent extends SliderBarComponent{
        private static final long serialVersionUID = -8176506397233683603L;
        
        /**
         * Constructor - Defines the component.
         *
         * @param calendarPropertyComponent Instance that contains the calendar component.
         */
        public MillisecondsPropertyComponent(CalendarPropertyComponent calendarPropertyComponent){
            super();
            
            if(calendarPropertyComponent != null){
                setPageContext(calendarPropertyComponent.getPageContext());
                setOutputStream(calendarPropertyComponent.getOutputStream());
                setActionFormName(calendarPropertyComponent.getActionFormName());
                setParent(calendarPropertyComponent);
            }
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.BasePropertyComponent#buildResources()
         */
        protected void buildResources() throws InternalErrorException{
            setResourcesId(UIConstants.DEFAULT_CALENDAR_RESOURCES_ID);
            setResourcesKey(UIConstants.DEFAULT_CALENDAR_MILLISECONDS_ID);
            
            super.buildResources();
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#buildName()
         */
        protected void buildName() throws InternalErrorException{
            CalendarPropertyComponent calendarPropertyComponent = (CalendarPropertyComponent) getParent();
            String name = (calendarPropertyComponent != null ? calendarPropertyComponent.getName() : null);
            
            if(name != null && name.length() > 0){
                StringBuilder nameBuffer = new StringBuilder();
                
                nameBuffer.append(name);
                nameBuffer.append(".");
                nameBuffer.append(UIConstants.DEFAULT_CALENDAR_MILLISECONDS_ID);
                
                setName(nameBuffer.toString());
                
                super.buildName();
            }
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.BasePropertyComponent#buildStyleClass()
         */
        protected void buildStyleClass() throws InternalErrorException{
            setLabelStyle("width: 40px;");
            setStyleClass(UIConstants.DEFAULT_CALENDAR_TIME_STYLE_CLASS);
            
            super.buildStyleClass();
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.SliderBarComponent#buildEvents()
         */
        protected void buildEvents() throws InternalErrorException{
            String currentOnChange = getOnChange();
            StringBuilder onChange = new StringBuilder();
            
            onChange.append("setCurrentCalendarMilliseconds(this);");
            
            if(currentOnChange != null && currentOnChange.length() > 0){
                onChange.append(" ");
                onChange.append(currentOnChange);
                
                if(!currentOnChange.endsWith(";"))
                    onChange.append(";");
            }
            
            setOnChange(onChange.toString());
            
            super.buildEvents();
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.BasePropertyComponent#buildSize()
         */
        protected void buildSize() throws InternalErrorException{
            setSize(3);
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.BasePropertyComponent#buildMaximumLength()
         */
        protected void buildMaximumLength() throws InternalErrorException{
            setMaximumLength(3);
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.TextPropertyComponent#buildRestrictions()
         */
        protected void buildRestrictions() throws InternalErrorException{
            setIsNumber(true);
            setMaximumValue(999);
            
            super.buildRestrictions();
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.BasePropertyComponent#buildAlignment()
         */
        protected void buildAlignment() throws InternalErrorException{
            setAlignmentType(AlignmentType.RIGHT);
            
            super.buildAlignment();
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.SliderBarComponent#initialize()
         */
        protected void initialize() throws InternalErrorException{
            super.initialize();
            
            DateTime dateTime = getValue();
            Calendar calendar = Calendar.getInstance(getCurrentLanguage());
            
            if(dateTime != null)
                calendar.setTimeInMillis(dateTime.getTime());
            
            setValue(calendar.get(Calendar.MILLISECOND));
        }
    }
    
    private class AmPropertyComponent extends RadioPropertyComponent{
        private static final long serialVersionUID = -8176506397233683603L;
        
        /**
         * Constructor - Defines the component.
         *
         * @param calendarPropertyComponent Instance that contains the calendar component.
         */
        public AmPropertyComponent(CalendarPropertyComponent calendarPropertyComponent){
            super();
            
            if(calendarPropertyComponent != null){
                setPageContext(calendarPropertyComponent.getPageContext());
                setOutputStream(calendarPropertyComponent.getOutputStream());
                setActionFormName(calendarPropertyComponent.getActionFormName());
                setParent(calendarPropertyComponent);
            }
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.BasePropertyComponent#buildResources()
         */
        protected void buildResources() throws InternalErrorException{
            setResourcesId(UIConstants.DEFAULT_CALENDAR_RESOURCES_ID);
            setResourcesKey(UIConstants.DEFAULT_CALENDAR_AM_ID);
            
            super.buildResources();
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#buildName()
         */
        protected void buildName() throws InternalErrorException{
            CalendarPropertyComponent calendarPropertyComponent = (CalendarPropertyComponent) getParent();
            String name = (calendarPropertyComponent != null ? calendarPropertyComponent.getName() : null);
            
            if(name != null && name.length() > 0){
                StringBuilder nameBuffer = new StringBuilder();
                
                nameBuffer.append(name);
                nameBuffer.append(".");
                nameBuffer.append(UIConstants.DEFAULT_CALENDAR_AM_PM_ID);
                
                setName(nameBuffer.toString());
                
                super.buildName();
            }
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.BasePropertyComponent#buildStyleClass()
         */
        protected void buildStyleClass() throws InternalErrorException{
            setLabelStyle("width: 40px;");
            setStyleClass(UIConstants.DEFAULT_CALENDAR_TIME_STYLE_CLASS);
            
            super.buildStyleClass();
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.SliderBarComponent#buildEvents()
         */
        protected void buildEvents() throws InternalErrorException{
            String currentOnChange = getOnChange();
            StringBuilder onChange = new StringBuilder();
            
            onChange.append("setCurrentCalendarAm(this);");
            
            if(currentOnChange != null && currentOnChange.length() > 0){
                onChange.append(" ");
                onChange.append(currentOnChange);
                
                if(!currentOnChange.endsWith(";"))
                    onChange.append(";");
            }
            
            setOnChange(onChange.toString());
            
            super.buildEvents();
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.BasePropertyComponent#buildAlignment()
         */
        protected void buildAlignment() throws InternalErrorException{
            setAlignmentType(AlignmentType.RIGHT);
            
            super.buildAlignment();
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.SliderBarComponent#initialize()
         */
        protected void initialize() throws InternalErrorException{
            super.initialize();
            
            DateTime dateTime = getValue();
            Calendar calendar = Calendar.getInstance(getCurrentLanguage());
            
            if(dateTime != null)
                calendar.setTimeInMillis(dateTime.getTime());
            
            setValue(calendar.get(Calendar.AM_PM) == Calendar.AM);
        }
    }
    
    private class PmPropertyComponent extends RadioPropertyComponent{
        private static final long serialVersionUID = -8176506397233683603L;
        
        /**
         * Constructor - Defines the component.
         *
         * @param calendarPropertyComponent Instance that contains the calendar component.
         */
        public PmPropertyComponent(CalendarPropertyComponent calendarPropertyComponent){
            super();
            
            if(calendarPropertyComponent != null){
                setPageContext(calendarPropertyComponent.getPageContext());
                setOutputStream(calendarPropertyComponent.getOutputStream());
                setActionFormName(calendarPropertyComponent.getActionFormName());
                setParent(calendarPropertyComponent);
            }
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.BasePropertyComponent#buildResources()
         */
        protected void buildResources() throws InternalErrorException{
            setResourcesId(UIConstants.DEFAULT_CALENDAR_RESOURCES_ID);
            setResourcesKey(UIConstants.DEFAULT_CALENDAR_PM_ID);
            
            super.buildResources();
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#buildName()
         */
        protected void buildName() throws InternalErrorException{
            CalendarPropertyComponent calendarPropertyComponent = (CalendarPropertyComponent) getParent();
            String name = (calendarPropertyComponent != null ? calendarPropertyComponent.getName() : null);
            
            if(name != null && name.length() > 0){
                StringBuilder nameBuffer = new StringBuilder();
                
                nameBuffer.append(name);
                nameBuffer.append(".");
                nameBuffer.append(UIConstants.DEFAULT_CALENDAR_AM_PM_ID);
                
                setName(nameBuffer.toString());
                
                super.buildName();
            }
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.BasePropertyComponent#buildStyleClass()
         */
        protected void buildStyleClass() throws InternalErrorException{
            setLabelStyle("width: 40px;");
            setStyleClass(UIConstants.DEFAULT_CALENDAR_TIME_STYLE_CLASS);
            
            super.buildStyleClass();
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.SliderBarComponent#buildEvents()
         */
        protected void buildEvents() throws InternalErrorException{
            String currentOnChange = getOnChange();
            StringBuilder onChange = new StringBuilder();
            
            onChange.append("setCurrentCalendarPm(this);");
            
            if(currentOnChange != null && currentOnChange.length() > 0){
                onChange.append(" ");
                onChange.append(currentOnChange);
                
                if(!currentOnChange.endsWith(";"))
                    onChange.append(";");
            }
            
            setOnChange(onChange.toString());
            
            super.buildEvents();
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.BasePropertyComponent#buildAlignment()
         */
        protected void buildAlignment() throws InternalErrorException{
            setAlignmentType(AlignmentType.RIGHT);
            
            super.buildAlignment();
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.SliderBarComponent#initialize()
         */
        protected void initialize() throws InternalErrorException{
            super.initialize();
            
            DateTime dateTime = getValue();
            Calendar calendar = Calendar.getInstance(getCurrentLanguage());
            
            if(dateTime != null)
                calendar.setTimeInMillis(dateTime.getTime());
            
            setValue(calendar.get(Calendar.AM_PM) == Calendar.PM);
        }
    }
}