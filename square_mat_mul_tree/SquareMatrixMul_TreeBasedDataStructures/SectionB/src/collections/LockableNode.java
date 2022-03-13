package collections;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class LockableNode implements Comparable<LockableNode> {
  private final char val;
  private final List<LockableNode> children;
  private volatile boolean isWord;

  public LockableNode(char val) {
    this.val = val;
    children = new ArrayList<>();
    isWord = false;
  }

  public LockableNode getChild(char childVal) {
    for (LockableNode childNode : children) {
      if (childNode.getElem() == childVal) {
        return childNode;
      }
    }
    return null;
  }

  public LockableNode getChildSynchronized(char childVal) {
    synchronized (children) {
      return getChild(childVal);
    }
  }

  public LockableNode addChildIfNotPresent(char childVal) {
    synchronized (children) {
      LockableNode child = getChild(childVal);
      if (child == null) {
        child = new LockableNode(childVal);
        children.add(child);
      }
      return child;
    }
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

  public List<LockableNode> getSortedChildren() {
    return children.stream().sorted(Comparator.comparingInt(LockableNode::getElem)).toList();
  }

  public List<String> uniqueWordsInAlphabeticOrder(String currentWord, List<String> allStrings) {
    for (LockableNode child : getSortedChildren()) {
      final String currentString = currentWord + child.getElem();
      if (child.getIsWord()) {
        allStrings.add(currentString);
      }
      child.uniqueWordsInAlphabeticOrder(currentString, allStrings);
    }
    return allStrings;
  }

  @Override
  public int compareTo(LockableNode o) {
    return 0;
  }
}
