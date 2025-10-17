package br.com.concepting.framework.service.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Class that defines the annotation of a service transaction parameter.
 *
 * @author fvilarinho
 * @since 3.2.0
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
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface TransactionParam {
    /**
     * Defines the parameter's name.
     *
     * @return String that contains the name.
     */
    String name() default "";

    /**
     * Indicates if the parameters is on the request path.
     *
     * @return True/False.
     */
    boolean fromPath() default false;

    /**
     * Indicates if the parameters is on the request body.
     *
     * @return True/False.
     */
    boolean fromBody() default false;
}