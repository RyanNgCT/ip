package answerme.command;

import answerme.exception.AnswerMeException;
import answerme.task.Task;
import answerme.task.TaskList;
import answerme.storage.Storage;
import answerme.ui.Ui;

public class UnmarkCommand extends Command {
    private final int taskIndex;

    public UnmarkCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }


    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage)
            throws AnswerMeException {
        Task t = taskList.getTask(taskIndex);
        t.setIncomplete();
        ui.showMessage("OK, I've marked this task as not done yet\n" + t);
        storage.saveTasks(taskList);
    }
}
