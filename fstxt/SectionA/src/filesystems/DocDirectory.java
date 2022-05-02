package filesystems;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DocDirectory extends DocFile {
  private Set<DocFile> files;

  DocDirectory(String name) {
    super(name);
    files = new HashSet<>();
  }

  @Override
  public int getSize() {
    return getName().length();
  }

  @Override
  public boolean isDirectory() {
    return true;
  }

  @Override
  public boolean isDataFile() {
    return false;
  }

  @Override
  public DocDirectory asDirectory() {
    return this;
  }

  @Override
  public DocDataFile asDataFile() {
    throw new UnsupportedOperationException();
  }

  @Override
  public DocFile duplicate() {
    final DocDirectory directory = new DocDirectory(getName());
    files.forEach(f -> {
      directory.addFile(f.duplicate());
    });
    return directory;
  }

  public boolean containsFile(String name) {
    return files.stream().map(DocFile::getName).toList().contains(name);
  }

  public Set<DocFile> getAllFiles() {
    return files;
  }

  public Set<DocDirectory> getDirectories() {
    return files.stream()
        .filter(f -> f instanceof DocDirectory)
        .map(d -> (DocDirectory) d)
        .collect(Collectors.toSet());
  }

  public Set<DocDataFile> getDataFiles() {
    return files.stream()
        .filter(f -> f instanceof DocDataFile)
        .map(d -> (DocDataFile) d)
        .collect(Collectors.toSet());
  }

  public void addFile(DocFile file) {
    if (containsFile(file.getName())) {
      throw new IllegalArgumentException("Duplicate filename");
    }
    files.add(file);
  }

  public boolean removeFile(String filename) {
    if (!containsFile(filename)) {
      return false;
    }
    return files.remove(getFile(filename));
  }

  public DocFile getFile(String filename) {
    return files.stream().filter(f -> f.getName().equals(filename)).toList().get(0);
  }
}