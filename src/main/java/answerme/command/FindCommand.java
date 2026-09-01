package answerme.command;

import answerme.exception.AnswerMeException;
import answerme.storage.Storage;
import answerme.task.TaskList;
import answerme.task.Task;
import answerme.ui.Ui;

/**
 * Represents a command that searches the task list for tasks containing
 * a specified keyword.
 */
public class FindCommand extends Command {
    private final String toFind;

    /**
     * Constructs a new FindCommand using user-supplied input.
     *
     * @param toFind The keyword string that the task should contain.
     */
    public FindCommand(String toFind) {
        this.toFind = toFind;
    }

    /**
     * {@inheritDoc}
     *
     * Finds and displays all tasks that contains the specified search term
     * and ignores case-sensitivity.
     *
     * @throws AnswerMeException If an error occurs while executing the find command.
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws AnswerMeException {
        TaskList foundTasks = new TaskList();
        for (Task t: taskList) {
            if (t.toString().toLowerCase().contains(toFind.toLowerCase())) {
                foundTasks.add(t);
            }
        }
        ui.listTasks(foundTasks, "Here are the matching tasks in your list:",
                "No tasks matching " + toFind + " were found" );
    }
}
