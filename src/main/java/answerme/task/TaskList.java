package answerme.task;

import java.util.ArrayList;
import java.util.Collection;

import answerme.exception.AnswerMeException;

/**
 * Represents a list of tasks.
 */
public class TaskList extends ArrayList<Task> {
    /**
     * Represents a list of tasks.
     */
    public TaskList() {
        super();
    }

    /**
     * Constructs a task list from a collection of tasks.
     *
     * @param tasks The collection of tasks to be added.
     */
    public TaskList(Collection<Task> tasks) {
        super(tasks);
    }

    /**
     * Retrieves the specified task based on its index.
     *
     * @param taskIndex The index of the task to return.
     * @return The target task.
     * @throws AnswerMeException If the list is empty or the index is invalid.
     */
    public Task getTask(int taskIndex)
            throws AnswerMeException {
        if (this.size() == 0) {
            throw new AnswerMeException("The task list is empty so cannot perform operation.");
        }

        if (taskIndex < 0 || taskIndex >= size()) {
            throw new AnswerMeException("The task does not exist in the list.");
        }
        return this.get(taskIndex);
    }

    /**
     * {@inheritDoc}
     *
     * Returns the tasks as a printable numbered list.
     */
    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < this.size(); i++) {
            result += "\t" + (i + 1) + ". " + this.get(i) + "\n";
        }
        return result;
    }
}
