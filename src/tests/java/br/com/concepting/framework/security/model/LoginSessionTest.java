package br.com.concepting.framework.security.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import br.com.concepting.framework.model.SystemModuleModel;
import br.com.concepting.framework.model.SystemSessionModel;
import br.com.concepting.framework.util.helpers.DateTime;
public class LoginSessionTest{
    private LoginSessionModel model = new LoginSessionModel();
    
    @Test
    public void id_Test(){
        model.setId(null);
        assertNull(model.getId());
        
        model.setId("test");
        
        assertNotNull(model.getId());
        assertEquals("test", model.getId());
    }
    @Test
    public void startDateTime_Test(){
        model.setStartDateTime(null);
        assertNull(model.getStartDateTime());
        
        DateTime dateTime = new DateTime();
        
        model.setStartDateTime(dateTime);
        
        assertNotNull(model.getStartDateTime());
        assertEquals(dateTime, model.getStartDateTime());
    }
    @Test
    public void systemSession_Test(){
        model.setSystemSession(null);
        assertNull(model.getSystemSession());
        
        SystemSessionModel value = new SystemSessionModel();
        
        model.setSystemSession(value);
        
        assertNotNull(model.getSystemSession());
        assertEquals(value, model.getSystemSession());
    }
    @Test
    public void systemModule_Test(){
        model.setSystemModule(null);
        assertNull(model.getSystemModule());
        
        SystemModuleModel value = new SystemModuleModel();
        
        model.setSystemModule(value);
        
        assertNotNull(model.getSystemModule());
        assertEquals(value, model.getSystemModule());
    }
    @Test
    public void user_Test(){
        model.setUser(null);
        assertNull(model.getUser());
        
        UserModel value = new UserModel();
        
        model.setUser(value);
        
        assertNotNull(model.getUser());
        assertEquals(value, model.getUser());
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
    public void rememberUserAndPassword_Test(){
        model.setRememberUserAndPassword(null);
        assertNull(model.getRememberUserAndPassword());
        
        model.setRememberUserAndPassword(true);
        
        assertNotNull(model.getRememberUserAndPassword());
        assertEquals(true, model.getRememberUserAndPassword());
        assertEquals(true, model.rememberUserAndPassword());
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
        
        LoginSessionModel value = new LoginSessionModel();
        
        model.setParent(value);
        
        assertNotNull(model.getParent());
        assertEquals(value, model.getParent());
    }
    @Test
    public void children_Test(){
        model.setChildren(null);
        assertNull(model.getChildren());
        
        List<LoginSessionModel> values = new ArrayList<>();
        
        values.add(new LoginSessionModel());
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
