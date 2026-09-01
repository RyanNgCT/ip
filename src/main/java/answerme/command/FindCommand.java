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
     * @param toFind
     */
    public FindCommand(String toFind) {
        this.toFind = toFind;
    }

    /**
     * Finds and displays all tasks that contains the specified search term
     * and ignores case-sensitivity.
     *
     * @param taskList The list of tasks to search through.
     * @param ui The user interface used to display matching tasks.
     * @param storage Storage handler to persist changes in a text file.
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
