// imports
import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;

public class AnswerMe {
    public static final String BOTNAME = "AnswerMe";
    public static final String HLINE = "____________________________________________________________";
    private static final Scanner SCANNER = new Scanner(System.in);
    private static ArrayList<Task> taskList = new ArrayList<>();

    public static void main(String[] args) {
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
        System.out.println(AnswerMe.HLINE);
        System.out.println("What can I do for you today?");
        System.out.println(AnswerMe.HLINE);

        boolean exitProgram = false;
        do {
            String userResponse = readUserInput();
            exitProgram = process(userResponse);
        }
        while (!exitProgram);

        formatOutputString("Bye. Hope to see you again soon!");
    }

    public static String readUserInput() {
        return AnswerMe.SCANNER.nextLine();
    }

    public static boolean process(String userResponse) {
        String[] responseParts = userResponse.split(" ");

        switch (responseParts[0].toLowerCase()){
            case "bye":
                return true;

            case "list":
                listTasks();
                break;

            case "mark":
            case "unmark":
                try {
                    Integer index = extractListIndex(responseParts);
                    Task t = AnswerMe.taskList.get(index);

                    // set or unset based on first arg
                    if (responseParts[0].equals("mark")) {
                        t.setComplete();
                        formatOutputString("Nice! I have marked this task as done:\n\t" + t.toString());
                    }
                    else {
                        t.setIncomplete();
                        formatOutputString("OK, I've marked this task as not done yet\n\t" + t.toString());
                    }
                }
                catch (IllegalArgumentException e) {
                    formatOutputString(e.getMessage());
                }
                catch (IndexOutOfBoundsException e) {
                    formatOutputString("Please supply a valid index!");
                }
                break;

            default:
                addTask(userResponse);
                break;
        }
        return false;
    }

    public static void listTasks() {
        if (!AnswerMe.taskList.isEmpty()) {
            System.out.println("\t" + AnswerMe.HLINE);
            System.out.println("\tHere are the tasks in your list:");
            for (int i = 0; i < AnswerMe.taskList.size(); i++) {
                System.out.println("\t" + (i + 1) + ". " + AnswerMe.taskList.get(i));
            }
            System.out.println("\tNow you have " + AnswerMe.taskList.size() + " tasks in the list.");
            System.out.println("\t" + AnswerMe.HLINE + "\n");
        }
        else {
            // empty list -> print message
            formatOutputString("Task List is Empty!");
        }
    }

    public static Integer extractListIndex(String[] responseParts) throws IllegalArgumentException, NumberFormatException {
        if (responseParts == null || responseParts.length < 2) {
            throw new IllegalArgumentException("An index must be supplied for this command.");
        }
        int index;
        try {
            index = Integer.parseInt(responseParts[1]);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("The list index must be a valid integer.", ex);
        }

        if (index < 1) {
            throw new IllegalArgumentException("The list index must be at least 1.");
        }
        return index - 1;
    }

    public static void formatOutputString(String toPrint) {
        System.out.println("\t" + AnswerMe.HLINE);
        System.out.println("\t" + toPrint);
        System.out.println("\t" + AnswerMe.HLINE + "\n");
    }

    public static void addTask(String userResponse) {
        String[] responseParts = userResponse.split(" ");
        switch (responseParts[0].toLowerCase()) {
            case "todo":
                String args = extractArgs(responseParts);
                AnswerMe.taskList.add(new ToDo(args));
                formatOutputString("added: " + args);
                break;
            case "deadline":
            case "event":
                break;
            default:
                // to change this echo behaviour later
                formatOutputString(userResponse);
        }

//        AnswerMe.taskList.add(new Task(userResponse));
//        formatOutputString("added: " + userResponse);
    }

    public static String extractArgs(String[] responseParts) {
        String[] resized = Arrays.copyOfRange(responseParts,1,responseParts.length);
        return String.join(" ", resized);
    }
}
