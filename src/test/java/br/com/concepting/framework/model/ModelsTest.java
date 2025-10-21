package br.com.concepting.framework.model;

import br.com.concepting.framework.security.model.GroupModel;
import br.com.concepting.framework.util.helpers.DateTime;
import br.com.concepting.framework.util.types.ComponentType;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

public class ModelsTest {
    @Test
    public void testFormModelGettersAndSetters() {
        SystemModuleModel systemModuleModel = new SystemModuleModel();

        systemModuleModel.setId(1);
        systemModuleModel.setName("testModule");

        ObjectModel objectModel = new ObjectModel();

        objectModel.setId(1L);
        objectModel.setName("testObject");

        Collection<ObjectModel> objectModels = List.of(objectModel);

        FormModel formModel = new FormModel();

        formModel.setId(1);
        formModel.setName("testForm");
        formModel.setDescription("This is a test form");
        formModel.setTitle("Test Form");

        assertNull(formModel.getObject("testObject"));

        formModel.setObjects(objectModels);
        formModel.setSystemModule(systemModuleModel);

        assertEquals(Integer.valueOf(1), formModel.getId());
        assertEquals("testForm", formModel.getName());
        assertEquals("This is a test form", formModel.getDescription());
        assertEquals("Test Form", formModel.getTitle());
        assertEquals(objectModels, formModel.getObjects());
        assertEquals(systemModuleModel, formModel.getSystemModule());
        assertEquals(objectModel, formModel.getObject("testObject"));

        assertNull(formModel.getObject("testObject2"));
    }

    @Test
    public void testUrlModelGettersAndSetters() {
        UrlModel urlModel = new UrlModel();

        urlModel.setId(1);
        urlModel.setPath(("/test/path"));

        assertEquals(Integer.valueOf(1), urlModel.getId());
        assertEquals("/test/path", urlModel.getPath());
    }

    @Test
    public void testObjectModelGettersAndSetters() {
        FormModel formModel = new FormModel();

        formModel.setId(1);

        GroupModel groupModel = new GroupModel();

        groupModel.setId(1);

        Collection<GroupModel> groups = List.of(groupModel);

        ObjectModel objectModel = new ObjectModel();

        objectModel.setId(1L);
        objectModel.setName("testObject");
        objectModel.setTitle("Test Object");
        objectModel.setDescription("This is a test object");
        objectModel.setTooltip("This is a test object");
        objectModel.setAction("testAction");
        objectModel.setActionTarget("testTarget");
        objectModel.setSequence(1L);
        objectModel.setType(ComponentType.BUTTON);
        objectModel.setForm(formModel);
        objectModel.setGroups(groups);

        assertEquals(Long.valueOf(1L), objectModel.getId());
        assertEquals("testObject", objectModel.getName());
        assertEquals("Test Object", objectModel.getTitle());
        assertEquals("This is a test object", objectModel.getDescription());
        assertEquals("This is a test object", objectModel.getTooltip());
        assertEquals("testAction", objectModel.getAction());
        assertEquals("testTarget", objectModel.getActionTarget());
        assertEquals(Long.valueOf(1L), objectModel.getSequence());
        assertEquals(ComponentType.BUTTON, objectModel.getType());
        assertEquals(formModel, objectModel.getForm());
        assertEquals(groups, objectModel.getGroups());
    }

    @Test
    public void testSystemModuleModelGettersAndSetters() {
        SystemModuleModel systemModuleModel = new SystemModuleModel();

        systemModuleModel.setId(1);
        systemModuleModel.setName("testModule");
        systemModuleModel.setTitle("Test Module");
        systemModuleModel.setDescription("This is a test module");
        systemModuleModel.setLogo(new byte[256]);
        systemModuleModel.setActive(true);
        systemModuleModel.setDomain("testDomain");
        systemModuleModel.setUrl("/test/url");
        systemModuleModel.setCopyright("All rights reserved");
        systemModuleModel.setVersion("1.0.0");

        assertEquals(Integer.valueOf(1), systemModuleModel.getId());
        assertEquals("testModule", systemModuleModel.getName());
        assertEquals("Test Module", systemModuleModel.getTitle());
        assertEquals("This is a test module", systemModuleModel.getDescription());
        assertEquals(256, systemModuleModel.getLogo().length);
        assertEquals(Boolean.TRUE, systemModuleModel.isActive());
        assertEquals("/test/url", systemModuleModel.getUrl());
        assertEquals("testDomain", systemModuleModel.getDomain());
        assertEquals("All rights reserved", systemModuleModel.getCopyright());
        assertEquals("1.0.0", systemModuleModel.getVersion());

        FormModel formModel = new FormModel();

        formModel.setId(1);
        formModel.setName("testForm");

        systemModuleModel.setForm(formModel);

        assertNull(systemModuleModel.getForm("testForm"));

        Collection<FormModel> forms = Arrays.asList(formModel);

        systemModuleModel.setForms(forms);

        assertNull(systemModuleModel.getForm("testForm2"));
        assertNull(systemModuleModel.getForm(null));
        assertNull(systemModuleModel.getForm(""));

        assertEquals(forms, systemModuleModel.getForms());
        assertEquals(formModel, systemModuleModel.getForm("testForm"));

        FormModel formModel2 = new FormModel();

        formModel2.setId(2);
        formModel2.setName("testForm2");

        systemModuleModel.setForm(formModel2);

        assertNull(systemModuleModel.getForm("testForm2"));

        formModel2.setId(1);

        systemModuleModel.setForm(formModel2);

        assertEquals(formModel2, systemModuleModel.getForm("testForm2"));

        UrlModel urlModel = new UrlModel();

        urlModel.setId(1);
        urlModel.setPath("/test/path");

        Collection<UrlModel> exclusionUrls = Arrays.asList(urlModel);

        systemModuleModel.setExclusionUrls(exclusionUrls);

        assertEquals(exclusionUrls, systemModuleModel.getExclusionUrls());
    }

    @Test
    public void testSystemSessionModelGettersAndSetters() {
        DateTime now = new DateTime();
        SystemSessionModel systemSessionModel = new SystemSessionModel();

        systemSessionModel.setId("abc123");
        systemSessionModel.setStartDateTime(now);
        systemSessionModel.setIp("127.0.0.1");
        systemSessionModel.setHostName("localhost");

        assertEquals("abc123", systemSessionModel.getId());
        assertEquals(now, systemSessionModel.getStartDateTime());
        assertEquals("127.0.0.1", systemSessionModel.getIp());
        assertEquals("localhost", systemSessionModel.getHostName());
    }

    @Test
    public void testMainConsoleModelGettersAndSetters() {
        MainConsoleModel mainConsoleModel = new MainConsoleModel() {};

        mainConsoleModel.setCurrentLanguage("pt_BR");
        mainConsoleModel.setCurrentSkin("dark");

        assertEquals("pt_BR", mainConsoleModel.getCurrentLanguage());
        assertEquals("dark", mainConsoleModel.getCurrentSkin());
    }

    @Test
    public void testBaseModel() {
        PhonebookModel phonebookModel = new PhonebookModel();

        phonebookModel.setName("Luke");
        phonebookModel.setPhone("123456789");

        assertEquals("Luke - 123456789", phonebookModel.toString());

        TestModel anotherModel = new TestModel();

        assertTrue(anotherModel.toString().startsWith(TestModel.class.getName()));
        assertNotEquals(new PhonebookModel(), phonebookModel);
    }
}