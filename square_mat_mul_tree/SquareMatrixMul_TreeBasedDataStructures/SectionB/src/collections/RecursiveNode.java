package collections;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RecursiveNode implements Comparable<RecursiveNode> {
  private final char val;
  private final List<RecursiveNode> children;
  private boolean isWord;

  public RecursiveNode(char val) {
    this.val = val;
    children = new ArrayList<>();
    isWord = false;
  }

  public RecursiveNode getChild(char childVal) {
    for (RecursiveNode childNode : children) {
      if (childNode.getElem() == childVal) {
        return childNode;
      }
    }
    return null;
  }

  public RecursiveNode addChild(RecursiveNode child) {
    children.add(child);
    return child;
  }

  public boolean add(String word) {
    if (word.length() > 0) {
      RecursiveNode child = getChild(word.charAt(0));
      if (child == null) {
        child = addChild(new RecursiveNode(word.charAt(0)));
      }
      return child.add(word.substring(1));
    }
    if (getIsWord()) {
      return false;
    } else {
      setIsWord(true);
      return true;
    }
  }

  public boolean remove(String word) {
    if (word.length() > 0) {
      RecursiveNode child = getChild(word.charAt(0));
      if (child == null) {
        return false;
      }
      return child.remove(word.substring(1));
    }
    if (getIsWord()) {
      setIsWord(false);
      return true;
    } else {
      return false;
    }
  }

  public boolean contains(String word) {
    if (word.length() > 0) {
      RecursiveNode child = getChild(word.charAt(0));
      if (child == null) {
        return false;
      }
      return child.contains(word.substring(1));
    }
    return true;
  }

  public void setIsWord(boolean isWord) {
    this.isWord = isWord;
  }

  public boolean getIsWord() {
    return isWord;
  }

  public char getElem() {
    return val;
  }

  public List<RecursiveNode> getSortedChildren() {
    return children.stream().sorted(Comparator.comparingInt(RecursiveNode::getElem)).toList();
  }

  public List<String> uniqueWordsInAlphabeticOrder(String currentWord, List<String> allStrings) {
    for (RecursiveNode child : getSortedChildren()) {
      final String currentString = currentWord + child.getElem();
      if (child.getIsWord()) {
        allStrings.add(currentString);
      }
      child.uniqueWordsInAlphabeticOrder(currentString, allStrings);
    }
    return allStrings;
  }

  @Override
  public int compareTo(RecursiveNode o) {
    return 0;
  }
}
