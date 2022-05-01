package domain.agents;

import domain.MarketPlace;
import domain.goods.PlasticGood;
import domain.goods.RawPlastic;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Manufacturer extends Agent {

  private final int neededRawPlasticBatches;

  public Manufacturer(int neededRawPlasticBatches, int thinkingTimeInMillis,
      MarketPlace marketPlace) {
    super(thinkingTimeInMillis, marketPlace);
    this.neededRawPlasticBatches = neededRawPlasticBatches;
    if (neededRawPlasticBatches < 1) {
      throw new IllegalArgumentException(
          "Number of plastic batches required must be less than 1"
      );
    }
  }

  @Override
  protected void doAction() {
    final List<RawPlastic> plasticBatches = new ArrayList<>();
    while (plasticBatches.size() != neededRawPlasticBatches) {
      Optional<RawPlastic> rawPlastic = marketPlace.buyRawPlastic();
      if (rawPlastic.isPresent()) {
        plasticBatches.add(rawPlastic.get());
      } else {
        think();
      }
    }
    marketPlace.sellPlasticGood(new PlasticGood(plasticBatches));
  }
}
