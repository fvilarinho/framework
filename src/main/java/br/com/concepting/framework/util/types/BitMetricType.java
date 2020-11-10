package br.com.concepting.framework.util.types;

import br.com.concepting.framework.util.interfaces.IMetric;

/**
 * Class that defines the metrics of bit ranges.
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
public enum BitMetricType implements IMetric{
	/**
	 * Bit metric.
	 */
	BIT(1d, "bit"),

	/**
	 * Kilobit metric.
	 */
	KILO_BIT(BIT.getValue() * 1000d, "Kbit"),

	/**
	 * Megabit metric.
	 */
	MEGA_BIT(KILO_BIT.getValue() * 1000d, "Mbit"),

	/**
	 * Gigabit metric.
	 */
	GIGA_BIT(MEGA_BIT.getValue() * 1000d, "Gbit"),

	/**
	 * Terabit metric.
	 */
	TERA_BYTE(GIGA_BIT.getValue() * 1000d, "Tbit");

	private Double metricValue = null;
	private String metricUnit  = null;

	/**
	 * Constructor - Defines the metric.
	 * 
	 * @param value Defines the metric value.
	 * @param unit Defines the metric unit.
	 */
	private BitMetricType(Double value, String unit){
		setValue(value);
		setUnit(unit);
	}

	/**
	 * @see br.com.concepting.framework.util.interfaces.IMetric#getValue()
	 */
	public Double getValue(){
		return this.metricValue;
	}

	/**
	 * @see br.com.concepting.framework.util.interfaces.IMetric#setValue(java.lang.Double)
	 */
	public void setValue(Double metricValue){
		this.metricValue = metricValue;
	}

	/**
	 * @see br.com.concepting.framework.util.interfaces.IMetric#getUnit()
	 */
	public String getUnit(){
		return this.metricUnit;
	}

	/**
	 * @see br.com.concepting.framework.util.interfaces.IMetric#setUnit(java.lang.String)
	 */
	public void setUnit(String metricUnit){
		this.metricUnit = metricUnit;
	}
}