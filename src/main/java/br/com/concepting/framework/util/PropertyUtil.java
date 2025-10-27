package br.com.concepting.framework.util;

import br.com.concepting.framework.audit.annotations.Auditable;
import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.controller.form.constants.ActionFormConstants;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.annotations.Model;
import br.com.concepting.framework.model.annotations.Property;
import br.com.concepting.framework.model.constants.ModelConstants;
import br.com.concepting.framework.model.helpers.ModelInfo;
import br.com.concepting.framework.model.types.ConditionOperationType;
import br.com.concepting.framework.model.types.ConditionType;
import br.com.concepting.framework.model.types.ValidationType;
import br.com.concepting.framework.model.util.ModelUtil;
import br.com.concepting.framework.persistence.constants.PersistenceConstants;
import br.com.concepting.framework.persistence.types.RelationJoinType;
import br.com.concepting.framework.persistence.types.RelationType;
import br.com.concepting.framework.processors.ExpressionProcessor;
import br.com.concepting.framework.resources.PropertiesResources;
import br.com.concepting.framework.ui.constants.UIConstants;
import br.com.concepting.framework.util.helpers.DateTime;
import br.com.concepting.framework.util.helpers.PropertyInfo;
import br.com.concepting.framework.util.types.*;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.apache.commons.beanutils.ConstructorUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class responsible to manipulate properties of classes and methods.
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
 * along with this program.  If not, see <a href="http://www.gnu.org/licenses"></a>.</pre>
 */
public class PropertyUtil {
    private static final PropertyUtilsBean beanUtils = new PropertyUtilsBean();

    private static ObjectMapper mapper = null;

    /**
     * Returns the mapper instance.
     *
     * @return Instance of the mapper.
     */
    public static ObjectMapper getMapper() {
        if (mapper == null) {
            mapper = new ObjectMapper();
            mapper.registerModule(new Hibernate5Module());
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            mapper.setDateFormat(new SimpleDateFormat(Constants.DEFAULT_DATE_TIME_PATTERN));
        }

        return mapper;
    }

    /**
     * Compares two objects.
     *
     * @param object1 Object 1.
     * @param object2 Object 2.
     * @return 1 - Indicates that the numeric value 1 is greater than numeric value 2.
     * value 2. 0 - Indicates that the numeric value 1 is equal to the numeric value 2.
     * value 2. -1 - Indicates that the numeric value 1 is less than numeric value 2.
     */
    public static int compareTo(Object object1, Object object2) {
        if (object1 == null && object2 == null)
            return 0;
        else if (object1 != null && object2 == null)
            return 1;
        else if (object1 == null)
            return -1;

        try {
            return (Integer) MethodUtil.invokeMethod(object1, "compareTo", object2);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            return -1;
        }
    }

    /**
     * Returns the property type.
     *
     * @param name String that contains the identifier of the property.
     * @return Instance that contains the property type.
     */
    public static PropertyType getType(String name) {
        if (name.endsWith(".".concat(Constants.LABEL_ATTRIBUTE_ID)) ||
                name.endsWith(".".concat(Constants.PATTERN_ATTRIBUTE_ID)) ||
                name.equals(ActionFormConstants.ACTION_ATTRIBUTE_ID) ||
                name.equals(ActionFormConstants.FORWARD_ATTRIBUTE_ID) ||
                name.equals(ModelConstants.VALIDATE_MODEL_ATTRIBUTE_ID) ||
                name.equals(ModelConstants.VALIDATE_MODEL_PROPERTIES_ATTRIBUTE_ID) ||
                name.endsWith(".".concat(ActionFormConstants.DATASET_START_INDEX_ATTRIBUTE_ID)) ||
                name.endsWith(".".concat(ActionFormConstants.DATASET_END_INDEX_ATTRIBUTE_ID)) ||
                name.endsWith(".".concat(ActionFormConstants.DATASET_SCOPE_ATTRIBUTE_ID)) ||
                name.endsWith(".".concat(ActionFormConstants.DATASET_ATTRIBUTE_ID)))
            return PropertyType.FORM;
        else if (name.endsWith(".".concat(UIConstants.SORT_ORDER_ATTRIBUTE_ID)) ||
                name.endsWith(".".concat(UIConstants.SORT_PROPERTY_ATTRIBUTE_ID)) ||
                name.endsWith(".".concat(UIConstants.CURRENT_GUIDE_ATTRIBUTE_ID)) ||
                name.endsWith(".".concat(UIConstants.CURRENT_SECTIONS_ATTRIBUTE_ID)) ||
                name.endsWith(".".concat(UIConstants.CURRENT_TREE_VIEW_NODE_ATTRIBUTE_ID)) ||
                name.endsWith(".".concat(UIConstants.IS_TREE_VIEW_NODE_EXPANDED_ATTRIBUTE_ID)) ||
                name.endsWith(".".concat(UIConstants.PAGER_ITEMS_PER_PAGE_ATTRIBUTE_ID)) ||
                name.endsWith(".".concat(UIConstants.PAGER_CURRENT_PAGE_ATTRIBUTE_ID)) ||
                name.endsWith(".".concat(UIConstants.PAGER_ACTION_ATTRIBUTE_ID)) ||
                name.endsWith(".".concat(UIConstants.COLOR_PICKER_RED_VALUE_ATTRIBUTE_ID)) ||
                name.endsWith(".".concat(UIConstants.COLOR_PICKER_GREEN_VALUE_ATTRIBUTE_ID)) ||
                name.endsWith(".".concat(UIConstants.COLOR_PICKER_BLUE_VALUE_ATTRIBUTE_ID)) ||
                name.endsWith(".".concat(UIConstants.CALENDAR_HOURS_ATTRIBUTE_ID)) ||
                name.endsWith(".".concat(UIConstants.CALENDAR_MINUTES_ATTRIBUTE_ID)) ||
                name.endsWith(".".concat(UIConstants.CALENDAR_SECONDS_ATTRIBUTE_ID)) ||
                name.endsWith(".".concat(UIConstants.CALENDAR_MILLISECONDS_ATTRIBUTE_ID)) ||
                name.equals(UIConstants.UPDATE_VIEWS_ATTRIBUTE_ID))
            return PropertyType.UI;
        else
            return PropertyType.MODEL;
    }

    /**
     * Parses a content.
     *
     * @param <O>                  Class that defines the content.
     * @param clazz                Class that defines the content.
     * @param value                String that contains the content.
     * @param pattern              String that contains the parsing pattern.
     * @param useAdditionalParsing Indicates if it must be used as an additional pattern for parsing.
     * @param precision            Numeric value that defines the precision.
     * @param language             Instance that defines the
     * @return Returns the parsed content.
     * @throws ParseException               Occurs when was not possible to execute the operation.
     * @throws UnsupportedEncodingException Occurs when was not possible to execute the operation.
     */
    @SuppressWarnings("unchecked")
    public static <O> O parse(Class<O> clazz, String value, String pattern, boolean useAdditionalParsing, int precision, Locale language) throws ParseException, UnsupportedEncodingException {
        if (isDate(clazz)) {
            if (pattern != null && !pattern.isEmpty())
                return (O) DateTimeUtil.parse(value, pattern);

            return (O) DateTimeUtil.parse(value, language);
        } else if (isNumber(clazz))
            return (O) NumberUtil.parse(clazz, value, useAdditionalParsing, precision, language);
        else if (isBoolean(clazz))
            return (O) Boolean.valueOf(value);
        else if (isByteArray(clazz))
            return (O) ByteUtil.fromBase64(value);

        return (O) value;
    }

    /**
     * Returns the default implementation of a class.
     *
     * @param clazz Definition of the class.
     * @return Implementation of the class.
     */
    public static Class<?> getDefaultImplementation(Class<?> clazz) {
        if (clazz.equals(Collection.class) || clazz.equals(List.class))
            return Constants.DEFAULT_LIST_CLASS;
        else if (clazz.equals(Set.class))
            return Constants.DEFAULT_SET_CLASS;
        else if (clazz.equals(Map.class))
            return Constants.DEFAULT_MAP_CLASS;
        else if (clazz.equals(Queue.class))
            return Constants.DEFAULT_LIFO_QUEUE_CLASS;

        return clazz;
    }

    /**
     * Instantiate the default implementation of a class.
     *
     * @param <O>   Class that defines the implementation.
     * @param clazz Definition of the class.
     * @return Instance that contains the implementation.
     */
    @SuppressWarnings("unchecked")
    public static <O> O instantiate(Class<?> clazz) {
        try {
            if (clazz != null)
                return (O) getDefaultImplementation(clazz).getDeclaredConstructor().newInstance();
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException |
                 NoSuchMethodException ignored) {
        }

        return null;
    }

