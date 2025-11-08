package br.com.concepting.framework.util;

import org.apache.commons.codec.language.RefinedSoundex;

/**
 * Class that implements phonetic routines.
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
 * along with this program.  If not, see <a href="https://www.gnu.org/licenses"></a>.</pre>
 */
public class PhoneticUtil{
    private static final RefinedSoundex algorithm = new RefinedSoundex();

    /**
     * Returns the phonetic representation of a string.
     *
     * @param value String that will be used.
     * @return String that contains the phonetic representation.
     */
    public static String soundCode(String value){
        return algorithm.encode(value);
    }
    
    /**
     * Returns the sound similarity percentage of two strings.
     *
     * @param value1 String 1.
     * @param value2 String 2
     * @return Numeric value that contains the similarity percentage.
     */
    public static double getSoundSimilarity(String value1, String value2) {
        try {
            String v1 = soundCode(value1);

            return ((double) algorithm.difference(value1, value2) / v1.length()) * 100d;
        }
        catch(Throwable ignored) {
        }

        return 0d;
    }
}