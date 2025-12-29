package br.com.concepting.framework.security.model;

import br.com.concepting.framework.model.*;
import br.com.concepting.framework.util.helpers.DateTime;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class ModelsTest {
    @Test
    public void testAccessModelGettersAndSetters() {
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
    public void testLoginParameterModelGettersAndSetters() {
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
    public void testLoginSessionModelGettersAndSetters() {
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
    public void testUserModelGettersAndSetters() {
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

        GroupModel groupModel = new GroupModel();

        groupModel.setId(1);

        userModel.setGroups(Arrays.asList(groupModel));

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
        assertNotNull(userModel.getGroups());
        assertEquals(1, userModel.getGroups().size());
    }

    @Test
    public void testUserModelRestrictions() {
        UserModel userModel = new UserModel();

        userModel.setId(1);

        // Must return false when there are no restrictions.
        assertFalse(userModel.hasRestrictions());

        userModel.setGroups(new ArrayList<>());

        assertFalse(userModel.hasRestrictions());

        userModel.setGroups(Arrays.asList((GroupModel)null));

        assertFalse(userModel.hasRestrictions());

        GroupModel groupModel = new GroupModel();

        groupModel.setId(1);

        userModel.setGroups(Arrays.asList(groupModel));

        assertFalse(userModel.hasRestrictions());

        groupModel.setObjects(new ArrayList<>());

        assertFalse(userModel.hasRestrictions());

        groupModel.setAccesses(new ArrayList<>());

        assertFalse(userModel.hasRestrictions());
    }

    @Test
    public void testUserModelHasAccessToAnUrl() {
        UserModel userModel = new UserModel();

        userModel.setId(1);

        assertTrue(userModel.hasPermission("/test"));

        userModel.setGroups(new ArrayList<>());

        assertTrue(userModel.hasPermission("/test"));

        userModel.setGroups(Arrays.asList((GroupModel)null));

        assertTrue(userModel.hasPermission("/test"));

        GroupModel groupModel = new GroupModel();

        groupModel.setId(1);

        userModel.setGroups(Arrays.asList(groupModel));

        AccessModel accessModel = new AccessModel();

        accessModel.setId(1);

        UrlModel urlModel = new UrlModel();

        urlModel.setId(1);
        urlModel.setPath("/test");

        accessModel.setUrl(urlModel);
        accessModel.setBlocked(true);

        groupModel.setAccesses(Arrays.asList(accessModel));

        assertTrue(userModel.hasRestrictions());
        assertFalse(userModel.hasPermission("/test"));
        assertFalse(userModel.hasPermission("/test2"));

        accessModel.setBlocked(false);

        assertTrue(userModel.hasPermission("/test"));
    }

    @Test
    public void testUserModelHasAccessToAnObject() {
        UserModel userModel = new UserModel();

        userModel.setId(1);

        assertTrue(userModel.hasPermission(new ObjectModel()));
        assertTrue(userModel.hasPermission((ObjectModel) null));

        userModel.setGroups(new ArrayList<>());

        assertTrue(userModel.hasPermission(new ObjectModel()));

        userModel.setGroups(Arrays.asList((GroupModel) null));

        assertTrue(userModel.hasPermission(new ObjectModel()));

        GroupModel groupModel = new GroupModel();

        groupModel.setId(1);

        userModel.setGroups(Arrays.asList(groupModel));

        assertTrue(userModel.hasPermission((ObjectModel) null));

        ObjectModel objectModel = new ObjectModel();

        objectModel.setId(1L);

        groupModel.setObjects(Arrays.asList(objectModel));

        assertTrue(userModel.hasPermission(objectModel));

        ObjectModel objectModel2 = new ObjectModel();

        objectModel2.setId(2L);

        assertFalse(userModel.hasPermission(objectModel2));
    }

    @Test
    public void testUserModelHasAccessToASystemModule() {
        UserModel userModel = new UserModel();

        userModel.setId(1);

        assertTrue(userModel.hasPermission(new SystemModuleModel()));
        assertTrue(userModel.hasPermission((SystemModuleModel) null));

        userModel.setGroups(new ArrayList<>());

        assertTrue(userModel.hasPermission(new SystemModuleModel()));

        userModel.setGroups(Arrays.asList((GroupModel) null));

        assertTrue(userModel.hasPermission(new SystemModuleModel()));

        GroupModel groupModel = new GroupModel();

        groupModel.setId(1);

        userModel.setGroups(Arrays.asList(groupModel));

        assertTrue(userModel.hasPermission((SystemModuleModel) null));

        FormModel formModel = new FormModel();

        formModel.setId(1);

        SystemModuleModel systemModuleModel = new SystemModuleModel();

        systemModuleModel.setId(1);

        formModel.setSystemModule(systemModuleModel);

        ObjectModel objectModel = new ObjectModel();

        objectModel.setId(1L);
        objectModel.setForm(formModel);

        groupModel.setObjects(Arrays.asList(objectModel));

        assertTrue(userModel.hasPermission(systemModuleModel));

        SystemModuleModel systemModuleModel2 = new SystemModuleModel();

        systemModuleModel2.setId(2);

        assertFalse(userModel.hasPermission(systemModuleModel2));
    }

    @Test
    public void testGroupModelGettersAndSetters() {
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

    @Test
    public void testGroupModelRestrictions() {
        GroupModel groupModel = new GroupModel();

        groupModel.setId(1);

        assertFalse(groupModel.hasRestrictions());

        groupModel.setObjects(new ArrayList<>());

        assertFalse(groupModel.hasRestrictions());

        groupModel.setObjects(Arrays.asList((ObjectModel)null));

        assertTrue(groupModel.hasRestrictions());

        groupModel.setObjects(null);
        groupModel.setAccesses(new ArrayList<>());

        assertFalse(groupModel.hasRestrictions());

        groupModel.setAccesses(Arrays.asList((AccessModel) null));

        assertTrue(groupModel.hasRestrictions());
    }

    @Test
    public void testGroupModelHasAccessToAnUrl() {
        GroupModel groupModel = new GroupModel();

        groupModel.setId(1);

        assertTrue(groupModel.hasPermission("/test"));

        groupModel.setAccesses(new ArrayList<>());

        assertTrue(groupModel.hasPermission("/test"));

        groupModel.setAccesses(Arrays.asList((AccessModel)null));

        assertTrue(groupModel.hasPermission("/test"));

        AccessModel accessModel = new AccessModel();

        accessModel.setId(1);

        UrlModel urlModel = new UrlModel();

        urlModel.setId(1);
        urlModel.setPath("/test");

        accessModel.setUrl(urlModel);
        accessModel.setBlocked(true);

        groupModel.setAccesses(Arrays.asList(accessModel));

        assertFalse(groupModel.hasPermission("/test"));

        accessModel.setBlocked(false);

        assertTrue(groupModel.hasPermission("/test"));
        assertFalse(groupModel.hasPermission("/test2"));
    }

    @Test
    public void testGroupModelHasAccessToAnObject() {
        GroupModel groupModel = new GroupModel();

        groupModel.setId(1);

        assertFalse(groupModel.hasPermission(new ObjectModel()));

        groupModel.setObjects(new ArrayList<>());

        assertFalse(groupModel.hasPermission(new ObjectModel()));

        groupModel.setObjects(Arrays.asList((ObjectModel)null));

        assertFalse(groupModel.hasPermission(new ObjectModel()));

        ObjectModel objectModel = new ObjectModel();

        objectModel.setId(1L);

        groupModel.setObjects(Arrays.asList(objectModel));

        assertTrue(groupModel.hasPermission(objectModel));
    }

    @Test
    public void testGroupModelHasAccessToASystemModule() {
        GroupModel groupModel = new GroupModel();

        groupModel.setId(1);

        assertTrue(groupModel.hasPermission(new SystemModuleModel()));

        groupModel.setObjects(new ArrayList<>());

        assertTrue(groupModel.hasPermission(new SystemModuleModel()));

        groupModel.setObjects(Arrays.asList((ObjectModel)null));

        assertTrue(groupModel.hasPermission(new SystemModuleModel()));
        assertTrue(groupModel.hasPermission((SystemModuleModel) null));

        ObjectModel objectModel = new ObjectModel();

        objectModel.setId(1L);

        groupModel.setObjects(Arrays.asList(objectModel));

        assertFalse(groupModel.hasPermission(new SystemModuleModel()));

        FormModel formModel = new FormModel();

        formModel.setId(1);

        objectModel.setForm(formModel);

        assertFalse(groupModel.hasPermission(new SystemModuleModel()));

        SystemModuleModel systemModuleModel = new SystemModuleModel();

        systemModuleModel.setId(1);

        assertFalse(groupModel.hasPermission(systemModuleModel));

        formModel.setSystemModule(systemModuleModel);

        assertTrue(groupModel.hasPermission(systemModuleModel));

        SystemModuleModel systemModuleModel2 = new SystemModuleModel();

        systemModuleModel2.setId(2);

        assertFalse(groupModel.hasPermission(systemModuleModel2));
    }
}