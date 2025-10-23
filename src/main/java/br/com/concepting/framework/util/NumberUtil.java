package br.com.concepting.framework.util;

import br.com.concepting.framework.constants.Constants;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.Map;

/**
 * Class responsible to manipulate numbers.
 *
 * @author fvilarinho
 * @since 1.0.0
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
 * along with this program.  If not, see <a href="http://www.gnu.org/licenses"></a>.</pre>
 */
public class NumberUtil{
    private static final SecureRandom random = new SecureRandom();
    private static final Map<Class<?>, Number[]> numberRange;
    
    static{
        numberRange = PropertyUtil.instantiate(Constants.DEFAULT_MAP_CLASS);

        if(numberRange != null){
            numberRange.put(Byte.class, new Number[]{Byte.MIN_VALUE, Byte.MAX_VALUE});
            numberRange.put(Short.class, new Number[]{Short.MIN_VALUE, Short.MAX_VALUE});
            numberRange.put(Integer.class, new Number[]{Integer.MIN_VALUE, Integer.MAX_VALUE});
            numberRange.put(Long.class, new Number[]{Long.MIN_VALUE, Long.MAX_VALUE});
            numberRange.put(BigInteger.class, new Number[]{Long.MIN_VALUE, Long.MAX_VALUE});
            numberRange.put(Float.class, new Number[]{Float.MIN_VALUE, Float.MAX_VALUE});
            numberRange.put(Double.class, new Number[]{Double.MIN_VALUE, Double.MAX_VALUE});
            numberRange.put(BigDecimal.class, new Number[]{Double.MIN_VALUE, Double.MAX_VALUE});
        }
    }

    /**
     * Returns the default number precision.
     *
     * @param clazz Class that defines the number.
     * @return Numeric value that contains the precision.
     */
    public static int getDefaultPrecision(Class<? extends Number> clazz){
        if(PropertyUtil.isFloat(clazz) || PropertyUtil.isDouble(clazz) || PropertyUtil.isBigDecimal(clazz) || PropertyUtil.isCurrency(clazz))
            return Constants.DEFAULT_DECIMAL_PRECISION;

        return 0;
    }

    /**
     * Returns the default number precision.
     *
     * @param value Instance that defines the number.
     * @return Numeric value that contains the precision.
     */
    public static int getDefaultPrecision(Object value){
        if(PropertyUtil.isFloat(value) || PropertyUtil.isDouble(value) || PropertyUtil.isBigDecimal(value) || PropertyUtil.isCurrency(value))
            return Constants.DEFAULT_DECIMAL_PRECISION;

        return 0;
    }

    /**
     * Returns the default formatting symbols of a numeric value.
     *
     * @return Instance that contains the formatting symbols.
     */
    public static DecimalFormatSymbols getDefaultFormatSymbols(){
        return getFormatSymbols(null);
    }
    
    /**
     * Returns the formatting symbols of a numeric value.
     *
     * @param language Instance that contains the language.
     * @return Instance that contains the formatting symbols.
     */
    public static DecimalFormatSymbols getFormatSymbols(Locale language){
        if(language == null)
            language = LanguageUtil.getDefaultLanguage();
        
        return new DecimalFormatSymbols(language);
    }
    
    /**
     * Converts a string into a float value.
     *
     * @param value String that contains the value.
     * @param language Instance that contains the properties of the language.
     * @return Instance that contains the float value.
     * @throws ParseException Occurs when was not possible to execute the
     * operation.
     */
    public static float parseFloat(String value, Locale language) throws ParseException{
        if(value != null && !value.isEmpty())
            return parse(Float.class, value, language);
        
        return 0F;
    }
    
    /**
     * Converts a string into a float value.
     *
     * @param value String that contains the value.
     * @return Instance that contains the float value.
     * @throws ParseException Occurs when was not possible to execute the
     * operation.
     */
    public static float parseFloat(String value) throws ParseException{
        return parseFloat(value, LanguageUtil.getDefaultLanguage());
    }
    
    /**
     * Converts a string into a double value.
     *
     * @param value String that contains the value.
     * @param language Instance that contains the properties of the language.
     * @return Instance that contains the double value.
     * @throws ParseException Occurs when was not possible to execute the
     * operation.
     */
    public static double parseDouble(String value, Locale language) throws ParseException{
        if(value != null && !value.isEmpty())
            return parse(Double.class, value, language);
        
        return 0D;
    }
    
