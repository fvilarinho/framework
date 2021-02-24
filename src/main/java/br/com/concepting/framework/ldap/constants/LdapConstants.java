package br.com.concepting.framework.ldap.constants;

/**
 * Class that defines the constants used in the manipulation of the LDAP
 * service.
 * 
 * @author fvilarinho
 * @since 3.0.0
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
@SuppressWarnings("javadoc")
public abstract class LdapConstants{
	public static final String  BASE_DN_ATTRIBUTE_ID        = "baseDn";
	public static final String  USER_DN_ATTRIBUTE_ID        = "userDn";
	public static final String  TIMEOUT_ATTRIBUTE_ID        = "com.sun.jndi.ldap.read.timeout";
	public static final String  DEFAULT_AUTHENTICATION_TYPE = "simple";
	public static final String  DEFAULT_ID                  = "ldap";  
	public static final Integer DEFAULT_PORT                = 389;
	public static final Long    DEFAULT_TIMEOUT             = 60000l;
}