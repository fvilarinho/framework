package br.com.concepting.framework.service.util;

import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.constants.ModelConstants;
import br.com.concepting.framework.security.model.LoginSessionModel;
import br.com.concepting.framework.service.constants.ServiceConstants;
import br.com.concepting.framework.service.interfaces.IService;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.webservice.constants.WebServiceConstants;

/**
 * Class responsible to manipulate services implementations.
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
public class ServiceUtil{
    /**
     * Return the service instance by the model class.
     *
     * @param <S> Class that defines the service.
     * @param modelClass Class that defines the model class.
     * @return Instance that contains the service.
     * @throws InternalErrorException Occurs when was not possible to instantiate the service.
     */
    public static <S extends IService<? extends BaseModel>> S getByModelClass(Class<? extends BaseModel> modelClass) throws InternalErrorException{
        if(modelClass != null)
            return getByModelClass(modelClass, null);
        
        return null;
    }
    
    /**
     * Return the service instance by the service class.
     *
     * @param <S> Class that defines the service.
     * @param serviceClass Class that defines the service.
     * @return Instance that contains the service.
     * @throws InternalErrorException Occurs when was not possible to instantiate the service.
     */
    public static <S extends IService<? extends BaseModel>> S getByServiceClass(Class<S> serviceClass) throws InternalErrorException{
        if(serviceClass != null)
            return getByServiceClass(serviceClass, null);
        
        return null;
    }
    
    /**
     * Return the service instance by the model class.
     *
     * @param <S> Class that defines the service.
     * @param modelClass Class that defines the model class.
     * @param loginSession Instance that contains the login session data model.
     * @return Instance that contains the service.
     * @throws InternalErrorException Occurs when was not possible to instantiate the service.
     */
    public static <S extends IService<? extends BaseModel>> S getByModelClass(Class<? extends BaseModel> modelClass, LoginSessionModel loginSession) throws InternalErrorException{
        if(modelClass != null){
            ServiceLocator serviceLocator = ServiceLocator.getInstance();
            
            return serviceLocator.lookupByModelClass(modelClass, loginSession);
        }
        
        return null;
    }
    
    /**
     * Return the service instance by the service class.
     *
     * @param <S> Class that defines the service.
     * @param serviceClass Class that defines the service.
     * @param loginSession Instance that contains the login session data model.
     * @return Instance that contains the service.
     * @throws InternalErrorException Occurs when was not possible to instantiate the service.
     */
    public static <S extends IService<? extends BaseModel>> S getByServiceClass(Class<S> serviceClass, LoginSessionModel loginSession) throws InternalErrorException{
        if(serviceClass != null){
            ServiceLocator serviceLocator = ServiceLocator.getInstance();
            
            return serviceLocator.lookupByServiceClass(serviceClass, loginSession);
        }
        
        return null;
    }

    /**
     * Returns the class of a data model based on the service implementation.
     *
     * @param <M> Class that defines a data model.
     * @param serviceClass Class of the service implementation.
     * @return Class that defines the data model.
     * @throws ClassNotFoundException Occurs when was not possible to execute
     * the operation.
     */
    @SuppressWarnings("unchecked")
    public static <M extends BaseModel> Class<M> getModelClassByService(Class<?> serviceClass) throws ClassNotFoundException{
        if(serviceClass != null){
            String modelClassId = StringUtil.replaceLast(serviceClass.getName(), StringUtil.capitalize(ServiceConstants.DEFAULT_IMPLEMENTATION_ID), StringUtil.capitalize(ModelConstants.DEFAULT_ID));

            modelClassId = StringUtil.replaceLast(modelClassId, StringUtil.capitalize(ServiceConstants.DEFAULT_ID), StringUtil.capitalize(ModelConstants.DEFAULT_ID));
            modelClassId = StringUtil.replaceAll(modelClassId, ".".concat(ServiceConstants.DEFAULT_ID), ".".concat(ModelConstants.DEFAULT_ID));
            
            return (Class<M>) Class.forName(modelClassId);
        }
        
        return null;
    }
    
    /**
     * Returns the class of the service implementation based on a data model.
     *
     * @param <S> Class of the service implementation.
     * @param modelClass Class that defines the data model.
     * @return Class of the service implementation.
     * @throws ClassNotFoundException Occurs when was not possible to execute
     * the operation.
     */
    @SuppressWarnings("unchecked")
    public static <S extends IService<? extends BaseModel>> Class<S> getServiceClassByModel(Class<? extends BaseModel> modelClass) throws ClassNotFoundException{
        if(modelClass != null)
            return (Class<S>) Class.forName(getServiceClassNameByModel(modelClass));
        
        return null;
    }
    
    /**
     * Returns the class name of the service implementation based on a data
     * model.
     *
     * @param modelClass Class that defines the data model.
     * @return String that contains the class name of the service
     * implementation.
     */
    public static String getServiceClassNameByModel(Class<? extends BaseModel> modelClass){
        if(modelClass != null){
            String serviceClassId = StringUtil.replaceLast(modelClass.getName(), StringUtil.capitalize(ModelConstants.DEFAULT_ID), StringUtil.capitalize(ServiceConstants.DEFAULT_IMPLEMENTATION_ID));
            
            serviceClassId = StringUtil.replaceAll(serviceClassId, ".".concat(ModelConstants.DEFAULT_ID), ".".concat(ServiceConstants.DEFAULT_ID));
            
            return serviceClassId;
        }
        
        return null;
    }
    
    /**
     * Returns the identifier of the service implementation based on a data
     * model.
     *
     * @param modelClass Class that defines the data model.
     * @return String that contains the identifier of the service
     * implementation.
     */
    public static String getServiceNameByModel(Class<? extends BaseModel> modelClass){
        if(modelClass != null)
            return StringUtil.replaceLast(modelClass.getSimpleName(), StringUtil.capitalize(ModelConstants.DEFAULT_ID), StringUtil.capitalize(ServiceConstants.DEFAULT_IMPLEMENTATION_ID));
        
        return null;
    }
    
    /**
     * Returns the interface class name of the service implementation based on a
     * data model.
     *
     * @param modelClass Class that defines the data model.
     * @return String that contains the interface class name of the service
     * implementation.
     */
    public static String getServiceInterfaceClassNameByModel(Class<? extends BaseModel> modelClass){
        if(modelClass != null){
            String serviceInterfaceId = StringUtil.replaceLast(modelClass.getName(), StringUtil.capitalize(ModelConstants.DEFAULT_ID), StringUtil.capitalize(ServiceConstants.DEFAULT_ID));
            
            serviceInterfaceId = StringUtil.replaceAll(serviceInterfaceId, ".".concat(ModelConstants.DEFAULT_ID), ".".concat(ServiceConstants.DEFAULT_ID).concat(".").concat(Constants.DEFAULT_INTERFACES_ID));
            
            return serviceInterfaceId;
        }
        
        return null;
    }
    
    /**
     * Returns the interface class name of the web service implementation based
     * on a data model.
     *
     * @param modelClass Class that defines the data model.
     * @return String that contains the interface class name of the WebService
     * implementation.
     */
    public static String getWebServiceInterfaceClassNameByModel(Class<? extends BaseModel> modelClass){
        if(modelClass != null){
            String webServiceInterfaceId = StringUtil.replaceLast(modelClass.getName(), StringUtil.capitalize(ModelConstants.DEFAULT_ID), StringUtil.capitalize(WebServiceConstants.DEFAULT_ID));
            
            webServiceInterfaceId = StringUtil.replaceAll(webServiceInterfaceId, ".".concat(ModelConstants.DEFAULT_ID), ".".concat(ServiceConstants.DEFAULT_ID).concat(".").concat(Constants.DEFAULT_INTERFACES_ID));
            
            return webServiceInterfaceId;
        }
        
        return null;
    }
    
    /**
     * Returns the interface name of the service implementation based on a data
     * model.
     *
     * @param modelClass Class that defines the data model.
     * @return String that contains the interface name of the service
     * implementation.
     */
    public static String getServiceInterfaceNameByModel(Class<? extends BaseModel> modelClass){
        if(modelClass != null)
            return StringUtil.replaceLast(modelClass.getSimpleName(), StringUtil.capitalize(ModelConstants.DEFAULT_ID), StringUtil.capitalize(ServiceConstants.DEFAULT_ID));
        
        return null;
    }
    
    /**
     * Returns the interface name of the web service implementation based on a
     * data model.
     *
     * @param modelClass Class that defines the data model.
     * @return String that contains the interface name of the WebService
     * implementation.
     */
    public static String getWebServiceInterfaceNameByModel(Class<? extends BaseModel> modelClass){
        if(modelClass != null)
            return StringUtil.replaceLast(modelClass.getSimpleName(), StringUtil.capitalize(ModelConstants.DEFAULT_ID), StringUtil.capitalize(WebServiceConstants.DEFAULT_ID));
        
        return null;
    }
    
    /**
     * Returns an interface of the service implementation based on a data model.
     *
     * @param <S> Class that defines the service implementation.
     * @param modelClass Class that defines the data model.
     * @return Interface that defines the service implementation.
     * @throws ClassNotFoundException Occurs when was not possible to execute
     * the operation.
     */
    @SuppressWarnings("unchecked")
    public static <S extends IService<? extends BaseModel>> Class<S> getServiceInterfaceClassByModel(Class<? extends BaseModel> modelClass) throws ClassNotFoundException{
        if(modelClass != null)
            return (Class<S>) Class.forName(getServiceInterfaceClassNameByModel(modelClass));
        
        return null;
    }
    
    /**
     * Returns an interface of the web service implementation based on a data
     * model.
     *
     * @param <S> Class that defines the web service implementation.
     * @param modelClass Class that defines the data model.
     * @return Interface that defines the web service implementation.
     * @throws ClassNotFoundException Occurs when was not possible to execute
     * the operation.
     */
    @SuppressWarnings("unchecked")
    public static <S extends IService<? extends BaseModel>> Class<S> getWebServiceInterfaceClassByModel(Class<? extends BaseModel> modelClass) throws ClassNotFoundException{
        if(modelClass != null)
            return (Class<S>) Class.forName(getWebServiceInterfaceClassNameByModel(modelClass));
        
        return null;
    }
    
    /**
     * Returns the package name of the service implementation.
     *
     * @param modelClass Class that defines the data model.
     * @return String that contains the package name.
     */
    public static String getServicePackageByModel(Class<? extends BaseModel> modelClass){
        if(modelClass != null)
            return StringUtil.replaceAll(modelClass.getPackage().getName(), ".".concat(ModelConstants.DEFAULT_ID), ".".concat(ServiceConstants.DEFAULT_ID));
        
        return null;
    }
    
    /**
     * Returns the package name of the web service implementation.
     *
     * @param modelClass Class that defines the data model.
     * @return String that contains the package name.
     */
    public static String getWebServicePackageByModel(Class<? extends BaseModel> modelClass){
        return getServicePackageByModel(modelClass);
    }
    
    /**
     * Returns the interface package name of the service implementation.
     *
     * @param modelClass Class that defines the data model.
     * @return String that contains the package name.
     */
    public static String getServiceInterfacePackageByModel(Class<? extends BaseModel> modelClass){
        if(modelClass != null)
            return StringUtil.replaceAll(modelClass.getPackage().getName(), ".".concat(ModelConstants.DEFAULT_ID), ".".concat(ServiceConstants.DEFAULT_ID).concat(".").concat(Constants.DEFAULT_INTERFACES_ID));
        
        return null;
    }
    
    /**
     * Returns the interface package name of the web service implementation.
     *
     * @param modelClass Class that defines the data model.
     * @return String that contains the package name.
     */
    public static String getWebServiceInterfacePackageByModel(Class<? extends BaseModel> modelClass){
        return getServiceInterfacePackageByModel(modelClass);
    }
}