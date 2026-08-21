// imports
import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

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
                        formatOutputString("Nice! I have marked this task as done:\n" + t);
                    }
                    else {
                        t.setIncomplete();
                        formatOutputString("OK, I've marked this task as not done yet\n" + t);
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
                try {
                    addTask(userResponse);
                }
                catch (IllegalArgumentException e) {
                    formatOutputString(e.getMessage());
                }
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
            // System.out.println("\t" + getListStatus());
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
        for (String line: toPrint.split("\n")) {
            System.out.println("\t" + line);
        }
        System.out.println("\t" + AnswerMe.HLINE + "\n");
    }

    public static void addTask(String userResponse) {
        String[] responseParts = userResponse.split(" ");
        String command = responseParts[0].toLowerCase();
        Task t;
        switch (command) {
            case "todo":
                String todoArgs = extractArgs(responseParts);
                t = new ToDo(todoArgs);
                AnswerMe.taskList.add(t);
                printAddNewItem(t);
                break;

            case "deadline":
            case "event":
                String args = extractArgs(responseParts);
                String[] segments = args.split("(?=/by|/from|/to)");
                String desc = segments[0].trim(); // task title
                HashMap<String, String> flags = new HashMap<>();

                // skip over task title
                for (int i = 1; i < segments.length; i++) {
                    String segment = segments[i].trim();
                    String[] argList = segment.split(" ", 2);
                    if (argList[1].isBlank()) {
                        throw new IllegalArgumentException("Every flag must be followed by an argument.");
                    }
                    flags.put(argList[0], argList[1]);
                }
                if (command.equals("deadline")) {
                    if (!flags.containsKey("/by")) {
                        throw new IllegalArgumentException("Format: <deadline> <description> /by <when>.");
                    }
                    t = new Deadline(desc, flags.get("/by"));
                }
                else {
                    if (!flags.containsKey("/from") || !flags.containsKey("/to")) {
                        throw new IllegalArgumentException("Format: <event> <description> /from <when> /to <when>.");
                    }
                    t = new Event(desc, flags.get("/from"), flags.get("/to"));
                }
                AnswerMe.taskList.add(t);
                printAddNewItem(t);
                break;

            default:
                // to change this echo behaviour later
                // formatOutputString(userResponse);
                throw new AnswerMeException("I'm not sure what you mean :(");
        }
    }

    public static String extractArgs(String[] responseParts) {
        String[] resized = Arrays.copyOfRange(responseParts,1,responseParts.length);
        return String.join(" ", resized);
    }

    public static String getListStatus() {
        return "Now you have " + AnswerMe.taskList.size() + " tasks in the list.";
    }

    public static void printAddNewItem(Task t) {
        String message = String.format("Got it. I've added this task:\n%s\n%s", t.toString(), getListStatus());
        formatOutputString(message);
    }
}