    /**
     * Clears all properties of an instance.
     *
     * @param <O>      Instance before clearing.
     * @param instance Instance after clearing.
     */
    public static <O> void clearAllProperties(O instance) {
        if (instance == null)
            return;

        Class<?> superClass = instance.getClass();

        while (superClass != null) {
            Field[] fields = superClass.getDeclaredFields();

            for (Field fieldItem : fields) {
                if (Modifier.isStatic(fieldItem.getModifiers()))
                    continue;

                try {
                    setValue(instance, fieldItem.getName(), null);
                } catch (InvocationTargetException ignored) {
                }
            }

            superClass = superClass.getSuperclass();
        }
    }

    /**
     * Indicates if the class is a string.
     *
     * @param className String that contains the class name.
     * @return True/False.
     * @throws ClassNotFoundException Occurs when was not possible to execute
     *                                the operation.
     */
    public static boolean isString(String className) throws ClassNotFoundException {
        if (className != null && !className.isEmpty())
            return isString(Class.forName(className));

        return false;
    }

    /**
     * Indicates if the value is a string.
     *
     * @param value Instance to be verified.
     * @return True/False.
     */
    public static boolean isString(Object value) {
        return (value instanceof String);
    }

    /**
     * Indicates if the class is a string.
     *
     * @param clazz Class that will be checked.
     * @return True/False.
     */
    public static boolean isString(Class<?> clazz) {
        if (clazz == null)
            return false;

        Class<?> superClass = clazz;

        while (superClass != null && !superClass.equals(Object.class)) {
            if (superClass.equals(String.class))
                return true;

            superClass = superClass.getSuperclass();
        }

        return false;
    }

    /**
     * Indicates if the class is boolean.
     *
     * @param className String that contains the class name.
     * @return True/False.
     * @throws ClassNotFoundException Occurs when was not possible to execute
     *                                the operation.
     */
    public static boolean isBoolean(String className) throws ClassNotFoundException {
        if (className != null && !className.isEmpty())
            return isBoolean(Class.forName(className));

        return false;
    }

    /**
     * Indicates if the value it is boolean.
     *
     * @param value Instance to be verified.
     * @return True/False.
     */
    public static boolean isBoolean(Object value) {
        return (value instanceof Boolean);
    }

    /**
     * Indicates if the class is boolean.
     *
     * @param clazz Class that will be checked.
     * @return True/False.
     */
    public static boolean isBoolean(Class<?> clazz) {
        if (clazz == null)
            return false;

        Class<?> superClass = clazz;

        while (superClass != null && !superClass.equals(Object.class)) {
            if (superClass.equals(Boolean.class) || superClass.equals(boolean.class))
                return true;

            superClass = superClass.getSuperclass();
        }

        return false;
    }

    /**
     * Indicates if the class is a date.
     *
     * @param className String that contains the class name.
     * @return True/False.
     * @throws ClassNotFoundException Occurs when was not possible to execute
     *                                the operation.
     */
    public static boolean isDate(String className) throws ClassNotFoundException {
        if (className != null && !className.isEmpty())
            return isDate(Class.forName(className));

        return false;
    }

    /**
     * Indicates if the value is a date.
     *
     * @param value Instance to be verified.
     * @return True/False.
     */
    public static boolean isDate(Object value) {
        return (value instanceof Date);
    }

    /**
     * Indicates if the class is a date.
     *
     * @param clazz Class that will be checked.
     * @return True/False.
     */
    public static boolean isDate(Class<?> clazz) {
        if (clazz == null)
            return false;

        Class<?> superClass = clazz;

        while (superClass != null && !superClass.equals(Object.class)) {
            if (superClass.equals(Date.class))
                return true;

            superClass = superClass.getSuperclass();
        }

        return false;
    }

    /**
     * Indicates if the class is a time.
     *
     * @param className String that contains the class name.
     * @return True/False.
     * @throws ClassNotFoundException Occurs when was not possible to execute
     *                                the operation.
     */
    public static boolean isTime(String className) throws ClassNotFoundException {
        if (className != null && !className.isEmpty())
            return isTime(Class.forName(className));

        return false;
    }

    /**
     * Indicates if the value is a time.
     *
     * @param value Instance to be verified.
     * @return True/False.
     */
    public static boolean isTime(Object value) {
        return (value instanceof DateTime);
    }

    /**
     * Indicates if the class is a time.
     *
     * @param clazz Class that will be checked.
     * @return True/False.
     */
    public static boolean isTime(Class<?> clazz) {
        if (clazz == null)
            return false;

        Class<?> superClass = clazz;

        while (superClass != null && !superClass.equals(Object.class)) {
            if (superClass.equals(DateTime.class))
                return true;

            superClass = superClass.getSuperclass();
        }

        return false;
    }

    /**
     * Indicates if the class is a byte array.
     *
     * @param className String that contains the class name.
     * @return True/False.
     * @throws ClassNotFoundException Occurs when was not possible to execute
     *                                the operation.
     */
    public static boolean isByteArray(String className) throws ClassNotFoundException {
        if (className != null && !className.isEmpty())
            return isByteArray(Class.forName(className));

        return false;
    }

    /**
     * Indicates if the value is a byte array.
     *
     * @param value Instance to be verified.
     * @return True/False.
     */
    public static boolean isByteArray(Object value) {
        return (value instanceof byte[]);
    }

    /**
     * Indicates if the class is a byte array.
     *
     * @param clazz Class that will be checked.
     * @return True/False.
     */
    public static boolean isByteArray(Class<?> clazz) {
        if (clazz == null)
            return false;

        Class<?> superClass = clazz;

        while (superClass != null && !superClass.equals(Object.class)) {
            if (superClass.equals(byte[].class) || superClass.equals(Byte[].class))
                return true;

            superClass = superClass.getSuperclass();
        }

        return false;
    }

    /**
     * Indicates if the class is an integer value.
     *
     * @param className String that contains the class name.
     * @return True/False.
     * @throws ClassNotFoundException Occurs when was not possible to execute
     *                                the operation.
     */
    public static boolean isInteger(String className) throws ClassNotFoundException {
        if (className != null && !className.isEmpty())
            return isInteger(Class.forName(className));

        return false;
    }

    /**
     * Indicates if the value is an integer value.
     *
     * @param value Instance to be verified.
     * @return True/False.
     */
    public static boolean isInteger(Object value) {
        return (value instanceof Integer);
    }

    /**
     * Indicates if the class is an integer value.
     *
     * @param clazz Class that will be checked.
     * @return True/False.
     */
    public static boolean isInteger(Class<?> clazz) {
        if (clazz == null)
            return false;

        Class<?> superClass = clazz;

        while (superClass != null && !superClass.equals(Object.class)) {
            if (superClass.equals(int.class) || superClass.equals(Integer.class))
                return true;

            superClass = superClass.getSuperclass();
        }

        return false;
    }

    /**
     * Indicates if the class is a long value.
     *
     * @param className String that contains the class name.
     * @return True/False.
     * @throws ClassNotFoundException Occurs when was not possible to execute
     *                                the operation.
     */
    public static boolean isLong(String className) throws ClassNotFoundException {
        if (className != null && !className.isEmpty())
            return isLong(Class.forName(className));

        return false;
    }

    /**
     * Indicates if the value is a long value.
     *
     * @param value Instance to be verified.
     * @return True/False.
     */
    public static boolean isLong(Object value) {
        return (value instanceof Long);
    }

    /**
     * Indicates if the class is a long value.
     *
     * @param clazz Class that will be checked.
     * @return True/False.
     */
    public static boolean isLong(Class<?> clazz) {
        if (clazz == null)
            return false;

        Class<?> superClass = clazz;

        while (superClass != null && !superClass.equals(Object.class)) {
            if (superClass.equals(long.class) || superClass.equals(Long.class))
                return true;

            superClass = superClass.getSuperclass();
        }

        return false;
    }

    /**
     * Indicates if the class is a short value.
     *
     * @param className String that contains the class name.
     * @return True/False.
     * @throws ClassNotFoundException Occurs when was not possible to execute
     *                                the operation.
     */
    public static boolean isShort(String className) throws ClassNotFoundException {
        if (className != null && !className.isEmpty())
            return isShort(Class.forName(className));

        return false;
    }

