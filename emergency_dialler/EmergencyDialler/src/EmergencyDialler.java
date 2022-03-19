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
    Contact nextContact = queue.dequeue();
    while (nextContact != null) {
      if (nextContact
          .getPeople()
          .stream()
          .allMatch(p -> dialler.call(p.getTelephoneNumber(), p.getName()))) {
        break;
      } else {
        nextContact = queue.dequeue();
      }
    }
    return nextContact;
  }

  public abstract void addToEmergencyContactList(Contact contact);
}
