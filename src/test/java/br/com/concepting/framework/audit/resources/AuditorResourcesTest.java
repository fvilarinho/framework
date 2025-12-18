package br.com.concepting.framework.audit.resources;

import br.com.concepting.framework.audit.appenders.ConsoleAuditorAppender;
import br.com.concepting.framework.audit.constants.AuditorConstants;
import br.com.concepting.framework.resources.exceptions.InvalidResourcesException;
import org.junit.Test;

import static org.junit.Assert.*;

public class AuditorResourcesTest {
    @Test
    public void testValidAuditorResources() {
        try {
            AuditorResourcesLoader loader = new AuditorResourcesLoader();
            AuditorResources resources = loader.getDefault();

            assertNotNull(resources);
            assertEquals("default", resources.getId());
            assertTrue(resources.isDefault());
            assertEquals("DEBUG", resources.getLevel());
            assertNotNull(resources.getAppenders());
            assertEquals(1, resources.getAppenders().size());
            assertEquals(ConsoleAuditorAppender.class.getName(), resources.getAppenders().iterator().next().getClazz());
        }
        catch(InvalidResourcesException e){
            fail(e.getMessage());
        }
    }

    @Test
    public void testInvalidAuditorResources() {
        try {
            new AuditorResourcesLoader("invalidDirectory");

            fail("Should throw InvalidResourcesException!");
        }
        catch(InvalidResourcesException e){
            assertEquals("invalidDirectory", e.getResourcesDirname());
            assertEquals(AuditorConstants.DEFAULT_RESOURCES_ID, e.getResourcesId());
            assertTrue(e.getMessage().contains("No such file or directory"));
        }
    }
}
