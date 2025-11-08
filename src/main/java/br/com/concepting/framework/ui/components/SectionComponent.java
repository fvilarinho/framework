package br.com.concepting.framework.ui.components;

import br.com.concepting.framework.controller.action.types.ActionType;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.ui.components.types.EventType;
import br.com.concepting.framework.util.types.ComponentType;

import javax.servlet.jsp.tagext.BodyContent;
import java.io.Serial;

/**
 * Class that defines the section component.
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
public class SectionComponent extends BaseActionFormComponent{
    @Serial
    private static final long serialVersionUID = -3844478753445296174L;
    
    private String content = null;
    private String onSelect = null;
    private String onSelectAction = null;
    private String onSelectForward = null;
    private String onSelectUpdateViews = null;
    private boolean onSelectValidateModel = false;
    private String onSelectValidateModelProperties = null;
    private String onUnSelect = null;
    private String onUnSelectAction = null;
    private String onUnSelectForward = null;
    private String onUnSelectUpdateViews = null;
    private boolean onUnSelectValidateModel = false;
    private String onUnSelectValidateModelProperties = null;
    
    /**
     * Returns the identifier of the unselect action.
     *
     * @return String that contains the identifier of the action.
     */
    public String getOnUnSelectAction(){
        return this.onUnSelectAction;
    }
    
    /**
     * Defines the identifier of the unselect action.
     *
     * @param onUnSelectAction String that contains the identifier of the
     * action.
     */
    public void setOnUnSelectAction(String onUnSelectAction){
        this.onUnSelectAction = onUnSelectAction;
    }
    
    /**
     * Defines the type of unselect action.
     *
     * @param onUnSelectActionType Instance that contains the action.
     */
    public void setOnUnSelectActionType(ActionType onUnSelectActionType){
        if(onUnSelectActionType != null)
            this.onUnSelectAction = onUnSelectActionType.getMethod();
        else
            this.onUnSelectAction = null;
    }
    
    /**
     * Returns the identifier of the unselect action forward.
     *
     * @return String that contains the identifier of the action forward.
     */
    public String getOnUnSelectForward(){
        return this.onUnSelectForward;
    }
    
    /**
     * Defines the identifier of the unselect action forward.
     *
     * @param onUnSelectForward String that contains the identifier of the
     * action forward.
     */
    public void setOnUnSelectForward(String onUnSelectForward){
        this.onUnSelectForward = onUnSelectForward;
    }
    
    /**
     * Returns the identifier of the unselect action views that will be updated.
     *
     * @return String that contains the identifier of the views.
     */
    public String getOnUnSelectUpdateViews(){
        return this.onUnSelectUpdateViews;
    }
    
    /**
     * Defines the identifier of the unselect action views that will be updated.
     *
     * @param onUnSelectUpdateViews String that contains the identifier of the
     * views.
     */
    public void setOnUnSelectUpdateViews(String onUnSelectUpdateViews){
        this.onUnSelectUpdateViews = onUnSelectUpdateViews;
    }
    
    /**
     * Indicates if the data model should be validated on the unselect event.
     *
     * @return True/False.
     */
    public boolean getOnUnSelectValidateModel(){
        return this.onUnSelectValidateModel;
    }
    
    /**
     * Defines if the data model should be validated on the unselect event.
     *
     * @param onUnSelectValidateModel True/False.
     */
    public void setOnUnSelectValidateModel(boolean onUnSelectValidateModel){
        this.onUnSelectValidateModel = onUnSelectValidateModel;
    }
    
    /**
     * Returns the validation properties of the data model that should be
     * validated on the unselect event.
     *
     * @return String that contains the identifiers of the properties.
     */
    public String getOnUnSelectValidateModelProperties(){
        return this.onUnSelectValidateModelProperties;
    }
    
    /**
     * Defines the validation properties of the data model that should be
     * validated on the unselect event.
     *
     * @param onUnSelectValidateModelProperties String that contains the
     * identifiers of the properties.
     */
    public void setOnUnSelectValidateModelProperties(String onUnSelectValidateModelProperties){
        this.onUnSelectValidateModelProperties = onUnSelectValidateModelProperties;
    }
    
    /**
     * Returns the identifier of the select action.
     *
     * @return String that contains the identifier of the action.
     */
    public String getOnSelectAction(){
        return this.onSelectAction;
    }
    
    /**
     * Defines the identifier of the select action.
     *
     * @param onSelectAction String that contains the identifier of the action.
     */
    public void setOnSelectAction(String onSelectAction){
        this.onSelectAction = onSelectAction;
    }
    
    /**
     * Defines the action of the select event.
     *
     * @param onSelectActionType Instance that contains the action.
     */
    public void setOnSelectActionType(ActionType onSelectActionType){
        if(onSelectActionType != null)
            this.onSelectAction = onSelectActionType.getMethod();
        else
            this.onSelectAction = null;
    }
    
    /**
     * Returns the identifier of the action forward of the select event.
     *
     * @return String that contains the identifier of the action forward.
     */
    public String getOnSelectForward(){
        return this.onSelectForward;
    }
    
    /**
     * Defines the identifier of the action forward of the select event.
     *
     * @param onSelectForward String that contains the identifier of the action
     * forward.
     */
    public void setOnSelectForward(String onSelectForward){
        this.onSelectForward = onSelectForward;
    }
    
    /**
     * Returns the identifier of the views that will be updated on the select
     * event.
     *
     * @return String that contains the identifier of the views.
     */
    public String getOnSelectUpdateViews(){
        return this.onSelectUpdateViews;
    }
    
    /**
     * Defines the identifier of the views that will be updated on the select
     * event.
     *
     * @param onSelectUpdateViews String that contains the identifier of the
     * views.
     */
    public void setOnSelectUpdateViews(String onSelectUpdateViews){
        this.onSelectUpdateViews = onSelectUpdateViews;
    }
    
    /**
     * Indicates if the data model should be validated on the select event.
     *
     * @return True/False.
     */
    public boolean getOnSelectValidateModel(){
        return this.onSelectValidateModel;
    }
    
    /**
     * Defines if the data model should be validated on the select event.
     *
     * @param onSelectValidateModel True/False.
     */
    public void setOnSelectValidateModel(boolean onSelectValidateModel){
        this.onSelectValidateModel = onSelectValidateModel;
    }
    
    /**
     * Returns the validation properties of the data model that should be
     * validated on the select event.
     *
     * @return String that contains the identifiers of the properties.
     */
    public String getOnSelectValidateModelProperties(){
        return this.onSelectValidateModelProperties;
    }
    
    /**
     * Defines the validation properties of the data model that should be
     * validated on the select event.
     *
     * @param onSelectValidateModelProperties String that contains the
     * identifiers of the properties.
     */
    public void setOnSelectValidateModelProperties(String onSelectValidateModelProperties){
        this.onSelectValidateModelProperties = onSelectValidateModelProperties;
    }
    
    /**
     * Returns the content of the section.
     *
     * @return String that contains the content of the section.
     */
    public String getContent(){
        return this.content;
    }
    
    /**
     * Defines the content of the section.
     *
     * @param content String that contains the content of the section.
     */
    public void setContent(String content){
        this.content = content;
    }
    
    /**
     * Returns the select event.
     *
     * @return String that contains the event.
     */
    public String getOnSelect(){
        return this.onSelect;
    }
    
    /**
     * Defines the select event.
     *
     * @param onSelect String that contains the event.
     */
    public void setOnSelect(String onSelect){
        this.onSelect = onSelect;
    }
    
    /**
     * Returns the unselect event snippet.
     *
     * @return String that contains the event.
     */
    public String getOnUnSelect(){
        return this.onUnSelect;
    }
    
    /**
     * Defines the unselect event snippet.
     *
     * @param onUnSelect String that contains the event.
     */
    public void setOnUnSelect(String onUnSelect){
        this.onUnSelect = onUnSelect;
    }

    @Override
    protected void buildEvents() throws InternalErrorException{
        super.buildEvents();
        
        buildEvent(EventType.ON_SELECT);
        buildEvent(EventType.ON_UN_SELECT);
    }

    @Override
    protected void buildDimensions() throws InternalErrorException{
        AccordionComponent accordionComponent = (AccordionComponent) findAncestorWithClass(this, AccordionComponent.class);
        
        if(accordionComponent != null){
            String height = getHeight();
            
            if(height == null || height.isEmpty()){
                height = accordionComponent.getHeight();
                
                setHeight(height);
            }
        }
        
        super.buildDimensions();
    }

    @Override
    protected void buildResources() throws InternalErrorException{
        AccordionComponent accordionComponent = (AccordionComponent) findAncestorWithClass(this, AccordionComponent.class);
        
        if(accordionComponent == null){
            try{
                accordionComponent = (AccordionComponent) getParent();
            }
            catch(ClassCastException ignored){
            }
        }
        
        if(accordionComponent != null){
            String resourcesId = getResourcesId();
            
            if(resourcesId == null || resourcesId.isEmpty()){
                resourcesId = accordionComponent.getResourcesId();
                
                setResourcesId(resourcesId);
            }
        }
        
        super.buildResources();
    }

    @Override
    protected void initialize() throws InternalErrorException{
        setComponentType(ComponentType.SECTION);
        
        super.initialize();
    }

    @Override
    public void renderOpen() throws InternalErrorException{
    }

    @Override
    public void renderClose() throws InternalErrorException{
    }

    @Override
    protected void renderBody() throws InternalErrorException{
        boolean render = render();
        
        if(render){
            AccordionComponent accordionComponent = (AccordionComponent) findAncestorWithClass(this, AccordionComponent.class);
            
            if(accordionComponent == null){
                try{
                    accordionComponent = (AccordionComponent) getParent();
                }
                catch(ClassCastException ignored){
                }
            }
            
            if(accordionComponent != null){
                try{
                    SectionComponent sectionComponent = (SectionComponent) this.clone();
                    BodyContent bodyContent = sectionComponent.getBodyContent();
                    String sectionContent = sectionComponent.getContent();
                    
                    if(bodyContent != null && (sectionContent == null || sectionContent.isEmpty())){
                        sectionContent = bodyContent.getString();
                        
                        sectionComponent.setContent(sectionContent);
                    }
                    
                    accordionComponent.addSectionComponent(sectionComponent);
                }
                catch(CloneNotSupportedException e){
                    throw new InternalErrorException(e);
                }
            }
        }
    }

    @Override
    protected void clearAttributes() throws InternalErrorException{
        super.clearAttributes();
        
        setOnSelect(null);
        setOnSelectAction(null);
        setOnSelectForward(null);
        setOnSelectUpdateViews(null);
        setOnSelectValidateModel(false);
        setOnSelectValidateModelProperties(null);
        setOnUnSelect(null);
        setOnUnSelectActionType(null);
        setOnUnSelectForward(null);
        setOnUnSelectUpdateViews(null);
        setOnUnSelectValidateModel(false);
        setOnUnSelectValidateModelProperties(null);
        setContent(null);
    }
}