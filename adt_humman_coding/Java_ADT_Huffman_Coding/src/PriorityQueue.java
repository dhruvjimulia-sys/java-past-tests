public class PriorityQueue<E extends Comparable<E>> implements PriorityQueueInterface<E> {

  private E[] items; // a heap of HuffmanTrees
  private static final int max_size = 256;
  private int size; // number of HuffManTrees in the heap.

  public PriorityQueue() {
    // constructor which creates an empty heap
    items = (E[]) new Comparable[max_size];
    size = 0;
  }

  public boolean isEmpty() {
    return size == 0;
  }

  public int getSize() {
    return size;
  }

  public E getMin() {
    E root = null;
    if (!isEmpty()) {
      root = items[0];
    }
    return root;
  }

  public void add(E newEntry) throws PriorityQueueException {
    // post: Adds a new entry to the priority queue according to
    // the priority value.
    size++;
    items[size - 1] = newEntry;
    percolateUp(size - 1);
  }

  private void percolateUp(int c) {
    if (c > 0) {
      int parent = (int) ((c - 1) / 2);
      if (items[c].compareTo(items[parent]) < 0) {
        swap(c, parent);
        percolateUp(parent);
      }
    }
  }

  public E removeMin() {
    // post: Removes the minimum valued item from the PriorityQueue
    E root = null;
    if (!isEmpty()) {
      root = items[0];
      items[0] = items[size - 1];
      size--;
      heapRebuild(0);
    }
    return root;
  }

  private void heapRebuild(int root) {
    // Rebuild heap to keep it ordered
    int left = 2 * root + 1;
    int right = 2 * root + 2;
    if (left < size - 1) {
      // Root is not a leaf
      int smallerSubHeap;
      if (left == size - 1) {
        // No right subheap
        smallerSubHeap = left;
      } else if (items[left].compareTo(items[right]) < 0) {
        smallerSubHeap = left;
      } else {
        smallerSubHeap = right;
      }
      if (items[root].compareTo(items[smallerSubHeap]) > 0) {
        swap(root, smallerSubHeap);
        heapRebuild(smallerSubHeap);
      }
    }
  }

  private void swap(int firstIndex, int secondIndex) {
    E temp = items[firstIndex];
    items[firstIndex] = items[secondIndex];
    items[secondIndex] = temp;
  }
}
