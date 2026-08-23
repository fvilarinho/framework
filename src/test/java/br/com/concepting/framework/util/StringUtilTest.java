package br.com.concepting.framework.util;

import br.com.concepting.framework.model.TestModel;
import br.com.concepting.framework.util.types.AlignmentType;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.junit.Test;

import static org.junit.Assert.*;

public class StringUtilTest {
    @Test
    public void testCapitalizationAndNormalization(){
        assertEquals("hello world", StringUtil.normalize("Hello World"));
        assertEquals("hello world", StringUtil.normalize("Hello World", StringUtils.EMPTY));
        assertEquals("hello world", StringUtil.normalize("Hello World", null));
        assertEquals("helloWorld", StringUtil.normalize("Hello_World"));
        assertEquals(StringUtils.EMPTY, StringUtil.normalize(StringUtils.EMPTY, StringUtils.EMPTY));
        assertEquals(StringUtils.EMPTY, StringUtil.normalize(StringUtils.EMPTY, null));
        assertEquals(StringUtils.EMPTY, StringUtil.normalize(null));
        assertEquals(StringUtils.EMPTY, StringUtil.normalize(null, null));
        assertEquals(StringUtils.EMPTY, StringUtil.normalize(null, StringUtils.EMPTY));

        assertEquals("Hello World", StringUtil.capitalize("hello world"));
        assertEquals("Hello World", StringUtil.capitalize("hello world", null));
        assertEquals("Hi there", StringUtil.capitalize("hi there", true));
        assertEquals("Orange,Tomato,Sugar", StringUtil.capitalize("orange,tomato,sugar", ","));
        assertEquals("Dark Side", StringUtil.capitalize("dark side", StringUtils.EMPTY, false));
        assertEquals(StringUtils.EMPTY, StringUtil.capitalize(null));
        assertEquals(StringUtils.EMPTY, StringUtil.capitalize(StringUtils.EMPTY));
        assertEquals(StringUtils.EMPTY, StringUtil.capitalize(null, ";", false));
        assertEquals(StringUtils.EMPTY, StringUtil.capitalize(StringUtils.EMPTY, ";", false));
    }

    @Test
    public void testSplit() {
        assertArrayEquals(new String[]{"Hello", "World"}, StringUtil.split("Hello,World"));
        assertArrayEquals(new String[]{"Hello", "World"}, StringUtil.split("Hello,World", null));
        assertArrayEquals(new String[]{"Hello", "World"}, StringUtil.split("Hello,World", StringUtils.EMPTY));
        assertArrayEquals(new String[]{"Hello", "World"}, StringUtil.split("Hello;World", ";"));

        assertNull(StringUtil.split(null));
        assertNull(StringUtil.split(StringUtils.EMPTY));
        assertNull(StringUtil.split(null, null));
        assertNull(StringUtil.split(null, StringUtils.EMPTY));
        assertNull(StringUtil.split(StringUtils.EMPTY, null));
        assertNull(StringUtil.split(StringUtils.EMPTY, StringUtils.EMPTY));
    }

    @Test
    public void testMerge() {
        assertEquals("Hello World", StringUtil.merge(new String[]{"Hello","World"}, " "));
        assertEquals("Orange,Tomato,Sugar", StringUtil.merge(new String[]{"Orange","Tomato","Sugar"}));
        assertEquals("Orange,Tomato,Sugar", StringUtil.merge(new String[]{"Orange","Tomato","Sugar"}, null));
        assertEquals("Orange,Tomato,Sugar", StringUtil.merge(new String[]{"Orange","Tomato","Sugar"}, StringUtils.EMPTY));

        assertNull(StringUtil.merge(null));
        assertNull(StringUtil.merge(new String[0]));
        assertNull(StringUtil.merge(null, null));
        assertNull(StringUtil.merge(null, ","));
        assertNull(StringUtil.merge(new String[0], null));
        assertNull(StringUtil.merge(new String[0], ","));
    }

    @Test
    public void testAlign() {
        assertEquals("Left alignment      ", StringUtil.align(AlignmentType.LEFT, 20, "Left alignment"));
        assertEquals("     Right alignment", StringUtil.align(AlignmentType.RIGHT, 20, "Right alignment"));
        assertEquals("  Center alignment  ", StringUtil.align(AlignmentType.CENTER, 20, "Center alignment"));
        assertEquals(StringUtils.EMPTY, StringUtil.align(AlignmentType.LEFT, 0, StringUtils.EMPTY));
        assertEquals("Bla", StringUtil.align(AlignmentType.LEFT, 0, "Bla"));
        assertEquals(StringUtils.EMPTY, StringUtil.align(AlignmentType.LEFT, 1, StringUtils.EMPTY));
        assertEquals("Bla", StringUtil.align(AlignmentType.LEFT, 1, "Bla"));

        assertNull(StringUtil.align(AlignmentType.LEFT, 0, null));
        assertNull(StringUtil.align(AlignmentType.LEFT, 1, null));
    }

    @Test
    public void testAscii() {
        assertEquals('$', StringUtil.chr(36));
        assertEquals(36, StringUtil.asc('$'));
        assertEquals(System.lineSeparator(), StringUtil.getLineBreak());
    }

    @Test
    public void testTrim() {
        assertEquals("Hello World", StringUtil.trim("  Hello World  "));
        assertEquals(StringUtils.EMPTY, StringUtil.trim(null));
        assertTrue(StringUtil.trim(new TestModel()).startsWith(TestModel.class.getName()));
        assertEquals("Hello World", StringUtil.trim(new TestModel("  Hello World  ")));
    }

