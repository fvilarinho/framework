package br.com.concepting.framework.util.helpers;

import br.com.concepting.framework.util.types.ProbeType;

@SuppressWarnings("javadoc")
public class ProbeOptions{
    public static final ProbeType DEFAULT_TYPE = ProbeType.DESKTOP;
    public static final Integer DEFAULT_TIMEOUT = 60;
    public static final Integer DEFAULT_WAIT_TIME = 2;
    
    private String id = null;
    private ProbeType type = DEFAULT_TYPE;
    private Integer viewPortWidth = null;
    private Integer viewPortHeight = null;
    private ProbeProxy proxy = null;
    private ProbeNetwork network = null;
    private Boolean headless = null;
    private Boolean kiosk = null;
    private Boolean captureNetworkMetrics = null;
    private String downloadDir = null;
    private Integer timeout = DEFAULT_TIMEOUT;
    
    public String getDownloadDir(){
        return this.downloadDir;
    }
    
    public void setDownloadDir(String downloadDir){
        this.downloadDir = downloadDir;
    }
    
    public Boolean getCaptureNetworkMetrics(){
        return this.captureNetworkMetrics;
    }
    
    public void setCaptureNetworkMetrics(Boolean captureNetworkMetrics){
        this.captureNetworkMetrics = captureNetworkMetrics;
    }
    
    public Boolean getHeadless(){
        return this.headless;
    }
    
    public void setHeadless(Boolean headless){
        this.headless = headless;
    }
    
    public Boolean getKiosk(){
        return this.kiosk;
    }
    
    public void setKiosk(Boolean kiosk){
        this.kiosk = kiosk;
    }
    
    public String getId(){
        return this.id;
    }
    
    public void setId(String id){
        this.id = id;
    }
    
    public ProbeType getType(){
        return this.type;
    }
    
    public void setType(ProbeType type){
        this.type = type;
    }
    
    public Integer getViewPortWidth(){
        return this.viewPortWidth;
    }
    
    public void setViewPortWidth(Integer viewPortWidth){
        this.viewPortWidth = viewPortWidth;
    }
    
    public Integer getViewPortHeight(){
        return this.viewPortHeight;
    }
    
    public void setViewPortHeight(Integer viewPortHeight){
        this.viewPortHeight = viewPortHeight;
    }
    
    public ProbeProxy getProxy(){
        return this.proxy;
    }
    
    public void setProxy(ProbeProxy proxy){
        this.proxy = proxy;
    }
    
    public ProbeNetwork getNetwork(){
        return this.network;
    }
    
    public void setNetwork(ProbeNetwork network){
        this.network = network;
    }
    
    public Integer getTimeout(){
        return this.timeout;
    }
    
    public void setTimeout(Integer timeout){
        if(timeout == null)
            setTimeout(DEFAULT_TIMEOUT);
        else
            this.timeout = timeout;
    }
}