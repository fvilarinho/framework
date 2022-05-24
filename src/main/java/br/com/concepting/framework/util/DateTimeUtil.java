package br.com.concepting.framework.util;

import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.util.helpers.DateTime;
import br.com.concepting.framework.util.types.DateFieldType;
import org.apache.commons.beanutils.ConstructorUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Class responsible to manipulate date/time.
 *
 * @author fvilarinho
 * @since 1.0.0
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
public class DateTimeUtil{
    /**
     * Returns the default formatting symbols of a date/time value.
     *
     * @return Instance that contains the formatting symbols.
     */
    public static DateFormatSymbols getDefaultFormatSymbols(){
        return getFormatSymbols(null);
    }
    
    /**
     * Returns the formatting symbols of a date/time value.
     *
     * @param language Instance that contains the language.
     * @return Instance that contains the formatting symbols.
     */
    public static DateFormatSymbols getFormatSymbols(Locale language){
        if(language == null)
            language = LanguageUtil.getDefaultLanguage();
        
        return new DateFormatSymbols(language);
    }
    
    /**
     * Indicates if the current day is a weekend.
     *
     * @return True/False.
     */
    public static boolean isWeekend(){
        return isWeekend(null);
    }
    
    /**
     * Indicates if the day is a weekend.
     *
     * @param date Instance that contains the date/time.
     * @return True/False.
     */
    public static boolean isWeekend(Date date){
        Calendar calendar = Calendar.getInstance();
        
        if(date != null)
            calendar.setTime(date);
        
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        
        return (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY);
    }
    
