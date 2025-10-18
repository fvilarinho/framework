package br.com.concepting.framework.model;

import br.com.concepting.framework.model.annotations.Model;
import br.com.concepting.framework.model.annotations.Property;
import br.com.concepting.framework.model.types.ConditionType;
import br.com.concepting.framework.model.types.ValidationType;
import br.com.concepting.framework.persistence.types.RelationJoinType;
import br.com.concepting.framework.persistence.types.RelationType;
import br.com.concepting.framework.security.model.GroupModel;
import br.com.concepting.framework.util.types.ComponentType;
import br.com.concepting.framework.util.types.SearchType;
import br.com.concepting.framework.util.types.SortOrderType;

import java.util.Collection;

/**
 * Class that defines the basic implementation of the data model that stores the
 * information of a form object.
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
@Model(descriptionPattern = "#{title}")
public class ObjectModel extends BaseModel{
    private static final long serialVersionUID = 4904881181797474284L;
    
    @Property(isIdentity = true)
    private Long id = null;
    
    @Property(isForSearch = true, validations = ValidationType.REQUIRED)
    private ComponentType type = null;
    
    @Property(isForSearch = true, relationType = RelationType.ONE_TO_ONE, relationJoinType = RelationJoinType.LEFT_JOIN, validations = ValidationType.REQUIRED)
    private FormModel form = null;
    
    @Property(isForSearch = true, searchType = SearchType.CASE_INSENSITIVE, validations = ValidationType.REQUIRED, isUnique = true)
    private String name = null;
    
    @Property(isForSearch = true, searchType = SearchType.CASE_INSENSITIVE, searchCondition = ConditionType.CONTAINS, validations = ValidationType.REQUIRED)
    private String title = null;
    
    @Property(isForSearch = true, searchType = SearchType.CASE_INSENSITIVE, searchCondition = ConditionType.CONTAINS, maximumLength = 1000)
    private String description = null;
    
    @Property
    private String tooltip = null;
    
    @Property(isForSearch = true, searchCondition = ConditionType.STARTS_WITH)
    private String action = null;
    
    @Property
    private String actionTarget = null;
    
    @Property(sortOrder = SortOrderType.ASCEND)
    private Long sequence = null;
    
    @Property(relationType = RelationType.MANY_TO_MANY)
    private Collection<? extends GroupModel> groups = null;
    
    /**
     * Returns the tooltip of the object.
     *
     * @return String that contains the tooltip.
     */
    public String getTooltip(){
        return this.tooltip;
    }
    
    /**
     * Defines the tooltip of the object.
     *
     * @param tooltip String that contains the tooltip.
     */
    public void setTooltip(String tooltip){
        this.tooltip = tooltip;
    }
    
    /**
     * Returns the list of groups that contains the object.
     *
     * @return List that contains the list of groups.
     */
    public Collection<? extends GroupModel> getGroups(){
        return this.groups;
    }
    
    /**
     * Defines the list of groups that contains the object.
     *
     * @param groups List that contains the list of groups.
     */
    public void setGroups(Collection<? extends GroupModel> groups){
        this.groups = groups;
    }
    
    /**
     * Returns the visualization sequence of the object.
     *
     * @return Numeric value that contains the sequence.
     */
    public Long getSequence(){
        return this.sequence;
    }
    
    /**
     * Defines the visualization sequence of the object.
     *
     * @param sequence Numeric value that contains the sequence.
     */
    public void setSequence(Long sequence){
        this.sequence = sequence;
    }
    
    /**
     * Returns the identifier of the object.
     *
     * @return Numeric value that contains the identifier of the object.
     */
    public Long getId(){
        return this.id;
    }
    
    /**
     * Defines the identifier of the object.
     *
     * @param id Numeric value that contains the identifier of the object.
     */
    public void setId(Long id){
        this.id = id;
    }
    
    /**
     * Defines the type of the object.
     *
     * @param type Instance that contains the type of the object.
     */
    public void setType(ComponentType type){
        this.type = type;
    }
    
    /**
     * Returns the type of the object.
     *
     * @return Instance that contains the type of the object.
     */
    public ComponentType getType(){
        return this.type;
    }
    
    /**
     * Returns the instance of the form.
     *
     * @param <F> Class that defines the data model.
     * @return Instance that contains the form.
     */
    @SuppressWarnings("unchecked")
    public <F extends FormModel> F getForm(){
        return (F) this.form;
    }
    
    /**
     * Defines the instance of the form.
     *
     * @param form Instance that contains the form.
     */
    public void setForm(FormModel form){
        this.form = form;
    }
    
    /**
     * Returns the name of the object.
     *
     * @return String that contains the name of the object.
     */
    public String getName(){
        return this.name;
    }
    
    /**
     * Defines the name of the object.
     *
     * @param name String that contains the name of the object.
     */
    public void setName(String name){
        this.name = name;
    }
    
    /**
     * Returns the action of the object.
     *
     * @return String that contains the action of the object.
     */
    public String getAction(){
        return this.action;
    }
    
    /**
     * Defines the action of the object.
     *
     * @param action String that contains the action of the object.
     */
    public void setAction(String action){
        this.action = action;
    }
    
    /**
     * Returns the action target of the object.
     *
     * @return String that contains the action target.
     */
    public String getActionTarget(){
        return this.actionTarget;
    }
    
    /**
     * Defines the action target of the object.
     *
     * @param actionTarget String that contains the action target.
     */
    public void setActionTarget(String actionTarget){
        this.actionTarget = actionTarget;
    }
    
    /**
     * Returns the title of the object.
     *
     * @return String that contains the title.
     */
    public String getTitle(){
        return this.title;
    }
    
    /**
     * Defines the title of the object.
     *
     * @param title String that contains the title.
     */
    public void setTitle(String title){
        this.title = title;
    }
    
    /**
     * Returns the description of the object.
     *
     * @return String that contains the description.
     */
    public String getDescription(){
        return this.description;
    }
    
    /**
     * Defines the description of the object.
     *
     * @param description String that contains the description.
     */
    public void setDescription(String description){
        this.description = description;
    }
}