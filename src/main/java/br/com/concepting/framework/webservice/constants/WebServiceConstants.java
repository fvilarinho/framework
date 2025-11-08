package br.com.concepting.framework.webservice.constants;

/**
 * Class that defines the constants used in the manipulation of the web services.
 *
 * @author fvilarinho
 * @version 3.7.0
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
 * along with this program.  If not, see <a href="https://www.gnu.org/licenses">...</a>.</pre>
 */
public final class WebServiceConstants{
    public static final String METHOD_ATTRIBUTE_ID = "method";
    public static final String URL_ATTRIBUTE_ID = "url";
    public static final String TOKEN_ATTRIBUTE_ID = "token";
    public static final String USERNAME_ATTRIBUTE_ID = "userName";
    public static final String PASSWORD_ATTRIBUTE_ID = "password";
    public static final String ESCAPE_ATTRIBUTE_ID = "escape";
    public static final String DATA_ATTRIBUTE_ID = "data";
    public static final String HEADERS_ATTRIBUTE_ID = "headers";
    public static final String DEFAULT_ID = "webService";
    public static final String DEFAULT_URL_ID = "/webServices";
    public static final String DEFAULT_URL_PATTERN = DEFAULT_URL_ID.concat("/*");
    public static final int DEFAULT_TIMEOUT = 60;
}