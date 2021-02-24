package br.com.concepting.framework.util.helpers;

@SuppressWarnings("javadoc")
public class ProbeNetwork{
    private Integer latency = null; 
    private Integer downloadKbps = null;
    private Integer uploadKbps = null;
	
    public Integer getLatency(){
		return this.latency;
	}
	
	public void setLatency(Integer latency){
		this.latency = latency;
	}
	
	public Integer getDownloadKbps(){
		return this.downloadKbps;
	}
	
	public void setDownloadKbps(Integer downloadKbps){
		this.downloadKbps = downloadKbps;
	}
	
	public Integer getUploadKbps(){
		return this.uploadKbps;
	}

	public void setUploadKbps(Integer uploadKbps){
		this.uploadKbps = uploadKbps;
	}
}