package videogame;

import java.util.HashSet;
import java.util.Set;

public class TransportUnit extends Entity {
  private final Set<Entity> entities;
  public static final int DAMAGE_DECAY = 2;

  public TransportUnit(String name, int lifePoints) {
    super(name, lifePoints);
    entities = new HashSet<>();
  }

  @Override
  protected int propagateDamage(int damageAmount) {
    return super.propagateDamage(damageAmount)
        + entities.stream()
            .map(e -> e.propagateDamage(damageAmount / DAMAGE_DECAY))
            .reduce(0, Integer::sum);
  }

  @Override
  public int minimumStrikeToDestroy() {
    return Math.max(
        lifePoints,
        entities.stream()
            .map(Entity::minimumStrikeToDestroy)
            .map(i -> i * DAMAGE_DECAY)
            .reduce(Integer.MIN_VALUE, Math::max));
  }

  public void add(Entity entity) {
    entities.add(entity);
  }

  @Override
  public String toString() {
    return super.toString()
        + " transporting: ["
        + entities.stream().map(Object::toString).reduce((a, b) -> a + ", " + b).orElse("")
        + "]";
  }
}
