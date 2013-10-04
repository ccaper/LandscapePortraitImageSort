package net.ccaper.LandscapePortraitImageSort.util;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LandscapePortraitUtils {
  private static final Log LOG = LogFactory
      .getLog(LandscapePortraitUtils.class);

  public enum Orientation {
    LANDSCAPE, PORTRAIT, EQUAL
  };

  public Orientation getImageOrientation(File file) {
    if (file == null) {
      return null;
    }
    String fileExtension = getFileExtension(file);
    ImageReader imageReader = getImageReaderForImageFile(fileExtension);
    if (imageReader == null) {
      LOG.error(String.format(
          "Could not find an image reader for image type '%s' for file '%s'.",
          fileExtension, file.getAbsolutePath()));
      return null;
    }
    return getOrientationFromFile(file, imageReader);
  }

  // visible for testing
  Orientation getOrientationFromFile(File file, ImageReader imageReader) {
    ImageInputStream imageInputStream = null;
    if (imageReader == null) {
      return null;
    }
    try {
      imageInputStream = getFileImageInputStream(file);
      return getOrientationFromImageInputStream(imageInputStream, imageReader);
    } catch (IOException e) {
      LOG.error(String.format("IOException while reading %s.",
          file.getAbsolutePath()), e);
      return null;
    } finally {
      imageReader.dispose();
      try {
        if (imageInputStream != null) {
          imageInputStream.close();
        }
      } catch (IOException e) {
        LOG.error(
            String.format("IOException while closing %s.",
                file.getAbsolutePath()), e);
      }
    }
  }

  // visible for testing
  FileImageInputStream getFileImageInputStream(File file) throws IOException {
    return new FileImageInputStream(file);
  }

  // visible for testing
  Orientation getOrientationFromImageInputStream(
      ImageInputStream imageInputStream, ImageReader imageReader)
      throws IOException {
    if (imageInputStream == null) {
      return null;
    }
    imageReader.setInput(imageInputStream);
    int minIndex = imageReader.getMinIndex();
    int width = imageReader.getWidth(minIndex);
    int height = imageReader.getHeight(minIndex);
    return getOrientationFromDimensions(width, height);
  }

  // visible for testing
  ImageReader getImageReaderForImageFile(String fileExtension) {
    if (StringUtils.isEmpty(fileExtension)) {
      return null;
    }
    Iterator<ImageReader> iter = ImageIO.getImageReadersBySuffix(fileExtension);
    if (iter.hasNext()) {
      return iter.next();
    } else {
      return null;
    }
  }

  // visible for testing
  static String getFileExtension(File file) {
    if (file == null) {
      return null;
    }
    if (!file.getName().contains(".")) {
      return null;
    }
    return file.getName().substring(file.getName().lastIndexOf('.') + 1);
  }

  // visible for testing
  static Orientation getOrientationFromDimensions(int width, int height) {
    if (height > width) {
      return Orientation.PORTRAIT;
    } else if (height < width) {
      return Orientation.LANDSCAPE;
    } else {
      return Orientation.EQUAL;
    }
  }
}
