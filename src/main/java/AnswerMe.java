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

        AnswerMe.taskList = Storage.loadTasks();
        boolean exitProgram = false;
        do {
            String userResponse = readUserInput();
            try {
                exitProgram = process(userResponse);
            }
            catch(AnswerMeException e) {
                formatOutputString(e.getMessage());
            }
        }
        while (!exitProgram);

        formatOutputString("Bye. Hope to see you again soon!");
    }

    public static String readUserInput() {
        return AnswerMe.SCANNER.nextLine();
    }

    public static boolean process(String userResponse) throws AnswerMeException {
        String[] responseParts = userResponse.split(" ");

        switch (responseParts[0].toLowerCase()){
            case "bye":
                return true;

            case "list":
                listTasks();
                break;

            case "mark":
            case "unmark":
            case "delete":
                if (AnswerMe.taskList.isEmpty()) {
                    formatOutputString("The list is empty so we have nothing to " + responseParts[0] + ".");
                    break;
                }
                try {
                    Integer index = extractListIndex(responseParts);
                    Task t = AnswerMe.taskList.get(index);

                    // set, unset or delete based on first arg
                    if (responseParts[0].equals("mark")) {
                        t.setComplete();
                        formatOutputString("Nice! I have marked this task as done:\n" + t);
                    }
                    else if (responseParts[0].equals("unmark")) {
                        t.setIncomplete();
                        formatOutputString("OK, I've marked this task as not done yet\n" + t);
                    }
                    else {
                        AnswerMe.taskList.remove(t);
                        printDeleteItem(t);
                    }
                    Storage.saveTasks(AnswerMe.taskList);
                }
                catch (AnswerMeException e) {
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
                catch (AnswerMeException e) {
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

    public static Integer extractListIndex(String[] responseParts) throws AnswerMeException {
        if (responseParts == null || responseParts.length < 2) {
            throw new AnswerMeException("An index must be supplied for this command.");
        }
        int index;
        try {
            index = Integer.parseInt(responseParts[1]);
        } catch (NumberFormatException ex) {
            throw new AnswerMeException("The list index must be a valid integer.");
        }

        if (index < 1 || index > AnswerMe.taskList.size()) {
            throw new AnswerMeException("The task does not exist in the list.");
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

    public static void addTask(String userResponse) throws AnswerMeException{
        String[] responseParts = userResponse.split(" ");
        String command = responseParts[0].toLowerCase();
        Task t;
        switch (command) {
            case "todo":
                String todoArgs = extractArgs(responseParts);
                if (todoArgs.isEmpty()) {
                    throw new AnswerMeException("Format: todo <description>");
                }
                t = new ToDo(todoArgs);
                AnswerMe.taskList.add(t);
                printAddNewItem(t);
                Storage.saveTasks(AnswerMe.taskList);
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
                        throw new AnswerMeException("Every flag must be followed by an argument.");
                    }
                    flags.put(argList[0], argList[1]);
                }
                if (command.equals("deadline")) {
                    if (!flags.containsKey("/by")) {
                        throw new AnswerMeException("Format: <deadline> <description> /by <when>.");
                    }
                    t = new Deadline(desc, flags.get("/by"));
                }
                else {
                    if (!flags.containsKey("/from") || !flags.containsKey("/to")) {
                        throw new AnswerMeException("Format: <event> <description> /from <when> /to <when>.");
                    }
                    t = new Event(desc, flags.get("/from"), flags.get("/to"));
                }
                AnswerMe.taskList.add(t);
                printAddNewItem(t);
                Storage.saveTasks(AnswerMe.taskList);
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
        return "You now have " + AnswerMe.taskList.size() + " tasks in the list.";
    }

    public static void printAddNewItem(Task t) {
        String message = String.format("Got it. I have added this task:\n%s\n%s", t.toString(), getListStatus());
        formatOutputString(message);
    }
    public static void printDeleteItem(Task t) {
        String message = String.format("Noted. I will remove this task:\n%s\n%s", t.toString(), getListStatus());
        formatOutputString(message);
    }
}
