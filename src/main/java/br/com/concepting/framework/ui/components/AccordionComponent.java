package br.com.concepting.framework.ui.components;

import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.ui.components.types.VisibilityType;
import br.com.concepting.framework.ui.constants.UIConstants;
import br.com.concepting.framework.ui.controller.UIController;
import br.com.concepting.framework.util.PropertyUtil;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.types.ComponentType;

import javax.servlet.jsp.JspException;
import java.io.Serial;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Class that defines the accordion component.
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
public class AccordionComponent extends BaseActionFormComponent{
    @Serial
    private static final long serialVersionUID = 1642597166746306289L;
    
    private boolean multipleSelection = false;
    private List<SectionComponent> sectionsComponents = null;
    private List<String> currentSections = null;
    
    /**
     * Returns the list of sections.
     *
     * @return List that contains the identifiers of the sections.
     */
    protected List<String> getCurrentSections(){
        return this.currentSections;
    }
    
    /**
     * Defines the list of sections.
     *
     * @param currentSections List that contains the identifiers of the
     * sections.
     */
    private void setCurrentSections(List<String> currentSections){
        this.currentSections = currentSections;
    }
    
    /**
     * Indicates if the component has multiple selection.
     *
     * @return True/False.
     */
    public boolean hasMultipleSelection(){
        return this.multipleSelection;
    }
    
    /**
     * Indicates if the component has multiple selection.
     *
     * @return True/False.
     */
    public boolean getMultipleSelection(){
        return hasMultipleSelection();
    }
    
    /**
     * Defines if the component has multiple selection.
     *
     * @param multipleSelection True/False.
     */
    public void setMultipleSelection(boolean multipleSelection){
        this.multipleSelection = multipleSelection;
    }
    
    /**
     * Returns the list of the sections.
     *
     * @return Instance that contains the list of sections.
     */
    protected List<SectionComponent> getSectionsComponents(){
        return this.sectionsComponents;
    }
    
    /**
     * Defines the list of the sections.
     *
     * @param sectionsComponents Instance that contains the list of sections.
     */
    protected void setSectionsComponents(List<SectionComponent> sectionsComponents){
        this.sectionsComponents = sectionsComponents;
    }
    
