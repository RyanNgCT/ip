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
        } catch (AnswerMeException exception) {
            ui.showLoadingError(exception.getMessage());
            taskList = new TaskList();
        }
    }

    /**
     * Runs the application until the user enters the exit command.
     */
    public void run() {
        ui.showWelcome();
        boolean shouldExit = false;
        while (!shouldExit) {
            String input = ui.readUserInput();
            CommandResult result = processCommand(input);
            shouldExit = result.shouldExit();
        }
    }

    /**
     * Processes the user's input by parsing and executing commands.
     *
     * @param input The user's input.
     * @return The command result containing the response and exit status.
     */
    public CommandResult processCommand(String input) {
        try {
            Command command = Parser.parse(input);
            command.execute(taskList, ui, storage);
            return new CommandResult(ui.getLatestResponse(), command.isExit());
        } catch (AnswerMeException exception) {
            String errorMessage = exception.getMessage();
            ui.showMessage(errorMessage);

            return new CommandResult(errorMessage, false);
        }
    }

    /**
     * Returns the chatbot's welcome message.
     *
     * @return The chatbot's welcome message.
     */
    public String showWelcomeMessage() {
        return ui.getShortWelcome();
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
