package net.ccaper.LandscapePortraitImageSort.spring;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StringUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:net/ccaper/LandscapePortraitImageSort/spring/properties-config-test.xml" })
public class AppConfigTest {

  @Before
  public void setUp() throws Exception {
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void testWiring() throws Exception {
    File actual = (File) AppContextFactory.getContext().getBean("startDirectory");
    System.out.println("File: " + actual.getAbsolutePath());
    File expected = new File("/some/start/dir");
    assertEquals(expected, actual);
  }

  @Test
  public void testConvertSlashToOsFileDelimiter_UnixSeparator()
      throws Exception {
    assertEquals("C:/dir1/dir2/file.txt",
        AppConfig.convertSlashToOsFileDelimiter("C:\\dir1\\dir2\\file.txt",
            AppConfig.UNIX_FILE_DELIMETER));
    assertEquals("blah", AppConfig.convertSlashToOsFileDelimiter("blah",
        AppConfig.UNIX_FILE_DELIMETER));
    assertEquals("", AppConfig.convertSlashToOsFileDelimiter("",
        AppConfig.UNIX_FILE_DELIMETER));
    assertEquals(null, AppConfig.convertSlashToOsFileDelimiter(null,
        AppConfig.UNIX_FILE_DELIMETER));
  }

  @Test
  public void testConvertSlashToOsFileDelimiter_MsSeparator() throws Exception {
    assertEquals("\\dir1\\dir2\\file.txt",
        AppConfig.convertSlashToOsFileDelimiter("/dir1/dir2/file.txt",
            AppConfig.MS_FILE_DELIMETER));
    assertEquals("blah", AppConfig.convertSlashToOsFileDelimiter("blah",
        AppConfig.MS_FILE_DELIMETER));
    assertEquals(null, AppConfig.convertSlashToOsFileDelimiter(null,
        AppConfig.MS_FILE_DELIMETER));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConvertSlashToOsFileDelimiter_BadDelimiters()
      throws Exception {
    AppConfig.convertSlashToOsFileDelimiter("someString", "-");
    AppConfig.convertSlashToOsFileDelimiter("someString", "");
    AppConfig.convertSlashToOsFileDelimiter("someString", null);
  }

  @Test
  public void testGenerateFileFromString() throws Exception {
    String string = " dir1/dir2/file1.txt ";
    assertEquals(new File(StringUtils.trimAllWhitespace(string)),
        AppConfig.generateFileFromString(string));
  }

  @Test
  public void testGenerateFilesFromString() throws Exception {
    assertEquals(null, AppConfig.generateFilesFromString(null));
    List<File> expected = new ArrayList<File>();
    expected.add(new File("/some/path/File1.txt"));
    expected.add(new File("/some/path/File 2.txt"));
    expected.add(new File("/some/path/File3.txt"));
    assertEquals(
        expected,
        AppConfig
        .generateFilesFromString(" /some/path/File1.txt , /some/path/File 2.txt , /some/path/File3.txt "));
  }
}
