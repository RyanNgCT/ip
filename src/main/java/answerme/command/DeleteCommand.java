package answerme.command;

import answerme.exception.AnswerMeException;
import answerme.storage.Storage;
import answerme.task.Task;
import answerme.task.TaskList;
import answerme.ui.Ui;

/**
 * Represents a command that deletes a task from the task list.
 */
public class DeleteCommand extends Command {
    private final int taskIndex;

    /**
     * Creates a command that deletes the task at the specified index.
     *
     * @param taskIndex The index of the task to delete.
     */
    public DeleteCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    /**
     * {@inheritDoc}
     *
     * Deletes the task at the specified index, displays a confirmation, and saves the task list.
     *
     * @throws AnswerMeException If no task exists at the specified index.
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage)
            throws AnswerMeException {
        Task task = taskList.getTask(taskIndex);
        taskList.remove(task);
        ui.printDeleteItem(task, taskList.size());
        storage.saveTasks(taskList);
    }
}
