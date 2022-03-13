package collections;

import collections.exceptions.InvalidWordException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SimpleCompactWordTree implements CompactWordsSet {
  private int size;
  private Node root;
  private final Lock lock;

  public SimpleCompactWordTree() {
    size = 0;
    root = new Node('\0');
    lock = new ReentrantLock();
  }

  @Override
  public boolean add(String word) throws InvalidWordException {
    lock.lock();
    try {
      CompactWordsSet.checkIfWordIsValid(word);
      Node curr = root;

      String remainingWord = word;
      while (remainingWord.length() > 0) {
        Node child = curr.getChild(remainingWord.charAt(0));
        if (child == null) {
          child = curr.addChild(new Node(remainingWord.charAt(0)));
        }
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
    } finally {
      lock.unlock();
    }
  }

  @Override
  public boolean remove(String word) throws InvalidWordException {
    lock.lock();
    try {
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
    } finally {
      lock.unlock();
    }
  }

  @Override
  public boolean contains(String word) throws InvalidWordException {
    CompactWordsSet.checkIfWordIsValid(word);
    lock.lock();
    try {
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
    } finally {
      lock.unlock();
    }
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
