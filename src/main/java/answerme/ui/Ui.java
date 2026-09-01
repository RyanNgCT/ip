package answerme.ui;

import answerme.task.Task;
import answerme.task.TaskList;

import java.util.Scanner;

public class Ui {
    private final String BOTNAME = "AnswerMe";
    private final String HLINE = "____________________________________________________________";
    private final Scanner SCANNER = new Scanner(System.in);

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
        System.out.println("Hello! I'm " + BOTNAME + ", your personal assistant bot.\nWhat can I do for you today?\n");
        System.out.println(HLINE);
        System.out.println("What can I do for you today?");
        System.out.println(HLINE);
    }

    public String readUserInput() {
        return SCANNER.nextLine();
    }

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

    public void showMessage(String toPrint) {
        System.out.println("\t" + HLINE);
        for (String line: toPrint.split("\n")) {
            System.out.println("\t" + line);
        }
        System.out.println("\t" + HLINE + "\n");
    }

    public void showLoadingError() {
        System.out.println(HLINE);
        System.out.println("!ERROR! Unable to load saved file.\nInitializing task list as empty...");
        System.out.println(HLINE);
    }

    public void printAddNewItem(Task t, int taskCount) {
        this.showMessage("Got it. I have added this task:\n"
                + t + "\nYou now have " + taskCount + " tasks in the list.");
    }
    public void printDeleteItem(Task t, int taskCount) {
        this.showMessage("Noted. I will remove this task:\n"
                + t + "\nYou now have " + taskCount + " tasks in the list.");
    }
}
