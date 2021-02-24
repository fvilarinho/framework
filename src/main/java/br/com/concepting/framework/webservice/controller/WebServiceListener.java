package br.com.concepting.framework.webservice.controller;

import java.util.Collection;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Path;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.resources.SystemResources;
import br.com.concepting.framework.resources.SystemResourcesLoader;
import br.com.concepting.framework.service.interfaces.IService;

/**
 * Class that defines the web services listener.
 * 
 * @author fvilarinho
 * @since 3.8.1
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
@ApplicationPath("/webServices")
public class WebServiceListener extends ResourceConfig{
	/**
	 * Default constructor.
	 * 
	 * @throws InternalErrorException Occurs when was not possible to locate the web services.
	 */
	@SuppressWarnings("unchecked")
	public WebServiceListener() throws InternalErrorException{
		packages("br.com.concepting.framework.webservice.helpers", 
				 "com.fasterxml.jackson.jaxrs.json", 
				 "org.glassfish.jersey.media.multipart");

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
