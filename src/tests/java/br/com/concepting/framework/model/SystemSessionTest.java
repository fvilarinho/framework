package br.com.concepting.framework.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import br.com.concepting.framework.util.helpers.DateTime;
public class SystemSessionTest{
    private SystemSessionModel model = new SystemSessionModel();
    
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
    public void ip_Test(){
        model.setIp(null);
        assertNull(model.getIp());
        
        model.setIp("test");
        
        assertNotNull(model.getIp());
        assertEquals("test", model.getIp());
    }
    @Test
    public void hostName_Test(){
        model.setHostName(null);
        assertNull(model.getHostName());
        
        model.setHostName("test");
        
        assertNotNull(model.getHostName());
        assertEquals("test", model.getHostName());
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
        
        SystemSessionModel value = new SystemSessionModel();
        
        model.setParent(value);
        
        assertNotNull(model.getParent());
        assertEquals(value, model.getParent());
    }
    @Test
    public void children_Test(){
        model.setChildren(null);
        assertNull(model.getChildren());
        
        List<SystemSessionModel> values = new ArrayList<>();
        
        values.add(new SystemSessionModel());
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
