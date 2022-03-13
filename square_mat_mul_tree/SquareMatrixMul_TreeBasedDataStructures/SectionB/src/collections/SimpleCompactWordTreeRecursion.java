package collections;

import collections.exceptions.InvalidWordException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SimpleCompactWordTreeRecursion implements CompactWordsSet {

  private final Lock lock;
  private int size;
  private RecursiveNode root;

  public SimpleCompactWordTreeRecursion() {
    size = 0;
    root = new RecursiveNode('\0');
    lock = new ReentrantLock();
  }

  @Override
  public boolean add(String word) throws InvalidWordException {
    lock.lock();
    try {
      CompactWordsSet.checkIfWordIsValid(word);
      boolean added = root.add(word);
      if (added) {
        size++;
      }
      return added;
    } finally {
      lock.unlock();
    }
  }

  @Override
  public boolean remove(String word) throws InvalidWordException {
    lock.lock();
    try {
      CompactWordsSet.checkIfWordIsValid(word);
      boolean removed = root.remove(word);
      if (removed) {
        size--;
      }
      return removed;
    } finally {
      lock.unlock();
    }
  }

  @Override
  public boolean contains(String word) throws InvalidWordException {
    CompactWordsSet.checkIfWordIsValid(word);
    lock.lock();
    try {
      RecursiveNode curr = root;
      String remainingWord = word;
      while (remainingWord.length() > 0) {
        RecursiveNode child = curr.getChild(remainingWord.charAt(0));
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
