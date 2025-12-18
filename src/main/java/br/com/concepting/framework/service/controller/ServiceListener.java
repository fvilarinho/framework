package br.com.concepting.framework.service.controller;

import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.SystemModuleModel;
import br.com.concepting.framework.model.SystemSessionModel;
import br.com.concepting.framework.model.exceptions.ItemAlreadyExistsException;
import br.com.concepting.framework.persistence.constants.PersistenceConstants;
import br.com.concepting.framework.persistence.util.HibernateUtil;
import br.com.concepting.framework.resources.SystemResources;
import br.com.concepting.framework.resources.SystemResourcesLoader;
import br.com.concepting.framework.security.model.LoginParameterModel;
import br.com.concepting.framework.security.model.LoginSessionModel;
import br.com.concepting.framework.security.model.UserModel;
import br.com.concepting.framework.security.resources.SecurityResources;
import br.com.concepting.framework.security.resources.SecurityResourcesLoader;
import br.com.concepting.framework.security.service.interfaces.LoginSessionService;
import br.com.concepting.framework.security.util.SecurityUtil;
import br.com.concepting.framework.service.helpers.ServiceThread;
import br.com.concepting.framework.service.interfaces.IService;
import br.com.concepting.framework.service.util.ServiceUtil;
import br.com.concepting.framework.util.FileUtil;
import br.com.concepting.framework.util.PropertyUtil;
import br.com.concepting.framework.util.helpers.DateTime;
import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.File;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Class that defines the service listener.
 *
 * @author fvilarinho
 * @since 3.5.0
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
@WebListener
public class ServiceListener implements ServletContextListener{
    private static ScheduledExecutorService scheduledExecutor = null;
    private static ExecutorService executor = null;
    
    private LoginSessionModel loginSession = null;
    private ServletContextEvent event = null;

    /**
     * Returns the instance of the event.
     *
     * @return Instance that contains the event.
     */
    public ServletContextEvent getEvent(){
        return this.event;
    }
    
    /**
     * Defines the instance of the event.
     *
     * @param event Instance that contains the event.
     */
    public void setEvent(ServletContextEvent event){
        this.event = event;
    }
    
    /**
     * Returns the service implementation of a specific data model.
     *
     * @param <S> Class that defines the service implementation.
     * @param modelClass Class that defines the data model.
     * @return Instance that contains the service implementation of the data model.
     * @throws InternalErrorException Occurs when was not possible to
     * instantiate the service implementation.
     */
    protected <S extends IService<? extends BaseModel>> S getService(Class<? extends BaseModel> modelClass) throws InternalErrorException{
        if(modelClass != null)
            return ServiceUtil.getByModelClass(modelClass, this.loginSession);
        
        return null;
    }

    @Override
    public void contextDestroyed(ServletContextEvent event){
        onDestroy(event);
    }
    
    protected <L extends LoginSessionModel, U extends UserModel, LP extends LoginParameterModel> void onDestroy(ServletContextEvent event){
        setEvent(event);
    
        try{
            SecurityResourcesLoader loader = new SecurityResourcesLoader();
            SecurityResources resources = loader.getDefault();
            Class<? extends LoginSessionModel> loginSessionClass = resources.getLoginSessionClass();
            LoginSessionService<L, U, LP> loginSessionService = getService(loginSessionClass);
            
            loginSessionService.logOutAll();
        }
        catch(InternalErrorException e){
            event.getServletContext().log(null, e);
        }
        
        if(scheduledExecutor != null)
            scheduledExecutor.shutdown();
        
        if(executor != null)
            executor.shutdown();

        AbandonedConnectionCleanupThread.checkedShutdown();
    }

    @Override
    public void contextInitialized(ServletContextEvent event){
        onInitialize(event);
    }

    protected void onInitialize(ServletContextEvent event){
        setEvent(event);

        initializePersistence();
        initializeServices();
    }

