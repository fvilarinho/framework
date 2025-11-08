package br.com.concepting.framework.util.types;

/**
 * Class that defines the types of text inputs.
 *
 * @author fvilarinho
 * @since 3.5.0
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
public enum InputType{
    /**
     * No input type defined.
     */
    NONE,
    
    /**
     * Capitalize words.
     */
    CAPITALIZE,
    
    /**
     * All words in uppercase.
     */
    UPPERCASE,
    
    /**
     * All words in lowercase.
     */
    LOWERCASE
}