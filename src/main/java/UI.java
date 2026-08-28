import java.util.Scanner;

public class UI {
    public static final String BOTNAME = "AnswerMe";
    public static final String HLINE = "____________________________________________________________";
    private static final Scanner SCANNER = new Scanner(System.in);

    public UI() {

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

    public static String readUserInput() {
        return SCANNER.nextLine();
    }

    public static void listTasks(TaskList taskList) {
        if (!taskList.isEmpty()) {
            System.out.println("\t" + HLINE);
            System.out.println("\tHere are the tasks in your list:");
            System.out.print(taskList.toString());
            System.out.println("\t" + HLINE + "\n");
        }
        else {
            // empty list -> print message
            formatOutputString("Task List is Empty!");
        }
    }

    public static void formatOutputString(String toPrint) {
        System.out.println("\t" + HLINE);
        for (String line: toPrint.split("\n")) {
            System.out.println("\t" + line);
        }
        System.out.println("\t" + HLINE + "\n");
    }

    public static void printAddNewItem(Task t, int taskCount) {
        formatOutputString("Got it. I have added this task:\n"
                + t + "\nYou now have " + taskCount + " tasks in the list.");
    }
    public static void printDeleteItem(Task t, int taskCount) {
        formatOutputString("Noted. I will remove this task:\n"
                + t + "\nYou now have " + taskCount + " tasks in the list.");
    }
}
