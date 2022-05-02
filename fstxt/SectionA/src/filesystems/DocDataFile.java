package filesystems;

import java.util.Arrays;

public final class DocDataFile extends DocFile {
  private final byte[] contents;

  public DocDataFile(String name, byte[] contents) {
    super(name);
    this.contents = contents;
  }

  @Override
  public int getSize() {
    return getName().length() + contents.length;
  }

  @Override
  public boolean isDirectory() {
    return false;
  }

  @Override
  public boolean isDataFile() {
    return true;
  }

  @Override
  public DocDirectory asDirectory() {
    throw new UnsupportedOperationException();
  }

  @Override
  public DocDataFile asDataFile() {
    return this;
  }

  @Override
  public DocFile duplicate() {
    return new DocDataFile(getName(), contents);
  }

  public boolean containsByte(byte content) {
    for (byte b : contents) {
      if (b == content) {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof DocDataFile)) {
      return false;
    }
    DocDataFile that = (DocDataFile) o;
    return Arrays.equals(contents, that.contents) && that.getName().equals(getName());
  }

  @Override
  public int hashCode() {
    return contents.length == 0 ? 0 : contents[0];
  }
}
