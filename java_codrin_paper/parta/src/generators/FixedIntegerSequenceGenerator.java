package generators;

public class FixedIntegerSequenceGenerator implements IntegerGenerator {
  private int curr = -1;

  @Override
  public Integer next() {
    if (!hasNext()) {
      throw new UnsupportedOperationException("No next exists");
    }
    curr++;
    return curr;
  }

  @Override
  public boolean hasNext() {
    return curr >= -1 && curr < 29;
  }
}