    /**
     * Indicates if the value is a short value.
     *
     * @param value Instance to be verified.
     * @return True/False.
     */
    public static boolean isShort(Object value) {
        return (value instanceof Short);
    }

    /**
     * Indicates if the class is a short value.
     *
     * @param clazz Class that will be checked.
     * @return True/False.
     */
    public static boolean isShort(Class<?> clazz) {
        if (clazz == null)
            return false;

        Class<?> superClass = clazz;

        while (superClass != null && !superClass.equals(Object.class)) {
            if (superClass.equals(short.class) || superClass.equals(Short.class))
                return true;

            superClass = superClass.getSuperclass();
        }

        return false;
    }

    /**
     * Indicates if the class is a byte value.
     *
     * @param className String that contains the class name.
     * @return True/False.
     * @throws ClassNotFoundException Occurs when was not possible to execute
     *                                the operation.
     */
    public static boolean isByte(String className) throws ClassNotFoundException {
        if (className != null && !className.isEmpty())
            return isByte(Class.forName(className));

        return false;
    }

    /**
     * Indicates if the value is a byte value.
     *
     * @param value Instance to be verified.
     * @return True/False.
     */
    public static boolean isByte(Object value) {
        return (value instanceof Byte);
    }

    /**
     * Indicates if the class is a byte value.
     *
     * @param clazz Class that will be checked.
     * @return True/False.
     */
    public static boolean isByte(Class<?> clazz) {
        if (clazz == null)
            return false;

        Class<?> superClass = clazz;

        while (superClass != null && !superClass.equals(Object.class)) {
            if (superClass.equals(byte.class) || superClass.equals(Byte.class))
                return true;

            superClass = superClass.getSuperclass();
        }

        return false;
    }

    /**
     * Indicates if the class is a float value.
     *
     * @param className String that contains the class name.
     * @return True/False.
     * @throws ClassNotFoundException Occurs when was not possible to execute
     *                                the operation.
     */
    public static boolean isFloat(String className) throws ClassNotFoundException {
        if (className != null && !className.isEmpty())
            return isFloat(Class.forName(className));

        return false;
    }

    /**
     * Indicates if the value is a float value.
     *
     * @param value Instance to be verified.
     * @return True/False.
     */
    public static boolean isFloat(Object value) {
        return (value instanceof Float);
    }

    /**
     * Indicates if the class is a float value.
     *
     * @param clazz Class that will be checked.
     * @return True/False.
     */
    public static boolean isFloat(Class<?> clazz) {
        if (clazz == null)
            return false;

        Class<?> superClass = clazz;

        while (superClass != null && !superClass.equals(Object.class)) {
            if (superClass.equals(float.class) || superClass.equals(Float.class))
                return true;

            superClass = superClass.getSuperclass();
        }

        return false;
    }

    /**
     * Indicates if the class is a double value.
     *
     * @param className String that contains the class name.
     * @return True/False.
     * @throws ClassNotFoundException Occurs when was not possible to execute
     *                                the operation.
     */
    public static boolean isDouble(String className) throws ClassNotFoundException {
        if (className != null && !className.isEmpty())
            return isDouble(Class.forName(className));

        return false;
    }

    /**
     * Indicates if the value is a double value.
     *
     * @param value Instance to be verified.
     * @return True/False.
     */
    public static boolean isDouble(Object value) {
        return (value instanceof Double);
    }

    /**
     * Indicates if the class is a double value.
     *
     * @param clazz Class that will be checked.
     * @return True/False.
     */
    public static boolean isDouble(Class<?> clazz) {
        if (clazz == null)
            return false;

        Class<?> superClass = clazz;

        while (superClass != null && !superClass.equals(Object.class)) {
            if (superClass.equals(double.class) || superClass.equals(Double.class))
                return true;

            superClass = superClass.getSuperclass();
        }

        return false;
    }

    /**
     * Indicates if the class is a big integer value.
     *
     * @param className String that contains the class name.
     * @return True/False.
     * @throws ClassNotFoundException Occurs when was not possible to execute
     *                                the operation.
     */
    public static boolean isBigInteger(String className) throws ClassNotFoundException {
        if (className != null && !className.isEmpty())
            return isBigInteger(Class.forName(className));

        return false;
    }

    /**
     * Indicates if the value is a big integer value.
     *
     * @param value Instance to be verified.
     * @return True/False.
     */
    public static boolean isBigInteger(Object value) {
        return (value instanceof BigInteger);
    }

    /**
     * Indicates if the class is a big integer value.
     *
     * @param clazz Class that will be checked.
     * @return True/False.
     */
    public static boolean isBigInteger(Class<?> clazz) {
        if (clazz == null)
            return false;

        Class<?> superClass = clazz;

        while (superClass != null && !superClass.equals(Object.class)) {
            if (superClass.equals(BigInteger.class))
                return true;

            superClass = superClass.getSuperclass();
        }

        return false;
    }

    /**
     * Indicates if the class is a big decimal value.
     *
     * @param className String that contains the class name.
     * @return True/False.
     * @throws ClassNotFoundException Occurs when was not possible to execute
     *                                the operation.
     */
    public static boolean isBigDecimal(String className) throws ClassNotFoundException {
        if (className != null && !className.isEmpty())
            return isBigDecimal(Class.forName(className));

        return false;
    }

    /**
     * Indicates if the value is a big decimal value.
     *
     * @param value Instance to be verified.
     * @return True/False.
     */
    public static boolean isBigDecimal(Object value) {
        return (value instanceof BigDecimal);
    }

    /**
     * Indicates if the class is a big decimal value.
     *
     * @param clazz Class that will be checked.
     * @return True/False.
     */
    public static boolean isBigDecimal(Class<?> clazz) {
        if (clazz == null)
            return false;

        Class<?> superClass = clazz;

        while (superClass != null && !superClass.equals(Object.class)) {
            if (superClass.equals(BigDecimal.class))
                return true;

            superClass = superClass.getSuperclass();
        }

        return false;
    }

    /**
     * Indicates if the class is a numeric value.
     *
     * @param className String that contains the class name.
     * @return True/False.
     * @throws ClassNotFoundException Occurs when was not possible to execute
     *                                the operation.
     */
    public static boolean isNumber(String className) throws ClassNotFoundException {
        if (className != null && !className.isEmpty())
            return isNumber(Class.forName(className));

        return false;
    }

    /**
     * Indicates if the value is a numeric value.
     *
     * @param value Instance to be verified.
     * @return True/False.
     */
    public static boolean isNumber(Object value) {
        return (value instanceof Number);
    }

    /**
     * Indicates if the class is a numeric value.
     *
     * @param clazz Class that will be checked.
     * @return True/False.
     */
    public static boolean isNumber(Class<?> clazz) {
        if (clazz == null)
            return false;

        Class<?> superClass = clazz;

        while (superClass != null && !superClass.equals(Object.class)) {
            if (superClass.equals(Number.class) || superClass.equals(byte.class) || superClass.equals(short.class) || superClass.equals(int.class) || superClass.equals(long.class) || superClass.equals(float.class) || superClass.equals(double.class))
                return true;

            superClass = superClass.getSuperclass();
        }

        return false;
    }

    /**
     * Indicates if the class is a currency value.
     *
     * @param className String that contains the class name.
     * @return True/False.
     * @throws ClassNotFoundException Occurs when was not possible to execute
     *                                the operation.
     */
    public static boolean isCurrency(String className) throws ClassNotFoundException {
        if (className != null && !className.isEmpty())
            return isCurrency(Class.forName(className));

        return false;
    }

    /**
     * Indicates if the value is a currency value.
     *
     * @param value Instance to be verified.
     * @return True/False.
     */
    public static boolean isCurrency(Object value) {
        return (value instanceof Currency);
    }

    /**
     * Indicates if the class is a currency value.
     *
     * @param clazz Class that will be checked.
     * @return True/False.
     */
    public static boolean isCurrency(Class<?> clazz) {
        if (clazz == null)
            return false;

        Class<?> superClass = clazz;

        while (superClass != null && !superClass.equals(Object.class)) {
            if (superClass.equals(Currency.class))
                return true;

            superClass = superClass.getSuperclass();
        }

        return false;
    }

    /**
     * Indicates if the class is a data model.
     *
     * @param className String that contains the class name.
     * @return True/False.
     * @throws ClassNotFoundException Occurs when was not possible to execute
     *                                the operation.
     */
    public static boolean isModel(String className) throws ClassNotFoundException {
        if (className != null && !className.isEmpty())
            return isModel(Class.forName(className));

        return false;
    }

