package br.com.innovativethinking.framework.audit.appenders;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import org.apache.log4j.spi.LoggingEvent;

import br.com.innovativethinking.framework.audit.Auditor;
import br.com.innovativethinking.framework.audit.constants.AuditorConstants;
import br.com.innovativethinking.framework.audit.model.AuditorComplementModel;
import br.com.innovativethinking.framework.audit.model.AuditorModel;
import br.com.innovativethinking.framework.exceptions.InternalErrorException;
import br.com.innovativethinking.framework.model.BaseModel;
import br.com.innovativethinking.framework.model.exceptions.ItemAlreadyExistsException;
import br.com.innovativethinking.framework.model.helpers.ModelInfo;
import br.com.innovativethinking.framework.model.util.ModelUtil;
import br.com.innovativethinking.framework.security.model.LoginSessionModel;
import br.com.innovativethinking.framework.service.interfaces.IService;
import br.com.innovativethinking.framework.service.util.ServiceUtil;
import br.com.innovativethinking.framework.util.helpers.PropertyInfo;

/**
 * Class that defines the database storage for auditing's messages.
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
public class PersistenceAuditorAppender extends BaseAuditorAppender{
	/**
	 * Constructor - Initialize the storage.
	 * 
	 * @param auditor Instance that contains the auditing.
	 */
	public PersistenceAuditorAppender(Auditor auditor){
		super(auditor);
	}

	/**
	 * @see org.apache.log4j.Appender#requiresLayout()
	 */
	public boolean requiresLayout(){
		return false;
	}

	/**
	 * Returns the service implementation of a specific data model.
	 *
	 * @param <M> Class that defines the data model.
	 * @param <S> Class that defines the service implementation.
	 * @param modelClass Class that defines the data model.
	 * @return Instance that contains the service implementation of the data model.
	 * @throws InternalErrorException Occurs when was not possible to
	 * instantiate the service implementation.
	 */
	protected <M extends BaseModel, S extends IService<M>> S getService(Class<M> modelClass) throws InternalErrorException{
		Auditor auditor = getAuditor();
		LoginSessionModel loginSession = (auditor != null ? auditor.getLoginSession() : null);

		return ServiceUtil.getByModelClass(modelClass, loginSession);
	}

	/**
	 * @see br.com.innovativethinking.framework.audit.appenders.BaseAuditorAppender#process(org.apache.log4j.spi.LoggingEvent)
	 */
	@SuppressWarnings("unchecked")
	protected void process(LoggingEvent event) throws InternalErrorException{
		AuditorModel model = getModel(event);

		if(model != null){
			Class<AuditorModel> modelClass = (Class<AuditorModel>)model.getClass();
			IService<AuditorModel> auditorService = null;

			try{
				auditorService = getService(modelClass);
			}
			catch(InternalErrorException e){
			}

			if(auditorService != null){
				try{
					model = auditorService.save(model);

					ModelInfo modelInfo = ModelUtil.getInfo(modelClass);
					PropertyInfo propertyInfo = modelInfo.getPropertyInfo(AuditorConstants.BUSINESS_COMPLEMENT_ATTRIBUTE_ID);

					if(propertyInfo != null && (propertyInfo.cascadeOnSave() == null || !propertyInfo.cascadeOnSave()) && propertyInfo.getMappedRelationPropertiesIds() != null && propertyInfo.getMappedRelationPropertiesIds().length > 0){
						Collection<AuditorComplementModel> auditorInfoComplement = model.getBusinessComplement();

						if(auditorInfoComplement != null && !auditorInfoComplement.isEmpty()){
							Class<AuditorComplementModel> collectionItemsClass = (Class<AuditorComplementModel>)propertyInfo.getCollectionItemsClass();
							IService<AuditorComplementModel> auditorComplementService = null;

							try{
								auditorComplementService = getService(collectionItemsClass);
							}
							catch(InternalErrorException e){
							}

							if(auditorComplementService != null)
								auditorComplementService.saveAll(auditorInfoComplement);
						}
					}
				}
				catch(ItemAlreadyExistsException | NoSuchFieldException | NoSuchMethodException e1){
				}
				catch(IllegalArgumentException | IllegalAccessException | InvocationTargetException | InstantiationException | ClassNotFoundException e1){
					throw new InternalErrorException(e1);
				}
			}
		}
	}
}