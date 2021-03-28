package br.com.concepting.framework.util.helpers;

import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.util.PropertyUtil;

import java.util.Collection;

/**
 * Class that defines the indentation rules for XML source code.
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
public class TagIndent extends BaseIndent{
    private static Collection<TagIndent> rules = null;
    
    static{
        rules = PropertyUtil.instantiate(Constants.DEFAULT_LIST_CLASS);
        rules.add(new TagIndent("<", "/>"));
        rules.add(new TagIndent("<", "</"));
        rules.add(new TagIndent("<!--", "-->", 1, true));
    }
    
    /**
     * Constructor - Defines the indentation rule.
     *
     * @param startChar String that contains the start character.
     * @param endChar String that contains the end character.
     */
    public TagIndent(String startChar, String endChar){
        super(startChar, endChar);
    }
    
    /**
     * Constructor - Defines the indentation rule.
     *
     * @param startChar String that contains the start character.
     * @param endChar String that contains the end character.
     * @param indentSize Numeric value that contains the amount of indentation
     * characters should be use.
     */
    public TagIndent(String startChar, String endChar, Integer indentSize){
        super(startChar, endChar, indentSize);
    }
    
    /**
     * Constructor - Defines the indentation rule.
     *
     * @param startChar String that contains the start character.
     * @param endChar String that contains the end character.
     * @param indentSize Numeric value that contains the amount of indentation
     * characters should be use.
     * @param backAfterEndChar Indicates if the indentation should be cleared
     * after match the end character.
     */
    public TagIndent(String startChar, String endChar, Integer indentSize, Boolean backAfterEndChar){
        super(startChar, endChar, indentSize, backAfterEndChar);
    }
    
    /**
     * Constructor - Defines the indentation rule.
     *
     * @param startChar String that contains the start character.
     * @param endChar String that contains the end character.
     * @param backAfterEndChar Indicates if the indentation should be cleared
     * after match the end character.
     */
    public TagIndent(String startChar, String endChar, Boolean backAfterEndChar){
        super(startChar, endChar, backAfterEndChar);
    }
    
    /**
     * Constructor - Defines the indentation rule.
     *
     * @param startChar String that contains the start character.
     * @param endChar String that contains the end character.
     * @param indentSize Numeric value that contains the amount of indentation
     * characters should be use.
     * @param indentChar String that contains the indentation character.
     */
    public TagIndent(String startChar, String endChar, Integer indentSize, String indentChar){
        super(startChar, endChar, indentSize, indentChar);
    }
    
    /**
     * Constructor - Defines the indentation rule.
     *
     * @param startChar String that contains the start character.
     * @param endChar String that contains the end character.
     * @param indentSize Numeric value that contains the amount of indentation
     * characters should be use.
     * @param indentChar String that contains the indentation character.
     * @param backAfterEndChar Indicates if the indentation should be cleared
     * after match the end character.
     */
    public TagIndent(String startChar, String endChar, Integer indentSize, String indentChar, Boolean backAfterEndChar){
        super(startChar, endChar, indentSize, indentChar, backAfterEndChar);
    }
    
    /**
     * Returns the indentation rules list.
     *
     * @return List that contains the indentation rules.
     */
    public static Collection<TagIndent> getRules(){
        return rules;
    }
}