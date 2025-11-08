package br.com.concepting.framework.util;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;

import static org.junit.Assert.*;

public class FileUtilTest {
    @Test
    public void testReadValidFile() {
        URL url = getClass().getClassLoader().getResource("etc/resources/readme.txt");

        if(url == null)
            fail("Test file not found!");

        try{
            String content = FileUtil.fromTextFile(new File(url.getFile()));

            assertEquals("Hello World! This is a test file!" + StringUtil.getLineBreak(), content);

            content = FileUtil.fromTextFile(url.getFile());

            assertEquals("Hello World! This is a test file!" + StringUtil.getLineBreak(), content);
        }
        catch(IOException e){
            fail(e.getMessage());
        }
    }

    @Test
    public void testReadVInvalidFile() {
        try{
            assertNull(FileUtil.fromTextFile((File)null));
            assertNull(FileUtil.fromTextFile((String)null));
            assertNull(FileUtil.fromTextFile(""));
            assertNull(FileUtil.fromBinaryFile((File)null));
            assertNull(FileUtil.fromBinaryFile((String)null));
            assertNull(FileUtil.fromBinaryFile(""));

            FileUtil.fromTextFile(new File("invalidFile.txt"));

            fail("Should throw an IOException!");
        }
        catch(IOException ignored){
        }

        try{
            FileUtil.fromTextFile("invalidFile.txt");

            fail("Should throw an IOException!");
        }
        catch(IOException ignored){
        }
    }

    @Test
    public void testWriteToValidFile() {
        String content = "Hello World!" + StringUtil.getLineBreak();
        String fileName = FileUtil.getTempDirectory() + FileUtil.getDirectorySeparator() + "test.txt";

        try {
            FileUtil.toFile(fileName, content);

            File file = new File(fileName);

            assertTrue(file.exists());

            String fileContent = FileUtil.fromTextFile(fileName);

            assertEquals(content, fileContent);

            assertTrue(file.delete());

            byte[] bytes = new byte[10];

            for(int i = 0 ; i < bytes.length ; i++)
                bytes[i] = (byte)i;

            FileUtil.toFile(fileName, bytes);

            assertTrue(file.exists());

            byte[] fileBytes = FileUtil.fromBinaryFile(fileName);

            for(int i = 0 ; i < fileBytes.length ; i++)
                if(fileBytes[i] != bytes[i])
                    fail("File content is different from the original!");

            assertTrue(file.exists());
        }
        catch(IOException e){
            fail(e.getMessage());
        }
    }

    @Test
    public void testWriteToInvalidFile() {
        try {
            FileUtil.toFile((String) null, (String) null);
            FileUtil.toFile((String) null, (String) null, (String)null);
            FileUtil.toFile((String) null, (String) null, (String)null, false);
            FileUtil.toFile((String) null, (String) null, "");
            FileUtil.toFile((String) null, (String) null, "", false);

            FileUtil.toFile((String) null, "", false);
            FileUtil.toFile((String) null, "", (String)null);
            FileUtil.toFile((String) null, "", (String)null, false);
            FileUtil.toFile((String) null, "", "");
            FileUtil.toFile((String) null, "", "", false);

            FileUtil.toFile("", (String)null);
            FileUtil.toFile("", (String)null, (String)null);
            FileUtil.toFile("", (String)null, (String)null, false);
            FileUtil.toFile("", (String)null, "");
            FileUtil.toFile("", (String)null, "", false);

            FileUtil.toFile("", "");
            FileUtil.toFile("", "", (String)null);
            FileUtil.toFile("", "", (String)null, false);
            FileUtil.toFile("", "", "");
            FileUtil.toFile("", "", "", false);

            FileUtil.toFile((String)null, (byte[])null);
            FileUtil.toFile((String)null, new byte[1]);
            FileUtil.toFile("", (byte[])null);
            FileUtil.toFile("", new byte[1]);
        }
        catch(IOException e){
            fail(e.getMessage());
        }
    }

    @Test
    public void testSystemProperties() {
        assertEquals(FileSystems.getDefault().getSeparator(), FileUtil.getDirectorySeparator());
        assertEquals(System.getProperty("java.io.tmpdir"), FileUtil.getTempDirectory());
        assertEquals(Charset.defaultCharset().displayName(), FileUtil.getDefaultEncoding());
    }
}
