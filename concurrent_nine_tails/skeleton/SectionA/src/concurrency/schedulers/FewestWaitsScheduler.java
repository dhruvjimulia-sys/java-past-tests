package concurrency.schedulers;

import concurrency.ConcurrentProgram;
import concurrency.DeadlockException;
import concurrency.statements.WaitStmt;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FewestWaitsScheduler implements Scheduler {

  @Override
  public int chooseThread(ConcurrentProgram program) throws DeadlockException {
    throwExceptionIfNoThreadsEnabled(program);

    final Map<Integer, Long> waitStatementCounts =
        program.getEnabledThreadIds().stream()
            .collect(
                Collectors.toMap(
                    Function.identity(),
                    i ->
                        program.remainingStatements(i).stream()
                            .filter(s -> s instanceof WaitStmt)
                            .count()));

    int chosenThread = -1;
    int minWaitStatementCount = Integer.MAX_VALUE;
    for (Map.Entry<Integer, Long> entry : waitStatementCounts.entrySet()) {
      if (entry.getValue() < minWaitStatementCount) {
        chosenThread = entry.getKey();
        minWaitStatementCount = Math.toIntExact(entry.getValue());
      } else if (entry.getValue() == minWaitStatementCount && entry.getKey() < chosenThread) {
        chosenThread = entry.getKey();
      }
    }

    return chosenThread;
  }
}
