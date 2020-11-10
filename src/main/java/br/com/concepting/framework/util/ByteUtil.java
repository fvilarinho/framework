package br.com.concepting.framework.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Base64;
import java.util.Locale;

import org.apache.commons.codec.binary.Hex;

import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.util.types.BitMetricType;
import br.com.concepting.framework.util.types.ByteMetricType;

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
public class ByteUtil{
	/**
	 * Converts a byte array into a Hexadecimal string.
	 *
	 * @param values Byte array that contains the data.
	 * @return String that was converted to Hexadecimal.
	 */
	public static String toHexadecimal(byte values[]){
		if(values != null && values.length > 0)
			return new String(Hex.encodeHex(values));
			
		return null;
	}

	/**
	 * Converts a Hexadecimal string into a byte array.
	 *
	 * @param value String that contains the value.
	 * @return Byte array converted from Hexadecimal.
	 */
	public static byte[] fromHexadecimal(String value){
		try{
			if(value != null && value.length() > 0)
				return Hex.decodeHex(value);
		}
		catch(Throwable e){
		}
			
		return null;
	}

	/**
	 * Converts a byte array into a Base64 string.
	 *
	 * @param value Byte array that contains the data.
	 * @return String that was converted to Base64.
	 * @throws UnsupportedEncodingException Occurs when was not possible to
	 * execute the operation.
	 */
	public static String toBase64(byte value[]) throws UnsupportedEncodingException{
		if(value != null && value.length > 0)
			return new String(Base64.getMimeEncoder().encode(value), Constants.DEFAULT_UNICODE_ENCODING);

		return null;
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
		if(value != null && value.length() > 0)
			return Base64.getMimeDecoder().decode(value.getBytes(Constants.DEFAULT_UNICODE_ENCODING));

		return null;
	}

	/**
	 * Reads the binary data from a stream.
	 * 
	 * @param stream Instance that contains the stream.
	 * @return Byte array that contains the data.
	 * @throws IOException Occurs when was not possible to read the data.
	 */
	public static byte[] fromBinaryStream(InputStream stream) throws IOException{
		if(stream != null){
			byte[] buffer = new byte[Constants.DEFAULT_BUFFER_SIZE];
			Integer length = null;
			ByteArrayOutputStream out = new ByteArrayOutputStream();

			while((length = stream.read(buffer)) > 0)
				out.write(buffer, 0, length);

			out.close();
			stream.close();

			return out.toByteArray();
		}

		return null;
	}

	/**
	 * Reads the text data from a stream.
	 * 
	 * @param stream Instance that contains the stream.
	 * @return Byte array that contains the data.
	 * @throws IOException Occurs when was not possible to read the data.
	 */
	public static byte[] fromTextStream(InputStream stream) throws IOException{
		if(stream != null){
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
			ByteArrayOutputStream streamBuffer = new ByteArrayOutputStream();
			String line = null;
			PrintStream out = new PrintStream(streamBuffer);

			while((line = reader.readLine()) != null)
				out.println(line);

			reader.close();

			return streamBuffer.toByteArray();
		}

		return null;
	}

	/**
	 * Transforms a byte representation into a bit representation.
	 *
	 * @param value Numeric value.
	 * @return Valor Numeric value transformed.
	 */
	public static Long bytesToBits(Long value){
		if(value != null)
			return value * 8;

		return null;
	}

	/**
	 * Transforms a bit representation into an byte representation.
	 *
	 * @param value Numeric value.
	 * @return Valor Numeric value transformed.
	 */
	public static Long bitsToBytes(Long value){
		if(value != null)
			return value / 8;

		return null;
	}

	/**
	 * Formats a numeric value using a bit representation.
	 *
	 * @param value Numeric value.
	 * @return Formatted string.
	 */
	public static String formatBits(Long value){
		if(value != null)
			return formatBits(value.doubleValue());

		return null;
	}

	/**
	 * Formats a numeric value using a bit representation.
	 *
	 * @param value Numeric value.
	 * @return Formatted string.
	 */
	public static String formatBits(Double value){
		if(value != null)
			return formatBits(value, LanguageUtil.getDefaultLanguage());

		return null;
	}

	/**
	 * Formats a numeric value using a bit representation.
	 *
	 * @param value Numeric value.
	 * @return Formatted string.
	 */
	public static String formatBits(BigDecimal value){
		if(value != null)
			return formatBits(value.longValue());

		return null;
	}

