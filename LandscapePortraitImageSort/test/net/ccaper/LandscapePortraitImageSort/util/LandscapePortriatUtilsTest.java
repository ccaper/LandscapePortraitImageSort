package net.ccaper.LandscapePortraitImageSort.util;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;

import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LandscapePortriatUtilsTest {

  @Before
  public void setUp() throws Exception {
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void testGetOrientationFromDimensions() throws Exception {
    assertEquals(LandscapePortraitUtils.Orientation.LANDSCAPE,
        LandscapePortraitUtils.getOrientationFromDimensions(10, 5));
    assertEquals(LandscapePortraitUtils.Orientation.PORTRAIT,
        LandscapePortraitUtils.getOrientationFromDimensions(5, 10));
    assertEquals(LandscapePortraitUtils.Orientation.EQUAL,
        LandscapePortraitUtils.getOrientationFromDimensions(5, 5));
  }

  @Test
  public void testGetFileExtention() throws Exception {
    assertEquals("jpg",
        LandscapePortraitUtils.getFileExtension(new File("blah.jpg")));
    assertEquals(null, LandscapePortraitUtils.getFileExtension(null));
    assertEquals(null,
        LandscapePortraitUtils.getFileExtension(new File("blah")));
    assertEquals(null,
        LandscapePortraitUtils.getFileExtension(new File(StringUtils.EMPTY)));
  }

  @Test
  public void testGetImageReaderForImageFile() throws Exception {
    assertNotNull(LandscapePortraitUtils.getImageReaderForImageFile("jpg"));
    assertNull(LandscapePortraitUtils.getImageReaderForImageFile(null));
    assertNull(LandscapePortraitUtils
        .getImageReaderForImageFile(StringUtils.EMPTY));
    assertNull(LandscapePortraitUtils.getImageReaderForImageFile("txt"));
  }

  @Test
  public void testGetOrientationFromImageInputStream() throws Exception {
    ImageInputStream imageInputStreamMock = createMock(ImageInputStream.class);
    ImageReader imageReaderMock = createMock(ImageReader.class);
    expect(imageReaderMock.getMinIndex()).andReturn(0);
    expect(imageReaderMock.getWidth(0)).andReturn(5);
    expect(imageReaderMock.getHeight(0)).andReturn(10);
    imageReaderMock.setInput(imageInputStreamMock);
    replay(imageReaderMock);
    replay(imageInputStreamMock);
    assertEquals(LandscapePortraitUtils.Orientation.PORTRAIT,
        LandscapePortraitUtils.getOrientationFromImageInputStream(
            imageInputStreamMock, imageReaderMock));
    verify(imageReaderMock);
    verify(imageInputStreamMock);
  }
}