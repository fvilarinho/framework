package br.com.concepting.framework.ui.components;

import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.ui.components.types.PagerActionType;
import br.com.concepting.framework.ui.constants.UIConstants;
import br.com.concepting.framework.ui.controller.UIController;
import br.com.concepting.framework.util.types.AlignmentType;
import br.com.concepting.framework.util.types.ComponentType;

import jakarta.servlet.jsp.JspException;

import java.io.Serial;
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
public class PagerComponent extends BaseOptionsPropertyComponent{
    @Serial
    private static final long serialVersionUID = 6880028362277325178L;
    
    private boolean showItemsPerPage = true;
    private int itemsPerPage = UIConstants.DEFAULT_PAGER_ITEMS_PER_PAGE;
    private int currentPage = 1;
    private int pages = 0;
    private String updateViews = null;
    
    /**
     * Returns the current page.
     *
     * @return Numeric value that defines the current page.
     */
    protected int getCurrentPage(){
        return this.currentPage;
    }
    
    /**
     * Defines the current page.
     *
     * @param currentPage Numeric value that defines the current page.
     */
    protected void setCurrentPage(int currentPage){
        this.currentPage = currentPage;
    }
    
    /**
     * Returns the number of pages.
     *
     * @return Numeric value that contains the pages.
     */
    protected int getPages(){
        return this.pages;
    }
    
