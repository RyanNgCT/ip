package answerme;

import answerme.command.Command;
import answerme.command.CommandResult;
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
     *
     * @param isCliInstance Whether the application is running
     *                      in command-line mode.
     */
    public AnswerMe(boolean isCliInstance) {
        storage = new Storage();
        ui = new Ui(isCliInstance);
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

    /**
     * Process the user's input by parsing and executing the
     * corresponding command.
     *
     * @param input The user's input.
     * @return The command result containing the result and exit status.
     */
    public CommandResult processCommand(String input) {
        try {
            Command command = Parser.parse(input);
            command.execute(taskList, ui, storage);
            return new CommandResult(ui.getLatestResponse(), command.isExit());
        } catch (AnswerMeException e) {
            return new CommandResult(e.getMessage(), false);
        }
    }

    /**
     * Returns the chatbot's welcome message.
     *
     * @return The chatbot's welcome message.
     */
    public String showWelcomeMessage() {
        return ui.printShortWelcome();
    }

    /**
     * Returns the loading error message when saved tasks could not be loaded.
     *
     * @return The loading error message, or {@code null} if loading succeeded.
     */
    public String getLoadingErrorMessage() {
        return ui.getLoadingErrorMessage();
    }

    /**
     * Starts a new instance of the AnswerMe chatbot application.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        AnswerMe answerMeBot = new AnswerMe(true);
        answerMeBot.run();
    }
}
