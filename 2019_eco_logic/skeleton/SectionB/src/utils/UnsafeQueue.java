package utils;

import java.util.Optional;

public class UnsafeQueue<E> implements Queue<E> {
  private Node<E> head;
  private int size;

  @Override
  public void push(E element) {
    head = new Node<>(element, head);
    size++;
  }

  @Override
  public Optional<E> pop() {
    if (size() == 0) {
      return Optional.empty();
    }
    Node<E> prev = head;
    Node<E> curr = prev.getNext();

    while (curr.getNext() != null) {
      prev = prev.getNext();
      curr = curr.getNext();
    }

    prev.setNext(null);
    size--;
    return Optional.of(curr.getValue());
  }

  @Override
  public int size() {
    return size;
  }
}
