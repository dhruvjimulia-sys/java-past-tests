package domain.agents;

import domain.MarketPlace;
import domain.goods.PlasticGood;
import domain.goods.RawPlastic;
import domain.goods.RawPlastic.Origin;
import java.util.Collection;
import java.util.Optional;

public class RecycleCenter extends Agent {
  private boolean extraRecycledPlastic;

  public RecycleCenter(int thinkingTimeInMillis, MarketPlace marketPlace) {
    super(thinkingTimeInMillis, marketPlace);
    extraRecycledPlastic = false;
  }

  @Override
  protected void doAction() {
    final Optional<PlasticGood> good =  marketPlace.collectDisposedGood();
    if (good.isPresent()) {
      final Collection<RawPlastic> materials = good.get().getBasicMaterials();
      final int numNewMaterials =
          (int) materials.stream().filter(m -> m.origin == Origin.NEW).count();
      final int numRecycledMaterials = materials.size() - numNewMaterials;
      int numMaterialsToBeMade =
          numNewMaterials + numRecycledMaterials / 2;
      if (numRecycledMaterials % 2 == 1) {
        if (extraRecycledPlastic) {
          numMaterialsToBeMade++;
          extraRecycledPlastic = false;
        } else {
          extraRecycledPlastic = true;
        }
      }
      for (int i = 0; i < numMaterialsToBeMade; i++) {
        marketPlace.sellRawPlastic(new RawPlastic(Origin.RECYCLED));
      }
    }
  }
}
