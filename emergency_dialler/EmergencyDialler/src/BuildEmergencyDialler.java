public class BuildEmergencyDialler {

  public static void main(String[] args) {
    final Dialler dialler = new Dialler();

    final Person contact1 = new Person("Jensen", 4, 2, -9);

    final Group contact2 = new Group("Contact 2 Group");
    contact2.add(new Person("Jamil", 3, 0, 32));
    final Group contact2subGroup = new Group("Contact 2 Subgroup 1");
    contact2subGroup.add(new Person("Ji", 5, -4, -9));
    contact2subGroup.add(new Person("Jane", 2, -4, 1));
    contact2.add(contact2subGroup);

    final Group contact3 = new Group("Contact 3 Group");
    contact3.add(new Person("Joe", 1, 2, 3));

    final EmergencyDialler emergencyDialler = new AvgEmergencyDialler(new Location(1, 1), dialler);
    emergencyDialler.addToEmergencyContactList(contact1);
    emergencyDialler.addToEmergencyContactList(contact2);
    emergencyDialler.addToEmergencyContactList(contact3);

    emergencyDialler.emergency();
  }
}
