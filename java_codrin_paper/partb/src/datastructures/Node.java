package datastructures;

import domain.producttypes.ExchangeableGood;
import java.util.List;

public class Node {
  private final int key;
  private List<ExchangeableGood> items;
  private Node left;
  private Node right;

  public Node(int key, List<ExchangeableGood> items) {
    this.key = key;
    this.items = items;
  }

  public ExchangeableGood pop() {
    return items.remove(0);
  }

  public boolean isEmpty() {
    return items.size() == 0;
  }

  public void push(ExchangeableGood good) {
    items.add(good);
  }

  public int getKey() {
    return key;
  }

  public Node getLeft() {
    return left;
  }

  public void setLeft(Node left) {
    this.left = left;
  }

  public Node getRight() {
    return right;
  }

  public void setRight(Node right) {
    this.right = right;
  }
}
