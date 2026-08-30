package answerme.command;

import answerme.task.TaskList;
import answerme.storage.Storage;
import answerme.ui.Ui;

public class ListCommand extends Command {

    public ListCommand() {

    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        ui.listTasks(taskList);
    }
}
