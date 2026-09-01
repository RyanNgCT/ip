package answerme.ui;

import answerme.task.Task;
import answerme.task.TaskList;

import java.util.Scanner;

/**
 * Handles the user's interaction with the AnswerMe chatbot.
 */
public class Ui {
    private final String BOTNAME = "AnswerMe";
    private final String HLINE = "____________________________________________________________";
    private final Scanner SCANNER = new Scanner(System.in);

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
        System.out.println("Hello! I'm " + BOTNAME + ", your personal assistant bot.\nWhat can I do for you today?\n");
        System.out.println(HLINE);
        System.out.println("What can I do for you today?");
        System.out.println(HLINE);
    }

    /**
     * Reads a line of input from the user.
     *
     * @return The input entered by the user.
     */
    public String readUserInput() {
        return SCANNER.nextLine();
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
            System.out.println("\t" + HLINE);
            System.out.println("\t" + heading);
            System.out.print(taskList.toString());
            System.out.println("\t" + HLINE + "\n");
        }
        else {
            showMessage(emptyMessage);
        }
    }

    /**
     * Prints the default message template to screen.
     *
     * @param toPrint The message to display.
     */
    public void showMessage(String toPrint) {
        System.out.println("\t" + HLINE);
        for (String line: toPrint.split("\n")) {
            System.out.println("\t" + line);
        }
        System.out.println("\t" + HLINE + "\n");
    }

    /**
     * Displays an error message when saved tasks cannot be loaded
     * from Storage.
     */
    public void showLoadingError() {
        System.out.println(HLINE);
        System.out.println("!ERROR! Unable to load saved file.\nInitializing task list as empty...");
        System.out.println(HLINE);
    }

    /**
     * Displays an informational message that a task has been added.
     *
     * @param t The added task.
     * @param taskCount The number of tasks currently in the list.
     */
    public void printAddNewItem(Task t, int taskCount) {
        this.showMessage("Got it. I have added this task:\n"
                + t + "\nYou now have " + taskCount + " tasks in the list.");
    }

    /**
     * Displays an informational message that a task has been deleted.
     *
     * @param t The deleted task.
     * @param taskCount The number of tasks currently in the list.
     */
    public void printDeleteItem(Task t, int taskCount) {
        this.showMessage("Noted. I will remove this task:\n"
                + t + "\nYou now have " + taskCount + " tasks in the list.");
    }
}
