package videogame;

public class Magician extends Entity implements SpellCaster {

  public Magician(String name, int lifePoints) {
    super(name, lifePoints);
  }

  @Override
  public int getStrength() {
    return lifePoints * 2;
  }

  @Override
  protected int propagateDamage(int damageAmount) {
    assert damageAmount >= 0;
    final int effectiveDamageAmount = Math.min(damageAmount, lifePoints);
    lifePoints -= effectiveDamageAmount;
    return effectiveDamageAmount;
  }

  @Override
  public int minimumStrikeToDestroy() {
    return lifePoints;
  }

  @Override
  public String toString() {
    return name + "(" + lifePoints + ")";
  }
}