    /**
     * Indicates if the value is a data model.
     *
     * @param value Instance to be verified.
     * @return True/False.
     */
    public static boolean isModel(Object value) {
        return (value instanceof BaseModel);
    }

    /**
     * Indicates if the class is a data model.
     *
     * @param clazz Class that will be checked.
     * @return True/False.
     */
    public static boolean isModel(Class<?> clazz) {
        if (clazz == null)
            return false;

        Class<?> superClass = clazz;

        while (superClass != null && !superClass.equals(Object.class)) {
            if (superClass.equals(BaseModel.class))
                return true;

            superClass = superClass.getSuperclass();
        }

        return false;
    }

    /**
     * Indicates if the value is an array.
     *
     * @param value Instance to be verified.
     * @return True/False.
     */
    public static boolean isArray(Object value) {
        return (value instanceof Object[]);
    }

    /**
     * Indicates if the class is a map.
     *
     * @param className String that contains the class name.
     * @return True/False.
     * @throws ClassNotFoundException Occurs when was not possible to execute
     *                                the operation.
     */
    public static boolean isMap(String className) throws ClassNotFoundException {
        if (className != null && !className.isEmpty())
            return isMap(Class.forName(className));

        return false;
    }

    /**
     * Indicates if the value is a map.
     *
     * @param value Instance that will be checked.
     * @return True/False.
     */
    public static boolean isMap(Object value) {
        return (value instanceof Map);
    }

    /**
     * Indicates if the class is a map.
     *
     * @param clazz Class that will be checked.
     * @return True/False.
     */
    public static boolean isMap(Class<?> clazz) {
        if (clazz == null)
            return false;

        Class<?> superClass = clazz;

        while (superClass != null && !superClass.equals(Object.class)) {
            if (superClass.equals(Map.class))
                return true;

            superClass = superClass.getSuperclass();
        }

        return false;
    }

    /**
     * Indicates if the class is a collection.
     *
     * @param className String that contains the class name.
     * @return True/False.
     * @throws ClassNotFoundException Occurs when was not possible to execute
     *                                the operation.
     */
    public static boolean isCollection(String className) throws ClassNotFoundException {
        if (className != null && !className.isEmpty())
            return isCollection(Class.forName(className));

        return false;
    }

    /**
     * Indicates if the value is a collection.
     *
     * @param value Instance to be verified.
     * @return True/False.
     */
    public static boolean isCollection(Object value) {
        return (value instanceof Collection);
    }

    /**
     * Indicates if the class is a collection.
     *
     * @param clazz Class that will be checked.
     * @return True/False.
     */
    public static boolean isCollection(Class<?> clazz) {
        if (clazz == null)
            return false;

        Class<?> superClass = clazz;

        while (superClass != null && !superClass.equals(Object.class)) {
            if (superClass.equals(AbstractCollection.class) || PersistenceConstants.DEFAULT_COLLECTION_TYPES_IDS.contains(superClass.getName()))
                return true;

            superClass = superClass.getSuperclass();
        }

        return false;
    }

    /**
     * Indicates if the class is an enumeration.
     *
     * @param className String that contains the class name.
     * @return True/False.
     * @throws ClassNotFoundException Occurs when was not possible to execute
     *                                the operation.
     */
    public static boolean isEnum(String className) throws ClassNotFoundException {
        if (className != null && !className.isEmpty())
            return isEnum(Class.forName(className));

        return false;
    }

    /**
     * Indicates if the value is an enumeration.
     *
     * @param value Instance to be verified.
     * @return True/False.
     */
    public static boolean isEnum(Object value) {
        return (value instanceof Enum);
    }

    /**
     * Indicates if the class is an enumeration.
     *
     * @param clazz Class that will be checked.
     * @return True/False.
     */
    public static boolean isEnum(Class<?> clazz) {
        if (clazz == null)
            return false;

        return clazz.isEnum();
    }

    /**
     * Formats an instance.
     *
     * @param <O>      Class that defines the instance.
     * @param instance Instance that will be used.
     * @param language Instance that contains the language that will be used.
     * @return String that contains the formatted instance.
     */
    public static <O> String format(O instance, Locale language) {
        if (instance != null)
            return format(instance, null, false, NumberUtil.getDefaultPrecision(instance), language);

        return null;
    }

    /**
     * Formats an instance.
     *
     * @param <O>      Class that defines the instance.
     * @param instance Instance that will be used.
     * @param pattern  String that contains the pattern that will be used.
     * @return String that contains the formatted instance.
     */
    public static <O> String format(O instance, String pattern) {
        if (instance != null)
            return format(instance, pattern, LanguageUtil.getDefaultLanguage());

        return null;
    }

    /**
     * Formats an instance.
     *
     * @param <O>      Class that defines the instance.
     * @param instance Instance that will be used.
     * @param pattern  String that contains the pattern that will be used.
     * @param language Instance that contains the language that will be used.
     * @return String that contains the formatted instance.
     */
    public static <O> String format(O instance, String pattern, Locale language) {
        if (instance != null)
            return format(instance, pattern, false, NumberUtil.getDefaultPrecision(instance), language);

        return null;
    }

    /**
     * Formats an instance.
     *
     * @param <O>      Class that defines the instance.
     * @param instance Instance that will be used.
     * @return String that contains the formatted instance.
     */
    public static <O> String format(O instance) {
        if (instance != null)
            return format(instance, null, false, NumberUtil.getDefaultPrecision(instance), LanguageUtil.getDefaultLanguage());

        return null;
    }

    /**
     * Formats an instance.
     *
     * @param <O>                     Class that defines the instance.
     * @param instance                Instance that will be used.
     * @param useAdditionalFormatting Indicates if additional formatting
     *                                should be used
     * @return String that contains the formatted instance.
     */
    public static <O> String format(O instance, boolean useAdditionalFormatting) {
        if (instance != null)
            return format(instance, null, useAdditionalFormatting, NumberUtil.getDefaultPrecision(instance), LanguageUtil.getDefaultLanguage());

        return null;
    }

    /**
     * Formats an instance.
     *
     * @param <O>                     Class that defines the instance.
     * @param instance                Instance that will be used.
     * @param useAdditionalFormatting Indicates if additional formatting
     *                                should be used
     * @param language                Instance that contains the language that will be used.
     * @return String that contains the formatted instance.
     */
    public static <O> String format(O instance, boolean useAdditionalFormatting, Locale language) {
        if (instance != null)
            return format(instance, null, useAdditionalFormatting, NumberUtil.getDefaultPrecision(instance), language);

        return null;
    }

    /**
     * Formats an instance.
     *
     * @param <O>                     Class that defines the instance.
     * @param instance                Instance that will be used.
     * @param useAdditionalFormatting Indicates if additional formatting
     *                                should be used
     * @param precision               Numeric value that contains the decimal precision.
     * @param language                Instance that contains the language that will be used.
     * @return String that contains the formatted instance.
     */
    public static <O> String format(O instance, boolean useAdditionalFormatting, int precision, Locale language) {
        if (instance != null)
            return format(instance, null, useAdditionalFormatting, precision, language);

        return null;
    }

    /**
     * Formats an instance.
     *
     * @param <O>                     Class that defines the instance.
     * @param instance                Instance that will be used.
     * @param pattern                 String that contains the pattern that will be used.
     * @param useAdditionalFormatting Indicates if additional formatting
     *                                should be used
     * @param precision               Numeric value that contains the decimal precision.
     * @param language                Instance that contains the language that will be used.
     * @return String that contains the formatted instance.
     */
    public static <O> String format(O instance, String pattern, boolean useAdditionalFormatting, int precision, Locale language) {
        String result = null;

        if (language == null)
            language = LanguageUtil.getDefaultLanguage();

        if (isDate(instance)) {
            Date value = (Date) instance;

            if (pattern == null || pattern.isEmpty())
                result = DateTimeUtil.format(value, language);
            else
                result = DateTimeUtil.format(value, pattern);
        } else if (isNumber(instance)) {
            Number value = (Number) instance;

            result = NumberUtil.format(value, useAdditionalFormatting, precision, language);
        } else if (isByteArray(instance)) {
            byte[] value = (byte[]) instance;

            try {
                result = ByteUtil.toBase64(value);
            } catch (UnsupportedEncodingException ignored) {
            }
        } else {
            if (instance != null) {
                result = StringUtil.trim(instance);

                if (pattern != null && !pattern.isEmpty())
                    result = StringUtil.format(result, pattern);
            }
        }

        return result;
    }

