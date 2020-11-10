package br.com.concepting.framework.security.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import br.com.concepting.framework.util.helpers.DateTime;
public class LoginParameterTest{
    private LoginParameterModel model = new LoginParameterModel();
    
    @Test
    public void id_Test(){
        model.setId(null);
        assertNull(model.getId());
        
        model.setId(1);
        
        assertNotNull(model.getId());
        assertTrue(model.getId().equals(1));
    }
    @Test
    public void expirePasswordInterval_Test(){
        model.setExpirePasswordInterval(null);
        assertNull(model.getExpirePasswordInterval());
        
        model.setExpirePasswordInterval(1);
        
        assertNotNull(model.getExpirePasswordInterval());
        assertTrue(model.getExpirePasswordInterval().equals(1));
    }
    @Test
    public void changePasswordInterval_Test(){
        model.setChangePasswordInterval(null);
        assertNull(model.getChangePasswordInterval());
        
        model.setChangePasswordInterval(1);
        
        assertNotNull(model.getChangePasswordInterval());
        assertTrue(model.getChangePasswordInterval().equals(1));
    }
    @Test
    public void changePassword_Test(){
        model.setChangePassword(null);
        assertNull(model.getChangePassword());
        
        model.setChangePassword(true);
        
        assertNotNull(model.getChangePassword());
        assertEquals(true, model.getChangePassword());
        assertEquals(true, model.changePassword());
    }
    @Test
    public void expirePasswordDateTime_Test(){
        model.setExpirePasswordDateTime(null);
        assertNull(model.getExpirePasswordDateTime());
        
        DateTime dateTime = new DateTime();
        
        model.setExpirePasswordDateTime(dateTime);
        
        assertNotNull(model.getExpirePasswordDateTime());
        assertEquals(dateTime, model.getExpirePasswordDateTime());
    }
    @Test
    public void useStrongPassword_Test(){
        model.setUseStrongPassword(null);
        assertNull(model.getUseStrongPassword());
        
        model.setUseStrongPassword(true);
        
        assertNotNull(model.getUseStrongPassword());
        assertEquals(true, model.getUseStrongPassword());
        assertEquals(true, model.useStrongPassword());
    }
    @Test
    public void minimumPasswordLength_Test(){
        model.setMinimumPasswordLength(null);
        assertNull(model.getMinimumPasswordLength());
        
        model.setMinimumPasswordLength(1);
        
        assertNotNull(model.getMinimumPasswordLength());
        assertTrue(model.getMinimumPasswordLength().equals(1));
    }
    @Test
    public void multipleLogins_Test(){
        model.setMultipleLogins(null);
        assertNull(model.getMultipleLogins());
        
        model.setMultipleLogins(true);
        
        assertNotNull(model.getMultipleLogins());
        assertEquals(true, model.getMultipleLogins());
    }
    @Test
    public void loginTries_Test(){
        model.setLoginTries(null);
        assertNull(model.getLoginTries());
        
        model.setLoginTries(1);
        
        assertNotNull(model.getLoginTries());
        assertTrue(model.getLoginTries().equals(1));
    }
    @Test
    public void currentLoginTries_Test(){
        model.setCurrentLoginTries(null);
        assertNull(model.getCurrentLoginTries());
        
        model.setCurrentLoginTries(1);
        
        assertNotNull(model.getCurrentLoginTries());
        assertTrue(model.getCurrentLoginTries().equals(1));
    }
    @Test
    public void receiveIm_Test(){
        model.setReceiveIm(null);
        assertNull(model.getReceiveIm());
        
        model.setReceiveIm(true);
        
        assertNotNull(model.getReceiveIm());
        assertEquals(true, model.getReceiveIm());
    }
    @Test
    public void receiveSms_Test(){
        model.setReceiveSms(null);
        assertNull(model.getReceiveSms());
        
        model.setReceiveSms(true);
        
        assertNotNull(model.getReceiveSms());
        assertEquals(true, model.getReceiveSms());
    }
    @Test
    public void receiveVoipCall_Test(){
        model.setReceiveVoipCall(null);
        assertNull(model.getReceiveVoipCall());
        
        model.setReceiveVoipCall(true);
        
        assertNotNull(model.getReceiveVoipCall());
        assertEquals(true, model.getReceiveVoipCall());
    }
    @Test
    public void mfa_Test(){
        model.setMfa(null);
        assertNull(model.getMfa());
        
        model.setMfa(true);
        
        assertNotNull(model.getMfa());
        assertEquals(true, model.getMfa());
        assertEquals(true, model.hasMfa());
    }
    @Test
    public void notificationToken_Test(){
        model.setNotificationToken(null);
        assertNull(model.getNotificationToken());
        
        model.setNotificationToken("test");
        
        assertNotNull(model.getNotificationToken());
        assertEquals("test", model.getNotificationToken());
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
    public void mfaTokenValidated_Test(){
        model.setMfaTokenValidated(null);
        assertNull(model.getMfaTokenValidated());
        
        model.setMfaTokenValidated(true);
        
        assertNotNull(model.getMfaTokenValidated());
        assertEquals(true, model.getMfaTokenValidated());
        assertEquals(true, model.isMfaTokenValidated());
    }
    @Test
    public void passwordWillExpire_Test(){
        model.setPasswordWillExpire(null);
        assertNull(model.getPasswordWillExpire());
        
        model.setPasswordWillExpire(true);
        
        assertNotNull(model.getPasswordWillExpire());
        assertEquals(true, model.getPasswordWillExpire());
        assertEquals(true, model.isPasswordWillExpire());
    }
    @Test
    public void daysUntilExpire_Test(){
        model.setDaysUntilExpire(null);
        assertNull(model.getDaysUntilExpire());
        
        model.setDaysUntilExpire(1);
        
        assertNotNull(model.getDaysUntilExpire());
        assertTrue(model.getDaysUntilExpire().equals(1));
    }
    @Test
    public void hoursUntilExpire_Test(){
        model.setHoursUntilExpire(null);
        assertNull(model.getHoursUntilExpire());
        
        model.setHoursUntilExpire(1);
        
        assertNotNull(model.getHoursUntilExpire());
        assertTrue(model.getHoursUntilExpire().equals(1));
    }
    @Test
    public void minutesUntilExpire_Test(){
        model.setMinutesUntilExpire(null);
        assertNull(model.getMinutesUntilExpire());
        
        model.setMinutesUntilExpire(1);
        
        assertNotNull(model.getMinutesUntilExpire());
        assertTrue(model.getMinutesUntilExpire().equals(1));
    }
    @Test
    public void secondsUntilExpire_Test(){
        model.setSecondsUntilExpire(null);
        assertNull(model.getSecondsUntilExpire());
        
        model.setSecondsUntilExpire(1);
        
        assertNotNull(model.getSecondsUntilExpire());
        assertTrue(model.getSecondsUntilExpire().equals(1));
    }
    @Test
    public void language_Test(){
        model.setLanguage(null);
        assertNull(model.getLanguage());
        
        model.setLanguage("test");
        
        assertNotNull(model.getLanguage());
        assertEquals("test", model.getLanguage());
    }
    @Test
    public void skin_Test(){
        model.setSkin(null);
        assertNull(model.getSkin());
        
        model.setSkin("test");
        
        assertNotNull(model.getSkin());
        assertEquals("test", model.getSkin());
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
        
        LoginParameterModel value = new LoginParameterModel();
        
        model.setParent(value);
        
        assertNotNull(model.getParent());
        assertEquals(value, model.getParent());
    }
    @Test
    public void children_Test(){
        model.setChildren(null);
        assertNull(model.getChildren());
        
        List<LoginParameterModel> values = new ArrayList<>();
        
        values.add(new LoginParameterModel());
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
