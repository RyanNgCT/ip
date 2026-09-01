package answerme.command;

import answerme.task.TaskList;
import answerme.storage.Storage;
import answerme.ui.Ui;

/**
 * Represents a command that displays all tasks in the task list.
 */
public class ListCommand extends Command {

    /**
     * Creates a command that displays all tasks.
     */
    public ListCommand() {

    }

    /**
     * {@inheritDoc}
     *
     * Displays all tasks in the task list.
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        ui.listTasks(taskList, "Here are the tasks in your list:", "Task list is empty!");
    }
}