    /**
     * Replaces the instance properties in a string. The string should be declared
     * like the following: <pre>#{&lt;property-name&gt;.&lt;child-property-name&gt;} E.g.: #{loginSession.user.name}</pre>
     *
     * @param <O>      Class that defines the instance.
     * @param instance Instance that will be used.
     * @param value    String before the processing.
     * @return String after the processing.
     */
    public static <O> String fillPropertiesInString(O instance, String value) {
        return fillPropertiesInString(instance, value, LanguageUtil.getDefaultLanguage());
    }

    /**
     * Replaces the instance properties in a string. The string should be declared
     * like the following: <pre>#{&lt;property-name&gt;.&lt;child-property-name&gt;} E.g.: #{loginSession.user.name}</pre>
     *
     * @param <O>      Class that defines the instance.
     * @param instance Instance that will be used.
     * @param value    String before the processing.
     * @param language Instance that contains the language that will be used.
     * @return String after the processing.
     */
    public static <O> String fillPropertiesInString(O instance, String value, Locale language) {
        return fillPropertiesInString(instance, value, true, language);
    }

    /**
     * Replaces the instance properties in a string. The string should be declared
     * like the following: <pre>#{&lt;property-name&gt;.&lt;child-property-name&gt;} E.g.: #{loginSession.user.name}</pre>
     *
     * @param <O>                    Class that defines the instance.
     * @param instance               Instance that will be used.
     * @param value                  String before the processing.
     * @param replaceNotFoundMatches Indicates if the properties that were not
     *                               found should be replaced with blank.
     * @param language               Instance that contains the language that will be used.
     * @return String after the processing.
     */
    public static <O> String fillPropertiesInString(O instance, String value, boolean replaceNotFoundMatches, Locale language) {
        return fillPropertiesInString(instance, value, replaceNotFoundMatches, false, language);
    }

    /**
     * Replaces the instance properties in a string. The string should be declared
     * like the following: <pre>#{&lt;property-name&gt;.&lt;child-property-name&gt;} E.g.: #{loginSession.user.name}</pre>
     *
     * @param <O>                    Class that defines the instance.
     * @param instance               Instance that will be used.
     * @param value                  String before the processing.
     * @param replaceNotFoundMatches Indicates if the properties that were not
     *                               found should be replaced with blank.
     * @param escapeCrlf             Indicates if it should escape CRLF in the replacements.
     * @param language               Instance that contains the language that will be used.
     * @return String after the processing.
     */
    public static <O> String fillPropertiesInString(O instance, String value, boolean replaceNotFoundMatches, boolean escapeCrlf, Locale language) {
        if (value != null && !value.isEmpty() && instance != null) {
            Pattern pattern = Pattern.compile("#\\{(.*?)(\\((.*?)\\))?}");
            Matcher matcher = pattern.matcher(value);
            ExpressionProcessor expressionProcessor = new ExpressionProcessor(instance, language);

            if (language == null)
                language = LanguageUtil.getDefaultLanguage();

            while (matcher.find()) {
                String propertyExpression = matcher.group();
                String propertyName = matcher.group(1);
                String propertyPattern = matcher.group(3);
                String propertyExpressionBuffer;

                if (propertyPattern != null && !propertyPattern.isEmpty()) {
                    propertyExpressionBuffer = StringUtil.replaceAll(propertyExpression, propertyPattern, "");
                    propertyExpressionBuffer = StringUtil.replaceAll(propertyExpressionBuffer, "(", "");
                    propertyExpressionBuffer = StringUtil.replaceAll(propertyExpressionBuffer, ")", "");
                } else
                    propertyExpressionBuffer = propertyExpression;

                try {
                    Object propertyValue = expressionProcessor.evaluate(propertyExpressionBuffer);

                    if (propertyValue != null || replaceNotFoundMatches) {
                        boolean useAdditionalFormatting = false;
                        int precision = 0;

                        if (propertyPattern == null || propertyPattern.isEmpty()) {
                            try {
                                PropertyInfo propertyInfo = getInfo(instance.getClass(), propertyName);

                                propertyPattern = propertyInfo.getPattern();

                                if (propertyInfo.isDate())
                                    useAdditionalFormatting = propertyInfo.isTime();
                                else if (propertyInfo.isNumber()) {
                                    useAdditionalFormatting = propertyInfo.useGroupSeparator();
                                    precision = propertyInfo.getPrecision();
                                }
                            } catch (NoSuchFieldException ignored) {
                            }
                        }

                        String buffer = null;

                        if (propertyValue != null) {
                            if (propertyPattern != null && !propertyPattern.isEmpty())
                                buffer = format(propertyValue, propertyPattern, language);
                            else
                                buffer = format(propertyValue, useAdditionalFormatting, precision, language);

                            if (escapeCrlf)
                                buffer = buffer.replaceAll(StringUtil.getLineBreak(), "\\\\n");
                        }

                        value = StringUtil.replaceAll(value, propertyExpression, buffer);
                    }
                } catch (IllegalArgumentException | NoSuchMethodException | IllegalAccessException |
                         InvocationTargetException | InstantiationException | ClassNotFoundException |
                         InternalErrorException ignored) {
                }
            }
        }

        return value;
    }

    /**
     * Replaces resources in a string. The string is declared like the
     * following: <pre>${&lt;property-name&gt;} E.g.: ${xyz}</pre>
     *
     * @param resources Instance that contains the resource.
     * @param value     String before the processing.
     * @return String after the processing.
     */
    public static String fillResourcesInString(PropertiesResources resources, String value) {
        String buffer = value;

        if (value != null && !value.isEmpty() && resources != null) {
            Pattern pattern = Pattern.compile("\\$\\{(.*?)}");
            Matcher matcher = pattern.matcher(value);

            while (matcher.find()) {
                String propertiesExpression = matcher.group();
                String propertiesName = matcher.group(1);
                String propertiesValue = resources.getProperty(propertiesName, false);

                buffer = StringUtil.replaceAll(buffer, propertiesExpression, propertiesValue);
                buffer = fillResourcesInString(resources, buffer);
            }
        }

        return buffer;
    }

    /**
     * Returns the generic type of class property.
     *
     * @param instanceClass Class that will be used.
     * @param name          String that contains the identifier of the property.
     * @return Class that defines the generic type.
     * @throws NoSuchFieldException Occurs when was not possible to execute the
     *                              operation.
     */
    public static Class<?> getGenericClass(Class<?> instanceClass, String name) throws NoSuchFieldException {
        if (instanceClass == null || name == null || name.isEmpty())
            return null;

        Field propertyField = getField(instanceClass, name);

        return getGenericClass(propertyField);
    }

    /**
     * Returns the generic type of class property.
     *
     * @param field Instance that contains the property attributes.
     * @return Class that defines the generic type.
     */
    public static Class<?> getGenericClass(Field field) {
        if (field == null)
            return null;

        Type genericType = field.getGenericType();
        Type type = field.getType();
        String typeName = field.getType().getName();
        Class<?> propertyClass;

        if (!genericType.equals(type)) {
            String buffer = StringUtil.replaceAll(genericType.toString(), typeName, "");

            buffer = StringUtil.replaceAll(buffer, "<", "");
            buffer = StringUtil.replaceAll(buffer, ">", "");
            buffer = StringUtil.replaceAll(buffer, "?", "");
            buffer = StringUtil.replaceAll(buffer, " extends ", "");

            try {
                propertyClass = Class.forName(buffer);
            } catch (ClassNotFoundException e) {
                propertyClass = Object.class;
            }
        } else
            propertyClass = Object.class;

        return propertyClass;
    }

    /**
     * Returns the instance that contains the property attributes.
     *
     * @param instanceClass Class that will be used.
     * @param name          String that contains the identifier of the property.
     * @return Instance that contains the property attributes.
     * @throws NoSuchFieldException Occurs when was not possible to execute the
     *                              operation.
     */
    public static Field getField(Class<?> instanceClass, String name) throws NoSuchFieldException {
        if (instanceClass == null || name == null || name.isEmpty())
            throw new NoSuchFieldException();

        String[] names = StringUtil.split(name, ".");
        Class<?> clazz = instanceClass;
        Field field = null;

        for (String nameItem : names) {
            while (clazz != null) {
                try {
                    field = clazz.getDeclaredField(nameItem);
                    clazz = field.getType();

                    break;
                } catch (NoSuchFieldException | SecurityException e) {
                    clazz = clazz.getSuperclass();
                }
            }
        }

        if (field == null)
            throw new NoSuchFieldException(name);

        return field;
    }

