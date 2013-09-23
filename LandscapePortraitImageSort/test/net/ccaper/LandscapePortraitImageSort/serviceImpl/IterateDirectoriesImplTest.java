package net.ccaper.LandscapePortraitImageSort.serviceImpl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.ccaper.LandscapePortraitImageSort.service.IterateDirectories;
import net.ccaper.LandscapePortraitImageSort.spring.AppConfig;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class IterateDirectoriesImplTest {
  AppConfig appConfig = new AppConfig();

  @Before
  public void setUp() throws Exception {
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void testGetFile_NullFilesNullDirectories() {
    FilenameFilter extensionFilter = appConfig.getFilenameFilter();
    File startDirectoryMock = createMock(File.class);
    expect(startDirectoryMock.listFiles(extensionFilter)).andReturn(null);
    expect(startDirectoryMock.listFiles(IterateDirectoriesImpl.DIRECTORY_FILTER))
    .andReturn(null);
    replay(startDirectoryMock);
    IterateDirectories iterateDirs = new IterateDirectoriesImpl(startDirectoryMock,
        extensionFilter);
    File file = iterateDirs.getFile();
    List<File> files = new ArrayList<File>();
    while (file != null) {
      files.add(file);
      file = iterateDirs.getFile();
    }
    assertEquals(0, files.size());
    assertTrue(files.containsAll(Arrays.asList(new File[] {})));
    verify(startDirectoryMock);
  }

  @Test
  public void testGetFile_EmptyFilesNullDirectories() {
    File[] expectedFiles = new File[] {};
    FilenameFilter extensionFilter = appConfig.getFilenameFilter();
    File startDirectoryMock = createMock(File.class);
    expect(startDirectoryMock.listFiles(extensionFilter)).andReturn(expectedFiles);
    expect(startDirectoryMock.listFiles(IterateDirectoriesImpl.DIRECTORY_FILTER))
    .andReturn(null);
    replay(startDirectoryMock);
    IterateDirectories iterateDirs = new IterateDirectoriesImpl(startDirectoryMock,
        extensionFilter);
    File file = iterateDirs.getFile();
    List<File> files = new ArrayList<File>();
    while (file != null) {
      files.add(file);
      file = iterateDirs.getFile();
    }
    assertEquals(expectedFiles.length, files.size());
    assertTrue(files.containsAll(Arrays.asList(expectedFiles)));
    verify(startDirectoryMock);
  }

  @Test
  public void testGetFile_EmptyFilesEmptyDirectories() {
    File[] expectedFiles = new File[] {};
    File[] expectedDirectories = new File[] {};
    FilenameFilter extensionFilter = appConfig.getFilenameFilter();
    File startDirectoryMock = createMock(File.class);
    expect(startDirectoryMock.listFiles(extensionFilter)).andReturn(expectedFiles);
    expect(startDirectoryMock.listFiles(IterateDirectoriesImpl.DIRECTORY_FILTER))
    .andReturn(expectedDirectories);
    replay(startDirectoryMock);
    IterateDirectories iterateDirs = new IterateDirectoriesImpl(startDirectoryMock,
        extensionFilter);
    File file = iterateDirs.getFile();
    List<File> files = new ArrayList<File>();
    while (file != null) {
      files.add(file);
      file = iterateDirs.getFile();
    }
    assertEquals(expectedFiles.length, files.size());
    assertTrue(files.containsAll(Arrays.asList(expectedFiles)));
    verify(startDirectoryMock);
  }

  @Test
  public void testGetFile_OnlyFilesNoDirectories() {
    File[] expectedFiles = new File[] { new File("file1.jpg"),
        new File("file2.jpg") };
    FilenameFilter extensionFilter = appConfig.getFilenameFilter();
    File startDirectoryMock = createMock(File.class);
    expect(startDirectoryMock.listFiles(extensionFilter)).andReturn(expectedFiles);
    expect(startDirectoryMock.listFiles(IterateDirectoriesImpl.DIRECTORY_FILTER))
    .andReturn(null);
    replay(startDirectoryMock);
    IterateDirectories iterateDirs = new IterateDirectoriesImpl(startDirectoryMock,
        extensionFilter);
    File file = iterateDirs.getFile();
    List<File> files = new ArrayList<File>();
    while (file != null) {
      files.add(file);
      file = iterateDirs.getFile();
    }
    assertEquals(expectedFiles.length, files.size());
    assertTrue(files.containsAll(Arrays.asList(expectedFiles)));
    verify(startDirectoryMock);
  }

  @Test
  public void testGetFile_OnlyFilesInSubDirectory() {
    File startDirectoryMock = createMock(File.class);
    File dir1Mock = createMock(File.class);
    File[] expectedFilesTopLevel = new File[] {};
    File[] expectedDirectoriesTopLevel = new File[] { dir1Mock };
    File[] expectedFilesDir1Level = new File[] { new File("file3.jpg"),
        new File("file4.jpg") };
    FilenameFilter extensionFilter = appConfig.getFilenameFilter();
    expect(startDirectoryMock.listFiles(extensionFilter))
    .andReturn(expectedFilesTopLevel);
    expect(startDirectoryMock.listFiles(IterateDirectoriesImpl.DIRECTORY_FILTER))
    .andReturn(expectedDirectoriesTopLevel);
    expect(dir1Mock.listFiles(extensionFilter))
    .andReturn(expectedFilesDir1Level);
    expect(dir1Mock.listFiles(IterateDirectoriesImpl.DIRECTORY_FILTER))
    .andReturn(null);
    replay(startDirectoryMock);
    replay(dir1Mock);
    IterateDirectories iterateDirs = new IterateDirectoriesImpl(startDirectoryMock,
        extensionFilter);
    File file = iterateDirs.getFile();
    List<File> files = new ArrayList<File>();
    while (file != null) {
      files.add(file);
      file = iterateDirs.getFile();
    }
    List<File> allExpectedFiles = new ArrayList<File>(Arrays.asList(expectedFilesTopLevel));
    allExpectedFiles.addAll(Arrays.asList(expectedFilesDir1Level));
    assertEquals(allExpectedFiles.size(), files.size());
    assertTrue(files.containsAll(Arrays.asList(expectedFilesTopLevel)));
    assertTrue(files.containsAll(allExpectedFiles));
    verify(startDirectoryMock);
    verify(dir1Mock);
  }

  @Test
  public void testGetFile_FilesInStartDirAndFilesInSubDirectory() {
    File startDirectoryMock = createMock(File.class);
    File dir1Mock = createMock(File.class);
    File[] expectedFilesTopLevel = new File[] {new File("file1.jpg"),
        new File("file2.jpg")};
    File[] expectedDirectoriesTopLevel = new File[] { dir1Mock };
    File[] expectedFilesDir1Level = new File[] { new File("file3.jpg"),
        new File("file4.jpg") };
    FilenameFilter extensionFilter = appConfig.getFilenameFilter();
    expect(startDirectoryMock.listFiles(extensionFilter))
    .andReturn(expectedFilesTopLevel);
    expect(startDirectoryMock.listFiles(IterateDirectoriesImpl.DIRECTORY_FILTER))
    .andReturn(expectedDirectoriesTopLevel);
    expect(dir1Mock.listFiles(extensionFilter))
    .andReturn(expectedFilesDir1Level);
    expect(dir1Mock.listFiles(IterateDirectoriesImpl.DIRECTORY_FILTER))
    .andReturn(null);
    replay(startDirectoryMock);
    replay(dir1Mock);
    IterateDirectories iterateDirs = new IterateDirectoriesImpl(startDirectoryMock,
        extensionFilter);
    File file = iterateDirs.getFile();
    List<File> files = new ArrayList<File>();
    while (file != null) {
      files.add(file);
      file = iterateDirs.getFile();
    }
    List<File> allExpectedFiles = new ArrayList<File>(Arrays.asList(expectedFilesTopLevel));
    allExpectedFiles.addAll(Arrays.asList(expectedFilesDir1Level));
    assertEquals(allExpectedFiles.size(), files.size());
    assertTrue(files.containsAll(Arrays.asList(expectedFilesTopLevel)));
    assertTrue(files.containsAll(allExpectedFiles));
    verify(startDirectoryMock);
    verify(dir1Mock);
  }
}
