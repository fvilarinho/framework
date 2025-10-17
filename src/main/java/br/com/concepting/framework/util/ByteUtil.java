package br.com.concepting.framework.util;

import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.util.types.BitMetricType;
import br.com.concepting.framework.util.types.ByteMetricType;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Base64;
import java.util.Locale;

/**
 * Class responsible to manipulate byte arrays.
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
public class ByteUtil{
    /**
     * Converts a byte array into a Hexadecimal string.
     *
     * @param values Byte array that contains the data.
     * @return String that was converted to Hexadecimal.
     */
    public static String toHexadecimal(byte[] values){
        if(values == null)
            return StringUtils.EMPTY;

        return new String(Hex.encodeHex(values));
    }
    
    /**
     * Converts a Hexadecimal string into a byte array.
     *
     * @param value String that contains the value.
     * @return Byte array converted from Hexadecimal.
     * @throws DecoderException Occurs when was not possible to
     * execute the operation.
     */
    public static byte[] fromHexadecimal(String value) throws DecoderException{
        if(value == null)
            return null;

        return Hex.decodeHex(value);
    }
    
    /**
     * Converts a byte array into a Base64 string.
     *
     * @param value Byte array that contains the data.
     * @return String that was converted to Base64.
     * @throws UnsupportedEncodingException Occurs when was not possible to
     * execute the operation.
     */
    public static String toBase64(byte[] value) throws UnsupportedEncodingException{
        if(value == null)
            return StringUtils.EMPTY;

        return new String(Base64.getMimeEncoder().encode(value), Constants.DEFAULT_UNICODE_ENCODING);
    }
    
    /**
     * Converts a Base64 string into a byte array.
     *
     * @param value String that contains the value.
     * @return Byte array converted from Base64.
     * @throws UnsupportedEncodingException Occurs when was not possible to
     * execute the operation.
     */
    public static byte[] fromBase64(String value) throws UnsupportedEncodingException{
        if(value == null)
            return null;

        return Base64.getMimeDecoder().decode(value.getBytes(Constants.DEFAULT_UNICODE_ENCODING));
    }
    
    /**
     * Reads the binary data from a stream.
     *
     * @param stream Instance that contains the stream.
     * @return Byte array that contains the data.
     * @throws IOException Occurs when was not possible to read the data.
     */
    public static byte[] fromBinaryStream( InputStream stream) throws IOException{
        byte[] buffer = new byte[Constants.DEFAULT_BUFFER_SIZE];
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int length;

        while((length = stream.read(buffer)) > 0)
            out.write(buffer, 0, length);

        out.close();
        stream.close();

        return out.toByteArray();
    }
    
    /**
     * Reads the text data from a stream.
     *
     * @param stream Instance that contains the stream.
     * @return Byte array that contains the data.
     * @throws IOException Occurs when was not possible to read the data.
     */
    public static byte[] fromTextStream(InputStream stream) throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        ByteArrayOutputStream streamBuffer = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(streamBuffer);
        String line;

        while((line = reader.readLine()) != null)
            out.println(line);

        reader.close();

        return streamBuffer.toByteArray();
    }
    
    /**
     * Transforms a byte representation into bit representation.
     *
     * @param value Numeric value.
     * @return Valor Numeric value transformed.
     */
    public static long bytesToBits(long value){
        return value * 8;
    }
    
    /**
     * Transforms the bit representation into byte representation.
     *
     * @param value Numeric value.
     * @return Valor Numeric value transformed.
     */
    public static long bitsToBytes(long value){
        return value / 8;
    }
    
    /**
     * Formats a numeric value using bit representation.
     *
     * @param value Numeric value.
     * @return Formatted string.
     */
    public static String formatBits(long value){
        return formatBits(value, LanguageUtil.getDefaultLanguage());
    }
    
    /**
     * Formats a numeric value using bit representation.
     *
     * @param value Numeric value.
     * @return Formatted string.
     */
    public static String formatBits(double value){
        return formatBits(value, LanguageUtil.getDefaultLanguage());
    }
    
    /**
     * Formats a numeric value using bit representation.
     *
     * @param value Numeric value.
     * @return Formatted string.
     */
    public static String formatBits(BigInteger value){
        return formatBits(value.longValue());
    }
    
    /**
     * Formats a numeric value using bit representation.
     *
     * @param value Numeric value.
     * @param language Instance that contains the language.
     * @return Formatted string.
     */
    public static String formatBits(long value, Locale language){
        BitMetricType currentMetric = null;

        for(BitMetricType metric: BitMetricType.values())
            if(value >= metric.getValue())
                currentMetric = metric;

        return formatBits(value, currentMetric, language);
    }
    
    /**
     * Formats a numeric value using bit representation.
     *
     * @param value Numeric value.
     * @param language Instance that contains the language.
     * @return Formatted string.
     */
    public static String formatBits(double value, Locale language){
        BitMetricType currentMetric = null;

        for(BitMetricType metric: BitMetricType.values())
            if(value >= metric.getValue())
                currentMetric = metric;

        return formatBits(value, currentMetric, language);
    }
    
    /**
     * Formats a numeric value using bit representation.
     *
     * @param value Numeric value.
     * @param language Instance that contains the language.
     * @return Formatted string.
     */
    public static String formatBits(BigDecimal value, Locale language){
        if(value == null)
            return StringUtils.EMPTY;

        return formatBits(value.doubleValue(), language);
    }
    
    /**
     * Formats a numeric value using bit representation.
     *
     * @param value Numeric value.
     * @param metric Instance that contains the type of representation (kilo,
     * mega, giga, etc.).
     * @return Formatted string.
     */
    public static String formatBits(long value, BitMetricType metric){
        return formatBits(value, metric, LanguageUtil.getDefaultLanguage());
    }
    
    /**
     * Formats a numeric value using bit representation.
     *
     * @param value Numeric value.
     * @param metric Instance that contains the type of representation (kilo,
     * mega, giga, etc.).
     * @return Formatted string.
     */
    public static String formatBits(double value, BitMetricType metric){
        return formatBits(value, metric, LanguageUtil.getDefaultLanguage());
    }
    
    /**
     * Formats a numeric value using bit representation.
     *
     * @param value Numeric value.
     * @param metric Instance that contains the type of representation (kilo,
     * mega, giga, etc.).
     * @return Formatted string.
     */
    public static String formatBits(BigDecimal value, BitMetricType metric){
        if(value != null && metric != null)
            return formatBits(value.doubleValue(), metric);
        
        return StringUtils.EMPTY;
    }
    
    /**
     * Formats a numeric value using bit representation.
     *
     * @param value Numeric value.
     * @param metric Instance that contains the type of representation (kilo,
     * mega, giga, etc.).
     * @param language Instance that contains the language.
     * @return Formatted string.
     */
    public static String formatBits(long value, BitMetricType metric, Locale language){
        if(metric != null)
            return formatBits(Long.valueOf(value).doubleValue(), metric, language);

        return StringUtils.EMPTY;
    }
    
    /**
     * Formats a numeric value using bit representation.
     *
     * @param value Numeric value.
     * @param metric Instance that contains the type of representation (kilo,
     * mega, giga, etc.).
     * @param language Instance that contains the language.
     * @return Formatted string.
     */
    public static String formatBits(double value, BitMetricType metric, Locale language){
        if(metric == null)
            return StringUtils.EMPTY;

        StringBuilder buffer = new StringBuilder();
        double metricValue = metric.getValue();
        String metricUnit = metric.getUnit();
        double valueBuffer = value / metricValue;
        double modBuffer = value % metricValue;

        if(modBuffer == 0)
            valueBuffer = (double) Math.round(valueBuffer);

        buffer.append(NumberUtil.format(valueBuffer, (modBuffer > 0 ? 2 : 0), language));

        if(metricUnit != null && !metricUnit.isEmpty()){
            buffer.append(" ");
            buffer.append(metricUnit);

            if(valueBuffer != 1)
                buffer.append("s");
        }

        return buffer.toString();
    }
    
    /**
     * Formats a numeric value using bit representation.
     *
     * @param value Numeric value.
     * @param metric Instance that contains the type of representation (kilo,
     * mega, giga, etc.).
     * @param language Instance that contains the language.
     * @return Formatted string.
     */
    public static String formatBits(BigDecimal value, BitMetricType metric, Locale language){
        if(value != null && metric != null)
            return formatBits(value.doubleValue(), metric, language);
        
        return StringUtils.EMPTY;
    }
    
    /**
     * Formats a numeric value using a byte representation.
     *
     * @param value Numeric value.
     * @param language Instance that contains the language.
     * @return Formatted string.
     */
    public static String formatBytes(long value, Locale language){
        return formatBytes(Long.valueOf(value).doubleValue(), language);
    }
    
    /**
     * Formats a numeric value using a byte representation.
     *
     * @param value Numeric value.
     * @param language Instance that contains the language.
     * @return Formatted string.
     */
    public static String formatBytes(double value, Locale language){
        ByteMetricType currentMetric = null;

        for(ByteMetricType metric: ByteMetricType.values())
            if(value >= metric.getValue())
                currentMetric = metric;

        return formatBytes(value, currentMetric, language);
    }
    
    /**
     * Formats a numeric value using a byte representation.
     *
     * @param value Numeric value.
     * @param language Instance that contains the language.
     * @return Formatted string.
     */
    public static String formatBytes(BigDecimal value, Locale language){
        if(value != null)
            return formatBytes(value.doubleValue(), language);

        return StringUtils.EMPTY;
    }
    
    /**
     * Formats a numeric value using a byte representation.
     *
     * @param value Numeric value.
     * @param metric Instance that contains the type of representation (kilo,
     * mega, giga, etc.).
     * @return Formatted string.
     */
    public static String formatBytes(long value, ByteMetricType metric){
        return formatBytes(Long.valueOf(value).doubleValue(), metric);
    }
    
    /**
     * Formats a numeric value using a byte representation.
     *
     * @param value Numeric value.
     * @param metric Instance that contains the type of representation (kilo,
     * mega, giga, etc.).
     * @return Formatted string.
     */
    public static String formatBytes(double value, ByteMetricType metric){
        if(metric != null)
            return formatBytes(value, metric, LanguageUtil.getDefaultLanguage());
        
        return StringUtils.EMPTY;
    }
    
    /**
     * Formats a numeric value using a byte representation.
     *
     * @param value Numeric value.
     * @param metric Instance that contains the type of representation (kilo,
     * mega, giga, etc.).
     * @return Formatted string.
     */
    public static String formatBytes(BigDecimal value, ByteMetricType metric){
        if(value != null && metric != null)
            return formatBytes(value.doubleValue(), metric);
        
        return StringUtils.EMPTY;
    }
    
    /**
     * Formats a numeric value using a byte representation.
     *
     * @param value Numeric value.
     * @param metric Instance that contains the type of representation (kilo,
     * mega, giga, etc.).
     * @param language Instance that contains the language.
     * @return Formatted string.
     */
    public static String formatBytes(long value, ByteMetricType metric, Locale language){
        if(metric == null)
            return StringUtils.EMPTY;

        return formatBytes(Long.valueOf(value).doubleValue(), metric, language);
    }
    
    /**
     * Formats a numeric value using a byte representation.
     *
     * @param value Numeric value.
     * @param metric Instance that contains the type of representation (kilo,
     * mega, giga, etc.).
     * @param language Instance that contains the language.
     * @return Formatted string.
     */
    public static String formatBytes(double value, ByteMetricType metric, Locale language){
        if(metric == null)
            return null;

        StringBuilder buffer = new StringBuilder();
        double metricValue = metric.getValue();
        String metricUnit = metric.getUnit();
        double valueBuffer = value / metricValue;
        double modBuffer = value % metricValue;

        if(modBuffer == 0)
            valueBuffer = (double) Math.round(valueBuffer);

        buffer.append(NumberUtil.format(valueBuffer, (modBuffer > 0 ? 2 : 0), language));

        if(metricUnit != null && !metricUnit.isEmpty()){
            buffer.append(" ");
            buffer.append(metricUnit);

            if(metric == ByteMetricType.BYTE && valueBuffer != 1)
                buffer.append("s");
        }

        return buffer.toString();
    }
    
    /**
     * Formats a numeric value using a byte representation.
     *
     * @param value Numeric value.
     * @param metric Instance that contains the type of representation (kilo,
     * mega, giga, etc.).
     * @param language Instance that contains the language.
     * @return Formatted string.
     */
    public static String formatBytes(BigDecimal value, ByteMetricType metric, Locale language){
        if(value == null)
            return StringUtils.EMPTY;

        return formatBytes(value.doubleValue(), metric, language);
    }
    
    /**
     * Formats a numeric value using a byte representation.
     *
     * @param value Numeric value.
     * @return Formatted string.
     */
    public static String formatBytes(long value){
        return formatBytes(value, LanguageUtil.getDefaultLanguage());
    }
    
    /**
     * Formats a numeric value using a byte representation.
     *
     * @param value Numeric value.
     * @return Formatted string.
     */
    public static String formatBytes(double value){
        return formatBytes(value, LanguageUtil.getDefaultLanguage());
    }
    
    /**
     * Formats a numeric value using a byte representation.
     *
     * @param value Numeric value.
     * @return Formatted string.
     */
    public static String formatBytes(BigDecimal value){
        if(value == null)
            return StringUtils.EMPTY;

        return formatBytes(value.doubleValue());
    }

    /**
     * Serializes an object.
     *
     * @param object Instance of the object.
     * @return Byte array of the serialized object.
     * @throws IOException Occurs when was not possible to serialize.
     */
    public static byte[] serialize(Object object) throws IOException{
        if(object != null) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);

            oos.writeObject(object);
            oos.flush();

            return bos.toByteArray();
        }

        return null;
    }

    /**
     * Deserializes an object.
     *
     * @param value Byte array of the serialized object.
     * @return Instance of the object.
     * @throws IOException Occurs when was not possible to deserialize.
     */
    public static Object deserialize(byte[] value) throws ClassNotFoundException, IOException{
        if(value == null)
            return null;

        ByteArrayInputStream bis = new ByteArrayInputStream(value);
        ObjectInputStream ois = new ObjectInputStream(bis);

        return ois.readObject();
    }
}