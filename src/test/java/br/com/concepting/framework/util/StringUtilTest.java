package br.com.concepting.framework.util;

import br.com.concepting.framework.model.TestModel;
import br.com.concepting.framework.util.types.AlignmentType;
import org.junit.Test;

import static org.junit.Assert.*;

public class StringUtilTest {
    @Test
    public void testCapitalizationAndNormalization(){
        assertEquals("Hello World", StringUtil.capitalize("hello world"));
        assertEquals("Hello World", StringUtil.capitalize("hello world", null));
        assertEquals("Hi there", StringUtil.capitalize("hi there", true));
        assertEquals("Orange,Tomato,Sugar", StringUtil.capitalize("orange,tomato,sugar", ","));
        assertEquals("Dark Side", StringUtil.capitalize("dark side", "", false));
        assertEquals("hello world", StringUtil.normalize("Hello World"));
        assertEquals("hello world", StringUtil.normalize("Hello World", ""));
        assertEquals("hello world", StringUtil.normalize("Hello World", null));
        assertEquals("helloWorld", StringUtil.normalize("Hello_World"));
        assertEquals("", StringUtil.normalize("", ""));
        assertEquals("", StringUtil.normalize("", null));

        assertNull(StringUtil.capitalize(null));
        assertNull(StringUtil.capitalize(""));
        assertNull(StringUtil.capitalize(null, ";", false));
        assertNull(StringUtil.capitalize("", ";", false));
        assertNull(StringUtil.normalize(null));
        assertNull(StringUtil.normalize(null, null));
        assertNull(StringUtil.normalize(null, ""));
    }

    @Test
    public void testSplit() {
        assertArrayEquals(new String[]{"Hello", "World"}, StringUtil.split("Hello,World"));
        assertArrayEquals(new String[]{"Hello", "World"}, StringUtil.split("Hello,World", null));
        assertArrayEquals(new String[]{"Hello", "World"}, StringUtil.split("Hello,World", ""));
        assertArrayEquals(new String[]{"Hello", "World"}, StringUtil.split("Hello;World", ";"));

        assertNull(StringUtil.split(null));
        assertNull(StringUtil.split(""));
        assertNull(StringUtil.split(null, null));
        assertNull(StringUtil.split(null, ""));
        assertNull(StringUtil.split("", null));
        assertNull(StringUtil.split("", ""));
    }

    @Test
    public void testMerge() {
        assertEquals("Hello World", StringUtil.merge(new String[]{"Hello","World"}, " "));
        assertEquals("Orange,Tomato,Sugar", StringUtil.merge(new String[]{"Orange","Tomato","Sugar"}));
        assertEquals("Orange,Tomato,Sugar", StringUtil.merge(new String[]{"Orange","Tomato","Sugar"}, null));
        assertEquals("Orange,Tomato,Sugar", StringUtil.merge(new String[]{"Orange","Tomato","Sugar"}, ""));

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
        assertEquals("", StringUtil.align(AlignmentType.LEFT, 0, ""));
        assertEquals("Bla", StringUtil.align(AlignmentType.LEFT, 0, "Bla"));
        assertEquals("", StringUtil.align(AlignmentType.LEFT, 1, ""));
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
        assertEquals("", StringUtil.trim(null));
        assertTrue(StringUtil.trim(new TestModel()).startsWith(TestModel.class.getName()));
        assertEquals("Hello World", StringUtil.trim(new TestModel("  Hello World  ")));
    }

    @Test
    public void testReverse() {
        assertEquals("olleH", StringUtil.reverse("Hello"));
        assertEquals("", StringUtil.reverse(""));

        assertNull(StringUtil.reverse(null));
    }

    @Test
    public void testReplicate() {
        assertEquals("abcabcabc", StringUtil.replicate("abc", 3));
        assertEquals("", StringUtil.replicate("", 3));

        assertNull(StringUtil.replicate(null, 3));
    }

