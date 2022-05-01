package generators;

public class DigitCombinationsGenerator implements StringGenerator {
  private int totalLength;
  private int counter;
  private int length;
  private static final int[] LIST = {2, 3, 4, 5};

  @Override
  public String next() {
    if (counter == 0) {
      counter++;
      return "";
    } else {
      if (isPowerOfFour(counter - 1)) {
        if (totalLength == length) {
          length++;
          totalLength = 0;
          counter = 1;
        } else {
          totalLength++;
        }
      }
      final String baseFour =
          increaseLength(convertToBaseFour(String.valueOf(counter - 1)));
      final StringBuilder sb = new StringBuilder();
      for (int i = 0; i < baseFour.length(); i++) {
        sb.append(LIST[Integer.parseInt(String.valueOf(baseFour.charAt(i)))]);
      }
      counter++;
      return sb.toString();
    }
  }

  private static String convertToBaseFour(String str) {
    return Integer.toString(Integer.parseInt(str, 10), 4);
  }

  private static boolean isPowerOfFour(int n) {
    if (n == 0) {
      return false;
    }
    while (n != 4) {
      if (n % 4 != 0) {
        return false;
      }
      n /= 4;
    }
    return true;
  }

  private String increaseLength(String str) {
    String curr = str;
    while (curr.length() <= length) {
      curr = "0" + curr;
    }
    return curr;
  }

  @Override
  public boolean hasNext() {
    return true;
  }
}
