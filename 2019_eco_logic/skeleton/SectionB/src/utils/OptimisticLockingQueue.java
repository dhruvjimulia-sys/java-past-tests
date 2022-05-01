package utils;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class OptimisticLockingQueue<E> implements Queue<E> {

  private final AtomicInteger size;
  private LockableNode<E> head, tail;

  public OptimisticLockingQueue() {
    this.head = new LockableNode<>(null);
    this.tail = new LockableNode<>(null);
    head.succ = tail;
    tail.pred = head;
    this.size = new AtomicInteger(0);
  }

  @Override
  public void push(E element) {
    LockableNode<E> newNode = new LockableNode<>(Objects.requireNonNull(element));

    while (true) {
      LockableNode<E> tailPred = tail.pred;

      tailPred.lock();
      tail.lock();

      if (tail.pred == tailPred) {
        tailPred.succ = newNode;
        newNode.pred = tailPred;
        newNode.succ = tail;
        tail.pred = newNode;

        size.incrementAndGet();
        tail.unlock();
        tailPred.unlock();
        break;
      }

      tail.unlock();
      tailPred.unlock();
    }
  }

  @Override
  public Optional<E> pop() {
    while (true) {
      LockableNode<E> headSuccessor = head.succ;
      head.lock();
      headSuccessor.lock();

      if (head.succ == headSuccessor) {
        if (head.succ == tail) {
          // The queue is empty
          return Optional.empty();
        }

        E result = headSuccessor.value;
        head.succ = headSuccessor.succ;
        head.succ.pred = head;

        size.decrementAndGet();

        headSuccessor.unlock();
        head.unlock();

        return Optional.of(result);
      }
      headSuccessor.unlock();
      head.unlock();
    }
  }

  @Override
  public int size() {
    // Only eventually consistent, but in practice operations on the atomic integer are much faster
    // than the locking steps
    return size.get();
  }

  public int traversalSize() {
    //Used only for consistency check
    int nodes = 0;
    for (LockableNode<E> current = head; current.succ != tail; current = current.succ) {
      nodes++;
    }
    return nodes;
  }

  static class LockableNode<E> {

    final E value; //null only for head and tail
    LockableNode<E> succ;
    LockableNode<E> pred;
    Lock lock;

    public LockableNode(E value) {
      this.value = value;
      this.lock = new ReentrantLock();
    }

    public void lock() {
      lock.lock();
    }

    public void unlock() {
      lock.unlock();
    }
  }
}
