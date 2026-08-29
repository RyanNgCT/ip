package answerme.command;

import answerme.exception.AnswerMeException;
import answerme.task.Task;
import answerme.task.TaskList;
import answerme.storage.Storage;
import answerme.ui.Ui;

public class DeleteCommand extends Command {
    private final int taskIndex;

    public DeleteCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage)
            throws AnswerMeException {
        Task t = taskList.getTask(taskIndex);
        taskList.remove(t);
        ui.printDeleteItem(t, taskList.size());
        storage.saveTasks(taskList);
    }
}
