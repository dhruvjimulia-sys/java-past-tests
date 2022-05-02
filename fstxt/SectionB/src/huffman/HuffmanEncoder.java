package huffman;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.TreeMap;

public class HuffmanEncoder {

  final HuffmanNode root;
  final Map<String, String> word2bitsequence;

  private HuffmanEncoder(HuffmanNode root, Map<String, String> word2bitSequence) {
    this.root = root;
    this.word2bitsequence = word2bitSequence;
  }

  public static HuffmanEncoder buildEncoder(Map<String, Integer> wordCounts) {
    if (wordCounts == null) {
      throw new HuffmanEncoderException("wordCounts cannot be null");
    }
    if (wordCounts.size() < 2) {
      throw new HuffmanEncoderException("This encoder requires at least two different words");
    }

    // fixing the order in which words will be processed: this determinize the execution and makes
    // tests reproducible.
    TreeMap<String, Integer> sortedWords = new TreeMap<>(wordCounts);
    PriorityQueue<HuffmanNode> queue = new PriorityQueue<>(sortedWords.size());

    // YOUR IMPLEMENTATION HERE...
    for (Map.Entry<String, Integer> entry : sortedWords.entrySet()) {
      queue.offer(new HuffmanLeaf(entry.getValue(), entry.getKey()));
    }
    while (queue.size() > 1) {
      final HuffmanNode fistNode = queue.poll();
      final HuffmanNode secondNode = queue.poll();
      queue.offer(new HuffmanInternalNode(fistNode, secondNode));
    }
    final HuffmanNode root = queue.poll();
    final Map<String, String> wordbitmap = new HashMap<>();
    return new HuffmanEncoder(root, traverse(root, "", wordbitmap));
  }

  private static Map<String, String> traverse(HuffmanNode currNode,
      String currString, Map<String, String> wordbitmap) {
    if (currNode instanceof HuffmanLeaf) {
      wordbitmap.put(((HuffmanLeaf) currNode).word, currString);
    } else {
      final HuffmanInternalNode internalNode = (HuffmanInternalNode) currNode;
      traverse(internalNode.left, currString + "0", wordbitmap);
      traverse(internalNode.right, currString + "1", wordbitmap);
    }
    return wordbitmap;
  }

  public String compress(List<String> text) {
    if (!text.stream().allMatch(word2bitsequence::containsKey)) {
      throw new HuffmanEncoderException("Word not in key set of map");
    }
    return text.stream()
        .map(word2bitsequence::get)
        .reduce("", (a, b) -> a + b);
  }

  public List<String> decompress(String compressedText) {
    final List<String> list = new ArrayList<>();
    while (compressedText.length() != 0) {
      HuffmanNode curr = root;
      int counter = 0;
      while (curr instanceof HuffmanInternalNode) {
        if (compressedText.length() == counter - 1) {
          throw new HuffmanEncoderException(
              "Compression does not represent word"
          );
        }
        if (compressedText.charAt(counter) == '0') {
          curr = ((HuffmanInternalNode) curr).left;
        } else {
          curr = ((HuffmanInternalNode) curr).right;
        }
        counter++;
      }
      list.add(((HuffmanLeaf) curr).word);
      compressedText = compressedText.substring(counter + 1);
    }
    return list;
  }

  private String findWord(Queue<Character> characterQueue, HuffmanNode curr) {
    if (curr instanceof HuffmanLeaf) {
      return ((HuffmanLeaf) curr).word;
    }
    if (characterQueue.isEmpty()) {
      throw new HuffmanEncoderException("Invalid Compressed Text");
    }

    Character bit = characterQueue.poll();
    if (bit == '0') {
      return findWord(characterQueue, ((HuffmanInternalNode) curr).left);
    } else {
      return findWord(characterQueue, ((HuffmanInternalNode) curr).right);
    }
  }

  // Below the classes representing the tree's nodes. There should be no need to modify them, but
  // feel free to do it if you see it fit

  private abstract static class HuffmanNode implements Comparable<HuffmanNode> {

    private final int count;

    public HuffmanNode(int count) {
      this.count = count;
    }

    @Override
    public int compareTo(HuffmanNode otherNode) {
      return count - otherNode.count;
    }
  }

  private static class HuffmanLeaf extends HuffmanNode {

    private final String word;

    public HuffmanLeaf(int frequency, String word) {
      super(frequency);
      this.word = word;
    }
  }

  private static class HuffmanInternalNode extends HuffmanNode {

    private final HuffmanNode left;
    private final HuffmanNode right;

    public HuffmanInternalNode(HuffmanNode left, HuffmanNode right) {
      super(left.count + right.count);
      this.left = left;
      this.right = right;
    }
  }
}
