package collections;

import collections.exceptions.InvalidWordException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class FineCompactWordTree implements CompactWordsSet {
  private final AtomicInteger size;
  private final LockableNode root;

  public FineCompactWordTree() {
    size = new AtomicInteger(0);
    root = new LockableNode('\0');
  }

  @Override
  public boolean add(String word) throws InvalidWordException {
    CompactWordsSet.checkIfWordIsValid(word);
    LockableNode curr = root;

    String remainingWord = word;
    while (remainingWord.length() > 0) {
      curr = curr.addChildIfNotPresent(remainingWord.charAt(0));
      remainingWord = remainingWord.substring(1);
    }

    if (curr.getIsWord()) {
      return false;
    } else {
      curr.setIsWord(true);
      size.incrementAndGet();
      return true;
    }
  }

  @Override
  public boolean remove(String word) throws InvalidWordException {
    CompactWordsSet.checkIfWordIsValid(word);
    LockableNode curr = root;

    String remainingWord = word;
    while (remainingWord.length() > 0) {
      LockableNode child = curr.getChildSynchronized(remainingWord.charAt(0));
      if (child == null) {
        return false;
      }
      curr = child;
      remainingWord = remainingWord.substring(1);
    }
    if (curr.getIsWord()) {
      curr.setIsWord(false);
      size.decrementAndGet();
      return true;
    } else {
      return false;
    }
  }

  @Override
  public boolean contains(String word) throws InvalidWordException {
    CompactWordsSet.checkIfWordIsValid(word);
    LockableNode curr = root;
    String remainingWord = word;
    while (remainingWord.length() > 0) {
      LockableNode child = curr.getChildSynchronized(remainingWord.charAt(0));
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
    return size.get();
  }

  @Override
  public List<String> uniqueWordsInAlphabeticOrder() {
    return root.uniqueWordsInAlphabeticOrder("", new ArrayList<>());
  }
}
