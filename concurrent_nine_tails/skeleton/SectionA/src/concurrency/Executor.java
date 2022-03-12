package concurrency;

import java.util.LinkedList;
import java.util.List;

import concurrency.schedulers.Scheduler;

public class Executor {

	private ConcurrentProgram program;
	private Scheduler scheduler;

	public Executor(ConcurrentProgram program, Scheduler scheduler) {
		this.program = program;
		this.scheduler = scheduler;
	}

	/**
	 * Executes program with respect to scheduler
	 *
	 * @return the final state and history of execution
	 */
	public String execute() {
		final List<Integer> history = new LinkedList<Integer>();
		boolean deadlockOccurred = false;

		do {
			try {
				final int chosenThread = scheduler.chooseThread(program);
				program.step(chosenThread);
				history.add(chosenThread);
			} catch (DeadlockException e) {
				deadlockOccurred = true;
				break;
			}
		} while (!program.isTerminated());

		StringBuilder result = new StringBuilder();
		result.append("Final state: " + program + "\n");
		result.append("History: " + history + "\n");
		result.append("Termination status: "
				+ (deadlockOccurred ? "deadlock" : "graceful") + "\n");
		return result.toString();
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Executor)) {
			return false;
		}
		return program.toString().equals(((Executor) other).program.toString());
	}
}
