/**
 * An enumeration of card rank values present in a standard deck of playing cards.
 *
 * <p>The Rank enum has the following implicitly declared methods:
 *
 * <p>-- public boolean equals(Object other): Returns true if the specified object is equal to this
 * enum constant.
 *
 * <p>-- public int ordinal(): Returns the ordinal of this enumeration constant (its position in its
 * enum declaration, where the initial constant is assigned an ordinal of zero).
 *
 * <p>-- public String name(): Returns the name of this enum constant, exactly as declared in its
 * enum declaration.
 *
 * <p>-- public static Rank[] values(): Returns an array of Rank values covered by the enumeration.
 *
 * <p>See java.lang.Enum<E> for a more comprehensive reference on enumerations.
 */
public enum Rank {
  TWO,
  THREE,
  FOUR,
  FIVE,
  SIX,
  SEVEN,
  EIGHT,
  NINE,
  TEN,
  JACK,
  QUEEN,
  KING,
  ACE
}
