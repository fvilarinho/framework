package br.com.concepting.framework.security.exceptions;

import br.com.concepting.framework.exceptions.ExpectedWarningException;

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
 * the Free Software Foundation, either version 3 of the License or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <a href="http://www.gnu.org/licenses"></a>.</pre>
 */
public class PasswordWillExpireException extends ExpectedWarningException{
    private static final long serialVersionUID = 5978487541406636762L;
    
    private final int daysUntilExpiration;
    private final int hoursUntilExpiration;
    private final int minutesUntilExpiration;
    private final int secondsUntilExpiration;
    
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

        this.daysUntilExpiration = daysUntilExpiration;
        this.hoursUntilExpiration = hoursUntilExpiration;
        this.minutesUntilExpiration = minutesUntilExpiration;
        this.secondsUntilExpiration = secondsUntilExpiration;
    }
    
    /**
     * Returns the days until expiration.
     *
     * @return Numeric value that contains the days until expiration.
     */
    public int getHoursUntilExpiration(){
        return this.hoursUntilExpiration;
    }

    /**
     * Returns the minutes until expiration.
     *
     * @return Numeric value that contains the minutes until expiration.
     */
    private int getMinutesUntilExpiration(){
        return this.minutesUntilExpiration;
    }

    /**
     * Returns the seconds until expiration.
     *
     * @return Numeric value that contains the seconds until expiration.
     */
    public int getSecondsUntilExpiration(){
        return this.secondsUntilExpiration;
    }

    /**
     * Returns the number of days until expiration.
     *
     * @return Numeric value that contains the number of days until expiration.
     */
    public int getDaysUntilExpiration(){
        return this.daysUntilExpiration;
    }
}