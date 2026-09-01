package answerme.command;

import answerme.exception.AnswerMeException;
import answerme.storage.Storage;
import answerme.task.Task;
import answerme.task.TaskList;
import answerme.ui.Ui;

public class DeleteCommand extends Command {
    private final int taskIndex;

    public DeleteCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage)
            throws AnswerMeException {
        Task task = taskList.getTask(taskIndex);
        taskList.remove(task);
        ui.printDeleteItem(task, taskList.size());
        storage.saveTasks(taskList);
    }
}
