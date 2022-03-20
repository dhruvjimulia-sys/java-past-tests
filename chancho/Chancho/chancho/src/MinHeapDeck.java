
/**
 * This class implements a Deck of playing cards using a min-heap to store the cards. Complete
 * implementations of the methods: size(), isEmpty(), addToBottom(Card) and removeFromTop() have
 * been provided and should not be modified.
 *
 * <p>The developer should implement the two Deck shuffling operations: cut() and
 * riffleShuffle(Deck).
 */
public final class MinHeapDeck implements Deck {

  private final IMinHeap<HeapEntry<Card>> heap;

  public MinHeapDeck() {
    heap = new MinHeap<HeapEntry<Card>>();
  }

  public int size() {
    return heap.size();
  }

  public boolean isEmpty() {
    return heap.isEmpty();
  }

  public void addToBottom(Card card) {
    assert card != null : "Attempting to add 'null' to a MinHeapDeck";

    heap.add(new HeapEntry<Card>(card, size()));
  }

  public Card removeFromTop() {
    assert !isEmpty() : "Attempting to remove a card from an empty MinHeapDeck.";
    return heap.removeMin().getItem();
  }

  public Deck cut() {
    final Deck top = new MinHeapDeck();
    final int size = this.size();
    for (int i = 0; i < (int) (size / 2); i++) {
      top.addToBottom(this.removeFromTop());
    }
    return top;
  }

  public Deck riffleShuffle(Deck deck) {
    assert (this.size() == deck.size());
    final int size = deck.size();
    final Deck shuffled = new MinHeapDeck();
    for (int i = 0; i < size; i++) {
      shuffled.addToBottom(removeFromTop());
      shuffled.addToBottom(deck.removeFromTop());
    }
    return shuffled;
  }

  @Override
  public String toString() {
    return heap.toString();
  }
}
