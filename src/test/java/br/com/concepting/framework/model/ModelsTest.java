package br.com.concepting.framework.model;

import br.com.concepting.framework.security.model.*;
import br.com.concepting.framework.util.helpers.DateTime;
import br.com.concepting.framework.util.types.ComponentType;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

public class ModelsTest {
    private static final Logger log = LoggerFactory.getLogger(ModelsTest.class);

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

    @Test
    public void testAccessModel() {
        AccessModel accessModel = new AccessModel();

        accessModel.setId(1);

        UrlModel urlModel = new UrlModel();

        urlModel.setId(1);
        urlModel.setPath("/test");

        accessModel.setUrl(urlModel);
        accessModel.setBlocked(true);

        assertEquals(1, accessModel.getId(), 0);
        assertNotNull(accessModel.getUrl());
        assertEquals("/test", accessModel.getUrl().getPath());
        assertEquals(Boolean.TRUE, accessModel.getBlocked());
    }

    @Test
    public void testLoginParameterModel() {
        DateTime now = new DateTime();
        LoginParameterModel loginParameterModel = new LoginParameterModel();

        loginParameterModel.setId(1);
        loginParameterModel.setCurrentLoginTries(1);
        loginParameterModel.setLoginTries(1);
        loginParameterModel.setMultipleLogins(true);
        loginParameterModel.setChangePassword(true);
        loginParameterModel.setPasswordWillExpire(true);
        loginParameterModel.setUseStrongPassword(true);
        loginParameterModel.setMinimumPasswordLength(1);
        loginParameterModel.setChangePasswordInterval(1);
        loginParameterModel.setExpirePasswordInterval(1);
        loginParameterModel.setExpirePasswordDateTime(now);
        loginParameterModel.setDaysUntilExpire(1);
        loginParameterModel.setHoursUntilExpire(1);
        loginParameterModel.setMinutesUntilExpire(1);
        loginParameterModel.setSecondsUntilExpire(1);
        loginParameterModel.setReceiveSms(true);
        loginParameterModel.setReceiveIm(true);
        loginParameterModel.setReceiveVoipCall(true);
        loginParameterModel.setMfa(true);
        loginParameterModel.setMfaTokenValidated(true);
        loginParameterModel.setMfaToken("testMfaToken");
        loginParameterModel.setNotificationToken("testNotificationToken");
        loginParameterModel.setSkin("test");
        loginParameterModel.setLanguage("pt_BR");

        assertEquals(1, loginParameterModel.getId(), 0);
        assertEquals(1, loginParameterModel.getCurrentLoginTries(), 0);
        assertEquals(1, loginParameterModel.getLoginTries(), 0);
        assertEquals(Boolean.TRUE, loginParameterModel.getMultipleLogins());
        assertEquals(Boolean.TRUE, loginParameterModel.getChangePassword());
        assertEquals(Boolean.TRUE, loginParameterModel.getPasswordWillExpire());
        assertEquals(Boolean.TRUE, loginParameterModel.getUseStrongPassword());
        assertEquals(1, loginParameterModel.getMinimumPasswordLength(), 0);
        assertEquals(now, loginParameterModel.getExpirePasswordDateTime());
        assertEquals(1, loginParameterModel.getChangePasswordInterval(), 0);
        assertEquals(1, loginParameterModel.getExpirePasswordInterval(), 0);
        assertEquals(1, loginParameterModel.getDaysUntilExpire(), 0);
        assertEquals(1, loginParameterModel.getHoursUntilExpire(), 0);
        assertEquals(1, loginParameterModel.getMinutesUntilExpire(), 0);
        assertEquals(1, loginParameterModel.getSecondsUntilExpire(), 0);
        assertEquals(Boolean.TRUE, loginParameterModel.getReceiveSms());
        assertEquals(Boolean.TRUE, loginParameterModel.getReceiveIm());
        assertEquals(Boolean.TRUE, loginParameterModel.getReceiveVoipCall());
        assertEquals(Boolean.TRUE, loginParameterModel.getMfa());
        assertEquals(Boolean.TRUE, loginParameterModel.getMfaTokenValidated());
        assertEquals("testMfaToken", loginParameterModel.getMfaToken());
        assertEquals("testNotificationToken", loginParameterModel.getNotificationToken());
        assertEquals("test", loginParameterModel.getSkin());
        assertEquals("pt_BR", loginParameterModel.getLanguage());
    }

