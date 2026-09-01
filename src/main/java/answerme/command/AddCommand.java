package answerme.command;

import answerme.task.Task;
import answerme.task.TaskList;
import answerme.storage.Storage;
import answerme.ui.Ui;

/**
 * Represents a command that adds a task to the task list.
 */
public abstract class AddCommand extends Command {
    private final Task task;

    /**
     * Creates a command that adds the specified task.
     *
     * @param task The task to add.
     */
    protected AddCommand(Task task) {
        this.task = task;
    }

    /**
     * {@inheritDoc}
     *
     * Adds this command's task to the task list, displays a confirmation,
     * and saves the task list.
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage)  {
        taskList.add(task);
        ui.printAddNewItem(task, taskList.size());
        storage.saveTasks(taskList);
    }

    /**
     * Checks equality of {@code AddCommand} objects.
     *
     * @param obj   the reference object with which to compare.
     * @return {@code true} if objects are equal, otherwise {@code false}.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        else if (obj instanceof AddCommand other) {
            return task.equals(other.task);
        }
        return false;
    }
}
