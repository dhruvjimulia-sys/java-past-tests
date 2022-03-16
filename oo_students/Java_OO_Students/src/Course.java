import java.util.Set;
import java.util.stream.Collectors;

public class Course {
  private final String name;
  private final Set<Student> students;

  public Course(String name, Set<Student> students) {
    this.name = name;
    this.students = students;
  }

  public Set<Postgraduate> getPostgraduates(String nameOfSupervisor) {
    return students.stream()
        .filter(s -> s instanceof Postgraduate)
        .map(s -> ((Postgraduate) s))
        .filter(p -> p.getSupervisor().getName().equals(nameOfSupervisor))
        .collect(Collectors.toSet());
  }
}
