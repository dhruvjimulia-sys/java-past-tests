package generators;

public class MissingPrimesGenerator implements IntegerGenerator {
  private int curr;

  @Override
  public Integer next() {
    curr++;
    if ((curr % 3 == 0 && curr % 5 != 0) || (curr % 3 != 0 && curr % 5 == 0)) {
      return next();
    } else {
      return curr;
    }
  }

  @Override
  public boolean hasNext() {
    return true;
  }
}
