package br.com.concepting.framework.util;

import br.com.concepting.framework.model.TestModel;
import br.com.concepting.framework.util.types.AlignmentType;
import org.junit.Test;

import static org.junit.Assert.*;

public class StringUtilTest {
    @Test
    public void testCapitalization(){
        assertEquals("Hello World", StringUtil.capitalize("hello world"));
        assertEquals("Hello World", StringUtil.capitalize("hello world", null));
        assertEquals("Hi there", StringUtil.capitalize("hi there", true));
        assertEquals("Orange,Tomato,Sugar", StringUtil.capitalize("orange,tomato,sugar", ","));
        assertEquals("Dark Side", StringUtil.capitalize("dark side", "", false));

        assertNull(StringUtil.capitalize(null));
        assertNull(StringUtil.capitalize(""));
        assertNull(StringUtil.capitalize(null, ";", false));
        assertNull(StringUtil.capitalize("", ";", false));
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
        assertEquals("", StringUtil.trim(new TestModel()));
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
}
