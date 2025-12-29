package br.com.concepting.framework.util.types;

/**
 * Class that defines the types of the fields in a date/time value.
 *
 * @author fvilarinho
 * @since 1.0.0
 *
 * <pre>Copyright (C) 2007 Innovative Thinking.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <a href="https://www.gnu.org/licenses"></a>.</pre>
 */
public enum DateFieldType{
    /**
     * Milliseconds field.
     */
    MILLISECONDS(1000L),
    
    /**
     * Seconds field.
     */
    SECONDS(MILLISECONDS.getMilliseconds()),
    
    /**
     * Minutes field.
     */
    MINUTES(SECONDS.getMilliseconds() * 60),
    
    /**
     * Hours field.
     */
    HOURS(MINUTES.getMilliseconds() * 60),
    
    /**
     * Days field.
     */
    DAYS(HOURS.getMilliseconds() * 24),
    
    /**
     * Months field.
     */
    MONTHS(DAYS.getMilliseconds() * 31),
    
    /**
     * Years field.
     */
    YEARS(MONTHS.getMilliseconds() * 12);
    
    private long milliseconds;
    
    /**
     * Constructor - Defines the type.
     *
     * @param milliseconds Numeric value containing the milliseconds that
     * define the field.
     */
    DateFieldType(long milliseconds){
        setMilliseconds(milliseconds);
    }
    
    /**
     * Returns the milliseconds that define the field.
     *
     * @return Numeric value that contains the milliseconds that defines the
     * field.
     */
    public long getMilliseconds(){
        return this.milliseconds;
    }
    
    /**
     * Defines the milliseconds that define the field.
     *
     * @param milliseconds Numeric value that contains the milliseconds that
     * defines the field.
     */
    void setMilliseconds(long milliseconds){
        this.milliseconds = milliseconds;
    }
}