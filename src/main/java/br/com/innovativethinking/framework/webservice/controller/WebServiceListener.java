package br.com.innovativethinking.framework.webservice.controller;

import java.util.Collection;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Path;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

import br.com.innovativethinking.framework.exceptions.InternalErrorException;
import br.com.innovativethinking.framework.model.BaseModel;
import br.com.innovativethinking.framework.resources.SystemResources;
import br.com.innovativethinking.framework.resources.SystemResourcesLoader;
import br.com.innovativethinking.framework.service.interfaces.IService;

@ApplicationPath("/webServices")
public class WebServiceListener extends ResourceConfig{
	@SuppressWarnings("unchecked")
	public WebServiceListener() throws InternalErrorException{
		packages("br.com.innovativethinking.framework.webservice.helpers", "com.fasterxml.jackson.jaxrs.json", "org.glassfish.jersey.media.multipart");

		register(MultiPartFeature.class);
		
		SystemResourcesLoader loader = new SystemResourcesLoader();
		SystemResources resources = loader.getDefault();
		Collection<String> services = resources.getServices();
		
		if(services != null && !services.isEmpty()){
			for(String service : services){
				try{
					Class<? extends IService<? extends BaseModel>> serviceClass = (Class<? extends IService<? extends BaseModel>>)Class.forName(service);
					Path pathAnnotation = serviceClass.getAnnotation(Path.class);
					
					if(pathAnnotation != null)
						register(serviceClass);
				}
				catch(ClassNotFoundException e){
				}
			}
		}
	}
}