    @Test
    public void testLoginSessionModel() {
        DateTime now = new DateTime();
        LoginSessionModel loginSessionModel = new LoginSessionModel();

        loginSessionModel.setId("abc123");
        loginSessionModel.setStartDateTime(now);
        loginSessionModel.setActive(true);
        loginSessionModel.setRememberUserAndPassword(true);

        UserModel user = new UserModel();

        user.setId(1);

        loginSessionModel.setUser(user);

        SystemSessionModel systemSessionModel = new SystemSessionModel();

        systemSessionModel.setId("abc123");

        loginSessionModel.setSystemSession(systemSessionModel);

        SystemModuleModel systemModuleModel = new SystemModuleModel();

        systemModuleModel.setId(1);

        loginSessionModel.setSystemModule(systemModuleModel);

        assertEquals("abc123", loginSessionModel.getId());
        assertEquals(now, loginSessionModel.getStartDateTime());
        assertEquals(Boolean.TRUE, loginSessionModel.getActive());
        assertEquals(Boolean.TRUE, loginSessionModel.getRememberUserAndPassword());
        assertNotNull(loginSessionModel.getUser());
        assertEquals(1, loginSessionModel.getUser().getId(), 0);
        assertNotNull(loginSessionModel.getSystemSession());
        assertEquals("abc123", loginSessionModel.getSystemSession().getId());
        assertNotNull(loginSessionModel.getSystemModule());
        assertEquals(1, loginSessionModel.getSystemModule().getId(), 0);
    }

