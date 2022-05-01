package agents;

import domain.Agent;
import domain.producttypes.Product;
import domain.producttypes.RawMaterial.Origin;
import goods.RawAluminium;
import goods.RawGlass;
import java.util.Optional;
import market.Market;

public class RecycleCenter extends Agent {

  public RecycleCenter(int thinkingTimeInMillis, Market market) {
    super(thinkingTimeInMillis, market);
  }

  @Override
  public void doAction() {
    Optional<Product> dpg = market.collectDisposedGood();
    dpg.ifPresent(product -> product.getConstituentMaterials()
        .stream()
        .filter(m -> m.origin == Origin.NEW).forEach(m -> {
          if (m instanceof RawGlass) {
            market.sellRawGlass(new RawGlass(Origin.RECYCLED), this);
          } else {
            market.sellRawAluminium(new RawAluminium(Origin.RECYCLED), this);
          }
        }));
  }
}
