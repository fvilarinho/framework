package br.com.concepting.framework.processors;

import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.model.TestModel;
import br.com.concepting.framework.util.LanguageUtil;
import br.com.concepting.framework.util.helpers.XmlNode;
import org.junit.Test;

import java.util.Locale;

import static org.junit.Assert.*;

public class EvaluateProcessorTest {
    @Test
    public void testConstructorsAndGetters() throws InternalErrorException {
        XmlNode content = new XmlNode("root", "#{declaration}");
        EvaluateProcessor processor = new EvaluateProcessor("testDomain", "This is a test", content, Locale.ENGLISH);

        assertEquals("testDomain", processor.getDomain());
        assertNotNull(processor.getDeclaration());
        assertEquals(content, processor.getContent());
        assertEquals(Locale.ENGLISH, processor.getLanguage());

        processor = new EvaluateProcessor("testDomain", "This is a test", Locale.ENGLISH);

        assertEquals("testDomain", processor.getDomain());
        assertNotNull(processor.getDeclaration());
        assertNull(processor.getContent());
        assertEquals(Locale.ENGLISH, processor.getLanguage());

        processor = new EvaluateProcessor("testDomain", Locale.ENGLISH);

        assertEquals("testDomain", processor.getDomain());
        assertNull(processor.getDeclaration());
        assertNull(processor.getContent());
        assertEquals(Locale.ENGLISH, processor.getLanguage());

        processor = new EvaluateProcessor("testDomain", "This is a test", content);

        assertEquals("testDomain", processor.getDomain());
        assertEquals("This is a test", processor.getDeclaration());
        assertEquals(content, processor.getContent());
        assertEquals(LanguageUtil.getDefaultLanguage(), processor.getLanguage());

        processor = new EvaluateProcessor("testDomain", "This is a test");

        assertEquals("testDomain", processor.getDomain());
        assertEquals("This is a test", processor.getDeclaration());
        assertNull(processor.getContent());
        assertEquals(LanguageUtil.getDefaultLanguage(), processor.getLanguage());

        processor = new EvaluateProcessor("testDomain");

        assertEquals("testDomain", processor.getDomain());
        assertNull(processor.getDeclaration());
        assertNull(processor.getContent());
        assertEquals(LanguageUtil.getDefaultLanguage(), processor.getLanguage());

        processor = new EvaluateProcessor(Locale.ENGLISH);

        assertEquals(ExpressionProcessorUtil.class.getName(), processor.getDomain());
        assertNull(processor.getDeclaration());
        assertNull(processor.getContent());
        assertEquals(Locale.ENGLISH, processor.getLanguage());

        processor = new EvaluateProcessor(new Object());

        assertEquals(ExpressionProcessorUtil.class.getName(), processor.getDomain());
        assertNotNull(processor.getDeclaration());
        assertNull(processor.getContent());
        assertEquals(LanguageUtil.getDefaultLanguage(), processor.getLanguage());
    }

    @Test
    public void testSetters() throws InternalErrorException {
        EvaluateProcessor processor = new EvaluateProcessor();

        processor.setDomain("domainX");
        processor.setDeclaration("declarationX");

        XmlNode otherNode = new XmlNode("otherNode");

        processor.setContent(otherNode);
        processor.setLanguage(Locale.FRENCH);

        assertEquals("domainX", processor.getDomain());
        assertEquals("declarationX", processor.getDeclaration());
        assertEquals(otherNode, processor.getContent());
        assertEquals(Locale.FRENCH, processor.getLanguage());

        String expression = "new br.com.concepting.framework.model.TestModel('This is a test')";

        processor.setValue(expression);

        assertEquals(expression, processor.getValue());
    }

    @Test
    public void testHasLogicAndReturnContent() throws InternalErrorException {
        EvaluateProcessor processor = new EvaluateProcessor();

        assertTrue(processor.hasLogic());
        assertFalse(processor.returnContent());
    }

    @Test
    public void testLogic() throws InternalErrorException {
        String expression = "new br.com.concepting.framework.model.TestModel('This is a test')";
        EvaluateProcessor processor = new EvaluateProcessor("testDomain", Locale.ENGLISH);

        processor.setValue(expression);

        Object result = processor.evaluate();

        assertEquals(TestModel.class, result.getClass());
        assertEquals("This is a test", ((TestModel) result).getTestField());

        processor = new EvaluateProcessor("testDomain", new TestModel(), Locale.ENGLISH);
        processor.setValue("br.com.concepting.framework.model.TestModel.saySomething('This is a test')");

        result = processor.evaluate();

        assertNull(result);
    }

    @Test
    public void testErrorsInLogic() throws InternalErrorException {
        String expression = "new br.com.concepting.framework.model.TestModel2()";
        EvaluateProcessor processor = new EvaluateProcessor("testDomain", Locale.ENGLISH);

        processor.setValue(expression);

        try {
            processor.evaluate();

            fail();
        }
        catch(InternalErrorException e){
            assertEquals(ClassNotFoundException.class, e.getCause().getClass());
        }
    }

    @Test
    public void testNoLogic() throws InternalErrorException {
        EvaluateProcessor processor = new EvaluateProcessor("testDomain", Locale.ENGLISH);
        Object result = processor.evaluate();

        assertNull(result);
    }
}
