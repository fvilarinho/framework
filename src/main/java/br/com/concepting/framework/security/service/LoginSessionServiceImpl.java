package br.com.concepting.framework.security.service;

import br.com.concepting.framework.audit.annotations.Auditable;
import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.constants.SystemConstants;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.model.exceptions.ItemAlreadyExistsException;
import br.com.concepting.framework.model.exceptions.ItemNotFoundException;
import br.com.concepting.framework.model.helpers.ModelInfo;
import br.com.concepting.framework.model.types.ConditionType;
import br.com.concepting.framework.model.util.ModelUtil;
import br.com.concepting.framework.persistence.helpers.Filter;
import br.com.concepting.framework.persistence.interfaces.IPersistence;
import br.com.concepting.framework.resources.exceptions.InvalidResourcesException;
import br.com.concepting.framework.security.constants.SecurityConstants;
import br.com.concepting.framework.security.exceptions.*;
import br.com.concepting.framework.security.model.GroupModel;
import br.com.concepting.framework.security.model.LoginParameterModel;
import br.com.concepting.framework.security.model.LoginSessionModel;
import br.com.concepting.framework.security.model.UserModel;
import br.com.concepting.framework.security.service.interfaces.LoginSessionService;
import br.com.concepting.framework.security.util.SecurityUtil;
import br.com.concepting.framework.service.BaseService;
import br.com.concepting.framework.service.annotations.Transaction;
import br.com.concepting.framework.service.annotations.TransactionParam;
import br.com.concepting.framework.service.interfaces.IService;
import br.com.concepting.framework.util.DateTimeUtil;
import br.com.concepting.framework.util.helpers.DateTime;
import br.com.concepting.framework.util.helpers.PropertyInfo;
import br.com.concepting.framework.util.types.ContentType;
import br.com.concepting.framework.util.types.DateFieldType;
import br.com.concepting.framework.util.types.MethodType;
import org.apache.commons.beanutils.ConstructorUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