    @Test
    public void testReplace() {
        assertEquals("Hello there!", StringUtil.replaceAll("Hello world!", "world", "there"));
        assertEquals("world", StringUtil.replaceAll("world", "world", "world"));
        assertEquals("there", StringUtil.replaceAll("world", "world", "there"));
        assertEquals("", StringUtil.replaceAll("world", "world", ""));
        assertEquals("", StringUtil.replaceAll("world", "world", null));
        assertEquals("world", StringUtil.replaceAll("world", null, "world"));
        assertEquals("world", StringUtil.replaceAll("world", "", "world"));
        assertEquals("world", StringUtil.replaceAll("world", null, null));
        assertEquals("world", StringUtil.replaceAll("world", "", ""));
        assertEquals("", StringUtil.replaceAll("", "world", "there"));
        assertEquals("Hello Luke! I'm am your father.", StringUtil.replaceLast("Hello Luke! I'm am your father!", "!", "."));
        assertEquals("Hello Luke! I'm am your father.", StringUtil.replaceLast("Hello Luke! I'm am your father!", '!', "."));
        assertEquals("Hello Luke! I'm am your father!", StringUtil.replaceLast("Hello Luke! I'm am your father!", '!', ""));
        assertEquals("Hello Luke! I'm am your father!", StringUtil.replaceLast("Hello Luke! I'm am your father!", '!', null));
        assertEquals("", StringUtil.replaceLast("", '!', null));

        assertNull(StringUtil.replaceAll(null, "world", null));
        assertNull(StringUtil.replaceAll(null, "world", ""));
        assertNull(StringUtil.replaceAll(null, "", null));
        assertNull(StringUtil.replaceAll(null, "", ""));
        assertNull(StringUtil.replaceAll(null, null, null));
        assertNull(StringUtil.replaceAll(null, null, ""));
        assertNull(StringUtil.replaceLast(null, '!', null));
        assertNull(StringUtil.replaceLast(null, '!', ""));
        assertNull(StringUtil.replaceLast(null, "world", null));
        assertNull(StringUtil.replaceLast(null, "world", ""));
        assertNull(StringUtil.replaceLast(null, '!', null));
        assertNull(StringUtil.replaceLast(null, '!', ""));
    }

    @Test
    public void testFormatting() {
        assertEquals("(11) 91234-5678", StringUtil.format("11912345678", "(99) 99999-9999"));
        assertEquals("21/10/2025 16:30:00", StringUtil.format("21102025163000", "dd/MM/yyyy HH:mm:ss"));
        assertEquals("11 91234-5678", StringUtil.format("11 91234-5678", "99999999999"));
        assertEquals("", StringUtil.format("", "99999999999"));
        assertEquals("", StringUtil.format("", null));
        assertEquals("", StringUtil.format("", ""));
        assertEquals("1234", StringUtil.format("1234", null));
        assertEquals("1234", StringUtil.format("1234", ""));
        assertEquals("11912345678", StringUtil.unformat("(11) 91234-5678", "(99) 99999-9999"));
        assertEquals("", StringUtil.unformat("", "99999999999"));
        assertEquals("", StringUtil.unformat("", null));
        assertEquals("", StringUtil.unformat("", ""));
        assertEquals("1234", StringUtil.unformat("1234", null));
        assertEquals("1234", StringUtil.unformat("1234", ""));

        assertNull(StringUtil.format(null, "99999999999"));
        assertNull(StringUtil.format(null, null));
        assertNull(StringUtil.format(null, ""));
        assertNull(StringUtil.unformat(null, "99999999999"));
        assertNull(StringUtil.unformat(null, null));
        assertNull(StringUtil.unformat(null, ""));
    }

    @Test
    public void testRegex() {
        assertEquals(".*", StringUtil.toRegex("*"));
        assertEquals(".", StringUtil.toRegex("?"));
        assertEquals(".*\\.*", StringUtil.toRegex("*.*"));
        assertEquals("", StringUtil.toRegex(""));

        assertNull(StringUtil.toRegex(null));
    }
}
