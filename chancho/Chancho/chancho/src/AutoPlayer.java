

public class AutoPlayer extends AbstractPlayer {

  AutoPlayer(CardPile left, CardPile right, String name) {
    super(left, right, name);
  }

  @Override
  protected int selectCard() {
    return 0;
  }

  // TODO: TASK 3: IMPLEMENTATION OF THIS SUB-CLASS

}