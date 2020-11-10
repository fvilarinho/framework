package br.com.innovativethinking.framework.util.helpers;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Iterator;

import br.com.innovativethinking.framework.util.PropertyUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JRRewindableDataSource;

/**
 * Class that defines a data source to be used in reports (JasperReports).
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
public class ReportDataSource implements JRRewindableDataSource{
	private Collection<?> data        = null;
	private Iterator<?>   iterator    = null;
	private Object        currentItem = null;

	/**
	 * Constructor - Initialize the data source.
	 *
	 * @param data Instance that contains the data.
	 */
	public ReportDataSource(Collection<?> data){
		super();

		this.data = data;

		moveFirst();
	}

	/**
	 * @see net.sf.jasperreports.engine.JRDataSource#next()
	 */
	public boolean next(){
		boolean hasNext = false;

		if(this.iterator != null){
			hasNext = this.iterator.hasNext();

			if(hasNext)
				this.currentItem = this.iterator.next();
		}

		return hasNext;
	}

	/**
	 * @see net.sf.jasperreports.engine.JRDataSource#getFieldValue(net.sf.jasperreports.engine.JRField)
	 */
	public Object getFieldValue(JRField field) throws JRException{
		if(this.currentItem != null && field != null){
			String fieldName = field.getName();

			try{
				Class<?> clazz = PropertyUtil.getPropertyType(this.currentItem, fieldName);
				Object fieldValue = PropertyUtil.getValue(this.currentItem, fieldName);

				if(PropertyUtil.isCollection(clazz))
					return new ReportDataSource((Collection<?>)fieldValue);

				return fieldValue;
			}
			catch(IllegalAccessException | InvocationTargetException | NoSuchMethodException e){
				throw new JRException(e);
			}
		}

		return null;
	}

	/**
	 * @see net.sf.jasperreports.engine.JRRewindableDataSource#moveFirst()
	 */
	public void moveFirst(){
		if(this.data != null && this.data.size() > 0)
			this.iterator = this.data.iterator();
	}
}