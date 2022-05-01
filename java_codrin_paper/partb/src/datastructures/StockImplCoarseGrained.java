package datastructures;

import domain.Agent;
import domain.producttypes.ExchangeableGood;
import java.util.Optional;

public class StockImplCoarseGrained<E extends ExchangeableGood> extends StockImpl<E> {

  @Override
  public synchronized void push(E item, Agent agent) {
    super.push(item, agent);
  }

  @Override
  public synchronized Optional<E> pop() {
    return super.pop();
  }

  @Override
  public synchronized int size() {
    return super.size();
  }
}
