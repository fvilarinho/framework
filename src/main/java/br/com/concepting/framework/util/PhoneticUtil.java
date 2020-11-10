package br.com.concepting.framework.util;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.codec.language.RefinedSoundex;

import com.wcohen.ss.Jaro;
import com.wcohen.ss.MongeElkan;

import br.com.concepting.framework.constants.Constants;

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
public class PhoneticUtil{
	private static Map<String, String> firstSoundexMap  = null;
	private static Map<String, String> middleSoundexMap = null;
	private static Map<String, String> lastSoundexMap   = null;

	static{
		firstSoundexMap = PropertyUtil.instantiate(Constants.DEFAULT_MAP_CLASS);
		firstSoundexMap.put("ka", "ca");
		firstSoundexMap.put("ke", "que");
		firstSoundexMap.put("ki", "qui");
		firstSoundexMap.put("ko", "co");
		firstSoundexMap.put("ku", "cu");
		firstSoundexMap.put("ra", "ha");
		firstSoundexMap.put("re", "he");
		firstSoundexMap.put("ri", "hi");
		firstSoundexMap.put("ro", "ho");
		firstSoundexMap.put("ru", "hu");
		firstSoundexMap.put("ha", "a");
		firstSoundexMap.put("he", "e");
		firstSoundexMap.put("hi", "i");
		firstSoundexMap.put("ho", "o");
		firstSoundexMap.put("hu", "u");
		firstSoundexMap.put("ja", "xia");
		firstSoundexMap.put("je", "xie");
		firstSoundexMap.put("jo", "xio");
		firstSoundexMap.put("ju", "xiu");
		firstSoundexMap.put("phe", "fe");
		firstSoundexMap.put("phi", "fi");
		firstSoundexMap.put("pha", "fa");
		firstSoundexMap.put("pho", "fo");

		middleSoundexMap = PropertyUtil.instantiate(Constants.DEFAULT_MAP_CLASS);
		middleSoundexMap.put("@", "a");
		middleSoundexMap.put("8", "B");
		middleSoundexMap.put("3", "E");
		middleSoundexMap.put("z", "2");
		middleSoundexMap.put("0", "O");
		middleSoundexMap.put("7", "T");
		middleSoundexMap.put("á", "a");
		middleSoundexMap.put("é", "e");
		middleSoundexMap.put("í", "i");
		middleSoundexMap.put("ó", "o");
		middleSoundexMap.put("ú", "u");
		middleSoundexMap.put("â", "a");
		middleSoundexMap.put("ê", "e");
		middleSoundexMap.put("ô", "o");
		middleSoundexMap.put("ã", "an");
		middleSoundexMap.put("õ", "on");
		middleSoundexMap.put("s", "ss");
		middleSoundexMap.put("ch", "x");
		middleSoundexMap.put("nh", "ni");
		middleSoundexMap.put("lh", "li");
		middleSoundexMap.put("'", "");

		lastSoundexMap = PropertyUtil.instantiate(Constants.DEFAULT_MAP_CLASS);
		lastSoundexMap.put("az", "as");
		lastSoundexMap.put("ez", "es");
		lastSoundexMap.put("iz", "is");
		lastSoundexMap.put("oz", "os");
		lastSoundexMap.put("uz", "us");
		lastSoundexMap.put("am", "an");
		lastSoundexMap.put("em", "en");
		lastSoundexMap.put("im", "in");
		lastSoundexMap.put("om", "on");
		lastSoundexMap.put("um", "un");
		lastSoundexMap.put("ax", "akis");
		lastSoundexMap.put("ex", "ekis");
		lastSoundexMap.put("ix", "ikis");
		lastSoundexMap.put("ox", "okis");
		lastSoundexMap.put("ux", "ukis");
		lastSoundexMap.put("pi", "p");
	}

	/**
	 * Returns the phonetic representation of a string.
	 * 
	 * @param value String that will be used.
	 * @return String that contains the phonetic representation.
	 */
	public static String soundCode(String value){
		if(value != null && value.length() > 0){
			String tokens[] = StringUtil.split(value.toLowerCase(), " ");

			for(Entry<String, String> entry : firstSoundexMap.entrySet())
				for(int cont = 0 ; cont < tokens.length ; cont++)
					if(tokens[cont].indexOf(entry.getKey()) == 0)
						tokens[cont] = StringUtil.replaceAll(tokens[cont], entry.getKey(), entry.getValue());

			for(Entry<String, String> entry : lastSoundexMap.entrySet())
				for(int cont = 0 ; cont < tokens.length ; cont++)
					if(tokens[cont].indexOf(entry.getKey()) == (tokens[cont].length() - entry.getKey().length()))
						tokens[cont] = StringUtil.replaceAll(tokens[cont], entry.getKey(), entry.getValue());

			for(Entry<String, String> entry : middleSoundexMap.entrySet())
				for(int cont = 0 ; cont < tokens.length ; cont++)
					tokens[cont] = StringUtil.replaceAll(tokens[cont], entry.getKey(), entry.getValue());

			RefinedSoundex encoder = new RefinedSoundex();

			for(int cont = 0 ; cont < tokens.length ; cont++)
				tokens[cont] = encoder.encode(tokens[cont]);

			return StringUtil.merge(tokens, " ");
		}

		return null;
	}

	/**
	 * Returns the accuracy percentage of two string.
	 * 
	 * @param value1 String 1.
	 * @param value2 String 2
	 * @return Numeric value that contains the accuracy percentage.
	 */
	public static Double getAccuracy(String value1, String value2){
		if(value1 == null || value2 == null)
			return null;

		String valueTokens1[] = StringUtil.split(value1, " ");
		String valueTokens2[] = StringUtil.split(value2, " ");
		StringBuilder valueBuffer1 = null;

		for(int cont = 0 ; cont < valueTokens1.length ; cont++){
			if(cont < valueTokens2.length){
				if(soundCode(valueTokens1[cont]).equals(soundCode(valueTokens2[cont]))){
					if(valueBuffer1 == null)
						valueBuffer1 = new StringBuilder();

					valueBuffer1.append(valueTokens1[cont]);
					valueBuffer1.append(" ");
				}
			}
		}

		if(valueBuffer1 == null || valueBuffer1.length() == 0)
			return 0d;

		return ((new MongeElkan().score(valueBuffer1.toString(), value2) + new Jaro().score(valueBuffer1.toString(), value2)) / 2d) * 100d;
	}
}