    /**
     * Returns the class of a property of a class
     *
     * @param instanceClass Class that defines the instance.
     * @param name          String that contains the identifier of the property.
     * @return Class that defines the property.
     * @throws ClassNotFoundException    Occurs when was not possible to execute
     *                                   the operation.
     * @throws InstantiationException    Occurs when was not possible to execute
     *                                   the operation.
     * @throws InvocationTargetException Occurs when was not possible to execute
     *                                   the operation.
     * @throws IllegalAccessException    Occurs when was not possible to execute
     *                                   the operation.
     * @throws NoSuchMethodException     Occurs when was not possible to execute the
     *                                   operation.
     * @throws IllegalArgumentException  Occurs when was not possible to execute
     *                                   the operation.
     * @throws NoSuchFieldException      Occurs when was not possible to execute the
     *                                   operation.
     */
    @SuppressWarnings("unchecked")
    public static Class<?> getClass(Class<?> instanceClass, String name) throws IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException, NoSuchFieldException {
        if (instanceClass == null || name == null || name.isEmpty())
            return null;

        Class<?> propertyClass = null;
        boolean isModel = isModel(instanceClass);

        if (isModel) {
            Class<? extends BaseModel> modelClass = (Class<? extends BaseModel>) instanceClass;
            ModelInfo modelInfo = ModelUtil.getInfo(modelClass);
            PropertyInfo propertyInfo = modelInfo.getPropertyInfo(name);

            propertyClass = propertyInfo.getClazz();
        }

        if (propertyClass != null)
            return getDefaultImplementation(propertyClass);

        Field propertyField = getField(instanceClass, name);

        return getClass(propertyField);
    }

    /**
     * Returns the class of a property.
     *
     * @param propertyField Instance that contains the property attributes.
     * @return Class that defines the property.
     */
    private static Class<?> getClass(Field propertyField) {
        if (propertyField == null)
            return null;

        Class<?> propertyClass = propertyField.getType();

        return getDefaultImplementation(propertyClass);
    }

    /**
     * Defines the value of a property
     *
     * @param instance Instance that will be used.
     * @param name     String that contains the identifier of the property.
     * @param value    Instance that contains the value of the property.
     * @throws InvocationTargetException Occurs when was not possible to execute
     *                                   the operation.
     */
    public static void setValue(Object instance, String name, Object value) throws InvocationTargetException {
        if (instance == null || name == null || name.isEmpty())
            return;

        String[] propertyNames = StringUtil.split(name, ".");
        String propertyName;
        Class<?> propertyClass;
        Object propertyValue = instance;
        Object propertyValueBuffer;

        try {
            for (int cont = 0; cont < (propertyNames.length - 1); cont++) {
                propertyName = propertyNames[cont];
                propertyClass = getClass(propertyValue.getClass(), propertyName);
                propertyValueBuffer = getValue(propertyValue, propertyName);

                if (propertyValueBuffer == null) {
                    propertyValueBuffer = ConstructorUtils.invokeConstructor(propertyClass, null);

                    PropertyUtils.setProperty(propertyValue, propertyName, propertyValueBuffer);
                }

                propertyValue = propertyValueBuffer;
            }

            propertyName = propertyNames[propertyNames.length - 1];
            propertyClass = getClass(propertyValue.getClass(), propertyName);

            if (value != null) {
                if (isNumber(propertyClass))
                    propertyValueBuffer = convertTo(value, propertyClass);
                else if (isTime(propertyClass))
                    propertyValueBuffer = new DateTime(((Date) value).getTime());
                else
                    propertyValueBuffer = value;
            } else
                propertyValueBuffer = null;

            PropertyUtils.setProperty(propertyValue, propertyName, propertyValueBuffer);
        } catch (IllegalAccessException | NoSuchMethodException | InstantiationException | IllegalArgumentException |
                 ClassNotFoundException | NoSuchFieldException e) {
            throw new InvocationTargetException(e);
        }
    }

    /**
     * Returns the value of a property.
     *
     * @param <O>      Instance that contains the value of the property.
     * @param instance Instance that will be used.
     * @param name     String that contains the identifier of the property.
     * @return Instance that contains the value of the property.
     * @throws IllegalAccessException    Occurs when was not possible to execute
     *                                   the operation.
     * @throws InvocationTargetException Occurs when was not possible to execute
     *                                   the operation.
     * @throws NoSuchMethodException     Occurs when was not possible to execute the
     *                                   operation.
     */
    @SuppressWarnings("unchecked")
    public static <O> O getValue(Object instance, String name) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        if (instance == null || name == null || name.isEmpty())
            return null;

        String[] propertyNames = StringUtil.split(name, ".");
        Object propertyValue = instance;

        for (String propertyName : propertyNames) {
            propertyValue = beanUtils.getProperty(propertyValue, propertyName);

            if (propertyValue == null) {
                Method method = MethodUtil.getAccessibleMethod(instance.getClass(), name, new Class[0]);

                if (method == null) {
                    String methodName = "get".concat(StringUtil.capitalize(name));

                    method = MethodUtil.getAccessibleMethod(instance.getClass(), methodName, new Class[0]);
                }

                if (method != null) {
                    propertyValue = method.invoke(instance);

                    if (propertyValue == null)
                        break;
                } else
                    break;
            }
        }

