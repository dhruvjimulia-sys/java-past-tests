package agents;

import domain.Agent;
import goods.Laptop;
import goods.RawAluminium;
import goods.RawGlass;
import java.util.Optional;
import market.Market;

public class LaptopManufacturer extends Agent {
  private int numAluminumSecured = 0;
  private boolean glassSecured;

  public LaptopManufacturer(int thinkingTimeInMillis, Market market) {
    super(thinkingTimeInMillis, market);
  }

  @Override
  public void doAction() {
    RawAluminium aluminium1, aluminium2;
    RawGlass glass;
    while (true) {
      Optional<RawAluminium> al1 = market.buyRawAluminium();
      if (al1.isPresent()) {
        aluminium1 = al1.get();
        break;
      }
    }
    while (true) {
      Optional<RawAluminium> al2 = market.buyRawAluminium();
      if (al2.isPresent()) {
        aluminium2 = al2.get();
        break;
      }
    }
    while (true) {
      Optional<RawGlass> gl = market.buyRawGlass();
      if (gl.isPresent()) {
        glass = gl.get();
        break;
      }
    }
    market.sellLaptop(new Laptop(glass, aluminium1, aluminium2), this);
  }
}
