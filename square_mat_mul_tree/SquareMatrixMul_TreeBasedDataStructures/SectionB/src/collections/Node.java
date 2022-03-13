package collections;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Node implements Comparable<Node> {
  private final char val;
  private final List<Node> children;
  private boolean isWord;

  public Node(char val) {
    this.val = val;
    children = new ArrayList<>();
    isWord = false;
  }

  public Node getChild(char childVal) {
    for (Node childNode : children) {
      if (childNode.getElem() == childVal) {
        return childNode;
      }
    }
    return null;
  }

  public Node addChild(Node child) {
    children.add(child);
    return child;
  }

  // Pre: child must be present
  public void removeChild(Node child) {
    getChild(child.val).isWord = false;
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

  public List<Node> getSortedChildren() {
    return children
        .stream()
        .sorted(Comparator.comparingInt(Node::getElem)).toList();
  }

  public List<String> uniqueWordsInAlphabeticOrder(
      String currentWord,
      List<String> allStrings
  ) {
    for (Node child : getSortedChildren()) {
      final String currentString = currentWord + child.getElem();
      if (child.getIsWord()) {
        allStrings.add(currentString);
      }
      child.uniqueWordsInAlphabeticOrder(currentString, allStrings);
    }
    return allStrings;
  }

  @Override
  public int compareTo(Node o) {
    return 0;
  }
}