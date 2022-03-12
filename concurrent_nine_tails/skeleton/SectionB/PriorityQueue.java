import java.util.Iterator;

/** You must implement the <code>add</code> and <code>PQRebuild</code> methods. */
public class PriorityQueue<T extends Comparable<T>> implements PriorityQueueInterface<T> {

  private T[] items; // a minHeap of elements T
  private static final int max_size = 512;
  private int size; // number of elements in the minHeap.

  public PriorityQueue() {
    items = (T[]) new Comparable[max_size];
    size = 0;
  }

  /** Returns true if the priority queue is empty. False otherwise. */
  public boolean isEmpty() {
    return size == 0;
  }

  /** Returns the size of the priority queue. */
  public int getSize() {
    return size;
  }

  /**
   * Returns the element with highest priority or null if the priority. queue is empty. The priority
   * queue is left unchanged
   */
  public T peek() {
    T root = null;
    if (!isEmpty()) {
      root = items[0];
    }
    return root;
  }

  /**
   * <strong>Implement this method for Question 2</strong> Adds a new entry to the priority queue
   * according to the priority value.
   *
   * @param newEntry the new element to add to the priority queue
   * @throws PQException if the priority queue is full
   */
  public void add(T newEntry) throws PQException {
    items[size] = newEntry;
    size++;
    percolateUp(size - 1);
  }

  private void percolateUp(int c) {
    if (c > 0) {
      int parent = ((c - 1) / 2);
      if (items[c].compareTo(items[parent]) < 0) {
        swap(c, parent);
        percolateUp(parent);
      }
    }
  }

  private void swap(int firstIndex, int secondIndex) {
    T temp = items[secondIndex];
    items[secondIndex] = items[firstIndex];
    items[firstIndex] = temp;
  }

  /** Removes the element with highest priority. */
  public void remove() {
    if (!isEmpty()) {
      items[0] = items[size - 1];
      size--;
      PQRebuild(0);
    }
  }

  /** <strong>Implement this method for Question 2</strong> */
  private void PQRebuild(int root) {
    int left = 2 * root + 1;
    int right = 2 * root + 2;
    if (left <= size - 1) {
      int smallerSubHeap;
      if (left == size - 1) {
        smallerSubHeap = left;
      } else if (items[left].compareTo(items[right]) < 0) {
        smallerSubHeap = left;
      } else {
        smallerSubHeap = right;
      }
      if (items[root].compareTo(items[smallerSubHeap]) > 0) {
        swap(root, smallerSubHeap);
        PQRebuild(smallerSubHeap);
      }
    }
  }

  public Iterator<Object> iterator() {
    return new PQIterator<Object>();
  }

  private class PQIterator<T> implements Iterator<Object> {

    private int position = 0;

    public boolean hasNext() {
      return position < size;
    }

    public Object next() {
      Object temp = items[position];
      position++;
      return temp;
    }

    public void remove() {
      throw new IllegalStateException();
    }
  }

  /** Returns a priority queue that is a clone of the current priority queue. */
  public PriorityQueue<T> clone() {
    PriorityQueue<T> clone = new PriorityQueue<T>();
    clone.size = this.size;
    clone.items = (T[]) new Comparable[max_size];
    System.arraycopy(this.items, 0, clone.items, 0, size);
    return clone;
  }

  public static void main(String[] args) {
    PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
    priorityQueue.add(2);
    priorityQueue.add(3);
    priorityQueue.add(1);
    priorityQueue.add(10);
    while (priorityQueue.size > 0) {
      System.out.println(priorityQueue.peek());
      priorityQueue.remove();
    }
  }
}
