package br.com.concepting.framework.util;

import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.processors.constants.ProcessorConstants;
import br.com.concepting.framework.util.helpers.BaseIndent;
import br.com.concepting.framework.util.types.AlignmentType;
import org.apache.commons.lang3.StringUtils;

import javax.swing.text.MaskFormatter;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class responsible to manipulate strings.
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
public class StringUtil{
    /**
     * Converts HTML to a Markdown style.
     *
     * @param html String that contains the HTML.
     * @return String that contains the Markdown.
     */
    public static String htmlToMarkdown(String html){
        html = replaceAll(html, "\t", "");
        html = replaceAll(html, "<br/>".concat(StringUtil.getLineBreak()), ProcessorConstants.DEFAULT_LINEBREAK_TAG_ID);
        html = replaceAll(html, "<br>".concat(StringUtil.getLineBreak()), ProcessorConstants.DEFAULT_LINEBREAK_TAG_ID);
        html = replaceAll(html, StringUtil.getLineBreak(), ProcessorConstants.DEFAULT_LINEBREAK_TAG_ID);
        
        Pattern pattern = Pattern.compile(".*(<.*>).*");
        
        while(true){
            Matcher matcher = pattern.matcher(html);
            
            if(matcher.matches()){
                String group = matcher.group(1);
                
                if(group.equalsIgnoreCase("<i>") || group.equalsIgnoreCase("</i>"))
                    html = replaceAll(html, group, "_");
                else if(group.equalsIgnoreCase("<b>") || group.equalsIgnoreCase("</b>"))
                    html = replaceAll(html, group, "*");
                else if(group.equalsIgnoreCase("<pre>") || group.equalsIgnoreCase("</pre>"))
                    html = replaceAll(html, group, "```");
                else if(group.equalsIgnoreCase("<code>") || group.equalsIgnoreCase("</code>"))
                    html = replaceAll(html, group, "`");
                else if(group.equalsIgnoreCase("<strike>") || group.equalsIgnoreCase("</strike>"))
                    html = replaceAll(html, group, "~");
                else if(group.equalsIgnoreCase("<br>") || group.equalsIgnoreCase("<br/>"))
                    html = replaceAll(html, group, ProcessorConstants.DEFAULT_LINEBREAK_TAG_ID);
                else if(group.equalsIgnoreCase("<li>"))
                    html = replaceAll(html, group, " - ");
                else
                    html = replaceAll(html, group, "");
            }
            else
                break;
        }
        
        html = replaceAll(html, ProcessorConstants.DEFAULT_LINEBREAK_TAG_ID, StringUtil.getLineBreak());
        html = StringUtil.trim(html);
        
        return html;
    }
    
    /**
     * Normalizes a string.
     *
     * @param value Normalized string.
     * @return String.
     */
    public static String normalize(String value){
        return normalize(value, Constants.DEFAULT_NORMALIZE_DELIMITER);
    }
    
    /**
     * Normalizes a string.
     *
     * @param value Normalized string.
     * @param delimiter String that contains the delimiter.
     * @return String.
     */
    public static String normalize(String value, String delimiter){
        if(value != null && !value.isEmpty()){
            if(delimiter == null || delimiter.isEmpty())
                delimiter = Constants.DEFAULT_NORMALIZE_DELIMITER;
            
            String[] parts = StringUtil.split(value.toLowerCase(), delimiter);
            
            if(parts != null && parts.length > 0){
                StringBuilder normalized = new StringBuilder();
                
                for(int cont = 0; cont < parts.length; cont++){
                    if(cont == 0)
                        normalized.append(parts[cont]);
                    else
                        normalized.append(StringUtil.capitalize(parts[cont]));
                }
                
                return normalized.toString();
            }
        }
        
        return value;
    }
    
    /**
     * Replicates a string.
     *
     * @param value String.
     * @param times Numeric value that contains the number of replications.
     * @return String replicated.
     */
    public static String replicate(String value, int times){
        if(value != null && !value.isEmpty() && times > 0)
            return value.repeat(times);

        return value;
    }
    
