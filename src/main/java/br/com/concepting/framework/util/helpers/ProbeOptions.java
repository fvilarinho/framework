package br.com.concepting.framework.util.helpers;

import br.com.concepting.framework.util.types.ProbeType;

public class ProbeOptions{
    public static final ProbeType DEFAULT_TYPE = ProbeType.DESKTOP;
    public static final int DEFAULT_TIMEOUT = 60;
    public static final int DEFAULT_WAIT_TIME = 2;
    public static final int DEFAULT_WIDTH = 1024;
    public static final int DEFAULT_HEIGHT = 768;

    private String id = null;
    private ProbeType type = DEFAULT_TYPE;
    private int viewPortWidth = DEFAULT_WIDTH;
    private int viewPortHeight = DEFAULT_HEIGHT;
    private ProbeProxy proxy = null;
    private ProbeNetwork network = null;
    private boolean headless = false;
    private boolean kiosk = true;
    private boolean captureNetworkMetrics = true;
    private String downloadDir = null;
    private int timeout = DEFAULT_TIMEOUT;

    public String getDownloadDir(){
        return this.downloadDir;
    }

    public void setDownloadDir(String downloadDir){
        this.downloadDir = downloadDir;
    }

    public boolean getCaptureNetworkMetrics(){
        return this.captureNetworkMetrics;
    }

    public void setCaptureNetworkMetrics(boolean captureNetworkMetrics){
        this.captureNetworkMetrics = captureNetworkMetrics;
    }

    public boolean getHeadless(){
        return this.headless;
    }

    public void setHeadless(boolean headless){
        this.headless = headless;
    }

    public boolean getKiosk(){
        return this.kiosk;
    }

    public void setKiosk(boolean kiosk){
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

    public int getViewPortWidth(){
        return this.viewPortWidth;
    }

    public void setViewPortWidth(int viewPortWidth){
        this.viewPortWidth = viewPortWidth;
    }

    public int getViewPortHeight(){
        return this.viewPortHeight;
    }

    public void setViewPortHeight(int viewPortHeight){
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

    public int getTimeout(){
        return this.timeout;
    }

    public void setTimeout(int timeout){
        this.timeout = timeout;
    }
}