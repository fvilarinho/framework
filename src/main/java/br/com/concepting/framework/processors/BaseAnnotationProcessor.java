package br.com.concepting.framework.processors;

import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.processors.interfaces.IAnnotationProcessor;

/**
 * Class that defines the basic implementation of an annotation processor.
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
public abstract class BaseAnnotationProcessor implements IAnnotationProcessor{
    private Class<?> declaration = null;
    private AnnotationProcessorFactory annotationProcessorFactory = null;
    
    /**
     * Constructor - Initializes the annotation processor.
     *
     * @param declaration Instance that contains the annotated class.
     * @param annotationProcessorFactory Instance that contains the annotation processor
     * factory.
     * @throws InternalErrorException Occurs when was not possible to
     * instantiate the processor.
     */
    public BaseAnnotationProcessor(Class<?> declaration, AnnotationProcessorFactory annotationProcessorFactory) throws InternalErrorException{
        super();
        
        setDeclaration(declaration);
        setAnnotationProcessorFactory(annotationProcessorFactory);
    }
    
    /**
     * Returns the instance of the class that contains the annotations that will
     * be processed.
     *
     * @return Class that contains the annotations that will be processed.
     */
    public Class<?> getDeclaration(){
        return this.declaration;
    }
    
    /**
     * Defines the instance of the class that contains the annotations that will
     * be processed.
     *
     * @param declaration Class that contains the annotations that will be
     * processed.
     */
    public void setDeclaration(Class<?> declaration){
        this.declaration = declaration;
    }
    
    /**
     * Returns the instance of the annotation processor factory.
     *
     * @return Instance that contains the annotation processor factory.
     */
    public AnnotationProcessorFactory getAnnotationProcessorFactory(){
        return this.annotationProcessorFactory;
    }
    
    /**
     * Defines the instance of the annotation processor factory.
     *
     * @param annotationProcessorFactory Instance that contains the annotation processor
     * factory.
     */
    public void setAnnotationProcessorFactory(AnnotationProcessorFactory annotationProcessorFactory){
        this.annotationProcessorFactory = annotationProcessorFactory;
    }

    @Override
    public void process() throws InternalErrorException{
    }
}