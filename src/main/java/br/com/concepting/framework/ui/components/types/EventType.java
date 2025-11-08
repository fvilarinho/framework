package br.com.concepting.framework.ui.components.types;

import br.com.concepting.framework.util.StringUtil;

/**
 * Class that defines the types of events supported by the components.
 *
 * @author fvilarinho
 * @since 3.0.0
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
public enum EventType{
    /**
     * On blur event.
     */
    ON_BLUR,
    
    /**
     * On focus event.
     */
    ON_FOCUS,
    
    /**
     * On select event.
     */
    ON_SELECT,
    
    /**
     * On unselect event.
     */
    ON_UN_SELECT,
    
    /**
     * On expand event.
     */
    ON_EXPAND,
    
    /**
     * On collapse event.
     */
    ON_COLLAPSE,
    
    /**
     * On change event.
     */
    ON_CHANGE,
    
    /**
     * On click event.
     */
    ON_CLICK,
    
    /**
     * On key press event.
     */
    ON_KEY_PRESS,
    
    /**
     * On key up event.
     */
    ON_KEY_UP,
    
    /**
     * On key down event.
     */
    ON_KEY_DOWN,
    
    /**
     * On mouse over event.
     */
    ON_MOUSE_OVER,
    
    /**
     * On mouse out event.
     */
    ON_MOUSE_OUT,
    
    /**
     * On trigger event.
     */
    ON_TRIGGER;
    
    /**
     * Returns the identifier of the event.
     *
     * @return String that contains the identifier of the event.
     */
    public String getId(){
        return StringUtil.normalize(toString());
    }
}