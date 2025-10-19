package br.com.concepting.framework.network.helpers;

public class ProbeProxy{
    private String url = null;
    private String user = null;
    private String password = null;
    
    public String getUrl(){
        return this.url;
    }
    
    public void setUrl(String url){
        this.url = url;
    }
    
    public String getUser(){
        return this.user;
    }
    
    public void setUser(String user){
        this.user = user;
    }
    
    public String getPassword(){
        return this.password;
    }
    
    public void setPassword(String password){
        this.password = password;
    }
}