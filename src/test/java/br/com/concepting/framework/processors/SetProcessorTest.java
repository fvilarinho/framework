package br.com.concepting.framework.processors;

import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.util.LanguageUtil;
import br.com.concepting.framework.util.LanguageUtilTest;
import br.com.concepting.framework.util.helpers.XmlNode;
import org.junit.Test;

import java.util.Locale;

import static org.junit.Assert.*;

public class SetProcessorTest {
    @Test
    public void testConstructorsAndGetters() throws InternalErrorException {
        XmlNode content = new XmlNode("root", "#{declaration}");
        SetProcessor processor = new SetProcessor("testDomain", "This is a test", content, Locale.ENGLISH);

        assertEquals("testDomain", processor.getDomain());
        assertNotNull(processor.getDeclaration());
        assertEquals(content, processor.getContent());
        assertEquals(Locale.ENGLISH, processor.getLanguage());

        processor = new SetProcessor("testDomain", "This is a test", Locale.ENGLISH);

        assertEquals("testDomain", processor.getDomain());
        assertNotNull(processor.getDeclaration());
        assertNull(processor.getContent());
        assertEquals(Locale.ENGLISH, processor.getLanguage());

        processor = new SetProcessor("testDomain", "This is a test");

        assertEquals("testDomain", processor.getDomain());
        assertEquals("This is a test", processor.getDeclaration());
        assertNull(processor.getContent());
        assertEquals(LanguageUtil.getDefaultLanguage(), processor.getLanguage());

        processor = new SetProcessor(new Object(), content, Locale.ENGLISH);

        assertEquals(ExpressionProcessorUtil.class.getName(), processor.getDomain());
        assertEquals(Object.class, processor.getDeclaration().getClass());
        assertEquals(content, processor.getContent());
        assertEquals(Locale.ENGLISH, processor.getLanguage());

        processor = new SetProcessor(new Object(), Locale.ENGLISH);

        assertEquals(ExpressionProcessorUtil.class.getName(), processor.getDomain());
        assertEquals(Object.class, processor.getDeclaration().getClass());
        assertNull(processor.getContent());
        assertEquals(Locale.ENGLISH, processor.getLanguage());

        processor = new SetProcessor(new Object());

        assertEquals(ExpressionProcessorUtil.class.getName(), processor.getDomain());
        assertEquals(Object.class, processor.getDeclaration().getClass());
        assertNull(processor.getContent());
        assertEquals(LanguageUtil.getDefaultLanguage(), processor.getLanguage());
    }

    @Test
    public void testSetters() throws InternalErrorException {
        SetProcessor processor = new SetProcessor();

        processor.setDomain("domainX");
        processor.setName("variableX");

        assertEquals("domainX", processor.getDomain());
        assertEquals("variableX", processor.getName());
    }

    @Test
    public void testHasLogicAndReturnContent() throws InternalErrorException {
        EvaluateProcessor processor = new EvaluateProcessor();

        assertTrue(processor.hasLogic());
        assertFalse(processor.returnContent());
    }

    @Test
    public void testWithLogic() throws InternalErrorException {
        SetProcessor processor = new SetProcessor();

        processor.setDomain("domainX");
        processor.setName("anotherVariable");
        processor.setValue("This is another test");
        processor.evaluate();

        assertEquals("This is another test", ExpressionProcessorUtil.getVariable("domainX", "anotherVariable"));
    }
}
