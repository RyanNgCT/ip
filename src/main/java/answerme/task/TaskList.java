package answerme.task;

import answerme.exception.AnswerMeException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents a list of tasks.
 */
public class TaskList extends ArrayList<Task>{
    /**
     * Constructs an empty task list.
     */
    public TaskList() {
        super();
    }

    /**
     * Constructs a task list from a collection of tasks.
     *
     * @param collection The collection of tasks to be added.
     */
    public TaskList(Collection<Task> collection) {
        super(collection);
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
