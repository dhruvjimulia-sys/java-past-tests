import java.util.HashSet;
import java.util.Set;

public class Person implements Contact {
  private final String name;
  private final int number;
  private final Location address;

  public Person(String name, int number, int locationX, int locationY) {
    this.name = name;
    this.number = number;
    address = new Location(locationX, locationY);
  }

  public int getTelephoneNumber() {
    return number;
  }

  public Location getAddress() {
    return address;
  }

  @Override
  public Set<Person> getPeople() {
    Set<Person> s = new HashSet<Person>();
    s.add(this);
    return s;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public int size() {
    return 1;
  }
}
