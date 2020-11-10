package br.com.concepting.framework.util;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import org.apache.commons.beanutils.ConstructorUtils;
import org.apache.commons.collections.CollectionUtils;

/**
 * Class responsible to manipulation collections of objects.
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
public class CollectionUtil extends CollectionUtils{
	/**
	 * Clones a list.
	 * 
	 * @param <O> Class that defines the object that will be cloned.
	 * @param <C> Class that defines the list of objects that will be cloned.
	 * @param source Instance that contains the list of objects.
	 * @return Instance that contains the cloned list.
	 * @throws NoSuchMethodException Occurs when was not possible to execute the
	 * operation.
	 * @throws IllegalAccessException Occurs when was not possible to execute
	 * the operation.
	 * @throws InvocationTargetException Occurs when was not possible to execute
	 * the operation.
	 * @throws InstantiationException Occurs when was not possible to execute
	 * the operation.
	 */
	@SuppressWarnings("unchecked")
	public static <O, C extends Collection<O>> C clone(C source) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException{
		C target = null;

		if(source != null){
			target = (C)ConstructorUtils.invokeConstructor(source.getClass(), null);

			for(O item : source)
				target.add((O)MethodUtil.invokeMethod(item, "clone", null));
		}

		return target;
	}
}