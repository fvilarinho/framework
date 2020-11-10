package br.com.concepting.framework.util;

import java.awt.Color;

/**
 * Class responsible to manipulate colors.
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
public class ColorUtil{
	/**
	 * Represents a color instance in string.
	 * 
	 * @param color Instance that contains the color.
	 * @return String that represents the color.
	 */
	public static String toString(Color color){
		if(color != null){
			Integer red = color.getRed();
			Integer green = color.getGreen();
			Integer blue = color.getBlue();
			StringBuilder value = new StringBuilder();

			value.append("rgb(");
			value.append(red);
			value.append(", ");
			value.append(green);
			value.append(",");
			value.append(blue);
			value.append(")");

			return value.toString();
		}

		return null;
	}

	/**
	 * Represents a color instance in a hexadecimal string.
	 * 
	 * @param color Instance that contains the color.
	 * @return String that represents the color.
	 */
	public static String toHexString(Color color){
		if(color != null){
			StringBuilder value = new StringBuilder();

			value.append("#");

			String red = Integer.toHexString(color.getRed());

			value.append(StringUtil.replicate("0", 2 - red.length()));
			value.append(red);

			String green = Integer.toHexString(color.getGreen());

			value.append(StringUtil.replicate("0", 2 - green.length()));
			value.append(green);

			String blue = Integer.toHexString(color.getBlue());

			value.append(StringUtil.replicate("0", 2 - blue.length()));
			value.append(blue);

			return value.toString();
		}

		return null;
	}

	/**
	 * Transforms a string into a color instance.
	 * 
	 * @param color String that contains the color.
	 * @return Instance that contains the color.
	 */
	public static Color toColor(String color){
		if(color != null && color.indexOf("rgb") >= 0){
			String value = StringUtil.replaceAll(color, "rgb(", "");

			value = StringUtil.replaceAll(value, ")", "");

			String values[] = StringUtil.split(value);

			if(values != null && values.length == 3)
				return new Color(Integer.parseInt(values[0]), Integer.parseInt(values[1]), Integer.parseInt(values[2]));
		}

		return null;
	}
}