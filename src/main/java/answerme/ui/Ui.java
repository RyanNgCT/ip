package answerme.ui;

import java.util.Scanner;

import answerme.task.Task;
import answerme.task.TaskList;

public class Ui {
    private final String botName = "AnswerMe";
    private final String horizontalLine = "____________________________________________________________";
    private final Scanner scanner = new Scanner(System.in);

    public Ui() {

    }

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

    public String readUserInput() {
        return scanner.nextLine();
    }

    public void listTasks(TaskList taskList) {
        if (!taskList.isEmpty()) {
            System.out.println("\t" + horizontalLine);
            System.out.println("\tHere are the tasks in your list:");
            System.out.print(taskList.toString());
            System.out.println("\t" + horizontalLine + "\n");
        } else {
            // empty list -> print message
            this.showMessage("Task List is Empty!");
        }
    }

    public void showMessage(String toPrint) {
        System.out.println("\t" + horizontalLine);
        for (String line : toPrint.split("\n")) {
            System.out.println("\t" + line);
        }
        System.out.println("\t" + horizontalLine + "\n");
    }

    public void showLoadingError() {
        System.out.println(horizontalLine);
        System.out.println("!ERROR! Unable to load saved file.\nInitializing task list as empty...");
        System.out.println(horizontalLine);
    }

    public void printAddNewItem(Task task, int taskCount) {
        showMessage("Got it. I have added this task:\n"
                + task + "\nYou now have " + taskCount + " tasks in the list.");
    }

    public void printDeleteItem(Task task, int taskCount) {
        showMessage("Noted. I will remove this task:\n"
                + task + "\nYou now have " + taskCount + " tasks in the list.");
    }
}
