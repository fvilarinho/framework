package br.com.concepting.framework.model.processors;

import br.com.concepting.framework.audit.Auditor;
import br.com.concepting.framework.audit.resources.AuditorResources;
import br.com.concepting.framework.audit.resources.AuditorResourcesLoader;
import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.constants.ProjectConstants;
import br.com.concepting.framework.constants.SystemConstants;
import br.com.concepting.framework.controller.action.BaseAction;
import br.com.concepting.framework.controller.form.BaseActionForm;
import br.com.concepting.framework.controller.form.constants.ActionFormConstants;
import br.com.concepting.framework.controller.form.util.ActionFormUtil;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.MainConsoleModel;
import br.com.concepting.framework.model.SystemModuleModel;
import br.com.concepting.framework.model.SystemSessionModel;
import br.com.concepting.framework.model.helpers.ModelInfo;
import br.com.concepting.framework.model.util.ModelUtil;
import br.com.concepting.framework.network.constants.NetworkConstants;
import br.com.concepting.framework.persistence.constants.PersistenceConstants;
import br.com.concepting.framework.persistence.resources.PersistenceResources;
import br.com.concepting.framework.persistence.resources.PersistenceResourcesLoader;
import br.com.concepting.framework.persistence.types.RepositoryType;
import br.com.concepting.framework.processors.*;
import br.com.concepting.framework.processors.annotations.Tag;
import br.com.concepting.framework.processors.constants.ProcessorConstants;
import br.com.concepting.framework.processors.helpers.ProjectBuild;
import br.com.concepting.framework.resources.FactoryResources;
import br.com.concepting.framework.resources.SystemResources;
import br.com.concepting.framework.resources.SystemResourcesLoader;
import br.com.concepting.framework.resources.constants.ResourcesConstants;
import br.com.concepting.framework.security.constants.SecurityConstants;
import br.com.concepting.framework.security.model.LoginSessionModel;
import br.com.concepting.framework.security.model.UserModel;
import br.com.concepting.framework.security.util.SecurityUtil;
import br.com.concepting.framework.service.annotations.Service;
import br.com.concepting.framework.service.constants.ServiceConstants;
import br.com.concepting.framework.service.interfaces.IService;
import br.com.concepting.framework.service.util.ServiceUtil;
import br.com.concepting.framework.ui.constants.UIConstants;
import br.com.concepting.framework.util.*;
import br.com.concepting.framework.util.constants.XmlConstants;
import br.com.concepting.framework.util.helpers.DateTime;
import br.com.concepting.framework.util.helpers.JavaIndent;
import br.com.concepting.framework.util.helpers.JspIndent;
import br.com.concepting.framework.util.helpers.XmlNode;
import org.dom4j.DocumentException;
import org.dom4j.DocumentType;
import org.dom4j.tree.DefaultDocumentType;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

