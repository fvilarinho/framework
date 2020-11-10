package br.com.innovativethinking.framework.util.types;

import br.com.innovativethinking.framework.util.interfaces.IMetric;

/**
 * Class that defines the metrics of byte ranges.
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
public enum ByteMetricType implements IMetric{
	/**
	 * Byte metric.
	 */
	BYTE(1d, "byte"),

	/**
	 * Kilobyte metric.
	 */
	KILO_BYTE(BYTE.getValue() * 1024d, "Kb"),

	/**
	 * Megabyte metric.
	 */
	MEGA_BYTE(KILO_BYTE.getValue() * 1024d, "Mb"),

	/**
	 * Gigabyte metric.
	 */
	GIGA_BYTE(MEGA_BYTE.getValue() * 1024d, "Gb"),

	/**
	 * Constant for a Terabyte.
	 */
	TERA_BYTE(GIGA_BYTE.getValue() * 1024d, "Tb");

	private Double value = null;
	private String unit  = null;

	/**
	 * Constructor - Default metric.
	 */
	private ByteMetricType(){
	}

	/**
	 * Constructor - Defines the metric.
	 * 
	 * @param value Defines the metric value.
	 * @param unit Defines the metric unit.
	 */
	private ByteMetricType(Double value, String unit){
		this();
		
		setValue(value);
		setUnit(unit);
	}

	/**
	 * @see br.com.innovativethinking.framework.util.interfaces.IMetric#getValue()
	 */
	public Double getValue(){
		return this.value;
	}

	/**
	 * @see br.com.innovativethinking.framework.util.interfaces.IMetric#setValue(java.lang.Double)
	 */
	public void setValue(Double metricValue){
		this.value = metricValue;
	}

	/**
	 * @see br.com.innovativethinking.framework.util.interfaces.IMetric#getUnit()
	 */
	public String getUnit(){
		return this.unit;
	}

	/**
	 * @see br.com.innovativethinking.framework.util.interfaces.IMetric#setUnit(java.lang.String)
	 */
	public void setUnit(String metricUnit){
		this.unit = metricUnit;
	}
}