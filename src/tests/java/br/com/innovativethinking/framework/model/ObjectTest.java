package br.com.innovativethinking.framework.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import br.com.innovativethinking.framework.security.model.GroupModel;
import br.com.innovativethinking.framework.util.types.ComponentType;
public class ObjectTest{
    private ObjectModel model = new ObjectModel();
    
    @Test
    public void id_Test(){
        model.setId(null);
        assertNull(model.getId());
        
        model.setId(1l);
        
        assertNotNull(model.getId());
        assertTrue(model.getId().equals(1l));
    }
    @Test
    public void type_Test(){
        model.setType(null);
        assertNull(model.getType());
        
        ComponentType value = ComponentType.values()[0];
        
        model.setType(value);
        
        assertNotNull(model.getType());
        assertEquals(value, model.getType());
    }
    @Test
    public void form_Test(){
        model.setForm(null);
        assertNull(model.getForm());
        
        FormModel value = new FormModel();
        
        model.setForm(value);
        
        assertNotNull(model.getForm());
        assertEquals(value, model.getForm());
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
    public void title_Test(){
        model.setTitle(null);
        assertNull(model.getTitle());
        
        model.setTitle("test");
        
        assertNotNull(model.getTitle());
        assertEquals("test", model.getTitle());
    }
    @Test
    public void description_Test(){
        model.setDescription(null);
        assertNull(model.getDescription());
        
        model.setDescription("test");
        
        assertNotNull(model.getDescription());
        assertEquals("test", model.getDescription());
    }
    @Test
    public void tooltip_Test(){
        model.setTooltip(null);
        assertNull(model.getTooltip());
        
        model.setTooltip("test");
        
        assertNotNull(model.getTooltip());
        assertEquals("test", model.getTooltip());
    }
    @Test
    public void action_Test(){
        model.setAction(null);
        assertNull(model.getAction());
        
        model.setAction("test");
        
        assertNotNull(model.getAction());
        assertEquals("test", model.getAction());
    }
    @Test
    public void actionTarget_Test(){
        model.setActionTarget(null);
        assertNull(model.getActionTarget());
        
        model.setActionTarget("test");
        
        assertNotNull(model.getActionTarget());
        assertEquals("test", model.getActionTarget());
    }
    @Test
    public void sequence_Test(){
        model.setSequence(null);
        assertNull(model.getSequence());
        
        model.setSequence(1l);
        
        assertNotNull(model.getSequence());
        assertTrue(model.getSequence().equals(1l));
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
        
        ObjectModel value = new ObjectModel();
        
        model.setParent(value);
        
        assertNotNull(model.getParent());
        assertEquals(value, model.getParent());
    }
    @Test
    public void children_Test(){
        model.setChildren(null);
        assertNull(model.getChildren());
        
        List<ObjectModel> values = new ArrayList<>();
        
        values.add(new ObjectModel());
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
