package br.com.innovativethinking.framework.controller.form.util;

import br.com.innovativethinking.framework.constants.SystemConstants;
import br.com.innovativethinking.framework.controller.action.BaseAction;
import br.com.innovativethinking.framework.controller.form.BaseActionForm;
import br.com.innovativethinking.framework.controller.form.constants.ActionFormConstants;
import br.com.innovativethinking.framework.model.BaseModel;
import br.com.innovativethinking.framework.model.constants.ModelConstants;
import br.com.innovativethinking.framework.util.StringUtil;

/**
 * Class responsible to manipulate forms and its actions.
 * 
 * @author fvilarinho
 * @since 3.0.0
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
public class ActionFormUtil{
	/**
	 * Indicates if the property belongs to the dataset.
	 * 
	 * @param propertyName String that contains the identifier of the property.
	 * @return True/False.
	 */
	public static Boolean isDatasetProperty(String propertyName){
		return (propertyName != null && propertyName.contains(":"));
	}

	/**
	 * Returns the identifier of an dataset property.
	 * 
	 * @param propertyName String that contains the identifier of the dataset property.
	 * @return String that contains the identifier of the data value.
	 */
	public static String getDatasetPropertyId(String propertyName){
		if(propertyName != null && propertyName.length() > 0){
			String propertyNameBuffer[] = StringUtil.split(propertyName, ":");

			if(propertyNameBuffer != null && propertyNameBuffer.length == 3)
				return propertyNameBuffer[3];
		}

		return null;
	}

	/**
	 * Returns the data model class based on the form class.
	 *
	 * @param <M> Class that defines the data model.
	 * @param <F> Class that defines the form.
	 * @param actionFormClass Class that defines the form.
	 * @return Class that defines the data model.
	 * @throws ClassNotFoundException Occurs when was not possible to execute
	 * the operation.
	 */
	@SuppressWarnings("unchecked")
	public static <M extends BaseModel, F extends BaseActionForm<M>> Class<M> getModelClassByActionForm(Class<F> actionFormClass) throws ClassNotFoundException{
		if(actionFormClass != null)
			return (Class<M>)Class.forName(getModelClassNameByActionForm(actionFormClass.getName()));

		return null;
	}

	/**
	 * Returns the data model class name based on the form class name.
	 *
	 * @param actionFormClassName String that defines the form class name.
	 * @return String that contains the data model class name.
	 */
	public static String getModelClassNameByActionForm(String actionFormClassName){
		if(actionFormClassName != null && actionFormClassName.length() > 0){
			String modelClassName = StringUtil.replaceLast(actionFormClassName, StringUtil.capitalize(ActionFormConstants.ACTION_ATTRIBUTE_ID).concat(StringUtil.capitalize(SystemConstants.FORM_ATTRIBUTE_ID)), StringUtil.capitalize(ModelConstants.DEFAULT_ID));

			modelClassName = StringUtil.replaceAll(modelClassName, ".".concat(SystemConstants.DEFAULT_CONTROLLER_ID).concat(".").concat(SystemConstants.FORM_ATTRIBUTE_ID), ".".concat(ModelConstants.DEFAULT_ID));

			return modelClassName;
		}

		return null;
	}

	/**
	 * Returns the data model based on an actions class.
	 * 
	 * @param <M> Class that defines the data model.
	 * @param <A> Class that defines the actions of the form.
	 * @param actionClass Class that defines the actions of the form.
	 * @return Class that defines the data model.
	 * @throws ClassNotFoundException Occurs when was not possible to execute
	 * the operation.
	 */
	@SuppressWarnings("unchecked")
	public static <M extends BaseModel, A extends BaseAction<M>> Class<M> getModelClassByAction(Class<A> actionClass) throws ClassNotFoundException{
		if(actionClass != null){
			String modelClassId = StringUtil.replaceLast(actionClass.getName(), StringUtil.capitalize(ActionFormConstants.ACTION_ATTRIBUTE_ID), StringUtil.capitalize(ModelConstants.DEFAULT_ID));

			modelClassId = StringUtil.replaceAll(modelClassId, ".".concat(SystemConstants.DEFAULT_CONTROLLER_ID).concat(".").concat(ActionFormConstants.ACTION_ATTRIBUTE_ID), ".".concat(ModelConstants.DEFAULT_ID));

			return (Class<M>)Class.forName(modelClassId);
		}

		return null;
	}

	/**
	 * Returns the actions class package name based on a data model.
	 * 
	 * @param modelClass Class that defines the data model.
	 * @return String that contains the actions class package.
	 */
	public static String getActionPackageByModel(Class<? extends BaseModel> modelClass){
		if(modelClass != null)
			return StringUtil.replaceAll(modelClass.getPackage().getName(), ".".concat(ModelConstants.DEFAULT_ID), ".".concat(SystemConstants.DEFAULT_CONTROLLER_ID).concat(".").concat(ActionFormConstants.ACTION_ATTRIBUTE_ID));

		return null;
	}

	/**
	 * Returns the actions class name based on a data model.
	 * 
	 * @param modelClass Class that defines the data model.
	 * @return String that contains the actions class name.
	 */
	public static String getActionClassNameByModel(Class<? extends BaseModel> modelClass){
		if(modelClass != null){
			String actionClassId = StringUtil.replaceLast(modelClass.getName(), StringUtil.capitalize(ModelConstants.DEFAULT_ID), StringUtil.capitalize(ActionFormConstants.ACTION_ATTRIBUTE_ID));

			return StringUtil.replaceAll(actionClassId, ".".concat(ModelConstants.DEFAULT_ID), ".".concat(SystemConstants.DEFAULT_CONTROLLER_ID).concat(".").concat(ActionFormConstants.ACTION_ATTRIBUTE_ID));
		}

		return null;
	}

	/**
	 * Returns the actions names based on a data model.
	 * 
	 * @param modelClass Class that defines the data model.
	 * @return String that contains the actions names.
	 */
	public static String getActionNameByModel(Class<? extends BaseModel> modelClass){
		if(modelClass != null)
			return StringUtil.replaceLast(modelClass.getSimpleName(), StringUtil.capitalize(ModelConstants.DEFAULT_ID), StringUtil.capitalize(ActionFormConstants.ACTION_ATTRIBUTE_ID));

		return null;
	}

	/**
	 * Returns the actions class based on a data model.
	 * 
	 * @param <A> Class that defines the actions of the form.
	 * @param modelClass Class that defines the data model.
	 * @return Instance that contains the actions class.
	 * @throws ClassNotFoundException Occurs when was not possible to execute
	 * the operation.
	 */
	@SuppressWarnings("unchecked")
	public static <A extends BaseAction<? extends BaseModel>> Class<A> getActionClassByModel(Class<? extends BaseModel> modelClass) throws ClassNotFoundException{
		if(modelClass != null)
			return (Class<A>)Class.forName(getActionClassNameByModel(modelClass));

		return null;
	}

	/**
	 * Returns the form class package based on a data model.
	 * 
	 * @param modelClass Class that defines the data model.
	 * @return String that contains the form class package.
	 */
	public static String getActionFormPackageByModel(Class<? extends BaseModel> modelClass){
		if(modelClass != null)
			return StringUtil.replaceAll(modelClass.getPackage().getName(), ".".concat(ModelConstants.DEFAULT_ID), ".".concat(SystemConstants.DEFAULT_CONTROLLER_ID).concat(".").concat(SystemConstants.FORM_ATTRIBUTE_ID));

		return null;
	}

	/**
	 * Returns the form class name based on a data model.
	 * 
	 * @param modelClass Class that defines the data model.
	 * @return String that contains the form class name.
	 */
	public static String getActionFormClassNameByModel(Class<? extends BaseModel> modelClass){
		if(modelClass != null){
			String actionFormClassId = StringUtil.replaceLast(modelClass.getName(), StringUtil.capitalize(ModelConstants.DEFAULT_ID), StringUtil.capitalize(ActionFormConstants.ACTION_ATTRIBUTE_ID).concat(StringUtil.capitalize(SystemConstants.FORM_ATTRIBUTE_ID)));

			return StringUtil.replaceAll(actionFormClassId, ".".concat(ModelConstants.DEFAULT_ID), ".".concat(SystemConstants.DEFAULT_CONTROLLER_ID).concat(".").concat(SystemConstants.FORM_ATTRIBUTE_ID));
		}

		return null;
	}

	/**
	 * Returns the form name based on a data model.
	 * 
	 * @param modelClass Class that defines the data model.
	 * @return String that contains the name of the form.
	 */
	public static String getActionFormNameByModel(Class<? extends BaseModel> modelClass){
		if(modelClass != null)
			return getActionFormNameByModel(modelClass.getSimpleName());

		return null;
	}

	/**
	 * Returns the form name based on a data model class name.
	 * 
	 * @param modelClassName String that contains the data model class name.
	 * @return String that contains the name of the form.
	 */
	public static String getActionFormNameByModel(String modelClassName){
		if(modelClassName != null && modelClassName.length() > 0){
			int pos = modelClassName.lastIndexOf(".");

			if(pos >= 0)
				modelClassName = modelClassName.substring(pos + 1);

			return StringUtil.replaceLast(modelClassName, StringUtil.capitalize(ModelConstants.DEFAULT_ID), StringUtil.capitalize(ActionFormConstants.ACTION_ATTRIBUTE_ID).concat(StringUtil.capitalize(SystemConstants.FORM_ATTRIBUTE_ID)));
		}

		return null;
	}

	/**
	 * Returns the form class based on a data model.
	 * 
	 * @param <B> Class that defines the form.
	 * @param modelClass Class that defines the data model.
	 * @return Instance that contains the form class.
	 * @throws ClassNotFoundException Occurs when was not possible to execute
	 * the operation.
	 */
	@SuppressWarnings("unchecked")
	public static <B extends BaseActionForm<? extends BaseModel>> Class<B> getActionFormClassByModel(Class<? extends BaseModel> modelClass) throws ClassNotFoundException{
		if(modelClass != null)
			return (Class<B>)Class.forName(getActionFormNameByModel(modelClass));

		return null;
	}

	/**
	 * Returns the form identifier based on a data model.
	 * 
	 * @param modelClass Class that defines the data model.
	 * @return String that contains the identifier of the form.
	 */
	public static String getActionFormIdByModel(Class<? extends BaseModel> modelClass){
		if(modelClass != null)
			return getActionFormIdByModel(modelClass.getName());

		return null;
	}

	/**
	 * Returns the form identifier based on a data model class name.
	 * 
	 * @param modelClassName String that contains the data model class name.
	 * @return String that contains the identifier of the form.
	 */
	public static String getActionFormIdByModel(String modelClassName){
		if(modelClassName != null && modelClassName.length() > 0){
			String actionFormName = getActionFormNameByModel(modelClassName);
			StringBuilder id = new StringBuilder();

			id.append(actionFormName.substring(0, 1).toLowerCase());
			id.append(actionFormName.substring(1));

			return id.toString();
		}

		return null;
	}
}