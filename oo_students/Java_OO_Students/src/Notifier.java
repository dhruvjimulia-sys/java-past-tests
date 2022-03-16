import java.util.Iterator;
import java.util.Set;

public class Notifier {
  Set<? extends Notifiable> notifiables;

  public Notifier(Set<? extends Notifiable> n) {
    notifiables = n;
  }

  public void doNotifyAll(String message) {
    Iterator<? extends Notifiable> i = notifiables.iterator();
    while (i.hasNext()) {
      i.next().notify(message);
    }
  }
}
