package br.com.concepting.framework.util;

import org.junit.Test;

import java.util.Locale;

import static org.junit.Assert.*;

public class LanguageUtilTest {
    @Test
    public void testGetByString(){
        assertEquals(Locale.of("en"), LanguageUtil.getLanguageByString("en"));
        assertEquals(Locale.of("pt", "BR"), LanguageUtil.getLanguageByString("pt_BR"));
        assertEquals(Locale.of("zh", "Hant", "TW"), LanguageUtil.getLanguageByString("zh_Hant_TW"));

        assertNull(LanguageUtil.getLanguageByString(null));
        assertNull(LanguageUtil.getLanguageByString(""));
    }

    @Test
    public void testGetDefault(){
        assertNotNull(LanguageUtil.getDefaultLanguage());
    }
}
