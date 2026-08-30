package answerme;

import answerme.command.Command;
import answerme.exception.AnswerMeException;
import answerme.parser.Parser;
import answerme.storage.Storage;
import answerme.task.TaskList;
import answerme.ui.Ui;

public class AnswerMe {
    private Storage storage;
    private TaskList taskList;
    private Ui ui;

    public AnswerMe() {
        storage = new Storage();
        ui = new Ui();
        try {
            taskList = new TaskList(storage.loadTasks());
        } catch (AnswerMeException e) {
            ui.showLoadingError();
            taskList = new TaskList();
        }
    }

    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String input = ui.readUserInput();
                Command cmd = Parser.parse(input);
                cmd.execute(taskList, ui, storage);
                isExit = cmd.isExit();
            } catch (AnswerMeException e) {
                ui.showMessage(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        AnswerMe answerMeBot = new AnswerMe();
        answerMeBot.run();
    }
}
