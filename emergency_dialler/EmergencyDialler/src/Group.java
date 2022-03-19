import java.util.HashSet;
import java.util.Set;

public class Group implements Contact {
  private final String name;
  private final Set<Contact> contacts;

  public Group(String name) {
    this.name = name;
    contacts = new HashSet<>();
  }

  @Override
  public Set<Person> getPeople() {
    final Set<Person> allPeople = new HashSet<>();
    for (Contact contact : contacts) {
      allPeople.addAll(contact.getPeople());
    }
    return allPeople;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public int size() {
    return getPeople().size();
  }

  public boolean add(Contact contact) {
    return contacts.add(contact);
  }

  public boolean remove(Contact contact) {
    return contacts.remove(contact);
  }
}
