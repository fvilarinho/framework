package br.com.concepting.framework.util;

import br.com.concepting.framework.model.TestModel;
import br.com.concepting.framework.util.types.AlignmentType;
import org.apache.logging.log4j.util.Strings;
import org.junit.Test;

import static org.junit.Assert.*;

public class StringUtilTest {
    @Test
    public void testCapitalizationAndNormalization(){
        assertEquals("hello world", StringUtil.normalize("Hello World"));
        assertEquals("hello world", StringUtil.normalize("Hello World", Strings.EMPTY));
        assertEquals("hello world", StringUtil.normalize("Hello World", null));
        assertEquals("helloWorld", StringUtil.normalize("Hello_World"));
        assertEquals(Strings.EMPTY, StringUtil.normalize(Strings.EMPTY, Strings.EMPTY));
        assertEquals(Strings.EMPTY, StringUtil.normalize(Strings.EMPTY, null));
        assertEquals(Strings.EMPTY, StringUtil.normalize(null));
        assertEquals(Strings.EMPTY, StringUtil.normalize(null, null));
        assertEquals(Strings.EMPTY, StringUtil.normalize(null, Strings.EMPTY));

        assertEquals("Hello World", StringUtil.capitalize("hello world"));
        assertEquals("Hello World", StringUtil.capitalize("hello world", null));
        assertEquals("Hi there", StringUtil.capitalize("hi there", true));
        assertEquals("Orange,Tomato,Sugar", StringUtil.capitalize("orange,tomato,sugar", ","));
        assertEquals("Dark Side", StringUtil.capitalize("dark side", Strings.EMPTY, false));
        assertEquals(Strings.EMPTY, StringUtil.capitalize(null));
        assertEquals(Strings.EMPTY, StringUtil.capitalize(Strings.EMPTY));
        assertEquals(Strings.EMPTY, StringUtil.capitalize(null, ";", false));
        assertEquals(Strings.EMPTY, StringUtil.capitalize(Strings.EMPTY, ";", false));
    }

    @Test
    public void testSplit() {
        assertArrayEquals(new String[]{"Hello", "World"}, StringUtil.split("Hello,World"));
        assertArrayEquals(new String[]{"Hello", "World"}, StringUtil.split("Hello,World", null));
        assertArrayEquals(new String[]{"Hello", "World"}, StringUtil.split("Hello,World", Strings.EMPTY));
        assertArrayEquals(new String[]{"Hello", "World"}, StringUtil.split("Hello;World", ";"));

        assertNull(StringUtil.split(null));
        assertNull(StringUtil.split(Strings.EMPTY));
        assertNull(StringUtil.split(null, null));
        assertNull(StringUtil.split(null, Strings.EMPTY));
        assertNull(StringUtil.split(Strings.EMPTY, null));
        assertNull(StringUtil.split(Strings.EMPTY, Strings.EMPTY));
    }

    @Test
    public void testMerge() {
        assertEquals("Hello World", StringUtil.merge(new String[]{"Hello","World"}, " "));
        assertEquals("Orange,Tomato,Sugar", StringUtil.merge(new String[]{"Orange","Tomato","Sugar"}));
        assertEquals("Orange,Tomato,Sugar", StringUtil.merge(new String[]{"Orange","Tomato","Sugar"}, null));
        assertEquals("Orange,Tomato,Sugar", StringUtil.merge(new String[]{"Orange","Tomato","Sugar"}, Strings.EMPTY));

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
        assertEquals(Strings.EMPTY, StringUtil.align(AlignmentType.LEFT, 0, Strings.EMPTY));
        assertEquals("Bla", StringUtil.align(AlignmentType.LEFT, 0, "Bla"));
        assertEquals(Strings.EMPTY, StringUtil.align(AlignmentType.LEFT, 1, Strings.EMPTY));
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
        assertEquals(Strings.EMPTY, StringUtil.trim(null));
        assertTrue(StringUtil.trim(new TestModel()).startsWith(TestModel.class.getName()));
        assertEquals("Hello World", StringUtil.trim(new TestModel("  Hello World  ")));
    }

    @Test
    public void testReverse() {
        assertEquals("olleH", StringUtil.reverse("Hello"));
        assertEquals(Strings.EMPTY, StringUtil.reverse(Strings.EMPTY));

        assertNull(StringUtil.reverse(null));
    }

    @Test
    public void testReplicate() {
        assertEquals("abcabcabc", StringUtil.replicate("abc", 3));
        assertEquals(Strings.EMPTY, StringUtil.replicate(Strings.EMPTY, 3));
        assertEquals(Strings.EMPTY, StringUtil.replicate(null, 3));
    }

