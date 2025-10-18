package br.com.concepting.framework.ui.components.types;

/**
 * Class that defines the actions of the pager component.
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
public enum PagerActionType{
    /**
     * Move to the first page.
     */
    FIRST_PAGE,
    
    /**
     * Move to the previous page.
     */
    PREVIOUS_PAGE,
    
    /**
     * Move to the next page.
     */
    NEXT_PAGE,
    
    /**
     * Move to the last page.
     */
    LAST_PAGE,
    
    /**
     * Refresh the current page.
     */
    REFRESH_PAGE
}