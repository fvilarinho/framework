package br.com.concepting.framework.util;

import br.com.concepting.framework.model.TestModel;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CollectionUtilTest {
    @Test
    public void testClone() {
        List<TestModel> source = new ArrayList<>();

        TestModel obj1 = new TestModel("value1");

        source.add(obj1);

        TestModel obj2 = new TestModel("value2");

        source.add(obj2);

        TestModel obj3 = new TestModel("value3");

        source.add(obj3);

        try {
            List<TestModel> clone = CollectionUtil.clone(source);

            assertEquals(clone.size(), source.size());
            assertEquals(obj1, clone.get(0));
            assertEquals(obj2, clone.get(1));
            assertEquals(obj3, clone.get(2));
            assertEquals("value1", clone.get(0).getTestField());
            assertEquals("value2", clone.get(1).getTestField());
            assertEquals("value3", clone.get(2).getTestField());

            assertNull(CollectionUtil.clone(null));
        }
        catch(Throwable e) {
            fail(e.getMessage());
        }
    }
}
