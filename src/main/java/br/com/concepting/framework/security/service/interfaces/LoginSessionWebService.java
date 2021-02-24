package br.com.concepting.framework.security.service.interfaces;

import br.com.concepting.framework.security.model.LoginSessionModel;
import br.com.concepting.framework.security.model.UserModel;

/**
 * Interface that contains the basic implementation of the login session
 * WebService implementation.
 * 
 * @author fvilarinho
 * @since 1.0.0
 * @param <L> Class that defines the login session data model. 
 * @param <U> Class that defines the user data model. 
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
public interface LoginSessionWebService<L extends LoginSessionModel, U extends UserModel> extends LoginSessionService<L, U>{
}