    /**
     * Merges a date and time instances.
     *
     * @param <D> Class that defines the date/time.
     * @param date Instance that contains the date.
     * @param time Instance that contains the time.
     * @return Instance that contains the date/time.
     */
    @SuppressWarnings("unchecked")
    public static <D extends Date> D mergeDateAndTime(Date date, Date time){
        if(date == null || time == null)
            return null;
        
        Calendar calendar = Calendar.getInstance();
        
        calendar.setTime(date);
        
        Calendar timeCalendar = Calendar.getInstance();
        
        timeCalendar.setTime(time);
        
        calendar.set(Calendar.HOUR_OF_DAY, timeCalendar.get(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, timeCalendar.get(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, timeCalendar.get(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, timeCalendar.get(Calendar.MILLISECOND));
        
        return (D) calendar.getTime();
    }
    
    /**
     * Formats a date/time.
     *
     * @param date Instance that contains the date/time.
     * @param pattern String that contains the pattern.
     * @return Instance that contains the date/time.
     */
    public static String format(Date date, String pattern){
        if(date != null && pattern != null && pattern.length() > 0){
            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            
            formatter.setLenient(false);
            
            return formatter.format(date);
        }
        
        return StringUtils.EMPTY;
    }
    
    /**
     * Formats a date/time.
     *
     * @param date Instance that contains the date/time.
     * @return Instance that contains the date/time.
     */
    public static String format(Date date){
        if(date != null)
            return format(date, LanguageUtil.getDefaultLanguage());

        return StringUtils.EMPTY;
    }
    
    /**
     * Formats a date/time.
     *
     * @param date Instance that contains the date/time.
     * @param language Instance that contains the language that will be used.
     * @return Instance that contains the date/time.
     */
    public static String format(Date date, Locale language){
        if(date != null){
            Class<?> dateClass = date.getClass();
            
            return format(date, PropertyUtil.isTime(dateClass), language);
        }

        return StringUtils.EMPTY;
    }
    
    /**
     * Formats a date/time.
     *
     * @param date Instance that contains the date/time.
     * @param isTime Indicates if it should consider the time formatting.
     * @param language Instance that contains the language that will be used.
     * @return Instance that contains the date/time.
     */
    private static String format(Date date, boolean isTime, Locale language){
        if(date != null){
            SimpleDateFormat formatter = new SimpleDateFormat(getDefaultPattern(isTime, language));
            
            formatter.setLenient(false);
            
            return formatter.format(date);
        }

        return StringUtils.EMPTY;
    }
    
    /**
     * Parses a string that contains the formatted date/time.
     *
     * @param <D> Class that contains the date/time.
     * @param value String that contains the date/time.
     * @return Instance that contains the date/time.
     * @throws ParseException Occurs when was not possible to execute the
     * operation.
     */
    public static <D extends Date> D parse(String value) throws ParseException{
        if(value != null && value.length() > 0)
            return parse(value, LanguageUtil.getDefaultLanguage());

        return null;
    }
    
    /**
     * Parses a string that contains the formatted date/time.
     *
     * @param <D> Class that contains the date/time.
     * @param value String that contains the date/time.
     * @param pattern String that contains the pattern.
     * @return Instance that contains the date/time.
     * @throws ParseException Occurs when was not possible to execute the
     * operation.
     */
    @SuppressWarnings("unchecked")
    public static <D extends Date> D parse(String value, String pattern) throws ParseException{
        if(value != null && value.length() > 0 && pattern != null && pattern.length() > 0){
            SimpleDateFormat parser = new SimpleDateFormat(pattern);
            
            parser.setLenient(false);
            
            Calendar result = Calendar.getInstance();
            Calendar calendarBuffer = Calendar.getInstance();
            
            calendarBuffer.setTime(parser.parse(value));
            
            if(pattern.contains("d")){
                int buffer = calendarBuffer.get(Calendar.DAY_OF_MONTH);
                
                if(buffer > 0)
                    result.set(Calendar.DAY_OF_MONTH, buffer);
            }
            
            if(pattern.contains("M")){
                int buffer = calendarBuffer.get(Calendar.MONTH);
                
                if(buffer >= 0)
                    result.set(Calendar.MONTH, buffer);
            }
            
            if(pattern.contains("y")){
                int buffer = calendarBuffer.get(Calendar.YEAR);
                
                if(buffer != 1970 && buffer > 0)
                    result.set(Calendar.YEAR, buffer);
            }
            
            if(pattern.contains("H") || pattern.contains("h")){
                int buffer = calendarBuffer.get(Calendar.HOUR_OF_DAY);
                
                if(buffer >= 0)
                    result.set(Calendar.HOUR_OF_DAY, buffer);
            }
            
            if(pattern.contains("m")){
                int buffer = calendarBuffer.get(Calendar.MINUTE);
                
                if(buffer >= 0)
                    result.set(Calendar.MINUTE, buffer);
            }
            
            if(pattern.contains("s")){
                int buffer = calendarBuffer.get(Calendar.SECOND);
                
                if(buffer >= 0)
                    result.set(Calendar.SECOND, buffer);
                
                buffer = calendarBuffer.get(Calendar.MILLISECOND);
                
                if(buffer >= 0)
                    result.set(Calendar.MILLISECOND, buffer);
            }
            
            if(pattern.contains("HH") || pattern.contains("mm") || pattern.contains("ss") || pattern.contains("SSS"))
                return (D) new DateTime(result.getTimeInMillis());
            
            return (D) result.getTime();
        }
        
        return null;
    }
    
    /**
     * Parses a string that contains the formatted date/time.
     *
     * @param <D> Class that contains the date/time.
     * @param value String that contains the date/time.
     * @param language Instance that contains the language that will be used.
     * @return Instance that contains the date/time.
     * @throws ParseException Occurs when was not possible to execute the
     * operation.
     */
    @SuppressWarnings("unchecked")
    public static <D extends Date> D parse(String value, Locale language) throws ParseException{
        if(value == null)
            return null;
        
        SimpleDateFormat parser = null;
        
        try{
            parser = new SimpleDateFormat(getDefaultDateTimePattern(language));
            
            parser.setLenient(false);
            
            return (D) new DateTime(parser.parse(value).getTime());
        }
        catch(IllegalArgumentException e){
            if(parser != null){
                parser.applyPattern(getDefaultDatePattern(language));
                
                return (D) parser.parse(value);
            }
            
            throw new ParseException(value, 1);
        }
    }
    
    /**
     * Returns the default date pattern.
     *
     * @param language Instance that contains the language that will be used.
     * @return String that contains a pattern.
     */
    public static String getDefaultDatePattern(Locale language){
        return getDefaultPattern(false, language);
    }
    
    /**
     * Returns the default date/time pattern.
     *
     * @param language Instance that contains the language that will be used.
     * @return String that contains a pattern.
     */
    public static String getDefaultDateTimePattern(Locale language){
        return getDefaultPattern(true, language);
    }
    
    /**
     * Returns the default pattern.
     *
     * @param isTime Indicates that the pattern includes time.
     * @param language Instance that contains the language that will be used.
     * @return String that contains a pattern.
     */
    private static String getDefaultPattern(boolean isTime, Locale language){
        if(language == null)
            language = LanguageUtil.getDefaultLanguage();
        
        DateFormat formatter;
        
        if(isTime)
            formatter = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, language);
        else
            formatter = DateFormat.getDateInstance(DateFormat.SHORT, language);
        
        String pattern = ((SimpleDateFormat) formatter).toPattern();
        
        Collection<String> patternParts = PropertyUtil.instantiate(Constants.DEFAULT_LIST_CLASS);
        String patternBuffer = String.valueOf(pattern.charAt(0));
        StringBuilder currentPattern = new StringBuilder(patternBuffer);
        
        for(int cont = 1; cont < pattern.length(); cont++){
            if(patternBuffer.equals(String.valueOf(pattern.charAt(cont))))
                currentPattern.append(patternBuffer);
            else{
                patternParts.add(currentPattern.toString());
                
                patternBuffer = String.valueOf(pattern.charAt(cont));
                currentPattern = new StringBuilder(patternBuffer);
            }
        }
        
        if(!patternParts.contains(currentPattern.toString()))
            patternParts.add(currentPattern.toString());
        
        currentPattern = new StringBuilder();
        
        for(String patternPart: patternParts){
            if(patternPart.length() == 1)
                patternBuffer = patternPart;
            else
                patternBuffer = patternPart.substring(0, 1);
            
            if(patternPart.contains("y")){
                patternBuffer = StringUtil.replicate(patternBuffer, 4 - patternPart.length());
                
                currentPattern.append(patternBuffer);
            }
            else if(patternPart.contains("S")){
                patternBuffer = StringUtil.replicate(patternBuffer, 3 - patternPart.length());
                
                currentPattern.append(patternBuffer);
            }
            else if(patternPart.equals("d") || patternPart.equals("M") || patternPart.equals("H") || patternPart.equals("h") || patternPart.equals("m")){
                patternBuffer = StringUtil.replicate(patternBuffer, 2 - patternPart.length());
                
                currentPattern.append(patternBuffer);
            }
            
            currentPattern.append(patternPart);
        }
        
        pattern = currentPattern.toString();
        
        return pattern;
    }
    
    /**
     * Indicates if the current year is a leap.
     *
     * @return True/False.
     */
    public static boolean isLeapYear(){
        return isLeapYear(null);
    }
    
    /**
     * Indicates if the year is a leap.
     *
     * @param date Instance that contains the date.
     * @return True/False.
     */
    public static boolean isLeapYear(Date date){
        Calendar calendar = Calendar.getInstance();
        
        if(date != null)
            calendar.setTime(date);
        
        return new GregorianCalendar().isLeapYear(calendar.get(Calendar.YEAR));
    }
    
    /**
     * Returns the last day of the current month.
     *
     * @return Numeric value that contains the last day of the month.
     */
    public static int getLastDayOfMonth(){
        return getLastDayOfMonth(null);
    }
    
    /**
     * Returns the last day of the month.
     *
     * @param date Instance that contains the date/time.
     * @return Numeric value that contains the last day of the month.
     */
    public static int getLastDayOfMonth(Date date){
        Calendar calendar = Calendar.getInstance();
        
        if(date != null)
            calendar.setTime(date);
        
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
    
    /**
     * Returns the month names list.
     *
     * @return List that contains the month names.
     */
    public static String[] getMonthsNames(){
        return getMonthsNames(LanguageUtil.getDefaultLanguage());
    }
    
    /**
     * Returns um array that contains the names of the months.
     *
     * @param language Instance that contains the resources of the language.
     * @return Array that contains the names of the months.
     */
    public static String[] getMonthsNames(Locale language){
        DateFormatSymbols symbols = new DateFormatSymbols((language == null ? LanguageUtil.getDefaultLanguage() : language));
        
        return symbols.getMonths();
    }
    
    /**
     * Returns the month name of a date.
     *
     * @return String that contains the name of the month.
     */
    public static String getMonthName(){
        return getMonthName(null);
    }
    
    /**
     * Returns the month name of a date.
     *
     * @param date Instance that contains the date.
     * @return String that contains the name of the month.
     */
    public static String getMonthName(Date date){
        return getMonthName(date, LanguageUtil.getDefaultLanguage());
    }
    
    /**
     * Returns the month name of a date.
     *
     * @param date Instance that contains the date.
     * @param language Instance that contains the language that will be used.
     * @return String that contains the name of the month.
     */
    public static String getMonthName(Date date, Locale language){
        Calendar calendar = Calendar.getInstance();
        
        if(date != null)
            calendar.setTime(date);
        
        String[] monthsNames = getMonthsNames(language);
        
        return monthsNames[calendar.get(Calendar.MONTH)];
    }
    
    /**
     * Returns the difference between two dates/times.
     *
     * @param date1 Instance that contains the date/time 1.
     * @param date2 Instance that contains the date/time 2.
     * @param dateField Instance that contains the field.
     * @return Numeric value that contains the difference.
     */
    public static int diff(Date date1, Date date2, DateFieldType dateField){
        if(date1 == null || date2 == null || dateField == null)
            return 0;
        
        Date dateBuffer1 = date1;
        Date dateBuffer2 = date2;
        
        if(dateField != DateFieldType.HOURS && dateField != DateFieldType.MINUTES && dateField != DateFieldType.SECONDS && dateField != DateFieldType.MILLISECONDS){
            Calendar calendar = Calendar.getInstance();
            
            calendar.setTime(dateBuffer1);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            
            dateBuffer1 = calendar.getTime();
            
            calendar.setTime(dateBuffer2);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            
            dateBuffer2 = calendar.getTime();
        }
        
        if(dateField == DateFieldType.MILLISECONDS)
            return (int)(dateBuffer1.getTime() - dateBuffer2.getTime());
        
        return (int)((dateBuffer1.getTime() - dateBuffer2.getTime()) / dateField.getMilliseconds());
    }
    
    /**
     * Calculates an age based on a birthdate.
     *
     * @param birthDate Instance that contains the birthdate.
     * @return Numeric value that contains the age.
     */
    public static int calculateAge(Date birthDate){
        if(birthDate == null)
            return 0;
        
        Calendar birthDateCalendar = Calendar.getInstance();
        Calendar nowCalendar = Calendar.getInstance();
        
        birthDateCalendar.setTime(birthDate);
        
        int yearBirth = birthDateCalendar.get(Calendar.YEAR);
        int monthBirth = birthDateCalendar.get(Calendar.MONTH);
        int dateBirth = birthDateCalendar.get(Calendar.DATE);
        int hoursBirth = birthDateCalendar.get(Calendar.HOUR_OF_DAY);
        int minutesBirth = birthDateCalendar.get(Calendar.MINUTE);
        int secondsBirth = birthDateCalendar.get(Calendar.SECOND);
        
        int yearNow = nowCalendar.get(Calendar.YEAR);
        int monthNow = nowCalendar.get(Calendar.MONTH);
        int dateNow = nowCalendar.get(Calendar.DATE);
        int hoursNow = nowCalendar.get(Calendar.HOUR_OF_DAY);
        int minutesNow = nowCalendar.get(Calendar.MINUTE);
        int secondsNow = nowCalendar.get(Calendar.SECOND);
        
        int age = yearNow - yearBirth;
        
        if(monthNow < monthBirth)
            age--;
        else if(monthNow == monthBirth){
            if(dateNow < dateBirth)
                age--;
            else if(dateNow == dateBirth){
                if(hoursNow < hoursBirth)
                    age--;
                else if(hoursNow == hoursBirth){
                    if(minutesNow < minutesBirth)
                        age--;
                    else if(minutesNow == minutesBirth){
                        if(secondsNow < secondsBirth)
                            age--;
                    }
                }
            }
        }
        
        return age;
    }
    
    /**
     * Adds a numeric value to a specific field of a date/time.
     *
     * @param <D> Class that defines the date/time.
     * @param date Instance that contains the date/time.
     * @param value Numeric value that contains the value that will be
     * subtracted.
     * @param dateField Instance that contains the field.
     * @return Instance that contains the date/time after the adition.
     */
    @SuppressWarnings("unchecked")
    public static <D extends Date> D add(Date date, int value, DateFieldType dateField){
        if(date == null || dateField == null)
            return null;
        
        Calendar calendar = Calendar.getInstance();
        
        calendar.setTime(date);
        
        switch(dateField){
            case DAYS:{
                calendar.add(Calendar.DATE, value);
                
                break;
            }
            case MONTHS:{
                calendar.add(Calendar.MONTH, value);
                
                break;
            }
            case YEARS:{
                calendar.add(Calendar.YEAR, value);
                
                break;
            }
            case HOURS:{
                calendar.add(Calendar.HOUR, value);
                
                break;
            }
            case MINUTES:{
                calendar.add(Calendar.MINUTE, value);
                
                break;
            }
            case SECONDS:{
                calendar.add(Calendar.SECOND, value);
                
                break;
            }
            case MILLISECONDS:{
                calendar.add(Calendar.MILLISECOND, value);
                
                break;
            }
        }
        
        if(date.getClass().equals(Date.class))
            return (D) calendar.getTime();
        
        try{
            return (D) ConstructorUtils.invokeConstructor(date.getClass(), calendar.getTimeInMillis());
        }
        catch(NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e){
            return (D) date;
        }
    }
    
    /**
     * Subtracts a numeric value from a specific field of a date/time.
     *
     * @param <D> Class that defines the date/time.
     * @param date Instance that contains the date/time.
     * @param value Numeric value that contains the value that will be
     * subtracted.
     * @param dateField Instance that contains the field.
     * @return Instance that contains the date/time after the subtraction.
     */
    public static <D extends Date> D subtract(Date date, int value, DateFieldType dateField){
        if(date == null || dateField == null)
            return null;
        
        return add(date, -value, dateField);
    }
    
    /**
     * Formats a time based on its milliseconds. HH:mm:ss.
     *
     * @param milliseconds Numeric value that contains the milliseconds.
     * @return Valor formatado.
     */
    public static String format(long milliseconds){
        StringBuilder result = new StringBuilder();
        int seconds = (int) (milliseconds / 1000);
        int hours = 0;
        int minutes = 0;

        if(milliseconds != 0){
            hours = seconds / 3600;
            seconds = seconds % 3600;
            minutes = seconds / 60;
            seconds = seconds % 60;
        }

        if(hours < 10)
            result.append("0");

        result.append(hours);
        result.append(":");

        if(minutes < 10)
            result.append("0");

        result.append(minutes);
        result.append(":");

        if(seconds < 10)
            result.append("0");

        result.append(seconds);

        return result.toString();
    }
}