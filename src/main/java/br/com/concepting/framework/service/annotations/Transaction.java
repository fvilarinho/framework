package br.com.concepting.framework.service.annotations;

import br.com.concepting.framework.util.types.ContentType;
import br.com.concepting.framework.util.types.MethodType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Class that defines the service transaction annotation.
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
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Transaction{
    /**
     * Defines the transaction timeout
     *
     * @return Numeric value that contains the timeout.
     */
    int timeout() default -1;
    
    /**
     * Defines for which caught exceptions, the transaction should be rolled
     * back.
     *
     * @return List that contains the caught exceptions.
     */
    Class<? extends Throwable>[] rollbackFor() default Throwable.class;

    /**
     * Defines the path for API calls.
     *
     * @return String that contains the path.
     */
    String url() default "";

    /**
     * Defines the method accepted for API calls.
     *
     * @return Instance that contains the method type.
     */
    MethodType type() default MethodType.NONE;

    /**
     * Defines the mime-type accepted for API calls.
     *
     * @return Instance that contains the mime-type type.
     */
    ContentType[] consumes() default {ContentType.NONE, ContentType.JSON};

    /**
     * Defines the mime-type of the API calling response.
     *
     * @return Instance that contains the mime-type type.
     */
    ContentType[] produces() default {ContentType.NONE, ContentType.JSON};
}