package answerme.command;

import answerme.exception.AnswerMeException;
import answerme.task.Task;
import answerme.task.TaskList;
import answerme.storage.Storage;
import answerme.ui.Ui;

public class MarkCommand extends Command {
    private final int taskIndex;

    public MarkCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage)
            throws AnswerMeException {
        Task t = taskList.getTask(taskIndex);
        t.setComplete();
        ui.showMessage("Nice! I have marked this task as done:\n" + t);
        storage.saveTasks(taskList);
    }
}
