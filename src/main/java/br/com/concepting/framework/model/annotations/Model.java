package br.com.concepting.framework.model.annotations;

import br.com.concepting.framework.controller.form.util.ActionFormValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Class that defines the annotation of a data model.
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
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Model{
    /**
     * Defines the identifier of the UI that will be used to generate code.
     *
     * @return String that contains the identifier.
     */
    String ui() default "";
    
    /**
     * Defines the identifier of the template that will be used to generate
     * code.
     *
     * @return String that contains the identifier.
     */
    String templateId() default "";
    
    /**
     * Defines the identifier of the repository that will store the data model.
     *
     * @return String that contains the identifier.
     */
    String mappedRepositoryId() default "";
    
    /**
     * Defines the properties mapping of the data model.
     *
     * @return Array that contains all mapped properties.
     */
    Property[] mappedProperties() default @Property;
    
    /**
     * Defines the identifier of the persistence resources.
     *
     * @return String that contains the identifier.
     */
    String persistenceResourcesId() default "";
    
    /**
     * Defines the form validator class.
     *
     * @return Class that defines the form validator.
     */
    Class<? extends ActionFormValidator> actionFormValidatorClass() default ActionFormValidator.class;
    
    /**
     * Defines the description pattern of the data model. The pattern should be
     * defined like the following: <pre>#{&lt;data-model-property&gt;} E.g.: #{id} - #{nome}</pre>
     *
     * @return String that contains the description pattern.
     */
    String descriptionPattern() default "";
    
    /**
     * Indicates if the data model is cacheable.
     *
     * @return True/False.
     */
    boolean cacheable() default false;
    
    /**
     * Indicates if the persistence should be generated.
     *
     * @return True/False.
     */
    boolean generatePersistence() default false;

    /**
     * Indicates if the service should be generated.
     *
     * @return True/False.
     */
    boolean generateService() default false;
}