import java.util.Arrays;

public class Util {

  private static int letterToIndex(char letter) {
    final int index = (int) letter - (int) 'A';
    assert index >= 0 && index <= 25;
    return index;
  }

  private static int numberToIndex(char number) {
    return Integer.parseInt(String.valueOf(number));
  }

  public static Coordinate parseCoordinate(String s) {
    final int letterIndex = letterToIndex(s.charAt(0));
    assert letterIndex <= 25 && letterIndex >= 0;

    return new Coordinate(letterIndex, numberToIndex(s.charAt(1)));
  }

  public static Piece hideShip(Piece piece) {
    return piece == Piece.SHIP ? Piece.WATER : piece;
  }

  public static void hideShips(Piece[][] grid) {
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        grid[i][j] = hideShip(grid[i][j]);
      }
    }
  }

  public static Piece[][] deepClone(Piece[][] input) {
    Piece[][] output = new Piece[input.length][];
    for (int i = 0; i < input.length; i++) {
      output[i] = Arrays.copyOf(input[i], input[i].length);
    }
    return output;
  }
}
