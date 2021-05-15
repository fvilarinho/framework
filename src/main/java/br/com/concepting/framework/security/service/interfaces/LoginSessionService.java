package br.com.concepting.framework.security.service.interfaces;

import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.security.exceptions.*;
import br.com.concepting.framework.security.model.LoginParameterModel;
import br.com.concepting.framework.security.model.LoginSessionModel;
import br.com.concepting.framework.security.model.UserModel;
import br.com.concepting.framework.service.interfaces.IService;

/**
 * Interface that contains the basic implementation of the login session service
 * implementation.
 *
 * @param <L> Class that defines the login session data model.
 * @param <U> Class that defines the user data model.
 * @param <LP> Class that defines the login parameter data model.
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
public interface LoginSessionService<L extends LoginSessionModel, U extends UserModel, LP extends LoginParameterModel> extends IService<L>{
    /**
     * Logs into the system.
     *
     * @param user Instance that contains the user data model.
     * @return Instance that contains the login session data model.
     * @throws UserAlreadyLoggedInException Occurs when the user is already
     * logged in.
     * @throws UserNotFoundException Occurs when the user was not found.
     * @throws UserBlockedException Occurs when the user is blocked.
     * @throws InvalidPasswordException Occurs when the user password is
     * invalid.
     * @throws PermissionDeniedException Occurs when the user doesn't have permission
     * to log in.
     * @throws InternalErrorException Occurs when was no possible to execute the
     * operation.
     */
    L logIn(U user) throws UserAlreadyLoggedInException, UserNotFoundException, UserBlockedException, InvalidPasswordException, PermissionDeniedException, InternalErrorException;
    
    /**
     * Validates the MFA token.
     *
     * @param user String that contains the token.
     * @throws InvalidMfaTokenException Occurs when the MFA token is invalid.
     * @throws InternalErrorException Occurs when was no possible to execute the
     * operation.
     */
    void validateMfaToken(U user) throws InvalidMfaTokenException, InternalErrorException;
    
    /**
     * Sends the forgotten password to the user.
     *
     * @param user Instance that contains the user data model.
     * @throws UserNotFoundException Occurs when the user was not found.
     * @throws UserBlockedException Occurs when the user is blocked.
     * to do it.
     * @throws InternalErrorException Occurs when was no possible to execute the
     * operation.
     */
    void sendForgottenPassword(U user) throws UserNotFoundException, UserBlockedException, InternalErrorException;
    
    /**
     * Change the user password.
     *
     * @param user Instance that contains the user data model.
     * @return Instance that contains the user data model.
     * @throws PasswordIsNotStrongException Occurs when the password is not strong.
     * @throws PasswordsNotMatchException Occurs when the new password doesn't match with the confirmation.
     * @throws PasswordWithoutMinimumLengthException Occurs when the password doesn't have the minimum length.
     * @throws InternalErrorException Occurs when was no possible to execute the
     * operation.
     */
    U changePassword(U user) throws PasswordIsNotStrongException, PasswordsNotMatchException, PasswordWithoutMinimumLengthException, InternalErrorException;
    
    /**
     * Logs out the system.
     *
     * @throws InternalErrorException Occurs when was no possible to execute the
     * operation.
     */
    void logOut() throws InternalErrorException;
    
    /**
     * Logs out all session of the system.
     *
     * @throws InternalErrorException Occurs when was no possible to execute the
     * operation.
     */
    void logOutAll() throws InternalErrorException;
}