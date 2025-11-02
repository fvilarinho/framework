package br.com.concepting.framework.model.types;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ModelsTypesTest {
    @Test
    public void testValidationTypes() {
        assertEquals("contentType", ValidationType.CONTENT_TYPE.getId());
        assertEquals("contentSize", ValidationType.CONTENT_SIZE.getId());
        assertEquals("dateTime", ValidationType.DATE_TIME.getId());
        assertEquals("maximumLength", ValidationType.MAXIMUM_LENGTH.getId());
        assertEquals("minimumLength", ValidationType.MINIMUM_LENGTH.getId());
        assertEquals("strongPassword", ValidationType.STRONG_PASSWORD.getId());
        assertEquals("wordCount", ValidationType.WORD_COUNT.getId());
        assertEquals("regularExpression", ValidationType.REGULAR_EXPRESSION.getId());
        assertEquals("pattern", ValidationType.PATTERN.getId());
        assertEquals("required", ValidationType.REQUIRED.getId());
        assertEquals("compare", ValidationType.COMPARE.getId());
        assertEquals("number", ValidationType.NUMBER.getId());
        assertEquals("email", ValidationType.EMAIL.getId());
        assertEquals("range", ValidationType.RANGE.getId());
        assertEquals("custom", ValidationType.CUSTOM.getId());
        assertEquals("none", ValidationType.NONE.getId());
    }

    @Test
    public void testConditionTypes() {
        assertEquals("=", ConditionType.EQUAL.getOperator());
        assertEquals(">=", ConditionType.GREATER_THAN_EQUAL.getOperator());
        assertEquals(">", ConditionType.GREATER_THAN.getOperator());
        assertEquals("<=", ConditionType.LESS_THAN_EQUAL.getOperator());
        assertEquals("<", ConditionType.LESS_THAN.getOperator());
        assertEquals("like", ConditionType.STARTS_WITH.getOperator());
        assertEquals("like", ConditionType.ENDS_WITH.getOperator());
        assertEquals("like", ConditionType.CONTAINS.getOperator());
        assertEquals("not like", ConditionType.NOT_CONTAINS.getOperator());
        assertEquals("in", ConditionType.IN.getOperator());
        assertEquals("not in", ConditionType.NOT_IN.getOperator());
        assertEquals("between", ConditionType.BETWEEN.getOperator());
        assertEquals("is null", ConditionType.IS_NULL.getOperator());
        assertEquals("is not null", ConditionType.IS_NOT_NULL.getOperator());
        assertEquals("", ConditionType.PHONETIC.getOperator());
    }
}
