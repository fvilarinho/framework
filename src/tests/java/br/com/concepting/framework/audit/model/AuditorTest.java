package br.com.concepting.framework.audit.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import br.com.concepting.framework.security.model.LoginSessionModel;
import br.com.concepting.framework.util.helpers.DateTime;
public class AuditorTest{
    private AuditorModel model = new AuditorModel();
    
    @Test
    public void id_Test(){
        model.setId(null);
        assertNull(model.getId());
        
        model.setId(1l);
        
        assertNotNull(model.getId());
        assertTrue(model.getId().equals(1l));
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
    public void severity_Test(){
        model.setSeverity(null);
        assertNull(model.getSeverity());
        
        model.setSeverity("test");
        
        assertNotNull(model.getSeverity());
        assertEquals("test", model.getSeverity());
    }
    @Test
    public void loginSession_Test(){
        model.setLoginSession(null);
        assertNull(model.getLoginSession());
        
        LoginSessionModel value = new LoginSessionModel();
        
        model.setLoginSession(value);
        
        assertNotNull(model.getLoginSession());
        assertEquals(value, model.getLoginSession());
    }
    @Test
    public void entityId_Test(){
        model.setEntityId(null);
        assertNull(model.getEntityId());
        
        model.setEntityId("test");
        
        assertNotNull(model.getEntityId());
        assertEquals("test", model.getEntityId());
    }
    @Test
    public void businessId_Test(){
        model.setBusinessId(null);
        assertNull(model.getBusinessId());
        
        model.setBusinessId("test");
        
        assertNotNull(model.getBusinessId());
        assertEquals("test", model.getBusinessId());
    }
    @Test
    public void businessComplement_Test(){
        model.setBusinessComplement(null);
        assertNull(model.getBusinessComplement());
        
        List<AuditorComplementModel> values = new ArrayList<>();
        
        values.add(new AuditorComplementModel());
        model.setBusinessComplement(values);
        
        assertNotNull(model.getBusinessComplement());
        assertEquals(1, model.getBusinessComplement().size());
    }
    @Test
    public void message_Test(){
        model.setMessage(null);
        assertNull(model.getMessage());
        
        model.setMessage("test");
        
        assertNotNull(model.getMessage());
        assertEquals("test", model.getMessage());
    }
    @Test
    public void responseTime_Test(){
        model.setResponseTime(null);
        assertNull(model.getResponseTime());
        
        model.setResponseTime(1l);
        
        assertNotNull(model.getResponseTime());
        assertTrue(model.getResponseTime().equals(1l));
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
        
        AuditorModel value = new AuditorModel();
        
        model.setParent(value);
        
        assertNotNull(model.getParent());
        assertEquals(value, model.getParent());
    }
    @Test
    public void children_Test(){
        model.setChildren(null);
        assertNull(model.getChildren());
        
        List<AuditorModel> values = new ArrayList<>();
        
        values.add(new AuditorModel());
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