    /**
     * Adds a new section.
     *
     * @param sectionComponent Instance that contains the component that defines the
     * section.
     */
    protected void addSectionComponent(SectionComponent sectionComponent){
        if(sectionComponent != null){
            if(this.sectionsComponents == null)
                this.sectionsComponents = PropertyUtil.instantiate(Constants.DEFAULT_LIST_CLASS);

            if(this.sectionsComponents != null)
                this.sectionsComponents.add(sectionComponent);
        }
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

    @Override
    protected void initialize() throws InternalErrorException{
        setComponentType(ComponentType.ACCORDION);
        
        super.initialize();
    }

    @Override
    protected void renderOpen() throws InternalErrorException{
    }

    @Override
    protected void renderBody() throws InternalErrorException{
        UIController uiController = getUIController();
        String actionFormName = getActionFormName();
        String name = getName();
        
        if(uiController == null || actionFormName == null || actionFormName.isEmpty() || name == null || name.isEmpty())
            return;
        
        this.currentSections = uiController.getCurrentSections(name);
        
        if(this.sectionsComponents != null && this.sectionsComponents.size() == 1){
            Iterator<SectionComponent> iterator = this.sectionsComponents.iterator();
            String sectionName = iterator.next().getName();
            
            if(this.currentSections == null)
                this.currentSections = PropertyUtil.instantiate(Constants.DEFAULT_LIST_CLASS);
            else
                this.currentSections.clear();

            if(this.currentSections != null)
                this.currentSections.add(sectionName);
        }
        else if(this.sectionsComponents != null && this.sectionsComponents.size() > 1){
            for (int cont = 0; cont < Objects.requireNonNull(this.currentSections).size(); cont++) {
                String currentSectionName = this.currentSections.get(cont);
                boolean found = false;

                for (SectionComponent sectionComponent : this.sectionsComponents) {
                    String sectionName = sectionComponent.getName();

                    if (currentSectionName.equals(sectionName)) {
                        found = true;

                        break;
                    }
                }

                if (!found) {
                    if (this.currentSections != null && !this.currentSections.isEmpty())
                        this.currentSections.remove(cont);

                    cont--;
                }
            }

            String lastSectionName = null;
            
            if(this.sectionsComponents != null && !this.sectionsComponents.isEmpty()){
                for(SectionComponent sectionComponent: this.sectionsComponents){
                    String sectionName = sectionComponent.getName();
                    
                    if(sectionComponent.focus() || (sectionName != null && this.currentSections != null && !this.currentSections.isEmpty() && this.currentSections.contains(sectionName)))
                        lastSectionName = sectionName;
                }
                
                for(SectionComponent sectionComponent: this.sectionsComponents){
                    String sectionName = sectionComponent.getName();
                    
                    if(((sectionComponent.focus() || (sectionName != null && !sectionName.isEmpty() && this.currentSections != null && !this.currentSections.isEmpty() && this.currentSections.contains(sectionName))) && this.multipleSelection) || (lastSectionName != null && lastSectionName.equals(sectionName))){
                        if(this.currentSections == null)
                            this.currentSections = PropertyUtil.instantiate(Constants.DEFAULT_LIST_CLASS);

                        if(this.currentSections != null)
                            this.currentSections.add(sectionName);
                    }
                    else{
                        String currentSectionStyle = sectionComponent.getStyle();
                        StringBuilder sectionStyle = new StringBuilder();
                        
                        if(currentSectionStyle != null && !currentSectionStyle.isEmpty()){
                            sectionStyle.append(currentSectionStyle);
                            
                            if(!currentSectionStyle.endsWith(";"))
                                sectionStyle.append(";");
                            
                            sectionStyle.append(" ");
                        }
                        
                        sectionStyle.append("display: ");
                        sectionStyle.append(VisibilityType.NONE);
                        sectionStyle.append(";");
                        
                        sectionComponent.setFocus(false);
                        sectionComponent.setStyle(sectionStyle.toString());
                        
                        if(this.currentSections != null && !this.currentSections.isEmpty()){
                            int pos = this.currentSections.indexOf(sectionName);
                            
                            if(pos >= 0)
                                this.currentSections.remove(pos);
                        }
                    }
                }
            }
        }
        
        StringBuilder nameBuffer = new StringBuilder();
        
        nameBuffer.append(name);
        nameBuffer.append(".");
        nameBuffer.append(UIConstants.CURRENT_SECTIONS_ATTRIBUTE_ID);
        
        if(this.multipleSelection){
            Collection<String> sections = PropertyUtil.instantiate(Constants.DEFAULT_LIST_CLASS);
            
            if(this.sectionsComponents != null && !this.sectionsComponents.isEmpty()){
                for(SectionComponent sectionComponent: this.sectionsComponents){
                    String sectionName = sectionComponent.getName();
                    
                    if(sectionName != null && !sectionName.isEmpty() && sections != null)
                        sections.add(sectionName);
                }
            }
            
            ListPropertyComponent currentSectionsPropertyComponent = new ListPropertyComponent();
            
            currentSectionsPropertyComponent.setPageContext(this.pageContext);
            currentSectionsPropertyComponent.setOutputStream(getOutputStream());
            currentSectionsPropertyComponent.setActionFormName(actionFormName);
            currentSectionsPropertyComponent.setResourcesId(getResourcesId());
            currentSectionsPropertyComponent.setShowFirstOption(false);
            currentSectionsPropertyComponent.setShowLabel(false);
            currentSectionsPropertyComponent.setName(nameBuffer.toString());
            currentSectionsPropertyComponent.setMultipleSelection(this.multipleSelection);
            currentSectionsPropertyComponent.setDatasetValues(sections);
            currentSectionsPropertyComponent.setValue(this.currentSections);
            
            StringBuilder currentSectionsStyle = new StringBuilder();
            
            currentSectionsStyle.append("display: ");
            currentSectionsStyle.append(VisibilityType.NONE);
            currentSectionsStyle.append(";");
            
            currentSectionsPropertyComponent.setStyle(currentSectionsStyle.toString());
            
            try{
                currentSectionsPropertyComponent.doStartTag();
                currentSectionsPropertyComponent.doEndTag();
            }
            catch(JspException e){
                throw new InternalErrorException(e);
            }
        }
        else{
            HiddenPropertyComponent currentSectionPropertyComponent = new HiddenPropertyComponent();
            
            currentSectionPropertyComponent.setPageContext(this.pageContext);
            currentSectionPropertyComponent.setOutputStream(getOutputStream());
            currentSectionPropertyComponent.setActionFormName(actionFormName);
            currentSectionPropertyComponent.setName(nameBuffer.toString());
            
            if(this.currentSections != null && !this.currentSections.isEmpty())
                currentSectionPropertyComponent.setValue(this.currentSections.getFirst());
            
            try{
                currentSectionPropertyComponent.doStartTag();
                currentSectionPropertyComponent.doEndTag();
            }
            catch(JspException e){
                throw new InternalErrorException(e);
            }
        }
        
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
        println("<td>");
        
        renderSections();
        
        println("</td>");
        println("</tr>");
        println("</table>");
    }

    /**
     * Renders as sections of the component.
     *
     * @throws InternalErrorException Occurs when was not possible to render.
     */
    protected void renderSections() throws InternalErrorException{
        if(this.sectionsComponents != null && !this.sectionsComponents.isEmpty()){
            print("<table class=\"");
            print(UIConstants.DEFAULT_CONTENT_PANEL_STYLE_CLASS);
            println("\">");

            for(int cont = 0; cont < this.sectionsComponents.size(); cont++){
                SectionComponent sectionComponent = this.sectionsComponents.get(cont);
                
                println("<tr>");
                println("<td>");
                
                renderSectionHeader(sectionComponent, cont);
                renderSectionContent(sectionComponent, cont);
                
                println("</td>");
                println("</tr>");
            }
            
            println("</table>");
        }
    }
    
    /**
     * Renders the header of the section.
     *
     * @param sectionComponent Instance that contains the properties of the
     * section.
     * @param index Numeric value that contains the index of the section.
     * @throws InternalErrorException Occurs when was not possible to render.
     */
    protected void renderSectionHeader(SectionComponent sectionComponent, int index) throws InternalErrorException{
        String actionFormName = getActionFormName();
        String id = getId();
        String name = getName();
        String sectionName = (sectionComponent != null ? sectionComponent.getName() : null);
        
        if(actionFormName != null && !actionFormName.isEmpty() && id != null && !id.isEmpty() && name != null && !name.isEmpty() && sectionName != null && !sectionName.isEmpty()){
            StringBuilder nameBuffer = new StringBuilder();
            
            nameBuffer.append(sectionName);
            nameBuffer.append(".");
            nameBuffer.append(Constants.LABEL_ATTRIBUTE_ID);
            
            HiddenPropertyComponent labelPropertyComponent = new HiddenPropertyComponent();
            
            labelPropertyComponent.setPageContext(this.pageContext);
            labelPropertyComponent.setOutputStream(getOutputStream());
            labelPropertyComponent.setActionFormName(actionFormName);
            labelPropertyComponent.setName(nameBuffer.toString());
            labelPropertyComponent.setValue(sectionComponent.getLabel());
            
            try{
                labelPropertyComponent.doStartTag();
                labelPropertyComponent.doEndTag();
            }
            catch(JspException e){
                throw new InternalErrorException(e);
            }
            
            print("<div id=\"");
            print(id);
            print(".");
            print(sectionName);
            print(".");
            print(UIConstants.DEFAULT_SECTION_HEADER_ID);
            print("\" class=\"");
            
            if(index == 0){
                print("first");
                print(StringUtil.capitalize(UIConstants.DEFAULT_SECTION_HEADER_STYLE_CLASS));
            }
            else if(index == this.sectionsComponents.size() - 1){
                boolean isCurrentSection = (this.currentSections != null && !this.currentSections.isEmpty() && this.currentSections.contains(sectionName));
                
                if(!isCurrentSection){
                    print("last");
                    print(StringUtil.capitalize(UIConstants.DEFAULT_SECTION_HEADER_STYLE_CLASS));
                }
                else
                    print(UIConstants.DEFAULT_SECTION_HEADER_STYLE_CLASS);
            }
            else
                print(UIConstants.DEFAULT_SECTION_HEADER_STYLE_CLASS);
            
            print("\" firstSection=\"");
            print(index == 0);
            print("\" lastSection=\"");
            print(this.sectionsComponents != null && index == (this.sectionsComponents.size() - 1));
            print("\" onClick=\"setCurrentSections('");
            print(sectionName);
            print("', '");
            print(name);
            print("', ");
            print(this.multipleSelection);
            
            String onSelect = sectionComponent.getOnSelect();
            String onUnSelect = sectionComponent.getOnUnSelect();
            
            if(onSelect != null && !onSelect.isEmpty()){
                print(", function(){");
                print(onSelect);
                print("}");
            }
            
            if(onUnSelect != null && !onUnSelect.isEmpty()){
                print(", ");

                if(onSelect != null && onSelect.isEmpty()){
                    print(Constants.DEFAULT_NULL_ID);
                    print(", ");
                }
                
                print("function(){");
                print(onUnSelect);
                print("}");
            }
            
            print(");\"");
            
            String labelStyle = sectionComponent.getLabelStyle();
            
            if(labelStyle != null && !labelStyle.isEmpty()){
                print(" style=\"");
                print(labelStyle);
                
                if(!labelStyle.endsWith(";"))
                    print(";");
                
                print("\"");
            }
            
            String tooltip = sectionComponent.getTooltip();
            
            if(tooltip != null && !tooltip.isEmpty()){
                print(" title=\"");
                print(tooltip);
                print("\"");
            }
            
            println(">");
            
            String label = sectionComponent.getLabel();
            
            if(label != null && !label.isEmpty())
                println(label);
            
            println("</div>");
        }
    }
    
    /**
     * Renders the content of the section.
     *
     * @param sectionComponent Instance that contains the component that defines the
     * section.
     * @param index Numeric value that contains the index of the section.
     * @throws InternalErrorException Occurs when was not possible to render.
     */
    protected void renderSectionContent(SectionComponent sectionComponent, int index) throws InternalErrorException{
        if(sectionComponent == null)
            return;
        
        String name = getName();
        String sectionName = sectionComponent.getName();
        
        if(name == null || name.isEmpty() || sectionName == null || sectionName.isEmpty())
            return;
        
        print("<div id=\"");
        print(name);
        print(".");
        print(sectionName);
        print(".");
        print(UIConstants.DEFAULT_SECTION_CONTENT_ID);
        print("\" class=\"");
        
        if(index == 0){
            print("first");
            print(StringUtil.capitalize(UIConstants.DEFAULT_SECTION_CONTENT_STYLE_CLASS));
        }
        else if( this.sectionsComponents != null && index == this.sectionsComponents.size() - 1){
            print("last");
            print(StringUtil.capitalize(UIConstants.DEFAULT_SECTION_CONTENT_STYLE_CLASS));
        }
        else
            print(UIConstants.DEFAULT_SECTION_CONTENT_STYLE_CLASS);
        
        print("\"");
        
        String style = sectionComponent.getStyle();
        
        if(style != null && !style.isEmpty()){
            print(" style=\"");
            print(style);
            
            if(!style.endsWith(";"))
                print(";");
            
            print("\"");
        }
        
        println(">");
        
        String content = sectionComponent.getContent();
        
        if(content != null && !content.isEmpty())
            println(content);
        
        println("</div>");
    }

    @Override
    protected void renderClose() throws InternalErrorException{
    }

    @Override
    protected void clearAttributes() throws InternalErrorException{
        super.clearAttributes();
        
        setMultipleSelection(false);
        setSectionsComponents(null);
        setCurrentSections(null);
    }
}