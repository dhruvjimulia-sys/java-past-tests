import java.util.Set;

/**
 * The class Dealer encapsulates the actions of a Chancho game-dealer. The game dealer is
 * responsible for dealing cards from the provided game-deck to each player, and scheduling rounds
 * of the game until a player has won the game. All players who have declared themselves as a winner
 * should be congratulated.
 *
 * <p>Developers should provide the constructor,
 *
 * <p>public Dealer(Set<Player> players, Deck gameDeck);
 */
public final class Dealer {
  private final Set<Player> players;
  private final Deck deck;

  public Dealer(Set<Player> players, Deck deck) {
    this.players = players;
    this.deck = deck;
  }

  public void playGame() {
    players.forEach(
        p -> {
          for (int i = 0; i < Player.HANDSIZE; i++) {
            p.addToHand(deck.removeFromTop());
          }
        });
    while (players.stream().noneMatch(Player::hasWon)) {
      players.forEach(Player::discard);
      players.forEach(Player::pickup);
    }
    congratulateWinners();
  }

  private void congratulateWinners() {
    System.out.println("The game has been won! Congratulations to:");
    players.stream()
        .filter(Player::hasWon)
        .forEach(
            p -> {
              System.out.println(p.toString());
              System.out.println();
            });
  }
}
