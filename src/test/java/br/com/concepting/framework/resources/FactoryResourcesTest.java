package br.com.concepting.framework.resources;

import br.com.concepting.framework.persistence.constants.PersistenceConstants;
import br.com.concepting.framework.persistence.resources.PersistenceFactoryResourcesLoader;
import br.com.concepting.framework.resources.constants.FactoryConstants;
import br.com.concepting.framework.resources.exceptions.InvalidResourcesException;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class FactoryResourcesTest {
    @Test
    public void testFactoryResourcesGettersAndSetters() {
        FactoryResources resources = new FactoryResources();

        resources.setId("test");
        resources.setType("Test");
        resources.setClazz("br.com.concepting.framework.resources.TestFactoryResources");
        resources.addOption("option1", "value1");

        assertEquals("test", resources.getId());
        assertEquals("Test", resources.getType());
        assertEquals("br.com.concepting.framework.resources.TestFactoryResources", resources.getClazz());
        assertNotNull(resources.getOptions());
        assertEquals(1, resources.getOptions().size());
        assertEquals("value1", resources.getOptions().get("option1"));

        Map<String, String> options = new LinkedHashMap<>();

        options.put("option1", "value1");

        resources.setOptions(options);

        assertEquals(options, resources.getOptions());
    }

    @Test
    public void testValidPersistenceFactory() {
        try {
            PersistenceFactoryResourcesLoader loader = new PersistenceFactoryResourcesLoader();
            FactoryResources resources = loader.getDefault();

            assertEquals("h2", resources.getId());
            assertEquals("H2", resources.getType());
            assertEquals("org.h2.Driver", resources.getClazz());
            assertEquals("jdbc:h2:mem:#{repositoryId}", resources.getUri());
            assertNull(resources.getOptions());
        }
        catch(InvalidResourcesException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testInvalidPersistenceFactory() {
        try {
            new PersistenceFactoryResourcesLoader("invalidDirectory");

            fail("Should throw InvalidResourcesException");
        }
        catch(InvalidResourcesException e) {
            assertEquals("invalidDirectory", e.getResourcesDirname());
            assertEquals(FactoryConstants.DEFAULT_RESOURCES_ID, e.getResourcesId());
            assertTrue(e.getMessage().contains("No such file or directory"));
        }
    }
}
