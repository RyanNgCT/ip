package answerme.command;

import answerme.exception.AnswerMeException;
import answerme.storage.Storage;
import answerme.task.TaskList;
import answerme.ui.Ui;

public abstract class Command {
    public abstract void execute(TaskList taskList, Ui ui, Storage storage)
            throws AnswerMeException;

    public boolean isExit() {
        return false;
    }
}
