package br.com.innovativethinking.framework.security.exceptions;

import br.com.innovativethinking.framework.exceptions.ExpectedWarningException;

/**
 * Class that defines the exception when the password will expire soon.
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
public class PasswordWillExpireException extends ExpectedWarningException{
	private static final long serialVersionUID = 5978487541406636762L;

	private Integer daysUntilExpiration    = null;
	private Integer hoursUntilExpiration   = null;
	private Integer minutesUntilExpiration = null;
	private Integer secondsUntilExpiration = null;

	/**
	 * Constructor - Initializes the exception.
	 * 
	 * @param daysUntilExpiration Numeric value that contains the days until expiration.
	 * @param hoursUntilExpiration Numeric value that contains the hours until expiration.
	 * @param minutesUntilExpiration Numeric value that contains the minutes until expiration.
	 * @param secondsUntilExpiration Numeric value that contains the seconds until expiration.
	 */
	public PasswordWillExpireException(Integer daysUntilExpiration, Integer hoursUntilExpiration, Integer minutesUntilExpiration, Integer secondsUntilExpiration){
		super();

		setDaysUntilExpiration(daysUntilExpiration);
		setHoursUntilExpiration(hoursUntilExpiration);
		setMinutesUntilExpiration(minutesUntilExpiration);
		setSecondsUntilExpiration(secondsUntilExpiration);
	}

	/**
	 * Returns the days until expiration.
	 * 
	 * @return Numeric value that contains the days until expiration.
	 */
	public Integer getHoursUntilExpiration(){
		return this.hoursUntilExpiration;
	}

	/**
	 * Defines the days until expiration.
	 * 
	 * @param hoursUntilExpiration Numeric value that contains the days until expiration.
	 */
	public void setHoursUntilExpiration(Integer hoursUntilExpiration){
		this.hoursUntilExpiration = hoursUntilExpiration;
	}

	/**
	 * Returns the minutes until expiration.
	 * 
	 * @return Numeric value that contains the minutes until expiration.
	 */
	public Integer getMinutesUntilExpiration(){
		return this.minutesUntilExpiration;
	}

	/**
	 * Defines the minutes until expiration.
	 * 
	 * @param minutesUntilExpiration Numeric value that contains the minutes until expiration.
	 */
	public void setMinutesUntilExpiration(Integer minutesUntilExpiration){
		this.minutesUntilExpiration = minutesUntilExpiration;
	}

	/**
	 * Returns the seconds until expiration.
	 * 
	 * @return Numeric value that contains the seconds until expiration.
	 */
	public Integer getSecondsUntilExpiration(){
		return this.secondsUntilExpiration;
	}

	/**
	 * Defines the seconds until expiration.
	 * 
	 * @param secondsUntilExpiration Numeric value that contains the seconds until expiration.
	 */
	public void setSecondsUntilExpiration(Integer secondsUntilExpiration){
		this.secondsUntilExpiration = secondsUntilExpiration;
	}

	/**
	 * Returns the number of days until expiration.
	 * 
	 * @return Numeric value that contains the number of days until expiration.
	 */
	public Integer getDaysUntilExpiration(){
		return this.daysUntilExpiration;
	}

	/**
	 * Defines the number of days until expiration.
	 * 
	 * @param daysUntilExpiration Numeric value that contains the number of days
	 * until expiration.
	 */
	public void setDaysUntilExpiration(Integer daysUntilExpiration){
		this.daysUntilExpiration = daysUntilExpiration;
	}
}