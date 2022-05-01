package agents;

import domain.Agent;
import goods.Laptop;
import java.util.Optional;
import market.Market;

public class Consumer extends Agent {

  public Consumer(int thinkingTimeInMillis, Market market) {
    super(thinkingTimeInMillis, market);
  }

  @Override
  public void doAction() {
    Optional<Laptop> lpo = market.buyLaptop();
    if (lpo.isPresent()) {
      think();
      market.disposeLaptop(lpo.get(), this);
    }
  }
}