    @Test
    public void testUserModel() {
        DateTime now = new DateTime();
        UserModel userModel = new UserModel();

        userModel.setId(1);
        userModel.setName("johndoe");
        userModel.setFullName("John Doe");
        userModel.setPhoneNumber("123456789");
        userModel.setEmail("johndoe@localhost");
        userModel.setLogo(new byte[256]);
        userModel.setPassword("123456");
        userModel.setNewPassword("78901");
        userModel.setConfirmPassword("78901");
        userModel.setMfaToken("testMfaToken");
        userModel.setCreation(now);
        userModel.setLastLogin(now);
        userModel.setLastUpdate(now);
        userModel.setSuperUser(true);
        userModel.setActive(true);
        userModel.setSystem(false);

        LoginParameterModel loginParameterModel = new LoginParameterModel();

        loginParameterModel.setId(1);

        userModel.setLoginParameter(loginParameterModel);

        assertEquals(1, userModel.getId(), 0);
        assertEquals("johndoe", userModel.getName());
        assertEquals("John Doe", userModel.getFullName());
        assertEquals("123456789", userModel.getPhoneNumber());
        assertEquals("johndoe@localhost", userModel.getEmail());
        assertEquals(256, userModel.getLogo().length);
        assertEquals("123456", userModel.getPassword());
        assertEquals("78901", userModel.getNewPassword());
        assertEquals("78901", userModel.getConfirmPassword());
        assertEquals("testMfaToken", userModel.getMfaToken());
        assertEquals(now, userModel.getCreation());
        assertEquals(now, userModel.getLastLogin());
        assertEquals(now, userModel.getLastUpdate());
        assertEquals(Boolean.TRUE, userModel.getSuperUser());
        assertEquals(Boolean.TRUE, userModel.getActive());
        assertEquals(Boolean.FALSE, userModel.getSystem());
        assertNotNull(userModel.getLoginParameter());
        assertEquals(loginParameterModel, userModel.getLoginParameter());

        //Test all permissions' possibilities.
        assertTrue(userModel.hasPermissions());
        assertTrue(userModel.hasPermission("/test"));
        assertTrue(userModel.hasPermission((String)null));
        assertTrue(userModel.hasPermission(new ObjectModel()));
        assertTrue(userModel.hasPermission((ObjectModel) null));
        assertTrue(userModel.hasPermission(new SystemModuleModel()));
        assertTrue(userModel.hasPermission((SystemModuleModel) null));

        userModel.setGroups(new ArrayList<>());

        assertTrue(userModel.hasPermissions());
        assertTrue(userModel.hasPermission("/test"));
        assertTrue(userModel.hasPermission((String)null));
        assertTrue(userModel.hasPermission(new ObjectModel()));
        assertTrue(userModel.hasPermission((ObjectModel) null));
        assertTrue(userModel.hasPermission(new SystemModuleModel()));
        assertTrue(userModel.hasPermission((SystemModuleModel) null));

        GroupModel groupModel = new GroupModel();

        groupModel.setId(1);

        userModel.setGroups(Arrays.asList(groupModel));

        assertNotNull(userModel.getGroups());
        assertEquals(1, userModel.getGroups().size());
        assertFalse(userModel.hasPermissions());
        assertTrue(userModel.hasPermission("/test"));
        assertTrue(userModel.hasPermission((String)null));
        assertTrue(userModel.hasPermission(new ObjectModel()));
        assertTrue(userModel.hasPermission((ObjectModel) null));
        assertTrue(userModel.hasPermission(new SystemModuleModel()));
        assertTrue(userModel.hasPermission((SystemModuleModel) null));

        groupModel.setAccesses(new ArrayList<>());

        assertFalse(userModel.hasPermissions());
        assertTrue(userModel.hasPermission("/test"));
        assertTrue(userModel.hasPermission((String) null));

        AccessModel accessModel = new AccessModel();

        accessModel.setId(1);

        UrlModel urlModel = new UrlModel();

        urlModel.setId(1);
        urlModel.setPath("/test");

        accessModel.setUrl(urlModel);
        accessModel.setBlocked(true);

        groupModel.setAccesses(Arrays.asList(accessModel));

        assertTrue(userModel.hasPermissions());
        assertFalse(userModel.hasPermission("/test"));
        assertTrue(userModel.hasPermission((String)null));

        accessModel.setBlocked(false);

        assertTrue(userModel.hasPermission("/test"));
        assertFalse(userModel.hasPermission("/test2"));

        groupModel.setObjects(new ArrayList<>());

        assertTrue(userModel.hasPermissions());
        assertFalse(userModel.hasPermission(new ObjectModel()));
        assertTrue(userModel.hasPermission((ObjectModel) null));
        assertTrue(userModel.hasPermission(new SystemModuleModel()));
        assertTrue(userModel.hasPermission((SystemModuleModel)null));

        ObjectModel objectModel = new ObjectModel();

        objectModel.setId(1l);

        groupModel.setObjects(Arrays.asList(objectModel));

        assertTrue(userModel.hasPermissions());
        assertTrue(userModel.hasPermission(objectModel));
        assertFalse(userModel.hasPermission(new ObjectModel()));
        assertTrue(userModel.hasPermission((ObjectModel) null));
        assertFalse(userModel.hasPermission(new SystemModuleModel()));
        assertTrue(userModel.hasPermission((SystemModuleModel) null));

        FormModel formModel = new FormModel();

        formModel.setId(1);

        SystemModuleModel systemModuleModel = new SystemModuleModel();

        systemModuleModel.setId(1);

        formModel.setSystemModule(systemModuleModel);

        objectModel.setForm(formModel);

        assertTrue(userModel.hasPermission(systemModuleModel));
        assertFalse(userModel.hasPermission(new ObjectModel()));
        assertTrue(userModel.hasPermission((ObjectModel) null));
        assertFalse(userModel.hasPermission(new SystemModuleModel()));
        assertTrue(userModel.hasPermission((SystemModuleModel) null));
    }

    @Test
    public void testGroupModel() {
        GroupModel groupModel = new GroupModel();

        groupModel.setId(1);
        groupModel.setName("testGroup");
        groupModel.setDescription("This is a test group");
        groupModel.setTitle("Test Group");
        groupModel.setAccesses(new ArrayList<>());
        groupModel.setUsers(new ArrayList<>());
        groupModel.setObjects(new ArrayList<>());

        assertEquals(1, groupModel.getId(), 0);
        assertEquals("testGroup", groupModel.getName());
        assertEquals("This is a test group", groupModel.getDescription());
        assertEquals("Test Group", groupModel.getTitle());
        assertNotNull(groupModel.getAccesses());
        assertTrue(groupModel.getAccesses().isEmpty());
        assertNotNull(groupModel.getUsers());
        assertTrue(groupModel.getUsers().isEmpty());
        assertNotNull(groupModel.getObjects());
        assertTrue(groupModel.getObjects().isEmpty());
    }
}