    /**
     * Replaces all occurrences of a string in another one.
     *
     * @param value Original string.
     * @param search String to be found.
     * @param replace String for replacement.
     * @return String after replacement.
     */
    public static String replaceAll(String value, String search, String replace){
        if(value != null && !value.isEmpty() && search != null && !search.isEmpty() && !search.equals(replace)){
            int pos = value.indexOf(search);
            
            if(pos >= 0){
                StringBuilder replaceBuffer = new StringBuilder();
                
                replaceBuffer.append(value, 0, pos);
                
                if(replace != null && !replace.isEmpty())
                    replaceBuffer.append(replace);
                
                replaceBuffer.append(value.substring(pos + search.length()));
                
                return replaceAll(replaceBuffer.toString(), search, replace);
            }
        }
        
        return value;
    }
    
    /**
     * Replaces all occurrences of a character in a string.
     *
     * @param value Original string.
     * @param search Character to be found.
     * @param replace String for replacement.
     * @return String after replacement.
     */
    public static String replaceLast(String value, char search, String replace){
        if(value != null && !value.isEmpty() && replace != null && !replace.isEmpty())
            return replaceLast(value, String.valueOf(search), replace);

        return StringUtils.EMPTY;
    }
    
    /**
     * Replaces the last occurrence of a string in another one.
     *
     * @param value Original string.
     * @param search String to be found.
     * @param replace String for replacement.
     * @return String after replacement.
     */
    public static String replaceLast(String value, String search, String replace){
        if(value != null && !value.isEmpty() && search != null && !search.isEmpty()){
            int pos = value.lastIndexOf(search);
            
            if(pos >= 0){
                StringBuilder replaceBuffer = new StringBuilder();
                
                replaceBuffer.append(value, 0, pos);
                
                if(replace != null && !replace.isEmpty())
                    replaceBuffer.append(replace);
                
                replaceBuffer.append(value.substring(pos + search.length()));
                
                return replaceBuffer.toString();
            }
        }

        return value;
    }
    
    /**
     * Inverts a string.
     *
     * @param value String to be inverted.
     * @return Inverted string.
     */
    public static String reverse(String value){
        if(value != null && !value.isEmpty()){
            StringBuilder reverseBuffer = new StringBuilder(value);
            
            return reverseBuffer.reverse().toString();
        }
        
        return null;
    }
    
    /**
     * Removes the whitespaces of the beginning and ending of a string.
     *
     * @param value String that will be used.
     * @return String without the whitespaces.
     */
    public static String trim(Object value){
        if(value == null)
            return "";
        
        if(PropertyUtil.isString(value))
            return ((String) value).trim();
        
        String result = value.toString();
        
        if(result == null)
            return "";
        
        return result.trim();
    }
    
    /**
     * Aligns a string.
     *
     * @param alignment Instance that contains the alignment type.
     * @param maxChars Numeric value that contains the maximum size of the final
     * string.
     * @param value String that will be used.
     * @return Aligned string.
     */
    public static String align(AlignmentType alignment, int maxChars, String value){
        if(value != null && !value.isEmpty() && maxChars > 0){
            StringBuilder align = new StringBuilder();
            int length = value.length();

            if(maxChars > length){
                switch(alignment){
                    case LEFT:{
                        align.append(value);
                        align.append(replicate(" ", maxChars - length));
                        
                        break;
                    }
                    case RIGHT:{
                        align.append(replicate(" ", maxChars - length));
                        align.append(value);
                        
                        break;
                    }
                    default:{
                        align.append(replicate(" ", (maxChars - length) / 2));
                        align.append(value);
                        align.append(replicate(" ", (maxChars - length) / 2));
                        
                        break;
                    }
                }
                
                return align.toString();
            }
        }
        
        return value;
    }
    
    /**
     * Returns the character based on its ASCII code.
     *
     * @param value Numeric value that contains the ASCII code.
     * @return Character that represents the ASCII code.
     */
    public static char chr(int value){
        return (char) value;
    }
    
    /**
     * Returns the ASCII code of a character.
     *
     * @param value Character that represents the ASCII code.
     * @return Numeric value that contains the ASCII code.
     */
    public static int asc(char value){
        return value;
    }
    