    /**
     * Converts a string into a double value.
     *
     * @param value String that contains the value.
     * @return Instance that contains the double value.
     * @throws ParseException Occurs when was not possible to execute the
     * operation.
     */
    public static double parseDouble(String value) throws ParseException{
        return parseDouble(value, LanguageUtil.getDefaultLanguage());    }
    
    /**
     * Converts a string into a big decimal value.
     *
     * @param value String that contains the value.
     * @param language Instance that contains the properties of the language.
     * @return Instance that contains the big decimal value.
     * @throws ParseException Occurs when was not possible to execute the
     * operation.
     */
    public static BigDecimal parseBigDecimal(String value, Locale language) throws ParseException{
        if(value != null && !value.isEmpty())
            return parse(BigDecimal.class, value, language);
        
        return BigDecimal.ZERO;
    }
    
    /**
     * Converts a string into a big decimal value.
     *
     * @param value String that contains the value.
     * @return Instance that contains the big decimal value.
     * @throws ParseException Occurs when was not possible to execute the
     * operation.
     */
    public static BigDecimal parseBigDecimal(String value) throws ParseException{
        return parseBigDecimal(value, LanguageUtil.getDefaultLanguage());
    }

    /**
     * Converts a string into a short value.
     *
     * @param value String that contains the value.
     * @return Instance that contains the short value.
     * @throws ParseException Occurs when was not possible to execute the
     * operation.
     */
    public static short parseShort(String value) throws ParseException{
        Number result = parse(Short.class, value, LanguageUtil.getDefaultLanguage());

        if(result == null)
            return (short)0;

        return (short)result;
    }
    
    /**
     * Converts a string into a byte value.
     *
     * @param value String that contains the value.
     * @return Instance that contains the byte value.
     * @throws ParseException Occurs when was not possible to execute the
     * operation.
     */
    public static byte parseByte(String value) throws ParseException{
        Number result = parse(Byte.class, value, LanguageUtil.getDefaultLanguage());

        if(result == null)
            return (byte)0;

        return (byte)result;
    }

    /**
     * Converts a string into a long value.
     *
     * @param value String that contains the value.
     * @return Instance that contains the long value.
     * @throws ParseException Occurs when was not possible to execute the
     * operation.
     */
    public static long parseLong(String value) throws ParseException{
        Long result = parse(Long.class, value, LanguageUtil.getDefaultLanguage());

        if(result == null)
            return (long)0;

        return result;
    }

    /**
     * Converts a string into a big integer value.
     *
     * @param value String that contains the value.
     * @return Instance that contains the big integer value.
     * @throws ParseException Occurs when was not possible to execute the
     * operation.
     */
    public static BigInteger parseBigInteger(String value) throws ParseException{
        BigInteger result = parse(BigInteger.class, value, LanguageUtil.getDefaultLanguage());

        if(result == null)
            return BigInteger.ZERO;

        return result;
    }

    /**
     * Converts a string into an integer value.
     *
     * @param value String that contains the value.
     * @return Instance that contains the integer value.
     * @throws ParseException Occurs when was not possible to execute the
     * operation.
     */
    public static int parseInt(String value) throws ParseException{
        Integer result = parse(Integer.class, value, LanguageUtil.getDefaultLanguage());

        if(result == null)
            return (int)0;

        return result;
    }

    /**
     * Converts a string into a numeric value.
     *
     * @param <N> Class that defines the numeric value.
     * @param clazz Class that defines the numeric value.
     * @param value String that contains the numeric value.
     * @return String that contains the formatted numeric value.
     * @throws ParseException Occurs when was not possible to execute the
     * operation.
     */
    public static <N extends Number> N parse(Class<?> clazz, String value) throws ParseException{
        if(clazz != null && value != null && !value.isEmpty())
            return parse(clazz, value, LanguageUtil.getDefaultLanguage());

        return null;
    }