    /**
     * Initialize the persistence with startup scripts.
     */
    private void initializePersistence(){
        try {
            Enumeration<URL> scriptsEnumeration = HibernateUtil.class.getClassLoader().getResources(PersistenceConstants.DEFAULT_SQL_DIR);

            if (scriptsEnumeration.hasMoreElements()) {
                while (scriptsEnumeration.hasMoreElements()) {
                    File scriptsDirectory = new File(scriptsEnumeration.nextElement().toURI());

                    if (scriptsDirectory.exists() && scriptsDirectory.isDirectory() && scriptsDirectory.canRead()) {
                        File[] scripts = scriptsDirectory.listFiles();

                        if (scripts != null) {
                            Arrays.sort(scripts, Comparator.comparing(File::getName));

                            for (File script : scripts) {
                                try (Session connection = HibernateUtil.getSession()) {
                                    String scriptContent = FileUtil.fromTextFile(script.getAbsolutePath());
                                    Scanner scriptScanner = new Scanner(scriptContent);

                                    scriptScanner.useDelimiter(";\n");

                                    Transaction transaction = connection.beginTransaction();

                                    while (scriptScanner.hasNext()) {
                                        String scriptCommand = scriptScanner.next();

                                        try {
                                            connection.createNativeQuery(scriptCommand).executeUpdate();
                                        }
                                        catch (Throwable e) {
                                            e.printStackTrace(System.err);
                                        }
                                    }

                                    transaction.commit();
                                }
                                catch (Throwable e) {
                                    e.printStackTrace(System.err);
                                }
                            }
                        }
                    }
                }
            }
        }
        catch (Throwable e) {
            e.printStackTrace(System.err);
        }
    }
    /**
     * Initialize services scheduling.
     */
    @SuppressWarnings("unchecked")
    private <L extends LoginSessionModel, U extends UserModel, LP extends LoginParameterModel, SM extends SystemModuleModel, SS extends SystemSessionModel> void initializeServices() {
        Runnable serviceListenerRunnable = () -> {
            try{
                ServiceListener.this.loginSession = SecurityUtil.getLoginSession();
                ServiceListener.this.loginSession.setId(SecurityUtil.generateToken());
                ServiceListener.this.loginSession.setStartDateTime(new DateTime());
                ServiceListener.this.loginSession.setActive(true);

                U user = ServiceListener.this.loginSession.getUser();
                Class<U> userClass = (Class<U>)user.getClass();
                IService<U> userService = getService(userClass);

                user.setName(SecurityUtil.getSystemUserName());
                user.setActive(true);

                Collection<U> users = userService.search(user);

                if(users != null && !users.isEmpty()){
                    user = users.iterator().next();

                    ServiceListener.this.loginSession.setUser(user);

                    SM systemModule = ServiceListener.this.loginSession.getSystemModule();
                    Class<SM> systemModuleClass = (Class<SM>) systemModule.getClass();
                    IService<SM> systemModuleService = getService(systemModuleClass);

                    systemModule.setUrl(event.getServletContext().getContextPath());

                    Collection<SM> systemModules = systemModuleService.search(systemModule);

                    if(systemModules != null && !systemModules.isEmpty()){
                        systemModule = systemModules.iterator().next();

                        ServiceListener.this.loginSession.setSystemModule(systemModule);

                        InetAddress localhost = InetAddress.getLocalHost();
                        SS systemSession = ServiceListener.this.loginSession.getSystemSession();
                        Class<SS> systemSessionClass = (Class<SS>) systemSession.getClass();
                        IService<SS> systemSessionService = getService(systemSessionClass);

                        systemSession.setId(ServiceListener.this.loginSession.getId());
                        systemSession.setStartDateTime(ServiceListener.this.loginSession.getStartDateTime());
                        systemSession.setIp(localhost.getHostAddress());
                        systemSession.setHostName(localhost.getHostName());

                        try{
                            systemSession = systemSessionService.save(systemSession);
                        }
                        catch(ItemAlreadyExistsException ignored){
                        }

                        ServiceListener.this.loginSession.setSystemSession(systemSession);

                        Class<L> loginSessionClass = (Class<L>) ServiceListener.this.loginSession.getClass();
                        LoginSessionService<L, U, LP> loginSessionService = getService(loginSessionClass);

                        try{
                            ServiceListener.this.loginSession = loginSessionService.save((L)ServiceListener.this.loginSession);
                        }
                        catch(ItemAlreadyExistsException ignored){
                        }

                        SystemResourcesLoader systemResourcesLoader = new SystemResourcesLoader();
                        SystemResources systemResources = systemResourcesLoader.getDefault();
                        Collection<SystemResources.ServiceResources> servicesResources = systemResources.getServices();

                        if(servicesResources != null && !servicesResources.isEmpty()){
                            Collection<Class<? extends IService<? extends BaseModel>>> jobServicesClasses = null;

                            for(SystemResources.ServiceResources serviceResources : servicesResources){
                                Class<? extends IService<? extends BaseModel>> serviceClass = null;

                                try{
                                    serviceClass = (Class<? extends IService<? extends BaseModel>>)Class.forName(serviceResources.getClazz());
                                }
                                catch(ClassNotFoundException ignored){
                                }

                                if(serviceClass != null){
                                    if(serviceResources.isJob()){
                                        if(serviceResources.isRecurrent()) {
                                            if (jobServicesClasses == null)
                                                jobServicesClasses = PropertyUtil.instantiate(Constants.DEFAULT_LIST_CLASS);

                                            if(jobServicesClasses != null)
                                                jobServicesClasses.add(serviceClass);
                                        }
                                        else {
                                            if (executor == null)
                                                executor = Executors.newWorkStealingPool();

                                            IService<? extends BaseModel> daemonService = ServiceUtil.getByServiceClass(serviceClass, ServiceListener.this.loginSession);

                                            executor.submit(new ServiceThread(daemonService));
                                        }
                                    }
                                }
                            }

                            if(jobServicesClasses != null && !jobServicesClasses.isEmpty()){
                                Calendar now = Calendar.getInstance();
                                int initialDelay = (60 - now.get(Calendar.SECOND));

                                if(initialDelay == 60)
                                    initialDelay = 0;

                                if(scheduledExecutor == null)
                                    scheduledExecutor = Executors.newScheduledThreadPool(jobServicesClasses.size());

                                for(Class<? extends IService<? extends BaseModel>> recurrentServiceClass : jobServicesClasses){
                                    IService<? extends BaseModel> recurrentService = ServiceUtil.getByServiceClass(recurrentServiceClass, ServiceListener.this.loginSession);

                                    scheduledExecutor.scheduleAtFixedRate(new ServiceThread(recurrentService), initialDelay, 60, TimeUnit.SECONDS);
                                }
                            }
                        }
                    }
                }
            }
            catch(InternalErrorException | UnknownHostException e){
                ServiceListener.this.event.getServletContext().log(null, e);
            }
        };

        Thread serviceListenerThread = new Thread(serviceListenerRunnable);

        serviceListenerThread.start();
    }
}