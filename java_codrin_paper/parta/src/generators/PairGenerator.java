package generators;

public class PairGenerator<S, T> implements DataGenerator<Pair<S, T>> {
  private final DataGenerator<S> firstGenerator;
  private final DataGenerator<T> secondGenerator;

  public PairGenerator(DataGenerator<S> firstGenerator,
      DataGenerator<T> secondGenerator) {
    this.firstGenerator = firstGenerator;
    this.secondGenerator = secondGenerator;
  }

  @Override
  public Pair<S, T> next() {
    return new Pair<>(firstGenerator.next(), secondGenerator.next());
  }

  @Override
  public boolean hasNext() {
    return firstGenerator.hasNext() && secondGenerator.hasNext();
  }
}
