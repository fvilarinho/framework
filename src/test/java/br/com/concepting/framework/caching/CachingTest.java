package br.com.concepting.framework.caching;

import br.com.concepting.framework.caching.constants.CacherConstants;
import br.com.concepting.framework.model.TestModel;
import br.com.concepting.framework.model.exceptions.ItemAlreadyExistsException;
import br.com.concepting.framework.model.exceptions.ItemNotFoundException;
import br.com.concepting.framework.util.types.DateFieldType;
import org.junit.Test;

import static org.junit.Assert.*;

public class CachingTest {
    @Test
    public void testConstructorsGettersAndSetters() {
        CachedObject<String> object = new CachedObject<>();

        object.setId("message");
        object.setContent("This is a test");

        assertEquals("message", object.getId());
        assertEquals("This is a test", object.getContent());
        assertFalse(object.isCached());
        assertNull(object.getCacheDate());
        assertNull(object.getLastAccess());

        CacherManager cacherManager = CacherManager.getInstance();
        Cacher<String> cacher = cacherManager.getCacher("customTest", 2L);

        assertEquals("customTest", cacher.getId());
        assertEquals(2L, cacher.getTimeout());
        assertEquals(CacherConstants.DEFAULT_TIMEOUT_TYPE, cacher.getTimeoutType());

        cacher = cacherManager.getCacher("test");

        assertEquals("test", cacher.getId());
        assertEquals(CacherConstants.DEFAULT_TIMEOUT, cacher.getTimeout());
        assertEquals(CacherConstants.DEFAULT_TIMEOUT_TYPE, cacher.getTimeoutType());

        try {
            cacher.add(null);
            cacher.add(object);

            assertEquals(1, cacher.getSize());
            assertTrue(object.isCached());
            assertNotNull(object.getCacheDate());
        }
        catch(ItemAlreadyExistsException e){
            fail(e.getMessage());
        }

        try {
            object = cacher.get("message");

            assertNotNull(object.getLastAccess());
        }
        catch(ItemNotFoundException e){
            fail(e.getMessage());
        }
    }

    @Test
    public void testExpire() {
        CacherManager cacherManager = CacherManager.getInstance();
        Cacher<String> cacher = cacherManager.getCacher("anotherTest", 1L, DateFieldType.SECONDS);

        cacher.clear();

        CachedObject<String> object = new CachedObject<>();

        object.setId("message");
        object.setContent("This is a test");

        try {
            cacher.add(object);
        }
        catch(ItemAlreadyExistsException e){
            fail(e.getMessage());
        }

        try {
            Thread.sleep(2000L);
        }
        catch(InterruptedException e){
            fail(e.getMessage());
        }

        try {
            object = cacher.get("message");

            fail("The item 'message' should have been expired at this point!");
        }
        catch(ItemNotFoundException ignored){
        }

        try {
            cacher.add(object);
        }
        catch(ItemAlreadyExistsException e){
            fail(e.getMessage());
        }

        cacher.expire();

        try {
            cacher.get("message");

            fail("The item 'message' should have been expired at this point!");
        }
        catch(ItemNotFoundException ignored){
        }
    }

    @Test
    public void testUpdateAndRemove() {
        CacherManager cacherManager = CacherManager.getInstance();
        Cacher<String> cacher = cacherManager.getCacher("oneMoreTest", 1L, DateFieldType.SECONDS);

        try {
            cacher.set(null);
            cacher.remove(null);
        }
        catch(ItemNotFoundException e){
            fail(e.getMessage());
        }

        CachedObject<String> object = new CachedObject<>();

        object.setId("message");
        object.setContent("This is a test");

        try {
            cacher.add(object);
            cacher.set(object);
        }
        catch(ItemAlreadyExistsException | ItemNotFoundException e){
            fail(e.getMessage());
        }

        CachedObject<String> newObject = new CachedObject<>();

        newObject.setId("message");
        newObject.setContent("This is a new test");

        try {
            cacher.set(newObject);

            object = cacher.get("message");

            assertEquals("This is a new test", object.getContent());
        }
        catch(ItemNotFoundException e){
            fail(e.getMessage());
        }

        try {
            cacher.remove(object);
        }
        catch(ItemNotFoundException e){
            fail(e.getMessage());
        }

        try {
            cacher.remove(object);

            fail("The item 'message' should have been removed at this point");
        }
        catch(ItemNotFoundException ignored){
        }

        try {
            cacher.set(object);

            fail("The item 'message' should have been removed at this point");
        }
        catch(ItemNotFoundException ignored){
        }

        try {
            cacher.get("message");

            fail("The item 'message' should have been removed at this point");
        }
        catch(ItemNotFoundException e){
            assertEquals(0, cacher.getSize());
        }
    }

    @Test
    public void testDefaultCacher() {
        CacherManager cacherManager = CacherManager.getInstance();
        Cacher<String> cacher = cacherManager.getDefaultCacher();

        assertEquals(Cacher.class.getName(), cacher.getId());
    }

    @Test
    public void testInvalidCacher() {
        CacherManager cacherManager = CacherManager.getInstance();
        Cacher<String> cacher = cacherManager.getCacher((Class<?>)null);

        assertNull(cacher);

        cacher = cacherManager.getCacher((Class<?>)null, null);

        assertNull(cacher);

        cacher = cacherManager.getCacher((Class<?>)null, null, null);

        assertNull(cacher);
    }

    @Test
    public void testCustomCacher() {
        CacherManager cacherManager = CacherManager.getInstance();
        Cacher<TestModel> cacher = cacherManager.getCacher(TestModel.class, 1L);

        assertEquals(1L, cacher.getTimeout());
        assertEquals(DateFieldType.HOURS, cacher.getTimeoutType());

        cacher = cacherManager.getCacher(TestModel.class, 2L, DateFieldType.MINUTES);

        assertEquals(2L, cacher.getTimeout());
        assertEquals(DateFieldType.MINUTES, cacher.getTimeoutType());
    }
}
