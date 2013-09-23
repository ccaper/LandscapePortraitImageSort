package net.ccaper.LandscapePortraitImageSort.serviceImpl;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import net.ccaper.LandscapePortraitImageSort.service.IterateDirectories;

public class IterateDirectoriesImpl implements IterateDirectories {
  // visible for testing
  static final FilenameFilter DIRECTORY_FILTER = new FilenameFilter() {
    @Override
    public boolean accept(File dir, String name) {
      return new File(dir, name).isDirectory();
    }
  };
  private final Queue<File> files = new LinkedList<File>();
  private final Queue<File> dirs = new LinkedList<File>();
  private final FilenameFilter extensionFilter;
  private final File startDirectory;
  private boolean filesAndDirsSeeded = false;

  public IterateDirectoriesImpl(File startDirectory,
      FilenameFilter extensionFilter) {
    this.extensionFilter = extensionFilter;
    this.startDirectory = startDirectory;
  }

  @Override
  // TODO: test
  public File getFile() {
    if (filesAndDirsSeeded == false) {
      seedFilesAndDirs();
    }
    if (!files.isEmpty()) {
      return files.remove();
    } else if (!dirs.isEmpty()) {
      File dir = dirs.remove();
      files.addAll(Arrays.asList(dir.listFiles(extensionFilter)));
      dirs.addAll(Arrays.asList((dir.listFiles(DIRECTORY_FILTER))));
      return getFile();
    } else {
      return null;
    }
  }

  // TODO: test
  private void seedFilesAndDirs() {
    if (startDirectory != null) {
      File[] filesArray = startDirectory.listFiles(this.extensionFilter);
      if (filesArray != null) {
        files.addAll(Arrays.asList(filesArray));
      }
      File[] dirsArray = startDirectory.listFiles(this.extensionFilter);
      if (dirsArray != null) {
        dirs.addAll(Arrays.asList(dirsArray));
      }
    }
    filesAndDirsSeeded = true;
  }
}
