package br.com.concepting.framework.mail.constants;

import br.com.concepting.framework.mail.types.MailStorageType;
import br.com.concepting.framework.mail.types.MailTransportType;

/**
 * Class that defines the constants used in the manipulation of the mail
 * services.
 * 
 * @author fvilarinho
 * @version 3.0
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
public abstract class MailConstants{
	public static final String            FROM_ATTRIBUTE_ID      = "from";
	public static final String            STORAGE_ATTRIBUTE_ID   = "storage";
	public static final String            SUBJECT_ATTRIBUTE_ID   = "subject";
	public static final String            TO_ATTRIBUTE_ID        = "to";
	public static final String            TRANSPORT_ATTRIBUTE_ID = "transport";
	public static final String            DEFAULT_ID             = "mail";
	public static final MailStorageType   DEFAULT_STORAGE_TYPE   = MailStorageType.IMAPS;
	public static final Integer           DEFAULT_STORAGE_PORT   = 143;
	public static final MailTransportType DEFAULT_TRANSPORT_TYPE = MailTransportType.SMTP;
	public static final Integer           DEFAULT_TRANSPORT_PORT = 25;
}