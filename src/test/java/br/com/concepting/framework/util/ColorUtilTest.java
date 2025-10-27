package br.com.concepting.framework.util;

import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ColorUtilTest {
    @Test
    public void testConversions() {
        String colorString = "rgb(255, 0, 0)";
        String colorHex = "#ff0000";
        Color colorObject = ColorUtil.toColor(colorString);

        assertEquals(Color.RED, colorObject);
        assertEquals(colorHex, ColorUtil.toHexString(colorObject));
        assertEquals(colorString, ColorUtil.toString(colorObject));

        assertNull(ColorUtil.toColor("#ff0000"));
        assertNull(ColorUtil.toColor("rgb(255, 0)"));
        assertNull(ColorUtil.toColor(null));
        assertNull(ColorUtil.toHexString(null));
        assertNull(ColorUtil.toString(null));
    }
}
