import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AutoPlayer extends AbstractPlayer {

  AutoPlayer(CardPile left, CardPile right, String name) {
    super(left, right, name);
  }

  @Override
  protected int selectCard() {
    Map<Rank, Integer> frequencyTable =
        Arrays
        .stream(cards)
        .map(Card::getRank)
        .distinct()
        .collect(Collectors.toMap(Function.identity(),
            r -> (int) Arrays.stream(cards).filter(c -> c.getRank().equals(r)).count())
        );

    int maxValue = Integer.MIN_VALUE;
    Rank maxValueRank = null;
    boolean uniqueMaxValue = true;

    for (Entry<Rank, Integer> entry : frequencyTable.entrySet()) {
      if (entry.getValue() > maxValue) {
        maxValueRank = entry.getKey();
        maxValue = entry.getValue();
        uniqueMaxValue = true;
      } else if (entry.getValue() == maxValue) {
        uniqueMaxValue = false;
      }
    }

    final Random random = new Random();
    if (!uniqueMaxValue) {
      int selectedCard;
      do {
        selectedCard = random.nextInt(Player.HANDSIZE);
      } while (cards[selectedCard].getRank() != maxValueRank);
      return selectedCard;
    } else {
      return random.nextInt(Player.HANDSIZE);
    }
  }
}