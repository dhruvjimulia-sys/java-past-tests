package videogame;

import java.util.HashSet;
import java.util.Set;

public class TransportUnit extends Entity {
  Set<Entity> entities;

  public TransportUnit(String name, int lifePoints) {
    super(name, lifePoints);
    entities = new HashSet<>();
  }

  @Override
  protected int propagateDamage(int damageAmount) {
    assert damageAmount >= 0;
    final int effectiveDamageAmount = Math.min(damageAmount, lifePoints);
    lifePoints -= effectiveDamageAmount;
    return effectiveDamageAmount
        + entities.stream().map(e -> e.propagateDamage(damageAmount / 2)).reduce(0, Integer::sum);
  }

  @Override
  public int minimumStrikeToDestroy() {
    return Math.max(
        lifePoints,
        entities.stream()
            .map(Entity::minimumStrikeToDestroy)
            .map(i -> i * 2)
            .reduce(Integer.MIN_VALUE, Math::max));
  }

  public void add(Entity entity) {
    entities.add(entity);
  }

  @Override
  public String toString() {
    return name
        + "("
        + lifePoints
        + ") "
        + "transporting: ["
        + entities.stream().map(Object::toString).reduce((a, b) -> a + ", " + b).orElse("")
        + "]";
  }
}
