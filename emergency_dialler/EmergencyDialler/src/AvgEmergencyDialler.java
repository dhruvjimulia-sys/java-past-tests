public class AvgEmergencyDialler extends EmergencyDialler {

  public AvgEmergencyDialler(Location location, Dialler dialler) {
    super(location, dialler);
  }

  @Override
  public void addToEmergencyContactList(Contact contact) {
    queue.add(
        contact.getPeople().stream()
            .map(Person::getAddress)
            .map(a -> a.distanceTo(location))
            .mapToDouble(Double::valueOf)
            .average()
            .orElse(0),
        contact);
  }
}