    /**
     * Converts a string into a numeric value.
     *
     * @param <N>      Class that defines the numeric value.
     * @param clazz    Class that defines the numeric value.
     * @param value    String that contains the numeric value.
     * @param language Instance that contains the language that will be used.
     * @return Number that contains the formatted numeric value.
     * @throws ParseException Occurs when was not possible to execute the
     *                        operation.
     */
    public static <N extends Number> N parse(Class<?> clazz, String value, Locale language) throws ParseException{
        if(clazz != null && value != null && !value.isEmpty())
            return parse(clazz, value, false, getDefaultPrecision(clazz), language);
        
        return null;
    }
    
    /**
     * Converts a string into a numeric value.
     *
     * @param <N> Class that defines the numeric value.
     * @param clazz Class that defines the numeric value.
     * @param value String that contains the numeric value.
     * @param useGroupSeparator Indicates if the number group separator should
     * be considered.
     * @param precision Numeric value that contains the decimal precision.
     * @return String that contains the formatted numeric value.
     * @throws ParseException Occurs when was not possible to execute the
     * operation.
     */
    public static <N extends Number> N parse(Class<?> clazz, String value, boolean useGroupSeparator, int precision) throws ParseException{
        if(clazz != null && value != null && !value.isEmpty())
            return parse(clazz, value, useGroupSeparator, precision, LanguageUtil.getDefaultLanguage());
        
        return null;
    }
    
    /**
     * Converts a string into a numeric value.
     *
     * @param <N> Class that defines the numeric value.
     * @param clazz Class that defines the numeric value.
     * @param value String that contains the numeric value.
     * @param useGroupSeparator Indicates if the number group separator should
     * be considered.
     * @param precision Numeric value that contains the decimal precision.
     * @param language Instance that contains the language that will be used.
     * @return String that contains the formatted numeric value.
     * @throws ParseException Occurs when was not possible to execute the
     * operation.
     */
    @SuppressWarnings("unchecked")
    public static <N extends Number> N parse(Class<?> clazz, String value, boolean useGroupSeparator, int precision, Locale language) throws ParseException{
        if(clazz != null && value != null && !value.isEmpty()){
            NumberFormat parser;
            
            if(PropertyUtil.isCurrency(clazz))
                parser = NumberFormat.getCurrencyInstance(language);
            else
                parser = NumberFormat.getInstance(language);

            parser.setGroupingUsed(useGroupSeparator);
            
            if(precision > 0){
                parser.setMaximumFractionDigits(precision);
                parser.setMinimumFractionDigits(precision);
            }

            try {
                return (N) PropertyUtil.convertTo(parser.parse(value), clazz);
            }
            catch(IllegalArgumentException e){
                throw new ParseException(e.getLocalizedMessage(), 0);
            }
        }
        
        return null;
    }
    
    /**
     * Formats a numeric value.
     *
     * @param value Instance that contains the numeric value.
     * @return String that contains the numeric value formatado.
     */
    public static String format(Number value){
        if(value != null)
            return format(value, LanguageUtil.getDefaultLanguage());
        
        return null;
    }
    
    /**
     * Formats a numeric value.
     *
     * @param value Instance that contains the numeric value.
     * @param language Instance that contains the language that will be used.
     * @return String that contains the formatted numeric value.
     */
    public static String format(Number value, Locale language){
        if(value != null)
            return format(value, false, getDefaultPrecision(value), language);
        
        return null;
    }
    
    /**
     * Formats a numeric value.
     *
     * @param value Instance that contains the numeric value.
     * @param precision Numeric value that contains the decimal precision.
     * @return String that contains the formatted numeric value.
     */
    public static String format(Number value, int precision){
        if(value != null)
            return format(value, precision, LanguageUtil.getDefaultLanguage());
        
        return null;
    }
    
    /**
     * Formats a numeric value.
     *
     * @param value Instance that contains the numeric value.
     * @param precision Numeric value that contains the decimal precision.
     * @param language Instance that contains the language that will be used.
     * @return String that contains the formatted numeric value.
     */
    public static String format(Number value, int precision, Locale language){
        if(value != null)
            return format(value, false, precision, language);
        
        return null;
    }
    
