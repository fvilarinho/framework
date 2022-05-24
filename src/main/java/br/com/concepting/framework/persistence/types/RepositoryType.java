package br.com.concepting.framework.persistence.types;

/**
 * Class that defines the types of persistence repositories.
 *
 * @author fvilarinho
 * @since 1.0.0
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
public enum RepositoryType{
    /**
     * Microsoft SQL Server.
     */
    MSSQL("MS-SQL Server", 1433, "[", "]"),
    
    /**
     * Sybase ASE/ASA.
     */
    SYBASE("Sybase ASE/ASA", 2368, "\"", "\""),
    
    /**
     * IBM DB2.
     */
    DB2("IBM DB2", 50000, "\"", "\""),
    
    /**
     * Oracle.
     */
    ORACLE("Oracle", 1521, "\"", "\""),
    
    /**
     * MySQL.
     */
    MYSQL("MySQL", 3306, "`", "`"),
    
    /**
     * PostgreSQL.
     */
    POSTGRESQL("PostgreSQL", 5432, "\"", "\""),
    
    /**
     * IBM Informix.
     */
    INFORMIX("Informix", 26437, "\"", "\"");
    
    private String description = null;
    private Integer defaultPort = null;
    private String openQuote = null;
    private String closeQuote = null;
    
    /**
     * Constructor - Defines the repository type.
     *
     * @param description String that contains the description of the
     * repository.
     * @param defaultPort Numeric value that contains the default listen port.
     * @param openQuote String that defines the open quote of the repository.
     * @param closeQuote String that defines the close quote of the repository.
     */
    RepositoryType(String description, Integer defaultPort, String openQuote, String closeQuote){
        setDescription(description);
        setDefaultPort(defaultPort);
        setOpenQuote(openQuote);
        setCloseQuote(closeQuote);
    }
    
    /**
     * Returns a description of the repository.
     *
     * @return String that contains the description of the repository.
     */
    public String getDescription(){
        return this.description;
    }
    
    /**
     * Defines a description of the repository.
     *
     * @param description String that contains the description of the
     * repository.
     */
    public void setDescription(String description){
        this.description = description;
    }
    
    /**
     * Returns the default listen port of the repository.
     *
     * @return Numeric value that contains the default listen port.
     */
    public Integer getDefaultPort(){
        return this.defaultPort;
    }
    
    /**
     * Defines the default listen port of the repository.
     *
     * @param defaultPort Numeric value that contains the default listen port.
     */
    public void setDefaultPort(Integer defaultPort){
        this.defaultPort = defaultPort;
    }
    
    /**
     * Returns the open quote character of the repository.
     *
     * @return String that contains the quote character.
     */
    public String getOpenQuote(){
        return this.openQuote;
    }
    
    /**
     * Defines the close quote character of the repository.
     *
     * @param openQuote String that contains the quote character.
     */
    public void setOpenQuote(String openQuote){
        this.openQuote = openQuote;
    }
    
    /**
     * Returns the close quote character of the repository.
     *
     * @return String that contains the quote character.
     */
    public String getCloseQuote(){
        return this.closeQuote;
    }
    
    /**
     * Defines the close quote character of the repository.
     *
     * @param closeQuote String that contains the quote character.
     */
    public void setCloseQuote(String closeQuote){
        this.closeQuote = closeQuote;
    }
}