	/**
	 * Formats a numeric value using a bit representation.
	 *
	 * @param value Numeric value.
	 * @param language Instance that contains the language.
	 * @return Formatted string.
	 */
	public static String formatBits(Long value, Locale language){
		if(value != null)
			return formatBits(value.doubleValue(), language);

		return null;
	}

	/**
	 * Formats a numeric value using a bit representation.
	 *
	 * @param value Numeric value.
	 * @param language Instance that contains the language.
	 * @return Formatted string.
	 */
	public static String formatBits(Double value, Locale language){
		if(value != null){
			BitMetricType currentMetric = null;

			for(BitMetricType metric : BitMetricType.values())
				if(value >= metric.getValue())
					currentMetric = metric;

			return formatBits(value, currentMetric, language);
		}

		return null;
	}

	/**
	 * Formats a numeric value using a bit representation.
	 *
	 * @param value Numeric value.
	 * @param language Instance that contains the language.
	 * @return Formatted string.
	 */
	public static String formatBits(BigDecimal value, Locale language){
		if(value != null)
			return formatBits(value.doubleValue(), language);

		return null;
	}

	/**
	 * Formats a numeric value using a bit representation.
	 *
	 * @param value Numeric value.
	 * @param metric Instance that contains the type of representation (kilo,
	 * mega, giga, etc).
	 * @return Formatted string.
	 */
	public static String formatBits(Long value, BitMetricType metric){
		if(value != null && metric != null)
			return formatBits(value.doubleValue(), metric);

		return null;
	}

	/**
	 * Formats a numeric value using a bit representation.
	 *
	 * @param value Numeric value.
	 * @param metric Instance that contains the type of representation (kilo,
	 * mega, giga, etc).
	 * @return Formatted string.
	 */
	public static String formatBits(Double value, BitMetricType metric){
		if(value != null && metric != null)
			return formatBits(value, metric, LanguageUtil.getDefaultLanguage());

		return null;
	}

	/**
	 * Formats a numeric value using a bit representation.
	 *
	 * @param value Numeric value.
	 * @param metric Instance that contains the type of representation (kilo,
	 * mega, giga, etc).
	 * @return Formatted string.
	 */
	public static String formatBits(BigDecimal value, BitMetricType metric){
		if(value != null && metric != null)
			return formatBits(value.doubleValue(), metric);

		return null;
	}

	/**
	 * Formats a numeric value using a bit representation.
	 *
	 * @param value Numeric value.
	 * @param metric Instance that contains the type of representation (kilo,
	 * mega, giga, etc).
	 * @param language Instance that contains the language.
	 * @return Formatted string.
	 */
	public static String formatBits(Long value, BitMetricType metric, Locale language){
		if(value != null && metric != null)
			return formatBits(value.doubleValue(), metric, language);

		return null;
	}

	/**
	 * Formats a numeric value using a bit representation.
	 *
	 * @param value Numeric value.
	 * @param metric Instance that contains the type of representation (kilo,
	 * mega, giga, etc).
	 * @param language Instance that contains the language.
	 * @return Formatted string.
	 */
	public static String formatBits(Double value, BitMetricType metric, Locale language){
		if(value != null && metric != null){
			StringBuilder buffer = new StringBuilder();
			Double metricValue = metric.getValue();
			String metricUnit = metric.getUnit();
			Double valueBuffer = value / metricValue;
			Double modBuffer = value % metricValue;

			if(modBuffer == 0)
				valueBuffer = (double)Math.round(valueBuffer);

			buffer.append(NumberUtil.format(valueBuffer, (modBuffer > 0 ? 2 : 0), language));

			if(metricUnit != null && metricUnit.length() > 0){
				buffer.append(" ");
				buffer.append(metricUnit);

				if(valueBuffer != 1)
					buffer.append("s");
			}

			return buffer.toString();
		}

		return null;
	}

	/**
	 * Formats a numeric value using a bit representation.
	 *
	 * @param value Numeric value.
	 * @param metric Instance that contains the type of representation (kilo,
	 * mega, giga, etc).
	 * @param language Instance that contains the language.
	 * @return Formatted string.
	 */
	public static String formatBits(BigDecimal value, BitMetricType metric, Locale language){
		if(value != null && metric != null)
			return formatBits(value.doubleValue(), metric, language);

		return null;
	}

	/**
	 * Formats a numeric value using a byte representation.
	 *
	 * @param value Numeric value.
	 * @param language Instance that contains the language.
	 * @return Formatted string.
	 */
	public static String formatBytes(Long value, Locale language){
		if(value != null)
			return formatBytes(value.doubleValue(), language);

		return null;
	}

