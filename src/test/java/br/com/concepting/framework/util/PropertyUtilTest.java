package br.com.concepting.framework.util;

import br.com.concepting.framework.model.TestModel;
import org.junit.Test;

import static org.junit.Assert.*;

public class PropertyUtilTest {
    @Test
    public void testSetValue() {
        TestModel model = new TestModel();

        try {
            PropertyUtil.setValue(model, "testField", "This is a test!");

            assertEquals("This is a test!", PropertyUtil.getValue(model, "testField"));

            assertNull(PropertyUtil.getValue(null, null));
            assertNull(PropertyUtil.getValue(null, ""));
            assertNull(PropertyUtil.getValue(null, "testField"));
            assertNull(PropertyUtil.getValue(model, null));
            assertNull(PropertyUtil.getValue(model, ""));
        }
        catch(Throwable e) {
            fail(e.getMessage());
        }
    }
}
