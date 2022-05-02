package huffman;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Utility {

  public static List<String> getWords(String filePath) {
    List<String> words = null;
    try (Stream<String> linesStream = Files.lines(Paths.get(filePath))) {
      words =
          linesStream
              .flatMap(line -> Arrays.stream(line.split(" ")))
              .map(word -> word.trim())
              .collect(Collectors.toList());
    } catch (IOException e) {
      e.printStackTrace();
    }
    return words;
  }

  public static String sequenceOfBitsAsNumber(String binaryEncoding) {
    final String binaryEncodingWithHeading1 =
        "1" + binaryEncoding; // Prepending 1 not to lose heading zeroes
    BigInteger result = new BigInteger(binaryEncodingWithHeading1, 2);
    return result.toString();
  }

  public static String numberAsSequenceOfBits(String numberRepresentation) {
    BigInteger number = new BigInteger(numberRepresentation);
    String binaryRepresentation = number.toString(2);
    return binaryRepresentation.substring(1); // Removing previously prepended 1
  }

  public static long totalLength(List<String> words) {
    long length = words.size() - 1; // White spaces
    length += words.stream().mapToLong(w -> w.length()).sum();
    return length;
  }

  public static Map<String, Integer> countWords(List<String> words) {
    final int THREAD_NUM = 8;

    final Counter[] threads = new Counter[THREAD_NUM];
    final int SUBLIST_LENGTH = words.size() / THREAD_NUM;
    for (int i = 0; i < THREAD_NUM - 1; i++) {
      threads[i] = new Counter(
          words,
          i * SUBLIST_LENGTH,
          (i + 1) * SUBLIST_LENGTH);
    }
    threads[THREAD_NUM - 1] = new Counter(words,
        (THREAD_NUM - 1) * SUBLIST_LENGTH, words.size());

    for (Thread thread : threads) {
      thread.start();
    }

    for (Thread thread : threads) {
      try {
        thread.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    final Map<String, Integer> map = new HashMap<>();
    for (Counter counter : threads) {
      for (Map.Entry<String, Integer> entry : counter.getResult().entrySet()) {
        if (!map.containsKey(entry.getKey())) {
          map.put(entry.getKey(), entry.getValue());
        } else {
          map.put(entry.getKey(), map.get(entry.getKey()) + entry.getValue());
        }
      }
    }
    return map;
  }
}
