package br.com.concepting.framework.util;

import br.com.concepting.framework.model.TestModel;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class MethodUtilTest {
    @Test
    public void testGetMethod() {
        Method method = MethodUtil.getMethod(TestModel.class, "getTestField");

        assertNotNull(method);
        assertEquals("getTestField", method.getName());

        method = MethodUtil.getMethod(TestModel.class, "nonExistentMethod");

        assertNull(method);

        try {
            method = MethodUtil.getMethodFromStackTrace(1);

            assertNotNull(method);
            assertEquals("testGetMethod", method.getName());
        }
        catch(Throwable e){
            fail(e.getMessage());
        }

        try {
            assertNull(MethodUtil.getMethodFromStackTrace(0));
        }
        catch(Throwable e){
            fail(e.getMessage());
        }
    }
}