    /**
     * Formats a numeric value.
     *
     * @param value Instance that contains the numeric value.
     * @param useGroupSeparator Indicates if the number group separator should
     * be considered.
     * @param precision Numeric value that contains the decimal precision.
     * @param language Instance that contains the language that will be used.
     * @return String that contains the formatted numeric value.
     */
    public static String format(Number value, boolean useGroupSeparator, int precision, Locale language){
        if(value == null)
            return null;
        
        NumberFormat formatter;
        
        if(PropertyUtil.isCurrency(value))
            formatter = NumberFormat.getCurrencyInstance(language);
        else
            formatter = NumberFormat.getInstance(language);

        formatter.setGroupingUsed(useGroupSeparator);
        
        if(precision == 0)
            precision = getDefaultPrecision(value);
        
        if(precision > 0){
            formatter.setMaximumFractionDigits(precision);
            formatter.setMinimumFractionDigits(precision);
        }
        
        try{
            return formatter.format(value);
        }
        catch(IllegalArgumentException e){
            return value.toString();
        }
    }
    
    /**
     * Formats a numeric value.
     *
     * @param value Instance that contains the numeric value.
     * @param pattern String that contains the pattern.
     * @return String that contains the formatted numeric value.
     */
    public static String format(Number value, String pattern){
        if(value != null && pattern != null && !pattern.isEmpty())
            return format(value, pattern, LanguageUtil.getDefaultLanguage());
        
        return null;
    }
    
    /**
     * Formats a numeric value.
     *
     * @param value Instance that contains the numeric value.
     * @param pattern String that contains the pattern.
     * @param language Instance that contains the language that will be used.
     * @return String that contains the formatted numeric value.
     */
    public static String format(Number value, String pattern, Locale language){
        if(value != null && pattern != null && !pattern.isEmpty()){
            if(language == null)
                language = LanguageUtil.getDefaultLanguage();
            
            DecimalFormatSymbols symbols = new DecimalFormatSymbols(language);
            DecimalFormat formatter = new DecimalFormat(pattern);
            
            formatter.setDecimalFormatSymbols(symbols);
            
            return formatter.format(value);
        }
        
        return null;
    }
    
    /**
     * Returns the ranges (minimum and maximum) of a numeric type.
     *
     * @param <N> Class that defines the number value.
     * @param clazz Class that defines the number value.
     * @return Array that contains the numeric value.
     */
    @SuppressWarnings("unchecked")
    public static <N extends Number> N[] getRange(Class<?> clazz){
        N[] range = null;
        
        if(clazz != null){
            if(clazz.equals(byte.class))
                clazz = Byte.class;
            else if(clazz.equals(short.class))
                clazz = Short.class;
            else if(clazz.equals(int.class))
                clazz = Integer.class;
            else if(clazz.equals(long.class))
                clazz = Long.class;
            else if(clazz.equals(float.class))
                clazz = Float.class;
            else if(clazz.equals(double.class))
                clazz = Double.class;

            range = (N[]) numberRange.get(clazz);
        }
        
        return range;
    }
    
    /**
     * Returns the minimum value of a numeric type.
     *
     * @param <N> Class that defines the numeric value.
     * @param clazz Class that defines the numeric value.
     * @return Instance that contains the minimum value of a numeric type.
     */
    public static <N extends Number> N getMinimumRange(Class<N> clazz){
        if(clazz != null){
            N[] range = getRange(clazz);
            
            if(range == null)
                range = getRange(Double.class);
            
            return range[0];
        }
        
        return null;
    }
    
    /**
     * Returns the maximum value of a numeric type.
     *
     * @param <N> Class that defines the numeric value.
     * @param clazz Class that defines the numeric value.
     * @return Instance that contains the maximum value of a numeric type.
     */
    public static <N extends Number> N getMaximumRange(Class<N> clazz){
        if(clazz != null){
            N[] range = getRange(clazz);
            
            if(range == null)
                range = getRange(Double.class);
            
            return range[1];
        }
        
        return null;
    }
    
    /**
     * Converts a numeric value into a hexadecimal string.
     *
     * @param value Instance that contains the numeric value.
     * @return String that contains the hexadecimal value.
     */
    public static String toHexadecimal(long value){
        return Long.toHexString(value);
    }
    
