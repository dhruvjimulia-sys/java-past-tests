package generalmatrices.matrix;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;

public class Matrix<T> {
  private final List<List<T>> matrix;
  private final int order;

  // Pre: list.length is perfect square
  public Matrix(List<T> list) {
    if (list.size() == 0) {
      throw new IllegalArgumentException("Empty list in Matrix constructor");
    }
    order = (int) Math.sqrt(list.size());
    matrix = new ArrayList<>(order);
    for (int i = 0; i < order; i++) {
      matrix.add(new ArrayList<>(order));
      for (int j = 0; j < order; j++) {
        matrix.get(i).add(list.get(j + i * order));
      }
    }
  }

  public T get(int row, int col) {
    return matrix.get(row).get(col);
  }

  public int getOrder() {
    return order;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("[");
    for (int row = 0; row < order; row++) {
      sb.append("[");
      for (int col = 0; col < order; col++) {
        sb.append(matrix.get(row).get(col));
        if (col + 1 < order) {
          sb.append(" ");
        }
      }
      sb.append("]");
    }
    sb.append("]");
    return sb.toString();
  }

  public Matrix<T> sum(Matrix<T> other, BinaryOperator<T> elementSum) {
    final List<T> matrixList = new ArrayList<>();
    for (int row = 0; row < order; row++) {
      for (int col = 0; col < order; col++) {
        matrixList.add(elementSum.apply(this.get(row, col), other.get(row, col)));
      }
    }
    return new Matrix<>(matrixList);
  }

  public Matrix<T> product(
      Matrix<T> other, BinaryOperator<T> elementSum, BinaryOperator<T> elementProduct) {
    final List<T> matrixList = new ArrayList<>();
    for (int row = 0; row < order; row++) {
      for (int col = 0; col < order; col++) {
        List<T> products = new ArrayList<>();
        for (int r = 0; r < order; r++) {
          products.add(elementProduct.apply(this.get(row, r), other.get(r, col)));
        }
        matrixList.add(products.stream().reduce(elementSum).orElse(null));
      }
    }
    return new Matrix<>(matrixList);
  }
}
