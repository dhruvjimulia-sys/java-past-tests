import java.util.HashSet;
import java.util.Set;

public class ProgrammingTest {

  public static void main(String[] args) {
    final Academic ricardo = new Academic("Ricardo Rodriguez");
    final Academic ismael = new Academic("Ismael Bento");

    final Undergraduate firstUg = new Undergraduate("gg4", "Godfrey Gold", "gg4@ic.ac.uk", ricardo);
    final Undergraduate secondUg =
        new Undergraduate("pr3", "Polly Ramfield", "pr@ic.ac.uk", ismael);
    final Postgraduate firstPg = new Postgraduate("te2", "Terry Emfield", "te2@ic.ac.uk", ricardo);
    final Postgraduate secondPg = new Postgraduate("yj34", "Yomna Jedi", "yj34@ic.ac.uk", ismael);
    final Postgraduate thirdPg = new Postgraduate("jj8", "Jerry Jammer", "jj8@ic.ac.uk", ismael);

    final Set<Student> students = new HashSet<>();
    students.add(firstUg);
    students.add(secondUg);
    students.add(firstPg);
    students.add(secondPg);
    students.add(thirdPg);

    final Course course = new Course("Mathematics", students);

    final Notifier notifier = new Notifier(course.getPostgraduates("Ismael Bento"));

    notifier.doNotifyAll("You have been notified!");
  }
}
