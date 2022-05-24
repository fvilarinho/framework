package br.com.concepting.framework.util.types;

import br.com.concepting.framework.util.interfaces.IMetric;

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
    BYTE(1D, "byte"),
    
    /**
     * Kilobyte metric.
     */
    KILO_BYTE(BYTE.getValue() * 1024D, "Kb"),
    
    /**
     * Megabyte metric.
     */
    MEGA_BYTE(KILO_BYTE.getValue() * 1024D, "Mb"),
    
    /**
     * Gigabyte metric.
     */
    GIGA_BYTE(MEGA_BYTE.getValue() * 1024D, "Gb"),
    
    /**
     * Constant for a Terabyte.
     */
    TERA_BYTE(GIGA_BYTE.getValue() * 1024D, "Tb");
    
    private double value;
    private String unit;

    /**
     * Constructor - Defines the metric.
     *
     * @param value Defines the metric value.
     * @param unit Defines the metric unit.
     */
    ByteMetricType(double value, String unit){
        setValue(value);
        setUnit(unit);
    }

    @Override
    public double getValue(){
        return this.value;
    }

    @Override
    public void setValue(double metricValue){
        this.value = metricValue;
    }

    @Override
    public String getUnit(){
        return this.unit;
    }
    
    @Override
    public void setUnit(String metricUnit){
        this.unit = metricUnit;
    }
}