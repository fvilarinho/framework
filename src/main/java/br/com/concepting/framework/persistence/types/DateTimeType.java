package br.com.concepting.framework.persistence.types;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import br.com.concepting.framework.util.helpers.DateTime;

/**
 * Class that defines the type of the date/time mapping.
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
public class DateTimeType implements UserType{
	/**
	 * @see org.hibernate.usertype.UserType#assemble(java.io.Serializable, java.lang.Object)
	 */
	public Object assemble(Serializable cached, Object owner) throws HibernateException{
		return disassemble(cached);
	}

	/**
	 * @see org.hibernate.usertype.UserType#deepCopy(java.lang.Object)
	 */
	public Object deepCopy(Object value) throws HibernateException{
		if(value != null)
			return ((DateTime)value).clone();

		return null;
	}

	/**
	 * @see org.hibernate.usertype.UserType#disassemble(java.lang.Object)
	 */
	public Serializable disassemble(Object value) throws HibernateException{
		return (DateTime)deepCopy(value);
	}

	/**
	 * @see org.hibernate.usertype.UserType#equals(java.lang.Object, java.lang.Object)
	 */
	public boolean equals(Object x, Object y) throws HibernateException{
		if(x == null && y == null)
			return true;

		if(x == null || y == null)
			return false;

		return x.equals(y);
	}

	/**
	 * @see org.hibernate.usertype.UserType#hashCode(java.lang.Object)
	 */
	public int hashCode(Object value) throws HibernateException{
		if(value != null)
			return value.hashCode();

		return 0;
	}

	/**
	 * @see org.hibernate.usertype.UserType#isMutable()
	 */
	public boolean isMutable(){
		return true;
	}

	/**
	 * @see org.hibernate.usertype.UserType#replace(java.lang.Object, java.lang.Object, java.lang.Object)
	 */
	public Object replace(Object original, Object target, Object owner) throws HibernateException{
		return disassemble(original);
	}

	/**
	 * @see org.hibernate.usertype.UserType#returnedClass()
	 */
	public Class<DateTime> returnedClass(){
		return DateTime.class;
	}

	/**
	 * @see org.hibernate.usertype.UserType#sqlTypes()
	 */
	public int[] sqlTypes(){
		return new int[]{Types.TIMESTAMP};
	}

	/**
	 * @see org.hibernate.usertype.UserType#nullSafeGet(java.sql.ResultSet, java.lang.String[], org.hibernate.engine.spi.SharedSessionContractImplementor, java.lang.Object)
	 */
	public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner) throws HibernateException, SQLException{
		Timestamp timestamp = rs.getTimestamp(names[0]);

		if(timestamp != null)
			return new DateTime(timestamp.getTime());

		return null;
	}

	/**
	 * @see org.hibernate.usertype.UserType#nullSafeSet(java.sql.PreparedStatement, java.lang.Object, int, org.hibernate.engine.spi.SharedSessionContractImplementor)
	 */
	public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session) throws HibernateException, SQLException {
		if(value == null)
			st.setNull(index, Types.TIMESTAMP);
		else
			st.setTimestamp(index, new Timestamp(((DateTime)value).getTime()));
	}
}