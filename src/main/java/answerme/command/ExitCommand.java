package answerme.command;

import answerme.task.TaskList;
import answerme.storage.Storage;
import answerme.ui.Ui;

/**
 * Represents a command that exits the application.
 */
public class ExitCommand extends Command {

    /**
     * Creates a command that exits the application.
     */
    public ExitCommand() {

    }

    /**
     * {@inheritDoc}
     *
     * Displays a farewell message before the application exits.
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        ui.showMessage(" Bye. Hope to see you again soon!");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isExit() {
        return true;
    }
}
