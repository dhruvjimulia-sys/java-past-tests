import java.util.Arrays;

/**
 * This class implements a min-heap abstract data type (as described by the generic interface
 * IMinHeap<T extends Comparable<T>>) using a fixed array of size MinHeap.MAXIMUM_HEAP_SIZE.
 */
public class MinHeap<T extends Comparable<T>> implements IMinHeap<T> {
  public static final int MAXIMUM_HEAP_SIZE = 100;
  private final T[] minHeap;
  private int size;

  public MinHeap() {
    minHeap = (T[]) new Comparable[MAXIMUM_HEAP_SIZE];
    size = 0;
  }

  @Override
  public void add(T element) throws HeapException {
    minHeap[size] = element;
    percolateUp(size);
    size++;
  }

  @Override
  public T removeMin() {
    final T min = minHeap[0];
    minHeap[0] = minHeap[size - 1];
    size--;
    fixMinHeap(0);
    return min;
  }

  @Override
  public T getMin() {
    return minHeap[0];
  }

  @Override
  public boolean isEmpty() {
    return size == 0;
  }

  @Override
  public int size() {
    return size;
  }

  private void fixMinHeap(int root) {
    final int left = 2 * root;
    final int right = 2 * root + 1;
    if (left <= size) {
      int smallerSubHeap = 0;
      if (left == size) {
        smallerSubHeap = left;
      } else if (minHeap[left].compareTo(minHeap[right]) < 0) {
        smallerSubHeap = left;
      } else {
        smallerSubHeap = right;
      }
      if (minHeap[root].compareTo(minHeap[smallerSubHeap]) > 0) {
        swap(root, smallerSubHeap);
        fixMinHeap(smallerSubHeap);
      }
    }
  }

  private void swap(int firstIndex, int secondIndex) {
    final T temp = minHeap[firstIndex];
    minHeap[firstIndex] = minHeap[secondIndex];
    minHeap[secondIndex] = temp;
  }

  private void percolateUp(int c) {
    if (c > 0) {
      final int parent = (int) (c / 2);
      if (minHeap[c].compareTo(minHeap[parent]) < 0) {
        swap(c, parent);
        percolateUp(parent);
      }
    }
  }

  public static void main(String[] args) {
    MinHeap<Integer> heap = new MinHeap<>();
    heap.add(2);
    heap.add(1);
    heap.add(8);
    heap.add(5);
    heap.add(10);
    heap.add(3);
    System.out.println(Arrays.toString(heap.minHeap));
    System.out.println(heap.removeMin());
    System.out.println(heap.removeMin());
    System.out.println(heap.removeMin());
    System.out.println(heap.removeMin());
    System.out.println(heap.removeMin());
    System.out.println(heap.removeMin());
  }
}
