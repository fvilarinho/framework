package br.com.concepting.framework.ui.controller;

import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.controller.SystemController;
import br.com.concepting.framework.controller.types.ScopeType;
import br.com.concepting.framework.ui.components.ActionFormComponent;
import br.com.concepting.framework.ui.components.types.PagerActionType;
import br.com.concepting.framework.ui.constants.UIConstants;
import br.com.concepting.framework.util.NumberUtil;
import br.com.concepting.framework.util.PropertyUtil;
import br.com.concepting.framework.util.types.SortOrderType;

import javax.servlet.http.Cookie;
import java.text.ParseException;
import java.util.List;

/**
 * Class that defines the UI controller.
 *
 * @author fvilarinho
 * @since 3.1.0
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
public class UIController{
    private final SystemController systemController;
    
    /**
     * Constructor - Initialize the controller.
     *
     * @param systemController Instance that contains the system controller.
     */
    public UIController(SystemController systemController){
        super();
        
        this.systemController = systemController;
    }
    
    /**
     * Indicates if the UI page has its CSS and JavaScripts imports.
     *
     * @return True/False.
     */
    public boolean hasPageImports(){
        StringBuilder pageImportsId = new StringBuilder();
        
        pageImportsId.append(this.systemController.getURL());
        pageImportsId.append("-");
        pageImportsId.append(UIConstants.PAGE_IMPORTS_ATTRIBUTE_ID);
        
        return (this.systemController.getAttribute(pageImportsId.toString(), ScopeType.REQUEST) != null);
    }
    
    /**
     * Defines if the UI page has its CSS and JavaScripts imports.
     *
     * @param value True/False.
     */
    public void hasPageImports(boolean value){
        StringBuilder pageImportsId = new StringBuilder();
        
        pageImportsId.append(this.systemController.getURL());
        pageImportsId.append("-");
        pageImportsId.append(UIConstants.PAGE_IMPORTS_ATTRIBUTE_ID);
        
        this.systemController.setAttribute(pageImportsId.toString(), value, ScopeType.REQUEST);
    }
    
    /**
     * Indicates if the UI page has its JavaScript events.
     *
     * @return True/False.
     */
    public boolean hasPageEvents(){
        return (this.systemController.getAttribute(UIConstants.PAGE_EVENTS_ATTRIBUTE_ID, ScopeType.REQUEST) != null);
    }
    
    /**
     * Defines if the UI page has its JavaScript events.
     *
     * @param value True/False.
     */
    public void hasPageEvents(boolean value){
        this.systemController.setAttribute(UIConstants.PAGE_EVENTS_ATTRIBUTE_ID, value, ScopeType.REQUEST);
    }
    
    /**
     * Indicates if the UI page has its component instantiated.
     *
     * @return True/False.
     */
    public boolean hasPageComponentInstance(){
        return (this.systemController.getAttribute(UIConstants.PAGE_COMPONENT_ATTRIBUTE_ID, ScopeType.REQUEST) != null);
    }
    
    /**
     * Indicates if the UI page has its component instantiated.
     *
     * @param hasPageComponent True/False.
     */
    public void hasPageComponentInstance(boolean hasPageComponent){
        this.systemController.setAttribute(UIConstants.PAGE_COMPONENT_ATTRIBUTE_ID, hasPageComponent, ScopeType.REQUEST);
    }
    
    /**
     * Returns the instance of the form component.
     *
     * @param actionFormName String that contains the identifier of the form.
     * @return Instance that contains the form component.
     */
    public ActionFormComponent getActionFormComponentInstance(String actionFormName){
        if(actionFormName != null && !actionFormName.isEmpty()){
            StringBuilder key = new StringBuilder();
            
            key.append(actionFormName);
            key.append(".");
            key.append(UIConstants.ACTION_FORM_COMPONENT_ATTRIBUTE_ID);
            
            return this.systemController.getAttribute(key.toString(), ScopeType.REQUEST);
        }
        
        return null;
    }
    
    /**
     * Defines the instance of the form component.
     *
     * @param actionFormName String that contains the identifier of the form.
     * @param actionFormInstance Instance that contains the form component.
     */
    public void setActionFormComponentInstance(String actionFormName, ActionFormComponent actionFormInstance){
        if(actionFormName != null && !actionFormName.isEmpty()){
            StringBuilder key = new StringBuilder();
            
            key.append(actionFormName);
            key.append(".");
            key.append(UIConstants.ACTION_FORM_COMPONENT_ATTRIBUTE_ID);
            
            this.systemController.setAttribute(key.toString(), actionFormInstance, ScopeType.REQUEST);
        }
    }
    
    /**
     * Returns the identifier of the current guide.
     *
     * @param name String that contains the identifier of the component.
     * @return String that contains the identifier of the current guide.
     */
    public String getCurrentGuide(String name){
        if(name != null && !name.isEmpty()){
            StringBuilder key = new StringBuilder();
            
            key.append(name);
            key.append(".");
            key.append(UIConstants.CURRENT_GUIDE_ATTRIBUTE_ID);
            
            return this.systemController.getParameterValue(key.toString());
        }
        
        return null;
    }
    
    /**
     * Returns the identifier of the current tree view node.
     *
     * @param name String that contains the identifier of the component.
     * @return String that contains the identifier of the tree view node.
     */
    public String getCurrentTreeViewNode(String name){
        if(name != null && !name.isEmpty()){
            StringBuilder key = new StringBuilder();
            
            key.append(name);
            key.append(".");
            key.append(UIConstants.CURRENT_TREE_VIEW_NODE_ATTRIBUTE_ID);
            
            return this.systemController.getParameterValue(key.toString());
        }
        
        return null;
    }
    
    /**
     * Indicates if a node of the tree view is expanded.
     *
     * @param name String that contains the identifier of the component.
     * @return True/False.
     */
    public boolean isTreeViewNodeExpanded(String name){
        if(name != null && !name.isEmpty()){
            StringBuilder key = new StringBuilder();
            
            key.append(name);
            key.append(".");
            key.append(UIConstants.IS_TREE_VIEW_NODE_EXPANDED_ATTRIBUTE_ID);
            
            return Boolean.parseBoolean(this.systemController.getParameterValue(key.toString()));
        }
        
        return false;
    }
    
    /**
     * Returns the list of current sections.
     *
     * @param name String that contains the identifier of the component.
     * @return List that contains the identifiers of the current sections.
     */
    public List<String> getCurrentSections(String name){
        if(name != null && !name.isEmpty()){
            StringBuilder key = new StringBuilder();
            
            key.append(name);
            key.append(".");
            key.append(UIConstants.CURRENT_SECTIONS_ATTRIBUTE_ID);
            
            String[] values = this.systemController.getParameterValues(key.toString());
            
            if(values != null && values.length > 0){
                List<String> result = null;
                
                for(String value: values){
                    if(value != null && !value.isEmpty()){
                        if(result == null)
                            result = PropertyUtil.instantiate(Constants.DEFAULT_LIST_CLASS);

                        if(result != null)
                            result.add(value);
                    }
                }
                
                return result;
            }
        }
        
        return null;
    }
    
    /**
     * Returns the number of items per page of the pager component.
     *
     * @param actionFormName String that contains the identifier of the form.
     * @param name String that contains the identifier of the component.
     * @param defaultItemsPerPage Numeric value that contains the number of the
     * default items per page.
     * @return Numeric value that contains the number of items per page.
     */
    public int getPagerItemsPerPage(String actionFormName, String name, int defaultItemsPerPage){
        int itemsPerPage;

        if(actionFormName != null && !actionFormName.isEmpty() && name != null && !name.isEmpty()){
            StringBuilder key = new StringBuilder();
            
            key.append(actionFormName);
            key.append(".");
            key.append(name);
            key.append(".");
            key.append(UIConstants.PAGER_ITEMS_PER_PAGE_ATTRIBUTE_ID);
            
            String itemsPerPageBuffer = this.systemController.getParameterValue(key.toString());

            try{
                itemsPerPage = NumberUtil.parseInt(itemsPerPageBuffer);

                if(itemsPerPage <= 0)
                    itemsPerPage = UIConstants.DEFAULT_PAGER_ITEMS_PER_PAGE;
            }
            catch(ParseException e1) {
                Cookie cookie = this.systemController.getCookie(key.toString());

                try {
                    if (cookie != null)
                        itemsPerPage = NumberUtil.parseInt(cookie.getValue());
                    else
                        itemsPerPage = UIConstants.DEFAULT_PAGER_ITEMS_PER_PAGE;
                }
                catch (ParseException e2){
                    itemsPerPage = UIConstants.DEFAULT_PAGER_ITEMS_PER_PAGE;
                }
            }

            this.systemController.addCookie(key.toString(), String.valueOf(itemsPerPage), true);

            return itemsPerPage;
        }
        else
            itemsPerPage = UIConstants.DEFAULT_PAGER_ITEMS_PER_PAGE;
        
        return itemsPerPage;
    }
    
    /**
     * Returns the action of the pager component.
     *
     * @param actionFormName String that contains the identifier of the form.
     * @param name String that contains the identifier of the component.
     * @return Instance that contains the pager action.
     */
    public PagerActionType getPagerAction(String actionFormName, String name){
        if(actionFormName != null && !actionFormName.isEmpty() && name != null && !name.isEmpty()){
            StringBuilder key = new StringBuilder();
            
            key.append(actionFormName);
            key.append(".");
            key.append(name);
            key.append(".");
            key.append(UIConstants.PAGER_ACTION_ATTRIBUTE_ID);
            
            try{
                String pagerActionType = this.systemController.getParameterValue(key.toString());
                
                if(pagerActionType == null || pagerActionType.isEmpty())
                    return PagerActionType.REFRESH_PAGE;
                
                return PagerActionType.valueOf(pagerActionType.toUpperCase());
            }
            catch(IllegalArgumentException e){
                return PagerActionType.REFRESH_PAGE;
            }
        }
        
        return PagerActionType.REFRESH_PAGE;
    }
    
    /**
     * Returns the number of the current page.
     *
     * @param actionFormName String that contains the identifier of the form.
     * @param name String that contains the identifier of the component.
     * @return Numeric value that contains the current page.
     */
    public int getPagerCurrentPage(String actionFormName, String name){
        int currentPage = 1;

        if(actionFormName != null && !actionFormName.isEmpty() && name != null && !name.isEmpty()){
            StringBuilder key = new StringBuilder();
            
            key.append(actionFormName);
            key.append(".");
            key.append(name);
            key.append(".");
            key.append(UIConstants.PAGER_CURRENT_PAGE_ATTRIBUTE_ID);
            
            try{
                currentPage = NumberUtil.parseInt(this.systemController.getParameterValue(key.toString()));
            }
            catch(ParseException ignored){
            }

            if(currentPage < 1)
                currentPage = 1;
        }
        
        return currentPage;
    }
    
    /**
     * Returns the zoom level of the map component.
     *
     * @param actionFormName String that contains the identifier of the form.
     * @param name String that contains the identifier of the component.
     * @param defaultZoom Numeric value that contains the default zoom.
     * @return Numeric value that contains the zoom level.
     */
    public int getMapsZoom(String actionFormName, String name, int defaultZoom){
        if(actionFormName != null && !actionFormName.isEmpty() && name != null && !name.isEmpty()){
            StringBuilder key = new StringBuilder();
            
            key.append(actionFormName);
            key.append(".");
            key.append(name);
            key.append(".");
            key.append(UIConstants.MAPS_ZOOM_ATTRIBUTE_ID);
            
            try{
                int zoom = NumberUtil.parseInt(this.systemController.getParameterValue(key.toString()));

                if(zoom <= 0)
                    zoom = UIConstants.DEFAULT_MAPS_ZOOM;

                return zoom;
            }
            catch(ParseException e){
                return UIConstants.DEFAULT_MAPS_ZOOM;
            }
        }
        
        return UIConstants.DEFAULT_MAPS_ZOOM;
    }
    
    /**
     * Returns the identifier of the sort property.
     *
     * @param name String that contains the identifier of the component.
     * @return String that contains the identifier of the sort property.
     */
    public String getSortProperty(String name){
        if(name != null && !name.isEmpty()){
            StringBuilder key = new StringBuilder();
            
            key.append(name);
            key.append(".");
            key.append(UIConstants.SORT_PROPERTY_ATTRIBUTE_ID);
            
            return this.systemController.getParameterValue(key.toString());
        }
        
        return null;
    }
    
    /**
     * Returns the type of the sort.
     *
     * @param name String that contains the identifier of the component.
     * @return Instance that contains the type of the sort.
     */
    public SortOrderType getSortOrder(String name){
        if(name != null && !name.isEmpty()){
            StringBuilder key = new StringBuilder();
            
            key.append(name);
            key.append(".");
            key.append(UIConstants.SORT_ORDER_ATTRIBUTE_ID);
            
            try{
                String sortOrder = this.systemController.getParameterValue(key.toString());
                
                if(sortOrder != null && !sortOrder.isEmpty())
                    return SortOrderType.valueOf(sortOrder.toUpperCase());
            }
            catch(IllegalArgumentException ignored){
            }
        }
        
        return Constants.DEFAULT_SORT_ORDER_TYPE;
    }
    
    /**
     * Returns the font name of the rich text area component.
     *
     * @param name String that contains the identifier of the component.
     * @return String that contains the font name.
     */
    public String getRichTextAreaFontName(String name){
        if(name != null && !name.isEmpty()){
            StringBuilder key = new StringBuilder();
            
            key.append(name);
            key.append(".");
            key.append(UIConstants.RICH_TEXT_AREA_TOOLBAR_FONT_NAME_ATTRIBUTE_ID);
            
            String result = this.systemController.getParameterValue(key.toString());
            
            if(result != null && !result.isEmpty())
                return result;
        }
        
        return UIConstants.DEFAULT_RICH_TEXT_AREA_FONT_NAME;
    }
    
    /**
     * Returns the font size of the rich text area component.
     *
     * @param name String that contains the identifier of the component.
     * @return Numeric value that contains the font size.
     */
    public int getRichTextAreaFontSize(String name){
        int result;

        if(name != null && !name.isEmpty()){
            StringBuilder key = new StringBuilder();
            
            key.append(name);
            key.append(".");
            key.append(UIConstants.RICH_TEXT_AREA_TOOLBAR_FONT_SIZE_ATTRIBUTE_ID);

            try{
                result = NumberUtil.parseInt(this.systemController.getParameterValue(key.toString()));
            }
            catch(ParseException e){
                result = UIConstants.DEFAULT_RICH_TEXT_AREA_FONT_SIZE;
            }
        }
        else
            result = UIConstants.DEFAULT_RICH_TEXT_AREA_FONT_SIZE;

        return result;
    }
    
    /**
     * Returns the font color of the rich text area component.
     *
     * @param name String that contains the identifier of the component.
     * @return String that contains the font color.
     */
    public String getRichTextAreaFontColor(String name){
        if(name != null && !name.isEmpty()){
            StringBuilder key = new StringBuilder();
            
            key.append(name);
            key.append(".");
            key.append(UIConstants.RICH_TEXT_AREA_TOOLBAR_FONT_COLOR_ATTRIBUTE_ID);
            
            String result = this.systemController.getParameterValue(key.toString());
            
            if(result != null && !result.isEmpty())
                return result;
        }
        
        return UIConstants.DEFAULT_RICH_TEXT_AREA_FONT_COLOR;
    }
    
    /**
     * Returns the background color of the rich text area component.
     *
     * @param name String that contains the identifier of the component.
     * @return String that contains the font name.
     */
    public String getRichTextAreaBackgroundColor(String name){
        if(name != null && !name.isEmpty()){
            StringBuilder key = new StringBuilder();
            
            key.append(name);
            key.append(".");
            key.append(UIConstants.RICH_TEXT_AREA_TOOLBAR_BACKGROUND_COLOR_ATTRIBUTE_ID);
            
            String result = this.systemController.getParameterValue(key.toString());
            
            if(result != null && !result.isEmpty())
                return result;
        }
        
        return UIConstants.DEFAULT_RICH_TEXT_AREA_BACKGROUND_COLOR;
    }
}