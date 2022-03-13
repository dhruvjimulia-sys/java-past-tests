package collections;

import collections.exceptions.InvalidWordException;
import java.util.List;

public interface CompactWordsSet {

  static void checkIfWordIsValid(String word) throws InvalidWordException {
    if (word == null || word.equals("") ||
        word.chars().anyMatch(t -> ((int) 'a') > t || ((int) 'z') < t)) {
      throw new InvalidWordException("Word is invalid");
    }
  }

  boolean add(String word) throws InvalidWordException;

  boolean remove(String word) throws InvalidWordException;

  boolean contains(String word) throws InvalidWordException;

  int size();

  List<String> uniqueWordsInAlphabeticOrder();

}