        return (O) propertyValue;
    }

    /**
     * Returns the instance that contains the property attributes.
     *
     * @param instanceClass Class that defines the instance.
     * @param name          String that contains the identifier of the property.
     * @return Instance that contains the property attributes.
     * @throws IllegalArgumentException  Occurs when was not possible to execute
     *                                   the operation.
     * @throws NoSuchMethodException     Occurs when was not possible to execute the
     *                                   operation.
     * @throws IllegalAccessException    Occurs when was not possible to execute
     *                                   the operation.
     * @throws InvocationTargetException Occurs when was not possible to execute
     *                                   the operation.
     * @throws InstantiationException    Occurs when was not possible to execute
     *                                   the operation.
     * @throws ClassNotFoundException    Occurs when was not possible to execute
     *                                   the operation.
     * @throws NoSuchFieldException      Occurs when was not possible to execute the
     *                                   operation.
     */
    public static PropertyInfo getInfo(Class<?> instanceClass, String name) throws IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException, NoSuchFieldException {
        if (instanceClass == null || name == null || name.isEmpty())
            throw new NoSuchFieldException();

        Field propertyField = getField(instanceClass, name);

        return getInfo(instanceClass, propertyField);
    }

    /**
     * Returns the instance that contains the property attributes.
     *
     * @param instanceClass Instance that contains the properties.
     * @param propertyField Instance that contains the property attributes.
     * @return Instance that contains the property attributes.
     * @throws NoSuchFieldException Occurs when was not possible to execute the
     *                              operation.
     */
    protected static PropertyInfo getInfo(Class<?> instanceClass, Field propertyField) throws NoSuchFieldException {
        if (instanceClass == null || propertyField == null)
            throw new NoSuchFieldException();

        Class<?> superClass = instanceClass;
        List<Property> mappedPropertiesList = null;
        Property propertyAnnotation = propertyField.getAnnotation(Property.class);

        if (propertyAnnotation != null) {
            mappedPropertiesList = PropertyUtil.instantiate(Constants.DEFAULT_LIST_CLASS);

            assert mappedPropertiesList != null;
            mappedPropertiesList.add(propertyAnnotation);
        }

        while (superClass != null && !superClass.equals(Object.class)) {
            Model modelAnnotation = superClass.getAnnotation(Model.class);

            if (modelAnnotation != null) {
                Property[] mappedProperties = modelAnnotation.mappedProperties();

                if (mappedProperties != null) {
                    for (Property mappedProperty : mappedProperties) {
                        if (mappedProperty.propertyId() != null && !mappedProperty.propertyId().isEmpty() && mappedProperty.propertyId().equals(propertyField.getName())) {
                            if (mappedPropertiesList == null)
                                mappedPropertiesList = PropertyUtil.instantiate(Constants.DEFAULT_LIST_CLASS);

                            if (mappedPropertiesList != null)
                                mappedPropertiesList.add(mappedProperty);
                        }
                    }
                }
            }

            superClass = superClass.getSuperclass();
        }

        PropertyInfo propertyInfo = new PropertyInfo();

        propertyInfo.setId(propertyField.getName());
        propertyInfo.setFromClass(propertyField.getDeclaringClass().equals(instanceClass));

        if (mappedPropertiesList != null && !mappedPropertiesList.isEmpty()) {
            for (Property mappedProperty : mappedPropertiesList) {
                if (propertyInfo.getPropertyTypeId() == null || propertyInfo.getPropertyTypeId().isEmpty())
                    propertyInfo.setPropertyTypeId(mappedProperty.propertyTypeId());

                if (!propertyInfo.isIdentity())
                    propertyInfo.setIsIdentity(mappedProperty.isIdentity());

                if (!propertyInfo.isUnique())
                    propertyInfo.setIsUnique(mappedProperty.isUnique());

                if (!propertyInfo.isSerializable())
                    propertyInfo.setIsSerializable(mappedProperty.isSerializable());

                if (propertyInfo.getSequenceId() == null || propertyInfo.getSequenceId().isEmpty())
                    propertyInfo.setSequenceId(mappedProperty.sequenceId());

                if (propertyInfo.getKeyId() == null || propertyInfo.getKeyId().isEmpty())
                    propertyInfo.setKeyId(mappedProperty.keyId());

                if (propertyInfo.getForeignKeyId() == null || propertyInfo.getForeignKeyId().isEmpty())
                    propertyInfo.setForeignKeyId(mappedProperty.foreignKeyId());

                if (!propertyInfo.isConstrained())
                    propertyInfo.setConstrained(mappedProperty.constrained());

                if (!propertyInfo.isNullable())
                    propertyInfo.setNullable(mappedProperty.nullable());

                if (!propertyInfo.isForSearch())
                    propertyInfo.setIsForSearch(mappedProperty.isForSearch());

                if (propertyInfo.getSearchCondition() == null || propertyInfo.getSearchCondition().equals(ConditionType.NONE))
                    propertyInfo.setSearchCondition(mappedProperty.searchCondition());

                if (propertyInfo.getSearchConditionOperation() == null || propertyInfo.getSearchConditionOperation().equals(ConditionOperationType.NONE))
                    propertyInfo.setSearchConditionOperation(mappedProperty.searchConditionOperation());

                if (propertyInfo.getSearchPropertyId() == null || propertyInfo.getSearchPropertyId().isEmpty())
                    propertyInfo.setSearchPropertyId(mappedProperty.searchPropertyId());

                if (propertyInfo.getSearchType() == null || propertyInfo.getSearchType().equals(SearchType.NONE))
                    propertyInfo.setSearchType(mappedProperty.searchType());

                if (propertyInfo.getValidations() == null || propertyInfo.getValidations().length == 0 || propertyInfo.getValidations()[0].equals(ValidationType.NONE))
                    propertyInfo.setValidations(mappedProperty.validations());

                if (propertyInfo.getValidationActions() == null || propertyInfo.getValidationActions().length == 0)
                    propertyInfo.setValidationActions(mappedProperty.validationActions());

                if (propertyInfo.getCustomValidationId() == null || propertyInfo.getCustomValidationId().isEmpty())
                    propertyInfo.setCustomValidationId(mappedProperty.customValidationId());

                if (propertyInfo.getSize() == 0)
                    propertyInfo.setSize(mappedProperty.size());

                if (propertyInfo.getMinimumLength() == 0)
                    propertyInfo.setMinimumLength(mappedProperty.minimumLength());

                if (propertyInfo.getMaximumLength() == 0)
                    propertyInfo.setMaximumLength(mappedProperty.maximumLength());

                if (propertyInfo.getMinimumValue() == null || propertyInfo.getMinimumValue().isEmpty())
                    propertyInfo.setMinimumValue(mappedProperty.minimumValue());

                if (propertyInfo.getMaximumValue() == null || propertyInfo.getMaximumValue().isEmpty())
                    propertyInfo.setMaximumValue(mappedProperty.maximumValue());

                if (propertyInfo.getPrecision() == 0)
                    propertyInfo.setPrecision(mappedProperty.precision());

                if (propertyInfo.getPattern() == null || propertyInfo.getPattern().isEmpty())
                    propertyInfo.setPattern(mappedProperty.pattern());

                if (propertyInfo.getRegularExpression() == null || propertyInfo.getRegularExpression().isEmpty())
                    propertyInfo.setRegularExpression(mappedProperty.regularExpression());

                if (!propertyInfo.persistPattern())
                    propertyInfo.setPersistPattern(mappedProperty.persistPattern());

                if (propertyInfo.getPhoneticPropertyId() == null || propertyInfo.getPhoneticPropertyId().isEmpty())
                    propertyInfo.setPhoneticPropertyId(mappedProperty.phoneticPropertyId());

                if (propertyInfo.getPropertiesIds() == null || propertyInfo.getPropertiesIds().length == 0)
                    propertyInfo.setPropertiesIds(mappedProperty.propertiesIds());

                if (propertyInfo.getMappedPropertyType() == null || propertyInfo.getMappedPropertyType().isEmpty())
                    propertyInfo.setMappedPropertyType(mappedProperty.mappedPropertyType());

                if (propertyInfo.getMappedPropertyId() == null || propertyInfo.getMappedPropertyId().isEmpty())
                    propertyInfo.setMappedPropertyId(mappedProperty.mappedPropertyId());

                if (propertyInfo.getRelationType() == null || propertyInfo.getRelationType() == RelationType.NONE)
                    propertyInfo.setRelationType(mappedProperty.relationType());

                if (propertyInfo.getRelationJoinType() == null || propertyInfo.getRelationJoinType() == RelationJoinType.NONE)
                    propertyInfo.setRelationJoinType(mappedProperty.relationJoinType());

                if (propertyInfo.getMappedPropertiesIds() == null || propertyInfo.getMappedPropertiesIds().length == 0)
                    propertyInfo.setMappedPropertiesIds(mappedProperty.mappedPropertiesIds());

                if (propertyInfo.getMappedRelationPropertiesIds() == null || propertyInfo.getMappedRelationPropertiesIds().length == 0)
                    propertyInfo.setMappedRelationPropertiesIds(mappedProperty.mappedRelationPropertiesIds());

                if (propertyInfo.getMappedRelationRepositoryId() == null || propertyInfo.getMappedRelationRepositoryId().isEmpty())
                    propertyInfo.setMappedRelationRepositoryId(mappedProperty.mappedRelationRepositoryId());

                if (!propertyInfo.cascadeOnSave())
                    propertyInfo.setCascadeOnSave(mappedProperty.cascadeOnSave());

                if (!propertyInfo.cascadeOnDelete())
                    propertyInfo.setCascadeOnDelete(mappedProperty.cascadeOnDelete());

                if (!propertyInfo.useGroupSeparator())
                    propertyInfo.setUseGroupSeparator(mappedProperty.useGroupSeparator());

                if (propertyInfo.getFormulaExpression() == null || propertyInfo.getFormulaExpression().isEmpty())
                    propertyInfo.setFormulaExpression(mappedProperty.formulaExpression());

                if (propertyInfo.getFormulaType() == null || propertyInfo.getFormulaType() == FormulaType.NONE)
                    propertyInfo.setFormulaType(mappedProperty.formulaType());

                if (propertyInfo.getSortOrder() == null || propertyInfo.getSortOrder() == SortOrderType.NONE)
                    propertyInfo.setSortOrder(mappedProperty.sortOrder());

                if (propertyInfo.getPhoneticAccuracy() == null || propertyInfo.getPhoneticAccuracy() == 0d)
                    propertyInfo.setPhoneticAccuracy(mappedProperty.phoneticAccuracy());

                if (propertyInfo.getCompareCondition() == null || propertyInfo.getCompareCondition().equals(ConditionType.NONE))
                    propertyInfo.setCompareCondition(mappedProperty.compareCondition());

                if (propertyInfo.getComparePropertyId() == null || propertyInfo.getComparePropertyId().isEmpty())
                    propertyInfo.setComparePropertyId(mappedProperty.comparePropertyId());

                if (propertyInfo.getWordCount() == 0)
                    propertyInfo.setWordCount(mappedProperty.wordCount());

                if (propertyInfo.getInputType() == null || propertyInfo.getInputType() == InputType.NONE)
                    propertyInfo.setInputType(mappedProperty.inputType());

                if (propertyInfo.getContentFilenamePropertyId() == null || propertyInfo.getContentFilenamePropertyId().isEmpty())
                    propertyInfo.setContentFilenamePropertyId(mappedProperty.contentFilenamePropertyId());

                if (propertyInfo.getContentTypePropertyId() == null || propertyInfo.getContentTypePropertyId().isEmpty())
                    propertyInfo.setContentTypePropertyId(mappedProperty.contentTypePropertyId());

                if (propertyInfo.getContentTypes() == null || propertyInfo.getContentTypes().length == 0 || propertyInfo.getContentTypes()[0] == ContentType.NONE)
                    propertyInfo.setContentTypePropertyId(mappedProperty.contentTypePropertyId());

                if (propertyInfo.getContentSize() == null || propertyInfo.getContentSize() == 0)
                    propertyInfo.setContentSize(mappedProperty.contentSize());

                if (propertyInfo.getContentSizeUnit() == null)
                    propertyInfo.setInputType(mappedProperty.inputType());

                if (propertyInfo.getDataset() == null || propertyInfo.getDataset().isEmpty())
                    propertyInfo.setDataset(mappedProperty.dataset());

                if (propertyInfo.getClazz() == null || propertyInfo.getClazz().equals(Object.class)) {
                    if (propertyInfo.getId().equals("parent"))
                        propertyInfo.setClazz(instanceClass);
                    else
                        propertyInfo.setClazz(mappedProperty.relationClass());
                }

                if (propertyInfo.getCollectionItemsClass() == null || propertyInfo.getCollectionItemsClass().equals(Object.class)) {
                    if (propertyInfo.getId().equals("children"))
                        propertyInfo.setCollectionItemsClass(instanceClass);
                    else
                        propertyInfo.setCollectionItemsClass(mappedProperty.relationCollectionItemsClass());
                }
            }
        }

        propertyInfo.setIsAuditable((propertyField.getAnnotation(Auditable.class) != null));

        if (propertyInfo.getClazz() == null || propertyInfo.getClazz().equals(Object.class))
            propertyInfo.setClazz(getClass(propertyField));

        if (propertyInfo.getCollectionItemsClass() == null || propertyInfo.getCollectionItemsClass().equals(Object.class))
            propertyInfo.setCollectionItemsClass(getGenericClass(propertyField));

        if (propertyInfo.getDataset() == null || propertyInfo.getDataset().isEmpty()) {
            if (propertyInfo.isCollection())
                propertyInfo.setDataset(propertyInfo.getId());
            else {
                if (propertyInfo.getId().endsWith("y"))
                    propertyInfo.setDataset(propertyInfo.getId().substring(0, propertyInfo.getId().length() - 1).concat("ies"));
                else
                    propertyInfo.setDataset(propertyInfo.getId().concat("s"));
            }
        }

        if (propertyInfo.getMappedPropertyType() == null || propertyInfo.getMappedPropertyType().isEmpty()) {
            if (propertyInfo.isEnum())
                propertyInfo.setMappedPropertyType(PersistenceConstants.DEFAULT_ENUM_TYPE_ID);
            else if (propertyInfo.isBoolean())
                propertyInfo.setMappedPropertyType(PersistenceConstants.DEFAULT_BOOLEAN_TYPE_ID);
            else if (propertyInfo.isByteArray())
                propertyInfo.setMappedPropertyType(PersistenceConstants.DEFAULT_BINARY_TYPE_ID);
            else if (propertyInfo.isDate()) {
                if (propertyInfo.isTime())
                    propertyInfo.setMappedPropertyType(PersistenceConstants.DEFAULT_DATE_TIME_TYPE_ID);
                else
                    propertyInfo.setMappedPropertyType(PersistenceConstants.DEFAULT_DATE_TYPE_ID);
            } else if (propertyInfo.isBigDecimal())
                propertyInfo.setMappedPropertyType(PersistenceConstants.DEFAULT_BIG_DECIMAL_TYPE_ID);
            else if (propertyInfo.isBigInteger())
                propertyInfo.setMappedPropertyType(PersistenceConstants.DEFAULT_BIG_INTEGER_TYPE_ID);
            else if (propertyInfo.isByte())
                propertyInfo.setMappedPropertyType(PersistenceConstants.DEFAULT_BYTE_TYPE_ID);
            else if (propertyInfo.isCurrency())
                propertyInfo.setMappedPropertyType(PersistenceConstants.DEFAULT_CURRENCY_TYPE_ID);
            else if (propertyInfo.isDouble())
                propertyInfo.setMappedPropertyType(PersistenceConstants.DEFAULT_DOUBLE_TYPE_ID);
            else if (propertyInfo.isFloat())
                propertyInfo.setMappedPropertyType(PersistenceConstants.DEFAULT_FLOAT_TYPE_ID);
            else if (propertyInfo.isInteger())
                propertyInfo.setMappedPropertyType(PersistenceConstants.DEFAULT_INTEGER_TYPE_ID);
            else if (propertyInfo.isLong())
                propertyInfo.setMappedPropertyType(PersistenceConstants.DEFAULT_LONG_TYPE_ID);
            else if (propertyInfo.isShort())
                propertyInfo.setMappedPropertyType(PersistenceConstants.DEFAULT_SHORT_TYPE_ID);
            else if (propertyInfo.isString())
                propertyInfo.setMappedPropertyType(PersistenceConstants.DEFAULT_STRING_TYPE_ID);
            else
                propertyInfo.setMappedPropertyType(PersistenceConstants.DEFAULT_OBJECT_TYPE_ID);
        }

        return propertyInfo;
    }

    /**
     * Returns the list that contains all properties' attributes of a class.
     *
     * @param <C>           Class that defines the list.
     * @param instanceClass Class that defines the instance.
     * @return List that contains all properties' attributes.
     * @throws InstantiationException    Occurs when was not possible to execute
     *                                   the operation.
     * @throws InvocationTargetException Occurs when was not possible to execute
     *                                   the operation.
     * @throws IllegalAccessException    Occurs when was not possible to execute
     *                                   the operation.
     * @throws NoSuchMethodException     Occurs when was not possible to execute the
     *                                   operation.
     * @throws NoSuchFieldException      Occurs when was not possible to execute the
     *                                   operation.
     */
    public static <C extends List<PropertyInfo>> C getInfos(Class<?> instanceClass) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        if (instanceClass == null)
            return null;

        C propertiesInfo = PropertyUtil.instantiate(Constants.DEFAULT_LIST_CLASS);
        Class<?> superClass = instanceClass;

        while (superClass != null) {
            Field[] propertyFields = superClass.getDeclaredFields();

            for (Field propertyField : propertyFields) {
                if (Modifier.isStatic(propertyField.getModifiers()))
                    continue;

                if (propertiesInfo != null)
                    propertiesInfo.add(getInfo(instanceClass, propertyField));
            }

            superClass = superClass.getSuperclass();
        }

        return propertiesInfo;
    }

    /**
     * Serialize an object.
     *
     * @param value Instance of the object.
     * @return String that contains the serialized object in JSON format.
     * @throws IOException Occurs when was not possible to serialize.
     */
    public static String serialize(Object value) throws IOException {
        if (value == null)
            return StringUtils.EMPTY;

        ObjectMapper mapper = getMapper();

        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(value);
    }

    /**
     * Deserialize an object.
     *
     * @param value String that contains the serialized object in JSON format.
     * @param clazz Class that defines the object.
     * @return Instance of the object.
     * @throws IOException Occurs when was not possible to deserialize.
     */
    public static <O> O deserialize(String value, Class<O> clazz) throws ClassNotFoundException, IOException {
        if (value == null || value.isEmpty())
            return null;

        return deserialize(value.getBytes(), clazz);
    }

    /**
     * Deserialize an object.
     *
     * @param value Byte array that contains the serialized object in JSON format.
     * @param clazz Class that defines the object.
     * @return Instance of the object.
     * @throws IOException Occurs when was not possible to deserialize.
     */
    @SuppressWarnings("unchecked")
    public static <O> O deserialize(byte[] value, Class<O> clazz) throws ClassNotFoundException, IOException {
        if (value == null || value.length == 0 || clazz == null)
            return null;

        ObjectMapper mapper = getMapper();
        JsonNode jsonNode = mapper.readValue(value, JsonNode.class);
        JsonNode rootClassName = jsonNode.get("class");

        if (rootClassName != null)
            clazz = (Class<O>) Class.forName(rootClassName.asText());

        return convertTo(jsonNode, clazz);
    }

    /**
     * Converts a value into another type.
     *
     * @param <O>   Class that defines the value type.
     * @param value Instance to be converted.
     * @param clazz Class that defines the value type.
     * @return Converted instance.
     */
    public static <O> O convertTo(Object value, Class<O> clazz) {
        ObjectMapper mapper = getMapper();

        return mapper.convertValue(value, clazz);
    }
}