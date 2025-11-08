package br.com.concepting.framework.util.helpers;

import br.com.concepting.framework.constants.Constants;

/**
 * Class that defines the basic implementation of the indentation rules.
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
public abstract class BaseIndent{
    private String startChar;
    private String endChar;
    private int indentSize;
    private String indentChar;
    private boolean backAfterEndChar;
    
    /**
     * Constructor - Defines the indentation rule.
     *
     * @param startChar String that contains the start character.
     * @param endChar String that contains the end character.
     */
    public BaseIndent(String startChar, String endChar){
        this(startChar, endChar, Constants.DEFAULT_INDENT_SIZE);
    }
    
    /**
     * Constructor - Defines the indentation rule.
     *
     * @param startChar String that contains the start character.
     * @param endChar String that contains the end character.
     * @param indentSize Numeric value that contains the number of indentation
     * characters should be used.
     */
    public BaseIndent(String startChar, String endChar, Integer indentSize){
        this(startChar, endChar, indentSize, Constants.DEFAULT_INDENT_CHARACTER);
    }
    
    /**
     * Constructor - Defines the indentation rule.
     *
     * @param startChar String that contains the start character.
     * @param endChar String that contains the end character.
     * @param indentSize Numeric value that contains the number of indentation
     * characters should be used.
     * @param backAfterEndChar Indicates if the indentation should be cleared
     * after match the end character.
     */
    public BaseIndent(String startChar, String endChar, Integer indentSize, boolean backAfterEndChar){
        this(startChar, endChar, indentSize);
        
        setBackAfterEndChar(backAfterEndChar);
    }
    
    /**
     * Constructor - Defines the indentation rule.
     *
     * @param startChar String that contains the start character.
     * @param endChar String that contains the end character.
     * @param backAfterEndChar Indicates if the indentation should be cleared
     * after match the end character.
     */
    public BaseIndent(String startChar, String endChar, boolean backAfterEndChar){
        this(startChar, endChar);
        
        setBackAfterEndChar(backAfterEndChar);
    }
    
    /**
     * Constructor - Defines the indentation rule.
     *
     * @param startChar String that contains the start character.
     * @param endChar String that contains the end character.
     * @param indentSize Numeric value that contains the number of indentation
     * characters should be used.
     * @param indentChar String that contains the indentation character.
     */
    public BaseIndent(String startChar, String endChar, Integer indentSize, String indentChar){
        this(startChar, endChar, indentSize, indentChar, false);
    }
    
    /**
     * Constructor - Defines the indentation rule.
     *
     * @param startChar String that contains the start character.
     * @param endChar String that contains the end character.
     * @param indentCount Numeric value that contains the number of indentation
     * characters should be used.
     * @param indentChar String that contains the indentation character.
     * @param backAfterEndChar Indicates if the indentation should be cleared
     * after match the end character.
     */
    public BaseIndent(String startChar, String endChar, Integer indentCount, String indentChar, boolean backAfterEndChar){
        super();
        
        setStartChar(startChar);
        setEndChar(endChar);
        setIndentSize(indentCount);
        setIndentChar(indentChar);
        setBackAfterEndChar(backAfterEndChar);
    }
    
    /**
     * Indicates if the indentation should be cleared after match the end
     * character.
     *
     * @return True/False
     */
    public boolean backAfterEndChar(){
        return this.backAfterEndChar;
    }
    
    /**
     * Defines if the indentation should be cleared after match the end
     * character.
     *
     * @param backAfterEndChar True/False
     */
    protected void setBackAfterEndChar(boolean backAfterEndChar){
        this.backAfterEndChar = backAfterEndChar;
    }
    
    /**
     * Returns the end character for indentation.
     *
     * @return String that contains the character.
     */
    public String getEndChar(){
        return this.endChar;
    }
    
    /**
     * Defines the end character for indentation.
     *
     * @param endChar String that contains the character.
     */
    protected void setEndChar(String endChar){
        this.endChar = endChar;
    }
    
    /**
     * Returns the start character for indentation.
     *
     * @return String that contains the character.
     */
    public String getStartChar(){
        return this.startChar;
    }
    
    /**
     * Defines the start character for indentation.
     *
     * @param startChar String that contains the character.
     */
    protected void setStartChar(String startChar){
        this.startChar = startChar;
    }
    
    /**
     * Returns the number of indentation characters should be used.
     *
     * @return Numeric value that contains the number of indentation characters
     * should be used.
     */
    public Integer getIndentSize(){
        return this.indentSize;
    }
    
    /**
     * Defines the amount of indentation characters should be used.
     *
     * @param indentSize Numeric value that contains the number of indentation
     * characters should be used.
     */
    protected void setIndentSize(Integer indentSize){
        this.indentSize = indentSize;
    }
    
    /**
     * Returns the indentation character.
     *
     * @return String that contains the character.
     */
    public String getIndentChar(){
        return this.indentChar;
    }
    
    /**
     * Defines the indentation character.
     *
     * @param indentChar String that contains the character.
     */
    protected void setIndentChar(String indentChar){
        this.indentChar = indentChar;
    }
}