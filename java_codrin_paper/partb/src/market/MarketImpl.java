package market;

import agents.RecycleCenter;
import datastructures.StockImpl;
import domain.Agent;
import domain.producttypes.Product;
import domain.producttypes.RawMaterial.Origin;
import goods.Laptop;
import goods.RawAluminium;
import goods.RawGlass;
import java.util.Optional;

public class MarketImpl implements Market {
  StockImpl<RawAluminium> rawAluminium;
  StockImpl<RawAluminium> recycledRawAluminum;
  StockImpl<Laptop> laptops;
  StockImpl<Product> disposedLaptops;
  StockImpl<RawGlass> rawGlass;
  StockImpl<RawGlass> recycledRawGlass;

  public MarketImpl() {
    this.rawAluminium = new StockImpl<>();
    this.recycledRawAluminum = new StockImpl<>();
    this.laptops = new StockImpl<>();
    this.disposedLaptops = new StockImpl<>();
    this.rawGlass = new StockImpl<>();
    this.recycledRawGlass = new StockImpl<>();
  }

  @Override
  public void sellRawAluminium(RawAluminium item, Agent agent) {
    if (item.origin == Origin.RECYCLED) {
      recycledRawAluminum.push(item, agent);
    } else {
      rawAluminium.push(item, agent);
    }
  }

  @Override
  public Optional<RawAluminium> buyRawAluminium() {
    if (rawAluminium.size() == 0 && recycledRawAluminum.size() == 0) {
      return Optional.empty();
    }
    if (recycledRawAluminum.size() != 0) {
      return recycledRawAluminum.pop();
    }
    return rawAluminium.pop();
  }

  @Override
  public void sellRawGlass(RawGlass item, Agent agent) {
    if (item.origin == Origin.RECYCLED) {
      recycledRawGlass.push(item, agent);
    } else {
      rawGlass.push(item, agent);
    }
  }

  @Override
  public Optional<RawGlass> buyRawGlass() {
    if (rawGlass.size() == 0 && recycledRawGlass.size() == 0) {
      return Optional.empty();
    }
    if (recycledRawGlass.size() != 0) {
      return recycledRawGlass.pop();
    }
    return rawGlass.pop();
  }

  @Override
  public void sellLaptop(Laptop item, Agent agent) {
    laptops.push(item, agent);
  }

  @Override
  public Optional<Laptop> buyLaptop() {
    if (laptops.size() == 0) {
      return Optional.empty();
    }
    return laptops.pop();
  }

  @Override
  public void disposeLaptop(Laptop item, Agent agent) {
    disposedLaptops.push(item, agent);
  }

  @Override
  public Optional<Product> collectDisposedGood() {
    if (disposedLaptops.size() == 0) {
      return Optional.empty();
    }
    return disposedLaptops.pop();
  }
}
