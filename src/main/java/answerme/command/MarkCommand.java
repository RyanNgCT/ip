package answerme.command;

import answerme.exception.AnswerMeException;
import answerme.storage.Storage;
import answerme.task.Task;
import answerme.task.TaskList;
import answerme.ui.Ui;

/**
 * Represents a command that marks a task as complete.
 */
public class MarkCommand extends Command {
    private final int taskIndex;

    /**
     * Creates a command that marks the task at the specified index as complete.
     *
     * @param taskIndex The index of the task to mark as complete.
     */
    public MarkCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    /**
     * {@inheritDoc}
     *
     * Marks the task at the specified index as complete and saves the task list.
     *
     * @throws AnswerMeException If no task exists at the specified index.
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage)
            throws AnswerMeException {
        Task task = taskList.getTask(taskIndex);
        task.setComplete();
        ui.showMessage("Nice! I have marked this task as done:\n" + task);
        storage.saveTasks(taskList);
    }
}
