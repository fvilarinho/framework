package br.com.concepting.framework.security.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import br.com.concepting.framework.util.helpers.DateTime;
public class UserTest{
    private UserModel model = new UserModel();
    
    @Test
    public void id_Test(){
        model.setId(null);
        assertNull(model.getId());
        
        model.setId(1);
        
        assertNotNull(model.getId());
        assertTrue(model.getId().equals(1));
    }
    @Test
    public void name_Test(){
        model.setName(null);
        assertNull(model.getName());
        
        model.setName("test");
        
        assertNotNull(model.getName());
        assertEquals("test", model.getName());
    }
    @Test
    public void fullName_Test(){
        model.setFullName(null);
        assertNull(model.getFullName());
        
        model.setFullName("test");
        
        assertNotNull(model.getFullName());
        assertEquals("test", model.getFullName());
    }
    @Test
    public void password_Test(){
        model.setPassword(null);
        assertNull(model.getPassword());
        
        model.setPassword("test");
        
        assertNotNull(model.getPassword());
        assertEquals("test", model.getPassword());
    }
    @Test
    public void newPassword_Test(){
        model.setNewPassword(null);
        assertNull(model.getNewPassword());
        
        model.setNewPassword("test");
        
        assertNotNull(model.getNewPassword());
        assertEquals("test", model.getNewPassword());
    }
    @Test
    public void confirmPassword_Test(){
        model.setConfirmPassword(null);
        assertNull(model.getConfirmPassword());
        
        model.setConfirmPassword("test");
        
        assertNotNull(model.getConfirmPassword());
        assertEquals("test", model.getConfirmPassword());
    }
    @Test
    public void mfaToken_Test(){
        model.setMfaToken(null);
        assertNull(model.getMfaToken());
        
        model.setMfaToken("test");
        
        assertNotNull(model.getMfaToken());
        assertEquals("test", model.getMfaToken());
    }
    @Test
    public void email_Test(){
        model.setEmail(null);
        assertNull(model.getEmail());
        
        model.setEmail("test");
        
        assertNotNull(model.getEmail());
        assertEquals("test", model.getEmail());
    }
    @Test
    public void phoneNumber_Test(){
        model.setPhoneNumber(null);
        assertNull(model.getPhoneNumber());
        
        model.setPhoneNumber("test");
        
        assertNotNull(model.getPhoneNumber());
        assertEquals("test", model.getPhoneNumber());
    }
    @Test
    public void creation_Test(){
        model.setCreation(null);
        assertNull(model.getCreation());
        
        DateTime dateTime = new DateTime();
        
        model.setCreation(dateTime);
        
        assertNotNull(model.getCreation());
        assertEquals(dateTime, model.getCreation());
    }
    @Test
    public void lastUpdate_Test(){
        model.setLastUpdate(null);
        assertNull(model.getLastUpdate());
        
        DateTime dateTime = new DateTime();
        
        model.setLastUpdate(dateTime);
        
        assertNotNull(model.getLastUpdate());
        assertEquals(dateTime, model.getLastUpdate());
    }
    @Test
    public void lastLogin_Test(){
        model.setLastLogin(null);
        assertNull(model.getLastLogin());
        
        DateTime dateTime = new DateTime();
        
        model.setLastLogin(dateTime);
        
        assertNotNull(model.getLastLogin());
        assertEquals(dateTime, model.getLastLogin());
    }
    @Test
    public void active_Test(){
        model.setActive(null);
        assertNull(model.getActive());
        
        model.setActive(true);
        
        assertNotNull(model.getActive());
        assertEquals(true, model.getActive());
        assertEquals(true, model.isActive());
    }
    @Test
    public void superUser_Test(){
        model.setSuperUser(null);
        assertNull(model.getSuperUser());
        
        model.setSuperUser(true);
        
        assertNotNull(model.getSuperUser());
        assertEquals(true, model.getSuperUser());
        assertEquals(true, model.isSuperUser());
    }
    @Test
    public void logo_Test(){
        model.setLogo(null);
        assertNull(model.getLogo());
        
        model.setLogo(new byte[1]);
        
        assertNotNull(model.getLogo());
        assertEquals(1, model.getLogo().length);
    }
    @Test
    public void loginParameter_Test(){
        model.setLoginParameter(null);
        assertNull(model.getLoginParameter());
        
        LoginParameterModel value = new LoginParameterModel();
        
        model.setLoginParameter(value);
        
        assertNotNull(model.getLoginParameter());
        assertEquals(value, model.getLoginParameter());
    }
    @Test
    public void groups_Test(){
        model.setGroups(null);
        assertNull(model.getGroups());
        
        List<GroupModel> values = new ArrayList<>();
        
        values.add(new GroupModel());
        model.setGroups(values);
        
        assertNotNull(model.getGroups());
        assertEquals(1, model.getGroups().size());
    }
    @Test
    public void compareAccuracy_Test(){
        model.setCompareAccuracy(null);
        assertNull(model.getCompareAccuracy());
        
        model.setCompareAccuracy(1d);
        
        assertNotNull(model.getCompareAccuracy());
        assertTrue(model.getCompareAccuracy().equals(1d));
    }
    @Test
    public void sortPropertyId_Test(){
        model.setSortPropertyId(null);
        assertNull(model.getSortPropertyId());
        
        model.setSortPropertyId("test");
        
        assertNotNull(model.getSortPropertyId());
        assertEquals("test", model.getSortPropertyId());
    }
    @Test
    public void parent_Test(){
        model.setParent(null);
        assertNull(model.getParent());
        
        UserModel value = new UserModel();
        
        model.setParent(value);
        
        assertNotNull(model.getParent());
        assertEquals(value, model.getParent());
    }
    @Test
    public void children_Test(){
        model.setChildren(null);
        assertNull(model.getChildren());
        
        List<UserModel> values = new ArrayList<>();
        
        values.add(new UserModel());
        model.setChildren(values);
        
        assertNotNull(model.getChildren());
        assertEquals(1, model.getChildren().size());
    }
    @Test
    public void index_Test(){
        model.setIndex(null);
        assertNull(model.getIndex());
        
        model.setIndex(1);
        
        assertNotNull(model.getIndex());
        assertTrue(model.getIndex().equals(1));
    }
    @Test
    public void level_Test(){
        model.setLevel(null);
        assertNull(model.getLevel());
        
        model.setLevel(1);
        
        assertNotNull(model.getLevel());
        assertTrue(model.getLevel().equals(1));
    }
}
