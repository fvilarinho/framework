package br.com.concepting.framework.service.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Class that defines the service annotation.
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
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Service{
    /**
     * Indicates if the service is a daemon.
     *
     * @return True/False.
     */
    boolean isDaemon() default false;
    
    /**
     * Indicates if the service is a job.
     *
     * @return True/False.
     */
    boolean isJob() default false;
    
    /**
     * Indicates the polling time (minutes) of the daemon.
     *
     * @return Numeric value that contains the polling time (minutes).
     */
    int pollingTime() default -1;
    
    /**
     * Defines the start time of the daemon.
     *
     * @return String that contains the start time.
     */
    String startTime() default "";
    
    /**
     * Defines the identifier of the application context resources.
     *
     * @return String that contains the identifier.
     */
    String contextResourcesId() default "";

    /**
     * Defines the path for APIs calls.
     *
     * @return String that contains the path.
     */
    String url() default "";

    boolean isWeb() default false;
}