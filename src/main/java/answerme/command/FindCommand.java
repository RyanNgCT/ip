package answerme.command;

import answerme.exception.AnswerMeException;
import answerme.storage.Storage;
import answerme.task.TaskList;
import answerme.task.Task;
import answerme.ui.Ui;

public class FindCommand extends Command {
    private final String toFind;

    public FindCommand(String toFind) {
        this.toFind = toFind;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws AnswerMeException {
        TaskList foundTasks = new TaskList();
        for (Task t: taskList) {
            if (t.getDesc().contains(toFind)) {
                foundTasks.add(t);
            }
        }
        ui.listTasks(foundTasks, "Here are the matching tasks in your list:",
                "No tasks matching " + toFind + " were found" );
    }
}
