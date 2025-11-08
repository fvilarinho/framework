package br.com.concepting.framework.util.types;

import br.com.concepting.framework.util.StringUtil;

/**
 * Class that defines the types of the UI components.
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
public enum ComponentType{
    /**
     * Accordion component (Section set).
     */
    ACCORDION,
    
    /**
     * Button component.
     */
    BUTTON,
    
    /**
     * Clock component.
     */
    CLOCK,
    
    /**
     * Bread crumb component (Navigation history).
     */
    BREAD_CRUMB,
    
    /**
     * Check-box field component.
     */
    CHECK_BOX,
    
    /**
     * Color picker component.
     */
    COLOR_PICKER,
    
    /**
     * Dialog box component.
     */
    DIALOG_BOX,
    
    /**
     * Download component.
     */
    DOWNLOAD,
    
    /**
     * Data grid component.
     */
    GRID,
    
    /**
     * Data grid column component.
     */
    GRID_COLUMN,
    
    /**
     * Guide component.
     */
    GUIDE,
    
    /**
     * Guides component.
     */
    GUIDES,
    
    /**
     * Hidden field component.
     */
    HIDDEN,
    
    /**
     * Image component.
     */
    IMAGE,
    
    /**
     * Label component.
     */
    LABEL,
    
    /**
     * Hyperlink component.
     */
    LINK,
    
    /**
     * List of options' component.
     */
    LIST,
    
    /**
     * Login session box component.
     */
    LOGIN_SESSION_BOX,
    
    /**
     * Maps component.
     */
    MAPS,
    
    /**
     * Menu bar component.
     */
    MENU_BAR,
    
    /**
     * Menu item component.
     */
    MENU_ITEM,
    
    /**
     * Menu item separator component.
     */
    MENU_ITEM_SEPARATOR,
    
    /**
     * Message box component.
     */
    MESSAGE_BOX(DIALOG_BOX),
    
    /**
     * Option component.
     */
    OPTION,
    
    /**
     * Options component.
     */
    OPTIONS,
    
    /**
     * Pager component.
     */
    PAGER,
    
    /**
     * Password field component.
     */
    PASSWORD,
    
    /**
     * Progress bar component.
     */
    PROGRESS_BAR,
    
    /**
     * Radio box field component.
     */
    RADIO,
    
    /**
     * Search properties group component.
     */
    SEARCH_PROPERTIES_GROUP,
    
    /**
     * Section component.
     */
    SECTION,
    
    /**
     * Text box field component.
     */
    TEXT_BOX("TEXT"),
    
    /**
     * Slider bar component.
     */
    SLIDER_BAR(TEXT_BOX),
    
    /**
     * Spinner component.
     */
    SPINNER(TEXT_BOX),
    
    /**
     * Suggestion box field component.
     */
    SUGGESTION_BOX(TEXT_BOX),
    
    /**
     * Timer component.
     */
    TIMER,
    
    /**
     * Text area field component.
     */
    TEXT_AREA,
    
    /**
     * Calendar component.
     */
    CALENDAR(TEXT_BOX),
    
    /**
     * Tree view component.
     */
    TREE_VIEW,
    
    /**
     * Upload component.
     */
    UPLOAD("FILE");
    
    private String id;
    private String type;
    
    /**
     * Constructor - Initializes the component.
     */
    ComponentType(){
        String componentType = toString();
        
        setId(StringUtil.normalize(componentType));
        setType(StringUtil.replaceAll(componentType, "_", ""));
    }
    
    /**
     * Constructor - Initializes the component.
     *
     * @param componentType Instance that contains the component type.
     */
    ComponentType(ComponentType componentType){
        setId(StringUtil.normalize(componentType.toString()));
        setType(StringUtil.replaceAll(componentType.getType(), "_", ""));
    }
    
    /**
     * Constructor - Initializes the component.
     *
     * @param componentType String that contains the identifier of the component
     * type.
     */
    ComponentType(String componentType){
        setId(StringUtil.normalize(toString()));
        setType(StringUtil.replaceAll(componentType, "_", ""));
    }
    
    /**
     * Returns the component identifier.
     *
     * @return String that contains the identifier.
     */
    public String getId(){
        return this.id;
    }
    
    /**
     * Defines the component identifier.
     *
     * @param id String that contains the identifier.
     */
    public void setId(String id){
        this.id = id;
    }
    
    /**
     * Returns the component type.
     *
     * @return String that contains the type.
     */
    public String getType(){
        return this.type;
    }
    
    /**
     * Defines the component type.
     *
     * @param type String that contains the type.
     */
    public void setType(String type){
        this.type = type;
    }
}