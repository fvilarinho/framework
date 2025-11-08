package br.com.concepting.framework.model.types;

/**
 * Class that defines the types of compare conditions.
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
public enum ConditionType{
    /**
     * No condition.
     */
    NONE,
    
    /**
     * Equal condition.
     */
    EQUAL("="),
    
    /**
     * Not equal condition.
     */
    NOT_EQUAL("<>"),
    
    /**
     * Greater than equal condition.
     */
    GREATER_THAN_EQUAL(">="),
    
    /**
     * Greater than condition.
     */
    GREATER_THAN(">"),
    
    /**
     * Less than equal condition.
     */
    LESS_THAN_EQUAL("<="),
    
    /**
     * Less than condition.
     */
    LESS_THAN("<"),
    
    /**
     * Starts with condition.
     */
    STARTS_WITH("like"),
    
    /**
     * Ends with condition.
     */
    ENDS_WITH("like"),
    
    /**
     * Not contains condition.
     */
    NOT_CONTAINS("not like"),
    
    /**
     * In condition.
     */
    IN("in"),
    
    /**
     * Not in condition.
     */
    NOT_IN("not in"),
    
    /**
     * Contains condition.
     */
    CONTAINS("like"),
    
    /**
     * Between condition.
     */
    BETWEEN("between"),
    
    /**
     * Phonetic condition.
     */
    PHONETIC,
    
    /**
     * Is null condition.
     */
    IS_NULL("is null"),
    
    /**
     * Is not a null condition.
     */
    IS_NOT_NULL("is not null");
    
    private String operator = "";
    
    /**
     * Constructor - Defines the condition.
     */
    ConditionType(){
    }
    
    /**
     * Constructor - Defines the condition.
     *
     * @param operator String that contains the condition operator.
     */
    ConditionType(String operator){
        this();
        
        setOperator(operator);
    }
    
    /**
     * Returns the condition operator.
     *
     * @return String that contains the operator.
     */
    public String getOperator(){
        return this.operator;
    }
    
    /**
     * Defines the condition operator.
     *
     * @param operator String that contains the operator.
     */
    private void setOperator(String operator){
        this.operator = operator;
    }
}