    /**
     * Returns the line break separator character.
     *
     * @return String that contains the character.
     */
    public static String getLineBreak(){
        return System.lineSeparator();
    }
    
    /**
     * Merges two or more objects into a string using the default delimiter.
     *
     * @param values List of objects that will be used.
     * @return Merged string.
     */
    public static String merge(Object[] values){
        if(values != null && values.length > 0)
            return merge(values, Constants.DEFAULT_DELIMITER);
        
        return null;
    }
    
    /**
     * Merges two or more objects into a string using a specific delimiter.
     *
     * @param values List of objects that will be used.
     * @param delimiter String that contains the delimiter.
     * @return Merged string.
     */
    public static String merge(Object[] values, String delimiter){
        if(values != null && values.length > 0){
            if(delimiter == null || delimiter.isEmpty())
                delimiter = Constants.DEFAULT_DELIMITER;
            
            StringBuilder result = new StringBuilder();
            
            for(int cont = 0; cont < values.length; cont++){
                if(cont > 0)
                    result.append(delimiter);
                
                result.append(values[cont].toString());
            }
            
            return result.toString();
        }
        
        return null;
    }
    
    /**
     * Splits a delimited string into an array of strings using the default
     * delimiter.
     *
     * @param value Delimited string.
     * @return Array that contains the strings.
     */
    public static String[] split(String value){
        if(value != null && !value.isEmpty())
            return split(value, Constants.DEFAULT_DELIMITER);
        
        return null;
    }
    
    /**
     * Splits a delimited string into an array of strings using a specific
     * delimiter.
     *
     * @param value Delimited string.
     * @param delimiter String that contains the delimiter.
     * @return Array that contains the strings.
     */
    public static String[] split(String value, String delimiter){
        if(value != null && !value.isEmpty()){
            if(delimiter == null || delimiter.isEmpty())
                delimiter = Constants.DEFAULT_DELIMITER;
            
            StringTokenizer tokenizer = new StringTokenizer(value, delimiter);
            String[] tokens = new String[tokenizer.countTokens()];
            
            for(int cont = 0; cont < tokens.length; cont++)
                tokens[cont] = StringUtil.trim(tokenizer.nextToken());
            
            return tokens;
        }
        
        return null;
    }
    
    /**
     * Capitalizes a string.
     *
     * @param value String that will be used.
     * @param onlyFirstWord Indicates if only the first word should be
     * capitalized.
     * @return String capitalized.
     */
    public static String capitalize(String value, boolean onlyFirstWord){
        return capitalize(value, Constants.DEFAULT_CAPITALIZE_DELIMITER, onlyFirstWord);
    }
    
    /**
     * Capitalizes a string.
     *
     * @param value String that will be used.
     * @param delimiter String that contains the delimiter.
     * @param onlyFirstWord Indicates if only the first word should be
     * capitalized.
     * @return String capitalized.
     */
    public static String capitalize(String value, String delimiter, boolean onlyFirstWord){
        if(value != null && !value.isEmpty()){
            StringBuilder result = new StringBuilder();
            
            if(onlyFirstWord){
                result.append(value.substring(0, 1).toUpperCase());
                result.append(value.substring(1));
            }
            else{
                if(delimiter == null || delimiter.isEmpty())
                    delimiter = Constants.DEFAULT_CAPITALIZE_DELIMITER;
                
                String[] values = StringUtil.split(value, delimiter);
                
                if(values != null){
                    for(String valueItem: values){
                        if(result.length() > 0)
                            result.append(delimiter);
                        
                        result.append(valueItem.substring(0, 1).toUpperCase());
                        result.append(valueItem.substring(1));
                    }
                }
            }
            
            return result.toString();
        }
        
        return null;
    }
    
    /**
     * Capitalizes a string.
     *
     * @param value String that will be used.
     * @param delimiter String that contains the delimiter.
     * @return String capitalized.
     */
    public static String capitalize(String value, String delimiter){
        if(value != null && !value.isEmpty())
            return capitalize(value, delimiter, false);
        
        return null;
    }
    
    /**
     * Capitalizes a string.
     *
     * @param value String that will be used.
     * @return String capitalized.
     */
    public static String capitalize(String value){
        return capitalize(value, Constants.DEFAULT_CAPITALIZE_DELIMITER);
    }
    
