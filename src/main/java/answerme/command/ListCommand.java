package answerme.command;

import answerme.storage.Storage;
import answerme.task.TaskList;
import answerme.ui.Ui;

public class ListCommand extends Command {
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        ui.listTasks(taskList);
    }
}
