// imports
import java.time.LocalDateTime;

import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.HashMap;

public class AnswerMe {
    private static TaskList taskList = new TaskList();

    public static void main(String[] args) {
        Storage storage = new Storage();
        AnswerMe.taskList = storage.loadTasks();
        Ui ui = new Ui();
        ui.showWelcome();
        boolean exitProgram = false;
        do {
            String userResponse = ui.readUserInput();
            try {
                exitProgram = process(userResponse);
            }
            catch(AnswerMeException e) {
                ui.showMessage(e.getMessage());
            }
        }
        while (!exitProgram);
        ui.showMessage("Bye. Hope to see you again soon!");
    }


    public static boolean process(String userResponse) throws AnswerMeException {
        String[] responseParts = userResponse.split(" ");
        Ui ui = new Ui();
        Storage storage = new Storage();

        switch (responseParts[0].toLowerCase()){
            case "bye":
                return true;

            case "list":
                ui.listTasks(AnswerMe.taskList);
                break;

            case "mark":
            case "unmark":
            case "delete":
                if (AnswerMe.taskList.isEmpty()) {
                    ui.showMessage("The list is empty so we have nothing to " + responseParts[0] + ".");
                    break;
                }
                try {
                    Integer index = extractListIndex(responseParts);
                    Task t = AnswerMe.taskList.get(index);

                    // set, unset or delete based on first arg
                    if (responseParts[0].equals("mark")) {
                        t.setComplete();
                        ui.showMessage("Nice! I have marked this task as done:\n" + t);
                    }
                    else if (responseParts[0].equals("unmark")) {
                        t.setIncomplete();
                        ui.showMessage("OK, I've marked this task as not done yet\n" + t);
                    }
                    else {
                        AnswerMe.taskList.remove(t);
                        ui.printDeleteItem(t, AnswerMe.taskList.size());
                    }
                    storage.saveTasks(AnswerMe.taskList);
                }
                catch (AnswerMeException e) {
                    ui.showMessage(e.getMessage());
                }
                catch (IndexOutOfBoundsException e) {
                    ui.showMessage("Please supply a valid index!");
                }
                break;

            default:
                try {
                    addTask(userResponse);
                }
                catch (AnswerMeException e) {
                    ui.showMessage(e.getMessage());
                }
                break;
        }
        return false;
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

    public static void addTask(String userResponse) throws AnswerMeException{
        String[] responseParts = userResponse.split(" ");
        String command = responseParts[0].toLowerCase();
        Task t;
        Ui ui = new Ui();
        Storage storage = new Storage();
        switch (command) {
            case "todo":
                String todoArgs = extractArgs(responseParts);
                if (todoArgs.isEmpty()) {
                    throw new AnswerMeException("Format: todo <description>");
                }
                t = new ToDo(todoArgs);
                AnswerMe.taskList.add(t);
                ui.printAddNewItem(t, AnswerMe.taskList.size());
                storage.saveTasks(AnswerMe.taskList);
                break;

            case "deadline":
            case "event":
                String args = extractArgs(responseParts);
                String[] segments = args.split("(?=/by|/from|/to)");
                String desc = segments[0].trim(); // task title
                HashMap<String, String> flags = new HashMap<>();
                DateTimeParser dtParser = new DateTimeParser();

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
                    try {
                        LocalDateTime by = dtParser.parseDateTime(flags.get("/by"));
                        t = new Deadline(desc, by);
                    } catch (DateTimeParseException e) {
                        throw new AnswerMeException("Ensure date/time is formatted correctly.");
                    }
                }
                else {
                    if (!flags.containsKey("/from") || !flags.containsKey("/to")) {
                        throw new AnswerMeException("Format: <event> <description> /from <when> /to <when>.");
                    }
                    LocalDateTime from = dtParser.parseDateTime(flags.get("/from"));
                    LocalDateTime to = dtParser.parseDateTime(flags.get("/to"));
                    if (!from.isBefore(to)) {
                        throw new AnswerMeException("'From' datetime must occur before 'To'");
                    }
                    t = new Event(desc, from, to);
                }
                AnswerMe.taskList.add(t);
                ui.printAddNewItem(t, AnswerMe.taskList.size());
                storage.saveTasks(AnswerMe.taskList);
                break;

            default:
                throw new AnswerMeException("I'm not sure what you mean :(");
        }
    }

    public static String extractArgs(String[] responseParts) {
        String[] resized = Arrays.copyOfRange(responseParts,1,responseParts.length);
        return String.join(" ", resized);
    }
}