/**
 * Class that defines the data model processor.
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
public class ModelAnnotationProcessor extends BaseAnnotationProcessor{
    private final ModelInfo modelInfo;
    private final ProjectBuild build;
    
    /**
     * Constructor - Initializes the annotation processor.
     *
     * @param declaration Instance that contains the annotated class.
     * @param annotationProcessorFactory Instance that contains the annotation processor
     * factory.
     * @throws InternalErrorException Occurs when was not possible to
     * instantiate the processor.
     */
    public ModelAnnotationProcessor(Class<? extends BaseModel> declaration, AnnotationProcessorFactory annotationProcessorFactory) throws InternalErrorException{
        super(declaration, annotationProcessorFactory);
        
        try{
            this.modelInfo = ModelUtil.getInfo(declaration);
            this.build = annotationProcessorFactory.getBuild();
        }
        catch(IllegalArgumentException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException | ClassNotFoundException | NoSuchFieldException e){
            throw new InternalErrorException(e);
        }
    }
    
    /**
     * Returns the instance that contains the properties of the data model.
     *
     * @return Instance that contains the properties of the data model.
     */
    public ModelInfo getModelInfo(){
        return this.modelInfo;
    }

    @Override
    public void process() throws InternalErrorException{
        if( modelInfo.isAbstract())
            return;
        
        LoginSessionModel loginSession = SecurityUtil.getLoginSession(this.build.getResourcesDirname());
        
        loginSession.setStartDateTime(new DateTime());
        
        UserModel user = loginSession.getUser();
        
        user.setName(SecurityUtil.getSystemUserName());
        
        SystemModuleModel systemModule = loginSession.getSystemModule();
        
        systemModule.setName(this.build.getName());
        systemModule.setVersion(this.build.getVersion());
        
        SystemSessionModel systemSession = loginSession.getSystemSession();
        
        systemSession.setId(SecurityUtil.generateToken());
        
        try{
            InetAddress localhost = InetAddress.getLocalHost();
            
            systemSession.setIp(localhost.getHostAddress());
            systemSession.setHostName(localhost.getHostName());
        }
        catch(UnknownHostException e){
            systemSession.setIp(NetworkConstants.DEFAULT_LOCALHOST_ADDRESS_ID);
            systemSession.setHostName(NetworkConstants.DEFAULT_LOCALHOST_NAME_ID);
        }
        
        ExpressionProcessorUtil.setVariable(ProcessorConstants.DEFAULT_NOW_ID, loginSession.getStartDateTime());
        ExpressionProcessorUtil.setVariable(ProcessorConstants.DEFAULT_RANDOM_ID, (int) (Math.random() * NumberUtil.getMaximumRange(Long.class)));
        ExpressionProcessorUtil.setVariable(SecurityConstants.USER_ATTRIBUTE_ID, loginSession.getUser());
        
        StringBuilder templateFilesDirname = new StringBuilder();
        
        templateFilesDirname.append(this.build.getBaseDirname());
        templateFilesDirname.append(ProjectConstants.DEFAULT_TEMPLATES_DIR);
        templateFilesDirname.append(this.modelInfo.getTemplateId());
        
        File templateFilesDir = new File(templateFilesDirname.toString());
        
        if(templateFilesDir.exists()){
            Method[] templateMethods = getClass().getMethods();
            File[] templateFiles = templateFilesDir.listFiles();
            AuditorResourcesLoader loader = new AuditorResourcesLoader(this.build.getResourcesDirname());
            AuditorResources auditorResources = loader.getDefault();
            Auditor auditor = null;

            if (templateFiles != null) {
                for (File templateFile : templateFiles) {
                    for (Method business : templateMethods) {
                        try {
                            Tag methodTag = business.getAnnotation(Tag.class);

                            if (methodTag != null && methodTag.value().equals(templateFile.getName())) {
                                if (auditorResources != null) {
                                    Class<?> entity = getClass();

                                    auditor = new Auditor(entity, business, new Object[]{this.modelInfo.getClazz().getName()}, loginSession, auditorResources);
                                    auditor.start();
                                }

                                business.invoke(this, templateFile.getAbsolutePath());

                                if (auditor != null)
                                    auditor.end();
                            }
                        } catch (Throwable e) {
                            if (auditor != null)
                                auditor.end(e);
                        }
                    }
                }
            }
            
            updatePersistenceResources();
        }
    }
    
    /**
     * Generates the action class.
     *
     * @param actionClassTemplateFilename String that contains the action class template filename.
     * @throws InternalErrorException Occurs when was not possible to generate
     * the class.
     */
    @SuppressWarnings("unchecked")
    @Tag(ProjectConstants.DEFAULT_ACTION_CLASS_TEMPLATE_FILE_ID)
    public void generateActionClass(String actionClassTemplateFilename) throws InternalErrorException{
        try{
            File actionClassTemplateFile = new File(actionClassTemplateFilename);
            XmlReader actionClassTemplateReader = new XmlReader(actionClassTemplateFile);
            String encoding = actionClassTemplateReader.getEncoding();
            XmlNode actionClassTemplateNode = actionClassTemplateReader.getRoot();
            List<XmlNode> actionClassTemplateArtifactsNode = actionClassTemplateNode.getChildren();
            
            if(actionClassTemplateArtifactsNode != null && !actionClassTemplateArtifactsNode.isEmpty()){
                ProjectBuild build = getAnnotationProcessorFactory().getBuild();
                ProcessorFactory processorFactory = ProcessorFactory.getInstance();
                ExpressionProcessor expressionProcessor = new ExpressionProcessor(this.modelInfo);
                
                for(XmlNode actionClassTemplateArtifactNode: actionClassTemplateArtifactsNode){
                    String outputDir = expressionProcessor.evaluate(actionClassTemplateArtifactNode.getAttribute(Constants.OUTPUT_DIRECTORY_ATTRIBUTE_ID));
                    String packagePrefix = expressionProcessor.evaluate(actionClassTemplateArtifactNode.getAttribute(Constants.PACKAGE_PREFIX_ATTRIBUTE_ID));
                    String packageName = expressionProcessor.evaluate(actionClassTemplateArtifactNode.getAttribute(Constants.PACKAGE_NAME_ATTRIBUTE_ID));
                    String packageSuffix = expressionProcessor.evaluate(actionClassTemplateArtifactNode.getAttribute(Constants.PACKAGE_SUFFIX_ATTRIBUTE_ID));
                    String name = expressionProcessor.evaluate(actionClassTemplateArtifactNode.getAttribute(Constants.NAME_ATTRIBUTE_ID));
                    StringBuilder packageNameBuffer = new StringBuilder();
                    
                    if(packagePrefix != null && !packagePrefix.isEmpty())
                        packageNameBuffer.append(packagePrefix);
                    
                    if(packageName != null && !packageName.isEmpty()){
                        if(packageNameBuffer.length() > 0)
                            packageNameBuffer.append(".");
                        
                        packageNameBuffer.append(packageName);
                    }
                    
                    if(packageSuffix != null && !packageSuffix.isEmpty()){
                        if(packageNameBuffer.length() > 0)
                            packageNameBuffer.append(".");
                        
                        packageNameBuffer.append(packageSuffix);
                    }
                    
                    packageName = packageNameBuffer.toString();
                    
                    StringBuilder actionClassName = new StringBuilder();
                    
                    if(!packageName.isEmpty()){
                        actionClassName.append(packageName);
                        actionClassName.append(".");
                    }
                    
                    actionClassName.append(name);
                    
                    StringBuilder actionClassFilename = new StringBuilder();
                    
                    if(outputDir != null && !outputDir.isEmpty()){
                        actionClassFilename.append(outputDir);
                        actionClassFilename.append(FileUtil.getDirectorySeparator());
                    }
                    else{
                        actionClassFilename.append(build.getBaseDirname());
                        actionClassFilename.append(FileUtil.getDirectorySeparator());
                        actionClassFilename.append(ProjectConstants.DEFAULT_JAVA_DIR);
                    }
                    
                    actionClassFilename.append(StringUtil.replaceAll(actionClassName.toString(), ".", FileUtil.getDirectorySeparator()));
                    actionClassFilename.append(ProjectConstants.DEFAULT_JAVA_FILE_EXTENSION);
                    
                    File actionClassFile = new File(actionClassFilename.toString());
                    
                    if(!actionClassFile.exists()){
                        if(this.modelInfo.generateUi()){
                            ExpressionProcessorUtil.setVariable(Constants.PACKAGE_PREFIX_ATTRIBUTE_ID, packagePrefix);
                            ExpressionProcessorUtil.setVariable(Constants.PACKAGE_SUFFIX_ATTRIBUTE_ID, packageSuffix);
                            ExpressionProcessorUtil.setVariable(Constants.PACKAGE_NAME_ATTRIBUTE_ID, packageName);
                            ExpressionProcessorUtil.setVariable(Constants.NAME_ATTRIBUTE_ID, name);
                            
                            GenericProcessor processor = processorFactory.getProcessor(this.modelInfo, actionClassTemplateArtifactNode);
                            String actionClassContent = StringUtil.indent(processor.process(), JavaIndent.getRules());
                            
                            if(!actionClassContent.isEmpty())
                                FileUtil.toTextFile(actionClassFilename.toString(), actionClassContent, encoding);
                        }
                    }
                    else{
                        if(!this.modelInfo.generateUi()){
                            try{
                                Class<? extends BaseAction<? extends BaseModel>> actionClass = (Class<? extends BaseAction<? extends BaseModel>>) Class.forName(actionClassName.toString());
                                
                                if(!Modifier.isAbstract(actionClass.getModifiers()))
                                    actionClassFile.delete();
                            }
                            catch(ClassNotFoundException e){
                                actionClassFile.delete();
                            }
                        }
                    }
                }
            }
        }
        catch(IOException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e){
            throw new InternalErrorException(e);
        }
    }
    
    /**
     * Generates the form class.
     *
     * @param actionFormClassTemplateFilename String that contains the action form class template filename.
     * @throws InternalErrorException Occurs when was not possible to generate
     * the class.
     */
    @SuppressWarnings("unchecked")
    @Tag(ProjectConstants.DEFAULT_ACTION_FORM_CLASS_TEMPLATE_FILE_ID)
    public void generateActionFormClass(String actionFormClassTemplateFilename) throws InternalErrorException{
        try{
            File actionFormClassTemplateFile = new File(actionFormClassTemplateFilename);
            XmlReader actionFormClassTemplateReader = new XmlReader(actionFormClassTemplateFile);
            String encoding = actionFormClassTemplateReader.getEncoding();
            XmlNode actionFormClassTemplateNode = actionFormClassTemplateReader.getRoot();
            List<XmlNode> actionFormClassTemplateArtifactsNode = actionFormClassTemplateNode.getChildren();
            
            if(actionFormClassTemplateArtifactsNode != null && !actionFormClassTemplateArtifactsNode.isEmpty()){
                ProcessorFactory processorFactory = ProcessorFactory.getInstance();
                ExpressionProcessor expressionProcessor = new ExpressionProcessor(this.modelInfo);
                
                for(XmlNode actionFormClassTemplateArtifactNode: actionFormClassTemplateArtifactsNode){
                    String outputDir = expressionProcessor.evaluate(actionFormClassTemplateArtifactNode.getAttribute(Constants.OUTPUT_DIRECTORY_ATTRIBUTE_ID));
                    String packagePrefix = expressionProcessor.evaluate(actionFormClassTemplateArtifactNode.getAttribute(Constants.PACKAGE_PREFIX_ATTRIBUTE_ID));
                    String packageName = expressionProcessor.evaluate(actionFormClassTemplateArtifactNode.getAttribute(Constants.PACKAGE_NAME_ATTRIBUTE_ID));
                    String packageSuffix = expressionProcessor.evaluate(actionFormClassTemplateArtifactNode.getAttribute(Constants.PACKAGE_SUFFIX_ATTRIBUTE_ID));
                    String name = expressionProcessor.evaluate(actionFormClassTemplateArtifactNode.getAttribute(Constants.NAME_ATTRIBUTE_ID));
                    StringBuilder packageNameBuffer = new StringBuilder();
                    
                    if(packagePrefix != null && !packagePrefix.isEmpty())
                        packageNameBuffer.append(packagePrefix);
                    
                    if(packageName != null && !packageName.isEmpty()){
                        if(packageNameBuffer.length() > 0)
                            packageNameBuffer.append(".");
                        
                        packageNameBuffer.append(packageName);
                    }
                    
                    if(packageSuffix != null && !packageSuffix.isEmpty()){
                        if(packageNameBuffer.length() > 0)
                            packageNameBuffer.append(".");
                        
                        packageNameBuffer.append(packageSuffix);
                    }
                    
                    packageName = packageNameBuffer.toString();
                    
                    StringBuilder actionFormClassName = new StringBuilder();
                    
                    if(!packageName.isEmpty()){
                        actionFormClassName.append(packageName);
                        actionFormClassName.append(".");
                    }
                    
                    actionFormClassName.append(name);
                    
                    StringBuilder actionFormClassFilename = new StringBuilder();
                    
                    if(outputDir != null && !outputDir.isEmpty()){
                        actionFormClassFilename.append(outputDir);
                        actionFormClassFilename.append(FileUtil.getDirectorySeparator());
                    }
                    else{
                        actionFormClassFilename.append(this.build.getBaseDirname());
                        actionFormClassFilename.append(ProjectConstants.DEFAULT_JAVA_DIR);
                    }
                    
                    actionFormClassFilename.append(StringUtil.replaceAll(actionFormClassName.toString(), ".", FileUtil.getDirectorySeparator()));
                    actionFormClassFilename.append(ProjectConstants.DEFAULT_JAVA_FILE_EXTENSION);
                    
                    File actionFormClassFile = new File(actionFormClassFilename.toString());
                    
                    if(!actionFormClassFile.exists()){
                        if(this.modelInfo.generateUi()){
                            ExpressionProcessorUtil.setVariable(Constants.PACKAGE_PREFIX_ATTRIBUTE_ID, packagePrefix);
                            ExpressionProcessorUtil.setVariable(Constants.PACKAGE_SUFFIX_ATTRIBUTE_ID, packageSuffix);
                            ExpressionProcessorUtil.setVariable(Constants.PACKAGE_NAME_ATTRIBUTE_ID, packageName);
                            ExpressionProcessorUtil.setVariable(Constants.NAME_ATTRIBUTE_ID, name);
                            
                            GenericProcessor processor = processorFactory.getProcessor(this.modelInfo, actionFormClassTemplateArtifactNode);
                            String actionFormClassContent = StringUtil.indent(processor.process(), JavaIndent.getRules());
                            
                            if(!actionFormClassContent.isEmpty())
                                FileUtil.toTextFile(actionFormClassFilename.toString(), actionFormClassContent, encoding);
                            
                            addActionFormMapping(actionFormClassName.toString());
                        }
                    }
                    else{
                        if(!this.modelInfo.generateUi()){
                            try{
                                Class<? extends BaseActionForm<? extends BaseModel>> actionFormClass = (Class<? extends BaseActionForm<? extends BaseModel>>) Class.forName(actionFormClassName.toString());
                                
                                if(!Modifier.isAbstract(actionFormClass.getModifiers())){
                                    actionFormClassFile.delete();
                                    
                                    removeActionFormMapping(actionFormClassName.toString());
                                }
                            }
                            catch(ClassNotFoundException e){
                                actionFormClassFile.delete();
                                
                                removeActionFormMapping(actionFormClassName.toString());
                            }
                        }
                        else
                            addActionFormMapping(actionFormClassName.toString());
                    }
                }
            }
        }
        catch(IOException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e){
            throw new InternalErrorException(e);
        }
    }
    
    /**
     * Adds an action form mapping.
     *
     * @param currentActionFormClassName String that contains the action form
     * class name.
     * @throws InternalErrorException Occurs when was not possible to execute
     * the operation.
     */
    private void addActionFormMapping(String currentActionFormClassName) throws InternalErrorException{
        try{
            if(currentActionFormClassName == null || currentActionFormClassName.isEmpty())
                return;
            
            StringBuilder systemResourcesFilename = new StringBuilder();
            
            systemResourcesFilename.append(this.build.getResourcesDirname());
            systemResourcesFilename.append(SystemConstants.DEFAULT_RESOURCES_ID);
            
            XmlReader reader = new XmlReader(new File(systemResourcesFilename.toString()));
            XmlNode content = reader.getRoot();
            XmlNode actionFormsNode = content.getNode(ActionFormConstants.ACTION_FORMS_ATTRIBUTE_ID);
            List<XmlNode> actionFormNodes = (actionFormsNode != null ? actionFormsNode.getChildren() : null);
            boolean found = false;
            
            if(actionFormNodes != null && !actionFormNodes.isEmpty()){
                for(XmlNode actionFormNode: actionFormNodes){
                    String actionFormClassName = actionFormNode.getAttribute(Constants.CLASS_ATTRIBUTE_ID);
                    
                    if(actionFormClassName != null && actionFormClassName.equals(currentActionFormClassName)){
                        found = true;
                        
                        break;
                    }
                }
            }
            
            if(!found){
                XmlNode actionFormNode = new XmlNode(ActionFormConstants.ACTION_FORM_ATTRIBUTE_ID);
                String currentModelClassName = ActionFormUtil.getModelClassNameByActionForm(currentActionFormClassName);
                String currentActionFormName = ActionFormUtil.getActionFormIdByModel(currentModelClassName);
                
                actionFormNode.addAttribute(Constants.NAME_ATTRIBUTE_ID, currentActionFormName);
                actionFormNode.addAttribute(Constants.CLASS_ATTRIBUTE_ID, currentActionFormClassName);
                
                StringBuilder actionFormUrl = new StringBuilder();
                String actionFormUrlBuffer = ModelUtil.getUrlByModel(currentModelClassName);
                
                actionFormUrl.append("/");
                actionFormUrl.append(ProjectConstants.DEFAULT_UI_PAGES_DIR);
                actionFormUrl.append(actionFormUrlBuffer.substring(1));
                actionFormUrl.append("/");
                actionFormUrl.append(ProjectConstants.DEFAULT_UI_PAGE_FILE_ID);
                
                actionFormNode.addAttribute(ActionFormConstants.ACTION_ATTRIBUTE_ID, actionFormUrlBuffer);
                
                XmlNode forwardsNode = new XmlNode(ActionFormConstants.FORWARDS_ATTRIBUTE_ID);
                XmlNode forwardNode = new XmlNode(ActionFormConstants.FORWARD_ATTRIBUTE_ID);
                
                forwardNode.addAttribute(Constants.NAME_ATTRIBUTE_ID, ActionFormConstants.DEFAULT_FORWARD_ID);
                forwardNode.addAttribute(SystemConstants.URL_ATTRIBUTE_ID, actionFormUrl.toString());
                
                forwardsNode.addChild(forwardNode);
                
                actionFormNode.addChild(forwardsNode);

                if(actionFormsNode != null)
                    actionFormsNode.addChild(actionFormNode);
                
                XmlWriter writer = new XmlWriter(new File(systemResourcesFilename.toString()));
                
                writer.write(content);
            }
        }
        catch(DocumentException | IOException e){
            throw new InternalErrorException(e);
        }
    }
    
    /**
     * Removes the action form mapping.
     *
     * @param currentActionFormClassName String that contains the action form
     * class name.
     * @throws InternalErrorException Occurs when was not possible to execute
     * the operation.
     */
    private void removeActionFormMapping(String currentActionFormClassName) throws InternalErrorException{
        try{
            if(currentActionFormClassName == null || currentActionFormClassName.isEmpty())
                return;
            
            StringBuilder systemResourcesFilename = new StringBuilder();
            
            systemResourcesFilename.append(this.build.getResourcesDirname());
            systemResourcesFilename.append(SystemConstants.DEFAULT_RESOURCES_ID);
            
            XmlReader reader = new XmlReader(new File(systemResourcesFilename.toString()));
            XmlNode content = reader.getRoot();
            XmlNode actionFormsNode = content.getNode(ActionFormConstants.ACTION_FORMS_ATTRIBUTE_ID);
            List<XmlNode> actionFormNodes = (actionFormsNode != null ? actionFormsNode.getChildren() : null);
            boolean found = false;
            
            if(actionFormNodes != null && !actionFormNodes.isEmpty()){
                for(int cont = 0; cont < actionFormNodes.size(); cont++){
                    XmlNode actionFormNode = actionFormNodes.get(cont);
                    String actionFormClassName = actionFormNode.getAttribute(Constants.CLASS_ATTRIBUTE_ID);
                    
                    if(actionFormClassName != null && actionFormClassName.equals(currentActionFormClassName)){
                        actionFormNodes.remove(cont);
                        
                        found = true;
                        
                        break;
                    }
                }
                
                if(found){
                    XmlWriter writer = new XmlWriter(new File(systemResourcesFilename.toString()));
                    
                    writer.write(content);
                }
            }
        }
        catch(DocumentException | IOException e){
            throw new InternalErrorException(e);
        }
    }
    
    /**
     * Generates the model class.
     *
     * @param modelClassTemplateFilename String that contains the model class template filename.
     * @throws InternalErrorException Occurs when was not possible to generate
     * the class.
     */
    @Tag(ProjectConstants.DEFAULT_MODEL_CLASS_TEMPLATE_FILE_ID)
    public void generateModelClass(String modelClassTemplateFilename) throws InternalErrorException{
        try{
            File modelClassTemplateFile = new File(modelClassTemplateFilename);
            XmlReader modelClassTemplateReader = new XmlReader(modelClassTemplateFile);
            String encoding = modelClassTemplateReader.getEncoding();
            XmlNode modelClassTemplateNode = modelClassTemplateReader.getRoot();
            List<XmlNode> modelClassTemplateArtifactsNode = modelClassTemplateNode.getChildren();
            
            if(modelClassTemplateArtifactsNode != null && !modelClassTemplateArtifactsNode.isEmpty()){
                ProcessorFactory processorFactory = ProcessorFactory.getInstance();
                ExpressionProcessor expressionProcessor = new ExpressionProcessor(this.modelInfo);
                
                for(XmlNode modelClassTemplateArtifactNode: modelClassTemplateArtifactsNode){
                    String outputDir = expressionProcessor.evaluate(modelClassTemplateArtifactNode.getAttribute(Constants.OUTPUT_DIRECTORY_ATTRIBUTE_ID));
                    String packagePrefix = expressionProcessor.evaluate(modelClassTemplateArtifactNode.getAttribute(Constants.PACKAGE_PREFIX_ATTRIBUTE_ID));
                    String packageName = expressionProcessor.evaluate(modelClassTemplateArtifactNode.getAttribute(Constants.PACKAGE_NAME_ATTRIBUTE_ID));
                    String packageSuffix = expressionProcessor.evaluate(modelClassTemplateArtifactNode.getAttribute(Constants.PACKAGE_SUFFIX_ATTRIBUTE_ID));
                    String name = expressionProcessor.evaluate(modelClassTemplateArtifactNode.getAttribute(Constants.NAME_ATTRIBUTE_ID));
                    StringBuilder packageNameBuffer = new StringBuilder();
                    
                    if(packagePrefix != null && !packagePrefix.isEmpty())
                        packageNameBuffer.append(packagePrefix);
                    
                    if(packageName != null && !packageName.isEmpty()){
                        if(packageNameBuffer.length() > 0)
                            packageNameBuffer.append(".");
                        
                        packageNameBuffer.append(packageName);
                    }
                    
                    if(packageSuffix != null && !packageSuffix.isEmpty()){
                        if(packageNameBuffer.length() > 0)
                            packageNameBuffer.append(".");
                        
                        packageNameBuffer.append(packageSuffix);
                    }
                    
                    packageName = packageNameBuffer.toString();
                    
                    StringBuilder modelClassName = new StringBuilder();
                    
                    if(!packageName.isEmpty()){
                        modelClassName.append(packageName);
                        modelClassName.append(".");
                    }
                    
                    modelClassName.append(name);
                    
                    StringBuilder modelClassFilename = new StringBuilder();
                    
                    if(outputDir != null && !outputDir.isEmpty()){
                        modelClassFilename.append(outputDir);
                        modelClassFilename.append(FileUtil.getDirectorySeparator());
                    }
                    else{
                        modelClassFilename.append(this.build.getBaseDirname());
                        modelClassFilename.append(ProjectConstants.DEFAULT_JAVA_DIR);
                    }
                    
                    modelClassFilename.append(StringUtil.replaceAll(modelClassName.toString(), ".", FileUtil.getDirectorySeparator()));
                    modelClassFilename.append(ProjectConstants.DEFAULT_JAVA_FILE_EXTENSION);
                    
                    File modelClassFile = new File(modelClassFilename.toString());
                    
                    if(!modelClassFile.exists()){
                        if(this.modelInfo.generatePersistence() || this.modelInfo.generateService()){
                            ExpressionProcessorUtil.setVariable(Constants.PACKAGE_PREFIX_ATTRIBUTE_ID, packagePrefix);
                            ExpressionProcessorUtil.setVariable(Constants.PACKAGE_SUFFIX_ATTRIBUTE_ID, packageSuffix);
                            ExpressionProcessorUtil.setVariable(Constants.PACKAGE_NAME_ATTRIBUTE_ID, packageName);
                            ExpressionProcessorUtil.setVariable(Constants.NAME_ATTRIBUTE_ID, name);
                            
                            GenericProcessor processor = processorFactory.getProcessor(this.modelInfo, modelClassTemplateArtifactNode);
                            String modelClassContent = StringUtil.indent(processor.process(), JavaIndent.getRules());
                            
                            if(!modelClassContent.isEmpty())
                                FileUtil.toTextFile(modelClassFilename.toString(), modelClassContent, encoding);
                            
                            addCheckGeneratedCode(modelClassName.toString());
                        }
                    }
                }
            }
        }
        catch(IOException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e){
            throw new InternalErrorException(e);
        }
    }
    
    /**
     * Generates the unit tests of the model class.
     *
     * @param modelClassTestTemplateFilename String that contains the model class unit test template filename.
     * @throws InternalErrorException Occurs when was not possible to generate
     * the class.
     */
    @Tag(ProjectConstants.DEFAULT_MODEL_CLASS_TEST_TEMPLATE_FILE_ID)
    public void generateModelClassTest(String modelClassTestTemplateFilename) throws InternalErrorException{
        try{
            File modelClassTestTemplateFile = new File(modelClassTestTemplateFilename);
            XmlReader modelClassTestTemplateReader = new XmlReader(modelClassTestTemplateFile);
            String encoding = modelClassTestTemplateReader.getEncoding();
            XmlNode modelClassTestTemplateNode = modelClassTestTemplateReader.getRoot();
            List<XmlNode> modelClassTestTemplateArtifactsNode = modelClassTestTemplateNode.getChildren();
            
            if(modelClassTestTemplateArtifactsNode != null && !modelClassTestTemplateArtifactsNode.isEmpty()){
                ProcessorFactory processorFactory = ProcessorFactory.getInstance();
                ExpressionProcessor expressionProcessor = new ExpressionProcessor(this.modelInfo);
                
                for(XmlNode modelClassTestTemplateArtifactNode: modelClassTestTemplateArtifactsNode){
                    String outputDir = expressionProcessor.evaluate(modelClassTestTemplateArtifactNode.getAttribute(Constants.OUTPUT_DIRECTORY_ATTRIBUTE_ID));
                    String packagePrefix = expressionProcessor.evaluate(modelClassTestTemplateArtifactNode.getAttribute(Constants.PACKAGE_PREFIX_ATTRIBUTE_ID));
                    String packageName = expressionProcessor.evaluate(modelClassTestTemplateArtifactNode.getAttribute(Constants.PACKAGE_NAME_ATTRIBUTE_ID));
                    String packageSuffix = expressionProcessor.evaluate(modelClassTestTemplateArtifactNode.getAttribute(Constants.PACKAGE_SUFFIX_ATTRIBUTE_ID));
                    String name = expressionProcessor.evaluate(modelClassTestTemplateArtifactNode.getAttribute(Constants.NAME_ATTRIBUTE_ID));
                    StringBuilder packageNameBuffer = new StringBuilder();
                    
                    if(packagePrefix != null && !packagePrefix.isEmpty())
                        packageNameBuffer.append(packagePrefix);
                    
                    if(packageName != null && !packageName.isEmpty()){
                        if(packageNameBuffer.length() > 0)
                            packageNameBuffer.append(".");
                        
                        packageNameBuffer.append(packageName);
                    }
                    
                    if(packageSuffix != null && !packageSuffix.isEmpty()){
                        if(packageNameBuffer.length() > 0)
                            packageNameBuffer.append(".");
                        
                        packageNameBuffer.append(packageSuffix);
                    }
                    
                    packageName = packageNameBuffer.toString();
                    
                    StringBuilder modelClassName = new StringBuilder();
                    
                    if(!packageName.isEmpty()){
                        modelClassName.append(packageName);
                        modelClassName.append(".");
                    }
                    
                    modelClassName.append(name);
                    
                    StringBuilder modelClassTestFilename = new StringBuilder();
                    
                    if(outputDir != null && !outputDir.isEmpty()){
                        modelClassTestFilename.append(outputDir);
                        modelClassTestFilename.append(FileUtil.getDirectorySeparator());
                    }
                    else{
                        modelClassTestFilename.append(this.build.getBaseDirname());
                        modelClassTestFilename.append(ProjectConstants.DEFAULT_TESTS_DIR);
                    }
                    
                    modelClassTestFilename.append(StringUtil.replaceAll(modelClassName.toString(), ".", FileUtil.getDirectorySeparator()));
                    modelClassTestFilename.append(ProjectConstants.DEFAULT_JAVA_FILE_EXTENSION);
                    
                    File modelClassFile = new File(modelClassTestFilename.toString());
                    
                    if(!modelClassFile.exists()){
                        ExpressionProcessorUtil.setVariable(Constants.PACKAGE_PREFIX_ATTRIBUTE_ID, packagePrefix);
                        ExpressionProcessorUtil.setVariable(Constants.PACKAGE_SUFFIX_ATTRIBUTE_ID, packageSuffix);
                        ExpressionProcessorUtil.setVariable(Constants.PACKAGE_NAME_ATTRIBUTE_ID, packageName);
                        ExpressionProcessorUtil.setVariable(Constants.NAME_ATTRIBUTE_ID, name);
                        
                        GenericProcessor processor = processorFactory.getProcessor(this.modelInfo, modelClassTestTemplateArtifactNode);
                        String modelClassTestContent = StringUtil.indent(processor.process(), JavaIndent.getRules());
                        
                        if(!modelClassTestContent.isEmpty())
                            FileUtil.toTextFile(modelClassTestFilename.toString(), modelClassTestContent, encoding);
                    }
                }
            }
        }
        catch(IOException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e){
            throw new InternalErrorException(e);
        }
    }
    
    /**
     * Generates the resources.
     *
     * @param resourcesTemplateFilename String that contains the resource template filename.
     * @throws InternalErrorException Occurs when was not possible to generate
     * the file.
     */
    @Tag(ProjectConstants.DEFAULT_RESOURCES_TEMPLATE_FILE_ID)
    public void generateResources(String resourcesTemplateFilename) throws InternalErrorException{
        try{
            File resourcesTemplateFile = new File(resourcesTemplateFilename);
            XmlReader resourcesTemplateReader = new XmlReader(resourcesTemplateFile);
            String encoding = resourcesTemplateReader.getEncoding();
            XmlNode resourcesTemplateNode = resourcesTemplateReader.getRoot();
            List<XmlNode> resourcesTemplateArtifactsNode = resourcesTemplateNode.getChildren();
            
            if(resourcesTemplateArtifactsNode != null && !resourcesTemplateArtifactsNode.isEmpty()){
                SystemResourcesLoader systemResourcesLoader = new SystemResourcesLoader(this.build.getResourcesDirname());
                SystemResources systemResources = systemResourcesLoader.getDefault();
                
                if(systemResources != null){
                    Collection<Locale> availableLanguages = systemResources.getLanguages();
                    
                    if(availableLanguages != null && !availableLanguages.isEmpty()){
                        ProcessorFactory processorFactory = ProcessorFactory.getInstance();
                        ExpressionProcessor expressionProcessor = new ExpressionProcessor(this.modelInfo);
                        
                        for(XmlNode resourcesTemplateArtifactNode: resourcesTemplateArtifactsNode){
                            String outputDir = expressionProcessor.evaluate(resourcesTemplateArtifactNode.getAttribute(Constants.OUTPUT_DIRECTORY_ATTRIBUTE_ID));
                            String packagePrefix = expressionProcessor.evaluate(resourcesTemplateArtifactNode.getAttribute(Constants.PACKAGE_PREFIX_ATTRIBUTE_ID));
                            String packageName = expressionProcessor.evaluate(resourcesTemplateArtifactNode.getAttribute(Constants.PACKAGE_NAME_ATTRIBUTE_ID));
                            String packageSuffix = expressionProcessor.evaluate(resourcesTemplateArtifactNode.getAttribute(Constants.PACKAGE_SUFFIX_ATTRIBUTE_ID));
                            String name = expressionProcessor.evaluate(resourcesTemplateArtifactNode.getAttribute(Constants.NAME_ATTRIBUTE_ID));
                            StringBuilder packageNameBuffer = new StringBuilder();
                            
                            if(packagePrefix != null && !packagePrefix.isEmpty())
                                packageNameBuffer.append(packagePrefix);
                            
                            if(packageName != null && !packageName.isEmpty()){
                                if(packageNameBuffer.length() > 0)
                                    packageNameBuffer.append(".");
                                
                                packageNameBuffer.append(packageName);
                            }
                            
                            if(packageSuffix != null && !packageSuffix.isEmpty()){
                                if(packageNameBuffer.length() > 0)
                                    packageNameBuffer.append(".");
                                
                                packageNameBuffer.append(packageSuffix);
                            }
                            
                            packageName = packageNameBuffer.toString();
                            
                            StringBuilder resourcesName = new StringBuilder();
                            
                            if(!packageName.isEmpty()){
                                resourcesName.append(packageName);
                                resourcesName.append(".");
                            }
                            
                            resourcesName.append(name);
                            
                            for(Locale availableLanguage: availableLanguages){
                                StringBuilder resourcesFilename = new StringBuilder();
                                
                                if(outputDir != null && !outputDir.isEmpty()){
                                    resourcesFilename.append(outputDir);
                                    resourcesFilename.append(FileUtil.getDirectorySeparator());
                                }
                                else
                                    resourcesFilename.append(this.build.getResourcesDirname());

                                resourcesFilename.append(StringUtil.replaceAll(resourcesName.toString(), ".", FileUtil.getDirectorySeparator()));
                                resourcesFilename.append("_");
                                resourcesFilename.append(availableLanguage);
                                resourcesFilename.append(ResourcesConstants.DEFAULT_PROPERTIES_RESOURCES_FILE_EXTENSION);
                                
                                File resourcesFile = new File(resourcesFilename.toString());
                                
                                if(!resourcesFile.exists()){
                                    if(this.modelInfo.generateService() || this.modelInfo.generateUi()){
                                        ExpressionProcessorUtil.setVariable(Constants.PACKAGE_PREFIX_ATTRIBUTE_ID, packagePrefix);
                                        ExpressionProcessorUtil.setVariable(Constants.PACKAGE_SUFFIX_ATTRIBUTE_ID, packageSuffix);
                                        ExpressionProcessorUtil.setVariable(Constants.PACKAGE_NAME_ATTRIBUTE_ID, packageName);
                                        ExpressionProcessorUtil.setVariable(Constants.NAME_ATTRIBUTE_ID, name);
                                        
                                        GenericProcessor processor = processorFactory.getProcessor(this.modelInfo, resourcesTemplateArtifactNode);
                                        String resourcesContent = StringUtil.indent(processor.process(), JavaIndent.getRules());
                                        
                                        FileUtil.toTextFile(resourcesFilename.toString(), resourcesContent, encoding);
                                    }
                                }
                                else{
                                    if(!this.modelInfo.generateService() && !this.modelInfo.generateUi())
                                        resourcesFile.delete();
                                }
                            }
                        }
                    }
                }
            }
        }
        catch(IOException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e){
            throw new InternalErrorException(e);
        }
    }
    
    /**
     * Generates the persistence class.
     *
     * @param persistenceClassTemplateFilename String that contains the persistence class template filename.
     * @throws InternalErrorException Occurs when was not possible to generate
     * the class.
     */
    @Tag(ProjectConstants.DEFAULT_PERSISTENCE_CLASS_TEMPLATE_FILE_ID)
    public void generatePersistenceClass(String persistenceClassTemplateFilename) throws InternalErrorException{
        try{
            File persistenceClassTemplateFile = new File(persistenceClassTemplateFilename);
            XmlReader persistenceClassTemplateReader = new XmlReader(persistenceClassTemplateFile);
            String encoding = persistenceClassTemplateReader.getEncoding();
            XmlNode persistenceClassTemplateNode = persistenceClassTemplateReader.getRoot();
            List<XmlNode> persistenceClassTemplateArtifactsNode = persistenceClassTemplateNode.getChildren();
            
            if(persistenceClassTemplateArtifactsNode != null && !persistenceClassTemplateArtifactsNode.isEmpty()){
                ProcessorFactory processorFactory = ProcessorFactory.getInstance();
                ExpressionProcessor expressionProcessor = new ExpressionProcessor(this.modelInfo);
                
                for(XmlNode persistenceClassTemplateArtifactNode: persistenceClassTemplateArtifactsNode){
                    String outputDir = expressionProcessor.evaluate(persistenceClassTemplateArtifactNode.getAttribute(Constants.OUTPUT_DIRECTORY_ATTRIBUTE_ID));
                    String packagePrefix = expressionProcessor.evaluate(persistenceClassTemplateArtifactNode.getAttribute(Constants.PACKAGE_PREFIX_ATTRIBUTE_ID));
                    String packageName = expressionProcessor.evaluate(persistenceClassTemplateArtifactNode.getAttribute(Constants.PACKAGE_NAME_ATTRIBUTE_ID));
                    String packageSuffix = expressionProcessor.evaluate(persistenceClassTemplateArtifactNode.getAttribute(Constants.PACKAGE_SUFFIX_ATTRIBUTE_ID));
                    String name = expressionProcessor.evaluate(persistenceClassTemplateArtifactNode.getAttribute(Constants.NAME_ATTRIBUTE_ID));
                    StringBuilder packageNameBuffer = new StringBuilder();
                    
                    if(packagePrefix != null && !packagePrefix.isEmpty())
                        packageNameBuffer.append(packagePrefix);
                    
                    if(packageName != null && !packageName.isEmpty()){
                        if(packageNameBuffer.length() > 0)
                            packageNameBuffer.append(".");
                        
                        packageNameBuffer.append(packageName);
                    }
                    
                    if(packageSuffix != null && !packageSuffix.isEmpty()){
                        if(packageNameBuffer.length() > 0)
                            packageNameBuffer.append(".");
                        
                        packageNameBuffer.append(packageSuffix);
                    }
                    
                    packageName = packageNameBuffer.toString();
                    
                    StringBuilder persistenceClassName = new StringBuilder();
                    
                    if(!packageName.isEmpty()){
                        persistenceClassName.append(packageName);
                        persistenceClassName.append(".");
                    }
                    
                    persistenceClassName.append(name);
                    
                    StringBuilder persistenceClassFilename = new StringBuilder();
                    
                    if(outputDir != null && !outputDir.isEmpty()){
                        persistenceClassFilename.append(outputDir);
                        persistenceClassFilename.append(FileUtil.getDirectorySeparator());
                    }
                    else{
                        persistenceClassFilename.append(this.build.getBaseDirname());
                        persistenceClassFilename.append(ProjectConstants.DEFAULT_JAVA_DIR);
                    }
                    
                    persistenceClassFilename.append(StringUtil.replaceAll(persistenceClassName.toString(), ".", FileUtil.getDirectorySeparator()));
                    persistenceClassFilename.append(ProjectConstants.DEFAULT_JAVA_FILE_EXTENSION);
                    
                    File persistenceClassFile = new File(persistenceClassFilename.toString());
                    
                    if(!persistenceClassFile.exists()){
                        if(this.modelInfo.generatePersistence()){
                            ExpressionProcessorUtil.setVariable(Constants.PACKAGE_PREFIX_ATTRIBUTE_ID, packagePrefix);
                            ExpressionProcessorUtil.setVariable(Constants.PACKAGE_SUFFIX_ATTRIBUTE_ID, packageSuffix);
                            ExpressionProcessorUtil.setVariable(Constants.PACKAGE_NAME_ATTRIBUTE_ID, packageName);
                            ExpressionProcessorUtil.setVariable(Constants.NAME_ATTRIBUTE_ID, name);
                            
                            GenericProcessor processor = processorFactory.getProcessor(this.modelInfo, persistenceClassTemplateArtifactNode);
                            String persistenceClassContent = StringUtil.indent(processor.process(), JavaIndent.getRules());
                            
                            if(!persistenceClassContent.isEmpty())
                                FileUtil.toTextFile(persistenceClassFilename.toString(), persistenceClassContent, encoding);
                        }
                    }
                    else{
                        if(!this.modelInfo.generatePersistence())
                            persistenceClassFile.delete();
                    }
                }
            }
        }
        catch(IOException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e){
            throw new InternalErrorException(e);
        }
    }
    
    /**
     * Generates the persistence interface.
     *
     * @param persistenceInterfaceTemplateFilename String that contains the persistence interface template filename.
     * @throws InternalErrorException Occurs when was not possible to generate
     * the interface.
     */
    @Tag(ProjectConstants.DEFAULT_PERSISTENCE_INTERFACE_TEMPLATE_FILE_ID)
    public void generatePersistenceInterface(String persistenceInterfaceTemplateFilename) throws InternalErrorException{
        try{
            File persistenceInterfaceTemplateFile = new File(persistenceInterfaceTemplateFilename);
            XmlReader persistenceInterfaceTemplateReader = new XmlReader(persistenceInterfaceTemplateFile);
            String encoding = persistenceInterfaceTemplateReader.getEncoding();
            XmlNode persistenceInterfaceTemplateNode = persistenceInterfaceTemplateReader.getRoot();
            List<XmlNode> persistenceInterfaceTemplateArtifactsNode = persistenceInterfaceTemplateNode.getChildren();
            
            if(persistenceInterfaceTemplateArtifactsNode != null && !persistenceInterfaceTemplateArtifactsNode.isEmpty()){
                ProcessorFactory processorFactory = ProcessorFactory.getInstance();
                ExpressionProcessor expressionProcessor = new ExpressionProcessor(this.modelInfo);
                
                for(XmlNode persistenceInterfaceTemplateArtifactNode: persistenceInterfaceTemplateArtifactsNode){
                    String outputDir = expressionProcessor.evaluate(persistenceInterfaceTemplateArtifactNode.getAttribute(Constants.OUTPUT_DIRECTORY_ATTRIBUTE_ID));
                    String packagePrefix = expressionProcessor.evaluate(persistenceInterfaceTemplateArtifactNode.getAttribute(Constants.PACKAGE_PREFIX_ATTRIBUTE_ID));
                    String packageName = expressionProcessor.evaluate(persistenceInterfaceTemplateArtifactNode.getAttribute(Constants.PACKAGE_NAME_ATTRIBUTE_ID));
                    String packageSuffix = expressionProcessor.evaluate(persistenceInterfaceTemplateArtifactNode.getAttribute(Constants.PACKAGE_SUFFIX_ATTRIBUTE_ID));
                    String name = expressionProcessor.evaluate(persistenceInterfaceTemplateArtifactNode.getAttribute(Constants.NAME_ATTRIBUTE_ID));
                    StringBuilder packageNameBuffer = new StringBuilder();
                    
                    if(packagePrefix != null && !packagePrefix.isEmpty())
                        packageNameBuffer.append(packagePrefix);
                    
                    if(packageName != null && !packageName.isEmpty()){
                        if(packageNameBuffer.length() > 0)
                            packageNameBuffer.append(".");
                        
                        packageNameBuffer.append(packageName);
                    }
                    
                    if(packageSuffix != null && !packageSuffix.isEmpty()){
                        if(packageNameBuffer.length() > 0)
                            packageNameBuffer.append(".");
                        
                        packageNameBuffer.append(packageSuffix);
                    }
                    
                    packageName = packageNameBuffer.toString();
                    
                    StringBuilder persistenceInterfaceName = new StringBuilder();
                    
                    if(!packageName.isEmpty()){
                        persistenceInterfaceName.append(packageName);
                        persistenceInterfaceName.append(".");
                    }
                    
                    persistenceInterfaceName.append(name);
                    
                    StringBuilder persistenceInterfaceFilename = new StringBuilder();
                    
                    if(outputDir != null && !outputDir.isEmpty()){
                        persistenceInterfaceFilename.append(outputDir);
                        persistenceInterfaceFilename.append(FileUtil.getDirectorySeparator());
                    }
                    else{
                        persistenceInterfaceFilename.append(this.build.getBaseDirname());
                        persistenceInterfaceFilename.append(ProjectConstants.DEFAULT_JAVA_DIR);
                    }
                    
                    persistenceInterfaceFilename.append(StringUtil.replaceAll(persistenceInterfaceName.toString(), ".", FileUtil.getDirectorySeparator()));
                    persistenceInterfaceFilename.append(ProjectConstants.DEFAULT_JAVA_FILE_EXTENSION);
                    
                    File persistenceInterfaceFile = new File(persistenceInterfaceFilename.toString());
                    
                    if(!persistenceInterfaceFile.exists()){
                        if(this.modelInfo.generatePersistence()){
                            ExpressionProcessorUtil.setVariable(Constants.PACKAGE_PREFIX_ATTRIBUTE_ID, packagePrefix);
                            ExpressionProcessorUtil.setVariable(Constants.PACKAGE_SUFFIX_ATTRIBUTE_ID, packageSuffix);
                            ExpressionProcessorUtil.setVariable(Constants.PACKAGE_NAME_ATTRIBUTE_ID, packageName);
                            ExpressionProcessorUtil.setVariable(Constants.NAME_ATTRIBUTE_ID, name);
                            
                            GenericProcessor processor = processorFactory.getProcessor(this.modelInfo, persistenceInterfaceTemplateArtifactNode);
                            String persistenceInterfaceContent = StringUtil.indent(processor.process(), JavaIndent.getRules());
                            
                            if(!persistenceInterfaceContent.isEmpty())
                                FileUtil.toTextFile(persistenceInterfaceFilename.toString(), persistenceInterfaceContent, encoding);
                        }
                    }
                    else{
                        if(!this.modelInfo.generatePersistence())
                            persistenceInterfaceFile.delete();
                    }
                }
            }
        }
        catch(IOException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e){
            throw new InternalErrorException(e);
        }
    }
    
    /**
     * Generates the persistence mapping.
     *
     * @param persistenceMappingTemplateFilename String that contains the persistence mapping template filename.
     * @throws InternalErrorException Occurs when was not possible to generate
     * the mapping.
     */
    @Tag(ProjectConstants.DEFAULT_PERSISTENCE_MAPPING_TEMPLATE_FILE_ID)
    public void generatePersistenceMapping(String persistenceMappingTemplateFilename) throws InternalErrorException{
        try{
            File persistenceMappingTemplateFile = new File(persistenceMappingTemplateFilename);
            XmlReader persistenceMappingTemplateReader = new XmlReader(persistenceMappingTemplateFile);
            XmlNode persistenceMappingTemplateNode = persistenceMappingTemplateReader.getRoot();
            List<XmlNode> persistenceMappingTemplateArtifactsNode = persistenceMappingTemplateNode.getChildren();
            
            if(persistenceMappingTemplateArtifactsNode != null && !persistenceMappingTemplateArtifactsNode.isEmpty()){
                ProcessorFactory processorFactory = ProcessorFactory.getInstance();
                ExpressionProcessor expressionProcessor = new ExpressionProcessor(this.modelInfo);
                
                for(XmlNode persistenceMappingTemplateArtifactNode: persistenceMappingTemplateArtifactsNode){
                    String outputDir = expressionProcessor.evaluate(persistenceMappingTemplateArtifactNode.getAttribute(Constants.OUTPUT_DIRECTORY_ATTRIBUTE_ID));
                    String packagePrefix = expressionProcessor.evaluate(persistenceMappingTemplateArtifactNode.getAttribute(Constants.PACKAGE_PREFIX_ATTRIBUTE_ID));
                    String packageName = expressionProcessor.evaluate(persistenceMappingTemplateArtifactNode.getAttribute(Constants.PACKAGE_NAME_ATTRIBUTE_ID));
                    String packageSuffix = expressionProcessor.evaluate(persistenceMappingTemplateArtifactNode.getAttribute(Constants.PACKAGE_SUFFIX_ATTRIBUTE_ID));
                    String name = expressionProcessor.evaluate(persistenceMappingTemplateArtifactNode.getAttribute(Constants.NAME_ATTRIBUTE_ID));
                    StringBuilder packageNameBuffer = new StringBuilder();
                    
                    if(packagePrefix != null && !packagePrefix.isEmpty())
                        packageNameBuffer.append(packagePrefix);
                    
                    if(packageName != null && !packageName.isEmpty()){
                        if(packageNameBuffer.length() > 0)
                            packageNameBuffer.append(".");
                        
                        packageNameBuffer.append(packageName);
                    }
                    
                    if(packageSuffix != null && !packageSuffix.isEmpty()){
                        if(packageNameBuffer.length() > 0)
                            packageNameBuffer.append(".");
                        
                        packageNameBuffer.append(packageSuffix);
                    }
                    
                    packageName = packageNameBuffer.toString();
                    
                    StringBuilder persistenceMappingName = new StringBuilder();
                    
                    if(!packageName.isEmpty()){
                        persistenceMappingName.append(packageName);
                        persistenceMappingName.append(".");
                    }
                    
                    persistenceMappingName.append(name);
                    
                    StringBuilder persistenceMappingFilename = new StringBuilder();
                    
                    if(outputDir != null && !outputDir.isEmpty()){
                        persistenceMappingFilename.append(outputDir);
                        persistenceMappingFilename.append(FileUtil.getDirectorySeparator());
                    }
                    else{
                        persistenceMappingFilename.append(this.build.getResourcesDirname());
                        persistenceMappingFilename.append(PersistenceConstants.DEFAULT_MAPPINGS_DIR);
                    }
                    
                    persistenceMappingFilename.append(persistenceMappingName);
                    persistenceMappingFilename.append(PersistenceConstants.DEFAULT_MAPPING_FILE_EXTENSION);
                    
                    File persistenceMappingFile = new File(persistenceMappingFilename.toString());
                    
                    if(persistenceMappingFile.exists())
                        persistenceMappingFile.delete();
                    
                    if(this.modelInfo.generatePersistence() && this.modelInfo.getMappedRepositoryId() != null && !this.modelInfo.getMappedRepositoryId().isEmpty()){
                        ExpressionProcessorUtil.setVariable(Constants.PACKAGE_PREFIX_ATTRIBUTE_ID, packagePrefix);
                        ExpressionProcessorUtil.setVariable(Constants.PACKAGE_SUFFIX_ATTRIBUTE_ID, packageSuffix);
                        ExpressionProcessorUtil.setVariable(Constants.PACKAGE_NAME_ATTRIBUTE_ID, packageName);
                        ExpressionProcessorUtil.setVariable(Constants.NAME_ATTRIBUTE_ID, name);
                        
                        GenericProcessor processor = processorFactory.getProcessor(this.modelInfo, persistenceMappingTemplateArtifactNode);
                        String persistenceMappingContent = processor.process();
                        
                        if(persistenceMappingContent != null && !persistenceMappingContent.isEmpty()){
                            DocumentType persistenceMappingDocumentType = new DefaultDocumentType();
                            String persistenceMappingEncoding = persistenceMappingTemplateReader.getEncoding();
                            
                            persistenceMappingDocumentType.setName(ProjectConstants.PERSISTENCE_MAPPING_ATTRIBUTE_ID);
                            persistenceMappingDocumentType.setPublicID(ProjectConstants.DEFAULT_PERSISTENCE_MAPPING_DTD_PUBLIC_ID);
                            persistenceMappingDocumentType.setSystemID(ProjectConstants.DEFAULT_PERSISTENCE_MAPPING_DTD_SYSTEM_ID);
                            
                            XmlWriter persistenceMappingTemplateWriter = new XmlWriter(persistenceMappingFile, persistenceMappingDocumentType, persistenceMappingEncoding);
                            
                            persistenceMappingTemplateWriter.write(persistenceMappingContent);
                        }
                    }
                }
            }
        }
        catch(IOException | DocumentException e){
            throw new InternalErrorException(e);
        }
    }
    
    /**
     * Updates the persistence resources.
     *
     * @throws InternalErrorException Occurs when was not possible to execute
     * the operation.
     */
    private void updatePersistenceResources() throws InternalErrorException{
        try{
            StringBuilder persistenceResourcesFilename = new StringBuilder();

            persistenceResourcesFilename.append(this.build.getResourcesDirname());
            persistenceResourcesFilename.append(FileUtil.getDirectorySeparator());
            persistenceResourcesFilename.append(PersistenceConstants.DEFAULT_RESOURCES_ID);
            
            File persistenceResourcesFile = new File(persistenceResourcesFilename.toString());
            XmlReader persistenceResourcesReader = new XmlReader(persistenceResourcesFile);
            XmlNode persistenceResourcesNode = persistenceResourcesReader.getRoot();
            XmlNode persistenceResourcesMappingsNode = persistenceResourcesNode.getNode(PersistenceConstants.MAPPINGS_ATTRIBUTE_ID);
            
            if(persistenceResourcesMappingsNode != null)
                persistenceResourcesNode.removeChild(persistenceResourcesMappingsNode);
            
            persistenceResourcesMappingsNode = new XmlNode(PersistenceConstants.MAPPINGS_ATTRIBUTE_ID);
            
            StringBuilder persistenceMappingDirName = new StringBuilder();
            
            persistenceMappingDirName.append(this.build.getResourcesDirname());
            persistenceMappingDirName.append(FileUtil.getDirectorySeparator());
            persistenceMappingDirName.append(PersistenceConstants.DEFAULT_MAPPINGS_DIR);
            
            File persistenceMappingDir = new File(persistenceMappingDirName.toString());
            
            if(persistenceMappingDir.exists()){
                File[] persistenceMappingFiles = persistenceMappingDir.listFiles();
                
                if(persistenceMappingFiles != null){
                    for(File persistenceMappingFile: persistenceMappingFiles){
                        String modelClassName = StringUtil.replaceAll(persistenceMappingFile.getName(), PersistenceConstants.DEFAULT_MAPPING_FILE_EXTENSION, "");
                        XmlNode persistenceResourcesMappingNode = new XmlNode(PersistenceConstants.MAPPING_ATTRIBUTE_ID, modelClassName);
                        
                        persistenceResourcesMappingsNode.addChild(persistenceResourcesMappingNode);
                    }
                }
            }
            
            persistenceResourcesNode.addChild(persistenceResourcesMappingsNode);
            
            XmlWriter persistenceResourcesWriter = new XmlWriter(persistenceResourcesFile, persistenceResourcesReader.getDocumentType(), persistenceResourcesReader.getEncoding());
            
            persistenceResourcesWriter.write(persistenceResourcesNode);
        }
        catch(DocumentException | IOException e){
            throw new InternalErrorException(e);
        }
    }
    
    /**
     * Generates the service class.
     *
     * @param serviceClassTemplateFilename String that contains the service class template filename.
     * @throws InternalErrorException Occurs when was not possible to generate
     * the class.
     */
    @SuppressWarnings("unchecked")
    @Tag(ProjectConstants.DEFAULT_SERVICE_CLASS_TEMPLATE_FILE_ID)
    public void generateServiceClass(String serviceClassTemplateFilename) throws InternalErrorException{
        try{
            File serviceClassTemplateFile = new File(serviceClassTemplateFilename);
            XmlReader serviceClassTemplateReader = new XmlReader(serviceClassTemplateFile);
            String encoding = serviceClassTemplateReader.getEncoding();
            XmlNode serviceClassTemplateNode = serviceClassTemplateReader.getRoot();
            List<XmlNode> serviceClassTemplateArtifactsNode = serviceClassTemplateNode.getChildren();
            
            if(serviceClassTemplateArtifactsNode != null && !serviceClassTemplateArtifactsNode.isEmpty()){
                ProcessorFactory processorFactory = ProcessorFactory.getInstance();
                ExpressionProcessor expressionProcessor = new ExpressionProcessor(this.modelInfo);
                
                for(XmlNode serviceClassTemplateArtifactNode: serviceClassTemplateArtifactsNode){
                    String outputDir = expressionProcessor.evaluate(serviceClassTemplateArtifactNode.getAttribute(Constants.OUTPUT_DIRECTORY_ATTRIBUTE_ID));
                    String packagePrefix = expressionProcessor.evaluate(serviceClassTemplateArtifactNode.getAttribute(Constants.PACKAGE_PREFIX_ATTRIBUTE_ID));
                    String packageName = expressionProcessor.evaluate(serviceClassTemplateArtifactNode.getAttribute(Constants.PACKAGE_NAME_ATTRIBUTE_ID));
                    String packageSuffix = expressionProcessor.evaluate(serviceClassTemplateArtifactNode.getAttribute(Constants.PACKAGE_SUFFIX_ATTRIBUTE_ID));
                    String name = expressionProcessor.evaluate(serviceClassTemplateArtifactNode.getAttribute(Constants.NAME_ATTRIBUTE_ID));
                    StringBuilder packageNameBuffer = new StringBuilder();
                    
                    if(packagePrefix != null && !packagePrefix.isEmpty())
                        packageNameBuffer.append(packagePrefix);
                    
                    if(packageName != null && !packageName.isEmpty()){
                        if(packageNameBuffer.length() > 0)
                            packageNameBuffer.append(".");
                        
                        packageNameBuffer.append(packageName);
                    }
                    
                    if(packageSuffix != null && !packageSuffix.isEmpty()){
                        if(packageNameBuffer.length() > 0)
                            packageNameBuffer.append(".");
                        
                        packageNameBuffer.append(packageSuffix);
                    }
                    
                    packageName = packageNameBuffer.toString();
                    
                    StringBuilder serviceClassName = new StringBuilder();
                    
                    if(!packageName.isEmpty()){
                        serviceClassName.append(packageName);
                        serviceClassName.append(".");
                    }
                    
                    serviceClassName.append(name);
                    
                    StringBuilder serviceClassFilename = new StringBuilder();
                    
                    if(outputDir != null && !outputDir.isEmpty()){
                        serviceClassFilename.append(outputDir);
                        serviceClassFilename.append(FileUtil.getDirectorySeparator());
                    }
                    else{
                        serviceClassFilename.append(this.build.getBaseDirname());
                        serviceClassFilename.append(ProjectConstants.DEFAULT_JAVA_DIR);
                    }
                    
                    serviceClassFilename.append(StringUtil.replaceAll(serviceClassName.toString(), ".", FileUtil.getDirectorySeparator()));
                    serviceClassFilename.append(ProjectConstants.DEFAULT_JAVA_FILE_EXTENSION);
                    
                    File serviceClassFile = new File(serviceClassFilename.toString());
                    
                    if(!serviceClassFile.exists()){
                        if(this.modelInfo.generateService()){
                            ExpressionProcessorUtil.setVariable(Constants.PACKAGE_PREFIX_ATTRIBUTE_ID, packagePrefix);
                            ExpressionProcessorUtil.setVariable(Constants.PACKAGE_SUFFIX_ATTRIBUTE_ID, packageSuffix);
                            ExpressionProcessorUtil.setVariable(Constants.PACKAGE_NAME_ATTRIBUTE_ID, packageName);
                            ExpressionProcessorUtil.setVariable(Constants.NAME_ATTRIBUTE_ID, name);
                            
                            GenericProcessor processor = processorFactory.getProcessor(this.modelInfo, serviceClassTemplateArtifactNode);
                            String serviceClassContent = StringUtil.indent(processor.process(), JavaIndent.getRules());
                            
                            if(!serviceClassContent.isEmpty())
                                FileUtil.toTextFile(serviceClassFilename.toString(), serviceClassContent, encoding);
                            
                            addServiceMapping(serviceClassName.toString());
                        }
                    }
                    else{
                        if(!this.modelInfo.generateService()){
                            try{
                                Class<? extends IService<? extends BaseModel>> serviceClass = (Class<? extends IService<? extends BaseModel>>) Class.forName(serviceClassName.toString());
                                
                                if(!Modifier.isAbstract(serviceClass.getModifiers())){
                                    serviceClassFile.delete();
                                    
                                    removeServiceMapping(serviceClassName.toString());
                                }
                            }
                            catch(ClassNotFoundException e){
                                serviceClassFile.delete();
                                
                                removeServiceMapping(serviceClassName.toString());
                            }
                        }
                        else
                            updateServiceMapping(serviceClassName.toString());
                    }
                }
            }
        }
        catch(IOException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e){
            throw new InternalErrorException(e);
        }
    }
    
    /**
     * Generates the service interface.
     *
     * @param serviceInterfaceTemplateFilename String that contains the service interface template filename.
     * @throws InternalErrorException Occurs when was not possible to generate
     * the interface.
     */
    @Tag(ProjectConstants.DEFAULT_SERVICE_INTERFACE_TEMPLATE_FILE_ID)
    public void generateServiceInterface(String serviceInterfaceTemplateFilename) throws InternalErrorException{
        try{
            File serviceInterfaceTemplateFile = new File(serviceInterfaceTemplateFilename);
            XmlReader serviceInterfaceTemplateReader = new XmlReader(serviceInterfaceTemplateFile);
            String encoding = serviceInterfaceTemplateReader.getEncoding();
            XmlNode serviceInterfaceTemplateNode = serviceInterfaceTemplateReader.getRoot();
            List<XmlNode> serviceInterfaceTemplateArtifactsNode = serviceInterfaceTemplateNode.getChildren();
            
            if(serviceInterfaceTemplateArtifactsNode != null && !serviceInterfaceTemplateArtifactsNode.isEmpty()){
                ProcessorFactory processorFactory = ProcessorFactory.getInstance();
                ExpressionProcessor expressionProcessor = new ExpressionProcessor(this.modelInfo);
                
                for(XmlNode serviceInterfaceTemplateArtifactNode: serviceInterfaceTemplateArtifactsNode){
                    String outputDir = expressionProcessor.evaluate(serviceInterfaceTemplateArtifactNode.getAttribute(Constants.OUTPUT_DIRECTORY_ATTRIBUTE_ID));
                    String packagePrefix = expressionProcessor.evaluate(serviceInterfaceTemplateArtifactNode.getAttribute(Constants.PACKAGE_PREFIX_ATTRIBUTE_ID));
                    String packageName = expressionProcessor.evaluate(serviceInterfaceTemplateArtifactNode.getAttribute(Constants.PACKAGE_NAME_ATTRIBUTE_ID));
                    String packageSuffix = expressionProcessor.evaluate(serviceInterfaceTemplateArtifactNode.getAttribute(Constants.PACKAGE_SUFFIX_ATTRIBUTE_ID));
                    String name = expressionProcessor.evaluate(serviceInterfaceTemplateArtifactNode.getAttribute(Constants.NAME_ATTRIBUTE_ID));
                    StringBuilder packageNameBuffer = new StringBuilder();
                    
                    if(packagePrefix != null && !packagePrefix.isEmpty())
                        packageNameBuffer.append(packagePrefix);
                    
                    if(packageName != null && !packageName.isEmpty()){
                        if(packageNameBuffer.length() > 0)
                            packageNameBuffer.append(".");
                        
                        packageNameBuffer.append(packageName);
                    }
                    
                    if(packageSuffix != null && !packageSuffix.isEmpty()){
                        if(packageNameBuffer.length() > 0)
                            packageNameBuffer.append(".");
                        
                        packageNameBuffer.append(packageSuffix);
                    }
                    
                    packageName = packageNameBuffer.toString();
                    
                    StringBuilder serviceInterfaceName = new StringBuilder();
                    
                    if(!packageName.isEmpty()){
                        serviceInterfaceName.append(packageName);
                        serviceInterfaceName.append(".");
                    }
                    
                    serviceInterfaceName.append(name);
                    
                    StringBuilder serviceInterfaceFilename = new StringBuilder();
                    
                    if(outputDir != null && !outputDir.isEmpty()){
                        serviceInterfaceFilename.append(outputDir);
                        serviceInterfaceFilename.append(FileUtil.getDirectorySeparator());
                    }
                    else{
                        serviceInterfaceFilename.append(this.build.getBaseDirname());
                        serviceInterfaceFilename.append(ProjectConstants.DEFAULT_JAVA_DIR);
                    }
                    
                    serviceInterfaceFilename.append(StringUtil.replaceAll(serviceInterfaceName.toString(), ".", FileUtil.getDirectorySeparator()));
                    serviceInterfaceFilename.append(ProjectConstants.DEFAULT_JAVA_FILE_EXTENSION);
                    
                    File serviceInterfaceFile = new File(serviceInterfaceFilename.toString());
                    
                    if(!serviceInterfaceFile.exists()){
                        if( this.modelInfo.generateService()){
                            ExpressionProcessorUtil.setVariable(Constants.PACKAGE_PREFIX_ATTRIBUTE_ID, packagePrefix);
                            ExpressionProcessorUtil.setVariable(Constants.PACKAGE_SUFFIX_ATTRIBUTE_ID, packageSuffix);
                            ExpressionProcessorUtil.setVariable(Constants.PACKAGE_NAME_ATTRIBUTE_ID, packageName);
                            ExpressionProcessorUtil.setVariable(Constants.NAME_ATTRIBUTE_ID, name);
                            
                            GenericProcessor processor = processorFactory.getProcessor(this.modelInfo, serviceInterfaceTemplateArtifactNode);
                            String serviceInterfaceContent = StringUtil.indent(processor.process(), JavaIndent.getRules());
                            
                            if(!serviceInterfaceContent.isEmpty())
                                FileUtil.toTextFile(serviceInterfaceFilename.toString(), serviceInterfaceContent, encoding);
                        }
                    }
                    else{
                        if(!this.modelInfo.generateService()){
                            try{
                                Class<? extends IService<? extends BaseModel>> serviceClass = ServiceUtil.getServiceClassByModel(this.modelInfo.getClazz());
                                
                                if(!Modifier.isAbstract(serviceClass.getModifiers()))
                                    serviceInterfaceFile.delete();
                            }
                            catch(ClassNotFoundException e){
                                serviceInterfaceFile.delete();
                            }
                        }
                    }
                }
            }
        }
        catch(IOException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e){
            throw new InternalErrorException(e);
        }
    }

    /**
     * Generates the persistence data.
     *
     * @param persistenceDataTemplateFilename String that contains the persistence data template filename.
     * @throws InternalErrorException Occurs when was not possible to generate
     * the UI page.
     */
    @Tag(ProjectConstants.DEFAULT_PERSISTENCE_DATA_TEMPLATE_FILE_ID)
    public void generatePersistenceData(String persistenceDataTemplateFilename) throws InternalErrorException{
        try{
            File persistenceDataTemplateFile = new File(persistenceDataTemplateFilename);
            XmlReader persistenceDataTemplateReader = new XmlReader(persistenceDataTemplateFile);
            String encoding = persistenceDataTemplateReader.getEncoding();
            XmlNode persistenceDataTemplateNode = persistenceDataTemplateReader.getRoot();
            List<XmlNode> persistenceDataTemplateArtifactsNode = persistenceDataTemplateNode.getChildren();
            
            if(persistenceDataTemplateArtifactsNode != null && !persistenceDataTemplateArtifactsNode.isEmpty()){
                PersistenceResourcesLoader persistenceResourcesLoader = new PersistenceResourcesLoader(this.build.getResourcesDirname());
                PersistenceResources persistenceResources = persistenceResourcesLoader.getDefault();
                
                if(persistenceResources != null){
                    FactoryResources persistenceFactoryResources = persistenceResources.getFactoryResources();
                    
                    if(persistenceFactoryResources != null){
                        String repositoryTypeBuffer = persistenceFactoryResources.getType();
                        
                        if(repositoryTypeBuffer != null && !repositoryTypeBuffer.isEmpty()){
                            RepositoryType repositoryType = RepositoryType.valueOf(repositoryTypeBuffer.toUpperCase());
                            String openQuote = repositoryType.getOpenQuote();
                            String closeQuote = repositoryType.getCloseQuote();
                            
                            ExpressionProcessorUtil.setVariable(PersistenceConstants.OPEN_QUOTE_ATTRIBUTE_ID, openQuote);
                            ExpressionProcessorUtil.setVariable(PersistenceConstants.CLOSE_QUOTE_ATTRIBUTE_ID, closeQuote);
                        }
                        
                        ProcessorFactory processorFactory = ProcessorFactory.getInstance();
                        ExpressionProcessor expressionProcessor = new ExpressionProcessor(this.modelInfo);
                        
                        ExpressionProcessorUtil.setVariable(PersistenceConstants.RESOURCES_ATTRIBUTE_ID, persistenceResources);
                        
                        for(XmlNode persistenceDataTemplateArtifactNode: persistenceDataTemplateArtifactsNode){
                            String outputDir = expressionProcessor.evaluate(persistenceDataTemplateArtifactNode.getAttribute(Constants.OUTPUT_DIRECTORY_ATTRIBUTE_ID));
                            String packagePrefix = expressionProcessor.evaluate(persistenceDataTemplateArtifactNode.getAttribute(Constants.PACKAGE_PREFIX_ATTRIBUTE_ID));
                            String packageName = expressionProcessor.evaluate(persistenceDataTemplateArtifactNode.getAttribute(Constants.PACKAGE_NAME_ATTRIBUTE_ID));
                            String packageSuffix = expressionProcessor.evaluate(persistenceDataTemplateArtifactNode.getAttribute(Constants.PACKAGE_SUFFIX_ATTRIBUTE_ID));
                            String name = expressionProcessor.evaluate(persistenceDataTemplateArtifactNode.getAttribute(Constants.NAME_ATTRIBUTE_ID));
                            StringBuilder packageNameBuffer = new StringBuilder();
                            
                            if(packagePrefix != null && !packagePrefix.isEmpty())
                                packageNameBuffer.append(packagePrefix);
                            
                            if(packageName != null && !packageName.isEmpty()){
                                if(packageNameBuffer.length() > 0)
                                    packageNameBuffer.append(".");
                                
                                packageNameBuffer.append(packageName);
                            }
                            
                            if(packageSuffix != null && !packageSuffix.isEmpty()){
                                if(packageNameBuffer.length() > 0)
                                    packageNameBuffer.append(".");
                                
                                packageNameBuffer.append(packageSuffix);
                            }
                            
                            packageName = packageNameBuffer.toString();
                            
                            StringBuilder persistenceDataName = new StringBuilder();
                            
                            if(!packageName.isEmpty()){
                                persistenceDataName.append(packageName);
                                persistenceDataName.append(".");
                            }
                            
                            persistenceDataName.append(name);
                            
                            StringBuilder persistenceDataFilename = new StringBuilder();
                            
                            if(outputDir != null && !outputDir.isEmpty()){
                                persistenceDataFilename.append(outputDir);
                                persistenceDataFilename.append(FileUtil.getDirectorySeparator());
                            }
                            else{
                                persistenceDataFilename.append(this.build.getBaseDirname());
                                persistenceDataFilename.append(ProjectConstants.DEFAULT_PERSISTENCE_DIR);
                            }
                            
                            persistenceDataFilename.append(StringUtil.replaceAll(persistenceDataName.toString(), ".", FileUtil.getDirectorySeparator()));
                            persistenceDataFilename.append(PersistenceConstants.DEFAULT_FILE_EXTENSION);
                            
                            File persistenceDataFile = new File(persistenceDataFilename.toString());
                            
                            if(!persistenceDataFile.exists()){
                                if(this.modelInfo.generatePersistence() || this.modelInfo.generateService()){
                                    ExpressionProcessorUtil.setVariable(Constants.PACKAGE_PREFIX_ATTRIBUTE_ID, packagePrefix);
                                    ExpressionProcessorUtil.setVariable(Constants.PACKAGE_SUFFIX_ATTRIBUTE_ID, packageSuffix);
                                    ExpressionProcessorUtil.setVariable(Constants.PACKAGE_NAME_ATTRIBUTE_ID, packageName);
                                    ExpressionProcessorUtil.setVariable(Constants.NAME_ATTRIBUTE_ID, name);
                                    
                                    GenericProcessor processor = processorFactory.getProcessor(this.modelInfo, persistenceDataTemplateArtifactNode);
                                    String persistenceDataContent = processor.process();
                                    
                                    if(persistenceDataContent != null && !persistenceDataContent.isEmpty()){
                                        StringBuilder persistenceDataContentBuffer = new StringBuilder();
                                        BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(persistenceDataContent.getBytes())));
                                        String line;

                                        while((line = reader.readLine()) != null){
                                            String indentation = StringUtil.replicate(Constants.DEFAULT_INDENT_CHARACTER, Constants.DEFAULT_INDENT_SIZE);
                                            
                                            if(indentation != null && !indentation.isEmpty())
                                                persistenceDataContentBuffer.append(indentation);
                                            
                                            persistenceDataContentBuffer.append(StringUtil.trim(line));
                                            persistenceDataContentBuffer.append(StringUtil.getLineBreak());
                                        }
                                        
                                        persistenceDataContent = persistenceDataContentBuffer.toString();
                                        
                                        FileUtil.toTextFile(persistenceDataFilename.toString(), persistenceDataContent, encoding);
                                    }
                                }
                            }
                            else{
                                if(!this.modelInfo.generatePersistence() && !this.modelInfo.generateService())
                                    persistenceDataFile.delete();
                            }
                        }
                    }
                }
            }
        }
        catch(IOException e){
            throw new InternalErrorException(e);
        }
    }
    
    /**
     * Generates the UI page.
     *
     * @param uiPageTemplateFilename String that contains the UI page template filename.
     * @throws InternalErrorException Occurs when was not possible to generate
     * the UI page.
     */
    @Tag(ProjectConstants.DEFAULT_UI_PAGE_TEMPLATE_FILE_ID)
    public void generateUiPage(String uiPageTemplateFilename) throws InternalErrorException{
        try{
            File uiPageTemplateFile = new File(uiPageTemplateFilename);
            XmlReader uiPageTemplateReader = new XmlReader(uiPageTemplateFile);
            String encoding = uiPageTemplateReader.getEncoding();
            XmlNode uiPageTemplateNode = uiPageTemplateReader.getRoot();
            List<XmlNode> uiPageTemplateArtifactsNode = uiPageTemplateNode.getChildren();
            
            if(uiPageTemplateArtifactsNode != null && !uiPageTemplateArtifactsNode.isEmpty()){
                ProcessorFactory processorFactory = ProcessorFactory.getInstance();
                ExpressionProcessor expressionProcessor = new ExpressionProcessor(this.modelInfo);
                
                for(XmlNode uiPageTemplateArtifactNode: uiPageTemplateArtifactsNode){
                    String outputDir = expressionProcessor.evaluate(uiPageTemplateArtifactNode.getAttribute(Constants.OUTPUT_DIRECTORY_ATTRIBUTE_ID));
                    String actionFormUrl = expressionProcessor.evaluate(uiPageTemplateArtifactNode.getAttribute(Constants.NAME_ATTRIBUTE_ID));
                    StringBuilder uiPageDirname = new StringBuilder();
                    StringBuilder uiPageImagesDirname = new StringBuilder();
                    StringBuilder uiPageScriptsDirname = new StringBuilder();
                    StringBuilder uiPageStylesDirname = new StringBuilder();
                    StringBuilder uiPageFilename = new StringBuilder();
                    StringBuilder uiPageScriptFilename = new StringBuilder();
                    StringBuilder uiPageStyleFilename = new StringBuilder();

                    if(outputDir != null && !outputDir.isEmpty()){
                        uiPageDirname.append(outputDir);
                        uiPageDirname.append(FileUtil.getDirectorySeparator());
                    }
                    else {
                        uiPageDirname.append(this.build.getBaseDirname());
                        uiPageDirname.append(ProjectConstants.DEFAULT_UI_DIR);
                    }

                    uiPageDirname.append(actionFormUrl);

                    File uiPageDir = new File(uiPageDirname.toString());
                    
                    if(outputDir != null && !outputDir.isEmpty()){
                        uiPageFilename.append(outputDir);
                        uiPageFilename.append(FileUtil.getDirectorySeparator());
                    }
                    else{
                        uiPageFilename.append(this.build.getBaseDirname());
                        uiPageFilename.append(ProjectConstants.DEFAULT_UI_DIR);
                        uiPageFilename.append(ProjectConstants.DEFAULT_UI_PAGES_DIR);
                    }
                    
                    uiPageFilename.append(actionFormUrl.substring(1));
                    uiPageFilename.append(FileUtil.getDirectorySeparator());
                    uiPageFilename.append(ProjectConstants.DEFAULT_UI_PAGE_FILE_ID);

                    File uiPageFile = new File(uiPageFilename.toString());
                    
                    uiPageImagesDirname.append(uiPageDirname);
                    uiPageImagesDirname.append(UIConstants.DEFAULT_IMAGES_RESOURCES_DIR);

                    File uiPageImagesDir = new File(uiPageImagesDirname.toString());
                    
                    uiPageScriptsDirname.append(uiPageDirname);
                    uiPageScriptsDirname.append(UIConstants.DEFAULT_SCRIPTS_RESOURCES_DIR);

                    File uiPageScriptsDir = new File(uiPageScriptsDirname.toString());
                    
                    uiPageStylesDirname.append(uiPageDirname);
                    uiPageStylesDirname.append(UIConstants.DEFAULT_STYLES_RESOURCES_DIR);

                    File uiPageStylesDir = new File(uiPageStylesDirname.toString());
                    
                    uiPageScriptFilename.append(uiPageScriptsDirname);
                    uiPageScriptFilename.append(UIConstants.DEFAULT_PAGE_SCRIPT_RESOURCES_ID);

                    File uiPageScriptFile = new File(uiPageScriptFilename.toString());
                    
                    uiPageStyleFilename.append(uiPageStylesDirname);
                    uiPageStyleFilename.append(UIConstants.DEFAULT_PAGE_STYLE_RESOURCES_ID);

                    File uiPageStyleFile = new File(uiPageStyleFilename.toString());
                    
                    if(!uiPageFile.exists() || !uiPageDir.exists() || !uiPageImagesDir.exists() || !uiPageStylesDir.exists() || !uiPageScriptsDir.exists() || !uiPageScriptFile.exists() || !uiPageStyleFile.exists()){
                        if(this.modelInfo.generateUi()){
                            if(!uiPageDir.exists())
                                uiPageDir.mkdirs();
                            
                            if(!uiPageImagesDir.exists())
                                uiPageImagesDir.mkdirs();
                            
                            if(!uiPageScriptsDir.exists())
                                uiPageScriptsDir.mkdirs();
                            
                            if(!uiPageStylesDir.exists())
                                uiPageStylesDir.mkdirs();
                            
                            if(!uiPageScriptFile.exists())
                                FileUtil.toTextFile(new FileOutputStream(uiPageScriptFile), "");
                            
                            if(!uiPageStyleFile.exists())
                                FileUtil.toTextFile(new FileOutputStream(uiPageStyleFile), "");
                            
                            if(!uiPageFile.exists()){
                                GenericProcessor processor = processorFactory.getProcessor(this.modelInfo, uiPageTemplateArtifactNode);
                                String uiPageContent = StringUtil.indent(processor.process(), JspIndent.getRules());
                                
                                if(!uiPageContent.isEmpty())
                                    FileUtil.toTextFile(uiPageFilename.toString(), uiPageContent, encoding);
                            }
                        }
                        else{
                            if(!this.modelInfo.generateUi()){
                                if(uiPageFile.exists())
                                    uiPageFile.delete();
                                
                                if(uiPageScriptFile.exists())
                                    uiPageScriptFile.delete();
                                
                                if(uiPageStyleFile.exists())
                                    uiPageStyleFile.delete();
                                
                                if(uiPageImagesDir.exists())
                                    uiPageImagesDir.delete();
                                
                                if(uiPageScriptsDir.exists())
                                    uiPageScriptsDir.delete();
                                
                                if(uiPageStylesDir.exists())
                                    uiPageStylesDir.delete();
                                
                                if(uiPageDir.exists())
                                    uiPageDir.delete();
                            }
                        }
                    }
                }
                
                updateSecurityResources();
                updateSystemResources();
            }
        }
        catch(IOException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e){
            throw new InternalErrorException(e);
        }
    }
    
    /**
     * Generates the module mappings.
     *
     * @param moduleMappingsTemplateFilename String that contains the module mappings template filename.
     * @throws InternalErrorException Occurs when was not possible to generate
     * the module mappings file.
     */
    @Tag(ProjectConstants.DEFAULT_MODULE_MAPPING_TEMPLATE_FILE_ID)
    public void generateModuleMappings(String moduleMappingsTemplateFilename) throws InternalErrorException{
        try{
            File moduleMappingTemplateFile = new File(moduleMappingsTemplateFilename);
            XmlReader moduleMappingTemplateReader = new XmlReader(moduleMappingTemplateFile);
            XmlNode moduleMappingTemplateNode = moduleMappingTemplateReader.getRoot();
            List<XmlNode> moduleMappingTemplateArtifactsNode = moduleMappingTemplateNode.getChildren();
            
            if(moduleMappingTemplateArtifactsNode != null && !moduleMappingTemplateArtifactsNode.isEmpty()){
                ProcessorFactory processorFactory = ProcessorFactory.getInstance();
                ExpressionProcessor expressionProcessor = new ExpressionProcessor(this.modelInfo);
                
                for(XmlNode moduleMappingTemplateArtifactNode: moduleMappingTemplateArtifactsNode) {
                    String outputDir = expressionProcessor.evaluate(moduleMappingTemplateArtifactNode.getAttribute(Constants.OUTPUT_DIRECTORY_ATTRIBUTE_ID));
                    String name = expressionProcessor.evaluate(moduleMappingTemplateArtifactNode.getAttribute(Constants.NAME_ATTRIBUTE_ID));
                    StringBuilder moduleMappingFilename = new StringBuilder();

                    if (outputDir != null && !outputDir.isEmpty()) {
                        moduleMappingFilename.append(outputDir);
                        moduleMappingFilename.append(FileUtil.getDirectorySeparator());
                    }
                    else {
                        moduleMappingFilename.append(this.build.getBaseDirname());
                        moduleMappingFilename.append(ProjectConstants.DEFAULT_UI_DIR);
                        moduleMappingFilename.append(ProjectConstants.DEFAULT_MODULE_DESCRIPTORS_DIR);
                        moduleMappingFilename.append("/");
                    }

                    moduleMappingFilename.append(name);
                    moduleMappingFilename.append(XmlConstants.DEFAULT_FILE_EXTENSION);

                    File moduleMappingFile = new File(moduleMappingFilename.toString());

                    if (!moduleMappingFile.exists()){
                        if (this.modelInfo.generateUi()) {
                            GenericProcessor processor = processorFactory.getProcessor(this.modelInfo, moduleMappingTemplateArtifactNode);
                            String moduleMappingContent = processor.process();

                            if (moduleMappingContent != null && !moduleMappingContent.isEmpty()) {
                                String moduleMappingEncoding = moduleMappingTemplateReader.getEncoding();
                                XmlWriter moduleMappingTemplateWriter = new XmlWriter(moduleMappingFile, moduleMappingEncoding);

                                moduleMappingTemplateWriter.write(moduleMappingContent);
                            }
                        }
                    }
                    else{
                        if (!this.modelInfo.generateUi())
                            moduleMappingFile.delete();
                    }
                }
            }
        }
        catch(IOException | DocumentException e){
            throw new InternalErrorException(e);
        }
    }
    
    /**
     * Updates the security resources.
     *
     * @throws InternalErrorException Occurs when was not possible to update the
     * security resources.
     */
    private void updateSecurityResources() throws InternalErrorException{
        if(this.modelInfo == null)
            return;
        
        Class<? extends BaseModel> modelClass = this.modelInfo.getClazz();
        
        if(modelClass != null && (modelClass.getSuperclass().equals(LoginSessionModel.class) || modelClass.getSuperclass().equals(MainConsoleModel.class))){
            try{
                StringBuilder securityResourcesFilename = new StringBuilder();
                
                securityResourcesFilename.append(this.build.getResourcesDirname());
                securityResourcesFilename.append(SecurityConstants.DEFAULT_RESOURCES_ID);
                
                XmlReader reader = new XmlReader(new File(securityResourcesFilename.toString()));
                XmlNode securityResourceContent = reader.getRoot();
                XmlNode loginSessionNode = securityResourceContent.getNode(SecurityConstants.LOGIN_SESSION_ATTRIBUTE_ID);
                
                if(loginSessionNode == null){
                    loginSessionNode = new XmlNode(SecurityConstants.LOGIN_SESSION_ATTRIBUTE_ID);
                    
                    securityResourceContent.addChild(loginSessionNode);
                }
                
                if(modelClass.getSuperclass().equals(LoginSessionModel.class)){
                    XmlNode classNode = loginSessionNode.getNode(Constants.CLASS_ATTRIBUTE_ID);
                    
                    if(classNode == null){
                        classNode = new XmlNode(Constants.CLASS_ATTRIBUTE_ID);
                        
                        loginSessionNode.addChild(classNode);
                    }
                    
                    classNode.setValue(modelClass.getName());
                }
                
                XmlNode timeoutNode = loginSessionNode.getNode(Constants.TIMEOUT_ATTRIBUTE_ID);
                
                if(timeoutNode == null){
                    timeoutNode = new XmlNode(Constants.TIMEOUT_ATTRIBUTE_ID, String.valueOf(SecurityConstants.DEFAULT_LOGIN_SESSION_TIMEOUT));
                    
                    loginSessionNode.addChild(timeoutNode);
                }
                
                XmlNode cryptographyNode = securityResourceContent.getNode(SecurityConstants.CRYPTOGRAPHY_ATTRIBUTE_ID);
                
                if(cryptographyNode == null){
                    cryptographyNode = new XmlNode(SecurityConstants.CRYPTOGRAPHY_ATTRIBUTE_ID);
                    cryptographyNode.addAttribute(SecurityConstants.CRYPTOGRAPHY_ALGORITHM_ATTRIBUTE_ID, SecurityConstants.DEFAULT_CRYPTO_ALGORITHM_ID);
                    cryptographyNode.addAttribute(SecurityConstants.CRYPTOGRAPHY_KEY_SIZE_ATTRIBUTE_ID, String.valueOf(SecurityConstants.DEFAULT_CRYPTO_KEY_SIZE));
                    
                    securityResourceContent.addChild(cryptographyNode);
                }
                
                XmlWriter writer = new XmlWriter(new File(securityResourcesFilename.toString()));
                
                writer.write(securityResourceContent);
            }
            catch(IOException | DocumentException e){
                throw new InternalErrorException(e);
            }
        }
    }
    
    /**
     * Adds a service mapping.
     *
     * @param serviceClassName String that contains the service implementation class name.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    private void addServiceMapping(String serviceClassName) throws InternalErrorException{
        if(this.modelInfo == null)
            return;
        
        try{
            StringBuilder systemResourcesFilename = new StringBuilder();

            systemResourcesFilename.append(this.build.getResourcesDirname());
            systemResourcesFilename.append(SystemConstants.DEFAULT_RESOURCES_ID);
            
            XmlReader reader = new XmlReader(new File(systemResourcesFilename.toString()));
            XmlNode systemResourceContentNode = reader.getRoot();
            XmlNode servicesNode = systemResourceContentNode.getNode(ServiceConstants.SERVICES_ATTRIBUTE_ID);
            
            if(servicesNode == null){
                servicesNode = new XmlNode(ServiceConstants.SERVICES_ATTRIBUTE_ID);
                
                systemResourceContentNode.addChild(servicesNode);
            }
            
            List<XmlNode> servicesNodes = servicesNode.getChildren();
            XmlNode serviceNode = null;

            if(servicesNodes != null && !servicesNodes.isEmpty()){
                for(XmlNode item : servicesNodes){
                    serviceNode = item;

                    if(serviceClassName != null && serviceClassName.equals(serviceNode.getValue()))
                        break;

                    serviceNode = null;
                }
            }
            
            if(serviceNode == null) {
                serviceNode = new XmlNode(ServiceConstants.SERVICE_ATTRIBUTE_ID, serviceClassName);

                servicesNode.addChild(serviceNode);
            }

            XmlWriter writer = new XmlWriter(new File(systemResourcesFilename.toString()));

            writer.write(systemResourceContentNode);
        }
        catch(IOException | DocumentException e){
            throw new InternalErrorException(e);
        }
    }

    @SuppressWarnings("unchecked")
    private void updateServiceMapping(String serviceClassName) throws InternalErrorException{
        if(this.modelInfo == null)
            return;

        try{
            StringBuilder systemResourcesFilename = new StringBuilder();

            systemResourcesFilename.append(this.build.getResourcesDirname());
            systemResourcesFilename.append(SystemConstants.DEFAULT_RESOURCES_ID);

            XmlReader reader = new XmlReader(new File(systemResourcesFilename.toString()));
            XmlNode systemResourceContentNode = reader.getRoot();

            XmlNode servicesNode = systemResourceContentNode.getNode(ServiceConstants.SERVICES_ATTRIBUTE_ID);
            List<XmlNode> servicesNodes = (servicesNode != null ? servicesNode.getChildren() : null);

            if(servicesNodes != null && !servicesNodes.isEmpty()){
                XmlNode serviceNode = null;

                for(XmlNode item : servicesNodes){
                    serviceNode = item;

                    if(serviceClassName != null && serviceClassName.equals(serviceNode.getValue()))
                        break;

                    serviceNode = null;
                }

                if(serviceNode != null){
                    Class<IService<? extends BaseModel>> serviceClass = (Class<IService<? extends BaseModel>>)Class.forName(serviceClassName);
                    Service serviceAnnotation = serviceClass.getAnnotation(Service.class);

                    if(serviceAnnotation != null){
                        if(serviceAnnotation.isJob()) {
                            serviceNode.addAttribute("isJob", String.valueOf(true));

                            if (serviceAnnotation.pollingTime() > 0)
                                serviceNode.addAttribute("isRecurrent", String.valueOf(true));
                        }
                        else {
                            serviceNode.removeAttribute("isJob");
                            serviceNode.removeAttribute("isRecurrent");
                        }

                        if(serviceAnnotation.isWeb()) {
                            serviceNode.addAttribute("isWeb", String.valueOf(true));

                            if (serviceAnnotation.url() != null && !serviceAnnotation.url().isEmpty())
                                serviceNode.addAttribute("url", serviceAnnotation.url());
                        }
                        else {
                            serviceNode.removeAttribute("isWeb");
                            serviceNode.removeAttribute("url");
                        }

                        XmlWriter writer = new XmlWriter(new File(systemResourcesFilename.toString()));

                        writer.write(systemResourceContentNode);
                    }
                }
            }
        }
        catch(IOException | DocumentException | ClassNotFoundException e){
            throw new InternalErrorException(e);
        }
    }
    
    /**
     * Removes a service mapping.
     *
     * @param serviceClassName String that contains the service implementation class name.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    private void removeServiceMapping(String serviceClassName) throws InternalErrorException{
        if(this.modelInfo == null)
            return;
        
        try{
            StringBuilder systemResourcesFilename = new StringBuilder();

            systemResourcesFilename.append(this.build.getResourcesDirname());
            systemResourcesFilename.append(SystemConstants.DEFAULT_RESOURCES_ID);
            
            XmlReader reader = new XmlReader(new File(systemResourcesFilename.toString()));
            XmlNode systemResourceContentNode = reader.getRoot();
            
            XmlNode servicesNode = systemResourceContentNode.getNode(ServiceConstants.SERVICES_ATTRIBUTE_ID);
            List<XmlNode> servicesNodes = (servicesNode != null ? servicesNode.getChildren() : null);

            if(servicesNodes != null && !servicesNodes.isEmpty()){
                boolean found = false;

                for(int cont = 0; cont < servicesNodes.size(); cont++){
                    XmlNode serviceNode = servicesNodes.get(cont);

                    if(serviceClassName != null && serviceClassName.equals(serviceNode.getValue())){
                        found = true;

                        servicesNodes.remove(cont);

                        break;
                    }
                }
                
                if(found){
                    XmlWriter writer = new XmlWriter(new File(systemResourcesFilename.toString()));
                    
                    writer.write(systemResourceContentNode);
                }
            }
        }
        catch(IOException | DocumentException e){
            throw new InternalErrorException(e);
        }
    }
    
    /**
     * Updates the system resources.
     *
     * @throws InternalErrorException Occurs when was not possible to update the
     * system resources.
     */
    private void updateSystemResources() throws InternalErrorException{
        if(this.modelInfo == null)
            return;
        
        try{
            StringBuilder systemResourcesFilename = new StringBuilder();

            systemResourcesFilename.append(this.build.getResourcesDirname());
            systemResourcesFilename.append(SystemConstants.DEFAULT_RESOURCES_ID);
            
            XmlReader reader = new XmlReader(new File(systemResourcesFilename.toString()));
            XmlNode systemResourceContentNode = reader.getRoot();
            
            Class<? extends BaseModel> modelClass = this.modelInfo.getClazz();
            
            if(modelClass != null && (modelClass.getSuperclass().equals(MainConsoleModel.class))){
                XmlNode mainConsoleNode = systemResourceContentNode.getNode(SystemConstants.MAIN_CONSOLE_ATTRIBUTE_ID);
                
                if(mainConsoleNode == null){
                    mainConsoleNode = new XmlNode(SystemConstants.MAIN_CONSOLE_ATTRIBUTE_ID);
                    
                    systemResourceContentNode.addChild(mainConsoleNode);
                }
                
                XmlNode classNode = mainConsoleNode.getNode(Constants.CLASS_ATTRIBUTE_ID);
                
                if(classNode == null){
                    classNode = new XmlNode(Constants.CLASS_ATTRIBUTE_ID);
                    
                    mainConsoleNode.addChild(classNode);
                }
                
                if(this.modelInfo.generateUi())
                    classNode.setValue(modelClass.getName());
            }
            
            XmlWriter writer = new XmlWriter(new File(systemResourcesFilename.toString()));
            
            writer.write(systemResourceContentNode);
        }
        catch(IOException | DocumentException e){
            throw new InternalErrorException(e);
        }
    }
    
    /**
     * Adds a class name to generate the code checking file.
     *
     * @param className String that contains the class name.
     * @throws IOException Occurs when was not possible to execute the
     * operation.
     */
    private void addCheckGeneratedCode(String className) throws IOException{
        if(className == null || className.isEmpty())
            return;
        
        ProjectBuild build = getAnnotationProcessorFactory().getBuild();
        StringBuilder checkGeneratedCodeFilename = new StringBuilder();

        checkGeneratedCodeFilename.append(FileUtil.getTempDirectory());
        checkGeneratedCodeFilename.append(build.getName());
        checkGeneratedCodeFilename.append("-");
        checkGeneratedCodeFilename.append(build.getVersion());
        
        StringBuilder contentBuffer = new StringBuilder();
        
        contentBuffer.append(className);
        contentBuffer.append(FileUtil.getDirectorySeparator());
        
        FileUtil.toTextFile(checkGeneratedCodeFilename.toString(), contentBuffer.toString(), true);
    }
}