    @Test
    public void testReverse() {
        assertEquals("olleH", StringUtil.reverse("Hello"));
        assertEquals(StringUtils.EMPTY, StringUtil.reverse(StringUtils.EMPTY));

        assertNull(StringUtil.reverse(null));
    }

    @Test
    public void testReplicate() {
        assertEquals("abcabcabc", StringUtil.replicate("abc", 3));
        assertEquals(StringUtils.EMPTY, StringUtil.replicate(StringUtils.EMPTY, 3));
        assertEquals(StringUtils.EMPTY, StringUtil.replicate(null, 3));
    }

    @Test
    public void testValidReplaces() {
        assertEquals("Hello there!", StringUtil.replaceAll("Hello world!", "world", "there"));
        assertEquals("there", StringUtil.replaceAll("world", "world", "there"));
        assertEquals("world", StringUtil.replaceAll("world", "world", null));
        assertEquals("Hello Luke! I'm am your father.", StringUtil.replaceLast("Hello Luke! I'm am your father!", "!", "."));
        assertEquals("Hello Luke! I'm am your father.", StringUtil.replaceLast("Hello Luke! I'm am your father!", '!', "."));
        assertEquals("Hello Luke! I'm am your father", StringUtil.replaceLast("Hello Luke! I'm am your father!", '!', StringUtils.EMPTY));
        assertEquals(StringUtils.EMPTY, StringUtil.replaceAll("world", "world", StringUtils.EMPTY));
    }

    @Test
    public void testInvalidReplaces() {
        assertEquals("world", StringUtil.replaceAll("world", "world", "world"));
        assertEquals("world", StringUtil.replaceAll("world", null, "world"));
        assertEquals("world", StringUtil.replaceAll("world", StringUtils.EMPTY, "world"));
        assertEquals("world", StringUtil.replaceAll("world", null, null));
        assertEquals("world", StringUtil.replaceAll("world", StringUtils.EMPTY, StringUtils.EMPTY));
        assertEquals("Hello Luke! I'm am your father!", StringUtil.replaceLast("Hello Luke! I'm am your father!", '!', null));
        assertEquals(StringUtils.EMPTY, StringUtil.replaceAll(StringUtils.EMPTY, "world", "there"));
        assertEquals(StringUtils.EMPTY, StringUtil.replaceLast(StringUtils.EMPTY, '!', null));

        assertNull(StringUtil.replaceAll(null, "world", null));
        assertNull(StringUtil.replaceAll(null, "world", StringUtils.EMPTY));
        assertNull(StringUtil.replaceAll(null, StringUtils.EMPTY, null));
        assertNull(StringUtil.replaceAll(null, StringUtils.EMPTY, StringUtils.EMPTY));
        assertNull(StringUtil.replaceAll(null, null, null));
        assertNull(StringUtil.replaceAll(null, null, StringUtils.EMPTY));
        assertNull(StringUtil.replaceLast(null, '!', null));
        assertNull(StringUtil.replaceLast(null, '!', StringUtils.EMPTY));
        assertNull(StringUtil.replaceLast(null, "world", null));
        assertNull(StringUtil.replaceLast(null, "world", StringUtils.EMPTY));
        assertNull(StringUtil.replaceLast(null, '!', null));
        assertNull(StringUtil.replaceLast(null, '!', StringUtils.EMPTY));
    }

    @Test
    public void testFormatting() {
        assertEquals("(11) 91234-5678", StringUtil.format("11912345678", "(99) 99999-9999"));
        assertEquals("21/10/2025 16:30:00", StringUtil.format("21102025163000", "dd/MM/yyyy HH:mm:ss"));
        assertEquals("11 91234-5678", StringUtil.format("11 91234-5678", "99999999999"));
        assertEquals(StringUtils.EMPTY, StringUtil.format(StringUtils.EMPTY, "99999999999"));
        assertEquals(StringUtils.EMPTY, StringUtil.format(StringUtils.EMPTY, null));
        assertEquals(StringUtils.EMPTY, StringUtil.format(StringUtils.EMPTY, StringUtils.EMPTY));
        assertEquals("1234", StringUtil.format("1234", null));
        assertEquals("1234", StringUtil.format("1234", StringUtils.EMPTY));
        assertEquals("11912345678", StringUtil.unformat("(11) 91234-5678", "(99) 99999-9999"));
        assertEquals(StringUtils.EMPTY, StringUtil.unformat(StringUtils.EMPTY, "99999999999"));
        assertEquals(StringUtils.EMPTY, StringUtil.unformat(StringUtils.EMPTY, null));
        assertEquals(StringUtils.EMPTY, StringUtil.unformat(StringUtils.EMPTY, StringUtils.EMPTY));
        assertEquals("1234", StringUtil.unformat("1234", null));
        assertEquals("1234", StringUtil.unformat("1234", StringUtils.EMPTY));

        assertNull(StringUtil.format(null, "99999999999"));
        assertNull(StringUtil.format(null, null));
        assertNull(StringUtil.format(null, StringUtils.EMPTY));
        assertNull(StringUtil.unformat(null, "99999999999"));
        assertNull(StringUtil.unformat(null, null));
        assertNull(StringUtil.unformat(null, StringUtils.EMPTY));
    }

    @Test
    public void testRegex() {
        assertEquals(".*", StringUtil.toRegex("*"));
        assertEquals(".", StringUtil.toRegex("?"));
        assertEquals(".*\\.*", StringUtil.toRegex("*.*"));
        assertEquals(StringUtils.EMPTY, StringUtil.toRegex(StringUtils.EMPTY));

        assertNull(StringUtil.toRegex(null));
    }
}