    /**
     * Converts a hexadecimal string into a numeric value.
     *
     * @param value String that contains the hexadecimal value.
     * @return Instance that contains the numeric value.
     */
    public static long fromHexadecimal(String value){
        if(value != null && !value.isEmpty()){
            try{
                return Long.parseLong(value, 16);
            }
            catch(IllegalArgumentException ignored){
            }
        }
        
        return 0L;
    }
    
    /**
     * Subtracts two numbers.
     *
     * @param <N> Class that defines the numeric value.
     * @param value1 Numeric value 1.
     * @param value2 Numeric value 2.
     * @return Final numeric value.
     */
    @SuppressWarnings("unchecked")
    public static <N extends Number> N subtract(N value1, N value2){
        if(value1 != null && value2 != null){
            if(PropertyUtil.isInteger(value1) || PropertyUtil.isInteger(value2))
                return (N) Integer.valueOf(value1.intValue() - value2.intValue());
            else if(PropertyUtil.isLong(value1) || PropertyUtil.isLong(value2))
                return (N) Long.valueOf(value1.longValue() - value2.longValue());
            else if(PropertyUtil.isByte(value1) || PropertyUtil.isByte(value2))
                return (N) Byte.valueOf((byte) (value1.byteValue() - value2.byteValue()));
            else if(PropertyUtil.isShort(value1) || PropertyUtil.isShort(value2))
                return (N) Short.valueOf((short) (value1.shortValue() - value2.shortValue()));
            else if(PropertyUtil.isDouble(value1) || PropertyUtil.isDouble(value2))
                return (N) Double.valueOf(value1.doubleValue() - value2.doubleValue());
            else if(PropertyUtil.isFloat(value1) || PropertyUtil.isFloat(value2))
                return (N) Float.valueOf(value1.floatValue() - value2.floatValue());
            else if(PropertyUtil.isBigDecimal(value1) || PropertyUtil.isBigDecimal(value2))
                return (N) ((BigDecimal) value1).subtract((BigDecimal) value2);
            else if(PropertyUtil.isBigInteger(value1) || PropertyUtil.isBigInteger(value2))
                return (N) ((BigInteger) value1).subtract((BigInteger) value2);
        }
        
        return null;
    }
    
    /**
     * Sums two numbers.
     *
     * @param <N> Class that defines the numeric value.
     * @param value1 Numeric value 1.
     * @param value2 Numeric value 2.
     * @return Final numeric value.
     */
    @SuppressWarnings("unchecked")
    public static <N extends Number> N add(N value1, N value2){
        if(value1 != null && value2 != null){
            if(PropertyUtil.isInteger(value1) || PropertyUtil.isInteger(value2))
                return (N) Integer.valueOf(value1.intValue() + value2.intValue());
            else if(PropertyUtil.isLong(value1) || PropertyUtil.isLong(value2))
                return (N) Long.valueOf(value1.longValue() + value2.longValue());
            else if(PropertyUtil.isByte(value1) || PropertyUtil.isByte(value2))
                return (N) Byte.valueOf((byte) (value1.byteValue() + value2.byteValue()));
            else if(PropertyUtil.isShort(value1) || PropertyUtil.isShort(value2))
                return (N) Short.valueOf((short) (value1.shortValue() + value2.shortValue()));
            else if(PropertyUtil.isDouble(value1) || PropertyUtil.isDouble(value2))
                return (N) Double.valueOf(value1.doubleValue() + value2.doubleValue());
            else if(PropertyUtil.isFloat(value1) || PropertyUtil.isFloat(value2))
                return (N) Float.valueOf(value1.floatValue() + value2.floatValue());
            else if(PropertyUtil.isBigDecimal(value1) || PropertyUtil.isBigDecimal(value2))
                return (N) ((BigDecimal) value1).add((BigDecimal) value2);
            else if(PropertyUtil.isBigInteger(value1) || PropertyUtil.isBigInteger(value2))
                return (N) ((BigInteger) value1).add((BigInteger) value2);
        }
        
        return null;
    }
    
