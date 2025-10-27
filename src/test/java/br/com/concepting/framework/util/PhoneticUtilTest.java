package br.com.concepting.framework.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PhoneticUtilTest {
    @Test
    public void testSoundSimilarity() {
        String value1 = "Joe";
        String value2 = "Jou";

        assertEquals(100d, PhoneticUtil.getSoundSimilarity(value1, value2), 0);

        value1 = "Adrian";
        value2 = "Joe";

        assertEquals(0d, PhoneticUtil.getSoundSimilarity(value1, value2), 0);

        value1 = "Alvaro";
        value2 = "Osvaldo";

        double percentage = PhoneticUtil.getSoundSimilarity(value1, value2);

        assertTrue(percentage >= 40 && percentage <= 50d);
        assertEquals(0d, PhoneticUtil.getSoundSimilarity(null, ""), 0);
    }
}
