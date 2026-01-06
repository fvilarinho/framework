package br.com.concepting.framework.processors.helpers;

import java.io.Serial;
import java.io.Serializable;

/**
 * Class that stores the build information of a project.
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
public class ProjectBuild implements Serializable{
    @Serial
    private static final long serialVersionUID = 5990509906533449866L;

    private String packageName = null;
    private String name = null;
    private String version = null;
    private String baseDirname = null;
    private String resourcesDirname = null;

    /**
     * Returns the package name.
     *
     * @return String that contains the package name.
     */
    public String getPackageName() {
        return this.packageName;
    }

    /**
     * Defines the package name.
     *
     * @param packageName String that contains the package name.
     */
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    /**
     * Returns the directory of the resources.
     *
     * @return String that contains the directory.
     */
    public String getResourcesDirname(){
        return this.resourcesDirname;
    }
    
    /**
     * Defines the directory of the resources.
     *
     * @param resourcesDirname String that contains the directory.
     */
    public void setResourcesDirname(String resourcesDirname){
        this.resourcesDirname = resourcesDirname;
    }
    
    /**
     * Returns the directory of the project.
     *
     * @return String that contains the directory.
     */
    public String getBaseDirname(){
        return this.baseDirname;
    }
    
    /**
     * Defines the directory of the project.
     *
     * @param baseDirname String that contains the directory.
     */
    public void setBaseDirname(String baseDirname){
        this.baseDirname = baseDirname;
    }
    
    /**
     * Returns the name of the project.
     *
     * @return String that contains the name.
     */
    public String getName(){
        return this.name;
    }
    
    /**
     * Defines the name of the project.
     *
     * @param name String that contains the name.
     */
    public void setName(String name){
        this.name = name;
    }
    
    /**
     * Returns the version of the project.
     *
     * @return String that contains the name.
     */
    public String getVersion(){
        return this.version;
    }
    
    /**
     * Defines the version of the project.
     *
     * @param version String that contains the version.
     */
    public void setVersion(String version){
        this.version = version;
    }
}