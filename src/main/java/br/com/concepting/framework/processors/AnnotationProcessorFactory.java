package br.com.concepting.framework.processors;

import br.com.concepting.framework.constants.ProjectConstants;
import br.com.concepting.framework.constants.SystemConstants;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.processors.ModelAnnotationProcessor;
import br.com.concepting.framework.processors.helpers.ProjectBuild;
import br.com.concepting.framework.processors.interfaces.IAnnotationProcessor;
import br.com.concepting.framework.resources.SystemResources;
import br.com.concepting.framework.resources.SystemResourcesLoader;
import br.com.concepting.framework.security.constants.SecurityConstants;
import br.com.concepting.framework.security.resources.SecurityResources;
import br.com.concepting.framework.security.resources.SecurityResourcesLoader;
import br.com.concepting.framework.util.ExceptionUtil;
import br.com.concepting.framework.util.FileUtil;
import br.com.concepting.framework.util.PropertyUtil;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic.Kind;
import java.io.File;
import java.lang.reflect.Modifier;
import java.util.Set;

/**
 * Class that defines an annotation processor factory.
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
@SupportedAnnotationTypes({"br.com.concepting.framework.model.annotations.Model"})
@SupportedOptions({"buildDir", "buildName", "buildVersion"})
public class AnnotationProcessorFactory extends AbstractProcessor{
    private ProcessingEnvironment environment = null;
    private ProjectBuild build = null;
    private SystemResources systemResources = null;
    private SecurityResources securityResources = null;
    
    /**
     * Returns the instance that contains the build information.
     *
     * @return Instance that contains the build information.
     */
    public ProjectBuild getBuild(){
        return this.build;
    }
    
    /**
     * Defines the instance that contains the build information.
     *
     * @param build Instance that contains the build information.
     */
    public void setBuild(ProjectBuild build){
        this.build = build;
    }
    
    /**
     * Returns the instance that contains the properties of the operating system
     * environment.
     *
     * @return Instance that contains the properties.
     */
    protected ProcessingEnvironment getEnvironment(){
        return this.environment;
    }
    
    /**
     * Defines the instance that contains the properties of the operating system
     * environment.
     *
     * @param environment Instance that contains the properties.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    private void setEnvironment(ProcessingEnvironment environment) throws InternalErrorException{
        try{
            this.environment = environment;
            
            String buildResourcesDirname = getBuildResourcesDirname();
            SystemResourcesLoader systemResourcesLoader = new SystemResourcesLoader(buildResourcesDirname);
            
            this.systemResources = systemResourcesLoader.getDefault();
            
            SecurityResourcesLoader securityResourcesLoader = new SecurityResourcesLoader(buildResourcesDirname);
            
            this.securityResources = securityResourcesLoader.getDefault();
            
            this.build = new ProjectBuild();
            this.build.setName(getBuildName());
            this.build.setVersion(getBuildVersion());
            this.build.setBaseDirname(getBuildBaseDirname());
            this.build.setResourcesDirname(buildResourcesDirname);
            
            ExpressionProcessorUtil.setVariable(SystemConstants.RESOURCES_ATTRIBUTE_ID, this.systemResources);
            ExpressionProcessorUtil.setVariable(SecurityConstants.RESOURCES_ATTRIBUTE_ID, this.securityResources);
            ExpressionProcessorUtil.setVariable(ProjectConstants.BUILD_ATTRIBUTE_ID, this.build);
        }
        catch(Throwable e){
            this.environment.getMessager().printMessage(Kind.ERROR, ExceptionUtil.getTrace(e));
        }
    }

    @Override
    public SourceVersion getSupportedSourceVersion(){
        return SourceVersion.latestSupported();
    }
    
    /**
     * Returns the identifier of the project.
     *
     * @return String that contains the identifier.
     */
    public String getBuildName(){
        return this.environment.getOptions().get(ProjectConstants.BUILD_NAME_ATTRIBUTE_ID);
    }
    
    /**
     * Returns the version of the project.
     *
     * @return String that contains the version.
     */
    public String getBuildVersion(){
        return this.environment.getOptions().get(ProjectConstants.BUILD_VERSION_ATTRIBUTE_ID);
    }
    
    /**
     * Returns the directory of the project.
     *
     * @return String that contains the directory.
     */
    private String getBuildBaseDirname(){
        String baseDirname = new File(this.environment.getOptions().get(ProjectConstants.BUILD_DIR_ATTRIBUTE_ID)).getAbsolutePath();
        
        if(!baseDirname.endsWith(FileUtil.getDirectorySeparator()))
            baseDirname = baseDirname.concat(FileUtil.getDirectorySeparator());
        
        return baseDirname;
    }
    
    /**
     * Returns the directory of the project resources.
     *
     * @return String that contains the directory.
     */
    public String getBuildResourcesDirname(){
        String baseDirname = getBuildBaseDirname();
        
        if(!baseDirname.isEmpty()){
            StringBuilder resourcesDirname = new StringBuilder();
            
            resourcesDirname.append(baseDirname);
            resourcesDirname.append(ProjectConstants.DEFAULT_RESOURCES_DIR);
            
            return resourcesDirname.toString();
        }
        
        return null;
    }

    @Override
    public synchronized void init(ProcessingEnvironment environment){
        try{
            super.init(environment);
            
            setEnvironment(environment);
        }
        catch(InternalErrorException e){
            this.environment.getMessager().printMessage(Kind.ERROR, ExceptionUtil.getTrace(e));
        }
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment environment){
        try{
            IAnnotationProcessor annotationProcessor;

            if(this.securityResources != null && this.systemResources != null && annotations != null && !annotations.isEmpty()){
                for(TypeElement annotation: annotations){
                    Set<? extends Element> declarations = environment.getElementsAnnotatedWith(annotation);
                    
                    if(declarations != null && !declarations.isEmpty()){
                        String loginSessionClassName = null;
                        String mainConsoleClassName = null;
                        
                        for(Element declaration: declarations){
                            String declarationClassName = declaration.toString();
                            
                            if(this.securityResources.getLoginSessionClass() != null && declarationClassName.equals(this.securityResources.getLoginSessionClass().getName()))
                                loginSessionClassName = declarationClassName;
                            else if(this.systemResources.getMainConsoleClass() != null && declarationClassName.equals(this.systemResources.getMainConsoleClass().getName()))
                                mainConsoleClassName = declarationClassName;
                            else if(PropertyUtil.isModel(declarationClassName)){
                                annotationProcessor = getAnnotationProcessor(declarationClassName);
                                
                                if(annotationProcessor != null)
                                    annotationProcessor.process();
                            }
                        }
                        
                        if(loginSessionClassName != null){
                            annotationProcessor = getAnnotationProcessor(loginSessionClassName);
                            
                            if(annotationProcessor != null)
                                annotationProcessor.process();
                        }
                        
                        if(mainConsoleClassName != null){
                            annotationProcessor = getAnnotationProcessor(mainConsoleClassName);
                            
                            if(annotationProcessor != null)
                                annotationProcessor.process();
                        }
                    }
                }
            }
            
            return true;
        }
        catch(Throwable e){
            this.environment.getMessager().printMessage(Kind.ERROR, ExceptionUtil.getTrace(e));
            
            return false;
        }
    }
    
    /**
     * Returns the annotation processor based on the declaration of the object
     * that will be processed.
     *
     * @param declarationClassName String that contains the class name of the
     * object that will be processed.
     * @return Instance that contains the annotation processor.
     * @throws InternalErrorException Occurs when was not possible to execute
     * the operation.
     */
    @SuppressWarnings("unchecked")
    public IAnnotationProcessor getAnnotationProcessor(String declarationClassName) throws InternalErrorException{
        try{
            Class<? extends BaseModel> declarationClass = (Class<? extends BaseModel>) Class.forName(declarationClassName);
            
            if(PropertyUtil.isModel(declarationClass) && !Modifier.isAbstract(declarationClass.getModifiers()))
                return new ModelAnnotationProcessor(declarationClass, this);
            
            return null;
        }
        catch(ClassNotFoundException e){
            throw new InternalErrorException(e);
        }
    }
}