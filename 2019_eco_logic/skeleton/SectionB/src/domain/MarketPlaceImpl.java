package domain;

import domain.goods.PlasticGood;
import domain.goods.RawPlastic;
import domain.goods.RawPlastic.Origin;
import java.util.Optional;
import utils.SafeQueue;


public class MarketPlaceImpl implements MarketPlace {

  private final boolean DEBUG_MESSAGES = true;
  private final SafeQueue<PlasticGood> plasticGoods;
  private final SafeQueue<PlasticGood> disposedPlasticGoods;
  private final SafeQueue<RawPlastic> rawPlastic;
  private final SafeQueue<RawPlastic> recycledRawPlastic;

  public MarketPlaceImpl() {
    this.plasticGoods = new SafeQueue<>();
    this.disposedPlasticGoods = new SafeQueue<>();
    this.rawPlastic = new SafeQueue<>();
    this.recycledRawPlastic = new SafeQueue<>();
  }

  public void sellRawPlastic(RawPlastic plasticItem) {
    if (DEBUG_MESSAGES) {
      System.out
          .println("Thread: " + Thread.currentThread().getId() + " - Sell plastic: " + plasticItem);
    }
    if (plasticItem.origin == Origin.NEW) {
      rawPlastic.push(plasticItem);
    } else {
      recycledRawPlastic.push(plasticItem);
    }
  }

  public Optional<RawPlastic> buyRawPlastic() {
    if (rawPlastic.size() == 0 && recycledRawPlastic.size() == 0) {
      return Optional.empty();
    }
    if (recycledRawPlastic.size() != 0) {
      return recycledRawPlastic.pop();
    }
    return rawPlastic.pop();
  }

  public void sellPlasticGood(PlasticGood good) {
    if (DEBUG_MESSAGES) {
      System.out.println("Thread: " + Thread.currentThread().getId() + " - Sell good: " + good);
    }
    plasticGoods.push(good);
  }

  public Optional<PlasticGood> buyPlasticGood() {
    return plasticGoods.pop();
  }

  public void disposePlasticGood(PlasticGood good) {
    if (DEBUG_MESSAGES) {
      System.out.println("Thread: " + Thread.currentThread().getId() + " - Dispose good: " + good);
    }
    disposedPlasticGoods.push(good);
  }

  public Optional<PlasticGood> collectDisposedGood() {
    return disposedPlasticGoods.pop();
  }
}
