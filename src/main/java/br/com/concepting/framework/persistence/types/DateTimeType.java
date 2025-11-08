package br.com.concepting.framework.persistence.types;

import br.com.concepting.framework.util.helpers.DateTime;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.*;

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
public class DateTimeType implements UserType{
    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException{
        return disassemble(cached);
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException{
        if(value != null)
            return ((DateTime) value).clone();
        
        return null;
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException{
        return (DateTime) deepCopy(value);
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException{
        if(x == null && y == null)
            return true;
        
        if(x == null || y == null)
            return false;
        
        return x.equals(y);
    }

    @Override
    public int hashCode(Object value) throws HibernateException{
        if(value != null)
            return value.hashCode();
        
        return 0;
    }

    @Override
    public boolean isMutable(){
        return true;
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException{
        return disassemble(original);
    }

    @Override
    public Class<DateTime> returnedClass(){
        return DateTime.class;
    }

    @Override
    public int[] sqlTypes(){
        return new int[]{Types.TIMESTAMP};
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner) throws HibernateException, SQLException{
        Timestamp timestamp = rs.getTimestamp(names[0]);
        
        if(timestamp != null)
            return new DateTime(timestamp.getTime());
        
        return null;
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session) throws HibernateException, SQLException{
        if(value == null)
            st.setNull(index, Types.TIMESTAMP);
        else
            st.setTimestamp(index, new Timestamp(((DateTime) value).getTime()));
    }
}