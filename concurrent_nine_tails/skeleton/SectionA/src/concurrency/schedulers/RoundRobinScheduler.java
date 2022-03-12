package concurrency.schedulers;

import concurrency.ConcurrentProgram;
import concurrency.DeadlockException;
import java.util.Set;

public class RoundRobinScheduler implements Scheduler {

  private boolean hasBeenInvoked;
  private int lastThreadInvoked;

  public RoundRobinScheduler() {
    hasBeenInvoked = false;
    lastThreadInvoked = 0;
  }

  @Override
  public int chooseThread(ConcurrentProgram program) throws DeadlockException {
    throwExceptionIfNoThreadsEnabled(program);

    final Set<Integer> enabledThreadIds = program.getEnabledThreadIds();
    if (!hasBeenInvoked) {
      hasBeenInvoked = true;
      final int invokedThread =
          enabledThreadIds.stream().min(Integer::compare).get();
      lastThreadInvoked = invokedThread;
      return invokedThread;
    }

    final int invokedThread = enabledThreadIds
        .stream()
        .filter(i -> i > lastThreadInvoked)
        .min(Integer::compare)
        .orElse(enabledThreadIds.stream().min(Integer::compare).get());
    lastThreadInvoked = invokedThread;

    return invokedThread;
  }
}
