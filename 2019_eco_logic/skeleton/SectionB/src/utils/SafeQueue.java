package utils;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SafeQueue<E> implements Queue<E> {
  private final AtomicInteger size;
  private final LockableNode<E> head;
  private final LockableNode<E> tail;

  public SafeQueue() {
    this.head = new LockableNode<>(null);
    this.tail = new LockableNode<>(null);
    head.succ = tail;
    tail.pred = head;
    this.size = new AtomicInteger(0);
  }

  @Override
  public void push(E element) {
    final LockableNode<E> newNode =
        new LockableNode<>(Objects.requireNonNull(element));

    while (true) {
      final LockableNode<E> tailPred = tail.pred;

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
      final LockableNode<E> headSuccessor = head.succ;
      head.lock();
      headSuccessor.lock();

      if (head.succ == headSuccessor) {
        if (head.succ == tail) {
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
    return size.get();
  }

  private static class LockableNode<E> {
    final E value; // null only for head and tail
    LockableNode<E> succ;
    LockableNode<E> pred;
    private final Lock lock;

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