    /**
     * Multiplies two numbers.
     *
     * @param <N> Class that defines the numeric value.
     * @param value1 Numeric value 1.
     * @param value2 Numeric value 2.
     * @return Final numeric value.
     */
    @SuppressWarnings("unchecked")
    public static <N extends Number> N multiply(N value1, N value2){
        if(value1 != null && value2 != null){
            if(PropertyUtil.isInteger(value1) || PropertyUtil.isInteger(value2))
                return (N) Integer.valueOf(value1.intValue() * value2.intValue());
            else if(PropertyUtil.isLong(value1) || PropertyUtil.isLong(value2))
                return (N) Long.valueOf(value1.longValue() * value2.longValue());
            else if(PropertyUtil.isByte(value1) || PropertyUtil.isByte(value2))
                return (N) Byte.valueOf((byte) (value1.byteValue() * value2.byteValue()));
            else if(PropertyUtil.isShort(value1) || PropertyUtil.isShort(value2))
                return (N) Short.valueOf((short) (value1.shortValue() * value2.shortValue()));
            else if(PropertyUtil.isDouble(value1) || PropertyUtil.isDouble(value2))
                return (N) Double.valueOf(value1.doubleValue() * value2.doubleValue());
            else if(PropertyUtil.isFloat(value1) || PropertyUtil.isFloat(value2))
                return (N) Float.valueOf(value1.floatValue() * value2.floatValue());
            else if(PropertyUtil.isBigDecimal(value1) || PropertyUtil.isBigDecimal(value2))
                return (N) ((BigDecimal) value1).multiply((BigDecimal) value2);
            else if(PropertyUtil.isBigInteger(value1) || PropertyUtil.isBigInteger(value2))
                return (N) ((BigInteger) value1).multiply((BigInteger) value2);
        }
        
        return null;
    }
    
    /**
     * Divides two numbers.
     *
     * @param <N> Class that defines the numeric value.
     * @param value1 Numeric value 1.
     * @param value2 Numeric value 2.
     * @return Final numeric value.
     */
    @SuppressWarnings("unchecked")
    public static <N extends Number> N divide(N value1, N value2){
        if(value1 != null && value2 != null){
            if(PropertyUtil.isInteger(value1) || PropertyUtil.isInteger(value2))
                return (N) Integer.valueOf(value1.intValue() / value2.intValue());
            else if(PropertyUtil.isLong(value1) || PropertyUtil.isLong(value2))
                return (N) Long.valueOf(value1.longValue() / value2.longValue());
            else if(PropertyUtil.isByte(value1) || PropertyUtil.isByte(value2))
                return (N) Byte.valueOf((byte) (value1.byteValue() / value2.byteValue()));
            else if(PropertyUtil.isShort(value1) || PropertyUtil.isShort(value2))
                return (N) Short.valueOf((short) (value1.shortValue() / value2.shortValue()));
            else if(PropertyUtil.isDouble(value1) || PropertyUtil.isDouble(value2))
                return (N) Double.valueOf(value1.doubleValue() / value2.doubleValue());
            else if(PropertyUtil.isFloat(value1) || PropertyUtil.isFloat(value2))
                return (N) Float.valueOf(value1.floatValue() / value2.floatValue());
            else if(PropertyUtil.isBigDecimal(value1) || PropertyUtil.isBigDecimal(value2))
                return (N) ((BigDecimal) value1).divide((BigDecimal) value2);
            else if(PropertyUtil.isBigInteger(value1) || PropertyUtil.isBigInteger(value2))
                return (N) ((BigInteger) value1).divide((BigInteger) value2);
        }
        
        return null;
    }
    
    /**
     * Returns a random number.
     *
     * @param <N> Class that defines the number.
     * @param clazz Class that defines the number.
     * @return Instance that contains the random number.
     */
    @SuppressWarnings("unchecked")
    public static <N extends Number> N random(Class<N> clazz){
        if(PropertyUtil.isInteger(clazz))
            return (N) Integer.valueOf(random.nextInt());
        else if(PropertyUtil.isLong(clazz))
            return (N) Long.valueOf(random.nextLong());
        else if(PropertyUtil.isBigInteger(clazz))
            return (N) BigInteger.valueOf(random.nextLong());
        else if(PropertyUtil.isFloat(clazz))
            return (N) Float.valueOf(random.nextFloat());
        else if(PropertyUtil.isDouble(clazz))
            return (N) Double.valueOf(random.nextDouble());
        
        return null;
    }
}