/**
 * Class that defines the basic implementation of the login session service.
 *
 * @param <L> Class that defines the login session data model.
 * @param <U> Class that defines the userdata model.
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
@Auditable
public abstract class LoginSessionServiceImpl<L extends LoginSessionModel, U extends UserModel, LP extends LoginParameterModel> extends BaseService<L> implements LoginSessionService<L, U, LP>{
    @SuppressWarnings("unchecked")
    @Transaction(url = "validateMfaToken", type = MethodType.POST, consumes = ContentType.JSON)
    @Auditable
    @Override
    public void validateMfaToken(@TransactionParam(fromBody = true) U user) throws InvalidMfaTokenException, InternalErrorException{
        L currentLoginSession = getLoginSession();
        
        if(currentLoginSession == null || currentLoginSession.getId() == null || currentLoginSession.getId().isEmpty())
            return;
        
        U currentUser = currentLoginSession.getUser();
        LP loginParameter = currentUser.getLoginParameter();
        
        if(loginParameter != null && loginParameter.isMfaTokenValidated() != null && loginParameter.isMfaTokenValidated())
            return;
        
        loginParameter = user.getLoginParameter();
        
        String token = user.getMfaToken();
        
        if(loginParameter != null && loginParameter.getMfaToken() != null && !loginParameter.getMfaToken().isEmpty() && token != null && !token.isEmpty()){
            String mfaToken = loginParameter.getMfaToken();
            
            if(token.contentEquals(mfaToken)){
                loginParameter.setMfaTokenValidated(true);
                loginParameter.setMfaToken(null);
                
                Class<LoginParameterModel> loginParameterClass = (Class<LoginParameterModel>) loginParameter.getClass();
                IService<LoginParameterModel> loginParameterService = getService(loginParameterClass);
                
                loginParameterService.update(loginParameter);
                
                return;
            }
        }
        
        throw new InvalidMfaTokenException();
    }
    
    /**
     * Does the user authentication.
     *
     * @param user Instance that contains the user data model.
     * @return Instance that contains the authenticated user data model.
     * @throws UserNotFoundException Occurs when the user was not found.
     * @throws InvalidPasswordException Occurs when the password is invalid.
     * @throws UserBlockedException Occurs when the user is blocked.
     * @throws InternalErrorException Occurs when was not possible to execute
     * the operation.
     */
    @SuppressWarnings("unchecked")
    protected U authenticate(U user) throws UserNotFoundException, InvalidPasswordException, UserBlockedException, InternalErrorException{
        if(user == null || user.getName() == null || user.getName().isEmpty())
            throw new UserNotFoundException();
        
        user.setId(null);
        user.setFullName(null);
        user.setEmail(null);
        user.setActive(null);
        user.setLoginParameter(null);
        
        Class<U> userClass = (Class<U>) user.getClass();
        IService<U> userService = getService(userClass);
        Collection<U> users = userService.search(user);
        
        if(users == null || users.isEmpty())
            throw new UserNotFoundException();
        
        String password = user.getPassword();
        
        if(password != null && !password.isEmpty()){
            try{
                password = SecurityUtil.encryptPassword(password);
            }
            catch(InvalidResourcesException e){
                throw new InternalErrorException(e);
            }
        }
        
        user = users.iterator().next();
        
        if(user.isActive() == null || !user.isActive())
            throw new UserBlockedException();
        
        boolean invalidPassword = false;
        
        if((password == null || password.isEmpty()) && user.getPassword() != null && !user.getPassword().isEmpty())
            invalidPassword = true;
        else if(password != null && !password.isEmpty() && (user.getPassword() == null || user.getPassword().isEmpty()))
            invalidPassword = true;
        else if(password != null && !password.isEmpty() && user.getPassword() != null && !password.equals(user.getPassword()))
            invalidPassword = true;
        
        LP loginParameter = user.getLoginParameter();
        
        if(invalidPassword){
            loginParameter = user.getLoginParameter();
            
            if(loginParameter != null){
                Integer loginTries = loginParameter.getLoginTries();
                Integer currentLoginTries = loginParameter.getCurrentLoginTries();
                
                if(currentLoginTries == null)
                    currentLoginTries = 1;
                else
                    currentLoginTries++;
                
                loginParameter.setCurrentLoginTries(currentLoginTries);
                
                if(loginTries != null && loginTries > 0 && currentLoginTries >= loginTries){
                    user.setActive(false);
                    
                    userService.update(user);
                    
                    throw new UserBlockedException();
                }
            }
            
            userService.update(user);
            
            throw new InvalidPasswordException();
        }
        else{
            if(loginParameter != null){
                loginParameter.setCurrentLoginTries(0);
                
                Class<LoginParameterModel> loginParameterClass = (Class<LoginParameterModel>) loginParameter.getClass();
                IService<LoginParameterModel> loginParameterService = getService(loginParameterClass);
                
                loginParameterService.update(loginParameter);
            }
        }
        
        return user;
    }
    
    /**
     * Check the user authorization.
     *
     * @param user Instance that contains the user data model.
     * @return Instance that contains the login session data model.
     * @throws UserAlreadyLoggedInException Occurs when the user is already logged in.
     * @throws PermissionDeniedException Occurs when the user doesn't have permission.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    @SuppressWarnings("unchecked")
    protected L authorize(U user) throws UserAlreadyLoggedInException, PermissionDeniedException, InternalErrorException{
        Class<U> userClass = (Class<U>) user.getClass();
        IService<U> userService = getService(userClass);
        
        user = userService.loadReference(user, SecurityConstants.GROUPS_ATTRIBUTE_ID);
        
        List<GroupModel> groups = (List<GroupModel>) user.getGroups();
        
        if(groups != null && !groups.isEmpty()){
            Class<GroupModel> groupClass = null;
            IService<GroupModel> groupService = null;
            
            for(int cont = 0; cont < groups.size(); cont++){
                GroupModel group = groups.get(cont);
                
                if(groupClass == null){
                    groupClass = (Class<GroupModel>) group.getClass();
                    groupService = getService(groupClass);
                }
                
                group = groupService.loadReference(group, SystemConstants.OBJECTS_ATTRIBUTE_ID);
                group = groupService.loadReference(group, SecurityConstants.ACCESSES_ATTRIBUTE_ID);
                
                groups.set(cont, group);
            }
        }
        
        LP loginParameter = user.getLoginParameter();
        boolean expiredPassword = false;
        boolean passwordWillExpire = false;
        int daysUntilExpire = 0;
        int hoursUntilExpire = 0;
        int minutesUntilExpire = 0;
        int secondsUntilExpire = 0;

        if(loginParameter == null){
            try{
                ModelInfo userModelInfo = ModelUtil.getInfo(user.getClass());
                PropertyInfo propertyInfo = userModelInfo.getPropertyInfo(SecurityConstants.LOGIN_PARAMETER_ATTRIBUTE_ID);
                
                loginParameter = (LP)ConstructorUtils.invokeConstructor(propertyInfo.getClazz(), null);
            }
            catch(IllegalArgumentException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException | ClassNotFoundException | NoSuchFieldException e){
                throw new InternalErrorException(e);
            }
            
            user.setLoginParameter(loginParameter);
        }
        
        if(loginParameter.getId() != null && loginParameter.getId() > 0){
            Calendar calendar = Calendar.getInstance();
            DateTime now = new DateTime(calendar.getTimeInMillis());
            DateTime expirePasswordDateTime = loginParameter.getExpirePasswordDateTime();
            Integer expirePasswordInterval = loginParameter.getExpirePasswordInterval();
            Integer changePasswordInterval = loginParameter.getChangePasswordInterval();
            
            if(expirePasswordInterval != null && expirePasswordInterval > 0){
                if(expirePasswordDateTime == null){
                    expirePasswordDateTime = DateTimeUtil.add(now, expirePasswordInterval, DateFieldType.DAYS);
                    
                    loginParameter.setExpirePasswordDateTime(expirePasswordDateTime);
                }
                
                daysUntilExpire = DateTimeUtil.diff(expirePasswordDateTime, now, DateFieldType.DAYS);
                hoursUntilExpire = DateTimeUtil.diff(expirePasswordDateTime, now, DateFieldType.HOURS) - (daysUntilExpire * 24);
                
                if(hoursUntilExpire > 24){
                    hoursUntilExpire -= 24;
                    daysUntilExpire += 1;
                }
                
                minutesUntilExpire = DateTimeUtil.diff(expirePasswordDateTime, now, DateFieldType.MINUTES) - (hoursUntilExpire * 60) - (daysUntilExpire * 24 * 60);
                secondsUntilExpire = DateTimeUtil.diff(expirePasswordDateTime, now, DateFieldType.SECONDS) - (minutesUntilExpire * 60) - (hoursUntilExpire * 60 * 60) - (daysUntilExpire * 24 * 60 * 60);
                
                if(changePasswordInterval != null && changePasswordInterval > 0)
                    if(daysUntilExpire <= changePasswordInterval)
                        passwordWillExpire = true;
                
                if((expirePasswordDateTime != null && now.after(expirePasswordDateTime)) || loginParameter.changePassword()){
                    expiredPassword = true;
                    passwordWillExpire = false;
                }
            }
        }
        
        L loginSession = getLoginSession();
        
        loginSession.setUser(user);
        
        Boolean multipleLogins = loginParameter.getMultipleLogins();
        Boolean superUser = user.getSuperUser();
        
        if(superUser == null || !superUser){
            if(!user.hasPermissions())
                throw new PermissionDeniedException();
            
            if(multipleLogins == null || !multipleLogins){
                L multipleLoginSession;
                
                try{
                    multipleLoginSession = (L) loginSession.clone();
                }
                catch(CloneNotSupportedException e){
                    throw new InternalErrorException(e);
                }
                
                multipleLoginSession.setId(null);
                multipleLoginSession.setSystemSession(null);
                multipleLoginSession.setActive(true);
                
                Collection<L> loginSessions = search(multipleLoginSession);
                
                if(loginSessions != null && !loginSessions.isEmpty())
                    throw new UserAlreadyLoggedInException();
            }
        }
        
        loginSession.setId(SecurityUtil.generateToken());
        loginSession.setStartDateTime(new DateTime());
        loginSession.setActive(true);
        
        Class<L> loginSessionClass = (Class<L>) loginSession.getClass();
        IService<L> loginSessionService = getService(loginSessionClass);
        
        try{
            loginSession = loginSessionService.save(loginSession);
        }
        catch(ItemAlreadyExistsException e){
            if(multipleLogins == null || !multipleLogins)
                throw new UserAlreadyLoggedInException();
        }
        
        user.setNewPassword(null);
        user.setConfirmPassword(null);
        
        loginParameter.setChangePassword(expiredPassword);
        loginParameter.setPasswordWillExpire(passwordWillExpire);
        
        if(passwordWillExpire){
            loginParameter.setDaysUntilExpire(daysUntilExpire);
            loginParameter.setHoursUntilExpire(hoursUntilExpire);
            loginParameter.setMinutesUntilExpire(minutesUntilExpire);
            loginParameter.setSecondsUntilExpire(secondsUntilExpire);
        }
        
        if(loginParameter.hasMfa() != null && loginParameter.hasMfa()){
            loginParameter.setMfaToken(SecurityUtil.generateToken().substring(0, SecurityConstants.DEFAULT_MINIMUM_PASSWORD_LENGTH));
            loginParameter.setMfaTokenValidated(false);
            
            sendMfaTokenMessage(user);
        }
        
        Class<LoginParameterModel> loginParameterClass = (Class<LoginParameterModel>) loginParameter.getClass();
        IService<LoginParameterModel> loginParameterService = getService(loginParameterClass);
        
        loginParameterService.update(loginParameter);
        
        return loginSession;
    }

    @Transaction(url = "logIn", type = MethodType.POST, consumes = ContentType.JSON, produces = ContentType.JSON)
    @Auditable
    @Override
    public L logIn(@TransactionParam(fromBody = true) U user) throws UserNotFoundException, UserBlockedException, InvalidPasswordException, UserAlreadyLoggedInException, PermissionDeniedException, InternalErrorException{
        if(user == null || user.getName() == null || user.getName().isEmpty())
            throw new UserNotFoundException();
        
        L currentLoginSession = getLoginSession();
        
        if(currentLoginSession != null && currentLoginSession.getId() != null && !currentLoginSession.getId().isEmpty())
            return currentLoginSession;
        
        user = authenticate(user);
        
        return authorize(user);
    }

    @SuppressWarnings("unchecked")
    @Transaction(url = "changePassword", type = MethodType.POST, consumes = ContentType.JSON, produces = ContentType.JSON)
    @Auditable
    @Override
    public U changePassword(@TransactionParam(fromBody = true) U user) throws PasswordIsNotStrongException, PasswordsNotMatchException, PasswordWithoutMinimumLengthException, InternalErrorException{
        if(user == null || user.getId() == null || user.getId() == 0)
            return user;
        
        String password = user.getPassword();
        String newPassword = user.getNewPassword();
        String confirmPassword = user.getConfirmPassword();
        IService<U> userService = getService(user.getClass());
        
        try{
            user = userService.find(user);
        }
        catch(ItemNotFoundException e){
            throw new PasswordsNotMatchException();
        }
        
        if(password != null && !password.isEmpty())
            password = SecurityUtil.encryptPassword(password);
        
        if(password != null && !password.equals(user.getPassword()))
            throw new PasswordsNotMatchException();
        
        if(newPassword != null && confirmPassword != null)
            if(!newPassword.equals(confirmPassword))
                throw new PasswordsNotMatchException();
        
        LP loginParameter = user.getLoginParameter();
        
        if(loginParameter != null){
            if(loginParameter.getMinimumPasswordLength() != null && newPassword != null && newPassword.length() < loginParameter.getMinimumPasswordLength())
                throw new PasswordWithoutMinimumLengthException(newPassword.length(), loginParameter.getMinimumPasswordLength());
            
            if(loginParameter.useStrongPassword() != null && loginParameter.useStrongPassword())
                if(!SecurityUtil.isStrongPassword(newPassword))
                    throw new PasswordIsNotStrongException();
        }
        
        try{
            newPassword = SecurityUtil.encryptPassword(newPassword);
        }
        catch(InvalidResourcesException e){
            throw new InternalErrorException(e);
        }
        
        user.setPassword(newPassword);
        
        DateTime lastUpdate = new DateTime();
        
        user.setLastUpdate(lastUpdate);
        
        if(loginParameter != null){
            loginParameter.setChangePassword(false);
            
            Integer expirePasswordInterval = loginParameter.getExpirePasswordInterval();
            
            if(expirePasswordInterval != null){
                DateTime expirePasswordDateTime = DateTimeUtil.add(lastUpdate, expirePasswordInterval, DateFieldType.DAYS);
                
                loginParameter.setExpirePasswordDateTime(expirePasswordDateTime);
            }
            
            user.setLoginParameter(loginParameter);
        }
        
        Class<U> userClass = (Class<U>) user.getClass();
        IPersistence<U> userPersistence = getPersistence(userClass);
        
        userPersistence.update(user);
        
        return user;
    }

    @SuppressWarnings("unchecked")
    @Transaction(url = "logOut", type = MethodType.DELETE)
    @Auditable
    @Override
    public void logOut() throws InternalErrorException{
        L loginSession = getLoginSession();
        
        if(loginSession == null || loginSession.getId() == null || loginSession.getId().isEmpty())
            return;
        
        U user = loginSession.getUser();
        
        if(user == null || user.getId() == null || user.getId() == 0)
            return;
        
        try{
            loginSession = find(loginSession);
            
            loginSession.setActive(false);
            
            update(loginSession);
            
            user = loginSession.getUser();
            
            LP loginParameter = user.getLoginParameter();
            
            if(loginParameter != null)
                loginParameter.setMfaToken(null);
            
            Class<U> userClass = (Class<U>) user.getClass();
            IPersistence<U> userPersistence = getPersistence(userClass);
            
            user.setLastLogin(loginSession.getStartDateTime());
            
            userPersistence.update(user);
        }
        catch(ItemNotFoundException ignored){
        }
    }

    @Transaction
    @Auditable
    @SuppressWarnings("unchecked")
    @Override
    public void logOutAll() throws InternalErrorException{
        Filter filter = new Filter();
        
        filter.addPropertyCondition(Constants.ACTIVE_ATTRIBUTE_ID, ConditionType.EQUAL);
        filter.addPropertyValue(Constants.ACTIVE_ATTRIBUTE_ID, true);
        
        Collection<L> loginSessions = filter(filter);
        
        if(loginSessions != null && !loginSessions.isEmpty()){
            for(L item: loginSessions){
                item.setActive(false);
                
                update(item);

                UserModel user = item.getUser();
                Class<UserModel> userClass = (Class<UserModel>) user.getClass();
                IPersistence<UserModel> userPersistence = getPersistence(userClass);
                
                user.setLastLogin(item.getStartDateTime());
                
                userPersistence.update(user);
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Transaction(url = "sendForgottenPassword", type = MethodType.POST, consumes = ContentType.JSON, produces = ContentType.JSON)
    @Auditable
    @Override
    public void sendForgottenPassword(@TransactionParam(fromBody = true) U user) throws UserNotFoundException, UserBlockedException, InternalErrorException{
        if(user == null || user.getEmail() == null || user.getEmail().isEmpty())
            throw new UserNotFoundException();
        
        user.setId(null);
        user.setActive(true);
        user.setName(null);
        
        Class<U> userClass = (Class<U>) user.getClass();
        IPersistence<U> persistence = getPersistence(userClass);
        Collection<U> users = persistence.search(user);
        
        if(users == null || users.isEmpty())
            throw new UserNotFoundException();
        
        user = users.iterator().next();
        
        if(user.isActive() == null || !user.isActive())
            throw new UserBlockedException();
        
        LP loginParameter = user.getLoginParameter();
        
        loginParameter.setChangePassword(true);
        
        String password = SecurityUtil.generateToken().substring(0, SecurityConstants.DEFAULT_MINIMUM_PASSWORD_LENGTH);
        
        user.setNewPassword(password);
        
        password = SecurityUtil.encryptPassword(password);
        
        user.setPassword(password);
        
        sendForgottenPasswordMessage(user);
        
        persistence.update(user);
    }
    
    /**
     * Sends the forgotten password to the user.
     *
     * @param user Instance that contains the user data model.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    protected abstract void sendForgottenPasswordMessage(U user) throws InternalErrorException;
    
    /**
     * Sends the MFA token to the user.
     *
     * @param user Instance that contains the user data model.
     * @throws InternalErrorException Occurs when was not possible to execute the operation.
     */
    protected abstract void sendMfaTokenMessage(U user) throws InternalErrorException;
}