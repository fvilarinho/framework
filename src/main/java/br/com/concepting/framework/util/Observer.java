package br.com.concepting.framework.util;

import br.com.concepting.framework.constants.Constants;

import java.io.Serializable;
import java.lang.reflect.*;
import java.util.Collection;

/**
 * Class responsible to observe the execution of a class' methods.
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
public class Observer implements InvocationHandler{
    private final Object interceptableInstance;
    private final Class<?> interceptableInterfaceClass;
    private final Interceptor interceptor;
    
    /**
     * Returns the instance of the interceptable class.
     *
     * @param <I> Class that defines the interceptable class.
     * @param interceptableInstance Instance that contains the interceptable class.
     * @param interceptableInterfaceClass Interface that defines the
     * interceptable class.
     * @param interceptor Instance that defines the interceptor.
     * @return Instance that contains the interceptable class.
     * @throws InstantiationException Occurs when was not possible to execute the
     * operation.
     * @throws InvocationTargetException Occurs when was not possible to execute the
     * operation.
     * @throws IllegalAccessException Occurs when was not possible to execute the
     * operation.
     * @throws NoSuchMethodException Occurs when was not possible to execute the
     * operation.
     */
    @SuppressWarnings("unchecked")
    public static <I> I getInstance(Object interceptableInstance, Class<?> interceptableInterfaceClass, Interceptor interceptor) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException{
        if(interceptableInstance == null || interceptableInterfaceClass == null)
            return (I) interceptableInstance;
        
        Class<?> interceptableClass = interceptableInstance.getClass();
        Collection<Class<?>> interceptableInstanceInterfacesList = getInterceptableInstanceInterfaces(interceptableInstance);
        
        if(interceptableInstanceInterfacesList != null && !interceptableInstanceInterfacesList.isEmpty()){
            Class<?>[] interceptableInstanceInterfaces = new Class[interceptableInstanceInterfacesList.size()];
            
            interceptableInstanceInterfaces = interceptableInstanceInterfacesList.toArray(interceptableInstanceInterfaces);
            
            return (I) Proxy.newProxyInstance(interceptableClass.getClassLoader(), interceptableInstanceInterfaces, new Observer(interceptableInstance, interceptableInterfaceClass, interceptor));
        }
        
        return null;
    }
    
    /**
     * Returns the list of the interceptable interfaces.
     *
     * @param interceptableInstance Instance that contains the interceptable class.
     * @return List of the interceptable interfaces.
     * @throws InstantiationException Occurs when was not possible to execute the
     * operation.
     * @throws InvocationTargetException Occurs when was not possible to execute the
     * operation.
     * @throws IllegalAccessException Occurs when was not possible to execute the
     * operation.
     * @throws NoSuchMethodException Occurs when was not possible to execute the
     * operation.
     */
    private static Collection<Class<?>> getInterceptableInstanceInterfaces(Object interceptableInstance) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException{
        if(interceptableInstance != null){
            Collection<Class<?>> interceptableInterfaces = PropertyUtil.instantiate(Constants.DEFAULT_LIST_CLASS);
            
            loadInterceptableInstanceInterfaces(interceptableInstance.getClass(), interceptableInterfaces);
            
            return interceptableInterfaces;
        }
        
        return null;
    }
    
    /**
     * Loads the interceptable interfaces of the instance.
     *
     * @param interceptableInstance Instance that contains the interceptable class.
     * @param interceptableInterfaces List of the interceptable interfaces.
     */
    private static void loadInterceptableInstanceInterfaces(Class<?> interceptableInstance, Collection<Class<?>> interceptableInterfaces){
        if(interceptableInstance == null)
            return;
        
        Class<?>[] instanceInterfaces = interceptableInstance.getInterfaces();
        boolean found = false;
        
        if(interceptableInterfaces != null && !interceptableInterfaces.isEmpty()){
            for(Class<?> item: interceptableInterfaces){
                if(item.isInterface() && item.getName().equals(interceptableInstance.getName())){
                    found = true;
                    
                    break;
                }
            }
        }
        
        if(!found && interceptableInstance.isInterface())
            if(interceptableInterfaces != null)
                interceptableInterfaces.add(interceptableInstance);

        if(interceptableInterfaces != null){
            for(Class<?> instanceInterface: instanceInterfaces){
                if(!instanceInterface.getName().endsWith(Serializable.class.getSimpleName()) && !instanceInterface.getName().endsWith(Object.class.getSimpleName())){
                    found = false;

                    for(Class<?> item: interceptableInterfaces){
                        if(item.isInterface() && item.getName().equals(instanceInterface.getName())){
                            found = true;

                            break;
                        }
                    }

                    if(!found && instanceInterface.isInterface()){
                        interceptableInterfaces.add(instanceInterface);

                        loadInterceptableInstanceInterfaces(instanceInterface, interceptableInterfaces);
                    }
                }
            }
        }
    }
    
    /**
     * Constructor - Defines the observer parameters.
     *
     * @param interceptableInstance Instance that contains the interceptable class.
     * @param interceptableInterfaceClass Interface that defines the
     * interceptable class.
     * @param interceptor Instance that defines the interceptor.
     */
    private Observer(Object interceptableInstance, Class<?> interceptableInterfaceClass, Interceptor interceptor){
        this.interceptableInstance = interceptableInstance;
        this.interceptableInterfaceClass = interceptableInterfaceClass;
        this.interceptor = interceptor;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] methodArgumentsValues) throws Throwable{
        this.interceptor.setInterceptableInstance(this.interceptableInstance);
        this.interceptor.setInterceptableInterfaceClass(this.interceptableInterfaceClass);
        this.interceptor.setInterceptableMethod(method);
        
        Parameter[] methodArguments = method.getParameters();
        
        if(methodArguments != null && methodArguments.length > 0){
            String[] methodArgumentsNames = new String[methodArguments.length];
            
            for(int cont = 0; cont < methodArguments.length; cont++)
                methodArgumentsNames[cont] = methodArguments[cont].getName();
            
            this.interceptor.setInterceptableMethodArgumentsNames(methodArgumentsNames);
        }
        
        this.interceptor.setInterceptableMethodArgumentsValues(methodArgumentsValues);
        this.interceptor.before();

        Throwable exception = null;

        try{
            return this.interceptor.execute();
        }
        catch(Throwable e){
            exception = e;

            this.interceptor.beforeThrow(e);
            
            throw e;
        }
        finally{
            if(exception == null)
                this.interceptor.after();
        }
    }
}