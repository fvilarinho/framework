package br.com.concepting.framework.audit.model;

import br.com.concepting.framework.security.model.LoginSessionModel;
import br.com.concepting.framework.util.helpers.DateTime;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ModelsTest {
    @Test
    public void testAuditorModelGettersAndSetters() {
        DateTime now = new DateTime();
        AuditorModel auditorModel = new AuditorModel();

        auditorModel.setId(1L);
        auditorModel.setStartDateTime(now);
        auditorModel.setEntityId("entity");
        auditorModel.setBusinessId("business");
        auditorModel.setSeverity("INFO");
        auditorModel.setMessage("message");
        auditorModel.setResponseTime(1000L);

        LoginSessionModel loginSessionModel = new LoginSessionModel();

        loginSessionModel.setId("abc123");

        auditorModel.setLoginSession(loginSessionModel);

        AuditorComplementModel businessComplementModel = new AuditorComplementModel();

        businessComplementModel.setId(1L);

        auditorModel.setBusinessComplement(Arrays.asList(businessComplementModel));

        assertEquals(1L, auditorModel.getId(), 0);
        assertEquals(now, auditorModel.getStartDateTime());
        assertEquals("entity", auditorModel.getEntityId());
        assertEquals("business", auditorModel.getBusinessId());
        assertEquals("INFO", auditorModel.getSeverity());
        assertEquals("message", auditorModel.getMessage());
        assertEquals(1000L, auditorModel.getResponseTime(), 0);
        assertEquals(loginSessionModel, auditorModel.getLoginSession());
        assertNotNull( auditorModel.getBusinessComplement());
        assertEquals(1, auditorModel.getBusinessComplement().size());
        assertEquals(businessComplementModel, auditorModel.getBusinessComplement().iterator().next());
    }

    @Test
    public void testAuditorComplementModelGettersAndSetters() {
        AuditorComplementModel auditorComplementModel = new AuditorComplementModel();

        auditorComplementModel.setId(1L);
        auditorComplementModel.setName("name");
        auditorComplementModel.setValue("value");
        auditorComplementModel.setType("java.lang.String");

        AuditorModel auditorModel = new AuditorModel();

        auditorModel.setId(1L);

        auditorComplementModel.setAuditor( auditorModel);

        assertEquals(1L, auditorComplementModel.getId(), 0);
        assertEquals("name", auditorComplementModel.getName());
        assertEquals("value", auditorComplementModel.getValue());
        assertEquals("java.lang.String", auditorComplementModel.getType());
        assertEquals(auditorModel, auditorComplementModel.getAuditor());
    }
}
