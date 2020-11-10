package br.com.concepting.framework.security.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import br.com.concepting.framework.model.ObjectModel;
public class GroupTest{
    private GroupModel model = new GroupModel();
    
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
    public void users_Test(){
        model.setUsers(null);
        assertNull(model.getUsers());
        
        List<UserModel> values = new ArrayList<>();
        
        values.add(new UserModel());
        model.setUsers(values);
        
        assertNotNull(model.getUsers());
        assertEquals(1, model.getUsers().size());
    }
    @Test
    public void objects_Test(){
        model.setObjects(null);
        assertNull(model.getObjects());
        
        List<ObjectModel> values = new ArrayList<>();
        
        values.add(new ObjectModel());
        model.setObjects(values);
        
        assertNotNull(model.getObjects());
        assertEquals(1, model.getObjects().size());
    }
    @Test
    public void accesses_Test(){
        model.setAccesses(null);
        assertNull(model.getAccesses());
        
        List<AccessModel> values = new ArrayList<>();
        
        values.add(new AccessModel());
        model.setAccesses(values);
        
        assertNotNull(model.getAccesses());
        assertEquals(1, model.getAccesses().size());
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
        
        GroupModel value = new GroupModel();
        
        model.setParent(value);
        
        assertNotNull(model.getParent());
        assertEquals(value, model.getParent());
    }
    @Test
    public void children_Test(){
        model.setChildren(null);
        assertNull(model.getChildren());
        
        List<GroupModel> values = new ArrayList<>();
        
        values.add(new GroupModel());
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