    @Test
    public void testReplace() {
        assertEquals("Hello there!", StringUtil.replaceAll("Hello world!", "world", "there"));
        assertEquals("world", StringUtil.replaceAll("world", "world", "world"));
        assertEquals("there", StringUtil.replaceAll("world", "world", "there"));
        assertEquals(Strings.EMPTY, StringUtil.replaceAll("world", "world", Strings.EMPTY));
        assertEquals(Strings.EMPTY, StringUtil.replaceAll("world", "world", null));
        assertEquals("world", StringUtil.replaceAll("world", null, "world"));
        assertEquals("world", StringUtil.replaceAll("world", Strings.EMPTY, "world"));
        assertEquals("world", StringUtil.replaceAll("world", null, null));
        assertEquals("world", StringUtil.replaceAll("world", Strings.EMPTY, Strings.EMPTY));
        assertEquals(Strings.EMPTY, StringUtil.replaceAll(Strings.EMPTY, "world", "there"));
        assertEquals("Hello Luke! I'm am your father.", StringUtil.replaceLast("Hello Luke! I'm am your father!", "!", "."));
        assertEquals("Hello Luke! I'm am your father.", StringUtil.replaceLast("Hello Luke! I'm am your father!", '!', "."));
        assertEquals("Hello Luke! I'm am your father!", StringUtil.replaceLast("Hello Luke! I'm am your father!", '!', Strings.EMPTY));
        assertEquals("Hello Luke! I'm am your father!", StringUtil.replaceLast("Hello Luke! I'm am your father!", '!', null));
        assertEquals(Strings.EMPTY, StringUtil.replaceLast(Strings.EMPTY, '!', null));

        assertNull(StringUtil.replaceAll(null, "world", null));
        assertNull(StringUtil.replaceAll(null, "world", Strings.EMPTY));
        assertNull(StringUtil.replaceAll(null, Strings.EMPTY, null));
        assertNull(StringUtil.replaceAll(null, Strings.EMPTY, Strings.EMPTY));
        assertNull(StringUtil.replaceAll(null, null, null));
        assertNull(StringUtil.replaceAll(null, null, Strings.EMPTY));
        assertNull(StringUtil.replaceLast(null, '!', null));
        assertNull(StringUtil.replaceLast(null, '!', Strings.EMPTY));
        assertNull(StringUtil.replaceLast(null, "world", null));
        assertNull(StringUtil.replaceLast(null, "world", Strings.EMPTY));
        assertNull(StringUtil.replaceLast(null, '!', null));
        assertNull(StringUtil.replaceLast(null, '!', Strings.EMPTY));
    }

    @Test
    public void testFormatting() {
        assertEquals("(11) 91234-5678", StringUtil.format("11912345678", "(99) 99999-9999"));
        assertEquals("21/10/2025 16:30:00", StringUtil.format("21102025163000", "dd/MM/yyyy HH:mm:ss"));
        assertEquals("11 91234-5678", StringUtil.format("11 91234-5678", "99999999999"));
        assertEquals(Strings.EMPTY, StringUtil.format(Strings.EMPTY, "99999999999"));
        assertEquals(Strings.EMPTY, StringUtil.format(Strings.EMPTY, null));
        assertEquals(Strings.EMPTY, StringUtil.format(Strings.EMPTY, Strings.EMPTY));
        assertEquals("1234", StringUtil.format("1234", null));
        assertEquals("1234", StringUtil.format("1234", Strings.EMPTY));
        assertEquals("11912345678", StringUtil.unformat("(11) 91234-5678", "(99) 99999-9999"));
        assertEquals(Strings.EMPTY, StringUtil.unformat(Strings.EMPTY, "99999999999"));
        assertEquals(Strings.EMPTY, StringUtil.unformat(Strings.EMPTY, null));
        assertEquals(Strings.EMPTY, StringUtil.unformat(Strings.EMPTY, Strings.EMPTY));
        assertEquals("1234", StringUtil.unformat("1234", null));
        assertEquals("1234", StringUtil.unformat("1234", Strings.EMPTY));

        assertNull(StringUtil.format(null, "99999999999"));
        assertNull(StringUtil.format(null, null));
        assertNull(StringUtil.format(null, Strings.EMPTY));
        assertNull(StringUtil.unformat(null, "99999999999"));
        assertNull(StringUtil.unformat(null, null));
        assertNull(StringUtil.unformat(null, Strings.EMPTY));
    }

    @Test
    public void testRegex() {
        assertEquals(".*", StringUtil.toRegex("*"));
        assertEquals(".", StringUtil.toRegex("?"));
        assertEquals(".*\\.*", StringUtil.toRegex("*.*"));
        assertEquals(Strings.EMPTY, StringUtil.toRegex(Strings.EMPTY));

        assertNull(StringUtil.toRegex(null));
    }
}
