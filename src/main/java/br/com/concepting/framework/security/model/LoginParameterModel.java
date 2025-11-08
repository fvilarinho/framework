package br.com.concepting.framework.security.model;

import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.annotations.Model;
import br.com.concepting.framework.model.annotations.Property;
import br.com.concepting.framework.model.types.ValidationType;
import br.com.concepting.framework.util.helpers.DateTime;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serial;

/**
 * Class that defines the data model that stores the login parameters.
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
@Model
public class LoginParameterModel extends BaseModel{
    @Serial
    private static final long serialVersionUID = -6631335675531065211L;
    
    @Property(isIdentity = true)
    private Integer id = null;
    
    @Property(validations = {ValidationType.REQUIRED, ValidationType.RANGE}, minimumValue = "5", maximumValue = "365")
    private Integer expirePasswordInterval = null;
    
    @Property(validations = {ValidationType.REQUIRED, ValidationType.RANGE}, minimumValue = "5", maximumValue = "365")
    private Integer changePasswordInterval = null;
    
    @Property
    private Boolean changePassword = null;
    
    @Property
    private DateTime expirePasswordDateTime = null;
    
    @Property
    private Boolean useStrongPassword = null;
    
    @Property(validations = {ValidationType.REQUIRED, ValidationType.RANGE}, minimumValue = "8", maximumValue = "999")
    private Integer minimumPasswordLength = null;
    
    @Property
    private Boolean multipleLogins = null;
    
    @Property(validations = {ValidationType.REQUIRED, ValidationType.RANGE}, minimumValue = "1", maximumValue = "999")
    private Integer loginTries = null;
    
    @Property(validations = {ValidationType.REQUIRED, ValidationType.RANGE}, minimumValue = "1", maximumValue = "999")
    private Integer currentLoginTries = null;
    
    @Property
    private Boolean receiveIm = null;
    
    @Property
    private Boolean receiveSms = null;
    
    @Property
    private Boolean receiveVoipCall = null;
    
    @Property
    private Boolean mfa = null;
    
    @Property
    @JsonIgnore
    private String notificationToken = null;
    
    @Property
    @JsonIgnore
    private String mfaToken = null;
    
    @Property
    private Boolean mfaTokenValidated = null;
    
    @Property
    private Boolean passwordWillExpire = null;
    
    @Property
    private Integer daysUntilExpire = null;
    
    @Property
    private Integer hoursUntilExpire = null;
    
    @Property
    private Integer minutesUntilExpire = null;
    
    @Property
    private Integer secondsUntilExpire = null;
    
    @Property
    private String language = null;
    
    @Property
    private String skin = null;
    
    /**
     * Returns the notification token.
     *
     * @return String that contains the notification token.
     */
    public String getNotificationToken(){
        return this.notificationToken;
    }
    
    /**
     * Defines the notification token.
     *
     * @param notificationToken String that contains the notification token.
     */
    public void setNotificationToken(String notificationToken){
        this.notificationToken = notificationToken;
    }
    
    /**
     * Returns if it receives instant message notifications.
     *
     * @return True/False.
     */
    public Boolean getReceiveIm(){
        return this.receiveIm;
    }
    
    /**
     * Defines if it receives instant message notifications.
     *
     * @param receiveIm True/False.
     */
    public void setReceiveIm(Boolean receiveIm){
        this.receiveIm = receiveIm;
    }
    
    /**
     * Returns if it receives SMS notifications.
     *
     * @return True/False.
     */
    public Boolean getReceiveSms(){
        return this.receiveSms;
    }
    
    /**
     * Defines if it receives SMS notifications.
     *
     * @param receiveSms True/False.
     */
    public void setReceiveSms(Boolean receiveSms){
        this.receiveSms = receiveSms;
    }
    
    /**
     * Returns if it receives VoIP calls.
     *
     * @return True/False.
     */
    public Boolean getReceiveVoipCall(){
        return this.receiveVoipCall;
    }
    
    /**
     * Defines if it receives VoIP calls.
     *
     * @param receiveVoipCall True/False.
     */
    public void setReceiveVoipCall(Boolean receiveVoipCall){
        this.receiveVoipCall = receiveVoipCall;
    }
    
    /**
     * Indicates if the MFA token was validated.
     *
     * @return True/False.
     */
    public Boolean isMfaTokenValidated(){
        return this.mfaTokenValidated;
    }
    
    /**
     * Indicates if the MFA token was validated.
     *
     * @return True/False.
     */
    public Boolean getMfaTokenValidated(){
        return isMfaTokenValidated();
    }
    
    /**
     * Defines if the MFA token was validated.
     *
     * @param mfaTokenValidated True/False.
     */
    public void setMfaTokenValidated(Boolean mfaTokenValidated){
        this.mfaTokenValidated = mfaTokenValidated;
    }
    
    /**
     * Returns the MFA token.
     *
     * @return String that contains the MFA token.
     */
    public String getMfaToken(){
        return this.mfaToken;
    }
    
    /**
     * Defines the MFA token.
     *
     * @param mfaToken String that contains the MFA token.
     */
    public void setMfaToken(String mfaToken){
        this.mfaToken = mfaToken;
    }
    
    /**
     * Returns the current login tries of the user.
     *
     * @return Numeric value that contains the login tries.
     */
    public Integer getCurrentLoginTries(){
        return this.currentLoginTries;
    }
    
    /**
     * Defines the current login tries of the user.
     *
     * @param currentLoginTries Numeric value that contains the login tries.
     */
    public void setCurrentLoginTries(Integer currentLoginTries){
        this.currentLoginTries = currentLoginTries;
    }
    
    /**
     * Indicates if the user has multifactor authentication.
     *
     * @return True/False.
     */
    public Boolean hasMfa(){
        return this.mfa;
    }
    
    /**
     * Indicates if the user has multifactor authentication.
     *
     * @return True/False.
     */
    public Boolean getMfa(){
        return hasMfa();
    }
    
    /**
     * Defines if the user has multifactor authentication.
     *
     * @param mfa True/False.
     */
    public void setMfa(Boolean mfa){
        this.mfa = mfa;
    }
    
    /**
     * Returns the number of days until password expiration.
     *
     * @return Numeric value that contains the number of days.
     */
    public Integer getDaysUntilExpire(){
        return this.daysUntilExpire;
    }
    
    /**
     * Defines the number of days until password expiration.
     *
     * @param daysUntilExpire Numeric value that contains the number of days.
     */
    public void setDaysUntilExpire(Integer daysUntilExpire){
        this.daysUntilExpire = daysUntilExpire;
    }
    
    /**
     * Returns the number of hours until password expiration.
     *
     * @return Numeric value that contains the number of hours.
     */
    public Integer getHoursUntilExpire(){
        return this.hoursUntilExpire;
    }
    
    /**
     * Defines the number of hours until password expiration.
     *
     * @param hoursUntilExpire Numeric value that contains the number of hours.
     */
    public void setHoursUntilExpire(Integer hoursUntilExpire){
        this.hoursUntilExpire = hoursUntilExpire;
    }
    
    /**
     * Returns the number of minutes until password expiration.
     *
     * @return Numeric value that contains the number of minutes.
     */
    public Integer getMinutesUntilExpire(){
        return this.minutesUntilExpire;
    }
    
    /**
     * Defines the number of minutes until password expiration.
     *
     * @param minutesUntilExpire Numeric value that contains the number of minutes.
     */
    public void setMinutesUntilExpire(Integer minutesUntilExpire){
        this.minutesUntilExpire = minutesUntilExpire;
    }
    
    /**
     * Returns the number of seconds until password expiration.
     *
     * @return Numeric value that contains the number of seconds.
     */
    public Integer getSecondsUntilExpire(){
        return this.secondsUntilExpire;
    }
    
    /**
     * Defines the number of seconds until password expiration.
     *
     * @param secondsUntilExpire Numeric value that contains the number of seconds.
     */
    public void setSecondsUntilExpire(Integer secondsUntilExpire){
        this.secondsUntilExpire = secondsUntilExpire;
    }
    
    /**
     * Indicates if the password expires soon.
     *
     * @return True/False.
     */
    public Boolean isPasswordWillExpire(){
        return this.passwordWillExpire;
    }
    
    /**
     * Indicates if the password expires soon.
     *
     * @return True/False.
     */
    public Boolean getPasswordWillExpire(){
        return isPasswordWillExpire();
    }
    
    /**
     * Defines if the password expires soon.
     *
     * @param passwordWillExpire True/False.
     */
    public void setPasswordWillExpire(Boolean passwordWillExpire){
        this.passwordWillExpire = passwordWillExpire;
    }
    
    /**
     * Returns the skin of the user.
     *
     * @return String that contains the skin.
     */
    public String getSkin(){
        return this.skin;
    }
    
    /**
     * Defines the skin of the user.
     *
     * @param skin String that contains the skin.
     */
    public void setSkin(String skin){
        this.skin = skin;
    }
    
    /**
     * Returns the language of the user.
     *
     * @return String that contains the language.
     */
    public String getLanguage(){
        return this.language;
    }
    
    /**
     * Defines the language of the user.
     *
     * @param language String that contains the language.
     */
    public void setLanguage(String language){
        this.language = language;
    }
    
    /**
     * Indicates if the password should be strong.
     *
     * @return True/False.
     */
    public Boolean useStrongPassword(){
        return this.useStrongPassword;
    }
    
    /**
     * Indicates if the password should be strong.
     *
     * @return True/False.
     */
    public Boolean getUseStrongPassword(){
        return useStrongPassword();
    }
    
    /**
     * Defines if the password should be strong.
     *
     * @param useStrongPassword True/False.
     */
    public void setUseStrongPassword(Boolean useStrongPassword){
        this.useStrongPassword = useStrongPassword;
    }
    
    /**
     * Returns the minimum length of the password.
     *
     * @return Valor number that contains the minimum length of the password.
     */
    public Integer getMinimumPasswordLength(){
        return this.minimumPasswordLength;
    }
    
    /**
     * Defines the minimum length of the password.
     *
     * @param minimumPasswordLength Valor number that contains the minimum
     * length of the password.
     */
    public void setMinimumPasswordLength(Integer minimumPasswordLength){
        this.minimumPasswordLength = minimumPasswordLength;
    }
    
    /**
     * Indicates if the user must change the password in the next login.
     *
     * @return True/False.
     */
    public Boolean changePassword(){
        return this.changePassword;
    }
    
    /**
     * Indicates if the user must change the password in the next login.
     *
     * @return True/False.
     */
    public Boolean getChangePassword(){
        return changePassword();
    }
    
    /**
     * Defines if the user must change the password in the next login.
     *
     * @param changePassword True/False.
     */
    public void setChangePassword(Boolean changePassword){
        this.changePassword = changePassword;
    }
    
    /**
     * Returns the date/time of the password expiration.
     *
     * @return Instance that contains the date/time of the password expiration.
     */
    public DateTime getExpirePasswordDateTime(){
        return this.expirePasswordDateTime;
    }
    
    /**
     * Defines the date/time of the password expiration.
     *
     * @param expirePasswordDateTime Instance that contains the date/time of the
     * password expiration.
     */
    public void setExpirePasswordDateTime(DateTime expirePasswordDateTime){
        this.expirePasswordDateTime = expirePasswordDateTime;
    }
    
    /**
     * Returns the identifier of the login parameters.
     *
     * @return Numeric value that contains the identifier.
     */
    public Integer getId(){
        return this.id;
    }
    
    /**
     * Defines the identifier of the login parameters.
     *
     * @param id Numeric value that contains the identifier.
     */
    public void setId(Integer id){
        this.id = id;
    }
    
    /**
     * Returns the number of login tries.
     *
     * @return Numeric value that contains the number of login tries.
     */
    public Integer getLoginTries(){
        return this.loginTries;
    }
    
    /**
     * Defines the number of login tries.
     *
     * @param loginTries Numeric value that contains the number of login tries.
     */
    public void setLoginTries(Integer loginTries){
        this.loginTries = loginTries;
    }
    
    /**
     * Returns the interval for password change.
     *
     * @return Numeric value that contains the interval for password change.
     */
    public Integer getChangePasswordInterval(){
        return this.changePasswordInterval;
    }
    
    /**
     * Defines the interval for password change.
     *
     * @param changePasswordInterval Numeric value that contains the interval
     * for password change.
     */
    public void setChangePasswordInterval(Integer changePasswordInterval){
        this.changePasswordInterval = changePasswordInterval;
    }
    
    /**
     * Returns the interval for password expiration.
     *
     * @return Numeric value that contains the interval for password expiration.
     */
    public Integer getExpirePasswordInterval(){
        return this.expirePasswordInterval;
    }
    
    /**
     * Defines the interval for password expiration.
     *
     * @param expirePasswordInterval Numeric value that contains the interval
     * for password expiration.
     */
    public void setExpirePasswordInterval(Integer expirePasswordInterval){
        this.expirePasswordInterval = expirePasswordInterval;
    }
    
    /**
     * Indicates if the user can have multiple login sessions.
     *
     * @return True/False.
     */
    public Boolean getMultipleLogins(){
        return this.multipleLogins;
    }
    
    /**
     * Defines if the user can have multiple login sessions.
     *
     * @param multipleLogins True/False.
     */
    public void setMultipleLogins(Boolean multipleLogins){
        this.multipleLogins = multipleLogins;
    }
}