	/**
	 * Formats a numeric value using a byte representation.
	 *
	 * @param value Numeric value.
	 * @param language Instance that contains the language.
	 * @return Formatted string.
	 */
	public static String formatBytes(Double value, Locale language){
		if(value != null){
			ByteMetricType currentMetric = null;

			for(ByteMetricType metric : ByteMetricType.values())
				if(value >= metric.getValue())
					currentMetric = metric;

			return formatBytes(value, currentMetric, language);
		}

		return null;
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

		return null;
	}

	/**
	 * Formats a numeric value using a byte representation.
	 *
	 * @param value Numeric value.
	 * @param metric Instance that contains the type of representation (kilo,
	 * mega, giga, etc).
	 * @return Formatted string.
	 */
	public static String formatBytes(Long value, ByteMetricType metric){
		if(value != null)
			return formatBytes(value.doubleValue(), metric);

		return null;
	}

	/**
	 * Formats a numeric value using a byte representation.
	 *
	 * @param value Numeric value.
	 * @param metric Instance that contains the type of representation (kilo,
	 * mega, giga, etc).
	 * @return Formatted string.
	 */
	public static String formatBytes(Double value, ByteMetricType metric){
		if(value != null && metric != null)
			return formatBytes(value, metric, LanguageUtil.getDefaultLanguage());

		return null;
	}

	/**
	 * Formats a numeric value using a byte representation.
	 *
	 * @param value Numeric value.
	 * @param metric Instance that contains the type of representation (kilo,
	 * mega, giga, etc).
	 * @return Formatted string.
	 */
	public static String formatBytes(BigDecimal value, ByteMetricType metric){
		if(value != null && metric != null)
			return formatBytes(value.doubleValue(), metric);

		return null;
	}

	/**
	 * Formats a numeric value using a byte representation.
	 *
	 * @param value Numeric value.
	 * @param metric Instance that contains the type of representation (kilo,
	 * mega, giga, etc).
	 * @param language Instance that contains the language.
	 * @return Formatted string.
	 */
	public static String formatBytes(Long value, ByteMetricType metric, Locale language){
		if(value != null && metric != null)
			return formatBytes(value.doubleValue(), metric, language);

		return null;
	}

	/**
	 * Formats a numeric value using a byte representation.
	 *
	 * @param value Numeric value.
	 * @param metric Instance that contains the type of representation (kilo,
	 * mega, giga, etc).
	 * @param language Instance that contains the language.
	 * @return Formatted string.
	 */
	public static String formatBytes(Double value, ByteMetricType metric, Locale language){
		if(value != null && metric != null){
			StringBuilder buffer = new StringBuilder();
			Double metricValue = metric.getValue();
			String metricUnit = metric.getUnit();
			Double valueBuffer = value / metricValue;
			Double modBuffer = value % metricValue;

			if(modBuffer == 0)
				valueBuffer = (double)Math.round(valueBuffer);

			buffer.append(NumberUtil.format(valueBuffer, (modBuffer > 0 ? 2 : 0), language));

			if(metricUnit != null && metricUnit.length() > 0){
				buffer.append(" ");
				buffer.append(metricUnit);

				if(metric == ByteMetricType.BYTE && valueBuffer != 1)
					buffer.append("s");
			}

			return buffer.toString();
		}

		return null;
	}

	/**
	 * Formats a numeric value using a byte representation.
	 *
	 * @param value Numeric value.
	 * @param metric Instance that contains the type of representation (kilo,
	 * mega, giga, etc).
	 * @param language Instance that contains the language.
	 * @return Formatted string.
	 */
	public static String formatBytes(BigDecimal value, ByteMetricType metric, Locale language){
		if(value != null && metric != null)
			return formatBytes(value.doubleValue(), metric, language);

		return null;
	}

	/**
	 * Formats a numeric value using a byte representation.
	 *
	 * @param value Numeric value.
	 * @return String formatada.
	 */
	public static String formatBytes(Long value){
		if(value != null)
			return formatBytes(value.doubleValue());

		return null;
	}

	/**
	 * Formats a numeric value using a byte representation.
	 *
	 * @param value Numeric value.
	 * @return String formatada.
	 */
	public static String formatBytes(Double value){
		if(value != null)
			return formatBytes(value, LanguageUtil.getDefaultLanguage());

		return null;
	}

	/**
	 * Formats a numeric value using a byte representation.
	 *
	 * @param value Numeric value.
	 * @return String formatada.
	 */
	public static String formatBytes(BigDecimal value){
		if(value != null)
			return formatBytes(value.doubleValue());

		return null;
	}
}