import java.util.Iterator;

public class LinkedListPriorityQueue<T> implements PriorityQueue<T> {
  private Node<T> head;

  public LinkedListPriorityQueue() {
    this.head = null;
  }

  @Override
  public void add(double priority, T element) {
    final Node<T> newNode = new Node<>(priority, element);
    if (head == null) {
      head = newNode;
    } else {
      Node<T> prev = null;
      Node<T> curr = head;
      while (curr != null && curr.getPriority() < priority) {
        prev = curr;
        curr = curr.getNext();
      }
      newNode.setNext(curr);
      if (prev != null) {
        prev.setNext(newNode);
      } else {
        head = newNode;
      }
    }
  }

  @Override
  public T dequeue() {
    if (isEmpty()) {
      return null;
    } else {
      final T headValue = head.getValue();
      head = head.getNext();
      return headValue;
    }
  }

  @Override
  public boolean isEmpty() {
    return head == null;
  }

  @Override
  public Iterator<Node<T>> iterator() {
    return new Iterator<Node<T>>() {
      private Node<T> curr = null;

      @Override
      public boolean hasNext() {
        return curr == null || curr.getNext() != null;
      }

      @Override
      public Node<T> next() {
        curr = (curr == null) ? head : curr.getNext();
        return curr;
      }

      @Override
      public void remove() {}
    };
  }
}
