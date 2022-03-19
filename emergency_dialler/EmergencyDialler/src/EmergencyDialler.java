public abstract class EmergencyDialler {
  protected final PriorityQueue<Contact> queue;
  protected final Location location;
  protected final Dialler dialler;

  public EmergencyDialler(Location location, Dialler dialler) {
    this.location = location;
    this.dialler = dialler;
    queue = new LinkedListPriorityQueue<>();
  }

  public Contact emergency() {
    Contact nextContact = null;
    do {
      nextContact = queue.dequeue();
    } while (nextContact != null
        && nextContact.getPeople().stream()
            .allMatch(p -> dialler.call(p.getTelephoneNumber(), "Emergency at " + location)));
    return nextContact;
  }

  public abstract void addToEmergencyContactList(Contact contact);
}
