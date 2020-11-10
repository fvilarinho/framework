package br.com.concepting.framework.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
public class SystemModuleTest{
    private SystemModuleModel model = new SystemModuleModel();
    
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
    public void url_Test(){
        model.setUrl(null);
        assertNull(model.getUrl());
        
        model.setUrl("test");
        
        assertNotNull(model.getUrl());
        assertEquals("test", model.getUrl());
    }
    @Test
    public void domain_Test(){
        model.setDomain(null);
        assertNull(model.getDomain());
        
        model.setDomain("test");
        
        assertNotNull(model.getDomain());
        assertEquals("test", model.getDomain());
    }
    @Test
    public void copyright_Test(){
        model.setCopyright(null);
        assertNull(model.getCopyright());
        
        model.setCopyright("test");
        
        assertNotNull(model.getCopyright());
        assertEquals("test", model.getCopyright());
    }
    @Test
    public void version_Test(){
        model.setVersion(null);
        assertNull(model.getVersion());
        
        model.setVersion("test");
        
        assertNotNull(model.getVersion());
        assertEquals("test", model.getVersion());
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
    public void logo_Test(){
        model.setLogo(null);
        assertNull(model.getLogo());
        
        model.setLogo(new byte[1]);
        
        assertNotNull(model.getLogo());
        assertEquals(1, model.getLogo().length);
    }
    @Test
    public void forms_Test(){
        model.setForms(null);
        assertNull(model.getForms());
        
        List<FormModel> values = new ArrayList<>();
        
        values.add(new FormModel());
        model.setForms(values);
        
        assertNotNull(model.getForms());
        assertEquals(1, model.getForms().size());
    }
    @Test
    public void exclusionUrls_Test(){
        model.setExclusionUrls(null);
        assertNull(model.getExclusionUrls());
        
        List<UrlModel> values = new ArrayList<>();
        
        values.add(new UrlModel());
        model.setExclusionUrls(values);
        
        assertNotNull(model.getExclusionUrls());
        assertEquals(1, model.getExclusionUrls().size());
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
        
        SystemModuleModel value = new SystemModuleModel();
        
        model.setParent(value);
        
        assertNotNull(model.getParent());
        assertEquals(value, model.getParent());
    }
    @Test
    public void children_Test(){
        model.setChildren(null);
        assertNull(model.getChildren());
        
        List<SystemModuleModel> values = new ArrayList<>();
        
        values.add(new SystemModuleModel());
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
