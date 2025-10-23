package br.com.concepting.framework.util;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.Locale;

import static org.junit.Assert.*;

public class NumberUtilTest {
    @Test
    public void testParseFloat() {
        try {
            float value = NumberUtil.parseFloat("1,5");

            assertEquals(1.5f, value, 0);

            value = NumberUtil.parseFloat("1.5", Locale.ENGLISH);

            assertEquals(1.5f, value, 0);
            assertEquals(0f, NumberUtil.parseFloat(null, Locale.ENGLISH), 0);
            assertEquals(0f, NumberUtil.parseFloat("", Locale.ENGLISH), 0);
        }
        catch(ParseException e){
            fail(e.getMessage());
        }
    }

    @Test
    public void testParseDouble() {
        try {
            double value = NumberUtil.parseDouble("1,5");

            assertEquals(1.5d, value, 0);

            value = NumberUtil.parseDouble("1.5", Locale.ENGLISH);

            assertEquals(1.5d, value, 0);
            assertEquals(0d, NumberUtil.parseDouble(null, Locale.ENGLISH), 0);
            assertEquals(0d, NumberUtil.parseDouble("", Locale.ENGLISH), 0);
        }
        catch(ParseException e){
            fail(e.getMessage());
        }
    }

    @Test
    public void testParseBigDecimal() {
        try {
            BigDecimal value = NumberUtil.parseBigDecimal("1,5");

            assertEquals(BigDecimal.valueOf(1.5), value);

            value = NumberUtil.parseBigDecimal("1.5", Locale.ENGLISH);

            assertEquals(BigDecimal.valueOf(1.5), value);
            assertEquals(BigDecimal.ZERO, NumberUtil.parseBigDecimal(null, Locale.ENGLISH));
            assertEquals(BigDecimal.ZERO, NumberUtil.parseBigDecimal("", Locale.ENGLISH));
        }
        catch(ParseException e){
            fail(e.getMessage());
        }
    }

    @Test
    public void testParseBigInteger() {
        try {
            BigInteger value = NumberUtil.parseBigInteger("9223372036854775807");

            assertEquals(BigInteger.valueOf(9223372036854775807L), value);

            value = NumberUtil.parseBigInteger("-9223372036854775808");

            assertEquals(BigInteger.valueOf(-9223372036854775808L), value);

            assertEquals(BigInteger.ZERO, NumberUtil.parseBigInteger(null));
            assertEquals(BigInteger.ZERO, NumberUtil.parseBigInteger(""));
        }
        catch(ParseException e){
            fail(e.getMessage());
        }
    }

    @Test
    public void testParseLong() {
        try {
            long value = NumberUtil.parseLong("9223372036854775807");

            assertEquals(9223372036854775807L, value);

            value = NumberUtil.parseLong("-9223372036854775808");

            assertEquals(-9223372036854775808L, value);

            assertEquals(0, NumberUtil.parseLong(null));
            assertEquals(0, NumberUtil.parseLong(""));
        }
        catch(ParseException e){
            fail(e.getMessage());
        }
    }

    @Test
    public void testParseInt() {
        try {
            int value = NumberUtil.parseInt("2147483647");

            assertEquals(2147483647, value);

            value = NumberUtil.parseInt("-2147483648");

            assertEquals(-2147483648, value);

            assertEquals(0, NumberUtil.parseInt(null));
            assertEquals(0, NumberUtil.parseInt(""));
        }
        catch(ParseException e){
            fail(e.getMessage());
        }
    }

    @Test
    public void testParseShort() {
        try {
            short value = NumberUtil.parseShort("32767");

            assertEquals(32767, value);

            value = NumberUtil.parseShort("-32768");

            assertEquals(-32768, value);

            assertEquals((short)0, NumberUtil.parseShort(null));
            assertEquals((short)0, NumberUtil.parseShort(""));
        }
        catch(ParseException e){
            fail(e.getMessage());
        }
    }

    @Test
    public void testParseByte() {
        try {
            byte value = NumberUtil.parseByte("127");

            assertEquals(127, value);

            value = NumberUtil.parseByte("-128");

            assertEquals(-128, value);
            assertEquals((byte)0, NumberUtil.parseByte(null));
            assertEquals((byte)0, NumberUtil.parseByte(""));
        }
        catch(ParseException e){
            fail(e.getMessage());
        }
    }
}
