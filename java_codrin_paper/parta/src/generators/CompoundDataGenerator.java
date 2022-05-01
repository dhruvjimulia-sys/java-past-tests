package generators;

import java.util.List;

public class CompoundDataGenerator<T> implements DataGenerator<T> {
  final List<DataGenerator<T>> list;
  int index;

  public CompoundDataGenerator(List<DataGenerator<T>> list) {
    this.list = list;
  }

  @Override
  public T next() {
    if (list.get(index).hasNext()) {
      return list.get(index).next();
    } else {
      index++;
      return next();
    }
  }

  @Override
  public boolean hasNext() {
    return index != list.size() - 1 || list.get(index).hasNext();
  }
}
