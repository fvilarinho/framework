package br.com.concepting.framework.processors;

import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.model.TestModel;
import br.com.concepting.framework.model.helpers.ModelInfo;
import br.com.concepting.framework.model.util.ModelUtil;
import br.com.concepting.framework.util.ByteUtil;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.XmlReader;
import br.com.concepting.framework.util.helpers.JavaIndent;
import br.com.concepting.framework.util.helpers.TagIndent;
import br.com.concepting.framework.util.helpers.XmlNode;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

import static org.junit.Assert.*;

public class GenericProcessorTest {
    private static final ProcessorFactory factory = ProcessorFactory.getInstance();

    private XmlNode contentWithLogic = null;
    private XmlNode contentWithNoLogic = null;
    private String validContentWithLogic = null;
    private String validContentWithNoLogic = null;

    @Before
    public void setup() throws IOException {
        XmlReader reader = new XmlReader(getClass().getClassLoader().getResourceAsStream("etc/resources/processors/contentWithLogic.xml"));

        this.contentWithLogic = reader.getRoot();

        reader = new XmlReader(getClass().getClassLoader().getResourceAsStream("etc/resources/processors/contentWithNoLogic.xml"));

        this.contentWithNoLogic = reader.getRoot();

        this.validContentWithLogic = new String(ByteUtil.fromTextStream(getClass().getClassLoader().getResourceAsStream("etc/resources/processors/validProcessedContentWithLogic.txt")), StandardCharsets.UTF_8);
        this.validContentWithNoLogic = new String(ByteUtil.fromTextStream(getClass().getClassLoader().getResourceAsStream("etc/resources/processors/validProcessedContentWithNoLogic.txt")), StandardCharsets.UTF_8);
    }

    @Test
    public void testConstructorsAndGetters() throws InternalErrorException {
        GenericProcessor processor = factory.getProcessor("testDomain", new Object(), this.contentWithLogic, Locale.ENGLISH);

        assertEquals("testDomain", processor.getDomain());
        assertEquals(Locale.ENGLISH, processor.getLanguage());
        assertNotNull(processor.getDeclaration());
        assertEquals(this.contentWithLogic, processor.getContent());
    }

    @Test
    public void testSetters() throws InternalErrorException {
        GenericProcessor processor = factory.getProcessor(null, null, (XmlNode) null, null);

        processor.setDomain("domainX");
        processor.setLanguage(Locale.FRENCH);
        processor.setDeclaration("declarationX");

        XmlNode otherNode = new XmlNode("otherNode");

        processor.setContent(otherNode);

        assertEquals("domainX", processor.getDomain());
        assertEquals(Locale.FRENCH, processor.getLanguage());
        assertEquals("declarationX", processor.getDeclaration());
        assertEquals(otherNode, processor.getContent());
    }

    @Test
    public void testHasLogicAndReturnContent() throws InternalErrorException {
        GenericProcessor processor = factory.getProcessor("testDomain", new Object(), this.contentWithLogic, Locale.ENGLISH);

        assertFalse(processor.hasLogic());
        assertTrue(processor.returnContent());
    }

    @Test
    public void testWithLogic() throws InternalErrorException {
        try {
            ModelInfo modelInfo = ModelUtil.getInfo(TestModel.class);
            GenericProcessor processor = factory.getProcessor("testDomain", modelInfo, this.contentWithLogic, Locale.ENGLISH);
            String result = StringUtil.indent(processor.process(), JavaIndent.getRules());

            assertEquals(StringUtil.trim(this.validContentWithLogic), StringUtil.trim(result));
        }
        catch(Throwable e){
            throw new InternalErrorException(e);
        }
    }

    @Test
    public void testWithNoLogic() throws InternalErrorException {
        try {
            GenericProcessor processor = factory.getProcessor("testDomain", "Darth Vader", this.contentWithNoLogic, Locale.ENGLISH);
            String result = StringUtil.indent(processor.process(), TagIndent.getRules());

            assertEquals(StringUtil.trim(this.validContentWithNoLogic), StringUtil.trim(result));
        }
        catch(Throwable e){
            throw new InternalErrorException(e);
        }
    }
}