    /**
     * Formats a string based on a pattern.
     *
     * @param value String that will be used.
     * @param pattern String that contains a pattern.
     * @return Formatted string.
     */
    public static String format(String value, String pattern){
        if(value != null && !value.isEmpty() && pattern != null && !pattern.isEmpty()){
            pattern = StringUtil.replaceAll(pattern, "#", "A");
            pattern = StringUtil.replaceAll(pattern, "9", "#");
            pattern = StringUtil.replaceAll(pattern, "d", "#");
            pattern = StringUtil.replaceAll(pattern, "M", "#");
            pattern = StringUtil.replaceAll(pattern, "y", "#");
            pattern = StringUtil.replaceAll(pattern, "H", "#");
            pattern = StringUtil.replaceAll(pattern, "h", "#");
            pattern = StringUtil.replaceAll(pattern, "m", "#");
            pattern = StringUtil.replaceAll(pattern, "s", "#");
            
            try{
                MaskFormatter formatter = new MaskFormatter(pattern);

                formatter.setValueContainsLiteralCharacters(false);
                
                value = formatter.valueToString(value);

                int cont;

                for(cont = 0; cont < pattern.length(); cont++)
                    if(pattern.charAt(cont) != ' ' && value.charAt(cont) == ' ')
                        break;
                
                if(pattern.charAt(cont - 1) != '#' && pattern.charAt(cont - 1) != '9' && pattern.charAt(cont - 1) != 'd' && pattern.charAt(cont - 1) != 'M' && pattern.charAt(cont - 1) != 'y' && pattern.charAt(cont - 1) != 'H' && pattern.charAt(cont - 1) != 'h' && pattern.charAt(cont - 1) != 'm' && pattern.charAt(cont - 1) != 's' && pattern.charAt(cont - 1) != 'A')
                    value = value.substring(0, cont - 1);
                else
                    value = value.substring(0, cont);
            }
            catch(ParseException ignored){
            }
        }
        
        return value;
    }
    
    /**
     * Removes the formatting of a string.
     *
     * @param value String that will be used.
     * @param pattern String that contains the pattern.
     * @return String without the formatting.
     */
    public static String unformat(String value, String pattern){
        if(value != null && !value.isEmpty() && pattern != null && !pattern.isEmpty()){
            for(int cont = 0; cont < pattern.length(); cont++)
                if(pattern.charAt(cont) != '9' && pattern.charAt(cont) != '#' && pattern.charAt(cont) != '0' && pattern.charAt(cont) != 'd' && pattern.charAt(cont) != 'M' && pattern.charAt(cont) != 'm' && pattern.charAt(cont) != 'y' && pattern.charAt(cont) != 'H' && pattern.charAt(cont) != 'h' && pattern.charAt(cont) != 's')
                    value = StringUtil.replaceAll(value, String.valueOf(pattern.charAt(cont)), "");
        }
        
        return value;
    }
    
