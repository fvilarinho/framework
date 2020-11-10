package br.com.concepting.framework.audit.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
public class AuditorComplementTest{
    private AuditorComplementModel model = new AuditorComplementModel();
    
    @Test
    public void id_Test(){
        model.setId(null);
        assertNull(model.getId());
        
        model.setId(1);
        
        assertNotNull(model.getId());
        assertTrue(model.getId().equals(1));
    }
    @Test
    public void auditor_Test(){
        model.setAuditor(null);
        assertNull(model.getAuditor());
        
        AuditorModel value = new AuditorModel();
        
        model.setAuditor(value);
        
        assertNotNull(model.getAuditor());
        assertEquals(value, model.getAuditor());
    }
    @Test
    public void type_Test(){
        model.setType(null);
        assertNull(model.getType());
        
        model.setType("test");
        
        assertNotNull(model.getType());
        assertEquals("test", model.getType());
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
    public void value_Test(){
        model.setValue(null);
        assertNull(model.getValue());
        
        model.setValue(new Object());
        
        assertNotNull(model.getValue());
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
        
        AuditorComplementModel value = new AuditorComplementModel();
        
        model.setParent(value);
        
        assertNotNull(model.getParent());
        assertEquals(value, model.getParent());
    }
    @Test
    public void children_Test(){
        model.setChildren(null);
        assertNull(model.getChildren());
        
        List<AuditorComplementModel> values = new ArrayList<>();
        
        values.add(new AuditorComplementModel());
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
