package datastructures;

import domain.Agent;
import domain.producttypes.ExchangeableGood;
import java.util.ArrayList;
import java.util.Optional;

public class StockImpl<E extends ExchangeableGood> implements Stock<E> {
  private Node root;
  private int size;

  @Override
  public void push(E item, Agent agent) {
    if (root == null) {
      root = new Node(agent.id, new ArrayList<>());
      root.push(item);
      size++;
      return;
    }

    Node curr = root;
    Node parent = null;
    while (curr != null) {
      parent = curr;
      if (curr.getKey() == agent.id) {
        curr.push(item);
        size++;
        return;
      } else if (curr.getKey() > agent.id) {
        curr = curr.getLeft();
      } else {
        curr = curr.getRight();
      }
    }

    Node newNode = new Node(agent.id, new ArrayList<>());
    if (parent.getKey() > agent.id) {
      parent.setLeft(newNode);
    } else {
      parent.setRight(newNode);
    }
    size++;
    newNode.push(item);
  }

  @Override
  public Optional<E> pop() {
    // Hint: always returns a product from the highest priority node. If a node gets to zero
    // products, it should be removed. Because this structure is a BST with nodes sorted by
    // agent.id,
    // the highest priority node should be the rightmost node, which can only be either a leaf or a
    // node with a single child (the left one).
    if (root == null) {
      return Optional.empty();
    }
    Node curr = root;
    Node parent = null;
    while (curr.getRight() != null) {
      parent = curr;
      curr = curr.getRight();
    }
    final Optional<E> item = Optional.of((E) curr.pop());
    if (curr.isEmpty()) {
      if (parent == null) {
        root = curr.getLeft();
      } else {
        parent.setRight(curr.getLeft());
      }
    }
    size--;
    return item;
  }

  @Override
  public int size() {
    return size;
  }
}
