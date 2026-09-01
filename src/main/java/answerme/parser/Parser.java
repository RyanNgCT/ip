package answerme.parser;

import answerme.command.Command;
import answerme.command.ExitCommand;
import answerme.command.ListCommand;
import answerme.command.MarkCommand;
import answerme.command.UnmarkCommand;
import answerme.command.DeleteCommand;
import answerme.command.AddToDoCommand;
import answerme.command.AddDeadlineCommand;
import answerme.command.AddEventCommand;
import answerme.exception.AnswerMeException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Parses user input into the corresponding command objects.
 */
public class Parser {

    /**
     * Constructs a new Parser object.
     */
    public Parser() {

    }

    /**
     * Parses a user input string into the corresponding command.
     *
     * @param userResponse The raw command string entered by the user.
     * @return The command object to be executed.
     * @throws AnswerMeException If the raw command is empty, unknown or
     *                           malformed.
     */
    public static Command parse(String userResponse)
            throws AnswerMeException {
        if (userResponse == null || userResponse.isBlank()) {
            throw new AnswerMeException("Please enter a command.");
        }

        String[] responseParts = userResponse.trim().split("\\s+", 2);
        String command = responseParts[0].toLowerCase();
        String args = extractArgs(responseParts);

        switch(command) {
            case "bye":
                return new ExitCommand();

            case "list":
                return new ListCommand();

            case "mark":
                return new MarkCommand(extractTaskIndex(args));

            case "unmark":
                return new UnmarkCommand(extractTaskIndex(args));

            case "delete":
                return new DeleteCommand(extractTaskIndex(args));

            case "todo":
                return parseToDo(args);

            case "deadline":
                return parseDeadline(args);

            case "event":
                return parseEvent(args);

            default:
                throw new AnswerMeException("I'm not sure what you mean :(");
        }
    }

    /**
     * Extracts a task index from the command arguments.
     *
     * @param args The arguments containing the task index.
     * @return The extracted task index.
     * @throws AnswerMeException If the index is missing, invalid or
     *                           less than one.
     */
    private static int extractTaskIndex(String args) throws AnswerMeException {
        if (args.isBlank()) {
            throw new AnswerMeException("An index must be supplied for this command.");
        }

        try {
            int index = Integer.parseInt(args);
            if (index < 1) {
                throw new AnswerMeException("The list index must be at least 1.");
            }
            return index - 1;
        } catch (NumberFormatException e) {
            throw new AnswerMeException("The list index must be a valid integer.");
        }
    }

    /**
     * Extracts and joins the argument components of a command.
     *
     * @param responseParts The components of the user command.
     * @return The arguments joined into a single string.
     */
    private static String extractArgs(String[] responseParts) {
        String[] resized = Arrays.copyOfRange(responseParts,1,responseParts.length);
        return String.join(" ", resized);
    }

    /**
     * Parses arguments for a command that adds a todo task.
     *
     * @param args The arguments containing the task description.
     * @return A command that adds the specified todo task.
     * @throws AnswerMeException If the task description is missing.
     */
    private static Command parseToDo(String args)
            throws AnswerMeException{
        if (args.isBlank()) {
            throw new AnswerMeException("Format: todo <description>");
        }
        return new AddToDoCommand(args);
    }

    /**
     * Parses arguments for a command that adds a deadline task.
     *
     * @param args The arguments containing the task description and deadline.
     * @return A command that adds the specified deadline task.
     * @throws AnswerMeException If the description, deadline flag, or deadline
     *                           value is invalid.
     */
    private static Command parseDeadline(String args)
            throws AnswerMeException {
        String[] segments = args.split("(?=/by)");
        String desc = segments[0].trim();
        HashMap<String, String> flags = extractFlags(segments);

        if (desc.isBlank() || !flags.containsKey("/by")) {
            throw new AnswerMeException("Format: deadline <description> /by <when>");
        }

        DateTimeParser dateTimeParser = new DateTimeParser();
        LocalDateTime by = dateTimeParser.parseDateTime(flags.get("/by"));
        return new AddDeadlineCommand(desc, by);
    }

    /**
     * Parses arguments for a command that adds an event task.
     *
     * @param args The arguments containing the task description, start time
     *             and end time.
     * @return A command that adds the specified event task.
     * @throws AnswerMeException If the event details are missing, invalid or
     *                           in the wrong order.
     */
    private static Command parseEvent(String args)
            throws AnswerMeException {
        String[] segments = args.split("(?=/(?:from|to)\\b)");
        String description = segments[0].trim();
        HashMap<String, String> flags = extractFlags(segments);

        if (description.isBlank() || !flags.containsKey("/from")
                || !flags.containsKey("/to")) {
            throw new AnswerMeException("Format: event <description> /from <when> /to <when>");
        }

        DateTimeParser dateTimeParser = new DateTimeParser();
        LocalDateTime from = dateTimeParser.parseDateTime(flags.get("/from"));
        LocalDateTime to = dateTimeParser.parseDateTime(flags.get("/to"));

        if (from.isAfter(to)) {
            throw new AnswerMeException("'From' datetime must occur before 'To'.");
        }
        return new AddEventCommand(description, from, to);
    }

    /**
     * Extracts command flags and their values from supplied argument segments.
     *
     * @param segments The argument segments from which to extract flags.
     * @return A mapping from each flag to its value.
     * @throws AnswerMeException If a flag does not have a specified value.
     */
    private static HashMap<String, String> extractFlags(String[] segments)
            throws AnswerMeException {
        HashMap<String, String> flags = new HashMap<>();

        for (int i = 1; i < segments.length; i++) {
            String segment = segments[i].trim();
            String[] argList = segment.split(" ", 2);
            if (argList.length < 2 || argList[1].isBlank()) {
                throw new AnswerMeException("Every flag must be followed by an argument.");
            }
            flags.put(argList[0], argList[1]);
        }
        return flags;
    }
}
