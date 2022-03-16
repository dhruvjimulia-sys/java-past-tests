package videogame;

public class Magician extends Entity implements SpellCaster {
  public static final int STRENGTH_MULTIPLE = 2;

  public Magician(String name, int lifePoints) {
    super(name, lifePoints);
  }

  @Override
  public int getStrength() {
    return lifePoints * STRENGTH_MULTIPLE;
  }

  @Override
  protected int propagateDamage(int damageAmount) {
    return super.propagateDamage(damageAmount);
  }

  @Override
  public int minimumStrikeToDestroy() {
    return lifePoints;
  }

  @Override
  public String toString() {
    return super.toString();
  }
}
