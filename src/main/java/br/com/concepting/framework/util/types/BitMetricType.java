package br.com.concepting.framework.util.types;

import br.com.concepting.framework.util.interfaces.IMetric;

import javax.validation.constraints.NotNull;

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
    BIT(1D, "bit"),
    
    /**
     * Kilobit metric.
     */
    KILO_BIT(BIT.getValue() * 1000D, "Kbit"),
    
    /**
     * Megabit metric.
     */
    MEGA_BIT(KILO_BIT.getValue() * 1000D, "Mbit"),
    
    /**
     * Gigabit metric.
     */
    GIGA_BIT(MEGA_BIT.getValue() * 1000D, "Gbit"),
    
    /**
     * Terabit metric.
     */
    TERA_BYTE(GIGA_BIT.getValue() * 1000D, "Tbit");
    
    private double metricValue;
    private String metricUnit;
    
    /**
     * Constructor - Defines the metric.
     *
     * @param value Defines the metric value.
     * @param unit Defines the metric unit.
     */
    BitMetricType(double value, String unit){
        setValue(value);
        setUnit(unit);
    }

    @Override
    public double getValue(){
        return this.metricValue;
    }

    @Override
    public void setValue(double metricValue){
        this.metricValue = metricValue;
    }

    @Override
    public String getUnit(){
        return this.metricUnit;
    }

    @Override
    public void setUnit(String metricUnit){
        this.metricUnit = metricUnit;
    }
}