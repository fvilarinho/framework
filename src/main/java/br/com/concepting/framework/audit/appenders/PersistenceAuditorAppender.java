package br.com.concepting.framework.audit.appenders;

import br.com.concepting.framework.audit.constants.AuditorConstants;
import br.com.concepting.framework.audit.model.AuditorComplementModel;
import br.com.concepting.framework.audit.model.AuditorModel;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.model.exceptions.ItemAlreadyExistsException;
import br.com.concepting.framework.model.helpers.ModelInfo;
import br.com.concepting.framework.model.util.ModelUtil;
import br.com.concepting.framework.security.model.LoginSessionModel;
import br.com.concepting.framework.service.interfaces.IService;
import br.com.concepting.framework.service.util.ServiceUtil;
import br.com.concepting.framework.util.helpers.PropertyInfo;
import org.apache.logging.log4j.core.LogEvent;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

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
public class PersistenceAuditorAppender extends BaseAuditorAppender{
    /**
     * Constructor - Initialize the appender.
     *
     * @param name String that contains the identifier of the appender.
     */
    public PersistenceAuditorAppender(final String name){
        super(name);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void process(LogEvent event) throws InternalErrorException{
        AuditorModel auditorModel = getModel(event);
        
        if(auditorModel != null){
            Class<AuditorModel> modelClass = (Class<AuditorModel>) auditorModel.getClass();
            LoginSessionModel loginSession = auditorModel.getLoginSession();
            IService<AuditorModel> auditorService = null;
            
            try{
                auditorService = ServiceUtil.getByModelClass(modelClass, loginSession);
            }
            catch(InternalErrorException ignored){
            }
            
            if(auditorService != null){
                try{
                    auditorModel = auditorService.save(auditorModel);
                    
                    ModelInfo modelInfo = ModelUtil.getInfo(modelClass);
                    PropertyInfo propertyInfo = modelInfo.getPropertyInfo(AuditorConstants.BUSINESS_COMPLEMENT_ATTRIBUTE_ID);
                    
                    if(propertyInfo != null && !propertyInfo.cascadeOnSave() && propertyInfo.getMappedRelationPropertiesIds() != null && propertyInfo.getMappedRelationPropertiesIds().length > 0){
                        Collection<AuditorComplementModel> auditorInfoComplement = auditorModel.getBusinessComplement();
                        
                        if(auditorInfoComplement != null && !auditorInfoComplement.isEmpty()){
                            Class<AuditorComplementModel> collectionItemsClass = (Class<AuditorComplementModel>) propertyInfo.getCollectionItemsClass();
                            IService<AuditorComplementModel> auditorComplementService = null;
                            
                            try{
                                auditorComplementService = ServiceUtil.getByModelClass(collectionItemsClass, loginSession);
                            }
                            catch(InternalErrorException ignored){
                            }
                            
                            if(auditorComplementService != null)
                                auditorComplementService.saveAll(auditorInfoComplement);
                        }
                    }
                }
                catch(ItemAlreadyExistsException ignored){
                }
                catch(IllegalArgumentException | IllegalAccessException | InvocationTargetException | InstantiationException | ClassNotFoundException | NoSuchFieldException | NoSuchMethodException e){
                    throw new InternalErrorException(e);
                }
            }
        }
    }
}