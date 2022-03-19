import java.util.Iterator;

public interface PriorityQueue<T> extends Iterable<Node<T>> {
  void add(double priority, T element);

  T dequeue();

  boolean isEmpty();

  Iterator<Node<T>> iterator();
}
