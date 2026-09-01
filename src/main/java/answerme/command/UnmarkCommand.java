package answerme.command;

import answerme.exception.AnswerMeException;
import answerme.task.Task;
import answerme.task.TaskList;
import answerme.storage.Storage;
import answerme.ui.Ui;

/**
 * Represents a command that marks a task as incomplete.
 */
public class UnmarkCommand extends Command {
    private final int taskIndex;

    /**
     * Creates a command that marks the task at the specified index as incomplete.
     *
     * @param taskIndex The index of the task to mark as incomplete.
     */
    public UnmarkCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }


    /**
     * {@inheritDoc}
     *
     * Marks the task at the specified index as incomplete and saves the task list.
     *
     * @throws AnswerMeException If no task exists at the specified index.
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage)
            throws AnswerMeException {
        Task t = taskList.getTask(taskIndex);
        t.setIncomplete();
        ui.showMessage("OK, I've marked this task as not done yet\n" + t);
        storage.saveTasks(taskList);
    }
}
