package answerme;

import answerme.command.Command;
import answerme.exception.AnswerMeException;
import answerme.parser.Parser;
import answerme.storage.Storage;
import answerme.task.TaskList;
import answerme.ui.Ui;

/**
 * Represents the AnswerMe task-management chatbot.
 */
public class AnswerMe {
    private Storage storage;
    private TaskList taskList;
    private Ui ui;

    /**
     * Constructs the chatbot application and loads saved tasks
     * from storage if available.
     */
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

    /**
     * Runs the application until the user enters the exit command.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String input = ui.readUserInput();
                Command command = Parser.parse(input);
                command.execute(taskList, ui, storage);
                isExit = command.isExit();
            } catch (AnswerMeException e) {
                ui.showMessage(e.getMessage());
            }
        }
    }

    public String getResponse(String input) {
        return input;
    }

    /**
     * Starts a new instance of the AnswerMe chatbot application.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        AnswerMe answerMeBot = new AnswerMe();
        answerMeBot.run();
    }
}
