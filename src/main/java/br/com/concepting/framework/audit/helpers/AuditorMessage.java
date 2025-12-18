package br.com.concepting.framework.audit.helpers;

import br.com.concepting.framework.security.model.LoginSessionModel;
import br.com.concepting.framework.util.helpers.DateTime;
import org.apache.logging.log4j.message.Message;

import java.lang.reflect.Method;

/**
 * Class that defines the auditing message.
 *
 * @author fvilarinho
 * @since 3.0.0
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
public class AuditorMessage implements Message {
    private DateTime startDateTime;
    private Class<?> entity;
    private Method business;
    private String[] businessComplementArgumentsIds;
    private Class<?>[] businessComplementArgumentsTypes;
    private Object[] businessComplementArgumentsValues;
    private LoginSessionModel loginSession;
    private String formattedMessage;
    private Throwable throwable;
    private Long responseTime;

    /**
     * Returns the business complement arguments identifiers.
     *
     * @return Array that contains the identifiers.
     */
    public String[] getBusinessComplementArgumentsIds() {
        return this.businessComplementArgumentsIds;
    }

    /**
     * Defines the business complement arguments identifiers.
     *
     * @param businessComplementArgumentsIds Array that contains the identifiers.
     */
    public void setBusinessComplementArgumentsIds(String[] businessComplementArgumentsIds) {
        this.businessComplementArgumentsIds = businessComplementArgumentsIds;
    }

    /**
     * Returns the business complement arguments types.
     *
     * @return Array that contains the types.
     */
    public Class<?>[] getBusinessComplementArgumentsTypes() {
        return this.businessComplementArgumentsTypes;
    }

    /**
     * Defines the business complement arguments types.
     *
     * @param businessComplementArgumentsTypes Array that contains the types.
     */
    public void setBusinessComplementArgumentsTypes(Class<?>[] businessComplementArgumentsTypes) {
        this.businessComplementArgumentsTypes = businessComplementArgumentsTypes;
    }

    /**
     * Returns the business complement arguments values.
     *
     * @return Array that contains the values.
     */
    public Object[] getBusinessComplementArgumentsValues() {
        return this.businessComplementArgumentsValues;
    }

    /**
     * Defines the business complement arguments values.
     *
     * @param businessComplementArgumentsValues Array that contains the values.
     */
    public void setBusinessComplementArgumentsValues(Object[] businessComplementArgumentsValues) {
        this.businessComplementArgumentsValues = businessComplementArgumentsValues;
    }

    /**
     * Returns the transaction response time (in milliseconds).
     *
     * @return Numeric value that contains the response time.
     */
    public Long getResponseTime() {
        return this.responseTime;
    }

    /**
     * Defines the transaction response time (in milliseconds).
     *
     * @param responseTime Numeric value that contains the response time.
     */
    public void setResponseTime(Long responseTime) {
        this.responseTime = responseTime;
    }

    /**
     * Returns the transaction start date/time.
     *
     * @return Instance that contains the transaction start date/time.
     */
    public DateTime getStartDateTime() {
        return this.startDateTime;
    }

    /**
     * Defines the transaction start date/time.
     *
     * @param startDateTime Instance that contains the transaction start date/time.
     */
    public void setStartDateTime(DateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    /**
     * Returns the transaction entity.
     *
     * @return Class that defines transaction entity.
     */
    public Class<?> getEntity() {
        return this.entity;
    }

    /**
     * Defines the transaction entity.
     *
     * @param entity Class that defines transaction entity.
     */
    public void setEntity(Class<?> entity) {
        this.entity = entity;
    }

    /**
     * Returns the transaction business.
     *
     * @return Method that defines transaction business.
     */
    public Method getBusiness() {
        return this.business;
    }

    /**
     * Defines the transaction business.
     *
     * @param business Method that defines transaction business.
     */
    public void setBusiness(Method business) {
        this.business = business;
    }

    /**
     * Returns the login session.
     *
     * @return Instance that contains the login session.
     */
    public LoginSessionModel getLoginSession() {
        return this.loginSession;
    }

    /**
     * Defines the login session.
     *
     * @param loginSession Instance that contains the login session.
     */
    public void setLoginSession(LoginSessionModel loginSession) {
        this.loginSession = loginSession;
    }

    @Override
    public String getFormattedMessage() {
        return this.formattedMessage;
    }

    /**
     * Defines the formatted message.
     *
     * @param formattedMessage String that contains the formatted message.
     */
    public void setFormattedMessage(String formattedMessage){
        this.formattedMessage = formattedMessage;
    }

    @Override
    public Object[] getParameters() {
        return null;
    }

    @Override
    public Throwable getThrowable() {
        return this.throwable;
    }

    /**
     * Defines the exception thrown.
     *
     * @param throwable Instance that contains the exception.
     */
    public void setThrowable(Throwable throwable){
        this.throwable = throwable;
    }
}
