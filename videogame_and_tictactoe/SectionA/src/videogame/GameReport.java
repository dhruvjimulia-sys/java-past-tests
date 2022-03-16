package videogame;

public class GameReport {

  public static void main(String[] args) {
    final Magician m1 = new Magician("Ged", 300);
    final Magician m2 = new Magician("Dumbledore", 200);
    final Magician m3 = new Magician("Saruman", 600);
    final Magician m4 = new Magician("Harry", 18);
    final Magician m5 = new Magician("Gandalf", 950);
    final Magician m6 = new Magician("Gargamel", 200);

    final TransportUnit t1 = new TransportUnit("Falkor", 2500);
    final TransportUnit t2 = new TransportUnit("Shadowfax", 350);

    t1.add(m1);
    t1.add(m2);
    t1.add(m3);
    t1.add(t2);

    t2.add(m4);
    t2.add(m5);

    System.out.println(t1);

    t1.applySpell(m6);

    System.out.println(t1);

    final Magician m7 =
        new Magician("Ron", t1.minimumStrikeToDestroy() / Magician.STRENGTH_MULTIPLE);
    t1.applySpell(m7);

    System.out.println(t1);

    assert !t1.isAlive()
        && !t2.isAlive()
        && !m1.isAlive()
        && !m2.isAlive()
        && !m3.isAlive()
        && !m4.isAlive()
        && !m5.isAlive();
    assert m6.isAlive() && m7.isAlive();
  }
}
