package br.com.concepting.framework.resources;

import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.model.MainConsoleModel;
import br.com.concepting.framework.util.PropertyUtil;
import br.com.concepting.framework.util.helpers.XmlNode;

import java.io.Serializable;
import java.util.Collection;
import java.util.Locale;

/**
 * Class responsible to store the system resources.
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
public class SystemResources extends BaseResources<XmlNode>{
    private static final long serialVersionUID = -1115220620296191917L;
    
    private Class<? extends MainConsoleModel> mainConsoleClass = null;
    private Collection<Locale> languages = null;
    private Locale defaultLanguage = null;
    private Collection<String> skins = null;
    private String defaultSkin = null;
    private Collection<ActionFormResources> actionForms = null;
    private Collection<ServiceResources> services = null;
    
    /**
     * Returns the system available services.
     *
     * @return List that contains the system available services.
     */
    public Collection<ServiceResources> getServices(){
        return this.services;
    }
    
    /**
     * Defines the system available services.
     *
     * @param services List that contains the system available services.
     */
    public void setServices(Collection<ServiceResources> services){
        this.services = services;
    }
    
    /**
     * Adds a service implementation.
     *
     * @param service String that contain the identifier of the service implementation.
     */
    public void addService(ServiceResources service){
        if(service != null){
            if(this.services == null)
                this.services = PropertyUtil.instantiate(Constants.DEFAULT_LIST_CLASS);
            
            this.services.add(service);
        }
    }
    
    /**
     * Returns the list that contains the action forms.
     *
     * @param <C> Class that defines the list of action forms.
     * @return Instance that contains the action forms.
     */
    @SuppressWarnings("unchecked")
    public <C extends Collection<ActionFormResources>> C getActionForms(){
        return (C) this.actionForms;
    }
    
    /**
     * Defines the list that contains the action forms.
     *
     * @param actionForms Instance that contains the action forms.
     */
    public void setActionForms(Collection<ActionFormResources> actionForms){
        this.actionForms = actionForms;
    }
    
    /**
     * Returns the class that defines the main console data model.
     *
     * @return Class that defines the main console data model.
     */
    public Class<? extends MainConsoleModel> getMainConsoleClass(){
        return this.mainConsoleClass;
    }
    
    /**
     * Defines the class that defines the main console data model.
     *
     * @param mainConsoleClassName String that contains the identifier of the
     * main console data model.
     * @throws ClassNotFoundException Occurs when was not possible to
     * instantiate the class.
     * @throws ClassCastException Occurs when was not possible to instantiate
     * the class.
     */
    @SuppressWarnings("unchecked")
    public void setMainConsoleClass(String mainConsoleClassName) throws ClassNotFoundException, ClassCastException{
        if(mainConsoleClassName != null && !mainConsoleClassName.isEmpty())
            setMainConsoleClass((Class<? extends MainConsoleModel>) Class.forName(mainConsoleClassName));
    }
    
    /**
     * Defines the class that defines the main console data model.
     *
     * @param mainConsoleClass Class that defines the main console data model.
     */
    public void setMainConsoleClass(Class<? extends MainConsoleModel> mainConsoleClass){
        this.mainConsoleClass = mainConsoleClass;
    }
    
    /**
     * Returns the list of available skins.
     *
     * @return List of available skins.
     */
    public Collection<String> getSkins(){
        return this.skins;
    }
    
    /**
     * Defines the list of available skins.
     *
     * @param skins List of available skins.
     */
    public void setSkins(Collection<String> skins){
        this.skins = skins;
    }
    
    /**
     * Returns the list of available languages.
     *
     * @return List of available languages.
     */
    public Collection<Locale> getLanguages(){
        return this.languages;
    }
    
    /**
     * Defines the list of available languages.
     *
     * @param languages List of available languages.
     */
    public void setLanguages(Collection<Locale> languages){
        this.languages = languages;
    }
    
    /**
     * Returns the default language.
     *
     * @return Instance that contains default language.
     */
    public Locale getDefaultLanguage(){
        return this.defaultLanguage;
    }
    
    /**
     * Defines the default language.
     *
     * @param defaultLanguage Instance that contains default language.
     */
    public void setDefaultLanguage(Locale defaultLanguage){
        this.defaultLanguage = defaultLanguage;
    }
    
    /**
     * Returns the default skin.
     *
     * @return String that contains default skin.
     */
    public String getDefaultSkin(){
        return this.defaultSkin;
    }
    
    /**
     * Defines the default skin.
     *
     * @param defaultSkin String that contains default skin.
     */
    public void setDefaultSkin(String defaultSkin){
        this.defaultSkin = defaultSkin;
    }

    public static class ActionFormResources implements Serializable {
        private static final long serialVersionUID = 8287693656398359248L;

        private String name = null;
        private String clazz = null;
        private String action = null;
        private Collection<ActionFormResources.ActionFormForwardResources> forwards = null;

        /**
         * Returns the action identifier of the action form.
         *
         * @return String that contains the action identifier.
         */
        public String getAction () {
            return this.action;
        }

        /**
         * Defines the action identifier of the action form.
         *
         * @param action String that contains the action identifier.
         */
        public void setAction (String action){
            this.action = action;
        }

        /**
         * Returns the action form class.
         *
         * @return String that contains the action form class.
         */
        public String getClazz () {
            return this.clazz;
        }

        /**
         * Defines the action form class.
         *
         * @param clazz String that contains the action form class.
         */
        public void setClazz (String clazz){
            this.clazz = clazz;
        }

        /**
         * Returns the identifier of the action form.
         *
         * @return String that contains the identifier.
         */
        public String getName () {
            return this.name;
        }

        /**
         * Defines the identifier of the action form.
         *
         * @param name String that contains the identifier.
         */
        public void setName (String name){
            this.name = name;
        }

        /**
         * Returns the list of forwards of the action form.
         *
         * @param <C> Class that defines the list of forwards.
         * @return Instance that contains the forwards of the action form.
         */
        @SuppressWarnings("unchecked")
        public <C extends Collection<ActionFormResources.ActionFormForwardResources>>C getForwards () {
            return (C) this.forwards;
        }

        /**
         * Defines the list of forwards of the action form.
         *
         * @param forwards Instance that contains the forwards of the action form.
         */
        public void setForwards (Collection < ActionFormResources.ActionFormForwardResources > forwards) {
            this.forwards = forwards;
        }

        /**
         * Returns a specific forward of the action form.
         *
         * @param forwardName String that contains the identifier of the forward.
         * @return String that contains the forward of the action form.
         */
        public ActionFormResources.ActionFormForwardResources getForward (String forwardName){
            if (this.forwards != null && !this.forwards.isEmpty())
                for(ActionFormResources.ActionFormForwardResources forward : this.forwards)
                    if (forward.getName().equals(forwardName))
                        return forward;

            return null;
        }

        public static class ActionFormForwardResources implements Serializable {
            private static final long serialVersionUID = 6106118252078337780L;

            private String name = null;
            private String url = null;

            /**
             * Returns the identifier of the forward.
             *
             * @return String that contains the identifier.
             */
            public String getName() {
                return this.name;
            }

            /**
             * Defines the identifier of the forward.
             *
             * @param name String that contains the identifier.
             */
            public void setName(String name) {
                this.name = name;
            }

            /**
             * Returns the URL of the forward.
             *
             * @return String that contains the identifier.
             */
            public String getUrl() {
                return this.url;
            }

            /**
             * Defines the URL of the forward.
             *
             * @param url String that contains the identifier.
             */
            public void setUrl(String url) {
                this.url = url;
            }
        }
    }

    public static class ServiceResources implements Serializable {
        private String clazz = null;
        private boolean isDaemon = false;
        private boolean isJob = false;
        private boolean isWeb = false;
        private String url = null;

        public String getClazz() {
            return this.clazz;
        }

        public void setClazz(String clazz) {
            this.clazz = clazz;
        }

        public boolean isDaemon(){
            return this.isDaemon;
        }

        public boolean getDaemon() {
            return isDaemon();
        }

        public void setDaemon(boolean isDaemon) {
            this.isDaemon = isDaemon;
        }

        public boolean isJob(){
            return this.isJob;
        }

        public boolean isWeb() { return this.isWeb; }

        public void setWeb(boolean isWeb) { this.isWeb = isWeb;}

        public boolean getJob() { return isJob(); }

        public void setJob(boolean isJob) {
            this.isJob = isJob;
        }

        public String getUrl() {
            return this.url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}