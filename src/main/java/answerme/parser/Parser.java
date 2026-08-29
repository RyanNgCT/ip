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

public class Parser {
    Parser() {

    }
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

    private static String extractArgs(String[] responseParts) {
        String[] resized = Arrays.copyOfRange(responseParts,1,responseParts.length);
        return String.join(" ", resized);
    }

    private static Command parseToDo(String args)
            throws AnswerMeException{
        if (args.isBlank()) {
            throw new AnswerMeException("Format: todo <description>");
        }
        return new AddToDoCommand(args);
    }

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

        if (!from.isBefore(to)) {
            throw new AnswerMeException("'From' datetime must occur before 'To'.");
        }
        return new AddEventCommand(description, from, to);
    }

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
