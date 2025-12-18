package br.com.concepting.framework.ui.components;

import br.com.concepting.framework.controller.action.types.ActionType;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.ui.components.types.EventType;
import br.com.concepting.framework.util.types.ComponentType;

import jakarta.servlet.jsp.tagext.BodyContent;

import java.io.Serial;

/**
 * Class that defines the guide component.
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
public class GuideComponent extends BaseActionFormComponent{
    @Serial
    private static final long serialVersionUID = 6950682512939115664L;
    
    private String onSelect = null;
    private String onSelectAction = null;
    private String onSelectForward = null;
    private String onSelectUpdateViews = null;
    private boolean onSelectValidateModel = false;
    private String onSelectValidateModelProperties = null;
    private String content = null;
    
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
     * Returns the identifier of the views that should be updated.
     *
     * @return String that contains the identifier of the views.
     */
    public String getOnSelectUpdateViews(){
        return this.onSelectUpdateViews;
    }
    
    /**
     * Defines the identifier of the views that should be updated.
     *
     * @param onSelectUpdateViews String that contains the identifier of the
     * views.
     */
    public void setOnSelectUpdateViews(String onSelectUpdateViews){
        this.onSelectUpdateViews = onSelectUpdateViews;
    }
    
    /**
     * Indicates if the data model should be validated.
     *
     * @return True/False.
     */
    public boolean getOnSelectValidateModel(){
        return this.onSelectValidateModel;
    }
    
    /**
     * Defines if the data model should be validated.
     *
     * @param onSelectValidateModel True/False.
     */
    public void setOnSelectValidateModel(boolean onSelectValidateModel){
        this.onSelectValidateModel = onSelectValidateModel;
    }
    
    /**
     * Returns the validation properties of the data model that should be
     * validated.
     *
     * @return String that contains the identifiers of the properties.
     */
    public String getOnSelectValidateModelProperties(){
        return this.onSelectValidateModelProperties;
    }
    
    /**
     * Defines the validation properties of the data model that should be
     * validated.
     *
     * @param onSelectValidateModelProperties String that contains the
     * identifiers of the properties.
     */
    public void setOnSelectValidateModelProperties(String onSelectValidateModelProperties){
        this.onSelectValidateModelProperties = onSelectValidateModelProperties;
    }
    
    /**
     * Returns the content of the guide.
     *
     * @return String that contains the content of the guide.
     */
    public String getContent(){
        return this.content;
    }
    
    /**
     * Defines the content of the guide.
     *
     * @param content String that contains the content of the guide.
     */
    public void setContent(String content){
        this.content = content;
    }
    
    /**
     * Returns the on select event.
     *
     * @return String that contains the event.
     */
    public String getOnSelect(){
        return this.onSelect;
    }
    
    /**
     * Defines the on select event.
     *
     * @param onSelect String that contains the event.
     */
    public void setOnSelect(String onSelect){
        this.onSelect = onSelect;
    }

    @Override
    protected void buildEvents() throws InternalErrorException{
        super.buildEvents();
        
        buildEvent(EventType.ON_SELECT);
    }

    @Override
    protected void buildResources() throws InternalErrorException{
        GuidesComponent guidesComponent = (GuidesComponent) findAncestorWithClass(this, GuidesComponent.class);
        
        if(guidesComponent == null){
            try{
                guidesComponent = (GuidesComponent) getParent();
            }
            catch(ClassCastException ignored){
            }
        }
        
        if(guidesComponent != null){
            String resourcesId = getResourcesId();
            
            if(resourcesId == null || resourcesId.isEmpty()){
                resourcesId = guidesComponent.getResourcesId();
                
                setResourcesId(resourcesId);
            }
        }
        
        super.buildResources();
    }

    @Override
    protected void initialize() throws InternalErrorException{
        setComponentType(ComponentType.GUIDE);
        
        super.initialize();
    }

    @Override
    protected void renderOpen() throws InternalErrorException{
    }

    @Override
    protected void renderClose() throws InternalErrorException{
    }

    @Override
    protected void renderBody() throws InternalErrorException{
        boolean render = render();
        
        if(render){
            GuidesComponent guidesComponent = (GuidesComponent) findAncestorWithClass(this, GuidesComponent.class);
            
            if(guidesComponent == null){
                try{
                    guidesComponent = (GuidesComponent) getParent();
                }
                catch(ClassCastException ignored){
                }
            }
            
            if(guidesComponent != null){
                try{
                    GuideComponent guideComponent = (GuideComponent) this.clone();
                    BodyContent bodyContent = guideComponent.getBodyContent();
                    String guideContent = guideComponent.getContent();
                    
                    if(bodyContent != null && (guideContent == null || guideContent.isEmpty())){
                        guideContent = bodyContent.getString();
                        
                        guideComponent.setContent(guideContent);
                    }
                    
                    guidesComponent.addGuideComponent(guideComponent);
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
        setContent(null);
    }
}