package answerme.ui;

import java.util.Scanner;

import answerme.task.Task;
import answerme.task.TaskList;

/**
 * Handles the user's interaction with the AnswerMe chatbot.
 */
public class Ui {
    private final String botName = "AnswerMe";
    private final String horizontalLine = "____________________________________________________________";
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Constructs the user interface for the chatbot.
     */
    public Ui() {

    }

    /**
     * Displays the chatbot's welcome message and banner.
     */
    public void showWelcome() {
        String banner = """
                      >>                                                        >=>       >=>          \s
                     >>=>                                                       >> >=>   >>=>          \s
                    >> >=>     >==>>==>   >===>  >=>      >=>   >==>    >> >==> >=> >=> > >=>   >==>   \s
                   >=>  >=>     >=>  >=> >=>      >=>  >  >=> >>   >=>   >=>    >=>  >=>  >=> >>   >=> \s
                  >=====>>=>    >=>  >=>   >==>   >=> >>  >=> >>===>>=>  >=>    >=>   >>  >=> >>===>>=>\s
                 >=>      >=>   >=>  >=>     >=>  >=>>  >=>=> >>         >=>    >=>       >=> >>       \s
                >=>        >=> >==>  >=> >=> >=> >==>    >==>  >====>   >==>    >=>       >=>  >====>  \s
                """;
        System.out.println(banner);
        System.out.println("Hello! I'm " + botName + ", your personal assistant bot.\n"
                + "What can I do for you today?\n");
        System.out.println(horizontalLine);
        System.out.println("What can I do for you today?");
        System.out.println(horizontalLine);
    }

    /**
     * Reads a line of input from the user.
     *
     * @return The input entered by the user.
     */
    public String readUserInput() {
        return scanner.nextLine();
    }

    /**
     * Displays the tasks in the given task list.
     *
     * @param taskList The task list to display.
     * @param heading The message to display if task list is not empty.
     * @param emptyMessage The message to display if task list is empty.
     */
    public void listTasks(TaskList taskList, String heading, String emptyMessage) {
        if (!taskList.isEmpty()) {
            System.out.println("\t" + horizontalLine);
            System.out.println("\t" + heading);
            System.out.print(taskList.toString());
            System.out.println("\t" + horizontalLine + "\n");
        } else {
            this.showMessage(emptyMessage);
        }
    }

    /**
     * Prints the default message template to screen.
     *
     * @param toPrint The message to display.
     */
    public void showMessage(String toPrint) {
        System.out.println("\t" + horizontalLine);
        for (String line : toPrint.split("\n")) {
            System.out.println("\t" + line);
        }
        System.out.println("\t" + horizontalLine + "\n");
    }

    /**
     * Displays an error message when saved tasks cannot be loaded
     * from Storage.
     */
    public void showLoadingError() {
        System.out.println(horizontalLine);
        System.out.println("!ERROR! Unable to load saved file.\nInitializing task list as empty...");
        System.out.println(horizontalLine);
    }

    /**
     * Displays an informational message that a task has been added.
     *
     * @param task The added task.
     * @param taskCount The number of tasks currently in the list.
     */
    public void printAddNewItem(Task task, int taskCount) {
        showMessage("Got it. I have added this task:\n"
                + task + "\nYou now have " + taskCount + " tasks in the list.");
    }

    /**
     * Displays an informational message that a task has been deleted.
     *
     * @param task The deleted task.
     * @param taskCount The number of tasks currently in the list.
     */
    public void printDeleteItem(Task task, int taskCount) {
        showMessage("Noted. I will remove this task:\n"
                + task + "\nYou now have " + taskCount + " tasks in the list.");
    }
}
