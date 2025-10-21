package br.com.concepting.framework.util;

import net.bytebuddy.asm.Advice;
import org.junit.Test;
import org.mockito.MockedStatic;

import java.util.Locale;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mockStatic;

public class LanguageUtilTest {
    @Test
    public void testGetByString(){
        assertEquals(new Locale("en"), LanguageUtil.getLanguageByString("en"));
        assertEquals(new Locale("pt", "BR"), LanguageUtil.getLanguageByString("pt_BR"));
        assertEquals(new Locale("zh", "Hant", "TW"), LanguageUtil.getLanguageByString("zh_Hant_TW"));

        assertNull(LanguageUtil.getLanguageByString(null));
        assertNull(LanguageUtil.getLanguageByString(""));
    }

    @Test
    public void testGetDefault(){
        assertNotNull(LanguageUtil.getDefaultLanguage());
    }
}
