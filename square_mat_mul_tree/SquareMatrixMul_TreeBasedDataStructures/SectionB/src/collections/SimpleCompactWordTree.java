package collections;

import collections.exceptions.InvalidWordException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SimpleCompactWordTree implements CompactWordsSet {
  private int size;
  private Node root;

  public SimpleCompactWordTree() {
    size = 0;
    root = new Node('\0');
  }

  @Override
  public boolean add(String word) throws InvalidWordException {
    CompactWordsSet.checkIfWordIsValid(word);
    Node curr = root;
    Node parent = null;

    String remainingWord = word;
    while (remainingWord.length() > 0) {
      Node child = curr.getChild(remainingWord.charAt(0));
      if (child == null) {
        child = curr.addChild(new Node(remainingWord.charAt(0)));
      }
      parent = curr;
      curr = child;
      remainingWord = remainingWord.substring(1);
    }
    if (curr.getIsWord()) {
      return false;
    } else {
      curr.setIsWord(true);
      size++;
      return true;
    }
  }

  @Override
  public boolean remove(String word) throws InvalidWordException {
    CompactWordsSet.checkIfWordIsValid(word);
    Node curr = root;

    String remainingWord = word;
    while (remainingWord.length() > 0) {
      Node child = curr.getChild(remainingWord.charAt(0));
      if (child == null) {
        return false;
      }
      curr = child;
      remainingWord = remainingWord.substring(1);
    }
    if (curr.getIsWord()) {
      curr.setIsWord(false);
      size--;
      return true;
    } else {
      return false;
    }
  }

  @Override
  public boolean contains(String word) throws InvalidWordException {
    CompactWordsSet.checkIfWordIsValid(word);
    Node curr = root;
    String remainingWord = word;
    while (remainingWord.length() > 0) {
      Node child = curr.getChild(remainingWord.charAt(0));
      if (child == null) {
        return false;
      }
      curr = child;
      remainingWord = remainingWord.substring(1);
    }
    return true;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public List<String> uniqueWordsInAlphabeticOrder() {
    return root.uniqueWordsInAlphabeticOrder("", new ArrayList<>());
  }
}
