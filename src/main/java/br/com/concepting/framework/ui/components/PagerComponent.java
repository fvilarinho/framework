package br.com.concepting.framework.ui.components;

import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.ui.components.types.PagerActionType;
import br.com.concepting.framework.ui.constants.UIConstants;
import br.com.concepting.framework.ui.controller.UIController;
import br.com.concepting.framework.util.types.AlignmentType;
import br.com.concepting.framework.util.types.ComponentType;

import javax.servlet.jsp.JspException;
import java.util.Collection;

/**
 * Class that defines the pager component.
 *
 * @author fvilarinho
 * @since 3.0.0
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
public class PagerComponent extends BaseOptionsPropertyComponent{
    private static final long serialVersionUID = 6880028362277325178L;
    
    private Boolean showItemsPerPage = null;
    private Integer itemsPerPage = null;
    private Integer currentPage = null;
    private Integer pages = null;
    private String updateViews = null;
    
    /**
     * Returns the current page.
     *
     * @return Numeric value that defines the current page.
     */
    protected Integer getCurrentPage(){
        return this.currentPage;
    }
    
    /**
     * Defines the current page.
     *
     * @param currentPage Numeric value that defines the current page.
     */
    protected void setCurrentPage(Integer currentPage){
        this.currentPage = currentPage;
    }
    
    /**
     * Returns the number of pages.
     *
     * @return Numeric value that contains the pages.
     */
    protected Integer getPages(){
        return this.pages;
    }
    
    /**
     * Defines the number of pages.
     *
     * @param pages Numeric value that contains the pages.
     */
    protected void setPages(Integer pages){
        this.pages = pages;
    }
    
    /**
     * Returns the identifiers of the views that should be updated.
     *
     * @return String that contains the identifiers of the views.
     */
    public String getUpdateViews(){
        return this.updateViews;
    }
    
    /**
     * Defines the identifiers of the views that should be updated.
     *
     * @param updateViews String that contains the identifiers of the views.
     */
    public void setUpdateViews(String updateViews){
        this.updateViews = updateViews;
    }
    
    /**
     * Indicates if the component that defines the number of items per page
     * should be shown.
     *
     * @return True/False.
     */
    public Boolean getShowItemsPerPage(){
        return this.showItemsPerPage;
    }
    
    /**
     * Indicates if the component that defines the number of items per page
     * should be shown.
     *
     * @return True/False.
     */
    public Boolean showItemsPerPage(){
        return this.showItemsPerPage;
    }
    
    /**
     * Defines if the component that defines the number of items per page should
     * be shown.
     *
     * @param showItemsPerPage True/False.
     */
    public void setShowItemsPerPage(Boolean showItemsPerPage){
        this.showItemsPerPage = showItemsPerPage;
    }
    
    /**
     * Returns the number of items per page.
     *
     * @return Numeric value that contains the number of items per page.
     */
    public Integer getItemsPerPage(){
        return this.itemsPerPage;
    }
    
    /**
     * Defines the number of items per page.
     *
     * @param itemsPerPage Numeric value that contains the number of items per
     * page.
     */
    public void setItemsPerPage(Integer itemsPerPage){
        this.itemsPerPage = itemsPerPage;
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#buildName()
     */
    protected void buildName() throws InternalErrorException{
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#buildLabel()
     */
    protected void buildLabel() throws InternalErrorException{
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#buildTooltip()
     */
    protected void buildTooltip() throws InternalErrorException{
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BasePropertyComponent#buildAlignment()
     */
    protected void buildAlignment() throws InternalErrorException{
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BasePropertyComponent#buildEvents()
     */
    protected void buildEvents() throws InternalErrorException{
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BaseOptionsPropertyComponent#buildRestrictions()
     */
    protected void buildRestrictions() throws InternalErrorException{
        if(this.showItemsPerPage == null)
            this.showItemsPerPage = true;
        
        super.buildRestrictions();
    }
    
    /**
     * Refresh the page indexes.
     *
     * @throws InternalErrorException Occurs when was not possible to execute
     * the operation.
     */
    protected void refreshPageIndexes() throws InternalErrorException{
        UIController uiController = getUIController();
        String actionFormName = getActionFormName();
        String name = getName();
        
        if(uiController == null || actionFormName == null || actionFormName.length() == 0 || name == null || name.length() == 0)
            return;
        
        PagerActionType pagerAction = uiController.getPagerAction(actionFormName, name);
        
        this.itemsPerPage = uiController.getPagerItemsPerPage(actionFormName, name, this.itemsPerPage);
        this.currentPage = uiController.getPagerCurrentPage(actionFormName, name);
        
        Collection<? extends BaseModel> datasetValues = getDatasetValues();
        Integer mod = (datasetValues != null && this.itemsPerPage != null ? (datasetValues.size() % this.itemsPerPage) : 0);
        
        this.pages = (datasetValues != null && datasetValues.size() > 0 ? (datasetValues.size() / this.itemsPerPage) : 1);
        
        if(mod > 0)
            if(this.pages != null)
                this.pages++;
        
        if(this.pages == null || this.pages == 0)
            this.pages = 1;
        
        if(pagerAction == null)
            pagerAction = PagerActionType.REFRESH_PAGE;
        
        if(pagerAction == PagerActionType.FIRST_PAGE)
            this.currentPage = 1;
        else if(pagerAction == PagerActionType.PREVIOUS_PAGE)
            this.currentPage--;
        else if(pagerAction == PagerActionType.NEXT_PAGE)
            this.currentPage++;
        else if(pagerAction == PagerActionType.LAST_PAGE)
            this.currentPage = this.pages;
        
        if(this.currentPage == null || this.currentPage < 1)
            this.currentPage = 1;
        
        if(this.currentPage > this.pages)
            this.currentPage = this.pages;
        
        Integer datasetStartIndex = (this.currentPage - 1) * this.itemsPerPage;
        Integer datasetEndIndex = this.currentPage * this.itemsPerPage;
        
        if(datasetValues != null && datasetValues.size() > 0)
            if(datasetEndIndex > datasetValues.size())
                datasetEndIndex = datasetValues.size();
        
        setDatasetStartIndex(datasetStartIndex);
        setDatasetEndIndex(datasetEndIndex);
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BaseComponent#initialize()
     */
    protected void initialize() throws InternalErrorException{
        setComponentType(ComponentType.PAGER);
        
        GridPropertyComponent gridComponent = (GridPropertyComponent) findAncestorWithClass(this, GridPropertyComponent.class);
        
        if(gridComponent == null){
            try{
                gridComponent = (GridPropertyComponent) getParent();
            }
            catch(ClassCastException e){
            }
        }
        
        if(gridComponent != null){
            setPropertyInfo(gridComponent.getPropertyInfo());
            setActionFormName(gridComponent.getActionFormName());
            setName(gridComponent.getName());
            setDataset(gridComponent.getDataset());
            setDatasetScope(gridComponent.getDatasetScope());
            setDatasetValues(gridComponent.getDatasetValues());
        }
        
        super.initialize();
        
        if(gridComponent == null)
            setHasInvalidPropertyDefinition(true);
        
        Boolean hasInvalidPropertyDefinition = hasInvalidPropertyDefinition();
        Boolean render = render();
        
        if((hasInvalidPropertyDefinition == null || hasInvalidPropertyDefinition == false) && render != null && render){
            refreshPageIndexes();
            
            try{
                PagerComponent pagerComponent = (PagerComponent) this.clone();
                
                gridComponent.setPagerComponent(pagerComponent);
            }
            catch(CloneNotSupportedException e){
                throw new InternalErrorException(e);
            }
        }
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BasePropertyComponent#renderOpen()
     */
    protected void renderOpen() throws InternalErrorException{
        GridPropertyComponent gridComponent = (GridPropertyComponent) findAncestorWithClass(this, GridPropertyComponent.class);
        
        if(gridComponent != null){
            try{
                gridComponent = (GridPropertyComponent) getParent();
            }
            catch(ClassCastException e){
            }
        }
        
        String actionFormName = getActionFormName();
        String name = getName();
        Boolean render = render();
        Boolean hasInvalidPropertyDefinition = hasInvalidPropertyDefinition();
        
        if(gridComponent == null && actionFormName != null && actionFormName.length() > 0 && name != null && name.length() > 0 && render != null && render && (hasInvalidPropertyDefinition == null || !hasInvalidPropertyDefinition)){
            StringBuilder nameBuffer = new StringBuilder();
            
            nameBuffer.append(actionFormName);
            nameBuffer.append(".");
            nameBuffer.append(name);
            nameBuffer.append(".");
            nameBuffer.append(UIConstants.PAGER_ACTION_ATTRIBUTE_ID);
            
            HiddenPropertyComponent pagerActionPropertyComponent = new HiddenPropertyComponent();
            
            pagerActionPropertyComponent.setPageContext(this.pageContext);
            pagerActionPropertyComponent.setOutputStream(getOutputStream());
            pagerActionPropertyComponent.setActionFormName(actionFormName);
            pagerActionPropertyComponent.setName(nameBuffer.toString());
            pagerActionPropertyComponent.setValue(PagerActionType.REFRESH_PAGE);
            
            try{
                pagerActionPropertyComponent.doStartTag();
                pagerActionPropertyComponent.doEndTag();
            }
            catch(JspException e){
                throw new InternalErrorException(e);
            }
            
            nameBuffer.delete(0, nameBuffer.length());
            nameBuffer.append(actionFormName);
            nameBuffer.append(".");
            nameBuffer.append(name);
            nameBuffer.append(".");
            nameBuffer.append(UIConstants.PAGER_CURRENT_PAGE_ATTRIBUTE_ID);
            
            HiddenPropertyComponent pagerCurrentPagePropertyComponent = new HiddenPropertyComponent();
            
            pagerCurrentPagePropertyComponent.setPageContext(this.pageContext);
            pagerCurrentPagePropertyComponent.setOutputStream(getOutputStream());
            pagerCurrentPagePropertyComponent.setActionFormName(actionFormName);
            pagerCurrentPagePropertyComponent.setName(nameBuffer.toString());
            pagerCurrentPagePropertyComponent.setValue(this.currentPage);
            
            try{
                pagerCurrentPagePropertyComponent.doStartTag();
                pagerCurrentPagePropertyComponent.doEndTag();
            }
            catch(JspException e){
                throw new InternalErrorException(e);
            }
        }
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BaseComponent#renderBody()
     */
    protected void renderBody() throws InternalErrorException{
        GridPropertyComponent gridComponent = (GridPropertyComponent) findAncestorWithClass(this, GridPropertyComponent.class);
        
        if(gridComponent != null){
            try{
                gridComponent = (GridPropertyComponent) getParent();
            }
            catch(ClassCastException e){
            }
        }
        
        Boolean render = render();
        
        if(gridComponent == null && render != null && render){
            Boolean hasInvalidPropertyDefinition = hasInvalidPropertyDefinition();
            
            if(hasInvalidPropertyDefinition != null && hasInvalidPropertyDefinition)
                super.renderInvalidPropertyMessage();
            else{
                String styleClass = getStyleClass();
                
                print("<table");
                
                if(styleClass != null && styleClass.length() > 0){
                    print(" class=\"");
                    print(styleClass);
                    print("\"");
                }
                
                String style = getStyle();
                
                if(style != null && style.length() > 0){
                    print(" style=\"");
                    print(style);
                    
                    if(!style.endsWith(";"))
                        print(";");
                    
                    print("\"");
                }
                
                println(">");
                println("<tr>");
                
                if(this.showItemsPerPage != null && this.showItemsPerPage){
                    print("<td align=\"");
                    print(AlignmentType.CENTER);
                    println("\">");
                    
                    ItemsPerPagePropertyComponent itemsPerPageComponent = new ItemsPerPagePropertyComponent(this);
                    
                    try{
                        itemsPerPageComponent.doStartTag();
                        itemsPerPageComponent.doEndTag();
                    }
                    catch(JspException e){
                        throw new InternalErrorException(e);
                    }
                    
                    println("</td>");
                    println("<td width=\"5\"></td>");
                }
                
                print("<td align=\"");
                print(AlignmentType.CENTER);
                println("\">");
                
                FirstPageButtonComponent firstPageButtonComponent = new FirstPageButtonComponent(this);
                
                try{
                    firstPageButtonComponent.doStartTag();
                    firstPageButtonComponent.doEndTag();
                }
                catch(JspException e){
                    throw new InternalErrorException(e);
                }
                
                println("</td>");
                print("<td align=\"");
                print(AlignmentType.CENTER);
                println("\">");
                
                PreviousPageButtonComponent previousPageButtonComponent = new PreviousPageButtonComponent(this);
                
                try{
                    previousPageButtonComponent.doStartTag();
                    previousPageButtonComponent.doEndTag();
                }
                catch(JspException e){
                    throw new InternalErrorException(e);
                }
                
                println("</td>");
                print("<td align=\"");
                print(AlignmentType.CENTER);
                println("\">");
                
                print("<table class=\"");
                print(UIConstants.DEFAULT_PAGER_DISPLAY_STYLE_CLASS);
                println("\">");
                println("<tr>");
                print("<td align=\"");
                print(AlignmentType.CENTER);
                println("\">");
                print(this.currentPage);
                print("/");
                print(this.pages);
                println("</td>");
                println("</tr>");
                println("</table>");
                
                println("</td>");
                print("<td align=\"");
                print(AlignmentType.CENTER);
                println("\">");
                
                NextPageButtonComponent nextPageButtonComponent = new NextPageButtonComponent(this);
                
                try{
                    nextPageButtonComponent.doStartTag();
                    nextPageButtonComponent.doEndTag();
                }
                catch(JspException e){
                    throw new InternalErrorException(e);
                }
                
                println("</td>");
                print("<td align=\"");
                print(AlignmentType.CENTER);
                println("\">");
                
                LastPageButtonComponent lastPageButtonComponent = new LastPageButtonComponent(this);
                
                try{
                    lastPageButtonComponent.doStartTag();
                    lastPageButtonComponent.doEndTag();
                }
                catch(JspException e){
                    throw new InternalErrorException(e);
                }
                
                println("</td>");
                println("</tr>");
                println("</table>");
            }
        }
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BasePropertyComponent#renderClose()
     */
    protected void renderClose() throws InternalErrorException{
    }
    
    /**
     * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#clearAttributes()
     */
    protected void clearAttributes() throws InternalErrorException{
        super.clearAttributes();
        
        setShowItemsPerPage(null);
        setItemsPerPage(null);
        setCurrentPage(null);
        setPages(null);
        setUpdateViews(null);
    }
    
    /**
     * Class that defines the component for the first page button.
     *
     * @author fvilarinho
     * @since 1.0.0
     */
    private class FirstPageButtonComponent extends ButtonComponent{
        private static final long serialVersionUID = -7169801688944456509L;
        
        /**
         * Constructor - Initializes the component.
         *
         * @param pagerComponent Instance that contains the pager component.
         * @throws InternalErrorException Occurs when was not possible to
         * initialize the component.
         */
        public FirstPageButtonComponent(PagerComponent pagerComponent) throws InternalErrorException{
            super();
            
            if(pagerComponent != null){
                setPageContext(pagerComponent.getPageContext());
                setOutputStream(pagerComponent.getOutputStream());
                setActionFormName(pagerComponent.getActionFormName());
                setParent(pagerComponent);
            }
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#buildResources()
         */
        protected void buildResources() throws InternalErrorException{
            setResourcesId(UIConstants.DEFAULT_PAGER_RESOURCES_ID);
            setResourcesKey(UIConstants.DEFAULT_PAGER_FIRST_PAGE_BUTTON_ID);
            
            super.buildResources();
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.ButtonComponent#buildStyleClass()
         */
        protected void buildStyleClass() throws InternalErrorException{
            setStyleClass(UIConstants.DEFAULT_PAGER_FIRST_PAGE_BUTTON_STYLE_CLASS);
            
            super.buildStyleClass();
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.ButtonComponent#buildEvents()
         */
        protected void buildEvents() throws InternalErrorException{
            PagerComponent pagerComponent = (PagerComponent) getParent();
            String actionFormName = (pagerComponent != null ? pagerComponent.getActionFormName() : null);
            String name = (pagerComponent != null ? pagerComponent.getName() : null);
            Integer currentPage = (pagerComponent != null ? pagerComponent.getCurrentPage() : null);
            
            if(actionFormName != null && actionFormName.length() > 0 && name != null && name.length() > 0 && currentPage != null && currentPage > 1){
                StringBuilder onClick = new StringBuilder();
                
                onClick.append("moveToFirstPage('");
                onClick.append(name);
                onClick.append("', ");
                
                String updateViews = (pagerComponent != null ? pagerComponent.getUpdateViews() : null);
                
                if(updateViews != null && updateViews.length() > 0){
                    onClick.append("'");
                    onClick.append(updateViews);
                    onClick.append("'");
                }
                else
                    onClick.append(Constants.DEFAULT_NULL_ID);
                
                onClick.append(", '");
                onClick.append(actionFormName);
                onClick.append("');");
                
                setOnClick(onClick.toString());
            }
            
            super.buildEvents();
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.ButtonComponent#initialize()
         */
        protected void initialize() throws InternalErrorException{
            PagerComponent pagerComponent = (PagerComponent) getParent();
            
            if(pagerComponent != null && (pagerComponent.getCurrentPage() == null || pagerComponent.getCurrentPage() <= 1))
                setEnabled(false);
            
            super.initialize();
        }
    }
    
    /**
     * Class that defines the component for the previous page button.
     *
     * @author fvilarinho
     * @since 1.0.0
     */
    private class PreviousPageButtonComponent extends ButtonComponent{
        private static final long serialVersionUID = -1949430040045196611L;
        
        /**
         * Constructor - Initializes the component.
         *
         * @param pagerComponent Instance that contains the pager component.
         * @throws InternalErrorException Occurs when was not possible to
         * initialize the component.
         */
        public PreviousPageButtonComponent(PagerComponent pagerComponent) throws InternalErrorException{
            super();
            
            if(pagerComponent != null){
                setPageContext(pagerComponent.getPageContext());
                setOutputStream(pagerComponent.getOutputStream());
                setActionFormName(pagerComponent.getActionFormName());
                setParent(pagerComponent);
            }
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#buildResources()
         */
        protected void buildResources() throws InternalErrorException{
            setResourcesId(UIConstants.DEFAULT_PAGER_RESOURCES_ID);
            setResourcesKey(UIConstants.DEFAULT_PAGER_PREVIOUS_PAGE_BUTTON_ID);
            
            super.buildResources();
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.ButtonComponent#buildStyleClass()
         */
        protected void buildStyleClass() throws InternalErrorException{
            setStyleClass(UIConstants.DEFAULT_PAGER_PREVIOUS_PAGE_BUTTON_STYLE_CLASS);
            
            super.buildStyleClass();
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.ButtonComponent#buildEvents()
         */
        protected void buildEvents() throws InternalErrorException{
            PagerComponent pagerComponent = (PagerComponent) getParent();
            String actionFormName = (pagerComponent != null ? pagerComponent.getActionFormName() : null);
            String name = (pagerComponent != null ? pagerComponent.getName() : null);
            Integer currentPage = (pagerComponent != null ? pagerComponent.getCurrentPage() : null);
            
            if(actionFormName != null && actionFormName.length() > 0 && name != null && name.length() > 0 && currentPage != null && currentPage > 1){
                StringBuilder onClick = new StringBuilder();
                
                onClick.append("moveToPreviousPage('");
                onClick.append(name);
                onClick.append("', ");
                
                String updateViews = (pagerComponent != null ? pagerComponent.getUpdateViews() : null);
                
                if(updateViews != null && updateViews.length() > 0){
                    onClick.append("'");
                    onClick.append(updateViews);
                    onClick.append("'");
                }
                else
                    onClick.append(Constants.DEFAULT_NULL_ID);
                
                onClick.append(", '");
                onClick.append(actionFormName);
                onClick.append("');");
                
                setOnClick(onClick.toString());
            }
            
            super.buildEvents();
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.ButtonComponent#initialize()
         */
        protected void initialize() throws InternalErrorException{
            PagerComponent pagerComponent = (PagerComponent) getParent();
            
            if(pagerComponent != null && (pagerComponent.getCurrentPage() == null || pagerComponent.getCurrentPage() <= 1))
                setEnabled(false);
            
            super.initialize();
        }
    }
    
    /**
     * Class that defines the component for the next page button.
     *
     * @author fvilarinho
     * @since 1.0.0
     */
    private class NextPageButtonComponent extends ButtonComponent{
        private static final long serialVersionUID = 2747901438645534966L;
        
        /**
         * Constructor - Initializes the component.
         *
         * @param pagerComponent Instance that contains the pager component.
         * @throws InternalErrorException Occurs when was not possible to
         * initialize the component.
         */
        public NextPageButtonComponent(PagerComponent pagerComponent) throws InternalErrorException{
            super();
            
            if(pagerComponent != null){
                setPageContext(pagerComponent.getPageContext());
                setOutputStream(pagerComponent.getOutputStream());
                setActionFormName(pagerComponent.getActionFormName());
                setParent(pagerComponent);
            }
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#buildResources()
         */
        protected void buildResources() throws InternalErrorException{
            setResourcesId(UIConstants.DEFAULT_PAGER_RESOURCES_ID);
            setResourcesKey(UIConstants.DEFAULT_PAGER_NEXT_PAGE_BUTTON_ID);
            
            super.buildResources();
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.ButtonComponent#buildStyleClass()
         */
        protected void buildStyleClass() throws InternalErrorException{
            setStyleClass(UIConstants.DEFAULT_PAGER_NEXT_PAGE_BUTTON_STYLE_CLASS);
            
            super.buildStyleClass();
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.ButtonComponent#buildEvents()
         */
        protected void buildEvents() throws InternalErrorException{
            PagerComponent pagerComponent = (PagerComponent) getParent();
            String actionFormName = (pagerComponent != null ? pagerComponent.getActionFormName() : null);
            String name = (pagerComponent != null ? pagerComponent.getName() : null);
            Integer currentPage = (pagerComponent != null ? pagerComponent.getCurrentPage() : null);
            Integer pages = (pagerComponent != null ? pagerComponent.getPages() : null);
            
            if(actionFormName != null && actionFormName.length() > 0 && name != null && name.length() > 0 && currentPage != null && pages != null && currentPage < pages){
                StringBuilder onClick = new StringBuilder();
                
                onClick.append("moveToNextPage('");
                onClick.append(name);
                onClick.append("', ");
                
                String updateViews = (pagerComponent != null ? pagerComponent.getUpdateViews() : null);
                
                if(updateViews != null && updateViews.length() > 0){
                    onClick.append("'");
                    onClick.append(updateViews);
                    onClick.append("'");
                }
                else
                    onClick.append(Constants.DEFAULT_NULL_ID);
                
                onClick.append(", '");
                onClick.append(actionFormName);
                onClick.append("');");
                
                setOnClick(onClick.toString());
            }
            
            super.buildEvents();
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.ButtonComponent#initialize()
         */
        protected void initialize() throws InternalErrorException{
            PagerComponent pagerComponent = (PagerComponent) getParent();
            
            if(pagerComponent != null && pagerComponent.getCurrentPage() != null && pagerComponent.getPages() != null && pagerComponent.getCurrentPage() >= pagerComponent.getPages())
                setEnabled(false);
            
            super.initialize();
        }
    }
    
    /**
     * Class that defines the component for the last page button.
     *
     * @author fvilarinho
     * @since 1.0.0
     */
    private class LastPageButtonComponent extends ButtonComponent{
        private static final long serialVersionUID = -574994133651394709L;
        
        /**
         * Constructor - Initializes the component.
         *
         * @param pagerComponent Instance that contains the pager component.
         * @throws InternalErrorException Occurs when was not possible to
         * initialize the component.
         */
        public LastPageButtonComponent(PagerComponent pagerComponent) throws InternalErrorException{
            super();
            
            if(pagerComponent != null){
                setPageContext(pagerComponent.getPageContext());
                setOutputStream(pagerComponent.getOutputStream());
                setActionFormName(pagerComponent.getActionFormName());
                setParent(pagerComponent);
            }
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#buildResources()
         */
        protected void buildResources() throws InternalErrorException{
            setResourcesId(UIConstants.DEFAULT_PAGER_RESOURCES_ID);
            setResourcesKey(UIConstants.DEFAULT_PAGER_LAST_PAGE_BUTTON_ID);
            
            super.buildResources();
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.ButtonComponent#buildStyleClass()
         */
        protected void buildStyleClass() throws InternalErrorException{
            setStyleClass(UIConstants.DEFAULT_PAGER_LAST_PAGE_BUTTON_STYLE_CLASS);
            
            super.buildStyleClass();
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.ButtonComponent#buildEvents()
         */
        protected void buildEvents() throws InternalErrorException{
            PagerComponent pagerComponent = (PagerComponent) getParent();
            String actionFormName = (pagerComponent != null ? pagerComponent.getActionFormName() : null);
            String name = (pagerComponent != null ? pagerComponent.getName() : null);
            Integer currentPage = (pagerComponent != null ? pagerComponent.getCurrentPage() : null);
            Integer pages = (pagerComponent != null ? pagerComponent.getPages() : null);
            
            if(actionFormName != null && actionFormName.length() > 0 && name != null && name.length() > 0 && currentPage != null && pages != null && currentPage < pages){
                StringBuilder onClick = new StringBuilder();
                
                onClick.append("moveToLastPage('");
                onClick.append(name);
                onClick.append("', ");
                
                String updateViews = (pagerComponent != null ? pagerComponent.getUpdateViews() : null);
                
                if(updateViews != null && updateViews.length() > 0){
                    onClick.append("'");
                    onClick.append(updateViews);
                    onClick.append("'");
                }
                else
                    onClick.append(Constants.DEFAULT_NULL_ID);
                
                onClick.append(", '");
                onClick.append(actionFormName);
                onClick.append("');");
                
                setOnClick(onClick.toString());
            }
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.ButtonComponent#initialize()
         */
        protected void initialize() throws InternalErrorException{
            PagerComponent pagerComponent = (PagerComponent) getParent();
            
            if(pagerComponent != null && pagerComponent.getCurrentPage() != null && pagerComponent.getPages() != null && pagerComponent.getCurrentPage() >= pagerComponent.getPages())
                setEnabled(false);
            
            super.initialize();
        }
    }
    
    /**
     * Class that defines the component for the items per page.
     *
     * @author fvilarinho
     * @since 1.0.0
     */
    private class ItemsPerPagePropertyComponent extends TextPropertyComponent{
        private static final long serialVersionUID = 7830360215475249040L;
        
        /**
         * Constructor - Initializes the component.
         *
         * @param pagerComponent Instance that contains the pager component.
         * @throws InternalErrorException Occurs when was not possible to
         * initialize the component.
         */
        public ItemsPerPagePropertyComponent(PagerComponent pagerComponent) throws InternalErrorException{
            super();
            
            if(pagerComponent != null){
                setPageContext(pagerComponent.getPageContext());
                setActionFormName(pagerComponent.getActionFormName());
                setOutputStream(pagerComponent.getOutputStream());
                setParent(pagerComponent);
            }
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.BasePropertyComponent#buildResources()
         */
        protected void buildResources() throws InternalErrorException{
            setResourcesId(UIConstants.DEFAULT_PAGER_RESOURCES_ID);
            setResourcesKey(UIConstants.DEFAULT_PAGER_ITEMS_PER_PAGE_ID);
            
            super.buildResources();
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.TextPropertyComponent#buildEvents()
         */
        protected void buildEvents() throws InternalErrorException{
            PagerComponent pagerComponent = (PagerComponent) getParent();
            
            if(pagerComponent != null){
                StringBuilder onKeyPressContent = new StringBuilder();
                
                onKeyPressContent.append("if(getKeyPressed(event) == 13){ refreshPage('");
                onKeyPressContent.append(pagerComponent.getName());
                onKeyPressContent.append("', ");
                
                String updateViews = pagerComponent.getUpdateViews();
                
                if(updateViews != null && updateViews.length() > 0){
                    onKeyPressContent.append("'");
                    onKeyPressContent.append(updateViews);
                    onKeyPressContent.append("'");
                }
                else
                    onKeyPressContent.append(Constants.DEFAULT_NULL_ID);
                
                onKeyPressContent.append(", '");
                onKeyPressContent.append(pagerComponent.getActionFormName());
                onKeyPressContent.append("'); }");
                
                setOnKeyPress(onKeyPressContent.toString());
                
                super.buildEvents();
            }
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#buildName()
         */
        protected void buildName() throws InternalErrorException{
            PagerComponent pagerComponent = (PagerComponent) getParent();
            String actionFormName = (pagerComponent != null ? pagerComponent.getActionFormName() : null);
            String name = (pagerComponent != null ? pagerComponent.getName() : null);
            
            if(actionFormName != null && actionFormName.length() > 0 && name != null && name.length() > 0){
                StringBuilder nameBuffer = new StringBuilder();
                
                nameBuffer.append(actionFormName);
                nameBuffer.append(".");
                nameBuffer.append(name);
                nameBuffer.append(".");
                nameBuffer.append(UIConstants.PAGER_ITEMS_PER_PAGE_ATTRIBUTE_ID);
                
                setName(nameBuffer.toString());
            }
            
            super.buildName();
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.TextPropertyComponent#buildRestrictions()
         */
        protected void buildRestrictions() throws InternalErrorException{
            setSize(3);
            setMaximumLength(3);
            setMinimumValue(1);
            setMaximumValue(999);
            
            super.buildRestrictions();
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.BasePropertyComponent#buildAlignment()
         */
        protected void buildAlignment() throws InternalErrorException{
            setAlignmentType(AlignmentType.RIGHT);
            
            super.buildAlignment();
        }
        
        /**
         * @see br.com.concepting.framework.ui.components.TextPropertyComponent#initialize()
         */
        protected void initialize() throws InternalErrorException{
            PagerComponent pagerComponent = (PagerComponent) getParent();
            
            if(pagerComponent != null)
                setValue(pagerComponent.getItemsPerPage());
            
            super.initialize();
        }
    }
}