    /**
     * Defines the number of pages.
     *
     * @param pages Numeric value that contains the pages.
     */
    protected void setPages(int pages){
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
    public boolean getShowItemsPerPage(){
        return this.showItemsPerPage;
    }
    
    /**
     * Indicates if the component that defines the number of items per page
     * should be shown.
     *
     * @return True/False.
     */
    public boolean showItemsPerPage(){
        return this.showItemsPerPage;
    }
    
    /**
     * Defines if the component that defines the number of items per page should
     * be shown.
     *
     * @param showItemsPerPage True/False.
     */
    public void setShowItemsPerPage(boolean showItemsPerPage){
        this.showItemsPerPage = showItemsPerPage;
    }
    
    /**
     * Returns the number of items per page.
     *
     * @return Numeric value that contains the number of items per page.
     */
    public int getItemsPerPage(){
        return this.itemsPerPage;
    }
    
    /**
     * Defines the number of items per page.
     *
     * @param itemsPerPage Numeric value that contains the number of items per
     * page.
     */
    public void setItemsPerPage(int itemsPerPage){
        this.itemsPerPage = itemsPerPage;
    }

    @Override
    protected void buildName() throws InternalErrorException{
    }

    @Override
    protected void buildLabel() throws InternalErrorException{
    }

    @Override
    protected void buildTooltip() throws InternalErrorException{
    }

    @Override
    protected void buildAlignment() throws InternalErrorException{
    }

    @Override
    protected void buildEvents() throws InternalErrorException{
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
        
        if(uiController == null || actionFormName == null || actionFormName.isEmpty() || name == null || name.isEmpty())
            return;
        
        PagerActionType pagerAction = uiController.getPagerAction(actionFormName, name);
        
        this.itemsPerPage = uiController.getPagerItemsPerPage(actionFormName, name, this.itemsPerPage);
        this.currentPage = uiController.getPagerCurrentPage(actionFormName, name);
        
        Collection<? extends BaseModel> datasetValues = getDatasetValues();
        int mod = ((datasetValues != null && !datasetValues.isEmpty() && this.itemsPerPage > 0) ? (datasetValues.size() % this.itemsPerPage) : 0);
        
        this.pages = ((datasetValues != null && !datasetValues.isEmpty() && this.itemsPerPage > 0) ? (datasetValues.size() / this.itemsPerPage) : 1);
        
        if(mod > 0)
            this.pages++;

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
        
        if(this.currentPage < 1)
            this.currentPage = 1;
        
        if(this.currentPage > this.pages)
            this.currentPage = this.pages;
        
        int datasetStartIndex = (this.currentPage - 1) * this.itemsPerPage;
        int datasetEndIndex = this.currentPage * this.itemsPerPage;
        
        if(datasetValues != null && !datasetValues.isEmpty())
            if(datasetEndIndex > datasetValues.size())
                datasetEndIndex = datasetValues.size();
        
        setDatasetStartIndex(datasetStartIndex);
        setDatasetEndIndex(datasetEndIndex);
    }

    @Override
    protected void initialize() throws InternalErrorException{
        setComponentType(ComponentType.PAGER);
        
        GridPropertyComponent gridComponent = (GridPropertyComponent) findAncestorWithClass(this, GridPropertyComponent.class);
        
        if(gridComponent == null){
            try{
                gridComponent = (GridPropertyComponent) getParent();
            }
            catch(ClassCastException ignored){
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
            setHasInvalidDefinition(true);
        
        boolean hasInvalidDefinition = hasInvalidDefinition();
        boolean render = render();
        
        if(!hasInvalidDefinition && render){
            refreshPageIndexes();
            
            try{
                PagerComponent pagerComponent = (PagerComponent) this.clone();

                if(gridComponent != null)
                    gridComponent.setPagerComponent(pagerComponent);
            }
            catch(CloneNotSupportedException e){
                throw new InternalErrorException(e);
            }
        }
    }

    @Override
    protected void renderOpen() throws InternalErrorException{
        GridPropertyComponent gridComponent = (GridPropertyComponent) findAncestorWithClass(this, GridPropertyComponent.class);
        
        if(gridComponent != null){
            try{
                gridComponent = (GridPropertyComponent) getParent();
            }
            catch(ClassCastException ignored){
            }
        }
        
        String actionFormName = getActionFormName();
        String name = getName();
        boolean render = render();
        boolean hasInvalidDefinition = hasInvalidDefinition();
        
        if(gridComponent == null && actionFormName != null && !actionFormName.isEmpty() && name != null && !name.isEmpty() && render && !hasInvalidDefinition){
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

    @Override
    protected void renderBody() throws InternalErrorException{
        GridPropertyComponent gridComponent = (GridPropertyComponent) findAncestorWithClass(this, GridPropertyComponent.class);
        
        if(gridComponent != null){
            try{
                gridComponent = (GridPropertyComponent) getParent();
            }
            catch(ClassCastException ignored){
            }
        }
        
        boolean render = render();
        
        if(gridComponent == null && render){
            boolean hasInvalidDefinition = hasInvalidDefinition();
            
            if(hasInvalidDefinition)
                super.renderInvalidDefinitionMessage();
            else{
                String styleClass = getStyleClass();
                
                print("<table");
                
                if(styleClass != null && !styleClass.isEmpty()){
                    print(" class=\"");
                    print(styleClass);
                    print("\"");
                }
                
                String style = getStyle();
                
                if(style != null && !style.isEmpty()){
                    print(" style=\"");
                    print(style);
                    
                    if(!style.endsWith(";"))
                        print(";");
                    
                    print("\"");
                }
                
                println(">");
                println("<tr>");
                
                if(this.showItemsPerPage){
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

    @Override
    protected void renderClose() throws InternalErrorException{
    }

    @Override
    protected void clearAttributes() throws InternalErrorException{
        super.clearAttributes();
        
        setShowItemsPerPage(true);
        setItemsPerPage(UIConstants.DEFAULT_PAGER_ITEMS_PER_PAGE);
        setCurrentPage(1);
        setPages(0);
        setUpdateViews(null);
    }
    
    /**
     * Class that defines the component for the first page button.
     *
     * @author fvilarinho
     * @since 1.0.0
     */
    private static class FirstPageButtonComponent extends ButtonComponent{
        @Serial
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
            
            setPageContext(pagerComponent.getPageContext());
            setOutputStream(pagerComponent.getOutputStream());
            setActionFormName(pagerComponent.getActionFormName());
            setParent(pagerComponent);
        }

        @Override
        protected void buildResources() throws InternalErrorException{
            setResourcesId(UIConstants.DEFAULT_PAGER_RESOURCES_ID);
            setResourcesKey(UIConstants.DEFAULT_PAGER_FIRST_PAGE_BUTTON_ID);
            
            super.buildResources();
        }

        @Override
        protected void buildStyleClass() throws InternalErrorException{
            setStyleClass(UIConstants.DEFAULT_PAGER_FIRST_PAGE_BUTTON_STYLE_CLASS);
            
            super.buildStyleClass();
        }

        @Override
        protected void buildEvents() throws InternalErrorException{
            PagerComponent pagerComponent = (PagerComponent) getParent();
            String actionFormName = pagerComponent.getActionFormName();
            String name = pagerComponent.getName();
            int currentPage = pagerComponent.getCurrentPage();
            
            if(actionFormName != null && !actionFormName.isEmpty() && name != null && !name.isEmpty() && currentPage > 1){
                StringBuilder onClick = new StringBuilder();
                
                onClick.append("moveToFirstPage('");
                onClick.append(name);
                onClick.append("', ");
                
                String updateViews = pagerComponent.getUpdateViews();
                
                if(updateViews != null && !updateViews.isEmpty()){
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

        @Override
        protected void initialize() throws InternalErrorException{
            PagerComponent pagerComponent = (PagerComponent) getParent();
            
            if(pagerComponent.getCurrentPage() <= 1)
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
    private static class PreviousPageButtonComponent extends ButtonComponent{
        @Serial
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
            
            setPageContext(pagerComponent.getPageContext());
            setOutputStream(pagerComponent.getOutputStream());
            setActionFormName(pagerComponent.getActionFormName());
            setParent(pagerComponent);
        }

        @Override
        protected void buildResources() throws InternalErrorException{
            setResourcesId(UIConstants.DEFAULT_PAGER_RESOURCES_ID);
            setResourcesKey(UIConstants.DEFAULT_PAGER_PREVIOUS_PAGE_BUTTON_ID);
            
            super.buildResources();
        }

        @Override
        protected void buildStyleClass() throws InternalErrorException{
            setStyleClass(UIConstants.DEFAULT_PAGER_PREVIOUS_PAGE_BUTTON_STYLE_CLASS);
            
            super.buildStyleClass();
        }

        @Override
        protected void buildEvents() throws InternalErrorException{
            PagerComponent pagerComponent = (PagerComponent) getParent();
            String actionFormName =pagerComponent.getActionFormName();
            String name = pagerComponent.getName();
            int currentPage = pagerComponent.getCurrentPage();
            
            if(actionFormName != null && !actionFormName.isEmpty() && name != null && !name.isEmpty() && currentPage > 1){
                StringBuilder onClick = new StringBuilder();
                
                onClick.append("moveToPreviousPage('");
                onClick.append(name);
                onClick.append("', ");
                
                String updateViews = pagerComponent.getUpdateViews();
                
                if(updateViews != null && !updateViews.isEmpty()){
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

        @Override
        protected void initialize() throws InternalErrorException{
            PagerComponent pagerComponent = (PagerComponent) getParent();
            
            if(pagerComponent.getCurrentPage() <= 1)
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
    private static class NextPageButtonComponent extends ButtonComponent{
        @Serial
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
            
            setPageContext(pagerComponent.getPageContext());
            setOutputStream(pagerComponent.getOutputStream());
            setActionFormName(pagerComponent.getActionFormName());
            setParent(pagerComponent);
        }

        @Override
        protected void buildResources() throws InternalErrorException{
            setResourcesId(UIConstants.DEFAULT_PAGER_RESOURCES_ID);
            setResourcesKey(UIConstants.DEFAULT_PAGER_NEXT_PAGE_BUTTON_ID);
            
            super.buildResources();
        }

        @Override
        protected void buildStyleClass() throws InternalErrorException{
            setStyleClass(UIConstants.DEFAULT_PAGER_NEXT_PAGE_BUTTON_STYLE_CLASS);
            
            super.buildStyleClass();
        }

        @Override
        protected void buildEvents() throws InternalErrorException{
            PagerComponent pagerComponent = (PagerComponent) getParent();
            String actionFormName = pagerComponent.getActionFormName();
            String name = pagerComponent.getName();
            int currentPage = pagerComponent.getCurrentPage();
            int pages = pagerComponent.getPages();
            
            if(actionFormName != null && !actionFormName.isEmpty() && name != null && !name.isEmpty() && currentPage < pages){
                StringBuilder onClick = new StringBuilder();
                
                onClick.append("moveToNextPage('");
                onClick.append(name);
                onClick.append("', ");
                
                String updateViews = pagerComponent.getUpdateViews();
                
                if(updateViews != null && !updateViews.isEmpty()){
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

        @Override
        protected void initialize() throws InternalErrorException{
            PagerComponent pagerComponent = (PagerComponent) getParent();
            
            if(pagerComponent.getCurrentPage() >= pagerComponent.getPages())
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
    private static class LastPageButtonComponent extends ButtonComponent{
        @Serial
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
            
            setPageContext(pagerComponent.getPageContext());
            setOutputStream(pagerComponent.getOutputStream());
            setActionFormName(pagerComponent.getActionFormName());
            setParent(pagerComponent);
        }

        @Override
        protected void buildResources() throws InternalErrorException{
            setResourcesId(UIConstants.DEFAULT_PAGER_RESOURCES_ID);
            setResourcesKey(UIConstants.DEFAULT_PAGER_LAST_PAGE_BUTTON_ID);
            
            super.buildResources();
        }

        @Override
        protected void buildStyleClass() throws InternalErrorException{
            setStyleClass(UIConstants.DEFAULT_PAGER_LAST_PAGE_BUTTON_STYLE_CLASS);
            
            super.buildStyleClass();
        }

        @Override
        protected void buildEvents() throws InternalErrorException{
            PagerComponent pagerComponent = (PagerComponent) getParent();
            String actionFormName = pagerComponent.getActionFormName();
            String name = pagerComponent.getName();
            int currentPage = pagerComponent.getCurrentPage();
            int pages = pagerComponent.getPages();
            
            if(actionFormName != null && !actionFormName.isEmpty() && name != null && !name.isEmpty() && currentPage < pages){
                StringBuilder onClick = new StringBuilder();
                
                onClick.append("moveToLastPage('");
                onClick.append(name);
                onClick.append("', ");
                
                String updateViews = pagerComponent.getUpdateViews();
                
                if(updateViews != null && !updateViews.isEmpty()){
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

        @Override
        protected void initialize() throws InternalErrorException{
            PagerComponent pagerComponent = (PagerComponent) getParent();
            
            if(pagerComponent.getCurrentPage() >= pagerComponent.getPages())
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
    private static class ItemsPerPagePropertyComponent extends TextPropertyComponent{
        @Serial
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
            
            setPageContext(pagerComponent.getPageContext());
            setActionFormName(pagerComponent.getActionFormName());
            setOutputStream(pagerComponent.getOutputStream());
            setParent(pagerComponent);
        }
        
        @Override
        protected void buildResources() throws InternalErrorException{
            setResourcesId(UIConstants.DEFAULT_PAGER_RESOURCES_ID);
            setResourcesKey(UIConstants.DEFAULT_PAGER_ITEMS_PER_PAGE_ID);
            
            super.buildResources();
        }

        @Override
        protected void buildEvents() throws InternalErrorException{
            PagerComponent pagerComponent = (PagerComponent) getParent();
            StringBuilder onKeyPressContent = new StringBuilder();

            onKeyPressContent.append("if(getKeyPressed(event) == 13){ refreshPage('");
            onKeyPressContent.append(pagerComponent.getName());
            onKeyPressContent.append("', ");

            String updateViews = pagerComponent.getUpdateViews();

            if(updateViews != null && !updateViews.isEmpty()){
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

        @Override
        protected void buildName() throws InternalErrorException{
            PagerComponent pagerComponent = (PagerComponent) getParent();
            String actionFormName = pagerComponent.getActionFormName();
            String name = pagerComponent.getName();
            
            if(actionFormName != null && !actionFormName.isEmpty() && name != null && !name.isEmpty()){
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

        @Override
        protected void buildRestrictions() throws InternalErrorException{
            setSize(3);
            setMaximumLength(3);
            setMinimumValue(1);
            setMaximumValue(999);
            
            super.buildRestrictions();
        }

        @Override
        protected void buildAlignment() throws InternalErrorException{
            setAlignmentType(AlignmentType.RIGHT);
            
            super.buildAlignment();
        }

        @Override
        protected void initialize() throws InternalErrorException{
            PagerComponent pagerComponent = (PagerComponent) getParent();
            
            setValue(pagerComponent.getItemsPerPage());
            
            super.initialize();
        }
    }
}