    /**
     * Indents a string based on a list of indentation rules.
     *
     * @param <R> Class that defines the indentation rule.
     * @param value String that will be used.
     * @param rules List that contains the indentation rules
     * @return Indented string
     * @throws IOException Occurs when was not possible to execute the
     * operation.
     * @throws InstantiationException Occurs when was not possible to execute the
     * operation.
     * @throws InvocationTargetException Occurs when was not possible to execute the
     * operation.
     * @throws IllegalAccessException Occurs when was not possible to execute the
     * operation.
     * @throws NoSuchMethodException Occurs when was not possible to execute the
     * operation.
     */
    public static <R extends BaseIndent> String indent(String value, Collection<R> rules) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException{
        StringBuilder result = new StringBuilder();
        
        if(value != null && !value.isEmpty() && rules != null && !rules.isEmpty()){
            ByteArrayInputStream in = new ByteArrayInputStream(value.getBytes());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            LinkedList<BaseIndent> rulesQueue = null;
            BaseIndent lastRule;
            BaseIndent currentRule;
            String indentation;
            int currentIdentCount = 0;
            String line;
            int pos;
            
            while((line = reader.readLine()) != null){
                line = StringUtil.trim(line);
                line = StringUtil.replaceAll(line, String.valueOf(StringUtil.chr(9)), "");
                
                if(rulesQueue != null && !rulesQueue.isEmpty()){
                    lastRule = rulesQueue.getLast();
                    
                    if(!lastRule.backAfterEndChar()){
                        if(lastRule.getEndChar() != null){
                            pos = line.indexOf(lastRule.getEndChar());
                            
                            if(pos >= 0){
                                if(pos == 0){
                                    if(lastRule.getIndentSize() != null){
                                        currentIdentCount -= lastRule.getIndentSize();
                                        
                                        rulesQueue.removeLast();
                                    }
                                }
                                else{
                                    if(lastRule.getStartChar() != null && !lastRule.getStartChar().isEmpty()){
                                        pos = line.indexOf(lastRule.getStartChar());
                                        
                                        if(pos < 0){
                                            currentIdentCount -= lastRule.getIndentSize();
                                            
                                            rulesQueue.removeLast();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                indentation = StringUtil.replicate(" ", currentIdentCount);
                
                if(indentation != null && !indentation.isEmpty())
                    result.append(indentation);
                
                result.append(line);
                result.append(StringUtil.getLineBreak());
                
                currentRule = null;
                
                for(BaseIndent rule: rules){
                    if(rule.getStartChar() != null && !rule.getStartChar().isEmpty()){
                        pos = line.indexOf(rule.getStartChar());
                        
                        if(pos >= 0){
                            currentRule = rule;
                            
                            if(rule.getEndChar() != null && !rule.getEndChar().isEmpty()){
                                pos = line.indexOf(rule.getEndChar());
                                
                                if(pos >= 0){
                                    currentRule = null;
                                    
                                    break;
                                }
                            }
                        }
                    }
                }
                
                if(currentRule != null && currentRule.getIndentSize() != null){
                    currentIdentCount += currentRule.getIndentSize();
                    
                    if(rulesQueue == null)
                        rulesQueue = PropertyUtil.instantiate(Constants.DEFAULT_LIFO_QUEUE_CLASS);

                    if(rulesQueue != null)
                        rulesQueue.add(currentRule);
                }
                
                if(rulesQueue != null && !rulesQueue.isEmpty()){
                    lastRule = rulesQueue.getLast();
                    
                    if(lastRule.backAfterEndChar()){
                        if(lastRule.getEndChar() != null && !lastRule.getEndChar().isEmpty()){
                            pos = line.indexOf(lastRule.getEndChar());
                            
                            if(pos >= 0){
                                if(pos == 0){
                                    if(lastRule.getIndentSize() != null){
                                        currentIdentCount -= lastRule.getIndentSize();
                                        
                                        rulesQueue.removeLast();
                                    }
                                }
                                else{
                                    if(lastRule.getStartChar() != null && !lastRule.getStartChar().isEmpty()){
                                        pos = line.indexOf(lastRule.getStartChar());
                                        
                                        if(pos < 0){
                                            currentIdentCount -= lastRule.getIndentSize();
                                            
                                            rulesQueue.removeLast();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return result.toString();
    }
    
    /**
     * Converts a string with wildcards into a string with regular expressions.
     *
     * @param expression String that will be used.
     * @return String that contains the regular expressions.
     */
    public static String toRegex(String expression){
        if(expression != null && !expression.isEmpty()){
            expression = StringUtil.replaceAll(expression, ".*", "\\.".concat(ProcessorConstants.DEFAULT_REMOVE_TAG_ID));
            expression = StringUtil.replaceAll(expression, "*.", ".".concat(ProcessorConstants.DEFAULT_REMOVE_TAG_ID).concat("\\."));
            expression = StringUtil.replaceAll(expression, "*", ".".concat(ProcessorConstants.DEFAULT_REMOVE_TAG_ID));
            expression = StringUtil.replaceAll(expression, "?", ".");
            expression = StringUtil.replaceAll(expression, ProcessorConstants.DEFAULT_REMOVE_TAG_ID, "*");
            
            return expression;
        }
        
        return null;
    }
}